/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import java.sql.*;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Manousakis
 */
public class ordertable extends AbstractTableModel {

    public ordertable(Connection con) {
        this.con=con;
        try {
            String changes=("SELECT * FROM warehousedb.order left join warehousedb.customers on idco=idc left join warehousedb.inventory on idio=idi");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs= stmt.executeQuery(changes);
            fireTableDataChanged();
        
    }  catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

  @Override
    public int getRowCount() {
        int count = 0;
        try {
            rs.last();
            count = rs.getRow();
            rs.beforeFirst();
            
        } catch (SQLException ex) {
            System.out.println("Model" + ex.getMessage());
        }
        return count;        
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object retVal = null;

        try {
            rs.absolute(rowIndex + 1);
            switch (columnIndex) {
                case 0:
                    retVal = rs.getInt("ido");
                    break;
                case 1:
                    retVal = rs.getString("lastname");
                    break;
                case 2:
                    retVal = rs.getString("category");
                    break;
                case 3:
                    retVal = rs.getString("description");
                    break;
                case 4:
                    retVal = rs.getInt("price");
                
            }
        } catch (SQLException sex) {
            sex.printStackTrace();
        }

        return retVal;
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column] == null ? "No Name" : columnNames[column];
    }

    private String[] columnNames = {"id", "Customer", "Category", "Description", "Price"};

    private ResultSet rs;
    private Connection con;
    private Statement stmt;
}
