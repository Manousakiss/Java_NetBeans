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
public class Pr_cus extends JFrame {

    public Pr_cus(Connection con) {
        this.con = con;
        initComponents();
    }

    private void initComponents() {
        setTitle("Print Customers");
        
        p = new JPanel();
        b = new JButton("Print Customers");
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
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM customers");

            //apotelesmata tou result set sto netbeans
            System.out.println("CUSTOMER REPORT");
            System.out.println();
            System.out.println("Code          Lastname          Firstname             AFM           Telephone");
            System.out.println("==============================================================================");
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("idc");
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                int AFM = rs.getInt("AFM");
                String phone = rs.getString("phone");
                //Display values
                System.out.printf("%3s", id);
                System.out.printf("%19s", lname);
                System.out.printf("%19s", fname);
                System.out.printf("%19s", AFM);
                System.out.printf("%17s", phone);
                System.out.println();

            }
            System.out.println("==============================================================================");
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
