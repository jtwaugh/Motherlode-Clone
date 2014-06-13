package world;

import java.awt.Point;

import window.Game;
import graphics.Sprite;

public class Block
{
	public static final float FRICTION = .2f;
	
	protected Sprite pic;
	
	protected float friction;
	protected float hardness;
	
	public Block (String spriteName, float _hardness)
	{
		pic = Game.RESOURCE.getSprite(spriteName);
		friction = FRICTION;
		hardness = 1;
	}
	
	public Block (Point pos, Block that)
	{
		pic = new Sprite(that.getSprite());
		hardness = that.getHardness();
		pic.setPosition(pos.x, pos.y);
		friction = that.getFriction();
	}
	
	public Sprite getSprite()
	{
		return pic;
	}
	
	public boolean isSolid()
	{
		return hardness != 0;
	}
	
	public float getHardness()
	{
		return hardness;
	}
	
	public float getFriction()
	{
		return friction;
	}
	
	public Point getPosition()
	{
		return pic.getPosition();
	}
	
	public Point bounds()
	{
		return new Point(pic.getWidth(), pic.getHeight());
	}
}
