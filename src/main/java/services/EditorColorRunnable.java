package services;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;

import modules.DocumentModule;
import modules.ProjectModule;
import ui.ViewResult;

public class EditorColorRunnable implements Runnable {
    private Editor editor;

    public EditorColorRunnable(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void run() {
        FlService flService = new FlServiceImpl();
        DocumentModule.setDocument(ProjectModule.getProject());
        if(DocumentModule.getDocument() != null) {
            String relativeFilePath = flService.parseRelativeFilePath(DocumentModule.getCurrentFilePath(), ProjectModule.getProjectPath());
            if (relativeFilePath.equals("")) {
                Messages.showMessageDialog(ProjectModule.getProject(), "An error occurred during parse the file path from the editor!", "Plugin error", Messages.getErrorIcon());
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
