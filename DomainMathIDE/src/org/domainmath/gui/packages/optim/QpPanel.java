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

package org.domainmath.gui.packages.optim;

import java.io.File;
import org.domainmath.gui.MainFrame;




public class QpPanel extends javax.swing.JPanel {

    /**
     * Creates new form QpPanel
     */
    public QpPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        xDataField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        hDataField = new javax.swing.JTextField();
        runButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        qField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        AField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        bField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lbField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        ubField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        a_lbField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        a_inField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        a_ubField = new javax.swing.JTextField();

        jSplitPane1.setDividerLocation(350);

        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Data");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Initial Guess");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Quadratic Penalty");

        runButton.setText("Run");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        jButton1.setText("[mxn]");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("[mxn]");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Help");
        jButton3.setToolTipText("<html>Function File: [<var>x</var>, <var>obj</var>, <var>info</var>,\n<var>lambda</var>] = <b>qp</b> (<var>x0,\nH, q, A, b, lb, ub, A_lb, A_in, A_ub</var>)<var><a\n name=\"index-qp-2454\"></a></var><var></var><var></var><var></var><var></var><var></var><br>\n<p>Solve the quadratic program </p>\n<pre class=\"example\">          min 0.5 x'*H*x + x'*q<br>           x<br></pre>\n<p>subject to </p>\n<pre class=\"example\">          A*x = b<br>          lb &lt;= x &lt;= ub<br>          A_lb &lt;= A_in*x &lt;= A_ub<br></pre>\n<p class=\"noindent\">using a null-space active-set method. </p></html>\n");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(xDataField)
                            .addComponent(hDataField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(jButton2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 225, Short.MAX_VALUE)
                        .addComponent(runButton)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(xDataField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(hDataField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 287, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(runButton)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Options");

        jLabel5.setText("Linear Penalty");

        qField.setText("[]");

        jLabel6.setText("Constraints Coefficients");

        AField.setText("[]");

        jLabel7.setText("RHS value for Constraints");

        bField.setText("[]");

        jLabel8.setText("Lower Bound");

        lbField.setText("[]");

        jLabel9.setText("Upper Bound");

        ubField.setText("[]");

        jLabel10.setText("Lower Bound of Constraints Coefficients");

        a_lbField.setText("[]");

        jLabel11.setText("Inequality Constraint Coefficients ");

        a_inField.setText("[]");

        jLabel12.setText("Upper Bound of Constraints Coefficients");

        a_ubField.setText("[]");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(162, 162, 162)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(qField)
                            .addComponent(AField, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(bField)
                            .addComponent(lbField)
                            .addComponent(ubField)
                            .addComponent(a_lbField)
                            .addComponent(a_inField)
                            .addComponent(a_ubField))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(qField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(AField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(bField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(ubField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(a_lbField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(a_inField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(a_ubField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(171, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jSplitPane1)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 406, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jSplitPane1)
                    .addGap(0, 0, 0)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
            String x0 = this.xDataField.getText();
            String h = this.hDataField.getText();
            
            if(!x0.equals("") || !h.equals("")){
                String  cmd = x0+","+h+","+this.qField.getText()+","+
                        this.AField.getText()+","+this.bField.getText()+","+
                        this.lbField.getText()+","+this.ubField.getText()+","+
                        this.a_lbField.getText()+","+this.a_inField.getText()+","+
                        this.a_ubField.getText();
                declare("x0",x0);
                 declare("H",h);
                MainFrame.octavePanel.evalWithOutput(
                "[x, obj, info, lambda] = qp ("+cmd+");");
                 MainFrame.octavePanel.evaluate(jar_path);
                 MainFrame.octavePanel.evaluate("ob= javaObject('ResultsFrame','');");
          	MainFrame.octavePanel.evaluate("ob.appendText("+Character.toString('"')+"Initial Guess:\n"+Character.toString('"')+");");
          	MainFrame.octavePanel.evaluate("ob.appendText(disp(x0));");
          	MainFrame.octavePanel.evaluate("ob.appendText("+Character.toString('"')+"Quadratic Penality:\n"+Character.toString('"')+");");
          	MainFrame.octavePanel.evaluate("ob.appendText(disp(H));");
                MainFrame.octavePanel.evaluate("ob.appendText("+Character.toString('"')+"\nRESULT:\n"+Character.toString('"')+");");
                MainFrame.octavePanel.evaluate("ob.appendText('Solution Status:');");
                MainFrame.octavePanel.evaluate("status=info.info;");
                MainFrame.octavePanel.evaluate("if(status == 0)");
                MainFrame.octavePanel.evaluate("	ob.appendText('The problem is feasible and convex. Global solution found. ');");
                MainFrame.octavePanel.evaluate("elseif(status == 1)");
                MainFrame.octavePanel.evaluate("	ob.appendText('The problem is not convex. Local solution found. ');");
                MainFrame.octavePanel.evaluate("elseif(status == 2)");
                MainFrame.octavePanel.evaluate("	ob.appendText('The problem is not convex and unbounded. ');");
                MainFrame.octavePanel.evaluate("elseif(status == 3)");
                MainFrame.octavePanel.evaluate("	ob.appendText('Maximum number of iterations reached. ');");
                MainFrame.octavePanel.evaluate("elseif(status == 6)");
                MainFrame.octavePanel.evaluate("	ob.appendText('The problem is infeasible. ');");
                MainFrame.octavePanel.evaluate("else");
                MainFrame.octavePanel.evaluate("	ob.appendText('Internal error. ');");
                MainFrame.octavePanel.evaluate("endif");
          	MainFrame.octavePanel.evaluate("ob.appendText("+Character.toString('"')+"\nValue of the decision variables at the optimum:\n"+Character.toString('"')+");");
                MainFrame.octavePanel.evaluate("ob.appendText(disp(x));");
                MainFrame.octavePanel.evaluate("ob.appendText("+Character.toString('"')+"\nOptimum value of the objective function:\n"+Character.toString('"')+");");
                MainFrame.octavePanel.evaluate("ob.appendText(disp(obj));");
	       
                 
            }
    }//GEN-LAST:event_runButtonActionPerformed

     private void declare(String name, String value) {
        MainFrame.octavePanel.eval(name+"="+value+";");
    }
     private String createOctMtx(String text) {
        String val = text.replaceAll("\t", ",");
       return val.replaceAll(" ", ";");
    }
   
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String text = this.xDataField.getText();
        this.xDataField.setText("["+createOctMtx(text)+"]");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
             String jar_path2 = "'"+System.getProperty("user.dir")+File.separator+"QuickHelp.jar'";
        MainFrame.octavePanel.evaluate("DomainMath_QuickHelp(help('qp'),"+jar_path2+","+"'QuickHelpFrame');");
   
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String text = this.hDataField.getText();
        this.hDataField.setText("["+createOctMtx(text)+"]");
    }//GEN-LAST:event_jButton2ActionPerformed
public String jar_path = "javaaddpath('"+System.getProperty("user.dir")+File.separator+"Results.jar');";

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AField;
    private javax.swing.JTextField a_inField;
    private javax.swing.JTextField a_lbField;
    private javax.swing.JTextField a_ubField;
    private javax.swing.JTextField bField;
    private javax.swing.JTextField hDataField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField lbField;
    private javax.swing.JTextField qField;
    private javax.swing.JButton runButton;
    private javax.swing.JTextField ubField;
    private javax.swing.JTextField xDataField;
    // End of variables declaration//GEN-END:variables
}
