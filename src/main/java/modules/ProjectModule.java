package modules;

import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.project.Project;

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

    public static Sdk getProjectSdk() {
        return projectSdk;
    }

    public static void setProjectSdk(Sdk projectSdk) {
        ProjectModule.projectSdk = projectSdk;
    }
}
