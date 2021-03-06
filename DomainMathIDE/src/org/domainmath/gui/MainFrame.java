
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


package org.domainmath.gui;

import jalview.bin.Cache;
import jalview.gui.AlignFrame;
import jalview.util.Platform;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import net.iharder.dnd.FileDrop;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.DockingWindowAdapter;
import net.infonode.docking.FloatingWindow;
import net.infonode.docking.OperationAbortedException;
import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.View;
import net.infonode.docking.ViewSerializer;
import net.infonode.docking.WindowBar;
import net.infonode.docking.mouse.DockingWindowActionMouseButtonListener;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.theme.ShapedGradientDockingTheme;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.MixedViewHandler;
import net.infonode.docking.util.PropertiesUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.util.Direction;
import org.domainmath.gui.Util.DomainMathFileFilter;
import org.domainmath.gui.about.AboutDlg;
import org.domainmath.gui.arrayeditor.ArrayEditorFrame;
import org.domainmath.gui.arrayeditor.ArrayEditorPanel;
import org.domainmath.gui.code_editor_dle.DLECodeEditorFrame;
import org.domainmath.gui.dialog.find_replace.FindAndReplaceDialog;
import org.domainmath.gui.editor.AutoCompleteListCellRenderer;
import org.domainmath.gui.editor.OctaveM;
import org.domainmath.gui.octave.OctavePanel;
import org.domainmath.gui.packages.bioinfo.HmmerFrame;
import org.domainmath.gui.packages.bioinfo.seq_viewer.SeqViewerFrame;
import org.domainmath.gui.packages.datasmooth.DataSmoothFrame;
import org.domainmath.gui.packages.db.DataBaseFrame;
import org.domainmath.gui.packages.image.ImageToolFrame;
import org.domainmath.gui.packages.nnet.NnetFrame;
import org.domainmath.gui.packages.optim.OptimizationFrame;
import org.domainmath.gui.pathsview.PathsViewPanel;
import org.domainmath.gui.pkgview.PkgViewPanel;
import org.domainmath.gui.preferences.PreferencesDlg;
import org.domainmath.gui.tools.dynare.DynareDlg;
import org.domainmath.gui.tools.multicore.MulticoreDialog;
import org.domainmath.gui.tools.worksheet.WorksheetFrame;
import org.domainmath.gui.workspace.WorkspacePanel;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaEditorKit;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.GutterIconInfo;
import org.fife.ui.rtextarea.RTextAreaEditorKit;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * It creates DomainMath IDE 
 * @author Vinu K.N
 */
public final class MainFrame extends javax.swing.JFrame {

    /**
     * Core of DomainMath IDE.
     * @see OctavePanel
     */
    public static OctavePanel octavePanel;
    
    /**
     * Object of commandArea for special purpose
     * @see OctavePanel
     * @see RSyntaxTextArea
     */
    private final RSyntaxTextArea commandArea;
    
    /**
     * Object of PreferencesDlg.
     */
    private  PreferencesDlg preferencesDlg;
    
    /**
     * Octave Installation path.
     * @see PreferencesDlg
     */
    public  static String octavePath;
    
    /**
     * Startup commands like graphics_toolkit gnuplot,pkg load java,....
     * Each commands are separated  by ;
     * @see PreferencesDlg
     */
    private final String startupCmd;
    
    /**
     * Startup commands like --interactive --no-history,....
     * see octave --help for more info about command line options
     * @see PreferencesDlg
     */
    private final String cmdLineOptions;
   
    /**
     * Creates a folder called cache to store details of path,packages etc.
     */
    private final File cache;
    
    /**
     * Parent root.
     */
    public static String parent_root;
    
    /**
     * History of commands.
     * @see RSyntaxTextArea
     */
    public static RSyntaxTextArea histArea;
    
    /**
     * ScrollPane of histArea.
     * @see histArea
     * @see RTextScrollPane
     */
    private final RTextScrollPane histScrollPane;
    
    
    /**
     * Workspace Panel.
     */
    public static WorkspacePanel workspace;
    
    /**
     * Log Root.
     */
    public static String log_root;

    /**
     * Text Area in a FileTab.
     */
    public  RSyntaxTextArea areaFileTab;
    
    /**
     * ScrollPane of areaFileTab.
     */
    public  RTextScrollPane scroll1;
    
    CompletionProvider provider1 = createCompletionProvider();
  
    /**
     * Selected file to Open in a file tab.
     */
    private String selectedFileToOpen;
    
    /**
     * Dynare Options.
     */
    private String dynareOptions;
    
    /**
     * Dynare installation path.
     */
    private String dynarePath;
    
    /**
     * Script Editor.
     */
    public   JTabbedPane fileTab = new JTabbedPane();

    /**
     * Folder or Parent of last selected file in file tab.
     */
    private String currentDirFileTab;
    
    /**
     * Names of selected files including full path.
     */
    public static  List fileNameList =Collections.synchronizedList(new ArrayList());

  
    
    /**
     * Create a folder called log.
     */
    private final File logDir;
    
    /**
     * Index of files in the file tabbed pane.
     */
    public static  int FILE_TAB_INDEX;
    
    /**
     * Gutter of FileTab.
     */
    private Gutter gutterFileTab;
    
    /**
     * URL of image.
     */
    private URL urlDebugImageStop;
    
    /**
     * Maximum number of recent files list.
     */
    private static int MAX_NUM_RECENT_FILES=10;
    
    /**
     * Recent menu's order.
     */
    private static int RECENT_MENU_ORDER=2;
    
    /**
     * Start up or default directory.
     */
    private final String startupDir;
    
    /**
     * Status bar.
     */
    public static StatusPanel statusPanel;
    
    /**
     * Menu contain recently opened files list.
     */
    private final RecentFileMenu recentFileMenu;
    
    private final PathsViewPanel pathPanel;
    private final PkgViewPanel pkgViewPanel;
    
    /**
     * Views of different panels.
     */
    private View fileTabView;
    private View pathsView;
    private View pkgView;
    private View consoleView;
    private View arrayEditorView;
    private View workspaceView;
    private RootWindow rootWindow;
    
    private ViewMap viewMap = new ViewMap();
    private HashMap dynamicViews = new HashMap();
    private DockingWindowsTheme currentTheme = new ShapedGradientDockingTheme();
    private RootWindowProperties properties = new RootWindowProperties();
    final RootWindowProperties titleBarStyleProperties = PropertiesUtil.createTitleBarStyleRootWindowProperties();
    TabWindow toolsTabWindow = new TabWindow();
    TabWindow workspaceAndFilesTabWindow = new TabWindow();
    private final View filesView;
    private View historyView;
    
    /** 
     * Creates new form MainFrame.
     */
    public MainFrame()  {
        
         // create folders called cache and log.
        cache = new File(System.getProperty("user.dir")+File.separator+"cache");
        logDir = new File(System.getProperty("user.dir")+File.separator+"log");
        cache.mkdir();
        logDir.mkdir();
        
        parent_root=cache.getAbsolutePath()+File.separator;
        log_root=logDir.getAbsolutePath()+File.separator;

        initComponents();
        makeMenu();
        
        setIconImage(icon);
        setSize(800,600);
        setLocationRelativeTo(null);
        
        FILE_TAB_INDEX =0;
        
        //create octavepanel.
        octavePanel = new OctavePanel(this,parent_root);
        commandArea = octavePanel.commandArea;
        
        preferencesDlg = new PreferencesDlg(this,true);
        octavePath =preferencesDlg.getPath();
        startupCmd =preferencesDlg.getStartupCmd();
        cmdLineOptions = preferencesDlg.getCmdLineOptions();
        
        // set up histroy widget.
        histArea = new RSyntaxTextArea();
        histArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_OCTAVE);
        histScrollPane  =new RTextScrollPane(histArea);
        histScrollPane.setWheelScrollingEnabled(true);
        
        workspace =new WorkspacePanel(parent_root+"DomainMath_OctaveVariables.dat",this);

      

        recentFileMenu=new RecentFileMenu("RecentFiles",MAX_NUM_RECENT_FILES){
            @Override
        	public void onSelectFile(String filePath,ActionEvent action_event){
        		onRecentFile(filePath,action_event);
        	}

           
    	};
        this.fileMenu.add(recentFileMenu,MainFrame.RECENT_MENU_ORDER);
        
        statusPanel = new StatusPanel();
         fileTab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
     
        pathPanel = new PathsViewPanel(parent_root+"DomainMath_OctavePaths.dat");
       pkgViewPanel = new PkgViewPanel(parent_root+"DomainMath_OctavePackages.dat",this);
      
        
        histPanel();
        this.popupTab();
        Preferences pr2 = Preferences.userNodeForPackage(this.getClass());
        String path2 =pr2.get("DomainMath_DynarePath",null);
        this.dynarePath=path2;
        this.dynareOptions=this.DynareOptions();
            
        
       startupDir=pr2.get("DomainMath_StartUpDir", null);
       currentDirFileTab = null;
        fileTabView=new View("Code Editor", null, fileTab);
       pathsView=new View("Set Paths", null, pathPanel);
       pkgView = new View("Packages", null, pkgViewPanel);
       consoleView = new View("Console", null, octavePanel);
       arrayEditorView = new View("Array Editor", null, new ArrayEditorPanel());
       workspaceView=new View("Workspace", null, workspace);
       filesView=new View("Files", null, new FilesBreadCrumb(this));
       createRootWindow();
        setDefaultLayout();
      
