package room;

import java.util.List;
import java.util.Map;

import io.socket.engineio.server.EngineIoServer.HandshakeInterceptor;

public class HandshakeInterceptorImpl implements HandshakeInterceptor {

	@Override
	public boolean intercept(Map<String, String> query, Map<String, List<String>> headers) {
		// TODO Auto-generated method stub
		return true;
	}

}
