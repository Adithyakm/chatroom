package room;

import java.time.LocalDateTime;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;



public class NettySocketTest {
	
  private static final Logger logger = LoggerFactory.getLogger(NettySocketTest.class);
	
  public static void main(String[] args) {
	  
	  Configuration config = new Configuration();
		config.setHostname("localhost");
		config.setPort(9894);
      
		final SocketIOServer server = new SocketIOServer(config);
		
		final SocketIONamespace namespace = server.addNamespace("/socket.io/*");
		namespace.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(SocketIOClient client) {
				// TODO Auto-generated method stub
				System.out.println("Client connected : "+client.getSessionId()+LocalDateTime.now());
				logger.info("Client connected : "+client.getSessionId());
			}
			
		});
		
		namespace.addDisconnectListener(new DisconnectListener() {

			@Override
			public void onDisconnect(SocketIOClient client) {
				// TODO Auto-generated method stub
				System.out.println("Client disconnected : "+client.getSessionId()+" at "+LocalDateTime.now());
				logger.info("Client disconnected : "+client.getSessionId());
			}
			
		});
		namespace.addEventListener("Hello", HelloWorldObject.class, new DataListener<HelloWorldObject>() {

			@Override
			public void onData(SocketIOClient client, HelloWorldObject data, AckRequest ackSender) throws Exception {
				// TODO Auto-generated method stub
				logger.info(data.getValue());
				System.out.println(data.getValue());
				namespace.getBroadcastOperations().sendEvent("HelloReturned", data);
				logger.info("event sent to client");
				System.out.println("event sent to client");
			}
			
		});
		
		
		server.start();
		
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		server.stop();
  }

}
