package org.kagelabs.hydrogen;

import java.util.HashMap;

import org.kagelabs.hydrogen.Reference;


/**
 * Memory context storage
 * @author Patrick Kage
 *
 */
public class Context {
	private HashMap<Reference, Value> mem;
	Context() {
		mem = new HashMap<Reference, Value>();
	}
	
	public boolean setVariable(Reference ref, Value val) {
		mem.put(ref, val);
		return true;
	}
	
	public boolean setVariable(String name, Value val) {
		if (!Reference.isValid(name, val.getType())) {
			return false;
		}
		Reference ref = new Reference(name, val.getType());
		return setVariable(ref, val);
	}
	
	public Value getVariable(String name, char prefix) {
		if (!Reference.isValid(name, prefix)) {
			return new Value(VarType.INVALID);
		}
		return getVariable(new Reference(name, prefix));
	}
	
	public boolean contains(Reference ref) {
		return mem.containsKey(ref);
			
	}
	
	public Value getVariable(Reference ref) {
		if (this.contains(ref)) {
			return new Value(VarType.INVALID);
		}
		return mem.get(ref);
	}
	
	public ComparationResult compare(Reference ref1, Reference ref2) {
		if (!contains(ref1) || !contains(ref2)) {
			return ComparationResult.INVALID;
		}
		return getVariable(ref1).compareTo(getVariable(ref2));
	}
	
	
}
