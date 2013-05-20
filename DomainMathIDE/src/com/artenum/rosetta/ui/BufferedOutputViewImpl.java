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
package com.artenum.rosetta.ui;

import java.io.Writer;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleContext;

import com.artenum.rosetta.interfaces.ui.OutputView;
import com.artenum.rosetta.util.BufferedWriter;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public class BufferedOutputViewImpl extends JTextPane implements OutputView, Runnable {
	private static final long serialVersionUID = 1L;
	private String activeStyle;
	private String lastAppendedStyle;
	private BlockingQueue<StringBuffer> bufferQueue;
	private LinkedList<String> styleQueue;
	private StringBuffer currentWorkingBuffer;

	public BufferedOutputViewImpl() {
		activeStyle = StyleContext.DEFAULT_STYLE;
		bufferQueue = new ArrayBlockingQueue<StringBuffer>(6);
		styleQueue = new LinkedList<String>();
		Thread thread = new Thread(this);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	public void run() {
		while (true) {
			try {
				StringBuffer buffer = bufferQueue.take();
				String style = styleQueue.poll();
				getStyledDocument().insertString(getStyledDocument().getLength(), buffer.toString(), getStyledDocument().getStyle(style));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void append(String content) {
		append(content, activeStyle);
	}

	public void append(String content, String styleName) {
		if (styleName.equals(lastAppendedStyle) && bufferQueue.size() > 1) {
			currentWorkingBuffer.append(content);
		} else {
			lastAppendedStyle = styleName;
			styleQueue.add(lastAppendedStyle);
			try {
				currentWorkingBuffer = new StringBuffer(content);
				bufferQueue.put(currentWorkingBuffer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Writer getErrorWriter() {
		return new BufferedWriter(StyleContext.DEFAULT_STYLE, bufferQueue, styleQueue);
	}

	public Writer getWriter() {
		return new BufferedWriter(StyleContext.DEFAULT_STYLE, bufferQueue, styleQueue);
	}

	public void reset() {
		try {
			getStyledDocument().remove(0, getStyledDocument().getLength());
		} catch (BadLocationException e) {
		}
		setCaretPosition(0);
	}

	public void setCaretPositionToBeginning() {
		setCaretPosition(0);
	}

	public void setCaretPositionToEnd() {
		setCaretPosition(getStyledDocument().getLength());
	}

	public void setStyleName(String styleName) {
		activeStyle = styleName;
	}

}
