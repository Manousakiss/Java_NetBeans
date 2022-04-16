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
public class Pr_inv extends JFrame {

    public Pr_inv(Connection con) {
        this.con = con;
        initComponents();
    }

    private void initComponents() {
        setTitle("Print Inventory");
        p = new JPanel();
        b = new JButton("Print Inventory");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doPrint();
            }
        });
        keno = new JPanel();
        
        keno.add(b, BorderLayout.CENTER );
        p.add(keno, CENTER_ALIGNMENT  );
        add(p);
        p.setPreferredSize(new Dimension(400, 100));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void doPrint() {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Inventory");
            System.out.println("INVENTORY REPORT");
            System.out.println();
            System.out.println("Code          Category              Description         Price           Quantity");
            System.out.println("=================================================================================");
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("idi");
                String category = rs.getString("category");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                //Display values
                System.out.printf("%3s", id);
                System.out.printf("%19s", category);
                System.out.printf("%25s", description);
                System.out.printf("%19s", price);
                System.out.printf("%17s", quantity);
                System.out.println();

            }
            System.out.println("=================================================================================");

        } catch (SQLException se) {
            se.printStackTrace();
        }
        setVisible(false);
    }

    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private JButton b;
    private JPanel p,keno;

}
