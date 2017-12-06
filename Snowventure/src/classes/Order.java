package classes;

import java.util.ArrayList;

/**
 * Beschreibung: Fachklasse für die Abbildung einer Bestellung
 * @author Ansprechpartner Fabian Meise
 *
 */
public class Order {

	public int orid;
	public int ulid;
	public Adress adress;
	public ShoppingCart shoppingCart;
	public ArrayList<OrderStatus> statuscycle;
	public String name;
	public String surname;
	public String email;
	
	/**
	 * Konstruktor
	 * @param a Lieferadresse
	 * @param scp Warenkorb
	 * @param orid Bestelltid
	 * @param name Nachname
	 * @param surname Vorname
	 * @param email Lieferemail
	 * @param ulid zugehöriger Loginaccountid
	 */
	public Order(Adress a, ShoppingCart scp, int orid, String name, String surname, String email, int ulid) {
		adress = a;
		shoppingCart = scp;
		statuscycle= new  ArrayList<OrderStatus>();
		//statuscycle.add(new OrderStatus(new Date(),"Posted"));
		this.orid = orid;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.ulid = ulid;
	}
	
	/**
	 * Konstruktor
	 * @param a Lieferadresse
	 * @param scp Warenkorb
	 * @param statuscycle Statushistorie
	 * @param orid Bestelltid
	 * @param name Nachname
	 * @param surname Vorname
	 * @param email Lieferemail
	 * @param ulid zugehöriger Loginaccountid
	 */
	public Order (Adress a, ShoppingCart scp, ArrayList<OrderStatus> statuscycle, int orid, String name, String surname, String email, int ulid) {
		adress = a;
		shoppingCart = scp;
		this.statuscycle = statuscycle;
		this.statuscycle= new  ArrayList<OrderStatus>(statuscycle);
		this.orid = orid;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.ulid = ulid;
	}
	
	public ArrayList<OrderStatus> getStatuscycle()
	{
		return statuscycle;
	}
	
	public ShoppingCart getShoppingCart()
	{
		return shoppingCart;
	}
	
	public Adress getAdress() 
	{
		return adress;
	}
	
	public int getOrid()
	{
		return orid;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getSurname()
	{
		return surname;
	}
	
	public String getEmail()
	{
		return email;
	}

	public int getUlid()
	{
		return ulid;
	}

}
