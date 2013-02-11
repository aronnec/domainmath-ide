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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.domainmath.gui.resources.icons.DomainMathIDE_SVG_ICON;
import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.RichTooltip;
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

/**
 * Creates a frame with Flemingo Ribbon Control
 * <code>MainRibbonFrame</code>
 * @author Vinu K.N
 */
public class MainRibbonFrame extends JRibbonFrame {
    
    private ResourceBundle bundle = ResourceBundle.getBundle("org/domainmath/gui/resources/DomainMathIDE_en");
    
    private RibbonApplicationMenu ribbonApplicationMenu;

    // TaskBar buttons.
    private JCommandButton undoTaskBarButton;
    private JCommandButton redoTaskBarButton;
    private JCommandButton saveAllTaskBarButton;
    
    // RibbonTask.
    private RibbonTask homeRibbonTask;
    private RibbonTask dataRibbonTask;
    private RibbonTask toolsRibbonTask;
    
    //RibbonBand.
    private JRibbonBand workspaceBand;
    private JRibbonBand historyBand;
    private JRibbonBand clipboardBand;
    private JRibbonBand editBand;
    
    // JCommandButtons for workspaceBand.
    private JCommandButton varNewCommandButton;
    private JCommandButton varRemoveCommandButton;
    private JCommandButton refreshCommandButton;
    
    // JCommandButtons for historyBand.
    private JCommandButton saveHistoryCommandButton;
    private JCommandButton clearHistoryCommandButton;
    private JCommandButton runInCommandButton;
    
    // JCommandButtons for clipBand.
    private JCommandButton cutCommandButton;
    private JCommandButton copyCommandButton;
    private JCommandButton pasteCommandButton;
    private JCommandButton selectAllCommandButton;
    
    /**
     * Constructs a frame with Flemingo Ribbon control
     * @see org.pushingpixels.flamingo.api.ribbon.JRibbon
     */
    public MainRibbonFrame()  {
        this.setApplicationIcon(new DomainMathIDE_SVG_ICON());
        this.setTitle(bundle.getString("DomainMathIDE.title"));
        initComponents();
        homeRibbonTask = new RibbonTask(bundle.getString("homeRibbonTask.title"), createClipboardBand()); 
        dataRibbonTask = new RibbonTask(bundle.getString("dataRibbonTask.title"), createWorkspaceBand());
        toolsRibbonTask = new RibbonTask(bundle.getString("toolsRibbonTask.title"), createHistoryBand());
        
        getRibbon().addTask(homeRibbonTask);
        getRibbon().addTask(dataRibbonTask);
        getRibbon().addTask(toolsRibbonTask);
    }

    /**
     * Handles workspace related operations like creating new variable,removing variable in Octave workspace.
     * @see org.pushingpixels.flamingo.api.ribbon.JRibbonBand
     * @return JRibbonBand
     */
    private JRibbonBand createWorkspaceBand(){
        workspaceBand = new JRibbonBand(bundle.getString("workspaceBand.title"),null);
        
        varNewCommandButton = new JCommandButton(bundle.getString("varNewCommandButton.text"),getResizableIcoFromResource32("resources/icons/size32/var-new.png"));
        varRemoveCommandButton = new JCommandButton(bundle.getString("varRemoveCommandButton.text"),getResizableIcoFromResource32("resources/icons/size32/var-remove.png"));
        refreshCommandButton = new JCommandButton(bundle.getString("refreshCommandButton.text"),getResizableIcoFromResource32("resources/icons/size32/refresh.png"));
        
        varNewCommandButton.setActionRichTooltip(new RichTooltip(bundle.getString("varNewCommandButton.tooltipHead"),bundle.getString("varNewCommandButton.tooltip")));
        varRemoveCommandButton.setActionRichTooltip(new RichTooltip(bundle.getString("varRemoveCommandButton.tooltipHead"),bundle.getString("varRemoveCommandButton.tooltip")));
        refreshCommandButton.setActionRichTooltip(new RichTooltip(bundle.getString("refreshCommandButton.tooltipHead"),bundle.getString("refreshCommandButton.tooltip")));
       
        workspaceBand.addCommandButton(varNewCommandButton, RibbonElementPriority.MEDIUM);
        workspaceBand.addCommandButton(varRemoveCommandButton, RibbonElementPriority.MEDIUM);      
        workspaceBand.addCommandButton(refreshCommandButton, RibbonElementPriority.MEDIUM);
        
        setPolicy(workspaceBand);
        return workspaceBand;
    }

