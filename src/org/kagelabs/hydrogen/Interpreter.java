package org.kagelabs.hydrogen;

/**
 * Sandboxed pkbasic interpreter
 * @author Patrick Kage
 *
 */
public class Interpreter {
	private LineBundle lines;
	private Context global;
	private DirectiveProcessor dirp;
	private ErrorHandler eh;
	private boolean errored;
	private boolean done;
	
	public Interpreter() {
		reset();
		eh = new ErrorHandler();
	}
	
	public void reset() {
		global = new Context();
		errored = false;
		done = false;
	}
	
	public void initialize(String filename) {
		Preprocessor pp = new Preprocessor();
		lines = pp.processFile(filename);
		DirectiveBundle db = new DirectiveBundle();
		for (int c = 0; c < lines.length(); c++) {
			Directive current = new Directive(lines.get(c));
			current = DirectiveClassifier.classifyDirective(current);
			
			db.add(current);
		}
		dirp = new DirectiveProcessor(db);
		dirp.reset();
	}
	
	public void initialize(LineBundle lb) {
		Preprocessor pp = new Preprocessor();
		lines = pp.processLineBundle(lb);
		DirectiveBundle db = new DirectiveBundle();
		for (int c = 0; c < lines.length(); c++) {
			Directive current = new Directive(lines.get(c));
			current = DirectiveClassifier.classifyDirective(current);
			
			db.add(current);
		}
		dirp = new DirectiveProcessor(db);
		dirp.reset();
	}
	
	public void tick() {
		if (!errored && !done) {
			dirp.tick(eh);
		}
		if (eh.hasErrors()) {
			System.out.println("\nSomething bad happened! Heres what we know: \n" + eh.generateReport());
			errored = true;
		}
		if (dirp.isFinished()) {
			done = true;
		}
	}
	
	public Context getGlobalContext() {
		return global;
	}
	
	public LineBundle getFullLines() {
		return lines;
	}
	
	public boolean canRunMore() {
		return !(done || errored);
	}

	public boolean isErrored() {
		return errored;
	}

	public boolean isDone() {
		return done;
	}
	
	public void registerActionProvider(ActionProvider ap) {
		this.dirp.addActionProvider(ap);
	}
}
