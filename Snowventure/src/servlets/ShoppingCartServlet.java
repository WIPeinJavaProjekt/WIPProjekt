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
 * Beschreibung: Servlet für die Anzeige und Berechnungen des Warenkorbs.
 * @author Jacob Markus
 *
 */
@WebServlet("/cart")
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ShoppingCartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ShoppingCart currentCart = (ShoppingCart) request.getSession().getAttribute("currentCart");
		
		if(request.getParameter("amountchanged") == null)
		{
			request.getSession().removeAttribute("error");
		}
		
		if(currentCart == null) {
			System.out.println("Cart is Null");
			currentCart = new ShoppingCart();
		}
		
		for(ShoppingCartPosition scp: currentCart.cartPositions) {
			System.out.println("WARENKORB Artikel: " + scp.article.getName() + " Amount: " + scp.amount + " Size: " + scp.size);
		}
		
		if(request.getParameter("scpid")!= null && request.getParameter("amount") != null)
		{
			String scpid = request.getParameter("scpid").toString();
			String amount = request.getParameter("amount");
			
			if(amount != null && amount != "")			
			amount = amount.length() > 8 ? amount.substring(0, 8) : amount;
			
			if(isIntegerValue(amount))
			{
				int newamount = Integer.parseInt(amount);
				boolean isStockAvailable = checkAvailableStock(scpid, newamount, request);
				
				if(isStockAvailable)
				{
					if(newamount > 0) 
						{ changeSCPAmount(scpid, newamount, request); }
					else 
						{ deleteShoppingCartPosition(request.getParameter("scpid"), request); }			
				}
				
				response.sendRedirect(request.getContextPath() + "/cart?amountchanged");
				return;	
			}
			else 
			{
				response.sendRedirect(request.getContextPath() + "/cart");
				return;	
			}
		}
		else if(request.getParameter("scpid")!= null && request.getParameter("option") != null && request.getParameter("option").toString().equals("delete"))
		{
			deleteShoppingCartPosition(request.getParameter("scpid"), request);
			response.sendRedirect(request.getContextPath() + "/cart");
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/Articles/shoppingcart.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("error");
		doGet(request, response);
	}

	
	/**
	 * Die "deleteShoppingCartPosition"-Methode löscht bei Vorhandensein eines ShoppingCart, die ShoppingCartPosition mit dem gegebenen Index aus dem ShoppingCart.
	 * @param scpid String
	 * @param request HttpServletRequest
	 * @throws ServletException, IOException
	 */
	private void deleteShoppingCartPosition(String scpid, HttpServletRequest request) throws ServletException, IOException 
	{
		ShoppingCart cart = (ShoppingCart)request.getSession().getAttribute("currentCart");
		
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
	 * Die "changeSCPAmount"-Methode verändert bei vorhandenem Einkaufswagen die Menge der angegebenen Position auf den neuen Wert.
	 * @param scpid String
	 * @param newamount integer
	 * @param request HttpServletRequest
	 * @throws ServletException, IOException
	 */
	private void changeSCPAmount(String scpid, int newamount,  HttpServletRequest request)throws ServletException, IOException 
	{
		ShoppingCart cart = (ShoppingCart)request.getSession().getAttribute("currentCart");
		
		try {				
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
	 * Die "checkAvailableStock"-Methode überprüft die Verfügbarkeit des Artikels einer Position des Einkaufswagens.
	 * Bei nicht verfügbaren Artikeln wird die Position entweder gelöscht oder die Menge in Abhängigkeit von dem verfügbaren 
	 * Bestand auf einen geringeren Wert gesenkt.
	 * 
	 * @param scpid String
	 * @param newamount integer
	 * @param request HttpServletRequest
	 * @throws ServletException, IOException
	 */
	public boolean checkAvailableStock(String scpid, int newamount,  HttpServletRequest request) throws ServletException, IOException 
	{		
		ShoppingCart cart = (ShoppingCart)request.getSession().getAttribute("currentCart");
		try 
		{				
			if (cart != null)
			{				
				ShoppingCartPosition scp = cart.cartPositions.get(Integer.parseInt(scpid));
				
				System.out.println(Integer.parseInt(scpid) + "   "  +  scp.article.getAllVersions().get(scp.article.getSelectedVersion()));

				int stock =  StockService.GetStock(scp.article.getAllVersions().get(scp.article.getSelectedVersion()), scp.size);
				
				System.out.println("NewAmount: " + newamount + "  -  Stock:" + stock);
				
				if (stock >= newamount && newamount > scp.amount) {return true;}
				if (stock >= newamount && newamount < scp.amount) {return true;}
				if (stock == 0) 
				{	
					deleteShoppingCartPosition(scpid, request);
					request.getSession().setAttribute("error", "Der von Ihnen veränderte Artikel ist leider nicht mehr verfügbar. Er wurde aus Ihrem Einkaufskorb entfernt.");
				}
				else if (stock > 0 && stock < newamount && stock >= scp.amount) 
				{	
					request.getSession().setAttribute("error", "Der Artikel '" + scp.article.name + "' ist in der von Ihnen ausgewählten Menge leider nicht mehr verfügbar. Es sind nur noch " + stock +" Stück auf Lager.");
				}
				else if (stock > 0 && stock < newamount && stock < scp.amount) 
				{	
					scp.amount = stock; 
					request.getSession().setAttribute("error", "Der Artikel ist in der von Ihnen ausgewählten Menge leider nicht mehr verfügbar. Die Menge wurde vorerst in den vorhandenen Lagerbestand geändert.");
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	/**
	 * Die "isIntegerValue"-Methode kontrolliert ob der gegebene String in einen Integer konvertiert werden kann.
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
		catch (Exception e)
		{
			isInt = false;
		}
		
		return isInt;
	}
}
