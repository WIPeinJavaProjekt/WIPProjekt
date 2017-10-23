package servlets;
import classes.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.Safetyquestion;
import classes.User;
import services.CategorieService;
import services.SafetyquestionService;
import services.UserService;	

@WebServlet("/start")
public class StartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		try {
			ArrayList<Categorie> categories = CategorieService.GetCategories();
			request.setAttribute("categories", categories);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		
		System.out.println("Current User: " + currentUser != null ? "Kein User" : currentUser.name);
		System.out.println("Is User logged in: " + request.getSession().getAttribute("userLoggedIn"));
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/welcome.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		
		
		if(request.getParameter("login") != null) {
			
			response.sendRedirect(request.getContextPath() + "/login");
			return;
			
		} else if(request.getParameter("logout") != null) {
			logout(request);
			response.sendRedirect("start");
			return;
			
		} else if(request.getParameter("start") != null) {
			response.sendRedirect("start");
			return;
			
		} else if(request.getParameter("search") != null) {	
			
			String searchValue = request.getParameter("search");
			
			System.out.println(searchValue);
		} else if(request.getParameter("settings") != null) {	
			response.sendRedirect("users");
			return;
		}  else if(request.getParameter("cart") != null) {	
			response.sendRedirect("cart");
			return;
		} 
		
		doGet(request, response);
	}
	
	public void logout(HttpServletRequest request) {
		request.getSession().setAttribute("userLoggedIn", false);
		request.getSession().setAttribute("currentUser", null);
	}

}
