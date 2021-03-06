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
import classes.ArticleColor;
import classes.ArticlePicture;
import classes.ArticleVersion;
import classes.Categorie;
import classes.Utils;
import services.ArticleColorService;
import services.ArticleService;
import services.CategorieService;
import services.StockService;

/**
 * Servlet implementation class ArticleServlet
 */
/**
 * Beschreibung: Servlet zum Hinzuf�gen/�ndern von Artikeln. Aufgrund der URL wird unterschieden, ob ein neuer Artikel angelegt wird, oder ob ein bestehender Artikel ge�ndert werden soll (ID ist in der URL angegeben).
 * @author Garrit Kniepkamp
 *
 */
@WebServlet("/article")
@MultipartConfig
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Article article = new Article();
	private ArticleVersion articleVersion = new ArticleVersion();
	private ArrayList<ArticleColor> articleColors = null;
	
    public ArticleServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(Utils.redirectUser(request, response)) {
			return;
		};

		try {
			ArrayList<Categorie> categories = CategorieService.GetCategories();
			this.articleColors = ArticleColorService.GetAllPossibleColors();
			request.getSession().setAttribute("categories", categories);
			request.getSession().setAttribute("articleColors", this.articleColors);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(request.getParameter("ID") != null) {
			try {
				this.article = ArticleService.GetArticle(Integer.parseInt(request.getParameter("ID")));
				String version = request.getParameter("version").replace(" ", "");
				this.article.setSelectedVersion(Integer.parseInt(version));
				
				ArrayList<String> colorStrings = this.article.versions.get(this.article.getSelectedVersion()).getColorNames();
				ArrayList<String> sizes = this.article.getSize();
				
				request.getSession().setAttribute("colors", colorStrings);
				request.getSession().setAttribute("sizes", sizes);
				request.getSession().setAttribute("genders", this.article.gender);

				request.getSession().setAttribute("updateArticle", true);
				request.getSession().setAttribute("article", this.article);
				
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}			
		} else {
			this.article = null;
			request.getSession().setAttribute("article", this.article);
			request.getSession().setAttribute("updateArticle", false);
		}
		
		if(request.getParameter("stock") != null) {
			RequestDispatcher rd = request.getRequestDispatcher("/JSP/Articles/stock.jsp");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/JSP/Articles/articleDetails.jsp");
			rd.forward(request, response);
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		if(request.getParameter("addArticle") != null) {
			try {
				int ret = addArticle(request);
				response.sendRedirect("article?ID=" + ret + "&version=0");
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if(request.getParameter("updateArticle") != null) {
			try {
				updateArticle(request);
				response.sendRedirect("article?ID=" + this.article.ID + "&version=" + this.article.getSelectedVersion());
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if(request.getParameter("addImage") != null) {
			addImage(request);
			response.sendRedirect("article?ID=" + this.article.ID + "&version=" + this.article.getSelectedVersion());
			return;
		} else if(request.getParameter("deleteImage") != null) {
			int imageId = Integer.parseInt(request.getParameter("currentImage"));
			ArticleService.DeletePictureFromArticleVersion(this.article.getSelectedArticleVersion().versionid, imageId);
			
			response.sendRedirect("article?ID=" + this.article.ID + "&version=" + this.article.getSelectedVersion());
			return;
		} else if(request.getParameter("addArticleVersion") != null) {
			try {
				addArticleVersion(request);
				response.sendRedirect("article?ID=" + this.article.ID + "&version=" + this.article.getSelectedVersion());
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(request.getParameter("changeStock") != null) {
			changeStock(request);
			response.sendRedirect("article?ID=" + this.article.ID + "&version=" + this.article.getSelectedVersion());
			return;
		}  else if(request.getParameter("back") != null) {
			response.sendRedirect("users?page=articlesearch");
			return;
		}

		doGet(request, response);
	}
	
	private void changeStock(HttpServletRequest request) {
		for(String size: this.article.getSize()) {
			try {
				StockService.UpdateStock(this.article.getSelectedArticleVersion(), size, Integer.parseInt(request.getParameter(size)));
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Eine neue Artikelversion wird zu einem bestehenden Artikel hinzugef�gt.
	 * @param request
	 * @return int Artikelversion id (-1, falls ein Fehler aufgetreten ist)
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private int addArticleVersion(HttpServletRequest request) throws SQLException, IOException, ServletException {
		this.articleVersion = new ArticleVersion();
		this.articleVersion.property = request.getParameter("property");
		this.articleVersion.propertyvalue = request.getParameter("propertyValue");
		this.articleVersion.price = Double.parseDouble(request.getParameter("price"));
		
		Part filePart = request.getPart("articleImage");
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    InputStream fileContent = filePart.getInputStream();
		ArticlePicture picture = new ArticlePicture(fileName, fileContent);
	    
	    ArrayList<String> sizesArr = new ArrayList<String>();
		ArrayList<ArticleColor> colorsArr = new ArrayList<ArticleColor>();
		
	    String[] colors = request.getParameterValues("color");
	    String[] sizes = request.getParameterValues("size");
	    
	    if(colors != null) {
		    for(int s= 0; s< colors.length;s++) {
		    	ArticleColor artColor = ArticleColorService.GetSpecificColor(Integer.parseInt(colors[s]));
				colorsArr.add(artColor);
			}
	    } else {
	    	request.setAttribute("errorArticle", "Bitte w�hlen Sie Farben f�r die Artikelversion aus!");
	    	return -1;
	    }
	    
	    if(sizes != null) {
		    for(int s= 0; s< sizes.length;s++) {
				sizesArr.add(sizes[s]);
			}
	    } else {
	    	request.setAttribute("errorArticle", "Bitte w�hlen Sie Gr��en f�r die Artikelversion aus!");
	    	return -1;
	    }
	    
	    this.articleVersion.colors = colorsArr;
		this.articleVersion.sizes = sizesArr;
		this.articleVersion.ID = this.article.ID;
		this.articleVersion.pictures.add(picture);
		
		try {
			int ret = ArticleService.AddArticleVersion(articleVersion);
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * F�gt ein neues Bild zu einer Artikelversion hinzu.
	 * @param request
	 * @throws IOException
	 * @throws ServletException
	 */
	private void addImage(HttpServletRequest request) throws IOException, ServletException {
		Part filePart = request.getPart("articleImage");
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    InputStream fileContent = filePart.getInputStream();
	    
	    this.article.setSelectedVersion(Integer.parseInt(request.getParameter("selectedVersion")));
	    
	    if(!fileName.equals("")) {
		    ArticlePicture picture = new ArticlePicture(fileName, fileContent);
		    
		    try {
				int ret = ArticleService.AddPictureToArticleVersion(picture, this.article.versions.get(this.article.getSelectedVersion()).getAvId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }	    
	}

	/**
	 * Bearbeiten eines bestehenden Artikels und updaten des Datenbanksatzes
	 * @param request
	 * @throws IOException 
	 * @throws SQLException 
	 */
	private void updateArticle(HttpServletRequest request) throws SQLException, IOException {
		
		this.article.name = request.getParameter("articleName");
		this.article.description = request.getParameter("articleDescription");
		this.article.setSelectedVersion(Integer.parseInt(request.getParameter("selectedVersion")));
		this.articleVersion.property = request.getParameter("property");
		this.articleVersion.propertyvalue = request.getParameter("propertyValue");
		this.articleVersion.price = Double.parseDouble(request.getParameter("price"));		
		
		ArrayList<String> sizesArr = new ArrayList<String>();
		ArrayList<ArticleColor> colorsArr = new ArrayList<ArticleColor>();
		
	    String[] colors = request.getParameterValues("color");
	    String[] sizes = request.getParameterValues("size");
	    if(colors != null) {
		    for(int s= 0; s< colors.length;s++) {
		    	ArticleColor artColor = ArticleColorService.GetSpecificColor(Integer.parseInt(colors[s]));
		    	colorsArr.add(artColor);
			}
	    } else {
	    	request.setAttribute("errorArticle", "Bitte w�hlen Sie Farben f�r die Artikelversion aus!");
	    	return;
	    }
	    
	    if(sizes != null) {
		    for(int s= 0; s< sizes.length;s++) {
				sizesArr.add(sizes[s]);
			}
	    } else {
	    	request.setAttribute("errorArticle", "Bitte w�hlen Sie Gr��en f�r die Artikelversion aus!");
	    	return;
	    }
	    
	    this.articleVersion.colors = colorsArr;
		this.articleVersion.sizes = sizesArr;
		this.articleVersion.versionid = this.article.versions.get(this.article.getSelectedVersion()).versionid;
		this.article.manufacturer = request.getParameter("manufacturer");
		this.article.gender = Arrays.toString(request.getParameterValues("genders"));
		this.article.acid = Integer.parseInt(request.getParameter("category"));
		this.article.versions.set(this.article.getSelectedVersion(), this.articleVersion);
		
		ArticleService.UpdateArticle(this.article);
		
		request.setAttribute("successArticle", "Artikel wurde erfolgreich ge�ndert");
	}

	/**
	 * Hinzuf�gen eines neuen Artikels.
	 * @param request
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ServletException 
	 */
	private int addArticle(HttpServletRequest request) throws SQLException, IOException, ServletException {
		
		Part filePart = request.getPart("articleImage");
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    InputStream fileContent = filePart.getInputStream();
	    
	    ArticlePicture picture = new ArticlePicture(fileName, fileContent);
	    
	    ArrayList<String> sizesArr = new ArrayList<String>();
		ArrayList<ArticleColor> colorsArr = new ArrayList<ArticleColor>();
		
	    String[] colors = request.getParameterValues("color");
	    String[] sizes = request.getParameterValues("size");
	    
	    if(colors != null) {
		    for(int s= 0; s< colors.length;s++) {
		    	ArticleColor artColor = ArticleColorService.GetSpecificColor(Integer.parseInt(colors[s]));
				colorsArr.add(artColor);
			}
	    } else {
	    	request.setAttribute("errorArticle", "Bitte w�hlen Sie Farben f�r die Artikelversion aus!");
	    	return -1;
	    }
	    
	    if(sizes != null) {
		    for(int s= 0; s< sizes.length;s++) {
				sizesArr.add(sizes[s]);
			}
	    } else {
	    	request.setAttribute("errorArticle", "Bitte w�hlen Sie Gr��en f�r die Artikelversion aus!");
	    	return -1;
	    }
	    
		this.article = new Article(request.getParameter("articleName"), request.getParameter("articleDescription"));
		this.article.manufacturer = request.getParameter("manufacturer");
		this.article.acid = Integer.parseInt(request.getParameter("category"));
		this.article.gender = Arrays.toString(request.getParameterValues("genders"));
		
		this.articleVersion = new ArticleVersion(Integer.parseInt(request.getParameter("selectedVersion")), request.getParameter("property"), 
				request.getParameter("propertyValue"), Double.parseDouble(request.getParameter("price")), this.article, sizesArr, colorsArr);
		this.articleVersion.pictures.add(picture);
		this.article.versions.add(this.articleVersion);
		
		
		int ret = ArticleService.AddArticle(this.article);
		
		if(ret == -1) {
			request.setAttribute("errorArticle", "Artikel wurde nicht hinzugef�gt");
		} else {
			this.article = null;
			request.setAttribute("successArticle", "Artikel wurde erfolgreich hinzugef�gt");
		}		
		return ret;
	}

}
