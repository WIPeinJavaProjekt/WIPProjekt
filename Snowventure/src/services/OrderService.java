package services;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import classes.Adress;
import classes.Article;
import classes.Order;
import classes.OrderStatus;
import classes.ShoppingCart;
import classes.ShoppingCartPosition;

/**
 * Beschreibung: Modelklasse für Bestelladministration
 * @author Ansprechpartner Fabian Meise
 *
 */
public class OrderService {
	
	/**
	 * Füge eine Bestellung hinzu
	 * @param o Bestellung
	 * @return -1 bei Fehler ansonsten id des hinzufügten Datensatzes
	 */
	public static int AddOrder(Order o){
		int oid= -1;
		String query="INSERT INTO ASSIGNMENT(Name, Surname, Email, Postcode, Street, StreetNo, City,ulid) "+
					 "VALUES('%s','%s','%s','%s','%s','%s','%s','%d')";
		query = String.format(query, 
					o.name,
					o.surname,
					o.email,
					o.adress.postcode,
					o.adress.street,
					o.adress.houseno,
					o.adress.location,
					o.ulid
				);
		oid = DatabaseConnector.createConnection().InsertQuery(query);
		
		if(oid == -1)
			return oid;
		
		int dummy=-1;
		
		dummy =AddOrderDetails(o.shoppingCart,oid);
		if(dummy ==-1)
			return dummy;
		
		dummy =AddOrderStatuscycle(o.statuscycle,oid);
		
		if(dummy ==-1)
			return dummy;
		
		return oid;
	}
	
	/**
	 * Hilfsmethode zum Hinzufügen von Bestelldetails
	 * @param Warenkorb
	 * @param Bestellid
	 * @return -1 bei Fehler anosnten id des hinzufügten Datensatzes
	 */
	private static int AddOrderDetails(ShoppingCart shoppingCart, int orid) {
		int odid= -1;
		for(ShoppingCartPosition p: shoppingCart.cartPositions)
		{
			odid=AddOrderDetailPosition(p,orid);
			if(odid == -1)
				return odid;
		}
			
		return odid;
	}
	
	/**
	 * Hilfsmethode zum Hinzufügen der Warenkorbpositionen	
	 * @param p Warenkorbposition
	 * @param orid Bestellid
	 * @return -1 bei Fehler anosnten id des hinzufügten Datensatzes
	 */
	private static int AddOrderDetailPosition(ShoppingCartPosition p, int orid) {
		Locale.setDefault(Locale.ENGLISH);		
		int id= -1;
		String query = "INSERT INTO ASSIGNMENTDETAILS(orid,avid,ASSIGNMENTPRICE,amount,size) VALUES('%d','%d','%f','%d', '%s')";

		query = String.format(query,orid,p.article.versions.get(p.article.getSelectedVersion()).versionid, p.article.getPrice(), p.amount, p.size);
		id = DatabaseConnector.createConnection().InsertQuery(query);
		return id;
	}
	
	/**
	 * Hilfsmethode zum Hinzufügen der Bestellhistorie
	 * @param statuscycle Bestellhistorie
	 * @param orid Bestellid
	 * @return
	 */
	public static int AddOrderStatuscycle(ArrayList<OrderStatus> statuscycle, int orid) {
		int osid= -1;
		DeleteOrderStatuscycle(orid);
		for(OrderStatus os: statuscycle)
		{
			osid = AddOrderStatus(os,orid);
			if(osid == -1)
				return osid;
		}
		
		return osid;
	}	
	
