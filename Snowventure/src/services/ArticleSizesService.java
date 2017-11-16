package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import classes.*;

/**
 * Modelclass for Articleversionsizes
 * 
**/
public class ArticleSizesService {
	public static ArrayList<String> GetAllPossibleSizes() throws SQLException{
		ArrayList<String> sizes = new ArrayList<String>();
		
		String query = "SELECT DISTINCT size from ARTICLEVERSIONSIZE";
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
			sizes.add(result.getString("size"));
		
		return sizes;
	}
	
	public static ArrayList<String> GetAllSizesFromArticle(int aid) throws SQLException{
		ArrayList<String> sizes = new ArrayList<String>();
		
		String query = "SELECT DISTINCT size from ARTICLEVERSIONSIZE WHERE avid in (SELECT avid FROM ARTICLEVERSION WHERE aid = '%d')";
		query = String.format(query,
				aid);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
			sizes.add(result.getString("size"));
		
		return sizes;
	}
}
