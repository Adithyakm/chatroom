package room;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.socket.engineio.server.Emitter;
import io.socket.engineio.server.EngineIoServer;
import io.socket.engineio.server.EngineIoWebSocket;
import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;





@WebServlet(urlPatterns ="/socketio/*",asyncSupported=true)
public class SockServlet extends HttpServlet {
	
	private static  EngineIoServer mEngineIoServer = new EngineIoServer();
    private static  SocketIoServer mSocketIoServer = new SocketIoServer(mEngineIoServer);
    private EngineIoWebSocket webSocket;
    private String roomDetails;
    private Logger logger = Logger.getLogger("SockServlet");
    
    //private final EngineIoServerOptions moptions = new EngineIoServerOptions();
    //private static final Logger logger = Logger.getLogger(ConnectionListener.class.getName());
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //logger.info("Sock Servlet Initialized");
        webSocket = new WebsocketHandler(mEngineIoServer);
        System.out.println("sockServlet Initialized");
        initIO();

    }
    
    private void initIO() {
    	
    	SocketIoServer server = mSocketIoServer;
        final SocketIoNamespace nameSpace = server.namespace("/");
		   final Messaging broadcastMessage = null;
		   nameSpace.on("connection", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				// TODO Auto-generatedmethod stub
				final SocketIoSocket socket = (SocketIoSocket) args[0];
				//socket.emit("connected", "Client and server connected on default namespace");
				System.out.println("Client " + socket.getId() + " (" + socket.getInitialHeaders().get("remote_addr") + ") has connected.");
				socket.on("create", new Emitter.Listener() {
					
					@Override
					public void call(Object... args) {
						// TODO Auto-generated method stub
						roomDetails = (String)args[0];
						socket.joinRoom(roomDetails);
						socket.emit("RoomJoined", "Room "+roomDetails+" joined successfully");
						logger.info(roomDetails+" joined successfully");
						socket.on("message", new Emitter.Listener() {
							
							@Override
							public void call(Object... args) {
								System.out.println(args[0]);
								ObjectMapper mapper = new ObjectMapper();
							    String json = (String)args[0];
							    Messaging messageDetails=null;
								try {
									messageDetails = mapper.readValue(json,Messaging.class);
									Messaging message = MessageDB.saveMessage(messageDetails);
									logger.info(message.getMessageSender()+":"+message.getMessageContent());
								    //socket.emit("message", message);
								    socket.broadcast(roomDetails,"messageDelivered",mapper.writeValueAsString(message));
								    //nameSpace.broadcast("room","message",broadcastMessage);
								    } catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									logger.info(e.getMessage());
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									logger.info(e.getMessage());
								}catch(IOException e) {
									logger.info(e.getMessage());
								}
							}
						} );
					}
				});
				socket.on("checking", new Emitter.Listener() {
					
					@Override
					public void call(Object... args) {
						// TODO Auto-generated method stub
						System.out.println(args[0]);
					}
				});
				
				
				
			}

		});
   	
		
		//logger.info(resp.get);
		
    	 
    }

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//EngineIoServerOptions options = EngineIoServerOptions.newFromDefault();
		//HandshakeInterceptor handshake = (HandshakeInterceptor)new HandshakeInterceptorImpl();
		//options.setHandshakeInterceptor(handshake);
//		HandshakeInterceptor handshakeInterceptor = options.getHandshakeInterceptor();
//		Map<String,String> map = new HashMap<>();
//		Map<String,List<String>> setMap = new HashMap<>();
		//mEngineIoServer = new EngineIoServer(options);
		//mSocketIoServer = new SocketIoServer(mEngineIoServer);
	//	try {
//		System.out.println(req.isAsyncSupported());	
		HttpSession session = req.getSession();
//		boolean check = req.authenticate(resp);
//		System.out.println(check);
//		if(session.getAttribute("id")==null) {
//			mEngineIoServer.handleRequest(req, resp);
//		}
		System.out.println(session.getId());
//		String id = session.getId();
//		session.setAttribute("id", id);
		//System.out.println(session.getMaxInactiveInterval());
		session.setMaxInactiveInterval(30000);
		System.out.println(session.getMaxInactiveInterval());
//		String sid = session.getId();
//		req.setAttribute("sid", sid);
		System.out.println(req.getQueryString());	
		mEngineIoServer.handleRequest(req, resp);
