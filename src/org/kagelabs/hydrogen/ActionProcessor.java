package org.kagelabs.hydrogen;

/**
 * Class to keep track of errors emitted by the project in various states of failure.
 * @author Patrick Kage 
 * @author Alex Zheng
 *
 */

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

public class ActionProcessor {
	private ArrayList<ActionProvider> actionProviders;
	private HashMap<ActionMetadata, Action> actions;
	
	public ActionProcessor(){
		this.actionProviders = new ArrayList<ActionProvider>();
		this.actions = new HashMap<ActionMetadata, Action>();
	}
	
	public boolean importLibrary(ErrorHandler eh, String name) {
		try {
			
			// get the location of the aux binaries
			URL location[] = {new URL("file:///home/patrick/workspace/pkbasic/bin/org/kagelabs/hydrogen/stl/"),
							  new URL("file:///usr/local/hydrogen/lib/"),
							  new URL("http://packages.kagelabs.org/host/")
							 };
			
			// create a class loader with the location
			URLClassLoader loader = new URLClassLoader(location);
			
			// extract the class
			Class<?> lib = loader.loadClass(name);
			System.out.println("loaded library");
			
			// get the ActionProvider provider 
			Method extractAP = lib.getDeclaredMethod("getActionProvider");
			System.out.println("loaded method");
			
			ActionProvider ap = (ActionProvider) extractAP.invoke(lib.newInstance());
			System.out.println("called extractor");
			
			actionProviders.add(ap);
			
			loader.close();
	
		} catch (Exception e) {
			e.printStackTrace();
			eh.addError(new Error("LibraryImport", "Failed to import " + name, "ActionProcessor"));
			return false;
		}
		
		// rebuild action library
		this.actions = pullActions();
		
		return true;
	}
	
	public void registerActionProvider(ActionProvider ap) {
		actionProviders.add(ap);
		this.actions = pullActions();
	}

	private HashMap<ActionMetadata, Action> pullActions() {
		// TODO Auto-generated method stub
		HashMap<ActionMetadata, Action> returnedHashMap = new HashMap<ActionMetadata, Action>();
		for(int i=0; i<this.actionProviders.size(); i++){
		returnedHashMap.putAll(actionProviders.get(i).getActionDictionary());
		}
		return returnedHashMap; 
	}
	
	public HashMap<ActionMetadata, Action> getActions() {
		return this.actions;
	}
	
	public Action getAction(ActionMetadata am) {
		return this.actions.get(am);
	}
	
	public Action getAction(String name) {
		for (ActionMetadata am : this.actions.keySet()) {
			if (am.getName().equals(name)) {
				return getAction(am);
			}
		}
		return null;
	}
	
	public void initAllActionProviders(ErrorHandler eh) {
		for (int c = 0; c < this.actionProviders.size(); c++) {
			this.actionProviders.get(c).init(eh);
		}
		this.actions = pullActions();
	}
}