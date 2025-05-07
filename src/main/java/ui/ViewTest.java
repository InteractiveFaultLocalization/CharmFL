package ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.JBColor;
import com.intellij.ui.SearchTextField;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import models.bean.TestData;
import models.bean.ViewTestData;
import modules.PluginModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import services.FlServiceImpl;
import services.Resources;
import ui.listener.CallChainTableMouseListener;
import ui.listener.ClassTableMouseListener;
import ui.listener.TreeViewTableMouseListener;
import ui.viewResultTableModels.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class ViewTest extends DialogWrapper {

    private final FlServiceImpl flService;
    private final ViewTestData viewTestData;
    private TestTableModel testViewTableModel;
    private JTable testViewTable;
    private JTabbedPane tabsPane;

    public ViewTest() {
        super(true);
        tabsPane = new JTabbedPane();
        String rankType = "";
        if (PluginModule.isAverageSelected()) {
            rankType = Resources.get("titles", "average_button");
        } else if (PluginModule.isMinimumSelected()) {
            rankType = Resources.get("titles", "minimum_button");
        } else if (PluginModule.isMaximumSelected()) {
            rankType = Resources.get("titles", "maximum_button");
        }

        String title = "Test Call Chains";
        String spectraMetrics = "";
        if (PluginModule.isTarantulaSelected()) {
            spectraMetrics = " (Tarantula)";
        } else if (PluginModule.isOchiaiSelected()) {
            spectraMetrics = " (Ochiai)";
        } else if (PluginModule.isWongIISelected()) {
            spectraMetrics = " (WongII)";
        } else if (PluginModule.isDStarSelected()) {
            spectraMetrics = " (DStar)";
        } else if (PluginModule.isBarinelSelected()) {
            spectraMetrics = " (Barinel)";
        }
        title += spectraMetrics;

        setTitle(title);

        flService = new FlServiceImpl();
        viewTestData = ViewTestData.getInstance();
        testViewTableModel = new TestTableModel(viewTestData);

        setModal(false);
        getWindow().addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                closeWindow();
            }
        });
        init();
    }

    public int getSelectedIndex(){return tabsPane.getSelectedIndex();}
    public void setSelectedIndex(int index){tabsPane.setSelectedIndex(index);}

    private static class SearchField {

        SearchTextField searchTextField;
        String placeholderText;

        public SearchField(String placeholderText) {
            this.searchTextField = new SearchTextField();
            this.placeholderText = placeholderText;
            setPlaceholder();
        }

        private void setPlaceholder() {
            this.searchTextField.setText(this.placeholderText);
            this.searchTextField.getTextEditor().setForeground(JBColor.GRAY);

            this.searchTextField.getTextEditor().addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (searchTextField.getText().equals(placeholderText)) {
                        searchTextField.setText("");
                        searchTextField.getTextEditor().setForeground(JBColor.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (searchTextField.getText().isEmpty()) {
                        searchTextField.getTextEditor().setForeground(JBColor.GRAY);
                        searchTextField.setText(placeholderText);
                    }
                }
            });
        }

        private void setSorter(TableRowSorter<TableModel> sorter) {
            this.searchTextField.addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    String text = searchTextField.getText();
                    if (text.trim().length() == 0 || searchTextField.getText().equals(placeholderText)) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    String text = searchTextField.getText();
                    if (text.trim().length() == 0 || searchTextField.getText().equals(placeholderText)) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {

                }
            });
        }
    }


    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        this.testViewTable = new JBTable(testViewTableModel);

        testViewTable.addMouseListener(new CallChainTableMouseListener(testViewTable,viewTestData));
        testViewTable.setSelectionMode(SINGLE_SELECTION);
        testViewTable.setAutoCreateRowSorter(true);
        testViewTable.getColumnModel().getColumn(TestTableModel.TESTNAME_COLUMN_INDEX).setPreferredWidth(250);
        testViewTable.getColumnModel().getColumn(TestTableModel.RESULT_COLUMN_INDEX).setPreferredWidth(75);
        testViewTable.getColumnModel().getColumn(TestTableModel.HEURISTIC_COLUMN_INDEX).setPreferredWidth(75);
        tabsPane.addTab(Resources.get("titles", "test_call_graph_title"), createTableScrollPane(testViewTable));
        tabsPane.setPreferredSize(new Dimension(500, 500));
        tabsPane.setLocation(600,300);
        pack();
        return tabsPane;
    }

    private JBTable createSubViewTable(TableModel tableModel, int fileNameIndex, int nameColumnIndex, int scoreColumnIndex, int modifiedScoreColumnIndex, int rankColumnIndex) {
        JBTable table = new JBTable();
        table.setAutoCreateRowSorter(true);

        table.getColumnModel().getColumn(nameColumnIndex).setPreferredWidth(80);
        table.getColumnModel().getColumn(fileNameIndex).setPreferredWidth(115);
        table.getColumnModel().getColumn(scoreColumnIndex).setPreferredWidth(5);
        table.getColumnModel().getColumn(modifiedScoreColumnIndex).setPreferredWidth(80);
        table.getColumnModel().getColumn(rankColumnIndex).setPreferredWidth(5);

        return table;
    }

    private JBScrollPane createTableScrollPane(JTable table) {
        JPanel mainPanel = createSearchField(table);

        JBScrollPane scrollPane = new JBScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    private JPanel createSearchField(JTable table) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        if (!(table.getModel().getClass().toString()).equals(TreeTableModel.class.toString())) {
            final TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
            table.setRowSorter(sorter);
            table.getRowSorter().toggleSortOrder(2);
            table.getRowSorter().toggleSortOrder(2);

            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

            String placeholderText = "";

            if ((table.getModel().getClass().toString()).equals(ClassTableModel.class.toString())) {
                placeholderText = Resources.get("titles", "class_placeholder");
            } else if ((table.getModel().getClass().toString()).equals(MethodTableModel.class.toString())) {
                placeholderText = Resources.get("titles", "method_placeholder");
            } else if ((table.getModel().getClass().toString()).equals(StatementTableModel.class.toString())) {
                placeholderText = Resources.get("titles", "statement_placeholder");
            }
            String finalPlaceholderText = placeholderText;

            SearchField searchField = new SearchField(finalPlaceholderText);
            searchField.setSorter(sorter);

            headerPanel.add(searchField.searchTextField);
            headerPanel.add(table.getTableHeader());
            mainPanel.add(headerPanel, BorderLayout.PAGE_START);
        } else {
            mainPanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        }

        mainPanel.add(table, BorderLayout.CENTER);
        return mainPanel;
    }

    private void closeWindow() {
        //refresh();
        flService.setViewTestTableDialogOpened(false);
        close(0);
    }

    @Override
    protected @NotNull Action[] createActions() {
        Action close = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        };

        close.putValue(Action.NAME, "Close");
        return new Action[] {close};
    }
}