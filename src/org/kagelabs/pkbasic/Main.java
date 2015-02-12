package org.kagelabs.pkbasic;


/**
 * Program entry point
 * @author Patrick Kage
 *
 */
public class Main {
	public static void main(String[] args) {
		System.out.println("It's alive!");
		Main main = new Main();
		main.run();
	}
	
	public void run() {
		Interpreter pkb = new Interpreter();
		pkb.initialize("pkb/fullexample.pkb");
	}
}
