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

import classes.ShoppingCart;
import classes.User;
import services.LoginService;
import services.UserService;

/**
 * Servlet für den Login eines Users
 */

/**
 * Beschreibung: Servlet für den Login eines Nutzers.
 * @author Garrit Kniepkamp
 *
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
	 * Überprüft, ob der Benutzer in der Datenbank existiert und ob das eingegebene Passwort korrekt ist. Ist beides der Fall, so wird der Nutzer eingeloggt.
	 */
	public boolean login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		if(LoginService.authenticate(userId, password)) {
			
			User user = new User();
			try {
				user = UserService.GetUser(userId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.getSession().setAttribute("currentUser", user);
			request.getSession().removeAttribute("errorLogin");
			
			if(request.getParameter("accessorder") != null && request.getParameter("accessorder").toString().equals("true"))
			{
				response.sendRedirect("order?neworder=true");
				return true;
			}
			
	        response.sendRedirect("start");  
	        return true;
		} else {
			request.getSession().setAttribute("errorLogin", "Der User existiert nicht oder das Passwort ist nicht korrekt!");
			return false;
		}
	}

}
