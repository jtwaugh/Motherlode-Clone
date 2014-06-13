package gui;

import java.awt.Graphics2D;
import java.awt.Point;

import window.Game;
import graphics.Sprite;

public class StatBar
{
	protected Point pos;
	
	protected Sprite full;
	protected Sprite cap;
	
	protected float max;
	protected float current;
	
	public StatBar(int x, int y, String fullName, String capName, float _max)
	{
		pos = new Point(x, y);
		full = Game.RESOURCE.getSprite(fullName);
		cap = Game.RESOURCE.getSprite(capName);
		max = _max;
		current = max;
	}
	
	public void setCurrent(float _current)
	{
		current = _current;
	}
	
	public float getMax()
	{
		return max;
	}
	
	public float getCurrent()
	{
		return current;
	}
	
	public void draw(Graphics2D g)
	{	
		int length = (int)(full.getWidth() * current / max);
		
		cap.setPosition(pos.x + length + cap.getHeight(), pos.y);
		cap.draw(g);
		cap.flipHorizontal();
		cap.setPosition(pos.x, pos.y);
		cap.draw(g);
		cap.flipHorizontal();
		
		
		full.setPosition(pos.x + cap.getWidth(), pos.y);
		full.drawPortion(g, full.getPosition(), new Point(length, full.getImage().getHeight()));
	}
}
