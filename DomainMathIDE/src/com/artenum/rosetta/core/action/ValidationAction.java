/*
 * (c) Copyright: Artenum SARL, 24 rue Louis Blanc,
 *                75010, Paris, France 2007-2009.
 *                http://www.artenum.com
 *
 * License:
 *
 *  This program is free software; you can redistribute it
 *  and/or modify it under the terms of the license defined in the
 *  LICENSE.TXT file at the root of the present package.
 *
 *  This program is distributed in the hope that it will be
 *  useful, but WITHOUT ANY WARRANTY; without even the implied
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the LICENSE.TXT for more details.
 *
 *  You should have received a copy of the License along with 
 *  this program; if not, write to:
 *    Artenum SARL, 24 rue Louis Blanc,
 *    75010, PARIS, FRANCE, e-mail: contact@artenum.com
 */ 
package com.artenum.rosetta.core.action;

import java.awt.event.ActionEvent;

import  com.artenum.rosetta.exception.ScriptException;

import com.artenum.rosetta.interfaces.core.InputParsingManager;
import com.artenum.rosetta.interfaces.ui.OutputView;
import com.artenum.rosetta.interfaces.ui.PromptView;
import com.artenum.rosetta.util.StringConstants;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.octave.OctavePanel;


/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public class ValidationAction extends AbstractConsoleAction {
	private static final long serialVersionUID = 1L;
    private  int i;

    public ValidationAction() {
        i=0;
    }

	/* 
	 * Execute a command received
	 */
        
        private void dispHistory(String text) {
             i++;

            DateFormat formatter = 
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                                   DateFormat.MEDIUM,
                                   Locale.getDefault());
        
    
        String t="# -- "+formatter.format(new Date())+" -- #";
        
        
        if((i%10) == 0) {
            MainFrame.histArea.append(t+"\n");
        }
        MainFrame.histArea.append(text+"\n");
      
        
             
        }
        
        private void reloadWorkspace(String c) {
            if(!c.contains("input") ||MainFrame.automaticRefreshCheckBoxMenuItem.isSelected()) {
         MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+MainFrame.parent_root+"DomainMath_OctaveVariables.dat',whos);");
         MainFrame.varView.reload();
         MainFrame.varView.reload();
        }
        }
	public void actionPerformed(ActionEvent e) {
		// Init
		InputParsingManager inputParsingManager = configuration.getInputParsingManager();
		OutputView outputView = configuration.getOutputView();
		PromptView promptView = configuration.getPromptView();
		String cmdToExecute = null;
		outputView.setCaretPositionToEnd();

		// Do the job
		if (inputParsingManager.isBlockEditing()) {
			// Create new line
			inputParsingManager.append(StringConstants.NEW_LINE);
			promptView.updatePrompt();
		} else {
			// reset command line
			cmdToExecute = inputParsingManager.getCommandLine();
			inputParsingManager.reset();
			promptView.updatePrompt();
			
			//Reset history settings
			configuration.getHistoryManager().setTmpEntry(null);
			configuration.getHistoryManager().setInHistory(false);
			
			// Hide the bottom line ?


			// Print the command in the output view
			boolean firstPrompt = true;
			for (String line : cmdToExecute.split(StringConstants.NEW_LINE)) {
				
				outputView.append(StringConstants.NEW_LINE);
				if (firstPrompt) {
					firstPrompt = false;
					outputView.append(promptView.getDefaultPrompt());
				} else {
					outputView.append(promptView.getInBlockPrompt());
				}
				outputView.append(line);
			}
			outputView.append(StringConstants.NEW_LINE);
			// Ask the engine to process the cmd
			//try {
				//configuration.getGenericInterpreter().eval(cmdToExecute);
//			} catch (ScriptException e1) {
//				e1.printStackTrace();
//			}
			OctavePanel.exh.println(cmdToExecute);
                        dispHistory(cmdToExecute);
                        reloadWorkspace(cmdToExecute);
                        
			// To be sure to see the prompt when entering empty lines 
			// If these lines removed, the prompt can be hidden (under the bottom of the console)
			if (cmdToExecute.length()==0) {
				configuration.getInputCommandView().setText(" ");
				configuration.getInputCommandView().backspace();
			}
			
			// Show the command line ?
		}

	}
}
