
/*
 * Copyright (C) 2011 Vinu K.N
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

package org.domainmath.gui.references;



import java.io.File;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.domainmath.gui.MainFrame;



/**
 *
 * @author Vinu K.N
 */
public class RefDlg extends javax.swing.JDialog {
    private FileSaver saver;
    private final MainFrame frame;
    
    /** Creates new form SaveDialog */
    
    
    public RefDlg(MainFrame frame, boolean modal) {
        super(frame, modal);
        this.frame = frame;
        initComponents();
        
        RefDocListener();
        
      
    }

 private void RefDocListener() {
         if(this.jTextField2.getText().equals("") || this.fileNameTextField.getText().equals("")) {
            this.addButton.setEnabled(false);
            
         }
        this.fileNameTextField.getDocument().addDocumentListener(
                new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                
                 addButton.setEnabled(true);
             
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(fileNameTextField.getText().equals("")) {
                    addButton.setEnabled(false);
                }
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
                addButton.setEnabled(true);
           }
                    
       });
        
         this.jTextField2.getDocument().addDocumentListener(
                new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                
                 addButton.setEnabled(true);
             
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(jTextField2.getText().equals("")) {
                    addButton.setEnabled(false);
                }
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
                addButton.setEnabled(true);
           }
                    
       });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileNameLabel = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        fileNameTextField = new javax.swing.JTextField();
        modeLabel = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        browseButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("References");

        fileNameLabel.setText("File Name:");

        modeLabel.setText("Title:");

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        browseButton.setText("Browse");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(modeLabel)
                            .addComponent(fileNameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(fileNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(browseButton)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addButton, browseButton, jButton1});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modeLabel)
                    .addComponent(jTextField2))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fileNameLabel)
                        .addComponent(fileNameTextField))
                    .addComponent(browseButton))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addButton, browseButton, jButton1});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        Preferences pr = Preferences.userNodeForPackage(frame.getClass());
         
       String plist=pr.get("Ref_list", null);
        
        System.out.println(pr.get("Ref_list", null));
        if(!this.fileNameTextField.getText().equals("") && !this.jTextField2.getText().equals("")) {
        saver = new FileSaver(frame);
        try {
            if(plist != null) {
                saver.saveData(this.jTextField2.getText()+"="+this.fileNameTextField.getText()+";"+plist);
                JOptionPane.showMessageDialog(rootPane, this.fileNameTextField.getText()+" has been added to the document list.");
            }else{
                 saver.saveData(this.jTextField2.getText()+"="+this.fileNameTextField.getText()+";");
                JOptionPane.showMessageDialog(rootPane, this.fileNameTextField.getText()+" has been added to the document list.");
        
            }
        
      
        }catch(Exception e) {
            saver.saveData(this.jTextField2.getText()+"="+this.fileNameTextField.getText()+";");   
            JOptionPane.showMessageDialog(rootPane, this.fileNameTextField.getText()+" has been added to the document list.");
             
        }
        
         }
        else{
            JOptionPane.showMessageDialog(frame, "Please give both name and file path","Error",JOptionPane.ERROR_MESSAGE);
        }
        frame.makeMenu();
        dispose();
    }//GEN-LAST:event_addButtonActionPerformed

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(this);
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            fileNameTextField.setText(file.getAbsolutePath());
            
        }
        
        
   
    }//GEN-LAST:event_browseButtonActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    dispose();
}//GEN-LAST:event_jButton1ActionPerformed

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton browseButton;
    private javax.swing.JLabel fileNameLabel;
    private javax.swing.JTextField fileNameTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel modeLabel;
    // End of variables declaration//GEN-END:variables

}