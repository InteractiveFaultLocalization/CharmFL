package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * This class represents an indicator panel in the floating window
 * TODO: Finalize the required input and output to work with real data, not with the dummy ones
 */
public class ScorePanel extends JPanel {
    private JLabel label;
    private JSlider slider;
    private JPanel scale;
    private List<JPanel> indicators;

    private double score;

    /**
     * @param label the label of the panel
     * @param score the double representation of the statements suspiciousness
     */
    public ScorePanel(String label, double score) {
        this.indicators = new ArrayList<>();
        this.score = score;
        this.label = createLabel(label);
        this.slider = createSlider();
        this.scale = createScalePanel();

        initComponents();
        initResponseArea();
    }


    /**
     * @return the suspiciousness value of the represented code element
     */
    public double getScore() {
        return score;
    }

    /**
     * To initialize the design of the panel
     */
    private void initComponents(){
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.add(slider, BorderLayout.EAST);
        this.add(label, BorderLayout.NORTH);
        this.add(scale, BorderLayout.CENTER);
    }

    /**
     * @return a JSlider component that has a specific range
     */
    private JSlider createSlider(){
        JSlider _slider = new JSlider(JSlider.VERTICAL, 1, 20, 10);
        _slider.setPaintTicks(true);
        _slider.setPaintLabels(true);
        _slider.setMinorTickSpacing(1);
        _slider.setMajorTickSpacing(10);
        _slider.setLabelTable(generateLabels(20));

        _slider.addChangeListener(change -> {
            if(!_slider.getValueIsAdjusting()){
                initResponseArea();
            }
        });
        return _slider;
    }

    /**
     * @param label a simple string
     * @return a centered JLabel component
     */
    private JLabel createLabel(String label){
        return new JLabel(label, SwingConstants.CENTER);
    }

    /**
     * @param name string, the name text of the JButton
     * @param action an ActionListener that defines which action shall the JButton perform on click
     * @return a composed JButton component with a specific name and behavior
     */
    private JButton createButton(String name, ActionListener action){
        JButton button = new JButton(name);
        button.addActionListener(action);
        return button;
    }

    /**
     * @return a JPanel component which is the main area of the panel
     * This contains the maximize/minimize buttons and the suspiciousness indicator area
     */
    private JPanel createScalePanel(){
        JPanel scalePanel = new JPanel(new BorderLayout());

        scalePanel.add(createButton("Faulty",(ac -> slider.setValue(20))),BorderLayout.NORTH);
        scalePanel.add(createResponseArea(10),BorderLayout.CENTER);
        scalePanel.add(createButton("Not Faulty", (ac -> slider.setValue(1))),BorderLayout.SOUTH);

        return scalePanel;
    }

    /**
     * @param limit is the number of the elements in the indicator are
     * @return a JPanel component which contains the indicators. By default these elements are white
     */
    private JPanel createResponseArea(int limit){
        JPanel responseArea = new JPanel();
        responseArea.setLayout(new BoxLayout(responseArea, BoxLayout.PAGE_AXIS));
        for (int i = 0; i < limit; i++) {
            JPanel field = new JPanel();
            field.setBackground(getColorByPosition(i));
            field.setBorder(new LineBorder(Color.WHITE,1,true));
            responseArea.add(field,0);
            indicators.add(0,field);
        }
        return responseArea;
    }

    /**
     * @param value the position of an indicator
     * @return the color related to the given position
     */
    private Color getColorByPosition(int value){
        if(value >= 6)
            return Color.GREEN;
        else if( value >= 2 && value < 6)
            return Color.YELLOW;

        return Color.RED;

    }

    /**
     * This function initializes the indicator area, also modifies the suspiciousness of the related code element
     */
    private void initResponseArea(){
        score = calculateSuspiciousness();
        int limit = calculateLimit();

        for (int i = 0 ; i < indicators.size(); ++i) {
            if(i < limit)
                indicators.get(i).setBackground(Color.WHITE);
            else
                indicators.get(i).setBackground(getColorByPosition(i));

        }
    }

    /**
     * This function is necessary because JSlider cannot be used with floating point numbers.
     * @param numOfSliders the number of JSliders to be displayed
     * @return A hashmap that maps the integer values to a desired set of values (practically JLabels)
     */
    private Hashtable<Integer, JLabel> generateLabels(int numOfSliders){
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = 1; i <= numOfSliders; i++) {
            labelTable.put(i,createLabel(Double.toString(i/10.)));
        }

        return labelTable;
    }

    /**
     * @return the updated suspiciousness score of the element considering the actual value range
     */
    private double calculateSuspiciousness(){
        return score * (slider.getValue() / 10.);
    }

    /**
     * @return the number that represents the rounded value of the indicators that need to be white
     */
    private int calculateLimit(){
        int temp = (int)Math.round(score);
        return 10 - (Math.min(temp, 10));
    }
}
