package org.kagelabs.hydrogen;

import java.util.ArrayDeque;

/**
 * Class to keep track of errors emitted by the project in various states of failure.
 * @author Patrick Kage
 * @author Alex Zheng
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
		// do some sh*t here
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
		String errorReport = "ERROR: \n";
		for(int i = 0; i<stack.size(); i++){
			errorReport += "\tError Type: " + errorNames[i] + "   ";
			errorReport += "\tError Reason: " + errorReasons[i] + "   ";
			errorReport += "\tError Origin: " + errorOrigins[i] + "    \n"; 
		}
		return errorReport;
	}
	
	public boolean hasErrors() {
		return (stack.size() > 0);
	}
	
	public int size() {return stack.size();}
	
	void clear() {
		this.stack = new ArrayDeque<Error>();
	}

	public void addError(String name, String reason, String origin) {
		this.addError(new Error(name, reason, origin));
		
	}
}
