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
import javax.swing.JOptionPane;
import org.domainmath.gui.MainFrame;


public class GlpkPanel extends javax.swing.JPanel {

    /**
     * Creates new form GlpkPanel
     */
    public GlpkPanel() {
        initComponents();
    }

       private String createOctMtx(String text) {
        String val = text.replaceAll("\t", ",");
       return val.replaceAll(" ", ";");
    }
    private void declare(String name, String value) {
        MainFrame.octavePanel.eval(name+"="+value+";");
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
        AButton = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        ATextField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        bTextField = new javax.swing.JTextField();
        bButton = new javax.swing.JButton();
        ubTextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        lbButton = new javax.swing.JButton();
        lbTextField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        vartypeTextField = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        helpButton = new javax.swing.JButton();
        ubButton = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        ctypeTextField = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        cButton = new javax.swing.JButton();
        cTextField = new javax.swing.JTextField();
        runButton = new javax.swing.JButton();
        senseComboBox = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        param_branchComboBox = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        param_itcntTextField = new javax.swing.JTextField();
        param_priceComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        param_roundComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        param_dualComboBox = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        param_btrackComboBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        param_scaleComboBox = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        param_msglevComboBox = new javax.swing.JComboBox();
        param_lpsolverComboBox = new javax.swing.JComboBox();
        param_itlimTextField = new javax.swing.JTextField();
        param_presolTextField = new javax.swing.JTextField();

        jSplitPane1.setDividerLocation(450);

        AButton.setText("[mxn]");
        AButton.setToolTipText("Convert the text to Octave matrix style");
        AButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AButtonActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("RHS value for Constraints");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Constraints Coefficients");

        ATextField.setToolTipText("<html>A matrix containing the <b>constraints coefficients</b></html>");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Lower Bound");

        bTextField.setToolTipText("<html>A column array containing the<b> right-hand side value for<br> each constraint in the constraint matrix</b></html>");

        bButton.setText("[mxn]");
        bButton.setToolTipText("Convert the text to Octave matrix style");
        bButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bButtonActionPerformed(evt);
            }
        });

        ubTextField.setToolTipText("<html>An array containing the <b>upper bound</b> on each of the variables.<br> If ub is not supplied, the default upper bound is assumed to be infinite</html>");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Upper Bound");

        lbButton.setText("[mxn]");
        lbButton.setToolTipText("Convert the text to Octave matrix style");
        lbButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbButtonActionPerformed(evt);
            }
        });

        lbTextField.setToolTipText("<html>An array containing the<b> lower bound</b> on each of the variables.<br> If lb is not supplied, the default lower bound for the variables is zero</html>");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("sense");

        vartypeTextField.setText("\"CCC\"");
        vartypeTextField.setToolTipText("<html>A column array containing the types of the variables.<br>\"C\"<br>&nbsp;&nbsp;&nbsp;A continuous variable.<br>\"I\"<br>&nbsp;&nbsp;&nbsp;An integer variable.</html> ");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("ctype");

        helpButton.setText("Help");
        helpButton.setToolTipText("<html> Function File: [<var>xopt</var>,\n<var>fmin</var>, <var>status</var>, <var>extra</var>]\n= <b>glpk</b> (<var>c, A, b, lb, ub, ctype, vartype,\nsense, param</var>)<var><a name=\"index-glpk-2449\"></a></var><br>\n<p>Solve a linear program using the GNU <span class=\"sc\">glpk</span>\nlibrary. Given three\narguments, <code>glpk</code> solves the following standard\nLP: </p>\n<pre class=\"example\">          min C'*x<br></pre>\n<p>subject to </p>\n<pre class=\"example\">          A*x  = b<br>            x &gt;= 0<br></pre>\n<p>but may also solve problems of the form </p>\n<pre class=\"example\">          [ min | max ] C'*x<br></pre>\n<p>subject to </p>\n<pre class=\"example\">          A*x [ \"=\" | \"&lt;=\" | \"&gt;=\" ] b<br>            x &gt;= LB<br>            x &lt;= UB<br></pre></html>");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        ubButton.setText("[mxn]");
        ubButton.setToolTipText("Convert the text to Octave matrix style");
        ubButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubButtonActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("vartype");

        ctypeTextField.setText("\"UUU\"");
        ctypeTextField.setToolTipText("<html>An array of characters containing the sense of each constraint in the constraint matrix.<br> Each element of the array may be one of the following values<br>\"F\"<br>&nbsp;&nbsp;&nbsp;A free (unbounded) constraint (the constraint is ignored).<br>\"U\"<br>&nbsp;&nbsp;&nbsp;An inequality constraint with an upper bound (A(i,:)*x <= b(i)).<br>\"S\"<br>&nbsp;&nbsp;&nbsp; An equality constraint (A(i,:)*x = b(i)).<br>\"L\"<br>&nbsp;&nbsp;&nbsp;An inequality with a lower bound (A(i,:)*x >= b(i)).<br>\n\"D\"<br>&nbsp;&nbsp;&nbsp;An inequality constraint with both upper and lower bounds (A(i,:)*x >= -b(i) and (A(i,:)*x <= b(i)).</html>");

        jLabel20.setForeground(new java.awt.Color(0, 0, 255));
        jLabel20.setText("Data");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("Objective Function");

        cButton.setText("[mxn]");
        cButton.setToolTipText("Convert the text to Octave matrix style");
        cButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cButtonActionPerformed(evt);
            }
        });

        cTextField.setToolTipText("<html>A column array containing<br> the <b>objective function coefficients<b><html>");

        runButton.setText("Run");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        senseComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "minimization", "maximization" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(helpButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(runButton))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel17))
                                .addGap(69, 69, 69)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cTextField)
                                    .addComponent(ATextField)
                                    .addComponent(bTextField)
                                    .addComponent(lbTextField)
                                    .addComponent(ubTextField)
                                    .addComponent(ctypeTextField)
                                    .addComponent(vartypeTextField)
                                    .addComponent(senseComboBox, 0, 120, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cButton)
                                    .addComponent(AButton)
                                    .addComponent(bButton)
                                    .addComponent(lbButton)
                                    .addComponent(ubButton))))
                        .addGap(29, 29, 29))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(ATextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(bTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lbTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(ubTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ubButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(ctypeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(vartypeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(senseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(runButton)
                    .addComponent(helpButton))
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel2);

        param_branchComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Branch on the first variable", "Branch on the last variable", "Branch using a heuristic by Driebeck and Tomlin" }));

        jLabel9.setText("Branching Heuristic  (for MIP only)");

        jLabel12.setText("LP Solver");

        param_priceComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Textbook pricing.", "Steepest edge pricing. " }));

        jLabel2.setText("Output Message");

        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Parameters");

        param_roundComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Report all primal and dual values \"as is\"", "Replace tiny primal and dual values by exact zero. " }));

        jLabel3.setText("Scaling");

        jLabel6.setText("Solution Rounding");

        param_dualComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "No dual simplex", "Use dual simplex" }));

        jLabel10.setText("Backtracking Heuristic  (for MIP only)");

        jLabel7.setText("Simplex Iterations Limit");

        param_btrackComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Depth first search", "Breadth first search", "Backtrack using the best projection heuristic" }));

        jLabel4.setText("Dual Simplex ");

        jLabel5.setText("Pricing");

        jLabel8.setText("Output Frequency in Iterations");

        param_scaleComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "No scaling", "Equilibration scaling", "Geometric mean scaling" }));

        jLabel11.setText("LP Presolver");

        param_msglevComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No output", "Error messages only", "Normal output", "Full output (includes informational messages)" }));
        param_msglevComboBox.setToolTipText("");

        param_lpsolverComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Revised simplex method.", "Interior point method. " }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(param_priceComboBox, 0, 1, Short.MAX_VALUE)
                            .addComponent(param_roundComboBox, 0, 1, Short.MAX_VALUE)
                            .addComponent(param_itlimTextField)
                            .addComponent(param_itcntTextField)
                            .addComponent(param_branchComboBox, 0, 1, Short.MAX_VALUE)
                            .addComponent(param_btrackComboBox, 0, 1, Short.MAX_VALUE)
                            .addComponent(param_lpsolverComboBox, 0, 1, Short.MAX_VALUE)
                            .addComponent(param_presolTextField)
                            .addComponent(param_msglevComboBox, 0, 1, Short.MAX_VALUE)
                            .addComponent(param_scaleComboBox, 0, 1, Short.MAX_VALUE)
                            .addComponent(param_dualComboBox, 0, 1, Short.MAX_VALUE))
                        .addGap(87, 87, 87)))
                .addGap(55, 55, 55))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(param_msglevComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(param_scaleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(param_dualComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(param_priceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(param_roundComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(param_itlimTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(param_itcntTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(param_branchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(param_btrackComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(param_presolTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(param_lpsolverComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 774, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void AButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AButtonActionPerformed
        String text = this.ATextField.getText();
        this.ATextField.setText("["+createOctMtx(text)+"]");
    }//GEN-LAST:event_AButtonActionPerformed

    private void bButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bButtonActionPerformed
        String text = this.bTextField.getText();
        this.bTextField.setText("["+createOctMtx(text)+"]");
    }//GEN-LAST:event_bButtonActionPerformed

    private void lbButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbButtonActionPerformed
        String text = this.lbTextField.getText();
        this.lbTextField.setText("["+createOctMtx(text)+"]");
    }//GEN-LAST:event_lbButtonActionPerformed

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        String jar_path2 = "'"+System.getProperty("user.dir")+File.separator+"QuickHelp.jar'";
        MainFrame.octavePanel.evaluate("DomainMath_QuickHelp(help('glpk'),"+jar_path2+","+"'QuickHelpFrame');");
    }//GEN-LAST:event_helpButtonActionPerformed

    private void ubButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubButtonActionPerformed
        String text = this.ubTextField.getText();
        this.ubTextField.setText("["+createOctMtx(text)+"]");
    }//GEN-LAST:event_ubButtonActionPerformed

    private void cButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cButtonActionPerformed
        String text = this.cTextField.getText();
        this.cTextField.setText("["+createOctMtx(text)+"]");
    }//GEN-LAST:event_cButtonActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        String c= this.cTextField.getText();
        String A =this.ATextField.getText();
        String b=this.bTextField.getText();
        String lb =this.lbTextField.getText();
        String ub =this.ubTextField.getText();
        String ctype =this.ctypeTextField.getText();
        String vartype =this.vartypeTextField.getText();
        int sense = this.senseComboBox.getSelectedIndex();
        int param_msglev = this.param_msglevComboBox.getSelectedIndex();
        int param_scale = this.param_scaleComboBox.getSelectedIndex();
        int param_dual = this.param_dualComboBox.getSelectedIndex();
        int param_price = this.param_priceComboBox.getSelectedIndex();
        int param_round = this.param_roundComboBox.getSelectedIndex();
        String param_itlim =this.param_itlimTextField.getText();
        String param_itcnt = this.param_itcntTextField.getText();
        int param_branch = this.param_branchComboBox.getSelectedIndex();
        int param_btrack = this.param_btrackComboBox.getSelectedIndex();
        String param_presol = this.param_presolTextField.getText();
        int param_lpsolver = this.param_lpsolverComboBox.getSelectedIndex();

        if(c.equals("") ||
            A.equals("") ||
            b.equals("") ||
            lb.equals("") ||
            ub.equals("") ||
            ctype.equals("") ||
            vartype.equals("")) {
            JOptionPane.showMessageDialog(this, "Insufficient data.", "DomainMath IDE", JOptionPane.ERROR_MESSAGE);

        }else{

            declare("c",c);
            declare("A",A);
            declare("b",b);
            declare("lb",lb);
            declare("ub",ub);
            declare("ctype",ctype);
            declare("vartype",vartype);
            if(sense ==0) {
                declare("sense","1");
            }else{
                declare("sense","-1");
            }

            declare("param.msglev",""+param_msglev);

            if(param_scale != 0) {
                declare("param.scale",""+(param_scale-1));
            }

            if(param_dual != 0) {
                declare("param.dual",""+(param_dual-1));
            }

            if(param_price != 0) {
                declare("param.price",""+(param_price-1));
            }
            if(param_round != 0) {
                declare("param.round",""+(param_round-1));
            }

            if(!param_itlim.equals("")) {
                declare("param.itlim",param_itlim);
            }
            if(!param_itcnt.equals("")) {
                declare("param.itcnt",param_itcnt);
            }

            if(!param_presol.equals("")) {
                declare("param.itlim",param_presol);
            }

            if(param_branch != 0) {
                declare("param.brach",""+(param_branch-1));
            }
            if(param_btrack != 0) {
                declare("param.btrack",""+(param_btrack-1));
            }
            if(param_lpsolver != 0) {
                declare("param.lpsolver",""+(param_lpsolver-1));
            }
            
            if(param_lpsolver ==0) {
                declare("param.lpsolver","1");
            }else{
                declare("param.lpsolver","2");
            }
            MainFrame.octavePanel.eval("[xmin, fmin, status, extra] = glpk (c, A, b, lb, ub, ctype, vartype, s, param)");
            

             MainFrame.octavePanel.evaluate(jar_path);
             MainFrame.octavePanel.evaluate("DomainMath_GlpkStatus(c, A, b,sense,xopt, fmin, status);");
             
        }
    }//GEN-LAST:event_runButtonActionPerformed
 public String jar_path = "javaaddpath('"+System.getProperty("user.dir")+File.separator+"Results.jar');";

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AButton;
    private javax.swing.JTextField ATextField;
    private javax.swing.JButton bButton;
    private javax.swing.JTextField bTextField;
    private javax.swing.JButton cButton;
    private javax.swing.JTextField cTextField;
    private javax.swing.JTextField ctypeTextField;
    private javax.swing.JButton helpButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
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
    private javax.swing.JButton lbButton;
    private javax.swing.JTextField lbTextField;
    private javax.swing.JComboBox param_branchComboBox;
    private javax.swing.JComboBox param_btrackComboBox;
    private javax.swing.JComboBox param_dualComboBox;
    private javax.swing.JTextField param_itcntTextField;
    private javax.swing.JTextField param_itlimTextField;
    private javax.swing.JComboBox param_lpsolverComboBox;
    private javax.swing.JComboBox param_msglevComboBox;
    private javax.swing.JTextField param_presolTextField;
    private javax.swing.JComboBox param_priceComboBox;
    private javax.swing.JComboBox param_roundComboBox;
    private javax.swing.JComboBox param_scaleComboBox;
    private javax.swing.JButton runButton;
    private javax.swing.JComboBox senseComboBox;
    private javax.swing.JButton ubButton;
    private javax.swing.JTextField ubTextField;
    private javax.swing.JTextField vartypeTextField;
    // End of variables declaration//GEN-END:variables
}
