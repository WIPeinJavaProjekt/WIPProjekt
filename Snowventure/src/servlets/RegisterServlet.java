package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Adress;
import classes.Safetyquestion;
import classes.User;
import services.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }
    
    private Adress adress = new Adress();
    private User user = new User();    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		try {
//			System.out.println(User2Database.GetUsers("TestUser"));
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		user.adress = adress;
		
		if(user!=null && adress!=null) {
			request.setAttribute("location", user.adress.location);
			request.setAttribute("houseno", user.adress.houseno);
			request.setAttribute("street", user.adress.street);
			request.setAttribute("postcode", user.adress.postcode);
			
			request.setAttribute("username", user.username);
			request.setAttribute("name", user.name);
			request.setAttribute("surname", user.surname);
			request.setAttribute("email", user.email);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("back") != null) {
			
			response.sendRedirect("login");
			return;
		} else if(request.getParameter("submitRegister") != null) {
			
			String password = request.getParameter("password");
			System.out.println(password);
			String passwordRepeat = request.getParameter("passwordRepeat");
			System.out.println(passwordRepeat);
			
			adress = new Adress(request.getParameter("location"), request.getParameter("houseno"),
					request.getParameter("postcode"), request.getParameter("street"));
			user = new User(request.getParameter("username"), request.getParameter("password"), request.getParameter("name"),
					request.getParameter("surname"), adress, request.getParameter("email"), 0);
			
			if(password != null && password.equals(passwordRepeat)) {
				
				System.out.println(user.name);
				System.out.println(user.surname);
				System.out.println(user.email);
				System.out.println(user.adress.postcode);
				System.out.println(user.adress.street);
				System.out.println(user.adress.houseno);
				System.out.println(user.adress.location);
				
				Safetyquestion sQuestion = new Safetyquestion(1, "Test", "123");
				
				user.squestion = sQuestion;
				
				int result = User2Database.AddUser(user);
				System.out.println(result);
				System.out.println("REGISTRATION");
				user = new User();
				adress = new Adress();
			} else {
				request.setAttribute("error", "Die Passwörter stimmen nicht überein");
				System.out.println("ERROR");
			}
		}
		
		doGet(request, response);
		
	}

}
