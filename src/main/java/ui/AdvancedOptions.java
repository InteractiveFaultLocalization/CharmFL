package ui;

import com.intellij.openapi.ui.DialogWrapper;
import modules.PluginModule;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdvancedOptions extends DialogWrapper {
    private JRadioButton tarantulaRadioButton;
    private JRadioButton ochiaiRadioButton;
    private JRadioButton dStarRadioButton;
    private JRadioButton wong2RadioButton;

    private JRadioButton minimumRadioButton;
    private JRadioButton maximumRadioButton;
    private JRadioButton averageRadioButton;

    public AdvancedOptions() {
        super(true);
        setTitle("Advanced Options");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setPreferredSize(new Dimension(150, 200));
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

        JLabel spectraMetricsLabel = new JLabel("Spectra metrics:");
        spectraMetricsLabel.setBorder(new EmptyBorder(0,0,5,0));
        tarantulaRadioButton = new JRadioButton("Tarantula");
        ochiaiRadioButton = new JRadioButton("Ochiai");
        dStarRadioButton = new JRadioButton("DStar");
        wong2RadioButton = new JRadioButton("Wong II");

        ButtonGroup spectraMetricsButtonGroup = new ButtonGroup();
        spectraMetricsButtonGroup.add(tarantulaRadioButton);
        spectraMetricsButtonGroup.add(ochiaiRadioButton);
        spectraMetricsButtonGroup.add(dStarRadioButton);
        spectraMetricsButtonGroup.add(wong2RadioButton);

        dialogPanel.add(spectraMetricsLabel);
        dialogPanel.add(tarantulaRadioButton);
        dialogPanel.add(ochiaiRadioButton);
        dialogPanel.add(dStarRadioButton);
        dialogPanel.add(wong2RadioButton);

        JLabel ranksLabel = new JLabel("Tie Ranks:");
        ranksLabel.setBorder(new EmptyBorder(10,0,5,0));
        minimumRadioButton = new JRadioButton("Minimum");
        maximumRadioButton = new JRadioButton("Maximum");
        averageRadioButton = new JRadioButton("Average");

        ButtonGroup ranksButtonGroup = new ButtonGroup();
        ranksButtonGroup.add(minimumRadioButton);
        ranksButtonGroup.add(maximumRadioButton);
        ranksButtonGroup.add(averageRadioButton);

        dialogPanel.add(ranksLabel);
        dialogPanel.add(minimumRadioButton);
        dialogPanel.add(maximumRadioButton);
        dialogPanel.add(averageRadioButton);

        tarantulaRadioButton.setSelected(PluginModule.isTarantulaSelected());
        ochiaiRadioButton.setSelected(PluginModule.isOchiaiSelected());
        dStarRadioButton.setSelected(PluginModule.isDStarSelected());
        wong2RadioButton.setSelected(PluginModule.isWongIISelected());

        maximumRadioButton.setSelected(PluginModule.isMaximumSelected());
        minimumRadioButton.setSelected(PluginModule.isMinimumSelected());
        averageRadioButton.setSelected(PluginModule.isAverageSelected());

        return dialogPanel;
    }

    public boolean isTarantulaRadioButton() {
        return tarantulaRadioButton.isSelected();
    }

    public boolean isOchiaiRadioButton() {
        return ochiaiRadioButton.isSelected();
    }

    public boolean isDStarRadioButton() {
        return dStarRadioButton.isSelected();
    }

    public boolean getWong2RadioButton() {
        return wong2RadioButton.isSelected();
    }

    public boolean isMinimumRadioButton() {
        return minimumRadioButton.isSelected();
    }

    public boolean isMaximumRadioButton() {
        return maximumRadioButton.isSelected();
    }

    public boolean isAverageRadioButton() {
        return averageRadioButton.isSelected();
    }
}
