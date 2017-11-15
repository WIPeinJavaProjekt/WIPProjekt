package classes;

import java.util.ArrayList;

public class ShoppingCart {
	public ArrayList<ShoppingCartPosition> cartPositions;
	
	public ShoppingCart() {
		cartPositions = new ArrayList<ShoppingCartPosition>();
	}
	
	public ShoppingCart(ArrayList<ShoppingCartPosition> cart) {
		this.cartPositions = new ArrayList<ShoppingCartPosition>(cart);
	}
	
	public ShoppingCart(ShoppingCart scp)
	{
		this.cartPositions = new ArrayList<ShoppingCartPosition>(scp.cartPositions);
	}	
	
	public String GetShoppingCartPrice() {
		double amount=0;
		
		for(ShoppingCartPosition scp: cartPositions)
			amount+=scp.GetPositionPrice();
				
		return String.format( "%.2f", amount );
	}
	
	public ArrayList<ShoppingCartPosition> getCartPositions()
	{
		return cartPositions;
	}
	
	public int GetArticleCount()
	{
		int count = 0;
		for(ShoppingCartPosition scp : cartPositions)
		{
			count += scp.amount;
		}
		return count;
	}
	
	public int GetCountShoppingCartPosition()
	{
		return cartPositions.size();
	}
	
}
