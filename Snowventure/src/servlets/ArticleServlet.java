package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import classes.Article;
import classes.ArticlePicture;
import classes.ArticleVersion;
import classes.Categorie;
import classes.Utils;
import services.ArticleService;
import services.CategorieService;

/**
 * Servlet implementation class ArticleServlet
 */
@WebServlet("/article")
@MultipartConfig
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Article article = new Article();
	private ArticleVersion articleVersion = new ArticleVersion();	 
	
    public ArticleServlet() {
        super();
    }

    /**
     * Checks the URL whether a new Article should be added or an existing article should be modified
     * Depending on the case, an article gets loaded or not
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utils.redirectUser(request, response);

		try {
			ArrayList<Categorie> categories = CategorieService.GetCategories();
			request.getSession().setAttribute("categories", categories);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(request.getParameter("ID") != null) {
			try {
				this.article = ArticleService.GetArticle(Integer.parseInt(request.getParameter("ID")));
				this.article.SetSelectedVersion(Integer.parseInt(request.getParameter("version")));
				request.getSession().setAttribute("updateArticle", true);
				request.getSession().setAttribute("article", this.article);
				request.getSession().setAttribute("availableVersions", this.article.versions.size()-1);
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}			
		} else {
			this.article = null;
			request.getSession().setAttribute("updateArticle", false);
		}		
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/Articles/articleDetails.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		if(request.getParameter("addArticle") != null) {
			try {
				addArticle(request);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if(request.getParameter("updateArticle") != null) {
			try {
				updateArticle(request);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if(request.getParameter("addImage") != null) {
			addImage(request);
		} else if(request.getParameter("addArticleVersion") != null) {
			System.out.println("New Version");
			try {
				addArticleVersion(request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		doGet(request, response);
	}

	private void addArticleVersion(HttpServletRequest request) throws SQLException {
		this.articleVersion.property = request.getParameter("property");
		this.articleVersion.propertyvalue = request.getParameter("propertyValue");
		this.articleVersion.price = Double.parseDouble(request.getParameter("price"));
		//Garrit bitte fixen
		//this.articleVersion.color = request.getParameter("color");
		String[] outerArray = request.getParameterValues("size");
		this.articleVersion.sizes = new ArrayList<String>(Arrays.asList(outerArray[0].split(",")));
		this.articleVersion.ID = this.article.ID;
		
		int ret = ArticleService.AddArticleVersion(articleVersion);
		
		System.out.println(ret);
	}

	private void addImage(HttpServletRequest request) throws IOException, ServletException {
		Part filePart = request.getPart("articleImage");
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    InputStream fileContent = filePart.getInputStream();
	    
	    System.out.println("fileName: " + fileName);
	    
	    if(!fileName.equals("")) {
		    ArticlePicture picture = new ArticlePicture(fileName, fileContent);
		    
		    try {
				int ret = ArticleService.AddPicture(picture, this.article.ID);
				System.out.println("return: " + ret);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }	    
	}

	/**
	 * Updating the selected article in the database
	 * @param request
	 * @throws IOException 
	 * @throws SQLException 
	 */
	private void updateArticle(HttpServletRequest request) throws SQLException, IOException {
		
		this.article.name = request.getParameter("articleName");
		this.article.description = request.getParameter("articleDescription");
		this.article.SetSelectedVersion(Integer.parseInt(request.getParameter("selectedVersion")));
		this.articleVersion.property = request.getParameter("property");
		this.articleVersion.propertyvalue = request.getParameter("propertyValue");
		this.articleVersion.price = Double.parseDouble(request.getParameter("price"));
		//Garrit need to fix
		//this.articleVersion.color = request.getParameter("color");
		String[] outerArray = request.getParameterValues("size");
		this.articleVersion.sizes = new ArrayList<String>(Arrays.asList(outerArray[0].split(",")));
		this.articleVersion.versionid = this.article.versions.get(this.article.GetSelectedVersion()).versionid;
		this.article.manufacturer = request.getParameter("manufacturer");
		this.article.acid = Integer.parseInt(request.getParameter("categories"));
		this.article.versions.set(this.article.GetSelectedVersion(), this.articleVersion);
		
		ArticleService.UpdateArticle(this.article);
		
		request.setAttribute("successArticle", "Artikel wurde erfolgreich geändert");
	}

	/**
	 * Adding the article into the Database and setting success or error message
	 * @param request
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ServletException 
	 */
	private void addArticle(HttpServletRequest request) throws SQLException, IOException, ServletException {
		
		Part filePart = request.getPart("articleImage");
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    InputStream fileContent = filePart.getInputStream();
	    
	    ArticlePicture picture = new ArticlePicture(fileName, fileContent);
		
	    String[] outerArray = request.getParameterValues("size");
		this.article = new Article(request.getParameter("articleName"), request.getParameter("articleDescription"));
		this.article.manufacturer = request.getParameter("manufacturer");
		this.article.acid = Integer.parseInt(request.getParameter("categories"));
		//Garrit please fix
		//this.articleVersion = new ArticleVersion(Integer.parseInt(request.getParameter("selectedVersion")), request.getParameter("property"), 
		//		request.getParameter("propertyValue"), Double.parseDouble(request.getParameter("price")), this.article, 
		//		request.getParameter("color"), new ArrayList<String>(Arrays.asList(outerArray[0].split(","))));
		
		this.article.versions.add(this.articleVersion);
		this.article.pictures.add(picture);
		
		int ret = ArticleService.AddArticle(this.article);
		
		if(ret == -1) {
			request.setAttribute("errorArticle", "Artikel wurde nicht hinzugefügt");
		} else {
			this.article = null;
			request.setAttribute("successArticle", "Artikel wurde erfolgreich hinzugefügt");
		}
	}

}
