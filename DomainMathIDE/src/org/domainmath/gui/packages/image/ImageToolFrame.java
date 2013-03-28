
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

package org.domainmath.gui.packages.image;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.StatusPanel;
import org.domainmath.gui.Util.DomainMathFileFilter;
import org.domainmath.gui.about.AboutDlg;


public class ImageToolFrame extends javax.swing.JFrame {



    public   JTabbedPane fileTab = new JTabbedPane();
    private final StatusPanel status_panel;
    private String currentDir1;
    
    private JPopupMenu popup;
    private JMenuItem ploadItem;
    private JMenuItem pcloseItem;
    private JMenuItem pcloseAllItem;
    private JMenuItem pPropertiesItem;
    private  List fileNameList =Collections.synchronizedList(new ArrayList());
    public static  int index;
    public ImageToolFrame() {
        this.setIconImage(icon);
        initComponents();
        this.setSize(600, 400);
        
        
         fileTab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
     
        this.popupTab();
        
       add(fileTab);
       status_panel=new StatusPanel();
       add(status_panel,BorderLayout.PAGE_END);
       currentDir1 = null;
          
    }

    public String getCurrentDir() {
        return currentDir1;
    }

    public void setCurrentDir(String currentDir) {
        this.currentDir1 = currentDir;
    }
    
