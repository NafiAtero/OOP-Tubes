package FE.Manager.Outlets;

import BE.OutletProduct;
import FE.Manager.Manager;

import javax.swing.*;
import java.awt.event.*;

public class DeleteOutletProduct extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel label;
    private final Manager parent;
    private final OutletProduct outletProduct;

    public DeleteOutletProduct(Manager parent, OutletProduct outletProduct) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Delete outlet product?");
        setLocationRelativeTo(null);
        this.parent = parent;
        this.outletProduct = outletProduct;
        label.setText("Delete " +outletProduct.getName()+ " from outlet?");
        pack();

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

    private void onOK() {
        parent.getUser().deleteOutletProduct(outletProduct);
        parent.updateTables();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /*public static void main(String[] args) {
        DeleteOutletProduct dialog = new DeleteOutletProduct();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
