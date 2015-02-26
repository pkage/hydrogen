package org.kagelabs.hydrogen;

import java.util.ArrayList;

/**
 * Utility class to split a directive's text into parts so it can be parsed by the classifier
 * @author Patrick Kage
 * @author Caleb
 * 
 */
public class SplitDirective extends ArrayList<String> {
	
	private static final long serialVersionUID = 1L;

	SplitDirective() {
		super();
	}
	
	SplitDirective(String line) {
		super();
		split(line);
	}
	
	public void split(String line) {
		String tmp = new String();
		boolean isQuote = false;
		// TODO : make this work with quotations
		// TODO : Patrick make your comments intelligent
		for (int c = 0; c < line.length(); c++) {
			char cAt = line.charAt(c);
			if((cAt!=' ')||(cAt==' '&&isQuote==true)) {tmp += cAt;}
			if (((cAt == ' '||c==line.length()-1)&&isQuote==false)||((cAt == '\"')&&(c==line.length()-1))) {
				this.add(tmp);
				tmp = "";
			}
			else if(cAt == '\"') {
				isQuote = !isQuote;
			}
		}
	}
}
