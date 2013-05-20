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

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.artenum.rosetta.interfaces.core.ConsoleConfiguration;
import com.artenum.rosetta.interfaces.ui.OutputView;
import com.artenum.rosetta.util.ConfigurationBuilder;
import com.artenum.rosetta.util.ConsoleBuilder;

public class TestConsoleHistoryPerformance implements Runnable {
	private OutputView outputView;
	private boolean writing;

	public TestConsoleHistoryPerformance(OutputView outputView) {
		this.outputView = outputView;
		writing = true;
	}

	public void setWriting(boolean writing) {
		this.writing = writing;
	}

	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long i = 0;
		long startTime = System.currentTimeMillis();
		while (writing) {
			i++;
			outputView.setCaretPositionToEnd();
			outputView.append(i + " : qsdf qsdfaze azer�asdf, qmlsdjf �ajzef:,; qsdfoj lkz f \n");
			writing = i < 25000;
			try {
				Thread.sleep(0, 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.gc();
		writing = true;
		while (writing) {
			i++;
			outputView.setCaretPositionToEnd();
			outputView.append(i + " : qsdf qsdfaze azer�asdf, qmlsdjf �ajzef:,; qsdfoj lkz f \n");
			outputView.setCaretPositionToEnd();
			writing = i < 40000;
		}
		System.out.println("Time: " + (System.currentTimeMillis() - startTime));
	}

	public static void main(String[] args) throws Exception {
		String configFilePath = "resources/configuration.xml";
		String profileName = null;
		//
		switch (args.length) {
		case 2:
			configFilePath = args[1];
		case 1:
			profileName = args[0];
			break;
		}

		if (!new File(configFilePath).exists()) {
			// Print usage
			System.err.println("Three way of launch:");
			System.err.println(" - 0 argument: The first profile of the ./resources/configuration.xml will be loaded.");
			System.err.println(" - 1 argument: The specified profile name of the ./resources/configuration.xml will be loaded. (args1=profileName)");
			System.err
					.println(" - 2 arguments: The specified profile with the specified configuration file will be loaded. (args1=profileName args2=configurationFilePath)");

		} else {
			ConsoleConfiguration config = ConfigurationBuilder.buildConfiguration(configFilePath);
			config.setActiveProfile(profileName);
			//
			JFrame window = new JFrame("Generic console");
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.getContentPane().setLayout(new BorderLayout());
			window.getContentPane().add(new JScrollPane(ConsoleBuilder.buildConsole(config, window)), BorderLayout.CENTER);
			window.setSize(600, 300);
			window.setLocationRelativeTo(null);
			window.setVisible(true);
			// 
			OutputView outputView = config.getOutputView();
			TestConsoleHistoryPerformance writingThread = new TestConsoleHistoryPerformance(outputView);
			new Thread(writingThread).start();
		}
	}

}
