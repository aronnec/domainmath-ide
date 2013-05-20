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
package com.artenum.rosetta.interfaces.ui;

import java.awt.Dimension;
import java.io.Writer;
import com.artenum.rosetta.interfaces.ui.GuiComponent;
import javax.swing.text.StyledDocument;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public interface OutputView extends GuiComponent {
	static final Dimension SMALL = new Dimension(0, 0);
	static final Dimension BIG = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);

	
	/**
	 * @param editable
	 */
	void setEditable(boolean editable);
	
	/**
	 * @param content
	 */
	void append(String content);

	/**
	 * @param content
	 * @param styleName
	 */
	void append(String content, String styleName);

	/**
	 * @param styleName
	 */
	void setStyleName(String styleName);

	/**
	 * @return
	 */
	String getText();

	/**
	 * 
	 */
	void reset();

	/**
	 * @param content
	 */
	void setText(String content);

	/**
	 * @param styledDocument
	 */
	void setStyledDocument(StyledDocument styledDocument);
	
	/**
	 * @return
	 */
	Writer getWriter();
	
	/**
	 * @return
	 */
	Writer getErrorWriter();
	
	/**
	 * 
	 */
	void setCaretPositionToBeginning();

	/**
	 * 
	 */
	void setCaretPositionToEnd();
}
