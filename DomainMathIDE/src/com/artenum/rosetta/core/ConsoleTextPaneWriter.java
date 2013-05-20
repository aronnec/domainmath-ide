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
package com.artenum.rosetta.core;

import java.io.IOException;
import java.io.Writer;

import com.artenum.rosetta.ui.ConsoleTextPane;


/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public class ConsoleTextPaneWriter extends Writer {
	private ConsoleTextPane consoleTextPane;
	private String styleName;
	private StringBuffer txtBuffer;

	/**
	 * @param consoleTextPane
	 * @param styleName
	 */
	public ConsoleTextPaneWriter(ConsoleTextPane consoleTextPane, String styleName) {
		this.consoleTextPane = consoleTextPane;
		this.styleName = styleName;
		txtBuffer = new StringBuffer();
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		txtBuffer.setLength(0);
		txtBuffer.append(cbuf, off, len);
		consoleTextPane.setCaretPositionToEnd();
		consoleTextPane.append(txtBuffer.toString(), styleName);
	}

}
