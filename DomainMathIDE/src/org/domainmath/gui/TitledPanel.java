
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
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitledPanel extends JPanel{
    private final JLabel label;
    private final Font fontLabel;

    public TitledPanel(String name) {
      setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
       
          
        label =new JLabel(name);
        
        fontLabel =label.getFont();
        label.setFont(new Font(fontLabel.getFontName(),Font.BOLD,fontLabel.getSize()));
        label.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        
       this.setBorder(BorderFactory.createRaisedBevelBorder());
       
        add(label,BorderLayout.WEST);
    }

    public void setClose() {
        this.setVisible(false);
    }




  





}
