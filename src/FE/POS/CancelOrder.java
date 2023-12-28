package FE.POS;

import javax.swing.*;
import java.awt.event.*;

public class CancelOrder extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private final POS parent;

    public CancelOrder(POS parent) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.parent = parent;

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
        parent.getUser().deleteOrder(parent.getSelectedOrder().getOrderId());
        parent.refreshTables();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /*public static void main(String[] args) {
        CancelOrder dialog = new CancelOrder();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
