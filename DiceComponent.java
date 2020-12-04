package YahzeeY;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.util.Random;
import java.awt.Dimension;

public class DiceComponent extends JComponent
{
  private Random generator = new Random(); //random number generator
  private int random;
  private int dimX = 220, dimY = 40; // dimensions of DiceComponent
  
  /***
  ** DieCoords array
  * [0] = x-coord of creation
  * [1] = y-coord of creation
  */
  private Die[] dice = new Die[5]; //array to hold the DieFaces
  private int[][] dieCoords = new int[5][2]; // hold dice coords of creation -- parallel to dice array ^^^^\
  public final byte DIE_SIZE = 30;
  private int dieY_coord = dimY/2-(DIE_SIZE/2); //y-coord of placement when creating die objects
  private Graphics2D g2;
  
  DiceComponent(int x)
  {
		this.dimX = x;
		this.setPreferredSize(new Dimension(dimX,dimY));
	}
  
  public void paintComponent(Graphics g)
  {
    g2 = (Graphics2D) g;
		for(int i = 0; i < 5; i++)
		{
			dice[i].draw(g2);
		} 
	}
	
	//reset dice completely by creating new random die objects, not used for now... only used when starting program.
	public void rollDice(boolean newRound)
	{
		int xPadding = (dimX-(DIE_SIZE*5))/6;//padding distance, total width minus total dice widths, all divided by 6 (# paddings required- 4 in between dice and 2 on ends.)
		int xEndPadd = (dimX%5)/2;

		//create and store DieFaces
		for(int i = 1; i <= 5; i++)
		{
			
			// reroll the highlighted dice if not a new round roll
			if (!newRound)
			{
				if (dice[i-1].highlighted)
				{
					continue; //is highlighted, so keeping die and moving to next
				}
			}
			
			//store die coords
			dieCoords[i-1][0] = (i==1?xEndPadd:0)+DIE_SIZE*(i-1)+(xPadding<0?0:xPadding*i);//last part is incase window is too small, which will make padding negative and dice overlapping, this will prevent it.
			dieCoords[i-1][1] = dieY_coord;			
			
			random = generator.nextInt(6)+1;
			
			//create actual die object
			switch(random)
			{
				case 1:
					dice[i-1] = new Die1(dieCoords[i-1][0],dieCoords[i-1][1], false);
					break;
				case 2:
					dice[i-1] = new Die2(dieCoords[i-1][0],dieCoords[i-1][1], false);
					break;
				case 3:
					dice[i-1] = new Die3(dieCoords[i-1][0],dieCoords[i-1][1], false);
					break;
				case 4:
					dice[i-1] = new Die4(dieCoords[i-1][0],dieCoords[i-1][1], false);
					break;
				case 5:
					dice[i-1] = new Die5(dieCoords[i-1][0],dieCoords[i-1][1], false);
					break;
				case 6:
					dice[i-1] = new Die6(dieCoords[i-1][0],dieCoords[i-1][1], false);
					break;
			}
		}
		
		repaint();
	}
	
	//Highlight die if clicked or hovered upon
	public int[] highlightDie(int eX, int eY, boolean hover)
	{
		int dieX, dieY;
		for(int i = 0; i < 5; i++)
		{
			dieX = this.dieCoords[i][0];
			dieY = this.dieCoords[i][1];
			if ( eX >= dieX && eX <= dieX+DIE_SIZE && eY >= dieY && eY <= dieY+DIE_SIZE )
			{
				if (hover)
				{
					dice[i].highlightHover(true);
					int[] returnArray = {dieX, dieY};
					repaint();
					//return coords of die location to use in listener class as a way of knowing which die is currently highlighted
					return returnArray;
				}
				else
				{
					dice[i].highlight();
					repaint();
				}				
			}
		}
		return null;
	}
	  
	//method to set all highlightHover properties to false;
	public void diceHighlightHoverOff()
	{
		for(int i = 0; i < 5; i++)
			dice[i].highlightHover(false);
			
		repaint();
	}  
	
	//method to return Die array
	public Die[] getDieArray()
	{
		return dice;
	}
  
}