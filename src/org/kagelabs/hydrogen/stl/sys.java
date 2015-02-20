package org.kagelabs.hydrogen.stl;

import org.kagelabs.hydrogen.*;
import org.kagelabs.hydrogen.Error;

import java.io.IOException;
import java.util.HashMap;

public class sys {
        public ActionProvider getActionProvider() {
                class SysActionProvider implements ActionProvider {
                        HashMap<ActionMetadata, Action> actionMap;

                        SysActionProvider() {
                                actionMap = new HashMap<ActionMetadata, Action>();
                        }
    
                        public void init(ErrorHandler eh) {
                                class Sys implements Action {
                                        ActionMetadata meta;
                                        Sys() {
                                                meta = new ActionMetadata();
                                                meta.setName("sys");
                                                meta.setReturnPrefix('\0');
                                        }
                                        public ActionMetadata getMetadata() {
                                                return meta;
                                        }
                                        public void init(ErrorHandler eh) {
                                                // do nothing
                                        }
                                        public Value call(ErrorHandler eh, Value[] values) {
                                        		if (values.length != 1 || values[0].getType() != VarType.STRING) {
                                        			eh.addError(new Error("Invalid argument!", "sys can only accept one string", "sys"));
                                        		}
                                        		try {
													Runtime.getRuntime().exec(values[0].getString());
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace(); 
												}
                                            	return new Value(VarType.INVALID);
                                        }
                                }
                                Sys sys = new Sys();
                                this.actionMap.put(sys.getMetadata(), sys);
                        }
    

                        public HashMap<ActionMetadata, Action> getActionDictionary() {
                                return this.actionMap;
                        }

						@Override
						public void run(ErrorHandler eh, ActionMetadata am) {
							// TODO Auto-generated method stub
						}
                }
                
                return new SysActionProvider();
        }

	public String getProviderName() {
		return "SysActionProvider";
	}

	public String[] getActionNames() {
		return new String[]{ "Sys" };
	}
}
