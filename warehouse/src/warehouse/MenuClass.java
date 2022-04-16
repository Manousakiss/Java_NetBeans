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
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Manousakis
 */
public class MenuClass extends JFrame {

    public MenuClass() {
        initComponents();
        connect2DB();
    }
    
    private void connect2DB() {
        // check for the driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            String msg = "The com.mysql.cj.jdbc.Driver is missing\n"
                    + "install and rerun the application";
            JOptionPane.showMessageDialog(this, msg, this.getTitle(), JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // connect to db
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehousedb?useSSL=false", "root", "root");
        } catch (SQLException e) {
            String msg = "Error Connecting to Database:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, msg, this.getTitle(), JOptionPane.ERROR_MESSAGE);
        }

    }

    private void initComponents() {
        setTitle("Invoice Application 2021");
        p=new JPanel();

        /* orizw to menubar */
        mb = new JMenuBar();

        /* bazw ta menus mazi me ta items tous */
 /*  files */
        filez = new JMenu("Files");
        filez1 = new JMenuItem("Inventory");
        filez1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new file_inv(con).setVisible(true);
            }
        });
        filez2 = new JMenuItem("Customers");
        filez2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new file_cus(con).setVisible(true);
            }
        });
        filez3 = new JMenuItem("Exit");
        filez3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            closeJDBC(rs, stmt, con);
            System.exit(0);
            }
        });
        /*orders */
        orderz = new JMenu("Οrders");
        orderz1 = new JMenuItem("Place Order");
        orderz1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Place_o(con).setVisible(true);
                } catch (SQLException g) {
                    System.out.println(g.getMessage());
                }
            }
        });

        /*reports */
        reportz = new JMenu("Reports");
        reportz1 = new JMenuItem("Customers");
        reportz1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Pr_cus(con).setVisible(true);
            }
        });
        reportz2 = new JMenuItem("Inventory");
        reportz2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Pr_inv(con).setVisible(true);
            }
        });

        reportz3 = new JMenuItem("INVOICE");
        reportz3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Pr_Invoice(con).setVisible(true);
            }
        });

        /*help */
        help = new JMenu("Help");
        help1 = new JMenuItem("About");
        help1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new about().setVisible(true);
                
            }
        });
        
        text = new JLabel("Welcome to the Invoice app. Select one option from the list above to get started.");

        mb.add(filez);
        mb.add(orderz);
        mb.add(reportz);
        mb.add(help);
        filez.add(filez1);
        filez.add(filez2);
        filez.add(filez3);
        orderz.add(orderz1);
        reportz.add(reportz1);
        reportz.add(reportz2);
        reportz.add(new JSeparator());
        reportz.add(reportz3);
        help.add(help1);
        
        mb.setLayout(new FlowLayout(FlowLayout.LEFT)); 
        add(mb,BorderLayout.NORTH);
        p.setPreferredSize(new Dimension(600, 100));
        p.add(text);
        add(p);
        
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    
        public void closeJDBC(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                if (!stmt.isClosed()) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        new MenuClass().setVisible(true);
    }
    private JPanel p;
    private JLabel text;
    private JMenuBar mb;
    private JMenu filez, orderz, reportz, help;
    private JMenuItem filez1, filez2, filez3;
    private JMenuItem orderz1;
    private JMenuItem reportz1, reportz2, reportz3;
    private JMenuItem help1;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
}
