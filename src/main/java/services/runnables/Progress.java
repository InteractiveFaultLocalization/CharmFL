package services.runnables;

import com.intellij.openapi.progress.ProgressIndicator;
import models.bean.ProcessResultData;
import services.FlServiceImpl;

public interface Progress {
    public FlServiceImpl initialization(ProgressIndicator progressIndicator);
    public boolean isProjectOpened(ProgressIndicator progressIndicator);
    public boolean isProjectPathAvailable(ProgressIndicator progressIndicator);
    public boolean isPluginIdAvailable(ProgressIndicator progressIndicator);
    public ProcessResultData checkRequirements(FlServiceImpl flService, ProgressIndicator progressIndicator);
    public boolean areRequirementsInstalled(FlServiceImpl flService, ProcessResultData processResultData, ProgressIndicator progressIndicator);
    public void execute(FlServiceImpl flService, ProgressIndicator progressIndicator);
    public void finish(ProgressIndicator progressIndicator);
}
