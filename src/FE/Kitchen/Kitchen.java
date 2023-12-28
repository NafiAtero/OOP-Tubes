package FE.Kitchen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Kitchen extends JFrame {
//region SWING COMPONENTS
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JButton finishOrderButton;
    private JList activeOrdersTable;
    private JTable orderProductsTable;
    private JButton deliverOrderProductButton;
    private JButton undoDeliverOrderProductButton;
    private JTable inventoryTable;
    private JButton addRawItemStockButton;
    private JButton addPerishableItemStockButton;
    private JButton clearPerishableItemStockButton;
    private JSpinner itemStockSpinner;
    private JButton saveEditItemButton;
//endregion
    private final BE.Kitchen user;

    public Kitchen(int userId, int companyId, int outletId) {
        setTitle("Kitchen Resto Vision");
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setVisible(true);
        user = new BE.Kitchen(userId, companyId, outletId);
        finishOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmFinishOrder dialog = new ConfirmFinishOrder();
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        addPerishableItemStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPerishableItemStock dialog = new AddPerishableItemStock();
                //dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        clearPerishableItemStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClearPerishableItemStock dialog = new ClearPerishableItemStock();
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        new Kitchen(2,2,2);
    }
}
