
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
package org.domainmath.gui.code_editor;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import org.domainmath.gui.StatusPanel;
import org.domainmath.gui.about.AboutDlg;
import org.domainmath.gui.dialog.find_replace.FindAndReplaceDialog;
import org.domainmath.gui.editor.AutoCompleteListCellRenderer;
import org.domainmath.gui.editor.OctaveM;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaEditorKit;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextAreaEditorKit;
import org.fife.ui.rtextarea.RTextScrollPane;

public class CodeEditorFrame extends javax.swing.JFrame {

    public  RSyntaxTextArea area;
    public  RTextScrollPane scroll;
    CompletionProvider provider = createCompletionProvider();
  
    private String fname;
    private String dynareOptions;
    private String dynarePath;
    public   JTabbedPane fileTab = new JTabbedPane();
    private final StatusPanel status_panel;
    private List data =Collections.synchronizedList(new ArrayList());
    private String currentDir;
    private  List fileNameList =Collections.synchronizedList(new ArrayList());
    public static  int file_index;
    public CodeEditorFrame(String dynareOptions,String dynarePath) {
        this.setIconImage(icon);
        initComponents();
        
       this.setSize(800, 600);
        this.setLocationRelativeTo(null);
         file_index=0;
         fileTab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        this.dynareOptions =dynareOptions;
        this.dynarePath=dynarePath;
        this.popupTab();
        
       add(fileTab);
       status_panel=new StatusPanel();
        add(status_panel,BorderLayout.PAGE_END);
       currentDir = null;
    
    }

    public void addFileNameToList(String name) {
        fileNameList.add(name);
    }
    
