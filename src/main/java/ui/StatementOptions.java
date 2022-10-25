package ui;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ui.JBUI;
import models.bean.ClassTestData;
import models.bean.MethodTestData;
import models.bean.StatementTestData;
import models.bean.TestData;
import modules.ProjectModule;
import org.jetbrains.annotations.Nullable;
import services.CallGraphEdgeData;
import services.Resources;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class StatementOptions extends DialogWrapper {

    private ArrayList<String> nameList;
    private ArrayList<Integer> lineList;
    private ArrayList<Double> rankList;
    private TestData testData;
    private int currentRow;

    JRadioButton buggyRadioButton = new JRadioButton(Resources.get("titles", "buggy_radio_button"));
    JLabel buggyContextLabel = new JLabel(Resources.get("titles", "buggy_context_label"));
    JRadioButton closeContextRadioButton = new JRadioButton(Resources.get("titles", "close_context_radio_button"));
    JRadioButton farContextRadioButton = new JRadioButton(Resources.get("titles", "far_context_radio_button"));
    JRadioButton neitherRadioButton = new JRadioButton(Resources.get("titles", "neither_radio_button"));
    JRadioButton notBuggyRadioButton = new JRadioButton(Resources.get("titles", "not_buggy_radio_button"));
    JButton viewCloseContextButton = new JButton(Resources.get("titles", "close_context_button"));
    JButton viewFarContextButton = new JButton(Resources.get("titles", "far_context_button"));
    JButton prevButton = new JButton(Resources.get("titles", "prev_button"));
    JButton nextButton = new JButton(Resources.get("titles", "next_button"));

    public StatementOptions(ArrayList<String> nameList, ArrayList<Integer> lineList, ArrayList<Double> rankList, TestData testData, int currentRow) {
        super(true);
        this.nameList = nameList;
        this.lineList = lineList;
        this.rankList = rankList;
        this.testData = testData;
        this.currentRow = currentRow;
        setTitle(Resources.get("titles", "statement_options"));
        init();
    }

    /**
     * If we click on the close-context button it will open the file in the editor and put the cursor on the stament number If we click on the prev button then the previous element will be shown in the table If we click on the next button
     * the next element will be shown in the table And if we click on the view far-context button, then the caller and called methods will be highlighted in the generated call graph.
     */
    @Override
    protected @Nullable JComponent createCenterPanel() {

        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setLayout(new BorderLayout());

        viewCloseContextButton.addActionListener(e -> {

            String fileName = nameList.get(currentRow);

            String fileNamePath = ProjectModule.getProjectPath() + File.separator + fileName;
            StatementTestData statement = getStatement(lineList.get(currentRow));
            for (var statementcontext : statement.getCloseContext()){
                System.out.println(statementcontext.getLine());
            }
            VirtualFile selectedFile = LocalFileSystem.getInstance().findFileByPath(fileNamePath);
            // TODO: Close context should put the cursor to the method start line
            if (selectedFile != null && ProjectModule.getProject() != null) {
                Project project = ProjectModule.getProject();
                FileEditorManager.getInstance(project).openFile(selectedFile, true);
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                editor.getCaretModel().moveToLogicalPosition(new LogicalPosition(lineList.get(currentRow) - 1, 0));
            }
        });

        prevButton.addActionListener(e -> {
            if (currentRow != 0) {
                currentRow -= 1;
                dialogPanel.removeAll();
                initPanel(dialogPanel);
                dialogPanel.revalidate();
                dialogPanel.repaint();

            }
        });
        nextButton.addActionListener(e -> {
            if (currentRow != nameList.size() - 1) {
                currentRow += 1;
                dialogPanel.removeAll();
                initPanel(dialogPanel);
                dialogPanel.revalidate();
                dialogPanel.repaint();

            }
        });
        viewFarContextButton.addActionListener(e -> {
            for (ClassTestData classTestData : testData.getClasses()) {
                for (MethodTestData methodTestData : classTestData.getMethods()) {
                    for (StatementTestData statement : methodTestData.getStatements()) {
                        if (this.nameList.get(currentRow).equals(classTestData.getRelativePath()) &&
                                this.lineList.get(currentRow) == statement.getLine()) {
                            String relativePath = classTestData.getRelativePath();
                            String methodName = statement.getMethodName();
                            CallGraphEdgeData.createHighlightedCallGraph(relativePath, methodName);
                        }
                    }
                }
            }

            new CallGraphHighlightedView().show();
        });

        initPanel(dialogPanel);

        return dialogPanel;
    }

    private StatementTestData getStatement(int line) {
        for (var classInstance : testData.getClasses()) {
            for (var methodInstance : classInstance.getMethods()) {
                for (var statementInstance : methodInstance.getStatements()) {
                    if (statementInstance.getLine() == line) {
                        return statementInstance;
                    }
                }
            }
        }
        return null;
    }


    /**
     * Creates the window view
     */
    public void initPanel(JPanel dialogPanel) {
        JPanel radioButtonPanel = new JPanel(new BorderLayout());
        radioButtonPanel.setLayout(new BoxLayout(radioButtonPanel, BoxLayout.Y_AXIS));
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JPanel tablePanel = new JPanel(new BorderLayout());
        JPanel headerPanel = new JPanel(new BorderLayout());

        String[] columnNames = {"Name", "Line", "Rank"};
        Object[][] data = {
                {this.nameList.get(currentRow), this.lineList.get(currentRow), this.rankList.get(currentRow)}
        };
        JTable table = new JTable(data, columnNames);

        buggyContextLabel.setBorder(JBUI.Borders.emptyTop(5));
        closeContextRadioButton.setBorder(JBUI.Borders.emptyLeft(20));
        farContextRadioButton.setBorder(JBUI.Borders.emptyLeft(20));
        neitherRadioButton.setBorder(JBUI.Borders.emptyLeft(20));
        notBuggyRadioButton.setBorder(JBUI.Borders.emptyTop(5));
        viewCloseContextButton.setPreferredSize(new Dimension(50, 10));
        buttonPanel.setBorder(JBUI.Borders.emptyTop(35));
        tablePanel.setBorder(JBUI.Borders.empty(20, 0));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(buggyRadioButton);
        buttonGroup.add(closeContextRadioButton);
        buttonGroup.add(farContextRadioButton);
        buttonGroup.add(neitherRadioButton);
        buttonGroup.add(notBuggyRadioButton);

        tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        tablePanel.add(table, BorderLayout.CENTER);

        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(nextButton, BorderLayout.EAST);
        headerPanel.add(tablePanel, BorderLayout.PAGE_END);

        radioButtonPanel.add(buggyRadioButton);
        radioButtonPanel.add(buggyContextLabel);
        radioButtonPanel.add(closeContextRadioButton);
        radioButtonPanel.add(farContextRadioButton);
        radioButtonPanel.add(neitherRadioButton);
        radioButtonPanel.add(notBuggyRadioButton);

        buttonPanel.add(viewCloseContextButton);
        buttonPanel.add(viewFarContextButton);

        dialogPanel.add(headerPanel, BorderLayout.PAGE_START);
        dialogPanel.add(radioButtonPanel, BorderLayout.LINE_START);
        dialogPanel.add(buttonPanel, BorderLayout.LINE_END);
    }
}