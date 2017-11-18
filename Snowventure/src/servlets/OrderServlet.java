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

import classes.Order;
import classes.OrderStatus;
import classes.ShoppingCart;
import classes.User;
import services.OrderService;

/**
 * Servlet zur Anzeige und Bearbeitung von Bestellungen
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OrderServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		ShoppingCart currentCart = (ShoppingCart) request.getSession().getAttribute("currentCart");
	
		if(currentUser != null)
		{
			try {
			
			String OrderID = request.getParameter("ID");
			
			if(OrderID != null && OrderID.toString() != "")
			{
				Order order = OrderService.GetSpecificOrder(Integer.parseInt(OrderID));
				request.getSession().setAttribute("currentOrder", order);
				
				RequestDispatcher rd = request.getRequestDispatcher("/JSP/Orders/orderdetails.jsp");
				rd.forward(request, response);
			}
			else if(request.getParameter("neworder") != null && currentCart != null)
			{
				Order neworder = new Order(currentUser.adress, currentCart, '0', currentUser.name, currentUser.surname, currentUser.email, currentUser.ulid);
				
				System.out.println("order in process");
				
				request.getSession().setAttribute("currentOrder", neworder);
				
				System.out.println("order open details");

				RequestDispatcher rd = request.getRequestDispatcher("/JSP/Orders/orderdetails.jsp");
				rd.forward(request, response);
			}
			else 
			{
				response.sendRedirect("users?page=ordersearch");
			}
			
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else 
		{
			response.sendRedirect("login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
