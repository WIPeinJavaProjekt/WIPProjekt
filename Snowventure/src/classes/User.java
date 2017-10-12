package WIP_classes;

public class User {

	public String username;
	public String password;
	public String name;
	public String surename;
	public String location;
	public String houseno;
	public String postcode;
	public String email;
	public int usertype;
	
	public User(String username, String password, String name, String surename, String location, String houseno, String postcode, String email, int usertype)
	{
		this.username = username;
		this.email = email;
		this.houseno = houseno;
		this.location = location;
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
	
}
