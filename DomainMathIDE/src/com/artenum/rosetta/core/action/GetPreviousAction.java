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
 * @author Vincent COUVERT
 */
public class GetPreviousAction extends AbstractConsoleAction {
	private static final long serialVersionUID = 1L;
	
	public void actionPerformed(ActionEvent e) {
		String historyLine = null;
		
		/* Do we start browsing */
		if(configuration.getHistoryManager().isInHistory()==false) {
			/* Sets browsing state */
			configuration.getHistoryManager().setInHistory(true);
			/* Save currently edited command line */
			configuration.getHistoryManager().setTmpEntry(configuration.getInputCommandView().getText());
		}
		
		/* Search matching line in history if exists !! */
		historyLine = configuration.getHistoryManager().getPreviousEntry(configuration.getHistoryManager().getTmpEntry());

		/* Update command line only if matching line found */
		if(historyLine!=null) {
			configuration.getInputCommandView().reset();
			configuration.getInputCommandView().append(historyLine);
		}
	}
}
