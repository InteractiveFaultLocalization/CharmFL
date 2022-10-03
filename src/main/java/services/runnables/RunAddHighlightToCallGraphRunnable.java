package services.runnables;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;

import models.bean.ProcessResultData;
import modules.PluginModule;
import modules.ProjectModule;
import org.jetbrains.annotations.NotNull;
import services.FlServiceImpl;
import services.Resources;

public class RunAddHighlightToCallGraphRunnable extends Task.Backgroundable implements Progress {
    static final int OK_CODE = 0;
    private Editor editor;
    private Project project;
    private Object methodName;

    public RunAddHighlightToCallGraphRunnable(Project project, String title, Editor editor, Object methodName) {
        super(project, title, false);
        this.project = project;
        this.editor = editor;
        this.methodName = methodName;
    }

    @Override
    public void run(@NotNull ProgressIndicator progressIndicator) {
        FlServiceImpl flService = initialization(progressIndicator);

        if (!isProjectOpened(progressIndicator)) {
            return;
        }

        if (!isProjectPathAvailable(progressIndicator)) {
            return;
        }

        if (!isPluginIdAvailable(progressIndicator)) {
            return;
        }

        ProcessResultData processResultData = checkRequirements(flService, progressIndicator);

        if (!areRequirementsInstalled(flService, processResultData, progressIndicator)) {
            return;
        }

        execute(flService, progressIndicator);
        finish(progressIndicator);
    }

    @Override
    public FlServiceImpl initialization(ProgressIndicator progressIndicator) {
        progressIndicator.setIndeterminate(false);
        progressIndicator.setFraction(0.0);
        progressIndicator.setText(Resources.get("states", "init"));

        FlServiceImpl flService = new FlServiceImpl();
        flService.setTestDataCollecting(true);
        return flService;
    }

    @Override
    public boolean isProjectOpened(ProgressIndicator progressIndicator) {
        PluginModule.refreshPluginId();
        PluginModule.refreshPluginPath();
        PluginModule.refreshPluginPythonBinPath();

        progressIndicator.setFraction(0.10);
        progressIndicator.setText(Resources.get("states", "project_opened"));

        ProjectModule.setProject(project);
        if (ProjectModule.getProject() == null) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(
                        Resources.get("errors", "open_project_error"),
                        Resources.get("titles", "project_not_found_title"),
                        Messages.getErrorIcon());
            });
            return false;
        }
        return true;
    }

    @Override
    public boolean isProjectPathAvailable(ProgressIndicator progressIndicator) {
        progressIndicator.setFraction(0.20);
        progressIndicator.setText(Resources.get("states", "project_path"));

        ProjectModule.setProjectPath(ProjectModule.getProject().getBasePath());
        if (ProjectModule.getProjectPath().equals("")) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(
                        ProjectModule.getProject(),
                        Resources.get("errors", "project_path_not_found_error"),
                        Resources.get("titles", "project_path_not_found_title"),
                        Messages.getErrorIcon());
            });
            return false;
        }
        return true;
    }

    @Override
    public boolean isPluginIdAvailable(ProgressIndicator progressIndicator) {
        progressIndicator.setFraction(0.30);
        progressIndicator.setText(Resources.get("states", "plugin_id"));

        if (PluginModule.getPluginId() == null) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(
                        ProjectModule.getProject(),
                        Resources.get("errors", "plugin_not_found_error"),
                        Resources.get("titles", "plugin_error_title"),
                        Messages.getErrorIcon());
            });
            return false;
        }
        return true;
    }

    @Override
    public ProcessResultData checkRequirements(FlServiceImpl flService, ProgressIndicator progressIndicator) {
        String pythonBinPath = ProjectRootManager.getInstance(ProjectModule.getProject()).getProjectSdk().getHomePath();
        PluginModule.setPythonBinPath(pythonBinPath);

        progressIndicator.setFraction(0.60);
        progressIndicator.setText(Resources.get("states", "check_requirements"));

        ProcessResultData processResultData = flService.executeGetPipBinPath(
                PluginModule.getPythonBinPath(),
                PluginModule.getCheckPipBinPath());
        return processResultData;
    }

    @Override
    public boolean areRequirementsInstalled(FlServiceImpl flService, ProcessResultData processResultData, ProgressIndicator progressIndicator) {
        if (processResultData.getExitCode() == OK_CODE) {
            PluginModule.setPipBinPath(processResultData.getOutput().get(0));
            processResultData = flService.executeRequirementsInstall(
                    PluginModule.getPythonBinPath(),
                    PluginModule.getPipBinPath(),
                    PluginModule.getRequirementsFilePath(),
                    ProjectModule.getProjectPath());
            if (processResultData.getExitCode() == OK_CODE) {
                return true;
            } else {
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(
                            ProjectModule.getProject(),
                            Resources.get("errors", "requirements_installation_error"),
                            Resources.get("titles", "plugin_error_title"),
                            Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return false;
            }
        } else {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(
                        ProjectModule.getProject(),
                        Resources.get("errors", "sdk_pip_not_found_error"),
                        Resources.get("titles", "package_not_found_title"),
                        Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return false;
        }
    }

    /**
     * This highlights the edges in the call graph.
     * @param flService
     * @param progressIndicator
     */
    @Override
    public void execute(FlServiceImpl flService, ProgressIndicator progressIndicator) {
        progressIndicator.setFraction(0.70);
        progressIndicator.setText(Resources.get("states", "running_tests"));

        flService.executeAddHighlightToCallGraph(
                PluginModule.callAddHighlightToGraphScriptName(),
                ProjectModule.getProjectPath(),
                methodName,
                PluginModule.getPythonBinPath());
    }

    @Override
    public void finish(ProgressIndicator progressIndicator) {
        progressIndicator.setFraction(1.00);
        progressIndicator.setText(Resources.get("states", "finished"));
    }
}
