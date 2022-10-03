package modules;

import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.project.Project;

/**
 * This class represents the opened project
 */
public class ProjectModule {
    private static Project project;
    private static String projectPath;
    private static Sdk projectSdk;

    public static Project getProject() {
        return project;
    }

    public static void setProject(Project project) {
        ProjectModule.project = project;
    }

    public static String getProjectPath() {
        return projectPath;
    }

    public static void setProjectPath(String projectPath) {
        ProjectModule.projectPath = projectPath;
    }

    /**
     * Gets the interpreter or software development kit
     * @return Sdk object
     */
    public static Sdk getProjectSdk() {
        return projectSdk;
    }

    /**
     * Sets the interpreter or software devkit to the project.
     * @param projectSdk
     */
    public static void setProjectSdk(Sdk projectSdk) {
        ProjectModule.projectSdk = projectSdk;
    }
}
