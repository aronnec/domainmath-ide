
/*
 * Copyright (C) 2011 Vinu K.N
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

package org.domainmath.gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JOptionPane;








/**
 *
 * @author Vinu K.N
 */
public class DocumentAction extends BaseAction{
        String title;
        String fileName;
    public DocumentAction(String title,String fileName) {
        
        super(title);
        this.title = title;
        this.fileName = fileName;
        this.setToolTip(title);
        
    }

    @Override
    public void actionPerformed(ActionEvent action_event) {
           File file = new File(fileName);
           System.out.println(fileName);
        try {
            Desktop desktop=Desktop.getDesktop();
          
         
            
            desktop.open(file);
            
        } catch (Exception ioe) {
            //ioe.printStackTrace();
            JOptionPane.showMessageDialog(null, file.getAbsolutePath() + " doesn't exist");
        }
    }
}
