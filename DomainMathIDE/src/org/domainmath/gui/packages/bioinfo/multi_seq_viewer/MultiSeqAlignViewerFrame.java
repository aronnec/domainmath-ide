
/*
 * Copyright (C) 2013 Vinu K.N
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


package org.domainmath.gui.packages.bioinfo.multi_seq_viewer;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.biojava3.core.util.ConcurrencyTools;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.Util.DomainMathFileFilter;
import org.domainmath.gui.about.AboutDlg;
import org.domainmath.gui.common.DomainMathDialog;





public class MultiSeqAlignViewerFrame extends javax.swing.JFrame {

       private String var_name;
   
    public   Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/domainmath/gui/resources/DomainMath.png"));
    private File selectedFile;
     private JPopupMenu popup;
    private JMenuItem pcloseItem;
    private JMenuItem pcloseAllItem;
    
    private  List fileNameList =Collections.synchronizedList(new ArrayList());
    public static  int index;
    public MultiSeqAlignViewerFrame() {
        setIconImage(icon);
        
        setSize(800,600);
        setLocationRelativeTo(null);
        initComponents();
        this.popupTab();
    }

   
    public String getExportVarName() {
        return this.var_name;
    }
    
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusPanel2 = new org.domainmath.gui.StatusPanel();
        fileTab = new javax.swing.JTabbedPane();
        jToolBar1 = new javax.swing.JToolBar();
        openButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        openItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        exportMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        closeMenuItem = new javax.swing.JMenuItem();
        closeAllMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        forumItem = new javax.swing.JMenuItem();
        onlineHelpItem = new javax.swing.JMenuItem();
        howToItem = new javax.swing.JMenuItem();
        faqItem = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        suggestionsItem = new javax.swing.JMenuItem();
        reportBugItem = new javax.swing.JMenuItem();
        feedBackItem = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        AboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Multiple Sequence Viewer");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBar1.setFloatable(false);

        openButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-open.png"))); // NOI18N
        openButton.setToolTipText("Open");
        openButton.setFocusable(false);
        openButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(openButton);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-save.png"))); // NOI18N
        saveButton.setToolTipText("Save");
        saveButton.setFocusable(false);
        saveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(saveButton);

        jMenu1.setText("File");

        openItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openItem.setText("Open");
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openItemActionPerformed(evt);
            }
        });
        jMenu1.add(openItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveMenuItem);

        exportMenuItem.setText("Export to Workspace");
        exportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(exportMenuItem);
        jMenu1.add(jSeparator2);

        closeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        closeMenuItem.setText("Close");
        closeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(closeMenuItem);

        closeAllMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        closeAllMenuItem.setText("Close All..");
        closeAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeAllMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(closeAllMenuItem);
        jMenu1.add(jSeparator1);

        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        jMenu1.add(exitItem);

        jMenuBar1.add(jMenu1);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en"); // NOI18N
        helpMenu.setText(bundle.getString("helpMenu.name")); // NOI18N

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
        helpMenu.add(jSeparator14);

        suggestionsItem.setText("Suggestions");
        suggestionsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestionsItemActionPerformed(evt);
            }
        });
        helpMenu.add(suggestionsItem);

        reportBugItem.setText(bundle.getString("reportBugItem.name")); // NOI18N
        reportBugItem.setToolTipText(bundle.getString("reportBugItem.tooltip")); // NOI18N
        reportBugItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportBugItemActionPerformed(evt);
            }
        });
        helpMenu.add(reportBugItem);

        feedBackItem.setText(bundle.getString("yourFeedbackItem.name")); // NOI18N
        feedBackItem.setToolTipText(bundle.getString("yourFeedbackItem.tooltip")); // NOI18N
        feedBackItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedBackItemActionPerformed(evt);
            }
        });
        helpMenu.add(feedBackItem);
        helpMenu.add(jSeparator7);

        AboutItem.setText(bundle.getString("aboutItem.name")); // NOI18N
        AboutItem.setToolTipText(bundle.getString("aboutItem.tooltip")); // NOI18N
        AboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutItemActionPerformed(evt);
            }
        });
        helpMenu.add(AboutItem);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
            .addComponent(fileTab)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(fileTab, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(statusPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setPath(String path) {
    try {
            URI uri = new URI(path);
            Desktop desktop=Desktop.getDesktop();
            desktop.browse(uri);
        } catch (URISyntaxException | IOException ex) {
        }
}
   
    private void openItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openItemActionPerformed
       open();
    }//GEN-LAST:event_openItemActionPerformed

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        ConcurrencyTools.shutdown();
        dispose();
    }//GEN-LAST:event_exitItemActionPerformed

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

    private void reportBugItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportBugItemActionPerformed
        setPath("http://domainmathide.freeforums.org/bugs-f3.html");
    }//GEN-LAST:event_reportBugItemActionPerformed

    private void feedBackItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feedBackItemActionPerformed
        setPath("http://domainmathide.freeforums.org/feedback-f4.html");
    }//GEN-LAST:event_feedBackItemActionPerformed

    private void AboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutItemActionPerformed
        AboutDlg aboutDlg = new AboutDlg(this,true);
        aboutDlg.setLocationRelativeTo(this);
        aboutDlg.setVisible(true);
    }//GEN-LAST:event_AboutItemActionPerformed

    private void exportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportMenuItemActionPerformed
        DomainMathDialog exportDialog = new DomainMathDialog(this,true,"Name:");
        exportDialog.setTitle("Export Sequence");
        exportDialog.setLocationRelativeTo(this);
        exportDialog.setVisible(true);
        exportVarTo(exportDialog.getVar_name());
        String v=this.getExportVarName();
        if(this.fileTab.getSelectedIndex() >= 0) {
           MultiSeqViewerPanel p = (MultiSeqViewerPanel) this.fileTab.getComponentAt(this.fileTab.getSelectedIndex());
         if(!v.equals("")){
                        
                        for(int i=0; i<p.Sequence.size();i++) {
                            
                            MainFrame.octavePanel.evaluate(v+"("+(i+1)+").Header='"+p.header.get(i)+"';");
                            MainFrame.octavePanel.evaluate(v+"("+(i+1)+").Sequence='"+p.Sequence.get(i)+"';");
                        }
         }
         MainFrame.reloadWorkspace(); 
        }
        
    }//GEN-LAST:event_exportMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
      save();
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private String getSequence(int size,int c,int r,ListModel model) {
                    int c_end=c+r;
                    String seq="";
                    for (int i=c; i<c_end;i++ ) {
                        if (i < size) {
                            seq+=model.getElementAt(i);
                        }
                    }
         return seq;
    }
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       ConcurrencyTools.shutdown();
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void closeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuItemActionPerformed
        if(fileTab.getSelectedIndex() >= 0) {

            removeFileNameFromList(fileTab.getSelectedIndex());

            fileTab.remove(fileTab.getSelectedIndex());
            index--;
        }
    }//GEN-LAST:event_closeMenuItemActionPerformed

    private void closeAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeAllMenuItemActionPerformed
        int i=fileTab.getTabCount()-1;
        while(i != -1) {

            removeFileNameFromList(i);
            fileTab.remove(i);
            index--;
            i--;
        }
    }//GEN-LAST:event_closeAllMenuItemActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        open();
    }//GEN-LAST:event_openButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        save();
    }//GEN-LAST:event_saveButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
           try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
       }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MultiSeqAlignViewerFrame().setVisible(true);
            }
        });
    }

  public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public File getSelectedFile() {
        return this.selectedFile;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private javax.swing.JMenuItem closeAllMenuItem;
    private javax.swing.JMenuItem closeMenuItem;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem exportMenuItem;
    private javax.swing.JMenuItem faqItem;
    private javax.swing.JMenuItem feedBackItem;
    private javax.swing.JTabbedPane fileTab;
    private javax.swing.JMenuItem forumItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem howToItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem onlineHelpItem;
    private javax.swing.JButton openButton;
    private javax.swing.JMenuItem openItem;
    private javax.swing.JMenuItem reportBugItem;
    private javax.swing.JButton saveButton;
    private javax.swing.JMenuItem saveMenuItem;
    private org.domainmath.gui.StatusPanel statusPanel2;
    private javax.swing.JMenuItem suggestionsItem;
    // End of variables declaration//GEN-END:variables

    private void exportVarTo(String var_name) {
        this.var_name=var_name;
    }

     public void addFileNameToList(String name) {
        fileNameList.add(name);
    }
    
    public void removeFileNameFromList(int index) {
        fileNameList.remove(index);
    }
     private void popupTab(){
         popup = new JPopupMenu();
         
         pcloseItem = new JMenuItem("Close");
         pcloseAllItem = new JMenuItem("Close All");
       
       
        popup.add(pcloseItem);
        popup.add(pcloseAllItem);
       fileTab.addMouseListener(new PopupListener(popup));
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
        
       
    }

    private void open() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(DomainMathFileFilter.FASTA_FILE_FILTER);
       
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.fileTab.add(fc.getSelectedFile().getName(),new MultiSeqViewerPanel(fc.getSelectedFile()));
            this.fileTab.setSelectedIndex(index);
             this.addFileNameToList(fc.getSelectedFile().getAbsolutePath());
                    index++;
            
         } 
    }

    private void save() {
         if(this.fileTab.getSelectedIndex() >= 0) {
          JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setMultiSelectionEnabled(false);
            fc.setFileFilter(DomainMathFileFilter.FASTA_FILE_FILTER);
       
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.setSelectedFile(fc.getSelectedFile());
             
             try {
                MultiSeqViewerPanel p = (MultiSeqViewerPanel) this.fileTab.getComponentAt(this.fileTab.getSelectedIndex());

                int r = p.getRowCount()/p.listConsensus.getModel().getSize();
                ListModel model = p.listConsensus.getModel();
                 int c =p.listConsensus.getModel().getSize();
               BufferedWriter w = new BufferedWriter(new FileWriter(fc.getSelectedFile()));
                for(int i=0; i<c; i++)  {
                     
                        w.write(">"+model.getElementAt(i).toString());
                        w.newLine();
                   
                    w.append(getSequence(p.listSequence.getModel().getSize(),(i*r),r,p.listSequence.getModel()));
                    w.newLine();
                }

                 w.close();
            } catch (IOException ex) {
                Logger.getLogger(MultiSeqAlignViewerFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
          
         } 
           
           
           System.out.println("Written");
       }
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
