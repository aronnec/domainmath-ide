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

package org.domainmath.gui.dialog.find_replace;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;




public class FindAndReplaceDialog extends javax.swing.JDialog {
    private int position;
    private  RSyntaxTextArea selectedArea;

   

    /** Creates new form FindAndReplaceDialog */
    public FindAndReplaceDialog(java.awt.Frame parent, boolean modal,JTabbedPane tab) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
           RTextScrollPane t =(RTextScrollPane) tab.getComponentAt(tab.getSelectedIndex());
	selectedArea = (RSyntaxTextArea)t.getTextArea();
        findDocListener();
        replaceDocListener();
    }

    public FindAndReplaceDialog(java.awt.Frame parent, boolean modal,JTabbedPane tab,String findText) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
           RTextScrollPane t =(RTextScrollPane) tab.getComponentAt(tab.getSelectedIndex());
	selectedArea = (RSyntaxTextArea)t.getTextArea();
        this.findTextField.setText(findText);
        findDocListener();
        replaceDocListener();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        findTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        replaceTextField = new javax.swing.JTextField();
        findNxtButton = new javax.swing.JButton();
        findPrevButton = new javax.swing.JButton();
        replaceButton = new javax.swing.JButton();
        replaceAllButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        regExCheckBox = new javax.swing.JCheckBox();
        matchCaseCheckBox = new javax.swing.JCheckBox();
        wholeWordsOnlyCheckBox = new javax.swing.JCheckBox();
        markAllComboBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Find and Replace");

        jLabel1.setText("Find What:");

        findTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findTextFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Replace With:");

        findNxtButton.setMnemonic('N');
        findNxtButton.setText("Find Next");
        findNxtButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findNxtButtonActionPerformed(evt);
            }
        });

        findPrevButton.setMnemonic('P');
        findPrevButton.setText("Find Previous");
        findPrevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findPrevButtonActionPerformed(evt);
            }
        });

        replaceButton.setMnemonic('R');
        replaceButton.setText("Replace");
        replaceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceButtonActionPerformed(evt);
            }
        });

        replaceAllButton.setMnemonic('A');
        replaceAllButton.setText("Replace All");
        replaceAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceAllButtonActionPerformed(evt);
            }
        });

        closeButton.setMnemonic('C');
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        jLabel3.setForeground(java.awt.SystemColor.activeCaption);
        jLabel3.setText("Options");

        regExCheckBox.setMnemonic('E');
        regExCheckBox.setText("Regular Expressions");
        regExCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regExCheckBoxActionPerformed(evt);
            }
        });

        matchCaseCheckBox.setMnemonic('M');
        matchCaseCheckBox.setText("Match Case");

        wholeWordsOnlyCheckBox.setText("Match Whole Words Only");

        markAllComboBox.setMnemonic('L');
        markAllComboBox.setText("Mark All");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(findTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                            .addComponent(replaceTextField))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(findPrevButton)
                            .addComponent(findNxtButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1)
                        .addGap(18, 18, 18)
                        .addComponent(replaceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(markAllComboBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(closeButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(matchCaseCheckBox)
                                    .addComponent(wholeWordsOnlyCheckBox)
                                    .addComponent(regExCheckBox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 275, Short.MAX_VALUE)
                                .addComponent(replaceAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {closeButton, findNxtButton, findPrevButton, replaceAllButton, replaceButton});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {matchCaseCheckBox, regExCheckBox, wholeWordsOnlyCheckBox});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(findTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(findNxtButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(replaceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(findPrevButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(replaceButton)
                        .addComponent(jLabel3))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(replaceAllButton)
                        .addGap(48, 48, 48)
                        .addComponent(closeButton)
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(matchCaseCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wholeWordsOnlyCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regExCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(markAllComboBox)
                        .addContainerGap(29, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {findNxtButton, findPrevButton});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {replaceAllButton, replaceButton});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void findNxtButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findNxtButtonActionPerformed
        find(true);
    }//GEN-LAST:event_findNxtButtonActionPerformed

    private void findPrevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findPrevButtonActionPerformed
        find(false);
    }//GEN-LAST:event_findPrevButtonActionPerformed

    private void replaceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceButtonActionPerformed
        replace(true);
    }//GEN-LAST:event_replaceButtonActionPerformed

    private void replaceAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceAllButtonActionPerformed
        replaceAll(true);
    }//GEN-LAST:event_replaceAllButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void regExCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regExCheckBoxActionPerformed
       
    }//GEN-LAST:event_regExCheckBoxActionPerformed

    private void findTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findTextFieldActionPerformed
        this.findNxtButton.doClick();
    }//GEN-LAST:event_findTextFieldActionPerformed
     
    private void findDocListener() {
         if(findTextField.getText().equals("")) {
            findNxtButton.setEnabled(false);
            findPrevButton.setEnabled(false);
         }
        this.findTextField.getDocument().addDocumentListener(
                new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                
                findNxtButton.setEnabled(true);
                findPrevButton.setEnabled(true);
             
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(findTextField.getText().equals("")) {
                    findNxtButton.setEnabled(false);
                    findPrevButton.setEnabled(false);
                }
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                findNxtButton.setEnabled(true);
                findPrevButton.setEnabled(true);
           }
                    
       });
    }
    
     private void replaceDocListener() {
         if(replaceTextField.getText().equals("")) {
            replaceButton.setEnabled(false);
            replaceAllButton.setEnabled(false);
         }
        this.replaceTextField.getDocument().addDocumentListener(
                new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                
                replaceButton.setEnabled(true);
                replaceAllButton.setEnabled(true);
             
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(replaceTextField.getText().equals("")) {
                    replaceButton.setEnabled(false);
                    replaceAllButton.setEnabled(false);
                }
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                replaceButton.setEnabled(true);
                replaceAllButton.setEnabled(true);
           }
                    
       });
    }
        public void replace(boolean back) {
              SearchContext context = new SearchContext();
            String text = this.replaceTextField.getText();
            if (text.length() == 0) {
                return;
            }
            String findText = this.findTextField.getText(); 
            
            context.setSearchFor(findText);
            context.setReplaceWith(text);
            context.setMatchCase(this.matchCaseCheckBox.isSelected());
            context.setRegularExpression(this.regExCheckBox.isSelected());
            context.setSearchForward(back);
            context.setWholeWord(this.wholeWordsOnlyCheckBox.isSelected());

            boolean isReplaced = SearchEngine.replace(selectedArea, context);

            if (!isReplaced) {
                JOptionPane.showMessageDialog(this, "Unable to replace the text","DomainMath IDE",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        public void replaceAll(boolean back) {
              SearchContext context = new SearchContext();
            String findText = this.findTextField.getText();  
            String text = this.replaceTextField.getText();
            if (text.length() == 0) {
                return;
            }
            context.setSearchFor(findText);
            context.setReplaceWith(text);
            context.setMatchCase(this.matchCaseCheckBox.isSelected());
            context.setRegularExpression(this.regExCheckBox.isSelected());
            context.setSearchForward(back);
            context.setWholeWord(this.wholeWordsOnlyCheckBox.isSelected());

            int found = SearchEngine.replaceAll(selectedArea, context);

            if (found == 0) {
                JOptionPane.showMessageDialog(this, "Unable to replace the text","DomainMath IDE",JOptionPane.INFORMATION_MESSAGE);
            }
        }
	
        
    public void find (boolean forward) {
          SearchContext context = new SearchContext();
      String text = findTextField.getText();
      if (text.length() == 0) {
         return;
      }
      context.setSearchFor(text);
      
      context.setMatchCase(this.matchCaseCheckBox.isSelected());
      context.setRegularExpression(this.regExCheckBox.isSelected());
      context.setSearchForward(forward);
      
      context.setWholeWord(this.wholeWordsOnlyCheckBox.isSelected());

      boolean found = SearchEngine.find(selectedArea, context);
      if(this.markAllComboBox.isSelected()) {
          selectedArea.markAll(text, this.matchCaseCheckBox.isSelected(), 
                  this.wholeWordsOnlyCheckBox.isSelected(), this.regExCheckBox.isSelected());
      }
      if (!found) {
         JOptionPane.showMessageDialog(this, "Text not found","DomainMath IDE",JOptionPane.INFORMATION_MESSAGE);
      }

    }
   


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JButton findNxtButton;
    private javax.swing.JButton findPrevButton;
    private javax.swing.JTextField findTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox markAllComboBox;
    private javax.swing.JCheckBox matchCaseCheckBox;
    private javax.swing.JCheckBox regExCheckBox;
    private javax.swing.JButton replaceAllButton;
    private javax.swing.JButton replaceButton;
    private javax.swing.JTextField replaceTextField;
    private javax.swing.JCheckBox wholeWordsOnlyCheckBox;
    // End of variables declaration//GEN-END:variables

}
