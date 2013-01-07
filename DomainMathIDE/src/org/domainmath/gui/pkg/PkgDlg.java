
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



package org.domainmath.gui.pkg;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.domainmath.gui.MainFrame;



/**
 *
 * @author Vinu K.N
 */
public class PkgDlg extends javax.swing.JDialog {
    private DefaultListModel _model;
    private final MainFrame frame;

    /** Creates new form UnInstallDlg */
    public PkgDlg(MainFrame frame, boolean modal) {
        super(frame, modal);
        this.frame = frame;
        initComponents();
       
       
       
       UnLoadButton.setEnabled(true);
        UnInstallButton.setEnabled(true);
        unloadAllButton.setEnabled(true);
        loadButton.setEnabled(true);
        loadAllButton.setEnabled(true);
        //listButton.setEnabled(false);
       

        String  cmd = frame.getOctavePath()+" -qf --eval  "+Character.toString('"')+
                    "OCTAVE_HOME ()"+
                     Character.toString('"');

      
       String line;
        try {

            Process p = Runtime.getRuntime().exec(cmd);

             BufferedReader br =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader br2 =
                    new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    line =br.readLine();
                     String path;
                        String sep =File.separator;
                        File f;
                        File f2[];
                        String s[];
                      path= line.substring(line.indexOf('=')+1,line.length()).trim();

                    
                     f = new File(path+sep+"share"+sep+"octave"+sep+"packages"+sep);

                        f2 =f.listFiles();
                        _model = new DefaultListModel();
                        pkgList.setModel(_model);

                     for( int i=0; i<f2.length; i++) {
                       _model.addElement(f2[i].getName());
                     }
         

            while((line=br2.readLine()) != null) {

                  System.out.println(line);

            }

        }
            catch(Exception ex) {
                
        }

        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pkgList = new javax.swing.JList();
        UnLoadButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        listButton = new javax.swing.JButton();
        unloadAllButton = new javax.swing.JButton();
        UnInstallButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        loadAllButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pathField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        optionsCombo = new javax.swing.JComboBox();
        genButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(" Package");
        setName("UnInstdialog"); // NOI18N

        pkgList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        pkgList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                pkgListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(pkgList);

        UnLoadButton.setText("Unload");
        UnLoadButton.setEnabled(false);
        UnLoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnLoadButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Close");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        listButton.setText("List");
        listButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listButtonActionPerformed(evt);
            }
        });

        unloadAllButton.setText("Unload All");
        unloadAllButton.setEnabled(false);
        unloadAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unloadAllButtonActionPerformed(evt);
            }
        });

        UnInstallButton.setText("Uninstall");
        UnInstallButton.setEnabled(false);
        UnInstallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnInstallButtonActionPerformed(evt);
            }
        });

        loadButton.setText("Load");
        loadButton.setEnabled(false);
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        loadAllButton.setText("Load All");
        loadAllButton.setEnabled(false);
        loadAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadAllButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Generate doc"));

        jLabel1.setText("Output directory:");

        browseButton.setText("Browse");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Options:");

        optionsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "octave-forge", "octave", "docbrowser" }));

        genButton.setText("Generate");
        genButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pathField, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(optionsCombo, 0, 286, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(genButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(browseButton, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {browseButton, genButton});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(optionsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(genButton))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {browseButton, genButton});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(UnInstallButton)
                                            .addComponent(listButton)
                                            .addComponent(cancelButton))
                                        .addComponent(loadAllButton))
                                    .addComponent(loadButton))
                                .addComponent(UnLoadButton))
                            .addComponent(unloadAllButton))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {UnInstallButton, UnLoadButton, cancelButton, listButton, loadAllButton, loadButton, unloadAllButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(listButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UnInstallButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                        .addComponent(loadButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadAllButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UnLoadButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(unloadAllButton)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {UnInstallButton, UnLoadButton, cancelButton, listButton, loadAllButton, loadButton, unloadAllButton});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listButtonActionPerformed
        
    }//GEN-LAST:event_listButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
       dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void UnLoadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnLoadButtonActionPerformed
        try {
        int i =JOptionPane.showConfirmDialog(frame,
                    "Unload?\n"+_model.getElementAt(pkgList.getSelectedIndex()).toString(), "Domain", JOptionPane.YES_NO_OPTION);
                   if(i == JOptionPane.YES_OPTION){
                       String pkg = _model.getElementAt(pkgList.getSelectedIndex()).toString();

                       String comd ="pkg unload "+pkg.substring(0, pkg.indexOf("-"));
                     MainFrame.octavePanel.evaluate(comd+";");
                    }

        }
        catch(Exception e) {
             JOptionPane.showMessageDialog(frame,"Package not selected","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_UnLoadButtonActionPerformed

    private void unloadAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unloadAllButtonActionPerformed
         try {
        int i =JOptionPane.showConfirmDialog(frame,
                    "Unload all packages?", "Domain", JOptionPane.YES_NO_OPTION);
                   if(i == JOptionPane.YES_OPTION){


                       String comd ="pkg unload all ";
                   
                        MainFrame.octavePanel.evaluate(comd+";");
        
                   }

        }
        catch(Exception e) {
             JOptionPane.showMessageDialog(frame,e.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_unloadAllButtonActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
         try {
        int i =JOptionPane.showConfirmDialog(frame,
                    "Load?\n"+_model.getElementAt(pkgList.getSelectedIndex()).toString(), "Domain", JOptionPane.YES_NO_OPTION);
                   if(i == JOptionPane.YES_OPTION){
                       
       

            String pkg = _model.getElementAt(pkgList.getSelectedIndex()).toString();

                       String comd ="pkg load "+pkg.substring(0, pkg.indexOf("-"));

                       
                   MainFrame.octavePanel.evaluate(comd+";");
                  

        



     }

        }
        catch(Exception e) {
             JOptionPane.showMessageDialog(frame,"Package not selected","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_loadButtonActionPerformed

    private void loadAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadAllButtonActionPerformed
        try {
        int i =JOptionPane.showConfirmDialog(frame,
                    "Load all packages?", "Domain", JOptionPane.YES_NO_OPTION);
                   if(i == JOptionPane.YES_OPTION){


                       String comd ="pkg load all ";
                      MainFrame.octavePanel.evaluate(comd+";");


                   }

        }
        catch(Exception e) {
             JOptionPane.showMessageDialog(frame,e.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_loadAllButtonActionPerformed

    private void UnInstallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnInstallButtonActionPerformed
          try {
        int i =JOptionPane.showConfirmDialog(frame,
                    "Uninstall?\n"+_model.getElementAt(pkgList.getSelectedIndex()).toString(), "Domain", JOptionPane.YES_NO_OPTION);
                   if(i == JOptionPane.YES_OPTION){
                       String pkg = _model.getElementAt(pkgList.getSelectedIndex()).toString();

                       String comd ="pkg uninstall "+pkg.substring(0, pkg.indexOf("-"));
                     MainFrame.octavePanel.evaluate(comd+";");
                    
      
              }
        }
        catch(Exception e) {
             JOptionPane.showMessageDialog(frame,"Package not selected","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_UnInstallButtonActionPerformed

    public  void setArea() {
        String pkg = _model.getElementAt(pkgList.getSelectedIndex()).toString();
         String comd2 =pkg.substring(0, pkg.indexOf("-"));
         String comd ="pkg ("+"'"+"describe"+"'"+","
                 +"'"+comd2+"'"+")";
         String  cmd = frame.getOctavePath()+" -qf --eval  "+Character.toString('"')+
                    comd+
                     Character.toString('"');

      
       String line="";

        try {

            Process p = Runtime.getRuntime().exec(cmd);

             BufferedReader br =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader br2 =
                    new BufferedReader(new InputStreamReader(p.getErrorStream()));

             String s="";
            while((line=br.readLine()) != null) {
                s += line+"\n";

            }


            while((line=br2.readLine()) != null) {
                 s += line+"\n";
            }
           
             JOptionPane.showMessageDialog(frame,s);

        }
            catch(Exception ex) {
            System.out.println(ex.toString());
        }

    }
    private void pkgListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_pkgListValueChanged
       //  setArea();

    }//GEN-LAST:event_pkgListValueChanged
 public  void browse(){
        JFileChooser fc = new JFileChooser();

        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File file = null;
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
                 file = fc.getSelectedFile();
                 pathField.setText(file.getAbsolutePath());

            } 

    }
    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        
        browse();
       
       
    }//GEN-LAST:event_browseButtonActionPerformed

    private void genButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genButtonActionPerformed

       String path = pathField.getText();
       String text = _model.getElementAt(pkgList.getSelectedIndex()).toString();
       String pkg = text.substring(0,text.indexOf("-") );
       String opt = (String) optionsCombo.getSelectedItem();
       String options = "options = get_html_options("+Character.toString('"')+opt+
      Character.toString('"')+         ");";
       String cmd =options+"generate_package_html("+
               Character.toString('"')+pkg+Character.toString('"')+
               ", "+
               Character.toString('"')+path+Character.toString('"')+
               ", "+
               "options"+
               ");";

       
       MainFrame.octavePanel.evaluate("pkg load generate_html;"+cmd);
       System.out.println(cmd);
      
       dispose();
    }//GEN-LAST:event_genButtonActionPerformed

 
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton UnInstallButton;
    private javax.swing.JButton UnLoadButton;
    private javax.swing.JButton browseButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton genButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton listButton;
    private javax.swing.JButton loadAllButton;
    private javax.swing.JButton loadButton;
    private javax.swing.JComboBox optionsCombo;
    private javax.swing.JTextField pathField;
    private javax.swing.JList pkgList;
    private javax.swing.JButton unloadAllButton;
    // End of variables declaration//GEN-END:variables

}
