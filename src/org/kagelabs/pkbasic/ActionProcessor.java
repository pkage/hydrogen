package org.kagelabs.pkbasic;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ActionProcessor {
	private ArrayList<ActionProvider> actionProviders;
	
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
}
