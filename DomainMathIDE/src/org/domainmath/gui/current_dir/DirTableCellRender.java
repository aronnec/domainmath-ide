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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableCellRenderer;

public class DirTableCellRender extends JLabel
    implements TableCellRenderer {

    private final String p;
    private final Color bgColor;
    private final Color fgColor;
    private final Color bgColorSelection;
    private final Color fgColorSelection;
   
    public DirTableCellRender(Font font,Color bgColor,Color fgColor,Color bgColorSelection,Color fgColorSelection, String p) {
        this.p = p;
        this.bgColor=bgColor;
        this.fgColor=fgColor;
        this.bgColorSelection=bgColorSelection;
        this.fgColorSelection=fgColorSelection;
        setBorder(new EmptyBorder(1, 1, 1, 1));
        setFont(font);
        setBackground(bgColor);
        setOpaque(true);
    }

    public Object getCellEditorValue() {
        return p+File.separator+getText();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        File file = new File(p+File.separator+value);
        setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
        setText(FileSystemView.getFileSystemView().getSystemDisplayName(file));
        if(isSelected || hasFocus)
        {
            setBackground(bgColorSelection);
            setForeground(fgColorSelection);
           
        } else
        {
            setBackground(bgColor);
            setForeground(fgColor);
        }
        setToolTipText(file.getAbsolutePath());
        return this;
    }
}
