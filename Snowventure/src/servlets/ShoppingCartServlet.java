package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.ShoppingCart;
import classes.ShoppingCartPosition;
import classes.User;
import services.ArticleService;
import services.ShoppingCartService;
import services.StockService;

/**
 * Servlet f�r die Anzeige und Berechnungen des Warenkorbs
 */
@WebServlet("/cart")
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ShoppingCartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ShoppingCart currentCart = (ShoppingCart) request.getSession().getAttribute("currentCart");
		if(currentCart == null) {
			currentCart = new ShoppingCart();
		}
		
		for(ShoppingCartPosition scp: currentCart.cartPositions) {
			System.out.println("WARENKORB Artikel: " + scp.article.GetName() + " Amount: " + scp.amount);
		}
				
		if(request.getParameter("scpid")!= null && request.getParameter("amount") != null)
		{
			String scpid = request.getParameter("scpid").toString();
			changeSCPAmount(scpid, Integer.parseInt(request.getParameter("amount").toString()), request);
			System.out.println("changed amount of SCP:" + request.getParameter("scpid") + "  to  " + request.getParameter("amount"));
			response.sendRedirect(request.getContextPath() + "/cart");
			return;		
		}
		else if(request.getParameter("scpid")!= null && request.getParameter("option") != null && request.getParameter("option").toString().equals("delete"))
		{
			deleteShoppingCartPosition(request.getParameter("scpid"), request);
			System.out.println("deleted: " + request.getParameter("scpid"));
			response.sendRedirect(request.getContextPath() + "/cart");
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/Articles/shoppingcart.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	/**
	 * Method for deleting a specific position of the shopping cart.
	 * @param scpid String
	 * @param request HttpServletRequest
	 * @throws ServletException, IOException
	 */
	private void deleteShoppingCartPosition(String scpid, HttpServletRequest request) throws ServletException, IOException 
	{
		User user = (User)request.getSession().getAttribute("currentUser");
		ShoppingCart cart = (ShoppingCart)request.getSession().getAttribute("currentCart");
		
		if(user != null && user.shoppingcart != null && user.shoppingcart.cartPositions != null && user.shoppingcart.cartPositions.size() > 0)
		{			
			ShoppingCartPosition scp = user.shoppingcart.cartPositions.get(Integer.parseInt(scpid));
			
			if(scp != null)
			{
				user.shoppingcart.cartPositions.remove(scp);
				//ShoppingCartService.UpdateShopping(user);
				request.getSession().setAttribute("currentUser", user);
				return;
			}
		}
		else if(cart != null && cart.cartPositions.size() > 0)
		{
			ShoppingCartPosition scp = cart.cartPositions.get(Integer.parseInt(scpid));
			
			if(scp != null)
			{
				cart.cartPositions.remove(scp);
				//ShoppingCartService.UpdateShopping(user);
				request.getSession().setAttribute("currentCart", cart);
				return;
			}

		}
	}
	
	
	/**
	 * Method for changing the amount of a specific shopping cart position.
	 * @param scpid String
	 * @param newamount integer
	 * @param request HttpServletRequest
	 * @throws ServletException, IOException
	 */
	private void changeSCPAmount(String scpid, int newamount,  HttpServletRequest request)throws ServletException, IOException 
	{
		User user = (User)request.getSession().getAttribute("currentUser");
		ShoppingCart cart = (ShoppingCart)request.getSession().getAttribute("currentCart");

		try {
				if(user != null && user.shoppingcart != null && user.shoppingcart.cartPositions != null && user.shoppingcart.cartPositions.size() > 0)
				{			
					//if (newamount <= StockService.GetStock(user.shoppingcart.cartPositions.get(Integer.parseInt(scpid)).article.versions.get(user.shoppingcart.cartPositions.get(Integer.parseInt(scpid)).article.GetSelectedVersion())))
					//{
						ShoppingCartPosition scp = user.shoppingcart.cartPositions.get(Integer.parseInt(scpid));
						
						if(scp != null)
						{
							scp.amount = newamount;
							//ShoppingCartService.UpdateShopping(user);
							request.getSession().setAttribute("currentUser", user);
							return;
						}
					//}
					//else 
					//{
						//System.out.println("out of stock - amount is higher than stock value");
					//} 
				}	
				else if (cart != null)
				{
					ShoppingCartPosition scp = cart.cartPositions.get(Integer.parseInt(scpid));
					if(scp != null)
					{
						scp.amount = newamount;
						request.getSession().setAttribute("currentCart", cart);
						return;
					}
				}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
