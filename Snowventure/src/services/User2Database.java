package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import classes.*;

public class User2Database {
	
	private PreparedStatement pquery = null;
	
	public int AddUser(User user) {
		String query;
		int udid;
	    query = "INSERT INTO USERDATA(Name,Surname,Email,Postcode,Street, StreetNo, CITY, Phone) VALUES('%s','%s','%s','%s','%s','%s','%s','%s');";
		
		query = String.format(query, 
				user.name,
				user.surname,
				user.email,
				user.adress.postcode,
				user.adress.street,
				user.adress.houseno,
				user.adress.location,
				"1337"
				);
		udid = DatabaseConnector.createConnection().InsertQuery(query);
		
		if(udid != -1) {
			query = "INSERT INTO USERLOGIN(Login, Password, SafetyAnswer,sqid,utid,udid) VALUES('%s','%s','%s','%d','%d','%d');";
			query = String.format(query,
					user.username,
					user.password,
					user.squestion.getAnswer(),
					user.squestion.sqid,
					user.usertype,
					udid
					);
			return DatabaseConnector.createConnection().InsertQuery(query);
		}
		return -1;
	}
	
	
	
}
