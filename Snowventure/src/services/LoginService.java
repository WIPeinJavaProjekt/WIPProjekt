package services;

import java.sql.SQLException;

import classes.User;

public class LoginService {

	public static boolean authenticate(String userId, String password) {
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
