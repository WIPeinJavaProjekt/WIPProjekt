package classes;

import java.util.ArrayList;

public class ArticleVersion extends Article{
	public int versionid;
	public String property;
	public String propertyvalue;
	public String color;
	public double price;
	public ArrayList<String> sizes;
	
	public ArticleVersion() {
		sizes = new ArrayList<String>();
	};
	
	public ArticleVersion(int versionid, String property,String propertyvalue, double price, Article a, String color, ArrayList<String> size)
	{
		super(a);
		this.versionid = versionid;
		this.property = property;
		this.propertyvalue = propertyvalue;
		this.price = price;
		this.color = color;
		this.sizes = new ArrayList<String>(size);
		
	}
	
	public int GetAvId() {
		return versionid;
	}
}
