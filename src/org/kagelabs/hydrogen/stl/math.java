package org.kagelabs.hydrogen.stl;
import org.kagelabs.hydrogen.*;
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
	                                                meta.setNamespace("");
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
                                                meta.setNamespace("");
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
                                                		eh.addError("Invalid operation!", "Can only subtract numbers!", "math");
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
	                                
	                                class Multiply implements Action {
                                        ActionMetadata meta;
                                        Multiply() {
                                                meta = new ActionMetadata();
                                                meta.setName("*");
                                                meta.setReturnPrefix('#');
                                                meta.setNamespace("");
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
                                                		eh.addError("Invalid operation!", "Can only multiply numbers!", "math");
                                                		return new Value(VarType.INVALID);
                                                	}
                                                }
                                        		Value ret = new Value(VarType.NUMBER);
                                        		ret.setNumber(0);
                                        		for (Value v : values) {
                                        			ret.setNumber(ret.getNumber() * v.getNumber());
                                        		}
                                                return ret;
                                        }
	                                }
	                                Multiply mult = new Multiply();
	                                this.actionMap.put(mult.getMetadata(), mult);
	                                
	                                class Divide implements Action {
                                        ActionMetadata meta;
                                        Divide() {
                                                meta = new ActionMetadata();
                                                meta.setName("/");
                                                meta.setNamespace("");
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
                                                		eh.addError("Invalid operation!", "Can only divide numbers!", "math");
                                                		return new Value(VarType.INVALID);
                                                	}
                                                }
                                        		Value ret = new Value(VarType.NUMBER);
                                        		ret.setNumber(0);
                                        		for (Value v : values) {
                                        			ret.setNumber(ret.getNumber() / v.getNumber());
                                        		}
                                                return ret;
                                        }
	                                }
	                                Divide div = new Divide();
	                                this.actionMap.put(div.getMetadata(), div);
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
	        	return new String[]{ "Add", "Subtract", "Divide", "Multiply" };
	        }
	}


