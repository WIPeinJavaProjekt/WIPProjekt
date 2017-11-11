package servlets;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Article;
import classes.ArticleColor;
import classes.ArticlePicture;
import classes.Categorie;
import classes.User;
import services.ArticleColorService;
import services.ArticleService;
import services.CategorieService;	
import services.ArtilceManufacturerService;
import services.ArticleSizesService;

/**
 * Start servlet implementation for the main page and the navigation
 */
@WebServlet("/start")
public class StartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Article> articles;
		try {
			articles = ArticleService.GetAllArticlesByName("");
			request.getSession().setAttribute("articles", articles);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Rufe alle  Artikel ab");
		
		try {
			ArrayList<Categorie> categories = CategorieService.GetCategories();
			request.getSession().setAttribute("categories", categories);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			ArrayList<String> manufacturers = ArtilceManufacturerService.GetAllPossibleManufacturers();
			System.out.println("hersteller:"+manufacturers.size());
			request.getSession().setAttribute("manufacturers", manufacturers);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			ArrayList<String> sizes = ArticleSizesService.GetAllPossibleSizes();
			request.getSession().setAttribute("sizes", sizes);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		try {
			ArrayList<ArticleColor> colors = ArticleColorService.GetAllPossibleColors();
			request.getSession().setAttribute("colors", colors);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		
		System.out.println("Current User: " + currentUser != null ? "Kein User" : currentUser.name);
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/welcome.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		
		
		if(request.getParameter("login") != null) {
			
			response.sendRedirect(request.getContextPath() + "/login");
			return;
			
		} else if(request.getParameter("logout") != null) {
			logout(request);
			response.sendRedirect("start");
			return;
			
		} else if(request.getParameter("start") != null) {
			response.sendRedirect("start");
			return;
			
		} else if(request.getParameter("search") != null) {	
			
			String searchPattern = request.getParameter("searchArticlePattern");
			
			findArticles(request, searchPattern);
			
			response.sendRedirect("articles");			
			return;
			
		} else if(request.getParameter("settings") != null) {	
			response.sendRedirect("users");
			return;
		}  else if(request.getParameter("cart") != null) {	
			response.sendRedirect("cart");
			return;
		} 
		
		doGet(request, response);
	}
	
	/**
	 * Logout-method to handle the user logout.
	 * @param request HttpServletRequest
	 */
	public void logout(HttpServletRequest request) {
		request.getSession().setAttribute("currentUser", null);
		request.getSession().invalidate();
	}
	
	/**
	 * Returns articles matching the search-pattern to the request
	 * @param request
	 * @param searchPattern Input search pattern to compare
	 * @throws IOException 
	 */
	private void findArticles(HttpServletRequest request, String searchPattern) throws IOException {
		ArrayList<Article> articles = null;
		
		try {
			articles = ArticleService.GetAllArticlesByName(searchPattern);
			
			Path currentRelativePath = Paths.get("");
			request.getSession().setAttribute("imagePath", currentRelativePath.toAbsolutePath().toString() + "\\");
			
			for (Article a: articles) {
				for(ArticlePicture ap: a.pictures) {
					File file = new File("" + ap.name);
					try {
					   ImageIO.write((RenderedImage) ap.image, "jpg", file);  // ignore returned boolean
					} catch(IOException e) {
					 System.out.println("Write error for " + file.getPath() + ": " + e.getMessage());
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(articles.size() == 0) {
			request.getSession().setAttribute("noArticleFound", true);
		} else {
			request.getSession().setAttribute("noArticleFound", false);
		}
		
		request.getSession().setAttribute("articles", articles);		
	}

}
