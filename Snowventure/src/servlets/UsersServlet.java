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
		
		if(request.getParameter("search-user") != null || request.getParameter("back") != null)
		{			
			searchforUsers(request, response);
			RequestDispatcher rd = request.getRequestDispatcher("/JSP/User/useraccount.jsp?page=usersearch");
			rd.forward(request, response);
			return;
		}		
		else if (request.getParameter("selecteduser") != null)
		{
			request.getSession().removeAttribute("error");
			getSelectedUser(request, response);
			RequestDispatcher rd = request.getRequestDispatcher("/JSP/User/useraccount.jsp?page=userinfo");
			rd.forward(request, response);
			return;
		}
		else if(request.getParameter("searchArticles") != null) {
			findArticles(request);
			
			response.sendRedirect("users?page=articlesearch");
			
//			RequestDispatcher rd = request.getRequestDispatcher("/JSP/User/useraccount.jsp?page=articlesearch");
//			rd.forward(request, response);
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

	private void findArticles(HttpServletRequest request) {
		String searchArticlePattern = request.getParameter("searchArticlePattern");
		ArrayList<Article> articles = null;
		
		try {
			if(searchArticlePattern.equals("") || searchArticlePattern == null) {
				articles = ArticleService.GetAllArticles();
			} else {
				articles = ArticleService.GetAllArticlesByName(searchArticlePattern);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(articles.size() == 0) {
			request.getSession().setAttribute("noArticleFound", true);
		} else {
			request.getSession().setAttribute("noArticleFound", false);
		}
		
		for(Article a: articles) {
			System.out.println("Artikel:");
			System.out.println(a.ID);
			System.out.println(a.name);
			System.out.println(a.description);
			System.out.println(a.GetPrice());
			System.out.println(a.GetSelectedVersion());
			System.out.println("Versions:");
			System.out.println(a.versions.size());
			for(ArticleVersion av: a.versions) {
				System.out.println(av.price);
				System.out.println(av.property);
				System.out.println(av.propertyvalue);
				System.out.println(av.versionid);
			}
			System.out.println("---------------------------------------------------");
		}
		
		request.getSession().setAttribute("articles", articles);
		
		System.out.println("Anzahl Artikel: " + articles.size());
		System.out.println(searchArticlePattern);
		
	}
	
	/** 
	 * @param request HttpServletRequest
	 * @throws IOException
	 * 
	 * The "changePassword"-method checks whether the given password is correct for this specific user and can be updated to the new password.
	 */
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
			userlist = UserService.GetUsers(request.getParameter("user-info"));
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
}
