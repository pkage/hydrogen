package org.kagelabs.pkbasic;

/**
 * Utility class to determine directive's statement type
 * @author Patrick Kage
 * @author Caleb
 *
 */
public class DirectiveClassifier {
	public Directive classifyDirective(Directive input) {
		
		// TODO : determine directive type
		
		switch(input.getSplit().get(0)) {
		case "import":
			input.setType(DirectiveType.LOADEXTERNAL);
			break;
		case "for":
			input.setType(DirectiveType.COMPARATION);
			break;
		case "end":
			input.setType(DirectiveType.TERMINATION);
			break;
		
		}
		
		return input;
	}
}
