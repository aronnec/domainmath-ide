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


package org.domainmath.gui.pkgview;

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
import org.domainmath.gui.pkg.*;

public class PkgViewPanel extends JPanel {
  
    JTable table;
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/pkgview/resources/pkgview_en"); 
        
    private final JPopupMenu popup = new JPopupMenu();
    PopupActionListener popupActionListener;
    TableMouseListener tableMouseListener;
    private final JMenuItem openItem;
    private final JMenuItem loadItem;
    private final JMenuItem unLoadItem;
    private final JMenuItem loadAllItem;
    private final JMenuItem unLoadAllItem;
    private final JMenuItem buildItem;
    private final JMenuItem refreshItem;
    private final DataFileTableModel model;
   
    private final String directory;
    private JToolBar pkgViewToolBar;
    private JButton openButton;
    private JButton loadButton;
    private JButton unloadButton;
    private JButton refreshButton;
    private ActionListener toolBarActionListener;
    private JButton installButton;
    private JButton unInstallButton;
    private final JMenuItem updateItem;
    private final JMenuItem generateDocItem;
    private final JMenuItem reBuildItem;
    private final JMenuItem reBuildAllItem;
    private final JMenuItem prefixDirItem;
    private final JMenuItem installItem;
    private final JMenuItem unInstallItem;
    private final JMenuItem unInstallAllItem;
    private final MainFrame frame;
   
    
    public PkgViewPanel(String directory,MainFrame frame) {
       // super(new GridLayout(1, 0));
        
        super(new BorderLayout());
        this.frame =frame;
        table = new JTable();
        this.directory = directory;
        popupActionListener = new PopupActionListener();
        toolBarActionListener = new ToolBarActionListener();
        tableMouseListener = new TableMouseListener();
        model = new DataFileTableModel(directory);
        model.init();
        
        table.setModel(model);
       // table.setAutoCreateColumnsFromModel(true);
       // table.setAutoCreateRowSorter(true);
        
        table.getTableHeader().setReorderingAllowed(false);
       table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
      
        table.setRowHeight(20);
        
       
        openItem = new JMenuItem(bundle.getString("openItem.name"));
        loadItem = new JMenuItem(bundle.getString("loadItem.name"));
        unLoadItem = new JMenuItem(bundle.getString("unloadItem.name"));
        loadAllItem = new JMenuItem(bundle.getString("loadAllItem.name"));
        unLoadAllItem = new JMenuItem(bundle.getString("unloadAllItem.name"));
        buildItem = new JMenuItem(bundle.getString("buildItem.name"));
        
        
        generateDocItem= new JMenuItem(bundle.getString("generateDocItem.name"));
        reBuildItem= new JMenuItem(bundle.getString("reBuildItem.name"));
        reBuildAllItem= new JMenuItem(bundle.getString("reBuildAllItem.name"));
        prefixDirItem= new JMenuItem(bundle.getString("prefixDirItem.name"));
        updateItem = new JMenuItem(bundle.getString("updateItem.name"));
        refreshItem = new JMenuItem(bundle.getString("refreshItem.name"));
        installItem = new JMenuItem(bundle.getString("installItem.name"));
        unInstallItem = new JMenuItem(bundle.getString("unInstallItem.name"));
        unInstallAllItem = new JMenuItem(bundle.getString("unInstallAllItem.name"));
        
        openItem.setToolTipText(bundle.getString("openItem.tooltip"));
        loadItem.setToolTipText(bundle.getString("loadItem.tooltip"));
        unLoadItem.setToolTipText(bundle.getString("unloadItem.tooltip"));
        loadAllItem.setToolTipText(bundle.getString("loadAllItem.tooltip"));
        unLoadAllItem.setToolTipText(bundle.getString("unloadAllItem.tooltip"));
        buildItem.setToolTipText(bundle.getString("buildItem.tooltip"));
        
        
        generateDocItem.setToolTipText(bundle.getString("generateDocItem.tooltip"));
        reBuildItem.setToolTipText(bundle.getString("reBuildItem.tooltip"));
        reBuildAllItem.setToolTipText(bundle.getString("reBuildAllItem.tooltip"));
        prefixDirItem.setToolTipText(bundle.getString("prefixDirItem.tooltip"));
        updateItem.setToolTipText(bundle.getString("updateItem.tooltip"));
        refreshItem.setToolTipText(bundle.getString("refreshItem.tooltip"));
        installItem.setToolTipText(bundle.getString("installItem.tooltip"));
        unInstallItem.setToolTipText(bundle.getString("unInstallItem.tooltip"));
        unInstallAllItem.setToolTipText(bundle.getString("unInstallAllItem.tooltip"));
        
        openItem.addActionListener(popupActionListener);
        loadItem.addActionListener(popupActionListener);
        unLoadItem.addActionListener(popupActionListener);
        loadAllItem.addActionListener(popupActionListener);
        unLoadAllItem.addActionListener(popupActionListener);
        buildItem.addActionListener(popupActionListener);
        
        
        generateDocItem.addActionListener(popupActionListener);
        reBuildItem.addActionListener(popupActionListener);
        reBuildAllItem.addActionListener(popupActionListener);
        prefixDirItem.addActionListener(popupActionListener);
        updateItem.addActionListener(popupActionListener);
        refreshItem.addActionListener(popupActionListener);
        installItem.addActionListener(popupActionListener);
        unInstallItem.addActionListener(popupActionListener);
        unInstallAllItem.addActionListener(popupActionListener);
        
        popup.add(openItem);
        popup.addSeparator();
        popup.add(loadItem);
        popup.add(unLoadItem);
        popup.addSeparator();
        popup.add(loadAllItem);
        popup.add(unLoadAllItem);
        popup.addSeparator();
        popup.add(installItem);
        popup.add(unInstallItem);
        popup.add(unInstallAllItem);
        popup.addSeparator();
        popup.add(buildItem);
        popup.add(reBuildItem);
        popup.add(reBuildAllItem);
        popup.addSeparator();
        popup.add(generateDocItem);
        popup.add(prefixDirItem);
        popup.add(updateItem);
        popup.add(refreshItem);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
        table.addMouseListener(tableMouseListener);
        table.add(popup);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        setUpToolBar();
         add(scrollPane,BorderLayout.CENTER);
      
    }
    private void setUpToolBar() {
        pkgViewToolBar = new JToolBar();
        pkgViewToolBar.setFloatable(false);
        pkgViewToolBar.setRollover(true);
        
        openButton = new JButton();
        loadButton =new JButton();
        unloadButton = new JButton();
        installButton = new JButton();
        unInstallButton = new JButton();
        refreshButton = new JButton();
        
        
        openButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/open_16.png"))); 
        loadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/load.png"))); 
        unloadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/unload.png"))); 
        installButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/package_add.png")));
        unInstallButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/package_delete.png")));
        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/view-refresh.png"))); 
        
        openButton.setToolTipText(bundle.getString("openItem.tooltip"));
        loadButton.setToolTipText(bundle.getString("loadItem.tooltip"));
        unloadButton.setToolTipText(bundle.getString("unloadItem.tooltip"));
        installButton.setToolTipText(bundle.getString("installItem.tooltip"));
        unInstallButton.setToolTipText(bundle.getString("unInstallItem.tooltip"));
        refreshButton.setToolTipText(bundle.getString("refreshItem.tooltip"));
        
        
        openButton.addActionListener(toolBarActionListener);
        loadButton.addActionListener(toolBarActionListener);
        unloadButton.addActionListener(toolBarActionListener);
        installButton.addActionListener(toolBarActionListener);
        unInstallButton.addActionListener(toolBarActionListener);
        refreshButton.addActionListener(toolBarActionListener);
       
        
        pkgViewToolBar.add(openButton);
        pkgViewToolBar.add(loadButton);      
        pkgViewToolBar.add(unloadButton);
        pkgViewToolBar.add(installButton);  
        pkgViewToolBar.add(unInstallButton);  
        pkgViewToolBar.add(refreshButton);
        
        
        
        add(pkgViewToolBar,BorderLayout.PAGE_START);
        
    }
  
