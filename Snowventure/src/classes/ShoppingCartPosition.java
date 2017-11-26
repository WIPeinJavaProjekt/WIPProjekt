package classes;

/**
 * Beschreibung: Fachklasse für die Abbildung einer Warenkorbposition
 * @author Ansprechpartner Fabian Meise
 *
 */
public class ShoppingCartPosition {
	public Article article;
	public int amount;
	public String size;
	public ArticleColor color;
	
	/**
	 * Konstruktor
	 * @param a Artikel mit ausgewählter Version
	 * @param amount Anzahl
	 * @param size gewählte Größe
	 * @param color gewählte Farbe
	 */
	public ShoppingCartPosition(Article a, int amount, String size, ArticleColor color) {
		this.article = a;
		this.amount = amount;
		this.size = size;
		this.color = color;
	}
	
	/**
	 * Erhalte Gesamtpreis der Artikelposition
	 * @return Preis * Menge
	 */
	public double getPositionPrice() {
		return article.versions.get(article.getSelectedVersion()).price * amount;
	}
	
	public Article getArticle()
	{
		return article;
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public ArticleColor getColor()
	{
		return color;
	}
	
	public String getSize()
	{
		return size;
	}
}
