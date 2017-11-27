package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import classes.*;

/**
 * Beschreibung: Modelklasse f�r Lageradministration
 * @author Ansprechpartner Fabian Meise
 *
 */
public class StockService {
	
	/**
	 * Erhalte Lagerbestand einer Artikelversion 
	 * @param a Artikelversion
	 * @param size gew�hlte Gr��e
	 * @return int Lagerbestand
	 * @throws SQLException
	 */
	public static int GetStock(ArticleVersion a, String size) throws SQLException {
		int amount = 0;
		String query;
		query = "SELECT amount FROM STOCK WHERE avid='%d' AND size='%s'";
		query = String.format(query,a.versionid,size);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		while(result.next())
		{
			amount = result.getInt("amount");
		}
		
		return amount;
	}
	
	
	/**
	 * Aktualisere Lagerbestand
	 * @param a zu aktualisierende Artikelversion 
	 * @param size gew�hlte Gr��e
	 * @param amount hinzuf�gender Lagerbestand - f�r Abzug
	 * @throws SQLException
	 */
	public static void UpdateStock(ArticleVersion a, String size, int amount) throws SQLException {
		int newamount = GetStock(a,size) + amount;
		if(newamount >=0 )
		{
			String query = "UPDATE STOCK SET amount = '%d' WHERE avid = '%d'";
			query = String.format(query,newamount,a.versionid);
			DatabaseConnector.createConnection().UpdateQuery(query);
		}
	}
	
}
