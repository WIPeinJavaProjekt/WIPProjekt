package classes;

import java.util.ArrayList;

public class ArticleVersion extends Article{
	public int versionid;
	public String property;
	public String propertyvalue;
	public double price;
	public ArrayList<String> sizes;
	public ArrayList<ArticleColor> colors;
	public ArrayList<ArticlePicture> pictures = new ArrayList<ArticlePicture>();

	
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
	
	public ArticlePicture getArticleHeadPicture()
	{
		if(this.pictures.size()>0) {
			ArticlePicture apic = this.pictures.get(0);	
			return apic;
		} return null;
	}
	
	public ArrayList<ArticlePicture> getArticlesPictures()
	{
		return this.pictures;
	}
	
	public String getColorsAsString() {
		String output = "";
		for(int i=0; i < this.colors.size(); i++) {
			output += this.colors.get(i).GetColorName();
			if(i<this.colors.size()-1) {
				output += " / ";
			}
		}
		return output;
	}
	
	public ArrayList<ArticleColor> getColors() {
		return this.colors;
	}
	
	public ArrayList<String> getColorNames(){
		ArrayList<String> colorStrings = new ArrayList<String>();
		for(ArticleColor ac: this.colors) {
			colorStrings.add(ac.GetColorName());
		}
		return colorStrings;
	}
}
