package classes;

import java.time.LocalDateTime;
import java.util.*;

public class Order {

	public Adress adress;
	public ShoppingCart shoppingCart;
	public ArrayList<OrderStatus> statuscycle;
	
	public Order(Adress a, ShoppingCart scp) {
		adress = a;
		shoppingCart = scp;
		statuscycle= new  ArrayList<OrderStatus>();
		statuscycle.add(new OrderStatus(new Date(),"Posted"));
	}
	
	public Order (Adress a, ShoppingCart scp, ArrayList<OrderStatus> statuscycle) {
		adress = a;
		shoppingCart = scp;
		this.statuscycle = statuscycle;
		this.statuscycle= new  ArrayList<OrderStatus>(statuscycle);
	}
}
