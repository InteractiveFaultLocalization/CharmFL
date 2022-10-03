package actions;

import services.Resources;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import services.FlServiceImpl;
import ui.ViewResult;

public class PluginTableView extends DumbAwareAction {
    /**
     * When you click on the menu item, then this will show the table data.
     * If the Fault localization has been run, then it will just open the data with the results
     * Otherwise it presents an error message.
     * @param e
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        FlServiceImpl flService = new FlServiceImpl();
        if (flService.isTestDataCollected()) {
            flService.setViewResultTableDialogOpened(true);
            new ViewResult().show();
        } else {
            Messages.showMessageDialog(
                    e.getProject(),
                    Resources.get("errors", "run_tests_error"),
                    Resources.get("titles", "data_not_collected_title"),
                    Messages.getErrorIcon());
        }
    }
}