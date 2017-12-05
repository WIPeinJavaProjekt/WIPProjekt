package classes;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Beschreibung: Fachklasse für die Abbildung eines Artikelbildes
 * 
 * @author Ansprechpartner Fabian Meise
 *
 */
public class ArticlePicture {
	public Image image = null;
	public String name;
	public byte[] content;
	public int imageId;

	/**
	 * Konstruktor
	 * 
	 * @param name
	 *            Bildname
	 * @param is
	 *            Inputstream des Bilds
	 * @throws IOException
	 *             Fehler beim Inputstream
	 */
	public ArticlePicture(String name, InputStream is) throws IOException {
		this.name = name;
		this.image = ImageIO.read(is);
	}

	/**
	 * Konstruktor
	 * 
	 * @param name
	 *            Bildname
	 * @param is
	 *            Inputstream des Bilds
	 * @param content
	 *            Inhalt von Bild
	 * @param imageId
	 *            Bildid
	 * @throws IOException
	 */
	public ArticlePicture(String name, InputStream is, byte[] content, int imageId) throws IOException {
		this.name = name;
		this.image = ImageIO.read(is);
		this.content = content;
		this.imageId = imageId;
	}

	/**
	 * Konstruktor
	 * 
	 * @param name
	 *            Bildname
	 * @param f
	 *            Bilddatei
	 * @throws IOException
	 */
	public ArticlePicture(String name, File f) throws IOException {
		this.name = name;
		this.image = ImageIO.read(f);
	}

	public String getName() {
		return this.name;
	}

	public String getImageId() {
		return Integer.toString(this.imageId);
	}

}
