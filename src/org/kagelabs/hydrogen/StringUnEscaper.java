package org.kagelabs.hydrogen;

public class StringUnEscaper {
	
	public static String unescape(String literal) {
		String result = new String();
		for (int c = 0; c < literal.length(); c++) {
			if (literal.charAt(c) == '\\' && c + 1 != literal.length()) {
				char ch = literal.charAt(c + 1);
				switch (ch) {
				case 'n': 
					result += '\n';
					break;
				case 't':
					result += '\t';
					break;
				case 'r':
					result += '\r';
					break;
				case '\"':
					result += '\"';
					break;
				case '\\':
					result += '\\';
					break;
				default:
					break;
				}
				c++;
				continue;
			}
			result += literal.charAt(c);
		}
		return result;
	}
}
