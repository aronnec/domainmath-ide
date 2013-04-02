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

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.domainmath.gui.MainFrame;

public class FileViewPanel extends JPanel {
  
    JTable table;
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/current_dir/resources/fileview_en"); 
        
    private final JPopupMenu popup = new JPopupMenu();
    PopupActionListener popupActionListener;
    TableMouseListener tableMouseListener;
    private final JMenuItem openItem;
    private final JMenuItem runItem;
    
    private final JMenuItem deleteItem;
    private final JMenuItem renameItem;
    private final JMenuItem duplicateItem;
    private final JMenuItem refreshItem;
    private final DirTableModel model;
    private final DirTableCellRender cellRender;
    private final String directory;
    private final JMenuItem octArchiveItem;
    private final String dynareOptions;
    private final String dynarePath;
    
    private final JMenuItem profilerItem;
    public FileViewPanel(String directory,String dynareOptions,String dynarePath) {
        super(new GridLayout(1, 0));
        table = new JTable();
        this.dynarePath = dynarePath;
        this.dynareOptions = dynareOptions;
        this.directory = directory;
        popupActionListener = new PopupActionListener();
        tableMouseListener = new TableMouseListener();
        model = new DirTableModel(Paths.get(directory));
        cellRender = new DirTableCellRender(table.getFont(),
                table.getBackground(),table.getForeground(),
                table.getSelectionBackground(),table.getSelectionForeground(),directory);
        
        table.setModel(model);
       // table.setAutoCreateColumnsFromModel(true);
       // table.setAutoCreateRowSorter(true);
        table.setDefaultRenderer(DirTableCellRender.class, cellRender);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRender);
        
       
        table.getTableHeader().setReorderingAllowed(false);
       
        table.setAutoscrolls(false);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(0);
        table.setRowHeight(20);
         
       
        openItem = new JMenuItem(bundle.getString("openItem.name"));
       // openInsideItem = new JMenuItem("Open Inside");
        runItem = new JMenuItem(bundle.getString("runItem.name"));
        octArchiveItem = new JMenuItem(bundle.getString("octArchiveItem.name"));
        deleteItem = new JMenuItem(bundle.getString("deleteItem.name"));
        renameItem = new JMenuItem(bundle.getString("renameItem.name"));
        duplicateItem = new JMenuItem(bundle.getString("duplicateItem.name"));
        profilerItem = new JMenuItem(bundle.getString("profilerItem.name"));
        refreshItem = new JMenuItem(bundle.getString("refreshItem.name"));
        
        popup.add(openItem);
      //  popup.add(openInsideItem);
        popup.add(runItem);
        popup.add(profilerItem);
        popup.add(octArchiveItem);
        popup.addSeparator();
        popup.add(deleteItem);
        popup.add(renameItem);
        popup.add(duplicateItem);
        popup.add(refreshItem);
        
        openItem.setToolTipText(bundle.getString("openItem.tooltip"));
       // openInsideItem.setToolTipText("Open inside");
        runItem.setToolTipText(bundle.getString("runItem.tooltip"));
        octArchiveItem.setToolTipText(bundle.getString("octArchiveItem.tooltip"));
        deleteItem.setToolTipText(bundle.getString("deleteItem.tooltip"));
        renameItem.setToolTipText(bundle.getString("renameItem.tooltip"));
        profilerItem.setToolTipText(bundle.getString("profilerItem.tooltip"));
        duplicateItem.setToolTipText(bundle.getString("duplicateItem.tooltip"));
        refreshItem.setToolTipText(bundle.getString("refreshItem.tooltip"));
        
       
        
