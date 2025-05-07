package ui.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import models.bean.ClassTestData;
import models.bean.TestData;
import models.bean.ViewTestData;
import modules.ProjectModule;
import ui.CallGraphView;
import ui.viewResultTableModels.ClassTableModel;
import ui.viewResultTableModels.MethodTableModel;
import ui.viewResultTableModels.TestTableModel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
public class CallChainTableMouseListener extends AbstractTableMouseListener{

    public CallChainTableMouseListener(JTable resultTable, ViewTestData viewTestData) {
        super(resultTable, viewTestData);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String path = "/html/tests/";
        if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3 || e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 ) {
            int row = resultTable.rowAtPoint(e.getPoint());
            int column = resultTable.columnAtPoint(e.getPoint());
            if (column == TestTableModel.TESTNAME_COLUMN_INDEX){
                if (row >= 0) {
                    Object value = resultTable.getValueAt(row, column);
                    String filename = value != null ? path + value + "_call_chain.html" : "";
                    if (!filename.isEmpty()){
                        new CallGraphView(filename, ProjectModule.getProject()).show();
                    }
                }
            }
        }
    }

}

