package FE.Manager.Outlets;

import BE.Outlet;
import BE.OutletProduct;
import BE.Product;
import FE.Manager.Manager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.*;
import java.util.List;

public class AddOutletProduct extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable productsTable;
    private Manager parent;
    private Outlet outlet;
    private ProductsModel productsModel;
    private Product selectedProduct;

    public AddOutletProduct(Manager parent, Outlet outlet) {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Add product to outlet (" + outlet.getName() + ")");
        setSize(300,500);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);
        this.parent = parent;
        this.outlet = outlet;
        productsModel = new ProductsModel(parent.getUser().getProducts());
        productsTable.setModel(productsModel);

        // Table Selection
        productsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!productsTable.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = productsTable.convertRowIndexToModel(productsTable.getSelectedRow());
                selectedProduct = parent.getUser().getProducts().get(selectedIndex);
                if (selectedProduct != null) {
                    buttonOK.setText("Add "+selectedProduct.getName());
                } else {
                    buttonOK.setText("Select Product");
                }
            }
        });

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
    }

    // Table Model
    private static class ProductsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Price"};
        private List<Product> list;
        public ProductsModel(List<Product> list) {
            this.list = list;
        }
        public void setList(List<Product> list) {
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
            else if (columnIndex == 1) return list.get(rowIndex).getPrice();
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
        if (selectedProduct != null) {
            parent.getUser().addOutletProduct(outlet, selectedProduct);
        }
        parent.updateTables();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /*public static void main(String[] args) {
        AddOutletProduct dialog = new AddOutletProduct();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
