package org.kagelabs.pkbasic;

/**
 * Sandboxed pkbasic interpreter
 * @author Patrick Kage
 *
 */
public class Interpreter {
	private LineBundle lines;
	private Context global;
	private DirectiveProcessor dirp;
	
	public void initialize(String filename) {
		Preprocessor pp = new Preprocessor();
		lines = pp.processFile(filename);
		
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
