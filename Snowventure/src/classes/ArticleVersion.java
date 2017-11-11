package classes;

import java.util.ArrayList;

public class ArticleVersion extends Article{
	public int versionid;
	public String property;
	public String propertyvalue;
	public double price;
	public ArrayList<String> sizes;
	public ArrayList<ArticleColor> colors;
	
	public ArticleVersion() {
		sizes = new ArrayList<String>();
		colors = new ArrayList<ArticleColor>();
	};
	
	public ArticleVersion(int versionid, String property,String propertyvalue, double price, Article a, ArrayList<String> size, ArrayList<ArticleColor> colors)
	{
		super(a);
		this.versionid = versionid;
		this.property = property;
		this.propertyvalue = propertyvalue;
		this.price = price;
		this.sizes = new ArrayList<String>(size);
		this.colors = new ArrayList<ArticleColor>(colors);
	}
	
	public int GetAvId() {
		return versionid;
	}
	
	public double getPrice()
	{
		return price;
	}
}
