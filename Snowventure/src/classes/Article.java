package classes;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Article {

	public int ID;
	public String name;
	public String description;	
	public ArrayList<ArticleVersion> versions;
	private int selectedversion;
	public ArrayList<ArticlePicture> pictures = new ArrayList<ArticlePicture>();
	
	
	public Article(){}
	
	public Article(int ID)
	{
		this.ID = ID;
		selectedversion = 0;
	}
	
	public Article(int ID, String name, String description)
	{
		this.ID = ID;
		this.name = name;
		this.description = description;
		versions = new ArrayList<ArticleVersion>();
		selectedversion = 0;
	}
	
	public Article(String name, String description)
	{
		this.name = name;
		this.description = description;
		versions = new ArrayList<ArticleVersion>();
		selectedversion = 0;
	}
	
	public Article(Article a)
	{
		this.ID = a.ID;
		this.name = a.name;
		this.description = a.description;
		this.versions = new ArrayList<ArticleVersion>(a.versions);
		this.selectedversion = a.selectedversion;
	}
	
	public ArrayList<ArticleVersion> GetAllVersions() {
		return this.versions;
	}
	
	
	public int GetSelectedVersion() {
		return selectedversion;
	}
	
	public void SetSelectedVersion(int s) {
		if(s>= 0&& s< versions.size())
			selectedversion = s;
	}
	
	public double GetPrice() {
		return versions.get(selectedversion).price;
	}
	
	public String GetPriceFormatted() {
		DecimalFormat f = new DecimalFormat("#.00"); 
		return f.format(GetPrice()).replace(".", ",");
	}
	
	public String GetSize() {
		return versions.get(selectedversion).size;
	}
	
	public String GetProperty() {
		return versions.get(selectedversion).property;
	}
	
	public String GetPropertyValue() {
		return versions.get(selectedversion).propertyvalue;
	}
	
	public String GetColor() {
		return versions.get(selectedversion).color;
	}
	
	public String GetId() {
		return Integer.toString(this.ID);
	}
	
	public String GetName() {
		return this.name;
	}
	
	public String GetDescription() {
		return this.description;
	}
	
	public ArticlePicture getArticleHeadPicture()
	{
		ArticlePicture apic = this.pictures.get(0);	
		return apic;
	}
}
