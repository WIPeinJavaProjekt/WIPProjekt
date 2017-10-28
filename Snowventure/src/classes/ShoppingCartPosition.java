package classes;

public class ShoppingCartPosition {
	public Article article;
	public int amount;
	
	public ShoppingCartPosition(Article a, int amount) {
		this.article = a;
		this.amount = amount;
	}
	
	public double GetPositionPrice() {
		return article.versions.get(article.GetSelectedVersion()).price * amount;
	}
}
