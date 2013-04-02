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

package org.domainmath.gui.current_dir;


import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    private final String path;
    private final Image image;
    private final String dynareOptions;
    private final String dynarePath;
    
    public Main(String path,Image image,String dynareOptions,String dynarePath) {
        this.path = path;
        this.dynarePath =dynarePath;
        this.image =image;
        this.dynareOptions=dynareOptions;
        System.out.println("Dynare:"+dynareOptions);
    }
    
    public void show() {
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
              
             
                 JFrame frame = new JFrame("FileView");
                
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
               
                 FileViewPanel newContentPane = new FileViewPanel(path,dynareOptions,dynarePath);
                    newContentPane.setOpaque(true);
                    frame.setContentPane(newContentPane);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setIconImage(image);
                    frame.setVisible(true);
                    newContentPane.getModel().init();
            }

        });
    }
}
