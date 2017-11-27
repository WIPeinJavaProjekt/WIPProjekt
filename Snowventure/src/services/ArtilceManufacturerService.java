package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import classes.*;

/**
 * Beschreibung: Modelklasse f�r Artikelhersteller
 * @author Ansprechpartner Fabian Meise
 *
 */
public class ArtilceManufacturerService {
	/**
	 * Erhalte alle ausw�hlbaren Hersteller
	 * @return ArrayList aller ausw�hlbaren Hersteller
	 * @throws SQLException
	 */
	public static ArrayList<String> GetAllPossibleManufacturers() throws SQLException{
		ArrayList<String> sizes = new ArrayList<String>();
		
		String query = "SELECT DISTINCT manufacturer from ARTICLE";
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
			sizes.add(result.getString("manufacturer"));
		
		return sizes;
	}
}
