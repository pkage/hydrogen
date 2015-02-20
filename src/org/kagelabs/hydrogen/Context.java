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
	
	public String dump() {
		String out = new String();
		for (Reference ref : mem.keySet()) {
			out += "\t" + ref.toString() + " : " + mem.get(ref).toString() + "\n";
		}
		return out;
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
	
	private Reference normalize(Reference ref) {
		for (Reference r : mem.keySet()) {
			if (r.toString().equals(ref.toString())) {
				return r;
			}
		}
		return ref;
	}
	
	public boolean contains(Reference ref) {
		return mem.containsKey(normalize(ref));
			
	}
	
	public Value getVariable(Reference ref) {
		System.out.println(this.dump());
		if (!this.contains(ref)) {
			return new Value(VarType.INVALID);
		}
		return mem.get(normalize(ref));
	}
	
	public ComparationResult compare(Reference ref1, Reference ref2) {
		if (!contains(ref1) || !contains(ref2)) {
			return ComparationResult.INVALID;
		}
		return getVariable(ref1).compareTo(getVariable(ref2));
	}
	
	
}
