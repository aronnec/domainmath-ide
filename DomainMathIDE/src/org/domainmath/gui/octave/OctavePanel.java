
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


import com.artenum.rosetta.interfaces.core.ConsoleConfiguration;
import com.artenum.rosetta.interfaces.ui.InputCommandView;
import com.artenum.rosetta.interfaces.ui.OutputView;
import com.artenum.rosetta.util.ConfigurationBuilder;
import com.artenum.rosetta.util.ConsoleBuilder;
import dev.exec.util.ExecHelper;
import dev.exec.util.ExecProcessor;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.arrayeditor.ArrayEditorPanel;

import org.domainmath.gui.pathsview.PathsViewPanel;
import org.domainmath.gui.pkgview.PkgViewPanel;
import org.domainmath.gui.preferences.PreferencesDlg;
import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.xml.sax.SAXException;



public class OctavePanel extends JPanel {

    public static String line2;
    
    StringBuilder b = new StringBuilder();
    private final MainFrame frame;
    private final String path;
    public  JTabbedPane tab = new JTabbedPane();
    
    private PreferencesDlg preferencesDlg;
    
    private JPopupMenu _p1;
    public static ExecHelper exh;
    private  String configFilePath;
    
    private  ConsoleConfiguration config;
    private  OutputView outputView;
    private  InputCommandView inputCommandView;
    private com.artenum.rosetta.ui.ConsoleTest buildConsole;
    public OctavePanel(MainFrame frame,String path) {
      
        this.frame = frame;
        this.path = path;
         
        
        
        init();
    }
    
    
    private void createConsole() {
        try {
         Font font = new Font("Monospaced",Font.PLAIN,13);
         java.net.URL url = getClass().getResource("resources/configuration.xml");
         configFilePath = url.getPath();
         
            config = ConfigurationBuilder.buildConfiguration(configFilePath);
            outputView = config.getOutputView();
            outputView.setFont(font);
            
        
        buildConsole = ConsoleBuilder.buildConsole(config, this);
        add(new JScrollPane(buildConsole), BorderLayout.CENTER);
        
        } catch (IllegalArgumentException | SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(OctavePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void start() {
         try {
                    
                    
                    
                    if (exh == null) {
                                 
                                String path2 =frame.getOctavePath()+" "+frame.getCmdLineOptions();
                                String addpath = " --path "+Character.toString('"') +System.getProperty("user.dir")+File.separator+"scripts"+Character.toString('"');
                                
				exh = ExecHelper.exec(new Exec(), path2+addpath);
                                inputCommandView  = config.getInputCommandView();    
                                exh.println(inputCommandView.getText());
                                System.err.println(path2+addpath);
				evaluate(frame.getStartupCmd());
                                evaluate("warning off");
			
                    }

                   
                   

                 } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Unable to find Octave","DomainMath IDE",JOptionPane.ERROR_MESSAGE);
                        preferencesDlg = new PreferencesDlg(frame,true);
                         preferencesDlg.setLocationRelativeTo(this);
                         preferencesDlg.setVisible(true);
                        
                    }
        
    
    }
 public void quit() {
     evaluate("exit");
     
 }

 public void clear() {
     this.outputView.setText("");
 }
    private void init() {
        setLayout(new BorderLayout());

        PathsViewPanel pathPanel = new PathsViewPanel(path+"DomainMath_OctavePaths.dat");
        PkgViewPanel pkgView = new PkgViewPanel(path+"DomainMath_OctavePackages.dat",frame);
        
        
      

       createConsole();
       tab.addTab("Console",new JScrollPane(this.buildConsole));
       tab.addTab("Set Paths", pathPanel);
       tab.addTab("Packages", pkgView);
       
       tab.addTab("Array Editor",new ArrayEditorPanel());

       add(tab,BorderLayout.CENTER);
       
       
    }
 public void evaluate(String text) {
     exh.println(text);
 }
 
class Exec implements ExecProcessor{

      private void updateTextArea(OutputView textArea, String line) {
          line = line.replaceAll(">>", "");
		textArea.append(line);
		
	}

    @Override
    public void processNewInput(String input) {
       updateTextArea(outputView, input);
    }

    @Override
    public void processNewError(String error) {
        updateTextArea(outputView, error);
    }

    @Override
    public void processEnded(int exitValue) {
       
       
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
		}
    }
        
    }
}