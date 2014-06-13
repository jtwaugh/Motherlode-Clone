package math;

import java.awt.Point;

import entity.Entity;


public abstract class Geometry 
{
	public static Point lineIntersect(Point p1, Point p2, Point p3, Point p4)
	{
		if (p1.equals(p2))
		{
			if (pointOnLine(p1, p3, p4))
			{
				return p1;
			}			
			else return null;
		}
		
		if (p1.x == p2.x || p3.x == p4.x)
		{
			if ((p1.x == p2.x && p3.x == p4.x))
			{
				 if (p1.x == p3.x)
				 {
					 return null;
				 }
			}
		}
		else
		{
			double slope1 = (p1.y - p2.y)/(p1.x - p2.x);
			double slope2 = (p3.y = p4.y)/(p3.x - p4.x);
			
			if ((p1.equals(p3) || p1.equals(p4))&&(p2.equals(p3) || p2.equals(p4)))
			{
				// lines are the same
				
			}
			
			if (slope1 == slope2 && (pointOnLine(p1, p3, p4) || pointOnLine(p2, p3, p4) || pointOnLine(p3, p1, p2) || pointOnLine(p4, p1, p2)))
			{
				// lines intersect, have same slope
			}
		}
		
		
		int d = (p1.x - p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x - p4.x);

		if (d == 0)
		{
			return null;
		}
		
		int xi = ((p3.x-p4.x)*(p1.x*p2.y-p1.y*p2.x)-(p1.x-p2.x)*(p3.x*p4.y-p3.y*p4.x))/d;
		int yi = ((p3.y-p4.y)*(p1.x*p2.y-p1.y*p2.x)-(p1.y-p2.y)*(p3.x*p4.y-p3.y*p4.x))/d;
		
		Point pt = new Point (xi, yi);
		
		
		if (pointOnLine(pt, p1, p2) && pointOnLine(pt, p3, p4))
		{
			return pt;
		}
		else return null;
	}
	
	public static boolean pointOnLine(Point p, Point l1, Point l2)
	{
		int Y;
		int y;
		
		int X;
		int x;
		
		X = Math.max(l1.x, l2.x);
		x = Math.min(l1.x, l2.x);
		
		Y = Math.max(l1.y, l2.y);
		y = Math.min(l1.y, l2.y);
		
		return ((p.x <= X && p.x >= x) && (p.y <= Y && p.y >= y));
	}
	
	public static boolean pointInQuad (Point p, Point p1, Point p2)
	{
		int left;
		int right;
		int top;
		int bottom;
		
		if (p1.x < p2.x)
		{
			left = p1.x;
			right = p2.x;
		}
		else
		{
			left = p2.x;
			right = p1.x;
		}
		
		if (p1.y < p2.y)
		{
			top = p1.y;
			bottom = p2.y;
		}
		else
		{
			top = p2.y;
			bottom = p1.y;
		}
		
		return (p.x <= right && p.x >= left && p.y <= bottom && p.y >= top);
	}
	
	public static boolean entityInProximity (Entity e1, Entity e2, double r2)
	{
		if (e1 == null || e2 == null)
		{
			return false;
		}
		
		Point p1 = e1.getPosition();
		Point p2 = e2.getPosition();
		double r1 = Math.sqrt((p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y));
		return (r1 <= r2);
	}
}

