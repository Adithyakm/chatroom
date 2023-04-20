package room;

import java.net.ServerSocket;

public class Config {
	
	public static void main(String[] args) {
		int portNumber=8087;
		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			System.out.println("Server listening on port " + portNumber);
		}catch(Exception e) {
			System.err.println("Error : "+e.getMessage());
		}
	}

}
