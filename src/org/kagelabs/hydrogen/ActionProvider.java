package org.kagelabs.hydrogen;

import java.util.HashMap;

/**
 * An interface that defines an action provider resource (eg a library)
 * @author Patrick Kage
 *
 */

public interface ActionProvider {
	public HashMap<ActionMetadata, Action> getActionDictionary();
	public void init(ErrorHandler eh);
	public void run(ErrorHandler eh, ActionMetadata am);
	
}
