
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
package org.domainmath.gui.dataview;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.about.AboutDlg;


public class DataViewFrame extends javax.swing.JFrame {

    private java.net.URL imgURL = getClass().getResource("resources/DomainMath.png");
    private Image icon = Toolkit.getDefaultToolkit().getImage(imgURL);
    private final DataViewPanel newContentPane;
    
    public static String REARRANGE_MATRIX_FUNCTIONS[] = {   "fliplr",
                                                            "flipud",
                                                            "flipdim",
                                                            "rot90",
                                                            "rotdim",
                                                            "sort", 
                                                            "sortrows",
                                                            "tril",
                                                            "vec",
                                                            "vech",
                                                            "diag",
                                                            "inverse",
                                                            "transpose"};
    
    
    public static String EXPONENTS_AND_LOGARITHMS_FUNCTIONS[] = {   "exp",
                                                                    "expm1", 
                                                                    "log",
                                                                    "reallog",
                                                                    "log1p",
                                                                    "log10", 
                                                                    "log2",
                                                                    "pow2",
                                                                    "nextpow2",
                                                                    "realpow",
                                                                    "sqrt",
                                                                    "realsqrt",
                                                                    "cbrt"};
    
    public static String COMPLEX_FUNCTIONS[] = {    "abs",
                                                    "arg",
                                                    "conj",
                                                    "cplxpair",
                                                    "imag",
                                                    "real"}; 
    
    public static String TRIGONOMETRY_FUNCTIONS[] = {   "sin",
                                                        "cos",
                                                        "tan",
                                                        "sec",
                                                        "csc",
                                                        "cot",
                                                        "asin",
                                                        "acos",
                                                        "atan",
                                                        "asec",
                                                        "acsc",
                                                        "acot",
                                                        "sinh",
                                                        "cosh",
                                                        "tanh",
                                                        "sech",
                                                        "csch",
                                                        "coth"};
    public static String TRIGONOMETRY_INVERSE_FUNCTIONS[] = {  "asin",
                                                        "acos",
                                                        "atan",
                                                        "asec",
                                                        "acsc",
                                                        "acot",
                                                        "asinh",
                                                        "acosh",
                                                        "atanh",
                                                        "asech",
                                                        "acsch",
                                                        "acoth",
                                                        "asind",
                                                        "acosd",
                                                        "atand",
                                                        "asecd",
                                                        "acscd",
                                                        "acotd"};
    public static String SUMS_AND_PRODUCTS_FUNCTIONS[] = {      "sum",
                                                                "prod",
                                                                "cumsum",
                                                                "cumprod"};
    public static String SPECIAL_FUNCTIONS[] = {    "erf",
                                                "erfc",
                                                "erfcx", 
                                                "erfinv",
                                                "gamma",
                                                "lgamma"};
    
    public static String UTILITY_FUNCTIONS[] = {"ceil",
                                                "fix",
                                                "floor",
                                                "round",
                                                "roundb",
                                                "max",
                                                "min",
                                                "cummax",
                                                "cummin",
                                                "gradient",
                                                "del2",
                                                "factorial",
                                                "factor",
                                                "primes",
                                                "sign"};
    
    public static String STATISTICS_FUNCTIONS[] ={  "mean", 
                                                    "median",
                                                    "mode",
                                                    "range",
                                                    "iqr",
                                                    "meansq", 
                                                    "std",
                                                    "var",
                                                    "skewness",
                                                    "kurtosis",
                                                    "statistics",
                                                    "center",
                                                    "zscore",
                                                    "perms",
                                                    "probit", 
                                                    "cloglog",
                                                    "table", 
                                                    "cov",
                                                    "corr",
                                                    "spearman",
                                                    "kendall"};
    
    public static String PLOT_FUNCTIONS[] = {  "plot",
                                                "semilogx",
                                                "semilogy",
                                                "loglog",
                                                "bar",
                                                "barh",
                                                "hist",
                                                "stairs",
                                                "stem",
                                                "plotmatrix",
                                                "pareto", 
                                                "contour",
                                                "contourf",
                                                "contour3",
                                                "pie",
                                                "pie3",
                                                "compass",
                                                "feather",
                                                "pcolor",
                                                "area",
                                                "comet", 
                                                "comet3",
                                                "mesh",
                                                "surf",
                                                "surfl",
                                                "lsurfnorm",
                                                "ribbon"};
    
