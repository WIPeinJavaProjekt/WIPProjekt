package servlets;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.LoginService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId, password;
		
		if(request.getParameter("login") != null) {

			userId = request.getParameter("userId");
			password = request.getParameter("password");
			
			System.out.println(userId);
			System.out.println(password);
			
		} else if(request.getParameter("register") != null) {
			
			response.sendRedirect("register");
			return;
			
		} else if(request.getParameter("back") != null) {
			
			response.sendRedirect("start");
			return;
		}
		
		doGet(request, response);
		
		
	}	

}
