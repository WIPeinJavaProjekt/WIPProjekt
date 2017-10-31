package classes;

public class ArticleVersion extends Article{
	public int versionid;
	public String property;
	public String propertyvalue;
	public double price;
	
	public ArticleVersion() {};
	
	public ArticleVersion(int versionid, String property,String propertyvalue, double price, Article a)
	{
		super(a);
		this.versionid = versionid;
		this.property = property;
		this.propertyvalue = propertyvalue;
		this.price = price;
	}
}
