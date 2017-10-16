package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Adress;
import classes.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		if(request.getParameter("back") != null) {
			
			response.sendRedirect("login");
			return;
		} else if(request.getParameter("submitRegister") != null) {
			
			Adress adress = new Adress(request.getParameter("location"), request.getParameter("houseno"),
					request.getParameter("street"), request.getParameter("postcode"));
			User user = new User(request.getParameter("username"), request.getParameter("password"), request.getParameter("name"),
					request.getParameter("surname"), adress, request.getParameter("email"), 0);
			
			System.out.println("Registrierung erfolgreich!");
			System.out.println(user.username);
		}
		
		doGet(request, response);
		
	}	

}
