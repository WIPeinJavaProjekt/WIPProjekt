package classes;

import java.util.ArrayList;

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
	public Adress adress;
	public ArrayList<Order> orders;
	public ShoppingCart shoppingcart;
	
	public User()
	{}
	
	public User(String username, String password, String name, String surname, Adress adress, String email, int utid)
	{
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.adress = adress;
		this.utid = utid;
	}
	
	public User(Safetyquestion question, String username, String password, String name, String surname, Adress adress, String email, int utid)
	{
		this.squestion = question;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.adress = adress;
		this.utid = utid;
	}
	
	public User(Safetyquestion question, String username, String password, String name, String surname, Adress adress, String email, int utid, int ulid, int TechIsActive, int TechIsDeleted)
	{
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
	}

	
	public static User get(String username, int usertype)
	{	
		return null;
	}
	
	public boolean insert()
	{	
		return true;
	}
	
	public boolean update()
	{	
		return true;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public String getUtid()
	{
		return Integer.toString(this.utid);
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public String getSurname()
	{
		return this.surname;
	}
	
	public Adress getAdress()
	{
		return this.adress;
	}
	
	public Safetyquestion getSquestion()
	{
		return this.squestion;
	}
	
	public ShoppingCart getShoppingcart()
	{
		return this.shoppingcart;
	}
	
	public int getTechisactive()
	{
		return this.techisactive;
	}
	
	public int getTechisdeleted()
	{
		return this.techisdeleted;
	}
}
