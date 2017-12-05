package classes;

import java.util.ArrayList;

/**
 * Beschreibung: Fachklasse für die Abbildung eines Nutzers
 * 
 * @author Ansprechpartner Fabian Meise
 *
 */
public class User {

	public int ulid;
	public int utid;
	public int udid;
	public int techisactive;
	public int techisdeleted;
	public Safetyquestion squestion;
	public String username;
	public String password;
	public String name;
	public String surname;
	public String email;
	public String phone;
	public Adress adress;
	public ArrayList<Order> orders;
	public ShoppingCart shoppingcart;

	/**
	 * Leerer Konstruktor
	 */
	public User() {
	}

	/**
	 * Konstruktor
	 * 
	 * @param username
	 *            Nutzername
	 * @param password
	 *            Password
	 * @param name
	 *            Nachname
	 * @param surname
	 *            Vorname
	 * @param adress
	 *            Adresse
	 * @param email
	 * @param utid
	 *            Nutzertyp
	 */
	public User(String username, String password, String name, String surname, Adress adress, String email, int utid) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.adress = adress;
		this.utid = utid;
	}

	/**
	 * Konstruktor
	 * 
	 * @param question
	 *            Sicherheitsfrage
	 * @param username
	 *            Nutzername
	 * @param password
	 *            Password
	 * @param name
	 *            Nachname
	 * @param surname
	 *            Vorname
	 * @param adress
	 *            Adresse
	 * @param email
	 * @param phone
	 *            Telefonnummer
	 * @param utid
	 *            Nutzertyp
	 */
	public User(Safetyquestion question, String username, String password, String name, String surname, Adress adress,
			String email, String phone, int utid) {
		this.squestion = question;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.adress = adress;
		this.utid = utid;
		this.phone = phone;
	}

	/**
	 * Konstruktor
	 * 
	 * @param question
	 *            Sicherheitsfrage
	 * @param username
	 *            Nutzername
	 * @param password
	 *            Password
	 * @param name
	 *            Nachname
	 * @param surname
	 *            Vorname
	 * @param adress
	 *            Adresse
	 * @param email
	 * @param utid
	 *            Nutzertyp
	 * @param ulid
	 *            Login-ID
	 * @param TechIsActive
	 *            Account aktiv
	 * @param TechIsDeleted
	 *            Account deaktiviert
	 * @param phone
	 *            Telefonnummer
	 */
	public User(Safetyquestion question, String username, String password, String name, String surname, Adress adress,
			String email, int utid, int ulid, int TechIsActive, int TechIsDeleted, String phone) {
		this.squestion = question;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.adress = adress;
		this.utid = utid;
		this.ulid = ulid;
		this.techisactive = TechIsActive;
		this.techisdeleted = TechIsDeleted;
		this.phone = phone;
	}

	public static User get(String username, int usertype) {
		return null;
	}

	public boolean insert() {
		return true;
	}

	public boolean update() {
		return true;
	}

	public String getName() {
		return this.name;
	}

	public String getUsername() {
		return this.username;
	}

	public String getUtid() {
		return Integer.toString(this.utid);
	}

	public String getEmail() {
		return this.email;
	}

	public String getSurname() {
		return this.surname;
	}

	public Adress getAdress() {
		return this.adress;
	}

	public Safetyquestion getSquestion() {
		return this.squestion;
	}

	public ShoppingCart getShoppingcart() {
		return this.shoppingcart;
	}

	public int getTechisactive() {
		return this.techisactive;
	}

	public int getTechisdeleted() {
		return this.techisdeleted;
	}

	public String getPhone() {
		return this.phone;
	}
}
