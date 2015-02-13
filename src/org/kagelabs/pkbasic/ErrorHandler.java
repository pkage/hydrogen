package org.kagelabs.pkbasic;

import java.util.ArrayDeque;

/**
 * Class to keep track of errors emitted by the project in various states of failure.
 * @author Patrick Kage && Alex Zheng
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
		String[] errorNames = new String[stack.size()];
		String[] errorReasons = new String[stack.size()];
		String[] errorOrigins = new String[stack.size()];
		Object[] stack2 = stack.toArray();
		for(int i = 0; i<stack.size(); i++){
			errorNames[i] = ((Error) stack2[i]).getName();
		}
		for(int i = 0; i<stack.size(); i++){
			errorReasons[i] = ((Error) stack2[i]).getReason();
		}
		for(int i = 0; i<stack.size(); i++){
			errorOrigins[i] = ((Error) stack2[i]).getOrigin();
		}
		String errorReport = "ERROR: /n";
		for(int i = 0; i<stack.size(); i++){
			errorReport += "Error Type: " + errorNames[i] + "   ";
			errorReport += "Error Reason: " + errorReasons[i] + "   ";
			errorReport += "Error Origin: " + errorOrigins[i] + "    /n"; 
		}
		return errorReport;
	}
	
	public boolean hasErrors() {
		return (stack.size() > 0);
	}
}
