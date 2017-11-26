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

/**
 * Servlet implementation class ArticleServlet
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

    /**
     * Checks the URL whether a new Article should be added or an existing article should be modified
     * Depending on the case, an article gets loaded or not
     */
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
				addArticleToCart(request, response);
				response.sendRedirect("articles");
				return;
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
//			response.sendRedirect("articles");
		} else if(request.getParameter("selectedSize") != null) {
			String selectedSize = request.getParameter("selectedSize");
			request.getSession().setAttribute("selectedSize", selectedSize);
			System.out.println(selectedSize);
			response.sendRedirect("articleshopping?ID=" + this.article.ID + "&version=" + this.article.getSelectedVersion());
			return;
		} else if(request.getParameter("selectColor") != null) {
			
			return;
		}
		
		doGet(request, response);
	}

	private void addArticleToCart(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException {
		ShoppingCart currentCart = (ShoppingCart) request.getSession().getAttribute("currentCart");
		if(currentCart == null) {
			currentCart = new ShoppingCart();
		}
		
		ArticleColor color = null;		
		if(request.getParameter("selectColor") != null)
		color = ArticleColorService.GetSpecificColor(Integer.parseInt(request.getParameter("selectColor")));
		
		System.out.println("Selected size: " + request.getParameter("selectedSize"));
				
		ShoppingCartPosition cartPosition = null;
		cartPosition = new ShoppingCartPosition(this.article, Integer.parseInt(request.getParameter("amount")), request.getParameter("selectedSize"), color);
				
		
		if(!checkforDouble(currentCart, cartPosition, Integer.parseInt(request.getParameter("amount"))))
		{ currentCart.cartPositions.add(cartPosition); }
		request.getSession().setAttribute("currentCart", currentCart);
	}

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
}
