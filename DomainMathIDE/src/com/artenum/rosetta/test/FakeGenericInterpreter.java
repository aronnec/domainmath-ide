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

import java.io.Reader;
import java.io.Writer;

import com.artenum.rosetta.exception.ScriptException;
import com.artenum.rosetta.interfaces.core.GenericInterpreter;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public class FakeGenericInterpreter  implements GenericInterpreter {

	public Object eval(String script) throws ScriptException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object eval(Reader reader) throws ScriptException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Writer getErrorWriter() {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getReader() {
		// TODO Auto-generated method stub
		return null;
	}

	public Writer getWriter() {
		// TODO Auto-generated method stub
		return null;
	}

	public void put(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	public void setErrorWriter(Writer writer) {
		// TODO Auto-generated method stub
		
	}

	public void setReader(Reader reader) {
		// TODO Auto-generated method stub
		
	}

	public void setWriter(Writer writer) {
		// TODO Auto-generated method stub
		
	}

}
