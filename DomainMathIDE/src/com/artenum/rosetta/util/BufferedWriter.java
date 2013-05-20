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
package com.artenum.rosetta.util;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

public class BufferedWriter extends Writer {
	private String styleName;
	private BlockingQueue<StringBuffer> bufferQueue;
	private LinkedList<String> styleQueue;
	private StringBuffer currentWorkingBuffer;

	public BufferedWriter(String styleName, BlockingQueue<StringBuffer> bufferQueue, LinkedList<String> styleQueue) {
		this.styleName = styleName;
		this.bufferQueue = bufferQueue;
		this.styleQueue = styleQueue;
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		if (bufferQueue.size() > 1 && styleQueue.getLast().equals(styleName) && currentWorkingBuffer != null) {
			currentWorkingBuffer.append(cbuf, off, len);
		} else {
			styleQueue.add(styleName);
			try {
				currentWorkingBuffer = new StringBuffer(5000);
				currentWorkingBuffer.append(cbuf, off, len);
				bufferQueue.put(currentWorkingBuffer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
