package servlets;

import java.io.IOException;
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
				this.article.SetSelectedVersion(Integer.parseInt(request.getParameter("version")));
				
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
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			response.sendRedirect("articles");
		}
		
		doGet(request, response);
	}

	private void addArticleToCart(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException {
		ShoppingCart currentCart = (ShoppingCart) request.getSession().getAttribute("currentCart");
		if(currentCart == null) {
			currentCart = new ShoppingCart();
		}
		
		ArticleColor color = ArticleColorService.GetSpecificColor(Integer.parseInt(request.getParameter("color")));
		ShoppingCartPosition cartPosition = new ShoppingCartPosition(this.article, Integer.parseInt(request.getParameter("amount")),request.getParameter("size"),color);
		
		currentCart.cartPositions.add(cartPosition);		
		request.getSession().setAttribute("currentCart", currentCart);
	}

	
}
