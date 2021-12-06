package actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import services.FlService;
import services.FlServiceImpl;
import ui.ViewResult;

public class PluginTableView extends DumbAwareAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        FlService flService = new FlServiceImpl();
        if(flService.isTestDataCollected()) {
            flService.setViewResultTableDialogOpened(true);
            new ViewResult().show();
        }
        else {
            Messages.showMessageDialog(e.getProject(), "You must run a test before view the results!", "Data not collected", Messages.getErrorIcon());
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        FlService flService = new FlServiceImpl();
        if(flService.isTestDataCollected() && !flService.isViewResultTableDialogOpened()) {
            e.getPresentation().setEnabled(true);
        }
        else if(!flService.isTestDataCollected() || flService.isViewResultTableDialogOpened()){
            e.getPresentation().setEnabled(false);
        }
    }
}