package actions;

import com.google.api.Logging;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.DumbAwareAction;


import groovy.util.logging.Log;
import groovy.util.logging.Log4j2;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import org.jetbrains.annotations.NotNull;


import modules.PluginModule;
import services.runnables.RunTestRunnable;

public class PluginTestRun extends DumbAwareAction {
    static Logger logger = Logger.getLogger(PluginTestRun.class.getName());

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