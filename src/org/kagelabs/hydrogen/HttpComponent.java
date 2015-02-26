package org.kagelabs.hydrogen;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.HashMap;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class HttpComponent {
	private HttpServer server;
	private ActionProvider injectableAP;
	
	HttpComponent(int port) {
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.setExecutor(null);
		initInjectableAP();
	}
	
	public void addRoute(String route, String handler) {
		server.createContext(route, new SourceHandler(handler, injectableAP));
	}
	
	public void start() {
		server.start();
	}
	
	private void initInjectableAP() {
		class HttpActionProvider implements ActionProvider {
			HashMap<ActionMetadata, Action> actionMap;
			private HttpComponent parent;
			
			HttpActionProvider(HttpComponent parent) {
				this.parent = parent;
				this.actionMap = new HashMap<ActionMetadata, Action>();
			}
			
			@Override
			public HashMap<ActionMetadata, Action> getActionDictionary() {
				return this.actionMap;
			}

			@Override
			public void init(ErrorHandler eh) {
				class AddRoute implements Action {
					private ActionMetadata am;
					private HttpComponent parent;
					AddRoute(HttpComponent parent) {
						this.am = new ActionMetadata();
						am.setArguments("$$");
						am.setName("addroute");
						am.setNamespace("http");
						am.setReturnPrefix('\0');
						this.parent = parent;
					}
					
					@Override
					public ActionMetadata getMetadata() {
						return am;
					}

					@Override
					public void init(ErrorHandler eh) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public Value call(ErrorHandler eh, Value[] values) {
						if (values.length != 2 || values[0].getType() != VarType.STRING || values[1].getType() != VarType.STRING) {
							eh.addError("Invalid argument set", "Can only accept string, string", "http");
						}
						parent.addRoute(values[0].getString(), values[1].getString());
						return new Value(VarType.INVALID);
					}
					
				}
				AddRoute ar = new AddRoute(this.parent);
				this.actionMap.put(ar.getMetadata(), ar);
				
				class StartServer implements Action {
					private ActionMetadata am;
					private HttpComponent parent;
					StartServer(HttpComponent parent) {
						this.am = new ActionMetadata();
						am.setArguments("");
						am.setName("startserver");
						am.setNamespace("http");
						am.setReturnPrefix('\0');
						this.parent = parent;
					}
					
					@Override
					public ActionMetadata getMetadata() {
						return am;
					}

					@Override
					public void init(ErrorHandler eh) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public Value call(ErrorHandler eh, Value[] values) {
						parent.start();
						return null;
					}
					
				}
				StartServer st = new StartServer(this.parent);
				this.actionMap.put(st.getMetadata(), st);
			}

			@Override
			public void run(ErrorHandler eh, ActionMetadata am) {
				// TODO Auto-generated method stub
				
			}
			
		}
		this.injectableAP = new HttpActionProvider(this);
		this.injectableAP.init(new ErrorHandler());
	}
	
	public void begin(String input) {
		Interpreter hy = new Interpreter();
		hy.initialize(input);
		hy.registerActionProvider(this.injectableAP);
		while (hy.canRunMore()) {
			hy.tick();
		}
	}
	
	static class SourceHandler implements HttpHandler {
		private String fn;
		private ActionProvider injectableAP;
		SourceHandler(String filename, ActionProvider injectableAP) {
			fn = filename;
			this.injectableAP = injectableAP;
		}
		@Override
		public void handle(HttpExchange t) throws IOException {
			// TODO redirect stdout
			Interpreter hy = new Interpreter();
			
			// redirect stdout to a stream
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			System.setOut(new PrintStream(baos));
			
			hy.initialize(fn);
			hy.registerActionProvider(this.injectableAP);
			while (hy.canRunMore()) {
				hy.tick();
			}
			String response = baos.toString();
			
			// restore stdout
			System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
			
			
		    t.sendResponseHeaders(200, response.length());
		    OutputStream os = t.getResponseBody();
		    os.write(response.getBytes());
		    os.close();
			
		}
	}
}
