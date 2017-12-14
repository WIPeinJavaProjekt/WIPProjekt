package services;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import javax.imageio.ImageIO;

import com.mysql.jdbc.PreparedStatement;

import classes.Article;
import classes.ArticleColor;
import classes.ArticlePicture;
import classes.ArticleVersion;
import classes.Categorie;

/**
 * Modelclass for article administration
 * 
**/
/**
 * Beschreibung: Modelklasse zur Administration von Artikeln
 * @author Ansprechpartner Fabian Meise
 *
 */
public class ArticleService {
	
	/**
	 * Füge einen Arikel hinzu
	 * @param a der Artikel
	 * @return -1 bei Fehler anosnten id des hinzufügten Datensatzes
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static int AddArticle(Article a) throws SQLException, IOException {
		String query;
		int aid = -1;
		query = "INSERT INTO ARTICLE(name,description,acid,manufacturer,gender) VALUES('%s','%s','%d','%s','%s')";
		query = String.format(query,
				a.name,
				a.description,
				a.acid,
				a.manufacturer,
				a.gender.replaceAll("([\\[\\]])", ""));
		aid = DatabaseConnector.createConnection().InsertQuery(query);
		a.ID = aid;
		
		for(ArticleVersion av: a.versions) {
			
			av.ID = a.ID;
			
			int dummy = AddArticleVersion(av);
			if(dummy == -1)
				return dummy;
		}
		

		
		return aid;
	}
	
	/**
	 * Füge eine Artikelversion hinzu
	 * @param av die Artikelversion
	 * @return -1 bei Fehler anosnten id des hinzufügten Datensatzes
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static int AddArticleVersion(ArticleVersion av) throws SQLException, IOException {		
		Locale.setDefault(Locale.ENGLISH);		
		String query;
		int avid = -1;
		query = "INSERT INTO ARTICLEVERSION(property,propertyvalue,defaultprice,aid) VALUES('%s','%s','%f','%d')";
		query = String.format(query,
				av.property,
				av.propertyvalue,
				av.price,
				av.ID);
		
		avid = DatabaseConnector.createConnection().InsertQuery(query);
		av.versionid = avid;
		
		int dummy = AddArticleVersionSize(av);
		
		for(ArticleColor c: av.colors)
		{
			ArticleColorService.AddArticleColorToVersion(c.acolid,av.versionid);
		if(dummy == -1)
			return dummy;
		}
		
		for(ArticlePicture pic: av.pictures)
		{
			if(pic != null) {
				dummy = AddPictureToArticleVersion(pic, av.versionid);
				if(dummy == -1)
					return dummy;
			}
		}
		
		return avid;
	}
	
	/**
	 * Füge alle Artikelgrößen eines Artikels hinzu
	 * @param av die Artikelversion
	 * @return -1 bei Fehler anosnten id des hinzufügten Datensatzes
	 */
	public static int AddArticleVersionSize(ArticleVersion av) {
		int insert = 1;		
		
		for(String s: av.sizes)
		{
			String query = "INSERT INTO ARTICLEVERSIONSIZE(size,avid) VALUES('%s','%s');";
			query = String.format(query, 
					s,
					av.versionid
					);
			insert = DatabaseConnector.createConnection().InsertQuery(query);
			if(insert == -1)
				return -1;
			insert = StockService.AddStock(av, s,0);
			if(insert == -1)
				return -1;
		}
		
		return insert;
	}
	
