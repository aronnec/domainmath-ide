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

package org.domainmath.gui.arrayeditor;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.StatusPanel;
import org.domainmath.gui.about.AboutDlg;



public class ArrayEditorFrame extends javax.swing.JFrame {
    private final JTable table;
    private final DefaultTableModel model;
    private final StatusPanel status_panel;

    
    
    public ArrayEditorFrame() {
        this.setIconImage(icon);
       // this.setLocationRelativeTo(frame);
        
        initComponents();
        
        model = new DefaultTableModel(0,1);
        
        table = new JTable(model);
        //table.setAutoCreateColumnsFromModel(true);
         table.setRowHeight(20);
         table.getTableHeader().setReorderingAllowed(false);
       table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         table.setColumnSelectionAllowed(true);
         table.setRowSelectionAllowed(true);
        JScrollPane scrollPane = new JScrollPane(table);
        
        
         add(scrollPane,BorderLayout.CENTER);
         status_panel=new StatusPanel();
         add(status_panel,BorderLayout.PAGE_END);
         pack();
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
        savVarButton = new javax.swing.JButton();
        printButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        addRowButton = new javax.swing.JButton();
        removeRowButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        addColumnButton = new javax.swing.JButton();
        removeColumnButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        saveVarItem = new javax.swing.JMenuItem();
        printItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        addRowItem = new javax.swing.JMenuItem();
        removeRowItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        addColumnItem = new javax.swing.JMenuItem();
        removeColumnItem = new javax.swing.JMenuItem();
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
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en"); // NOI18N
        setTitle(bundle.getString("ArrayEditor.title")); // NOI18N
        setLocationByPlatform(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jToolBar1.setRollover(true);
        jToolBar1.setName("Standard"); // NOI18N

        savVarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-save.png"))); // NOI18N
        savVarButton.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en").getString("saveVarItem.mnemonic").charAt(0));
        savVarButton.setToolTipText(bundle.getString("saveVarItem.tooltip")); // NOI18N
        savVarButton.setFocusable(false);
        savVarButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        savVarButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        savVarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savVarButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(savVarButton);

        printButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-print2.png"))); // NOI18N
        printButton.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en").getString("printItem.mnemonic").charAt(0));
        printButton.setToolTipText(bundle.getString("printItem.tooltip")); // NOI18N
        printButton.setFocusable(false);
        printButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        printButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(printButton);
        jToolBar1.add(jSeparator2);

        addRowButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/row_insert.png"))); // NOI18N
        addRowButton.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en").getString("addRowItem.mnemonic").charAt(0));
        addRowButton.setToolTipText(bundle.getString("addRowItem.tooltip")); // NOI18N
        addRowButton.setFocusable(false);
        addRowButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addRowButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(addRowButton);

        removeRowButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/row_delete.png"))); // NOI18N
        removeRowButton.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en").getString("removeRowItem.mnemonic").charAt(0));
        removeRowButton.setToolTipText(bundle.getString("removeRowItem.tooltip")); // NOI18N
        removeRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRowButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(removeRowButton);
        jToolBar1.add(jSeparator3);

        addColumnButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/insert-column.png"))); // NOI18N
        addColumnButton.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en").getString("addColumnItem.mnemonic").charAt(0));
        addColumnButton.setFocusable(false);
        addColumnButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addColumnButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addColumnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addColumnButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(addColumnButton);

        removeColumnButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/delete-column.png"))); // NOI18N
        removeColumnButton.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en").getString("removeColumnItem.tooltip").charAt(0));
        removeColumnButton.setToolTipText(bundle.getString("removeColumnItem.tooltip")); // NOI18N
        removeColumnButton.setFocusable(false);
        removeColumnButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeColumnButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeColumnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeColumnButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(removeColumnButton);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jMenu1.setText(bundle.getString("fileMenu.name")); // NOI18N

        saveVarItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveVarItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-save.png"))); // NOI18N
        saveVarItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en").getString("saveVarItem.mnemonic").charAt(0));
        saveVarItem.setText(bundle.getString("saveVarItem.name")); // NOI18N
        saveVarItem.setToolTipText(bundle.getString("saveVarItem.tooltip")); // NOI18N
        saveVarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveVarItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveVarItem);

        printItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-print2.png"))); // NOI18N
        printItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en").getString("printItem.mnemonic").charAt(0));
        printItem.setText(bundle.getString("printItem.name")); // NOI18N
        printItem.setToolTipText(bundle.getString("printItem.tooltip")); // NOI18N
        printItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printItemActionPerformed(evt);
            }
        });
        jMenu1.add(printItem);

        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        exitItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en").getString("exitItem.mnemonic").charAt(0));
        exitItem.setText(bundle.getString("exitItem.name")); // NOI18N
        exitItem.setToolTipText(bundle.getString("exitItem.tooltip")); // NOI18N
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        jMenu1.add(exitItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText(bundle.getString("InsertMenu.name")); // NOI18N

        addRowItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        addRowItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/row_insert.png"))); // NOI18N
        addRowItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en").getString("addRowItem.mnemonic").charAt(0));
        addRowItem.setText(bundle.getString("addRowItem.name")); // NOI18N
        addRowItem.setToolTipText(bundle.getString("addRowItem.tooltip")); // NOI18N
        addRowItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowItemActionPerformed(evt);
            }
        });
        jMenu2.add(addRowItem);

        removeRowItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        removeRowItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/row_delete.png"))); // NOI18N
        removeRowItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en").getString("removeRowItem.mnemonic").charAt(0));
        removeRowItem.setText(bundle.getString("removeRowItem.name")); // NOI18N
        removeRowItem.setToolTipText(bundle.getString("removeRowItem.tooltip")); // NOI18N
        removeRowItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRowItemActionPerformed(evt);
            }
        });
        jMenu2.add(removeRowItem);
        jMenu2.add(jSeparator1);

        addColumnItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        addColumnItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/insert-column.png"))); // NOI18N
        addColumnItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en").getString("addColumnItem.mnemonic").charAt(0));
        addColumnItem.setText(bundle.getString("addColumnItem.name")); // NOI18N
        addColumnItem.setToolTipText(bundle.getString("addColumnItem.tooltip")); // NOI18N
        addColumnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addColumnItemActionPerformed(evt);
            }
        });
        jMenu2.add(addColumnItem);

        removeColumnItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        removeColumnItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/delete-column.png"))); // NOI18N
        removeColumnItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en").getString("removeColumnItem.mnemonic").charAt(0));
        removeColumnItem.setText(bundle.getString("removeColumnItem.name")); // NOI18N
        removeColumnItem.setToolTipText(bundle.getString("removeColumnItem.tooltip")); // NOI18N
        removeColumnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeColumnItemActionPerformed(evt);
            }
        });
        jMenu2.add(removeColumnItem);

        jMenuBar1.add(jMenu2);

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

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        this.dispose();
        
    }//GEN-LAST:event_exitItemActionPerformed

    
    private void addRowItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowItemActionPerformed
       addRow();
    }//GEN-LAST:event_addRowItemActionPerformed

    private void addColumnItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addColumnItemActionPerformed
        addColumn();
    }//GEN-LAST:event_addColumnItemActionPerformed

    private void removeRowItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRowItemActionPerformed
      removeRow();
        
    }//GEN-LAST:event_removeRowItemActionPerformed

    private void removeColumnItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeColumnItemActionPerformed
        
       removeColumn();
      
    }//GEN-LAST:event_removeColumnItemActionPerformed

    private void saveVar() {
        String name = JOptionPane.showInputDialog("Enter Variable Name:");
        StringBuilder s = new StringBuilder();
        s.append(name).append(" =[");
        
        int r=table.getRowCount();
        int c= table.getColumnCount();
       
        
        if(r>0 && c>=0) {
           for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                s.append(table.getValueAt(i, j));
                if (j == (table.getColumnCount() - 1)) {
                    s.append(";");
                } else {
                    s.append(",");
                }

            }

        }

            s.deleteCharAt(s.length() - 1);
            s.append("]");
            String cmd = s.toString();
            MainFrame.octavePanel.eval(cmd+";"); 
        }else{
             s.append("]");
            String cmd = s.toString();
            MainFrame.octavePanel.eval(cmd+";"); 
        }
        
         MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+MainFrame.parent_root+"DomainMath_OctaveVariables.dat',whos);");
        MainFrame.varView.reload();
    }
    private void saveVarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveVarItemActionPerformed
        saveVar();
    }//GEN-LAST:event_saveVarItemActionPerformed

    private void savVarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savVarButtonActionPerformed
        saveVar();
    }//GEN-LAST:event_savVarButtonActionPerformed

    private void printItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printItemActionPerformed
        printTable();
    }//GEN-LAST:event_printItemActionPerformed

    private void addRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowButtonActionPerformed
        addRow();
    }//GEN-LAST:event_addRowButtonActionPerformed

    private void removeRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRowButtonActionPerformed
        removeRow();
    }//GEN-LAST:event_removeRowButtonActionPerformed

    private void addColumnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addColumnButtonActionPerformed
        addColumn();
    }//GEN-LAST:event_addColumnButtonActionPerformed

    private void removeColumnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeColumnButtonActionPerformed
        removeColumn();
    }//GEN-LAST:event_removeColumnButtonActionPerformed
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

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
       printTable();
    }//GEN-LAST:event_printButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ArrayEditorFrame().setVisible(true);
            }
        });
    }
    private java.net.URL imgURL = getClass().getResource("resources/DomainMath.png");
    private Image icon = Toolkit.getDefaultToolkit().getImage(imgURL);
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private javax.swing.JButton addColumnButton;
    private javax.swing.JMenuItem addColumnItem;
    private javax.swing.JButton addRowButton;
    private javax.swing.JMenuItem addRowItem;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem faqItem;
    private javax.swing.JMenuItem feedBackItem1;
    private javax.swing.JMenuItem forumItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem howToItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem onlineHelpItem;
    private javax.swing.JButton printButton;
    private javax.swing.JMenuItem printItem;
    private javax.swing.JButton removeColumnButton;
    private javax.swing.JMenuItem removeColumnItem;
    private javax.swing.JButton removeRowButton;
    private javax.swing.JMenuItem removeRowItem;
    private javax.swing.JMenuItem reportBugItem1;
    private javax.swing.JButton savVarButton;
    private javax.swing.JMenuItem saveVarItem;
    private javax.swing.JMenuItem suggestionsItem;
    // End of variables declaration//GEN-END:variables

    private void printTable() {
        try {
            if (! table.print()) {
               System.err.println("User cancelled printing");
           }
        } catch (PrinterException ex) {
            Logger.getLogger(ArrayEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }

    private void addRow() {
         List data =Collections.synchronizedList(new ArrayList());
   
        for(int i=0;i<model.getColumnCount();i++) {
            data.add("0");
        }
        model.addRow(data.toArray());
    }

    private void removeColumn() {
          try {
           
           TableColumn c =table.getColumnModel().getColumn(table.getSelectedColumn());
           table.removeColumn(c);
           
       }catch(Exception e) {
           
       }
           
    }

    private void removeRow() {
         try {
           model.removeRow(table.getSelectedRow());
       }catch(Exception e) {
           
       }
    }

    private void addColumn() {
        List col =Collections.synchronizedList(new ArrayList());
   
        for(int i=0;i<model.getRowCount();i++) {
            col.add("0");
        }
        model.addColumn(null,col.toArray());
    }
}
