package services;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import classes.*;


/**
 * Beschreibung: Modelklasse für Warenkorbadministration
 * @author Ansprechpartner Fabian Meise
 *
 */
public class ShoppingCartService {

	
	/**
	 * Aktualisiere den aktuellen Warenkorb eines Nutzers
	 * @param u Nutzer
	 */
	public static void UpdateShopping(User u) {
		DeleteShoppingCartPositions(u);
		AddShoppingCart(u);
	}
	
	/**
	 * Erhalte bestehenden Warenkorb eines Nutzers
	 * @param u Nutzer
	 * @return Warenkorb
	 * @throws SQLException
	 * @throws IOException
	 */
	public static ShoppingCart GetShoppingCart(User u) throws SQLException, IOException {
		String query ="SELECT avid,ulid,amount,size from SHOPPINGCART WHERE ulid='%d'";
		query = String.format(query, u.ulid);
		ShoppingCart scp = new ShoppingCart(GetShoppingCartfromQuery(query));
		
		return scp;
	}
	
	/**
	 * Erhalte bestehenden Warenkorb anhand des Nutzerlogins
	 * @param ulid Nutzerid
	 * @return Warenkorb
	 * @throws SQLException
	 * @throws IOException
	 */
	public static ShoppingCart GetShoppingCart(int ulid) throws SQLException, IOException {
		String query ="SELECT avid,ulid,amount, size from SHOPPINGCART WHERE ulid='%d'";
		query = String.format(query, ulid);
		ShoppingCart scp = new ShoppingCart(GetShoppingCartfromQuery(query));

		return scp;
	}

	/**
	 * Füge Warenkorb eines Nutzers ein	
	 * @param u Nutzer
	 * @return
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
	 * Füge eine Warenkorbposition ein
	 * @param position Warenkorbposition
	 * @param ulid Nutzerloginid
	 * @return -1 bei Fehler anosnten id des hinzufügten Datensatzes
	 */
	private static int AddShoppingCartPosition(ShoppingCartPosition position, int ulid) {
		int pid = -1;
		String query ="INSERT INTO SHOPPINGCART(avid,ulid,amount, size) VALUES(%d,%d,%d,%s);";
		query = String.format(query, position.article.versions.get(position.article.getSelectedVersion()).versionid, ulid, position.amount,position.size);
		pid = DatabaseConnector.createConnection().InsertQuery(query);
		return pid;
	}
	
	/**
	 * Erhalte Warenkorb anhand eines SELECTs	
	 * @param SELECT
	 * @return ArrayList mit Warenkorbpositionen
	 * @throws SQLException
	 * @throws IOException
	 */
	private static ArrayList<ShoppingCartPosition> GetShoppingCartfromQuery(String query) throws SQLException, IOException {
		ArrayList<ShoppingCartPosition> scp = new ArrayList<ShoppingCartPosition>();
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next()) {
			ShoppingCartPosition position = new ShoppingCartPosition(ArticleService.GetSelectedArticle(result.getInt("avid")),result.getInt("amount"),result.getString("size"));
			scp.add(position);
		}
		
		return scp;
	}
	
	/**
	 * Hilfsmethode zum Löschen eines bestehenden Warenkorbs eines Nutzers
	 * @param Nutzer
	 */
	private static void DeleteShoppingCartPositions(User u) {
		String query ="DELETE SHOPPINGCART WHERE ulid = '%d';";
		query = String.format(query, u.ulid);
		DatabaseConnector.createConnection().UpdateQuery(query);
		
	}
	
}
