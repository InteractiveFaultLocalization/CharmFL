package modules;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class DocumentModule {
    private static Document document;

    public static Document getDocument() {
        return document;
    }

    public static VirtualFile getCurrentFile() {
        return FileDocumentManager.getInstance().getFile(document);
    }

    public static String getCurrentFileName() {
        return getCurrentFile().getName();
    }

    public static String getCurrentFilePath() {
        return getCurrentFile().getPath();
    }

    public static void setDocument(Project project) {
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if(editor != null) {
            document = editor.getDocument();
        }
    }
}
