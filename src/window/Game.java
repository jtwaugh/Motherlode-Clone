package window;
import io.ResourceManager;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.*;

import entity.Player;
import state.GameOver;
import state.State;
import state.World;

public class Game extends Canvas implements ImageObserver
{	
	public static final int KEY_UP = KeyEvent.VK_W;
	public static final int KEY_LEFT = KeyEvent.VK_A;
	public static final int KEY_RIGHT = KeyEvent.VK_D;
	public static final int KEY_DOWN = KeyEvent.VK_S;
	public static final int KEY_ACTION = KeyEvent.VK_SPACE;
	
	public static final Font textFont = new Font("SansSerif", Font.PLAIN, 12);;
	
	private boolean gameRunning = true;
	private boolean waitingForKeyPress = true;

	public static Point mousePos;
	
	public static ResourceManager RESOURCE;
	//public static KeywordTable keywords;
	
	public static HashMap<String, State> gameStates;
	private State currentState;
	
	public static String command;
	
	

	public Game() throws IOException
	{		
		//keywords = new KeywordTable();
		
		RESOURCE = new ResourceManager();
		
		RESOURCE.loadSprites();
		RESOURCE.loadBlocks();
		
		Player steve = new Player("steve");
		
		State world = new World(steve, 800, 600, "bg");
		
		gameStates = new HashMap<String, State>();
		gameStates.put("World", world);
		currentState = world;
		
		mousePos = new Point(0, 0);
	
		command = "";
	}
	
	public void setState(String stateName)
	{
		currentState = gameStates.get(stateName);
	}
	
	private void startGame() 
	{
	}

	private void drawBackground(Graphics2D g)
	{
		g.setColor(Color.blue);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

	public void loop(BufferStrategy strategy) throws IOException
	{
		
		long lastLoopTime = System.currentTimeMillis();
		long delta;
		
		while (gameRunning)
		{			
			// Create rendering object for this frame
			
			Graphics2D backdropRenderer = (Graphics2D) strategy.getDrawGraphics();
			
			Graphics2D gameRenderer = (Graphics2D) strategy.getDrawGraphics();
			
			Graphics2D interfaceRenderer = (Graphics2D) strategy.getDrawGraphics();
			
			// Draw the background
			drawBackground(gameRenderer);
			
			// Let the game state render the frame
			String state = currentState.loop(backdropRenderer, gameRenderer, interfaceRenderer);
			
			if (!state.equals("GameOver"))
			{
				State le = gameStates.get(state);
				if (le == null)
				{
					GameOver go = new GameOver(state);
					currentState = go;
				}
				else
				{
					currentState = le;
				}
			}
			
			// Wipe rendering objects
			gameRenderer.dispose();
			
			interfaceRenderer.dispose();
			
			// Display the frame
			strategy.show();
			
			// Get time in frame
			delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			
			// Schlafen
			try 
			{ 
				Thread.sleep(15);
			}
			catch (Exception e) {}
		}
	}
		
	public void keyPressed(KeyEvent e) 
	{
		currentState.keyPressHandle(e);
	} 
	
	public void keyReleased(KeyEvent e) 
	{
		currentState.keyReleaseHandle(e);
	}

	public void keyTyped(KeyEvent e) 
	{
		if (e.getKeyChar() == 27)
		{
			System.exit(0);
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		currentState.mousePressHandle(e);
	}
	
	public void mouseReleased(MouseEvent e)
	{
		currentState.mouseReleaseHandle(e);
	}

	private void getMouse()
	{
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		mousePos.x = (int) b.getX() - getLocationOnScreen().x;
		mousePos.y = (int) b.getY() - getLocationOnScreen().y;
	}
}

