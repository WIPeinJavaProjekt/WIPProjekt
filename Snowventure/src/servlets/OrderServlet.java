package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Order;
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
		try {
			
			String OrderID = request.getParameter("ID").toString();
			if(OrderID != "")
			{
				Order order = OrderService.GetSpecificOrder(Integer.parseInt(OrderID));
				request.getSession().setAttribute("currentOrder", order);
				
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
