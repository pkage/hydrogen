package org.kagelabs.pkbasic;

/**
 * Preprocessor
 * @author Patrick Kage
 *
 */
public class Preprocessor {
	public LineBundle processFile(String fn) {	
		LineBundle lb = this.resolveFile(fn);
		while (this.containsInsert(lb)) {
			lb = replaceLineWithBundle(lb, firstInsertLocation(lb), resolveFile(getFilename(lb, firstInsertLocation(lb))));
		}
		return lb;
	}
	
	
	private String getFilename(LineBundle lb, int index) {
		String fn = lb.get(index);
		String split[] = fn.split(" ");
		return split[1];
	}
	
	private LineBundle resolveFile(String fn) {
		FileExtractor fe = new FileExtractor();
		return fe.extract(fn);
	}
	
	private int firstInsertLocation(LineBundle lb) {
		for (int c = 0; lb.length() > 0; c++) {
			String str = lb.get(c);
			if (str.startsWith("insert")) {
				return c;
			}
		}
		return -1;
	}
	
	private boolean containsInsert(LineBundle lb) {
		return (firstInsertLocation(lb) != -1);
	}
	
	private LineBundle replaceLineWithBundle(LineBundle lb1, int index, LineBundle lb2) {
		LineBundle ret = new LineBundle();
		for (int c = 0; c < lb1.length(); c++) {
			if (c != index) {
				ret.add(lb1.get(c));
			} else {
				for (int d = 0; d < lb2.length(); d++) {
					ret.add(lb2.get(d));
				}
			}
		}
		
		return ret;
	}
}
