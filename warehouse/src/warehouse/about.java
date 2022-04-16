/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import java.awt.Dimension;
import javax.swing.*;

/**
 *
 * @author Manousakis
 */
public class about extends JDialog {
    public about(){
       initComponents();
    }

    
    private void initComponents(){
        lb=new JLabel("(c) Γιώργος Μανουσάκης 2021",JLabel.CENTER);
        add(lb);
        setPreferredSize(new Dimension(400, 100));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    private JLabel lb;
    
}
