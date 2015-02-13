package org.kagelabs.pkbasic;

/**
 * Class to keep track of errors emitted by the project in various states of failure.
 * @author Patrick Kage 
 * @author Alex Zheng
 *
 */

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActionProcessor {
	private ArrayList<ActionProvider> actionProviders;
	private HashMap<ActionMetadata, Action> actions;
	
	public ActionProcessor(){
		this.actionProviders = new ArrayList<ActionProvider>();
		this.actions = new HashMap<ActionMetadata, Action>();
	}
	
	public boolean importLibrary(ErrorHandler eh, String refname) {
		try {
			// get the system's class loader
			ClassLoader cl = ClassLoader.getSystemClassLoader();
			// add the default package
			if (refname.indexOf('.') == -1) {
				refname += "org.kagelabs.pkbasic.stl.";
			}
			
			// what the fuck am i doing
			Class loadedClass = cl.loadClass(refname);
			Object loadedInstance = loadedClass.newInstance();
			Method getActionProvider = loadedClass.getMethod("getActionProvider", new Class[]{});
			ActionProvider ap = (ActionProvider) getActionProvider.invoke(loadedInstance);
			actionProviders.add(ap);
	
		} catch (Exception e) {
			eh.addError(new Error("LibraryImport", "Failed to import " + refname, "ActionProcessor"));
			return false;
		}
		return true;
	}

	private HashMap<ActionMetadata, Action> pullActions() {
		// TODO Auto-generated method stub
		HashMap<ActionMetadata, Action> returnedHashMap = new HashMap<ActionMetadata, Action>();
		for(int i=0; i<this.actionProviders.size(); i++){
		returnedHashMap.putAll(actionProviders.get(i).getActionDictionary());
		}
		return returnedHashMap; 
	}
}
