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
package com.artenum.rosetta.interfaces.core;

import javax.swing.text.StyledDocument;

import com.artenum.rosetta.interfaces.ui.CompletionWindow;
import com.artenum.rosetta.interfaces.ui.InputCommandView;
import com.artenum.rosetta.interfaces.ui.OutputView;
import com.artenum.rosetta.interfaces.ui.PromptView;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */

public interface ConsoleConfiguration extends Configuration {
	// UI components
	/**
	 * @return
	 */
	PromptView getPromptView();
	
	/**
	 * @return
	 */
	OutputView getOutputView();
	
	/**
	 * @return
	 */
	InputCommandView getInputCommandView();
	

	// Core components
	/**
	 * @return
	 */
	StyledDocument getOutputViewStyledDocument();
	
	/**
	 * @return
	 */
	StyledDocument getInputCommandViewStyledDocument();
	
	/**
	 * @return
	 */
	InputParsingManager getInputParsingManager();
	
	/**
	 * Returns the current Generic Interpreter
	 * @return the Generic Interpreter
	 */
	GenericInterpreter getGenericInterpreter();
	
	/**
	 * Returns the current completion manager 
	 * @return the completion manager
	 */
	CompletionManager getCompletionManager();
	
	/**
	 * @return
	 */
	CompletionWindow getCompletionWindow();

	/**
	 * @return
	 */
	HistoryManager getHistoryManager();
	
	// Property getter
	/**
	 * @return
	 */
	String getBackgroundColor();
	
	/**
	 * @return
	 */
	String getForegroundColor();
	
	/**
	 * @return
	 */
	int getScrollableUnitIncrement();
	
	/**
	 * @return
	 */
	int getScrollableBlockIncrement();
	
	/**
	 * @return
	 */
	boolean getHorizontalWrapAllowed();
	
	/**
	 * @return
	 */
	boolean getVerticalWrapAllowed();
	
	/**
	 * @return
	 */
	String getFontName();
	
	/**
	 * @return
	 */
	int getFontStyle();
	
	/**
	 * @return
	 */
	int getFontSize();

	/**
	 * @return
	 */
	String getWelcomeLine();
}
