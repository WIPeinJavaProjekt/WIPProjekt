package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Beschreibung: Klasse zum Herstellen der Datenbankverbindung
 * @author Ansprechpartner Fabian Meise
 *
 */
public class DatabaseConnector {
	private static String dbHost = "meiserv.de"; // hostname
	private static String dbPort = "3306";      // Port -- default: 3306
	private static String dbName = "WIPPROJEKT";   // databasename
	private static String dbUser = "tech_WIP";     // databaseuser
	private static String dbPass = "wipprojektrocks";      // databasepassword
	
    public static Connection connect = null; //connection
    
    private static DatabaseConnector databaseconnection = null; //global connector
	
    /**
     * Herstellen der Datenbankverbindung
     */
    private DatabaseConnector()
    {
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connect = DriverManager.getConnection("jdbc:mysql://"+dbHost+":3306/"+dbName,dbUser,dbPass);
			System.out.print("CONNECTED TO DATABASE!!");
		} catch (ClassNotFoundException e) {
			connect = null;
			System.out.println("ClassNotFoundException hallo");
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("ClassNotFoundException hallo");
		} catch (SQLException e) {
			connect = null;
			System.out.println("SQLException");
			System.out.println(e.getMessage());
		}
    }
    
    /**
     * Erstelle den DatabaseConnector
     * @return DatabaseConnector
     */
	public static DatabaseConnector createConnection() {
		if(databaseconnection == null)
				databaseconnection = new DatabaseConnector();
		return databaseconnection;
	}
	   
	/**
	 * F�hre INSERT aus
	 * @param INSERT
	 * @return -1 bei Fehler anosnten id des hinzuf�gten Datensatzes
	 */
	public int InsertQuery(String query) {
		
		if(this.connect != null) {
			System.out.println("INSIDE");
			try {
				Statement statement = this.connect.createStatement();
				statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
				ResultSet keyset = statement.getGeneratedKeys();
				
				if(keyset.next())
					return keyset.getInt(1);
			}
			catch (SQLException e){
			}
		}
		System.out.println("OUTSIDE");
		return -1;
	}
	
	/**
	 * F�hre SELECT aus
	 * @param SELECT
	 * @return Resultset welches das Ergebnis des SELECTs enth�lt
	 */
	public ResultSet SelectQuery(String query) {
		if(this.connect != null)
			try {
				Statement statement = this.connect.createStatement();
				ResultSet result = statement.executeQuery(query);
				return result;
			}
			catch (SQLException e){
				
			}
		
		return null;
	}
	
	/**
	 * F�hre UPDATE aus
	 * @param UPDATE
	 */
	public void UpdateQuery(String query) {
		System.out.println("Im using the update");
		if(this.connect != null)
			try {
				Statement statement = this.connect.createStatement();
				System.out.println(statement.executeUpdate(query));
			}
			catch (SQLException e){
				
			}
	}
	
	
	/**
	 * Schlie�e Datenbankverbindung
	 * @throws SQLException
	 */
	public static void CloseConnection() throws SQLException {
		if(databaseconnection != null)
		{
			databaseconnection.connect.close();
			databaseconnection= null;
		}
	}
}