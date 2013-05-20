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

import java.io.Writer;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleContext;

import com.artenum.rosetta.core.ConsoleTextPaneWriter;
import com.artenum.rosetta.interfaces.ui.OutputView;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public class ConsoleTextPane extends JTextPane implements OutputView {
	private static final long serialVersionUID = 1L;
	private String currentStyleName;

	public ConsoleTextPane() {
		currentStyleName = StyleContext.DEFAULT_STYLE;
		this.setAutoscrolls(true);
		// Enabled Drag&Drop with this component
		this.setDragEnabled(true);
	}

	public void append(String content) {
		append(content, currentStyleName);
	}

	public void setStyleName(String styleName) {
		currentStyleName = styleName;
	}

	public void append(String content, String styleName) {
		try {
			getStyledDocument().insertString(getCaretPosition(), content, getStyle(styleName));
		} catch (BadLocationException e) {
		}
	}

	public void reset() {
		try {
			getStyledDocument().remove(0, getStyledDocument().getLength());
		} catch (BadLocationException e) {
		}
		setCaretPosition(0);
	}

	public Writer getErrorWriter() {
		return new ConsoleTextPaneWriter(this, StyleContext.DEFAULT_STYLE);
	}

	public Writer getWriter() {
		return new ConsoleTextPaneWriter(this, StyleContext.DEFAULT_STYLE);
	}

	public void setCaretPositionToBeginning() {
		setCaretPosition(0);
	}
	
	public void setCaretPositionToEnd() {
		setCaretPosition(getStyledDocument().getLength());
	}
	
	public void backspace() {
		try {
			getStyledDocument().remove(getCaretPosition() - 1, 1);
		} catch (BadLocationException e) {
		}
	}
}
