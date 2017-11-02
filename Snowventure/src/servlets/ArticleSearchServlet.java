package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Article;

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
		
		System.out.println("ArtSearch doPost");
		System.out.println(request.getAttribute("ID"));
		
		if(request.getParameter("login") != null) {
			
			response.sendRedirect("article?ID=");
			return;
			
		}
		
		doGet(request, response);
	}

}
