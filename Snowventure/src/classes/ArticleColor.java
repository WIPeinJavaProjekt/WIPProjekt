package classes;

/**
 * Beschreibung: Fachklasse für die Abbildung einer Artikel Farbe
 * 
 * @author Ansprechpartner Fabian Meise
 *
 */
public class ArticleColor {
	public int acolid;
	public String color;
	public String hexcode;

	/**
	 * Konstruktor
	 * 
	 * @param acolid
	 *            Farbid
	 * @param color
	 *            Name der Farbe
	 * @param hexcode
	 *            Hexwert
	 */
	public ArticleColor(int acolid, String color, String hexcode) {
		this.acolid = acolid;
		this.color = color;
		this.hexcode = hexcode;
	}

	public int getAcolid() {
		return this.acolid;
	}

	public String getColorName() {
		return this.color;
	}

	public String getHexcode() {
		return this.hexcode;
	}

	public String getBackgroundHexcode() {
		if (this.hexcode.equals("#000") || this.hexcode.equals("#00f")) {
			return "#fff";
		}
		return "#000";
	}
}
