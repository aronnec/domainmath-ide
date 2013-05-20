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

import java.io.Reader;
import java.io.Writer;

import com.artenum.rosetta.exception.ScriptException;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */

public interface GenericInterpreter {
	/**
	 * Executes the specified script. The default <code>ScriptContext</code>
	 * for the <code>ScriptEngine</code> is used.
	 * 
	 * @param script
	 *            The script language source to be executed.
	 * 
	 * @return The value returned from the execution of the script.
	 * 
	 * @throws ScriptException
	 *             if error occurrs in script.
	 * @throws NullPointerException
	 *             if the argument is null.
	 */
	Object eval(String script) throws ScriptException;

	/**
	 * Same as <code>eval(String)</code> except that the source of the script
	 * is provided as a <code>Reader</code>
	 * 
	 * @param reader
	 *            The source of the script.
	 * 
	 * @return The value returned by the script.
	 * 
	 * @throws ScriptException
	 *             if an error occurrs in script.
	 * @throws NullPointerException
	 *             if the argument is null.
	 */
	Object eval(Reader reader) throws ScriptException;

	/**
	 * Sets a key/value pair in the state of the ScriptEngine that may either
	 * create a Java Language Binding to be used in the execution of scripts or
	 * be used in some other way, depending on whether the key is reserved. Must
	 * have the same effect as
	 * <code>getBindings(ScriptContext.ENGINE_SCOPE).put</code>.
	 * 
	 * @param key
	 *            The name of named value to add
	 * @param value
	 *            The value of named value to add.
	 * 
	 * @throws NullPointerException
	 *             if key is null.
	 * @throws IllegalArgumentException
	 *             if key is empty.
	 */
	void put(String key, Object value);

	/**
	 * Retrieves a value set in the state of this engine. The value might be one
	 * which was set using <code>setValue</code> or some other value in the
	 * state of the <code>ScriptEngine</code>, depending on the
	 * implementation. Must have the same effect as
	 * <code>getBindings(ScriptContext.ENGINE_SCOPE).get</code>
	 * 
	 * @param key
	 *            The key whose value is to be returned
	 * @return the value for the given key
	 * 
	 * @throws NullPointerException
	 *             if key is null.
	 * @throws IllegalArgumentException
	 *             if key is empty.
	 */
	Object get(String key);

	/**
	 * Returns the <code>Writer</code> for scripts to use when displaying
	 * output.
	 * 
	 * @return The <code>Writer</code>.
	 */
	Writer getWriter();

	/**
	 * Returns the <code>Writer</code> used to display error output.
	 * 
	 * @return The <code>Writer</code>
	 */
	Writer getErrorWriter();

	/**
	 * Sets the <code>Writer</code> for scripts to use when displaying output.
	 * 
	 * @param writer
	 *            The new <code>Writer</code>.
	 */
	void setWriter(Writer writer);

	/**
	 * Sets the <code>Writer</code> used to display error output.
	 * 
	 * @param writer
	 *            The <code>Writer</code>.
	 */
	void setErrorWriter(Writer writer);

	/**
	 * Returns a <code>Reader</code> to be used by the script to read input.
	 * 
	 * @return The <code>Reader</code>.
	 */
	Reader getReader();

	/**
	 * Sets the <code>Reader</code> for scripts to read input .
	 * 
	 * @param reader
	 *            The new <code>Reader</code>.
	 */
	void setReader(Reader reader);
}
