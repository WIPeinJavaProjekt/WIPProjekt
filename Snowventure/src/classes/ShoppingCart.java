package classes;

import java.util.ArrayList;

public class ShoppingCart {
	public ArrayList<ShoppingCartPosition> cart;
	
	public ShoppingCart() {
		cart = new ArrayList<ShoppingCartPosition>();
	}
	
	public ShoppingCart(ArrayList<ShoppingCartPosition> cart) {
		this.cart = new ArrayList<ShoppingCartPosition>(cart);
	}
	
	public ShoppingCart(ShoppingCart scp)
	{
		this.cart = new ArrayList<ShoppingCartPosition>(scp.cart);
	}
	
	
	
	public double GetShoppingCartPrice() {
		double amount=0;
		
		for(ShoppingCartPosition scp: cart)
			amount+=scp.GetPositionPrice();
		
		return amount;
	}
	
	public ArrayList<ShoppingCartPosition> getCart()
	{
		return cart;
	}
	
}
