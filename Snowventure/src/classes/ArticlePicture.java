package classes;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ArticlePicture {
	public Image image  = null;
	public String name;
	
	public ArticlePicture(String name, InputStream is) throws IOException {
		this.name = name;
		this.image = ImageIO.read(is);
	}
	
	public ArticlePicture(String name, File f) throws IOException
	{
		this.name = name;
		this.image = ImageIO.read(f);
	}
	
}
