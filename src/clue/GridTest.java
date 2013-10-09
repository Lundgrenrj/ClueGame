/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

/**
 *
 * @author Robby
 */
public class GridTest {

    public static void main(String[] args) throws IOException {


        JFrame frame = new JFrame("Lay Out My Components in a Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel panel = new JPanel(new GridLayout(25, 24, -1, -1));
        //panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        DefaultListModel listModel = new DefaultListModel();


        JList list1 = new JList(listModel);

        for (int i = 0; i < (25 * 24); i++) {
            final JLabel label = new JLabel("" + i);
            listModel.addElement(label);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }


        Object test1 = new Object();
        test1 = listModel.getElementAt(40);
        JLabel test2 = (JLabel) test1;
        test2.setBackground(Color.red);
        ArrayList<Integer> kitchen = new ArrayList<Integer>();
        
        kitchen.add(0);
        kitchen.add(1);
        kitchen.add(2);
        kitchen.add(3);
        kitchen.add(4);
        kitchen.add(5);
        kitchen.add(6);
        kitchen.add(24);
        kitchen.add(25);
        kitchen.add(26);
        kitchen.add(27);
        kitchen.add(28);
        kitchen.add(29);
        kitchen.add(30);
        kitchen.add(48);
        kitchen.add(49);
        kitchen.add(50);
        kitchen.add(51);
        kitchen.add(52);
        kitchen.add(53);
        kitchen.add(54);
        kitchen.add(72);
        kitchen.add(73);
        kitchen.add(74);
        kitchen.add(75);
        kitchen.add(76);
        kitchen.add(77);
        kitchen.add(78);
        
        for (int i = 0; i < (25 * 24); i++) {

            Object test3 = new Object();
            test3 = listModel.getElementAt(i);
            JLabel test4 = (JLabel) test3;

            if (myCoords.) {
                test4.setText("");
                test4.setBackground(Color.red);
                test4.setOpaque(true);
                panel.add(test4);
            } else {
                panel.add(test4);

            }
        }




        frame.add(panel);
        frame.setSize(768, 768);
        frame.setVisible(true);

    }
}
