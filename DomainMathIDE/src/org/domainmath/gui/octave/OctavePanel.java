
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

package org.domainmath.gui.octave;


import dev.exec.util.ExecHelper;
import dev.exec.util.ExecProcessor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.arrayeditor.ArrayEditorPanel;
import org.domainmath.gui.editor.AutoCompleteListCellRenderer;
import org.domainmath.gui.editor.OctaveM;
import org.domainmath.gui.octave.OctavePanel.OctaveEngine;
import org.domainmath.gui.pathsview.PathsViewPanel;
import org.domainmath.gui.pkgview.PkgViewPanel;
import org.domainmath.gui.preferences.PreferencesDlg;
import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;



public class OctavePanel extends JPanel implements ExecProcessor{

    private  JScrollPane scrollOutputArea;
    public RSyntaxTextArea commandArea;
    public JTextArea outputArea = new JTextArea();
    OctaveEngine oc = new OctaveEngine();
    public static String line2;
    CompletionProvider provider = createCompletionProvider();
    StringBuilder b = new StringBuilder();
    private final MainFrame frame;
    private final String path;
    public  JTabbedPane tab = new JTabbedPane();
    
    private PreferencesDlg preferencesDlg;
    private List history =Collections.synchronizedList(new ArrayList());
    private int histLine;
    private int id=1;
    private JPopupMenu _p1;
    private ExecHelper exh;
    public OctavePanel(MainFrame frame,String path) {
      
        this.frame = frame;
        this.path = path;
        
        init();
    }
    
