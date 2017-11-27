package services;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import classes.*;

/**
 * Beschreibung: Modelclass for user administration
 * @author Ansprechpartner Fabian Meise
 *
 */
public class UserService {	
	
	/**
	 * Füge neuen Nutzer hinzu
	 * @param user hinzuzufügender Nutzer
	 * @return -1 bei Fehler anosnten id des hinzufügten Datensatzes
	 */
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
					user.utid,
					udid
					);
			return DatabaseConnector.createConnection().InsertQuery(query);
		}
		return -1;
	}
	
	/**
	 * Erhalte alle Nutzer anhand des Loginnamens
	 * @param pattern Suchwert
	 * @param utid Nutzertyp
	 * @return ArrayList der zutreffenden Nutzer
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static ArrayList<User> GetUsers(String pattern, int utid) throws SQLException, IOException
	{
		ArrayList<User> users = new ArrayList<User>();
		String query;
		
		query = "SELECT b.utid, a.udid, b.ulid, a.name,"+
				"a.surname, a.email,"+
				"a.postcode, a.street,"+
				"a.streetno, a.city, a.phone,"+
				"b.login, b.password, b.safetyanswer,"+
				"s.sqid, s.SafetyQuestion, b.TechIsActive, b.TechIsDeleted"+
				" FROM USERDATA a "+
				" left join USERLOGIN b ON a.udid = b.udid"+
				" left join SAFETYQUESTION s ON s.sqid = b.sqid"+
				" WHERE b.login like '%"+pattern+"%' ";
				if ( utid != -1) query += " AND b.utid = "+utid+" ";
				//query += " AND a.TechIsActive = 1 AND a.TechIsDeleted = 0 "+
				//" AND b.TechIsActive = 1 AND b.TechIsDeleted = 0 "+
				query += " AND s.TechIsActive = 1 AND s.TechIsDeleted = 0 "+
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
					result.getInt("utid"),
					result.getInt("ulid"),
					result.getInt("TechIsActive"),
					result.getInt("TechIsDeleted")
					);
			u.orders = new ArrayList<Order>(OrderService.GetAllOrders(u.ulid));
			u.shoppingcart = new ShoppingCart(ShoppingCartService.GetShoppingCart(u));
			users.add(u);
			
		}
		
		return users;
	}
	
	/**
	 * Erhalte einen Nutzer anhand des Namens
	 * @param pattern Suchwert
	 * @return spezifischer Nutzer
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static User GetUser(String pattern) throws SQLException, IOException
	{
		User user = null;
		String query;
		
		query = "SELECT b.utid, a.udid, b.ulid, a.name,"+
				"a.surname, a.email,"+
				"a.postcode, a.street,"+
				"a.streetno, a.city, a.phone,"+
				"b.login, b.password, b.safetyanswer,"+
				"s.sqid, s.SafetyQuestion, b.TechIsActive, b.TechIsDeleted"+
				" FROM USERDATA a "+
				" left join USERLOGIN b ON a.udid = b.udid"+
				" left join SAFETYQUESTION s ON s.sqid = b.sqid"+
				" WHERE b.login='"+pattern+"'"+
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
					result.getInt("utid"),
					result.getInt("ulid"),
					result.getInt("TechIsActive"),
					result.getInt("TechIsDeleted")
					);
			user = u;
			user.orders = new ArrayList<Order>(OrderService.GetAllOrders(u.ulid));
			user.shoppingcart = new ShoppingCart(ShoppingCartService.GetShoppingCart(u));
		}
		
		return user;
	}
	
	 /**
	  * Aktualisere Nutzerdaten
	  * @param zu aktualierender Nutzer
	  */
	public static void UpdateUser(User user) {
		String query = "UPDATE USERDATA SET name='%s', surname ='%s', email ='%s', postcode='%s',street='%s',city='%s', phone='%s',streetno='%s', TechIsActive=%d, TechIsDeleted=%d"
				+ " WHERE udid in (SELECT udid from USERLOGIN where ulid = '%d')";
		query = String.format(query,
				user.name,
				user.surname,
				user.email,
				user.adress.postcode,
				user.adress.street,
				user.adress.location,
				"1337",
				user.adress.houseno,
				user.techisactive,
				user.techisdeleted,
				user.ulid
				);
		
		System.out.println(query);

		DatabaseConnector.createConnection().UpdateQuery(query);
				
		query = "UPDATE USERLOGIN SET login ='%s', password='%s', safetyanswer='%s', sqid= '%d', TechIsActive=%d, TechIsDeleted=%d where ulid =%d";
		query = String.format(query, 
				user.username,
				user.password,
				user.squestion.getAnswer(),
				user.squestion.sqid,
				user.techisactive,
				user.techisdeleted,
				user.ulid
				);
		
		System.out.println(query);

		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
	/**
	 * Aktualisere Nutzerrechte
	 * @param user zu aktualisierender Nutzer
	 * @param rightnum neues Nutzerrecht
	 */
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