//		 final Enumeration<String> headerNamesEnum = req.getHeaderNames();
//       while (headerNamesEnum.hasMoreElements()) {
//           final String headerName = headerNamesEnum.nextElement();
//           Enumeration<String> headerValues = req.getHeaders(headerName);
//           while(headerValues.hasMoreElements()) {
//          	 System.out.println(headerValues.nextElement());
//           }
//       }
//	    
//		logger.info(Integer.toString(resp.getStatus()));
//		//logger.info(resp.getHeader("ERROR_JSON"));
//		SocketIoServer server = mSocketIoServer;
//        final SocketIoNamespace nameSpace = server.namespace("/");
//		   final Messaging broadcastMessage = null;
//		   nameSpace.on("connection", new Emitter.Listener() {
//			@Override
//			public void call(Object... args) {
//				// TODO Auto-generatedmethod stub
//				final SocketIoSocket socket = (SocketIoSocket) args[0];
//				//socket.emit("connected", "Client and server connected on default namespace");
//				System.out.println("Client " + socket.getId() + " (" + socket.getInitialHeaders().get("remote_addr") + ") has connected.");
//				socket.on("create", new Emitter.Listener() {
//					
//					@Override
//					public void call(Object... args) {
//						// TODO Auto-generated method stub
//						roomDetails = (String)args[0];
//						socket.joinRoom(roomDetails);
//						socket.emit("RoomJoined", "Room "+roomDetails+" joined successfully");
//						logger.info(roomDetails+" joined successfully");
//						socket.on("message", new Emitter.Listener() {
//							
//							@Override
//							public void call(Object... args) {
//								System.out.println(args[0]);
//								ObjectMapper mapper = new ObjectMapper();
//							    String json = (String)args[0];
//							    Messaging messageDetails=null;
//								try {
//									messageDetails = mapper.readValue(json,Messaging.class);
//									Messaging message = MessageDB.saveMessage(messageDetails);
//									logger.info(message.getMessageSender()+":"+message.getMessageContent());
//								    //socket.emit("message", message);
//								    socket.broadcast(roomDetails,"messageDelivered",mapper.writeValueAsString(message));
//								    //nameSpace.broadcast("room","message",broadcastMessage);
//								    } catch (JsonMappingException e) {
//									// TODO Auto-generated catch block
//									logger.info(e.getMessage());
//								} catch (JsonProcessingException e) {
//									// TODO Auto-generated catch block
//									logger.info(e.getMessage());
//								}catch(IOException e) {
//									logger.info(e.getMessage());
//								}
//							}
//						} );
//					}
//				});
//				socket.on("checking", new Emitter.Listener() {
//					
//					@Override
//					public void call(Object... args) {
//						// TODO Auto-generated method stub
//						System.out.println(args[0]);
//					}
//				});
//				
//				
//				
//			}
//
//		});
//   	
//		
//		//logger.info(resp.get);
//		}catch(Exception e) {
//			logger.log(Level.WARNING,"Exception :: "+e.getMessage());
//		}
//		//req.setAttribute("sid", sid);
////        SocketIoServer server = mSocketIoServer;
////        final SocketIoNamespace nameSpace = server.namespace("/");
////		   final Messaging broadcastMessage = null;
////		   nameSpace.on("connection", new Emitter.Listener() {
////			@Override
////			public void call(Object... args) {
////				// TODO Auto-generated method stub
////				final SocketIoSocket socket = (SocketIoSocket) args[0];
////				System.out.println("Client " + socket.getId() + " (" + socket.getInitialHeaders().get("remote_addr") + ") has connected.");
////				socket.on("message", new Emitter.Listener() {
////					
////					@Override
////					public void call(Object... args) {
////						ObjectMapper mapper = new ObjectMapper();
////					    String json = (String)args[0];
////					    Messaging messageDetails;
////						try {
////							messageDetails = mapper.readValue(json,Messaging.class);
////							Messaging message = MessageDB.saveMessage(messageDetails);
////						    socket.emit("message", message);
////						    nameSpace.broadcast("room","message",broadcastMessage);
////						    } catch (JsonMappingException e) {
////							// TODO Auto-generated catch block
////							e.printStackTrace();
////						} catch (JsonProcessingException e) {
////							// TODO Auto-generated catch block
////							e.printStackTrace();
////						}
////					}
////				} );
////				
////			}
////
////		});
////		System.out.println(req.getLocalPort());
////		System.out.println(req.getQueryString());
////		System.out.println(req.getMethod());
////		 final Enumeration<String> headerNamesEnum = req.getHeaderNames();
////         while (headerNamesEnum.hasMoreElements()) {
////             final String headerName = headerNamesEnum.nextElement();
////             Enumeration<String> headerValues = req.getHeaders(headerName);
////             while(headerValues.hasMoreElements()) {
////            	 System.out.println(headerValues.nextElement());
////             }
////         }
////         System.out.println(mEngineIoServer.getOptions().isCorsHandlingDisabled());
////         System.out.println(mEngineIoServer.getOptions().isSyncPollingAllowed());
////         System.out.println(req.isAsyncSupported());
////         System.out.println(req.getHeader("Origin")!=null);
//         //final Map<String, String> query = ParseQS.decode(req.getQueryString());
         
	}
	
	

	public static EngineIoServer getMengineioserver() {
		return mEngineIoServer;
	}

	public static SocketIoServer getMsocketioserver() {
		return mSocketIoServer;
	}
	
	private class WebsocketHandler extends EngineIoWebSocket{
		
		private EngineIoServer webSocketServer=null;
		private HttpSession session;
		private Map<String, String> mQuery;
	    private Map<String, List<String>> mHeaders;
	    
	    public WebsocketHandler(EngineIoServer server) {
	    	this.webSocketServer=server;
	    }
		@Override
		public Map<String, String> getQuery() {
			// TODO Auto-generated method stub
			return mQuery;
		}

		@Override
		public Map<String, List<String>> getConnectionHeaders() {
			// TODO Auto-generated method stub
			return mHeaders;
		}

		@Override
		public void write(String message) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write(byte[] message) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			if(session!=null) {
				session.invalidate();
			}
			
		}
		
	}

	

}
