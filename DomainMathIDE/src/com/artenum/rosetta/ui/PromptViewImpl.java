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

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.artenum.rosetta.interfaces.core.InputParsingManager;
import com.artenum.rosetta.interfaces.ui.PromptView;

public class PromptViewImpl extends JPanel implements PromptView {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_PROMPT = ">>> ";
	private static final String DEFAULT_IN_BLOCK_PROMPT = "... ";
	private static final String HTML_START = "<html>";
	private static final String HTML_NEW_LINE = "<br/>";
	private static final String HTML_END = "</html>";
	private String defaultPrompt;
	private String inBlockPrompt;
	private InputParsingManager inputParsingManager;
	// Working vars
	private StringBuffer promptTextContent;
	private JLabel promptUI;

	public PromptViewImpl() {
		// internal part
		promptTextContent = new StringBuffer();
		defaultPrompt = DEFAULT_PROMPT;
		inBlockPrompt = DEFAULT_IN_BLOCK_PROMPT;
		// gui part
		promptUI = new JLabel();
		promptUI.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		promptUI.setOpaque(true);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(promptUI);
		add(Box.createVerticalGlue());
	}

	public void setDefaultPrompt(String defaultPrompt) {
		this.defaultPrompt = defaultPrompt;
		updatePrompt();
	}

	public void setInBlockPrompt(String inBlockPrompt) {
		this.inBlockPrompt = inBlockPrompt;
		updatePrompt();
	}

	public void setInputParsingManager(InputParsingManager inputParsingManager) {
		this.inputParsingManager = inputParsingManager;
	}

	public void updatePrompt() {
		promptTextContent.setLength(0);
		promptTextContent.append(HTML_START);
		promptTextContent.append(defaultPrompt.replaceAll(">", "&gt;"));
		int nbLineToShow = inputParsingManager.getNumberOfLines();
		while (nbLineToShow-- > 1) {
			promptTextContent.append(HTML_NEW_LINE);
			promptTextContent.append(inBlockPrompt);
		}
		promptTextContent.append(HTML_END);
		promptUI.setText(promptTextContent.toString());
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setBackground(java.awt.Color)
	 */
	public void setBackground(Color bgColor) {
		super.setBackground(bgColor);
		if (promptUI != null) {
			promptUI.setBackground(bgColor);
		}
	}

	public String getDefaultPrompt() {
		return defaultPrompt;
	}

	public String getInBlockPrompt() {
		return inBlockPrompt;
	}
}
