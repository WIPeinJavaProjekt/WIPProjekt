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

import classes.Adress;
import classes.Safetyquestion;
import classes.User;
import classes.Users;
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
		
		this.user.adress = this.adress;
		
		this.user = Users.get("").get(0);
		
		if(this.user!=null && this.adress!=null) {
			request.setAttribute("location", this.user.adress.location);
			request.setAttribute("houseno", this.user.adress.houseno);
			request.setAttribute("street", this.user.adress.street);
			request.setAttribute("postcode", this.user.adress.postcode);
			
			request.setAttribute("username", this.user.username);
			request.setAttribute("name", this.user.name);
			request.setAttribute("surname", this.user.surname);
			request.setAttribute("email", this.user.email);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		System.out.println(request.getParameter("back"));
		System.out.println(request.getParameter("submitRegister"));
		
		if(request.getParameter("back") != null) {
			
			response.sendRedirect("login");
			return;
		} else if(request.getParameter("submitRegister") != null) {
			
			registrate(request);
			
		}
		
		doGet(request, response);
		
	}
	
	private void registrate (HttpServletRequest request) {
		
		System.out.println("Registrate");
		
		String password = request.getParameter("password");
		String passwordRepeat = request.getParameter("passwordRepeat");
		
		this.adress = new Adress(request.getParameter("location"), request.getParameter("houseno"),
				request.getParameter("postcode"), request.getParameter("street"));
		this.user = new User(request.getParameter("username"), request.getParameter("password"), request.getParameter("name"),
				request.getParameter("surname"), this.adress, request.getParameter("email"), 0);
		
		if(password.equals(passwordRepeat)) {
			
			System.out.println(this.user.name);
			System.out.println(this.user.surname);
			System.out.println(this.user.email);
			System.out.println(this.user.adress.postcode);
			System.out.println(this.user.adress.street);
			System.out.println(this.user.adress.houseno);
			System.out.println(this.user.adress.location);
			
			Safetyquestion sQuestion = new Safetyquestion(1, "Test", "123");
			
			user.squestion = sQuestion;
			
			int result = User2Database.AddUser(this.user);
			System.out.println(result);
			System.out.println("REGISTRATION");
			this.user = new User();
			this.adress = new Adress();
		} else {
			request.setAttribute("error", "Die Passw�rter stimmen nicht �berein");
		}
	}

}
