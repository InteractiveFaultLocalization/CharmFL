package actions;

import services.Resources;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import modules.PluginModule;
import services.runnables.RunCallGraphRunnable;
import ui.*;

import java.lang.Thread;

public class PluginCallGraph extends DumbAwareAction {
    /**
     * When you click the Start Call Graph menu item, then this method will generate a call graph.
     * @param e An event
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String mainFileName = JOptionPane.showInputDialog(Resources.get("errors", "enter_main_file_message"));
        ProgressManager.getInstance().run(new RunCallGraphRunnable(e.getProject(), PluginModule.getPluginName(),
                FileEditorManager.getInstance(e.getProject()).getSelectedTextEditor(), mainFileName));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException z) {
            Thread.currentThread().interrupt();
        }
        //new CallGraphView("",e.getProject()).show();
    }
}