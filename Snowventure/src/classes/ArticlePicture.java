package classes;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ArticlePicture {
	public Image image  = null;
	public String name;
	public byte[] content;
	public int imageId;
	
	public ArticlePicture(String name, InputStream is) throws IOException {
		this.name = name;
		this.image = ImageIO.read(is);
	}
	
	public ArticlePicture(String name, InputStream is, byte[] content, int imageId) throws IOException {
		this.name = name;
		this.image = ImageIO.read(is);
		this.content = content;
		this.imageId = imageId;
	}
	
	public ArticlePicture(String name, File f) throws IOException
	{
		this.name = name;
		this.image = ImageIO.read(f);
	}

	public String getName() {
		return this.name;
	}

	public String GetImageId() {
		return Integer.toString(this.imageId);
	}
	
}
