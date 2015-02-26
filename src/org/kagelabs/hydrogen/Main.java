package org.kagelabs.hydrogen;
import java.io.*;
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
		
		boolean ide = false, http = false;
		String name = null;
		for (String arg : args) {
			if (arg.equals("--http")) {
				http = true;
			} else if (arg.equals("--ide")) {
				ide = true;
			} else {
				name = arg;
			}
		}
		if (ide) {
		
		} else if (http) {
			main.http(name);
		} else if (name != null) {
			main.run(name);
		} else {
			System.out.println("Usage: hyd <path> [--http] [--ide]");
		}
	}
	
	public void run(String name) {
		Interpreter hy = new Interpreter();
		hy.initialize(name);
		while (hy.canRunMore()) {
			hy.tick();
		}

	}
	
	public void http(String name) {
		HttpComponent hc = new HttpComponent(8000);
		hc.begin(name);
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
			System.out.println("[" + directive.getSplit().get(i) + "]");
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
		arg.setString(kb.nextLine());kb.close();
		action.call(eh, new Value[]{ arg });
		if (eh.hasErrors()) {
			System.out.println(eh.generateReport());
		} else {
			System.out.println("\nfinished call successfully");
		}
		
		
	}
}
