package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import classes.*;

/**
 * Modelclass for order administration
 * 
**/
public class OrderService {
	
	//Email für info per mail bzw services die per mail ausgeliefert werden per telefon geht ja schlecht...
	/**
	 * Add an Order
	 * @param o the Order
	 * @return int value depending on success of insertion
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
		
		
		oid =AddOrderDetails(o.shoppingCart,oid);
		oid =AddOrderStatuscycle(o.statuscycle,oid);
		
		return oid;
	}
	
	/**
	 * Helper Method for Adding a Orderdetails
	 * @param shoppingcart shoppingcart, which includes the articles ordered
	 * @param orid the depending orderid
	 * @return positiv in case of success, negativ in case of an error
	 * @throws SQLException
	 */	
	private static int AddOrderDetails(ShoppingCart shoppingCart, int orid) {
		int odid= -1;
		
		for(ShoppingCartPosition p: shoppingCart.cartPositions)
		{
			odid=AddOrderDetailPosition(p,orid);
			if(orid == -1)
				return odid;
		}
			
		return odid;
	}
	
	/**
	 * Helper Method for Adding a Shoppingcartpositions from an order
	 * @param shoppingcart shoppingcart, which includes the articles ordered
	 * @param orid the depending orderid
	 * @return positiv in case of success, negativ in case of an error
	 * @throws SQLException
	 */		
	private static int AddOrderDetailPosition(ShoppingCartPosition p, int orid) {
		int id= -1;
		String query = "INSERT INTO ASSIGNMENTDETAILS(orid,avid,ASSIGNMENTPRICE,amount,acolid, size) VALUES('%d','%d','%f','%d', '%d', '%s');";
		query = String.format(query,orid,p.article.versions.get(p.article.GetSelectedVersion()).versionid,p.amount,p.color.acolid,p.size);
		id = DatabaseConnector.createConnection().InsertQuery(query);
		return id;
	}
	
	/**
	 * Helper Method for Adding the status life cycle of the order
	 * @param statuscycle Arraylist contain all order status
	 * @param orid the depending orderid
	 * @return positiv in case of success, negativ in case of an error
	 * @throws SQLException
	 */	
	private static int AddOrderStatuscycle(ArrayList<OrderStatus> statuscycle, int orid) {
		int osid= -1;
		for(OrderStatus os: statuscycle)
		{
			osid = AddOrderStatus(os,orid);
			if(osid == -1)
				return osid;
		}
		
		return osid;
	}	
	
