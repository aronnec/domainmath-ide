
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.Util.DomainMathFileFilter;
import org.domainmath.gui.about.AboutDlg;
import org.domainmath.gui.common.DomainMathDialog;





public class MultiSeqAlignViewerFrame extends javax.swing.JFrame {

       private String var_name;
    private int tab_index=0;
    public   Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/domainmath/gui/resources/DomainMath.png"));
    
    public MultiSeqAlignViewerFrame() {
        setIconImage(icon);
        
        setSize(800,600);
        setLocationRelativeTo(null);
        initComponents();
    }

   
    public String getExportVarName() {
        return this.var_name;
    }
    
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusPanel2 = new org.domainmath.gui.StatusPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        openItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
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
        setTitle("Sequence Viewer");

        jMenu1.setText("File");

        openItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openItem.setText("Open");
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openItemActionPerformed(evt);
            }
        });
        jMenu1.add(openItem);

        saveAsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveAsMenuItem.setText("Save As...");
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveAsMenuItem);

        jMenuItem1.setText("Export");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

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
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
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
       JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(DomainMathFileFilter.FASTA_FILE_FILTER);
       
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.jTabbedPane1.add(fc.getSelectedFile().getName(),new MultiSeqViewerPanel(fc.getSelectedFile()));
            this.jTabbedPane1.setSelectedIndex(tab_index);
            tab_index++;
         } 
    }//GEN-LAST:event_openItemActionPerformed

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
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

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        DomainMathDialog exportDialog = new DomainMathDialog(this,true,"Name:");
        exportDialog.setTitle("Export Sequence");
        exportDialog.setLocationRelativeTo(this);
        exportDialog.setVisible(true);
        exportVarTo(exportDialog.getVar_name());
        
        String v=this.getExportVarName();
        if(this.jTabbedPane1.getSelectedIndex() >= 0) {
           MultiSeqViewerPanel p = (MultiSeqViewerPanel) this.jTabbedPane1.getComponentAt(this.jTabbedPane1.getSelectedIndex());
         if(!v.equals("")){
                        
                        for(int i=0; i<p.Sequence.size();i++) {
                            
                            MainFrame.octavePanel.evaluate(v+"("+(i+1)+").Header='"+p.header.get(i)+"';");
                            MainFrame.octavePanel.evaluate(v+"("+(i+1)+").Sequence='"+p.Sequence.get(i)+"';");
                        }
         }
         MainFrame.reloadWorkspace(); 
        }
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
      if(this.jTabbedPane1.getSelectedIndex() >= 0) {
           
            try {
                MultiSeqViewerPanel p = (MultiSeqViewerPanel) this.jTabbedPane1.getComponentAt(this.jTabbedPane1.getSelectedIndex());
                JList list = p.listSequence;
           
                List<String> s = list.getSelectedValuesList();
                BufferedWriter w = new BufferedWriter(new FileWriter(new File("E:/Test.txt")));
                w.write(">Test");
                w.newLine();
                
                 for(int i=0; i<s.size(); i++) {
                    w.append(s.get(i));
                }
                 w.newLine();
                 w.close();
            } catch (IOException ex) {
                Logger.getLogger(MultiSeqAlignViewerFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
          
           System.out.println("Written");
       }
    }//GEN-LAST:event_saveAsMenuItemActionPerformed

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
            public void run() {
                new MultiSeqAlignViewerFrame().setVisible(true);
            }
        });
    }

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem faqItem;
    private javax.swing.JMenuItem feedBackItem;
    private javax.swing.JMenuItem forumItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem howToItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuItem onlineHelpItem;
    private javax.swing.JMenuItem openItem;
    private javax.swing.JMenuItem reportBugItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private org.domainmath.gui.StatusPanel statusPanel2;
    private javax.swing.JMenuItem suggestionsItem;
    // End of variables declaration//GEN-END:variables

    private void exportVarTo(String var_name) {
        this.var_name=var_name;
    }

   
    
}
