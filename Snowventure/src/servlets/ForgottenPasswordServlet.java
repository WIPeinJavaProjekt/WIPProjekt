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
 * Servlet implementation class ForgottenPasswordServlet
 */
@WebServlet("/forgottenPassword")
public class ForgottenPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgottenPasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("param") != null)
		{
			request.getSession().removeAttribute("userpw");
			request.getSession().removeAttribute("correctAnswer");
			request.getSession().removeAttribute("pwerror");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/User/forgottenpassword.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("back") != null)
		{
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		else if(request.getParameter("confirmusername") != null)
		{
			request.getSession().removeAttribute("userpw");
			request.getSession().removeAttribute("correctAnswer");
			request.getSession().removeAttribute("pwerror");
			
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
			String npw = request.getParameter("newpassword");
			String npwrepeat = request.getParameter("newpasswordrepeat");
			
			if(npw.toString().equals(npwrepeat.toString()))
			{
				User user = (User)request.getSession().getAttribute("userpw");
				
				user.password = npw;
				
				UserService.UpdateUser(user);
				
				request.getSession().removeAttribute("userpw");
				request.getSession().removeAttribute("correctAnswer");
				request.getSession().removeAttribute("pwerror");
				
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}
			else 
			{
				request.getSession().setAttribute("pwerror", "Die Passwörter stimmen nicht überein.");
			}
		}
		doGet(request, response);
	}
}
