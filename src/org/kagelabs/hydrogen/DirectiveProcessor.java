package org.kagelabs.hydrogen;

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
	private boolean finished;
	
	public DirectiveProcessor() {
		reset();
		this.bundle = new DirectiveBundle();
		this.ap = new ActionProcessor();
		this.global = new Context();
		
	}
	
	public DirectiveProcessor(DirectiveBundle db) {
		reset();
		this.bundle = db;
		this.ap = new ActionProcessor();
		this.global = new Context();
	}
	
	public void reset() {
		this.head = 0;
	}
	
	public boolean tick(ErrorHandler eh) {
		if(head < bundle.getSize()) {
			switch(bundle.get(head).getType()) {
			case ASSIGNMENT:
				//TODO
				break;
			case BLANK:
				break;
			case CALLEXTERNAL:
				//TODO
				break;
			case COMPARATION:
				//TODO
				break;
			case GOTO:
				for(int i = 0; i < bundle.getSize(); i++) {
					if((bundle.get(i).getType() == DirectiveType.LABEL)&&(bundle.get(i).getSplit().get(1).equals(bundle.get(head).getSplit().get(1)))) {
						head = i;
						return true;
					}
				}
				break;
			case INVALID:
				eh.addError(new Error("Syntax Error", "Invalid Directive Type", "Directive Processor"));
				return false;
			case LABEL:
				break;
			case LOADEXTERNAL:
				//TODO
				break;
			case SUBROUTINECALL:
				eh.addError(new Error("Syntax Error", "Directive Type Not Supported", "Directive Processor"));
				return false;
			case SUBROUTINEDEFINITION:
				eh.addError(new Error("Syntax Error", "Directive Type Not Supported", "Directive Processor"));
				return false;
			case TERMINATION:
				this.finished = true;
				return true;
			default:
				eh.addError(new Error("Syntax Error", "Directive Type Not Found", "Directive Processor"));
				return false;
			}
			
			head++;
			return true;
		}
		return false;
	}
	
	private int getLabelLocation(String id) {
		for(int i = 0; i < bundle.getSize(); i++) {
			if(bundle.get(i).getSplit().get(0).equals("label")&&bundle.get(i).getSplit().get(1).equals(id)) {
				return i;
			}
		}
		return -1;
	}
	
	public Value resolveReference(Reference ref) {
		if(!global.contains(ref)) {
			return new Value(VarType.INVALID);
		}
		return global.getVariable(ref);
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

	public boolean isFinished() {
		return finished;
	}
}
