package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.*;
import services.*;

/**
 * Servlet implementation class UsersServlet
 */
@WebServlet("/users")
public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("search-user") != null || request.getParameter("back") != null)
		{
			try 
			{
				List<User> userlist = UserService.GetUsers(request.getParameter("user-info"));
				if(userlist != null)
				{
					System.out.println(userlist.size());			
				}

				System.out.println(userlist.size());			
				request.getSession().setAttribute("userlist", userlist);

				RequestDispatcher rd = request.getRequestDispatcher("/JSP/User/useraccount.jsp?page=usersearch");
				rd.forward(request, response);
			} 
			catch (Exception e) 
			{
				System.out.println(-1);
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			
			try {
				ArrayList<Safetyquestion> squestions = SafetyquestionService.GetSafetyquestion();
				request.getSession().setAttribute("squestions", squestions);				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(-1);	
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/JSP/User/useraccount.jsp?page=mydata");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("currentUser");
		
		if(request.getParameter("update-data") != null)
		{			
			Adress adress = new Adress(request.getParameter("location"), request.getParameter("houseno"),
									   request.getParameter("street"), request.getParameter("postcode"));		
			
			String usertype = request.getParameter("state");
		
			user.adress = adress;
			user.email = request.getParameter("email");
			user.surname = request.getParameter("last-name");
			user.name = request.getParameter("first-name");			
			
			if(usertype != null && usertype != "")
			{user.utid = usertype.equals("admin")? 1 : usertype.equals("employee")? 3 : 2;}
			
			UserService.UpdateUser(user);	
			UserService.UpdateUserRights(user, user.utid);
			
			session.setAttribute("currentUser", user);
		}
		else if(request.getParameter("update-password") != null)
		{
			changePassword(request);
		}
		else if(request.getParameter("update-squestion") != null)
		{
			user.squestion = new Safetyquestion(Integer.parseInt(request.getParameter("safetyQuestion").toString()), "", request.getParameter("safetyAnswer").toString());
			UserService.UpdateUser(user);
			session.setAttribute("currentUser", user);
			System.out.println("sQuestion updated.");	
		}
		
		doGet(request, response);
	}

	private void changePassword(HttpServletRequest request)
	{
		String old_password = request.getParameter("password");
		String new_password = request.getParameter("new-password");
		String new_repeated_password = request.getParameter("new-passwordRepeat");
		
		HttpSession session = request.getSession();
		User user = (User)(session.getAttribute("currentUser"));
		
		if(LoginService.authenticate(user.username, old_password))
		{			
			if(new_password.toString().equals(new_repeated_password.toString()))
			{	
				System.out.println("Passwort alt:" + old_password.toString() + "	neu:" + new_password.toString());	
				user.password = new_password.toString();
						
				UserService.UpdateUser(user);	
				
				session.setAttribute("currentUser", user);
				return;
			}
			else {
				System.out.println("Die Passwörter stimmen nicht überein.");
				request.setAttribute("passworderror", "Die Passwörter stimmen nicht überein.");
				return;
			}		
		}		
		
		System.out.println("Das angegebene Nutzerpasswort ist falsch.");
		request.setAttribute("passworderror", "Das angegebene Nutzerpasswort ist falsch.");
	}
	
}
