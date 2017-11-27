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
		
		try {
			ArrayList<Categorie> categories = CategorieService.GetCategories();
			ArrayList<String> manufacturers = ArtilceManufacturerService.GetAllPossibleManufacturers();
			ArrayList<String> sizes = ArticleSizesService.GetAllPossibleSizes();
			ArrayList<ArticleColor> colors = ArticleColorService.GetAllPossibleColors();
			
			request.getSession().setAttribute("articleColors", colors);
			request.getSession().setAttribute("availableSizes", sizes);
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
			
			String searchPattern = request.getParameter("searchArticlePattern");			
			
			double minprice = Double.parseDouble(request.getParameter("minprice") ==null || request.getParameter("minprice") ==""? "0":request.getParameter("minprice") );
			double maxprice = Double.parseDouble(request.getParameter("maxprice") ==null || request.getParameter("maxprice") ==""? "0":request.getParameter("maxprice") );
			String[] colors = request.getParameterValues("color");
		    String[] sizes = request.getParameterValues("size");
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
			System.out.println("Suche Starten: "+ System.currentTimeMillis());
			articles = ArticleService.GetAllArticlesByFilter(category,searchPattern == null? "" : searchPattern,1,1,gen,mf,minprice,maxprice,col,si);
			System.out.println("Suche beendet: "+System.currentTimeMillis());
			Path currentRelativePath = Paths.get("");
			System.out.println(currentRelativePath.toAbsolutePath().toString());
			request.getSession().setAttribute("imagePath", currentRelativePath.toAbsolutePath().toString() + "\\");
			
			/*for (Article a: articles) {
				for(ArticlePicture ap: a.versions.get(a.GetSelectedVersion()).pictures) {
					File file = new File("" + ap.name);
					try {
					   ImageIO.write((RenderedImage) ap.image, "jpg", file);  // ignore returned boolean
					} catch(IOException e) {
					 System.out.println("Write error for " + file.getPath() + ": " + e.getMessage());
					}
				}
			}*/
			System.out.println("Bilder angezeigt: "+System.currentTimeMillis());
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
