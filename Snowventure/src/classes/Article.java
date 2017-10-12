package classes;

public class Article {

	public String ID;
	public String name;
	public String description;
	public double price;
	
	public Article(String ID)
	{
		this.ID = ID;
	}
	
	public Article(String ID, String name, String description, double price)
	{
		this.ID = ID;
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public Article get(String ID)
	{
		return null;
	}
	
	public boolean insert()
	{
		return true;
	}
	
	public boolean update()
	{
		return true;
	}
}
