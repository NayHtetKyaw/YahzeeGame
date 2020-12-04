package YahzeeY;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

class Die1 extends Die
{	
	public Die1(int x, int y, boolean highlight)
	{
		super(x, y, highlight, 1);
	}

  public void draw(Graphics2D g2)
  {
    Rectangle box = new Rectangle(x,y,30,30);
    Ellipse2D.Double circle = new Ellipse2D.Double(x+10,y+10,10,10);
   
    g2.setColor(Color.black);
    if (highlighted || highlightedHover)
			g2.fill(box);
		else
			g2.draw(box);
    g2.setColor(Color.red);
    g2.fill(circle);
	}
}

class Die2 extends Die
{	
	public Die2(int x, int y, boolean highlight)
	{
		super(x, y, highlight, 2);
	}

  public void draw(Graphics2D g2)
  {
    Rectangle box = new Rectangle(x,y,30,30);
    Ellipse2D.Double circle = new Ellipse2D.Double(x,y,10,10);
    Ellipse2D.Double circle2 = new Ellipse2D.Double(x+20,y+20,10,10);
   
    g2.setColor(Color.black);
    if (highlighted || highlightedHover)
			g2.fill(box);
		else
			g2.draw(box);
    g2.setColor(Color.red);
    g2.fill(circle);
    g2.fill(circle2);
  }
}

class Die3 extends Die
{	
	public Die3(int x, int y, boolean highlight)
	{
		super(x, y, highlight, 3);
	}

  public void draw(Graphics2D g2)
  {
    Rectangle box = new Rectangle(x,y,30,30);
    Ellipse2D.Double circle = new Ellipse2D.Double(x+10,y,10,10);
    Ellipse2D.Double circle2 = new Ellipse2D.Double(x+10,y+10,10,10);
    Ellipse2D.Double circle3 = new Ellipse2D.Double(x+10,y+20,10,10);
   
    g2.setColor(Color.black);
    if (highlighted || highlightedHover)
			g2.fill(box);
		else
			g2.draw(box);
    g2.setColor(Color.red);
    g2.fill(circle);
    g2.fill(circle2);
    g2.fill(circle3);
  }
}

class Die4 extends Die
{	
	public Die4(int x, int y, boolean highlight)
	{
		super(x, y, highlight, 4);
	}

  public void draw(Graphics2D g2)
  {
    Rectangle box = new Rectangle(x,y,30,30);
    Ellipse2D.Double circle = new Ellipse2D.Double(x,y,10,10);
    Ellipse2D.Double circle2 = new Ellipse2D.Double(x+20,y+20,10,10);
    Ellipse2D.Double circle3 = new Ellipse2D.Double(x+20,y,10,10);
    Ellipse2D.Double circle4 = new Ellipse2D.Double(x,y+20,10,10);
   
    g2.setColor(Color.black);
    if (highlighted || highlightedHover)
			g2.fill(box);
		else
			g2.draw(box);
    g2.setColor(Color.red);
    g2.fill(circle);
    g2.fill(circle2);
    g2.fill(circle3);
    g2.fill(circle4);
  }
}

class Die5 extends Die
{	
	public Die5(int x, int y, boolean highlight)
	{
		super(x, y, highlight, 5);
	}

  public void draw(Graphics2D g2)
  {
    Rectangle box = new Rectangle(x,y,30,30);
    Ellipse2D.Double circle = new Ellipse2D.Double(x,y,10,10);
    Ellipse2D.Double circle2 = new Ellipse2D.Double(x+20,y+20,10,10);
    Ellipse2D.Double circle3 = new Ellipse2D.Double(x+20,y,10,10);
    Ellipse2D.Double circle4 = new Ellipse2D.Double(x,y+20,10,10);
    Ellipse2D.Double circle5 = new Ellipse2D.Double(x+10,y+10,10,10);
   
    g2.setColor(Color.black);
    if (highlighted || highlightedHover)
			g2.fill(box);
		else
			g2.draw(box);
    g2.setColor(Color.red);
    g2.fill(circle);
    g2.fill(circle2);
    g2.fill(circle3);
    g2.fill(circle4);
    g2.fill(circle5);
  }
}

class Die6 extends Die
{	
	public Die6(int x, int y, boolean highlight)
	{
		super(x, y, highlight, 6);
	}

  public void draw(Graphics2D g2)
  {
    Rectangle box = new Rectangle(x,y,30,30);
    Ellipse2D.Double circle = new Ellipse2D.Double(x,y,10,10);
    Ellipse2D.Double circle2 = new Ellipse2D.Double(x+20,y+20,10,10);
    Ellipse2D.Double circle3 = new Ellipse2D.Double(x+20,y,10,10);
    Ellipse2D.Double circle4 = new Ellipse2D.Double(x,y+20,10,10);
    Ellipse2D.Double circle5 = new Ellipse2D.Double(x,y+10,10,10);
    Ellipse2D.Double circle6 = new Ellipse2D.Double(x+20,y+10,10,10);
   
    g2.setColor(Color.black);
    if (highlighted || highlightedHover)
			g2.fill(box);
		else
			g2.draw(box);
    g2.setColor(Color.red);
    g2.fill(circle);
    g2.fill(circle2);
    g2.fill(circle3);
    g2.fill(circle4);
    g2.fill(circle5);
    g2.fill(circle6);
  }
}