     /**
     * Handles clipboard operations like cut,copy and paste.
     * @see org.pushingpixels.flamingo.api.ribbon.JRibbonBand
     * @return JRibbonBand
     */
    private JRibbonBand createClipboardBand(){
        clipboardBand = new JRibbonBand(bundle.getString("clipboardBand.title"),null);
        
        cutCommandButton = new JCommandButton(bundle.getString("cutCommandButton.text"),getResizableIcoFromResource32("resources/icons/size32/edit-cut.png"));
        copyCommandButton = new JCommandButton(bundle.getString("copyCommandButton.text"),getResizableIcoFromResource32("resources/icons/size32/edit-copy.png"));
        pasteCommandButton = new JCommandButton(bundle.getString("pasteCommandButton.text"),getResizableIcoFromResource48("resources/icons/size48/edit-paste.png"));
        selectAllCommandButton = new JCommandButton(bundle.getString("selectAllCommandButton.text"),getResizableIcoFromResource48("resources/icons/size32/edit-select-all.png"));
       
        cutCommandButton.setActionRichTooltip(new RichTooltip(bundle.getString("cutCommandButton.tooltipHead"),bundle.getString("cutCommandButton.tooltip")));
        copyCommandButton.setActionRichTooltip(new RichTooltip(bundle.getString("copyCommandButton.tooltipHead"),bundle.getString("copyCommandButton.tooltip")));
        pasteCommandButton.setActionRichTooltip(new RichTooltip(bundle.getString("pasteCommandButton.tooltipHead"),bundle.getString("pasteCommandButton.tooltip")));
        selectAllCommandButton.setActionRichTooltip(new RichTooltip(bundle.getString("selectAllCommandButton.tooltipHead"),bundle.getString("selectAllCommandButton.tooltip")));
       
        clipboardBand.addCommandButton(pasteCommandButton, RibbonElementPriority.TOP);
        clipboardBand.addCommandButton(cutCommandButton, RibbonElementPriority.MEDIUM);
        clipboardBand.addCommandButton(copyCommandButton, RibbonElementPriority.MEDIUM);
        clipboardBand.addCommandButton(selectAllCommandButton, RibbonElementPriority.MEDIUM);
        
        setPolicy(clipboardBand);
        return clipboardBand;
    }
    
