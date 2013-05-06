
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

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava3.core.sequence.io.FastaReader;
import org.biojava3.core.sequence.io.FastaReaderHelper;
import org.biojava3.core.sequence.io.GenericFastaHeaderParser;
import org.biojava3.core.sequence.io.ProteinSequenceCreator;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.about.AboutDlg;





public class SeqFrame extends javax.swing.JFrame {
    public  java.net.URL imgURL = getClass().getResource("resources/DomainMath.png");
    public   Image icon = Toolkit.getDefaultToolkit().getImage(imgURL);
    private List data =Collections.synchronizedList(new ArrayList());
    private  List col =Collections.synchronizedList(new ArrayList());

    private List header =Collections.synchronizedList(new ArrayList());
    private  List Sequence =Collections.synchronizedList(new ArrayList());
    private  DefaultListModel listModel;

    private JList list;
    private JSplitPane splitPane;
    private final DefaultListModel listModel2;
    private final JList list2;
    private String var_name;
    public Path2D polygon = null;
    private final Point srcPoint = new Point();
    
   private Color PCOLOR;

    public SeqFrame() {
        setIconImage(icon);
        
        setSize(800,600);
        setLocationRelativeTo(null);
        initComponents();
        
        listModel = new DefaultListModel();
        list = new JList();
        list.setModel(listModel);
        
        listModel2 = new DefaultListModel();
        list2 = new JList() {
            
            private SeqListCellRenderer renderer;
            private AlphaComposite alcomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
            private Color PCOLOR;
            @Override public void updateUI() {
                setSelectionForeground(null);
                setSelectionBackground(null);
                setCellRenderer(null);
                if(renderer!=null) {
                    removeMouseMotionListener(renderer);
                    removeMouseListener(renderer);
                }else{
                    renderer = new SeqListCellRenderer();
                }
                super.updateUI();
                EventQueue.invokeLater(new Runnable() {
                    @Override public void run() {
                        setCellRenderer(renderer);
                        addMouseMotionListener(renderer);
                        addMouseListener(renderer);
                    }
                });
                Color c = getSelectionBackground();
                int r = c.getRed(), g = c.getGreen(), b = c.getBlue();
                PCOLOR = r>g ? r>b ? new Color(r,0,0) : new Color(0,0,b)
                             : g>b ? new Color(0,g,0) : new Color(0,0,b);
            }
            @Override public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(renderer.polygon!=null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setPaint(getSelectionBackground());
                    g2d.draw(renderer.polygon);
                    g2d.setComposite(alcomp);
                    g2d.setPaint(PCOLOR);
                    g2d.fill(renderer.polygon);
                }
            }
        };

        list2.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list2.setModel(listModel2);
       
        splitPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new JScrollPane(list),new JScrollPane(list2));
        splitPane.setDividerLocation(250);
        this.jPanel1.add(splitPane,BorderLayout.CENTER);
        jPanel1.repaint();
        
       
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
        importItem = new javax.swing.JMenuItem();
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
        setTitle("Sequence Viewer");

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
    private void importItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importItemActionPerformed
       JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        
       
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            getFasta(fc.getSelectedFile());
         } 
    }//GEN-LAST:event_importItemActionPerformed

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
        ExportDialog exportDialog = new ExportDialog(this,true,"Name:");
        exportDialog.setTitle("Export Sequence");
        exportDialog.setLocationRelativeTo(this);
        exportDialog.setVisible(true);
        exportVarTo(exportDialog.getVar_name());
        
        String v=this.getExportVarName();
         if(!v.equals("")){
                        
                        for(int i=0; i<this.Sequence.size();i++) {
                            
                            MainFrame.octavePanel.evaluate(v+"("+i+1+").Header='"+this.header.get(i)+"';");
                            MainFrame.octavePanel.evaluate(v+"("+i+1+").Sequence='"+this.Sequence.get(i)+"';");
                        }
         }
         MainFrame.reloadWorkspace();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
                new SeqFrame().setVisible(true);
            }
        });
    }

    private void getFasta(File selectedFile) {
        try{
            String[] t ;
            String s;
           LinkedHashMap<String, ProteinSequence> a = FastaReaderHelper.readFastaProteinSequence(selectedFile);
 
		
 

		FileInputStream inStream = new FileInputStream( selectedFile );
		FastaReader<ProteinSequence,AminoAcidCompound> fastaReader = 
			new FastaReader<ProteinSequence,AminoAcidCompound>(
					inStream, 
					new GenericFastaHeaderParser<ProteinSequence,AminoAcidCompound>(), 
					new ProteinSequenceCreator(AminoAcidCompoundSet.getAminoAcidCompoundSet()));
		LinkedHashMap<String, ProteinSequence> b = fastaReader.process();
               
		for (  Map.Entry<String, ProteinSequence> entry : b.entrySet() ) {
                    listModel.addElement(entry.getValue().getOriginalHeader());
                    s=entry.getValue().getSequenceAsString();
                    t =s.split("");

                    for(int j=0; j<t.length; j++) {
                       listModel2.addElement(t[j]);
                    }
                    this.header.add(entry.getValue().getOriginalHeader());
                    this.Sequence.add(entry.getValue().getSequenceAsString());
                   

		}
                
                int k=0;
                for(int i=0; i<listModel2.getSize(); i++) {
                    if(listModel2.get(i).equals("")) {
                      k++;
                      listModel2.remove(i);
                    }
                }
                list2.setVisibleRowCount(k);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem faqItem;
    private javax.swing.JMenuItem feedBackItem;
    private javax.swing.JMenuItem forumItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem howToItem;
    private javax.swing.JMenuItem importItem;
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
    // End of variables declaration//GEN-END:variables

    private void exportVarTo(String var_name) {
        this.var_name=var_name;
    }

    class DotBorder extends EmptyBorder {
        public DotBorder(Insets borderInsets) {
            super(borderInsets);
        }
        public DotBorder(int top, int left, int bottom, int right) {
            super(top, left, bottom, right);
        }
        @Override public boolean isBorderOpaque() {return true;}
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D)g;
            g2.translate(x,y);
            g2.setPaint(new Color(~SystemColor.activeCaption.getRGB()));

            BasicGraphicsUtils.drawDashedRect(g2, 0, 0, w, h);
            g2.translate(-x,-y);
        }

}

  class SeqListCellRenderer extends JPanel implements ListCellRenderer, MouseListener, MouseMotionListener {
      
    
    private final JLabel label = new JLabel("", JLabel.CENTER);
    private final Border dotBorder = new DotBorder(2,2,2,2);
    private final Border empBorder = BorderFactory.createEmptyBorder(2,2,2,2);
    private final Point srcPoint = new Point();
    public Path2D polygon = null;
    public SeqListCellRenderer() {
        super(new BorderLayout());
        
        label.setOpaque(true);
        label.setForeground(getForeground());
        label.setBackground(getBackground());
        label.setBorder(empBorder);
        this.setOpaque(false);
        this.setBorder(empBorder);
        
        this.add(label, BorderLayout.CENTER);
    }
    @Override 
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
       
            String item = value.toString();
            
            label.setText(item);
            label.setBorder(cellHasFocus?dotBorder:empBorder);
            
            if(isSelected) {
                label.setForeground(list.getSelectionForeground());
                label.setBackground(list.getSelectionBackground());
            }else{

                    if(item.equals("A")) {
                        label.setForeground(list.getForeground());
                        label.setBackground(Color.pink);
                    }else{
                          label.setForeground(list.getForeground());
                        label.setBackground(list.getBackground());
                    }
               
                
            }

        return this;
    }
    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {
        JList list = (JList)e.getSource();
        list.setFocusable(true);
        if(polygon==null){
            srcPoint.setLocation(e.getPoint());
        }
        Point destPoint = e.getPoint();
        polygon = new Path2D.Double();
        polygon.moveTo(srcPoint.x,  srcPoint.y);
        polygon.lineTo(destPoint.x, srcPoint.y);
        polygon.lineTo(destPoint.x, destPoint.y);
        polygon.lineTo(srcPoint.x,  destPoint.y);
        polygon.closePath();
        list.setSelectedIndices(getIntersectsIcons(list, polygon));
        list.repaint();
    }
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {
        JList list = (JList)e.getSource();
        list.setFocusable(true);
        polygon = null;
        list.repaint();
    }
    @Override public void mousePressed(MouseEvent e) {
        JList list = (JList)e.getSource();
        int index = list.locationToIndex(e.getPoint());
        Rectangle rect = list.getCellBounds(index,index);
        if(!rect.contains(e.getPoint())) {
            list.clearSelection();
            list.getSelectionModel().setAnchorSelectionIndex(-1);
            list.getSelectionModel().setLeadSelectionIndex(-1);
            //list.getSelectionModel().setLeadSelectionIndex(list.getModel().getSize());
            list.setFocusable(false);
        }else{
            list.setFocusable(true);
        }
    }
    private int[] getIntersectsIcons(JList l, Shape p) {
        ListModel model = l.getModel();
        ArrayList<Integer> list = new ArrayList<>(model.getSize());
        for(int i=0;i<model.getSize();i++) {
            Rectangle r = l.getCellBounds(i,i);
            if(p.intersects(r)) {
                list.add(i);
            }
        }
        int[] il = new int[list.size()];
        for(int i=0;i<list.size();i++) {
            il[i] = list.get(i);
        }
        return il;
    }
    }

    
}
