package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import classes.*;


public class OrderService {
	
	//Email für info per mail bzw services die per mail ausgeliefert werden per telefon geht ja schlecht...
	public static int AddOrder(Order o, Adress a){
		int oid= -1;
		String query="INSERT INTO ASSIGNMENT(Name, Surname, Email, Postcode, Street, StreetNo, City,ulid) "+
					 "VALUES('%s','%s','%s','%s','%s','%s','%s','%d')";
		query = String.format(query, 
					o.name,
					o.surname,
					o.email,
					a.postcode,
					a.street,
					a.houseno,
					a.location,
					o.ulid
				);
		oid = DatabaseConnector.createConnection().InsertQuery(query);
		
		if(oid == -1)
			return oid;
		
		
		oid =AddOrderDetails(o.shoppingCart,oid);
		oid =AddOrderStatuscycle(o.statuscycle,oid);
		
		return oid;
	}
	
	private static int AddOrderDetails(ShoppingCart shoppingCart, int orid) {
		int odid= -1;
		
		for(ShoppingCartPosition p: shoppingCart.cart)
		{
			odid=AddOrderDetailPosition(p,orid);
			if(orid == -1)
				return odid;
		}
			
		return odid;
	}
	
	private static int AddOrderDetailPosition(ShoppingCartPosition p, int orid) {
		int id= -1;
		String query = "INSERT INTO ASSIGNMENTDETAILS(orid,avid,ASSIGNMENTPRICE,amount) VALUES('%d','%d','%f','%d');";
		query = String.format(query,orid,p.article.versions.get(p.article.GetSelectedVersion()).versionid,p.amount);
		id = DatabaseConnector.createConnection().InsertQuery(query);
		return id;
	}
	
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
	
	private static int AddOrderStatus(OrderStatus os, int orid) {
		int osid= -1;
		
		String query ="INSERT INTO ASSIGNMENTSTATUS(status,statusday) VALUES('%s','%s')";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		query = String.format(query, os.description, sdf.format(os.statusdate));
		
		osid = DatabaseConnector.createConnection().InsertQuery(query);
		
		return osid;
	}
	
	public static void DeleteOrder(int orid)
	{
		String query = "DELETE ASSIGNMENT WHERE orid = '%d'";
		query = String.format(query, orid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}

	private static void DeleteOrderDetailPosition(int orid)
	{
		String query = "DELETE ASSIGNMENTDETAILS WHERE orid = '%d'";
		query = String.format(query, orid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}

	private static void DeleteOrderStatuscycle(int orid)
	{
		String query = "DELETE ASSIGNMENTSTATUS WHERE orid = '%d'";
		query = String.format(query, orid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
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
	
	private static ShoppingCart GetShoppingCartFromOrder(int orid) throws SQLException
	{
		String query ="SELECT odid,avid,assignmentprice,amount from ASSIGNMENTDETAILS WHERE orid='%d'";
		query = String.format(query, orid);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		ShoppingCart scp = new ShoppingCart();
		while(result.next())
		{
			Article a = ArticleService.GetSelectedArticle(result.getInt("avid"));
			a.versions.get(a.GetSelectedVersion()).price = result.getDouble("assignmentprice");
			ShoppingCartPosition p = new ShoppingCartPosition(a,result.getInt("amount"));
			scp.cart.add(p);
		}
		
		
		return scp;
	}
	
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
