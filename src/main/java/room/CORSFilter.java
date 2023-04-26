package room;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class CORSFilter implements Filter{
	
	public CORSFilter() {
		
	}
	
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request;
		System.out.println("CORSFilter HTTP Request: " + req.getMethod());
		
		String clientOrigin = req.getHeader("origin");
		System.out.println(clientOrigin);

		// Authorize (allow) all domains to consume the content
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", clientOrigin);
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials","true");
		
		HttpServletResponse resp = (HttpServletResponse) response;
		// For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
		if (req.getMethod().equals("OPTIONS")) {
			resp.setStatus(HttpServletResponse.SC_ACCEPTED);
			return;
		}

		// pass the request along the filter chain
		chain.doFilter(req, response);
		
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
