package classes;

import java.util.*;

public class Users {

	public static List<User> get(String username)
	{	
		List<User> lUsers = new ArrayList<User>();		

		if(username == null)
		{}
		
		User user = new User("test", "1234", "testname", "testsurename", "loc", "4a", "23923", "test@gmail.com", 1);
		lUsers.add(user);		
		
		return lUsers;	
	}
}
