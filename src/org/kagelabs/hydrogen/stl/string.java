package org.kagelabs.hydrogen.stl;

import org.kagelabs.hydrogen.*;

import java.util.HashMap;

public class string {
	public ActionProvider getActionProvider() {
		class StringActionProvider implements ActionProvider {
			HashMap<ActionMetadata, Action> actionMap;

			StringActionProvider() {
				actionMap = new HashMap<ActionMetadata, Action>();
			}

			public void init(ErrorHandler eh) {
				class Length implements Action {
					ActionMetadata meta;
					Length() {
						meta = new ActionMetadata();
						meta.setName("length");
						meta.setReturnPrefix('#');
						meta.setNamespace("str");
					}
					public ActionMetadata getMetadata() {
						return meta;
					}
					public void init(ErrorHandler eh) {
						// do nothing
					}
					public Value call(ErrorHandler eh, Value[] values) {
						if (values.length != 1) {
							eh.addError("Invalid argument count!", "This can only accept one argument", "str");
							return new Value(VarType.INVALID);
						}
						Value val = new Value(VarType.NUMBER);
						val.setNumber(values[0].getString().length());
						return val;
					}
				}
				Length len = new Length();
				this.actionMap.put(len.getMetadata(), len);

				class Substring implements Action {
					ActionMetadata meta;
					Substring() {
						meta = new ActionMetadata();
						meta.setName("substr");
						meta.setReturnPrefix('$');
						meta.setNamespace("str");
					}
					public ActionMetadata getMetadata() {
						return meta;
					}
					public void init(ErrorHandler eh) {
						// do nothing
					}
					public Value call(ErrorHandler eh, Value[] values) {
						if (values.length != 3) {
							eh.addError("Invalid argument count!", "This can only accept three arguments", "str");
							return new Value(VarType.INVALID);
						}
						Value val = new Value(VarType.STRING);
						String str = values[0].getString();
						int begin = (int)values[1].getNumber();
						int end = (int)values[2].getNumber();
						val.setString(str.substring(begin, end));
						return val;
					}
				}
				Substring sub = new Substring();
				this.actionMap.put(sub.getMetadata(), sub);

				class Concatenate implements Action {
					ActionMetadata meta;
					Concatenate() {
						meta = new ActionMetadata();
						meta.setName("cat");
						meta.setReturnPrefix('$');
						meta.setNamespace("str");
					}
					public ActionMetadata getMetadata() {
						return meta;
					}
					public void init(ErrorHandler eh) {
						// do nothing
					}
					public Value call(ErrorHandler eh, Value[] values) {
						Value ret = new Value(VarType.STRING);
						ret.setString("");
						for (Value v : values) {
							if (v.getType() == VarType.STRING) {
								ret.setString(ret.getString() + v.getString());
							} else if (v.getType() == VarType.NUMBER) {
								ret.setString(ret.getString() + v.getNumber());
							}
						}
						return ret;
					}
				}
				Concatenate cat = new Concatenate();
				this.actionMap.put(cat.getMetadata(), cat);

			}


			public HashMap<ActionMetadata, Action> getActionDictionary() {
				return this.actionMap;
			}

			@Override
			public void run(ErrorHandler eh, ActionMetadata am) {
				// TODO Auto-generated method stub
			}
		}

		return new StringActionProvider();
	}

	public String getProviderName() {
		return "StringActionProvider";
	}

	public String[] getActionNames() {
		return new String[]{ "Length", "Substring", "Concatenate" };
	}
}
