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

public class ScorePanel extends JPanel {
    private JLabel label;
    private JSlider slider;
    private JPanel scale;
    private List<JPanel> indicators;

    private double score;

    public ScorePanel(String label, double score) {
        this.indicators = new ArrayList<>();
        this.score = score;
        this.label = createLabel(label);
        this.slider = createSlider();
        this.scale = createScalePanel();

        initComponents();
        initResponseArea();
    }

    private void initComponents(){
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.add(slider, BorderLayout.EAST);
        this.add(label, BorderLayout.NORTH);
        this.add(scale, BorderLayout.CENTER);
    }

    private JSlider createSlider(){
        JSlider _slider = new JSlider(JSlider.VERTICAL, 1, 20, 10);
        _slider.setPaintTicks(true);
        _slider.setPaintLabels(true);
        _slider.setMinorTickSpacing(1);
        _slider.setMajorTickSpacing(10);
        _slider.setLabelTable(generateLabels(20));

        _slider.addChangeListener(change -> {
            if(!_slider.getValueIsAdjusting()){
                System.err.println("hit");
                System.err.println(score);
                initResponseArea();
                System.err.println(score);
            }
        });
        return _slider;
    }

    private JLabel createLabel(String label){
        return new JLabel(label, SwingConstants.CENTER);
    }

    private JButton createButton(String name, ActionListener action){
        JButton button = new JButton(name);
        button.addActionListener(action);
        return button;
    }

    private JPanel createScalePanel(){
        JPanel scalePanel = new JPanel(new BorderLayout());

        scalePanel.add(createButton("Faulty",(ac -> slider.setValue(20))),BorderLayout.NORTH);
        scalePanel.add(createResponseArea(10),BorderLayout.CENTER);
        scalePanel.add(createButton("Not Faulty", (ac -> slider.setValue(1))),BorderLayout.SOUTH);

        return scalePanel;
    }

    private JPanel createResponseArea(int limit){
        JPanel responseArea = new JPanel();
        responseArea.setLayout(new BoxLayout(responseArea, BoxLayout.PAGE_AXIS));
        for (int i = 0; i < limit; i++) {
            JPanel field = new JPanel();
            field.setBackground(getColorByRange(i));
            field.setBorder(new LineBorder(Color.WHITE,1,true));
            responseArea.add(field,0);
            indicators.add(0,field);
        }
        return responseArea;
    }

    private Color getColorByRange(int value){
        if(value >= 6)
            return Color.GREEN;
        else if( value >= 2 && value < 6)
            return Color.YELLOW;

        return Color.RED;

    }

    private void initResponseArea(){
        score = calculateSuspiciousness();

        int limit = calculateLimit();
        System.out.println("limit: " + limit + " score: " + score);
        for (int i = 0 ; i < indicators.size(); ++i) {
            if(i < limit)
                indicators.get(i).setBackground(Color.WHITE);
            else
                indicators.get(i).setBackground(getColorByRange(i));

        }
    }

    private Hashtable<Integer, JLabel> generateLabels(int uLimit){
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = 1; i <= uLimit; i++) {
            labelTable.put(i,createLabel(Double.toString(i/10.)));
        }

        return labelTable;
    }

    private double calculateSuspiciousness(){
        return score * (slider.getValue() / 10.);
    }

    private int calculateLimit(){
        int temp = (int)Math.round(score);
        return 10 - ((temp > 10) ? 10 : temp);
    }
}
