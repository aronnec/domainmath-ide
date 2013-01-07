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


import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
import org.domainmath.gui.MainFrame;


public class PathsViewPanel extends JPanel {

   
    JTable table;
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/pathsview/resources/pathsview_en"); 
        
    private final JPopupMenu popup = new JPopupMenu();
    PopupActionListener popupActionListener;
    TableMouseListener tableMouseListener;
    private final JMenuItem openItem;
    private final JMenuItem addFolderItem;
    
    private final JMenuItem addSubFolderItem;
    private final JMenuItem removeItem;
    
    private final JMenuItem refreshItem;
    private final PathsFileTableModel model;
   
    private final String directory;
    private JToolBar pathsToolBar;
    private JButton openButton;
    private JButton addFolderButton;
    
    private JButton removeFolderButton;
    private JButton refreshButton;
    private ToolBarActionListener toolBarActionListener;
    
    
    public PathsViewPanel(String directory) {
       // super(new GridLayout(1, 0));
        super(new BorderLayout());
        table = new JTable();
        this.directory = directory;
        popupActionListener = new PopupActionListener();
        toolBarActionListener = new ToolBarActionListener();
        tableMouseListener = new TableMouseListener();
        model = new PathsFileTableModel(directory);
        model.init();
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        
        table.setRowHeight(20);
        
       
        openItem = new JMenuItem(bundle.getString("openItem.name"));
        addFolderItem = new JMenuItem(bundle.getString("addFolderItem.name"));
       
        addSubFolderItem = new JMenuItem(bundle.getString("addSubFolderItem.name"));
        removeItem = new JMenuItem(bundle.getString("removeItem.name"));
        
        refreshItem = new JMenuItem(bundle.getString("refreshItem.name"));
        
        popup.add(openItem);
        popup.addSeparator();
        popup.add(addFolderItem);
        
        popup.addSeparator();
        popup.add(addSubFolderItem);
        popup.add(removeItem);
       
        popup.add(refreshItem);
        
        openItem.setToolTipText(bundle.getString("openItem.tooltip"));
        addFolderItem.setToolTipText(bundle.getString("addFolderItem.tooltip"));
       
        addSubFolderItem.setToolTipText(bundle.getString("addSubFolderItem.tooltip"));
        removeItem.setToolTipText(bundle.getString("removeItem.tooltip"));
        
        refreshItem.setToolTipText(bundle.getString("refreshItem.tooltip"));
        
        openItem.addActionListener(popupActionListener);
        addFolderItem.addActionListener(popupActionListener);        
        addSubFolderItem.addActionListener(popupActionListener);
        removeItem.addActionListener(popupActionListener);       
        refreshItem.addActionListener(popupActionListener);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
        table.addMouseListener(tableMouseListener);
        
        table.add(popup);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        setUpToolBar();
        add(scrollPane,BorderLayout.CENTER);
      
    }

 
    private void setUpToolBar() {
        pathsToolBar = new JToolBar();
        pathsToolBar.setFloatable(false);
        pathsToolBar.setRollover(true);
        
        openButton = new JButton();
        addFolderButton =new JButton();
        removeFolderButton = new JButton();
        refreshButton = new JButton();
        
        openButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/open_16.png"))); 
        addFolderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/folder_add_16.png"))); 
        removeFolderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/folder_delete_16.png"))); 
        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/view-refresh.png"))); 
        
        openButton.setToolTipText(bundle.getString("openItem.tooltip"));
        addFolderButton.setToolTipText(bundle.getString("addFolderItem.tooltip"));
        removeFolderButton.setToolTipText(bundle.getString("removeItem.tooltip"));
        refreshButton.setToolTipText(bundle.getString("refreshItem.tooltip"));
        
        openButton.addActionListener(toolBarActionListener);
        addFolderButton.addActionListener(toolBarActionListener);
        removeFolderButton.addActionListener(toolBarActionListener);
        refreshButton.addActionListener(toolBarActionListener);
        
        pathsToolBar.add(openButton);
        pathsToolBar.add(addFolderButton);      
        pathsToolBar.add(removeFolderButton);
        pathsToolBar.add(refreshButton);
        
        
        add(pathsToolBar,BorderLayout.PAGE_START);
        
    }
    private void showPopup(MouseEvent e){
        if(e.isPopupTrigger())
            popup.show(e.getComponent(), e.getX(), e.getY());
     }

