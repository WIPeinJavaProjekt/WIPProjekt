package classes;

public class Adress {
	
	public String location;
	public String houseno;
	public String postcode;
	public String street;
	
	public Adress() {
		
	}
	
	public Adress(String location, String houseno, String postcode, String street) {
		this.location = location;
		this.houseno = houseno;
		this.postcode = postcode;
		this.street = street;	
	}

	public String getLocation()
	{
		return this.location;
	}
	
	public String getStreet()
	{
		return this.street;
	}

	public String getPostcode()
	{
		return this.postcode;
	}
	
	public String getHouseno()
	{
		return this.houseno;
	}
	
}
