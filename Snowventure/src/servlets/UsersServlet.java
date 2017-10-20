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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		if(request.getParameter("search-user") != null)
		{
			try 
			{
				List<User> userlist = User2Database.GetUsers(request.getParameter("user-info"));
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
			ServletContext sc = this.getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/JSP/User/useraccount.jsp?page=mydata");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(request.getParameter("update-data") != null)
		{
			Adress adress = new Adress(request.getParameter("location"), request.getParameter("houseno"),
									   request.getParameter("street"), request.getParameter("postcode"));
		
			String state = request.getParameter("state").toString();
			
			User user = new User("test", request.getParameter("password"), request.getParameter("first-name"),
								 request.getParameter("last-name"), adress, request.getParameter("email"),	state.equals("employee")? 3 :
																								            state.equals("customer")? 2 :
																							                1);
			//User2Database.UpdateUser(user);			
			
			System.out.println(request.getParameter("first-name")+"  " +request.getParameter("last-name") + "  " + state + "  " + user.usertype);
		}
		else if(request.getParameter("update-password") != null)
		{
			String old_password = request.getParameter("password");
			String new_password = request.getParameter("new-password");
			String new_repeated_password = request.getParameter("new-passwordRepeat");
			
			HttpSession ses = request.getSession();
			User sesUser = (User)(ses.getAttribute("user"));
			
			if(old_password.toString().equals(sesUser.password.toString()) && new_password != null & old_password.toString() != new_password.toString() && new_password.toString().equals(new_repeated_password.toString()))
			{	
				System.out.println("Passwort erfolgreich ge�ndert! (alt:" + old_password.toString() + "  " + "neu:" + new_password.toString() + ")");
			
				sesUser.password = new_password.toString();
				ses.setAttribute("user", sesUser);
			}
			else {
				System.out.println("Ihr Passwort konnte nicht ge�ndert werden. Bitte �berpr�fen Sie Ihre Eingaben.");
			}	
		}
		doGet(request, response);
	}

}