        add(rootWindow, BorderLayout.CENTER);
        add(statusPanel,BorderLayout.PAGE_END);
       dragNDrop();
 
    }

     public void initTabComponent(int i) {
        fileTab.setTabComponentAt(FILE_TAB_INDEX,
                 new ButtonTabComponent(this));
    } 
     
    /**
     * Returns folder or Parent of last selected file in file tab.
     * @return currentDirFileTab
     */
    public String getCurrentDirFileTab() {
        return currentDirFileTab;
    }

     private void onRecentFile(String fileName,ActionEvent action_event) {
             File file1=new File(fileName);
        if(file1.exists()) {
            if(!MainFrame.fileNameList.contains(file1.getAbsolutePath())) {

           open(file1, MainFrame.FILE_TAB_INDEX);
          setCurrentDirFileTab(file1.getParent()); 
         
        }else {
            System.out.println(file1.getAbsolutePath()+" already open!");
        }
        }else{
            JOptionPane.showMessageDialog(this, file1.getName()+" does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            JMenuItem item = (JMenuItem) action_event.getSource();
            recentFileMenu.remove(item);
            
        }    
     }
    /**
     * Set folder or Parent of last selected file in file tab.
     * @param currentDirFileTab
     */
    public void setCurrentDirFileTab(String currentDir) {
        
        this.currentDirFileTab = currentDir;
    }
    
    public static void reloadWorkspace() {
        if(MainFrame.automaticRefreshCheckBoxMenuItem.isSelected()) {
        MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+MainFrame.parent_root+"DomainMath_OctaveVariables.dat',whos);");
        workspace.reload();
        }
    }
    
    public void dirty() {
        areaFileTab.getDocument().addDocumentListener(
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
    
    /**
     * Set TextArea with octave syntax style.
     */
    public void setUpArea() {
        areaFileTab =new  RSyntaxTextArea();
      
        areaFileTab.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_OCTAVE);
        scroll1 = new RTextScrollPane(areaFileTab);
         gutterFileTab = scroll1.getGutter();
        gutterFileTab.setVisible(true);
        gutterFileTab.setBookmarkingEnabled(true);
        urlDebugImageStop = getClass().getResource("resources/stop.png");

        gutterFileTab.setFoldIndicatorEnabled(true);
        needOct(true);
      
        scroll1.setWheelScrollingEnabled(true);
        
         
    }
    
    
     
    public void saveAs() {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);

        fc.setDialogTitle("Save As");
        File file1;
        int returnVal = fc.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                String oldfile = fileTab.getToolTipTextAt(fileTab.getSelectedIndex());
                    save(fc.getSelectedFile(),fileTab.getSelectedIndex());
                    this.removeFileNameFromList(oldfile);
                    fileTab.setToolTipTextAt(fileTab.getSelectedIndex(), fc.getSelectedFile().getAbsolutePath());
                    this.addFileNameToList(fc.getSelectedFile().getAbsolutePath());
        }
       
    }
    public void addFileNameToList(String name) {
        fileNameList.add(name);
    }
    
    
    public void removeFileNameFromList(int index) {
        fileNameList.remove(index);
    }
    public void removeFileNameFromList(String name) {
        fileNameList.remove(name);
    }
    public void open(File file ,int file_index) {
     selectedFileToOpen= file.getName();
     if(selectedFileToOpen.endsWith(".m") ||
             selectedFileToOpen.endsWith(".dyn") ||
             selectedFileToOpen.endsWith(".mod") ||
             selectedFileToOpen.endsWith(".pl")) {
         
     
     try {
            BufferedReader r = new BufferedReader(new FileReader(file));
            try {
               
                setUpArea();
                selectedFileToOpen= file.getName();
                if(selectedFileToOpen.endsWith(".m")) {
                         areaFileTab.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_OCTAVE);
                        
                        needOct(true);
                    }else if(selectedFileToOpen.endsWith(".dyn")) {
                         areaFileTab.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
                         areaFileTab.setCodeFoldingEnabled(true);
                        
                    }else if(selectedFileToOpen.endsWith(".mod")) {
                         areaFileTab.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
                         areaFileTab.setCodeFoldingEnabled(true);
                       
                    }else if(selectedFileToOpen.endsWith(".pl")) {
                         areaFileTab.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PERL);
                        
                        
                    }
                    else{
                         areaFileTab.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
                        
                    }
                areaFileTab.read(r, null);
                r.close();
                
                fileTab.addTab(file.getName(), scroll1);
               initTabComponent(FILE_TAB_INDEX);
                fileTab.setToolTipTextAt(FILE_TAB_INDEX, file.getAbsolutePath());
                
                fileTab.setSelectedIndex(FILE_TAB_INDEX);
                this.addFileNameToList(file.getAbsolutePath());
                 FILE_TAB_INDEX++;
                dirty();
                 openInFlotingWindow(this.fileTabView);
                 
            } catch (IOException ex) {
               
            }
        } catch (FileNotFoundException ex) {
           
        }
     }
        
    }
    
    public void openInFlotingWindow(View view) {
       if(!this.fileTabView.isShowing()) {
                      FloatingWindow fw = rootWindow.createFloatingWindow(new Point(50, 50),
                                                            new Dimension(600, 400),
                                                            view);
                    fw.getTopLevelAncestor().setVisible(true);
                  } 
    }
     public void open(){
        JFileChooser fc = new JFileChooser();
        
        if(fileTab.getTabCount() >0) {
              File f = new File(fileTab.getToolTipTextAt(fileTab.getSelectedIndex()));
               fc.setCurrentDirectory(f.getParentFile());  
          }
           
       FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "M-Files  (*.m)", "m");
        FileNameExtensionFilter filter_dyn = new FileNameExtensionFilter(
        "Dynare-Files  (*.mod; *.dyn)", "mod","dyn");
        fc.setAcceptAllFileFilterUsed(false);
        
         fc.setFileFilter(filter_dyn);
         fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(true);

        
        File file1[];
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
                   
                file1 = fc.getSelectedFiles();
                
                this.setCurrentDirFileTab(fc.getCurrentDirectory().getAbsolutePath());
                  for(int i=0;i<file1.length;i++) {
                            if(!fileNameList.contains(file1[i].getAbsolutePath())) {
                               open(file1[i],i);
                                                
                            }else {
                                System.out.println(file1[i].getAbsolutePath()+" already open!");
                            }
                    }  
   
        }
    }
     
     public static void  requestToChangeDir(String dir) {
         octavePanel.evalWithOutput("chdir "+"'"+dirComboBox.getSelectedItem().toString()+"'"); 
     }
      private void save() {
        if(fileTab.getSelectedIndex() >= 0) {
           String _file = fileTab.getToolTipTextAt(fileTab.getSelectedIndex());
           String fl =fileTab.getTitleAt(fileTab.getSelectedIndex());
        
         
            if(fl.endsWith("*")) {
                File f =new File(_file);
                save(f,fileTab.getSelectedIndex());
            } 
        }
                   
            
        
    }
    
    
    private void undo() {
        if(fileTab.getSelectedIndex() >= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.undoLastAction();
            
        }
    }
    private void redo() {
        if(fileTab.getSelectedIndex() >= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.redoLastAction();
            
        }
    }
    private void cut() {
        if(fileTab.getSelectedIndex() >= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.cut();
            
        }
    }
    private void copy() {
        if(fileTab.getSelectedIndex() >= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.copy();
            
        }
    }
    private void paste() {
        if(fileTab.getSelectedIndex() >= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.paste();
            
        }
    }
    private void selectAll() {
        if(fileTab.getSelectedIndex() >= 0) {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.selectAll();
            
        }
    }

    private void histPanel(){
        JPanel p = new JPanel(new BorderLayout());
        JToolBar b = new JToolBar("");
        b.setFloatable(false);
       b.setRollover(true);
        JButton saveButton = new JButton();
        JButton runButton = new JButton();
        
        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/script_save.png"))); 
        runButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/terminal.png"))); 
        b.add(saveButton);
        b.add(runButton);
        p.add(b,BorderLayout.PAGE_START);
        p.add(this.histScrollPane,BorderLayout.CENTER);
        
        saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                saveHistoryAs();
            }
            
        });
        
        runButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String path=System.getProperty("user.dir")+File.separator+"scripts"+File.separator;
                String os =System.getProperty("os.name").toLowerCase();
                boolean isWindows= (os.indexOf("win") >=0);

                saveHistory(new File(path+"dmns.m"));
                if(isWindows) {
                    createFile(path,".bat");
                    openscript(new File(path+"octave.bat"));
                }else{
                    createFile(path,"");
                    openscript(new File(path+"octave"));
                }
            }
            
        });
        historyView=new View("History", null, p);
    }
    public  String DynareOptions() {
        Preferences pr = Preferences.userNodeForPackage(this.getClass());
        String path =pr.get("DomainMath_DynareOptions",null);
        
        return path;
    }
    public String getStartupCmd() {
        return startupCmd;
    }
   public String getOctavePath() {
        return octavePath;
    }
   public String getCmdLineOptions() {
        return cmdLineOptions;
    }
    
    public  void browse(){
        JFileChooser fc = new JFileChooser();
        
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setMultiSelectionEnabled(false);
        
        File _file;
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
                 _file = fc.getSelectedFile();
                 setDir(_file.getAbsolutePath());

            } 
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        connectButton = new javax.swing.JButton();
        disconnectButton = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jSeparator15 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jSeparator13 = new javax.swing.JToolBar.Separator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dirComboBox = new javax.swing.JComboBox();
        folderUpButton = new javax.swing.JButton();
        browseButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newFileItem = new javax.swing.JMenuItem();
        openItem = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JPopupMenu.Separator();
        saveFileItem = new javax.swing.JMenuItem();
        saveAsItem = new javax.swing.JMenuItem();
        savePlotItem = new javax.swing.JMenuItem();
        saveAllItem = new javax.swing.JMenuItem();
        diaryMenu = new javax.swing.JMenu();
        diaryOnItem = new javax.swing.JRadioButtonMenuItem();
        diaryOffItem = new javax.swing.JRadioButtonMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        diarySaveItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        printFileItem = new javax.swing.JMenuItem();
        printItem = new javax.swing.JMenuItem();
        jSeparator19 = new javax.swing.JPopupMenu.Separator();
        preferencesItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        connectItem = new javax.swing.JMenuItem();
        disconnectItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        exitItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        undoItem = new javax.swing.JMenuItem();
        redoItem = new javax.swing.JMenuItem();
        jSeparator20 = new javax.swing.JPopupMenu.Separator();
        cutItem = new javax.swing.JMenuItem();
        copyItem = new javax.swing.JMenuItem();
        pasteItem = new javax.swing.JMenuItem();
        jSeparator21 = new javax.swing.JPopupMenu.Separator();
        deleteItem = new javax.swing.JMenuItem();
        selectAllItem = new javax.swing.JMenuItem();
        jSeparator22 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator23 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator24 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        clearOutWindowItem = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        consoleItem = new javax.swing.JMenuItem();
        historyItem = new javax.swing.JMenuItem();
        filesItem = new javax.swing.JMenuItem();
        workspaceItem = new javax.swing.JMenuItem();
        codeEditorItem = new javax.swing.JMenuItem();
        pathsItem = new javax.swing.JMenuItem();
        pkgsItem = new javax.swing.JMenuItem();
        arrayEditItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        findItem = new javax.swing.JMenuItem();
        replaceItem = new javax.swing.JMenuItem();
        gotoItem = new javax.swing.JMenuItem();
        jSeparator25 = new javax.swing.JPopupMenu.Separator();
        googleItem = new javax.swing.JMenuItem();
        wikiItem = new javax.swing.JMenuItem();
        pkgMenuItem = new javax.swing.JMenu();
        BioInfoMenu = new javax.swing.JMenu();
        sequenceViewerMenuItem = new javax.swing.JMenuItem();
        multipleSequenceViewerMenuItem = new javax.swing.JMenuItem();
        hmmerItem = new javax.swing.JMenuItem();
        dataBaseMenuItem = new javax.swing.JMenuItem();
        dSmoothItem = new javax.swing.JMenuItem();
        imageToolItem = new javax.swing.JMenuItem();
        multicoreItem = new javax.swing.JMenuItem();
        nNetMenuItem = new javax.swing.JMenuItem();
        phyConstItem = new javax.swing.JMenuItem();
        docPkgItem = new javax.swing.JMenuItem();
        debugMenu = new javax.swing.JMenu();
        runScriptItem = new javax.swing.JMenuItem();
        toggleBreakpointItem = new javax.swing.JMenuItem();
        removeToggleBreakpointItem = new javax.swing.JMenuItem();
        clearAllBreakpointsItem = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        stepItem = new javax.swing.JMenuItem();
        stepInItem = new javax.swing.JMenuItem();
        stepOutItem = new javax.swing.JMenuItem();
        continueItem = new javax.swing.JMenuItem();
        jSeparator26 = new javax.swing.JPopupMenu.Separator();
        stackItem = new javax.swing.JMenuItem();
        dbupItem = new javax.swing.JMenuItem();
        dbdownItem = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        finishDebugItem = new javax.swing.JMenuItem();
        WorkspaceMenu = new javax.swing.JMenu();
        newVariableItem = new javax.swing.JMenuItem();
        deleteVariableItem = new javax.swing.JMenuItem();
        clearAllItem = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        loadMenuItem = new javax.swing.JMenuItem();
        saveDataItem = new javax.swing.JMenuItem();
        saveAllDataItem = new javax.swing.JMenuItem();
        jSeparator28 = new javax.swing.JPopupMenu.Separator();
        importSpreadSheetDataItem = new javax.swing.JMenuItem();
        exportItem = new javax.swing.JMenuItem();
        exportAllItem = new javax.swing.JMenuItem();
        jSeparator27 = new javax.swing.JPopupMenu.Separator();
        refreshItem = new javax.swing.JMenuItem();
        automaticRefreshCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        toolsMenu = new javax.swing.JMenu();
        dleEditorItem = new javax.swing.JMenuItem();
        arrayEditorItem = new javax.swing.JMenuItem();
        fltkplotItem = new javax.swing.JMenuItem();
        octaveCmdItem = new javax.swing.JMenuItem();
        dynareItem = new javax.swing.JMenuItem();
        worksheetItem = new javax.swing.JMenuItem();
        optimItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        previousWindowItem = new javax.swing.JMenuItem();
        nextWindowItem = new javax.swing.JMenuItem();
        jSeparator29 = new javax.swing.JPopupMenu.Separator();
        closeItem = new javax.swing.JMenuItem();
        closeAllItem = new javax.swing.JMenuItem();
        jSeparator18 = new javax.swing.JPopupMenu.Separator();
        windowsItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        forumItem = new javax.swing.JMenuItem();
        octaveInfoItem = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        quickHelpItem = new javax.swing.JMenuItem();
        referenceMenu = new javax.swing.JMenu();
        referenceItem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        onlineHelpItem = new javax.swing.JMenuItem();
        howToItem = new javax.swing.JMenuItem();
        faqItem = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        suggestionsItem = new javax.swing.JMenuItem();
        reportBugItem = new javax.swing.JMenuItem();
        feedBackItem = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        octaveItem = new javax.swing.JMenuItem();
        AboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en"); // NOI18N
        setTitle(bundle.getString("DomainMath.title")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jToolBar1.setRollover(true);
        jToolBar1.setName("Standard"); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/size22x22/document-new.png"))); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/size22x22/document-open.png"))); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/size22x22/document-save-all.png"))); // NOI18N
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

        connectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/size22x22/connect.png"))); // NOI18N
        connectButton.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("connectItem.mnemonic").charAt(0));
        connectButton.setToolTipText(bundle.getString("connectItem.tooltip")); // NOI18N
        connectButton.setFocusable(false);
        connectButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        connectButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(connectButton);

        disconnectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/size22x22/disconnect.png"))); // NOI18N
        disconnectButton.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("disconnectItem.mnemonic").charAt(0));
        disconnectButton.setToolTipText(bundle.getString("disconnectItem.tooltip")); // NOI18N
        disconnectButton.setFocusable(false);
        disconnectButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        disconnectButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(disconnectButton);
        jToolBar1.add(jSeparator9);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/size22x22/edit-undo.png"))); // NOI18N
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/size22x22/edit-redo.png"))); // NOI18N
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);
        jToolBar1.add(jSeparator15);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/size22x22/edit-cut.png"))); // NOI18N
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/size22x22/edit-copy.png"))); // NOI18N
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton7);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/size22x22/edit-paste.png"))); // NOI18N
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton8);
        jToolBar1.add(jSeparator13);

        jLabel1.setText("Current Directory:");

        dirComboBox.setEditable(true);
        dirComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dirComboBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dirComboBox, 0, 112, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(dirComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jToolBar1.add(jPanel1);

        folderUpButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/size22x22/go-up.png"))); // NOI18N
        folderUpButton.setToolTipText("Up");
        folderUpButton.setFocusable(false);
        folderUpButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        folderUpButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        folderUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folderUpButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(folderUpButton);

        browseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/size22x22/document-open.png"))); // NOI18N
        browseButton.setToolTipText(bundle.getString("browseButton.title")); // NOI18N
        browseButton.setFocusable(false);
        browseButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        browseButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(browseButton);

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/size22x22/edit-add.png"))); // NOI18N
        addButton.setToolTipText(bundle.getString("addButton.title")); // NOI18N
        addButton.setFocusable(false);
        addButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(addButton);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        fileMenu.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("fileMenu.mnemonic").charAt(0));
        fileMenu.setText(bundle.getString("fileMenu.name")); // NOI18N

        newFileItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newFileItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-new.png"))); // NOI18N
        newFileItem.setText("New");
        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("org/domainmath/gui/code_editor_dle/resources/DLECodeEditor_en"); // NOI18N
        newFileItem.setToolTipText(bundle1.getString("newFileItem.tooltip")); // NOI18N
        newFileItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileItemActionPerformed(evt);
            }
        });
        fileMenu.add(newFileItem);

        openItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-open.png"))); // NOI18N
        openItem.setText("Open...");
        openItem.setToolTipText("Open File");
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openItemActionPerformed(evt);
            }
        });
        fileMenu.add(openItem);
        fileMenu.add(jSeparator17);

        saveFileItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveFileItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-save.png"))); // NOI18N
        saveFileItem.setText("Save ");
        saveFileItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveFileItem);

        saveAsItem.setText("Save As...");
        saveAsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsItem);

        savePlotItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("savePlotItem.mnemonic").charAt(0));
        savePlotItem.setText(bundle.getString("savePlotItem.name")); // NOI18N
        savePlotItem.setToolTipText(bundle.getString("savePlotItem.tooltip")); // NOI18N
        savePlotItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePlotItemActionPerformed(evt);
            }
        });
        fileMenu.add(savePlotItem);

        saveAllItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveAllItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-save-all.png"))); // NOI18N
        saveAllItem.setText(bundle1.getString("saveAllItem.name")); // NOI18N
        saveAllItem.setToolTipText(bundle1.getString("saveAllItem.tooltip")); // NOI18N
        saveAllItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAllItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAllItem);

        diaryMenu.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("diaryMenu.mnemonic").charAt(0));
        diaryMenu.setText(bundle.getString("diaryMenu.name")); // NOI18N

        buttonGroup1.add(diaryOnItem);
        diaryOnItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("diaryOnItem.mnemonic").charAt(0));
        diaryOnItem.setText(bundle.getString("diaryOnItem.name")); // NOI18N
        diaryOnItem.setToolTipText(bundle.getString("diaryOnItem.tooltip")); // NOI18N
        diaryOnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaryOnItemActionPerformed(evt);
            }
        });
        diaryMenu.add(diaryOnItem);

        buttonGroup1.add(diaryOffItem);
        diaryOffItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("diaryOffItem.mnemonic").charAt(0));
        diaryOffItem.setSelected(true);
        diaryOffItem.setText(bundle.getString("diaryOffItem.name")); // NOI18N
        diaryOffItem.setToolTipText(bundle.getString("diaryOffItem.tooltip")); // NOI18N
        diaryOffItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaryOffItemActionPerformed(evt);
            }
        });
        diaryMenu.add(diaryOffItem);
        diaryMenu.add(jSeparator16);

        diarySaveItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("diarySaveItem.mnemonic").charAt(0));
        diarySaveItem.setText(bundle.getString("diarySaveItem.name")); // NOI18N
        diarySaveItem.setToolTipText(bundle.getString("diarySaveItem.tooltip")); // NOI18N
        diarySaveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diarySaveItemActionPerformed(evt);
            }
        });
        diaryMenu.add(diarySaveItem);

        fileMenu.add(diaryMenu);
        fileMenu.add(jSeparator1);

        printFileItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printFileItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-print2.png"))); // NOI18N
        printFileItem.setText("Print");
        printFileItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printFileItemActionPerformed(evt);
            }
        });
        fileMenu.add(printFileItem);

        printItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        printItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("printItem.mnemonic").charAt(0));
        printItem.setText(bundle.getString("printItem.name")); // NOI18N
        printItem.setToolTipText(bundle.getString("printItem.tooltip")); // NOI18N
        printItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printItemActionPerformed(evt);
            }
        });
        fileMenu.add(printItem);
        fileMenu.add(jSeparator19);

        preferencesItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/preferences.png"))); // NOI18N
        preferencesItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("preferecesItem.mnemonic").charAt(0));
        preferencesItem.setText(bundle.getString("preferencesItem.name")); // NOI18N
        preferencesItem.setToolTipText(bundle.getString("preferencesItem.tooltip")); // NOI18N
        preferencesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preferencesItemActionPerformed(evt);
            }
        });
        fileMenu.add(preferencesItem);
        fileMenu.add(jSeparator2);

        connectItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/connect.png"))); // NOI18N
        connectItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("connectItem.mnemonic").charAt(0));
        connectItem.setText(bundle.getString("connectItem.name")); // NOI18N
        connectItem.setToolTipText(bundle.getString("connectItem.tooltip")); // NOI18N
        connectItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectItemActionPerformed(evt);
            }
        });
        fileMenu.add(connectItem);

        disconnectItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/disconnect.png"))); // NOI18N
        disconnectItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("disconnectItem.mnemonic").charAt(0));
        disconnectItem.setText(bundle.getString("disconnectItem.name")); // NOI18N
        disconnectItem.setToolTipText(bundle.getString("disconnectItem.tooltip")); // NOI18N
        disconnectItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectItemActionPerformed(evt);
            }
        });
        fileMenu.add(disconnectItem);
        fileMenu.add(jSeparator3);

        exitItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("exitItem.mnemonic").charAt(0));
        exitItem.setText(bundle.getString("exitItem.name")); // NOI18N
        exitItem.setToolTipText(bundle.getString("exitItem.tooltip")); // NOI18N
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitItem);

        jMenuBar1.add(fileMenu);

        editMenu.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("editMenu.mnemonic").charAt(0));
        editMenu.setText("Edit");

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
        editMenu.add(jSeparator20);

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
        editMenu.add(jSeparator21);

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
        editMenu.add(jSeparator22);

        jMenuItem2.setAction(new RSyntaxTextAreaEditorKit.InsertTabAction());
        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Increase Indent");
        editMenu.add(jMenuItem2);

        jMenuItem3.setAction(new RSyntaxTextAreaEditorKit.DecreaseIndentAction());
        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem3.setText("Decrease Indent");
        editMenu.add(jMenuItem3);
        editMenu.add(jSeparator23);

        jMenuItem4.setAction(new RTextAreaEditorKit.UpperSelectionCaseAction());
        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Make Uppercase");
        editMenu.add(jMenuItem4);

        jMenuItem5.setAction(new RTextAreaEditorKit.LowerSelectionCaseAction());
        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Make Lowercase");
        editMenu.add(jMenuItem5);
        editMenu.add(jSeparator24);

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
        editMenu.add(jSeparator11);

        clearOutWindowItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        clearOutWindowItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("clearOutputWindowItem.mnemonic").charAt(0));
        clearOutWindowItem.setText(bundle.getString("clearOutputWindowItem.name")); // NOI18N
        clearOutWindowItem.setToolTipText(bundle.getString("clearOutputWindowItem.tooltip")); // NOI18N
        clearOutWindowItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearOutWindowItemActionPerformed(evt);
            }
        });
        editMenu.add(clearOutWindowItem);

        jMenuBar1.add(editMenu);

        jMenu3.setText(bundle.getString("viewMenu.title")); // NOI18N

        consoleItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_0, java.awt.event.InputEvent.CTRL_MASK));
        consoleItem.setText(bundle.getString("consoleItem.title")); // NOI18N
        consoleItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consoleItemActionPerformed(evt);
            }
        });
        jMenu3.add(consoleItem);

        historyItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        historyItem.setText(bundle.getString("historyItem.title")); // NOI18N
        historyItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyItemActionPerformed(evt);
            }
        });
        jMenu3.add(historyItem);

        filesItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_MASK));
        filesItem.setText(bundle.getString("filesItem.title")); // NOI18N
        filesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesItemActionPerformed(evt);
            }
        });
        jMenu3.add(filesItem);

        workspaceItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_MASK));
        workspaceItem.setText(bundle.getString("worksaceItem.title")); // NOI18N
        workspaceItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                workspaceItemActionPerformed(evt);
            }
        });
        jMenu3.add(workspaceItem);

        codeEditorItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.CTRL_MASK));
        codeEditorItem.setText(bundle.getString("codeEditorItem.title")); // NOI18N
        codeEditorItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codeEditorItemActionPerformed(evt);
            }
        });
        jMenu3.add(codeEditorItem);

        pathsItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_5, java.awt.event.InputEvent.CTRL_MASK));
        pathsItem.setText(bundle.getString("setPathsItem.title")); // NOI18N
        pathsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathsItemActionPerformed(evt);
            }
        });
        jMenu3.add(pathsItem);

        pkgsItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_6, java.awt.event.InputEvent.CTRL_MASK));
        pkgsItem.setText(bundle.getString("pkgsItem.title")); // NOI18N
        pkgsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pkgsItemActionPerformed(evt);
            }
        });
        jMenu3.add(pkgsItem);

        arrayEditItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_7, java.awt.event.InputEvent.CTRL_MASK));
        arrayEditItem.setText(bundle.getString("arrayEidtorItem.title")); // NOI18N
        arrayEditItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arrayEditItemActionPerformed(evt);
            }
        });
        jMenu3.add(arrayEditItem);

        jMenuBar1.add(jMenu3);

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
        jMenu1.add(jSeparator25);

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

        pkgMenuItem.setText(bundle.getString("pkgMenu.name")); // NOI18N

        BioInfoMenu.setText("Bioinformatics");

        sequenceViewerMenuItem.setText("Sequence Viewer");
        sequenceViewerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sequenceViewerMenuItemActionPerformed(evt);
            }
        });
        BioInfoMenu.add(sequenceViewerMenuItem);

        multipleSequenceViewerMenuItem.setText("Jalview");
        multipleSequenceViewerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multipleSequenceViewerMenuItemActionPerformed(evt);
            }
        });
        BioInfoMenu.add(multipleSequenceViewerMenuItem);

        hmmerItem.setText("Hmmer Service");
        hmmerItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hmmerItemActionPerformed(evt);
            }
        });
        BioInfoMenu.add(hmmerItem);

        pkgMenuItem.add(BioInfoMenu);

        dataBaseMenuItem.setText("Database");
        dataBaseMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataBaseMenuItemActionPerformed(evt);
            }
        });
        pkgMenuItem.add(dataBaseMenuItem);

        dSmoothItem.setText("Data Smoothing");
        dSmoothItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dSmoothItemActionPerformed(evt);
            }
        });
        pkgMenuItem.add(dSmoothItem);

        java.util.ResourceBundle bundle2 = java.util.ResourceBundle.getBundle("org/domainmath/gui/packages/image/resources/image-tool_en"); // NOI18N
        imageToolItem.setText(bundle2.getString("imageToolFrame.title")); // NOI18N
        imageToolItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageToolItemActionPerformed(evt);
            }
        });
        pkgMenuItem.add(imageToolItem);

        multicoreItem.setText("Multicore");
        multicoreItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multicoreItemActionPerformed(evt);
            }
        });
        pkgMenuItem.add(multicoreItem);

        nNetMenuItem.setText("Nnet");
        nNetMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nNetMenuItemActionPerformed(evt);
            }
        });
        pkgMenuItem.add(nNetMenuItem);

        phyConstItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("phyConstItem.mnemonic").charAt(0));
        phyConstItem.setText(bundle.getString("phyConstItem.name")); // NOI18N
        phyConstItem.setToolTipText(bundle.getString("phyContItem.tooltip")); // NOI18N
        phyConstItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phyConstItemActionPerformed(evt);
            }
        });
        pkgMenuItem.add(phyConstItem);

        docPkgItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.CTRL_MASK));
        docPkgItem.setText("Package Documentation");
        docPkgItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                docPkgItemActionPerformed(evt);
            }
        });
        pkgMenuItem.add(docPkgItem);

        jMenuBar1.add(pkgMenuItem);

        debugMenu.setText("Debug");

        runScriptItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        runScriptItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/Run.png"))); // NOI18N
        runScriptItem.setText("Run Script");
        runScriptItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runScriptItemActionPerformed(evt);
            }
        });
        debugMenu.add(runScriptItem);

        toggleBreakpointItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        toggleBreakpointItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/resources/stop.png"))); // NOI18N
        toggleBreakpointItem.setText("Toggle Breakpoint");
        toggleBreakpointItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleBreakpointItemActionPerformed(evt);
            }
        });
        debugMenu.add(toggleBreakpointItem);

        removeToggleBreakpointItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, java.awt.event.InputEvent.SHIFT_MASK));
        removeToggleBreakpointItem.setText("Remove Toggle Breakpoint");
        removeToggleBreakpointItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeToggleBreakpointItemActionPerformed(evt);
            }
        });
        debugMenu.add(removeToggleBreakpointItem);

        clearAllBreakpointsItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        clearAllBreakpointsItem.setText("Clear All Breakpoints...");
        clearAllBreakpointsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllBreakpointsItemActionPerformed(evt);
            }
        });
        debugMenu.add(clearAllBreakpointsItem);
        debugMenu.add(jSeparator10);

        stepItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, java.awt.event.InputEvent.SHIFT_MASK));
        stepItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/step.png"))); // NOI18N
        stepItem.setText("Step");
        stepItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepItemActionPerformed(evt);
            }
        });
        debugMenu.add(stepItem);

        stepInItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        stepInItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/step-in.png"))); // NOI18N
        stepInItem.setText("Step in");
        stepInItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepInItemActionPerformed(evt);
            }
        });
        debugMenu.add(stepInItem);

        stepOutItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, java.awt.event.InputEvent.SHIFT_MASK));
        stepOutItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/step-out.png"))); // NOI18N
        stepOutItem.setText("Step out");
        stepOutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepOutItemActionPerformed(evt);
            }
        });
        debugMenu.add(stepOutItem);

        continueItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
        continueItem.setText("Continue");
        continueItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continueItemActionPerformed(evt);
            }
        });
        debugMenu.add(continueItem);
        debugMenu.add(jSeparator26);

        stackItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        stackItem.setText("Stack");
        stackItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stackItemActionPerformed(evt);
            }
        });
        debugMenu.add(stackItem);

        dbupItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, java.awt.event.InputEvent.SHIFT_MASK));
        dbupItem.setText("Move up");
        dbupItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dbupItemActionPerformed(evt);
            }
        });
        debugMenu.add(dbupItem);

        dbdownItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        dbdownItem.setText("Move down");
        dbdownItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dbdownItemActionPerformed(evt);
            }
        });
        debugMenu.add(dbdownItem);
        debugMenu.add(jSeparator5);

        finishDebugItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, java.awt.event.InputEvent.SHIFT_MASK));
        finishDebugItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/finish.png"))); // NOI18N
        finishDebugItem.setText("Finish Debugger Session");
        finishDebugItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishDebugItemActionPerformed(evt);
            }
        });
        debugMenu.add(finishDebugItem);

        jMenuBar1.add(debugMenu);

        WorkspaceMenu.setText(bundle.getString("workspaceMenu.text")); // NOI18N

        newVariableItem.setText(bundle.getString("newVariableItem.text")); // NOI18N
        newVariableItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newVariableItemActionPerformed(evt);
            }
        });
        WorkspaceMenu.add(newVariableItem);

        deleteVariableItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        deleteVariableItem.setText("Delete Variable");
        deleteVariableItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteVariableItemActionPerformed(evt);
            }
        });
        WorkspaceMenu.add(deleteVariableItem);

        clearAllItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, java.awt.event.InputEvent.CTRL_MASK));
        clearAllItem.setText("Clear All...");
        clearAllItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllItemActionPerformed(evt);
            }
        });
        WorkspaceMenu.add(clearAllItem);
        WorkspaceMenu.add(jSeparator8);

        loadMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        loadMenuItem.setText("Load");
        loadMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMenuItemActionPerformed(evt);
            }
        });
        WorkspaceMenu.add(loadMenuItem);

        saveDataItem.setText("Save");
        saveDataItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveDataItemActionPerformed(evt);
            }
        });
        WorkspaceMenu.add(saveDataItem);

        saveAllDataItem.setText("Save All...");
        saveAllDataItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAllDataItemActionPerformed(evt);
            }
        });
        WorkspaceMenu.add(saveAllDataItem);
        WorkspaceMenu.add(jSeparator28);

        importSpreadSheetDataItem.setText("Import Spreadsheet Data");
        importSpreadSheetDataItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importSpreadSheetDataItemActionPerformed(evt);
            }
        });
        WorkspaceMenu.add(importSpreadSheetDataItem);

        exportItem.setText("Export");
        exportItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportItemActionPerformed(evt);
            }
        });
        WorkspaceMenu.add(exportItem);

        exportAllItem.setText("Export All...");
        exportAllItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportAllItemActionPerformed(evt);
            }
        });
        WorkspaceMenu.add(exportAllItem);
        WorkspaceMenu.add(jSeparator27);

        refreshItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        refreshItem.setText("Refresh");
        refreshItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshItemActionPerformed(evt);
            }
        });
        WorkspaceMenu.add(refreshItem);

        automaticRefreshCheckBoxMenuItem.setSelected(true);
        automaticRefreshCheckBoxMenuItem.setText("Automatic Refresh");
        WorkspaceMenu.add(automaticRefreshCheckBoxMenuItem);

        jMenuBar1.add(WorkspaceMenu);

        toolsMenu.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("ToolsMenu.mnemonic").charAt(0));
        toolsMenu.setText(bundle.getString("ToolsMenu.name")); // NOI18N

        dleEditorItem.setText("DLE Code Editor");
        dleEditorItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dleEditorItemActionPerformed(evt);
            }
        });
        toolsMenu.add(dleEditorItem);

        arrayEditorItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("arrayEditor.mnemonic").charAt(0));
        arrayEditorItem.setText(bundle.getString("arrayEditor.name")); // NOI18N
        arrayEditorItem.setToolTipText(bundle.getString("arrayEditor.tooltip")); // NOI18N
        arrayEditorItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arrayEditorItemActionPerformed(evt);
            }
        });
        toolsMenu.add(arrayEditorItem);

        fltkplotItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fltkplotItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("fltkPlotItem.mnemonic").charAt(0));
        fltkplotItem.setText(bundle.getString("fltkPlotItem.name")); // NOI18N
        fltkplotItem.setToolTipText(bundle.getString("fltkPlotItem.tooltip")); // NOI18N
        fltkplotItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fltkplotItemActionPerformed(evt);
            }
        });
        toolsMenu.add(fltkplotItem);

        octaveCmdItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("octaveCmdItem.mnemonic").charAt(0));
        octaveCmdItem.setText(bundle.getString("octaveCmdItem.name")); // NOI18N
        octaveCmdItem.setToolTipText(bundle.getString("octaveCmdItem.tooltip")); // NOI18N
        octaveCmdItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                octaveCmdItemActionPerformed(evt);
            }
        });
        toolsMenu.add(octaveCmdItem);

        dynareItem.setText("Dynare");
        dynareItem.setToolTipText("Dynare");
        dynareItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dynareItemActionPerformed(evt);
            }
        });
        toolsMenu.add(dynareItem);

        worksheetItem.setText("Worksheet");
        worksheetItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                worksheetItemActionPerformed(evt);
            }
        });
        toolsMenu.add(worksheetItem);

        optimItem.setText("Optimization Tool");
        optimItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optimItemActionPerformed(evt);
            }
        });
        toolsMenu.add(optimItem);

        jMenuBar1.add(toolsMenu);

        jMenu2.setText(bundle.getString("windowMenu.title")); // NOI18N

        previousWindowItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_DOWN, java.awt.event.InputEvent.CTRL_MASK));
        previousWindowItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/go-previous.png"))); // NOI18N
        previousWindowItem.setText(bundle.getString("previousWindowItem.title")); // NOI18N
        previousWindowItem.setToolTipText(bundle.getString("previousWindowItem.tooltip")); // NOI18N
        previousWindowItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousWindowItemActionPerformed(evt);
            }
        });
        jMenu2.add(previousWindowItem);

        nextWindowItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_UP, java.awt.event.InputEvent.CTRL_MASK));
        nextWindowItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/go-next.png"))); // NOI18N
        nextWindowItem.setText(bundle.getString("nextWindowItem.title")); // NOI18N
        nextWindowItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextWindowItemActionPerformed(evt);
            }
        });
        jMenu2.add(nextWindowItem);
        jMenu2.add(jSeparator29);

        closeItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.ALT_MASK));
        closeItem.setText(bundle1.getString("closeItem.name")); // NOI18N
        closeItem.setToolTipText(bundle1.getString("closeItem.tooltip")); // NOI18N
        closeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeItemActionPerformed(evt);
            }
        });
        jMenu2.add(closeItem);

        closeAllItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        closeAllItem.setText(bundle1.getString("closeAllItem.name")); // NOI18N
        closeAllItem.setToolTipText(bundle1.getString("closeAllItem.tooltip")); // NOI18N
        closeAllItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeAllItemActionPerformed(evt);
            }
        });
        jMenu2.add(closeAllItem);
        jMenu2.add(jSeparator18);

        windowsItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.SHIFT_MASK));
        windowsItem.setText(bundle.getString("windowsItem.title")); // NOI18N
        windowsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                windowsItemActionPerformed(evt);
            }
        });
        jMenu2.add(windowsItem);

        jMenuBar1.add(jMenu2);

        helpMenu.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("helpMenu.mnemonic").charAt(0));
        helpMenu.setText(bundle.getString("helpMenu.name")); // NOI18N

        forumItem.setText("Forum");
        forumItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forumItemActionPerformed(evt);
            }
        });
        helpMenu.add(forumItem);

        octaveInfoItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("octInfoItem.mnemonic").charAt(0));
        octaveInfoItem.setText(bundle.getString("octInfoItem.name")); // NOI18N
        octaveInfoItem.setToolTipText(bundle.getString("octInfoItem.tooltip")); // NOI18N
        octaveInfoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                octaveInfoItemActionPerformed(evt);
            }
        });
        helpMenu.add(octaveInfoItem);
        helpMenu.add(jSeparator12);

        quickHelpItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        quickHelpItem.setText(bundle.getString("quickHelpItem.name")); // NOI18N
        quickHelpItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quickHelpItemActionPerformed(evt);
            }
        });
        helpMenu.add(quickHelpItem);

        referenceMenu.setText(bundle.getString("referencesMenu.name")); // NOI18N
        helpMenu.add(referenceMenu);

        referenceItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        referenceItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("addReferencesItem.mnemonic").charAt(0));
        referenceItem.setText(bundle.getString("addReferencesItem.name")); // NOI18N
        referenceItem.setToolTipText(bundle.getString("addReferencesItem.tooltip")); // NOI18N
        referenceItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                referenceItemActionPerformed(evt);
            }
        });
        helpMenu.add(referenceItem);
        helpMenu.add(jSeparator6);

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

        reportBugItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("reportBugItem.mnemonic").charAt(0));
        reportBugItem.setText(bundle.getString("reportBugItem.name")); // NOI18N
        reportBugItem.setToolTipText(bundle.getString("reportBugItem.tooltip")); // NOI18N
        reportBugItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportBugItemActionPerformed(evt);
            }
        });
        helpMenu.add(reportBugItem);

        feedBackItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("yourFeedbackItem.mnemonic").charAt(0));
        feedBackItem.setText(bundle.getString("yourFeedbackItem.name")); // NOI18N
        feedBackItem.setToolTipText(bundle.getString("yourFeedbackItem.tooltip")); // NOI18N
        feedBackItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedBackItemActionPerformed(evt);
            }
        });
        helpMenu.add(feedBackItem);
        helpMenu.add(jSeparator7);

        octaveItem.setText("GNU Octave");
        octaveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                octaveItemActionPerformed(evt);
            }
        });
        helpMenu.add(octaveItem);

        AboutItem.setMnemonic(java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMath_en").getString("aboutItem.mnemonic").charAt(0));
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

        pack();
    }// </editor-fold>//GEN-END:initComponents
  
    /**
     * Set Current directory of Octave.
     * @param dir 
     */
    public static void setDir(String dir) {
        MainFrame.dirComboBox.insertItemAt(dir,dirComboBox.getItemCount());
        MainFrame.dirComboBox.setSelectedItem(dir);
    }
  
    /**
     * Creates Reference menus
     */
  public void makeMenu() {
        try {
            Preferences pr = Preferences.userNodeForPackage(this.getClass());
            StringTokenizer  t = new StringTokenizer(pr.get("Ref_list", null),"=;");
            
            while(t.hasMoreTokens()) {
                referenceMenu.add(new DocumentAction(t.nextToken(),t.nextToken()));
            }
        } catch (Exception ex) {
        }
  }
