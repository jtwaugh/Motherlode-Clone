package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Backdrop
{
	private BufferedImage pic;
	int xSize;
	int ySize;
	
	public Backdrop(String filename) throws IOException
	{
		File file = new File("resource/sprites/" + filename + ".png");
		pic = ImageIO.read(file);
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(pic, 0, 0, null);
	}
}