     /**
     * Handles edit operations like comment,increase indent,decrease indent.
     * @see org.pushingpixels.flamingo.api.ribbon.JRibbonBand
     * @return JRibbonBand
     */
    private JRibbonBand createEditBand(){
        editBand = new JRibbonBand(bundle.getString("edit.title"),null);
        
        cutCommandButton = new JCommandButton(bundle.getString("cutCommandButton.text"),getResizableIcoFromResource32("resources/icons/size32/edit-cut.png"));
        copyCommandButton = new JCommandButton(bundle.getString("copyCommandButton.text"),getResizableIcoFromResource32("resources/icons/size32/edit-copy.png"));
        pasteCommandButton = new JCommandButton(bundle.getString("pasteCommandButton.text"),getResizableIcoFromResource48("resources/icons/size48/edit-paste.png"));
        
        cutCommandButton.setActionRichTooltip(new RichTooltip(bundle.getString("cutCommandButton.tooltipHead"),bundle.getString("cutCommandButton.tooltip")));
        copyCommandButton.setActionRichTooltip(new RichTooltip(bundle.getString("copyCommandButton.tooltipHead"),bundle.getString("copyCommandButton.tooltip")));
        pasteCommandButton.setActionRichTooltip(new RichTooltip(bundle.getString("pasteCommandButton.tooltipHead"),bundle.getString("pasteCommandButton.tooltip")));
        
        clipboardBand.addCommandButton(pasteCommandButton, RibbonElementPriority.TOP);
        clipboardBand.addCommandButton(cutCommandButton, RibbonElementPriority.MEDIUM);
        clipboardBand.addCommandButton(copyCommandButton, RibbonElementPriority.MEDIUM);
      
        
        setPolicy(clipboardBand);
        return clipboardBand;
    }
     /**
     * Handles history related functions.
     * @see org.pushingpixels.flamingo.api.ribbon.JRibbonBand
     * @return JRibbonBand
     */
    private JRibbonBand createHistoryBand(){
        historyBand = new JRibbonBand(bundle.getString("historyBand.title"),null);
        
        saveHistoryCommandButton = new JCommandButton(bundle.getString("saveHistoryCommandButton.text"),getResizableIcoFromResource24("resources/icons/size32/save-history.png"));
        clearHistoryCommandButton = new JCommandButton(bundle.getString("clearHistoryCommandButton.text"),getResizableIcoFromResource24("resources/icons/size32/clear-history.png"));
        runInCommandButton = new JCommandButton(bundle.getString("runInCommandButton.text"),getResizableIcoFromResource24("resources/icons/size32/terminal.png"));
         
        historyBand.addCommandButton(saveHistoryCommandButton, RibbonElementPriority.MEDIUM);
        historyBand.addCommandButton(clearHistoryCommandButton, RibbonElementPriority.MEDIUM);
        historyBand.addCommandButton(runInCommandButton, RibbonElementPriority.MEDIUM);

        
        setPolicy(historyBand);
        return historyBand;
    }
    
    /**
     * Set Ribbon band policy.
     * @see org.pushingpixels.flamingo.api.ribbon.resize.BaseRibbonBandResizePolicy
     * @param band 
     */
    private void setPolicy(JRibbonBand band) {
        List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(band
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(band
				.getControlPanel()));
		band.setResizePolicies(resizePolicies);
    }
    
    /**
     * Main entry point to the application.
     * @param args 
     */
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

    /**
     * Creates RibbonApplicationMenuEntry in the Application Menu.
     * It shows new file dialog box.
     * @see org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary
     * @return RibbonApplicationMenuEntryPrimary
     */
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
    
