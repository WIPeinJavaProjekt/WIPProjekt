package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Article;
import classes.ArticleVersion;
import services.ArticleService;

/**
 * Servlet implementation class ArticleServlet
 */
@WebServlet("/article")
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Article article = new Article();
	private ArticleVersion articleVersion = new ArticleVersion();	
       
    public ArticleServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(this.article!=null) {
			request.setAttribute("articleName", this.article.name);
			request.setAttribute("articleDescription", this.article.description);
		}

		RequestDispatcher rd = request.getRequestDispatcher("/JSP/Articles/articleDetails.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("addArticle") != null) {
			
			addArticle(request);

		} else if(request.getParameter("updateArticle") != null) {			
			// TODO: Update Article
		}

		doGet(request, response);
	}

	private void addArticle(HttpServletRequest request) {
		
		this.article = new Article(request.getParameter("articleName"), request.getParameter("articleDescription"));
		this.articleVersion = new ArticleVersion(Integer.parseInt(request.getParameter("version")), request.getParameter("property"), 
				request.getParameter("propertyValue"), Integer.parseInt(request.getParameter("price")), this.article);
		
		this.article.versions.add(this.articleVersion);
		
//		this.article = new Article("Test", "Beschreibung");
//		this.articleVersion = new ArticleVersion(1, "Größe", "Value", 100, this.article);
//		
//		this.article.versions.add(this.articleVersion);
		
		int ret = ArticleService.AddArticle(this.article);
		
		System.out.println("Article return: " + ret);		
	}

}
