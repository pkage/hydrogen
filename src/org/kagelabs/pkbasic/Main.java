package org.kagelabs.pkbasic;


/**
 * Program entry point
 * @author Patrick Kage
 *
 */
public class Main {
	public static void main(String[] args) {
		//System.out.println("It's alive!");
		/*Main main = new Main();
		main.run();*/
		test();
	}
	
	public void run() {
		Interpreter pkb = new Interpreter();
		pkb.initialize("pkb/fullexample.pkb");
	}
	
	public static void test() {
		Directive directive = new Directive("test \"test test test\" test");
		System.out.println(directive.getFull());
		for(int i = 0; i < directive.getSplit().size(); i++) {
			System.out.println(directive.getSplit().get(i)+" "+i);
		}
		System.out.println(directive.getSplit().size());
		
	}
}
