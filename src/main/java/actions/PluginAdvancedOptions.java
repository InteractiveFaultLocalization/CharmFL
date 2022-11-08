package actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.Messages;
import modules.PluginModule;
import org.jetbrains.annotations.NotNull;

import services.FlServiceImpl;
import services.Resources;
import ui.AdvancedOptions;
import ui.ViewResult;
import ui.ViewResultHolder;

/**
 *  This class represents the CharmFL menu's Option button.
 */
public class PluginAdvancedOptions extends DumbAwareAction {
    /**
     * This method is called when you click on the menu item. I.e. opening the options window.
     * @param e An event
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        AdvancedOptions dialog = new AdvancedOptions();
        FlServiceImpl flService = new FlServiceImpl();

        if (dialog.showAndGet()) {
            PluginModule.setTarantulaSelected(dialog.isTarantulaRadioButtonSelected());
            PluginModule.setOchiaiSelected(dialog.isOchiaiRadioButtonSelected());
            PluginModule.setDStarSelected(dialog.isDStarRadioButtonSelected());
            PluginModule.setWongIISelected(dialog.getWong2RadioButtonSelected());

            PluginModule.setMaximumSelected(dialog.isMaximumRadioButtonSelected());
            PluginModule.setMinimumSelected(dialog.isMinimumRadioButtonSelected());
            PluginModule.setAverageSelected(dialog.isAverageRadioButtonSelected());

            if (flService.isTestDataCollected()) {
                flService.setViewResultTableDialogOpened(true);
                ViewResultHolder.reOpen();
            } else {
                Messages.showMessageDialog(
                        e.getProject(),
                        Resources.get("errors", "applied_but_run_tests_error"),
                        Resources.get("titles", "data_not_collected_title"),
                        Messages.getErrorIcon());
            }
        }
    }
}