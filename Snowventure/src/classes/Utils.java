package classes;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utils {
	
	/**
	 * Redirects the User to the main page in case the session is invalid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void redirectUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(request.getSession().getAttribute("currentUser") == null) {
			response.sendRedirect("start");
			return;
		}
	}
	
	/**
	 * Prints article-Details
	 * @param a Article
	 */
	public static void printArticleDetails(Article a) {
		System.out.println("Artikel:");
		System.out.println("Artikel-ID:" + a.ID);
		System.out.println("Name: " + a.name);
		System.out.println("Beschreibung: " + a.description);
		System.out.println("Preis: " + a.GetPrice());
		System.out.println("Selected Version: " + a.GetSelectedVersion());
		System.out.println("Color: " + a.GetColor());
		System.out.println("Size: " + a.GetSize());
	}
	
}
