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

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Point;

import com.artenum.rosetta.interfaces.ui.InputCommandView;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public class InputCommandViewImpl extends ConsoleTextPane implements InputCommandView {
	private static final long serialVersionUID = 1L;
	private static final String END_LINE = "\n";
	private static final Point ERROR_POINT = new Point(0, 0);

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.ui.InputCommandView#getCaretLocation()
	 */
	public Point getCaretLocation() {
		FontMetrics fontMetric = getFontMetrics(getFont());
		String[] lines = null;
		try {
			lines = getStyledDocument().getText(0, getCaretPosition()).split(END_LINE);
		} catch (Exception e1) {
			return ERROR_POINT;
		}

		Point result = new Point(fontMetric.stringWidth(lines[lines.length - 1]), (lines.length * fontMetric.getHeight()));

		// Translate for absolute coordinates
		Component currentComponent = this;
		while (currentComponent != null) {
			result.translate(currentComponent.getLocation().x, currentComponent.getLocation().y);
			currentComponent = currentComponent.getParent();
		}

		return result;
	}
}
