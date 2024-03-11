package services.runnables;

import java.io.File;
import java.util.ArrayList;

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

public class RunTestRunnable extends Task.Backgroundable implements Progress {
    static final int OK_CODE = 0;
    static final int NOT_FOUND_TEST_ERROR_CODE = 2;
    static final int NOT_FOUND_RESULTS_DICTIONARY_ERROR_CODE = 3;
    static final int NOT_FOUND_COVERAGE_MATRIX_ERROR_CODE = 4;
    static final int NOT_FOUND_STATISTICS_MATRIX_ERROR_CODE = 5;
    static final int EXCEL_REPORT_FILE_NOT_FOUND = 7;
    static final int FAILED_COPY_COVERAGE_RC_FILE = 6;
    private Editor editor;
    private Project project;

    public RunTestRunnable(Project project, String title, Editor editor) {
        super(project, title, false);
        this.project = project;
        this.editor = editor;
    }

    /**
     * This runs the tests and takes care of the fault localization process
     *
     * @param progressIndicator
     */
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


        progressIndicator.setText(Resources.get("states", "selected_sdk"));

        ProjectModule.setProjectSdk(ProjectRootManager.getInstance(ProjectModule.getProject()).getProjectSdk());
        if (ProjectModule.getProjectSdk() == null) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(
                        ProjectModule.getProject(),
                        Resources.get("errors", "no_sdk_selected_error"),
                        Resources.get("titles", "project_sdk_not_found_title"),
                        Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }


        progressIndicator.setText(Resources.get("states", "selected_binary"));

        File sdkBin = new File(ProjectRootManager.getInstance(ProjectModule.getProject()).getProjectSdk().getHomePath());
        if (!sdkBin.exists() || sdkBin.isDirectory()) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(
                        ProjectModule.getProject(),
                        Resources.get("errors", "sdk_not_found"),
                        Resources.get("titles", "project_sdk_not_found_title"),
                        Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }

        String pythonBinPath = ProjectRootManager.getInstance(ProjectModule.getProject()).getProjectSdk().getHomePath();
        PluginModule.setPythonBinPath(pythonBinPath);

        ProcessResultData processResultData = checkRequirements(flService, progressIndicator);

        if (!areRequirementsInstalled(flService, processResultData, progressIndicator)) {
            return;
        }

        processResultData = executeTests(flService, progressIndicator);

        if (!isExecutionOk(flService, processResultData)) {
            return;
        }

        parseResults(flService, progressIndicator);

        if (flService.getTestData() == null) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(
                        ProjectModule.getProject(),
                        Resources.get("errors", "parsing_error"),
                        Resources.get("titles", "plugin_error_title"),
                        Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }

        //executeVisualizing(flService, progressIndicator);

        flService.setTestDataCollected(true);

        ApplicationManager.getApplication().invokeLater(new EditorColorRunnable(editor));

