package YahzeeY;

//Super Class for DieFaces
import java.awt.Graphics2D;

public class Die
{ 
  public int x, y, faceValue;
  public boolean highlighted, highlightedHover;
  
  public Die(int x, int y, boolean highlight, int faceValue)
  {
    this.x = x;
    this.y = y;
    this.highlighted = highlight;
    this.highlightedHover = false;
    this.faceValue = faceValue;
  }
  
  public void highlight()
  {
		this.highlighted = !this.highlighted;
  }

  public void highlightHover(boolean h)
  {
		this.highlightedHover = h;
  }
  
  public int getFaceValue()
  {
		return faceValue;
	}
  
  public void draw(Graphics2D g2) {}
}