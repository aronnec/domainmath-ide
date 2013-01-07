
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


package org.domainmath.gui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;


public class TitledPanelContainer{

    
    
    
   
    private JComponent com;
    JPanel panel = new JPanel();

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
TitledPanel PANEL;
    public TitledPanelContainer(String name) {
        PANEL =new TitledPanel(name);
    }



    public JPanel add(JComponent com) {
        setCom(com);
        
        panel.setLayout(new BorderLayout());
        panel.add(PANEL,BorderLayout.PAGE_START);
        
        //panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        com.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
        panel.add(getCom(),BorderLayout.CENTER);
        
        return panel;
    }

     public JComponent getCom() {
        return com;
    }

    public void setCom(JComponent com) {
        this.com = com;
    }
    public void remove(JComponent com) {
        panel.remove(com);
    }

}
