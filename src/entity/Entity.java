package entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import state.World;
import math.BlockCollision;
import world.Block;
import graphics.Sprite;

public class Entity 
{
	protected static float speedCap = 3;
	protected static float terminalVelocity = 100;
	
	protected Sprite pic;
	protected Point2D.Float velocity;

	protected boolean colliding;
	
	protected float acceleration;
	
	public Entity()
	{
		velocity = new Point2D.Float(0, 0);
	}
	
	public void render(Graphics2D g)
	{
		pic.draw(g);
	}
	
	public void move()
	{
		pic.move((int)velocity.x, (int)velocity.y);
	}
	
	public Point2D.Float getVelocity()
	{
		return velocity;
	}
	
	public void setColliding(boolean b)
	{
		colliding = b;
	}
	
	public Point getPosition()
	{
		return pic.getPosition();
	}
	
	public void setPosition(int x, int y)
	{
		pic.setPosition(x, y);
	}
	
	public Sprite getSprite()
	{
		return pic;
	}
	
	public boolean blockCollideHandle(BlockCollision coll, Block that)
	{
		if (!that.isSolid())
		{
			applyFriction(that.getFriction(), 0);
			return true;
		}
		
		if (coll.topCollided && coll.bottomCollided)
		{
			boolean r = !(that.getPosition().x + 16 < pic.getPosition().x);

			blockSideCollide(that, r);
			return false;

		}
		else
		{
			if (coll.bottomCollided)
			{
				blockBottomCollide(that);
			}
			else if (coll.topCollided)
			{
				return blockTopCollide(that);
			}
			else if (coll.rightCollided)
			{
				blockSideCollide(that, true);
			}
			else if (coll.leftCollided)
			{
				blockSideCollide(that, false);
			}
			else
			{
				// do nothing
			}

			return false;
		}
		
		
	}
	
	protected boolean blockTopCollide(Block that)
	{
		if (velocity.y > 0)
		{
			velocity.y = 0;
		}
		
		pic.setPosition(pic.getPosition().x, that.getPosition().y - pic.getHeight());
		applyFriction(that.getFriction(), 0);
		return false;
	}
	
	protected void blockSideCollide(Block that, boolean isRightSide)
	{
		velocity.x = 0;
		
		if (isRightSide)
		{
			pic.setPosition(that.getPosition().x + that.bounds().x, pic.getPosition().y);
		}
		else
		{
			pic.setPosition(that.getPosition().x - pic.getWidth(), pic.getPosition().y);
		}
	}
	
	protected void blockBottomCollide(Block that)
	{
		if (velocity.y < 0)
		{
			velocity.y = 0;
		}
		
		pic.setPosition(pic.getPosition().x, that.getPosition().y + that.bounds().y);
	}
	
	public void update()
	{
		gravity();
	}
	
	protected void gravity()
	{
		velocity.y += World.GRAVITY;
	}
	
	public void applyFriction(float fric, float zero)
	{
		if (velocity.x == zero)
		{
			return;
		}
		else
		{
			if (velocity.x < zero)
			{
				if (fric > -velocity.x - zero)
				{
					velocity.x = zero;
				}
				else
				{
					velocity.x += fric;
				}
			}
			else
			{
				if (fric > velocity.x - zero)
				{
					velocity.x = zero;
				}
				else
				{
					velocity.x -= fric;
				}
			}
		}
	}
}
