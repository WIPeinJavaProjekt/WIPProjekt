package classes;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import services.ArticleSizesService;

public class Article {

	public int ID;
	public String name;
	public String description;
	public String manufacturer;
	public int acid =1;
	public ArrayList<ArticleVersion> versions;
	private int selectedversion;
	public String gender;
	
	
	public Article(){}
	
	public Article(int ID)
	{
		this.ID = ID;
		selectedversion = 0;
	}
	
	public Article(int ID, String name, String description, int acid, String manufacturer, String gender)
	{
		this.ID = ID;
		this.name = name;
		this.description = description;
		versions = new ArrayList<ArticleVersion>();
		selectedversion = 0;
		this.acid = acid;
		this.manufacturer = manufacturer;
		this.gender = gender;
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
	
	public ArrayList<String> GetSize() {
		return versions.get(selectedversion).sizes;
	}
	
	public ArrayList<String> getAllSizesFromArticle(){
		try {
			return ArticleSizesService.GetAllSizesFromArticle(this.ID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String GetProperty() {
		return versions.get(selectedversion).property;
	}
	
	public String GetPropertyValue() {
		return versions.get(selectedversion).propertyvalue;
	}
	
	public ArrayList<ArticleColor> GetColor() {
		return versions.get(selectedversion).colors;
	}
	
	public String GetId() {
		return Integer.toString(this.ID);
	}
	
	public String GetName() {
		return this.name;
	}
	
	public String GetManufacturer() {
		return this.manufacturer;
	}
	
	public String GetDescription() {
		return this.description;
	}
	
	public ArticleVersion getAvByVersionId(int inputId) {
		for(ArticleVersion av: versions) {
			if(inputId == av.versionid) {
				return av;
			}
		}
		return null;
	}
	
}
