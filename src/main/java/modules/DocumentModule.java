package modules;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class DocumentModule {
    /**
     * Opened file eg example.py
     */
    private static Document document;

    /**
     * Get the document object
     * @return document object.
     */
    public static Document getDocument() {
        return document;
    }

    /**
     * Gets the currently opened file
     * @return document object
     */
    public static VirtualFile getCurrentFile() {
        return FileDocumentManager.getInstance().getFile(document);
    }

    /**
     * Gets the currently opened file's name
     * @return a string of the file name.
     */
    public static String getCurrentFileName() {
        return getCurrentFile().getName();
    }

    /**
     * Get the currently opened file's path.
     * @return a path.
     */
    public static String getCurrentFilePath() {
        return getCurrentFile().getPath();
    }

    /**
     * Set the document file
     * @param project
     */
    public static void setDocument(Project project) {
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor != null) {
            document = editor.getDocument();
        }
    }
}
