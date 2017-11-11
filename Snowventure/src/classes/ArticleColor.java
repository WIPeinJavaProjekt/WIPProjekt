package classes;

public class ArticleColor {
	public int acolid;
	public String color;
	public String hexcode;
	
	public ArticleColor(int acolid, String color, String hexcode)
	{
		this.acolid = acolid;
		this.color = color;
		this.hexcode = hexcode;
	}
	
	public int GetAcolid() {
		return this.acolid;
	}
	
	public String GetColorName() {
		return this.color;
	}
	
	public String GetHexcode() {
		return this.hexcode;
	}
}
