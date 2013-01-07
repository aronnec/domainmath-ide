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

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;

public class DirTableModel extends AbstractTableModel {

    private final List data;
    private final List col;
    private DirectoryStream<Path> stream;
    private final Path path;
   

    public DirTableModel(Path path) {
        this.path =path;
        data = Collections.synchronizedList(new ArrayList());
        col = Collections.synchronizedList(new ArrayList());
        setCol();
       
    }

    private void setCol() {
        col.add("Name");
        col.add("Created");
        col.add("Last Access");
        col.add("Last Modified");
        col.add("Size");
        col.add("Status");
        col.add("Description");
    }
    
    @Override
    public String getColumnName(int i) {
        String c = "";
        if(i <= getColumnCount())
        {
            c = (String)col.get(i);
        }
        return c;
    }

    @Override
    public int getRowCount() {
        return data.size() / getColumnCount();
    }

    @Override
    public int getColumnCount() {
        return col.size();
    }

    public void refresh(){  
      data.clear();
      col.clear();
      setCol();
      init();
   }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex * getColumnCount() + columnIndex);
    }

    public void init(){ 
        String d = "";
    
        try {
            stream = Files.newDirectoryStream(path);
            for(Path file:stream) {
                 BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                    FileSystemView v = FileSystemView.getFileSystemView();
                    String s = v.getSystemTypeDescription(file.toFile());
                    if(!attr.isDirectory()) {
                        if(Files.isHidden(file)) {
                            d = "Hidden";
                            if(!Files.isReadable(file)){
                                d = "Hidden,Not Readable";
                            } else if(!Files.isWritable(file)){
                                d = "Hidden,Read Only";
                            }
                        } else
                        if(!Files.isWritable(file)){
                            d = "Read Only";
                        }
                        
                        data.add(file.toFile().getName());
                        data.add(attr.creationTime());
                        data.add(attr.lastAccessTime());
                        data.add(attr.lastModifiedTime());
                        data.add((attr.size() / 1024)+" KB");
                        data.add(d);
                        data.add(s);
                    }
            }
        }catch (Exception ex) {
            System.err.println(ex);
        }
    }

   
    
}
