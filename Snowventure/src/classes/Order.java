package WIP_classes;

import java.util.*;

public class Order {

	public String orderID;
	public User user;
	public List<Article> articles;
	public Date orderDate;	
	
	public Order()
	{}
	
	public Order(String orderID, User user, List<Article> articles, Date date)
	{
		this.orderID = orderID;
		this.user = user;
		this.articles = articles;
		this.orderDate = date;
	}
	
	public void get(String orderID)
	{}
	
	public boolean insert()
	{
		return true;
	}
	
	public boolean update()
	{
		return true;
	}
}
