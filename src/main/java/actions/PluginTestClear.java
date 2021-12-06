package actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

import services.ColorService;
import services.FlService;
import services.FlServiceImpl;

public class PluginTestClear extends DumbAwareAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ColorService colorService = new ColorService();
        colorService.setEditor(e.getData(CommonDataKeys.EDITOR));
        colorService.removeColorsByEditor();
        FlService flService = new FlServiceImpl();
        flService.clearTestData();
        flService.setTestDataCollected(false);
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
