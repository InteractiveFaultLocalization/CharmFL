package ui;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.editor.impl.TextRangeInterval;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import static ui.viewResultTableModels.TreeTableModel.*;

import java.awt.event.MouseEvent;
import java.io.File;

import modules.PluginModule;
import modules.ProjectModule;
import services.runnables.RunAddHighlightToCallGraphRunnable;
import ui.viewResultTableModels.TreeTableModel;

import com.intellij.openapi.progress.ProgressManager;

import java.lang.Thread;

public final class TreeViewTableMouseListener extends MouseInputAdapter {
    private final JTable resultTable;
    private final TreeTableModel tableModel;

    public TreeViewTableMouseListener(JTable resultTable, TreeTableModel tableModel) {
        this.resultTable = resultTable;
        this.tableModel = tableModel;
    }

    /**
     * If you click once on a row in the table then the highlight is shown
     * If you click twice on a row then it opens the file in the editor
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = resultTable.getSelectedRow();
        int selectedColumn = resultTable.getSelectedColumn();

        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {


            String fileName = ((String) resultTable.getValueAt(resultTable.getSelectedRow(), NAME_COLUMN_INDEX))
                    .substring(TreeTableModel.TABLE_ROW_IDENT_PREFIX.length() * 2);

            String fileNamePath = ProjectModule.getProjectPath() + File.separator + fileName.trim();

            VirtualFile selectedFile = LocalFileSystem.getInstance().findFileByPath(fileNamePath);
            if (selectedFile != null && ProjectModule.getProject() != null) {
                System.out.println(resultTable.getValueAt(selectedRow, selectedColumn).getClass().getSimpleName());
                Project project = ProjectModule.getProject();
                FileEditorManager.getInstance(project).openFile(selectedFile, true);
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                int line = (int) resultTable.getValueAt(resultTable.getSelectedRow(), LINE_COLUMN_INDEX) - 1;

                editor.getScrollingModel().scrollTo(new LogicalPosition(line, 0), ScrollType.CENTER);
                editor.getCaretModel().moveToLogicalPosition(new LogicalPosition(line, 0));
            }

        }
    }
}