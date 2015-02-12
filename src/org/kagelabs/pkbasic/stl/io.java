package org.kagelabs.pkbasic.stl;

import org.kagelabs.pkbasic.*;
import org.kagelabs.pkbasic.Error;

import java.util.HashMap;

class io {
        ActionProvider getActionProvider() {
                class IOActionProvider implements ActionProvider {
                        HashMap<ActionMetadata, Action> actionMap;

                        IOActionProvider() {
                                actionMap = new HashMap<ActionMetadata, Action>();
                        }
    
                        public void init(ErrorHandler eh) {
                                class Print implements Action {
                                        ActionMetadata meta;
                                        Print() {
                                                meta = new ActionMetadata();
                                                meta.setName("print");
                                                meta.setReturnPrefix('\0');
                                        }
                                        public ActionMetadata getMetadata() {
                                                return meta;
                                        }
                                        public void init(ErrorHandler eh) {
                                                // do nothing
                                        }
                                        public Value call(ErrorHandler eh, Value[] values) {
                                                for (int c = 0; c < values.length; c++) {
                                                        if (values[c].getType() == VarType.INVALID) {
                                                                eh.addError(new Error("Invalid print!", "The variable you are trying to print is invalid!", "io"));
                                                                continue;
                                                        }
                                                        System.out.println( ((values[c].getType() == VarType.NUMBER) ? values[c].getNumber() : values[c].getString()) );
                                                }
                                                return new Value(VarType.INVALID);
                                        }
                                }
                                Print print = new Print();
                                this.actionMap.put(print.getMetadata(), print);
                        }
    

                        public HashMap<ActionMetadata, Action> getActionDictionary() {
                                return this.actionMap;
                        }

						@Override
						public void run(ErrorHandler eh, ActionMetadata am) {
							// TODO Auto-generated method stub
						}
                }
                
                return new IOActionProvider();
        }
}
