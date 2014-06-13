package state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import window.Game;

public class GameOver extends State
{
	protected String message;
	protected boolean spacePressed;
	
	public GameOver(String msg)
	{
		message = msg;
		ret = "GameOver";
		spacePressed = false;
	}
	
	public String loop(Graphics2D BGDraw, Graphics2D gameDraw, Graphics2D infDraw)
	{
		logic();
		render(BGDraw, gameDraw);
		return ret;
	}
	
	private void render(Graphics2D BGDraw, Graphics2D gameDraw)
	{
		BGDraw.setColor(Color.BLACK);
		BGDraw.fill(new Rectangle(0, 0, 800, 600));
		
		gameDraw.setColor(Color.WHITE);
		gameDraw.setFont(Game.textFont);
		gameDraw.drawString(message, 400 - gameDraw.getFontMetrics().stringWidth(message)/2, 300 - gameDraw.getFontMetrics().getAscent());
		String msg = "Press spacebar to restart";
		gameDraw.drawString(msg, 400 - gameDraw.getFontMetrics().stringWidth(msg)/2, 300 + gameDraw.getFontMetrics().getAscent());
	}
	
	private void logic()
	{
		if (spacePressed == true)
		{
			spacePressed = false;
			ret = "Menu";
		}
	}
	
	public void keyPressHandle(KeyEvent e)
	{
		
	}
	
	public void keyReleaseHandle(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			spacePressed = true;
		}
	}
	
	public void mousePressHandle(MouseEvent e)
	{
		
	}
	
	public void mouseReleaseHandle(MouseEvent e)
	{
		
	}
}
