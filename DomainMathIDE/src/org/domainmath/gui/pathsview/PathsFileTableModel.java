

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
package org.domainmath.gui.pathsview;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.table.AbstractTableModel;
public final class PathsFileTableModel extends AbstractTableModel{
    private final String datafile;
    private  List data =Collections.synchronizedList(new ArrayList());
    private  List col =Collections.synchronizedList(new ArrayList());
    private int i;
   


    public PathsFileTableModel(String f) {
        
        datafile=f;
        col.add("Available Paths:");
      
        
    }

    
    @Override
    public int getRowCount() {
       try{
           i= data.size() / getColumnCount(); 
        }catch(Exception e) {
            
        }
       
       
       return i;
    }

    @Override
    public int getColumnCount() {
       return col.size();
    }

    @Override
    public String getColumnName(int i) {
        String c = "";
        if(i <=getColumnCount())
            c = (String)col.get(i);
        return c;
    }
    
    public Class getColClass(int i) {
        return String.class;
    }
    
    @Override
    public boolean isCellEditable(int r,int c) {
        return false;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       return  data.get((rowIndex*getColumnCount())+columnIndex);
    }

    
    public void refresh(){  
      data.clear();
 
      init();
     }
    public void init() {
        String line;
        
        try {
            FileInputStream fin = new FileInputStream(datafile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            try {
               while((line=br.readLine()) != null) {
                        StringTokenizer s2 = new StringTokenizer(line,";");
                        while(s2.hasMoreTokens()) {
                            data.add(s2.nextToken());
                       
                        }
                   
                }
                br.close();
            } catch (IOException ex) {
               ex.printStackTrace();
            }
            
            
        } catch (FileNotFoundException ex) {
            //ex.printStackTrace();
        }
        
        
    }

  
    
    
}
