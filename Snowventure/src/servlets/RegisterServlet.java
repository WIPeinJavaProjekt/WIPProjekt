package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
import services.*;

/**
 * Beschreibung: Serlvet f�r die Registrierung von neuen Nutzern
 * @author Garrit Kniepkamp
 *
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }
    
    private Adress adress = new Adress();
    private User user = new User();
    private Safetyquestion sfQuestion = new Safetyquestion();

    /**
     * doGet loads user-data when it's not null (e.g. when passwords do not match - so that the other fields keep filled with data)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.user.adress = this.adress;
		this.user.squestion = this.sfQuestion;
		
		try {
			ArrayList<Safetyquestion> squestions = SafetyquestionService.GetSafetyquestion();
			request.setAttribute("squestions", squestions);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(this.user!=null && this.adress!=null) {
			request.setAttribute("location", this.user.adress.location);
			request.setAttribute("houseno", this.user.adress.houseno);
			request.setAttribute("street", this.user.adress.street);
			request.setAttribute("postcode", this.user.adress.postcode);
			
			request.setAttribute("username", this.user.username);
			request.setAttribute("name", this.user.name);
			request.setAttribute("surname", this.user.surname);
			request.setAttribute("email", this.user.email);
			
			request.setAttribute("safetyQuestion", this.user.squestion.getAnswer());
			request.setAttribute("safetyAnswer", this.user.squestion.getQuestion());
		}
		
		request.getSession().removeAttribute("selectedUser");
		request.getSession().removeAttribute("error");
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/User/register.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if(request.getParameter("back") != null) {
			
			response.sendRedirect("login");
			return;
		} else if(request.getParameter("submitRegister") != null) {
			
			int retVal = registrate(request);
			
			if(retVal == 0) {
				
				if(request.getSession().getAttribute("currentUser")==null || ((User)(request.getSession().getAttribute("currentUser"))).utid!=1)
				{
					response.sendRedirect("start");
				}
				else {
					response.sendRedirect("users?page=usersearch");
				}
				return;
			}
			
		}
		
		doGet(request, response);
		
	}
	
	/**
	 * 
	 * @param request HttpServletRequest
	 * @return int-value (0 for success, -1 for error)
	 * @throws IOException
	 * 
	 * Registrierungs-Methode um neue Nutzer in der Datenbank zu speichern.
	 * Die eingegebenen Passw�rter werden vorher auf Gleicheit �berpr�ft.
	 * Es wird auch kontrolliert, ob bereits ein Nutzer mit dem gleichen Benutzernamen vorhanden ist.
	 */
	private int registrate (HttpServletRequest request) throws IOException {
		
		String password = request.getParameter("password");
		String passwordRepeat = request.getParameter("passwordRepeat");
		String usertype = request.getParameter("state");
		
		this.adress = new Adress(request.getParameter("location"), request.getParameter("houseno"),
				request.getParameter("postcode"), request.getParameter("street"));
		this.sfQuestion = new Safetyquestion(Integer.parseInt(request.getParameter("safetyQuestion")), "", request.getParameter("safetyAnswer"));
		this.user = new User(this.sfQuestion, request.getParameter("username"), request.getParameter("password"), request.getParameter("name"),
				request.getParameter("surname"), this.adress, request.getParameter("email"), usertype == null ? 2 : usertype.toString().equals("admin")? 1 : usertype.toString().equals("employee")? 3 : 2);
		
		if(!password.equals(passwordRepeat)) {
			
			request.setAttribute("error", "Die Passw�rter stimmen nicht �berein");			
			return -1;
			
		} else
			try {
				if(UserService.GetUser(this.user.username) != null){
					request.setAttribute("error", "Ein Benutzer mit dem angegebenen Namen ist bereits vorhanden!");			
					return -1;
				} else {
					int result = UserService.AddUser(this.user);
					this.user = new User();
					this.adress = new Adress();
					
					return 0;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return -1;
	}

}
