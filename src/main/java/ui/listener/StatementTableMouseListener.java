package ui.listener;


import static ui.viewResultTableModels.TreeTableModel.LINE_COLUMN_INDEX;
import static ui.viewResultTableModels.TreeTableModel.NAME_COLUMN_INDEX;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import java.io.File;
import models.bean.StatementTestData;
import models.bean.TestData;


import modules.ProjectModule;
import ui.viewResultTableModels.StatementTableModel;


import javax.swing.*;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import ui.viewResultTableModels.TreeTableModel;


public class StatementTableMouseListener extends AbstractTableMouseListener {

    public StatementTableMouseListener(JTable resultTable, TestData testData) {
        super(resultTable, testData);
    }


    public void mouseClicked(MouseEvent e) {
        int selectedRow = resultTable.getSelectedRow();

        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            String fileName = ((String) resultTable.getValueAt(resultTable.getSelectedRow(), NAME_COLUMN_INDEX));

            String fileNamePath = ProjectModule.getProjectPath() + File.separator + fileName.trim();
            System.out.println(fileNamePath);
            VirtualFile selectedFile = LocalFileSystem.getInstance().findFileByPath(fileNamePath);
            String name = resultTable.getValueAt(selectedRow, StatementTableModel.NAME_COLUMN_INDEX).toString();
            int line = (int) resultTable.getValueAt(selectedRow, StatementTableModel.LINE_COLUMN_INDEX);
            Project project = ProjectModule.getProject();
            FileEditorManager.getInstance(project).openFile(selectedFile, true);
            Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
            editor.getScrollingModel().scrollTo(new LogicalPosition(line - 1, 0), ScrollType.CENTER);
            editor.getCaretModel().moveToLogicalPosition(new LogicalPosition(line - 1, 0));
            updateIndicatorPanel(name, name, line);
        }
    }
}
