package room;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

@WebServlet("/engine.io/*")
public class EngineIoEndpoint extends HttpServlet {
	
	private HttpSession msession;

}
