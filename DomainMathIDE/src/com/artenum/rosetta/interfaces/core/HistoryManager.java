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
package com.artenum.rosetta.interfaces.core;

/**
 * @author Vincent COUVERT
 *
 */
public interface HistoryManager {

	/**
	 * Add a line or a block in history
	 * @param newEntry the entry to be added at the end of the history
	 */
	void addEntry(String newEntry);
	
	/**
	 * Display all history entries with line  numbers
	 */
	void display();
	
	/**
	 * Set the maximum number of entries (lines/blocks) allowed in the history
	 * @param numberOfEntries the number of entries to set
	 */
	void setMaxEntryNumber(int numberOfEntries);
	
	/**
	 * Clears the history
	 */
	void reset();
	
	/**
	 * Load history from a file
	 */
	void load();
	
	/**
	 * Save history to a file
	 */
	void save();
	
	/**
	 * Gets an entry in the history by giving its number
	 * @param entryIndex the index of the entry in the history
	 * @return the entry
	 */
	String getEntry(int entryIndex);
	
	/**
	 * Gets previous entry in the history by giving its beginning
	 * @param beg the beginning of the entry (can be empty)
	 * @return the entry
	 */
	String getPreviousEntry(String beg);
	
	/**
	 * Gets next entry in the history by giving its beginning
	 * @param beg the beginning of the entry (can be empty)
	 * @return the entry
	 */
	String getNextEntry(String beg);
	
	/**
	 * Saves the currently edited command line before erasing it
	 * @param currentCommandLine the text currently edited by the user 
	 */
	void setTmpEntry(String currentCommandLine);

	/**
	 * Gets the currently edited command line before erasing it
	 * @return the text currently edited by the user before going into history
	 */
	String getTmpEntry();

	/**
	 * Are we browsing history ?
	 * @return true if we are browing history, false else
	 */
	boolean isInHistory();

	/**
	 * Sets the flag indicating if we are browsing history 
	 * @param status is true if we begin to browse the history, false if we stop browsing
	 */
	void setInHistory(boolean status);

}
