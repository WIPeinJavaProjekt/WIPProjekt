package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import classes.*;

public class UserService {
	
	
	public static int AddUser(User user) {
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
			System.out.println("Userlogin: "+query);
			return DatabaseConnector.createConnection().InsertQuery(query);
		}
		return -1;
	}
	
	public static ArrayList<User> GetUsers(String pattern) throws SQLException
	{
		ArrayList<User> users = new ArrayList<User>();
		String query;
		
		query = "SELECT b.utid, a.udid, a.name,"+
				"a.surname, a.email,"+
				"a.postcode, a.street,"+
				"a.streetno, a.city, a.phone,"+
				"b.login, b.password, b.safetyanswer,"+
				"s.sqid, s.SafetyQuestion"+
				" FROM USERDATA a "+
				" left join USERLOGIN b ON a.udid = b.udid"+
				" left join SAFETYQUESTION s ON s.sqid = b.sqid"+
				" WHERE b.login like '%"+pattern+"%'"+
				" ORDER BY b.login";
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			Safetyquestion q = new Safetyquestion(result.getInt("sqid"),result.getString("safetyquestion"),result.getString("safetyanswer"));
			Adress a = new Adress(result.getString("city"),result.getString("streetno"),result.getString("postcode"),result.getString("street"));
			User u = new User(q, 
					result.getString("login"),
					result.getString("password"),
					result.getString("name"),
					result.getString("surname"),
					a,
					result.getString("email"),
					result.getInt("utid")
					);
			users.add(u);
			
		}
		
		return users;
	}
	
	public static User GetUser(String pattern) throws SQLException
	{
		User user = null;
		String query;
		
		query = "SELECT b.utid, a.udid, a.name,"+
				"a.surname, a.email,"+
				"a.postcode, a.street,"+
				"a.streetno, a.city, a.phone,"+
				"b.login, b.password, b.safetyanswer,"+
				"s.sqid, s.SafetyQuestion"+
				" FROM USERDATA a "+
				" left join USERLOGIN b ON a.udid = b.udid"+
				" left join SAFETYQUESTION s ON s.sqid = b.sqid"+
				" WHERE b.login='"+pattern+"'"+
				" ORDER BY b.login";

		System.out.println(query);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			Safetyquestion q = new Safetyquestion(result.getInt("sqid"),result.getString("safetyquestion"),result.getString("safetyanswer"));
			Adress a = new Adress(result.getString("city"),result.getString("streetno"),result.getString("postcode"),result.getString("street"));
			User u = new User(q, 
					result.getString("login"),
					result.getString("password"),
					result.getString("name"),
					result.getString("surname"),
					a,
					result.getString("email"),
					result.getInt("utid")
					);
			user = u;
			
		}
		
		return user;
	}
	
	public static void UpdateUser(User user) {
		String query = "UPDATE USERDATA SET name='%s', surname ='%s', email ='%s', postcode='%s',street='%s',city='%s', phone='%s',streetno='%s'"
				+ " WHERE udid in (SELECT udid from userlogin where ulid = '%d')";
		query = String.format(query,
				user.name,
				user.surname,
				user.email,
				user.adress.postcode,
				user.adress.street,
				user.adress.location,
				"1337",
				user.adress.houseno,
				user.ulid
				);
		DatabaseConnector.createConnection().UpdateQuery(query);
		
		query = "UPDATE USERLOGIN SET login ='%s', password='%s', safetyanswer='%s', sqid= '%d' where ulid ='%s'";
		query = String.format(query, 
				user.username,
				user.password,
				user.squestion.getAnswer(),
				user.squestion.sqid,
				user.ulid
				);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
	
	public static void UpdateUserRights(User user, int rightnum)
	{
		String query = "UPDATE USERLOGIN SET utid= '%d' where ulid ='%d'";
		query = String.format(query, 
				rightnum,
				user.ulid
				);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
}
