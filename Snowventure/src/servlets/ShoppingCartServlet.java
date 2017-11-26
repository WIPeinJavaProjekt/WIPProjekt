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
 * Servlet für die Anzeige und Berechnungen des Warenkorbs
 */
@WebServlet("/cart")
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ShoppingCartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ShoppingCart currentCart = (ShoppingCart) request.getSession().getAttribute("currentCart");
		//User currentUser = (User) request.getSession().getAttribute("currentUser");

		if(currentCart == null) {
			System.out.println("Cart is Null");
			currentCart = new ShoppingCart();
		}
		
		for(ShoppingCartPosition scp: currentCart.cartPositions) {
			System.out.println("WARENKORB Artikel: " + scp.article.getName() + " Amount: " + scp.amount + " Size: " + scp.size);
		}
		
		/*
		if(currentUser != null && currentCart != null && currentUser.shoppingcart.cartPositions.size() == 0)
		{
			currentUser.shoppingcart = currentCart;
			request.getSession().setAttribute("currentUser", currentUser);
		}
		*/

		if(request.getParameter("scpid")!= null && request.getParameter("amount") != null)
		{
			String scpid = request.getParameter("scpid").toString();
			String amount = request.getParameter("amount").toString();
			
			if(isIntegerValue(amount))
			{
			int newamount = Integer.parseInt(amount);
		
			if(newamount > 0) 
				{changeSCPAmount(scpid, newamount, request);}
			else 
				{deleteShoppingCartPosition(request.getParameter("scpid"), request);}			
			//System.out.println("changed amount of SCP:" + currentCart.cartPositions.get(Integer.parseInt(scpid)).amount + "  to  " + amount);
			}
			response.sendRedirect(request.getContextPath() + "/cart");
			return;		
		}
		else if(request.getParameter("scpid")!= null && request.getParameter("option") != null && request.getParameter("option").toString().equals("delete"))
		{
			deleteShoppingCartPosition(request.getParameter("scpid"), request);
			//System.out.println("deleted cart-position: " + request.getParameter("scpid"));
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
		ShoppingCart cart = (ShoppingCart)request.getSession().getAttribute("currentCart");
		
		/*
		User user = (User)request.getSession().getAttribute("currentUser");
		
		if(user != null && cart != null)
		{
			user.shoppingcart = cart;
		}

		
		if(user != null && user.shoppingcart != null && user.shoppingcart.cartPositions != null && user.shoppingcart.cartPositions.size() > 0)
		{			
			ShoppingCartPosition scp = user.shoppingcart.cartPositions.get(Integer.parseInt(scpid));
			
			if(scp != null)
			{
				System.out.print("Delete Article " + scp.article.acid + " from shoppingcart.");
				user.shoppingcart.cartPositions.remove(scp);
				ShoppingCartService.UpdateShopping(user);
				request.getSession().setAttribute("currentUser", user);
				return;
			}
		}
		else 
		*/
		if(cart != null && cart.cartPositions.size() > 0)
		{
			ShoppingCartPosition scp = cart.cartPositions.get(Integer.parseInt(scpid));
			
			if(scp != null)
			{
				cart.cartPositions.remove(scp);
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
		//User user = (User)request.getSession().getAttribute("currentUser");
		ShoppingCart cart = (ShoppingCart)request.getSession().getAttribute("currentCart");
		
		/* 
		if(user != null && cart != null)
		{
			user.shoppingcart = cart;
		}
		*/	

		try {
				/*
				if(user != null && user.shoppingcart != null && user.shoppingcart.cartPositions != null && user.shoppingcart.cartPositions.size() > 0)
				{			
					if (newamount <= StockService.GetStock(user.shoppingcart.cartPositions.get(Integer.parseInt(scpid)).article.versions.get(user.shoppingcart.cartPositions.get(Integer.parseInt(scpid)).article.GetSelectedVersion())))
					{
						ShoppingCartPosition scp = user.shoppingcart.cartPositions.get(Integer.parseInt(scpid));
						
						if(scp != null)
						{
							System.out.print("Update Article " + scp.article.acid + " amount from " + scp.amount + " to " + newamount);
							scp.amount = newamount;
							ShoppingCartService.UpdateShopping(user);
							request.getSession().setAttribute("currentUser", user);
							return;
						}
					}
					else 
					{
						System.out.println("out of stock - amount is higher than stock value");
					} 
				}	
				else 
				*/
				
				if (cart != null)
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
	
	
	/**
	 * Method for checking if the given string is convertable into an integer value.
	 * @param value String
	 * @return isInt boolean
	 */
	private boolean isIntegerValue(String value)
	{
		boolean isInt = true;
		
		try
		{
			Integer.parseInt(value);
		}
		catch (NumberFormatException ex) 
		{
		    isInt = false;
		}
		
		return isInt;
	}
}
