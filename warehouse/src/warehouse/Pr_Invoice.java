/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Manousakis
 */
public class Pr_Invoice extends JFrame {

    public Pr_Invoice(Connection con) {
        this.con = con;
        initCompo();

    }

    private void initCompo() {
        setTitle("Print Invoice");
        panel = new JPanel();
        Selection = new JPanel(new GridLayout(1, 1));
        cb = new JComboBox<>();
        cb.setModel(new customer_selection(con));

        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });
        lb = new JLabel("Invoice For Customer:", JLabel.CENTER);
        pprint = new JPanel(new BorderLayout());
        print = new JButton("Print");
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                cust = cb.getSelectedIndex();
                if (cust == -1)
                {
                 JOptionPane.showMessageDialog(null, "You have to select a customer to invoice him. Select one.","Blank selection",JOptionPane.WARNING_MESSAGE);
                }
                else{
                int custid = (new customer_selection(con)).getIdCustAt(cust);
                
                try {
                    PreparedStatement stmt = con.prepareStatement("SELECT * FROM warehousedb.order left join warehousedb.customers on idco=idc left join warehousedb.inventory on idio=idi where idc=?",
                            ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    stmt.setInt(1, custid);
                    rs = stmt.executeQuery();

                    //apotelesmata tou result set sto netbeans
                    System.out.println("Invoice  REPORT");
                    System.out.println();
                    System.out.println("For Customer ID:"+custid);
                    rs.first();
                    String names=rs.getString("firstname");
                    String surnames=rs.getString("lastname");
                    System.out.println("  Customer Name: "+surnames +" "+names);
                    
                    System.out.println();
                    System.out.println("Order            Category            description          quantity               Price");
                    System.out.println("=======================================================================================");
                    rs.beforeFirst();
                    while (rs.next()) {
                        //Retrieve by column name
                        id = rs.getInt("ido");
                        
                            String category = rs.getString("category");
                            String quantity = rs.getString("quantity");
                            String description = rs.getString("description");
                            int price = rs.getInt("price");

                            System.out.printf("%3s", id);
                            System.out.printf("%22s", category);
                            System.out.printf("%23s", description);
                            System.out.printf("%18s", quantity);
                            
                            System.out.printf("%20s", price);
                            System.out.println();
                    }
                        System.out.println("=======================================================================================");
                    
                } catch (SQLException se) {
                    se.printStackTrace();
                }
                setVisible(false);
            }}
        });
        
        Selection.add(lb);
        Selection.add(cb);
        panel.add(Selection, CENTER_ALIGNMENT);


        panel.add(print);
        add(panel, BorderLayout.SOUTH);

        panel.setPreferredSize(new Dimension(400, 100));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

    }


    private Connection con;
    private Statement stmt, pstmt;
    private ResultSet rs;
    private JPanel panel, pprint, Selection;
    private JComboBox<String> cb;
    private JLabel lb;
    private JButton print;
    private Integer id = -1, cust=-1;

}
