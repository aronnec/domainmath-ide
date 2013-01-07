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

package org.domainmath.gui.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

public class AutoCompleteListCellRenderer extends JLabel implements ListCellRenderer  {
    private Object value;
    private final Color bgColor;
    private final Color fgColor;
    private final Color bgColorSelection;
    private final Color fgColorSelection;

    public AutoCompleteListCellRenderer(Font font,Color bgColor,Color fgColor,Color bgColorSelection,Color fgColorSelection) {
        this.bgColor=bgColor;
        this.fgColor=fgColor;
        this.bgColorSelection=bgColorSelection;
        this.fgColorSelection=fgColorSelection;
         setBorder(new EmptyBorder(1, 1, 1, 1));
        setFont(font);
        //setBackground(bgColor);
        setOpaque(true);
    }

    
    public Object getCellEditorValue() {
        return value;
    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        this.value = value;
        ImageIcon icon;
      
            java.net.URL imgURL = getClass().getResource("ftn.gif");
            java.net.URL imgURL2 = getClass().getResource("private.gif");
            icon = new ImageIcon(imgURL);
        ImageIcon icon2 = new ImageIcon(imgURL2);
            String text = value.toString();
            if(text.startsWith("_")) {
                setIcon(icon2);
            setText(value.toString());
            }else {
                setIcon(icon);
            setText(value.toString());
            }
            
               
           
       
        
        if(isSelected || cellHasFocus)
        {
            setBackground(bgColorSelection);
            setForeground(fgColorSelection);
           
        } else
        {
            setBackground(bgColor);
            setForeground(fgColor);
        }
       this.setToolTipText(value.toString());
        return this;
    }
    
}