    /**
     * Handle actions of RibbonApplicationMenuEntryPrimary entryNew
     * @param e 
     */
    private void entryNewActionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(rootPane, "New");
    }
    
    /**
     * Creates RibbonApplicationMenuEntry in the Application Menu.
     * It open file(s).
     * @see org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary
     * @return RibbonApplicationMenuEntryPrimary
     */
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
    
    /**
     * Handle actions of RibbonApplicationMenuEntryPrimary entryOpen
     * @param e 
     */
    private void entryOpenActionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(rootPane, "Open");
    }
    
    /**
     * Creates RibbonApplicationMenuEntry in the Application Menu.
     * It save file(s).
     * @see org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary
     * @return RibbonApplicationMenuEntryPrimary
     */
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
    
    /**
     * Handle actions of RibbonApplicationMenuEntryPrimary entrySave
     * @param e 
     */
    private void entrySaveActionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(rootPane, "Save");
    }
    
    /**
     * Creates RibbonApplicationMenuEntry in the Application Menu.
     * It analyze or profile Octave script.
     * @see org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary
     * @return RibbonApplicationMenuEntryPrimary
     */
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
    
    /**
     * Handle actions of RibbonApplicationMenuEntryPrimary entryAnalyze
     * @param e 
     */
    private void entryAnalyzeActionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(rootPane, "Analyze");
    }
    
    /**
     * Creates RibbonApplicationMenuEntry in the Application Menu.
     * It print  file.
     * @see org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary
     * @return RibbonApplicationMenuEntryPrimary
     */
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
    
    /**
     * Handle actions of RibbonApplicationMenuEntryPrimary entryPrint
     * @param e 
     */
    private void entryPrintActionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(rootPane, "Print");
    }
      
    /**
     * Creates RibbonApplicationMenuEntry in the Application Menu.
     * It closes a file.
     * @see org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary
     * @return RibbonApplicationMenuEntryPrimary
     */
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
    
    /**
     * Handle actions of RibbonApplicationMenuEntryPrimary entryClose
     * @param e 
     */
    private void entryCloseActionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(rootPane, "Close");
    }
    
     /**
     * Creates RibbonApplicationMenuEntryFooter in the Application Menu.
     * It shows Preferences dialog box.
     * @see org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryFooter
     * @return RibbonApplicationMenuEntryFooter
     */
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
    
    /**
     * Handle actions of RibbonApplicationMenuEntryFooter footerEntryPreferences
     * @param e 
     */
    private void footerEntryPreferencesActionPerformed(ActionEvent e) {
               JOptionPane.showMessageDialog(rootPane, "Preferences"); 
      }
    
    /**
     * Creates RibbonApplicationMenuEntryFooter in the Application Menu.
     * It closes the application.
     * @see org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryFooter
     * @return RibbonApplicationMenuEntryFooter
     */
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
    
    /**
     * Handle actions of RibbonApplicationMenuEntryFooter footerEntryExit
     * @param e 
     */
    private void footerEntryExitActionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPane, "Exit");
                 
     }
    
    /**
     * Creates Task bar. It contains Save all,undo and  redo buttons
     * @see org.pushingpixels.flamingo.api.ribbon.JRibbon
     */
    private void createTaskBar(){
        saveAllTaskBarButton = new JCommandButton(null,getResizableIcoFromResource24("resources/icons/size24/document-save-all.png"));
        this.getRibbon().addTaskbarComponent(this.saveAllTaskBarButton);
        undoTaskBarButton = new JCommandButton(null,getResizableIcoFromResource24("resources/icons/size24/edit-undo.png"));
        this.getRibbon().addTaskbarComponent(this.undoTaskBarButton);
        redoTaskBarButton = new JCommandButton(null,getResizableIcoFromResource24("resources/icons/size24/edit-redo.png"));
        this.getRibbon().addTaskbarComponent(this.redoTaskBarButton);    
    }
    
    /**
     * Initiate all components.
     */
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
    
    /**
     * Create ResizableIcon 24x24 from Resource.
     * @see org.pushingpixels.flamingo.api.common.icon.ResizableIcon
     * @param resource
     * @return ResizableIcon
     */
    private  ResizableIcon getResizableIcoFromResource24(String resource) {
        return  ImageWrapperResizableIcon.getIcon(getClass().getResource(resource),new Dimension(24,24));
    }
    
    /**
     * Create ResizableIcon 32x32 from Resource.
     * @see org.pushingpixels.flamingo.api.common.icon.ResizableIcon
     * @param resource
     * @return ResizableIcon
     */
    private  ResizableIcon getResizableIcoFromResource32(String resource) {
        return  ImageWrapperResizableIcon.getIcon(getClass().getResource(resource),new Dimension(32,32));
    }
    
    /**
     * Create ResizableIcon 48x48 from Resource.
     * @see org.pushingpixels.flamingo.api.common.icon.ResizableIcon
     * @param resource
     * @return ResizableIcon
     */
     private  ResizableIcon getResizableIcoFromResource48(String resource) {
        return  ImageWrapperResizableIcon.getIcon(getClass().getResource(resource),new Dimension(48,48));
    }
   
    
}
