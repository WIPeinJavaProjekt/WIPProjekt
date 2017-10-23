package classes;

public class User {

	public int ulid;
	public int utid;
	public int udid;
	public Safetyquestion squestion;
	public String username;
	public String password;
	public String name;
	public String surname;
	public String email;
	public Adress adress;
	public int usertype;
	
	public User()
	{}
	
	public User(String username, String password, String name, String surname, Adress adress, String email, int usertype)
	{
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.adress = adress;
		this.usertype = usertype;
	}
	
	public User(Safetyquestion question, String username, String password, String name, String surname, Adress adress, String email, int usertype)
	{
		this.squestion = question;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.adress = adress;
		this.usertype = usertype;
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
		return Integer.toString(this.usertype);
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
}
