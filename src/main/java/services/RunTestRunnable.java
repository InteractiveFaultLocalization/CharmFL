package services;

import java.util.ArrayList;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;

import models.bean.ProcessResult;
import modules.PluginModule;
import modules.ProjectModule;

public class RunTestRunnable implements Runnable{
    private AnActionEvent e;

    public RunTestRunnable(AnActionEvent e) {
        this.e = e;
    }
    @Override
    public void run() {
        FlService flService = new FlServiceImpl();
        flService.setTestDataCollecting(true);

        PluginModule.refreshPluginId();
        PluginModule.refreshPluginPath();
        PluginModule.refreshPluginPythonBinPath();

        ProjectModule.setProject(e.getProject());
        if(ProjectModule.getProject() == null) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog("You must open a project to use this plugin!", "Project not found", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }

        ProjectModule.setProjectPath(ProjectModule.getProject().getBasePath());
        if(ProjectModule.getProjectPath().equals("")) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(ProjectModule.getProject(), "The project path not found!", "Project path not found", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }

        if(PluginModule.getPluginId() == null) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(ProjectModule.getProject(), "The plugin not found via ID!", "Plugin error", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }

        ProjectModule.setProjectSdk(ProjectRootManager.getInstance(ProjectModule.getProject()).getProjectSdk());
        if(ProjectModule.getProjectSdk() == null) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(ProjectModule.getProject(), "Please select an SDK to your project!", "Project SDK not found", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }

        String pythonBinPath = ProjectRootManager.getInstance(ProjectModule.getProject()).getProjectSdk().getHomePath();
        PluginModule.setPythonBinPath(pythonBinPath);

        ProcessResult processResult = flService.executeGetPipBinPath(PluginModule.getPythonBinPath(), PluginModule.getCheckPipBinPath());
        switch(processResult.getExitCode()) {
            case 0:
                PluginModule.setPipBinPath(processResult.getOutput().get(0));
                processResult = flService.executeRequirementsInstall(PluginModule.getPythonBinPath(), PluginModule.getPipBinPath(), PluginModule.getRequirementsFilePath(), ProjectModule.getProjectPath());
                switch(processResult.getExitCode()) {
                    case 0:
                        break;
                    default:
                        ApplicationManager.getApplication().invokeLater(() -> {
                            Messages.showMessageDialog(ProjectModule.getProject(), "Unknown error occurred while tried to install requirements!", "Plugin error", Messages.getErrorIcon());
                        });
                        return;
                }
                break;
            case 1:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(ProjectModule.getProject(), "The project SDK pip not found! Did you install the pip package?", "Plugin error", Messages.getErrorIcon());
                });
                return;
            default:
                return;
        }

        /*if(!PluginModule.parsePipBinPath(pythonBinPath)) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(ProjectModule.getProject(), "The project SDK pip not found!", "Plugin error", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }*/

        processResult = flService.executeTest(PluginModule.getPythonBinPath(), PluginModule.getPyflBinPath(), ProjectModule.getProjectPath());
        switch(processResult.getExitCode()){
            case 2:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(ProjectModule.getProject(), "The plugin cannot find \"tests\" or \"test\" folder in the root of project!", "Plugin error", Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return;
            case 3:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(ProjectModule.getProject(), "Pytest cannot collect any test items! Or every test passed:", "Plugin error", Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return;
        }

        ArrayList<String> lines = processResult.getOutput();
        if((processResult.getExitCode() == 0 && lines == null) || lines.size() == 0) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(ProjectModule.getProject(), "An error occurred during the test run! Maybe pytest didn't find any test?\nYou have to put your tests into the \"tests\" subfolder in the project root folder!", "Plugin error", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }

        flService.clearTestData();
        flService.setTestData(flService.parseTestOutput(lines));
        if(flService.getTestData() == null || flService.getTestData().size() == 0) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(ProjectModule.getProject(), "An error occurred during parse the test output!", "Plugin error", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }
        flService.setTestDataCollected(true);

        ApplicationManager.getApplication().invokeLater(new EditorColorRunnable(e));
    }
}
