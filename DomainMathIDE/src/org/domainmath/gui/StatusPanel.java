
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


import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class StatusPanel extends JPanel{
    private final JLabel status_label;
    public static StatusPanel STATUS_PANEL;

    public StatusPanel() {
        setLayout(new BorderLayout());
         status_label = new JLabel("Ready");
        status_label.setBorder(BorderFactory.createEmptyBorder(3, 5, 4,0 ));
        this.setBorder(BorderFactory.createEtchedBorder());
       add(status_label,BorderLayout.PAGE_END);
    }

}