    public void removeFileNameFromList(int index) {
        fileNameList.remove(index);
    }
    public String getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(String currentDir) {
        this.currentDir = currentDir;
    }
    public void dirty() {
        area.getDocument().addDocumentListener(
                new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                    if(fileTab.getTabRunCount() > 0) {
                        String n = fileTab.getTitleAt(fileTab.getSelectedIndex());
                        if(!n.endsWith("*")) {
                            fileTab.setTitleAt(fileTab.getSelectedIndex(), n+"*");
                       }    
                    }
            }
                    
            });
    }
    public void setUpArea() {
        area =new  RSyntaxTextArea();
      
        area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
        scroll = new RTextScrollPane(area);
        scroll.getGutter().setVisible(true);
        scroll.getGutter().setBookmarkingEnabled(true);
        scroll.setFoldIndicatorEnabled(true);
        needOct(true);
       
        scroll.setWheelScrollingEnabled(true);
        
         
    }
 
    public void saveAs() {
    JFileChooser fc = new JFileChooser();

       
        fc.setMultiSelectionEnabled(false);

        fc.setDialogTitle("Save As");
        File file;
        int returnVal = fc.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                    save(fc.getSelectedFile(),fileTab.getSelectedIndex());

        }
                 

            
}
    public void open(File file ,int index) {
     try {
            BufferedReader r = new BufferedReader(new FileReader(file));
            try {
               
                setUpArea();
                fname= file.getName();
                if(fname.endsWith(".m")) {
                         area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
                        
                        needOct(true);
                    }else if(fname.endsWith(".dyn")) {
                         area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
                         area.setCodeFoldingEnabled(true);
                        
                    }else if(fname.endsWith(".mod")) {
                         area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
                         area.setCodeFoldingEnabled(true);
                       
                    }else if(fname.endsWith(".pl")) {
                         area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PERL);
                        
                        
                    }
                    else {
                         area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
                        
                    }
                area.read(r, null);
                r.close();
                
                fileTab.addTab(file.getName(), scroll);
                fileTab.setToolTipTextAt(file_index, file.getAbsolutePath());
                this.addFileNameToList(fileTab.getToolTipTextAt(file_index));
                fileTab.setSelectedIndex(file_index);
                file_index++;
             
                dirty();
                
                 
            } catch (IOException ex) {
                Logger.getLogger(CodeEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CodeEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newFileItem = new javax.swing.JMenuItem();
        openItem = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        saveFileItem = new javax.swing.JMenuItem();
        saveAsFileItem = new javax.swing.JMenuItem();
        saveAllItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        closeItem = new javax.swing.JMenuItem();
        closeAllItem = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        printFileItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        undoItem = new javax.swing.JMenuItem();
        redoItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        cutItem = new javax.swing.JMenuItem();
        copyItem = new javax.swing.JMenuItem();
        pasteItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        deleteItem = new javax.swing.JMenuItem();
        selectAllItem = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        findItem = new javax.swing.JMenuItem();
        replaceItem = new javax.swing.JMenuItem();
        gotoItem = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        googleItem = new javax.swing.JMenuItem();
        wikiItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        forumItem = new javax.swing.JMenuItem();
        onlineHelpItem = new javax.swing.JMenuItem();
        howToItem = new javax.swing.JMenuItem();
        faqItem = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        suggestionsItem = new javax.swing.JMenuItem();
        reportBugItem1 = new javax.swing.JMenuItem();
        feedBackItem1 = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        AboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/code_editor/resources/CodeEditor_en"); // NOI18N
        setTitle(bundle.getString("CodeEditorFrame.title")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-new.png"))); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-open.png"))); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-save.png"))); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator4);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-undo.png"))); // NOI18N
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-redo.png"))); // NOI18N
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);
        jToolBar1.add(jSeparator5);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-cut.png"))); // NOI18N
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-copy.png"))); // NOI18N
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton7);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-paste.png"))); // NOI18N
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton8);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        fileMenu.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/code_editor/resources/CodeEditor_en").getString("FileMenu.mnemonic").charAt(0));
        fileMenu.setText(bundle.getString("FileMenu.name")); // NOI18N

        newFileItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newFileItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-new.png"))); // NOI18N
        newFileItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/code_editor/resources/CodeEditor_en").getString("newFileItem.mnemonic").charAt(0));
        newFileItem.setText("New");
        newFileItem.setToolTipText(bundle.getString("newFileItem.tooltip")); // NOI18N
        newFileItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileItemActionPerformed(evt);
            }
        });
        fileMenu.add(newFileItem);

        openItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-open.png"))); // NOI18N
        openItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/code_editor/resources/CodeEditor_en").getString("openFileItem.mnemonic").charAt(0));
        openItem.setText("Open...");
        openItem.setToolTipText("Open File");
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openItemActionPerformed(evt);
            }
        });
        fileMenu.add(openItem);
        fileMenu.add(jSeparator7);

        saveFileItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveFileItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-save.png"))); // NOI18N
        saveFileItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/code_editor/resources/CodeEditor_en").getString("saveFileItem.mnemonic").charAt(0));
        saveFileItem.setText("Save ");
        saveFileItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveFileItem);

        saveAsFileItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-save-as.png"))); // NOI18N
        saveAsFileItem.setText("Save As...");
        saveAsFileItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsFileItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsFileItem);

        saveAllItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveAllItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/code_editor/resources/CodeEditor_en").getString("saveAllItem.mnemonic").charAt(0));
        saveAllItem.setText(bundle.getString("saveAllItem.name")); // NOI18N
        saveAllItem.setToolTipText(bundle.getString("saveAllItem.tooltip")); // NOI18N
        saveAllItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAllItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAllItem);
        fileMenu.add(jSeparator1);

        closeItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.ALT_MASK));
        closeItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/code_editor/resources/CodeEditor_en").getString("closeItem.mnemonic").charAt(0));
        closeItem.setText(bundle.getString("closeItem.name")); // NOI18N
        closeItem.setToolTipText(bundle.getString("closeItem.tooltip")); // NOI18N
        closeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeItemActionPerformed(evt);
            }
        });
        fileMenu.add(closeItem);

        closeAllItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        closeAllItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/code_editor/resources/CodeEditor_en").getString("closeAllItem.mnemonic").charAt(0));
        closeAllItem.setText(bundle.getString("closeAllItem.name")); // NOI18N
        closeAllItem.setToolTipText(bundle.getString("closeAllItem.tooltip")); // NOI18N
        closeAllItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeAllItemActionPerformed(evt);
            }
        });
        fileMenu.add(closeAllItem);
        fileMenu.add(jSeparator8);

        printFileItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printFileItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-print2.png"))); // NOI18N
        printFileItem.setText("Print");
        printFileItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printFileItemActionPerformed(evt);
            }
        });
        fileMenu.add(printFileItem);

        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitItem);

        jMenuBar1.add(fileMenu);

        editMenu.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/code_editor/resources/CodeEditor_en").getString("EditMenu.mnemonic").charAt(0));
        editMenu.setText(bundle.getString("EditMenu.name")); // NOI18N

        undoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-undo.png"))); // NOI18N
        undoItem.setText("Undo");
        undoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoItemActionPerformed(evt);
            }
        });
        editMenu.add(undoItem);

        redoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redoItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-redo.png"))); // NOI18N
        redoItem.setText("Redo");
        redoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoItemActionPerformed(evt);
            }
        });
        editMenu.add(redoItem);
        editMenu.add(jSeparator2);

        cutItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cutItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-cut.png"))); // NOI18N
        cutItem.setText("Cut");
        cutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutItemActionPerformed(evt);
            }
        });
        editMenu.add(cutItem);

        copyItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-copy.png"))); // NOI18N
        copyItem.setText("Copy");
        copyItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyItemActionPerformed(evt);
            }
        });
        editMenu.add(copyItem);

        pasteItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        pasteItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-paste.png"))); // NOI18N
        pasteItem.setText("Paste");
        pasteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteItemActionPerformed(evt);
            }
        });
        editMenu.add(pasteItem);
        editMenu.add(jSeparator3);

        deleteItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        deleteItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-delete.png"))); // NOI18N
        deleteItem.setText("Delete");
        deleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteItemActionPerformed(evt);
            }
        });
        editMenu.add(deleteItem);

        selectAllItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        selectAllItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-select-all.png"))); // NOI18N
        selectAllItem.setText("Select All");
        selectAllItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllItemActionPerformed(evt);
            }
        });
        editMenu.add(selectAllItem);
        editMenu.add(jSeparator11);

        jMenuItem2.setAction(new RSyntaxTextAreaEditorKit.InsertTabAction());
        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Increase Indent");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        editMenu.add(jMenuItem2);

        jMenuItem3.setAction(new RSyntaxTextAreaEditorKit.DecreaseIndentAction());
        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem3.setText("Decrease Indent");
        editMenu.add(jMenuItem3);
        editMenu.add(jSeparator10);

        jMenuItem4.setAction(new RTextAreaEditorKit.UpperSelectionCaseAction());
        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Make Uppercase");
        editMenu.add(jMenuItem4);

        jMenuItem5.setAction(new RTextAreaEditorKit.LowerSelectionCaseAction());
        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Make Lowercase");
        editMenu.add(jMenuItem5);
        editMenu.add(jSeparator12);

        jMenuItem6.setText("Clear All Marks...");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        editMenu.add(jMenuItem6);

        jMenuItem1.setAction( new RSyntaxTextAreaEditorKit.ToggleCommentAction());
        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SLASH, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Toggle Comment");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        editMenu.add(jMenuItem1);

        jMenuBar1.add(editMenu);

        jMenu1.setMnemonic('S');
        jMenu1.setText("Search");

        findItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        findItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-find.png"))); // NOI18N
        findItem.setMnemonic('F');
        findItem.setText("Find...");
        findItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findItemActionPerformed(evt);
            }
        });
        jMenu1.add(findItem);

        replaceItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        replaceItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/edit-find-replace.png"))); // NOI18N
        replaceItem.setText("Replace...");
        replaceItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceItemActionPerformed(evt);
            }
        });
        jMenu1.add(replaceItem);

        gotoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        gotoItem.setText("Go To...");
        gotoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gotoItemActionPerformed(evt);
            }
        });
        jMenu1.add(gotoItem);
        jMenu1.add(jSeparator13);

        googleItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        googleItem.setText("Google Search");
        googleItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                googleItemActionPerformed(evt);
            }
        });
        jMenu1.add(googleItem);

        wikiItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        wikiItem.setText("Wikipedia Search");
        wikiItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wikiItemActionPerformed(evt);
            }
        });
        jMenu1.add(wikiItem);

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
        helpMenu.add(jSeparator16);

        suggestionsItem.setText("Suggestions");
        suggestionsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestionsItemActionPerformed(evt);
            }
        });
        helpMenu.add(suggestionsItem);

        reportBugItem1.setText(bundle1.getString("reportBugItem.name")); // NOI18N
        reportBugItem1.setToolTipText(bundle1.getString("reportBugItem.tooltip")); // NOI18N
        reportBugItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportBugItem1ActionPerformed(evt);
            }
        });
        helpMenu.add(reportBugItem1);

        feedBackItem1.setText(bundle1.getString("yourFeedbackItem.name")); // NOI18N
        feedBackItem1.setToolTipText(bundle1.getString("yourFeedbackItem.tooltip")); // NOI18N
        feedBackItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedBackItem1ActionPerformed(evt);
            }
        });
        helpMenu.add(feedBackItem1);
        helpMenu.add(jSeparator9);

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

    public void open(){
      
        JFileChooser fc = new JFileChooser();

          if(fileTab.getSelectedIndex() >= 0) { 
              File f = new File(currentDir);
               fc.setCurrentDirectory(f);  
          }
           
       FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "M-Files  (*.m)", "m");
        FileNameExtensionFilter filter_dyn = new FileNameExtensionFilter(
        "Dynare-Files  (*.mod; *.dyn)", "mod","dyn");
        fc.setAcceptAllFileFilterUsed(false);
        
         fc.setFileFilter(filter_dyn);
         fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(true);

        
        File file[];
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
                   
                file = fc.getSelectedFiles();
                
                this.setCurrentDir(fc.getCurrentDirectory().getAbsolutePath());
                  for(int i=0;i<file.length;i++) {
                            if(!fileNameList.contains(file[i].getAbsolutePath())) {
                               open(file[i],i);
                                
                            }else {
                                System.out.println(file[i].getAbsolutePath()+" already open!");
                            }
                    }  
      
        }
    }
    private void openItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openItemActionPerformed
        open();
    }//GEN-LAST:event_openItemActionPerformed

    private void save() {
        if(fileTab.getSelectedIndex()>= 0) {
           String file = fileTab.getToolTipTextAt(fileTab.getSelectedIndex());
           String fl =fileTab.getTitleAt(fileTab.getSelectedIndex());
        
         
            if(fl.endsWith("*")) {
                File f =new File(file);
                save(f,fileTab.getSelectedIndex());
            } 
        }
                   
            
        
    }
    
    
    private void undo() {
        if(fileTab.getSelectedIndex()>= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.undoLastAction();
            
        }
    }
    private void redo() {
        if(fileTab.getSelectedIndex()>= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.redoLastAction();
            
        }
    }
    private void cut() {
        if(fileTab.getSelectedIndex()>= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.cut();
            
        }
    }
    private void copy() {
        if(fileTab.getSelectedIndex()>= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.copy();
            
        }
    }
    private void paste() {
        if(fileTab.getSelectedIndex()>= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.paste();
            
        }
    }
    private void selectAll() {
        if(fileTab.getSelectedIndex()>= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.selectAll();
            
        }
    }
    private void saveFileItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFileItemActionPerformed
       
        save();
    }//GEN-LAST:event_saveFileItemActionPerformed

    private void saveAsFileItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsFileItemActionPerformed
        saveAs();
    }//GEN-LAST:event_saveAsFileItemActionPerformed

    private void printFileItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printFileItemActionPerformed
        printdoc();
    }//GEN-LAST:event_printFileItemActionPerformed

    private void undoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoItemActionPerformed
      this.undo();
    }//GEN-LAST:event_undoItemActionPerformed

    private void redoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoItemActionPerformed
      this.redo();
    }//GEN-LAST:event_redoItemActionPerformed

    private void cutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutItemActionPerformed
     this.cut();
    }//GEN-LAST:event_cutItemActionPerformed

    private void copyItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyItemActionPerformed
      this.copy();
    }//GEN-LAST:event_copyItemActionPerformed

    private void pasteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteItemActionPerformed
       this.paste();
    }//GEN-LAST:event_pasteItemActionPerformed

    private void selectAllItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllItemActionPerformed
      this.selectAll();
    }//GEN-LAST:event_selectAllItemActionPerformed

    private void deleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteItemActionPerformed
        this.deleteText();
    }//GEN-LAST:event_deleteItemActionPerformed

    private void newFileItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFileItemActionPerformed
        newFile();
    }//GEN-LAST:event_newFileItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int i=fileTab.getTabCount()-1;
                while(i != -1) {
                   
                    askSave(i);
                    i--;
                }
      this.dispose();
       
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        newFile();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.open();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.save();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.undo();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
         this.redo();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       this.cut();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
         this.copy();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        this.paste();
    }//GEN-LAST:event_jButton8ActionPerformed

    public void setPath(String path) {
    try {
            URI uri = new URI(path);
            Desktop desktop=Desktop.getDesktop();
            desktop.browse(uri);
        } catch (URISyntaxException | IOException ex) {
        }
}
    private void closeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeItemActionPerformed
         if(fileTab.getSelectedIndex()>= 0) { 
                    askSave(fileTab.getSelectedIndex());
               }
    }//GEN-LAST:event_closeItemActionPerformed

    private void closeAllItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeAllItemActionPerformed
        int i=fileTab.getTabCount()-1;
                while(i != -1) {
                   
                    askSave(i);
                    i--;
                }
    }//GEN-LAST:event_closeAllItemActionPerformed

    private void saveAllItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAllItemActionPerformed
        for(int i=0;i<fileTab.getTabCount();i++) {
            
            String file = fileTab.getToolTipTextAt(i);
            String fl = fileTab.getTitleAt(i);
        
         
            if(fl.endsWith("*")) {
                File f =new File(file);
                save(f,i);
            } 
            
        }
    }//GEN-LAST:event_saveAllItemActionPerformed

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
         int i=fileTab.getTabCount()-1;
                while(i != -1) {
                   
                    askSave(i);
                    i--;
                }
             this.dispose();
           
       
    }//GEN-LAST:event_exitItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
         
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        if(fileTab.getSelectedIndex()>= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
             selectedArea.clearMarkAllHighlights();
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void AboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutItemActionPerformed
        AboutDlg aboutDlg = new AboutDlg(this, true);
        aboutDlg.setLocationRelativeTo(this);
        aboutDlg.setVisible(true);
    }//GEN-LAST:event_AboutItemActionPerformed

    private void findItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findItemActionPerformed
        if(fileTab.getSelectedIndex()>= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            FindAndReplaceDialog find = new FindAndReplaceDialog(this,false,fileTab,selectedArea.getSelectedText());
            find.setVisible(true); 
        
        }
    }//GEN-LAST:event_findItemActionPerformed

    private void replaceItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceItemActionPerformed
        if(fileTab.getSelectedIndex()>= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            FindAndReplaceDialog find = new FindAndReplaceDialog(this,false,fileTab,selectedArea.getSelectedText());
            find.setVisible(true); 
        
        }
    }//GEN-LAST:event_replaceItemActionPerformed

    private void googleItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_googleItemActionPerformed
        if(fileTab.getSelectedIndex()>= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            String s = selectedArea.getSelectedText();
            if(!s.equals("")) {
                String f="http://www.google.com/search?q="+s.replaceAll(" ", "+");
                setPath(f);
            }
            
        }
    }//GEN-LAST:event_googleItemActionPerformed

    private void wikiItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wikiItemActionPerformed
        if(fileTab.getSelectedIndex()>= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            String s = selectedArea.getSelectedText();
            if(!s.equals("")) {
                String f="http://en.wikipedia.org/wiki/Special:Search?search="+s.replaceAll(" ", "+");
                setPath(f);
            }
            
        }
    }//GEN-LAST:event_wikiItemActionPerformed

    private void gotoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gotoItemActionPerformed
       if(fileTab.getSelectedIndex()>= 0) {
        String s = JOptionPane.showInputDialog("Line Number:");
        RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
        try {
           	Element element = selectedArea.getDocument().getDefaultRootElement();
			int lineRequested = Integer.parseInt(s);
			int rowCount = element.getElementCount();
			if (lineRequested > rowCount || lineRequested < 0) {
				
				setVisible(false);
				return;
			}
			Element row = null;
			int firstCharacter = 0;
			int rowNumber = 0;
			for (int i = 0; i < lineRequested; ++i) {
				firstCharacter = getFirstCharacter(row);
				rowNumber = element.getElementIndex(firstCharacter);
				row = element.getElement(rowNumber);
			}
			int lastColumnInRow = row.getEndOffset();
			selectedArea.select(firstCharacter, lastColumnInRow - 1);
		
        }catch(Exception e) {
            
        }
       } 
    }//GEN-LAST:event_gotoItemActionPerformed

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

    private void reportBugItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportBugItem1ActionPerformed
        setPath("http://domainmathide.freeforums.org/bugs-f3.html");
    }//GEN-LAST:event_reportBugItem1ActionPerformed

    private void feedBackItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feedBackItem1ActionPerformed
        setPath("http://domainmathide.freeforums.org/feedback-f4.html");
    }//GEN-LAST:event_feedBackItem1ActionPerformed
    private int getFirstCharacter(Element row) {
		if (row == null)
			return 0;
		int lastColumnInRow = row.getEndOffset();
		return lastColumnInRow;
	}
    
    public void newFile() {
        NewFileDialog newFileDlg =new NewFileDialog(this,true,this.dynareOptions,this.dynarePath);
        newFileDlg.setLocationRelativeTo(this);
        newFileDlg.setVisible(true);
    }
    public void deleteText() {
       
	 RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
	RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
        RSyntaxTextArea textArea = (RSyntaxTextArea) t.getTextArea();
        boolean beep = true;
			if ((textArea != null) && (textArea.isEditable())) {
				try {
					Document doc = textArea.getDocument();
					Caret caret = textArea.getCaret();
					int dot = caret.getDot();
					int mark = caret.getMark();
					if (dot != mark) {
						doc.remove(Math.min(dot, mark), Math.abs(dot - mark));
						beep = false;
					}
					else if (dot < doc.getLength()) {
						int delChars = 1;
						if (dot < doc.getLength() - 1) {
							String dotChars = doc.getText(dot, 2);
							char c0 = dotChars.charAt(0);
							char c1 = dotChars.charAt(1);
							if (c0 >= '\uD800' && c0 <= '\uDBFF' &&
								c1 >= '\uDC00' && c1 <= '\uDFFF') {
								delChars = 2;
							}
						}
						doc.remove(dot, delChars);
						beep = false;
					}
				} catch (Exception bl) {
				}
			}

			if (beep)
				UIManager.getLookAndFeel().provideErrorFeedback(textArea);

			textArea.requestFocusInWindow();
}
     public void needOct(boolean need) {
          AutoCompletion ac = new AutoCompletion(provider);

        if(need) {
             ac.install(this.area);
             
        }
         else   {
              ac.uninstall();
         }
    }
   public CompletionProvider createCompletionProvider() {


      DefaultCompletionProvider provider = new DefaultCompletionProvider();

      
      JList l2 = new JList();
     
        AutoCompleteListCellRenderer cellRender = new  AutoCompleteListCellRenderer(l2.getFont(),
                                               l2.getBackground(),l2.getForeground(),
                                               l2.getSelectionBackground(),l2.getSelectionForeground());
      provider.setListCellRenderer(cellRender);
      
      OctaveM _m = new OctaveM();
      List a = _m.getKey("DomainMath_OctaveAutoComplete.ini");
      
     
      for(int i=0;i<a.size();i++) {
          provider.addCompletion(new BasicCompletion(provider, a.get(i).toString()));
      }
     

      
      return provider;

   }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
       JFrame.setDefaultLookAndFeelDecorated(true);    
                 try {
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
       }
         
       
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new CodeEditorFrame("","").setVisible(true);
            }
        });
    }
    public  java.net.URL imgURL = getClass().getResource("resources/DomainMath.png");
    public   Image icon = Toolkit.getDefaultToolkit().getImage(imgURL);
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private javax.swing.JMenuItem closeAllItem;
    private javax.swing.JMenuItem closeItem;
    private javax.swing.JMenuItem copyItem;
    private javax.swing.JMenuItem cutItem;
    private javax.swing.JMenuItem deleteItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem faqItem;
    private javax.swing.JMenuItem feedBackItem1;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem findItem;
    private javax.swing.JMenuItem forumItem;
    private javax.swing.JMenuItem googleItem;
    private javax.swing.JMenuItem gotoItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem howToItem;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem newFileItem;
    private javax.swing.JMenuItem onlineHelpItem;
    private javax.swing.JMenuItem openItem;
    private javax.swing.JMenuItem pasteItem;
    private javax.swing.JMenuItem printFileItem;
    private javax.swing.JMenuItem redoItem;
    private javax.swing.JMenuItem replaceItem;
    private javax.swing.JMenuItem reportBugItem1;
    private javax.swing.JMenuItem saveAllItem;
    private javax.swing.JMenuItem saveAsFileItem;
    private javax.swing.JMenuItem saveFileItem;
    private javax.swing.JMenuItem selectAllItem;
    private javax.swing.JMenuItem suggestionsItem;
    private javax.swing.JMenuItem undoItem;
    private javax.swing.JMenuItem wikiItem;
    // End of variables declaration//GEN-END:variables

    public void save(File file,int index) {
      
     try {
            try (BufferedWriter r = new BufferedWriter(new FileWriter(file))) {
                //setUpArea();
                this.fileTab.setTitleAt(index,file.getName());
               // System.out.println(index+","+file.getAbsolutePath());
                //this.area.write(r);
                RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(index);
                RSyntaxTextArea a = (RSyntaxTextArea)t.getTextArea();
                a.write(r);
                r.close();
                this.setCurrentDir(file.getParent());
            }
                       
		} catch (Exception re) {
		JOptionPane.showMessageDialog(this,re.toString(),"Error",JOptionPane.ERROR_MESSAGE);
                
		}
}

    private void printdoc() {
        try {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
	RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.print();
        } catch (PrinterException ex) {
            Logger.getLogger(CodeEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void popupTab(){
        JPopupMenu popup = new JPopupMenu();
        JMenuItem pcloseItem = new JMenuItem("Close");
        JMenuItem pcloseAllItem = new JMenuItem("Close All");
        
        popup.add(pcloseItem);
        popup.add(pcloseAllItem);
        fileTab.addMouseListener(new PopupListener(popup));
        
        pcloseItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileTab.getSelectedIndex()>= 0) { 
                    askSave(fileTab.getSelectedIndex());
               }
               
            }

            
           
        });
        
        pcloseAllItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int i=fileTab.getTabCount()-1;
                while(i != -1) {
                   
                    askSave(i);
                    i--;
                }
                    
               
               
            }

            
           
        });
    }
    public void askSave(int selectedIndex) {
            String s = fileTab.getTitleAt(selectedIndex) ; 
            
            if(s.endsWith("*")) {
                String f =s.substring(0, s.lastIndexOf("*"));
                 int i = 
                        JOptionPane.showConfirmDialog(this, 
"Do you want to save changes in "+f+" ?", "DomainMath IDE", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                 if(i == JOptionPane.YES_OPTION) {
                File file = new File(fileTab.getToolTipTextAt(selectedIndex));
                     save(file,selectedIndex);
                    
                   
                 }else if (i == JOptionPane.NO_OPTION){
                      this.removeFileNameFromList(selectedIndex);
                     fileTab.remove(selectedIndex);
                     file_index--;
                    
                 }
            }else {
                this.removeFileNameFromList(selectedIndex);
                fileTab.remove(selectedIndex);
                file_index--;
                 
            }
    }

   
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
            if (e.isPopupTrigger() && fileTab.getTabCount() > 0) {
                popup.show(e.getComponent(),
                           e.getX(), e.getY());
            }
        }
    }
}