private void AboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutItemActionPerformed
        AboutDlg aboutDlg = new AboutDlg(this,true);
        aboutDlg.setLocationRelativeTo(this);
        aboutDlg.setVisible(true);
}//GEN-LAST:event_AboutItemActionPerformed
public static void setPath(String path) {
    try {
            URI uri = new URI(path);
            Desktop desktop=Desktop.getDesktop();
            desktop.browse(uri);
        } catch (URISyntaxException | IOException ex) {
        }
}
private void reportBugItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportBugItemActionPerformed
        setPath("http://domainmathide.freeforums.org/bugs-f3.html");
}//GEN-LAST:event_reportBugItemActionPerformed

private void feedBackItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feedBackItemActionPerformed
        setPath("http://domainmathide.freeforums.org/feedback-f4.html");
}//GEN-LAST:event_feedBackItemActionPerformed

private void referenceItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_referenceItemActionPerformed
        DocDialog DocDlg = new DocDialog(this,true);
        DocDlg.setLocationRelativeTo(this);
        DocDlg.setVisible(true);
}//GEN-LAST:event_referenceItemActionPerformed

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        MainFrame.octavePanel.start();
        
           DateFormat formatter = 
    DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                                   DateFormat.MEDIUM,
                                   Locale.getDefault());
        
    
        String t="# -- "+formatter.format(new Date())+" -- #";
       
        histArea.append(t+"\n");
        
        Preferences pr = Preferences.userNodeForPackage(this.getClass());
        String path =pr.get("DomainMath_DynarePath",null);
        if(path != null ) {
            String cmdAddPath = "addpath("+"'"+path+"');";
            String savePath = "savepath();";
              MainFrame.octavePanel.evaluate(cmdAddPath);
              MainFrame.octavePanel.evaluate(savePath);
        }
        
         MainFrame.octavePanel.evaluate("DomainMath_OctavePaths('"+parent_root+"DomainMath_OctavePaths.dat');");
                         
        
         MainFrame.octavePanel.evaluate("DomainMath_OctavePackages('"+parent_root+"DomainMath_OctavePackages.dat');");
         MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+parent_root+"DomainMath_OctaveVariables.dat',whos);");
        // MainFrame.workspace.reload();
        
       MainFrame.octavePanel.evaluate("javaaddpath('"+System.getProperty("user.dir")+File.separator+"scripts"+File.separator+"symja.jar')");
       MainFrame.octavePanel.evaluate("javaaddpath('"+System.getProperty("user.dir")+File.separator+"scripts"+File.separator+"DefaultApp.jar')");
       if(startupDir == null) {
           setDir(System.getProperty("user.dir"));
           octavePanel.evaluate("chdir "+"'"+System.getProperty("user.dir") +"'"); 
       }else{
           setDir(startupDir);
        octavePanel.evaluate("chdir "+"'"+startupDir +"'"); 
       }
       
        
}//GEN-LAST:event_formWindowOpened

