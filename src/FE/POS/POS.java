package FE.POS;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// todo move dialog pack setLocationRelativeTo
public class POS extends JFrame {
//region SWING COMPONENTS
    private JPanel mainPanel;
    private JButton finishOrderButton;
    private JButton saveEditOrderButton;
    private JButton addProductToOrderButton;
    private JTable activeOrdersTable;
    private JButton addOrderButton;
    private JTable orderProductsTable;
    private JTable availableProductsTable;
    private JButton deleteOrderButton;
    private JTextField orderTableNameTextField;
    private JButton deleteOrderProductButton;
    private JButton saveEditOrderProductButton;
    private JSpinner orderProductQuantitySpinner;
//endregion
    private final BE.POS user;
    public POS(int userId, int companyId, int outletId) {
        setTitle("Cashier Resto Vision");
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setVisible(true);

        user = new BE.POS(0, 0, 0);
        finishOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmFinishOrder dialog = new ConfirmFinishOrder();
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CancelOrder dialog = new CancelOrder();
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        deleteOrderProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteOrderProduct dialog = new DeleteOrderProduct();
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOrder dialog = new AddOrder();
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        saveEditOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //user.updateOrder(156744, orderTableNameTextField.getText())
            }
        });
    }
    private void updateTables() {

    }
    public static void main(String[] args) {
        new POS(3, 2, 2);
    }
}
