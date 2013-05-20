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
package com.artenum.rosetta.core.action;

import java.awt.event.ActionEvent;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public class CompletionAction extends AbstractConsoleAction {
	private static final long serialVersionUID = 1L;

	public void actionPerformed(ActionEvent e) {
		configuration.getCompletionWindow().show(configuration.getCompletionManager().getCompletionItems(),
				configuration.getInputParsingManager().getWindowCompletionLocation());
	}
}
