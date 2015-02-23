package org.kagelabs.hydrogen;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.model.core.Ref;

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
			//System.out.println("exec " + bundle.get(head).getFull());
			switch(bundle.get(head).getType()) {
			case ASSIGNMENT:
				if (bundle.get(head).getSplit().size() != 3) {
					eh.addError("Invalid assignment", "Invalid arg count", "Directive Processor");
				}
				//System.out.println("Setting " + toReference(bundle.get(head).getSplit().get(0).toString() + " to " + ( (toValue(bundle.get(head).getSplit().get(2)).getType() == VarType.NUMBER) ? toValue(bundle.get(head).getSplit().get(2)).getNumber() : toValue(bundle.get(head).getSplit().get(2)).getString() )));
				global.setVariable(toReference(bundle.get(head).getSplit().get(0)), toValue(bundle.get(head).getSplit().get(2)));
				//System.out.println(global.dump());
				break;
			case BLANK:
				break;
			case CALLEXTERNAL:
				Action act;
				if (ap.hasAction(bundle.get(head).getSplit().get(0))) {
					act = ap.getAction(bundle.get(head).getSplit().get(0));
				} else {
					eh.addError(new Error("Unknown Token", "Unregistered token execution", "Directive Processor"));
					return false;
				}
				ArrayList<Value> args = new ArrayList<Value>();
				for (int i = 1; i < bundle.get(head).getSplit().size(); i++) {
					args.add(this.toValue(bundle.get(head).getSplit().get(i)));
				}
				Value[] argarr = args.toArray(new Value[args.size()]);
				act.call(eh, argarr);
				
				break;
			case COMPARATION:
				ComparationResult cr = toValue(bundle.get(head).getSplit().get(1)).compareTo(toValue(bundle.get(head).getSplit().get(3)));
				ComparationType ct = toComparationType(bundle.get(head).getSplit().get(2));
				if (cr == ComparationResult.INVALID) {
					eh.addError("Invalid Comparation", "These two values can't be compared.", "Directive Processor");
					return false;
				}
				if (ct == ComparationType.INVALID) {
					eh.addError("Invalid Comparison", "Invalid comparation operand", "Directive Processor");
				}
				if ((ct == ComparationType.EQUALTO || ct == ComparationType.GREATERTHANOREQUALTO || ct == ComparationType.LESSTHANOREQUALTO) && cr == ComparationResult.EQUALTO) {
					//System.out.println("positive comparation");
					break;
				} else if (ct == ComparationType.GREATERTHAN && cr == ComparationResult.GREATERTHAN) {
					//System.out.println("positive comparation");
					break;
				} else if (ct == ComparationType.LESSTHAN && cr == ComparationResult.LESSTHAN) {
					//System.out.println("positive comparation");
					break;
				} else if (ct == ComparationType.NOTEQUALTO && cr != ComparationResult.EQUALTO) {
					//System.out.println("positive comparation");
					break;
				}
				//System.out.println("negative comparation");
				head++;
				break;
			case GOTO:
				for(int i = 0; i < bundle.getSize(); i++) {
					if (bundle.get(i).getType() == DirectiveType.LABEL) {
						//System.out.println("found label at " + i);
						if (toValue(bundle.get(i).getSplit().get(1)).compareTo(toValue(bundle.get(head).getSplit().get(1))) == ComparationResult.EQUALTO) {
							head = i;
							break;
						}
					}
				}
				break;
			case INVALID:
				eh.addError(new Error("Syntax Error", "Invalid Directive Type", "Directive Processor"));
				return false;
			case LABEL:
				break;
			case LOADEXTERNAL:
				//TODO : make less shitty
				//System.out.println("Loading " + bundle.get(head).getSplit().get(1));
				ap.importLibrary(eh, bundle.get(head).getSplit().get(1));
				//ap.initAllActionProviders(eh);
				
				break;
			
			case TERMINATION:
				this.finished = true;
				return true;
			case CALLEXTERNALWITHRET:
				Action act2;
				//System.out.println("Call external \"" + bundle.get(head).getSplit().get(2) + "\"");
				
				if (ap.hasAction(bundle.get(head).getSplit().get(2))) {
					act2 = ap.getAction(bundle.get(head).getSplit().get(2));
				} else {
					eh.addError(new Error("Unknown Token", "Unregistered token execution", "Directive Processor"));
					return false;
				}
				ArrayList<Value> args2 = new ArrayList<Value>();
				for (int i = 1; i < bundle.get(head).getSplit().size(); i++) {
					args2.add(this.toValue(bundle.get(head).getSplit().get(i)));
				}
				Reference ret = toReference(bundle.get(head).getSplit().get(0));
				if (ret.getPrefix() != act2.getMetadata().getReturnPrefix()) {
					eh.addError("Invalid return type", "Can't store return in that variable", "Directive Processor");
					return false;
				}
				Value[] argarr2 = args2.toArray(new Value[args2.size()]);
				global.setVariable(toReference(bundle.get(head).getSplit().get(0)), act2.call(eh, argarr2));
				break;
			case SUBROUTINECALL:
			case SUBROUTINEDEFINITION:
			default:
				eh.addError(new Error("Syntax Error", "Directive Type Not Found", "Directive Processor"));
				return false;
			}
			
			head++;
			return true;
		}
		return false;
	}
	
	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
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
		//System.out.println("resolving " + str);
		if(str.startsWith("$")) {
			Value value = new Value(VarType.STRING);
			//System.out.println("global does " + ( (global.contains(new Reference(str.substring(1), VarType.STRING))) ? "" : "not " ) + "contain " + (new Reference(str.substring(1), VarType.STRING)).toString());
			value.setString(global.getVariable(new Reference(str.substring(1), VarType.STRING)).getString());
			//System.out.println("resolved value as " + value.getString());
			return value;
		}
		else if(str.startsWith("\"")) {
			Value value = new Value(VarType.STRING);
			value.setString(str.substring(1, str.length()-1));
			return value;
		}
		else if(str.startsWith("#")) {
			Value value = new Value(VarType.NUMBER);
			value.setNumber(global.getVariable(new Reference(str.substring(1), VarType.NUMBER)).getNumber());
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
	
	private Reference toReference(String str) {
		if(str.startsWith("$")) {
			return new Reference(str.substring(1), VarType.STRING);
		}
		else if(str.startsWith("#")) {
			return new Reference(str.substring(1), VarType.NUMBER);
		} else {
			return new Reference("", VarType.INVALID);
		}
	}
	
	public void registerActionProvider(ActionProvider ap) {
		this.ap.registerActionProvider(ap);
	}
	
	private ComparationType toComparationType(String str) {
		if (str.equals("==")) {
			return ComparationType.EQUALTO;
		} else if (str.equals("!=")) {
			return ComparationType.NOTEQUALTO;
		} else if (str.equals("<")) {
			return ComparationType.LESSTHAN;
		} else if (str.equals(">")) {
			return ComparationType.GREATERTHAN;
		} else if (str.equals("<=")) {
			return ComparationType.LESSTHANOREQUALTO;
		} else if (str.equals(">=")) {
			return ComparationType.GREATERTHANOREQUALTO;
		}
		return ComparationType.INVALID;
	}

	public boolean isFinished() {
		return finished;
	}
	
	public void addActionProvider(ActionProvider adict) {
		this.ap.registerActionProvider(adict);
	}
}
