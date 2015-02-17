package org.kagelabs.hydrogen;


/**
 * Single directive for the directive processor
 * @author Patrick Kage
 * @author Caleb
 *
 */
public class Directive {
	private DirectiveType stype;
	private String full;
	private SplitDirective split;
	private String filename; // error reporting
	private int linenumber; // error reporting

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getLinenumber() {
		return linenumber;
	}

	public void setLinenumber(int linenumber) {
		this.linenumber = linenumber;
	}


	
	Directive() {
		this.full = new String();
		this.stype = DirectiveType.INVALID;
		this.refreshSplit();
	}

	public Directive(String full) {
		this.full = full;
		this.refreshSplit();
	}

	Directive(DirectiveType type, String full) {
		this.full = full;
		this.stype = type;
		this.refreshSplit();
	}
	
	public DirectiveType getType() {
		return stype;
	}

	public void setType(DirectiveType stype) {
		this.stype = stype;
	}

	public String getFull() {
		return full;
	}

	public void setFull(String full) {
		this.full = full;
	}

	public SplitDirective getSplit() {
		return split;
	}
	
	public void refreshSplit() {
		split = new SplitDirective(this.full);
	}

	
}
