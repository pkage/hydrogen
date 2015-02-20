package org.kagelabs.hydrogen;

import java.util.ArrayList;

/**
 * A storage class for a bundle of directives
 * @author Patrick Kage
 *
 */
public class LineBundle {
	private ArrayList<String> bundle;
	
	LineBundle() {
		bundle = new ArrayList<String>();
	}
	
	public void add(String str) {
		bundle.add(str);
	}
	
	public String get(int index) {
		if (bundle.size() <= index) return "";
		return bundle.get(index);
	}
	
	public boolean replace(int index, String str) {
		if (bundle.size() <= index) return false;
		bundle.set(index, str);
		return true;
	}
	
	public int length() {
		return bundle.size();
	}
	
	public boolean set(int index, String str) {
		if (index < 0 || index >= bundle.size()) return false;
		bundle.set(index, str);
		return true;
	}
}
