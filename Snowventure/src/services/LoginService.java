package services;

import java.io.IOException;
import java.sql.SQLException;

import classes.User;

public class LoginService {

	/**
	 * Checks whether user-credentials are correct (Comparing with the database)
	 * @param userId
	 * @param password
	 * @return boolean value
	 * @throws IOException 
	 */
	public static boolean authenticate(String userId, String password) throws IOException {
		if(password != null && !password.equals("")){
			User user = new User();
			try {
				user = UserService.GetUser(userId);
				if(user.password.equals(password)) {
					return true;
				}
				System.out.println("Wrong  PW");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
}
