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


import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.TitledPanelContainer;
import org.domainmath.gui.about.AboutDlg;
import org.domainmath.gui.databrowser.DataBrowserPanel;


public class OptimizationFrame extends javax.swing.JFrame {
    private int glpkIndex=1;
    private JPopupMenu popup;
    private JMenuItem pcloseItem;
    private JMenuItem pcloseAllItem;
    private  List fileNameList =Collections.synchronizedList(new ArrayList());
    private int qpIndex=1;
    private int sqpIndex=1;
    private int tabIndex=1;
    private final MainFrame frame;
    private final DataBrowserPanel dataBrowser;
    private final JTabbedPane tabbedPane;
    private final JSplitPane splitPane;
    
    /**
     * Creates new form GlpkFrame
     */
    public OptimizationFrame(MainFrame frame) {
        dataBrowser =new DataBrowserPanel(MainFrame.parent_root+"DomainMath_OctaveVariables.dat",frame);
         
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,dataBrowser,tabbedPane);
        splitPane.setDividerLocation(200);
       
        this.setIconImage(icon);
        this.frame =frame;
        
        initComponents();
        this.add(splitPane,BorderLayout.CENTER);
        
        this.popupTab(); 
        this.pack();
    }

    private void popupTab(){
         popup = new JPopupMenu();
         
         pcloseItem = new JMenuItem("Close");
         pcloseAllItem = new JMenuItem("Close All");
        
       
        popup.add(pcloseItem);
        popup.add(pcloseAllItem);
        
        tabbedPane.addMouseListener(new PopupListener(popup));
        
        
        pcloseItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                 if(tabbedPane.getSelectedIndex() >= 0) { 
                   
                    removeFileNameFromList(tabbedPane.getSelectedIndex());
                   
                   tabbedPane.remove(tabbedPane.getSelectedIndex());
                   tabIndex--;
               }
               
            }
  
        });
        
        pcloseAllItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int i=tabbedPane.getTabCount()-1;
                while(i != -1) {

                    removeFileNameFromList(i);
                    tabbedPane.remove(i);
                    tabIndex--;
                    i--;
                }
 
           }

        });

    }
     public void removeFileNameFromList(int index) {
        fileNameList.remove(index);
    }
      public void addFileNameToList(String name) {
        fileNameList.add(name);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusPanel2 = new org.domainmath.gui.StatusPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        closeItem = new javax.swing.JMenuItem();
        closeAllItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
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
        setTitle("Optimization Tool");
        getContentPane().add(statusPanel2, java.awt.BorderLayout.PAGE_END);

        jMenu1.setText("File");

        jMenuItem2.setText("glpk");
        jMenuItem2.setToolTipText("<html> Function File: [<var>xopt</var>,\n<var>fmin</var>, <var>status</var>, <var>extra</var>]\n= <b>glpk</b> (<var>c, A, b, lb, ub, ctype, vartype,\nsense, param</var>)<var><a name=\"index-glpk-2449\"></a></var><br>\n<p>Solve a linear program using the GNU <span class=\"sc\">glpk</span>\nlibrary. Given three\narguments, <code>glpk</code> solves the following standard\nLP: </p>\n<pre class=\"example\">          min C'*x<br></pre>\n<p>subject to </p>\n<pre class=\"example\">          A*x  = b<br>            x &gt;= 0<br></pre>\n<p>but may also solve problems of the form </p>\n<pre class=\"example\">          [ min | max ] C'*x<br></pre>\n<p>subject to </p>\n<pre class=\"example\">          A*x [ \"=\" | \"&lt;=\" | \"&gt;=\" ] b<br>            x &gt;= LB<br>            x &lt;= UB<br></pre></html>");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("qp");
        jMenuItem3.setToolTipText("<html>Function File: [<var>x</var>, <var>obj</var>, <var>info</var>,\n<var>lambda</var>] = <b>qp</b> (<var>x0,\nH, q, A, b, lb, ub, A_lb, A_in, A_ub</var>)<var><a\n name=\"index-qp-2454\"></a></var><var></var><var></var><var></var><var></var><var></var><br>\n<p>Solve the quadratic program </p>\n<pre class=\"example\">          min 0.5 x'*H*x + x'*q<br>           x<br></pre>\n<p>subject to </p>\n<pre class=\"example\">          A*x = b<br>          lb &lt;= x &lt;= ub<br>          A_lb &lt;= A_in*x &lt;= A_ub<br></pre>\n<p class=\"noindent\">using a null-space active-set method. </p></html>\n");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("sqp");
        jMenuItem4.setToolTipText("<html>Function File: [<small class=\"dots\">...</small>] = <b>sqp</b>\n(<var>x0, phi, g, h, lb, ub, maxiter, tol</var>)<var><a\n name=\"index-sqp-2467\"></a></var><br>\n<p>Solve the nonlinear program </p>\n<pre class=\"example\">          min phi (x)<br>           x<br></pre>\n<p>subject to </p>\n<pre class=\"example\">          g(x)  = 0<br>          h(x) &gt;= 0<br>          lb &lt;= x &lt;= ub<br></pre>\n<p class=\"noindent\">using a sequential quadratic\nprogramming method. </p><html>\n");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator1);

        closeItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/packages/image/resources/image-tool_en"); // NOI18N
        closeItem.setText(bundle.getString("closeItem.name")); // NOI18N
        closeItem.setToolTipText(bundle.getString("closeItem.tooltip")); // NOI18N
        closeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeItemActionPerformed(evt);
            }
        });
        jMenu1.add(closeItem);

        closeAllItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        closeAllItem.setText(bundle.getString("closeAllItem.name")); // NOI18N
        closeAllItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeAllItemActionPerformed(evt);
            }
        });
        jMenu1.add(closeAllItem);
        jMenu1.add(jSeparator2);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en"); // NOI18N
        helpMenu.setText(bundle1.getString("helpMenu.name")); // NOI18N

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

        reportBugItem.setText(bundle1.getString("reportBugItem.name")); // NOI18N
        reportBugItem.setToolTipText(bundle1.getString("reportBugItem.tooltip")); // NOI18N
        reportBugItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportBugItemActionPerformed(evt);
            }
        });
        helpMenu.add(reportBugItem);

        feedBackItem.setText(bundle1.getString("yourFeedbackItem.name")); // NOI18N
        feedBackItem.setToolTipText(bundle1.getString("yourFeedbackItem.tooltip")); // NOI18N
        feedBackItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedBackItemActionPerformed(evt);
            }
        });
        helpMenu.add(feedBackItem);
        helpMenu.add(jSeparator7);

        AboutItem.setText(bundle1.getString("aboutItem.name")); // NOI18N
        AboutItem.setToolTipText(bundle1.getString("aboutItem.tooltip")); // NOI18N
        AboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutItemActionPerformed(evt);
            }
        });
        helpMenu.add(AboutItem);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        tabbedPane.add("glpk #"+this.glpkIndex, new GlpkPanel(this.dataBrowser));
        tabbedPane.setSelectedIndex(this.tabIndex-1);
        this.tabIndex++;
         this.addFileNameToList("glpk #"+this.glpkIndex);
        this.glpkIndex++;
        

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void closeAllItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeAllItemActionPerformed
        int i=tabbedPane.getTabCount()-1;
        while(i != -1) {

            removeFileNameFromList(i);
            tabbedPane.remove(i);
            this.tabIndex--;
            i--;
        }
    }//GEN-LAST:event_closeAllItemActionPerformed

    private void closeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeItemActionPerformed
        if(tabbedPane.getSelectedIndex() >= 0) {

            removeFileNameFromList(tabbedPane.getSelectedIndex());

            tabbedPane.remove(tabbedPane.getSelectedIndex());
            this.tabIndex--;

        }
    }//GEN-LAST:event_closeItemActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        tabbedPane.add("qp #"+this.qpIndex, new QpPanel(this.dataBrowser));
        tabbedPane.setSelectedIndex(this.tabIndex-1);
        this.tabIndex++;
         this.addFileNameToList("qp #"+this.qpIndex);
        this.qpIndex++;
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        tabbedPane.add("sqp #"+this.sqpIndex, new SqpPanel(this.dataBrowser));
        tabbedPane.setSelectedIndex(this.tabIndex-1);
        this.tabIndex++;
         this.addFileNameToList("sqp #"+this.sqpIndex);
        this.sqpIndex++;
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OptimizationFrame(null).setVisible(true);
            }
        });
    }
    
    public void setPath(String path) {
    try {
            URI uri = new URI(path);
            Desktop desktop=Desktop.getDesktop();
            desktop.browse(uri);
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
        }
}
  
      public String jar_path = "javaaddpath('"+System.getProperty("user.dir")+File.separator+"Results.jar');";

       private java.net.URL imgURL = getClass().getResource("resources/DomainMath.png");
    private Image icon = Toolkit.getDefaultToolkit().getImage(imgURL);
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private javax.swing.JMenuItem closeAllItem;
    private javax.swing.JMenuItem closeItem;
    private javax.swing.JMenuItem faqItem;
    private javax.swing.JMenuItem feedBackItem;
    private javax.swing.JMenuItem forumItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem howToItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JMenuItem onlineHelpItem;
    private javax.swing.JMenuItem reportBugItem;
    private org.domainmath.gui.StatusPanel statusPanel2;
    private javax.swing.JMenuItem suggestionsItem;
    // End of variables declaration//GEN-END:variables

     class PopupListener extends MouseAdapter {
        JPopupMenu popup;

        PopupListener(JPopupMenu popupMenu) {
            popup = popupMenu;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger() && tabbedPane.getTabCount() > 0) {
                popup.show(e.getComponent(),
                           e.getX(), e.getY());
            }
        }
    }
}
