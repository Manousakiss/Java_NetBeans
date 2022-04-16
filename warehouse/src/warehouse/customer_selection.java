/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import java.sql.*;
import javax.swing.*;

/**
 *
 * @author Manousakis
 */
public class customer_selection extends DefaultComboBoxModel<String> {

    public customer_selection(Connection con) {
        this.con = con;
        initComponents();
    }

    private void initComponents() {
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "SELECT * FROM customers";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("customer_selection: " + e.getMessage());
        }
    }

    @Override
    public String getElementAt(int index) {
        String lstn = null;
        try {
            rs.absolute(index + 1);
            lstn = rs.getString("lastname") + ", " + rs.getString("firstname") + ", " + rs.getInt("idc");
        } catch (SQLException e) {
            System.out.println("getElementAt(): " + e.getMessage());
        }
        return lstn;
    }

    @Override
    public int getSize() {
        int cnt = 0;

        try {
            rs.last();
            cnt = rs.getRow();
        } catch (SQLException ex) {
            System.out.println("getSize(): " + ex.getMessage());
        }
        return cnt;
    }

    public int getIdCustAt(int index) {
        int id = 0;
        try {
            rs.absolute(index + 1);
            id = rs.getInt("idc");
        } catch (SQLException e) {
            System.out.println("idcust(): " + e.getMessage());
        }
        return id;
    }

    public String getCustAt(int index) {
        String cust = null;
        try {
            rs.absolute(index + 1);
            cust = rs.getString("lastname");
        } catch (SQLException e) {
            System.out.println("idcust(): " + e.getMessage());
        }
        return cust;
    }

    private Connection con;
    private Statement stmt;
    private ResultSet rs;
}
