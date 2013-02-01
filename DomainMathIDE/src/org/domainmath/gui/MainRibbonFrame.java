/*
 * Copyright (C) 2011 Vinu K.N
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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.domainmath.gui.resources.icons.DomainMathIDE_SVG_ICON;
import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.JRibbonFrame;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenu;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryFooter;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary;
import org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.RibbonBandResizePolicy;


public class MainRibbonFrame extends JRibbonFrame {
     java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMathIDE_en");
    private RibbonApplicationMenu ribbonApplicationMenu;
    private JCommandButton undoTaskBarButton;
    private JCommandButton redoTaskBarButton;
    private JCommandButton saveAllTaskBarButton;
    private JCommandButton cutTaskBarButton;
    private JCommandButton copyTaskBarButton;
    private JCommandButton pasteTaskBarButton;
    private JRibbonBand workspaceBand;
    private JCommandButton varNewCommandButton;
    private JCommandButton varRemoveCommandButton;
    private JCommandButton importCommandButton;
    private JCommandButton exportCommandButton;
    private JCommandButton refreshCommandButton;
    private JRibbonBand historyBand;
    private final RibbonTask homeRibbonTask;
    private JCommandButton saveHistoryCommandButton;
    private JCommandButton clearHistoryCommandButton;
    private JCommandButton runInCommandButton;
    
    public MainRibbonFrame()  {
        this.setApplicationIcon(new DomainMathIDE_SVG_ICON());
        initComponents();
        homeRibbonTask = new RibbonTask("Home", createWorkspaceBand(),
                                                createHistoryBand());
        getRibbon().addTask(homeRibbonTask);
    }

    private JRibbonBand createWorkspaceBand(){
        workspaceBand = new JRibbonBand("Workspace",null);
        
        varNewCommandButton = new JCommandButton(bundle.getString("varNewCommandButton.text"),getResizableIcoFromResource24("resources/icons/size32/var-new.png"));
        varRemoveCommandButton = new JCommandButton(bundle.getString("varRemoveCommandButton.text"),getResizableIcoFromResource24("resources/icons/size32/var-remove.png"));
        importCommandButton = new JCommandButton(bundle.getString("importCommandButton.text"),getResizableIcoFromResource24("resources/icons/size32/var-import.png"));
        exportCommandButton = new JCommandButton(bundle.getString("exportCommandButton.text"),getResizableIcoFromResource24("resources/icons/size32/var-export.png"));
        refreshCommandButton = new JCommandButton(bundle.getString("refreshCommandButton.text"),getResizableIcoFromResource24("resources/icons/size32/refresh.png"));
        
        workspaceBand.addCommandButton(importCommandButton, RibbonElementPriority.TOP);
        workspaceBand.addCommandButton(exportCommandButton, RibbonElementPriority.TOP);
        workspaceBand.addCommandButton(varNewCommandButton, RibbonElementPriority.MEDIUM);
        workspaceBand.addCommandButton(varRemoveCommandButton, RibbonElementPriority.MEDIUM);      
        workspaceBand.addCommandButton(refreshCommandButton, RibbonElementPriority.MEDIUM);
        
        setPolicy(workspaceBand);
        return workspaceBand;
    }

    private JRibbonBand createHistoryBand(){
        historyBand = new JRibbonBand("History",null);
        
        saveHistoryCommandButton = new JCommandButton(bundle.getString("saveHistoryCommandButton.text"),getResizableIcoFromResource24("resources/icons/size32/save-history.png"));
        clearHistoryCommandButton = new JCommandButton(bundle.getString("clearHistoryCommandButton.text"),getResizableIcoFromResource24("resources/icons/size32/clear-history.png"));
        runInCommandButton = new JCommandButton(bundle.getString("runInCommandButton.text"),getResizableIcoFromResource24("resources/icons/size32/terminal.png"));
         
        historyBand.addCommandButton(saveHistoryCommandButton, RibbonElementPriority.MEDIUM);
        historyBand.addCommandButton(clearHistoryCommandButton, RibbonElementPriority.MEDIUM);
        historyBand.addCommandButton(runInCommandButton, RibbonElementPriority.MEDIUM);

        
        setPolicy(historyBand);
        return historyBand;
    }
    
    private void setPolicy(JRibbonBand band) {
        List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(band
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(band
				.getControlPanel()));
		band.setResizePolicies(resizePolicies);
    }
    
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
			UIManager.setLookAndFeel(new org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel());
		} catch (Exception exc) {
		}

       SwingUtilities.invokeLater( new Runnable() {
           public void run() {
               MainRibbonFrame c = new MainRibbonFrame();
                                c.setSize(600, 600);
                                c.setLocationRelativeTo(null);
				c.setVisible(true);
				c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              
           }
       });
    }

    private RibbonApplicationMenuEntryPrimary createEntryNew() {
         RibbonApplicationMenuEntryPrimary entryNew = new RibbonApplicationMenuEntryPrimary(
				 getResizableIcoFromResource48("resources/icons/size48/document-new.png"),
				bundle.getString("appMenuNew.text"),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						entryNewActionPerformed(e);
					}
           
				}, JCommandButton.CommandButtonKind.ACTION_ONLY);
		entryNew.setActionKeyTip("N");
      return entryNew;
    }
    private void entryNewActionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(rootPane, "New");
    }
    
    private RibbonApplicationMenuEntryPrimary createEntryOpen() {
         RibbonApplicationMenuEntryPrimary entryOpen = new RibbonApplicationMenuEntryPrimary(
				 getResizableIcoFromResource48("resources/icons/size48/document-open.png"),
				bundle.getString("appMenuOpen.text"),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						entryOpenActionPerformed(e);
					}
           
				}, JCommandButton.CommandButtonKind.ACTION_ONLY);
		entryOpen.setActionKeyTip("O");
      return entryOpen;
    }
    private void entryOpenActionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(rootPane, "Open");
    }
    
    private RibbonApplicationMenuEntryPrimary createEntrySave() {
         RibbonApplicationMenuEntryPrimary entrySave = new RibbonApplicationMenuEntryPrimary(
				 getResizableIcoFromResource48("resources/icons/size48/document-save.png"),
				bundle.getString("appMenuSave.text"),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						entrySaveActionPerformed(e);
					}
           
				}, JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		entrySave.setActionKeyTip("S");
      return entrySave;
    }
    private void entrySaveActionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(rootPane, "Save");
    }
    
    private RibbonApplicationMenuEntryPrimary createEntryAnalyze() {
         RibbonApplicationMenuEntryPrimary entryAnalyze = new RibbonApplicationMenuEntryPrimary(
				 getResizableIcoFromResource48("resources/icons/size48/analyze.png"),
				bundle.getString("appMenuAnalyze.text"),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						entryAnalyzeActionPerformed(e);
					}
           
				}, JCommandButton.CommandButtonKind.ACTION_ONLY);
		entryAnalyze.setActionKeyTip("L");
      return entryAnalyze;
    }
    private void entryAnalyzeActionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(rootPane, "Analyze");
    }
    private RibbonApplicationMenuEntryPrimary createEntryPrint() {
         RibbonApplicationMenuEntryPrimary entryPrint = new RibbonApplicationMenuEntryPrimary(
				 getResizableIcoFromResource48("resources/icons/size48/document-print.png"),
				bundle.getString("appMenuPrint.text"),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						entryPrintActionPerformed(e);
					}
           
				}, JCommandButton.CommandButtonKind.ACTION_ONLY);
		entryPrint.setActionKeyTip("P");
      return entryPrint;
    }
    private void entryPrintActionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(rootPane, "Print");
    }
      
    private RibbonApplicationMenuEntryPrimary createEntryClose() {
         RibbonApplicationMenuEntryPrimary entryClose = new RibbonApplicationMenuEntryPrimary(
				 getResizableIcoFromResource48("resources/icons/size48/document-close.png"),
				bundle.getString("appMenuClose.text"),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						entryCloseActionPerformed(e);
					}
           
				}, JCommandButton.CommandButtonKind.ACTION_ONLY);
		entryClose.setActionKeyTip("L");
      return entryClose;
    }
    private void entryCloseActionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(rootPane, "Close");
    }
    
    
    private RibbonApplicationMenuEntryFooter createFootEntryPreferences() {
        RibbonApplicationMenuEntryFooter footerEntryPreferences = new RibbonApplicationMenuEntryFooter(
                                getResizableIcoFromResource32("resources/icons/size32/configure.png")
				, bundle.getString("appMenuPreferences.text"),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						footerEntryPreferencesActionPerformed(e);
					}

            
				});
        return footerEntryPreferences ;
    }
    
    private void footerEntryPreferencesActionPerformed(ActionEvent e) {
               JOptionPane.showMessageDialog(rootPane, "Preferences"); 
      }
    private RibbonApplicationMenuEntryFooter createFootEntryExit() {
        RibbonApplicationMenuEntryFooter footerEntryExit = new RibbonApplicationMenuEntryFooter(
                                getResizableIcoFromResource32("resources/icons/size32/application-exit.png")
				, bundle.getString("appMenuExit.text"),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						footerEntryExitActionPerformed(e);
					}

            
				});
        return footerEntryExit ;
    }
    
    private void footerEntryExitActionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPane, "Exit");
                 
     }
    private void createTaskBar(){
        saveAllTaskBarButton = new JCommandButton(null,getResizableIcoFromResource24("resources/icons/size24/document-save-all.png"));
        this.getRibbon().addTaskbarComponent(this.saveAllTaskBarButton);
        undoTaskBarButton = new JCommandButton(null,getResizableIcoFromResource24("resources/icons/size24/edit-undo.png"));
        this.getRibbon().addTaskbarComponent(this.undoTaskBarButton);
        redoTaskBarButton = new JCommandButton(null,getResizableIcoFromResource24("resources/icons/size24/edit-redo.png"));
        this.getRibbon().addTaskbarComponent(this.redoTaskBarButton);
  
    }
    private void initComponents() {
       ribbonApplicationMenu = new RibbonApplicationMenu();
        ribbonApplicationMenu.addMenuEntry(this.createEntryNew());
        ribbonApplicationMenu.addMenuEntry(this.createEntryOpen()); 
        ribbonApplicationMenu.addMenuEntry(this.createEntrySave()); 
        ribbonApplicationMenu.addMenuEntry(this.createEntryAnalyze()); 
        ribbonApplicationMenu.addMenuSeparator();
        ribbonApplicationMenu.addMenuEntry(this.createEntryPrint()); 
        ribbonApplicationMenu.addMenuSeparator();
        ribbonApplicationMenu.addMenuEntry(this.createEntryClose()); 
        
        ribbonApplicationMenu.addFooterEntry(this.createFootEntryPreferences());
	ribbonApplicationMenu.addFooterEntry(this.createFootEntryExit());
        
        this.getRibbon().setApplicationMenu(ribbonApplicationMenu);
        createTaskBar();
       
    }
    

    private  ResizableIcon getResizableIcoFromResource24(String resource) {
        return  ImageWrapperResizableIcon.getIcon(getClass().getResource(resource),new Dimension(24,24));
    }
    private  ResizableIcon getResizableIcoFromResource32(String resource) {
        return  ImageWrapperResizableIcon.getIcon(getClass().getResource(resource),new Dimension(32,32));
    }
     private  ResizableIcon getResizableIcoFromResource48(String resource) {
        return  ImageWrapperResizableIcon.getIcon(getClass().getResource(resource),new Dimension(48,48));
    }
   
    
}