        finish(progressIndicator);
    }

    /**
     * Initializes the fault localization progress
     * Sets the test data collecting parameter to true, therefore the process is running
     *
     * @param progressIndicator Background task manager
     * @return the fl service object
     */
    @Override
    public FlServiceImpl initialization(ProgressIndicator progressIndicator) {
        progressIndicator.setIndeterminate(false);

        progressIndicator.setText(Resources.get("states", "init"));

        FlServiceImpl flService = new FlServiceImpl();
        flService.setTestDataCollecting(true);
        return flService;
    }

    /**
     * Checks if the project is opened and sets the necessarry parameters
     *
     * @param progressIndicator Background task manager
     * @return true if the project is opened
     */
    @Override
    public boolean isProjectOpened(ProgressIndicator progressIndicator) {
        PluginModule.refreshPluginId();
        PluginModule.refreshPluginPath();
        PluginModule.refreshPluginPythonBinPath();

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

    /**
     * Sets the project's absolute path
     * If it finds nothing it will open an alert window
     *
     * @param progressIndicator Background task manager
     * @return true if the project path was set successfully.
     */
    @Override
    public boolean isProjectPathAvailable(ProgressIndicator progressIndicator) {
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

    /**
     * Gets the plugin id
     *
     * @param progressIndicator
     * @return true if getting the plugin's id was successful
     */
    @Override
    public boolean isPluginIdAvailable(ProgressIndicator progressIndicator) {
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

    /**
     * Tries to install the packages from requirements.txt
     *
     * @param flService
     * @param progressIndicator
     * @return the result of the terminal run
     */
    @Override
    public ProcessResultData checkRequirements(FlServiceImpl flService, ProgressIndicator progressIndicator) {
        String pythonBinPath = ProjectRootManager.getInstance(ProjectModule.getProject()).getProjectSdk().getHomePath();
        PluginModule.setPythonBinPath(pythonBinPath);

        progressIndicator.setText(Resources.get("states", "check_requirements"));
        return flService.executeGetPipBinPath(
                PluginModule.getPythonBinPath(),
                PluginModule.getCheckPipBinPath());

    }

    /**
     * Checks whether requirements were successfully installed
     *
     * @param flService
     * @param processResultData
     * @param progressIndicator
     * @return true if the all the packages were installed
     */
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
     * This method is not supported. Please use executeTests.
     *
     * @param flService
     * @param progressIndicator
     */
    @Override
    public void execute(FlServiceImpl flService, ProgressIndicator progressIndicator) {
        throw new UnsupportedOperationException("No implementation.");
    }

    /**
     * Runs the fault localization process (backend)
     *
     * @param flService
     * @param progressIndicator
     * @return the results of the terminal output
     */
    public ProcessResultData executeTests(FlServiceImpl flService, ProgressIndicator progressIndicator) {
        progressIndicator.setText(Resources.get("states", "running_tests"));

        ProcessResultData processResultData = flService.executeTest(
                PluginModule.getPythonBinPath(),
                PluginModule.getPyflBinPath(),
                ProjectModule.getProjectPath());

        return processResultData;
    }



    /**
     * Checks whether the fault localization process were successful
     *
     * @param flService
     * @param processResultData
     * @return true if it was okay
     */
    public boolean isExecutionOk(FlServiceImpl flService, ProcessResultData processResultData) {
        switch (processResultData.getExitCode()) {
            case NOT_FOUND_TEST_ERROR_CODE:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(
                            ProjectModule.getProject(),
                            Resources.get("errors", "no_tests_found_error"),
                            Resources.get("titles", "plugin_error_title"),
                            Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return false;
            case NOT_FOUND_RESULTS_DICTIONARY_ERROR_CODE:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(
                            ProjectModule.getProject(),
                            Resources.get("errors", "empty_results_directory_error"),
                            Resources.get("titles", "dictionary_empty_title"),
                            Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return false;
            case NOT_FOUND_COVERAGE_MATRIX_ERROR_CODE:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(
                            ProjectModule.getProject(),
                            Resources.get("errors", "no_coverage_matrix_error"),
                            Resources.get("titles", "plugin_error_title"),
                            Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return false;
            case NOT_FOUND_STATISTICS_MATRIX_ERROR_CODE:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(
                            ProjectModule.getProject(),
                            Resources.get("errors", "no_statistics_matrix_error"),
                            Resources.get("titles", "plugin_error_title"),
                            Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return false;
            case FAILED_COPY_COVERAGE_RC_FILE:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(
                            ProjectModule.getProject(),
                            Resources.get("errors", "bad_results_error"),
                            Resources.get("titles", "plugin_error_title"),
                            Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return false;
            case EXCEL_REPORT_FILE_NOT_FOUND:
                ApplicationManager.getApplication().invokeLater(() -> {
                    Messages.showMessageDialog(
                            ProjectModule.getProject(),
                            Resources.get("errors", "report_not_created_error"),
                            Resources.get("titles", "plugin_error_title"),
                            Messages.getErrorIcon());
                });
                flService.setTestDataCollecting(false);
                return false;
            default:
                return true;
        }
    }

    /**
     * Parses the results json to viewable table
     * And it does not collect any tests so the TestDataCollecting phase is done i.e. false.
     *
     * @param flService
     * @param progressIndicator
     */
    public void parseResults(FlServiceImpl flService, ProgressIndicator progressIndicator) {
        progressIndicator.setFraction(0.80);
        progressIndicator.setText(Resources.get("states", "parse_results"));

        ArrayList<String> lines = flService.readTextFile(
                ProjectModule.getProjectPath() + File.separator + PluginModule.getResultsJsonFileName());
        if (lines.size() == 0) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Messages.showMessageDialog(
                        ProjectModule.getProject(),
                        Resources.get("errors", "pytest_parsing_error"),
                        Resources.get("titles", "plugin_error_title"),
                        Messages.getErrorIcon());
            });
            flService.setTestDataCollecting(false);
            return;
        }
        flService.clearTestData();
        flService.setTestData(flService.parseTestDataJSON(lines));
    }


    public ProcessResultData executeVisualizing(FlServiceImpl flService, ProgressIndicator progressIndicator) {
        progressIndicator.setText("Visualizing data...");

        ProcessResultData processResultData = flService.executeSunburst(
                PluginModule.getPythonBinPath(),
                PluginModule.getPyflBinPath(),
                ProjectModule.getProjectPath());

        return processResultData;
    }
    /**
     * This method sets the background task to finish
     *
     * @param progressIndicator
     */
    @Override
    public void finish(ProgressIndicator progressIndicator) {
        progressIndicator.setFraction(1.00);
        progressIndicator.setText(Resources.get("states", "finished"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException z) {
            Thread.currentThread().interrupt();
        }
    }
}
