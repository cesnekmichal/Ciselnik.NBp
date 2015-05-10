/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ciselnik_dlouheho_drivi.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import unidataz.laf.UniLookAndFeel;

/**
 *
 * @author Michal
 */
public class ImageUtil {
    
    public static void main(String[] args) {
        JDialog jDialog = new JDialog();
        jDialog.setLayout(new GridLayout(1,1));
        JButton btn = new JButton("Tla49tko");
        setIcon(btn,6);
        jDialog.add(btn);
        jDialog.setVisible(true);
    }
    
    public static void setIcon(AbstractButton button, int number){
        button.setIcon(unidataz.util.ImageUtil.toImageIcon(drawImage(number)));        
    }
    
    private static BufferedImage drawImage(int number){
        String s = number+"";
        int x = 22;
        int y = 16;
        int f = 12;
        BufferedImage img = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(Font.BOLD,f));
        g = (Graphics2D) UniLookAndFeel.setAntialiasingFont(g);
        int width = g.getFontMetrics(g.getFont()).stringWidth(s);
        g.drawString(s, x-width-((x-width)/2)+1, f);
        g.dispose();
        return img;
    }
}