public void deleteText() {
      
        RSyntaxTextArea textArea = commandArea;
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

			if (beep) {
                            UIManager.getLookAndFeel().provideErrorFeedback(textArea);
                        }

			textArea.requestFocusInWindow();
}
private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
           int j=fileTab.getTabCount()-1;
                while(j != -1) {
                   
                    askSave(j);
                    j--;
                }
      int option =  JOptionPane.showConfirmDialog(this, "Really do you want to exit?", "DomainMath IDE", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                 if(option == JOptionPane.YES_OPTION) {
                      octavePanel.quit();
                        File f = new File(System.getProperty("user.dir")+File.separator+"scripts"+File.separator+"dmns.m");
                        f.deleteOnExit();
                        File dir_content[];
                        try {
                            dir_content =logDir.listFiles();
                            for(int i=0; i<dir_content.length;i++) {
                                Files.delete(dir_content[i].toPath());
                            }
                            Files.delete(logDir.toPath());
                        } catch (IOException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                     this.dispose();
                     System.exit(0);
                 }
     
}//GEN-LAST:event_exitItemActionPerformed

 
private void printItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printItemActionPerformed
        try {
            octavePanel.outputArea.print();
        } catch (PrinterException ex) {
            
        }
}//GEN-LAST:event_printItemActionPerformed

