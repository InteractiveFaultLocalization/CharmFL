package ui.listener;

import static ui.viewResultTableModels.TreeTableModel.NAME_COLUMN_INDEX;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import java.io.File;
import models.bean.ITestData;
import models.bean.MethodTestData;
import models.bean.TestData;
import modules.ProjectModule;
import ui.CallGraphView;
import ui.viewResultTableModels.MethodTableModel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;
import ui.viewResultTableModels.TreeTableModel;

public class MethodTableMouseListener extends AbstractTableMouseListener {

    public MethodTableMouseListener(JTable resultTable, TestData testData) {
        super(resultTable, testData);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = resultTable.getSelectedRow();
        int row = resultTable.rowAtPoint(e.getPoint());
        int column = resultTable.columnAtPoint(e.getPoint());

        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            String fileName = ((String) resultTable.getValueAt(resultTable.getSelectedRow(), MethodTableModel.FILE_NAME_COLUMN_INDEX));

            String fileNamePath = ProjectModule.getProjectPath() + File.separator + fileName.trim();

            VirtualFile selectedFile = LocalFileSystem.getInstance().findFileByPath(fileNamePath);
            ArrayList<ITestData> methods = TestData.getInstance().getAllMethods();
            String fullName = resultTable.getValueAt(selectedRow, MethodTableModel.NAME_COLUMN_INDEX).toString();
            // java.util.regex.PatternSyntaxException: Unexpected internal error near index 1
            String separator = File.separator.replace("\\","\\\\");;
            String[] temp = fullName.split(separator);
            String name = temp[temp.length - 1];
            System.out.println(methods.stream().anyMatch(x -> name.equals(x.getName())));
            MethodTestData selected = (MethodTestData) methods.stream().filter(x -> name.equals(x.getName())).collect(Collectors.toList()).get(0);
            System.out.println(selected.getName());
            for (var a : selected.getFarContext()) {
                System.out.println(a.getName());
                System.out.println(a.getLine());
            }

            int line = (int) resultTable.getValueAt(resultTable.getSelectedRow(), MethodTableModel.LINE_COLUMN_INDEX) - 1;
            Project project = ProjectModule.getProject();
            FileEditorManager.getInstance(project).openFile(selectedFile, true);
            Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
            editor.getScrollingModel().scrollTo(new LogicalPosition(line, 0), ScrollType.CENTER);
            editor.getCaretModel().moveToLogicalPosition(new LogicalPosition(line, 0));
            //System.out.println(selected.getName() + " " + selected.getLine());
            updateIndicatorPanel(resultTable.getValueAt(selectedRow,
                            MethodTableModel.FILE_NAME_COLUMN_INDEX).toString(),
                    name,
                    selected.getLine());
        }
        if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3){
            if (column == MethodTableModel.NAME_COLUMN_INDEX){
                Object value = resultTable.getValueAt(row, column);
                if (value != null) {
                    String methodValue = value.toString();

                    String fileNameOnly = resultTable.getValueAt(row, 0).toString();
                    String fileBaseName = fileNameOnly.replace(".py", "");

                    String methodFormatted = methodValue.replace("\\", "_").replace("/", "_");
                    String fullMethodName = fileBaseName + "." + methodFormatted;

                    String filename = "/html/methods/" + fullMethodName + "_call_chain.html";

                    new CallGraphView(filename, ProjectModule.getProject()).show();
                }
            }
        }
    }

}