    public static String IMAGE_FUNCTIONS[] ={"imshow", "image", "imagesc" };
    private  JMenuItem functionsItem;
    private final JMenu trigonometryInverseMenu;
    private final String _title;
    private String var_name;
    public DataViewFrame(String path) {
         
         File f = new File(path);
                _title= f.getName().substring(0, f.getName().indexOf(".dat"));
        setTitle("Variable View-"+_title);            
        newContentPane = new DataViewPanel(path);
                    newContentPane.setOpaque(true);
                    setContentPane(newContentPane);
                    pack();
            initComponents();
            
            for(int i= 0; i<REARRANGE_MATRIX_FUNCTIONS.length; i++) {
               functionsItem =new JMenuItem(REARRANGE_MATRIX_FUNCTIONS[i].toUpperCase()) ;
               this.rearrageMtxMenu.add(functionsItem);
               MenuAction();
            }
            
             for(int i= 0; i<EXPONENTS_AND_LOGARITHMS_FUNCTIONS.length; i++) {
                functionsItem=new JMenuItem(EXPONENTS_AND_LOGARITHMS_FUNCTIONS[i].toUpperCase()) ;
               this.expAndLogMenu.add(functionsItem);
               MenuAction();
            }
             
            for(int i= 0; i<COMPLEX_FUNCTIONS.length; i++) {
                functionsItem=new JMenuItem(COMPLEX_FUNCTIONS[i].toUpperCase()) ;
               this.complexMenu.add(functionsItem);
               MenuAction();
            }
            
            for(int i= 0; i<TRIGONOMETRY_FUNCTIONS.length; i++) {
               functionsItem =new JMenuItem(TRIGONOMETRY_FUNCTIONS[i].toUpperCase()) ;
               this.trigonometryMenu.add(functionsItem);
               MenuAction();
            }
            trigonometryInverseMenu = new JMenu("Inverse Functions");
            this.trigonometryMenu.add(trigonometryInverseMenu);
            
            for(int i= 0; i<TRIGONOMETRY_INVERSE_FUNCTIONS.length; i++) {
               functionsItem =new JMenuItem(TRIGONOMETRY_INVERSE_FUNCTIONS[i].toUpperCase()) ;
               this.trigonometryInverseMenu.add(functionsItem);
               MenuAction();
            }
             for(int i= 0; i< SUMS_AND_PRODUCTS_FUNCTIONS.length; i++) {
               functionsItem =new JMenuItem( SUMS_AND_PRODUCTS_FUNCTIONS[i].toUpperCase()) ;
               this.sumProdMenu.add(functionsItem);
               MenuAction();
            }
              for(int i= 0; i<UTILITY_FUNCTIONS.length; i++) {
               functionsItem =new JMenuItem(UTILITY_FUNCTIONS[i].toUpperCase()) ;
               this.utilityMenu.add(functionsItem);
               MenuAction();
            }
             
           for(int i= 0; i<SPECIAL_FUNCTIONS.length; i++) {
               functionsItem =new JMenuItem(SPECIAL_FUNCTIONS[i].toUpperCase()) ;
               this.specialFunctionsMenu.add(functionsItem);
               MenuAction();
            }
           for(int i= 0; i<STATISTICS_FUNCTIONS.length; i++) {
               functionsItem =new JMenuItem(STATISTICS_FUNCTIONS[i].toUpperCase()) ;
               this.StatisticsMenu.add(functionsItem);
               MenuAction();
            }
          for(int i= 0; i<PLOT_FUNCTIONS.length; i++) {
               functionsItem =new JMenuItem(PLOT_FUNCTIONS[i].toUpperCase()) ;
               this.plotMenu.add(functionsItem);
               PlotAndImageAction();
            }
          for(int i= 0; i<IMAGE_FUNCTIONS.length; i++) {
               functionsItem =new JMenuItem(IMAGE_FUNCTIONS[i].toUpperCase()) ;
               this.imageMenu.add(functionsItem);
               PlotAndImageAction();
            }
        this.setIconImage(icon);
        setLocationRelativeTo(null);
        setVisible(true);
        newContentPane.reload();
        newContentPane.reload();
    }