private void preferencesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preferencesItemActionPerformed
        
        preferencesDlg.setLocationRelativeTo(this);
        preferencesDlg.setVisible(true);
}//GEN-LAST:event_preferencesItemActionPerformed

public void saveHistory(File file) {
     try {
            try (BufferedWriter r = new BufferedWriter(new FileWriter(file))) {
                    DateFormat formatter = 
                    DateFormat.getDateTimeInstance(DateFormat.LONG,
                                   DateFormat.LONG,
                                   Locale.getDefault());
                r.append("# Created by DomainMath IDE on "+formatter.format(new Date()));
                r.newLine();
                histArea.write(r);
                r.close();
            }
                       
		} catch (Exception re) {
		JOptionPane.showMessageDialog(this,re.toString(),"Error",JOptionPane.ERROR_MESSAGE);
                
		}
}

public void saveHistoryAs() {
    JFileChooser fc = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "M-Files  (*.m)", "m");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);

        fc.setDialogTitle("Save As");
        File file_save;
        int returnVal = fc.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                if(!path.endsWith(".m")) {
                    file_save = new File(fc.getSelectedFile().getAbsolutePath()+".m");
                    saveHistory(file_save);
                }else {
                     file_save = new File(fc.getSelectedFile().getAbsolutePath()+".m");
                     saveHistory(file_save);
                }
                 
        }
}
public void saveAs1() {
    JFileChooser fc = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "M-Files  (*.m)", "m");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);

        fc.setDialogTitle("Save As");
        File file_save;
        int returnVal = fc.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                if(!path.endsWith(".m")) {
                    file_save = new File(fc.getSelectedFile().getAbsolutePath()+".m");
                    save(file_save,fileTab.getSelectedIndex());
                }else {
                     file_save = new File(fc.getSelectedFile().getAbsolutePath()+".m");
                     save(file_save,fileTab.getSelectedIndex());
                }
                 
        }
                 

            
}

   public void connect() {
    commandArea.setText("");
        octavePanel.outputArea.setText("");
        octavePanel.start();
         JOptionPane.showMessageDialog(this,"Octave connected.","DomainMath IDE",JOptionPane.INFORMATION_MESSAGE);
                  
        if(!commandArea.isEnabled()) {
            commandArea.setEnabled(true);
            
        }
}
public void disconnect() {
    octavePanel.quit();
        commandArea.setEnabled(false);
       JOptionPane.showMessageDialog(this,"Octave disconnected.","DomainMath IDE",JOptionPane.INFORMATION_MESSAGE);
                
}
private void connectItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectItemActionPerformed
        connect();
         Preferences pr = Preferences.userNodeForPackage(this.getClass());
        String path =pr.get("DomainMath_DynarePath",null);
        if(path != null ) {
            String cmdAddPath = "addpath("+"'"+path+"');";
            String savePath = "savepath();";
              MainFrame.octavePanel.evaluate(cmdAddPath);
              MainFrame.octavePanel.evaluate(savePath);
        }
        
         MainFrame.octavePanel.evaluate("DomainMath_OctavePaths('"+parent_root+"DomainMath_OctavePaths.dat');");
                         
         
         MainFrame.octavePanel.evaluate("DomainMath_OctavePackages('"+parent_root+"DomainMath_OctavePackages.dat');");
         MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+parent_root+"DomainMath_OctaveVariables.dat',whos);");
         MainFrame.workspace.reload();
         MainFrame.workspace.reload(); 
         if(this.startupDir.equals("")) {
           setDir(System.getProperty("user.dir"));
           octavePanel.evaluate("chdir "+"'"+System.getProperty("user.dir") +"'"); 
       }else{
           setDir(startupDir);
        octavePanel.evaluate("chdir "+"'"+startupDir +"'"); 
       }
  
}//GEN-LAST:event_connectItemActionPerformed

private void disconnectItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectItemActionPerformed
        disconnect();
}//GEN-LAST:event_disconnectItemActionPerformed

private void clearOutWindowItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearOutWindowItemActionPerformed
    octavePanel.clear();
}//GEN-LAST:event_clearOutWindowItemActionPerformed

private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
    connect();
}//GEN-LAST:event_connectButtonActionPerformed

private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectButtonActionPerformed
        disconnect();
}//GEN-LAST:event_disconnectButtonActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int j=fileTab.getTabCount()-1;
                while(j != -1) {
                   
                    askSave(j);
                    j--;
                }
      int option =  JOptionPane.showConfirmDialog(this, "Really do you want to exit?", "DomainMath IDE", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                 if(option == JOptionPane.YES_OPTION) {
                      octavePanel.quit();
                        File f = new File(System.getProperty("user.dir")+File.separator+"scripts"+File.separator+"dmns.m");
                        f.deleteOnExit();
                        File dir_content[];
                        try {
                            dir_content =logDir.listFiles();
                            for(int i=0; i<dir_content.length;i++) {
                                Files.delete(dir_content[i].toPath());
                            }
                            Files.delete(logDir.toPath());
                        } catch (IOException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     this.dispose();
                     System.exit(0);
                 }
               
}//GEN-LAST:event_formWindowClosing

private void createFile(String path,String ext)  {
    
    String name = path+"octave"+ext;
    String cmd  =MainFrame.octavePath+" --persist "+Character.toString('"')+path+"dmns.m"+Character.toString('"');   
    try {
            try (FileWriter w = new FileWriter(new File(name))) {
                w.write(cmd, 0, cmd.length());
                w.flush();
            }
        } catch (IOException ex) {
        }
        
    
}
private void octaveCmdItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_octaveCmdItemActionPerformed
   
    this.openscript(new File(MainFrame.octavePath));
    
}//GEN-LAST:event_octaveCmdItemActionPerformed
private void openscript(File file) {
     
            
        try {
            Desktop desktop=Desktop.getDesktop();
      
            desktop.open(file);
              
        } catch (Exception ioe) {
            //ioe.printStackTrace();
            JOptionPane.showMessageDialog(null, file.getAbsolutePath() + " doesn't exist");
        }
}
private void fltkplotItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fltkplotItemActionPerformed
    String path=System.getProperty("user.dir")+File.separator+"scripts"+File.separator;
    String os =System.getProperty("os.name").toLowerCase();
    boolean isWindows= (os.indexOf("win") >=0);
    
    saveHistory(new File(path+"dmns.m"));
    if(isWindows) {
        createFile(path,".bat");
        openscript(new File(path+"octave.bat"));
    }else{
        createFile(path,"");
        openscript(new File(path+"octave"));
    }
}//GEN-LAST:event_fltkplotItemActionPerformed

private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
    JFileChooser fc = new JFileChooser();
        
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setMultiSelectionEnabled(false);
        
        Path browse_file;
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
                 browse_file = fc.getSelectedFile().toPath();
                 setDir(browse_file.toString());
               requestToChangeDir(browse_file.toString()); 
  

            } 
  
}//GEN-LAST:event_browseButtonActionPerformed

private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
    String dir=MainFrame.dirComboBox.getSelectedItem().toString();
    int i = 
                        JOptionPane.showConfirmDialog(this, 
"Add this folder to Path List?-\n"+dir, "DomainMath IDE", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);

                if(i == JOptionPane.YES_OPTION) {
                      
                        MainFrame.octavePanel.evaluate("addpath(genpath("+"'"+dir+"'));");
                        MainFrame.octavePanel.evaluate("savepath();");
                    }
}//GEN-LAST:event_addButtonActionPerformed

    private void phyConstItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phyConstItemActionPerformed
        MainFrame.octavePanel.evaluate("javaaddpath('"+System.getProperty("user.dir")+File.separator+"scripts"+File.separator+"Grid.jar')");
         MainFrame.octavePanel.evaluate("DomainMath_OctavePhyConst");     
    }//GEN-LAST:event_phyConstItemActionPerformed

    private void dynareItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dynareItemActionPerformed
        DynareDlg dynare = new DynareDlg(this,true);
        dynare.setLocationRelativeTo(this);
        dynare.setVisible(true);
        
        
    }//GEN-LAST:event_dynareItemActionPerformed

    private void octaveInfoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_octaveInfoItemActionPerformed
    MainFrame.octavePanel.evaluate("javaaddpath('"+System.getProperty("user.dir")+File.separator+"scripts"+File.separator+"Grid.jar')");
     
             MainFrame.octavePanel.evaluate("DomainMath_OctaveConfig");
        
    }//GEN-LAST:event_octaveInfoItemActionPerformed

    private void arrayEditorItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arrayEditorItemActionPerformed
        ArrayEditorFrame af = new ArrayEditorFrame();
        af.setVisible(true);
         
    }//GEN-LAST:event_arrayEditorItemActionPerformed