	/**
	 * Helper Method for Adding an OrderStatus
	 * @param os one Orderstatus
	 * @param orid the depending orderid
	 * @return positiv in case of success, negativ in case of an error
	 * @throws SQLException
	 */	
	private static int AddOrderStatus(OrderStatus os, int orid) {
		int osid= -1;
		
		String query ="INSERT INTO ASSIGNMENTSTATUS(status,statusday) VALUES('%s','%s')";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		query = String.format(query, os.description, sdf.format(os.statusdate));
		
		osid = DatabaseConnector.createConnection().InsertQuery(query);
		
		return osid;
	}
	
	
	/**
	 * Method for Deleting an Order by its Orderid
	 * @param orid the depending orderid
	 * @return positiv in case of success, negativ in case of an error
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
	 * Helper Method for Deleting the Orderpositions by its Orderid
	 * @param orid the depending orderid
	 * @return positiv in case of success, negativ in case of an error
	 */	
	private static void DeleteOrderDetailPosition(int orid)
	{
		String query = "DELETE ASSIGNMENTDETAILS WHERE orid = '%d'";
		query = String.format(query, orid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}

	/**
	 * Helper Method for Deleting the Order life cycle by its Orderid
	 * @param orid the depending orderid
	 * @return positiv in case of success, negativ in case of an error
	 */	
	private static void DeleteOrderStatuscycle(int orid)
	{
		String query = "DELETE ASSIGNMENTSTATUS WHERE orid = '%d'";
		query = String.format(query, orid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
	
	/**
	 * Method for updating an Order
	 * @param o the depending order
	 */	
	public static void UpdateOrder(Order o) {
		//update order 
		
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
	 * Method for getting all Orders from a specific Orderid
	 * @param ulid Userloginid
	 * @return Arraylist with all Orders of the Userloginid
	 * @throws SQLException
	 */	
	public static ArrayList<Order> GetAllOrders(int ulid) throws SQLException
	{
		ArrayList<Order> orders = new ArrayList<Order>();
		String query = "SELECT ulid, orid, name, surname, email, postcode, street, streetno, city FROM ASSIGNMENT WHERE ulid ='%d'";
		query = String.format(query, ulid);
		Order o;
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			
			Adress a = new Adress(result.getString("city"),result.getString("streetno"),result.getString("code"), result.getString("streetno"));
			o = new Order(a, GetShoppingCartFromOrder(result.getInt("orid")),GetOrderStatuscycle(result.getInt("orid")),result.getInt("orid"), result.getString("name"), result.getString("surname"), result.getString("email"), result.getInt("ulid"));
			orders.add(o);
		}
		return orders;
	}
	
	
	/**
	 * Method for getting all available Orders
	 * @return Arraylist with all Orders
	 * @throws SQLException
	 */	
	public static ArrayList<Order> GetAllOrders() throws SQLException
	{
		ArrayList<Order> orders = new ArrayList<Order>();
		String query = "SELECT ulid, orid, name, surname, email, postcode, street, streetno, city FROM ASSIGNMENT";
		Order o;
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			
			Adress a = new Adress(result.getString("city"),result.getString("streetno"),result.getString("code"), result.getString("streetno"));
			o = new Order(a, GetShoppingCartFromOrder(result.getInt("orid")),GetOrderStatuscycle(result.getInt("orid")),result.getInt("orid"), result.getString("name"), result.getString("surname"), result.getString("email"), result.getInt("ulid"));
			orders.add(o);
		}
		return orders;
	}
	
	
	/**
	 * Method for getting a specific Order 
	 * @param orid Orderid
	 * @return the specific Order
	 * @throws SQLException
	 */		
	public static Order GetSpecificOrder(int orid) throws SQLException 
	{
		String query = "SELECT ulid, orid, name, surname, email, postcode, street, streetno, city FROM ASSIGNMENT WHERE orid ='%d'";
		query = String.format(query, orid);
		Order o;
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			
			Adress a = new Adress(result.getString("city"),result.getString("streetno"),result.getString("code"), result.getString("streetno"));
			o = new Order(a, GetShoppingCartFromOrder(orid),GetOrderStatuscycle(orid),orid, result.getString("name"), result.getString("surname"), result.getString("email"), result.getInt("ulid"));
			return o;
		}
		
		return null;
	}
	
	
	/**
	 * Helper Method for getting the Shoppingcart from a specific Order 
	 * @param orid Orderid
	 * @return the specific shoppingcart
	 * @throws SQLException
	 */	
	private static ShoppingCart GetShoppingCartFromOrder(int orid) throws SQLException
	{
		String query ="SELECT odid,avid,assignmentprice,amount,acolid,size from ASSIGNMENTDETAILS WHERE orid='%d'";
		query = String.format(query, orid);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		ShoppingCart scp = new ShoppingCart();
		while(result.next())
		{
			Article a = ArticleService.GetSelectedArticle(result.getInt("avid"));
			a.versions.get(a.GetSelectedVersion()).price = result.getDouble("assignmentprice");
			ShoppingCartPosition p = new ShoppingCartPosition(a,result.getInt("amount"),result.getString("size"), ArticleColorService.GetSpecificColor(result.getInt("acolid")));
			scp.cartPositions.add(p);
		}
		
		
		return scp;
	}
	
	/**
	 * Helper Method for getting the status life cycle from a specific Order 
	 * @param orid Orderid
	 * @return the specific Arraylist containing the different state
	 * @throws SQLException
	 */	
	private static ArrayList<OrderStatus> GetOrderStatuscycle(int orid) throws SQLException{
		ArrayList<OrderStatus> statuslist = new ArrayList<OrderStatus>();
		
		String query = "SELECT osid, Status, statusday FROM ASSIGNMENTSTATUS where orid ='%d'";
		query = String.format(query, orid);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			OrderStatus os = new OrderStatus(result.getDate("statusday"),result.getString("status"));
			statuslist.add(os);
		}
		
		return statuslist;
	}



}
