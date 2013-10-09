/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Robby
 */
public class DrawLine extends JPanel{
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        g.drawLine(width/2, 0, width/2, height);
        
    }
    
}