public void saveplot() {
        JFileChooser fc = new JFileChooser();
       
        fc.setFileFilter(DomainMathFileFilter.SAVE_PLOT_FILE_FILTER);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        
        File file_plot;
        String name;
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             System.err.println(fc.getFileFilter().getDescription());
                 file_plot = fc.getSelectedFile();
                 name =file_plot.getName();
                 MainFrame.octavePanel.evaluate("saveas(1,"+"'"+file_plot.getAbsolutePath()+"');");
                 
                 // open saved image using default image viewer.
                 MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file_plot.getAbsolutePath()+Character.toString('"')+");");
                 
            }
    }
    private void savePlotItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePlotItemActionPerformed
       saveplot();
    }//GEN-LAST:event_savePlotItemActionPerformed

    private void dleEditorItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dleEditorItemActionPerformed
        DLECodeEditorFrame fr = new DLECodeEditorFrame();
       
        fr.setLocationRelativeTo(this);
        fr.setVisible(true);
    }//GEN-LAST:event_dleEditorItemActionPerformed

    private void forumItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forumItemActionPerformed
        setPath("http://domainmathide.freeforums.org/");
    }//GEN-LAST:event_forumItemActionPerformed

    private void faqItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faqItemActionPerformed
        setPath("http://domainmathide.freeforums.org/faq-f8.html");
    }//GEN-LAST:event_faqItemActionPerformed

    private void suggestionsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestionsItemActionPerformed
        setPath("http://domainmathide.freeforums.org/suggestions-f6.html");
    }//GEN-LAST:event_suggestionsItemActionPerformed

    private void onlineHelpItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlineHelpItemActionPerformed
        setPath("http://domainmathide.freeforums.org/help-and-support-f5.html");
    }//GEN-LAST:event_onlineHelpItemActionPerformed

    private void howToItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_howToItemActionPerformed
        setPath("http://domainmathide.freeforums.org/how-to-f9.html");
    }//GEN-LAST:event_howToItemActionPerformed
     private int getFirstCharacter(Element row) {
		if (row == null) {
                     return 0;
                 }
		int lastColumnInRow = row.getEndOffset();
		return lastColumnInRow;
	}
    private void diaryOnItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaryOnItemActionPerformed
        octavePanel.evaluate("diary on");
       
    }//GEN-LAST:event_diaryOnItemActionPerformed

    private void diaryOffItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaryOffItemActionPerformed
        octavePanel.evaluate("diary off");
        
    }//GEN-LAST:event_diaryOffItemActionPerformed

    private void diarySaveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diarySaveItemActionPerformed
         JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        
        File file_diary;
        String name;
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             System.err.println(fc.getFileFilter().getDescription());
                 file_diary = fc.getSelectedFile();
                 name =file_diary.getName();
                 MainFrame.octavePanel.evaluate("diary "+"'"+file_diary.getAbsolutePath()+"'");
                 
            }
    }//GEN-LAST:event_diarySaveItemActionPerformed

    private void docPkgItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_docPkgItemActionPerformed
        setPath("http://octave.sourceforge.net/packages.php");
        
      
    }//GEN-LAST:event_docPkgItemActionPerformed

    private void octaveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_octaveItemActionPerformed
        octavePanel.evaluate("run('"+System.getProperty("user.dir")+File.separator+"scripts"+File.separator+"DomainMath_Octave.m"+"');");
    }//GEN-LAST:event_octaveItemActionPerformed

    private void quickHelpItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quickHelpItemActionPerformed
         String jar_path = "'"+System.getProperty("user.dir")+File.separator+"QuickHelp.jar'";
         String help_text = "help('"+octavePanel.outputArea.getSelectedText()+"')";
         String sel =octavePanel.outputArea.getSelectedText();
         if(sel ==null) {
             String s = JOptionPane.showInputDialog("Enter a keyword to search\nExample hilb");
             MainFrame.octavePanel.evaluate("DomainMath_QuickHelp(help('"+s+"'),"+jar_path+","+"'QuickHelpFrame');");
         }else{
             MainFrame.octavePanel.evaluate("DomainMath_QuickHelp("+help_text+","+jar_path+","+"'QuickHelpFrame');");
         }
        
    }//GEN-LAST:event_quickHelpItemActionPerformed

    private void dSmoothItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dSmoothItemActionPerformed
        MainFrame.octavePanel.eval("pkg load data-smoothing;");
        DataSmoothFrame dataSmoothFrame = new DataSmoothFrame();
        dataSmoothFrame.setLocationRelativeTo(this);
        dataSmoothFrame.setVisible(true);
    }//GEN-LAST:event_dSmoothItemActionPerformed

    private void worksheetItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_worksheetItemActionPerformed
        WorksheetFrame actFrame = new WorksheetFrame();
        actFrame.setLocationRelativeTo(this);
        actFrame.setVisible(true);
    }//GEN-LAST:event_worksheetItemActionPerformed

    private void optimItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optimItemActionPerformed
       OptimizationFrame optimFrame = new OptimizationFrame();
        optimFrame.setLocationRelativeTo(this);
        optimFrame.setVisible(true);
    }//GEN-LAST:event_optimItemActionPerformed

    private void folderUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folderUpButtonActionPerformed
        try{
            File f = new File(MainFrame.dirComboBox.getSelectedItem().toString());
        
        setDir(f.getParent().toString());
        requestToChangeDir(f.getParent().toString()); 
        
        }catch(Exception e) {
            System.out.println("No Parent");
        }
    }//GEN-LAST:event_folderUpButtonActionPerformed

    private void dataBaseMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataBaseMenuItemActionPerformed
        DataBaseFrame dbFrame = new DataBaseFrame();
        dbFrame.setLocationRelativeTo(this);
        dbFrame.setVisible(true);
    }//GEN-LAST:event_dataBaseMenuItemActionPerformed

    private void newFileItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFileItemActionPerformed
        newFile();
    }//GEN-LAST:event_newFileItemActionPerformed

    private void openItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openItemActionPerformed
        open();
    }//GEN-LAST:event_openItemActionPerformed

    private void saveFileItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFileItemActionPerformed

        save();
    }//GEN-LAST:event_saveFileItemActionPerformed

    private void saveAllItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAllItemActionPerformed
        for(int i=0;i<fileTab.getTabCount();i++) {

            String _file = fileTab.getToolTipTextAt(i);
            String fl = fileTab.getTitleAt(i);

            if(fl.endsWith("*")) {
                File f =new File(_file);
                save(f,i);
            }

        }
    }//GEN-LAST:event_saveAllItemActionPerformed

    private void closeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeItemActionPerformed
        if(fileTab.getSelectedIndex() >= 0) {
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

    private void deleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteItemActionPerformed
        this.deleteText();
    }//GEN-LAST:event_deleteItemActionPerformed

    private void selectAllItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllItemActionPerformed
        this.selectAll();
    }//GEN-LAST:event_selectAllItemActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        if(fileTab.getSelectedIndex() >= 0) {
            RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.clearMarkAllHighlights();
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void findItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findItemActionPerformed
        if(fileTab.getSelectedIndex() >= 0) {
            RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            FindAndReplaceDialog find = new FindAndReplaceDialog(this,false,fileTab,selectedArea.getSelectedText());
            find.setVisible(true);

        }
    }//GEN-LAST:event_findItemActionPerformed

    private void replaceItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceItemActionPerformed
        if(fileTab.getSelectedIndex() >= 0) {
            RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            FindAndReplaceDialog find = new FindAndReplaceDialog(this,false,fileTab,selectedArea.getSelectedText());
            find.setVisible(true);

        }
    }//GEN-LAST:event_replaceItemActionPerformed

    private void gotoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gotoItemActionPerformed
        if(fileTab.getSelectedIndex() >= 0) {
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

    private void googleItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_googleItemActionPerformed
        if(fileTab.getSelectedIndex() >= 0) {
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
        if(fileTab.getSelectedIndex() >= 0) {
            RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
            RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            String s = selectedArea.getSelectedText();
            if(!s.equals("")) {
                String f="http://en.wikipedia.org/wiki/Special:Search?search="+s.replaceAll(" ", "+");
                setPath(f);
            }

        }
    }//GEN-LAST:event_wikiItemActionPerformed

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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        for(int i=0;i<fileTab.getTabCount();i++) {

            String _file = fileTab.getToolTipTextAt(i);
            String fl = fileTab.getTitleAt(i);

            if(fl.endsWith("*")) {
                File f =new File(_file);
                save(f,i);
            }

        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        open();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        newFile();
        
    }//GEN-LAST:event_jButton1ActionPerformed

     private void setUpDynare(Path path) {
              if(dynareOptions!=null || dynarePath != null) {
                    File f = new File(dynarePath+File.separator+"dynare_m.exe");
                    String c = "system('"+f.getAbsolutePath()+" "+path.toString()+" "+dynareOptions+"');";
                    System.out.println(c);
                     MainFrame.octavePanel.evaluate(c);
                     MainFrame.octavePanel.evaluate("disp('---------------------------------------------------')");
                     String p = path.getFileName().toString();
                     String dyn_file =p.substring(0, p.indexOf("."))+".m" ;
                     MainFrame.octavePanel.evaluate("run("+"'"+path.getParent().resolve(Paths.get(dyn_file)).toString() +"'"+");");
              
                     MainFrame.octavePanel.evaluate("whos");
                     reloadWorkspace();
         
                }else if( dynarePath != null){
                     File f = new File(dynarePath+File.separator+"dynare_m.exe");
                    String c2="system('"+f.getAbsolutePath()+" "+path.toString()+" "+"noclearall"+"');";
                    System.out.println(c2);
                    MainFrame.octavePanel.evaluate(c2);
                    MainFrame.octavePanel.evaluate("disp('---------------------------------------------------')");
                    String p = path.getFileName().toString();
                     String mscript =p.substring(0, p.indexOf("."))+".m" ;
                     MainFrame.octavePanel.evaluate("run("+"'"+path.getParent().resolve(Paths.get(mscript)).toString() +"'"+");");
                     MainFrame.octavePanel.evaluate("whos");
                     reloadWorkspace();
                }
        }
        
     
        /**
         * Run script.
         */
        private void runFile(Path path) {

            String name =path.getFileName().toString();
            String ext =name.substring(name.lastIndexOf("."));

            if(ext.equalsIgnoreCase(".m")){
                String m_file_name=name.substring(0, name.indexOf(".m")) ;
                 MainFrame.octavePanel.evalWithOutput(m_file_name);
                 MainFrame.octavePanel.commandArea.setText("");
                 MainFrame.octavePanel.commandArea.setText(m_file_name);
                 reloadWorkspace();

            }else if(ext.equalsIgnoreCase(".pl")) {
                MainFrame.octavePanel.evalWithOutput("perl("+"'"+path.toString()+"'"+");");

            }else if(ext.equalsIgnoreCase(".mod")) {
                setUpDynare(path);
            }else if(ext.equalsIgnoreCase(".dyn")) {
                setUpDynare(path);
            }
        }
    public void newFile() {
        NewScriptDialog newFileDlg =new NewScriptDialog(this,true,this.dynareOptions,this.dynarePath);
        newFileDlg.setLocationRelativeTo(this);
        newFileDlg.setVisible(true);
        this.openInFlotingWindow(this.fileTabView);
    }
   
     public void needOct(boolean need) {
          AutoCompletion ac = new AutoCompletion(provider1);

        if(need) {
             ac.install(this.areaFileTab);
             
        }
         else   {
              ac.uninstall();
         }
    }
   public CompletionProvider createCompletionProvider() {


      DefaultCompletionProvider provider = new DefaultCompletionProvider();

      
      JList l = new JList();
     
        AutoCompleteListCellRenderer cellRender = new  AutoCompleteListCellRenderer(l.getFont(),
                                               l.getBackground(),l.getForeground(),
                                               l.getSelectionBackground(),l.getSelectionForeground());
      provider.setListCellRenderer(cellRender);
      
      OctaveM _m = new OctaveM();
      List a = _m.getKey("DomainMath_OctaveAutoComplete.ini");
      
     
      for(int i=0;i<a.size();i++) {
          provider.addCompletion(new BasicCompletion(provider, a.get(i).toString()));
      }

      return provider;

   }
    public void save(File file,int index) {
      
     try {
            try (BufferedWriter r = new BufferedWriter(new FileWriter(file))) {
                this.fileTab.setTitleAt(index,file.getName());
                RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(index);
                RSyntaxTextArea a = (RSyntaxTextArea)t.getTextArea();
                a.write(r);
                r.close();
                this.setCurrentDirFileTab(file.getParent());
            }
                       
		} catch (Exception re) {
		JOptionPane.showMessageDialog(this,re.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                
		}
}

    private void printdoc() {
        if(fileTab.getSelectedIndex() >=0) {
            try {
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
	RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
            selectedArea.print();
        } catch (PrinterException ex) {
            
        }
        }
        
    }
    
    private void popupTab(){
        JPopupMenu popup = new JPopupMenu();
        JMenuItem pcloseItem = new JMenuItem("Close");
        JMenuItem pcloseAllItem = new JMenuItem("Close All");
        
        popup.add(pcloseItem);
        popup.add(pcloseAllItem);
        fileTab.addMouseListener(new MainFrame.PopupListener(popup));
        
        pcloseItem.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileTab.getSelectedIndex() >= 0) { 
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
    
    /**
     * It closes a file.
     * @param selectedIndex 
     */
    public void askSave(int selectedIndex) {
            String s = fileTab.getTitleAt(selectedIndex) ; 
            
            if(s.endsWith("*")) {   //modified document.
                String f =s.substring(0, s.lastIndexOf("*"));
                
                 // ask to save file.
                 int i =  JOptionPane.showConfirmDialog(this, "Do you want to save changes in "+f+" ?", "DomainMath IDE", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                 
                 if(i == JOptionPane.YES_OPTION) {  //need to save file
                     
                     // add this file to recent menu list.
                     File selected_file = new File(fileTab.getToolTipTextAt(selectedIndex));  
                     recentFileMenu.addEntry(selected_file.getAbsolutePath()); 
                   
                     // save the file.
                     save(selected_file,selectedIndex);     
                     
                     // close the file.
                     this.removeFileNameFromList(selectedIndex);
                     fileTab.remove(selectedIndex);
                     FILE_TAB_INDEX--;

                 }else if (i == JOptionPane.NO_OPTION){    // I dont need to save the file.
                     
                     // add this file to recent menu list.
                     File selected_file = new File(fileTab.getToolTipTextAt(selectedIndex));
                     recentFileMenu.addEntry(selected_file.getAbsolutePath());
                     
                     // close the file.
                     this.removeFileNameFromList(selectedIndex);
                     fileTab.remove(selectedIndex);
                     FILE_TAB_INDEX--;
                    
                 }
            }else {     //unmodified file.
                
                // add this file to recent menu list.
                File selected_file = new File(fileTab.getToolTipTextAt(selectedIndex));
                recentFileMenu.addEntry(selected_file.getAbsolutePath());  
                
                // close the file.
                removeFileNameFromList(selectedIndex);
                fileTab.remove(selectedIndex);
                FILE_TAB_INDEX--;
            }
    }

    /**
     * Start Jalview.
     */
    private void startJalview() {
        System.out.println("Java version: "
            + System.getProperty("java.version"));
    System.out.println(System.getProperty("os.arch") + " "
            + System.getProperty("os.name") + " "
            + System.getProperty("os.version"));
    if (new Platform().isAMac())
    {
      System.setProperty("com.apple.mrj.application.apple.menu.about.name",
              "Jalview");
      System.setProperty("apple.laf.useScreenMenuBar", "true");
    }
    String args[] = {""};
    ArgsParser aparser = new ArgsParser(args);
    boolean headless = false;


   Cache.loadProperties(aparser.getValue("props")); // must do this before
    // anything else!
    String defs = aparser.getValue("setprop");
    while (defs != null)
    {
      int p = defs.indexOf('=');
      if (p == -1)
      {
        System.err.println("Ignoring invalid setprop argument : " + defs);
      }
      else
      {
        System.out.println("Executing setprop argument: " + defs);
        // DISABLED FOR SECURITY REASONS
        // TODO: add a property to allow properties to be overriden by cli args
        // Cache.setProperty(defs.substring(0,p), defs.substring(p+1));
      }
      defs = aparser.getValue("setprop");
    }
    if (aparser.contains("nodisplay"))
    {
      System.setProperty("java.awt.headless", "true");
    }
    if (System.getProperty("java.awt.headless") != null
            && System.getProperty("java.awt.headless").equals("true"))
    {
      headless = true;
    }
    System.setProperty("http.agent", "Jalview Desktop/"+Cache.getDefault("VERSION", "Unknown"));
    try
    {
      Cache.initLogger();
    } catch (java.lang.NoClassDefFoundError error)
    {
      System.out
              .println("\nEssential logging libraries not found."
                      + "\nUse: java -Djava.ext.dirs=$PATH_TO_LIB$ jalview.bin.Jalview");
      System.exit(0);
    }

    jalview.gui.Desktop desktop = null;


    if (!headless)
    {
      desktop = new jalview.gui.Desktop();
      desktop.setInBatchMode(true); // indicate we are starting up
      desktop.setVisible(true);
      desktop.startServiceDiscovery();
    }
    }

    private void createRootWindow() {

       viewMap.addView(0, fileTabView);
       viewMap.addView(1, pathsView);
       viewMap.addView(2, pkgView);
       viewMap.addView(3, consoleView);
       viewMap.addView(4, arrayEditorView);
       viewMap.addView(5, workspaceView);
       viewMap.addView(6, filesView);
       viewMap.addView(7, historyView);
       
      MixedViewHandler handler = new MixedViewHandler(viewMap, new ViewSerializer() {
          @Override
      public void writeView(View view, ObjectOutputStream out) throws IOException {
        out.writeInt(((DynamicView) view).getId());
      }

          @Override
      public View readView(ObjectInputStream in) throws IOException {
        return getDynamicView(in.readInt());
      }
    });
     rootWindow = DockingUtil.createRootWindow(viewMap, handler, true);


    properties.addSuperObject(currentTheme.getRootWindowProperties());
    properties.addSuperObject(titleBarStyleProperties);
    rootWindow.getRootWindowProperties().addSuperObject(properties);

    rootWindow.getWindowBar(Direction.DOWN).setEnabled(true);

    rootWindow.addListener(new DockingWindowAdapter() {
        @Override
      public void windowAdded(DockingWindow addedToWindow, DockingWindow addedWindow) {
        updateViews(addedWindow, true);

      }

        @Override
      public void windowRemoved(DockingWindow removedFromWindow, DockingWindow removedWindow) {
        updateViews(removedWindow, false);
      }

        @Override
      public void windowClosing(DockingWindow window) throws OperationAbortedException {
        // Confirm close operation
        if (JOptionPane.showConfirmDialog(null, "Really close window '" + window + "'?") != JOptionPane.YES_OPTION) {
              throw new OperationAbortedException("Window close was aborted!");
          }
      }

        @Override
      public void windowDocking(DockingWindow window) throws OperationAbortedException {
        // Confirm dock operation
        if (JOptionPane.showConfirmDialog(null, "Really dock window '" + window + "'?") != JOptionPane.YES_OPTION) {
              throw new OperationAbortedException("Window dock was aborted!");
          }
      }

        @Override
      public void windowUndocking(DockingWindow window) throws OperationAbortedException {
        // Confirm undock operation 
        if (JOptionPane.showConfirmDialog(null, "Really undock window '" + window + "'?") != JOptionPane.YES_OPTION) {
              throw new OperationAbortedException("Window undock was aborted!");
          }
      }  
            });
    rootWindow.addTabMouseButtonListener(DockingWindowActionMouseButtonListener.MIDDLE_BUTTON_CLOSE_LISTENER);
    }
   
     private void updateViews(DockingWindow window, boolean added) {
    if (window instanceof View) {
      if (window instanceof DynamicView) {
        if (added) {
              dynamicViews.put(new Integer(((DynamicView) window).getId()), window);
          }
        else {
              dynamicViews.remove(new Integer(((DynamicView) window).getId()));
          }
      }
      else {
       
//            if (this.fileTabView == window && this.codeEditorItem != null) {
//                this.codeEditorItem .setEnabled(!added);
//            }else if (this.pathsView == window && this.pathsItem != null) {
//                this.pathsItem .setEnabled(!added);
//            }else if (this.pkgView == window && this.pkgsItem != null) {
//                this.pkgsItem.setEnabled(!added);
//            }else if (this.consoleView == window && this.consoleItem != null) {
//                this.consoleItem .setEnabled(!added);
//            }else if (this.arrayEditorView == window && this.arrayEditItem != null) {
//                this.arrayEditItem.setEnabled(!added);
//            }else if (this.workspaceView == window && this.workspaceItem != null) {
//                this.workspaceItem .setEnabled(!added);
//            }else if (this.filesView == window && this.filesItem != null) {
//                this.filesItem .setEnabled(!added);
//            }else if (this.historyView == window && this.historyItem != null) {
//                this.historyItem .setEnabled(!added);
//            }
          
      }
    }
    else {
      for (int i = 0; i < window.getChildWindowCount(); i++) {
            updateViews(window.getChildWindow(i), added);
        }
    }
  }
    private View getDynamicView(int id) {
    View view = (View) dynamicViews.get(new Integer(id));

    if (view == null) {
        if(id == 0) {
            view =fileTabView;
        }else if(id ==1) {
            view=pathsView;
        }else if(id ==2) {
            view=pkgView;
        }else if(id ==3) {
            view=consoleView;
        }else if(id ==4) {
            view=arrayEditorView;
        }else if(id ==5) {
            view=workspaceView;
        }else if(id ==6) {
            view=filesView;
        }else if(id ==7) {
            view=historyView;
        }
          
      }

    return view;
  }
  
    private void setDefaultLayout() {
       workspaceAndFilesTabWindow.addTab(this.workspaceView);
       workspaceAndFilesTabWindow.addTab(this.filesView);
        
       workspaceView.restoreFocus();
       
        SplitWindow leftSplitWindow = new SplitWindow(false, 0.7f,this.workspaceAndFilesTabWindow,this.historyView);
       
       rootWindow.setWindow(new SplitWindow(true,
                                         0.3f,leftSplitWindow,consoleView));

    WindowBar windowBar = rootWindow.getWindowBar(Direction.DOWN);

    while (windowBar.getChildWindowCount() > 0) {
          windowBar.getChildWindow(0).close();
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
    private void runScriptItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runScriptItemActionPerformed
        if(fileTab.getSelectedIndex() >= 0) {
            save();
            File file_selected = new File(fileTab.getToolTipTextAt(fileTab.getSelectedIndex()));
            requestToChangeDir(file_selected.getParent()); 
            setDir(file_selected.getParent());
            this.runFile(Paths.get(file_selected.getAbsolutePath()));

       }
        
    }//GEN-LAST:event_runScriptItemActionPerformed

    private void imageToolItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageToolItemActionPerformed
        ImageToolFrame imageToolFrame = new ImageToolFrame();
        imageToolFrame.setLocationRelativeTo(this);
        imageToolFrame.setVisible(true);
    }//GEN-LAST:event_imageToolItemActionPerformed

    private void multicoreItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multicoreItemActionPerformed
        MulticoreDialog multicoreDialog = new MulticoreDialog(this,true);
        multicoreDialog.setLocationRelativeTo(this);
        multicoreDialog.setVisible(true);
    }//GEN-LAST:event_multicoreItemActionPerformed

    private void finishDebugItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishDebugItemActionPerformed
        MainFrame.octavePanel.evalWithOutput("dbquit");
    }//GEN-LAST:event_finishDebugItemActionPerformed

    private void stepItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepItemActionPerformed
        MainFrame.octavePanel.evalWithOutput("dbstep");
    }//GEN-LAST:event_stepItemActionPerformed

    private void stepInItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepInItemActionPerformed
        MainFrame.octavePanel.evalWithOutput("dbstep in");
    }//GEN-LAST:event_stepInItemActionPerformed

    private void stepOutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepOutItemActionPerformed
        MainFrame.octavePanel.evalWithOutput("dbstep out");
    }//GEN-LAST:event_stepOutItemActionPerformed

    private void setToggleBreakpoint() {
        if(fileTab.getSelectedIndex() >= 0) {
            
            save();
            
            String file_selected = fileTab.getTitleAt(fileTab.getSelectedIndex());
            if(file_selected.endsWith(".m")) {
                
                 String ftn_name=file_selected.substring(0,file_selected.indexOf(".m"));
                 RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
                 RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
                 
             octavePanel.evalWithOutput( "dbstop ('"+ftn_name+"',"+ selectedArea.getCaretLineNumber()+")"); 
           
                }
                   
            }else{
                System.out.println("Hello");
            } 
       
    }
    
    private void removeToggleBreakpoint() {
        if(fileTab.getSelectedIndex() >= 0) {
            
            save();
            
            String file_selected = fileTab.getTitleAt(fileTab.getSelectedIndex());
            if(file_selected.endsWith(".m")) {
                
                 String ftn_name=file_selected.substring(0,file_selected.indexOf(".m"));
                 RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
                 RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
                 
                octavePanel.evalWithOutput( "dbclear ('"+ftn_name+"',"+ selectedArea.getCaretLineNumber()+")"); 
                }
                   
            }else{
                System.out.println("Hello");
            } 
    }
    private void toggleBreakpointItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleBreakpointItemActionPerformed
         if(fileTab.getSelectedIndex() >= 0) {
        RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
                    RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
							
            try {
                   
                    setToggleBreakpoint();
                    t.getGutter().addLineTrackingIcon(selectedArea.getCaretLineNumber(), new ImageIcon(urlDebugImageStop));
  
            } catch (BadLocationException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
         }		
                        
    }//GEN-LAST:event_toggleBreakpointItemActionPerformed

    private void clearAllBreakpointsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllBreakpointsItemActionPerformed

        if(fileTab.getSelectedIndex() >= 0) {
            
            save();
            
            String file_selected = fileTab.getTitleAt(fileTab.getSelectedIndex());
            if(file_selected.endsWith(".m")) {
                String ftn_name=file_selected.substring(0,file_selected.indexOf(".m"));
                
                 
             octavePanel.evalWithOutput( "dbclear ('"+ftn_name+"',dbstatus('"+ftn_name+"'.line))"); 
             
             RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
                    t.getGutter().removeAllTrackingIcons();
            }else{
                System.out.println("");
            }

       }
    }//GEN-LAST:event_clearAllBreakpointsItemActionPerformed

    private void removeToggleBreakpointItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeToggleBreakpointItemActionPerformed
         if(fileTab.getSelectedIndex() >= 0) {
         RTextScrollPane t =(RTextScrollPane) fileTab.getComponentAt(fileTab.getSelectedIndex());
                    RSyntaxTextArea selectedArea = (RSyntaxTextArea)t.getTextArea();
        try {
            GutterIconInfo[] trackingIcons = gutterFileTab.getTrackingIcons(selectedArea.getCaret().getMagicCaretPosition());
            removeToggleBreakpoint();
                    t.getGutter().removeTrackingIcon(trackingIcons[0]);
        } catch (Exception ex) {
           
        }
         }
    }//GEN-LAST:event_removeToggleBreakpointItemActionPerformed

    private void continueItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continueItemActionPerformed
        MainFrame.octavePanel.evalWithOutput("dbcont");
    }//GEN-LAST:event_continueItemActionPerformed

    private void stackItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stackItemActionPerformed
       MainFrame.octavePanel.evalWithOutput("dbstack");
    }//GEN-LAST:event_stackItemActionPerformed

    private void dbupItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dbupItemActionPerformed
        MainFrame.octavePanel.evalWithOutput("dbup");
    }//GEN-LAST:event_dbupItemActionPerformed

    private void dbdownItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dbdownItemActionPerformed
        MainFrame.octavePanel.evalWithOutput("dbdown");
    }//GEN-LAST:event_dbdownItemActionPerformed

    private void nNetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nNetMenuItemActionPerformed
        NnetFrame nnetFrame = new NnetFrame();
        nnetFrame.setLocationRelativeTo(this);
        nnetFrame.setVisible(true);
    }//GEN-LAST:event_nNetMenuItemActionPerformed

    private void saveAsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsItemActionPerformed
        if(fileTab.getSelectedIndex() >=0) {
            saveAs();
        }
        
    }//GEN-LAST:event_saveAsItemActionPerformed

    private void dirComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dirComboBoxItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) {
            octavePanel.evaluate("chdir "+"'"+dirComboBox.getSelectedItem().toString()+"'"); 
             
        }
    }//GEN-LAST:event_dirComboBoxItemStateChanged

    private void deleteVariableItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteVariableItemActionPerformed
        workspace.delete();
    }//GEN-LAST:event_deleteVariableItemActionPerformed

    private void newVariableItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newVariableItemActionPerformed
        workspace.addVariable();
    }//GEN-LAST:event_newVariableItemActionPerformed

    private void clearAllItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllItemActionPerformed
        workspace.clearAll();
    }//GEN-LAST:event_clearAllItemActionPerformed

    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMenuItemActionPerformed
        workspace.open();
    }//GEN-LAST:event_loadMenuItemActionPerformed

    private void saveDataItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveDataItemActionPerformed
        workspace.saveData();
    }//GEN-LAST:event_saveDataItemActionPerformed

    private void saveAllDataItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAllDataItemActionPerformed
        workspace.saveAll();
    }//GEN-LAST:event_saveAllDataItemActionPerformed

    private void importSpreadSheetDataItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importSpreadSheetDataItemActionPerformed
       workspace.importSpreadSheetData();
    }//GEN-LAST:event_importSpreadSheetDataItemActionPerformed

    private void exportItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportItemActionPerformed
        workspace.exportData();
    }//GEN-LAST:event_exportItemActionPerformed

    private void exportAllItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportAllItemActionPerformed
        workspace.exportAll();
    }//GEN-LAST:event_exportAllItemActionPerformed

    private void refreshItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshItemActionPerformed
        workspace.refreshData();
    }//GEN-LAST:event_refreshItemActionPerformed

    public void dragNDrop() {
        FileDrop fileDrop = new FileDrop( System.out, fileTab, /*dragBorder,*/ new FileDrop.Listener(){   
            @Override
            public void filesDropped( java.io.File[] files ){   
                for( int i = 0; i < files.length; i++ ) { 
                    try{   
                         File file1=files[i];
                           if(!MainFrame.fileNameList.contains(file1.getAbsolutePath())) {
                               
                               open(file1, MainFrame.FILE_TAB_INDEX);
                               setCurrentDirFileTab(file1.getParent());                
                            }else {
                                System.out.println(file1.getAbsolutePath()+" already open!");
                            }
                    }   
                    catch( Exception e ) {
                    }
                 }   
             }   
        }); 

    }
    private void multipleSequenceViewerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multipleSequenceViewerMenuItemActionPerformed
      
