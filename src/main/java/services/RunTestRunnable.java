package services;

import java.io.File;
import java.util.ArrayList;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;

import models.bean.ProcessResult;
import modules.PluginModule;
import modules.ProjectModule;
import org.jetbrains.annotations.NotNull;

public class RunTestRunnable extends Task.Backgroundable {
    private Editor editor;
    private Project project;

    public RunTestRunnable(Project project, String title, Editor editor) {
        super(project, title, false);
        this.project = project;
        this.editor = editor;
    }

    @Override
    public void run(@NotNull ProgressIndicator progressIndicator) {
        progressIndicator.setIndeterminate(false);
        progressIndicator.setFraction(0.0);
        progressIndicator.setText("Initalizing...");

        FlService flService = new FlServiceImpl();
        flService.setTestDataCollecting(true);

        PluginModule.refreshPluginId();
        PluginModule.refreshPluginPath();
        PluginModule.refreshPluginPythonBinPath();

        progressIndicator.setFraction(0.10);
        progressIndicator.setText("Checking opened project...");

        ProjectModule.setProject(project);
        if(ProjectModule.getProject() == null) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog("You must open a project to use this plugin!", "Project not found", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }

        progressIndicator.setFraction(0.20);
        progressIndicator.setText("Getting project path...");

        ProjectModule.setProjectPath(ProjectModule.getProject().getBasePath());
        if(ProjectModule.getProjectPath().equals("")) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(ProjectModule.getProject(), "The project path not found!", "Project path not found", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }

        progressIndicator.setFraction(0.30);
        progressIndicator.setText("Getting Plugin ID...");

        if(PluginModule.getPluginId() == null) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(ProjectModule.getProject(), "The plugin not found via ID!", "Plugin error", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }

        progressIndicator.setFraction(0.40);
        progressIndicator.setText("Getting selected SDK...");

        ProjectModule.setProjectSdk(ProjectRootManager.getInstance(ProjectModule.getProject()).getProjectSdk());
        if(ProjectModule.getProjectSdk() == null) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(ProjectModule.getProject(), "Please select an SDK to your project!", "Project SDK not found", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }

        progressIndicator.setFraction(0.50);
        progressIndicator.setText("Getting selected SDK binary...");

        File sdkBin = new File(ProjectRootManager.getInstance(ProjectModule.getProject()).getProjectSdk().getHomePath());
        if(!sdkBin.exists() || sdkBin.isDirectory()) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(ProjectModule.getProject(), "The executable of project SDK is not found! Please select a correct SDK to your project!", "Project SDK not found", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }

        String pythonBinPath = ProjectRootManager.getInstance(ProjectModule.getProject()).getProjectSdk().getHomePath();
        PluginModule.setPythonBinPath(pythonBinPath);

        progressIndicator.setFraction(0.60);
        progressIndicator.setText("Checking requirments.txt to install packages...");

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
                        flService.setTestDataCollecting(false);
                        return;
                }
                break;
            case 1:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(ProjectModule.getProject(), "The project SDK pip not found! Did you install the pip package?", "Package not found", Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return;
            default:
                return;
        }

        progressIndicator.setFraction(0.70);
        progressIndicator.setText("Running the test cases and calculating...");

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
                    Messages.showMessageDialog(ProjectModule.getProject(), "The result dictionary is empty! Maybe all tests passed?", "Dictionary empty", Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return;
            case 4:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(ProjectModule.getProject(), "No coverage matrix collected!", "Plugin error", Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return;
            case 5:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(ProjectModule.getProject(), "No statistics matrix made!", "Plugin error", Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return;
            case 6:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(ProjectModule.getProject(), "Something wrong with the results!", "Plugin error", Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return;
            case 7:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(ProjectModule.getProject(), "report.xslx not created!", "Plugin error", Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return;
        }

        progressIndicator.setFraction(0.80);
        progressIndicator.setText("Parsing the results into table...");

        ArrayList<String> lines = flService.readTextFile(ProjectModule.getProjectPath() + File.separator + PluginModule.getResultsJSONFileName());
        if(lines.size() == 0) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(ProjectModule.getProject(), "An error occurred during the test run! Maybe pytest didn't find any test?\nYou have to put your tests into the \"tests\" subfolder in the project root folder!", "Plugin error", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }


        flService.clearTestData();
        // Only for debug!
        //System.out.println(ProjectModule.getProjectPath() + File.separator + PluginModule.getRequirementsFilePath());
        flService.setTestData(flService.parseTestDataJSON(lines));
        if(flService.getTestData() == null) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(ProjectModule.getProject(), "An error occurred during parse the test output!", "Plugin error", Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }
        flService.setTestDataCollected(true);

        ApplicationManager.getApplication().invokeLater(new EditorColorRunnable(editor));

        progressIndicator.setFraction(1.00);
        progressIndicator.setText("Finished!");
    }
}
