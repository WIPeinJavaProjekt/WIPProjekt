package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.User;
import services.UserService;

/**
 * Beschreibung: Dieses Servlet behandelt das Zurücksetzen des Passwortes für den Nutzer.
 * @author Garrit Kniepkamp, Jacob Markus
 *
 */
@WebServlet("/forgottenPassword")
public class ForgottenPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ForgottenPasswordServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("param") != null)
		{
			removeResetAttributes(request, response);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/User/forgottenpassword.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("back") != null)
		{
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		else if(request.getParameter("confirmusername") != null)
		{
			removeResetAttributes(request, response);
			
			try {
				User user = UserService.GetUser(request.getParameter("username"));		
				request.getSession().setAttribute("userpw", user);
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
		else if(request.getParameter("confirmanswer") != null)
		{
			User user = (User)request.getSession().getAttribute("userpw");
			if(request.getParameter("safetyAnswer").toString().equals(user.squestion.getAnswer().toString()))
			{
				request.getSession().setAttribute("correctAnswer", "true");
			}
		}
		else if (request.getParameter("submitNewPassword") != null)
		{
			if(changePassword(request, response))
			{return;}
		}
		doGet(request, response);
	}
	
	
	
	/** 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return Returns a boolean value
	 * @throws IOException
	 * 
	 * Die "changePassword"-Methode kontrolliert, ob das gewünschte neue Passwort zwei mal gleich eingegeben wurde 
	 * und setzt das Passwort dann als aktuelles Passwort für den Nutzer in der Datenbank.
	 */
	private boolean changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String npw = request.getParameter("newpassword");
		String npwrepeat = request.getParameter("newpasswordrepeat");
		
		if(npw.toString().equals(npwrepeat.toString()))
		{
			User user = (User)request.getSession().getAttribute("userpw");
			
			user.password = npw;
			
			UserService.UpdateUser(user);
			
			removeResetAttributes(request, response);
			
			response.sendRedirect(request.getContextPath() + "/login");
			return true;
		}
		else 
		{
			request.getSession().setAttribute("pwerror", "Die Passwörter stimmen nicht überein.");
			return false;
		}
	}
	
	
	/** 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException
	 * 
	 * Die "removeResetAttributes"-Methode löscht alle nicht mehr benötigten Attribute aus der Session.
	 */
	private void removeResetAttributes(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.getSession().removeAttribute("userpw");
		request.getSession().removeAttribute("correctAnswer");
		request.getSession().removeAttribute("pwerror");
	}
}
