package dev.exec.util;

/**
 * <p>Title: ExecCommander</p>
 * <p>Description: This project serves as a launchpad for development and tests of a component to make use of process execution easier</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: N/A</p>
 * @author Doron Barak
 * @version 1.0
 */
public interface ExecProcessor {
	// This method gets called when the process sent us a new input String..
	public void processNewInput(String input);

	// This method gets called when the process sent us a new error String..
	public void processNewError(String error);

	// This method gets called when the process has ended..
	public void processEnded(int exitValue);
}