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
import models.bean.ClassTestData;
import models.bean.TestData;
import modules.ProjectModule;
import ui.viewResultTableModels.ClassTableModel;


import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;
import ui.viewResultTableModels.TreeTableModel;

public class ClassTableMouseListener extends AbstractTableMouseListener{

    public ClassTableMouseListener(JTable resultTable, TestData testData) {
        super(resultTable, testData);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = resultTable.getSelectedRow();


        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            String fileName = ((String) resultTable.getValueAt(resultTable.getSelectedRow(), ClassTableModel.FILE_NAME_COLUMN_INDEX));

            String fileNamePath = ProjectModule.getProjectPath() + File.separator + fileName.trim();

            VirtualFile selectedFile = LocalFileSystem.getInstance().findFileByPath(fileNamePath);
            List<ClassTestData> classes = TestData.getInstance().getClasses();
            String fullName = resultTable.getValueAt(selectedRow, ClassTableModel.NAME_COLUMN_INDEX).toString();
            String separator = File.separator.replace("\\","\\\\");
            String[] temp = fullName.split(separator);
            String name = temp[temp.length-1];

            ClassTestData selected = classes.stream().filter(x -> fullName.equals(x.getName())).collect(Collectors.toList()).get(0);

            int line = (int) resultTable.getValueAt(resultTable.getSelectedRow(), ClassTableModel.LINE_COLUMN_INDEX) - 1;
            Project project = ProjectModule.getProject();
            FileEditorManager.getInstance(project).openFile(selectedFile, true);
            Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
            editor.getScrollingModel().scrollTo(new LogicalPosition(line, 0), ScrollType.CENTER);
            editor.getCaretModel().moveToLogicalPosition(new LogicalPosition(line, 0));
            updateIndicatorPanel(resultTable.getValueAt(selectedRow,
                                                        ClassTableModel.FILE_NAME_COLUMN_INDEX).toString(),
                                                        name,
                                                        selected.getLine());
        }
    }
}
