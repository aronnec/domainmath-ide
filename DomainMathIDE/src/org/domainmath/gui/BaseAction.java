
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

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public  class BaseAction extends AbstractAction{

    public BaseAction(String name) {
        super(name);
    }

    public void setShortKey(int key_event, int action_event){
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(
                    key_event, action_event));
    }

    public void setToolTip(String text) {
        putValue(SHORT_DESCRIPTION, text);
    }

    public void setIcon(String path) {
       java.net.URL imgURL = getClass().getResource(path);
       
       ImageIcon icon = new ImageIcon(imgURL);
       putValue(SMALL_ICON,icon);
    }

   public void setEnabled(boolean enabled) {
       
   }
 
    public void actionPerformed(ActionEvent e) {

    }

}
