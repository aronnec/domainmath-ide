
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


package org.domainmath.gui.references;

import java.util.prefs.Preferences;
import org.domainmath.gui.MainFrame;


public class FileSaver {
    String file;
    private final MainFrame frame;
    public FileSaver(String f,MainFrame frame) {
        file =f;
        this.frame =frame;
        
    
    }
    public FileSaver(MainFrame frame) {
        this.frame =frame;
    }
    
    public void saveData(String data) {
        
        Preferences pr = Preferences.userNodeForPackage(frame.getClass());
        pr.put("Ref_list", data);                
    }
    
     private String getData() {
         Preferences pr = Preferences.userNodeForPackage(frame.getClass());
         return pr.get("Ref_list", null);       
     }
}