    public DataFileTableModel getModel() {
        return model;
    }

    private void showPopup(MouseEvent e){
        if(table.getSelectedRow() > -1 && e.isPopupTrigger())
            popup.show(e.getComponent(), e.getX(), e.getY());
     }
   public void reload() {
          new DataTask().execute();
       }
    private void open(String path) {
    try {
           
            URI uri = Paths.get(path).toUri();
            Desktop desktop=Desktop.getDesktop();
            desktop.browse(uri);
        } catch (Exception ex) {
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
                  String dir =table.getValueAt(table.getSelectedRow(), 8).toString();
                    open(dir);
                   System.out.println("File: "+dir);
                   
                 }
                 
             }else if(source.equals(loadItem)) {
                 if(table.getSelectedRow() >= 0)  {
                     try {
                            String pkg_name =table.getValueAt(table.getSelectedRow(), 0).toString();
                            MainFrame.octavePanel.eval("pkg load "+pkg_name+";");
                             MainFrame.octavePanel.evaluate("DomainMath_OctavePackages('"+directory+"');");
                             
                             reload();
                        } catch (Exception ex) {
                        }
               
                   
                 }
             }else if(source.equals(unLoadItem)) {
                 if(table.getSelectedRow() >= 0)  {
                     try {
                            String pkg_name =table.getValueAt(table.getSelectedRow(), 0).toString();
                            MainFrame.octavePanel.eval("pkg unload "+pkg_name+";");
                            MainFrame.octavePanel.evaluate("DomainMath_OctavePackages('"+directory+"');");
                            reload();
                        } catch (Exception ex) {
                        }
               
                   
                 }
             }else if(source.equals(loadAllItem)) {              
                MainFrame.octavePanel.eval("pkg load all;");
               
                MainFrame.octavePanel.evaluate("DomainMath_OctavePackages('"+directory+"');");   
                reload();
             }else if(source.equals(unLoadAllItem)) {
                 MainFrame.octavePanel.eval("pkg unload all;");
                 MainFrame.octavePanel.evaluate("DomainMath_OctavePackages('"+directory+"');");  
                 reload();
             }else if(source.equals(buildItem)) {
                OctPkgBuildDlg octPkgBuildDlg = new OctPkgBuildDlg(frame,true);
                octPkgBuildDlg.setLocationRelativeTo(frame);
                octPkgBuildDlg.setVisible(true);
                reload();
             }
            else if(source.equals(reBuildItem)) {
                if(table.getSelectedRow() >= 0)  {
                     try {
                            String pkg_name =table.getValueAt(table.getSelectedRow(), 0).toString();
                             ReBuildDlg reBuildDlg = new ReBuildDlg(frame,true,pkg_name);
                            reBuildDlg.setLocationRelativeTo(frame);
                            reBuildDlg.setVisible(true);
                            reload();
                        } catch (Exception ex) {
                        }
               
                   
                } 
             }else if(source.equals(reBuildAllItem)) {
                MainFrame.octavePanel.eval("pkg rebuild all;");
                MainFrame.octavePanel.evaluate("DomainMath_OctavePackages('"+directory+"');"); 
                reload();
             }else if(source.equals(generateDocItem)) {
                  if(table.getSelectedRow() >= 0)  {
                     try {
                            String pkg_name =table.getValueAt(table.getSelectedRow(), 0).toString();
                            MainFrame.octavePanel.eval("pkg load generate_html");
                           GenerateDocDialog generateDocDialog = new GenerateDocDialog(frame,true,pkg_name);
                            generateDocDialog .setLocationRelativeTo(frame);
                            generateDocDialog .setVisible(true);
                            reload();
                        } catch (Exception ex) {
                        }
               
                   
                 }
                
             }else if(source.equals(prefixDirItem)) {
                   PrefixDirDialog prefixDialog = new PrefixDirDialog(frame,true);
                    prefixDialog.setLocationRelativeTo(frame);
                    prefixDialog.setVisible(true);
                    reload();
             }else if(source.equals(installItem)) {
                 InstallDialog installDialog = new InstallDialog(frame,true);
                    installDialog.setLocationRelativeTo(frame);
                    installDialog.setVisible(true);
                    reload();
             }else if(source.equals(unInstallItem)) {
                if(table.getSelectedRow() >= 0)  {
                     try {
                            String pkg_name =table.getValueAt(table.getSelectedRow(), 0).toString();
                            MainFrame.octavePanel.eval("pkg uninstall "+pkg_name+";");
                            MainFrame.octavePanel.evaluate("DomainMath_OctavePackages('"+directory+"');");
                            reload();
                        } catch (Exception ex) {
                        }
               
                   
                 }
             }else if(source.equals(unInstallAllItem)) {
                 MainFrame.octavePanel.eval("pkg uninstall all;");
                MainFrame.octavePanel.evaluate("DomainMath_OctavePackages('"+directory+"');"); 
                reload();
             }else if(source.equals(updateItem)) {
                MainFrame.octavePanel.eval("pkg update");
                MainFrame.octavePanel.evaluate("DomainMath_OctavePackages('"+directory+"');"); 
                reload();
             }
             else if(source.equals(refreshItem)) {
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
                            String dir =table.getValueAt(table.getSelectedRow(), 8).toString();
                            URI uri = Paths.get(dir).toUri();
                            Desktop desktop=Desktop.getDesktop();
                            desktop.browse(uri);
                        } catch (IOException | NullPointerException ex) {
                        }
               
                   
                 }
                 
             }else if(loadButton.equals(source)) {
                 if(table.getSelectedRow() >= 0)  {
                     try {
                            String pkg_name =table.getValueAt(table.getSelectedRow(), 0).toString();
                            MainFrame.octavePanel.eval("pkg load "+pkg_name+";");
                             MainFrame.octavePanel.evaluate("DomainMath_OctavePackages('"+directory+"');");
                             reload();
                        } catch (Exception ex) {
                        }
               
                   
                 }
                
             }else if(unloadButton.equals(source)) {
                 if(table.getSelectedRow() >= 0)  {
                     try {
                            String pkg_name =table.getValueAt(table.getSelectedRow(), 0).toString();
                            MainFrame.octavePanel.eval("pkg unload "+pkg_name+";");
                            MainFrame.octavePanel.evaluate("DomainMath_OctavePackages('"+directory+"');");
                            reload();
                        } catch (Exception ex) {
                        }
               
                   
                 }
             }else if(installButton.equals(source)) {
                 InstallDialog installDialog = new InstallDialog(frame,true);
                    installDialog.setLocationRelativeTo(frame);
                    installDialog.setVisible(true);
                    reload();
            }else if(unInstallButton.equals(source)) {
               if(table.getSelectedRow() >= 0)  {
                     try {
                            String pkg_name =table.getValueAt(table.getSelectedRow(), 0).toString();
                            MainFrame.octavePanel.eval("pkg uninstall "+pkg_name+";");
                            MainFrame.octavePanel.evaluate("DomainMath_OctavePackages('"+directory+"');");
                            reload();
                        } catch (Exception ex) {
                        }
               
                   
                 }
            }
             else if(refreshButton.equals(source)) {
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
