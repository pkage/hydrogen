package org.kagelabs.pkbasic;

/**
 * Utility class to determine directive's statement type
 * @author Patrick Kage
 * @author Caleb
 *
 */
public class DirectiveClassifier {
	public static Directive classifyDirective(Directive input) {
		
		if(input.getSplit().get(0).startsWith("$")||input.getSplit().get(0).startsWith("#")) {
			input.setType(DirectiveType.ASSIGNMENT);
			return input;
		}

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
		case "goto":
			input.setType(DirectiveType.GOTO);
			break;
		case "label":
			input.setType(DirectiveType.LABEL);
			break;
		case "abort":
			input.setType(DirectiveType.TERMINATION);
			break;
		default:
			input.setType(DirectiveType.CALLEXTERNAL);
			break;
		}
		
		return input;
	}
}
