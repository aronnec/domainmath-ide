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

package org.domainmath.gui.packages.nnet;

import java.io.File;
import org.domainmath.gui.MainFrame;

public class SimulateNetDialog extends javax.swing.JDialog {
    private final String net;

    /**
     * Creates new form SimulateNetDialog
     */
    public SimulateNetDialog(java.awt.Frame parent, boolean modal,String net) {
        super(parent, modal);
        initComponents();
        this.net =net;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        OkButton = new javax.swing.JButton();
        helpButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Simulate Network");
        setResizable(false);

        jLabel1.setText("Input Data Set:");

        jTextField1.setToolTipText("<html><div class=\"defun\">\n<div class=\"defun\">\n<p class=\"functionfile\"> Function File: <var>netoutput</var>\n= <b>sim</b> (<var>net, mInput</var>)<var><a\n name=\"index-sim-1\"></a></var><br>\n</p>\n<blockquote>\n  <p><code>sim</code> is usuable to simulate a before\ndefined neural network. <code>net</code> is created with\nnewff(<small class=\"dots\">...</small>) and <var>mInput</var>\nshould be the\ncorresponding input data set! </p>\n</blockquote></html>\n");

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        OkButton.setText("OK");
        OkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkButtonActionPerformed(evt);
            }
        });

        helpButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/help-browser.png"))); // NOI18N
        helpButton.setToolTipText("<html><div class=\"defun\">\n<div class=\"defun\">\n<p class=\"functionfile\"> Function File: <var>netoutput</var>\n= <b>sim</b> (<var>net, mInput</var>)<var><a\n name=\"index-sim-1\"></a></var><br>\n</p>\n<blockquote>\n  <p><code>sim</code> is usuable to simulate a before\ndefined neural network. <code>net</code> is created with\nnewff(<small class=\"dots\">...</small>) and <var>mInput</var>\nshould be the\ncorresponding input data set! </p>\n</blockquote></html>\n");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(helpButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                        .addComponent(OkButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(OkButton)
                    .addComponent(helpButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
       MainFrame.octavePanel.evaluate("pkg load nnet");
        MainFrame.octavePanel.evaluate("helpdlg(help('sim'));");
    }//GEN-LAST:event_helpButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void OkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkButtonActionPerformed
        if(!this.jTextField1.getText().equals("")) {
            NnetFrame.declare("mInput", this.jTextField1.getText());
            MainFrame.octavePanel.evaluate("pkg load nnet");
            MainFrame.octavePanel.evaluate("netoutput = sim ("+net+", mInput)");
            MainFrame.octavePanel.evaluate(jar_path);
            MainFrame.octavePanel.evaluate("ob= javaObject('ResultsFrame','');");
            MainFrame.octavePanel.evaluate("ob.appendText(disp(netoutput));");
            MainFrame.octavePanel.evaluate("clear 'ob'");
            dispose();
        }
        
    }//GEN-LAST:event_OkButtonActionPerformed

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
            java.util.logging.Logger.getLogger(SimulateNetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SimulateNetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SimulateNetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SimulateNetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SimulateNetDialog dialog = new SimulateNetDialog(new javax.swing.JFrame(), true,"");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
     public String jar_path = "javaaddpath('"+System.getProperty("user.dir")+File.separator+"Results.jar');";

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OkButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton helpButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