	/**
	 * Hilfsmethode zum Einfügen eines Bestellstatus
	 * @param os Bestellstatus
	 * @param orid Bestellid
	 * @return
	 */
	public static int AddOrderStatus(OrderStatus os, int orid) {
		int osid= -1;
		
		String query ="INSERT INTO ASSIGNMENTSTATUS(status,statusday, orid) VALUES('%s','%s', '%d')";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		query = String.format(query, os.description, sdf.format(os.statusdate), orid);
		
		osid = DatabaseConnector.createConnection().InsertQuery(query);
		
		return osid;
	}
	
	
	/**
	 * Lösche eine Bestellung
	 * @param orid Bestellid
	 */
	public static void DeleteOrder(int orid)
	{
		DeleteOrderDetailPosition(orid);
		DeleteOrderStatuscycle(orid);
		String query = "DELETE ASSIGNMENT WHERE orid = '%d'";
		query = String.format(query, orid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
	/**
	 * Lösche alle Bestellpositionen einer Bestellung
	 * @param orid Bestellid
	 */
	private static void DeleteOrderDetailPosition(int orid)
	{
		String query = "DELETE ASSIGNMENTDETAILS WHERE orid = %d";
		query = String.format(query, orid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}

	/**
	 * Lösche alle Bestellstati einer Bestellung
	 * @param orid Bestellid
	 */
	public static void DeleteOrderStatuscycle(int orid)
	{
		String query = "DELETE ASSIGNMENTSTATUS WHERE orid = %d";
		query = String.format(query, orid);
		
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
	
	/**
	 * Aktualisiere eine Bestellung
	 * @param o Bestellung
	 */
	public static void UpdateOrder(Order o) {
		
		DeleteOrderDetailPosition(o.orid);
		DeleteOrderStatuscycle(o.orid);
		int check = AddOrderDetails(o.shoppingCart, o.orid);
		check = AddOrderStatuscycle(o.statuscycle,o.orid);
			
		String query = "UPDATE ASSIGNMENT SET Name='%s',Surname='%s', Email ='%s', Postcode='%s', Street ='%s', StreetNo ='%s', CITY ='%s', ulid ='%d'"
				+ " WHERE orid = '%d'";
		query = String.format(query, 
						o.name,
						o.surname,
						o.email,
						o.adress.postcode,
						o.adress.street,
						o.adress.houseno,
						o.adress.location,
						o.ulid,
						o.orid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
	/**
	 * Erhalte alle Bestellung eines Nutzers
	 * @param ulid Nutzerloginid
	 * @return ArrayList aller bestellungen
	 * @throws SQLException
	 * @throws IOException
	 */
	public static ArrayList<Order> GetAllOrders(int ulid) throws SQLException, IOException
	{
		ArrayList<Order> orders = new ArrayList<Order>();
		String query = "SELECT ulid, orid, name, surname, email, postcode, street, streetno, city FROM ASSIGNMENT WHERE ulid ='%d'";
		query = String.format(query, ulid);
		Order o;
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			
			Adress a = new Adress(result.getString("city"),result.getString("streetno"),result.getString("postcode"), result.getString("streetno"));
			o = new Order(a, GetShoppingCartFromOrder(result.getInt("orid")),GetOrderStatuscycle(result.getInt("orid")),result.getInt("orid"), result.getString("name"), result.getString("surname"), result.getString("email"), result.getInt("ulid"));
			orders.add(o);
		}
		return orders;
	}
	
	
	
	/**
	 * Erhalte alle Bestellung mit einem bestimmten Status
	 * @param status Bestellstatus
	 * @return ArrayList aller bestellungen
	 * @throws SQLException
	 * @throws IOException
	 */
	public static ArrayList<Order> GetAllOrders(OrderStatus status) throws SQLException, IOException
	{
		ArrayList<Order> orders = new ArrayList<Order>();
		String query = "SELECT ulid, orid, name, surname, email, postcode, street, streetno, city,"
					+ " (SELECT s.status FROM ASSIGNMENTSTATUS s WHERE s.orid = a.orid Order By statusday desc LIMIT 1) as status"
				    + " FROM ASSIGNMENT a where (SELECT s.status FROM ASSIGNMENTSTATUS s WHERE s.orid = a.orid Order By statusday desc LIMIT 1) Like '%" + status.description + "%'";

		Order o;
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			
			Adress a = new Adress(result.getString("city"),result.getString("streetno"),result.getString("postcode"), result.getString("streetno"));
			o = new Order(a, GetShoppingCartFromOrder(result.getInt("orid")),GetOrderStatuscycle(result.getInt("orid")),result.getInt("orid"), result.getString("name"), result.getString("surname"), result.getString("email"), result.getInt("ulid"));
			orders.add(o);
		}
		return orders;
	}
	
	
	
	/**
	 * Erhalte alle Bestellung eines Nutzers mit einem bestimmten Status
	 * @param status Bestellstatus
	 * @param ulid Login-ID des Nutzers
	 * @return ArrayList aller bestellungen
	 * @throws SQLException
	 * @throws IOException
	 */
	public static ArrayList<Order> GetAllOrders(OrderStatus status, int ulid) throws SQLException, IOException
	{
		ArrayList<Order> orders = new ArrayList<Order>();
		String query = "SELECT ulid, orid, name, surname, email, postcode, street, streetno, city,"
					+ " (SELECT s.status FROM ASSIGNMENTSTATUS s WHERE s.orid = a.orid Order By statusday desc LIMIT 1) as status"
				    + " FROM ASSIGNMENT a where (SELECT s.status FROM ASSIGNMENTSTATUS s WHERE s.orid = a.orid Order By statusday desc LIMIT 1) Like '%" + status.description + "%' AND ulid='" + ulid + "'";

		Order o;
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			
			Adress a = new Adress(result.getString("city"),result.getString("streetno"),result.getString("postcode"), result.getString("streetno"));
			o = new Order(a, GetShoppingCartFromOrder(result.getInt("orid")),GetOrderStatuscycle(result.getInt("orid")),result.getInt("orid"), result.getString("name"), result.getString("surname"), result.getString("email"), result.getInt("ulid"));
			orders.add(o);
		}
		return orders;
	}

	
	
	
	/**
	 * Erhalte alle Bestellungen
	 * @return ArrayList aller Bestellungen
	 * @throws SQLException
	 * @throws IOException
	 */
	public static ArrayList<Order> GetAllOrders() throws SQLException, IOException
	{
		ArrayList<Order> orders = new ArrayList<Order>();
		String query = "SELECT ulid, orid, name, surname, email, postcode, street, streetno, city FROM ASSIGNMENT";
		Order o;
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			
			Adress a = new Adress(result.getString("city"),result.getString("streetno"),result.getString("postcode"), result.getString("street"));
			o = new Order(a, GetShoppingCartFromOrder(result.getInt("orid")),GetOrderStatuscycle(result.getInt("orid")),result.getInt("orid"), result.getString("name"), result.getString("surname"), result.getString("email"), result.getInt("ulid"));
			orders.add(o);
		}
		return orders;
	}
	
	
	/**
	 * Erhalte eine bestimmte Bestellung anhand der Bestellid
	 * @param orid Bestellid
	 * @return Bestellung
	 * @throws SQLException
	 * @throws IOException
	 */
	public static Order GetSpecificOrder(int orid) throws SQLException, IOException 
	{
		String query = "SELECT ulid, orid, name, surname, email, postcode, street, streetno, city FROM ASSIGNMENT WHERE orid ='%d'";
		query = String.format(query, orid);
		Order o;
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			
			Adress a = new Adress(result.getString("city"),result.getString("streetno"),result.getString("postcode"), result.getString("street"));
			o = new Order(a, GetShoppingCartFromOrder(orid),GetOrderStatuscycle(orid),orid, result.getString("name"), result.getString("surname"), result.getString("email"), result.getInt("ulid"));
			return o;
		}
		
		return null;
	}
	
	
	/**
	 * Erhalte alle Bestellpositionen einer Bestellung
	 * @param orid Bestellid
	 * @return Warenkorb
	 * @throws SQLException
	 * @throws IOException
	 */
	private static ShoppingCart GetShoppingCartFromOrder(int orid) throws SQLException, IOException
	{
		String query ="SELECT odid,avid,assignmentprice,amount,size from ASSIGNMENTDETAILS WHERE orid='%d'";
		query = String.format(query, orid);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		ShoppingCart scp = new ShoppingCart();
		while(result.next())
		{
			Article a = ArticleService.GetSelectedArticle(result.getInt("avid"));
			a.versions.get(a.getSelectedVersion()).price = result.getDouble("assignmentprice");
			ShoppingCartPosition p = new ShoppingCartPosition(a,result.getInt("amount"),result.getString("size"));
			scp.cartPositions.add(p);
		}
		
		
		return scp;
	}
	
	/**
	 * Erhalte alle Bestellstatus (Historie) einer Bestellung
	 * @param orid Bestellid
	 * @return ArrayList der Bestellstatus (Historie)
	 * @throws SQLException
	 */
	private static ArrayList<OrderStatus> GetOrderStatuscycle(int orid) throws SQLException{
		ArrayList<OrderStatus> statuslist = new ArrayList<OrderStatus>();
		
		String query = "SELECT osid, Status, statusday FROM ASSIGNMENTSTATUS where orid ='%d'";
		query = String.format(query, orid);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			OrderStatus os = new OrderStatus(result.getTimestamp("statusday"),result.getString("status"));
			statuslist.add(os);
		}
		
		return statuslist;
	}



}
