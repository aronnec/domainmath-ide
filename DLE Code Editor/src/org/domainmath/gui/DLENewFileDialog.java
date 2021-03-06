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

package org.domainmath.gui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;


public class DLENewFileDialog extends javax.swing.JDialog {
  
    private  DLECodeEditorFrame frame;
    private String fname;
    private String file_name;
   
    public DLENewFileDialog(DLECodeEditorFrame frame, boolean modal,String dynareOptions, String dynarePath) {
        super(frame, modal);
        initComponents();
        this.frame =frame;
       
       this.NewDocListener();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New File");

        jLabel1.setText("New File:");

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "C File", "CPP File", "Header File", "Fortran File", "Text File" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        jLabel2.setText("File Name with path:");

        jTextField1.setEditable(false);

        jButton1.setText("...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("OK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton2, jButton3});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

     public void browse(String desc, String ext, String... exts) {
        JFileChooser fc = new JFileChooser();

        
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        desc, exts);
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);
        fc.setAcceptAllFileFilterUsed(false);
        
        File file;
        int returnVal = fc.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                
                    file = fc.getSelectedFile();
                    file_name = file.getName();
                    
                    if(!file_name.endsWith(ext)) {
                        this.jTextField1.setText(path+ext);
                    }else {
                        this.jTextField1.setText(path);
                    }
                    
                 
        }
    }
     private void NewDocListener() {
         if(this.jTextField1.getText().equals("")) {
            this.jButton3.setEnabled(false);
           
         }
        jTextField1.getDocument().addDocumentListener(
                new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                
               jButton3.setEnabled(true);
             
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(jTextField1.getText().equals("")) {
                    jButton3.setEnabled(false);
                }
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                jButton3.setEnabled(true);
           }
                    
       });
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         int opt = this.jList1.getSelectedIndex();
        if(opt == 0) {
            browse("C-Files  (*.c)", ".c","c");
        }else if(opt == 1) {
            browse( "CPP-Files  (*.cpp)", ".cpp","cpp");
        }else if(opt == 2) {
            browse("Header-Files  (*.h)", ".h", "h");
        }
        else if(opt == 3) {
            browse("Fortran-Files  (*.f)", ".f", "f");
        }else if(opt == 3) {
            browse("Text Files   (*txt)",".txt","txt");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public void setFile(File file, int index) {
        frame.setUpArea();
                fname= file.getName();
                if(fname.endsWith(".c")  ) {
                         frame.area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
                         frame.area.setCodeFoldingEnabled(true);
                    }else if(fname.endsWith(".cpp")|| fname.endsWith(".cc") || fname.endsWith(".C")) {
                         frame.area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
                        frame.area.setCodeFoldingEnabled(true);
                        
                    }else if(fname.endsWith(".f") || fname.endsWith(".F") || fname.endsWith(".f90") || fname.endsWith(".F90")) {
                         frame.area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_FORTRAN);
                         
                       
                    }else if(fname.endsWith(".h")) {
                         frame.area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
                        frame.area.setCodeFoldingEnabled(true);
                        
                    }
                    else {
                         frame.area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
                         
                    }
               
                frame.fileTab.addTab(file.getName(), frame.scroll);
                frame.initTabComponent(DLECodeEditorFrame.file_index);
                frame.fileTab.setToolTipTextAt(DLECodeEditorFrame.file_index, file.getAbsolutePath());
                frame.addFileNameToList(file.getAbsolutePath());
                frame.fileTab.setSelectedIndex(DLECodeEditorFrame. file_index);
                
                DLECodeEditorFrame.file_index++;
                
                frame.dirty();
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
                    
                    if(jList1.getSelectedIndex() == 0) {
                           setFile(new File(this.jTextField1.getText()),frame.fileTab.getTabRunCount());
                          
                    }else if(jList1.getSelectedIndex() == 1) {
                            setFile(new File(this.jTextField1.getText()),frame.fileTab.getTabRunCount());
                         
                        
                       
                         
                    }else if(jList1.getSelectedIndex() == 2) {
                           setFile(new File(this.jTextField1.getText()),frame.fileTab.getTabRunCount());
                         
                         
                    }else if(jList1.getSelectedIndex() == 3) {
                             setFile(new File(this.jTextField1.getText()),frame.fileTab.getTabRunCount());
                         
                         
                    }
                    else if(jList1.getSelectedIndex() == 4) {
                        setFile(new File(this.jTextField1.getText()),frame.fileTab.getTabRunCount());
                         
                    }else if(jList1.getSelectedIndex() == 5) {
                        setFile(new File(this.jTextField1.getText()),frame.fileTab.getTabRunCount());
                         
                    }
                    
                   
                    dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
