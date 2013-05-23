
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
import java.io.File;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class RecentFilesOpenAction extends BaseAction{
        String title;
        String fileName;
    private final MainFrame frame;
    private final JMenu menu;
    public RecentFilesOpenAction(MainFrame frame,JMenu menu,String title,String fileName) {
        
        super(title);
        this.title = title;
        this.fileName = fileName;
        this.setToolTip(fileName);
        this.frame = frame;
        this.menu =menu;
        
    }

    @Override
    public void actionPerformed(ActionEvent action_event) {
        File file1=new File(fileName);
       if(!MainFrame.fileNameList.contains(file1.getAbsolutePath())) {

           frame.open(file1, MainFrame.FILE_TAB_INDEX);
           frame.setCurrentDirFileTab(file1.getParent()); 
           JMenuItem item = (JMenuItem) action_event.getSource();
           menu.remove(item);
        }else {
            System.out.println(file1.getAbsolutePath()+" already open!");
        }

    }
}
