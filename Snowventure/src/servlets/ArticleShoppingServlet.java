package servlets;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Article;
import classes.ArticleColor;
import classes.ArticleVersion;
import classes.ShoppingCart;
import classes.ShoppingCartPosition;
import services.ArticleColorService;
import services.ArticleService;
import services.StockService;

/**
 * Servlet implementation class ArticleServlet
 */
/**
 * Beschreibung: Servlet zum hinzufügen eines Artikels in den Warenkorb
 * @author Garrit Kniepkamp, Jacob Markus
 *
 */
@WebServlet("/articleshopping")
@MultipartConfig
public class ArticleShoppingServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Article article = new Article();
	private ArticleVersion articleVersion = new ArticleVersion();	
       
    public ArticleShoppingServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("ID") != null) {
			try {
				this.article = ArticleService.GetArticle(Integer.parseInt(request.getParameter("ID")));
				this.article.setSelectedVersion(Integer.parseInt(request.getParameter("version")));
				
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		} else {
			this.article = null;
			request.getSession().setAttribute("updateArticle", false);
		}
		
		if(request.getParameter("amounterror") == null)
		{
			request.getSession().removeAttribute("error");
		}
		
		if(this.article!=null) {
			request.setAttribute("article", this.article);
			request.setAttribute("availableVersions", this.article.versions.size()-1);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/Articles/articleDetailsCustomer.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("addToCart") != null) {
			try {
				request.getSession().removeAttribute("error");
				
				if(checkArticleStock(request, response))
				{
					addArticleToCart(request, response);
					response.sendRedirect("articles");
					return;
				}
				else 
				{
					response.sendRedirect("articleshopping?ID=" + this.article.ID + "&version=" + this.article.getSelectedVersion() + "&amounterror");
					return;
				}
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
//			response.sendRedirect("articles");
		} else if(request.getParameter("selectedSize") != null) {
			String selectedSize = request.getParameter("selectedSize");
			request.getSession().setAttribute("selectedSize", selectedSize);
			
			response.sendRedirect("articleshopping?ID=" + this.article.ID + "&version=" + this.article.getSelectedVersion());
			return;
		} else if(request.getParameter("selectColor") != null) {
			
			return;
		}
		
		doGet(request, response);
	}

	/**
	 * Methode zum Hinzufügen eines Artikels in den Warenkorb. Es wird kontrolliert, ob die gleiche Artikelversion bereits im Warenkorb vorhanden ist.
	 * @see checkForDouble
	 * @param request
	 * @param response
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	private void addArticleToCart(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException {
		ShoppingCart currentCart = (ShoppingCart) request.getSession().getAttribute("currentCart");
		if(currentCart == null) {
			currentCart = new ShoppingCart();
		}
		
				
		ShoppingCartPosition cartPosition = null;
		cartPosition = new ShoppingCartPosition(this.article, Integer.parseInt(request.getParameter("amount")), request.getParameter("selectedSize"));
				
		if(!checkforDouble(currentCart, cartPosition, Integer.parseInt(request.getParameter("amount"))))
		{ currentCart.cartPositions.add(cartPosition); }
		request.getSession().setAttribute("currentCart", currentCart);
	}

	/**
	 * Die Methode kontrolliert, ob ein Artikel bereits im Warenkorb vorhanden ist. Ist dies der Fall, so wird nur die Anzahl des Artikels geändert, 
	 * andernfalls wird der Artikel dem Warenkorb hinzugefügt.
	 * @param sc ShoppingCart to compare with
	 * @param scPos CartPosition, which should be added
	 * @param amount
	 * @return boolean
	 */
	private boolean checkforDouble(ShoppingCart sc, ShoppingCartPosition scPos, int amount)
	{
		if(sc.cartPositions.size() > 0)
		{
			for(ShoppingCartPosition scp : sc.cartPositions)
			{						
				if(		scp.article.ID == scPos.article.ID 
						&& scp.article.getSelectedVersion() == scPos.article.getSelectedVersion() 
						&& scp.size.toString().equals(scPos.size.toString())
						)
				{
					scp.amount += amount;
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	
	/**
	 * Die "checkArticleStock"-Methode überprüft ob die wünschte Menge des Artikels verfügbar ist.
 	 * @param request
	 * @param response	 
	 * @return boolean Rückgabe true wenn der Artikel verfügbar ist, ansonsten false
	 */
	private boolean checkArticleStock(HttpServletRequest request, HttpServletResponse response)
	{	ShoppingCart currentCart = (ShoppingCart)request.getSession().getAttribute("currentCart");
		int stock = 0;
		
		try 
		{
			int amount = Integer.parseInt(request.getParameter("amount"));
			
			ArticleVersion av = this.article.getSelectedArticleVersion();
			
			if(currentCart != null)
			{
				for(ShoppingCartPosition scp : currentCart.cartPositions)
				{
					if(	scp.article.ID == this.article.ID 
						&& scp.article.getSelectedVersion() == this.article.getSelectedVersion() 
						&& scp.size.toString().equals(request.getParameter("selectedSize").toString())
					)
					{
						amount += scp.amount;
						break;
					}
				}
			}			
			stock = StockService.GetStock(this.article.getSelectedArticleVersion(), request.getParameter("selectedSize"));
			if(stock >= amount)
			{
				return true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch(NumberFormatException e){
			e.printStackTrace();
		}
		if(stock > 0)
		{request.getSession().setAttribute("error", "Die angegebene Menge des gewünschten Artikels ist leider nicht mehr vorhanden. Es sind nur noch " + stock + " Stück auf Lager.");}
		else 
		{request.getSession().setAttribute("error", "Der von Ihnen gewünschte Artikel ist leider nicht mehr verfügbar.");}
		return false;
	}
}
