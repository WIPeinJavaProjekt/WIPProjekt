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
import services.User2Database;

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
		
		HttpSession session = request.getSession();
		
		if(request.getParameter("login") != null) {

			login(request);
			
		} else if(request.getParameter("register") != null) {

			response.sendRedirect(request.getContextPath() + "/register");
			return;
			
		} else if(request.getParameter("back") != null) {
			
			response.sendRedirect("start");
			return;
		}
		
		doGet(request, response);

	}
	
	public void login(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		if(LoginService.authenticate(userId, password)) {
			
			HttpSession session = request.getSession();
			User user = new User();
			try {
				user = User2Database.GetUsers(userId).get(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        session.setAttribute("user", user);  
		} else {
			//TODO: Error
		}
		
		System.out.println(userId);
		System.out.println(password);
	}

}
