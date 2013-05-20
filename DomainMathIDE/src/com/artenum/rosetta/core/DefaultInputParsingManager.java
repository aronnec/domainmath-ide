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

import java.awt.Point;

import com.artenum.rosetta.interfaces.core.InputParsingManager;
import com.artenum.rosetta.interfaces.ui.InputCommandView;
import com.artenum.rosetta.util.StringConstants;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public class DefaultInputParsingManager implements InputParsingManager {
	private InputCommandView inputCommandView;
	private Point windowCompletionPosition;

	/**
	 * 
	 */
	public DefaultInputParsingManager() {
		windowCompletionPosition = new Point(0, 0);
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.InputParsingManager#append(java.lang.String)
	 */
	public void append(String content) {
		inputCommandView.append(content);
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.InputParsingManager#getCaretPosition()
	 */
	public int getCaretPosition() {
		return inputCommandView.getCaretPosition();
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.InputParsingManager#getCommandLine()
	 */
	public String getCommandLine() {
		return inputCommandView.getText();
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.InputParsingManager#getCompletionLevel()
	 */
	public int getCompletionLevel() {
		// FIXME : No completion by default
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.InputParsingManager#getNumberOfLines()
	 */
	public int getNumberOfLines() {
		int result = inputCommandView.getText().split(StringConstants.NEW_LINE).length;
		if (inputCommandView.getText().lastIndexOf(StringConstants.NEW_LINE) != -1) {
			result++;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.InputParsingManager#getPartLevel(int)
	 */
	public String getPartLevel(int level) {
		// FIXME : No completion by default
		if (level == 0) {
			return getCommandLine();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.InputParsingManager#getWindowCompletionLocation()
	 */
	public Point getWindowCompletionLocation() {
		windowCompletionPosition.setLocation(inputCommandView.getCaretLocation());
		windowCompletionPosition.translate(4, 2);
		return windowCompletionPosition;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.InputParsingManager#isBlockEditing()
	 */
	public boolean isBlockEditing() {
		// FIXME : No block editing by default
		return false;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.InputParsingManager#reset()
	 */
	public void reset() {
		inputCommandView.reset();
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.InputParsingManager#setInputCommandView(com.artenum.rosetta.interfaces.ui.InputCommandView)
	 */
	public void setInputCommandView(InputCommandView inputCommandView) {
		this.inputCommandView = inputCommandView;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.InputParsingManager#writeCompletionPart(java.lang.String)
	 */
	public void writeCompletionPart(String completionResult) {
		// FIXME : No completion by default (Implementation made just to see
		// something)
		inputCommandView.append(completionResult);
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.InputParsingManager#backspace()
	 */
	public void backspace() {
		inputCommandView.backspace();
	}

}
