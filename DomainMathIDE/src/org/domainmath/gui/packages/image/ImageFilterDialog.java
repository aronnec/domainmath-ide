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

package org.domainmath.gui.packages.image;

import java.awt.event.ItemEvent;
import java.util.List;
import javax.swing.DefaultListModel;
import org.domainmath.gui.MainFrame;


public class ImageFilterDialog extends javax.swing.JDialog {
    private final DefaultListModel model;
    private final List varNames;

    /**
     * Creates new form ImageFilterDialog
     */
    public ImageFilterDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        model = new DefaultListModel();
        varNames = MainFrame.varView.getVarNames();
        for(int i=0;i<varNames.size();i++) {
            model.addElement(varNames.get(i));
        }
        this.imageVarList.setModel(model);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        imageRadioButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        imagePathTextField = new javax.swing.JTextField();
        browseImageButton = new javax.swing.JButton();
        octaveImageVarRadioButton = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        imageVarList = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        imageVarTextField = new javax.swing.JTextField();
        createImageVarButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        spFilterComboBox = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        optionsComboBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        outputVarTextField = new javax.swing.JTextField();
        imShowCheckBox = new javax.swing.JCheckBox();
        cancelButton = new javax.swing.JButton();
        OKButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Image Filter");
        setResizable(false);

        buttonGroup1.add(imageRadioButton);
        imageRadioButton.setSelected(true);
        imageRadioButton.setText("Image:");
        imageRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                imageRadioButtonItemStateChanged(evt);
            }
        });

        jLabel1.setText("Image Path:");

        browseImageButton.setText("Browse");

        buttonGroup1.add(octaveImageVarRadioButton);
        octaveImageVarRadioButton.setText("Octave Workspace Variable:");
        octaveImageVarRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                octaveImageVarRadioButtonItemStateChanged(evt);
            }
        });

        imageVarList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        imageVarList.setEnabled(false);
        jScrollPane1.setViewportView(imageVarList);

        jLabel2.setText("Octave Variable Name:");

        createImageVarButton.setText("Create");

        jLabel3.setText("Spatial Filter:");

        jButton1.setText("Create");

        jRadioButton1.setText("Options:");
        jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton1ItemStateChanged(evt);
            }
        });

        optionsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Image is padded symmetrically", "Image is padded using the border of image", "Image is padded by circular repeating of image elements", "Size of  output image is the same as  input image", "Display full filtering result", "Perform filtering using correlation", "Perform filtering using convolution " }));
        optionsComboBox.setEnabled(false);

        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setText("Output Variables");

        jLabel5.setText("Linear Filtering:");

        outputVarTextField.setText("J");

        imShowCheckBox.setSelected(true);
        imShowCheckBox.setText("Display output as image");

        cancelButton.setText("Cancel");

        OKButton.setText("OK");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addGap(22, 22, 22))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(imagePathTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                    .addComponent(imageVarTextField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(browseImageButton)
                                    .addComponent(createImageVarButton, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(85, 85, 85)
                        .addComponent(spFilterComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(imageRadioButton)
                            .addComponent(octaveImageVarRadioButton)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel5)))
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(imShowCheckBox)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(optionsComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(outputVarTextField)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(OKButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {browseImageButton, createImageVarButton});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {OKButton, cancelButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(imagePathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseImageButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(imageVarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createImageVarButton))
                .addGap(18, 18, 18)
                .addComponent(octaveImageVarRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spFilterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(optionsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(outputVarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imShowCheckBox)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(OKButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {browseImageButton, createImageVarButton});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {OKButton, cancelButton});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void imageRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_imageRadioButtonItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) {
             this.browseImageButton.setEnabled(true);
             this.createImageVarButton.setEnabled(true);
            this.imagePathTextField.setEnabled(true);
            this.imageVarList.setEnabled(false);
        }
    }//GEN-LAST:event_imageRadioButtonItemStateChanged

    private void octaveImageVarRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_octaveImageVarRadioButtonItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) {
            this.imageVarList.setEnabled(true);
            this.browseImageButton.setEnabled(false);
            this.imagePathTextField.setEnabled(false);
            this.createImageVarButton.setEnabled(false);
            
        }
    }//GEN-LAST:event_octaveImageVarRadioButtonItemStateChanged

    private void jRadioButton1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton1ItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) {
            this.optionsComboBox.setEnabled(true);
        }
    }//GEN-LAST:event_jRadioButton1ItemStateChanged

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
            java.util.logging.Logger.getLogger(ImageFilterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ImageFilterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ImageFilterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImageFilterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ImageFilterDialog dialog = new ImageFilterDialog(new javax.swing.JFrame(), true);
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OKButton;
    private javax.swing.JButton browseImageButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton createImageVarButton;
    private javax.swing.JCheckBox imShowCheckBox;
    private javax.swing.JTextField imagePathTextField;
    private javax.swing.JRadioButton imageRadioButton;
    private javax.swing.JList imageVarList;
    private javax.swing.JTextField imageVarTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton octaveImageVarRadioButton;
    private javax.swing.JComboBox optionsComboBox;
    private javax.swing.JTextField outputVarTextField;
    private javax.swing.JComboBox spFilterComboBox;
    // End of variables declaration//GEN-END:variables
}
