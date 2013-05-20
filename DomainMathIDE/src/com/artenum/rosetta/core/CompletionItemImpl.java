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

import com.artenum.rosetta.interfaces.core.CompletionItem;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public class CompletionItemImpl implements CompletionItem {
	private String help;
	private String methodProfile;
	private String returnValue;
	private String type;

	public CompletionItemImpl() {

	}

	/**
	 * @param type
	 * @param methodProfile
	 * @param returnValue
	 * @param help
	 */
	public CompletionItemImpl(String type, String methodProfile, String returnValue, String help) {
		super();
		this.type = type;
		this.methodProfile = methodProfile;
		this.returnValue = returnValue;
		this.help = help;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.CompletionItem#getHelp()
	 */
	public String getHelp() {
		return help;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.CompletionItem#setHelp(java.lang.String)
	 */
	public void setHelp(String help) {
		this.help = help;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.CompletionItem#getMethodProfile()
	 */
	public String getMethodProfile() {
		return methodProfile;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.CompletionItem#setMethodProfile(java.lang.String)
	 */
	public void setMethodProfile(String methodProfile) {
		this.methodProfile = methodProfile;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.CompletionItem#getReturnValue()
	 */
	public String getReturnValue() {
		return returnValue;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.CompletionItem#setReturnValue(java.lang.String)
	 */
	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.CompletionItem#getType()
	 */
	public String getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see com.artenum.rosetta.interfaces.core.CompletionItem#setType(java.lang.String)
	 */
	public void setType(String type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(CompletionItem o) {
		return getReturnValue().compareTo(o.getReturnValue());
	}
}
