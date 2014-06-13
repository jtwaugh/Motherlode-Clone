package state;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import math.BlockCollision;
import entity.Player;
import graphics.Backdrop;
import gui.StatBar;
import window.Game;
import world.Block;

public class World extends State
{
	public static final float GRAVITY = .6f;
	protected static int BLOCKSIZE = 32;
	protected ArrayList<ArrayList<Block>> blocks;	// procedurally generated
	
	protected int xSize;
	protected int ySize;
	
	protected Backdrop bg;
	
	protected Player steve;
	
	protected Collision coll;
	
	// GUI
	
	protected StatBar hpBar;
	protected StatBar fuelBar;
	
	public World(Player p, int width, int height, String bgName) throws IOException
	{
		super();
		steve = p;
		blocks = new ArrayList<ArrayList<Block>>();
		coll = new Collision();
		
		xSize = width;
		ySize = height;
		
		bg = new Backdrop(bgName);
		
		for (int y = 0; y < 8; y++)
		{
			blocks.add(new ArrayList<Block>());
			
			for (int x = 0; x < Math.ceil((float)xSize / BLOCKSIZE)+2; x++)
			{
				blocks.get(y).add(null);
			}
		}
		
		for (int y = 8; y < 50; y++)
		{
			blocks.add(new ArrayList<Block>());
			
			for (int x = 0; x < Math.ceil((float)xSize / BLOCKSIZE)+2; x++)
			{
				blocks.get(y).add((Math.random() > .5) ? null : new Block(new Point(x * BLOCKSIZE, y * BLOCKSIZE), Game.RESOURCE.getBlock("dirt")));
			}
		}
		
		blocks.get(0).set(10, null);
		
		steve.setPosition(320, 0);
		
		ret = "World";
		
		hpBar = new StatBar(10, 10, "hpbarfull", "hpbarcap", steve.getHull());
		fuelBar = new StatBar(10, 26, "fuelbarfull", "fuelbarcap", steve.getFuel());
	}
	
	public String loop(Graphics2D BGDraw, Graphics2D gameDraw, Graphics2D infDraw)
	{			
		logic();
		
		bg.draw(BGDraw);
		
		renderGame(gameDraw);

		renderEffects(gameDraw);

		renderInterface(infDraw);
		
		return ret;
	}
	
	protected void logic()
	{
		respondToPlayerInput();
		
		actEntities();
		
		checkCollisions();
		
		moveEntities();
		
		updateInterface();
	}
	
	protected void actEntities()
	{
		steve.update();
		
		if (steve.getFuel() <= 0)
		{
			ret = "You are stranded from fuel loss.";
		}
		else if (steve.getHull() <= 0)
		{
			ret = "You have been destroyed.";
		}
	}
	
	protected void moveEntities()
	{
		steve.move();
	}
	
	protected void checkCollisions()
	{
		coll.entityBlockCollision();
		
		Point2D.Float v = steve.getVelocity();
		Point p = steve.getPosition();
		
		if (p.x + v.x + steve.getSprite().getWidth() > xSize)
		{
			steve.setPosition(xSize - steve.getSprite().getWidth() - 1, p.y);
		}
		
		if (p.x + v.x < 0)
		{
			steve.setPosition(0, p.y);
		}
	}
	
	protected void renderGame(Graphics2D g)
	{
		renderBlocks(g);
		renderEntities(g);
	}
	
	protected void renderEntities(Graphics2D g)
	{
		steve.render(g);
	}
	
	protected void renderBlocks(Graphics2D g)
	{
		for (int y = 0; y < blocks.size(); y++)
		{
			for (int x = 0; x < blocks.get(0).size(); x++)
			{
				Block b = blocks.get(y).get(x);
				
				if (b != null)
				{
					b.getSprite().draw(g);
				}
				
			}
		}
	}
	
	protected void renderEffects(Graphics2D g)
	{
		
	}
	
	protected void renderInterface(Graphics2D g)
	{
		hpBar.draw(g);
		fuelBar.draw(g);
	}
	
	protected void updateInterface()
	{
		hpBar.setCurrent(steve.getHull());
		fuelBar.setCurrent(steve.getFuel());
	}
	
	protected void respondToPlayerInput()
	{
		steve.reactToKeys();
	}
	
	public void keyPressHandle(KeyEvent e)
	{
		steve.keyPressHandle(e);
	}
	
	public void keyReleaseHandle(KeyEvent e)
	{
		steve.keyReleaseHandle(e);
	}
	
	public void mousePressHandle(MouseEvent e)
	{
		steve.mousePressHandle(e);
	}
	
	public void mouseReleaseHandle(MouseEvent e)
	{
		steve.mouseReleaseHandle(e);
	}
	
	private class Collision
	{
		public BlockCollision entityBlockCollision()
		{
			BlockCollision myColl = null;
		
			int steveBlockX = (steve.getPosition().x + BLOCKSIZE/2) / BLOCKSIZE;
			int steveBlockY = (steve.getPosition().y + BLOCKSIZE/2) / BLOCKSIZE;
			
			int qx;
			int qy;
			
			int yAllowance = 3;
			
			if (steve.getVelocity().y >= BLOCKSIZE/2)
			{
				yAllowance += (steve.getVelocity().y/BLOCKSIZE);
			}
			
			boolean steveCollided = false;
			
			for (int y = steveBlockY - 1; y <= steveBlockY + yAllowance; y++)
			{
				if (y < 0)
				{
					qy = 0;
				}
				else if (y >= ySize)
				{
					qy = ySize-1;
				}
				else 
				{
					qy = y;
				}
				
				for (int x = steveBlockX - 1; x <= steveBlockX + 1; x++)
				{
					
					if (x < 0)
					{
						qx = 0;
					}
					else if (x >= xSize)
					{
						qx = xSize-1;
					}
					else 
					{
						qx = x;
					}
					
					Block that = blocks.get(qy).get(qx);
					
					if (that != null)
					{
						myColl = BlockCollision.collBlockEntity(steve, that);
						if (steve.blockCollideHandle(myColl, that) || BlockCollision.isAtRestOn(steve, that))
						{
							steveCollided = true;
						}
					}
				}
			}
			
			if (steveCollided == false)
			{
				steve.setColliding(false);
			}
			
			return myColl;
		}
	}
}
