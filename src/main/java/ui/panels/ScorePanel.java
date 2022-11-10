package ui.panels;

import com.intellij.util.ui.JBUI;
import models.bean.Formula;
import models.bean.ITestData;
import models.bean.MethodTestData;
import models.bean.context.Context;
import services.interactivity.StatementInteractivity;
import ui.ViewResult;
import ui.ViewResultHolder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

/**
 * This class represents an indicator panel in the floating window
 */
public class ScorePanel extends JPanel {

    private JLabel label;
    private JSlider slider;
    private JPanel scale;
    private List<JPanel> indicators;
    private Context context;
    private ITestData element;
    private Formula formula;

    private double score;
    private StatementInteractivity interactivity;

    /**
     * @param label the label of the panel
     */
    public ScorePanel(String label) {
        this.indicators = new ArrayList<>();
        this.score = .0;
        this.label = createLabel(label);
        this.slider = createSlider();
        this.scale = createScalePanel();
        this.interactivity = new StatementInteractivity();
        initComponents();
        initResponseArea();
    }

    /**
     * This function calculates the suspiciousness score of an element, and it's context
     *
     * @param element the inspected element
     * @param context defines the type of the element
     * @param formula defines the currently selected formula
     */
    public void calculateScore(ITestData element, Context context, Formula formula) {
        //this check should be more civilized in the future
        if (element == null) {
            System.out.println("NULL again");
            return;
        }
        this.context = context;
        this.element = element;
        this.formula = formula;
        this.score = contextScore(element, context, formula);
        slider.setValue(0);
        initResponseArea();
        setScoreLabel();
    }

    public void setLabel(String componentName) {
        this.label.setText(componentName);
    }

    private void setScoreLabel() {
        int limit = calculateLimit();
        if (12 == limit) {
            limit = 11;
        } else if (-1 == limit) {
            limit = 0;
        }
        JLabel valueLabel = createLabel(String.format(Locale.US, "%.2f", score));
        valueLabel.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        valueLabel.setForeground(Color.BLACK);
        indicators.forEach(Container::removeAll);
        indicators.get(limit).add(valueLabel, BorderLayout.CENTER);
    }

    /**
     * This function calculates suspiciousness score based on the context If the context not the component itself, then the result will be the average of the elements in the context
     *
     * @param element the inspected element
     * @param context defines the type of the element
     * @param formula defines the currently selected formula
     * @return the suspiciousness score of the given element
     */
    private double contextScore(ITestData element, Context context, Formula formula) {
        switch (context) {
            default:
            case COMPONENT:
                return formulaScore(element, formula);
            case CLOSE_CONTEXT:
                return element.getCloseContext().stream().mapToDouble(x -> formulaScore(x, formula)).average().orElse(.0);
            case FAR_CONTEXT:
                if (element.getLevel() > 1) {//Method
                    return element.getFarContext().stream().mapToDouble(x -> formulaScore(x, formula)).average().orElse(.0);
                }
                else {
                    return .0;
                }
            case OTHER:
                return element.getOtherContext().stream().mapToDouble(x -> formulaScore(x, formula)).average().orElse(.0);
        }
    }

    /**
     * This function select the suspiciousness score of the element
     *
     * @param element the inspected element
     * @param formula defines the currently selected formula
     * @return the suspiciousness score related to the selected formula
     */
    private double formulaScore(ITestData element, Formula formula) {
        switch (formula) {
            default:
            case TARANTULA:
                return element.getTarantula();
            case OCHIAI:
                return element.getOchiai();
        }
    }

    /**
     * To initialize the design of the panel
     */
    private void initComponents() {
        this.setLayout(new BorderLayout());
        this.setBorder(JBUI.Borders.empty(5));
        this.add(label, BorderLayout.NORTH);
        this.add(scale, BorderLayout.CENTER);
    }

