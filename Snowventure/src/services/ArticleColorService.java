package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import classes.*;


/**
 * Beschreibung: Modelklasse für Artikelfarben 
 * @author Ansprechpartner Fabian Meise
 *
 */
public class ArticleColorService {
	
	/**
	 * Erhalte alle auswählbaren Farben (notwendig für Filter
	 * @return ArrayList aller Artikelfarben
	 * @throws SQLException
	 */
	public static ArrayList<ArticleColor> GetAllPossibleColors() throws SQLException{
		ArrayList<ArticleColor> colors = new ArrayList<ArticleColor>();
		
		String query = "SELECT DISTINCT acolid, color, hexcode from ARTICLECOLOR";
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
			colors.add(new ArticleColor(result.getInt("acolid"),result.getString("color"), result.getString("hexcode")));
		return colors;
	}
	
	/**
	 * Erhalte alle Artikelfarben einer Artikelversion
	 * @param avid Artikelversion
	 * @return ArrayList Artikelfarben 
	 * @throws SQLException
	 */
	public static ArrayList<ArticleColor> GetSpecificColors(int avid) throws SQLException{
		ArrayList<ArticleColor> colors = new ArrayList<ArticleColor>();
		
		String query = "SELECT DISTINCT acolid, color, hexcode from ARTICLECOLOR WHERE acolid in (SELECT acolid FROM ARTICLEVERSION_TO_COLOR WHERE avid = '%d')";
		query = String.format(query, avid);
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
			colors.add(new ArticleColor(result.getInt("acolid"),result.getString("color"), result.getString("hexcode")));
		return colors;
	}
	
	/**
	 * Erhalte eine spezifische Artikelfarbe anhand der Farbid
	 * @param acolid Farbid
	 * @return Artikelfarbe
	 * @throws SQLException
	 */
	public static ArticleColor GetSpecificColor(int acolid) throws SQLException{
		
		
		String query = "SELECT DISTINCT acolid, color, hexcode from ARTICLECOLOR WHERE acolid = '%d'";
		query = String.format(query, acolid);
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
			return new ArticleColor(result.getInt("acolid"),result.getString("color"), result.getString("hexcode"));
		
		return null;
	}
	
	/**
	 * Füge eine Artikelfarbe einer Artikelversion hinzu
	 * @param acolid Farbid
	 * @param avid Artikelversionsid
	 * @return -1 bei Fehler anosnten id des hinzufügten Datensatzes
	 * @throws SQLException
	 */
	public static int AddArticleColorToVersion(int acolid, int avid) throws SQLException{
		int insert =0;
		
		String query = "INSERT INTO ARTICLEVERSION_TO_COLOR(avid,acolid) VALUES('%d','%d')";
		query = String.format(query, avid, acolid);
		
		insert = DatabaseConnector.createConnection().InsertQuery(query);
		
		return insert;
	}
	
	/**
	 * Lösche alle Artikelfarben einer Artikelversion
	 * @param avid Artikelversion
	 */
	public static void DeleteArticleColorToVersion(int avid) {
		String query = "DELETE ARTICLEVERSION_TO_COLOR WHERE avid='%d'";
		query = String.format(query, avid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
}
