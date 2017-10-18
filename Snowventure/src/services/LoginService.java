package services;

public class LoginService {

	public static boolean authenticate(String userId, String password) {
		if(password == null || password.equals(""))
		{
			return false;
		}
		return true;
	}
	
}
