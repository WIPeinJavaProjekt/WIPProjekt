package classes;

import java.util.ArrayList;

/**
 * Beschreibung: Fachklasse für die Abbildung einer Artikelversion
 * @author Ansprechpartner Fabian Meise
 *
 */
public class ArticleVersion extends Article{
	public int versionid;
	public String property;
	public String propertyvalue;
	public double price;
	public ArrayList<String> sizes;
	public ArrayList<ArticleColor> colors;
	public ArrayList<ArticlePicture> pictures = new ArrayList<ArticlePicture>();

	/**
	 * leerer Konstruktor
	 */
	public ArticleVersion() {
		sizes = new ArrayList<String>();
		colors = new ArrayList<ArticleColor>();
	};
	
	/**
	 * Konstruktor
	 * @param versionid Versionsid
	 * @param property frei belegbares Eigenschaftsfeld
	 * @param propertyvalue Wert für frei belegbares Eigenschaftsfeld
	 * @param price Preis
	 * @param a Artikel der Version
	 * @param size Größen
	 * @param colors Farben
	 */
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
	
	public int getAvId() {
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
	
	/**
	 * Erhalte alle Farben des Artikels /-Getrennt
	 * @return Alle Farben /-Getrennt
	 */
	public String getColorsAsString() {
		String output = "";
		for(int i=0; i < this.colors.size(); i++) {
			output += this.colors.get(i).getColorName();
			if(i<this.colors.size()-1) {
				output += " / ";
			}
		}
		return output;
	}
	
	public ArrayList<ArticleColor> getColors() {
		return this.colors;
	}
	
	/**
	 * Erhalte alle Farbnamen als String ArrayList
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getColorNames(){
		ArrayList<String> colorStrings = new ArrayList<String>();
		for(ArticleColor ac: this.colors) {
			colorStrings.add(ac.getColorName());
		}
		return colorStrings;
	}
}