    private void popupTab(){
         popup = new JPopupMenu();
         ploadItem = new JMenuItem("Load");
         pcloseItem = new JMenuItem("Close");
         pcloseAllItem = new JMenuItem("Close All");
         pPropertiesItem = new JMenuItem("Properties");
        popup.add(ploadItem);
        popup.add(pcloseItem);
        popup.add(pcloseAllItem);
        popup.add(pPropertiesItem);
        fileTab.addMouseListener(new PopupListener(popup));
        
        pPropertiesItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.octavePanel.evaluate("javaaddpath('"+System.getProperty("user.dir")+File.separator+"scripts"+File.separator+"Grid.jar')");
                 if(fileTab.getSelectedIndex() >= 0) { 
                        MainFrame.octavePanel.evaluate("DomainMath_Image('"+fileTab.getToolTipTextAt(fileTab.getSelectedIndex())+"');");
                 }
            }
            
        });
        pcloseItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                 if(fileTab.getSelectedIndex() >= 0) { 
                   
                    removeFileNameFromList(fileTab.getSelectedIndex());
                   
                   fileTab.remove(fileTab.getSelectedIndex());
                  index--;
               }
               
            }
  
        });
        
        pcloseAllItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int i=fileTab.getTabCount()-1;
                while(i != -1) {

                    removeFileNameFromList(i);
                    fileTab.remove(i);
                    index--;
                    i--;
                }
 
           }

        });
        
        ploadItem.addActionListener(new java.awt.event.ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                 ImageLoadDialog imageLoadDialog = new ImageLoadDialog(null,true,fileTab.getToolTipTextAt(fileTab.getSelectedIndex()));
                       imageLoadDialog.setLocationRelativeTo(null);
                       imageLoadDialog.setVisible(true);
            }
            
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        openButton = new javax.swing.JButton();
        propertiesButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        openItem = new javax.swing.JMenuItem();
        closeItem = new javax.swing.JMenuItem();
        closeAllItem = new javax.swing.JMenuItem();
        exportMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitItem = new javax.swing.JMenuItem();
        imageMenu = new javax.swing.JMenu();
        loadImageItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        propertiesItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        forumItem = new javax.swing.JMenuItem();
        onlineHelpItem = new javax.swing.JMenuItem();
        howToItem = new javax.swing.JMenuItem();
        faqItem = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        suggestionsItem = new javax.swing.JMenuItem();
        reportBugItem1 = new javax.swing.JMenuItem();
        feedBackItem1 = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        AboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/packages/image/resources/image-tool_en"); // NOI18N
        setTitle(bundle.getString("imageToolFrame.title")); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        openButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-open.png"))); // NOI18N
        openButton.setFocusable(false);
        openButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(openButton);

        propertiesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/dialog-information.png"))); // NOI18N
        propertiesButton.setFocusable(false);
        propertiesButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        propertiesButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        propertiesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                propertiesButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(propertiesButton);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jMenu1.setText(bundle.getString("fileMenu.name")); // NOI18N

        openItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-open.png"))); // NOI18N
        openItem.setText(bundle.getString("openIem.name")); // NOI18N
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openItemActionPerformed(evt);
            }
        });
        jMenu1.add(openItem);

        closeItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        closeItem.setText(bundle.getString("closeItem.name")); // NOI18N
        closeItem.setToolTipText(bundle.getString("closeItem.tooltip")); // NOI18N
        closeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeItemActionPerformed(evt);
            }
        });
        jMenu1.add(closeItem);

        closeAllItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        closeAllItem.setText(bundle.getString("closeAllItem.name")); // NOI18N
        closeAllItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeAllItemActionPerformed(evt);
            }
        });
        jMenu1.add(closeAllItem);

        exportMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        exportMenuItem.setText("Export");
        exportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(exportMenuItem);
        jMenu1.add(jSeparator1);

        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        exitItem.setText(bundle.getString("exitItem.name")); // NOI18N
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        jMenu1.add(exitItem);

        jMenuBar1.add(jMenu1);

        imageMenu.setText(bundle.getString("imageMenu.name")); // NOI18N

        loadImageItem.setText(bundle.getString("loadImageItem")); // NOI18N
        loadImageItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadImageItemActionPerformed(evt);
            }
        });
        imageMenu.add(loadImageItem);

        jMenuItem1.setText("Image Filter");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        imageMenu.add(jMenuItem1);

        jMenuItem2.setText("Smooth Image");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        imageMenu.add(jMenuItem2);

        propertiesItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        propertiesItem.setText(bundle.getString("propertiesItem")); // NOI18N
        propertiesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                propertiesItemActionPerformed(evt);
            }
        });
        imageMenu.add(propertiesItem);

        jMenuBar1.add(imageMenu);

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en"); // NOI18N
        helpMenu.setText(bundle1.getString("helpMenu.name")); // NOI18N

        forumItem.setText("Forum");
        forumItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forumItemActionPerformed(evt);
            }
        });
        helpMenu.add(forumItem);

        onlineHelpItem.setText("Help and Support");
        onlineHelpItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineHelpItemActionPerformed(evt);
            }
        });
        helpMenu.add(onlineHelpItem);

        howToItem.setText("How to...");
        howToItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                howToItemActionPerformed(evt);
            }
        });
        helpMenu.add(howToItem);

        faqItem.setText("Online FAQ");
        faqItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faqItemActionPerformed(evt);
            }
        });
        helpMenu.add(faqItem);
        helpMenu.add(jSeparator16);

        suggestionsItem.setText("Suggestions");
        suggestionsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestionsItemActionPerformed(evt);
            }
        });
        helpMenu.add(suggestionsItem);

        reportBugItem1.setText(bundle1.getString("reportBugItem.name")); // NOI18N
        reportBugItem1.setToolTipText(bundle1.getString("reportBugItem.tooltip")); // NOI18N
        reportBugItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportBugItem1ActionPerformed(evt);
            }
        });
        helpMenu.add(reportBugItem1);

        feedBackItem1.setText(bundle1.getString("yourFeedbackItem.name")); // NOI18N
        feedBackItem1.setToolTipText(bundle1.getString("yourFeedbackItem.tooltip")); // NOI18N
        feedBackItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedBackItem1ActionPerformed(evt);
            }
        });
        helpMenu.add(feedBackItem1);
        helpMenu.add(jSeparator12);

        AboutItem.setText(bundle1.getString("aboutItem.name")); // NOI18N
        AboutItem.setToolTipText(bundle1.getString("aboutItem.tooltip")); // NOI18N
        AboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutItemActionPerformed(evt);
            }
        });
        helpMenu.add(AboutItem);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openItemActionPerformed
       open();
    }//GEN-LAST:event_openItemActionPerformed

    public void setPath(String path) {
    try {
            URI uri = new URI(path);
            Desktop desktop=Desktop.getDesktop();
            desktop.browse(uri);
        } catch (URISyntaxException | IOException ex) {
        }
    } 
    private void forumItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forumItemActionPerformed
        setPath("http://domainmathide.freeforums.org/");
    }//GEN-LAST:event_forumItemActionPerformed

    private void onlineHelpItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlineHelpItemActionPerformed
        setPath("http://domainmathide.freeforums.org/help-and-support-f5.html");
    }//GEN-LAST:event_onlineHelpItemActionPerformed

    private void howToItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_howToItemActionPerformed
        setPath("http://domainmathide.freeforums.org/how-to-f9.html");
    }//GEN-LAST:event_howToItemActionPerformed

    private void faqItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faqItemActionPerformed
        setPath("http://domainmathide.freeforums.org/faq-f8.html");
    }//GEN-LAST:event_faqItemActionPerformed

    private void suggestionsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestionsItemActionPerformed
        setPath("http://domainmathide.freeforums.org/suggestions-f6.html");
    }//GEN-LAST:event_suggestionsItemActionPerformed

    private void reportBugItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportBugItem1ActionPerformed
        setPath("http://domainmathide.freeforums.org/bugs-f3.html");
    }//GEN-LAST:event_reportBugItem1ActionPerformed

    private void feedBackItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feedBackItem1ActionPerformed
        setPath("http://domainmathide.freeforums.org/feedback-f4.html");
    }//GEN-LAST:event_feedBackItem1ActionPerformed

    private void AboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutItemActionPerformed
        AboutDlg aboutDlg = new AboutDlg(this, true);
        aboutDlg.setLocationRelativeTo(this);
        aboutDlg.setVisible(true);
    }//GEN-LAST:event_AboutItemActionPerformed

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        dispose();
    }//GEN-LAST:event_exitItemActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
       open();
    }//GEN-LAST:event_openButtonActionPerformed

    private void propertiesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_propertiesItemActionPerformed
        MainFrame.octavePanel.evaluate("javaaddpath('"+System.getProperty("user.dir")+File.separator+"scripts"+File.separator+"Grid.jar')");
         if(fileTab.getSelectedIndex() >= 0) { 
                MainFrame.octavePanel.evaluate("DomainMath_Image('"+fileTab.getToolTipTextAt(fileTab.getSelectedIndex())+"');");
         }
    
    }//GEN-LAST:event_propertiesItemActionPerformed

    private void loadImageItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadImageItemActionPerformed
         if(fileTab.getSelectedIndex() >= 0) { 
               ImageLoadDialog imageLoadDialog = new ImageLoadDialog(this,true,fileTab.getToolTipTextAt(fileTab.getSelectedIndex()));
                       imageLoadDialog.setLocationRelativeTo(this);
                       imageLoadDialog.setVisible(true);
         }
    }//GEN-LAST:event_loadImageItemActionPerformed

    private void propertiesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_propertiesButtonActionPerformed
        MainFrame.octavePanel.evaluate("javaaddpath('"+System.getProperty("user.dir")+File.separator+"scripts"+File.separator+"Grid.jar')");
         if(fileTab.getSelectedIndex() >= 0) { 
                MainFrame.octavePanel.evaluate("DomainMath_Image('"+fileTab.getToolTipTextAt(fileTab.getSelectedIndex())+"');");
         }
    }//GEN-LAST:event_propertiesButtonActionPerformed

    private void closeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeItemActionPerformed
         if(fileTab.getSelectedIndex() >= 0) { 
                   
                    removeFileNameFromList(fileTab.getSelectedIndex());
                   
                   fileTab.remove(fileTab.getSelectedIndex());
                   index--;
               }
    }//GEN-LAST:event_closeItemActionPerformed

    private void closeAllItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeAllItemActionPerformed
     int i=fileTab.getTabCount()-1;
        while(i != -1) {

            removeFileNameFromList(i);
            fileTab.remove(i);
            index--;
            i--;
        }
    }//GEN-LAST:event_closeAllItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if(fileTab.getSelectedIndex() >= 0) { 
            ImageFilterDialog im= new ImageFilterDialog(this,fileTab.getToolTipTextAt(fileTab.getSelectedIndex()),true);
            im.setLocationRelativeTo(this);
            im.setVisible(true);
         }else{
            ImageFilterDialog im= new ImageFilterDialog(this,"",true);
            im.setLocationRelativeTo(this);
            im.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void exportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportMenuItemActionPerformed
        ExportImageDialog exportImageDialog = new ExportImageDialog(this,true);
        exportImageDialog.setLocationRelativeTo(this);
        exportImageDialog.setVisible(true);
    }//GEN-LAST:event_exportMenuItemActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        SmoothImageDialog smoothImageDialog = new SmoothImageDialog(this,true);
        smoothImageDialog.setLocationRelativeTo(this);
        smoothImageDialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException {
       
         try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            }
         
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
             
         
            @Override
            public void run() {
                new ImageToolFrame().setVisible(true);
            }
        });
    }
    public  java.net.URL imgURL = getClass().getResource("resources/DomainMath.png");
    public   Image icon = Toolkit.getDefaultToolkit().getImage(imgURL);
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private javax.swing.JMenuItem closeAllItem;
    private javax.swing.JMenuItem closeItem;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem exportMenuItem;
    private javax.swing.JMenuItem faqItem;
    private javax.swing.JMenuItem feedBackItem1;
    private javax.swing.JMenuItem forumItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem howToItem;
    private javax.swing.JMenu imageMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem loadImageItem;
    private javax.swing.JMenuItem onlineHelpItem;
    private javax.swing.JButton openButton;
    private javax.swing.JMenuItem openItem;
    private javax.swing.JButton propertiesButton;
    private javax.swing.JMenuItem propertiesItem;
    private javax.swing.JMenuItem reportBugItem1;
    private javax.swing.JMenuItem suggestionsItem;
    // End of variables declaration//GEN-END:variables

     public void addFileNameToList(String name) {
        fileNameList.add(name);
    }
    
    public void removeFileNameFromList(int index) {
        fileNameList.remove(index);
    }
   public void open(){

        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(DomainMathFileFilter.IMAGES_FILE_FILTER);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);     

          if(fileTab.getSelectedIndex() >= 0) { 
              File f = new File(currentDir1);
               fc.setCurrentDirectory(f);  
          }

        fc.setMultiSelectionEnabled(true);

        File file[];
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
                   
                file = fc.getSelectedFiles();
                
                this.setCurrentDir(fc.getCurrentDirectory().getAbsolutePath());
                  for(int i=0;i<file.length;i++) {
                            if(!fileNameList.contains(file[i].getAbsolutePath())) {
                               open(file[i]);
                                
                                
                            }else {
                                System.out.println(file[i].getAbsolutePath()+" already open!");
                            }
                    }  
      
        }
    }
 
    public void open(File file) {
                LoadImagePanel loadImagePanel = new LoadImagePanel(file);
                fileTab.addTab(file.getName(), loadImagePanel);    
                fileTab.setToolTipTextAt(index, file.getAbsolutePath());
                fileTab.setSelectedIndex(index);
                this.addFileNameToList(file.getAbsolutePath());
                 index++;
               

    }
    class PopupListener extends MouseAdapter {
        JPopupMenu popup;

        PopupListener(JPopupMenu popupMenu) {
            popup = popupMenu;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger() && fileTab.getTabCount() > 0) {
                popup.show(e.getComponent(),
                           e.getX(), e.getY());
            }
        }
    }
}
