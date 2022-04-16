/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 *
 * @author Manousakis
 */
public class Place_o extends JFrame {

    public Place_o(Connection con) throws SQLException {
        this.con = con;
        initCompo();

    }

    public void initCompo() throws SQLException {
        setTitle("Place Order");
        p = new JPanel(new BorderLayout());
        fields = new JPanel(new GridLayout(6, 2));
        lbcus = new JLabel("Customer:", JLabel.RIGHT);
        cbcus = new JComboBox<>();
        cbcus.setModel(new customer_selection(con));

        cbcus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cust = cbcus.getSelectedIndex();
                custid = (new customer_selection(con)).getIdCustAt(cust);
                System.out.println(custid);
                customer = (new customer_selection(con)).getCustAt(cust);
                System.out.println(customer);
            }
        });
        lbinv = new JLabel("Inventory:", JLabel.RIGHT);

        cbinv = new JComboBox<>();
        //edw exw allo arxeio pou diaxeirizetai to table
        cbinv.setModel(new inventory_selection(con));
        cbinv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inv = cbinv.getSelectedIndex();
                invid = (new inventory_selection(con)).getIdInvAt(inv);
                price = (new inventory_selection(con)).getPriceAt(inv);
                tbprice.setText(String.valueOf(price));
                tbquan.setText("1");
                tbt_price.setText(String.valueOf(price));
                category = (new inventory_selection(con)).getCategoryAt(inv);
                description = (new inventory_selection(con)).getDescriptionAt(inv);

            }
        }
        );
        lbprice = new JLabel("Item Price:", JLabel.RIGHT);
        lbquan = new JLabel("Quanity:", JLabel.RIGHT);
        lbt_price = new JLabel("Total Price:", JLabel.RIGHT);
        tbprice = new JTextField();
        tbquan = new JTextField();
        tbt_price = new JTextField();
        tbprice.setEditable(false);
        tbquan.setEditable(false);
        tbt_price.setEditable(false);
        keno = new JLabel("");

        fields.add(lbcus);
        fields.add(cbcus);
        fields.add(lbinv);
        fields.add(cbinv);
        fields.add(lbprice);
        fields.add(tbprice);
        fields.add(lbquan);
        fields.add(tbquan);
        fields.add(lbt_price);
        fields.add(tbt_price);

        ptable = new JPanel(new BorderLayout());
        table = new JTable(new ordertable(con));

        buttonp = new JPanel();
        badd = new JButton("Add Line");
        badd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cust == -1) {
                    JOptionPane.showMessageDialog(null, "You have to select a customer to place an order. Select one.", "Blank selection of customer", JOptionPane.WARNING_MESSAGE);
                } else if (inv == -1) {
                    JOptionPane.showMessageDialog(null, "You have to select an item to place an order. Select one.", "Blank selection of item", JOptionPane.WARNING_MESSAGE);
                } else {

                    mode = 0;
                    try {
                        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        rs = stmt.executeQuery("select * from warehousedb.order  ");
                        if (mode == 0) {
                            rs.moveToInsertRow();
                        }
                        rs.updateInt("idco", custid);
                        rs.updateInt("idio", invid);
                        rs.updateInt("quantity", 1);
                        rs.updateInt("price", price);
                        if (mode == 0) {
                            rs.insertRow();
                        }
                    } catch (SQLException g) {
                        System.out.println(g.getMessage());

                    }
                    table.setModel(new ordertable(con));
                }
            }
        }
        );
        bdelete = new JButton("Delete Line");
        bdelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (row >= 0 && col >= 0) {
                    try {
                        String sql = "DELETE FROM warehousedb.order WHERE ido = ?";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        {
                            pstmt.setInt(1, (Integer) table.getModel().getValueAt(row, 0));
                            pstmt.executeUpdate();
                        }
                    } catch (SQLException h) {
                        System.out.println(h.getMessage());
                    }
                }
                table.setModel(new ordertable(con));
            }
        });
        bExt = new JToolBar();
        bExt.setFloatable(false);
        bExit = new JButton("Exit");
        bExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);

            }
        });

        buttonp.add(badd);
        buttonp.add(bdelete);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                row = table.rowAtPoint(evt.getPoint());
                col = table.columnAtPoint(evt.getPoint());

            }
        });

        p.add(fields, BorderLayout.NORTH);
        p.add(buttonp, BorderLayout.CENTER);

        sc = new JScrollPane(table);
        ptable.add(sc);
        ptable.setPreferredSize(new Dimension(150, 150));
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buttonp, ptable);
        p.add(splitPane);
        bExt.add(bExit);
        p.add(bExt, BorderLayout.SOUTH);

        add(p);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

    }

    private JPanel p, fields, buttonp, ptable;
    private JToolBar bExt;
    private JTable table;
    private JSplitPane splitPane;
    private JScrollPane sc;
    private JComboBox<String> cbcus;
    private JComboBox<String> cbinv;
    private JLabel lbcus, lbinv, lbprice, lbquan, lbt_price, keno;
    private JTextField tbprice, tbquan, tbt_price;
    private JButton bdelete, badd, bExit;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    public String category, description, customer;
    public int invid, inv = -1, custid, cust = -1, price, mode = 0, current = 0, row, col;

}
