package fr.teamPayots.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.teamPayots.beans.User;
import fr.teamPayots.controllers.SigninController;

/**
 * Servlet implementation class SigninServlet
 */
@WebServlet("/signin")
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String VUE = "/WEB-INF/signin.jsp";
	public static final String ATT_USER = "user";
	public static final String ATT_CONT = "controller";
	public static final String ATT_USER_SESS = "userSession";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SigninServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		SigninController cont = new SigninController();
		
		User user = cont.connectUser(request);
		
		HttpSession session = request.getSession();
		
		if (cont.getErrors().isEmpty())
			session.setAttribute(ATT_USER_SESS, user);
		else
			session.setAttribute(ATT_USER_SESS, null);
		
		request.setAttribute(ATT_CONT, cont);
		request.setAttribute(ATT_USER, user);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
