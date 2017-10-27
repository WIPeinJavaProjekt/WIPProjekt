package classes;

import java.util.ArrayList;

public class ShoppingCart {
	ArrayList<ShoppingCartPosition> cart = new ArrayList<ShoppingCartPosition>();
	
	public ShoppingCart() {
		
	}
	
	public ShoppingCart(ArrayList<ShoppingCartPosition> cart) {
		this.cart = cart;
	}
	
	public double GetShoppingCartPrice() {
		double amount=0;
		
		for(ShoppingCartPosition scp: cart)
			amount+=scp.GetPositionPrice();
		
		return amount;
	}
	
}
