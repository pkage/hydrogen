package org.kagelabs.hydrogen.stl;

import org.kagelabs.hydrogen.*;
import org.kagelabs.hydrogen.Error;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class sys {
	public ActionProvider getActionProvider() {
		class SysActionProvider implements ActionProvider {
			HashMap<ActionMetadata, Action> actionMap;

			SysActionProvider() {
				actionMap = new HashMap<ActionMetadata, Action>();
			}

			public void init(ErrorHandler eh) {
				class Call implements Action {
					ActionMetadata meta;
					Call() {
						meta = new ActionMetadata();
						meta.setName("call");
						meta.setReturnPrefix('\0');
						meta.setNamespace("sys");
					}
					public ActionMetadata getMetadata() {
						return meta;
					}
					public void init(ErrorHandler eh) {
						// do nothing
					}
					public Value call(ErrorHandler eh, Value[] values) {
						if (values.length != 1 || values[0].getType() != VarType.STRING) {
							eh.addError(new Error("Invalid argument!", "call can only accept one string", "sys"));
						}
						try {

							Process p = Runtime.getRuntime().exec(values[0].getString());
							try {
								p.waitFor();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
							String out = new String(), line;
							while ((line = reader.readLine())!= null) {
								out += line;
							}
							System.out.println(out);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace(); 
						}
						return new Value(VarType.INVALID);
					}
				}
				Call sys = new Call();
				this.actionMap.put(sys.getMetadata(), sys);
				class XCall implements Action {
					ActionMetadata meta;
					XCall() {
						meta = new ActionMetadata();
						meta.setName("xcall");
						meta.setReturnPrefix('$');
						meta.setNamespace("sys");
					}
					public ActionMetadata getMetadata() {
						return meta;
					}
					public void init(ErrorHandler eh) {
						// do nothing
					}
					public Value call(ErrorHandler eh, Value[] values) {
						if (values.length != 1 || values[0].getType() != VarType.STRING) {
							eh.addError(new Error("Invalid argument!", "call can only accept one string", "sys"));
						}
						Value ret = new Value(VarType.STRING);
						ret.setString("");
						try {

							Process p = Runtime.getRuntime().exec(values[0].getString());
							try {
								p.waitFor();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
							String out = new String(), line;
							while ((line = reader.readLine())!= null) {
								out += line;
							}
							ret.setString(out);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace(); 
						}
						return ret;
					}
					
				}
				XCall xc = new XCall();
				this.actionMap.put(xc.getMetadata(), xc);
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
		return new String[]{ "Call", "XCall" };
	}
}
