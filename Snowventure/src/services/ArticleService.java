package services;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import javax.imageio.ImageIO;

import java.io.InputStream;

import com.mysql.jdbc.PreparedStatement;

import classes.*;

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
		query = "INSERT INTO ARTICLE(name,description,acid,manufacturer) VALUES('%s','%s','%d','%s')";
		query = String.format(query,
				a.name,
				a.description,
				a.acid,
				a.manufacturer);
		System.out.println(query);
		aid = DatabaseConnector.createConnection().InsertQuery(query);
		a.ID = aid;
		
		for(ArticleVersion av: a.versions) {
			
			av.ID = a.ID;
			
			int dummy = AddArticleVersion(av);
			if(dummy == -1)
				return dummy;
		}
		
		for(ArticlePicture pic: a.pictures)
		{
			int dummy = AddPicture(pic, a.ID);
			if(dummy == -1)
				return dummy;
		}
		
		return aid;
	}
	
	/**
	 * Method for adding an Articleversion
	 * @param av the ArticleVersion
	 * @return int value depending on success of insertion
	 */
	public static int AddArticleVersion(ArticleVersion av) {		
		Locale.setDefault(Locale.ENGLISH);		
		String query;
		int avid = -1;
		query = "INSERT INTO ARTICLEVERSION(property,propertyvalue,defaultprice,aid,color,size) VALUES('%s','%s','%f','%d','%s','%s')";
		query = String.format(query,
				av.property,
				av.propertyvalue,
				av.price,
				av.ID,
				av.color,
				av.size);
		
		avid = DatabaseConnector.createConnection().InsertQuery(query);
		
		return avid;
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
		query = "UPDATE ARTICLE SET name ='%s', description ='%s' where aid ='%d'";
		
		query = String.format(query, a.name,a.description,a.ID);
		
		System.out.println(query);
		
		DatabaseConnector.createConnection().UpdateQuery(query);
		for(ArticleVersion av: a.versions)
			UpdateArticleVersion(av);
		
		DeletePicture(a.ID);
		for(ArticlePicture pic: a.pictures)
		{
			int dummy = AddPicture(pic, a.ID);
		}
	}
	
	/**
	 * Method for updating an Articleversion
	 * @param av Articleversion to be updated
	 */
	public static void UpdateArticleVersion(ArticleVersion av) {
		Locale.setDefault(Locale.ENGLISH);
		String query;
		query = "UPDATE ARTICLEVERSION SET property = '%s', propertyvalue = '%s', defaultprice = '%f', color = '%s', size = '%s' where avid ='%d'";
		
		query = String.format(query,
				av.property,
				av.propertyvalue,
				av.price,
				av.color,
				av.size,
				av.versionid);
		
		System.out.println(query);
		
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
	/**
	 * Method for getting all Articleversions of an Article
	 * @param a Article
	 * @return Arraylist of the specific Articleversions
	 * @throws SQLException
	 */
	public static ArrayList<ArticleVersion> GetAllArticleVersion(Article a) throws SQLException {
		ArrayList<ArticleVersion> av = new ArrayList<ArticleVersion>();
		
		String query ="SELECT  avid,property,propertyvalue, defaultprice, color, size FROM ARTICLEVERSION WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND aid ='%d'";
		query = String.format(query, a.ID);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			ArticleVersion version = new ArticleVersion(result.getInt("avid"),result.getString("property"),result.getString("propertyvalue"),result.getDouble("defaultprice"),a, result.getString("color"),result.getString("size"));
			av.add(version);
		}
		
		return av;
	}
	
	/**
	 * Method for getting all Articles
	 * @return Arraylist of all Articles
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static ArrayList<Article> GetAllArticles() throws SQLException, IOException{
		ArrayList<Article> articles = new ArrayList<Article>();
		
		String query = "SELECT aid, name, description,acid,manufacturer FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0;";
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			Article a = new Article(result.getInt("aid"), result.getString("name"), result.getString("description"),result.getInt("acid"),result.getString("manufacturer"));
			a.versions = (ArrayList<ArticleVersion>)GetAllArticleVersion(a).clone();
			a.pictures = (ArrayList<ArticlePicture>)GetPicturesFromArticleId(a.ID).clone();
			articles.add(a);
		}
		
		return articles;
	}
	
	/**
	 * Method for getting all Articles with specific name
	 * @param namepattern compare String
	 * @return Arraylist with specific Articles
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static ArrayList<Article> GetAllArticlesByName(String namepattern) throws SQLException, IOException{
		ArrayList<Article> articles = new ArrayList<Article>();
		
		String query = "SELECT aid, name, description, acid, manufacturer FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND name like '%"+namepattern+"%';";
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			Article a = new Article(result.getInt("aid"), result.getString("name"), result.getString("description"),result.getInt("acid"),result.getString("manufacturer"));
			a.versions = (ArrayList<ArticleVersion>)GetAllArticleVersion(a).clone();
			a.pictures = (ArrayList<ArticlePicture>)GetPicturesFromArticleId(a.ID).clone();
			articles.add(a);
		}
		
		return articles;
	}
	
	//Categorie nicht im Artikel enthalten da keine Relevanz für weitere Verarbeitung verkomplizierung für Bestellungen etc | Was passiert wenn sich Kategorie ändert? z.B. durch Umstruktuierung usw.
	
	/**
	 * Method for Getting all Articles with specific categorie
	 * @param c specific categories
	 * @return Arraylist with specific Articles
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static ArrayList<Article> GetAllArticlesByCategorie(Categorie c) throws SQLException, IOException{
		ArrayList<Article> articles = new ArrayList<Article>();
		
		String query = "SELECT aid, name, description, acid, manufacturer FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0 acid ='%d';";
		query = String.format(query, c.GetACID());
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			Article a = new Article(result.getInt("aid"),result.getString("name"),result.getString("name"),result.getInt("acid"),result.getString("manufacturer"));
			a.versions = (ArrayList<ArticleVersion>)GetAllArticleVersion(a).clone();
			a.pictures = (ArrayList<ArticlePicture>)GetPicturesFromArticleId(a.ID).clone();
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
		String query = "SELECT aid, name, description, acid, manufacturer  FROM ARTICLE WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND aid='%d';";
		query = String.format(query, id);
		
//		System.out.println(query);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			article = new Article(result.getInt("aid"),result.getString("name"),result.getString("description"),result.getInt("acid"),result.getString("manufacturer"));
			article.versions = (ArrayList<ArticleVersion>)GetAllArticleVersion(article).clone();
			article.pictures = (ArrayList<ArticlePicture>)GetPicturesFromArticleId(article.ID).clone();
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
		
		return PrepSelectedArticle(av.versionid,article);
	}
	
	/**
	 * Method for getting a specific Article by its id and selected version
	 * @param avid selected version id
	 * @return Article the specific Article and selected version
	 * @throws SQLException
	 */
	public static Article GetSelectedArticle(int avid) throws SQLException{
		Article article = new Article( GetArticleIdFromAvid(avid));
		
		return PrepSelectedArticle(avid,article);
	}

	/**
	 * Helper Method for getting a specific Articleid by its Articleversionid
	 * @param avid selected version id
	 * @return int id of the article
	 * @throws SQLException
	 */
	private static int GetArticleIdFromAvid(int avid) throws SQLException{
		int aid = -1;
		
		String query = "SELECT TOP 1 aid from articleversion where TechIsActive = 1 AND TechIsDeleted = 0 avid='%d'";
		query = String.format(query, avid);
		aid = DatabaseConnector.createConnection().InsertQuery(query);
		
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
		for(int i = 0; i< a.versions.size(); i++)
		{
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
	public static ArrayList<ArticlePicture> GetPicturesFromArticleId(int aid) throws IOException, SQLException{
		ArrayList<ArticlePicture> pictures = new ArrayList<ArticlePicture>();
		
		String query = "SELECT name,image FROM ARTICLEIMAGE WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND aid ='%d';";
		query = String.format(query, aid);
		
		System.out.println(query);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			Blob imageblob = result.getBlob("image");
			InputStream binaryStream = (InputStream) imageblob.getBinaryStream(1, imageblob.length());
			ArticlePicture p = new ArticlePicture(result.getString("name"),binaryStream);
			pictures.add(p);
		}		
		return pictures;
	}
	
	/**
	 * Method for adding a ArticlePicture
	 * @param img
	 * @return int value depending on success of insertion
	 * @throws SQLException
	 * @throws IOException
	 */
	public static int AddPicture(ArticlePicture img, int aid) throws SQLException, IOException {
		int apid =-1;
		String query = "INSERT INTO ARTICLEIMAGE(name,image, aid) VALUES( ?, ?, ?)";
		PreparedStatement statement = (PreparedStatement) DatabaseConnector.connect.prepareStatement(query);
		statement.setString(1, img.name);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write((RenderedImage) img.image,"png", os); 
		ByteArrayInputStream fis = new ByteArrayInputStream(os.toByteArray());
		statement.setBlob(2, fis);
		statement.setInt(3, aid);
		apid = statement.executeUpdate();
		return apid;
	}

	/**
	 * Method for deleting all Pictures from an Article
	 * @param aid Articleid
	 */
	public static void DeletePicture(int aid) {
		String query = "Delete ArticleImage WHERE aid = '%d'";
		query = String.format(query, aid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
}
