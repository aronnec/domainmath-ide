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
public class GetNextAction extends AbstractConsoleAction {
	private static final long serialVersionUID = 1L;
	
	public void actionPerformed(ActionEvent e) {
		String historyLine = null;

		/* Search matching line in history if exists !! */
		if(configuration.getHistoryManager().isInHistory()) {
			historyLine = configuration.getHistoryManager().getNextEntry(configuration.getHistoryManager().getTmpEntry());
		}
		
		/* If nothing found in history, go back to edited line */
		if(historyLine==null) {
			historyLine = configuration.getHistoryManager().getTmpEntry();
			/* Reset edited line */
			configuration.getHistoryManager().setTmpEntry(null);
			configuration.getHistoryManager().setInHistory(false);
		}
		
		/* If a matching entry has been found */
		if(historyLine!=null) {
			/* Update command line */
			configuration.getInputCommandView().reset();
			configuration.getInputCommandView().append(historyLine);
		}
	}
}
