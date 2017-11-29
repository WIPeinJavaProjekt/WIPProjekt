package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Adress;
import classes.Order;
import classes.OrderStatus;
import classes.ShoppingCart;
import classes.User;
import services.OrderService;

/**
 * Beschreibung: Servlet zur Anzeige und Bearbeitung von Bestellungen.
 * @author Jacob Markus
 *
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
		Order currentOrder = (Order) request.getSession().getAttribute("currentOrder");

	
		if(currentUser != null)
		{
			try {
			
			String OrderID = request.getParameter("ID");						
			
			if(OrderID != null && OrderID.toString() != "")
			{
				Order order = OrderService.GetSpecificOrder(Integer.parseInt(OrderID));
				if(order != null && (order.ulid == currentUser.ulid || (currentUser.utid == 1 || currentUser.utid == 3)))
				{
					request.getSession().setAttribute("currentOrder", order);
				
					RequestDispatcher rd = request.getRequestDispatcher("/JSP/Orders/orderdetails.jsp");
					rd.forward(request, response);
					return;
				}
			}
			else if(request.getParameter("neworder") != null && currentCart != null)
			{
				Order neworder = new Order(new Adress(currentUser.adress.location, currentUser.adress.houseno, currentUser.adress.postcode, currentUser.adress.street), currentCart, '0', currentUser.name, currentUser.surname, currentUser.email, currentUser.ulid);
				
				System.out.println("order in process");
				
				request.getSession().setAttribute("currentOrder", neworder);
				
				System.out.println("order open details");

				RequestDispatcher rd = request.getRequestDispatcher("/JSP/Orders/orderdetails.jsp");
				rd.forward(request, response);
				return;
			}
			else if(request.getParameter("processOrder") != null && currentOrder != null && request.getParameter("processOrder").toString().equals("true"))
			{
				System.out.println("Order process started");
				
				currentOrder.statuscycle.add(new OrderStatus(new Date(),"Neu"));
				
				OrderService.AddOrder(currentOrder);
				
				request.getSession().removeAttribute("currentCart");
				request.getSession().removeAttribute("currentOrder");
				
				RequestDispatcher rd = request.getRequestDispatcher("/JSP/Orders/orderConfirmation.jsp");
				rd.forward(request, response);
				return;
			}
			else if(currentOrder != null 
					&& (currentUser.utid == 1 || currentUser.utid == 3)
					&& request.getParameter("saveorder") != null 
					&& request.getParameter("saveorder").toString().equals("true") 
					&& request.getParameter("newstatus") != null
					&& !request.getParameter("newstatus").toString().equals("")
					&& !request.getParameter("newstatus").toString().equals("Neu")
					&& !request.getParameter("newstatus").toString().equals(currentOrder.statuscycle.get(currentOrder.statuscycle.size()-1).description.toString())
					)
			{
				OrderStatus newstatus = new OrderStatus(new Date(), request.getParameter("newstatus").toString());
				currentOrder.statuscycle.add(newstatus);
				
				System.out.println("OrderID: " + currentOrder.orid + "	- New status: " + newstatus.description + " " + newstatus.statusdate );
				
				OrderService.AddOrderStatus(currentOrder.statuscycle.get(currentOrder.statuscycle.size()-1), currentOrder.orid);
				
				response.sendRedirect("order?ID=" + currentOrder.orid);
				return;
			}
						
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			response.sendRedirect("users?page=ordersearch");
			return;
		}
		else 
		{
			response.sendRedirect("login?accessorder=true");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		
		if(currentUser != null && request.getParameter("delivery-adr-submit") != null)
		{
			Order currentOrder = (Order) request.getSession().getAttribute("currentOrder");
			
			if(currentOrder != null)
			{
				currentOrder.name = request.getParameter("name").toString();
				currentOrder.surname = request.getParameter("surename").toString();
				currentOrder.adress.street = request.getParameter("street").toString();
				currentOrder.adress.houseno = request.getParameter("houseno").toString();
				currentOrder.adress.postcode = request.getParameter("postcode").toString();
				currentOrder.adress.location = request.getParameter("location").toString();
			}
			RequestDispatcher rd = request.getRequestDispatcher("/JSP/Orders/orderdetails.jsp");
			rd.forward(request, response);
			return;
		}
		
		doGet(request, response);
	}

}
