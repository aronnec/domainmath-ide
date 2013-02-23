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
package org.domainmath.gui.ftndlg;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import org.domainmath.gui.MainFrame;


public class FtnDialog extends javax.swing.JDialog {

    
    private  JButton okButton;
    private  JButton cancelButton;
    private  JLabel l;
    
    String[] labels;
    private  JTextField text;
   ArrayList <JTextField> list = new ArrayList();
   
    String[] label2;
    
    private  String[] tooltip;
    
    private  JFrame f;
    private  String cmd;
    private  JButton helpButton;
    
    private  int numPairs;
    
    private boolean showDlg;

     public String jar_path = "javaaddpath('"+System.getProperty("user.dir")+File.separator+"Results.jar');";
   
  

   
    

   
    public FtnDialog(JFrame f, boolean modal,String[] labels,String[] tooltip,String[] defText) {
        super(f, modal);
        initComponents();
       
        this.labels =labels;
        this.tooltip=tooltip;
        
      
        numPairs = labels.length;

        JPanel p = new JPanel(new SpringLayout());
        
        for (int i = 0; i < numPairs; i++) {
            
            l = new JLabel(labels[i], JLabel.LEFT);
            p.add(l);
             text = new JTextField(30);
             
             list.add(text);
             list.get(i).setText(defText[i]);
             
             list.get(i).setToolTipText(tooltip[i]);
            l.setLabelFor(list.get(i));
            p.add(list.get(i));
           
            if(i==(numPairs-1)){
                
                l.setForeground(Color.red);
            }
        }

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(p,
                                        numPairs, 2, //rows, cols
                                        6, 6,        //initX, initY
                                        6, 6);       //xPad, yPad


     
        

       

       JPanel b =new JPanel()  ;
       okButton=new JButton("OK");
       cancelButton =new JButton("Cancel");
       helpButton = new JButton("Help");
       okButton.addActionListener(new ButtonActionListener());
       cancelButton.addActionListener(new ButtonActionListener());
       helpButton.addActionListener(new ButtonActionListener());
       b.add(okButton);
       b.add(helpButton);
        b.add(cancelButton);
       p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
       add(p,BorderLayout.CENTER);
       
       add(b,BorderLayout.PAGE_END);
       this.pack();
       

    }
 
    
    
    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

     public boolean isShowDlg() {
        return showDlg;
    }

    public void setShowDlg(boolean showDlg) {
        this.showDlg = showDlg;
    }
    
    public void showDlg(boolean show,String text) {
       
         
         MainFrame.octavePanel.evaluate(jar_path);
        MainFrame.octavePanel.evaluate("_obResult = javaObject('ResultsFrame',disp("+text+"));");
        
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
private  class ButtonActionListener implements ActionListener {
        
       @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            if(b.equals(okButton)) {
                String s ="";
                
                if(numPairs == 2) {
                    s=list.get(0).getText();
                    MainFrame.octavePanel.eval(list.get(numPairs-1).getText()+"="+cmd+"("+s+");");
                    
                    dispose();
                    if(isShowDlg()) {
                        showDlg(isShowDlg(),list.get(numPairs-1).getText());
                    }
                }else{
                    for (int i = 0; i <(labels.length-1) ; i++) {
                        s+=list.get(i).getText()+",";
                        
                    }
                    
                     MainFrame.octavePanel.eval(list.get(numPairs-1).getText()+"="+cmd+s.substring(0, s.lastIndexOf(","))+");");
                    dispose();
                    if(isShowDlg()) {
                        showDlg(isShowDlg(),list.get(numPairs-1).getText());
                    }
                }
                
              
            }else if(b.equals(cancelButton)){
                dispose();
            }else if(b.equals(helpButton)){
               
                 String jar_path = "'"+System.getProperty("user.dir")+File.separator+"QuickHelp.jar'";
                   
                   
                    MainFrame.octavePanel.evaluate("DomainMath_QuickHelp(help('"+cmd+"'),"+jar_path+","+"'QuickHelpFrame');");
                
            }
            
        }
    }
}
