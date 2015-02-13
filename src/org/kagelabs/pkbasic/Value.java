package org.kagelabs.pkbasic;

import org.kagelabs.pkbasic.VarType;
import org.kagelabs.pkbasic.ComparationResult;

/**
 * Simple value storage. Part of the R-V pairing.
 * @author Patrick Kage
 * @see Reference
 * @see Context
 * <p>
 * 	Used in conjunction with a Reference by the context manager to create a variable
 * </p>
 */
public class Value {
	private double dval;
	private String sval;
	private VarType type;
	
	public double getNumber() {
		return dval;
	}
	public void setNumber(double dval) {
		this.dval = dval;
	}
	public String getString() {
		return sval;
	}
	public void setString(String sval) {
		this.sval = sval;
	}
	public VarType getType() {
		return type;
	}
	public void setType(VarType type) {
		this.type = type;
	}
	
	ComparationResult compareTo(Value v2) {
		if (v2.getType() != this.getType() || this.getType() == VarType.INVALID) {
			return ComparationResult.INVALID;
		}
		
		if (this.getType() == VarType.STRING) {
			if (v2.getString().compareTo(this.getString()) == 0) {
				return ComparationResult.EQUALTO;
			} else {
				return ComparationResult.NOTEQUALTO;
			}
		} else {
			double n1 = this.getNumber();
			double n2 = v2.getNumber();
			if (n1 == n2) {
				return ComparationResult.EQUALTO;
			} else if (n1 > n2) {
				return ComparationResult.GREATERTHAN;
			} else if (n1 < n2) {
				return ComparationResult.LESSTHAN;
			}
		}
		
		return ComparationResult.INVALID; // something went wrong, should never execute;
	}
	
	public Value(VarType type) {
		this.setType(type);
	}
}
