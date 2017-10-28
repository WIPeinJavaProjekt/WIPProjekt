package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import classes.*;



public class ShoppingCartService {

	
	//Bitte diese Methode benutzen, danke
	public static void UpdateShopping(User u) {
		DeleteShoppingCartPositions(u);
		AddShoppingCart(u);
	}
	
	public static ShoppingCart GetShoppingCart(User u) throws SQLException {
		String query ="SELECT avid,ulid,amount from SHOPPINGCART WHERE ulid='%d'";
		query = String.format(query, u.ulid);
		ShoppingCart scp = new ShoppingCart(GetShoppingCartfromQuery(query));
		
		return scp;
	}
	
	public static ShoppingCart GetShoppingCart(int ulid) throws SQLException {
		String query ="SELECT avid,ulid,amount from SHOPPINGCART WHERE ulid='%d'";
		query = String.format(query, ulid);
		ShoppingCart scp = new ShoppingCart(GetShoppingCartfromQuery(query));

		return scp;
	}


	//HILFSMETHODEN
	private static int AddShoppingCart(User u) {
		int scpid = 1;
		
		for(ShoppingCartPosition p: u.shoppingcart.cart)
		{
			scpid = AddShoppingCartPosition(p,u.ulid);
			if(scpid == -1) {
				return scpid;
			}
		}
		
		return scpid;
	}
	
	private static int AddShoppingCartPosition(ShoppingCartPosition position, int ulid) {
		int pid = -1;
		String query ="INSERT INTO SHOPPINGCART(avid,ulid,amount) VALUES(%d,%d,%d);";
		query = String.format(query, position.article.versions.get(position.article.GetSelectedVersion()).versionid, ulid, position.amount);
		pid = DatabaseConnector.createConnection().InsertQuery(query);
		return pid;
	}
	
	private static ArrayList<ShoppingCartPosition> GetShoppingCartfromQuery(String query) throws SQLException {
		ArrayList<ShoppingCartPosition> scp = new ArrayList<ShoppingCartPosition>();
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next()) {
			ShoppingCartPosition position = new ShoppingCartPosition(ArticleService.GetSelectedArticle(result.getInt("avid")),result.getInt("amount"));
			scp.add(position);
		}
		
		return scp;
	}
	
	private static void DeleteShoppingCartPositions(User u) {
		String query ="DELETE SHOPPINGCART WHERE ulid = '%d';";
		query = String.format(query, u.ulid);
		DatabaseConnector.createConnection().UpdateQuery(query);
		
	}
	
}
