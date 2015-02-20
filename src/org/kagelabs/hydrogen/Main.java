package org.kagelabs.hydrogen;
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Program entry point
 * @author Patrick Kage
 *
 */
public class Main {
	public static void main(String[] args) {
		//System.out.println("It's alive!");
		Main main = new Main();
		main.dload();
		//test();
	}
	
	public void run() {
		Interpreter hy = new Interpreter();
		hy.initialize("scripts/fullexample.hy");
	}
	
	public static void test() {
		String input = null;
		BufferedReader kboard = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			input = kboard.readLine();
		}
		catch(IOException e) { 
			System.out.println("Error reading keyboard input"); 
		}
		
		Directive directive = new Directive(input);
		//System.out.println(directive.getFull());
		for(int i = 0; i < directive.getSplit().size(); i++) {
			System.out.println(directive.getSplit().get(i));
		}
		//System.out.println(directive.getSplit().size());
		DirectiveClassifier.classifyDirective(directive);
		if(directive.getType() == DirectiveType.ASSIGNMENT)
		{
		System.out.println("yes");
		}
	}
	
	public void dload() {
		ActionProcessor ap = new ActionProcessor();
		ErrorHandler eh = new ErrorHandler();
		ap.importLibrary(eh, "org.kagelabs.hydrogen.stl.io");
		//ap.importLibrary(eh, "org.kagelabs.hydrogen.stl.io2");
		ap.importLibrary(eh, "org.kagelabs.hydrogen.stl.sys");
		if (eh.hasErrors()) {
			System.out.println(eh.generateReport());
		} else {
			System.out.println("loaded io.class successfully");
		}
		
		ap.initAllActionProviders(eh);
		eh.clear();
		
		System.out.println("Available methods: " + ap.getActions().size());
		
		System.out.println("Listing methods: ");
		for (ActionMetadata am : ap.getActions().keySet()) {
			System.out.println("\t[" + ((am.getReturnPrefix() == '\0') ? 'V' : am.getReturnPrefix()) + "]" + am.getName());
		}
		
		Scanner kb = new Scanner(System.in);
		System.out.print("Method to load: ");
		Action action = ap.getAction(kb.nextLine());
		System.out.print("Loaded action. Argument: ");
		Value arg = new Value(VarType.STRING);
		arg.setString(kb.nextLine());
		action.call(eh, new Value[]{ arg });
		if (eh.hasErrors()) {
			System.out.println(eh.generateReport());
		} else {
			System.out.println("\nfinished call successfully");
		}
		
	}
}
