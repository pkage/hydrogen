package org.kagelabs.pkbasic;

import java.util.ArrayDeque;

/**
 * Class to keep track of errors emitted by the project in various states of failure.
 * @author Patrick Kage
 *
 */
public class ErrorHandler {
	private ArrayDeque<Error> stack;
	
	ErrorHandler() {
		stack = new ArrayDeque<Error>();
	}
	
	public void addError(Error e) {
		stack.addLast(e);
	}
	
	public String generateReport() {
		// do some shit here
		return "Error reporting NYI";
	}
	
	public boolean hasErrors() {
		return (stack.size() > 0);
	}
}