	/**
	 * Hilfsmethode zum Löschen aller bestehenden Artikelgrößen einer Version
	 * @param Artikelversion
	 */
	private static void DeleteArticleVersionSize(int avid)
	{
		String query ="DELETE FROM ARTICLEVERSIONSIZE WHERE avid = %d";
		query = String.format(query, avid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
	/**
	 * Aktualisiere einen Artikel
	 * @param der zu aktualisierende Artikel
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void UpdateArticle(Article a) throws SQLException, IOException
	{
		String query;
		query = "UPDATE ARTICLE SET name ='%s', description ='%s', manufacturer = '%s', acid = '%s', gender = '%s' where aid ='%d'";
		
		query = String.format(query, a.name,a.description,a.manufacturer,a.acid,a.gender.replaceAll("([\\[\\]])", ""), a.ID);
		System.out.println(query);
		DatabaseConnector.createConnection().UpdateQuery(query);
		for(ArticleVersion av: a.versions)
			UpdateArticleVersion(av);
		
	}
	
	/**
	 * Aktualisiere eine Artikelversion
	 * @param die zu aktualisierende Artikelversion
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void UpdateArticleVersion(ArticleVersion av) throws SQLException, IOException {
		Locale.setDefault(Locale.ENGLISH);
		String query;
		
		System.out.println("Update folgenden Artikel " + av.versionid);
		query = "UPDATE ARTICLEVERSION SET property = '%s', propertyvalue = '%s', defaultprice = '%f' where avid ='%d'";
		
		query = String.format(query,
				av.property,
				av.propertyvalue,
				av.price,
				av.versionid);
		
		ArticleColorService.DeleteArticleColorToVersion(av.versionid);		
		
		int dummy=0;
		
		for(ArticleColor c: av.colors)
		{
			dummy = ArticleColorService.AddArticleColorToVersion(c.acolid,av.versionid);
			System.out.println("Return addColor: " + dummy);
		}

		UpdateArticleVersionSize(av);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
	
	
	/**
	 * Aktualisiere alle Größen einer Artikelversion
	 * @param av ArticleVersion
	 */
	public static void UpdateArticleVersionSize(ArticleVersion av) {
		DeleteArticleVersionSize(av.versionid);
		AddArticleVersionSize(av);
	}
	
	
	/**
	 * Erhalte alle Versionen eines Artikels
	 * @param der zugehörige Artikel
	 * @return Arraylist aller Versionen
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static ArrayList<ArticleVersion> GetAllArticleVersion(Article a, int piclimit, int versionlimit) throws SQLException, IOException {
		ArrayList<ArticleVersion> av = new ArrayList<ArticleVersion>();
		
		String query ="SELECT  avid,property,propertyvalue, defaultprice FROM ARTICLEVERSION WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND aid ='%d'";
		query = String.format(query, a.ID);
		
		if(versionlimit >0)
			query +=" limit "+ versionlimit;
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			ArrayList<String> sizes = new ArrayList<String>(GetAllArticleVersionsize(result.getInt("avid")));
			ArticleVersion version = new ArticleVersion(result.getInt("avid"),result.getString("property"),result.getString("propertyvalue"),result.getDouble("defaultprice"),a,sizes,ArticleColorService.GetSpecificColors(result.getInt("avid")));
			version.pictures = (ArrayList<ArticlePicture>)GetPicturesFromArticleVersionId(version.versionid,piclimit).clone();
			av.add(version);
		}
		
		return av;
	}
	
	/**
	 * Erhalte alle auswählbaren größen einer Artikelversion
	 * @param Artikelversion
	 * @return Arraylist aller Größen
	 * @throws SQLException
	 */
	public static ArrayList<String> GetAllArticleVersionsize(int avid) throws SQLException{
		ArrayList<String> sizes = new ArrayList<String>();
		String query = "SELECT size from ARTICLEVERSIONSIZE Where avid ='%d'";
		query = String.format(query, avid);
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
			sizes.add(result.getString("size"));
		
		return sizes;
	}
	
	/**
	 * Erhalte alle Artikel
	 * @return Arraylist aller Artikel
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static ArrayList<Article> GetAllArticles() throws SQLException, IOException{

		String query = "SELECT aid, name, description,acid,manufacturer,gender FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0;";
		
		
		return SearchForArticles(query,1,0);
	}
	
	/**
	 * Erhalte alle Artikel anhand des Namensvergleich
	 * @param pattern der Namensvergleich
	 * @param piclimit Anzahl der geladenen Bilder pro Version
	 * @param versionlimit Anzahl der geladenen Versionen
	 * @return ArrayList aller bedingungserfüllenden Artikel
	 * @throws SQLException
	 * @throws IOException
	 */
	public static ArrayList<Article> GetAllArticlesByName(String pattern, int piclimit, int versionlimit) throws SQLException, IOException{

		String query = "SELECT aid, name, description, acid, manufacturer,gender FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND name like '%"+pattern+"%';";
		
		return SearchForArticles(query,piclimit,versionlimit);
	}
	

	/**
	 * Erhalte alle Artikel anhand von Name und Kategorie
	 * @param c Kategorie
	 * @param pattern der Namensvergleich
	 * @param piclimit Anzahl der geladenen Bilder pro Version
	 * @param versionlimit Anzahl der geladenen Versionen
	 * @return ArrayList aller bedingungserfüllenden Artikel
	 * @throws SQLException
	 * @throws IOException
	 */
	public static ArrayList<Article> GetAllArticlesByCategorie(int c, String pattern, int piclimit, int versionlimit) throws SQLException, IOException{

		String query = "SELECT aid, name, description, acid, manufacturer, gender FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND acid ='"+c+"' AND name like '%"+pattern+"%';";

		return SearchForArticles(query,piclimit,versionlimit);
	}
	
	
	/**
	 * Erhalte alle Artikel anhand der Filterkriterien
	 * @param c Kategorie
	 * @param pattern der Namensvergleich
	 * @param piclimit Anzahl der geladenen Bilder pro Version
	 * @param versionlimit Anzahl der geladenen Versionen
	 * @param gender gewähltes Geschlecht 
	 * @param manufacturer gewählter Hersteller
	 * @param minprice Mindestpreis
	 * @param maxprice Maximalpreis
	 * @param color gewählte Farben
	 * @param size gewählte Größen
	 * @return ArrayList aller bedingungserfüllenden Artikel
	 * @throws SQLException
	 * @throws IOException
	 */
	public static ArrayList<Article> GetAllArticlesByFilter(int c, String pattern, int piclimit, int versionlimit, String gender, String manufacturer, double minprice, double maxprice, String color, String size) throws SQLException, IOException{

		
		String query = "SELECT aid, name, description, acid, manufacturer, gender FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0";
		if(c != -1)
			query +=" AND acid="+ c;	
		if(pattern != "")
			query +=" AND name like '%"+pattern+"%'";
		if(manufacturer != "")
			query +=" AND INSTR('"+manufacturer+"',manufacturer)";
		if(gender != "")
		{
			gender = gender.replaceAll("([\\[\\]])", "");
			query +=" AND (INSTR('"+gender+"',gender) OR INSTR(gender,'"+gender+"'))";
		}
		
		String subquery="";
		if(minprice >0)
			subquery=" AND AID IN(SELECT distinct aid from ARTICLEVERSION where defaultprice >="+minprice+"";
		if(maxprice >0 && subquery!= "")
			subquery+= " and defaultprice <="+maxprice+"";
		else if(maxprice >0)
		{
			subquery=" AND AID IN(SELECT distinct aid from ARTICLEVERSION where defaultprice <="+maxprice+"";
		}
		if(subquery!="")
		subquery +=")";
		
		query+= subquery;
		
		
		subquery="";
		if(color != "")
			subquery=" AND AID IN(SELECT aid FROM ARTICLEVERSION_TO_COLOR s LEFT OUTER JOIN ARTICLEVERSION v ON s.avid = v.avid WHERE INSTR('"+color+"',acolid))";
		query+= subquery;
		
		subquery="";
		if(size != "")
			subquery=" AND AID IN(SELECT aid FROM ARTICLEVERSIONSIZE s LEFT OUTER JOIN ARTICLEVERSION v ON  s.avid = v.avid WHERE INSTR('"+size+"',size))";
		query+= subquery;
		
		
		
		return SearchForArticles(query,piclimit,versionlimit);
	}
	
	/**
	 * Hilfsmethode zum Suchen von Artikeln
	 * @param query auszuführendes SELECT
	 * @param piclimit Anzahl der geladenen Bilder pro Version
	 * @param versionlimit Anzahl der geladenen Versionen
	 * @return Artikel aus allen Datensätzen des SELECTS
	 * @throws SQLException
	 * @throws IOException
	 */
	private static ArrayList<Article> SearchForArticles(String query, int piclimit, int versionlimit) throws SQLException, IOException{
		ArrayList<Article> articles = new ArrayList<Article>();
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			Article a = new Article(result.getInt("aid"),result.getString("name"),result.getString("name"),result.getInt("acid"),result.getString("manufacturer"),result.getString("gender"));
			a.versions = (ArrayList<ArticleVersion>)GetAllArticleVersion(a, piclimit, versionlimit).clone();
			articles.add(a);
		}
		
		return articles;
		
	}
	
	
	
	/**
	 * Erhalte Artikel anhand der ID
	 * @param Artikelid
	 * @return den spezifischen Artikel
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static Article GetArticle(int id) throws SQLException, IOException{
		Article article = new Article(-1);
		String query = "SELECT aid, name, description, acid, manufacturer,gender  FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND aid='%d';";
		query = String.format(query, id);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			article = new Article(result.getInt("aid"),result.getString("name"),result.getString("description"),result.getInt("acid"),result.getString("manufacturer"),result.getString("gender"));
			article.versions = (ArrayList<ArticleVersion>)GetAllArticleVersion(article,-1,-1).clone();
		}
		
		return article;
	}
	
	
	/**
	 * Erhalte Artikel anhand der Artikelversion
	 * @param ausgewählte Artikelversion
	 * @return den spezifischen Artikel
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static Article GetSelectedArticle(ArticleVersion av) throws SQLException, IOException {
		Article article = new Article( GetArticle(av.ID));
		article = PrepSelectedArticle(av.versionid,article);
		return article;
	}
	
	/**
	 * Erhalte Artikel anhand der Artikelversion
	 * @param ausgewählte Artikelversionsid
	 * @return den spezifischen Artikel
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static Article GetSelectedArticle(int avid) throws SQLException, IOException{
		Article article = GetArticle( GetArticleIdFromAvid(avid));
		article = PrepSelectedArticle(avid,article);
		return article;
	}

	/**
	 * Erhalte Artikelid anhand der Artikelversionsid
	 * @param avid Artikelversionsid
	 * @return Artikelid
	 * @throws SQLException
	 */
	private static int GetArticleIdFromAvid(int avid) throws SQLException{
		int aid = -1;
		
		String query = "SELECT aid from ARTICLEVERSION where TechIsActive = 1 AND TechIsDeleted = 0 AND avid=%d LIMIT 1";
		query = String.format(query, avid);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			aid = result.getInt("aid");
		}
		return aid;
	}
	
