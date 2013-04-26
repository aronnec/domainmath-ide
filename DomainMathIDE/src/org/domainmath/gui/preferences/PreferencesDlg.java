
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


package org.domainmath.gui.preferences;

import java.io.File;
import java.nio.file.Path;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.Util.DomainMathFileFilter;

/**
 *
 * @author Vinu K.N
 */
public class PreferencesDlg extends javax.swing.JDialog {
    private final MainFrame frame;

    /** Creates new form PreferencesDlg */
    public PreferencesDlg(MainFrame frame, boolean modal) {
        super(frame, modal);
        initComponents();
        
        this.frame = frame;
        cmdLinField.setText("--interactive --no-history --traditional");
        startupCmdField.setText("graphics_toolkit gnuplot;");
        setPath();
        setStartupDir();
        setCmdLineOptions();
        setStartupCmd();
    }

    private void setPath() {
        Preferences pr = Preferences.userNodeForPackage(frame.getClass());
        String path =pr.get("Octave_Path", null);
        try {
            if(path != null) {
                pathField.setText(path);
            }
            else {
                pathField.setText("");
            }
        }catch(Exception e) {
            pathField.setText("");
        }
    }
    private void setCmdLineOptions() {
        Preferences pr = Preferences.userNodeForPackage(frame.getClass());
        String path =pr.get("DomainMath_CmdLine", null);
        try {
            if(path != null ) {
                this.cmdLinField.setText(path);
            }
            else {
                cmdLinField.setText("--interactive --no-history --traditional");
            }
        }catch(Exception e) {
            cmdLinField.setText("--interactive --no-history --traditional");
        }
    }
    private void setStartupCmd() {
        Preferences pr = Preferences.userNodeForPackage(frame.getClass());
        String path =pr.get("DomainMath_StartUpCommand", null);
        try {
            if(path != null ) {
                this.startupCmdField.setText(path);
            }
            else {
                this.startupCmdField.setText("graphics_toolkit gnuplot;");
            } 
        }catch(Exception e) {
            this.startupCmdField.setText("graphics_toolkit gnuplot;");
        }
    }
    private void setStartupDir() {
        Preferences pr = Preferences.userNodeForPackage(frame.getClass());
        String path =pr.get("DomainMath_StartUpDir", null);
        try {
            if(path != null ) {
                this.defaultDirTextField.setText(path);
            }
            else {
                this.defaultDirTextField.setText(System.getProperty("user.dir"));
            } 
        }catch(Exception e) {
            this.defaultDirTextField.setText(System.getProperty("user.dir"));
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

        jLabel1 = new javax.swing.JLabel();
        pathField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        browseButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        startupCmdField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmdLinField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        defaultDirTextField = new javax.swing.JTextField();
        browseDefaultButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/preferences/resources/preferences_en"); // NOI18N
        setTitle(bundle.getString("Dialog.title")); // NOI18N

        jLabel1.setText(bundle.getString("label.title")); // NOI18N

        pathField.setToolTipText("If you are using Octave 3.7.2+, choose octave-cli.exe or octave-cli-3.7.2+.exe. Do not choose octave.exe or octave3.7.2+");

        cancelButton.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/preferences/resources/preferences_en").getString("cancelButton.mnemonic").charAt(0));
        cancelButton.setText(bundle.getString("cancelButton.title")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/preferences/resources/preferences_en").getString("okButton.mnemonic").charAt(0));
        okButton.setText(bundle.getString("okButton.title")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        browseButton.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/preferences/resources/preferences_en").getString("browseButton.mnemonic").charAt(0));
        browseButton.setText(bundle.getString("browseButton.title")); // NOI18N
        browseButton.setToolTipText("If you are using Octave 3.7.2+, choose octave-cli.exe or octave-cli-3.7.2+.exe. Do not choose octave.exe or octave3.7.2+");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        jLabel2.setText(bundle.getString("startuplabel.title")); // NOI18N

        jLabel3.setText(bundle.getString("commandLine.title")); // NOI18N

        cmdLinField.setText("--interactive");

        jLabel4.setText("Default Startup Directory:");

        browseDefaultButton.setText("Browse");
        browseDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseDefaultButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdLinField)
                    .addComponent(pathField)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(okButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(cancelButton))
                    .addComponent(startupCmdField)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(defaultDirTextField)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(browseButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(browseDefaultButton, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdLinField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(defaultDirTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(browseDefaultButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startupCmdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
         JFileChooser fc = new JFileChooser();
         fc.setFileFilter(DomainMathFileFilter.EXE_FILE_FILTER);
         fc.setAcceptAllFileFilterUsed(false);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        File file = null;
        int returnVal = fc.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
                 file = fc.getSelectedFile();
                 pathField.setText(file.getAbsolutePath());

            } 
}//GEN-LAST:event_browseButtonActionPerformed

private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
  
    dispose();
}//GEN-LAST:event_cancelButtonActionPerformed
public String getPath() {
   Preferences pr = Preferences.userNodeForPackage(frame.getClass());
   return pr.get("Octave_Path", null);
}
public String getCmdLineOptions() {
    Preferences pr = Preferences.userNodeForPackage(frame.getClass());
   return pr.get("DomainMath_CmdLine", null);
}
public String getStartupCmd() {
     Preferences pr = Preferences.userNodeForPackage(frame.getClass());
   return pr.get("DomainMath_StartUpCommand", null);
}
private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        Preferences pr = Preferences.userNodeForPackage(frame.getClass());
        
        String path = pathField.getText();
        String startup= this.startupCmdField.getText();
        String cmdline = this.cmdLinField.getText();
        String defaultDir = this.defaultDirTextField.getText();
        if(!startup.equals("")) {
            pr.put("DomainMath_StartUpCommand", startup);
         }
        if(!path.equals("")) {
            pr.put("Octave_Path",path );
        }else {
           JOptionPane.showMessageDialog(frame, "Please choose Octave path.","Error",JOptionPane.ERROR_MESSAGE);
        }
        if(!cmdline.equals("")) {
            pr.put("DomainMath_CmdLine", cmdline);
        }else {
            pr.put("DomainMath_CmdLine", "--interactive --traditional");
            this.cmdLinField.setText("--interactive --traditional");
        }
        if(!defaultDir.equals("")) {
            pr.put("DomainMath_StartUpDir", this.defaultDirTextField.getText());
        }else{
            pr.put("DomainMath_StartUpDir", System.getProperty("user.dir"));
        }
                
       JOptionPane.showMessageDialog(frame, "Please restart DomainMath to take the changes effect.","Information",JOptionPane.INFORMATION_MESSAGE);    
       
     dispose();
}//GEN-LAST:event_okButtonActionPerformed

    private void browseDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseDefaultButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setMultiSelectionEnabled(false);
        
        Path browse_file;
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
                 browse_file = fc.getSelectedFile().toPath();
                 MainFrame.setDir(browse_file.toString());
                MainFrame.requestToChangeDir(browse_file.toString()); 
                
            } 
    }//GEN-LAST:event_browseDefaultButtonActionPerformed

    /**
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JButton browseDefaultButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField cmdLinField;
    private javax.swing.JTextField defaultDirTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField pathField;
    private javax.swing.JTextField startupCmdField;
    // End of variables declaration//GEN-END:variables

   
}
