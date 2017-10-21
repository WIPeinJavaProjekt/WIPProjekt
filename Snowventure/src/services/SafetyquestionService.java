package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import classes.*;

public class SafetyquestionService {
	public static ArrayList<Safetyquestion> GetSafetyquestion() throws SQLException
	{
		ArrayList<Safetyquestion> squestions = new ArrayList<Safetyquestion>();
		String query;
	    query = "SELECT sqid, safetyquestion from SAFETYQUESTION;";
		
	    ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
	    
	    while(result.next())
	    {
	    	Safetyquestion s = new Safetyquestion(result.getInt(1),result.getString(2),"");
	    	squestions.add( s );
	    }
	    
		return squestions;
	}
}
