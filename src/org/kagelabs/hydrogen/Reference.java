package org.kagelabs.hydrogen;

/**
 * Reference for a variable, part of R-V pair
 * @author Patrick Kage
 * @see Context
 * @see Value
 * <p>
 * 	Variables are stored as a reference-value pair by the context manager.
 * </p>
 */
public class Reference {
	private String name;
	private char prefix;
	
	public void setType(VarType type) {
		this.prefix = (type == VarType.NUMBER) ? '#' : '$';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public char getPrefix() {
		return prefix;
	}

	public boolean equals(Reference ref) {
		System.out.println("equals comparing " + this.toString() + " to " + ref.toString());
		return (ref.name.equals(this.name) && ref.prefix == this.prefix);
	}

	Reference(String name, VarType type) {
		this.prefix = (type == VarType.NUMBER) ? '#' : '$';
		this.name = name;
	}
	
	Reference(String name, char prefix) {
		this.prefix = prefix; // dangerous
		this.name = name;
	}
	
	static boolean isValid(String name, char prefix) {
		if (prefix != '#' && prefix != '$') {
			return false;
		}
		if (!isValid(name)) {
			return false;
		}
		
		return true;
	}
	static boolean isValid(String name) {
		String allowed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		for (int c = 0; c < name.length(); c++) {
			if (allowed.indexOf(name.charAt(c)) == -1) {
				return false;
			}
		}
		
		return true;
	}
	
	static boolean isValid(String name, VarType type) {
		if (!isValid(name)) {
			return false;
		}
		if (type != VarType.NUMBER && type != VarType.STRING) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		return prefix + name;
	}
}
 