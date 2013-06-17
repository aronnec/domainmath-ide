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

package org.domainmath.gui.dataview;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import org.domainmath.gui.MainFrame;

public class DataViewPanel extends JPanel {
  
    JTable table;
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/dataview/resources/dataview_en"); 
    private final String directory;
    public String jar_path = "javaaddpath('"+System.getProperty("user.dir")+File.separator+"Results.jar');";   
 
    public List data =Collections.synchronizedList(new ArrayList());
    private JToolBar datViewToolBar;
    private JButton printButton;
    private JButton refreshButton;
    private final ToolBarActionListener toolBarActionListener;
    private final DataViewFileTableModel model;
    private final TableMouseListener tableMouseListener;
    private final String title;

    
    public DataViewPanel(String title,String directory) {
        super(new BorderLayout());
        tableMouseListener = new TableMouseListener();
        this.directory = directory;
        this.title=title;
        toolBarActionListener = new ToolBarActionListener();
        
        model = new DataViewFileTableModel(directory);
        table = new JTable();
        table.setModel(model);
     
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         table.setColumnSelectionAllowed(true);
         table.setRowSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        table.addMouseListener(tableMouseListener);
        table.setRowHeight(20);
        final JScrollPane scrollPane = new JScrollPane(table);
        table.setDragEnabled(true);
        setUpToolBar();
        add(scrollPane,BorderLayout.CENTER);
         
      
    }
 private void setUpToolBar() {
        datViewToolBar = new JToolBar();
        datViewToolBar.setFloatable(false);
        datViewToolBar.setRollover(true);
        
        
        printButton = new JButton("");
        
        refreshButton = new JButton("");
        
      
        printButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-print.png"))); 
       
        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/view-refresh.png"))); 
        
        
        printButton.setToolTipText(bundle.getString("printItem.tooltip"));
        
        refreshButton.setToolTipText(bundle.getString("refreshItem.tooltip"));
        
       
        printButton.addActionListener(toolBarActionListener);
        refreshButton.addActionListener(toolBarActionListener);
        
        
        datViewToolBar.add(printButton);
       
       
        datViewToolBar.add(refreshButton);
        add(datViewToolBar,BorderLayout.PAGE_START);
        
    }
  

   
        public void reload() {
          new DataTask().execute();
       }
  
     private  class ToolBarActionListener implements ActionListener {
        private Path path;

       

        @Override
       public void actionPerformed(ActionEvent e) {
             JButton source = (JButton)(e.getSource());
            
             if(printButton.equals(source) ) {
                 printTable();
             }else if(refreshButton.equals(source)) {
                reload();
            }
    }

        private void printTable() {
            try {
                if (! table.print()) {
                System.err.println("User cancelled printing");
            }
            } catch (java.awt.print.PrinterException e) {
                    System.err.format("Cannot print %s%n", e.getMessage());
            }


        }
  
 }
    
