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
package com.artenum.rosetta.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.Scrollable;

import com.artenum.rosetta.interfaces.core.ConsoleConfiguration;
import com.artenum.rosetta.interfaces.ui.GuiComponent;
import com.artenum.rosetta.interfaces.ui.InputCommandView;
import com.artenum.rosetta.interfaces.ui.OutputView;
import com.artenum.rosetta.interfaces.ui.PromptView;
import com.artenum.rosetta.util.ConfigurationBuilder;
import com.artenum.rosetta.util.ConsoleBuilder;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public class ConsoleTest extends JPanel implements GuiComponent, Scrollable {
	private static final long serialVersionUID = 1L;
	private CommandLineViewImpl commandLine;
	private ArrayList<GuiComponent> guiComponent;
	private int scrollableBlockIncrement = 10;
	private int scrollableUnitIncrement = 10;
	private boolean horizontalWrapAllowed = true;
	private boolean verticalWrapAllowed = false;

	/**
	 * Creates the console
	 */
	public ConsoleTest() {
		setLayout(new BorderLayout());
		commandLine = new CommandLineViewImpl();
		add(commandLine, BorderLayout.CENTER);
		guiComponent = new ArrayList<GuiComponent>();
		setOpaque(true);
	}

	/**
	 * Sets the component where all outputs are displayed
	 * @param output the component
	 */
	public void setOutputView(OutputView output) {
		add((JComponent) output, BorderLayout.NORTH);
		guiComponent.add(output);
	}

	/**
	 * Sets the component where the user enters its commands
	 * @param command the component
	 */
	public void setInputCommandView(InputCommandView command) {
		commandLine.setInputCommandView((JComponent) command);
		guiComponent.add(command);
	}

	/**
	 * Sets the component where the prompt is printed
	 * @param prompt the component
	 */
	public void setPromptView(PromptView prompt) {
		commandLine.setPromptView((JComponent) prompt);
		guiComponent.add(prompt);
	}

	/**
	 * Sets the background of all components in the console
	 * @param color the color to set
	 * @see javax.swing.JComponent#setBackground(java.awt.Color)
	 */
	public void setBackground(Color color) {
		super.setBackground(color);
		if (guiComponent != null) {
			for (GuiComponent component : guiComponent) {
				component.setBackground(color);
			}
		}
	}

	/**
	 * Sets the foreground of all components in the console
	 * @param color the color to set
	 * @see javax.swing.JComponent#setForeground(java.awt.Color)
	 */
	public void setForeground(Color color) {
		super.setForeground(color);
		if (guiComponent != null) {
			for (GuiComponent component : guiComponent) {
				component.setForeground(color);
			}
		}
	}

	/**
	 * Sets the font of all components in the console
	 * @param font the font to set
	 * @see javax.swing.JComponent#setFont(java.awt.Font)
	 */
	public void setFont(Font font) {
		super.setFont(font);
		if (guiComponent != null) {
			for (GuiComponent component : guiComponent) {
				component.setFont(font);
			}
		}
	}

	/**
	 * Gets the preferred size of the console taking into account the scrolling policy
	 * @return the dimensions of the console
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		Dimension minSize = ((JViewport) getParent()).getExtentSize();
		Dimension prefSize = super.getPreferredSize();
		Dimension returnedSize = new Dimension(minSize);
		
		// Vertical wrapping not accepted
		if (!getScrollableTracksViewportHeight() && prefSize.height > minSize.height) {
			returnedSize.height = prefSize.height;
		}
		// Horizontal wrapping not accepted
		if (!getScrollableTracksViewportWidth() && prefSize.width > minSize.width) {
			returnedSize.width = prefSize.width;
		}
		
		return returnedSize;
	}

	/**
	 * Gets the size of the viewport used for scrolling policy
	 * @return the dimensions of the viewport
	 * @see javax.swing.Scrollable#getPreferredScrollableViewportSize()
	 */
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	/**
	 * Gets the scrolling unit increment value
	 * @see javax.swing.Scrollable#getScrollableUnitIncrement(java.awt.Rectangle, int, int)
	 */
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		return scrollableUnitIncrement;
	}

	/**
	 * Gets the scrolling block increment value
	 * @see javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle, int, int)
	 */
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		return scrollableBlockIncrement;
	}

	/**
	 * Sets the scrolling unit increment value
	 * @param increment the increment value
	 */
	public void setScrollableUnitIncrement(int increment) {
		scrollableUnitIncrement = increment;
	}

	/**
	 * Sets the scrolling block increment value
	 * @param increment the increment value
	 */
	public void setScrollableBlockIncrement(int increment) {
		scrollableBlockIncrement = increment;
	}

	/**
	 * Gets the property telling if the viewport can have an horizontal scrollbar
	 * @return false if the viewport can have an horizontal scrollbar (true if it can)
	 * @see javax.swing.Scrollable#getScrollableTracksViewportWidth()
	 */
	public boolean getScrollableTracksViewportWidth() {
                if (!horizontalWrapAllowed) {
                        boolean b = true;
		        for (GuiComponent c : guiComponent) {
		                b = b && ((JComponent) c).getPreferredSize().width < ((JViewport) getParent()).getExtentSize().width;
                        }
                        return b;
                }
                return true;
	}

	/**
	 * Gets the property telling if the viewport can have a vertical scrollbar
	 * @return false if the viewport can have a vertical scrollbar (true if it can)
	 * @see javax.swing.Scrollable#getScrollableTracksViewportHeight()
	 */
        public boolean getScrollableTracksViewportHeight() {
	        return verticalWrapAllowed;
	}

	/**
	 * Sets the property telling if the console enables vertical word wrapping
	 * @param wrapMode true if words can be wrapped verticaly (false if a scrollbar has to be used instead)
	 */
	public void setVerticalWrapAllowed(boolean wrapMode) {
	        verticalWrapAllowed = wrapMode;
	}

	/**
	 * Gets the property telling if the console enables vertical word wrapping
	 * @return true if words can be wrapped verticaly (false if a scrollbar has to be used instead)
	 */
	public boolean getVerticalWrapAllowed() {
		return verticalWrapAllowed;
	}

	/**
	 * Sets the property telling if the console enables horizontal word wrapping
	 * @param wrapMode true if words can be wrapped verticaly (false if a scrollbar has to be used instead)
	 */
	public void setHorizontalWrapAllowed(boolean wrapMode) {
		horizontalWrapAllowed = wrapMode;
	}

	/**
	 * Gets the property telling if the console enables horizontal word wrapping
	 * @return true if words can be wrapped verticaly (false if a scrollbar has to be used instead)
	 */
	public boolean getHorizontalWrapAllowed() {
		return horizontalWrapAllowed;
	}

	/**
	 * Main class used to test console
	 * @param args 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String configFilePath = "./resources/configuration.xml";
		String profileName = null;
		//
		switch (args.length) {
		case 2:
			configFilePath = args[1];
		case 1:
			profileName = args[0];
			break;
		}
		if (!new File(configFilePath).exists()) {
			// Print usage
			System.err.println("Three way of launch:");
			System.err.println(" - 0 argument: The first profile of the ./resources/configuration.xml will be loaded.");
			System.err.println(" - 1 argument: The specified profile name of the ./resources/configuration.xml will be loaded. (args1=profileName)");
			System.err.println(" - 2 arguments: The specified profile with the specified configuration file will be loaded. (args1=profileName args2=configurationFilePath)");

		} else {
			ConsoleConfiguration config = ConfigurationBuilder.buildConfiguration(configFilePath);
			config.setActiveProfile(profileName);
			//
			JFrame window = new JFrame("Generic console");
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.getContentPane().setLayout(new BorderLayout());
			window.getContentPane().add(new JScrollPane(ConsoleBuilder.buildConsole(config, window)), BorderLayout.CENTER);
			window.setSize(600, 300);
			window.setLocationRelativeTo(null);
			window.setVisible(true);
		}
	}

}
