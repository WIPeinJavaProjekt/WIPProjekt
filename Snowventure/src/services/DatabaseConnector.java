package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
	private static Connection con = null;
	private static String dbHost = "meiserv.de"; // Hostname
	private static String dbPort = "3306";      // Port -- Standard: 3306
	private static String dbName = "WIPPROJEKT";   // Datenbankname
	private static String dbUser = "tech_WIP";     // Datenbankuser
	private static String dbPass = "wipprojektrocks";      // Datenbankpasswort
	
    private Connection connect = null;
    
    private static DatabaseConnector databaseconnection = null;
	
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
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			connect = null;
			System.out.println(e.getMessage());
		}
    }
    
	public static DatabaseConnector createConnection() {
		if(databaseconnection == null)
				databaseconnection = new DatabaseConnector();
		return databaseconnection;
	}
	   
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
	
	public void UpdateQuery(String query) {
		if(this.connect != null)
			try {
				Statement statement = this.connect.createStatement();
				statement.executeUpdate(query);
			}
			catch (SQLException e){
				
			}
	}
	
	
	
	public static void CloseConnection() throws SQLException {
		if(databaseconnection != null)
		{
			databaseconnection.connect.close();
			databaseconnection= null;
		}
	}
	
	private void close() {
        try {
        	databaseconnection.connect.close();
        }
        catch (Exception e) {
        	
        }
	}
}