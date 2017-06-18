package fr.teamPayots.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserzoneServlet
 */
@WebServlet("/restricted/userZone")
public class UserzoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String VUE = "/WEB-INF/restricted/userZone.jsp";
	public static final String ATT_CLICK = "click";
       
    public UserzoneServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int i = 1;
		request.setAttribute(ATT_CLICK, i);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
