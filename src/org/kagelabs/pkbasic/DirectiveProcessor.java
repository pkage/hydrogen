package org.kagelabs.pkbasic;

/**
 * Directive Processor
 * @author Patrick Kage
 * @author Caleb
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
		if(str.startsWith("$")) {
			Value value = new Value(VarType.STRING);
			value.setString(global.getVariable(new Reference(str.substring(1,  str.length()-2), VarType.STRING)).getString());
			return value;
		}
		else if(str.startsWith("\"")) {
			Value value = new Value(VarType.STRING);
			value.setString(str.substring(1, str.length()-2));
			return value;
		}
		else if(str.startsWith("#")) {
			Value value = new Value(VarType.NUMBER);
			value.setNumber(global.getVariable(new Reference(str.substring(1, str.length()-2), VarType.NUMBER)).getNumber());
			return value;
		}
		Value value = new Value(VarType.NUMBER);
		double num = 0;
		try {
			num = Double.parseDouble(str);
		} catch(NumberFormatException nfe) {
			return new Value(VarType.INVALID);
		}
		value.setNumber(num);
		return value;
		}
	
	public void registerActionProvider(ActionProvider ap) {
		this.ap.registerActionProvider(ap);
	}
	
	private ComparationResult compare(Reference r1, Reference r2) {
		return ComparationResult.EQUALTO;
	}
	
	private ComparationResult compare(Value v1, Value v2) {
		return ComparationResult.EQUALTO;
	}
}
