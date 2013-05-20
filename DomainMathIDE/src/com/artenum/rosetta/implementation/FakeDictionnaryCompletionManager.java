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
package com.artenum.rosetta.implementation;

import java.util.ArrayList;
import java.util.List;

import com.artenum.rosetta.core.CompletionItemImpl;
import com.artenum.rosetta.interfaces.core.CompletionItem;
import com.artenum.rosetta.interfaces.core.CompletionManager;
import com.artenum.rosetta.interfaces.core.GenericInterpreter;
import com.artenum.rosetta.interfaces.core.InputParsingManager;

/**
 * A test class in order to test the Completion Manager
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public class FakeDictionnaryCompletionManager implements CompletionManager {
	private ArrayList<CompletionItem> dictionnary;
	
	/**
	 * Create a fake database of completion informations
	 */
	public FakeDictionnaryCompletionManager() {
		// Build dictionnary
		dictionnary = new ArrayList<CompletionItem>();
		dictionnary.add(new CompletionItemImpl("var", "frame", "frame" , "Java Swing Frame"));
		dictionnary.add(new CompletionItemImpl("var", "content", "content" , "String buffer"));
		dictionnary.add(new CompletionItemImpl("method", "Dimension getSize()", "getSize()" , "Return the size of the window"));
		dictionnary.add(new CompletionItemImpl("method", "getContentPane(JComponent)", "getContentPane(" , "Add content in frame"));
		dictionnary.add(new CompletionItemImpl("var", "frame2", "frame2" , "Java Swing Frame"));
		dictionnary.add(new CompletionItemImpl("var", "content2", "content2" , "String buffer"));
		dictionnary.add(new CompletionItemImpl("method", "Dimension getSize2()", "getSize2()" , "Return the size of the window"));
		dictionnary.add(new CompletionItemImpl("method", "getContentPane2(JComponent)", "getContentPane2(" , "Add content in frame"));
		dictionnary.add(new CompletionItemImpl("method", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" , "Add content in frame"));
	}

	public List<CompletionItem> getCompletionItems() {
		return dictionnary;
	}

	public void setInputParsingManager(InputParsingManager inputParsingManager) {
		// No need for fake implementation
	}

	public void setInterpretor(GenericInterpreter interpretor) {
		// No need for fake implementation
	}

}
