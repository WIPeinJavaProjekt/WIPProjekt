package classes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.ArticleColorService;

public class Utils {
	
	/**
	 * Redirects the User to the main page in case the session is invalid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static boolean redirectUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(request.getSession().getAttribute("currentUser") == null) {
			response.sendRedirect("start");
			return true;
		}
		return false;
	}
	
	public static ArrayList<String> getAllSizes() {
		ArrayList<String> sizes = new ArrayList<String>();
		sizes.add("Unigröße");
		sizes.add("S");
		sizes.add("M");
		sizes.add("L");
		sizes.add("XL");
		sizes.add("XXL");
		sizes.add("XXXL");
		
		sizes.add("40");
		sizes.add("41");
		sizes.add("42");
		sizes.add("43");
		sizes.add("44");
		sizes.add("45");
		sizes.add("46");
		sizes.add("47");
		sizes.add("48");
		sizes.add("49");
		
		return sizes;
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
		System.out.println("Preis: " + a.getPrice());
		System.out.println("Selected Version: " + a.getSelectedVersion());
		System.out.println("Color: " + a.getColor());
		System.out.println("Size: " + a.getSize());
	}
	
	public static ArrayList<String> colorArraytoArrayList(String[] inputArray) {
		ArrayList<String> outputArray = new ArrayList<String>();
		if(inputArray != null) {
		    for(int s= 0; s< inputArray.length;s++) {
		    	ArticleColor artColor = null;
				try {
					artColor = ArticleColorService.GetSpecificColor(Integer.parseInt(inputArray[s]));
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
				if(artColor!=null) {
					outputArray.add(artColor.getColorName());
				}
			}
	    }
		return outputArray;
	}
	
	
	public static ArrayList<String> stringArraytoArrayList(String[] inputArray) {
		ArrayList<String> outputArray = new ArrayList<String>();
		if(inputArray != null) {
			for(int s= 0; s< inputArray.length;s++) {
				outputArray.add(inputArray[s]);
			}
		}
		return outputArray;
	}
}