    private class DataTask extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() {
            int delay = 100; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
                @Override
            public void actionPerformed(ActionEvent evt) {
               
                model.refresh();
                model.fireTableStructureChanged();
                model.fireTableDataChanged();
       
                table.repaint();
                
            }
        };
         javax.swing.Timer t= new javax.swing.Timer(delay, taskPerformer);
        t.setRepeats(false);   
       t.start();
            return null;
            
        }

        
        @Override
       protected void done() {
           try {
                 int delay = 100; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
                @Override
            public void actionPerformed(ActionEvent evt) {
               
                model.refresh();
                model.fireTableStructureChanged();
                model.fireTableDataChanged();
       
                table.repaint();
                
            }
        };
        javax.swing.Timer t= new javax.swing.Timer(delay, taskPerformer);
        t.setRepeats(false);   
       t.start();     
           } catch (Exception ignore) {
               
           }
       }

      
    }
    
    
     private class TableMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2) {
                int r=table.getSelectedRow();
                int c=table.getSelectedColumn();
                 String variable = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
                 
                 
                
                 
                  if(!variable.contains("'") ){
                     if(r>=0 && c>=0) {
                                 if(title.contains("<") && title.contains(">")) {
                                    String parent_var_class = title.substring(title.indexOf(" "), title.indexOf(">"));
                                    String parent_var_name = title.substring(0, title.indexOf("<"));
                                    
                             
                             switch (parent_var_class.trim()) {
                                 
                                 // selected variable is a member of a structure.
                                 case "struct":
                                     {
                                         System.out.println(title);
                                         String child = table.getValueAt(table.getSelectedRow(), 0).toString();
                                         String childType = table.getValueAt(table.getSelectedRow(), 1).toString();
                                         processStruct(parent_var_name,child,childType);
                                         break;
                                     }
                                 
                                 // selected variable is a member of a structure array.
                                 case "struct-array":
                                     {
                                          System.out.println(title);
                                          String child = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
                                          processStructArray(parent_var_name, child,table.getSelectedRow(),table.getSelectedColumn());
                                         break;
                                     }
                                     
                                 // selected variable is a member of a cell.
                                 case "cell":
                                     {
                                          System.out.println(title);
                                          String child = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
                                          processCell(parent_var_name, child,table.getSelectedRow(),table.getSelectedColumn());
                                         break;
                                     }
                                 
                                     
                             }
                        }
                    } 
                  }
                   if(variable.startsWith("'")) {
                       MainFrame.octavePanel.evaluate(jar_path);
                        MainFrame.octavePanel.evaluate("ob= javaObject("+Character.toString('"')+"ResultsFrame"+Character.toString('"')+","+
                                Character.toString('"')+Character.toString('"')+");");
                        MainFrame.octavePanel.evaluate("ob.appendText(disp("+variable+"));");
                 }  

            }
        }

        
        @Override
        public void mousePressed(MouseEvent e) {
            
           
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
           
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        private void processStruct(String parent,String child,String childType) {
             MainFrame.octavePanel.evaluate("DomainMath_OctaveDataView('"+MainFrame.log_root+parent+"."+child+".dat',"+parent+"."+child+");");
             
             if(childType.contains("struct<") ||childType.contains("cell<") || childType.contains("struct-array<")) {
                

                String t = parent+"."+child+getDataLabel(childType); 
                DataViewFrame main =new DataViewFrame(t,MainFrame.log_root+parent+"."+child+".dat");
             }else{
                 String t = parent+"."+child; 
                DataViewFrame main =new DataViewFrame(t,MainFrame.log_root+parent+"."+child+".dat");
             }
             
        }

        private void processStructArray(String parent, String child,int r,int c) {
            MainFrame.octavePanel.evaluate("DomainMath_OctaveDataView('"+MainFrame.log_root+parent+"("+(r+1)+","+(c+1)+").dat',"+parent+"("+(r+1)+","+(c+1)+"));");
             

                
            String t = parent+"("+(r+1)+","+(c+1)+")"; 
            String _title =parent+"("+(r+1)+","+(c+1)+")"+getDataLabel(child); 
                DataViewFrame main =new DataViewFrame(_title,MainFrame.log_root+t+".dat");
        }
 
        private String getDataLabel(String child) {
            String s = child.substring(0, child.indexOf("<"));
                String s2 = child.substring( child.indexOf("<"), child.length());
                String s3 = s2.substring(0, s2.indexOf(">"))+" "+s+">";
             return s3;
        }

        private void processCell(String parent, String child,int r,int c) {
            MainFrame.octavePanel.evaluate("DomainMath_OctaveDataView('"+MainFrame.log_root+parent+"{"+(r+1)+","+(c+1)+"}.dat',"+parent+"{"+(r+1)+","+(c+1)+"});");
             

                
            String t = parent+"{"+(r+1)+","+(c+1)+"}"; 
            String _title =parent+"{"+(r+1)+","+(c+1)+"}"+getDataLabel(child); 
                DataViewFrame main =new DataViewFrame(_title,MainFrame.log_root+t+".dat");
        }
    }
   
     public void d(String text,String value) {
         System.out.println(text+":"+value);
     }
}
