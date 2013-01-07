package org.domainmath.gui.packages.image;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


public class LoadImagePanel extends JPanel {
          
    BufferedImage img;

    @Override
    public void paint(Graphics g) {
        g.drawImage(img, 10, 10, null);
    }

    public LoadImagePanel(File file) {
       try {
           img = ImageIO.read(file);
       } catch (IOException e) {
       }

    }

    @Override
    public Dimension getPreferredSize() {
        if (img == null) {
             return new Dimension(100,100);
        } else {
           return new Dimension(img.getWidth(null), img.getHeight(null));
       }
    }

    public static void main(String[] args) {

        JFrame f = new JFrame("Load Image Sample");
            
        f.addWindowListener(new WindowAdapter(){
            @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

        f.add(new LoadImagePanel(null));
        f.pack();
        f.setVisible(true);
    }
}
