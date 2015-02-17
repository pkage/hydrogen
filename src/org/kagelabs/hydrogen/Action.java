package org.kagelabs.hydrogen;

public interface Action {
	public ActionMetadata getMetadata();
	public void init(ErrorHandler eh);
	public Value call(ErrorHandler eh, Value[] values);
}
