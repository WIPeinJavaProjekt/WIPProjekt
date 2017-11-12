package classes;

public class ShoppingCartPosition {
	public Article article;
	public int amount;
	public String size;
	public ArticleColor color;
	
	public ShoppingCartPosition(Article a, int amount, String size, ArticleColor color) {
		this.article = a;
		this.amount = amount;
		this.size = size;
		this.color = color;
	}
	
	public double GetPositionPrice() {
		return article.versions.get(article.GetSelectedVersion()).price * amount;
	}
	
	public Article getArticle()
	{
		return article;
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public ArticleColor getColor()
	{
		return color;
	}
	
	public String getSize()
	{
		return size;
	}
}
