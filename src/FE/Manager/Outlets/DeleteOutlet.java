package FE.Manager.Outlets;

import BE.Outlet;
import FE.Manager.Manager;

import javax.swing.*;
import java.awt.event.*;

public class DeleteOutlet extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel deleteLabel;
    private Manager parent;
    private Outlet outlet;

    public DeleteOutlet(Manager parent, Outlet outlet) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Delete outlet?");
        setLocationRelativeTo(null);
        pack();
        this.parent = parent;
        this.outlet = outlet;
        deleteLabel.setText("Delete " + outlet.getName());

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
        parent.getUser().deleteOutlet(outlet);
        parent.updateTables();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /*public static void main(String[] args) {
        DeleteOutlet dialog = new DeleteOutlet();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
