package classes;

public class Categorie {
	private int acid;
	private String name;
	
	public Categorie (int acid, String name) {
		this.acid = acid;
		this.name = name;
	}
	
	public int getACID()
	{
		return acid;
	}
	
	public String getName()
	{
		return name;
	}
}
