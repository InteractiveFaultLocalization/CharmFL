package ui;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.ui.table.JBTable;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;

import services.FlService;
import services.FlServiceImpl;
import modules.PluginModule;
import modules.ProjectModule;

public class ViewResult extends DialogWrapper {
    private FlService flService;

    public ViewResult() {
        super(true);

        String title = "SBFL Results";
        String spectraMetrics = "";
        if(PluginModule.isTarantulaSelected()) {
            spectraMetrics = " (Tarantula)";
        }
        else if(PluginModule.isOchiaiSelected()) {
            spectraMetrics = " (Ochiai)";
        }
        else if(PluginModule.isDStarSelected() || PluginModule.isWongIISelected()) {
            spectraMetrics = " (WongII)";
        }
        title += spectraMetrics;
        setTitle(title);

        setModal(false);
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        flService = new FlServiceImpl();

        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setPreferredSize(new Dimension(300, 300));
        ViewResultTableModel tableModel = new ViewResultTableModel();
        JTable resultTable = new JBTable(tableModel);
        resultTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = resultTable.getSelectedRow();
                boolean openInEditor = tableModel.isSelectedRowOpenInEditor(selectedRow);

                String fileName = "", fileNamePath = "";
                int line = 0;

                if(e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                    if(!openInEditor) {
                        tableModel.toggleDataShow(selectedRow);
                        resultTable.revalidate();
                    }
                }
                else if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    if(openInEditor) {
                        fileName = (String)resultTable.getValueAt(resultTable.getSelectedRow(), 0);
                        fileName = fileName.substring(ViewResultTableModel.TABLE_ROW_IDENT_PREFIX.length() * 2);
                        System.out.println(fileName);
                        fileNamePath = ProjectModule.getProjectPath() + File.separator + fileName;
                        System.out.println(fileNamePath);
                        VirtualFile selectedFile = LocalFileSystem.getInstance().findFileByPath(fileNamePath);
                        if(selectedFile != null && ProjectModule.getProject() != null) {
                            Project project = ProjectModule.getProject();
                            FileEditorManager.getInstance(project).openFile(selectedFile, true);
                            Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                            line = (int)resultTable.getValueAt(resultTable.getSelectedRow(), 1) - 1;
                            editor.getCaretModel().moveToLogicalPosition(new LogicalPosition(line, 0));
                        }
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
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(280);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(5);
        resultTable.getColumnModel().getColumn(2).setPreferredWidth(5);
        resultTable.getColumnModel().getColumn(3).setPreferredWidth(5);
        resultTable.getColumnModel().getColumn(4).setPreferredWidth(5);
        //resultTable.setAutoCreateRowSorter(true);

        JScrollPane scrollableTextArea = new JBScrollPane(resultTable);
        scrollableTextArea.setPreferredSize(new Dimension(300, 300));
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        dialogPanel.add(scrollableTextArea, BorderLayout.CENTER);
        return dialogPanel;
    }

    @Override
    protected @NotNull Action[] createActions() {
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