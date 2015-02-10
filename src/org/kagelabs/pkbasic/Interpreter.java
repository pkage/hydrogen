package org.kagelabs.pkbasic;

/**
 * Sandboxed pkbasic interpreter
 * @author Patrick Kage
 *
 */
public class Interpreter {
	private LineBundle lines;
	private Context global;
	
	public void initialize(String filename) {
		FileExtractor fe = new FileExtractor();
		lines = fe.extract(filename);
		
	}
	
	public void tick() {
		// tick sim
	}
	
	public Context getGlobalContext() {
		return global;
	}
	
	public LineBundle getFullLines() {
		return lines;
	}
}