    /**
     * @return a JSlider component that has a specific range
     */
    private JSlider createSlider() {
        JSlider scoreSlider = new JSlider(JSlider.VERTICAL, -100, 100, 0);
        scoreSlider.setBorder(new EmptyBorder(0, 0, 0, 0));
        scoreSlider.setPaintTicks(false);
        scoreSlider.setPaintLabels(true);
        scoreSlider.setLabelTable(createCustomLabeling());
        scoreSlider.setMinorTickSpacing(25);
        scoreSlider.setMajorTickSpacing(25);
        scoreSlider.addChangeListener(change -> {
            /*
             * Here comes the updated multiplier
             * TODO: finish implementation!
             */
            if (!scoreSlider.getValueIsAdjusting()) {

                score *= calculateScoreMultiplier();
                try {
                    switch (context) {
                        default:
                            break;
                        case OTHER:
                            interactivity.recalculateOtherElementScores(element, calculateScoreMultiplier(), formula);
                            break;
                        case COMPONENT:
                            interactivity.recalculateEntityScore(element, calculateScoreMultiplier(), formula);
                            break;
                        case CLOSE_CONTEXT:
                            interactivity.recalculateCloseContextScores(element, calculateScoreMultiplier(), formula);
                            break;
                        case FAR_CONTEXT:
                            interactivity.recalculateFarContextScores(element, calculateScoreMultiplier(), formula);
                            break;
                    }
                } catch (NullPointerException nullptr) {
                    System.err.println(nullptr.getMessage());
                }
                initResponseArea();
                setScoreLabel();
                ViewResultHolder.reOpen();
            }
        });
        return scoreSlider;
    }

    /**
     * @param label a simple string
     * @return a centered JLabel component
     */
    private JLabel createLabel(String label) {
        return new JLabel(label, SwingConstants.CENTER);
    }

    /**
     * @param name string, the name text of the JButton
     * @param action an ActionListener that defines which action shall the JButton perform on click
     * @return a composed JButton component with a specific name and behavior
     */
    private JButton createButton(String name, ActionListener action) {
        JButton button = new JButton(name);
        button.addActionListener(action);
        //button.setPreferredSize(new Dimension(50,50));
        return button;
    }

    /**
     * @return a JPanel component which is the main area of the panel This contains the maximize/minimize buttons and the suspiciousness indicator area
     */
    private JPanel createScalePanel() {
        JPanel scalePanel = new JPanel(new BorderLayout());
        JPanel center = new JPanel(new GridLayout(1, 2));
        center.add(createResponseArea(12));
        center.add(slider);
        scalePanel.add(createButton("Faulty", (ac -> slider.setValue(100))), BorderLayout.NORTH);
        scalePanel.add(center, BorderLayout.CENTER);
        scalePanel.add(createButton("Not Faulty", (ac -> slider.setValue(-100))), BorderLayout.SOUTH);

        return scalePanel;
    }

    /**
     * @param limit is the number of the elements in the indicator are
     * @return a JPanel component which contains the indicators. By default these elements are white
     */
    private JPanel createResponseArea(int limit) {
        JPanel responseArea = new JPanel();
        responseArea.setLayout(new BoxLayout(responseArea, BoxLayout.PAGE_AXIS));
        for (int i = 0; i < limit; i++) {
            JPanel field = new JPanel(new BorderLayout());
            field.setBackground(getColorByPosition(i));
            field.setBorder(new LineBorder(Color.WHITE, 1, true));
            responseArea.add(field, 0);
            indicators.add(0, field);
        }
        return responseArea;
    }

    /**
     * @param value the position of an indicator
     * @return the color related to the given position
     */
    private Color getColorByPosition(int value) {
        if (value >= 8) {
            return Color.GREEN;
        } else if (value >= 4) {
            return Color.YELLOW;
        }

        return Color.RED;
    }

    /**
     * This function initializes the indicator area, also modifies the suspiciousness of the related code element
     */
    private void initResponseArea() {
        int limit = calculateLimit();
        for (int i = 0; i < indicators.size(); ++i) {
            if (i < limit) {
                indicators.get(i).setBackground(Color.WHITE);
            } else {
                indicators.get(i).setBackground(getColorByPosition(i));
            }
        }
    }

    /**
     * This function is to calculate the multiplier of the score
     *
     * @return the updated suspiciousness score of the element considering the actual value range
     */
    private double calculateScoreMultiplier() {
        int sliderVal = slider.getValue();
        double temp = Math.abs(sliderVal) / 100.;
        if (sliderVal <= 0) {
            return (1. - temp);
        }
        return 1 + temp;
    }

    /**
     * @return the number that represents the rounded value of the indicators that need to be white
     */
    private int calculateLimit() {
        return 12 - (Math.min((int) Math.round(score * 12), 12));
    }

    private Hashtable<Integer, JLabel> createCustomLabeling() {
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(-100, new JLabel("-100%", SwingConstants.LEFT));
        labels.put(-67, new JLabel("-67%", SwingConstants.LEFT));
        labels.put(-33, new JLabel("-33%", SwingConstants.LEFT));
        labels.put(0, new JLabel(" 0%", SwingConstants.LEFT));
        labels.put(33, new JLabel("+33%", SwingConstants.LEFT));
        labels.put(67, new JLabel("+67%", SwingConstants.LEFT));
        labels.put(100, new JLabel("+100%", SwingConstants.LEFT));
        return labels;
    }
}
