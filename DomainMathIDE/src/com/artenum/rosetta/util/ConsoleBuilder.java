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
package com.artenum.rosetta.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.artenum.rosetta.interfaces.core.CompletionManager;
import com.artenum.rosetta.interfaces.core.ConsoleConfiguration;
import com.artenum.rosetta.interfaces.core.GenericInterpreter;
import com.artenum.rosetta.interfaces.core.InputParsingManager;
import com.artenum.rosetta.interfaces.ui.CompletionWindow;
import com.artenum.rosetta.interfaces.ui.InputCommandView;
import com.artenum.rosetta.interfaces.ui.OutputView;
import com.artenum.rosetta.interfaces.ui.PromptView;
import com.artenum.rosetta.ui.ConsoleTest;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 *
 */
public class ConsoleBuilder {
	/**
	 * @param configuration
	 * @param parentComponent
	 * @return
	 */
	public static ConsoleTest buildConsole(ConsoleConfiguration configuration, Component parentComponent) {
		ConsoleTest console = new ConsoleTest();
		// Get variables
		PromptView promptView = configuration.getPromptView();
		OutputView outputView = configuration.getOutputView();
		InputCommandView inputCommandView = configuration.getInputCommandView();
		InputParsingManager inputParsingManager = configuration.getInputParsingManager();
		GenericInterpreter interpreter = configuration.getGenericInterpreter();
		CompletionManager completionManager = configuration.getCompletionManager();
		CompletionWindow completionWindow = configuration.getCompletionWindow();

		// set UI components
		console.setPromptView(promptView);
		console.setOutputView(outputView);
		console.setInputCommandView(inputCommandView);
		
		// link components
		outputView.setStyledDocument(configuration.getOutputViewStyledDocument());
		inputCommandView.setStyledDocument(configuration.getInputCommandViewStyledDocument());
		inputParsingManager.setInputCommandView(inputCommandView);
		promptView.setInputParsingManager(inputParsingManager);
		outputView.setEditable(false);
		outputView.append(configuration.getWelcomeLine());
		outputView.setCaretPositionToEnd();
		
		// Scrolling policy
		console.setScrollableBlockIncrement(configuration.getScrollableBlockIncrement());
		console.setScrollableUnitIncrement(configuration.getScrollableUnitIncrement());
		console.setVerticalWrapAllowed(configuration.getVerticalWrapAllowed());
		console.setHorizontalWrapAllowed(configuration.getHorizontalWrapAllowed());

		// Fonts
		try {
			console.setFont(new Font(configuration.getFontName(), configuration.getFontStyle(), configuration.getFontSize()));
		} catch (Exception e) {
			System.out.println("Could not read font settings, switching to defaults.");
		}
		
		interpreter.setErrorWriter(outputView.getErrorWriter());
		interpreter.setWriter(outputView.getWriter());

		// Should be put in config
		//System.setOut(new PrintStream(new OutputStreamAdapter(outputView.getWriter())));
		//System.setErr(new PrintStream(new OutputStreamAdapter(outputView.getErrorWriter())));

		// Add completion support
		if (completionManager != null) {
			completionManager.setInputParsingManager(inputParsingManager);
			completionManager.setInterpretor(interpreter);
			completionWindow.setGraphicalContext(parentComponent);
			completionWindow.setInputParsingManager(inputParsingManager);
			completionWindow.setFocusOut((JComponent)inputCommandView);
		}

		console.setBackground(Color.decode(configuration.getBackgroundColor()));
		console.setForeground(Color.decode(configuration.getForegroundColor()));

		// Refresh UI
		promptView.updatePrompt();
		// Key binding
		overideInputMap(((JComponent) inputCommandView).getInputMap(), configuration.getKeyMapping());
		overideActionMap(((JComponent) inputCommandView).getActionMap(), configuration.getActionMapping());

		// Return builded console
		return console;
	}

	/**
	 * @param sourceMap
	 * @param newMap
	 */
	private static void overideInputMap(InputMap sourceMap, InputMap newMap) {
		for (KeyStroke key : newMap.allKeys()) {
			sourceMap.put(key, newMap.get(key));
		}
	}

	/**
	 * @param sourceMap
	 * @param newMap
	 */
	private static void overideActionMap(ActionMap sourceMap, ActionMap newMap) {
		for (Object key : newMap.allKeys()) {
			sourceMap.put(key, newMap.get(key));
		}
	}
}