//        MultiSeqAlignViewerFrame multiSeqAlignViewerFrame = new MultiSeqAlignViewerFrame();
//        multiSeqAlignViewerFrame.setLocationRelativeTo(this);
//        multiSeqAlignViewerFrame.setVisible(true);
        startJalview();
    }//GEN-LAST:event_multipleSequenceViewerMenuItemActionPerformed

    private void hmmerItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hmmerItemActionPerformed
        HmmerFrame hmmerFrame = new HmmerFrame();
        hmmerFrame.setLocationRelativeTo(this);
        hmmerFrame.setVisible(true);
    }//GEN-LAST:event_hmmerItemActionPerformed

    private void sequenceViewerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sequenceViewerMenuItemActionPerformed
                  SeqViewerFrame seqFrame = new SeqViewerFrame();
                seqFrame.setLocationRelativeTo(this);
                seqFrame.setVisible(true);
    }//GEN-LAST:event_sequenceViewerMenuItemActionPerformed

    private void previousWindowItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousWindowItemActionPerformed
         if(fileTab.getSelectedIndex() >=0) {
                 if(fileTab.getSelectedIndex() != 0){
                    fileTab.setSelectedIndex((fileTab.getSelectedIndex()-1));
                // FILE_TAB_INDEX=(fileTab.getSelectedIndex()-1); 
                 }
                 
                 
           
         }
        
    }//GEN-LAST:event_previousWindowItemActionPerformed

    private void nextWindowItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextWindowItemActionPerformed
        if(fileTab.getSelectedIndex() >=0) {
            try{
                 fileTab.setSelectedIndex((fileTab.getSelectedIndex()+1));
                // FILE_TAB_INDEX=(fileTab.getSelectedIndex()+1);
            }catch(Exception e){
                 fileTab.setSelectedIndex(0);
               //  FILE_TAB_INDEX=(0);
            }
               

         }
    }//GEN-LAST:event_nextWindowItemActionPerformed

    private void focusView(View view) {
        if (view.getRootWindow() != null) {
            view.restoreFocus();
        }
          else {
            DockingUtil.addWindow(view, rootWindow);
          }
    }
    private void codeEditorItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codeEditorItemActionPerformed
       focusView(this.fileTabView);
    }//GEN-LAST:event_codeEditorItemActionPerformed

    private void pathsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathsItemActionPerformed
        this.openInFlotingWindow(this.pathsView);
    }//GEN-LAST:event_pathsItemActionPerformed

    private void pkgsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pkgsItemActionPerformed
        this.openInFlotingWindow(this.pkgView);
    }//GEN-LAST:event_pkgsItemActionPerformed

    private void consoleItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consoleItemActionPerformed
        focusView(this.consoleView);
    }//GEN-LAST:event_consoleItemActionPerformed

    private void arrayEditItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arrayEditItemActionPerformed
        this.openInFlotingWindow(this.arrayEditorView);
    }//GEN-LAST:event_arrayEditItemActionPerformed

    private void workspaceItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_workspaceItemActionPerformed
        focusView(this.workspaceView);
    }//GEN-LAST:event_workspaceItemActionPerformed

    private void filesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filesItemActionPerformed
        focusView(this.filesView);
    }//GEN-LAST:event_filesItemActionPerformed

    private void historyItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyItemActionPerformed
        focusView(this.historyView);
    }//GEN-LAST:event_historyItemActionPerformed

    private void windowsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_windowsItemActionPerformed
        WindowsDialog windowsDialog = new WindowsDialog(this,true);
        windowsDialog.setLocationRelativeTo(this);
        windowsDialog.setVisible(true);
    }//GEN-LAST:event_windowsItemActionPerformed

    public static void main(String args[])   {
                 try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
       }
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
              new MainFrame().setVisible(true);
                      
            }
        });
    }
    public  java.net.URL imgURL = getClass().getResource("/org/domainmath/gui/resources/DomainMath.png");
    public   Image icon = Toolkit.getDefaultToolkit().getImage(imgURL);
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private javax.swing.JMenu BioInfoMenu;
    private javax.swing.JMenu WorkspaceMenu;
    private javax.swing.JButton addButton;
    private javax.swing.JMenuItem arrayEditItem;
    private javax.swing.JMenuItem arrayEditorItem;
    public static javax.swing.JCheckBoxMenuItem automaticRefreshCheckBoxMenuItem;
    private javax.swing.JButton browseButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenuItem clearAllBreakpointsItem;
    private javax.swing.JMenuItem clearAllItem;
    private javax.swing.JMenuItem clearOutWindowItem;
    private javax.swing.JMenuItem closeAllItem;
    private javax.swing.JMenuItem closeItem;
    private javax.swing.JMenuItem codeEditorItem;
    private javax.swing.JButton connectButton;
    private javax.swing.JMenuItem connectItem;
    private javax.swing.JMenuItem consoleItem;
    private javax.swing.JMenuItem continueItem;
    private javax.swing.JMenuItem copyItem;
    private javax.swing.JMenuItem cutItem;
    private javax.swing.JMenuItem dSmoothItem;
    private javax.swing.JMenuItem dataBaseMenuItem;
    private javax.swing.JMenuItem dbdownItem;
    private javax.swing.JMenuItem dbupItem;
    private javax.swing.JMenu debugMenu;
    private javax.swing.JMenuItem deleteItem;
    private javax.swing.JMenuItem deleteVariableItem;
    private javax.swing.JMenu diaryMenu;
    private javax.swing.JRadioButtonMenuItem diaryOffItem;
    private javax.swing.JRadioButtonMenuItem diaryOnItem;
    private javax.swing.JMenuItem diarySaveItem;
    public static javax.swing.JComboBox dirComboBox;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JMenuItem disconnectItem;
    private javax.swing.JMenuItem dleEditorItem;
    private javax.swing.JMenuItem docPkgItem;
    private javax.swing.JMenuItem dynareItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem exportAllItem;
    private javax.swing.JMenuItem exportItem;
    private javax.swing.JMenuItem faqItem;
    private javax.swing.JMenuItem feedBackItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem filesItem;
    private javax.swing.JMenuItem findItem;
    private javax.swing.JMenuItem finishDebugItem;
    private javax.swing.JMenuItem fltkplotItem;
    private javax.swing.JButton folderUpButton;
    private javax.swing.JMenuItem forumItem;
    private javax.swing.JMenuItem googleItem;
    private javax.swing.JMenuItem gotoItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem historyItem;
    private javax.swing.JMenuItem hmmerItem;
    private javax.swing.JMenuItem howToItem;
    private javax.swing.JMenuItem imageToolItem;
    private javax.swing.JMenuItem importSpreadSheetDataItem;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JToolBar.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JToolBar.Separator jSeparator15;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JPopupMenu.Separator jSeparator17;
    private javax.swing.JPopupMenu.Separator jSeparator18;
    private javax.swing.JPopupMenu.Separator jSeparator19;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator20;
    private javax.swing.JPopupMenu.Separator jSeparator21;
    private javax.swing.JPopupMenu.Separator jSeparator22;
    private javax.swing.JPopupMenu.Separator jSeparator23;
    private javax.swing.JPopupMenu.Separator jSeparator24;
    private javax.swing.JPopupMenu.Separator jSeparator25;
    private javax.swing.JPopupMenu.Separator jSeparator26;
    private javax.swing.JPopupMenu.Separator jSeparator27;
    private javax.swing.JPopupMenu.Separator jSeparator28;
    private javax.swing.JPopupMenu.Separator jSeparator29;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JMenuItem multicoreItem;
    private javax.swing.JMenuItem multipleSequenceViewerMenuItem;
    private javax.swing.JMenuItem nNetMenuItem;
    private javax.swing.JMenuItem newFileItem;
    private javax.swing.JMenuItem newVariableItem;
    private javax.swing.JMenuItem nextWindowItem;
    private javax.swing.JMenuItem octaveCmdItem;
    private javax.swing.JMenuItem octaveInfoItem;
    private javax.swing.JMenuItem octaveItem;
    private javax.swing.JMenuItem onlineHelpItem;
    private javax.swing.JMenuItem openItem;
    private javax.swing.JMenuItem optimItem;
    private javax.swing.JMenuItem pasteItem;
    private javax.swing.JMenuItem pathsItem;
    private javax.swing.JMenuItem phyConstItem;
    private javax.swing.JMenu pkgMenuItem;
    private javax.swing.JMenuItem pkgsItem;
    private javax.swing.JMenuItem preferencesItem;
    private javax.swing.JMenuItem previousWindowItem;
    private javax.swing.JMenuItem printFileItem;
    private javax.swing.JMenuItem printItem;
    private javax.swing.JMenuItem quickHelpItem;
    private javax.swing.JMenuItem redoItem;
    private javax.swing.JMenuItem referenceItem;
    private javax.swing.JMenu referenceMenu;
    private javax.swing.JMenuItem refreshItem;
    private javax.swing.JMenuItem removeToggleBreakpointItem;
    private javax.swing.JMenuItem replaceItem;
    private javax.swing.JMenuItem reportBugItem;
    private javax.swing.JMenuItem runScriptItem;
    private javax.swing.JMenuItem saveAllDataItem;
    private javax.swing.JMenuItem saveAllItem;
    private javax.swing.JMenuItem saveAsItem;
    private javax.swing.JMenuItem saveDataItem;
    private javax.swing.JMenuItem saveFileItem;
    private javax.swing.JMenuItem savePlotItem;
    private javax.swing.JMenuItem selectAllItem;
    private javax.swing.JMenuItem sequenceViewerMenuItem;
    private javax.swing.JMenuItem stackItem;
    private javax.swing.JMenuItem stepInItem;
    private javax.swing.JMenuItem stepItem;
    private javax.swing.JMenuItem stepOutItem;
    private javax.swing.JMenuItem suggestionsItem;
    private javax.swing.JMenuItem toggleBreakpointItem;
    private javax.swing.JMenu toolsMenu;
    private javax.swing.JMenuItem undoItem;
    private javax.swing.JMenuItem wikiItem;
    private javax.swing.JMenuItem windowsItem;
    private javax.swing.JMenuItem worksheetItem;
    private javax.swing.JMenuItem workspaceItem;
    // End of variables declaration//GEN-END:variables
