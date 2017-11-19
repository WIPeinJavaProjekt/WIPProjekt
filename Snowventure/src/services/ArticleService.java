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
public class ArticleService {
	
	/**
	 * Method for adding an Article
	 * @param a the Article
	 * @return int value depending on success of insertion
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
				a.gender);
		System.out.println(query);
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
	 * Method for adding an Articleversion
	 * @param av the ArticleVersion
	 * @return int value depending on success of insertion
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
	 * Method for adding an ArticleversionSize
	 * @param av the ArticleVersion
	 * @return int value depending on success of insertion
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
		}
		
		return insert;
	}
	
	/**
	 * Helper Method for deleting All AVSizes of a specific articleversion
	 * @param avid
	 */
	private static void DeleteArticleVersionSize(int avid)
	{
		String query ="DELETE ARTICLEVERSIONSIZE WHERE avid = '%d'";
		query = String.format(query, avid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
	/**
	 * Method for updating an Article
	 * @param a Article to be updated
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void UpdateArticle(Article a) throws SQLException, IOException
	{
		String query;
		query = "UPDATE ARTICLE SET name ='%s', description ='%s', manufacturer = '%s', acid = '%s', gender = '%s' where aid ='%d'";
		
		query = String.format(query, a.name,a.description,a.ID,a.manufacturer,a.gender,a.acid);
		
		System.out.println(query);
		
		DatabaseConnector.createConnection().UpdateQuery(query);
		for(ArticleVersion av: a.versions)
			UpdateArticleVersion(av);
		

	}
	
	/**
	 * Method for updating an Articleversion
	 * @param av Articleversion to be updated
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void UpdateArticleVersion(ArticleVersion av) throws SQLException, IOException {
		Locale.setDefault(Locale.ENGLISH);
		String query;
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
		}

		DeletePictureFromArticleVersion(av.versionid);
		for(ArticlePicture pic: av.pictures)
		{
			dummy = AddPictureToArticleVersion(pic, av.versionid);
		}
		
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
	
	
	/**
	 * Method for UpdatingArticleVersionsizes
	 * @param av
	 */
	public static void UpdateArticleVersionSize(ArticleVersion av) {
		DeleteArticleVersionSize(av.versionid);
		AddArticleVersionSize(av);
	}
	
	
	/**
	 * Method for getting all Articleversions of an Article
	 * @param a Article
	 * @return Arraylist of the specific Articleversions
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
	 * Method for getting all Articles
	 * @return Arraylist of all Articles
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static ArrayList<Article> GetAllArticles() throws SQLException, IOException{

		String query = "SELECT aid, name, description,acid,manufacturer,gender FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0;";
		
		
		return SearchForArticles(query,1,0);
	}
	
	/**
	 * Method for getting all Articles with specific name
	 * @param namepattern compare String
	 * @return Arraylist with specific Articles
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static ArrayList<Article> GetAllArticlesByName(String namepattern, int piclimit, int versionlimit) throws SQLException, IOException{

		String query = "SELECT aid, name, description, acid, manufacturer,gender FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND name like '%"+namepattern+"%';";
		
		return SearchForArticles(query,piclimit,versionlimit);
	}
	

	/**
	 * Method for Getting all Articles with specific categorie
	 * @param c specific categories
	 * @return Arraylist with specific Articles
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static ArrayList<Article> GetAllArticlesByCategorie(int c, String pattern, int piclimit, int versionlimit) throws SQLException, IOException{

		String query = "SELECT aid, name, description, acid, manufacturer, gender FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND acid ='%d' AND name like '%"+pattern+"%';";
		query = String.format(query, c);
		
		
		
		return SearchForArticles(query,piclimit,versionlimit);
	}
	
	
	public static ArrayList<Article> GetAllArticlesByFilter(int c, String pattern, int piclimit, int versionlimit, String gender, String manufacturer, double minprice, double maxprice, String color, String size) throws SQLException, IOException{

		
		String query = "SELECT aid, name, description, acid, manufacturer, gender FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0";
		if(c != -1)
			query +=" AND acid="+ c;		
		if(manufacturer != "")
			query +=" AND INSTR('"+manufacturer+"',manufacturer)";
		if(gender != "")
			query +=" AND INSTR('"+gender+"',gender)";
		
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
		
		
		
		
		
		System.out.println("Filter: "+subquery);
		System.out.println("Filter: "+query);
		
		
		return SearchForArticles(query,piclimit,versionlimit);
	}
	
	
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
	 * Method for getting a specific Article by its id
	 * @param id Articleid
	 * @return Article the specific Article
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static Article GetArticle(int id) throws SQLException, IOException{
		Article article = new Article(-1);
		String query = "SELECT aid, name, description, acid, manufacturer,gender  FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND aid='%d';";
		query = String.format(query, id);
		
//		System.out.println(query);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			article = new Article(result.getInt("aid"),result.getString("name"),result.getString("description"),result.getInt("acid"),result.getString("manufacturer"),result.getString("gender"));
			article.versions = (ArrayList<ArticleVersion>)GetAllArticleVersion(article,-1,-1).clone();
		}
		
		return article;
	}
	
	
	/**
	 * Method for getting a specific Article by its id and selected version
	 * @param av the selected version
	 * @return Article the specific Article
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static Article GetSelectedArticle(ArticleVersion av) throws SQLException, IOException {
		Article article = new Article( GetArticle(av.ID));
		article = PrepSelectedArticle(av.versionid,article);
		return article;
	}
	
	/**
	 * Method for getting a specific Article by its id and selected version
	 * @param avid selected version id
	 * @return Article the specific Article and selected version
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static Article GetSelectedArticle(int avid) throws SQLException, IOException{
		System.out.println("Suche nach AVID: "+avid);
		Article article = GetArticle( GetArticleIdFromAvid(avid));
		System.out.println("Ausgewählterartikel: "+article.ID);
		System.out.println("Vor der Prep");
		article = PrepSelectedArticle(avid,article);
		System.out.println("Artikel sollte jetzt prepared sein");
		return article;
	}

	/**
	 * Helper Method for getting a specific Articleid by its Articleversionid
	 * @param avid selected version id
	 * @return int id of the article
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
		
		
		System.out.println("Aushewählte AID: "+aid);
		return aid;
	}
	
	/**
	 * Helper Method to prepare a selected Article
	 * @param avid id of the selected version
	 * @param a Article to be prepared
	 * @return Article the specific Article and set selected version
	 * @throws SQLException
	 */
	private static Article PrepSelectedArticle(int avid, Article a) {
		System.out.println("Anzahl der versionen:"+ a.versions.size());
		
		for(int i = 0; i< a.versions.size(); i++)
		{
			System.out.println("Versionsvergleich "+ a.versions.get(i).versionid + " == "+ avid);
			if(a.versions.get(i).versionid == avid)
			{
				a.SetSelectedVersion(i);
				return a;
			}
			
		}
		
		return a;
	}
	
	/**
	 * Method for getting all ArticlePictures from a specific Articleid
	 * @param aid Articleid
	 * @return Arraylist of all ArticlePictures
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
	 * Method for adding a ArticlePicture
	 * @param img
	 * @return int value depending on success of insertion
	 * @throws SQLException
	 * @throws IOException
	 */
	public static int AddPictureToArticleVersion(ArticlePicture img, int avid) throws SQLException, IOException {
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

	/**
	 * Method for deleting all Pictures from an Article
	 * @param aid Articleid
	 */
	public static void DeletePictureFromArticleVersion(int avid) {
		String query = "Delete ArticleImage WHERE avid = '%d'";
		query = String.format(query, avid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
}
