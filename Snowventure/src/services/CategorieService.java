package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import classes.*;

/**
 * Modelclass for categorie administration
 * 
**/
/**
 * Beschreibung:
 * @author Ansprechpartner
 *
 */
public class CategorieService {
	
	/**
	 * Method for getting all Categories
	 * @return Arraylist with all Categories
	 * @throws SQLException
	 */
	public static ArrayList<Categorie> GetCategories() throws SQLException
	{
		ArrayList<Categorie> categories = new ArrayList<Categorie>();
		String query;
	    query = "SELECT acid,categorie from CATEGORIES;";
		
	    ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
	    
	    while(result.next())
	    {
	    	Categorie s = new Categorie(result.getInt(1),result.getString(2));
	    	categories.add( s );
	    }
	    
		return categories;
	}
}
