package servlets;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.User;
import services.LoginService;
import services.UserService;

/**
 * Servlet f�r den Login eines Users
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/User/login.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("login") != null) {

			if(login(request, response))
			return;
			
		} else if(request.getParameter("register") != null) {

			response.sendRedirect(request.getContextPath() + "/register");
			return;
			
		} else if(request.getParameter("back") != null) {
			
			response.sendRedirect("start");
			return;
		}
		
		doGet(request, response);

	}
	
	/** 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return Returns a boolean value
	 * @throws IOException
	 * 
	 * The "login"-method checks whether the user is in the database and can be logged in.
	 */
	public boolean login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		if(LoginService.authenticate(userId, password)) {
			
			HttpSession session = request.getSession();
			User user = new User();
			try {
				user = UserService.GetUser(userId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        session.setAttribute("currentUser", user);
	        response.sendRedirect("start");
	        
	        return true;
		} else {
			//TODO: Error
			System.out.println("User nicht in DB / PW falsch");
			return false;
		}
	}

}
