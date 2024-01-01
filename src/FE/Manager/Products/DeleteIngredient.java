package FE.Manager.Products;

import BE.Ingredient;
import BE.Product;
import FE.Manager.Manager;

import javax.swing.*;
import java.awt.event.*;

public class DeleteIngredient extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel label1;
    private final Manager parent;
    private final Ingredient ingredient;
    private final Product product;

    public DeleteIngredient(Manager parent, Ingredient ingredient, Product product) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.parent = parent;
        this.ingredient = ingredient;
        this.product = product;
        label1.setText("Delete " +ingredient.getName()+ " from " +product.getName()+ "ingredient?");
        pack();
        setLocationRelativeTo(null);

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
        parent.getUser().deleteProductIngredient(ingredient);
        parent.updateTables();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        DeleteIngredient dialog = new DeleteIngredient();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
