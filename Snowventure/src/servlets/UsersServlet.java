package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import classes.*;

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

		//List<User> lUser = Users.get(request.getParameter("username"));
		//request.setAttribute("userlist", lUser);
	
		ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/useraccount.jsp?page=mydata");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Adress adress = new Adress(request.getParameter("location"), request.getParameter("houseno"),
				request.getParameter("street"), request.getParameter("postcode"));
		
		String state = request.getParameter("state").toString();
		
		User user = new User("test", request.getParameter("password"), request.getParameter("first-name"),
				request.getParameter("last-name"), adress, request.getParameter("email"),	state.equals("employee")? 2 :
																							state.equals("customer")? 1 :
																							0);
				
		System.out.println("Daten erfolgreich geändert!");
		System.out.println(request.getParameter("first-name")+"  " +request.getParameter("last-name") + "  " + state + "  " + user.usertype);
		
		doGet(request, response);
	}

}
