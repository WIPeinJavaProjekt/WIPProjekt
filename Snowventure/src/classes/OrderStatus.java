package classes;
import java.util.Date;


public class OrderStatus {
	public Date statusdate;
	public String description;
	
	public OrderStatus(Date statusdate, String description) {
		this.statusdate = statusdate;
		this.description = description;
	}
}