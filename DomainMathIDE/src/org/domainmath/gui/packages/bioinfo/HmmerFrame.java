
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


package org.domainmath.gui.packages.bioinfo;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava3.core.sequence.loader.UniprotProxySequenceReader;
import org.biojava3.ws.hmmer.HmmerDomain;
import org.biojava3.ws.hmmer.HmmerResult;
import org.biojava3.ws.hmmer.HmmerScan;
import org.biojava3.ws.hmmer.RemoteHmmerScan;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.about.AboutDlg;
import org.domainmath.gui.common.DomainMathDialog;





public class HmmerFrame extends javax.swing.JFrame {
    public  java.net.URL imgURL = getClass().getResource("resources/DomainMath.png");
    public   Image icon = Toolkit.getDefaultToolkit().getImage(imgURL);
    private List data =Collections.synchronizedList(new ArrayList());
    private  List col =Collections.synchronizedList(new ArrayList());

  
   

   
    private String var_name;
    private final GridModel gridModel;
    private final JTable table;
    private String id;
   
    

    public HmmerFrame() {
        setIconImage(icon);
        
        setSize(800,600);
        setLocationRelativeTo(null);
        initComponents();
        
        
        
        gridModel = new GridModel();
      
        
        table = new JTable();
        gridModel.setCellEditable(false);
        table.setModel(gridModel);

        
      
     
        table.getTableHeader().setReorderingAllowed(false);

        table.setRowHeight(20);
        JScrollPane scrollPane = new JScrollPane(table);
        
        
        
           
        
        this.jPanel1.add(scrollPane,BorderLayout.CENTER);
        jPanel1.repaint();
        
    }

 
     public void showTable() {
          gridModel.fireTableStructureChanged();
                gridModel.fireTableDataChanged();
       
                table.revalidate();
                table.repaint();
       
    }

    public String getExportVarName() {
        return this.var_name;
    }
    
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusPanel2 = new org.domainmath.gui.StatusPanel();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        NewMenuItem = new javax.swing.JMenuItem();
        uniProtSeqItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        forumItem = new javax.swing.JMenuItem();
        onlineHelpItem = new javax.swing.JMenuItem();
        howToItem = new javax.swing.JMenuItem();
        faqItem = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        suggestionsItem = new javax.swing.JMenuItem();
        reportBugItem = new javax.swing.JMenuItem();
        feedBackItem = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        AboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Hmmer Service");

        jPanel1.setLayout(new java.awt.BorderLayout());

        jMenu1.setText("File");

        NewMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        NewMenuItem.setText("New");
        NewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(NewMenuItem);

        uniProtSeqItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        uniProtSeqItem.setText("UniProt Sequence");
        uniProtSeqItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uniProtSeqItemActionPerformed(evt);
            }
        });
        jMenu1.add(uniProtSeqItem);

        jMenuItem1.setText("Export");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        jMenu1.add(exitItem);

        jMenuBar1.add(jMenu1);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en"); // NOI18N
        helpMenu.setText(bundle.getString("helpMenu.name")); // NOI18N

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
        helpMenu.add(jSeparator14);

        suggestionsItem.setText("Suggestions");
        suggestionsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestionsItemActionPerformed(evt);
            }
        });
        helpMenu.add(suggestionsItem);

        reportBugItem.setText(bundle.getString("reportBugItem.name")); // NOI18N
        reportBugItem.setToolTipText(bundle.getString("reportBugItem.tooltip")); // NOI18N
        reportBugItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportBugItemActionPerformed(evt);
            }
        });
        helpMenu.add(reportBugItem);

        feedBackItem.setText(bundle.getString("yourFeedbackItem.name")); // NOI18N
        feedBackItem.setToolTipText(bundle.getString("yourFeedbackItem.tooltip")); // NOI18N
        feedBackItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedBackItemActionPerformed(evt);
            }
        });
        helpMenu.add(feedBackItem);
        helpMenu.add(jSeparator7);

        AboutItem.setText(bundle.getString("aboutItem.name")); // NOI18N
        AboutItem.setToolTipText(bundle.getString("aboutItem.tooltip")); // NOI18N
        AboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutItemActionPerformed(evt);
            }
        });
        helpMenu.add(AboutItem);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(statusPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setPath(String path) {
    try {
            URI uri = new URI(path);
            Desktop desktop=Desktop.getDesktop();
            desktop.browse(uri);
        } catch (URISyntaxException | IOException ex) {
        }
}
     public void addRow(String r) {
        data.add(r);
      }
      public void addCol(String c) {
        col.add(c);
        }
    private void uniProtSeqItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uniProtSeqItemActionPerformed
      DomainMathDialog dmnDialog = new DomainMathDialog(this,true,"UniProt Sequence ID:");
        dmnDialog.setTitle("Get UniProt Sequence");
        dmnDialog.setLocationRelativeTo(this);
        dmnDialog.setVisible(true);
        
        setID(dmnDialog.getVar_name());
        
        String v=getID();
      
         getSeq(v);
         this.uniProtSeqItem.setEnabled(false);
         this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         
    }//GEN-LAST:event_uniProtSeqItemActionPerformed

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        dispose();
    }//GEN-LAST:event_exitItemActionPerformed

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

    private void reportBugItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportBugItemActionPerformed
        setPath("http://domainmathide.freeforums.org/bugs-f3.html");
    }//GEN-LAST:event_reportBugItemActionPerformed

    private void feedBackItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feedBackItemActionPerformed
        setPath("http://domainmathide.freeforums.org/feedback-f4.html");
    }//GEN-LAST:event_feedBackItemActionPerformed

    private void AboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutItemActionPerformed
        AboutDlg aboutDlg = new AboutDlg(this,true);
        aboutDlg.setLocationRelativeTo(this);
        aboutDlg.setVisible(true);
    }//GEN-LAST:event_AboutItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        DomainMathDialog exportDialog = new DomainMathDialog(this,true,"Name:");
        exportDialog.setTitle("Export Sequence");
        exportDialog.setLocationRelativeTo(this);
        exportDialog.setVisible(true);
        exportVarTo(exportDialog.getVar_name());
        
        String v=this.getExportVarName();
         if(!v.equals("")){
                        
                        for(int i=0; i<this.col.size();i++) {
                            
                            MainFrame.octavePanel.evaluate(v+"("+(i+1)+").Annotation='"+this.col.get(i)+"';");
                            MainFrame.octavePanel.evaluate(v+"("+(i+1)+").HmmerResult='"+this.data.get(i)+"';");
                        }
         }
         MainFrame.reloadWorkspace();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void NewMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewMenuItemActionPerformed
        HmmerFrame hmmerFrame = new HmmerFrame();
        hmmerFrame.setLocationRelativeTo(this);
        hmmerFrame.setVisible(true);
    }//GEN-LAST:event_NewMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
           try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
       }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HmmerFrame().setVisible(true);
            }
        });
    }

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private javax.swing.JMenuItem NewMenuItem;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem faqItem;
    private javax.swing.JMenuItem feedBackItem;
    private javax.swing.JMenuItem forumItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem howToItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JMenuItem onlineHelpItem;
    private javax.swing.JMenuItem reportBugItem;
    private org.domainmath.gui.StatusPanel statusPanel2;
    private javax.swing.JMenuItem suggestionsItem;
    private javax.swing.JMenuItem uniProtSeqItem;
    // End of variables declaration//GEN-END:variables

    private void exportVarTo(String var_name) {
        this.var_name=var_name;
    }

    private void getSeq(String v) {
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(!v.equals("")) {
             try {
                 
            String uniProtID = v;
            System.out.println("uniProtID:"+uniProtID);
            ProteinSequence seq = getUniprot(uniProtID);

            HmmerScan hmmer = new RemoteHmmerScan();

            SortedSet<HmmerResult> results = hmmer.scan(seq);

            System.out.println(String.format("#\t%15s\t%10s\t%s\t%s\t%8s\t%s",
                            "Domain","ACC", "Start","End","eValue","Description"));
            addCol("#");
            addCol("Domain");
            addCol("ACC");
            addCol("Start");
            addCol("End");
            addCol("eValue");
            addCol("Description");
            int counter = 0;
            
            for (HmmerResult hmmerResult : results) {
                    //System.out.println(hmmerResult);

                    for ( HmmerDomain domain : hmmerResult.getDomains()) {
                            counter++;
                            System.out.println(String.format("%d\t%15s\t%10s\t%5d\t%5d\t%.2e\t%s",
							counter,
							hmmerResult.getName(), domain.getHmmAcc(), 
							domain.getSqFrom(),domain.getSqTo(),
							hmmerResult.getEvalue(), hmmerResult.getDesc()
							));
                            addRow(Integer.toString(counter));
                            addRow(hmmerResult.getName());
                            addRow(domain.getHmmAcc());
                            addRow(Integer.toString(domain.getSqFrom()));
                            addRow(Integer.toString(domain.getSqTo()));
                            addRow(Float.toString(hmmerResult.getEvalue()));
                            addRow(hmmerResult.getDesc());

                    }

            }
            showTable();
            
        } catch (Exception e) {
        }
        }
       
    }
    private static ProteinSequence getUniprot(String uniProtID) throws Exception {
		
		AminoAcidCompoundSet set = AminoAcidCompoundSet.getAminoAcidCompoundSet();
		UniprotProxySequenceReader<AminoAcidCompound> uniprotSequence = new UniprotProxySequenceReader<AminoAcidCompound>(uniProtID,set);
		
		ProteinSequence seq = new ProteinSequence(uniprotSequence);
		
		return seq;
    }

    private void setID(String id) {
        this.id=id;
    }

    private String getID() {
        return this.id;
    }
    class GridModel extends AbstractTableModel{
        int i;
        private boolean editable;
  

        @Override
        public int getRowCount() {
          try{
                i = data.size() / getColumnCount();
            }catch(Exception e) {

            }


           return i;
        }

        @Override
        public int getColumnCount() {
           return col.size();
        }

        @Override
        public String getColumnName(int i) {
            String c = "";
            if(i <=getColumnCount()) {
                c = (String)col.get(i);
            }
            return c;
        }

        public Class getColClass(int i) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int r,int c) {
            return editable;
        }
        
        public void setCellEditable(boolean editable) {
            this.editable=editable;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
           return  data.get((rowIndex*getColumnCount())+columnIndex);
        }
    }
}
