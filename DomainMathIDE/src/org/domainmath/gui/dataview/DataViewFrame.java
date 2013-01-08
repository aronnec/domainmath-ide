
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
package org.domainmath.gui.dataview;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.domainmath.gui.about.AboutDlg;


public class DataViewFrame extends javax.swing.JFrame {

    private java.net.URL imgURL = getClass().getResource("Resources/DomainMath.png");
    private Image icon = Toolkit.getDefaultToolkit().getImage(imgURL);
    private final DataViewPanel newContentPane;
    public DataViewFrame(String path) {
         
         File f = new File(path);
                    String t= f.getName().substring(0, f.getName().indexOf("."));
        setTitle("Variable View-"+t);            
        newContentPane = new DataViewPanel(path);
                    newContentPane.setOpaque(true);
                    setContentPane(newContentPane);
                    pack();
            initComponents();
        this.setIconImage(icon);
        setLocationRelativeTo(null);
        setVisible(true);
        newContentPane.reload();
        newContentPane.reload();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusPanel1 = new org.domainmath.gui.StatusPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        refreshItem = new javax.swing.JMenuItem();
        printItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        matrixMenu = new javax.swing.JMenu();
        helpMenu2 = new javax.swing.JMenu();
        forumItem2 = new javax.swing.JMenuItem();
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
        getContentPane().add(statusPanel1, java.awt.BorderLayout.PAGE_END);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/dataview/resources/dataview_en"); // NOI18N
        fileMenu.setText(bundle.getString("fileMenu.title")); // NOI18N

        refreshItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        refreshItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/view-refresh.png"))); // NOI18N
        refreshItem.setText(bundle.getString("refreshItem.title")); // NOI18N
        refreshItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshItemActionPerformed(evt);
            }
        });
        fileMenu.add(refreshItem);

        printItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-print.png"))); // NOI18N
        printItem.setText(bundle.getString("printItem.title")); // NOI18N
        printItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printItemActionPerformed(evt);
            }
        });
        fileMenu.add(printItem);

        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        exitItem.setText(bundle.getString("exitItem.title")); // NOI18N
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitItem);

        jMenuBar1.add(fileMenu);

        matrixMenu.setText(bundle.getString("matrixMenu.title")); // NOI18N
        jMenuBar1.add(matrixMenu);

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en"); // NOI18N
        helpMenu2.setText(bundle1.getString("helpMenu.name")); // NOI18N

        forumItem2.setText("Forum");
        forumItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forumItem2ActionPerformed(evt);
            }
        });
        helpMenu2.add(forumItem2);

        onlineHelpItem.setText("Help and Support");
        onlineHelpItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineHelpItemActionPerformed(evt);
            }
        });
        helpMenu2.add(onlineHelpItem);

        howToItem.setText("How to...");
        howToItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                howToItemActionPerformed(evt);
            }
        });
        helpMenu2.add(howToItem);

        faqItem.setText("Online FAQ");
        faqItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faqItemActionPerformed(evt);
            }
        });
        helpMenu2.add(faqItem);
        helpMenu2.add(jSeparator16);

        suggestionsItem.setText("Suggestions");
        suggestionsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestionsItemActionPerformed(evt);
            }
        });
        helpMenu2.add(suggestionsItem);

        reportBugItem1.setText(bundle1.getString("reportBugItem.name")); // NOI18N
        reportBugItem1.setToolTipText(bundle1.getString("reportBugItem.tooltip")); // NOI18N
        reportBugItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportBugItem1ActionPerformed(evt);
            }
        });
        helpMenu2.add(reportBugItem1);

        feedBackItem1.setText(bundle1.getString("yourFeedbackItem.name")); // NOI18N
        feedBackItem1.setToolTipText(bundle1.getString("yourFeedbackItem.tooltip")); // NOI18N
        feedBackItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedBackItem1ActionPerformed(evt);
            }
        });
        helpMenu2.add(feedBackItem1);
        helpMenu2.add(jSeparator12);

        AboutItem.setText(bundle1.getString("aboutItem.name")); // NOI18N
        AboutItem.setToolTipText(bundle1.getString("aboutItem.tooltip")); // NOI18N
        AboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutItemActionPerformed(evt);
            }
        });
        helpMenu2.add(AboutItem);

        jMenuBar1.add(helpMenu2);

        setJMenuBar(jMenuBar1);

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
    private void forumItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forumItem2ActionPerformed
        setPath("http://domainmathide.freeforums.org/");
    }//GEN-LAST:event_forumItem2ActionPerformed

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

    private void refreshItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshItemActionPerformed
        newContentPane.reload();
        newContentPane.reload();
    }//GEN-LAST:event_refreshItemActionPerformed

    private void printItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printItemActionPerformed
        try {
            newContentPane.table.print();
        } catch (PrinterException ex) {
            
        }
    }//GEN-LAST:event_printItemActionPerformed

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        dispose();
    }//GEN-LAST:event_exitItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DataViewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataViewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataViewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataViewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataViewFrame("").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem faqItem;
    private javax.swing.JMenuItem feedBackItem1;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem forumItem;
    private javax.swing.JMenuItem forumItem1;
    private javax.swing.JMenuItem forumItem2;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenu helpMenu1;
    private javax.swing.JMenu helpMenu2;
    private javax.swing.JMenuItem howToItem;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JMenu matrixMenu;
    private javax.swing.JMenuItem onlineHelpItem;
    private javax.swing.JMenuItem printItem;
    private javax.swing.JMenuItem refreshItem;
    private javax.swing.JMenuItem reportBugItem1;
    private org.domainmath.gui.StatusPanel statusPanel1;
    private javax.swing.JMenuItem suggestionsItem;
    // End of variables declaration//GEN-END:variables
}
