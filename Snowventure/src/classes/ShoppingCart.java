package classes;

import java.util.ArrayList;

/**
 * Beschreibung: Fachklasse für die Abbildung eines Warenkorbs
 * @author Ansprechpartner Fabian Meise
 *
 */
public class ShoppingCart {
	public ArrayList<ShoppingCartPosition> cartPositions;
	
	/**
	 * Leerer Konstruktor
	 */
	public ShoppingCart() {
		cartPositions = new ArrayList<ShoppingCartPosition>();
	}
	
	/**
	 * Konstruktor
	 * @param ArrayList<ShoppingCartPosition> ArrayList mit Warenkorbpositionen
	 */
	public ShoppingCart(ArrayList<ShoppingCartPosition> cart) {
		this.cartPositions = new ArrayList<ShoppingCartPosition>(cart);
	}
	
	/**
	 * Konstruktor
	 * @param scp bestehender Einkaufswagen
	 */
	public ShoppingCart(ShoppingCart scp)
	{
		this.cartPositions = new ArrayList<ShoppingCartPosition>(scp.cartPositions);
	}	
	
	/**
	 * Formatierten Gesamtpreis (Warenkorbwert) erhalten
	 * @return Gesamtpreis 
	 */
	public String getShoppingCartPrice() {
		double amount=0;
		
		for(ShoppingCartPosition scp: cartPositions)
			amount+=scp.getPositionPrice();
				
		return String.format( "%.2f", amount );
	}
	
	public ArrayList<ShoppingCartPosition> getCartPositions()
	{
		return cartPositions;
	}
	
	/**
	 * Summe aller Artikel im Warenkorb
	 * @return int Summe
	 */
	public int getArticleCount()
	{
		int count = 0;
		for(ShoppingCartPosition scp : cartPositions)
		{
			count += scp.amount;
		}
		return count;
	}
	
	public int getCountShoppingCartPosition()
	{
		return cartPositions.size();
	}
	
}
