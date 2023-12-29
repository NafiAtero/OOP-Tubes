package FE.Kitchen;

import BE.OutletItem;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class AddPerishableItemStock extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner addItemAmountSpinner;
    private JTable outletPerishableItemsTable;
    private JLabel amountLabel;
    private final Kitchen parent;
    private List<OutletItem> perishableItems = new ArrayList<>();
    private PerishableItemsModel perishableItemsModel;
    private int selectedItemIndex;
    private OutletItem selectedItem;

    public AddPerishableItemStock(Kitchen parent) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(300,500);
        this.parent = parent;
        for (OutletItem item : parent.getUser().getOutletItems()) {
            if (item.isPerishable()) {
                System.out.println("a");
                perishableItems.add(item);
            }
        }
        perishableItemsModel = new PerishableItemsModel(perishableItems);
        outletPerishableItemsTable.setModel(perishableItemsModel);

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

        outletPerishableItemsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!outletPerishableItemsTable.getSelectionModel().isSelectionEmpty()) {
                selectedItemIndex = outletPerishableItemsTable.convertRowIndexToModel(outletPerishableItemsTable.getSelectedRow());
                selectedItem = parent.getUser().getOutletItems().get(selectedItemIndex);
                if (selectedItem != null) {
                    amountLabel.setText("Amount (" + selectedItem.getName() + ")");
                } else {
                    amountLabel.setText("Amount");
                }
            }
        });
    }

    private void onOK() {
        float stock = 0;
        Object o = addItemAmountSpinner.getValue();
        if (o != null) {
            if (o instanceof Number) {
                stock = ((Number) o).floatValue();
            }
        }
        parent.getUser().createPerishableItem(selectedItem.getItemId(), stock);
        parent.updateTables();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private static class PerishableItemsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Unit", "Current Stock"};
        private List<OutletItem> list;
        public PerishableItemsModel(List<OutletItem> list) {
            this.list = list;
        }
        public void setList(List<OutletItem> list) {
            this.list = list;
        }
        @Override
        public int getRowCount() {
            if (list == null)  return 0;
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
            else if (columnIndex == 2) return list.get(rowIndex).getAmount();
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

    /*public static void main(String[] args) {
        AddPerishableItemStock dialog = new AddPerishableItemStock();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
