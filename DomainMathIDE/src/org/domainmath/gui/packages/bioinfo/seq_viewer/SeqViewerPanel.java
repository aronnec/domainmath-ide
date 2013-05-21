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

package org.domainmath.gui.packages.bioinfo.seq_viewer;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.SystemColor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicGraphicsUtils;


public class SeqViewerPanel extends javax.swing.JPanel {


   

    public List header =Collections.synchronizedList(new ArrayList());
    
    private  DefaultListModel listConsensusModel;

    public JList listDetails;
    private JSplitPane splitPane;
    
    public  JList listSequence;

    public Path2D polygon = null;
    private final Font fontListConsensus;
    
    
    private final DefaultListModel seqModel;
    private final String[] t;
    private final DefaultListModel seq_details;
    char amino_acid[] ={'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H', 'I',  'L',  'K',  'M',  'F',  'P',  'S',  'T',  'W',  'Y',  'V',  'B',  'Z',  'X',  '-'};
    private  int count;
    private double percent;
    private int size;
           

    
     public SeqViewerPanel(String seq){
        fontListConsensus = new Font("Monospaced",Font.BOLD,11);
        seq_details = new DefaultListModel();
        listDetails = new JList();
       listDetails.setFont(fontListConsensus);
       
        seqModel =new DefaultListModel();
        t =seq.split("");
        for(int j=0; j<t.length; j++) {
           seqModel.addElement(t[j]);
        }
         int k=0;
                for(int i=0; i<seqModel.getSize(); i++) {
                    if(seqModel.get(i).equals("")) {
                      k++;
                      seqModel.remove(i);
                    }
                }
       this.setRowCount(seqModel.getSize());
        for(int j=0; j<amino_acid.length; j++) {
            
            
                count = getCount(seq.toUpperCase(),amino_acid[j]);
                percent = ((count*100)/this.getRowCount());
                
                if(count != 0) {
                    if(amino_acid[j] == '-') {
                        seq_details.addElement("Gap"+":"+count+"("+percent+"%"+")");
                    }else{
                        seq_details.addElement(amino_acid[j]+":"+count+"("+percent+"%"+")");
                    }
                    
                }
                
                
            
        }
        listDetails.setModel(seq_details);
        listDetails.setSelectedIndex(0);
        listDetails.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        listSequence = new JList() {
            
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

        listSequence.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        listSequence.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listSequence.setModel(seqModel);
         
        splitPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new JScrollPane(listDetails),new JScrollPane(listSequence));
        splitPane.setDividerLocation(250);
        
       
        setLayout(new BorderLayout());
        add(splitPane,BorderLayout.CENTER);
        repaint();

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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void setRowCount(int size) {
        this.size =size;
    }
    private int getRowCount(){
        return this.size;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
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
        
        this.setOpaque(false);

        this.add(label, BorderLayout.CENTER);
    }
    @Override 
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
       
            String item = value.toString();
            
            label.setText(item);
            label.setBorder(cellHasFocus?dotBorder:empBorder);
            setRasmolColorSheme(label,item,isSelected);
           

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

        private void setRasmolColorSheme(JLabel label,String item,boolean isSelected) {
             if(isSelected) {
                label.setForeground(listDetails.getSelectionForeground());
                label.setBackground(listDetails.getSelectionBackground());
            }else{
                    
                    if(item.equalsIgnoreCase("A")) {
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color(200,200,200));
                    }else if(item.equalsIgnoreCase("C") ||
                            item.equalsIgnoreCase("M")) {
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color(230,230,0));
                    }else if(item.equalsIgnoreCase("N") ||
                            item.equalsIgnoreCase("Q")) { 
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color( 0,220,220));
                    }else if(item.equalsIgnoreCase("I") ||
                            item.equalsIgnoreCase("L") ||
                            item.equalsIgnoreCase("V")) { 
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color(15,130,15));
                    }else if(item.equalsIgnoreCase("F")) { 
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color(20,90,255));
                    }else if(item.equalsIgnoreCase("H")) { 
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color(130,130,210));
                    }else if(item.equalsIgnoreCase("K") ||
                            item.equalsIgnoreCase("R")) { 
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color(20,90,255));
                    }else if(item.equalsIgnoreCase("G")) { 
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color(235,235,235));
                    }else if(item.equalsIgnoreCase("S") ||
                            item.equalsIgnoreCase("T")) { 
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color(250,150,0));
                    }else if(item.equalsIgnoreCase("D") ||
                            item.equalsIgnoreCase("E")) { 
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color(230,10,10));
                    }else if(item.equalsIgnoreCase("Y")) { 
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color(50,50,170));
                    }else if(item.equalsIgnoreCase("B") ||
                            item.equalsIgnoreCase("Z") ||
                            item.equalsIgnoreCase("X")) { 
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color(190,160,110));
                    }else if(item.equalsIgnoreCase("P")) { 
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color(220,150,130));
                    }else if(item.equalsIgnoreCase("W")) { 
                        label.setForeground(listDetails.getForeground());
                        label.setBackground(new Color(180,90,180));
                    }
                    
                    else{
                          label.setForeground(listDetails.getForeground());
                        label.setBackground(listDetails.getBackground());
                    }
               
                
            }
        }
    }

  

}
