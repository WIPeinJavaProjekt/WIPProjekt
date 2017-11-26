package classes;

/**
 * Beschreibung: Fachklasse für die Abbildung einer Artikelkategorie
 * @author Ansprechpartner Fabian Meise
 *
 */
public class Categorie {
	private int acid;
	private String name;
	
	/**
	 * Konstruktor
	 * @param acid Kategorieid
	 * @param name Kategoriename
	 */
	public Categorie (int acid, String name) {
		this.acid = acid;
		this.name = name;
	}
	
	public int getACID()
	{
		return acid;
	}
	
	public String getName()
	{
		return name;
	}
}
