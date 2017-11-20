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
 * Servlet für die Bearbeitung und Rückgabe von Nutzern
 */

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UsersServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = (User)request.getSession().getAttribute("currentUser");
		
		if(user != null)
		{
			if(user.utid == 1 && request.getParameter("search-user") != null || request.getParameter("back") != null)
			{			
				searchforUsers(request, response);
				RequestDispatcher rd = request.getRequestDispatcher("/JSP/User/useraccount.jsp?page=usersearch");
				rd.forward(request, response);
				return;
			}		
			else if (user.utid == 1 && request.getParameter("selecteduser") != null)
			{
				request.getSession().removeAttribute("error");
				getSelectedUser(request, response);
				RequestDispatcher rd = request.getRequestDispatcher("/JSP/User/useraccount.jsp?page=userinfo");
				rd.forward(request, response);
				return;
			}
			else if((user.utid == 1 || user.utid == 3) && request.getParameter("searchArticles") != null) 
			{
				findArticles(request);
				
				response.sendRedirect("users?page=articlesearch");
				
				return;
			}
			else if(user != null && request.getParameter("searchOrders") != null) 
			{
				findOrders(request, user);
					
				response.sendRedirect("users?page=ordersearch");
				
				return;
			}
			else if((user.utid == 2 && request.getParameter("page") != null && (request.getParameter("page").toString().equals("articlesearch") || request.getParameter("page").toString().equals("usersearch"))) || (user.utid == 3 && request.getParameter("page") != null && request.getParameter("page").toString().equals("usersearch")))
			{
				response.sendRedirect("users");
				
				return;
			}
			else 
			{		
				try 
				{
					ArrayList<Safetyquestion> squestions = SafetyquestionService.GetSafetyquestion();
					request.getSession().setAttribute("squestions", squestions);				
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
					System.out.println(-1);	
				}
				
				RequestDispatcher rd = request.getRequestDispatcher("/JSP/User/useraccount.jsp?page=mydata");
				rd.forward(request, response);
			}
		}
		else {
			response.sendRedirect("login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = (User)request.getSession().getAttribute("currentUser");
		
		if(request.getParameter("update-data") != null){			
			updateCurrentUser(user, request);
		}
		else if(request.getParameter("update-password") != null){
			changePassword(request);
		}
		else if(request.getParameter("update-squestion") != null){
			changeSafetyQuestion(user, request);
		}
		else if(request.getParameter("addArticle") != null){
			response.sendRedirect("article");
			return;
		}
		else if(request.getParameter("back") != null)
		{
			response.sendRedirect("users?page=usersearch");
			return;
		}
		else if(request.getParameter("updateSelection") != null)
		{
			User selectedUser = (User)request.getSession().getAttribute("selectedUser");			
			updateSelectedUser(selectedUser, request);
			response.sendRedirect("users?page=userinfo&selectedUser=" + selectedUser.username );
			return;
		}
				
		doGet(request, response);
	}

	/**
	 * Returns articles matching the search-pattern to the request
	 * @param request
	 * @throws IOException 
	 */
	private void findArticles(HttpServletRequest request) throws IOException {
		String searchArticlePattern = request.getParameter("searchArticlePattern");
		int category = Integer.parseInt(request.getParameter("categories").toString());
		
		ArrayList<Article> articles = null;
		
		try {
			if(category == -1) {
				articles = ArticleService.GetAllArticlesByName(searchArticlePattern,1,0);
			}
			else {
				articles = ArticleService.GetAllArticlesByCategorie(category, searchArticlePattern,1,0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(articles.size() == 0) {
			request.getSession().setAttribute("noArticleFound", true);
		} else {
			request.getSession().setAttribute("noArticleFound", false);
		}
		
		request.getSession().setAttribute("articles", articles);		
	}
	
	/** 
	 * @param request HttpServletRequest
	 * @throws IOException
	 * 
	 * The "changePassword"-method checks whether the given password is correct for this specific user and can be updated to the new password.
	 */
	private void changePassword(HttpServletRequest request) throws IOException
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
	
	/** 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException
	 * 
	 * The "searchforUsers"-method gets all users depending on the given search criteria (username). 
	 * If available the result list is added into the session attributes.
	 */
	private void searchforUsers(HttpServletRequest request, HttpServletResponse response) throws IOException
	{		
		List<User> userlist = null;
		
		try 
		{
			userlist = UserService.GetUsers(request.getParameter("user-info"), Integer.parseInt(request.getParameter("categories").toString()));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println(userlist.size());	
		request.getSession().setAttribute("userlist", userlist);
		
		if(userlist.size() == 0 && request.getParameter("back") == null)
		{ 
			request.getSession().setAttribute("nouserfound", true);	
		}
		else 
		{ 
			request.getSession().setAttribute("nouserfound", false); 
		}
	}
	
	
	
	/** 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException
	 * 
	 * The "getSelectedUser"-method searches for the user having the given username and if available the user is set into session attributes.
	 */
	private void getSelectedUser(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{		
		try {
			User user = UserService.GetUser(request.getParameter("selecteduser"));
			if(user != null) 
			{
				request.getSession().setAttribute("selectedUser", user);
				System.out.println("User is selected and set as attribute.");
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/** 
	 * @param user User
	 * @param request HttpServletRequest
	 * @throws IOException
	 * 
	 * The "updateCurrentUser"-method updates data and rights of the user which is currently logged in and resets the currentUser attribute of the session.
	 */
	private void updateCurrentUser(User user, HttpServletRequest request) throws IOException
	{
		Adress adress = new Adress(request.getParameter("location"), request.getParameter("houseno"),
				   				   request.getParameter("street"), request.getParameter("postcode"));		

		String usertype = request.getParameter("state");
		
		user.adress = adress;
		user.email = request.getParameter("email");
		user.surname = request.getParameter("surname");
		user.name = request.getParameter("name");			
		
		if(usertype != null && usertype != "")
		{
			user.utid = usertype.equals("admin")? 1 : usertype.equals("employee")? 3 : 2;
		}
		
		UserService.UpdateUser(user);	
		UserService.UpdateUserRights(user, user.utid);
		
		request.getSession().setAttribute("currentUser", user);
	}
	
	
	/** 
	 * @param user User
	 * @param request HttpServletRequest
	 * @throws IOException
	 * 
	 * The "changeSafetyQuestion"-method updates the safetyquestion and answer of the user which is currently logged in and resets the currentUser attribute of the session.
	 */
	private void changeSafetyQuestion(User user, HttpServletRequest request) throws IOException
	{
		user.squestion = new Safetyquestion(Integer.parseInt(request.getParameter("safetyQuestion").toString()), "", request.getParameter("safetyAnswer").toString());
		
		UserService.UpdateUser(user);
		
		request.getSession().setAttribute("currentUser", user);
		
		System.out.println("sQuestion updated.");	
	}
	
	
	
	/** 
	 * @param user User
	 * @param request HttpServletRequest
	 * @throws IOException
	 * 
	 * The "updateSelectedUser"-method updates data and rights of the selected user and resets the selectedUser attribute of the session.
	 */
	private void updateSelectedUser(User user, HttpServletRequest request) throws IOException
	{
		Adress adress = new Adress(request.getParameter("location"), request.getParameter("houseno"),
				   				   request.getParameter("street"), request.getParameter("postcode"));
		
		user.squestion = new Safetyquestion(Integer.parseInt(request.getParameter("safetyQuestion").toString()), "", request.getParameter("safetyAnswer").toString());

		String password = request.getParameter("password");
		String passwordrepeat = request.getParameter("passwordRepeat");
		
		request.getSession().removeAttribute("error");
		
		if(password != "" && passwordrepeat != "" && password.equals(passwordrepeat))
		{
			user.password = request.getParameter("password").toString();
		}
		else if(password != "" && passwordrepeat != "")
		{
			request.getSession().setAttribute("error", "Die Passwörter stimmen nicht überein.");
		}
		
		String usertype = request.getParameter("state");
		
		user.adress = adress;
		user.email = request.getParameter("email");
		user.surname = request.getParameter("surname");
		user.name = request.getParameter("name");			

		if(usertype != null && usertype != "")
		{
			user.utid = usertype.equals("admin")? 1 : usertype.equals("employee")? 3 : 2;
		}
		
		UserService.UpdateUser(user);	
		UserService.UpdateUserRights(user, user.utid);
		
		request.getSession().setAttribute("selectedUser", user);
	}
	
	
	/**
	 * Returns orders matching the search-pattern to the request and are accessible by the role of the user 
	 * @param request HttpServletRequest
	 * @param user User
	 * @throws IOException 
	 */
	private void findOrders(HttpServletRequest request, User user) throws IOException 
	{		
		String searchOrderIDPattern = request.getParameter("searchOrderIDPattern");

		ArrayList<OrderStatus> statusList = getStatusList();
				
		ArrayList<Order> orders = new ArrayList<Order>();
		
		try {
			if((user.utid == 1 || user.utid == 3) && searchOrderIDPattern != null && searchOrderIDPattern != "") 
			{
				Order order = OrderService.GetSpecificOrder(Integer.parseInt(searchOrderIDPattern));
				if (order != null) 
				{ orders.add(order);}
			}
			else if(user.utid == 2 && searchOrderIDPattern != null && searchOrderIDPattern != "")
			{
				Order order = OrderService.GetSpecificOrder(Integer.parseInt(searchOrderIDPattern));
				if (order != null && order.ulid == user.ulid) 
				{ orders.add(order);}
			}
			else if(user.utid == 1 || user.utid == 3)
			{
				orders = OrderService.GetAllOrders();
			}
			else if(user.utid == 2)
			{
				orders = OrderService.GetAllOrders(user.ulid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(orders == null || orders.size() == 0) {
			request.getSession().setAttribute("noOrderFound", true);
		} else {
			request.getSession().setAttribute("noOrderFound", false);
		}
		
		request.getSession().setAttribute("orders", orders);	
		request.getSession().setAttribute("statusList", statusList);		
	}
	
	
	/**
	 * Returns list of possible status of an order 
	 * @return statusList ArrayList<OrderStatus>
	 */
	private ArrayList<OrderStatus> getStatusList()
	{
		OrderStatus send = new OrderStatus(null, "Gesendet");
		OrderStatus inprocess = new OrderStatus(null, "In Bearbeitung");
		OrderStatus ondelivery = new OrderStatus(null, "In Zustellung");
		OrderStatus finished = new OrderStatus(null, "Abgeschlossen");
		
		ArrayList<OrderStatus> statusList = new ArrayList<OrderStatus>();
		
		statusList.add(send);
		statusList.add(inprocess);
		statusList.add(ondelivery);
		statusList.add(finished);
		
		return statusList;
	}
}
