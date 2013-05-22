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
import java.awt.print.PrinterException;
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
public class ArrayEditorPanel extends javax.swing.JPanel {

    private final JTable table;
    private final DefaultTableModel model;
   
    public ArrayEditorPanel() {
        super(new BorderLayout());
        initComponents();
        model = new DefaultTableModel(0,1);
        
        table = new JTable(model);
        //table.setAutoCreateColumnsFromModel(true);
      
         table.getTableHeader().setReorderingAllowed(false);
       table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         table.setColumnSelectionAllowed(true);
         table.setRowSelectionAllowed(true);
        JScrollPane scrollPane = new JScrollPane(table);
        
        
         add(scrollPane,BorderLayout.CENTER);
         
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

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("Standard"); // NOI18N

        savVarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-save.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/arrayeditor/resources/ArrayEditor_en"); // NOI18N
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
        removeRowButton.setToolTipText(bundle.getString("removeRowItem.tooltip")); // NOI18N
        removeRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRowButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(removeRowButton);
        jToolBar1.add(jSeparator3);

        addColumnButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/insert-column.png"))); // NOI18N
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

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

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
        MainFrame.workspace.reload();
    }
    private void savVarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savVarButtonActionPerformed
        saveVar();
    }//GEN-LAST:event_savVarButtonActionPerformed

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

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
        printTable();
    }//GEN-LAST:event_printButtonActionPerformed

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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addColumnButton;
    private javax.swing.JButton addRowButton;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton printButton;
    private javax.swing.JButton removeColumnButton;
    private javax.swing.JButton removeRowButton;
    private javax.swing.JButton savVarButton;
    // End of variables declaration//GEN-END:variables
}
