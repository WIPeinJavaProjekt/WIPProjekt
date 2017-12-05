package classes;

import java.util.Date;

/**
 * Beschreibung: Fachklasse für die Abbildung eines Bestellstatus
 * 
 * @author Ansprechpartner Fabian Meise
 *
 */
public class OrderStatus {
	public Date statusdate;
	public String description;

	/**
	 * Bestellstatus
	 * 
	 * @param statusdate
	 *            Statusdatum
	 * @param description
	 *            Statusbeschreibung
	 */
	public OrderStatus(Date statusdate, String description) {
		this.statusdate = statusdate;
		this.description = description;
	}

	public Date getStatusdate() {
		return this.statusdate;
	}

	public String getDescription() {
		return this.description;
	}
}
