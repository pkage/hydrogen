package org.kagelabs.pkbasic;

/**
 * Directive Processor
 * @author Patrick Kage
 *
 */
public class DirectiveProcessor {
	private int head;
	private DirectiveBundle bundle;
	private ActionProcessor ap;
	private Context global;
	
	public DirectiveProcessor() {
		// TODO: initialize all variables
		
	}
	
	public void reset() {
		// TODO: Reset DirectiveProcessor state
		// this means reset the head
	}
	
	public boolean tick(ErrorHandler eh) {
		// TODO : execute one tick
		return false;
	}
	
	private int getLabelLocation(String id) {
		// TODO : find a thing from id
		return -1;
	}
	
	public Value resolveReference(Reference ref) {
		// TODO : resolve references out of global context
		return new Value(VarType.INVALID);
	}
	
	private Value toValue(String str) {
		// TODO : turn a string into a value
		return new Value(VarType.INVALID);
	}
	
	public void registerActionProvider(ActionProvider ap) {
		// TODO : add a new action provider
	}
	
	private ComparationResult compare(Reference r1, Reference r2) {
		return ComparationResult.EQUALTO;
	}
	
	private ComparationResult compare(Value v1, Value v2) {
		return ComparationResult.EQUALTO;
	}
}
