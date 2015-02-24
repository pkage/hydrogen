package org.kagelabs.hydrogen.stl;
import org.kagelabs.hydrogen.*;
import org.kagelabs.hydrogen.Error;
import java.util.HashMap;

public class math {

	        public ActionProvider getActionProvider() {
	                class MathActionProvider implements ActionProvider {
	                        HashMap<ActionMetadata, Action> actionMap;

	                        MathActionProvider() {
	                                actionMap = new HashMap<ActionMetadata, Action>();
	                        }
	    
	                        public void init(ErrorHandler eh) {
	                                class Add implements Action {
	                                        ActionMetadata meta;
	                                        Add() {
	                                                meta = new ActionMetadata();
	                                                meta.setName("+");
	                                                meta.setReturnPrefix('#');
	                                        }
	                                        public ActionMetadata getMetadata() {
	                                                return meta;
	                                        }
	                                        public void init(ErrorHandler eh) {
	                                                // do nothing
	                                        }
	                                        public Value call(ErrorHandler eh, Value[] values) {
	                                        	
	                                                for (Value v : values) {
	                                                	if (v.getType() != VarType.NUMBER) {
	                                                		eh.addError("Invalid operation!", "Can only add numbers!", "math");
	                                                		return new Value(VarType.INVALID);
	                                                	}
	                                                }
	                                        		Value ret = new Value(VarType.NUMBER);
	                                        		ret.setNumber(0);
	                                        		for (Value v : values) {
	                                        			ret.setNumber(ret.getNumber() + v.getNumber());
	                                        		}

	                                                return ret;
	                                        }
	                                }
	                                Add add = new Add();
	                                this.actionMap.put(add.getMetadata(), add);

	                                class Subtract implements Action {
                                        ActionMetadata meta;
                                        Subtract() {
                                                meta = new ActionMetadata();
                                                meta.setName("-");
                                                meta.setReturnPrefix('#');
                                        }
                                        public ActionMetadata getMetadata() {
                                                return meta;
                                        }
                                        public void init(ErrorHandler eh) {
                                                // do nothing
                                        }
                                        public Value call(ErrorHandler eh, Value[] values) {
                                                for (Value v : values) {
                                                	
                                                	if (v.getType() != VarType.NUMBER) {
                                                		eh.addError("Invalid operation!", "Can only add numbers!", "math");
                                                		return new Value(VarType.INVALID);
                                                	}
                                                }
                                        		Value ret = new Value(VarType.NUMBER);
                                        		ret.setNumber(0);
                                        		for (Value v : values) {
                                        			ret.setNumber(ret.getNumber() - v.getNumber());
                                        		}
                                                return ret;
                                        }
	                                }
	                                Subtract sub = new Subtract();
	                                this.actionMap.put(sub.getMetadata(), sub);
	                                
	                        }
	                        

	                        public HashMap<ActionMetadata, Action> getActionDictionary() {
	                                return this.actionMap;
	                        }

							@Override
							public void run(ErrorHandler eh, ActionMetadata am) {
								// TODO Auto-generated method stub
							}
	                }
	                
	                return new MathActionProvider();
	        }
	        public String getProviderName() {
	        	return "MathActionProvider";
	        }
	        public String[] getActionNames() {
	        	return new String[]{ "Add", "Subtract" };
	        }
	}


