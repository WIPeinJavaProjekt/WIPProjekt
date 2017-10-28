package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import classes.*;


public class StockService {
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
