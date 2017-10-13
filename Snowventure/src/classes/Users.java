package classes;

import java.util.*;

public class Users {

	public static List<User> get(String username)
	{	
		List<User> lUsers = new ArrayList<User>();		

		if(username == null)
		{}
		
		Adress adress = new Adress("Paderborn", "1", "12345", "Hauptstraße");
		User user = new User("TestUser", "test", "Test", "User", adress, "Test.User@test.de", 1);
		lUsers.add(user);		
		
		return lUsers;	
	}
}
