package actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

import services.FlService;
import services.FlServiceImpl;
import services.RunTestRunnable;

public class PluginTestRun extends DumbAwareAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Thread thread = new Thread(new RunTestRunnable(e));
        thread.start();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        FlService flService = new FlServiceImpl();
        if(flService.isViewResultTableDialogOpened() || flService.isTestDataCollecting()) {
            e.getPresentation().setEnabled(false);
        }
        else if(!flService.isViewResultTableDialogOpened() && !flService.isTestDataCollecting()){
            e.getPresentation().setEnabled(true);
        }
    }
}