private class ArgsParser
{
  
    List vargs = null;
  public ArgsParser(String[] args)
  {
      try {
          vargs =Collections.synchronizedList(new ArrayList());
    for (int i = 0; i < args.length; i++)
    {
      String arg = args[i].trim();
      if (arg.charAt(0) == '-')
      {
        arg = arg.substring(1);
      }
      vargs.add(arg);
    }
      }catch(Exception e) {
          
      }
    
  }

  /**
   * check for and remove first occurence of arg+parameter in arglist.
   * 
   * @param arg
   * @return return the argument following the given arg if arg was in list.
   */
  public String getValue(String arg)
  {
    return getValue(arg, false);
  }

  public String getValue(String arg, boolean utf8decode)
  {
    int index = vargs.indexOf(arg);
    String dc = null, ret = null;
    if (index != -1)
    {
      ret = vargs.get(index + 1).toString();
      vargs.remove(index);
      vargs.remove(index);
      if (utf8decode && ret != null)
      {
        try
        {
          dc = URLDecoder.decode(ret, "UTF-8");
          ret = dc;
        } catch (Exception e)
        {
          // TODO: log failure to decode
        }
      }
    }
    return ret;
  }

  /**
   * check for and remove first occurence of arg in arglist.
   * 
   * @param arg
   * @return true if arg was present in argslist.
   */
  public boolean contains(String arg)
  {
    if (vargs.contains(arg))
    {
      vargs.remove(arg);
      return true;
    }
    else
    {
      return false;
    }
  }

  public String nextValue()
  {
    return vargs.remove(0).toString();
  }

  public int getSize()
  {
    return vargs.size();
  }

}

/**
 * keep track of feature fetching tasks.
 * 
 * @author JimP
 * 
 */
private class FeatureFetcher
{
  /*
   * TODO: generalise to track all jalview events to orchestrate batch
   * processing events.
   */

  private int queued = 0;

  private int running = 0;

  public FeatureFetcher()
  {

  }

  public void addFetcher(final AlignFrame af, final Vector dasSources)
  {
    final long id = System.currentTimeMillis();
    queued++;
    final FeatureFetcher us = this;
    new Thread(new Runnable()
    {

      public void run()
      {
        synchronized (us)
        {
          queued--;
          running++;
        }

        af.setProgressBar("DAS features being retrieved...", id);
        af.featureSettings_actionPerformed(null);
        af.featureSettings.fetchDasFeatures(dasSources, true);
        af.setProgressBar(null, id);
        synchronized (us)
        {
          running--;
        }
      }
    }).start();
  }

  public synchronized boolean allFinished()
  {
    return queued == 0 && running == 0;
  }
}

private static class DynamicView extends View {
    private int id;

    /**
     * Constructor.
     *
     * @param title     the view title
     * @param icon      the view icon
     * @param component the view component
     * @param id        the view id
     */
    DynamicView(String title, Icon icon, Component component, int id) {
      super(title, icon, component);
      this.id = id;
    }

    /**
     * Returns the view id.
     *
     * @return the view id
     */
    public int getId() {
      return id;
    }
  }
}
