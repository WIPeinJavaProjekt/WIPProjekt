package servlets;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Article;
import classes.ArticleColor;
import classes.Categorie;
import classes.Utils;
import services.ArticleColorService;
import services.ArticleService;
import services.ArticleSizesService;
import services.ArtilceManufacturerService;
import services.CategorieService;

/**
 * Beschreibung: Servlet Suchen und Anzeigen von Artikeln
 * @author Garrit Kniepkamp, Fabian Meise
 *
 */
@WebServlet("/articles")
public class ArticleSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArticleSearchServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameterValues("size") != null) {
		    for(int i=0; i<request.getParameterValues("size").length; i++) {
		    	System.out.println(request.getParameterValues("size")[i]);
		    }
	    }
		
		try {
			ArrayList<Categorie> categories = CategorieService.GetCategories();
			ArrayList<String> manufacturers = ArtilceManufacturerService.GetAllPossibleManufacturers();
			ArrayList<String> availableSizes = ArticleSizesService.GetAllPossibleSizes();
			ArrayList<ArticleColor> colors = ArticleColorService.GetAllPossibleColors();
			
			request.getSession().setAttribute("articleColors", colors);
			request.getSession().setAttribute("availableSizes", availableSizes);
			request.getSession().setAttribute("availableManufacturers", manufacturers);
			request.getSession().setAttribute("categories", categories);
			
			String[] sessionManufacturer = request.getParameterValues("manufacturers");
			if(sessionManufacturer!= null) {
			} else {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/Articles/articleSearchResults.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("search") != null) {	
			
			String searchPattern = request.getParameter("searchArticlePattern").replaceAll("[^a-zA-Z 0-9]+", "");			
			
			double minprice = Double.parseDouble(request.getParameter("minprice") ==null || request.getParameter("minprice") ==""? "0":request.getParameter("minprice") );
			double maxprice = Double.parseDouble(request.getParameter("maxprice") ==null || request.getParameter("maxprice") ==""? "0":request.getParameter("maxprice") );
			String[] colors = request.getParameterValues("color");
		    String[] sizes = request.getParameterValues("size");
		    
		    if(sizes != null) {
			    for(int i=0; i<sizes.length; i++) {
			    	System.out.println(sizes[i]);
			    }
		    }
		    
			String[] manufacturers = request.getParameterValues("manufacturer");
			int category = Integer.parseInt(request.getParameter("categorie"));
			String[] genders = request.getParameterValues("genders");
			
			ArrayList<String> colorsArr = Utils.colorArraytoArrayList(colors);
			ArrayList<String> manufacturersArr = Utils.stringArraytoArrayList(manufacturers);
			ArrayList<String> gendersArr = Utils.stringArraytoArrayList(genders);
			
			request.getSession().setAttribute("colors", colorsArr);
			request.getSession().setAttribute("sizes", sizes);
			request.getSession().setAttribute("manufacturers", manufacturersArr);
			request.getSession().setAttribute("genders", gendersArr);
			request.getSession().setAttribute("selectedCategory", category);
			request.getSession().setAttribute("minprice", minprice);
			request.getSession().setAttribute("maxprice", maxprice);
			
			findArticles(request, category,searchPattern,minprice,maxprice,sizes,manufacturers,colors,genders);			
		}
		
		doGet(request, response);
	}
	
	/**
	 * Gibt alle Artikel zurück, die den eingegebenen Filterkriterien entsprechen
	 * @param request
	 * @param searchPattern Input search pattern to compare
	 * @throws IOException 
	 */
	private void findArticles(HttpServletRequest request,int category ,String searchPattern, double minprice, double maxprice, String[] sizes, String[] manufacturers, String[] colors, String[] genders) throws IOException {
		
		ArrayList<Article> articles = null;
		
		try {
			
			String mf="";
			String gen="";
			String col="";
			String si ="";
			
			
			
			if(manufacturers != null)
				 mf = Arrays.toString(manufacturers);
			if(genders != null)
				gen = Arrays.toString(genders);
			if(sizes != null)
				si = Arrays.toString(sizes);
			if(colors != null)
				col = Arrays.toString(colors);
			articles = ArticleService.GetAllArticlesByFilter(category,searchPattern == null? "" : searchPattern,1,1,gen,mf,minprice,maxprice,col,si);
			Path currentRelativePath = Paths.get("");
			request.getSession().setAttribute("imagePath", currentRelativePath.toAbsolutePath().toString() + "\\");
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
