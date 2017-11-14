package servlets;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Article;
import classes.ArticlePicture;
import services.ArticleFilterService;
import services.ArticleService;

@WebServlet("/articles")
public class ArticleSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArticleSearchServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/Articles/articleSearchResults.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("search") != null) {	
			
			String searchPattern = request.getParameter("searchArticlePattern");
			
			
			double minprice = Double.parseDouble(request.getParameter("minprice") ==null || request.getParameter("minprice") ==""? "-1":request.getParameter("minprice") );
			double maxprice = Double.parseDouble(request.getParameter("maxprice") ==null || request.getParameter("maxprice") ==""? "-1":request.getParameter("maxprice") );
			String[] sizes  = request.getParameterValues("sizes");
			String[] manufacturers = request.getParameterValues("manufacturer");
			String[] colors = request.getParameterValues("colors");
			int category = Integer.parseInt(request.getParameter("categorie"));
			findArticles(request, category,searchPattern,minprice,maxprice,sizes,manufacturers,colors);
			
		}
		
		doGet(request, response);
	}
	
	/**
	 * Returns articles matching the search-pattern to the request
	 * @param request
	 * @param searchPattern Input search pattern to compare
	 * @throws IOException 
	 */
	private void findArticles(HttpServletRequest request,int category ,String searchPattern, double minprice, double maxprice, String[] sizes, String[] manufacturers, String[] colors) throws IOException {
		ArrayList<Article> articles = null;
		
		try {
			if(category ==-1) {
				articles = ArticleService.GetAllArticlesByName(searchPattern);
			}
			else {
				articles = ArticleService.GetAllArticlesByCategorie(category, searchPattern);
			}
			articles = (ArrayList<Article>) ArticleFilterService.FilterPrice(minprice, maxprice, articles).clone();
			System.out.println("Artikelanzahl"+articles.size());
			if(sizes != null)
				articles = (ArrayList<Article>) ArticleFilterService.FilterSize(sizes, articles).clone();
			
			if(manufacturers != null)
				articles = (ArrayList<Article>) ArticleFilterService.FilterManufacturer(manufacturers, articles).clone();
			
			if(colors != null)
				articles = (ArrayList<Article>) ArticleFilterService.FilterColor(colors, articles).clone();
			
			Path currentRelativePath = Paths.get("");
			request.getSession().setAttribute("imagePath", currentRelativePath.toAbsolutePath().toString() + "\\");
			
			for (Article a: articles) {
				for(ArticlePicture ap: a.versions.get(a.GetSelectedVersion()).pictures) {
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
