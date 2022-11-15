package actions;

import com.intellij.openapi.ui.Messages;
import modules.ProjectModule;
import org.apache.commons.lang3.SystemUtils;
import services.FlServiceImpl;
import services.Resources;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

import modules.PluginModule;
import services.runnables.RunCallGraphRunnable;
import ui.*;

import javax.swing.*;
import java.io.*;
import java.lang.Thread;

import static services.ProcessService.executeCommand;

public class PluginCallGraph extends DumbAwareAction {
    /**
     * When you click the Start Call Graph menu item, then this method will generate a call graph.
     *
     * @param e An event
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        //String mainFileName = JOptionPane.showInputDialog(Resources.get("errors", "enter_main_file_message"));
        //ProgressManager.getInstance().run(new RunCallGraphRunnable(e.getProject(), PluginModule.getPluginName(),
        //        FileEditorManager.getInstance(e.getProject()).getSelectedTextEditor(), ""));
        FlServiceImpl flService = new FlServiceImpl();
        if (flService.isTestDataCollected()) {


            File file = new File(ProjectModule.getProjectPath() + File.separator +
                    "static_call_graph.html");
            Writer fileWriter;
            try {
                if (!file.exists()) {

                    file.createNewFile();

                }
                fileWriter = new FileWriter(ProjectModule.getProjectPath() + File.separator +
                        "static_call_graph.html", false);


                String command = "";
                if (SystemUtils.IS_OS_WINDOWS) {

                    command = "\"" + PluginModule.getPythonBinPath() + "\""
                            + " -m pyan " + "\"" + ProjectModule.getProjectPath() + File.separator + "**/*.py " + "\"" + " --uses --no-defines --colored --grouped --annotated --html ";
                } else if (SystemUtils.IS_OS_LINUX) {
                    command = PluginModule.getPythonBinPath().replaceAll(" ", "\\ ")
                            + " -m pyan " + ProjectModule.getProjectPath() + File.separator + "**/*.py --uses --no-defines --colored --grouped --annotated --html";
                }

                var proc = executeCommand(command);
                var outputHtml = proc.getOutput();
                outputHtml.forEach(m -> {
                    try {
                        fileWriter.write(m + System.lineSeparator());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                fileWriter.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (NullPointerException exception) {
                FileWriter fstream = null;
                try {
                    fstream = new FileWriter("exception.txt");

                    BufferedWriter out = new BufferedWriter(fstream);
                    out.write(PluginModule.getPythonBinPath().replaceAll(" ", "\\ ")
                            + " -m pyan " + ProjectModule.getProjectPath() + File.separator + "*.py --uses --no-defines --colored --grouped --annotated --html");
                    out.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException z) {
                Thread.currentThread().interrupt();
            }
            JOptionPane.showMessageDialog(null, "Creating call graph was successful.\nYou may open the generated html file in any browser." +
                    "\nThe file is located at " + ProjectModule.getProjectPath() + File.separator + "static_call_graph.html", "Call Graph is ready", JOptionPane.PLAIN_MESSAGE);
            //new PopUpView("static_call_graph.html").show();
        } else {
            Messages.showMessageDialog(
                    e.getProject(),
                    Resources.get("errors", "run_tests_error"),
                    Resources.get("titles", "data_not_collected_title"),
                    Messages.getErrorIcon());
        }
    }
}