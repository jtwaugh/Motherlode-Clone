package entity;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import window.Game;
import world.Block;

enum PlayerState { Rest, Moving, Flying, Drilling, Shop };

public class Player extends Entity
{
	private boolean waitingForKeyPress;
	
	private boolean leftPressed;
	private boolean rightPressed;
	private boolean upPressed;
	private boolean downPressed;
	
	private boolean qPressed;
	private boolean ePressed;
	
	private boolean mousePressed;
	private boolean mouseReleased;
	
	private float hp;
	private float hpMax;
	private float fuel;
	private float fuelMax;
	
	private PlayerState state;
	
	private float armor;
	
	private float fuelPerTime;
	private float dmgPerSpeed;
	
	private Block drillTarget;
	private float drillSpeed;
	
	public Player(String spriteName)
	{
		pic = Game.RESOURCE.getSprite(spriteName);
		enableInput();
		acceleration = .7f;
		
		hpMax = 30;
		hp = hpMax;
		
		fuelMax = 30;
		fuel = fuelMax;
		
		fuelPerTime = .004f;
		dmgPerSpeed = .03f;
		armor = 4;
		
		state = PlayerState.Rest;
		drillSpeed = 2.3f;
	}
	
	public void render(Graphics2D g)
	{
		if (state == PlayerState.Drilling)
		{
			pic.playAnimation("drill");
		}
		else
		{
			pic.playAnimation("default");
		}
		
		pic.draw(g);
	}
	
	public void disableInput()
	{
		waitingForKeyPress = false;
	}
	
	public void enableInput()
	{
		waitingForKeyPress = true;
	}
	
	public void reactToKeys()
	{
		// React appropriately
		
		if (waitingForKeyPress)
		{
			
			if (state == PlayerState.Rest || state == PlayerState.Flying || state == PlayerState.Moving)
			{
				if (leftPressed)
				{
					if (-velocity.x <= speedCap)
					{
						velocity.x -= acceleration;
					}
				}
				else if (rightPressed)
				{
					
					if (velocity.x <= speedCap)
					{
						velocity.x += acceleration;
					}
				}
				else
				{
				}
				
				if (upPressed)
				{
					if (-velocity.y <= speedCap)
					{
						velocity.y -= acceleration;
					}
				}
				
				if (qPressed)
				{
				}
				
				if (ePressed)
				{
				}
			}
			
			if (state == PlayerState.Rest)
			{
				
				if (downPressed)
				{
					state = PlayerState.Drilling;
				}
			}
			
			if (state == PlayerState.Drilling)
			{
				if (!downPressed)
				{
					state = PlayerState.Rest;
				}
			}
		}
	}
	
	public void keyPressHandle(KeyEvent e)
	{
		// Figure out which keys were pressed
		if (e.getKeyCode() == Game.KEY_LEFT)
		{
			leftPressed = true;
		}
		if (e.getKeyCode() == Game.KEY_RIGHT)
		{
			rightPressed = true;
		}
		if (e.getKeyCode() == Game.KEY_UP)
		{
			upPressed = true;
		}
		if (e.getKeyCode() == Game.KEY_DOWN)
		{
			downPressed = true;
		}
	}
	
	public void keyReleaseHandle(KeyEvent e)
	{
		if (e.getKeyCode() == Game.KEY_LEFT)
		{
			leftPressed = false;
		}
		if (e.getKeyCode() == Game.KEY_RIGHT)
		{
			rightPressed = false;
		}
		if (e.getKeyCode() == Game.KEY_UP)
		{
			upPressed = false;
		}
		if (e.getKeyCode() == Game.KEY_DOWN)
		{
			downPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_Q)
		{
			qPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_E)
		{
			ePressed = true;
		}
	}
	
	public void update()
	{
		gravity();
		setState();
		
		if (state == PlayerState.Flying || state == PlayerState.Moving)
		{
			float v = Math.abs(velocity.x);
			
			if (velocity.y <= 0)
			{
				v = (float)Math.sqrt(v * v + velocity.y * velocity.y);
			}
			
			fuel -= v * fuelPerTime;
			
			//System.out.println("fuel: " + fuel + "/" + fuelMax + ", hull: " + hp + "/" + hpMax);
			//System.out.println("Colliding = " + colliding);
		}
		
	}
	
	private void setState()
	{
		if (velocity.y < 0)
		{
			state = PlayerState.Flying;
		}
		else if (colliding)
		{
			if (velocity.x != 0)
			{
				state = PlayerState.Moving;
			}
			else if (downPressed)
			{
				state = PlayerState.Drilling;
			}
			else
			{
				state = PlayerState.Rest;
			}
		}
	}
	
	public float getHull()
	{
		return hp;
	}
	
	public float getFuel()
	{
		return fuel;
	}
	
	protected boolean blockTopCollide(Block that)
	{
		if (!colliding)
		{
			if ((velocity.y * velocity.y * dmgPerSpeed - armor) > 0)
			{
				hp -= velocity.y * velocity.y * dmgPerSpeed - armor;
				System.out.println(velocity.y * velocity.y * dmgPerSpeed);
			}
		}
		
		if (velocity.y > 0)
		{
			velocity.y = 0;
		}
		
		pic.setPosition(pic.getPosition().x, that.getPosition().y - pic.getHeight());
		applyFriction(that.getFriction(), 0);
		
		if (colliding == false)
		{
			colliding = true;
		}
		
		return true;
	}
	
	public void reactToMouse()
	{
		if (mousePressed)
		{
			
		}
		if (mouseReleased)
		{
			mouseReleased = false;
		}
	}
	
	public void mousePressHandle(MouseEvent e)
	{
		mousePressed = true;
	}
	
	public void mouseReleaseHandle(MouseEvent e)
	{
		mousePressed = false;
		mouseReleased = true;
	}
}