	/**
	 * Wähle die Version eines Artikels aus
	 * @param auszuwählende Versions (ID)
	 * @param der zu modifizerende Artikel
	 * @return modifizerter Artikel
	 */
	private static Article PrepSelectedArticle(int avid, Article a) {	
		for(int i = 0; i< a.versions.size(); i++)
		{
			if(a.versions.get(i).versionid == avid)
			{
				a.setSelectedVersion(i);
				return a;
			}
			
		}
		
		return a;
	}
	
	/**
	 * Erhalte Bilder einer Artikelversion
	 * @param avid Artikelversionsid
	 * @param piclimit Anzahl der Bilder
	 * @return ArrayList aller Artikelbilder
	 * @throws IOException
	 * @throws SQLException
	 */
	public static ArrayList<ArticlePicture> GetPicturesFromArticleVersionId(int avid, int piclimit) throws IOException, SQLException{
		ArrayList<ArticlePicture> pictures = new ArrayList<ArticlePicture>();
		
		
		
		String query = "SELECT aimgid,name,image FROM ARTICLEIMAGE WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND avid ='%d'";
		
		if(piclimit >0)
			query += " LIMIT "+piclimit;
		
		query = String.format(query, avid,piclimit);
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			Blob imageblob = result.getBlob("image");
			InputStream binaryStream = (InputStream) imageblob.getBinaryStream(1, imageblob.length());
			byte[] content = result.getBytes("image");
			ArticlePicture p = new ArticlePicture(result.getString("name"), binaryStream, content, Integer.parseInt(result.getString("aimgid")));
			
			pictures.add(p);
		}		
		return pictures;
	}
	
	/**
	 * Erhalte ein Bild anhand einer Bildid
	 * @param imgId Bildid
	 * @return Artikelbild
	 * @throws IOException
	 * @throws SQLException
	 */
	public static ArticlePicture GetPictureFromPictureId(int imgId) throws IOException, SQLException{
		
		String query = "SELECT name,image FROM ARTICLEIMAGE WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND aimgid ='%d';";
		query = String.format(query, imgId);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			Blob imageblob = result.getBlob("image");
			InputStream binaryStream = (InputStream) imageblob.getBinaryStream(1, imageblob.length());
			byte[] content = result.getBytes("image");
			ArticlePicture p = new ArticlePicture(result.getString("name"),binaryStream, content, imgId);
			return p;
		}		
		return null;
	}
	
	/**
	 * Füge ein Artikelbild der DB hinzu
	 * @param img Bild
	 * @param avid Artikelversion
	 * @return -1 bei Fehler anosnten id des hinzufügten Datensatzes
	 * @throws SQLException
	 * @throws IOException
	 */
	public static int AddPictureToArticleVersion(ArticlePicture img, int avid) throws SQLException, IOException {
		if(img.image != null) {
			int apid =-1;
			String query = "INSERT INTO ARTICLEIMAGE(name,image, avid) VALUES( ?, ?, ?)";
			PreparedStatement statement = (PreparedStatement) DatabaseConnector.connect.prepareStatement(query);
			statement.setString(1, img.name);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write((RenderedImage) img.image,"png", os);
			ByteArrayInputStream fis = new ByteArrayInputStream(os.toByteArray());
			statement.setBlob(2, fis);
			statement.setInt(3, avid);
			apid = statement.executeUpdate();
			return apid;
		}
		return -1;
	}

	/**
	 * Lösche alle Bilder einer Artikelversion
	 * @param avid Artikelversionsid
	 */
	public static void DeletePictureFromArticleVersion(int avid) {
		String query = "Delete FROM ARTICLEIMAGE WHERE avid = %d";
		query = String.format(query, avid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
	/**
	 * Lösche ein Bild einer Artikelversion
	 * @param avid Artikelversionsid
	 * @param imgid zulöschende Bildid
	 */
	public static void DeletePictureFromArticleVersion(int avid, int imgid) {
		String query = "DELETE FROM ARTICLEIMAGE WHERE avid = %d and aimgid =%d";
		query = String.format(query, avid, imgid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}	
}
