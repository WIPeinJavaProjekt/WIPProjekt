package classes;

import java.util.*;

public class Order {

	public Adress adress;
	public ShoppingCart shoppingCart;
	
	public Order(Adress a, ShoppingCart scp) {
		adress = a;
		shoppingCart = scp;
	}
}
