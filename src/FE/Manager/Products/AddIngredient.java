package FE.Manager.Products;

import BE.Item;
import BE.PerishableItem;
import BE.Product;
import FE.Manager.Manager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.*;
import java.util.List;

public class AddIngredient extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table;
    private JSpinner spinner;
    private final Manager parent;
    private final Product product;
    private final ItemsModel model;
    private Item selectedItem;

    public AddIngredient(Manager parent, Product product) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(500, 600);
        this.parent = parent;
        this.product = product;
        model = new ItemsModel(parent.getUser().getItems());
        table.setModel(model);


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
                selectedItem = parent.getUser().getItems().get(selectedIndex);
                if (selectedItem != null) {
                    buttonOK.setText("Add " + selectedItem.getName());
                } else {
                    buttonOK.setText("Add");
                }
            }
        });
    }


    // Model
    private static class ItemsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Unit", "Perishable"};
        private List<Item> list;

        public ItemsModel(List<Item> list) {
            this.list = list;
        }

        public void setList(List<Item> list) {
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
            else if (columnIndex == 2) return list.get(rowIndex) instanceof PerishableItem;
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

    private void onOK() {
        float amount = 0;
        Object o = spinner.getValue();
        if (o != null) {
            if (o instanceof Number) {
                amount = ((Number) o).floatValue();
            }
        }
        parent.getUser().addProductIngredient(product, selectedItem, amount);
        parent.updateTables();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


}