    private void MenuAction() {
        functionsItem.addActionListener(new ActionListener() {
            private String source;
           
            @Override
            public void actionPerformed(ActionEvent e) {
                source = e.getActionCommand().toLowerCase();
                findAns(source);
                
            }
            

           });
    }
    private void PlotAndImageAction() {
        functionsItem.addActionListener(new ActionListener() {
            private String source;
           
            @Override
            public void actionPerformed(ActionEvent e) {
                source = e.getActionCommand().toLowerCase();
                MainFrame.octavePanel.evaluate(source+"("+_title+")");
                
            }
            

           });
    }
    public void reloadData() {
        this.newContentPane.reload();
        this.newContentPane.reload();
    }
    public void findAns(String source) {
        var_name =_title;
                MainFrame.octavePanel.evaluate("if(exist('"+var_name+"','var'))");
                MainFrame.octavePanel.evaluate("tempName=genvarname ('"+var_name+"',who());");
                MainFrame.octavePanel.evaluate("eval ([tempName ' = "+var_name+";']);");
                MainFrame.octavePanel.evaluate(var_name+"="+source+"("+_title+");");
                MainFrame.octavePanel.evaluate("clear('tempName');");
                MainFrame.reloadWorkspace();
                
                MainFrame.octavePanel.evaluate("else");
                MainFrame.octavePanel.evaluate(var_name+"="+source+"("+_title+");");
                MainFrame.octavePanel.evaluate("endif");
                
                MainFrame.octavePanel.evaluate("DomainMath_OctaveDataView('"+MainFrame.log_root+var_name+".dat',"+var_name+");");

                newContentPane.reload();
                newContentPane.reload();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusPanel1 = new org.domainmath.gui.StatusPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        refreshItem = new javax.swing.JMenuItem();
        printItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        formulasMenu = new javax.swing.JMenu();
        rearrageMtxMenu = new javax.swing.JMenu();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        plotMenu = new javax.swing.JMenu();
        imageMenu = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        expAndLogMenu = new javax.swing.JMenu();
        complexMenu = new javax.swing.JMenu();
        trigonometryMenu = new javax.swing.JMenu();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        sumProdMenu = new javax.swing.JMenu();
        utilityMenu = new javax.swing.JMenu();
        specialFunctionsMenu = new javax.swing.JMenu();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        StatisticsMenu = new javax.swing.JMenu();
        helpMenu2 = new javax.swing.JMenu();
        forumItem2 = new javax.swing.JMenuItem();
        onlineHelpItem = new javax.swing.JMenuItem();
        howToItem = new javax.swing.JMenuItem();
        faqItem = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        suggestionsItem = new javax.swing.JMenuItem();
        reportBugItem1 = new javax.swing.JMenuItem();
        feedBackItem1 = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        AboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().add(statusPanel1, java.awt.BorderLayout.PAGE_END);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/dataview/resources/dataview_en"); // NOI18N
        fileMenu.setText(bundle.getString("fileMenu.title")); // NOI18N

        refreshItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        refreshItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/view-refresh.png"))); // NOI18N
        refreshItem.setText(bundle.getString("refreshItem.title")); // NOI18N
        refreshItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshItemActionPerformed(evt);
            }
        });
        fileMenu.add(refreshItem);

        printItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-print.png"))); // NOI18N
        printItem.setText(bundle.getString("printItem.title")); // NOI18N
        printItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printItemActionPerformed(evt);
            }
        });
        fileMenu.add(printItem);

        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        exitItem.setText(bundle.getString("exitItem.title")); // NOI18N
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitItem);

        jMenuBar1.add(fileMenu);

        formulasMenu.setText(bundle.getString("formulasMenu.title")); // NOI18N

        rearrageMtxMenu.setText(bundle.getString("rearrageMtxMenu.title")); // NOI18N
        formulasMenu.add(rearrageMtxMenu);
        formulasMenu.add(jSeparator4);

        plotMenu.setText(bundle.getString("plotMenu.title")); // NOI18N
        formulasMenu.add(plotMenu);

        imageMenu.setText(bundle.getString("imageMenu.title")); // NOI18N
        formulasMenu.add(imageMenu);
        formulasMenu.add(jSeparator1);

        expAndLogMenu.setText(bundle.getString("expAndLogMenu.title")); // NOI18N
        formulasMenu.add(expAndLogMenu);

        complexMenu.setText(bundle.getString("complexMenu.title")); // NOI18N
        complexMenu.setActionCommand("");
        formulasMenu.add(complexMenu);

        trigonometryMenu.setText(bundle.getString("trigonometryMenu.title")); // NOI18N
        formulasMenu.add(trigonometryMenu);
        formulasMenu.add(jSeparator2);

        sumProdMenu.setText(bundle.getString("sumProdMenu.title")); // NOI18N
        formulasMenu.add(sumProdMenu);

        utilityMenu.setText(bundle.getString("utilityMenu.title")); // NOI18N
        formulasMenu.add(utilityMenu);

        specialFunctionsMenu.setText(bundle.getString("specialFunctionsMenu.title")); // NOI18N
        formulasMenu.add(specialFunctionsMenu);
        formulasMenu.add(jSeparator3);

        StatisticsMenu.setText(bundle.getString("StatisticsMenu.title")); // NOI18N
        formulasMenu.add(StatisticsMenu);

        jMenuBar1.add(formulasMenu);

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en"); // NOI18N
        helpMenu2.setText(bundle1.getString("helpMenu.name")); // NOI18N

        forumItem2.setText("Forum");
        forumItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forumItem2ActionPerformed(evt);
            }
        });
        helpMenu2.add(forumItem2);

        onlineHelpItem.setText("Help and Support");
        onlineHelpItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineHelpItemActionPerformed(evt);
            }
        });
        helpMenu2.add(onlineHelpItem);

        howToItem.setText("How to...");
        howToItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                howToItemActionPerformed(evt);
            }
        });
        helpMenu2.add(howToItem);

        faqItem.setText("Online FAQ");
        faqItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faqItemActionPerformed(evt);
            }
        });
        helpMenu2.add(faqItem);
        helpMenu2.add(jSeparator16);

        suggestionsItem.setText("Suggestions");
        suggestionsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestionsItemActionPerformed(evt);
            }
        });
        helpMenu2.add(suggestionsItem);

        reportBugItem1.setText(bundle1.getString("reportBugItem.name")); // NOI18N
        reportBugItem1.setToolTipText(bundle1.getString("reportBugItem.tooltip")); // NOI18N
        reportBugItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportBugItem1ActionPerformed(evt);
            }
        });
        helpMenu2.add(reportBugItem1);

        feedBackItem1.setText(bundle1.getString("yourFeedbackItem.name")); // NOI18N
        feedBackItem1.setToolTipText(bundle1.getString("yourFeedbackItem.tooltip")); // NOI18N
        feedBackItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedBackItem1ActionPerformed(evt);
            }
        });
        helpMenu2.add(feedBackItem1);
        helpMenu2.add(jSeparator12);

        AboutItem.setText(bundle1.getString("aboutItem.name")); // NOI18N
        AboutItem.setToolTipText(bundle1.getString("aboutItem.tooltip")); // NOI18N
        AboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutItemActionPerformed(evt);
            }
        });
        helpMenu2.add(AboutItem);

        jMenuBar1.add(helpMenu2);

        setJMenuBar(jMenuBar1);

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
    private void forumItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forumItem2ActionPerformed
        setPath("http://domainmathide.freeforums.org/");
    }//GEN-LAST:event_forumItem2ActionPerformed

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

    private void reportBugItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportBugItem1ActionPerformed
        setPath("http://domainmathide.freeforums.org/bugs-f3.html");
    }//GEN-LAST:event_reportBugItem1ActionPerformed

    private void feedBackItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feedBackItem1ActionPerformed
        setPath("http://domainmathide.freeforums.org/feedback-f4.html");
    }//GEN-LAST:event_feedBackItem1ActionPerformed

    private void AboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutItemActionPerformed
        AboutDlg aboutDlg = new AboutDlg(this, true);
        aboutDlg.setLocationRelativeTo(this);
        aboutDlg.setVisible(true);
    }//GEN-LAST:event_AboutItemActionPerformed

    private void refreshItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshItemActionPerformed
        newContentPane.reload();
        newContentPane.reload();
    }//GEN-LAST:event_refreshItemActionPerformed

    private void printItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printItemActionPerformed
        try {
            newContentPane.table.print();
        } catch (PrinterException ex) {
            
        }
    }//GEN-LAST:event_printItemActionPerformed

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        dispose();
    }//GEN-LAST:event_exitItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataViewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataViewFrame("").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private javax.swing.JMenu StatisticsMenu;
    private javax.swing.JMenu complexMenu;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenu expAndLogMenu;
    private javax.swing.JMenuItem faqItem;
    private javax.swing.JMenuItem feedBackItem1;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu formulasMenu;
    private javax.swing.JMenuItem forumItem2;
    private javax.swing.JMenu helpMenu2;
    private javax.swing.JMenuItem howToItem;
    private javax.swing.JMenu imageMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JMenuItem onlineHelpItem;
    private javax.swing.JMenu plotMenu;
    private javax.swing.JMenuItem printItem;
    private javax.swing.JMenu rearrageMtxMenu;
    private javax.swing.JMenuItem refreshItem;
    private javax.swing.JMenuItem reportBugItem1;
    private javax.swing.JMenu specialFunctionsMenu;
    private org.domainmath.gui.StatusPanel statusPanel1;
    private javax.swing.JMenuItem suggestionsItem;
    private javax.swing.JMenu sumProdMenu;
    private javax.swing.JMenu trigonometryMenu;
    private javax.swing.JMenu utilityMenu;
    // End of variables declaration//GEN-END:variables
}
