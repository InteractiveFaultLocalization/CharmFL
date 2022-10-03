package actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

import modules.PluginModule;
import services.runnables.RunTestRunnable;

public class PluginTestRun extends DumbAwareAction {
    /**
     * This calls the fault localization process.
     * @param e
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ProgressManager.getInstance().run(new RunTestRunnable(e.getProject(), PluginModule.getPluginName(),
                FileEditorManager.getInstance(e.getProject()).getSelectedTextEditor()));
    }

}