      public void addFolder() {
        JFileChooser fc = new JFileChooser();
        
        
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(this);
        
        fc.setMultiSelectionEnabled(false);
        
        String dir;
        String cmdAddPath;
        String savePath;
         if (returnVal == JFileChooser.APPROVE_OPTION) {
              dir =fc.getSelectedFile().getAbsolutePath();
              cmdAddPath = "addpath("+"'"+dir+"');";
              savePath   = "savepath();";
              MainFrame.octavePanel.evaluate(cmdAddPath);
              MainFrame.octavePanel.evaluate(savePath);
                MainFrame.octavePanel.evaluate("DomainMath_OctavePaths('"+directory+"');");
                reload();
              
         }
         
    }
      
   public void reload() {
          new DataTask().execute();
       }
    private void addSubFolder() {
         JFileChooser fc = new JFileChooser();
        
        
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(this);
        fc.setMultiSelectionEnabled(false);
        String dir;
        String cmdAddPath;
        String savePath;
         if (returnVal == JFileChooser.APPROVE_OPTION) {
              dir =fc.getSelectedFile().getAbsolutePath();
              cmdAddPath = "addpath(genpath("+"'"+dir+"'));";
              savePath   = "savepath();";
              MainFrame.octavePanel.evaluate(cmdAddPath);
              MainFrame.octavePanel.evaluate(savePath);
              MainFrame.octavePanel.evaluate("DomainMath_OctavePaths('"+directory+"');");
              reload();
               
              
              }
        
    }
    private class PopupActionListener implements ActionListener {
       private Path path;
        
        @Override
        public void actionPerformed(ActionEvent e) {
             JMenuItem source = (JMenuItem)(e.getSource());
            
             if(source.equals(openItem) ) {
                 if(table.getSelectedRow() >= 0)  {
                     try {
                            String dir =table.getValueAt(table.getSelectedRow(), 0).toString();
                            URI uri = Paths.get(dir).toUri();
                            Desktop desktop=Desktop.getDesktop();
                            desktop.browse(uri);
                        } catch (IOException | NullPointerException ex) {
                        }
               
                   
                 }
                 
             }else if(source.equals(addFolderItem)) {
                       addFolder();
                
             }else if(source.equals(addSubFolderItem)) {              
                    
                        addSubFolder();
             }else if(source.equals(removeItem)) {
                 if(table.getSelectedRow() >= 0)  {
                   path =Paths.get(directory).resolve(table.getValueAt(table.getSelectedRow(), 0).toString());
                   try {
                         String dir =table.getValueAt(table.getSelectedRow(), 0).toString();
                          
                        if(!dir.equals("")){
                            String rmPath ="rmpath("+"'"+dir+"');";
                            String savePath = "savepath();";
                            MainFrame.octavePanel.evaluate(rmPath);
                            MainFrame.octavePanel.evaluate(savePath);
                            
                            MainFrame.octavePanel.evaluate("DomainMath_OctavePaths('"+directory+"');");
                            reload();
                         
                    }else{
                        JOptionPane.showMessageDialog(null, "Please choose a path.");
                    }
                    }catch(Exception ex) {
                        JOptionPane.showMessageDialog(null, "Please choose a path.");
                    }
                   
                   
                 }
             }else if(source.equals(refreshItem)) {
                reload();
            }
         
        }
  
    }
    
     private  class ToolBarActionListener implements ActionListener {
        private Path path;

       

        @Override
       public void actionPerformed(ActionEvent e) {
             JButton source = (JButton)(e.getSource());
            
             if(openButton.equals(source) ) {
                 if(table.getSelectedRow() >= 0)  {
                     try {
                            String dir =table.getValueAt(table.getSelectedRow(), 0).toString();
                            URI uri = Paths.get(dir).toUri();
                            Desktop desktop=Desktop.getDesktop();
                            desktop.browse(uri);
                        } catch (IOException | NullPointerException ex) {
                        }
               
                   
                 }
                 
             }else if(addFolderButton.equals(source)) {
                       addFolder();
                
             }else if(removeFolderButton.equals(source)) {
                 if(table.getSelectedRow() >= 0)  {
                   path =Paths.get(directory).resolve(table.getValueAt(table.getSelectedRow(), 0).toString());
                   try {
                         String dir =table.getValueAt(table.getSelectedRow(), 0).toString();
                          
                        if(!dir.equals("")){
                            String rmPath ="rmpath("+"'"+dir+"');";
                            String savePath = "savepath();";
                            MainFrame.octavePanel.evaluate(rmPath);
                            MainFrame.octavePanel.evaluate(savePath);
                            
                            MainFrame.octavePanel.evaluate("DomainMath_OctavePaths('"+directory+"');");
                            reload();
                         
                    }else{
                        JOptionPane.showMessageDialog(null, "Please choose a path.");
                    }
                    }catch(Exception ex) {
                        JOptionPane.showMessageDialog(null, "Please choose a path.");
                    }
                   
                   
                 }
             }else if(refreshButton.equals(source)) {
                reload();
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
}
