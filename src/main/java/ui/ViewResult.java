package ui;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.ui.table.JBTable;

import services.FlService;
import services.FlServiceImpl;
import modules.ProjectModule;

public class ViewResult extends DialogWrapper {
    private FlService flService;

    public ViewResult() {
        super(true);
        setTitle("Fault Localization results");
        setModal(false);
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        flService = new FlServiceImpl();

        JPanel dialogPanel = new JPanel(new BorderLayout());
        //dialogPanel.setPreferredSize(new Dimension(100, 100));
        TableModel tableModel = new TableModel() {
            private String[] columnNames = {"File", "Number of Line", "Score"};
            private String[][] data = flService.prepareTestDataForTable();

            @Override
            public int getRowCount() {
                return data.length;
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Nls
            @Override
            public String getColumnName(int columnIndex) {
                return columnNames[columnIndex];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return data[0][columnIndex].getClass();
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return data[rowIndex][columnIndex];
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                data[rowIndex][columnIndex] = (String)aValue;
            }

            @Override
            public void addTableModelListener(TableModelListener l) {

            }

            @Override
            public void removeTableModelListener(TableModelListener l) {

            }
        };

        JTable resultTable = new JBTable(tableModel);
        resultTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    VirtualFile selectedFile = LocalFileSystem.getInstance().findFileByPath(ProjectModule.getProjectPath() + File.separator + (String)resultTable.getValueAt(resultTable.getSelectedRow(), 0));
                    if(selectedFile != null && ProjectModule.getProject() != null) {
                        Project project = ProjectModule.getProject();
                        FileEditorManager.getInstance(project).openFile(selectedFile, true);
                        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                        editor.getCaretModel().moveToLogicalPosition(new LogicalPosition(Integer.parseInt((String)resultTable.getValueAt(resultTable.getSelectedRow(), 1)) - 1, 0));
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        resultTable.setSelectionMode(SINGLE_SELECTION);
        resultTable.setAutoCreateRowSorter(true);

        JScrollPane scrollableTextArea = new JBScrollPane(resultTable);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        dialogPanel.add(scrollableTextArea, BorderLayout.CENTER);
        return dialogPanel;
    }

    @Override
    protected Action @NotNull [] createActions() {
        //super.createDefaultActions();
        Action close = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flService.setViewResultTableDialogOpened(false);
                close(0);

            }
        };
        //Action close = getCancelAction();
        //setCancelButtonText("Close");
        close.putValue(Action.NAME, "Close");
        return new Action[] {close};
    }
}