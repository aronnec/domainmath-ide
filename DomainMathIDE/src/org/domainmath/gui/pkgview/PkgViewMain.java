/*
 * Copyright (C) 2012 Vinu K.N
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.domainmath.gui.pkgview;

import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.domainmath.gui.MainFrame;

public class PkgViewMain {
    private final String path;
    private final Image image;
    private final MainFrame frame;
     public PkgViewMain(MainFrame frame,String path,Image image) {
        this.path = path;
        this.image =image;
        this.frame=frame;
    }
    
    public  void show() {
        JFrame.setDefaultLookAndFeelDecorated(true);
       
        try {
           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex){
            System.err.println(ex);
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                 JFrame f = new JFrame("Packages");
                
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 PkgViewPanel newContentPane = new PkgViewPanel(path,frame);
                    newContentPane.setOpaque(true);
                    f.setContentPane(newContentPane);
                    f.pack();
                    f.setLocationRelativeTo(null);
                    f.setIconImage(image);
                    f.setVisible(true);
               
               
               
            }

        });
    }
}
