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
public class inventory_selection extends DefaultComboBoxModel<String> {

    public inventory_selection(Connection con) {
        this.con = con;
        initComponents();
    }
    private void initComponents() {
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "SELECT * FROM inventory";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("inventory_selection: " + e.getMessage());
        }
    }
    @Override
    public String getElementAt(int index) {
        String lstn = null;
        try {
            rs.absolute(index + 1);
            lstn = rs.getString("category") + ", " + rs.getString("description") + ", " + rs.getInt("price") + ", " + rs.getInt("idi");
        } catch (SQLException e) {
            System.out.println("getElementAt(): " + e.getMessage());
        }
        return lstn;
    }
    public String getCategoryAt(int index) {
        String lstn = null;
        try {
            rs.absolute(index + 1);
            lstn = rs.getString("category") ;
        } catch (SQLException e) {
            System.out.println("getCategoryAt(): " + e.getMessage());
        }
        return lstn;
    }
    public String getDescriptionAt(int index) {
        String lstn = null;
        try {
            rs.absolute(index + 1);
            lstn =  rs.getString("description") ;
        } catch (SQLException e) {
            System.out.println("getDescriptionAt(): " + e.getMessage());
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
 
     public int getIdInvAt(int index) {
        int id = 0;
        try {
            rs.absolute(index + 1);
            id = rs.getInt("idi");
        } catch (SQLException e) {
            System.out.println("idinv(): " + e.getMessage());
        }
        return id;
    }
    public int getPriceAt(int index) {
        int price = 0;
        try {
            rs.absolute(index + 1);
            price = rs.getInt("price");
        } catch (SQLException e) {
            System.out.println("price(): " + e.getMessage());
        }
        return price;
    }

    private Connection con;
    private Statement stmt;
    private ResultSet rs;
}
