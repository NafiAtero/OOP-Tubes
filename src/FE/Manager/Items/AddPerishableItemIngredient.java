package FE.Manager.Items;

import BE.PerishableItem;
import BE.RawItem;
import FE.Manager.Manager;
import java.util.List;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.*;

public class AddPerishableItemIngredient extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table;
    private JSpinner amountSpinner;
    private final Manager parent;
    private final PerishableItem perishableItem;
    private RawItem selectedRawItem;
    private RawItemsModel rawItemsModel;

    public AddPerishableItemIngredient(Manager parent, PerishableItem perishableItem) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(500, 600);
        setTitle("Add Perishable Item Ingredient");
        this.parent = parent;
        this.perishableItem = perishableItem;
        setLocationRelativeTo(null);
        rawItemsModel = new RawItemsModel(parent.getUser().getRawItems());
        table.setModel(rawItemsModel);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!table.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = table.convertRowIndexToModel(table.getSelectedRow());
                selectedRawItem = parent.getUser().getRawItems().get(selectedIndex);
                if (selectedRawItem != null) {
                    buttonOK.setText("Add " + selectedRawItem.getName());
                } else {
                    buttonOK.setText("Add");
                }
            }
        });
    }

    private void onOK() {
        float amount = 0;
        Object o = amountSpinner.getValue();
        if (o != null) {
            if (o instanceof Number) {
                amount = ((Number) o).floatValue();
            }
        }
        parent.getUser().addPerishableItemIngredient(selectedRawItem, perishableItem, amount);
        parent.updateTables();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private static class RawItemsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Unit"};
        private List<RawItem> list;
        public RawItemsModel(List<RawItem> list) {
            this.list = list;
        }
        public void setList(List<RawItem> list) {
            this.list = list;
        }
        @Override
        public int getRowCount() {
            return list.size();
        }
        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) return list.get(rowIndex).getName();
            else if (columnIndex == 1) return list.get(rowIndex).getUnit();
            else return "-";
        }
        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
}
