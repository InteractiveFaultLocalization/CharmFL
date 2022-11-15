package actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.Messages;
import modules.PluginModule;
import modules.ProjectModule;
import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.NotNull;
import services.FlServiceImpl;
import services.Resources;
import ui.PopUpView;
import ui.ViewResultHolder;

import javax.swing.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import static services.ProcessService.executeCommand;

public class PluginSunburst extends DumbAwareAction {
    /**
     * When you click the Start Call Graph menu item, then this method will generate a call graph.
     *
     * @param e An event
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e)  {

        FlServiceImpl flService = new FlServiceImpl();
        if (!flService.isTestDataCollected()) {

            Messages.showMessageDialog(
                    e.getProject(),
                    Resources.get("errors", "run_tests_error"),
                    Resources.get("titles", "data_not_collected_title"),
                    Messages.getErrorIcon());
        }else {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException z) {
                Thread.currentThread().interrupt();
            }
            JOptionPane.showMessageDialog(null, "Creating sunburst visualization was successful.\nYou may open the generated html file in any browser." +
                    "\nThe file is located at " + ProjectModule.getProjectPath() + File.separator + "sunburst.html", "Sunburst is ready", JOptionPane.PLAIN_MESSAGE);
            }
        }


}