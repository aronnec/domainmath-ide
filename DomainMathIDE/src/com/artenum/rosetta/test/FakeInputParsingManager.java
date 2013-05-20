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
package com.artenum.rosetta.test;

import java.awt.Point;

import com.artenum.rosetta.interfaces.core.InputParsingManager;
import com.artenum.rosetta.interfaces.ui.InputCommandView;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */

public class FakeInputParsingManager implements InputParsingManager {
	private static int count = 0;
	private int localId;

	public FakeInputParsingManager() {
		localId = count++;
	}

	public void append(String content) {
		// TODO Auto-generated method stub

	}

	public int getCaretPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getCommandLine() {
		// TODO Auto-generated method stub
		return "Fake cmd line " + localId;
	}

	public int getCompletionLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumberOfLines() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getPartLevel(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	public Point getWindowCompletionLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isBlockEditing() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	public void setInputCommandView(InputCommandView inputCommandView) {
		// TODO Auto-generated method stub

	}

	public void writeCompletionPart(String completionResult) {
		// TODO Auto-generated method stub

	}
	
	public void backspace() {
		// TODO Auto-generated method stub
		
	}

}
