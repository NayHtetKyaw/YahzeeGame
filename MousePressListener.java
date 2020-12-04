package YahzeeY;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
//import java.io.IOException;

public class MousePressListener implements MouseListener, MouseMotionListener, ActionListener
{
	private int[] hoverCoords; //coords of dice when hovered upon [0]->x coord [1]-> y coord
	private int round, numRollsLeft;
	private DiceComponent component;
	private JButton[] cButtons;
  private JLabel[][] cLabels;
  private JLabel statusLabel;
  private YahtzeeScore[] score;
  private JLabel highscoreLabel;
  private boolean canScore;//see if can score, if not, need to set a score to 0 --- used in actionevent
  private int numGridRows;
  private int player; //main control of which player's turn, changes turns
  private int numPlayers; //number of players, referenced from frame class


	MousePressListener(DiceComponent component, JButton[] cButtons, JLabel[][] cLabels, JLabel statusLabel, int numGridRows, YahtzeeScore[] score, JLabel highscoreLabel, int numPlayers)
	{
		this.component = component;
		this.cButtons = cButtons;
		this.cLabels = cLabels;
		this.statusLabel = statusLabel;
		this.numGridRows = numGridRows;
		this.score = score;
		this.highscoreLabel = highscoreLabel;
		this.player = 0;
		this.numPlayers = numPlayers;
		round = 1;
		numRollsLeft = 3;

		canScore = score[player].checkDice(numRollsLeft);
	}
	/*
	//concatenates an array of info into one string.
	private String stringConcat(int[] a)
	{
		String mainString = "";
		int place = 1;
		for(int b : a)
		{
			if (b != 0)
				mainString = new String(mainString.concat(place+" - "+b+"<br>"));
			place++;
		}

		return mainString;
	}

	public void popHighscores() throws IOException
	{
		System.out.println("popHighscores was called");
		File file = new File("Highscores.txt");
    BufferedReader input = new BufferedReader(new FileReader(file));

    String line = null;
    int[] highscores = new int[20];
    int numScores = 0;
    while((line = input.readLine()) != null)
    {
			highscores[numScores++] = Integer.parseInt(line);
		}
		input.close();
		highscoreLabel.setText("<html>Highscores<br><br>"+stringConcat(highscores)+"</html>");
	}

	private void checkHighscore(int curScore) throws IOException
	{
		System.out.println("checkHighscore was called");
		int[] curHighscores = new int[20];

		File file = new File("Highscores.txt");
    BufferedReader input = new BufferedReader(new FileReader(file));

    //populate current highscores array to compare next
    int numScores = 0;
    String line = null;
    while((line = input.readLine()) != null)
    {
			curHighscores[numScores] = Integer.parseInt(line);
			numScores++;
		}
		if (numScores == 0)
			numScores++; // prevent program from creating an exception error
		input.close();

		//if current score is greater than lowest high score, add it in and output that new high score has been added.
		if (curScore > curHighscores[--numScores])
		{
			System.out.println("inside");
			int placementIndex = 0;
			while(placementIndex <= numScores)
			{ //get index of placement for this new highscore
				if (curScore > curHighscores[placementIndex])
					break;
				placementIndex++;
			}
			int[] writeScore = new int[20];
			boolean curScorePlaced = false;
			for(int i = 0; i <= numScores+1; i++)
			{
				if (placementIndex == i && !curScorePlaced) //current score is higher than the score in this index
				{
					writeScore[i] = curScore;
					curScorePlaced = true;
					continue;
				}
				writeScore[i] = (curScorePlaced) ? curHighscores[i-1] : curHighscores[i];
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, false)); //false overwrites the file
			for (int wScore : writeScore)
			{
				if (wScore == 0)
					break; //end of scores, stop writing

				writer.write(wScore);
				writer.newLine();
			}
			/*
			for (int i = 0; i < 20; i++)
			{

			}
			writer.close();
			popHighscores();
			JOptionPane.showMessageDialog(null, "Congratulation, you have a highscore!\n\n");
		}
	}
	*/
	//method to form a new game, reset everything
	private void newGame()
	{
		Object[] options = {"Create new game","Cancel"};
		int answer = JOptionPane.showOptionDialog(null, "Are you sure you want to start a new game? All your current data will be deleted.", "New Game?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (answer == JOptionPane.YES_OPTION)
		{
			cButtons[0].setEnabled(true);
			numRollsLeft = 3;
			round = 1;
			cLabels[player][0].setText("");
			cLabels[0][0].setText("Rolls Left: "+numRollsLeft);
			player = 0;
			component.rollDice(true);
			for(YahtzeeScore sco : score)
				sco.reset();
			statusLabel.setText("<html>New game has been started!<br>Please select the dice that you wish to hold or click on a scoring button</html>");
		}
	}

	private void nextPlayer()
	{
		cLabels[player][0].setText(""); // remove label above player
		if (player+1 < numPlayers)
			player++; //next player
		else
		{
			player = 0; //back to first player
			round++; // increment round
		}
	}

	public void mousePressed(MouseEvent e)
	{ // highlight die and keep highlighted
		int x = e.getX();
		int y = e.getY();
		component.highlightDie(x,y, false);
	}

	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}

	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e)
	{ //highlight die when mouse hovered on
		int x = e.getX();
		int y = e.getY();
		if ( !(hoverCoords != null && //array does NOT exists
					x >= hoverCoords[0] && x <= hoverCoords[0]+component.DIE_SIZE && //NOT inside x coords
					y >= hoverCoords[1] && y <= hoverCoords[1]+component.DIE_SIZE )) //NOT inside y coords
		{
			component.diceHighlightHoverOff(); //method to set all highlightHover properties to false b/c not hovering on a die or went off from a die
			hoverCoords = component.highlightDie(x,y, true); //check if die is hovered on
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		statusLabel.setForeground(Color.black);
		//reroll button clicked
		if (e.getSource() == cButtons[0])
		{
			component.rollDice(false);
			cLabels[player][0].setText("Rolls Left: "+ --numRollsLeft);
			if (numRollsLeft <= 0)
			{
				cButtons[0].setEnabled(false);
				statusLabel.setText("<html>No more rolls left.<br>Choose how you wish to score.</html>");
			}
		}

		//new game button clicked
		else if (e.getSource() == cButtons[numGridRows-1])
		{
			newGame(); // reset everything for new game
		}

		//one of the scoring buttons was clicked
		else
		{
			int[] points = score[player].score(e.getSource(), canScore);
			numRollsLeft = 2;
			component.rollDice(true); // rolls new 5 dice
			cButtons[0].setEnabled(true); // sets roll dice button to enable
			statusLabel.setText("<html>Scored "+points[0]+" points!"+
															(points[0] == 50 ? " YAHTZEE!<br>" : "<br>") +
															(points[1]==35 ?"You have also received a bonus of +35 points for having 63+ points in the Upper Section!<br>":"") +
															(points[2]==100 ?"You have also received a Yahtzee bonus of +100 points!<br>":"") +
															"<br>Please select the dice that you wish to reroll or click on a scoring button</html>");
			if (round == 13 && player+1 == numPlayers)
			{
				cButtons[0].setEnabled(false);
				if (numPlayers == 1)
					statusLabel.setText("<html>End of game. You have scored a Grand Total of "+score[player].getGrandTotal()+" points!<br><br>To play again, click on NEW GAME!</html>");
				else
				{ //more than one players, compare scores
					int winnerScore = 0;
					int winner = 0;
					for(int k = 0; k < numPlayers; k++)
					{
						if (score[k].getGrandTotal() > winnerScore)
						{
							winnerScore = score[k].getGrandTotal();
							winner = k+1;
						}
					}
					statusLabel.setText("<html>End of game. Player "+winner+" is the winner with a Grand Total of "+winnerScore+" points!<br><br>To play again, click on NEW GAME!</html>");
				}
				/*try {
					checkHighscore(score.getGrandTotal());
				} catch (IOException t) {
						System.err.println("FileNotFoundException: " + t.getMessage());
				}*/
			}
			nextPlayer();
			cLabels[player][0].setText("Rolls Left: "+ numRollsLeft);
		}

		canScore = score[player].checkDice(numRollsLeft);
		if (!canScore && numRollsLeft == 0)
		{
			statusLabel.setText("<html>You can not score, select which scoring catagory to fill a 0 in</html>");
			statusLabel.setForeground(Color.red);
		}

	}

}