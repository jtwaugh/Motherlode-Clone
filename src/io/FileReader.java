package io;

import graphics.Animation;
import graphics.Sprite;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import window.Game;
import world.Block;

public class FileReader 
{
	private FileInputStream fStream;
	private DataInputStream inStream;
	private BufferedReader reader;
	
	public static final String drive = "resource/";
	
	private boolean isSetUp;
	
	public FileReader()
	{
		isSetUp = false;
	}
	
	public FileReader (String filename) throws FileNotFoundException
	{
		setUp(filename);
	}
	
	private void setUp(String filename) throws FileNotFoundException
	{
		// Open the file that is the first 
		fStream = new FileInputStream(filename);
		
		// Get the object of DataInputStream
		inStream = new DataInputStream(fStream);
		reader = new BufferedReader(new InputStreamReader(inStream));
	}
	
	private String getLine() throws IOException
	{
		String strLine = new String();
		strLine = reader.readLine();
		if (strLine != null)
		{
			return strLine;
		}
		else
		{
			return null;
		}
  	}
	
	private void parseList(String filename, ArrayList<String> ret, String url) throws IOException
	{
		setUp(filename);
		
		String myDrive = drive + url;
		String tempStr = getLine();
		String tempDir = "";
		String tempFile;
		
		while (tempStr != null)
		{
			if (tempStr.contains(":"))
			// Directory
			{
				tempDir = tempStr;
				tempDir = tempDir.replaceAll(":", "/");
			}
			else if (tempStr.substring(0, 1).equals("-"))
			// Filename
			{
				
				tempStr = tempStr.replaceAll("-", "");
				tempFile = myDrive + tempDir + tempStr;
				
				tempStr = tempStr.split("\\.")[0];
				
				ret.add(tempFile);
			}
			
			tempStr = getLine();
		}
	}
	
	public void parseStringList (String filename, ArrayList<String> ret) throws IOException
	{
		setUp(filename);
		
		String temp = getLine();
		
		while (temp != null)
		{
			ret.add(temp);
			temp = getLine();
		}
	}
	
	public static Color RGBA(int x, int y, BufferedImage image)
	{
		int argb =  image.getRGB(x,y); 
		int b = (argb)&0xFF;
		int g = (argb>>8)&0xFF;
		int r = (argb>>16)&0xFF;
		int a = (argb>>24)&0xFF;
		
		int[] clr = new int[4];
		clr[0] = r;
		clr[1] = g;
		clr[2] = b;
		clr[3] = a;
		
		return new Color(clr[0], clr[1], clr[2], clr[3]);
	}
	
	public HashMap<String, Animation> parseSpriteAnims(BufferedImage sheet, String fileguide) throws IOException
	{
		setUp(fileguide);
		
		String[] temp = getLine().split("x");
		
		int sizeX = Integer.parseInt(temp[0]);
		int sizeY = Integer.parseInt(temp[1]);
		
		int picX = sheet.getWidth()/sizeX;
		int picY = sheet.getHeight()/sizeY;
		
		BufferedImage[] pics = new BufferedImage[sizeX*sizeY];
		
		for (int y = 0; y < sizeY; y++)
		{
			for (int x = 0; x < sizeX; x++)
			{
				pics[x + sizeX*y] = sheet.getSubimage(x * picX, y * picY, picX, picY);
			}
		}
		
		HashMap<String, Animation> ret = new HashMap<String, Animation>();
		
		String myStr = getLine();
		
		while (myStr != null)
		{
			temp = myStr.replace(" ", "").split("=");
			int start = Integer.parseInt(temp[0].split("-")[0]);
			int end = Integer.parseInt(temp[0].split("-")[1]);
			
			String name = temp[1].split(",")[0];
			int frameDelay = Integer.parseInt(temp[1].split(",")[1]);
			
			BufferedImage[] ra = new BufferedImage[(end-start)*frameDelay];
			
			for (int q = 0; q < end-start; q++)
			{
				for (int s = 0; s < frameDelay; s++)
				{
					ra[q*frameDelay + s] = pics[start+q];
				}
			}
			
			ret.put(name, new Animation(ra));
			
			myStr = getLine();
		}
		return ret;
	}
	
	public void getSprites(File folder, HashMap<String, Sprite> ret) throws IOException
	// IMPORTANT: Key is the filename without extension
	{		
	    for (File fileEntry : folder.listFiles()) 
	    {
	        if (fileEntry.isDirectory())
	        {
	            getSprites(fileEntry, ret);
	        }
	        else
	        {
	        	String str = fileEntry.getName().split("\\.")[0];
				ret.put(str, new Sprite(fileEntry));
				// TODO support animation files
	        }
	    }
	}
}