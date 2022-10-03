package ui;

import com.intellij.openapi.ui.DialogWrapper;
import modules.PluginModule;
import org.jetbrains.annotations.Nullable;
import services.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * This class represents the Options window.
 */
public class AdvancedOptions extends DialogWrapper {
    private JRadioButton tarantulaRadioButton;
    private JRadioButton ochiaiRadioButton;
    private JRadioButton dStarRadioButton;
    private JRadioButton wong2RadioButton;

    private JRadioButton minimumRadioButton;
    private JRadioButton maximumRadioButton;
    private JRadioButton averageRadioButton;

    /**
     * When you make an advanced options object, it sets the title and opens the window.
     */
    public AdvancedOptions() {
        super(true);
        setTitle(Resources.get("titles", "advanced_options"));
        init();
    }

    /**
     * Creates the elements in the window, and groups them.
     * @return the panel object
     */
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setPreferredSize(new Dimension(150, 200));
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

        JLabel spectraMetricsLabel = new JLabel(Resources.get("titles", "spectra_metrics_label"));
        spectraMetricsLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
        tarantulaRadioButton = new JRadioButton(Resources.get("titles", "tarantula_button"));
        ochiaiRadioButton = new JRadioButton(Resources.get("titles", "ochiai_button"));
        dStarRadioButton = new JRadioButton(Resources.get("titles", "dstar_button"));
        wong2RadioButton = new JRadioButton(Resources.get("titles", "wong_button"));

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

        JLabel ranksLabel = new JLabel(Resources.get("titles", "ranks_label"));
        ranksLabel.setBorder(new EmptyBorder(10, 0, 5, 0));
        minimumRadioButton = new JRadioButton(Resources.get("titles", "minimum_button"));
        maximumRadioButton = new JRadioButton(Resources.get("titles", "maximum_button"));
        averageRadioButton = new JRadioButton(Resources.get("titles", "average_button"));

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

    /**
     * Tells you whether the button was selected
     * @return true if it is
     */
    public boolean isTarantulaRadioButtonSelected() {
        return tarantulaRadioButton.isSelected();
    }

    /**
     * Tells you whether the button was selected
     * @return true if it is
     */
    public boolean isOchiaiRadioButtonSelected() {
        return ochiaiRadioButton.isSelected();
    }

    /**
     * Tells you whether the button was selected
     * @return true if it is
     */
    public boolean isDStarRadioButtonSelected() {
        return dStarRadioButton.isSelected();
    }

    /**
     * Tells you whether the button was selected
     * @return true if it is
     */
    public boolean getWong2RadioButtonSelected() {
        return wong2RadioButton.isSelected();
    }

    /**
     * Tells you whether the button was selected
     * @return true if it is
     */
    public boolean isMinimumRadioButtonSelected() {
        return minimumRadioButton.isSelected();
    }

    /**
     * Tells you whether the button was selected
     * @return true if it is
     */
    public boolean isMaximumRadioButtonSelected() {
        return maximumRadioButton.isSelected();
    }

    /**
     * Tells you whether the button was selected
     * @return true if it is
     */
    public boolean isAverageRadioButtonSelected() {
        return averageRadioButton.isSelected();
    }
}
