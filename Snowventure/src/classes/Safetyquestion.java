package classes;

public class Safetyquestion {
	public int sqid;
	private String question;
	private String answer;
	
	public Safetyquestion (int sqid, String question, String answer) {
		this.sqid = sqid;
		this.question = question;
		this.answer = answer;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String getAnswer()
	{
		return answer;
	}
}
