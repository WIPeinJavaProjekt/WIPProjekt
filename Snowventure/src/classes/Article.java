package classes;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import services.ArticleSizesService;
import services.StockService;

/**
 * Beschreibung: Fachklasse für die Abbildung eines Artikels
 * 
 * @author Ansprechpartner Fabian Meise
 *
 */
public class Article {

	public int ID;
	public String name;
	public String description;
	public String manufacturer;
	public int acid;
	public ArrayList<ArticleVersion> versions;
	private int selectedversion;
	public String gender;

	/**
	 * Leerer Konstruktor
	 */
	public Article() {
	}

	/**
	 * Konstruktor
	 * 
	 * @param ID
	 *            Artikelid
	 */
	public Article(int ID) {
		this.ID = ID;
		selectedversion = 0;
	}

	/**
	 * Konstruktor
	 * 
	 * @param ID
	 *            Artikelid
	 * @param name
	 *            Artikelname
	 * @param description
	 *            Artikelbeschreibung
	 * @param acid
	 *            Artikelkategorieid
	 * @param manufacturer
	 *            Hersteller
	 * @param gender
	 *            Geschlecht
	 */
	public Article(int ID, String name, String description, int acid, String manufacturer, String gender) {
		this.ID = ID;
		this.name = name;
		this.description = description;
		versions = new ArrayList<ArticleVersion>();
		selectedversion = 0;
		this.acid = acid;
		this.manufacturer = manufacturer;
		this.gender = gender;
	}

	/**
	 * Konstruktor
	 * 
	 * @param name
	 *            Artikelname
	 * @param description
	 *            Artikelbeschreibung
	 */
	public Article(String name, String description) {
		this.name = name;
		this.description = description;
		versions = new ArrayList<ArticleVersion>();
		selectedversion = 0;
	}

	/**
	 * Konstruktor
	 * 
	 * @param a
	 *            ein anderer Artikel
	 */
	public Article(Article a) {
		this.ID = a.ID;
		this.name = a.name;
		this.description = a.description;
		this.versions = new ArrayList<ArticleVersion>(a.versions);
		this.selectedversion = a.selectedversion;
	}

	public ArrayList<ArticleVersion> getAllVersions() {
		return this.versions;
	}

	public int getSelectedVersion() {
		return selectedversion;
	}

	public ArticleVersion getSelectedArticleVersion() {
		ArticleVersion av = this.getAllVersions().get(getSelectedVersion());
		return av;
	}
	
	public int getAcid() {
		return this.acid;
	}

	public void setSelectedVersion(int s) {
		if (s >= 0 && s < versions.size())
			selectedversion = s;
	}

	public double getPrice() {
		return versions.get(selectedversion).price;
	}

	/**
	 * Methode um Preis in dotted Formatierung anzuzeigen
	 * 
	 * @return Formatierten Preis
	 */
	public String getPriceFormatted() {
		DecimalFormat f = new DecimalFormat("#.00");
		return f.format(getPrice()).replace(".", ",");
	}

	public ArrayList<String> getSize() {
		return versions.get(selectedversion).sizes;
	}

	public ArrayList<String> getAllSizesFromArticle() {
		try {
			return ArticleSizesService.GetAllSizesFromArticle(this.ID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getProperty() {
		return versions.get(selectedversion).property;
	}

	public String getPropertyValue() {
		return versions.get(selectedversion).propertyvalue;
	}

	public ArrayList<ArticleColor> getColor() {
		return versions.get(selectedversion).colors;
	}

	public String getId() {
		return Integer.toString(this.ID);
	}

	public String getName() {
		return this.name;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public String getDescription() {
		return this.description;
	}

	/**
	 * Erhalte Artikelversion anhand einer Versionsid
	 * 
	 * @param inputId
	 *            Versionsid
	 * @return Artikelversion
	 */
	public ArticleVersion getAvByVersionId(int inputId) {
		for (ArticleVersion av : versions) {
			if (inputId == av.versionid) {
				return av;
			}
		}
		return null;
	}

	public String getStock(String size) {
		int stock;
		try {
			stock = StockService.GetStock(this.getAllVersions().get(this.getSelectedVersion()), size);
			return Integer.toString(stock);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "n.V.";
	}
}
