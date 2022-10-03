package actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

import services.ColorService;
import services.FlServiceImpl;

public class PluginTestClear extends DumbAwareAction {

    /**
     * This method clears the data and the colouring
     * @param e
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ColorService colorService = new ColorService();
        colorService.setEditor(e.getData(CommonDataKeys.EDITOR));
        colorService.removeColorsByEditor();
        FlServiceImpl flService = new FlServiceImpl();
        flService.clearTestData();
        flService.setTestDataCollected(false);
    }
}
