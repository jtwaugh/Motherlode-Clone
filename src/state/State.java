package state;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public abstract class State
{
	protected String ret;
	
	public State ()
	{

	}
	
	public String loop(Graphics2D BGDraw, Graphics2D gameDraw, Graphics2D infDraw)
	{
		return null;
	}
	
	private void render()
	{
		
	}
	
	private void logic()
	{
		
	}
	
	public void keyPressHandle(KeyEvent e)
	{
		
	}
	
	public void keyReleaseHandle(KeyEvent e)
	{
		
	}
	
	public void mousePressHandle(MouseEvent e)
	{
		
	}
	
	public void mouseReleaseHandle(MouseEvent e)
	{
		
	}
}

