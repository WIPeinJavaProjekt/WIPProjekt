package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShoppingCartServlet
 */
@WebServlet("/cart")
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCartServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("scpid")!= null && request.getParameter("amount") != null)
		{
			changeSCPAmount(request.getParameter("scpid"), Integer.parseInt(request.getParameter("amount")));
			System.out.println("changed amount of SCP:" + request.getParameter("scpid") + "  to  " + request.getParameter("amount"));
			response.sendRedirect(request.getContextPath() + "/cart");
			return;		
		}
		else if(request.getParameter("scpid")!= null && request.getParameter("option") != null && request.getParameter("option").toString().equals("delete"))
		{
			deleteShoppingCartPosition(request.getParameter("scpid"));
			System.out.println("deleted: " + request.getParameter("scpid"));
			response.sendRedirect(request.getContextPath() + "/cart");
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/Articles/shoppingcart.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void deleteShoppingCartPosition(String scpid)
	{
		//Löschen der ShoppingCartPosition
	}
	
	private void changeSCPAmount(String scpid, int newamount)
	{
		//Änderung der Menge der ShoppingCartPosition
	}
	
}
