package actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import modules.DocumentModule;
import modules.ProjectModule;
import services.ColorService;
import services.FlService;
import services.FlServiceImpl;

public class PluginColoringTurnOn extends DumbAwareAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        FlService flService = new FlServiceImpl();
        if(!flService.isColoringTurnOn()) {
            flService.setColoringTurnOn(true);
            ColorService colorService = new ColorService();
            colorService.setEditor(e.getData(CommonDataKeys.EDITOR));
            ProjectModule.setProject(e.getProject());
            if(ProjectModule.getProject() == null) {
                Messages.showMessageDialog("You must open a project to use this plugin!", "Project not found", Messages.getErrorIcon());
                return;
            }
            DocumentModule.setDocument(ProjectModule.getProject());
            String relativeFilePath = flService.parseRelativeFilePath(DocumentModule.getCurrentFilePath(), ProjectModule.getProjectPath());
            if(relativeFilePath.equals("")) {
                Messages.showMessageDialog(ProjectModule.getProject(), "An error occurred during parse the file path from the editor!", "Plugin error", Messages.getErrorIcon());
                return;
            }
            colorService.setColorsByEditor(flService.getTestData().get(relativeFilePath));
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        FlService flService = new FlServiceImpl();
        if(flService.isTestDataCollected()) {
            e.getPresentation().setEnabled(true);
        }
        else {
            e.getPresentation().setEnabled(false);
        }
    }
}