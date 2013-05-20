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

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.artenum.rosetta.interfaces.ui.CommandLineView;
import com.artenum.rosetta.interfaces.ui.OutputView;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public class CommandLineViewImpl extends JPanel implements CommandLineView {
	private static final long serialVersionUID = 1L;

	public CommandLineViewImpl() {
		setLayout(new BorderLayout());
		setMinimumSize(OutputView.SMALL);
	}

	public void setInputCommandView(JComponent command) {
		add(command, BorderLayout.CENTER);
	}

	public void setPromptView(JComponent prompt) {
		add(prompt, BorderLayout.WEST);
	}

	public void setVisible(boolean visible) {
		if (visible) {
			expand();
		} else {
			shrink();
		}
	}

	private void shrink() {
		setMaximumSize(OutputView.SMALL);
		invalidate();
		doLayout();
	}

	private void expand() {
		setMaximumSize(OutputView.BIG);
		invalidate();
		doLayout();
	}

}
