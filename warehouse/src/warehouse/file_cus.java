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


public class file_cus extends JFrame {

    public file_cus(Connection con) {
        this.con = con;
        initComponents();
        prepareForm();
    }

    private void initComponents() {
        setTitle("Customers");
        tb = new JToolBar();
        tb.setFloatable(false);
        bDelete = new JButton("Delete");
        bDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doDelete();
            }
        });
        badd = new JButton("Add");
        badd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doRegister();
            }
        });

        bFrst = new JButton("First");
        bFrst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doFirst();
            }
        });
        bPrv = new JButton("Previous");
        bPrv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doPrevious();
            }
        });
        bNxt = new JButton("Next");
        bNxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doNext();
            }
        });
        bLst = new JButton("Last");
        bLst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLast();
            }
        });
        bOK = new JButton("OK");
        bOK.setEnabled(false);
        bOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doOK();
            }
        });
        bCancel = new JButton("Cancel");
        bCancel.setEnabled(false);
        bCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doCancel();
            }
        });
        bModi = new JButton("Modify");
        bModi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doModify();
            }
        });
        bex = new JPanel();
        bExit = new JButton("Exit");
        bExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doExit();
            }
        });

        tb.add(bFrst);
        tb.add(bPrv);
        tb.add(bNxt);
        tb.add(bLst);
        tb.add(badd);
        tb.add(bModi);
        tb.add(bDelete);
        tb.add(bOK);
        tb.add(bCancel);

        form = new JPanel(new BorderLayout());
        fp = new JPanel(new GridLayout(5, 2));

        lid = new JLabel("ID:", JLabel.RIGHT);
        lfn = new JLabel("*First Name:", JLabel.RIGHT);
        lln = new JLabel("*Last Name:", JLabel.RIGHT);
        lAFM = new JLabel("*AFM:", JLabel.RIGHT);
        lphone = new JLabel("*Phone:", JLabel.RIGHT);

        tid = new JTextField();
        tfn = new JTextField();
        tln = new JTextField();
        tAFM = new JTextField();
        tphone = new JTextField();

        fp.add(lid);
        fp.add(tid);
        fp.add(lfn);
        fp.add(tfn);
        fp.add(lln);
        fp.add(tln);
        fp.add(lAFM);
        fp.add(tAFM);
        fp.add(lphone);
        fp.add(tphone);

        form.add(fp);
        add(tb, BorderLayout.NORTH);
        bex.add(bExit);
        form.add(bex, BorderLayout.SOUTH);

        add(form);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    
    private void prepareForm() {
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "SELECT * FROM customers";
            rs = stmt.executeQuery(query);

            if (rs.first()) {
                data2Form();
                current = 1;
            } else {
                current = 0;
            }
            tid.setEditable(false);
            tfn.setEditable(false);
            tln.setEditable(false);
            tAFM.setEditable(false);
            tphone.setEditable(false);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void doFirst() {
        // first
        try {

            if (rs.first()) {
                data2Form();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void doLast() {
        // last
        try {

            if (rs.last()) {
                data2Form();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }        // TODO add your handling code here:
    }

    private void doPrevious() {
        // previous
        try {
            if (!rs.isFirst()) {
                if (rs.previous()) {
                    data2Form();
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }        // TODO add your handling code here:
    }

    private void doNext() {
        // next
        try {
            if (!rs.isLast()) {
                if (rs.next()) {
                    data2Form();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void doOK() {
        // OK
        form2DB();
        if (mode == 0) {
            doLast();
        }
        current += 1;
        tid.setEditable(false);
        tfn.setEditable(false);
        tln.setEditable(false);
        tAFM.setEditable(false);
        tphone.setEditable(false);

        bOK.setEnabled(false);
        bCancel.setEnabled(false);
    }

    private void doCancel() {
        if (current > 0) {
            try {
                doFirst();
                while (current != rs.getInt(1)) {
                    doNext();
                }
                data2Form();
            } catch (SQLException e) {
                System.out.println("doCancel: " + e.getMessage());
            }
        }
        tid.setEditable(false);
        tfn.setEditable(false);
        tln.setEditable(false);
        tAFM.setEditable(false);
        tphone.setEditable(false);
        bOK.setEnabled(false);
        bCancel.setEnabled(false);
    }

    private void doRegister() {
        // register
        mode = 0;
        if (current > 0) {
            try {
                current = rs.getInt(1);
            } catch (SQLException e) {
                System.out.println("doRegister: " + e.getMessage());
            }
        }

        tfn.setEditable(true);
        tln.setEditable(true);
        tAFM.setEditable(true);
        tphone.setEditable(true);

        space2Form();
        bOK.setEnabled(true);
        bCancel.setEnabled(true);
    }

    private void doModify() {
        // modify
        mode = 1;
        if (current > 0) {
            try {
                current = rs.getInt(1);
            } catch (SQLException e) {
                System.out.println("doRegister: " + e.getMessage());
            }
        }

        tfn.setEditable(true);
        tln.setEditable(true);
        tAFM.setEditable(true);
        tphone.setEditable(true);

        bOK.setEnabled(true);
        bCancel.setEnabled(true);
    }

    private void data2Form() {
        try {
            tid.setText(String.valueOf(rs.getInt("idc")));
            tfn.setText(rs.getString("firstname"));
            tln.setText(rs.getString("lastname"));
            tAFM.setText(rs.getString("AFM"));
            tphone.setText(rs.getString("phone"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void space2Form() {

        tid.setText(null);
        tfn.setText(null);
        tln.setText(null);
        tAFM.setText(null);
        tphone.setText(null);

    }

    private void form2DB() {
        try {
            if (mode == 0) { // register
                rs.moveToInsertRow();
            }

            rs.updateString("firstname", tfn.getText());
            rs.updateString("lastname", tln.getText());
            rs.updateString("phone", tphone.getText());
            rs.updateString("AFM", tAFM.getText());

            if (mode == 0) {
                rs.insertRow();
            } else { // modify
                rs.updateRow();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void doDelete() {
        pstmt = null;
        int id = 0;
        try {

            current = rs.getInt("idc");
            System.out.print(current);
            String change = "delete from customers where idc=?";
            PreparedStatement pstmt = con.prepareStatement(change, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pstmt.setInt(1, current);
            pstmt.executeUpdate();
            String query = "SELECT * FROM customers";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Get data at cursor
            }
            doPrevious();
            while (rs.previous()) {
                // Get data at cursor
            }
            doNext();
        } catch (SQLException e) {
            System.out.println("doDelete: " + e.getMessage());
        }
    }

    ;

    private void doExit() {
        
        setVisible(false);
    }


    private Connection con;
    private Statement stmt, pstmt;
    private ResultSet rs;
    private JToolBar tb;
    private JPanel form, fp, bex;
    private JLabel lid, lAFM, lfn, lln, lphone;
    private JTextField tid, tAFM, tfn, tln, tphone;
    private JButton bDelete, badd, bExit, bFrst, bPrv, bNxt, bLst, bOK, bCancel, bModi;

    private int current = 0;
    private int mode = 0;
}
