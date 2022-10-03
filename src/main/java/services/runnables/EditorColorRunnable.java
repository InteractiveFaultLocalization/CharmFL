package services.runnables;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;

import modules.DocumentModule;
import modules.ProjectModule;
import services.ColorService;
import services.FlServiceImpl;
import services.Resources;
import ui.ViewResult;

public class EditorColorRunnable implements Runnable {
    private Editor editor;

    public EditorColorRunnable(Editor editor) {
        this.editor = editor;
    }

    /**
     * Sets the colouring and opens the file.
     */
    @Override
    public void run() {
        FlServiceImpl flService = new FlServiceImpl();
        DocumentModule.setDocument(ProjectModule.getProject());
        if (DocumentModule.getDocument() != null) {
            String relativeFilePath = flService.parseRelativeFilePath(DocumentModule.getCurrentFilePath(),
                    ProjectModule.getProjectPath());
            if (relativeFilePath.equals("")) {
                Messages.showMessageDialog(
                        ProjectModule.getProject(),
                        Resources.get("errors", "file_path_not_found_error"),
                        Resources.get("titles", "plugin_error_title"),
                        Messages.getErrorIcon());
                flService.setTestDataCollecting(false);
                return;
            }

            ColorService colorService = new ColorService();

            colorService.setEditor(editor);
            colorService.removeColorsByEditor();
            colorService.setColorsByEditor(flService.getTestData(), relativeFilePath);
        }

        flService.startFileEditorManagerListener(ProjectModule.getProject());
        flService.setViewResultTableDialogOpened(true);
        flService.setTestDataCollecting(false);
        new ViewResult().show();
    }
}
