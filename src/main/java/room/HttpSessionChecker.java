package room;



import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class HttpSessionChecker implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("Session ID "+se.getSession().getId()+" created at "+new Date());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
	    System.out.println("Session ID "+se.getSession().getId()+" destroyed at "+new Date());
	}

	
}
