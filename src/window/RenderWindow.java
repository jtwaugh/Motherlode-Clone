package window;
//
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.swing.*;

public class RenderWindow extends Canvas implements ImageObserver
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4841316160511087032L;
	private static final int boundX = 800;
	private static final int boundY = 600;
	
	private JFrame container;
	private JPanel panel;
	
	private BufferStrategy strategy;
	
	private Game game;

	public RenderWindow(String name) throws IOException
	{			
		setUpWindow(name);			
		hardcodeZone();
		unitTests();
		game = new Game();
	}
	
	private void hardcodeZone() throws IOException
	// This should be empty by release
	{	
		//currentState = gameStates.get("loadingScreen");
	}
	
	private void unitTests()
	{
	}

	private void setUpWindow(String name)
	{
		container = new JFrame(name);
		

		panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(boundX, boundY));
		panel.setLayout(null);
		

		setBounds(0, 0, boundX, boundY);
		
		panel.add(this);
		

		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		

		container.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) 
			{
				System.exit(0);
			}
		});

		requestFocus();

		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		addKeyListener(new KeyInputHandler());
		addMouseListener(new MouseInputHandler());
	}

	public void run() throws IOException
	{
		game.loop(strategy);
	}
	
	private class KeyInputHandler extends KeyAdapter 
	{
		
		private int pressCount = 1;
		
		public void keyPressed(KeyEvent e) 
		{
			game.keyPressed(e);
		} 
		
		public void keyReleased(KeyEvent e) 
		{
			game.keyReleased(e);
		}

		public void keyTyped(KeyEvent e) 
		{
			game.keyTyped(e);
		}
	}
	
	private class MouseInputHandler extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			game.mousePressed(e);
		}
		
		public void mouseReleased(MouseEvent e)
		{
			game.mouseReleased(e);
		}
	}
}