        openItem.addActionListener(popupActionListener);
      //  openInsideItem.addActionListener(popupActionListener);
        runItem.addActionListener(popupActionListener);
        profilerItem.addActionListener(popupActionListener);
        octArchiveItem.addActionListener(popupActionListener);
        deleteItem.addActionListener(popupActionListener);
        renameItem.addActionListener(popupActionListener);
        duplicateItem.addActionListener(popupActionListener);
        refreshItem.addActionListener(popupActionListener);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
        table.addMouseListener(tableMouseListener);
        table.add(popup);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
      
    }

    private void refresh() {
        table.createDefaultColumnsFromModel();
        table.setDefaultRenderer(DirTableCellRender.class, cellRender);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRender);
    }
    public DirTableModel getModel() {
        return model;
    }

    private void showPopup(MouseEvent e){
        if(table.getSelectedRow() > -1 && e.isPopupTrigger()) {
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
     }

    private void open(Path path) {
    try {
           
            URI uri = path.toUri();
            Desktop desktop=Desktop.getDesktop();
            desktop.browse(uri);
        } catch (IOException ex) {
            System.err.println(ex);
            JOptionPane.showMessageDialog(table, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        } 
        
        
    }
   
  
    private class PopupActionListener implements ActionListener {
       private Path path;
        
        @Override
        public void actionPerformed(ActionEvent e) {
             JMenuItem source = (JMenuItem)(e.getSource());
             
             if(source.equals(openItem)) {
                 if(table.getSelectedRow() >= 0)  {
                   path =Paths.get(directory).resolve(table.getValueAt(table.getSelectedRow(), 0).toString());
                    open(path);
                   System.out.println("File: "+path.toString());
                   
                 }
             }
             /*else if(source.equals(openInsideItem)) {
                 if(table.getSelectedRow() >= 0)  {
                   path =Paths.get(directory).resolve(table.getValueAt(table.getSelectedRow(), 0).toString());
                    openInside(path);
                   System.out.println("File: "+path.toString());
                   
                 }
             }
             * 
             */
             else if(source.equals(runItem)) {
                 if(table.getSelectedRow() >= 0)  {
                   path =Paths.get(directory).resolve(table.getValueAt(table.getSelectedRow(), 0).toString());
                    runFile(path);
                   System.out.println("File: "+path.toString());
                   
                 }
             }else if(source.equals(deleteItem)) {              
                    if(table.getSelectedRow() >= 0)  {
                        path =Paths.get(directory).resolve(table.getValueAt(table.getSelectedRow(), 0).toString());
                        delete(path);
                        refresh();
                        model.refresh();
                        
                        System.out.println(path.toString());
                        
                    }
                 
             }else if(source.equals(renameItem)) {
                 if(table.getSelectedRow() >= 0)  {
                   path =Paths.get(directory).resolve(table.getValueAt(table.getSelectedRow(), 0).toString());
                   rename(path,false);
                   System.out.println("File: "+path.toString());
                   refresh();
                   model.refresh();
                 }
             }else if(source.equals(duplicateItem)) {
                 if(table.getSelectedRow() >= 0)  {
                   path =Paths.get(directory).resolve(table.getValueAt(table.getSelectedRow(), 0).toString());
                   rename(path,true);
                  
                   System.out.println("File: "+path.toString());
                   model.refresh();
                 }
             }else if(source.equals(refreshItem)) {
                 refresh();
                 model.refresh();
             }else if(source.equals(profilerItem)) {
                if(table.getSelectedRow() >= 0)  {
                   path =Paths.get(directory).resolve(table.getValueAt(table.getSelectedRow(), 0).toString());
                     String name =path.getFileName().toString();
                        String f =name.substring(0, name.lastIndexOf("."));
                     String ext =name.substring(name.lastIndexOf("."));
             
            if(ext.equalsIgnoreCase(".m")){
                MainFrame.octavePanel.evaluate("profile on");
                 MainFrame.octavePanel.evaluate(f+";");
                 MainFrame.octavePanel.evaluate("profile off");
                 MainFrame.octavePanel.evaluate("profshow (profile ("+Character.toString('"')+"info"+Character.toString('"')+"), 20);");
                 MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+MainFrame.parent_root+"DomainMath_OctaveVariables.dat',whos);");
         
                   System.out.println("File: "+path.toString());
                   
                 }
           
            
             }else if(source.equals(octArchiveItem)) {
                 Path p= Paths.get(directory);
                 String f;
               
                 if(p.getNameCount() ==1) {
                     onlyOneFile(p);
                   
                 }else if(p.getNameCount() > 1){
                     f="[";
                     String _c =Character.toString('"');
                    try {
                        DirectoryStream<Path> stream = Files.newDirectoryStream(p);
                        
                       
                        for (Path file: stream) {
                       
                         f+=_c+file.getFileName()+_c+";";
                      }
                     f+="]";
                     MainFrame.octavePanel.evaluate("tar("+_c+p.getFileName()+".tar"+_c+","+f+");");
                     MainFrame.octavePanel.evaluate("gzip("+_c+p.getFileName()+".tar"+_c+");");
                     model.refresh();
                    } catch (IOException ex) {
                        Logger.getLogger(FileViewPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                     
                 }
                 
             }
         
             }}

        
        private void delete(Path path) {
            int opt =JOptionPane.showConfirmDialog(table, "Do you want to delete "+path.getFileName().toString(), "Information",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(opt == JOptionPane.YES_OPTION)  {
                try {
                  Files.delete(path);
                }catch (IOException ex) {
                  System.err.println(ex);
                  JOptionPane.showMessageDialog(null, ex.toString(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
       }

        private void rename(Path path,boolean duplicate) {
            if(!duplicate) {
                String name= JOptionPane.showInputDialog(table, "Enter New File Name:");
                String file = table.getValueAt(table.getSelectedRow(),0).toString();
                if(file != null) {
                   String ext=file.substring(file.indexOf("."));
                Path p =Paths.get(directory).resolve(name+ext);
                try {
                    Files.move(path,p , StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString(),"Error", JOptionPane.ERROR_MESSAGE);
                
                } 
                }
                
            }else {
                
                try {
                    String name= JOptionPane.showInputDialog(table, "Enter suffix");
                    String file = table.getValueAt(table.getSelectedRow(),0).toString();
                    if(file != null) {
                         Path p =Paths.get(directory).resolve(name+file);
                        Files.move(path,p , StandardCopyOption.REPLACE_EXISTING);
                    }
                   
                } catch (HeadlessException | IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString(),"Error", JOptionPane.ERROR_MESSAGE);
                
                }
            }
            
        }

        private void setUpDynare(Path path) {
              if(dynareOptions!=null || dynarePath != null) {
                    File f = new File(dynarePath+File.separator+"dynare_m.exe");
                    String c = "system('"+f.getAbsolutePath()+" "+path.toString()+" "+dynareOptions+"');";
                    System.out.println(c);
                     MainFrame.octavePanel.evaluate(c);
                     MainFrame.octavePanel.evaluate("disp('---------------------------------------------------')");
                     String p = path.getFileName().toString();
                     String file =p.substring(0, p.indexOf("."))+".m" ;
                     MainFrame.octavePanel.evaluate("run("+"'"+path.getParent().resolve(Paths.get(file)).toString() +"'"+");");
              
                     MainFrame.octavePanel.evaluate("whos");
                     MainFrame.octavePanel.eval("DomainMath_OctaveVariables('"+MainFrame.parent_root+"DomainMath_OctaveVariables.dat',whos);");
         
                }else if( dynarePath != null){
                     File f = new File(dynarePath+File.separator+"dynare_m.exe");
                    String c2="system('"+f.getAbsolutePath()+" "+path.toString()+" "+"noclearall"+"');";
                    System.out.println(c2);
                    MainFrame.octavePanel.evaluate(c2);
                    MainFrame.octavePanel.evaluate("disp('---------------------------------------------------')");
                    String p = path.getFileName().toString();
                     String file =p.substring(0, p.indexOf("."))+".m" ;
                     MainFrame.octavePanel.evaluate("run("+"'"+path.getParent().resolve(Paths.get(file)).toString() +"'"+");");
                     MainFrame.octavePanel.evaluate("whos");
                      MainFrame.octavePanel.eval("DomainMath_OctaveVariables('"+MainFrame.parent_root+"DomainMath_OctaveVariables.dat',whos);");
         
                }
        }
        private void runFile(Path path) {
            
            String name =path.getFileName().toString();
            String ext =name.substring(name.lastIndexOf("."));
             
            if(ext.equalsIgnoreCase(".m")){
                
                 MainFrame.octavePanel.evaluate("run("+"'"+path.toString()+"'"+");");
                 MainFrame.octavePanel.commandArea.append("run("+"'"+path.toString()+"'"+");\n");
                  MainFrame.octavePanel.eval("DomainMath_OctaveVariables('"+MainFrame.parent_root+"DomainMath_OctaveVariables.dat',whos);");
                  
            }else if(ext.equalsIgnoreCase(".pl")) {
                MainFrame.octavePanel.evaluate("perl("+"'"+path.toString()+"'"+");");
                MainFrame.octavePanel.commandArea.append("perl("+"'"+path.toString()+"'"+");\n");
            }
            else if(ext.equalsIgnoreCase(".py")) {
                MainFrame.octavePanel.evaluate("python("+"'"+path.toString()+"'"+");");
                MainFrame.octavePanel.commandArea.append("python("+"'"+path.toString()+"'"+");\n");
            }
            else if(ext.equalsIgnoreCase(".mod")) {
                setUpDynare(path);
            }else if(ext.equalsIgnoreCase(".dyn")) {
                setUpDynare(path);
            }
        }
       
    }
    
    private class TableMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showPopup(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
    
    public void onlyOneFile(Path p) {
        String f;
        String _c =Character.toString('"');
         try {
              DirectoryStream<Path> stream = Files.newDirectoryStream(p);
              for (Path file: stream) {
                  f=_c+file.getFileName()+_c;
                  MainFrame.octavePanel.evaluate("tar("+_c+p.getFileName()+".tar"+_c+","+f+");");
                  MainFrame.octavePanel.evaluate("gzip("+_c+p.getFileName()+".tar"+_c+");");
                 
               }
               refresh();
                      
        } catch (IOException ex) {
            Logger.getLogger(FileViewPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
