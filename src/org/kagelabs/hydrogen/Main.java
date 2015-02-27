package org.kagelabs.hydrogen;
import java.io.*;
import java.util.Scanner;

import javax.swing.JFrame;

/**
 * Program entry point
 * @author Patrick Kage
 *
 */
public class Main {
	public static void main(String[] args) {
		//System.out.println("It's alive!");


		Main main = new Main();

		boolean ideb = false, http = false;
		String name = null;
		for (String arg : args) {
			if (arg.equals("--http")) {
				http = true;
			} else if (arg.equals("--ide")) {
				ideb = true;
			} else {
				name = arg;
			}
		}
		if (ideb) {
			IDE ide = new IDE();
			ide.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ide.setVisible(true);
		} else if (http) {
			main.http(name);
		} else if (name != null) {
			main.run(name);
		} else {
			main.live();
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
	public void live() {
		Scanner kb = new Scanner(System.in);
		System.out.println("Hydrogen (version 0.1.2a)\nLive mode initialized.\nType \"live.abort\" to exit.");
		DirectiveProcessor dp = new DirectiveProcessor(new DirectiveBundle());
		String input = "";
		ErrorHandler eh = new ErrorHandler();
		boolean resetEH = false;
		while (!input.equals("live.abort")) {
			Directive dir = new Directive(input);
			dir = DirectiveClassifier.classifyDirective(dir);
			dp.addDirective(dir);
			if (resetEH) {
				eh = new ErrorHandler();
				resetEH = false;
				dp.forceHeadAdvance();
			}
			dp.tick(eh);
			if (eh.hasErrors()) {
				System.out.println("------------------\nErrors detected! Type \"live.dump\" to generate an error dump. length: " + eh.size());
			}
			System.out.print(">>> ");
			input = kb.nextLine();
			if (input.equals("live.dump")) {
				
				if (eh.hasErrors()) {
					System.out.println(eh.generateReport());
					resetEH = true;

				} else {
					System.out.println("nothing to report.");
				}
				input = "";
			}
		}

	}
}
