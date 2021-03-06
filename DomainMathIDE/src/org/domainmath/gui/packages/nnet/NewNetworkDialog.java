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


public class NewNetworkDialog extends javax.swing.JDialog {

    /**
     * Creates new form NewNetworkDialog
     */
    public static int netIndex=1;
    public NewNetworkDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
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
        prTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ssTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        trfTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btfTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        blfTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        pfTextField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        OkButton = new javax.swing.JButton();
        helpButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Network");
        setResizable(false);

        jLabel1.setText("Input Range:");

        prTextField.setToolTipText("<html><b>Pr</b> - R x 2 matrix of min and max values for R input elements</html>");

        jLabel2.setText("No. of Neurons:");

        ssTextField.setToolTipText("<html><b>Ss</b> - 1 x Ni row vector with size of ith layer, for N layers</html>");

        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Function");

        jLabel4.setText("Transfer Function:");

        trfTextField.setText("\"tansig\"");
        trfTextField.setToolTipText("<html><b>trf</b> - 1 x Ni list with transfer function of ith layer,<br>default = \"tansig\"</html>");

        jLabel5.setText("Network Training Function:");

        btfTextField.setText("\"trainlm\"");
        btfTextField.setToolTipText("<html><b>btf</b> - Batch network training function,<br>default = \"trainlm\"</html>");

        jLabel6.setText("Weight/Bias Learning Function:");

        blfTextField.setText("\"learngdm\"");
        blfTextField.setToolTipText("<html><b>blf</b> - Batch weight/bias learning function,<br>default = \"learngdm\"</html>");

        jLabel7.setText("Performance Function:");

        pfTextField.setText("\"mse\"");
        pfTextField.setToolTipText("<html><b>pf</b>  - Performance function,<br>default = \"mse\"</html>");

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
        helpButton.setToolTipText("<html><div class=\"defun\">\n<p class=\"functionfile\"> Function File: <var>net</var>\n<b>=</b><var> <span style=\"font-weight: bold;\">newff</span>\n</var>(<var>Pr,ss,trf,btf,blf,pf</var>)<var><a\n name=\"index-g_t_003d-1\"></a></var><br>\n</p>\n<blockquote>\n  <p><code>newff</code> create a feed-forward\nbackpropagation network </p>\n  <pre class=\"example\">         <span\n style=\"font-weight: bold;\"> Pr</span> - R x 2 matrix of min and max values for R input elements<br>          <span\n style=\"font-weight: bold;\">Ss</span> - 1 x Ni row vector with size of ith layer, for N layers<br>          <span\n style=\"font-weight: bold;\">trf</span> - 1 x Ni list with transfer function of ith layer,<br>                default = \"tansig\"<br>          <span\n style=\"font-weight: bold;\">btf</span> - Batch network training function,<br>                default = \"trainlm\"<br>          <span\n style=\"font-weight: bold;\">blf</span> - Batch weight/bias learning function,<br>                default = \"learngdm\"<br>          <span\n style=\"font-weight: bold;\">pf</span>  - Performance function,<br>                default = \"mse\".<br></pre>\n  <pre class=\"example\">          EXAMPLE 1<br>          Pr = [0.1 0.8; 0.1 0.75; 0.01 0.8];<br>               it's a 3 x 2 matrix, this means 3 input neurons<br>          <br>          net = newff(Pr, [4 1], {\"tansig\",\"purelin\"}, \"trainlm\", \"learngdm\", \"mse\");<br></pre>\n</blockquote>\n</div></html>\n");
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ssTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                            .addComponent(prTextField)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(trfTextField)
                            .addComponent(btfTextField)
                            .addComponent(blfTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                            .addComponent(pfTextField)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(helpButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(OkButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {prTextField, ssTextField, trfTextField});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {OkButton, cancelButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(prTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ssTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(trfTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(btfTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(blfTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pfTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(OkButton)
                    .addComponent(helpButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {prTextField, ssTextField, trfTextField});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {OkButton, cancelButton});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void OkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkButtonActionPerformed
        String pr = this.prTextField.getText();
        String ss = this.ssTextField.getText();
       
        if(!pr.equals("") || !ss.equals("")) {
            String rowData[] ={"net"+netIndex,pr,ss,trfTextField.getText(),this.btfTextField.getText(),this.blfTextField.getText(),this.pfTextField.getText()};
            NnetFrame.model.addRow(rowData);
            MainFrame.octavePanel.evaluate("pkg load nnet");
            NnetFrame.declare("Pr", pr);
            NnetFrame.declare("ss", ss);
            NnetFrame.declare("trf", this.trfTextField.getText());
            NnetFrame.declare("btf", this.btfTextField.getText());
            NnetFrame.declare("blf", this.blfTextField.getText());
            NnetFrame.declare("pf", this.pfTextField.getText());
            MainFrame.octavePanel.evalWithOutput("net"+netIndex+" = newff (Pr,ss,trf,btf,blf,pf)");
           
             
            
            dispose();
            MainFrame.octavePanel.evaluate(jar_path);
            MainFrame.octavePanel.evaluate("ob= javaObject('ResultsFrame','');");
            MainFrame.octavePanel.evaluate("ob.appendText(disp(net"+netIndex+"));");
            MainFrame.octavePanel.evaluate("clear 'ob'");
             netIndex++;
           
        }
            
        
    }//GEN-LAST:event_OkButtonActionPerformed

    public static void minusIndex() {
        netIndex--;
    }
    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        MainFrame.octavePanel.evaluate("pkg load nnet");
        MainFrame.octavePanel.evaluate("helpdlg(help('newff'));");
    }//GEN-LAST:event_helpButtonActionPerformed

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
            java.util.logging.Logger.getLogger(NewNetworkDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewNetworkDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewNetworkDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewNetworkDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewNetworkDialog dialog = new NewNetworkDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField blfTextField;
    private javax.swing.JTextField btfTextField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton helpButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField pfTextField;
    private javax.swing.JTextField prTextField;
    private javax.swing.JTextField ssTextField;
    private javax.swing.JTextField trfTextField;
    // End of variables declaration//GEN-END:variables
}
