package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import classes.*;


/**
 * Modelclass for shoppingcart administration
 * 
**/
public class ShoppingCartService {

	
	/**
	 * Update Shoppingcart by deleting and adding alls items
	 * @param u User, who owns the shoppingcart
	 */
	public static void UpdateShopping(User u) {
		DeleteShoppingCartPositions(u);
		AddShoppingCart(u);
	}
	
	/**
	 * Get Shoppingcart from User
	 * @param u User 
	 * @return Shoppingcart from the User
	 * @throws SQLException
	 */
	public static ShoppingCart GetShoppingCart(User u) throws SQLException {
		String query ="SELECT avid,ulid,amount from SHOPPINGCART WHERE ulid='%d'";
		query = String.format(query, u.ulid);
		ShoppingCart scp = new ShoppingCart(GetShoppingCartfromQuery(query));
		
		return scp;
	}
	
	/**
	 * Get Shoppingcart from Userid
	 * @param ulid Userloginid
	 * @return Shoppingcart from the Userloginid
	 * @throws SQLException
	 */	
	public static ShoppingCart GetShoppingCart(int ulid) throws SQLException {
		String query ="SELECT avid,ulid,amount from SHOPPINGCART WHERE ulid='%d'";
		query = String.format(query, ulid);
		ShoppingCart scp = new ShoppingCart(GetShoppingCartfromQuery(query));

		return scp;
	}

	/**
	 * Helper Method for Updating the Shoppingcart
	 * @param u User, who owns the shoppingcart
	 * @return positiv in case of success, negativ in case of an error
	 * @throws SQLException
	 */	
	private static int AddShoppingCart(User u) {
		int scpid = 1;
		
		for(ShoppingCartPosition p: u.shoppingcart.cartPositions)
		{
			scpid = AddShoppingCartPosition(p,u.ulid);
			if(scpid == -1) {
				return scpid;
			}
		}
		
		return scpid;
	}
	
	/**
	 * Helper Method for Adding a shoppingcartposition
	 * @param position Shoppingcartposition of the Article to be added
	 * @param ulid the Userloginid belongs to the position
	 * @return positiv in case of success, negativ in case of an error
	 * @throws SQLException
	 */	
	private static int AddShoppingCartPosition(ShoppingCartPosition position, int ulid) {
		int pid = -1;
		String query ="INSERT INTO SHOPPINGCART(avid,ulid,amount) VALUES(%d,%d,%d);";
		query = String.format(query, position.article.versions.get(position.article.GetSelectedVersion()).versionid, ulid, position.amount);
		pid = DatabaseConnector.createConnection().InsertQuery(query);
		return pid;
	}
	
	/**
	 * Helper Method for Selection all Shoppingcartpositions
	 * @param position Shoppingcartposition of the Article to be added
	 * @param ulid the Userloginid belongs to the position
	 * @return Arraylist of ShoppingCartpositions
	 * @throws SQLException
	 */		
	private static ArrayList<ShoppingCartPosition> GetShoppingCartfromQuery(String query) throws SQLException {
		ArrayList<ShoppingCartPosition> scp = new ArrayList<ShoppingCartPosition>();
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next()) {
			ShoppingCartPosition position = new ShoppingCartPosition(ArticleService.GetSelectedArticle(result.getInt("avid")),result.getInt("amount"));
			scp.add(position);
		}
		
		return scp;
	}
	
	/**
	 * Helper Method for Deleting all Shoppingcartpositions from a specific user
	 * @param u User, whose shoppingcartpositions should be deleted 
	 */	
	private static void DeleteShoppingCartPositions(User u) {
		String query ="DELETE SHOPPINGCART WHERE ulid = '%d';";
		query = String.format(query, u.ulid);
		DatabaseConnector.createConnection().UpdateQuery(query);
		
	}
	
}
