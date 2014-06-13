package io;

import graphics.Sprite;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import world.Block;

public class ResourceManager
{
	private HashMap<String, Sprite> sprites;
	private HashMap<String, Block> blocks;
	
	private int numLoaded;
	
	private FileReader reader;
	
	public ResourceManager() throws IOException
	{
		reader = new FileReader();
		
		numLoaded = 0;
		
		sprites = new HashMap<String, Sprite>();
		blocks = new HashMap<String, Block>();
	}
	
	public void loadSprites() throws IOException
	{
		reader.getSprites(new File(FileReader.drive + "sprites"), sprites);
	}
	
	public void loadBlocks()
	{
		blocks.put("dirt", new Block("dirt", 4));
	}
	
	public Sprite getSprite(String key)
	{
		return new Sprite(sprites.get(key));
		
	}
	
	public Block getBlock(String key)
	{
		return blocks.get(key);
	}
	
	public void put(String key, Sprite pic)
	{
		sprites.put(key, pic);
	}
	
	
	public void loadSpriteFromFile(String filename) throws IOException
	{
		String key = filename.split(".")[0];
		
		sprites.put(key, new Sprite(filename));
	}
}
