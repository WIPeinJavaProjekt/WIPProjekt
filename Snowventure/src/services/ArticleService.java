package services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import classes.*;

public class ArticleService {
	
	public static int AddArticle(Article a) {
		String query;
		int aid = -1;
		query = "INSERT INTO ARTICLE(name,description) VALUES('%s','%s')";
		query = String.format(query,
				a.name,
				a.description);
		aid = DatabaseConnector.createConnection().InsertQuery(query);
		a.ID = aid;
		
		for(ArticleVersion av: a.versions) {
			
			av.ID = a.ID;
			
			int dummy = AddArticleVersion(av);
			System.out.println("dummy: " + dummy);
			if(dummy == -1)
				return dummy;
		}
		
		return aid;
	}
	
	public static int AddArticleVersion(ArticleVersion av) {
		
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
		
		return avid;
	}
	
	public static void UpdateArticle(Article a)
	{
		String query;
		query = "UPDATE ARTICLE SET name ='%s', description ='%s' where aid ='%d'";
		
		query = String.format(query, a.name,a.description,a.ID);
		
		DatabaseConnector.createConnection().UpdateQuery(query);
		for(ArticleVersion av: a.versions)
			UpdateArticleVersion(av);
		
	}
	
	public static void UpdateArticleVersion(ArticleVersion av) {
		String query;
		query = "UPDATE ARTICLEVERSION SET property = '%s' and propertyvalue = '%s' defaultprice = '%f' where avid ='%d'";
		
		query = String.format(query, av.property, av.propertyvalue, av.price, av.versionid);
		DatabaseConnector.createConnection().UpdateQuery(query);
	}
	
	public static ArrayList<ArticleVersion> GetAllArticleVersion(Article a) throws SQLException {
		ArrayList<ArticleVersion> av = new ArrayList<ArticleVersion>();
		
		String query ="SELECT  avid,property,propertyvalue, defaultprice FROM ARTICLEVERSION WHERE TechIsActive = 1 AND TechIsDeleted = 0 AND aid ='%d'";
		query = String.format(query, a.ID);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			ArticleVersion version = new ArticleVersion(result.getInt("avid"),result.getString("property"),result.getString("propertyvalue"),result.getDouble("defaultprice"),a);
			av.add(version);
		}
		
		return av;
	}
	
	public static ArrayList<Article> GetAllArticlesByName(String namepattern) throws SQLException{
		ArrayList<Article> articles = new ArrayList<Article>();
		
		String query = "SELECT aid, name, description FROM ARTICLE WHERE name ='%s';";
		query = String.format(query, namepattern);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			Article a = new Article(result.getInt("aid"),result.getString("name"),result.getString("name"));
			a.versions = (ArrayList<ArticleVersion>)GetAllArticleVersion(a).clone();
			articles.add(a);
		}
		
		return articles;
	}
	
	//Categorie nicht im Artikel enthalten da keine Relevanz für weitere Verarbeitung verkomplizierung für Bestellungen etc | Was passiert wenn sich Kategorie ändert? z.B. durch Umstruktuierung usw.
	public static ArrayList<Article> GetAllArticlesByCategorie(Categorie c) throws SQLException{
		ArrayList<Article> articles = new ArrayList<Article>();
		
		String query = "SELECT aid, name, description FROM ARTICLE WHERE acid ='%d';";
		query = String.format(query, c.GetACID());
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			Article a = new Article(result.getInt("aid"),result.getString("name"),result.getString("name"));
			a.versions = (ArrayList<ArticleVersion>)GetAllArticleVersion(a).clone();
			articles.add(a);
		}
		
		return articles;
	}
	
	public static Article GetArticle(int id) throws SQLException{
		Article article = new Article(-1);
		String query = "SELECT aid, name, description FROM ARTICLE WHERE aid='%d';";
		query = String.format(query, id);
		
		ResultSet result = DatabaseConnector.createConnection().SelectQuery(query);
		
		while(result.next())
		{
			article = new Article(result.getInt("aid"),result.getString("name"),result.getString("name"));
			article.versions = GetAllArticleVersion(article);
		}
		
		return article;
	}
		
	public static Article GetSelectedArticle(ArticleVersion av) throws SQLException {
		Article article = new Article( GetArticle(av.ID));
		
		return PrepSelectedArticle(av.versionid,article);
	}
		
	public static Article GetSelectedArticle(int avid) throws SQLException{
		Article article = new Article( GetArticleIdFromAvid(avid));
		
		return PrepSelectedArticle(avid,article);
	}

	public static int GetArticleIdFromAvid(int avid) throws SQLException{
		int aid = -1;
		
		String query = "SELECT TOP 1 aid from articleversion where avid='%d'";
		query = String.format(query, avid);
		aid = DatabaseConnector.createConnection().InsertQuery(query);
		
		return aid;
	}
	
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
	
}
