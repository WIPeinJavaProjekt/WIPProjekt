package classes;

public class ShoppingCartPosition {
	public ArticleVersion article;
	public int amount;
	
	public ShoppingCartPosition(ArticleVersion a, int amount) {
		this.article = a;
		this.amount = amount;
	}
	
	public double GetPositionPrice() {
		return article.price * amount;
	}
}
