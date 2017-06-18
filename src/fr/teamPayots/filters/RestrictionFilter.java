package fr.teamPayots.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/restricted/*")
public class RestrictionFilter implements Filter {
	
	public static final String ACCES_DENIED = "/rejected.jsp";
	public static final String ATT_USER_SESS = "userSession";

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute(ATT_USER_SESS) == null){
			response.sendRedirect(request.getContextPath() + ACCES_DENIED);
		}else{
			chain.doFilter(request, response);
		}
	}


	public void init(FilterConfig config) throws ServletException {

	}

}
