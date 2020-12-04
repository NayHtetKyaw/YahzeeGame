package YahzeeY;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
//import java.io.IOException;
//import java.io.FileNotFoundException;

// changed cLabels, score

public class ExFrame extends JFrame
{
	private int numCreateButLabCalls, numPlayers;
	private int numGridRows, buttonWidth;
	private DiceComponent component;
	private JButton[] cButtons;     //borderlayout center buttons
	private String[] cButtonsText; //holds initial text for the borderlayout center buttons- parallel to cButtons
  private JLabel[][] cLabels;     //borderlayout center labels- parallel to cButtons
  private JLabel statusLabel;    //borderlayout south
  private JLabel highscoreLabel;
  private YahtzeeScore score[];
  private JPanel centerPanel;
  private MousePressListener listener;  
  
  ExFrame(int numPlayers)
  { 
		this.numPlayers = numPlayers;
		this.numGridRows = 21;
		this.buttonWidth = 140;
		this.numCreateButLabCalls = 0;
		this.component = new DiceComponent(buttonWidth*2);
		this.cButtons = new JButton[numGridRows];
		this.cButtonsText = new String[numGridRows];
		this.cLabels = new JLabel[numPlayers][numGridRows];
		this.statusLabel = new JLabel("<html>New game has been started!<br>Please select the dice that you wish to hold or click on a scoring button</html>");
		this.highscoreLabel = new JLabel("Highscores");
		this.score = new YahtzeeScore[numPlayers];
		
		//populate score array
		for(int k = 0; k < numPlayers; k++)
		{
			score[k] = new YahtzeeScore(cButtons,cLabels, statusLabel, component.getDieArray(), cButtonsText, numGridRows, k);
		}
		
		statusLabel.setPreferredSize(new Dimension(buttonWidth*2, 100));
		centerPanel = new JPanel(new GridLayout(numGridRows,numPlayers+1)); //columns based on numPlayers
		
		component.rollDice(true);
		popCenterPanel();
		for(int k = 0; k < numPlayers; k++)
			score[k].reset();
		addListeners();
		
    this.add(centerPanel, BorderLayout.CENTER);
    this.add(component, BorderLayout.PAGE_START);
    this.add(statusLabel, BorderLayout.SOUTH);
    //this.add(highscoreLabel, BorderLayout.EAST);
    this.pack();		
	}
	
	private void addListeners()
	{
    listener = new MousePressListener(component, cButtons, cLabels, statusLabel, numGridRows, score, highscoreLabel, numPlayers);
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
		/*
		try {
  	  listener.popHighscores();
		} catch (FileNotFoundException e) {
		    System.err.println("FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
		    System.err.println("Caught IOException: "+ e.getMessage());
		}*/

		
		for(JButton b:cButtons)
		{
			if (b.isVisible())
				b.addActionListener(listener);
		}		
	}
	
	private void popCenterPanel()
	{
    //create buttons and labels for centerPanel
    createButLab("Roll Dice", "Rolls Left: 3",true);
    createButLab("","",false);
    createButLab("Aces", "Score: ",true);
    createButLab("Twos", "Score: ",true);
    createButLab("Threes", "Score: ",true);
    createButLab("Fours", "Score: ",true);
    createButLab("Fives", "Score: ",true);
    createButLab("Sixes", "Score: ",true);
    createButLab("", "BONUS: +0",false);
    createButLab("", "UPPER TOTAL: 0",false);
    
    //LOWER SECTION
    createButLab("3 of a kind", "Score: ",true);
    createButLab("4 of a kind", "Score: ",true);
   
    createButLab("YAHTZEE!", "Score: ",true);             //15    //12
    createButLab("Chance", "Score: ",true);              //16    //13
    createButLab("", "Yahtzee BONUS: +0",false);        //17    //14
    createButLab("", "LOWER TOTAL: 0",false);          //18    // 15
    createButLab("", "GRAND TOTAL: 0",false);          //19    //16
    
    createButLab("Full House", "Score: ",false);
    createButLab("Sm. Straight", "Score: ",false);
    createButLab("Lg. Straight", "Score: ",false);
    createButLab("NEW GAME!!", "",true);  //20
    
    for (int i = 1; i < numPlayers; i++)
    	cLabels[i][0] = new JLabel("");
    
    //add all center buttons/labels into centerPanel
    boolean allButtonsAdded = false;
    for(int i = 0; i < cButtons.length; i++)
    {
			centerPanel.add(cButtons[i]);
			for(int p = 0; p < numPlayers; p++)					
				centerPanel.add(cLabels[p][i]);
		}
	}
  
  //method to create a button and label, parallel to each other 
  private void createButLab(String butText, String labText, boolean visible)
  {		
		cButtonsText[numCreateButLabCalls] = butText;
		cButtons[numCreateButLabCalls] = new JButton(butText);			
		cButtons[numCreateButLabCalls].setPreferredSize(new Dimension(buttonWidth,25));
		
		for (int i = 0; i < numPlayers; i++)
			cLabels[i][numCreateButLabCalls] = new JLabel(labText);
		
		if (numCreateButLabCalls > 0 && numCreateButLabCalls != numGridRows-1)//if not first button and not new game button, proceed
			cButtons[numCreateButLabCalls].setEnabled(false); 
		if (!visible)
			cButtons[numCreateButLabCalls].setVisible(false); 
		numCreateButLabCalls++;
	}

		
}