    public void start() {
         try {
                    
                    
                    
                    if (exh == null) {
                                 outputArea.append("Connecting..."+"\n");
                                String path2 =frame.getOctavePath()+" "+frame.getCmdLineOptions();
                                String addpath = " --path "+Character.toString('"') +System.getProperty("user.dir")+File.separator+"scripts"+Character.toString('"');
                   
				exh = ExecHelper.exec(this, path2+addpath);
                                System.err.println(path2+addpath);
				oc.find(frame.getStartupCmd());
                                oc.find("warning off");
			
                    }

                   
                   

                 } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Unable to find Octave","DomainMath IDE",JOptionPane.ERROR_MESSAGE);
                        preferencesDlg = new PreferencesDlg(frame,true);
                         preferencesDlg.setLocationRelativeTo(this);
                         preferencesDlg.setVisible(true);
                        
                    }
        
    
    }
 public void quit() {
     oc.exit();
     
 }


    private void init() {
        Font font = new	Font("Monospaced",Font.PLAIN,13);
        outputArea.setFont(font);
        commandArea =new RSyntaxTextArea();
        commandArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
        outputArea.setDragEnabled(true);
        scrollOutputArea = new JScrollPane(outputArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        outputArea.setBackground(new Color(255,255,193));    
        commandArea.setToolTipText("Type commands here,press Enter to get output");
        scrollOutputArea.setWheelScrollingEnabled(true);
        
        needOct(true);
        setLayout(new BorderLayout());
        KeyStroke key = KeyStroke.getKeyStroke(
                    KeyEvent.VK_ENTER, 0);

        commandArea.getInputMap().put(key, new ExecuteAction(commandArea,oc));
       
        PathsViewPanel pathPanel = new PathsViewPanel(path+"DomainMath_OctavePaths.dat");
        PkgViewPanel pkgView = new PkgViewPanel(path+"DomainMath_OctavePackages.dat",frame);
        
        
       outputArea.setEditable(false);
       commandArea.setBorder(null);
       JSplitPane p  = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
       p.setDividerLocation(130);
       p.add(scrollOutputArea);
       p.add(new RTextScrollPane(commandArea));
       p.setOneTouchExpandable(true);
       tab.addTab("Console",p);
       tab.addTab("Set Paths", pathPanel);
       tab.addTab("Packages", pkgView);
       
       tab.addTab("Array Editor",new ArrayEditorPanel());


       _p1 = new JPopupMenu();

       JMenuItem _copy = new JMenuItem("Copy");
       JMenuItem _selectAll = new JMenuItem("Select All");
       

       _p1.add(_copy);
       _p1.add(_selectAll);
       
       outputArea.add(_p1);
       
      
       
       _copy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.copy();
            }
           
       }); 
       
       _selectAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.selectAll();
            }
           
       }); 
       
       outputArea.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                outputArea.requestFocusInWindow();
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               outputArea.requestFocusInWindow();
               showPopup(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
               
            }

            @Override
            public void mouseExited(MouseEvent e) {
               
            }
           
       });
       add(tab,BorderLayout.CENTER);
       
       commandArea.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent evt) {
                    if(evt.getKeyCode() == KeyEvent.VK_ENTER ) {
                String s = commandArea.getText();

                    if ( s.length()	!= 0 )	{
                            history.add( s );

                    }

                    evt.consume();
            
            histLine = 0;
            commandArea.selectAll();

            }else if( evt.getKeyCode() == KeyEvent.VK_UP) {

                if ( histLine <history.size() ) {
                            histLine++;

                            showHistoryLine();
                            
                            commandArea.selectAll();

                    }
                evt.consume();
            }else if( evt.getKeyCode() == KeyEvent.VK_DOWN) {
                if ( histLine == 0 ) {
                    return ;
                }

                    histLine--;
                    showHistoryLine();
                    
                    commandArea.selectAll();
        }
            }
           
       });
    }

    private void showPopup(MouseEvent e){
     
        if( e.isPopupTrigger() ) {
            _p1.show(e.getComponent(), e.getX(), e.getY());
            
        }
            
     }
    /**
     * Evaluate commands for internal purpose.
     * @param c 
     */
    public void evaluate(String c) {
        if(MainFrame.automaticRefreshCheckBoxMenuItem.isSelected()) {
             System.out.println(c);
             oc.find(c);
        }
    }

     private	void showHistoryLine() {
        String showline;
                if(histLine != 0) {
                     showline = (String)history.get( history.size() - histLine);
                commandArea.setText(showline);

                }
                
	}
   
      public void evalWithOutput(String c) {
        outputArea.append(">> ");
        id++;
         outputArea.append(c+"\n");
         oc.find(c);
         
         if(MainFrame.automaticRefreshCheckBoxMenuItem.isSelected()) {
             MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+MainFrame.parent_root+"DomainMath_OctaveVariables.dat',whos);");
            MainFrame.varView.reload();
            MainFrame.varView.reload();
         }
         
         MainFrame.histArea.append(c+"\n");
         history.add(c);
    }
     
     public void setOctaveTag(String s) {
         outputArea.append(">> ");
        id++;
         outputArea.append(s+"\n");
     }
     public void eval(String c) {
         outputArea.append(">> ");
        id++;
         outputArea.append(c+"\n");
         oc.find(c);
         if(MainFrame.automaticRefreshCheckBoxMenuItem.isSelected()) {
             MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+MainFrame.parent_root+"DomainMath_OctaveVariables.dat',whos);");
            MainFrame.varView.reload();
            MainFrame.varView.reload();
         }
         MainFrame.histArea.append(c+"\n");
    }

     private void updateTextArea(JTextArea textArea, String line) {
            line = line.replaceAll(">>", "");

		outputArea.append(line);
		outputArea.setSelectionStart(textArea.getText().length());
		outputArea.setSelectionEnd(textArea.getText().length());
	}

    @Override
    public void processNewInput(String input) {
       updateTextArea(outputArea, input);
    }

    @Override
    public void processNewError(String error) {
        updateTextArea(outputArea, error);
    }

    @Override
    public void processEnded(int exitValue) {
       exh = null;
       
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
		}
    }

    class OctaveEngine {
    public void exit() {
                _eval("exit");
    }
    
    public void find(String c) {
       _eval(c);
    }
     public void eval(String c) {
         
      
        outputArea.append(">> ");
         outputArea.append(c+"\n");
         id++;
        _eval(c);
         if(MainFrame.automaticRefreshCheckBoxMenuItem.isSelected()) {
             MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+MainFrame.parent_root+"DomainMath_OctaveVariables.dat',whos);");
            MainFrame.varView.reload();
            MainFrame.varView.reload();
         }
    }
     public void eval(String c,String tag) {

         outputArea.append(tag);
         outputArea.append(c+"\n");
         id++;
        _eval(c);
        if(!c.contains("input") ||MainFrame.automaticRefreshCheckBoxMenuItem.isSelected()) {
         MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+MainFrame.parent_root+"DomainMath_OctaveVariables.dat',whos);");
         MainFrame.varView.reload();
         MainFrame.varView.reload();
        }
     }
}

  public void _eval(String text) {
      if (exh != null) {
			exh.println(text);
			
		}
  }
      
   public void needOct(boolean need) {
          AutoCompletion ac = new AutoCompletion(provider);

        if(need) {
                
             ac.install(commandArea);
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
   
   
    public void clear() {
        
        outputArea.setText("");
    }

  
   



 class ExecuteAction extends AbstractAction {
    private  RSyntaxTextArea area;
    private final OctaveEngine oc;
    private  int i;
    
    public ExecuteAction(RSyntaxTextArea area,OctaveEngine oc) {
       this.area=area;
       this.oc=oc;
       i=0;
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        i++;
        
        oc.eval(area.getText(),">> ");

            DateFormat formatter = 
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                                   DateFormat.MEDIUM,
                                   Locale.getDefault());
        
    
        String t="# -- "+formatter.format(new Date())+" -- #";
        
        
        if((i%10) == 0) {
            MainFrame.histArea.append(t+"\n");
        }
        MainFrame.histArea.append(area.getText()+"\n");
      
        area.setSelectionStart(0);
        area.setSelectionEnd(area.getText().length());
       
        
    }
    
}
}