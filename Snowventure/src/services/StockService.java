package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import classes.*;

/**
 * Modelclass for Stock administration
 * 
**/
/**
 * Beschreibung:
 * @author Ansprechpartner
 *
 */
public class StockService {
	
	/**
	 * Get Stock from an ArticleVersion
	 * @param a ArticleVersion
	 * @return amount of the submitted article
	 * @throws SQLException
	 */
	public static int GetStock(ArticleVersion a) throws SQLException {
		int amount = 0;
		String query;
		query = "SELECT amount FROM STOCK WHERE avid='%d'";
		query = String.format(query,a.versionid);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		while(result.next())
		{
			amount = result.getInt("amount");
		}
		
		return amount;
	}
	
	
	/**
	 * Update the Stock of an Article
	 * @param a ArticleVersion, which 
	 * @param amount Amount of ArticleVersions added to the Stock, negative value for removal
	 * @throws SQLException
	 */
	public void UpdateStock(ArticleVersion a, int amount) throws SQLException {
		int newamount = GetStock(a) - Math.abs(amount);
		if(newamount >=0 )
		{
			String query = "UPDATE STOCK SET amount = '%d' WHERE avid = '%d'";
			query = String.format(query,newamount,a.versionid);
			DatabaseConnector.createConnection().UpdateQuery(query);			
		}
	}
	
}
