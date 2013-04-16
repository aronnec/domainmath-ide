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
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.about.AboutDlg;
import org.domainmath.gui.ftndlg.FtnDialog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;





public class BioInfoFrame extends javax.swing.JFrame {
    public  java.net.URL imgURL = getClass().getResource("resources/DomainMath.png");
    public   Image icon = Toolkit.getDefaultToolkit().getImage(imgURL);
    private CategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
   
    public BioInfoFrame() {
        setIconImage(icon);
        
        setSize(800,600);
        setLocationRelativeTo(null);
        initComponents();

    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        statusPanel2 = new org.domainmath.gui.StatusPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        SeqArea = new org.fife.ui.rsyntaxtextarea.RSyntaxTextArea();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        importItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        aa2intItem = new javax.swing.JMenuItem();
        plotItem = new javax.swing.JMenuItem();
        aminolookupItem = new javax.swing.JMenuItem();
        cleaveItem = new javax.swing.JMenuItem();
        reverseItem = new javax.swing.JMenuItem();
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
        setTitle("BioInfo Tool");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Sequence:");

        SeqArea.setEditable(false);
        SeqArea.setColumns(20);
        SeqArea.setRows(5);
        jScrollPane1.setViewportView(SeqArea);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jMenu1.setText("File");

        importItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        importItem.setText("Import Sequence");
        importItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importItemActionPerformed(evt);
            }
        });
        jMenu1.add(importItem);

        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        jMenu1.add(exitItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Sequence");

        aa2intItem.setText("Convert to Integers...");
        aa2intItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aa2intItemActionPerformed(evt);
            }
        });
        jMenu2.add(aa2intItem);

        plotItem.setText("Plot");
        plotItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plotItemActionPerformed(evt);
            }
        });
        jMenu2.add(plotItem);

        aminolookupItem.setText("Convert between amino acid...");
        aminolookupItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aminolookupItemActionPerformed(evt);
            }
        });
        jMenu2.add(aminolookupItem);

        cleaveItem.setText("Cleave");
        cleaveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleaveItemActionPerformed(evt);
            }
        });
        jMenu2.add(cleaveItem);

        reverseItem.setText("Reverse");
        reverseItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reverseItemActionPerformed(evt);
            }
        });
        jMenu2.add(reverseItem);

        jMenuBar1.add(jMenu2);

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
            .addComponent(statusPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
    private void importItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importItemActionPerformed
       JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        
       
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                this.SeqArea.read(new FileReader(fc.getSelectedFile()), null);
                plotData(this.SeqArea.getText());
            } catch (IOException ex) {
                
            }
            MainFrame.octavePanel.eval("Sequencechar=fileread('"+fc.getSelectedFile().getAbsolutePath()+"');");
            
         } 
    }//GEN-LAST:event_importItemActionPerformed

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        dispose();
    }//GEN-LAST:event_exitItemActionPerformed

    private void aa2intItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aa2intItemActionPerformed
        MainFrame.octavePanel.evaluate("pkg load bioinfo;");
        String s[] = {"Sequencechar:","Sequenceint:"};
            String t[] = {"Amino acid characters","Integer representation"};
            String d[] = {"Sequencechar","Sequenceint"};
        FtnDialog ftnDialog = new FtnDialog(this,true,s,t,d);
        ftnDialog.setTitle("aa2int");
        ftnDialog.setLocationRelativeTo(this);
        ftnDialog.setCmd("aa2int");
        ftnDialog.setShowDlg(true);
        ftnDialog.setVisible(true);

        
    }//GEN-LAST:event_aa2intItemActionPerformed

    private void aminolookupItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aminolookupItemActionPerformed
        MainFrame.octavePanel.evaluate("pkg load bioinfo;");
        String s[] = {"Sequencechar:","Aminodesc:"};
            String t[] = {"Amino acid characters","Output"};
            String d[] = {"Sequencechar","Aminodesc"};
        FtnDialog ftnDialog = new FtnDialog(this,true,s,t,d);
        ftnDialog.setTitle("aminolookup");
        ftnDialog.setLocationRelativeTo(this);
        ftnDialog.setShowDlg(true);
        ftnDialog.setCmd("aminolookup");
        ftnDialog.setVisible(true);

    }//GEN-LAST:event_aminolookupItemActionPerformed

    private void cleaveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleaveItemActionPerformed
        MainFrame.octavePanel.evaluate("pkg load bioinfo;");
        String s[] = {"Sequencechar:","Pattern:","Position:","Output Parameters:"};
            String t[] = {"Amino acid characters","Regular expression to find the location of the cleavage","Position is the position relative to that regular expression","Output"};
            String d[] = {"Sequencechar","Pattern","Position","[fragments, cuttingsites]"};
        FtnDialog ftnDialog = new FtnDialog(this,true,s,t,d);
        ftnDialog.setTitle("cleave");
        ftnDialog.setLocationRelativeTo(this);
        ftnDialog.setCmd("cleave");
        ftnDialog.setShowDlg(true);
        ftnDialog.setVisible(true);

    }//GEN-LAST:event_cleaveItemActionPerformed

    private void reverseItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reverseItemActionPerformed
         MainFrame.octavePanel.evaluate("pkg load bioinfo;");
        String s[] = {"Seqf:","Seqr:"};
            String t[] = {"Seqf can be either a numeric or code DNA or RNA sequence or a struct with the field sequence","Reverse the direction of the seqf"};
            String d[] = {"Seqf","Seqr"};
        FtnDialog ftnDialog = new FtnDialog(this,true,s,t,d);
        ftnDialog.setTitle("seqreverse");
        ftnDialog.setLocationRelativeTo(this);
        ftnDialog.setCmd("seqreverse");
        ftnDialog.setVisible(true);

    }//GEN-LAST:event_reverseItemActionPerformed

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

    private void plotItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plotItemActionPerformed
        MainFrame.octavePanel.evaluate("pkg load bioinfo;");
        String s2 = JOptionPane.showInputDialog(this,"Name of the variable:","DomainMath IDE",JOptionPane.QUESTION_MESSAGE);
        
        MainFrame.octavePanel.eval("plot("+s2+");");
    }//GEN-LAST:event_plotItemActionPerformed

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
                new BioInfoFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private org.fife.ui.rsyntaxtextarea.RSyntaxTextArea SeqArea;
    private javax.swing.JMenuItem aa2intItem;
    private javax.swing.JMenuItem aminolookupItem;
    private javax.swing.JMenuItem cleaveItem;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem faqItem;
    private javax.swing.JMenuItem feedBackItem;
    private javax.swing.JMenuItem forumItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem howToItem;
    private javax.swing.JMenuItem importItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JMenuItem onlineHelpItem;
    private javax.swing.JMenuItem plotItem;
    private javax.swing.JMenuItem reportBugItem;
    private javax.swing.JMenuItem reverseItem;
    private org.domainmath.gui.StatusPanel statusPanel2;
    private javax.swing.JMenuItem suggestionsItem;
    // End of variables declaration//GEN-END:variables

    private void plotData(String text) {
        //char base[] ={ 'A', 'C', 'G', 'T', 'U', 'R', 'Y', 'K', 'M', 'S',  'W',  'B',  'D',  'H','V',  'N'};
        char amino_acid[] ={'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H', 'I',  'L',  'K',  'M',  'F',  'P',  'S',  'T',  'W',  'Y',  'V',  'B',  'Z',  'X',  '*',  '-',  '?'};
        List data =Collections.synchronizedList(new ArrayList());
        
        for(int i=0; i<amino_acid.length; i++) {
            data.add(getCount(text.toUpperCase(),amino_acid[i]));
        }
        if(chartPanel == null) {
            dataset = createDataset(data);
            chart = createChart(dataset);
            chartPanel = new ChartPanel(chart);
            chartPanel.setFillZoomRectangle(true);
            chartPanel.setMouseWheelEnabled(true);
            this.jPanel1.add(chartPanel,BorderLayout.CENTER);
        
            jPanel1.revalidate();
        }else {
            jPanel1.removeAll();
            dataset = createDataset(data);
            chart = createChart(dataset);
            chartPanel = new ChartPanel(chart);
            chartPanel.setFillZoomRectangle(true);
            chartPanel.setMouseWheelEnabled(true);
            this.jPanel1.add(chartPanel,BorderLayout.CENTER);
        
            jPanel1.revalidate();
        }
        
    }

    private int getCount(String text, char string) {
        int k=0;
        
        for(int i=0; i<text.length(); i++) {
            if(text.charAt(i) == string) {
                k++;
            }
        }
        return k;
    }
    
  
    private static CategoryDataset createDataset(List data) {

        String series1 = "Amino Acids";
        String amino_acid[] ={"A", "R", "N", "D", "C", "Q", "E", "G", "H", "I",  "L",  "K",  "M",  "F",  "P",  "S",  "T",  "W",  "Y",  "V",  "B",  "Z",  "X",  "*",  "-",  "?"};

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i=0; i<amino_acid.length; i++) {
            dataset.addValue(Integer.parseInt(data.get(i).toString()), series1, amino_acid[i]);
        }
        return dataset;

    }

    private static JFreeChart createChart(CategoryDataset dataset) {

        JFreeChart chart = ChartFactory.createBarChart(
            "",       
            "Amino Acids",              
            "Count",                  
            dataset,                  
            PlotOrientation.VERTICAL, 
            true,                     
            true,                     
            false                     
        );

        chart.setBackgroundPaint(Color.white);
        return chart;

    }

   


}
