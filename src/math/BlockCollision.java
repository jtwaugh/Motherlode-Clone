package math;

import entity.Entity;
import graphics.Sprite;

import java.awt.Point;

import world.Block;

public class BlockCollision
{
	public boolean rightCollided;
	public boolean leftCollided;
	public boolean topCollided;
	public boolean bottomCollided;
	
	public BlockCollision(boolean rColl, boolean lColl, boolean tColl, boolean bColl)
	{
		rightCollided = rColl;
		leftCollided = lColl;
		topCollided = tColl;
		bottomCollided = bColl;
	}
	
	public BlockCollision(BlockCollision that)
	{
		rightCollided = that.rightCollided;
		leftCollided = that.leftCollided;
		topCollided = that.topCollided;
		bottomCollided = that.bottomCollided;
	}
	
	public boolean collided()
	{
		return (rightCollided || leftCollided || topCollided || bottomCollided);
	}
	
	public static BlockCollision collBlockEntity(Entity e, Block that)
	{
		boolean[] coll = {false, false, false, false};
		// 0 is right
		// 1 is left
		// 2 is top
		// 3 is bottom
		
		Sprite pic = e.getSprite();
		double xMomentum = e.getVelocity().x;
		double yMomentum = e.getVelocity().y;
		
		int x = pic.getPosition().x;
		int y = pic.getPosition().y;
		
		Point cTopLeft = pic.getPosition();
		Point cTopRight = new Point(x + pic.getWidth() - 1, y);
		Point cBotLeft = new Point(x, y + pic.getHeight() - 1);
		Point cBotRight = new Point(x + pic.getWidth() - 1, y + pic.getHeight() - 1);
		Point cMidLeft = new Point(x, y + ((pic.getHeight() - 1)/ 2));
		Point cMidRight = new Point(x + pic.getWidth() - 1, y + ((pic.getHeight() - 1) / 2));
		
		Point eTopLeft = new Point((int)(cTopLeft.x + xMomentum), (int)(cTopLeft.y + yMomentum));
		Point eTopRight = new Point((int)(cTopRight.x + xMomentum), (int)(cTopRight.y + yMomentum));
		Point eBotLeft = new Point((int)(cBotLeft.x + xMomentum), (int)(cBotLeft.y + yMomentum));
		Point eBotRight = new Point((int)(cBotRight.x + xMomentum), (int)(cBotRight.y + yMomentum));
		Point eMidLeft = new Point((int)(x + xMomentum), (int) (y + ((pic.getHeight() - 1)/ 2) + yMomentum));
		Point eMidRight = new Point((int)(cMidRight.x + xMomentum), (int) (y + ((pic.getHeight() - 1)/ 2) + yMomentum));
		
		int tx = that.getPosition().x;
		int ty = that.getPosition().y;
		
		Point tTopLeft = that.getPosition();
		Point tTopRight = new Point(tx + that.bounds().x - 1, ty);
		Point tBotLeft = new Point(tx, ty + that.bounds().y - 1);
		Point tBotRight = new Point(tx + that.bounds().x - 1, ty + that.bounds().y - 1);
		
		// Check intersection of top of block
		if ((Geometry.lineIntersect(cBotLeft, eBotLeft, tTopLeft, tTopRight) != null)
			|| (Geometry.lineIntersect(cBotRight, eBotRight, tTopLeft, tTopRight) != null)
			|| (Geometry.lineIntersect(cTopLeft, cBotLeft, tTopLeft, tTopRight) != null))
		{
			coll[2] = true;
		}
		
		if (!that.isSolid())
		{
			if (Geometry.lineIntersect(cTopRight, cBotRight, tTopLeft, tTopRight) != null)
			{
				coll[2] = true;
			}
		}
		
		// Check intersection of bottom of block
		if ((Geometry.lineIntersect(cTopLeft, eTopLeft, tBotLeft, tBotRight) != null)
			|| (Geometry.lineIntersect(cTopRight, eTopRight, tBotLeft, tBotRight) != null)
			|| (Geometry.lineIntersect(cTopLeft, cBotLeft, tBotLeft, tBotRight) != null))
		{
			coll[3] = true;
		}
		
		if (!that.isSolid())
		{
			if (Geometry.lineIntersect(cTopRight, eBotRight, tBotLeft, tBotRight) != null)
			{
				coll[3] = true;
			}
		}
		
		if (!coll[2] && !coll[3])
		{
			// Check intersection of left side of block
			if ((Geometry.lineIntersect(cTopRight, eTopRight, tTopLeft, tBotLeft) != null)
				|| (Geometry.lineIntersect(cMidRight, eMidRight, tTopLeft, tBotLeft) != null)
				|| (Geometry.lineIntersect(cBotRight, eBotRight, tTopLeft, tBotLeft) != null)
				|| (cBotRight.x > tBotLeft.x && cBotLeft.y >= tBotLeft.y && cBotLeft.y <= tTopLeft.y))
			{
				coll[1] = true;
			}
			
			// Check intersection of right side of block
			if ((Geometry.lineIntersect(cTopLeft, eTopLeft, tTopRight, tBotRight) != null)
				|| (Geometry.lineIntersect(cMidLeft, eMidLeft, tTopRight, tBotRight) != null)
				|| (Geometry.lineIntersect(cBotLeft, eBotLeft, tTopRight, tBotRight) != null)
				|| (cBotLeft.x < tBotRight.x && cBotLeft.y >= tBotLeft.y && cBotLeft.y <= tTopLeft.y))
			{
				coll[0] = true;
			}
		}
		
		
		BlockCollision collision = new BlockCollision(coll[0], coll[1], coll[2], coll[3]);
		return collision;
	}
	
	public static boolean isAtRestOn(Entity e, Block b)
	{
		boolean ret = (e.getSprite().getPosition().y == b.getPosition().y - e.getSprite().getHeight() && e.getVelocity().y < 1);
		return ret;
	}
}