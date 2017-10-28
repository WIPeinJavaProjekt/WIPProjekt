package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import classes.*;


public class OrderService {
	
	//Email für info per mail bzw services die per mail ausgeliefert werden per telefon geht ja schlecht...
	public static int AddOrder(Order o, Adress a, int ulid, String name, String surname, String email){
		int oid= -1;
		String query="INSERT INTO ASSIGNMENT(Name, Surname, Email, Postcode, Street, StreetNo, City,ulid) "+
					 "VALUES('%s','%s','%s','%s','%s','%s','%s','%d')";
		query = String.format(query, 
					name,
					surname,
					email,
					a.postcode,
					a.street,
					a.houseno,
					a.location,
					ulid
				);
		oid = DatabaseConnector.createConnection().InsertQuery(query);
		
		if(oid == -1)
			return oid;
		
		
		oid =AddOrderDetails(o.shoppingCart,oid);
		oid =AddOrderStatuscycle(o.statuscycle,oid);
		
		return oid;
	}
	
	private static int AddOrderDetails(ShoppingCart shoppingCart, int oid) {
		int odid= -1;
		
		for(ShoppingCartPosition p: shoppingCart.cart)
		{
			odid=AddOrderDetailPosition(p,oid);
			if(oid == -1)
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
	
	
	
	//FEHLEND SIND NOCH UPDATE; DELETE; SELECT
	public static void UpdateOrder() {
		
	}
	
	
	public static void UpdateOrderDetails() {
		
	}
	

}
