package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * Beschreibung: Servlet f�r die Bearbeitung und R�ckgabe von Nutzern sowie Zugriffe innerhalb des Nutzerkontos.
 * @author Jacob Markus
 *
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
					
				response.sendRedirect("users?page=ordersearch&specified");
				
				return;
			}
			else if(user != null && user.utid == 2 && request.getSession().getAttribute("orders") == null && request.getParameter("searchOrders") == null && request.getParameter("specified") == null && request.getParameter("page") != null && request.getParameter("page").toString().equals("ordersearch") && request.getParameter("all") == null) 
			{
				findOrders(request, user);
					
				response.sendRedirect("users?page=ordersearch&all");
				
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
				
				if(request.getParameter("falsepassword")==null && request.getSession().getAttribute("passworderror")!=null)
				{
					request.getSession().removeAttribute("passworderror");
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
		
		if(user != null)
		{
			if(request.getParameter("update-data") != null){			
				updateCurrentUser(user, request);
			}
			else if(request.getParameter("update-password") != null){				
				if(!changePassword(request))
				{
					RequestDispatcher rd = request.getRequestDispatcher("/JSP/User/useraccount.jsp?page=mydata&falsepassword");
					rd.forward(request, response);
					return;
				}
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
			else if(request.getParameter("deleteUserByUser") != null)
			{
				user.techisactive = 0;
				user.techisdeleted = 1;
				
				UserService.UpdateUser(user);
				
				request.getSession().removeAttribute("currentUser");
				response.sendRedirect("start");
				return;
			}
		}
				
		doGet(request, response);
	}

	
	
	/**
	 * Die "findArticles"-Methode gibt alle Artikel zur�ck, die bestimmten Suchkriterien(Name, Kategorie) entsprechen.
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
	 * Die "changePassword"-Methode �berpr�ft ob das gegebene Passwort, dem des aktuellen Nutzers entspricht 
	 * und ersetzt im Fall der �bereinstimmung das Nutzerpasswort durch das neue Passwort. 
	 * 
	 * @param request HttpServletRequest
	 * @throws IOException
	 * @return boolean
	 */
	private boolean changePassword(HttpServletRequest request) throws IOException
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
				return true;
			}
			else {
				System.out.println("Die Passw�rter stimmen nicht �berein.");
				request.getSession().setAttribute("passworderror", "Die Passw�rter stimmen nicht �berein. Eine �nderung konnte nicht vorgenommen werden.");
				return false;
			}		
		}		
		
		System.out.println("Das angegebene Nutzerpasswort ist falsch.");
		request.getSession().setAttribute("passworderror", "Das angegebene Nutzerpasswort ist falsch.");
		return false;
	}	
	
	
	
	/** 
	 * Die "searchforUsers"-Methode erstellt eine Nutzerliste aus allen Nutzern, die bestimmten Suchkriterien(Nutzername, Nutzertyp) entsprechen. 
	 * Sofern eine Nutzerliste besteht, wird diese in ein neues Attribut der aktuellen Session �bergeben. 
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException
	 * 
	 */
	private void searchforUsers(HttpServletRequest request, HttpServletResponse response) throws IOException
	{		
		List<User> userlist = null;
		
		try 
		{						
			userlist = UserService.GetUsers(request.getParameter("user-info").replaceAll("[^a-zA-Z 0-9\\u002E\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", ""), Integer.parseInt(request.getParameter("categories").toString()));
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
	 * Die "getSelectedUser"-Methode sucht nach dem Nutzer mit dem gegebenen Nutzernamen 
	 * und setzt ihn, wenn vorhanden, in ein Attribut der aktuellen Session.
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException
	 * 
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
	 * Die "updateCurrentUser"-Methode aktualisiert Daten und Rechte des aktuell eingeloggten Nutzers.
	 * Anschlie�end wird das currentUser-Objekt der aktuellen Session aktualisiert.
	 * 
	 * @param user User
	 * @param request HttpServletRequest
	 * @throws IOException
	 * 
	 */
	private void updateCurrentUser(User user, HttpServletRequest request) throws IOException
	{
		String location = request.getParameter("location").replaceAll("[^a-zA-Z 0-9\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "");
		String houseno = request.getParameter("houseno").replaceAll("[^a-zA-Z 0-9\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "");
		String street = request.getParameter("street").replaceAll("[^a-zA-Z 0-9\\u002E\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "");
		String postcode = request.getParameter("postcode").replaceAll("[^a-zA-Z 0-9\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "");
		String surname = request.getParameter("surname").replaceAll("[^a-zA-Z 0-9\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "");
		String name = request.getParameter("name").replaceAll("[^a-zA-Z 0-9\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "");	
						
		Adress adress = new Adress(location.length() > 0 ? location : user.adress.location, houseno.length() > 0 ? houseno : user.adress.houseno,
									postcode.length() > 0 ? postcode : user.adress.postcode, street.length() > 0 ? street : user.adress.street);		

		String usertype = request.getParameter("state");
		
		user.adress = adress;
		
		if(validateEmail(request.getParameter("email")))
		user.email = request.getParameter("email");
		
		if(isNumber(request.getParameter("phone").toString())) 
		{
			user.phone = request.getParameter("phone");
		}
		
		user.surname = surname.length() > 0 ? surname: user.surname;
		user.name = name.length() > 0 ? name: user.name;			
		
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
		String safetyAnswer =  request.getParameter("safetyAnswer").replaceAll("[^a-zA-Z 0-9\\u002E\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "").trim();
		
		if(safetyAnswer.length() > 0)
		{
		user.squestion = new Safetyquestion(Integer.parseInt(request.getParameter("safetyQuestion").toString()), "", safetyAnswer);
		
		UserService.UpdateUser(user);
		
		request.getSession().setAttribute("currentUser", user);
		
		System.out.println("sQuestion updated.");	
		}
	}
	
	
	
	/** 
	 * Die "updateSelectedUser"-Methode aktualisiert Daten und Rechte des ausgew�hlten Nutzers.
	 * Anschlie�end wird das selectedUser-Objekt der aktuellen Session aktualisiert.
	 * 
	 * @param user User
	 * @param request HttpServletRequest
	 * @throws IOException
	 * 
	 */
	private void updateSelectedUser(User user, HttpServletRequest request) throws IOException
	{
		//Remove special expressions from input strings
		String location = request.getParameter("location").replaceAll("[^a-zA-Z 0-9\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "");
		String houseno = request.getParameter("houseno").replaceAll("[^a-zA-Z 0-9\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "");
		String street = request.getParameter("street").replaceAll("[^a-zA-Z 0-9\\u002E\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "");
		String postcode = request.getParameter("postcode").replaceAll("[^a-zA-Z 0-9\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "");
		String surname = request.getParameter("surname").replaceAll("[^a-zA-Z 0-9\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "");
		String name = request.getParameter("name").replaceAll("[^a-zA-Z 0-9\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "");
		String safetyAnswer =  request.getParameter("safetyAnswer").replaceAll("[^a-zA-Z 0-9\\u002E\\u002D\\u00E4\\u00F6\\u00FC\\u00C4\\u00D6\\u00DC\\u00df]+", "");
		
		Adress adress = new Adress(location.length() > 0 ? location : user.adress.location, houseno.length() > 0 ? houseno : user.adress.houseno,
									postcode.length() > 0 ? postcode : user.adress.postcode, street.length() > 0 ? street : user.adress.street);		
		
		if(safetyAnswer.length() > 0)				
		user.squestion = new Safetyquestion(Integer.parseInt(request.getParameter("safetyQuestion").toString()), "", safetyAnswer);

		String password = request.getParameter("password");
		String passwordrepeat = request.getParameter("passwordRepeat");
		
		request.getSession().removeAttribute("error");
		
		//Validate and set the new password of the selected user
		if(password != "" && passwordrepeat != "" && password.equals(passwordrepeat))
		{
			user.password = request.getParameter("password").toString();
		}
		else if(password != "" && passwordrepeat != "")
		{
			request.getSession().setAttribute("error", "Die Passw�rter stimmen nicht �berein.");
		}
		
		if(isNumber(request.getParameter("phone")))
		{
			user.phone = request.getParameter("phone");
		}
		
		String usertype = request.getParameter("state");
		
		user.adress = adress;
		
		if(validateEmail(request.getParameter("email")))
		user.email = request.getParameter("email");
		
		user.surname = surname.length() > 0 ? surname: user.surname;
		user.name = name.length() > 0 ? name: user.name;			

		//Check if the usertype of the selected user was changed
		if(usertype != null && usertype != "")
		{
			user.utid = usertype.equals("admin")? 1 : usertype.equals("employee")? 3 : 2;
		}
				
		//Check if the status of the selected user was changed
		if(request.getParameter("user-status") == null && user.techisactive == 1)
		{
			user.techisactive = 0;
			user.techisdeleted = 1;
			System.out.println("deactivate user");
		}
		else if(request.getParameter("user-status") != null && request.getParameter("user-status").toString().equals("on") && user.techisactive == 0)
		{			
			user.techisactive = 1;
			user.techisdeleted = 0;
			System.out.println("activate user");
		}
		
		UserService.UpdateUser(user);	
		UserService.UpdateUserRights(user, user.utid);
		
		request.getSession().setAttribute("selectedUser", user);
	}
	
	
	
	/**
	 * Die "findOrders"-Methode gibt alle Bestellungen zur�ck, die den gegebenen Suchkriterien
	 * und dem Zugriffsrecht des aktuellen Nutzers entsprechen.
	 * 
	 * @param request HttpServletRequest
	 * @param user User
	 * @throws IOException 
	 */
	private void findOrders(HttpServletRequest request, User user) throws IOException 
	{		
		String searchOrderPattern = request.getParameter("searchOrderPattern");
		
		ArrayList<OrderStatus> statusList = getStatusList();
				
		ArrayList<Order> orders = new ArrayList<Order>();
				
		try {
			if((user.utid == 1 || user.utid == 3) && searchOrderPattern != null && searchOrderPattern != "") 
			{
				System.out.println("Selected Categorie Orders: " + request.getParameter("categorie").toString()); 
				
				if(Integer.parseInt(request.getParameter("categorie").toString()) == 0 && isNumber(searchOrderPattern))
				{
					Order order = OrderService.GetSpecificOrder(Integer.parseInt(searchOrderPattern));
					if (order != null) 
					{ orders.add(order);}
				}
				else if (Integer.parseInt(request.getParameter("categorie").toString()) == 1)
				{
					//get Order by status
					OrderStatus status = new OrderStatus(null, searchOrderPattern.replaceAll("[^\\p{L}\\p{Z}]", ""));
					orders = OrderService.GetAllOrders(status);
					
				}
				else if (Integer.parseInt(request.getParameter("categorie").toString()) == 2)
				{
					//get orders of a specific user
					User orderUser = UserService.GetUser(searchOrderPattern);
					if(orderUser != null)
					{
						orders = OrderService.GetAllOrders(orderUser.ulid);
					}
				}
			}
			else if(user.utid == 2 && searchOrderPattern != null && searchOrderPattern != "")
			{
				if(Integer.parseInt(request.getParameter("categorie").toString()) == 0 && isNumber(searchOrderPattern))
				{
					Order order = OrderService.GetSpecificOrder(Integer.parseInt(searchOrderPattern));
					if (order != null && order.ulid == user.ulid) 
					{ orders.add(order);}
				}
				else if (Integer.parseInt(request.getParameter("categorie").toString()) == 1)
				{
					//get Orders of a specific user with a specific status
					OrderStatus status = new OrderStatus(null, searchOrderPattern.replaceAll("[^\\p{L}\\p{Z}]", ""));
					orders = OrderService.GetAllOrders(status, user.ulid);
				}
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
	 * Die "getStatusList"-Methode gibt eine Liste mit m�glichen Status zur�ck, die eine Bestellung annehmen kann.
	 * 
	 * @return statusList ArrayList<OrderStatus>
	 */
	private ArrayList<OrderStatus> getStatusList()
	{
		OrderStatus send = new OrderStatus(null, "Neu");
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
	
	
	
	/** 
	 * @param pattern String
	 * @return boolean 
	 * @throws IOException
	 * 
	 * Die "isNumber"-Methode �bepr�ft ob jedes Zeichen des Strings eine Zahl ist.
	 */
	private boolean isNumber(String pattern) throws IOException 
	{
	  for (int i = 0; i < pattern.length(); i++) {
	    if (!Character.isDigit(pattern.charAt(i))) {
	      return false;
	    }
	  }

	  return true;
	}
	
	
	
	/** 
	 * @param email String
	 * @return boolean 
	 * @throws IOException
	 * 
	 * Die "validateEmail"-Methode �bepr�ft ob die eingegebene Email-Adresse valide ist.
	 */
	private boolean validateEmail(String email) throws IOException 
	{
		Pattern p = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
		Matcher m = p.matcher(email);
		boolean validEmail = m.matches();

		return validEmail;
	}
}
