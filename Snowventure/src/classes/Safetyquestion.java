package classes;

/**
 * Beschreibung: Fachklasse für die Abbildung einer Sicherheitsfrage für
 * Accounts
 * 
 * @author Ansprechpartner Fabian Meise
 *
 */
public class Safetyquestion {
	public int sqid;
	private String question;
	private String answer;

	/**
	 * leerer Konstruktor
	 */
	public Safetyquestion() {

	}

	/**
	 * Sicherheitsfrage
	 * 
	 * @param sqid
	 *            Sicherheitsfragenid
	 * @param question
	 *            Sicherheitsfrage
	 * @param answer
	 *            Antwort auf Sicherheitsfrage
	 */
	public Safetyquestion(int sqid, String question, String answer) {
		this.sqid = sqid;
		this.question = question;
		this.answer = answer;
	}

	public String getId() {
		return Integer.toString(sqid);
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}
}
