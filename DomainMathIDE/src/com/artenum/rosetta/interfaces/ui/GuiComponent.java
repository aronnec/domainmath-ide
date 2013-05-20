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

import java.awt.Color;
import java.awt.Font;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public interface GuiComponent {
	/**
	 * Sets the background of the component
	 * @param bgColor the background Color
	 */
	void setBackground(Color bgColor);
	
	/**
	 * Sets the foreground of the component
	 * @param fgColor the foreground Color
	 */
	void setForeground(Color fgColor);

	/**
	 * Sets the visibility status of the component
	 * @param status is true to set the component visible and false else
	 */
	void setVisible(boolean status);
	
	/**
	 * Sets the font of the component
	 * @param font the font
	 */
	void setFont(Font font);
}
