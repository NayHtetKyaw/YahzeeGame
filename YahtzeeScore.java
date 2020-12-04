package YahzeeY;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JButton;

//Scoring class for Yahtzee
//Most important calculations happen here

public class YahtzeeScore
{

/**********************
******scores Array*****
[0]  -> chance
[1]  -> aces
[2]  -> twos
[3]  -> threes
[4]  -> fours
[5]  -> fives
[6]  -> sixes
[7]  -> 3 of a kind
[8]  -> 4 of a kind
[9]  -> fullhouse
[10] -> small straight
[11] -> large straight
[12] -> yahtzee
[13] -> upper total
[14] -> lower total
[15] -> grand total
**********************/
	
	private int[] scores = new int[16];//holds scores, 13 + 3 totals at end
	private boolean[] yahtzeeBonus = new boolean[2]; //keeps track of yahtzee bonuses
	private boolean upperBonus = false; // keeps track of upper bonus
	private JButton[] cButtons; // reference to center buttons
	private JLabel[][] cLabels; // reference to center labels
	private JLabel statusLabel; // reference to status label at BorderLayout.SOUTH
	private Die[] dice; // reference to die array
	private String[] cButtonsText; // reference to center button initial texts
	private int numGridRows;
	private int[] faceValues; // holds # amount of each face respectively 0-5 == (1-6)-----populated by checkDice(), then used by score()
	private boolean printedBonus = false;
	private int player;
	
	
	YahtzeeScore(JButton[] buttons, JLabel[][] labels, JLabel status, Die[] dice, String[] cButtonsText, int numGridRows, int player)
	{
		this.cButtons = buttons;
		this.cLabels = labels;
		this.statusLabel = status;
		this.dice = dice;
		this.cButtonsText = cButtonsText;
		this.numGridRows = numGridRows;
		this.player = player;
	}
	
	//check an array if it has the same int 'n' number of times or more
	//also check for sequences
	private boolean inArray(int n, int[] array, String find)
	{// if find = "exact", 'n' needs to be exact number in array
		int seqCounter = 0;
		for (int arr : array)
		{
			if ((find.equals("exact") && arr == n) || (find.equals("min") && arr >= n))
				return true;
		}

		if (find.equals("sequence"))
		{//sequence of 'n', limited possibilities
			if ( n == 4 && (
						(array[0]>0 && array[1]>0 && array[2]>0 && array[3]>0) ||
						(array[1]>0 && array[2]>0 && array[3]>0 && array[4]>0) ||
						(array[2]>0 && array[3]>0 && array[4]>0 && array[5]>0)
					)) return true;
			else if ( n == 5 && (
						(array[0]>0 && array[1]>0 && array[2]>0 && array[3]>0 && array[4]>0) ||
						(array[1]>0 && array[2]>0 && array[3]>0 && array[4]>0 && array[5]>0)
					)) return true;
		}
		return false;
	}
	
	//enable a center button and set its text;
	private boolean cButtonEnable(int bIndex, String bText)
	{
		cButtons[bIndex].setEnabled(true);
		if (bText != null)
			cButtons[bIndex].setText(bText);
		return true;
	}
	
	//method which enables buttons that can be scored with the current active dice
	public boolean checkDice(int numRollsLeft)
	{
		boolean canScore = false;
		faceValues = new int[6];
		/*
		////TESTING FOR YAHTZEE EVERY TIME
		if (numRollsLeft == 2)
		{
			dice[0].faceValue = 5;
			dice[1].faceValue = 5;
			dice[2].faceValue = 5;
			dice[3].faceValue = 5;
			dice[4].faceValue = 5;
		}
		if (numRollsLeft == 1)
		{
			dice[0].faceValue = 6;
			dice[1].faceValue = 6;
			dice[2].faceValue = 6;
			dice[3].faceValue = 6;
			dice[4].faceValue = 6;
		}
		if (numRollsLeft == 0)
		{
			dice[0].faceValue = 4;
			dice[1].faceValue = 4;
			dice[2].faceValue = 4;
			dice[3].faceValue = 4;
			dice[4].faceValue = 4;
		}*/
		////**************************////
		
		//stores/counts how many of each die faces there are.
		for (int i=0; i<5; i++)
		{
			faceValues[dice[i].getFaceValue()-1]++;
		}
		
		//total value of all 5 dice
		int total5 = (faceValues[0])+(faceValues[1]*2)+(faceValues[2]*3)+(faceValues[3]*4)+(faceValues[4]*5)+(faceValues[5]*6);
		
		//set all scoring buttons to initial disabled
		for (int i=2; i<=numGridRows-3; i++)
		{
			cButtons[i].setEnabled(false); 
			cButtons[i].setText(cButtonsText[i]); //reset button to initial text
		}
		
		//check upper section
		if (faceValues[0] > 0 && scores[1] == -1)
			canScore = cButtonEnable(2, "Aces (+"+faceValues[0]+")");
		if (faceValues[1] > 0 && scores[2] == -1)
			canScore = cButtonEnable(3, "Twos (+"+faceValues[1]*2+")");
		if (faceValues[2] > 0 && scores[3] == -1)
			canScore = cButtonEnable(4, "Threes (+"+faceValues[2]*3+")");
		if (faceValues[3] > 0 && scores[4] == -1)
			canScore = cButtonEnable(5, "Fours (+"+faceValues[3]*4+")");
		if (faceValues[4] > 0 && scores[5] == -1)
			canScore = cButtonEnable(6, "Fives (+"+faceValues[4]*5+")");
		if (faceValues[5] > 0 && scores[6] == -1)
			canScore = cButtonEnable(7, "Sixes (+"+faceValues[5]*6+")");
			
		//check 3 of a kind
		if (inArray(3, faceValues, "min") && scores[7] == -1)
			canScore = cButtonEnable(10, "3 of a kind (+"+total5+")");
			
		//check 4 of a kind
		if (inArray(4, faceValues, "min") && scores[8] == -1)
			canScore = cButtonEnable(11, "4 of a kind (+"+total5+")");
			
//		//check full house
//		if (inArray(3, faceValues, "exact") && inArray(2, faceValues, "exact") && scores[9] == -1)
//			canScore = cButtonEnable(12, "Full House (+25)");
//			
//		//check small straight
//		if (inArray(4, faceValues, "sequence") && scores[10] == -1)
//			canScore = cButtonEnable(13, "Sm. Straight (+30)");
//
//		//check large straight
//		if (inArray(5, faceValues, "sequence") && scores[11] == -1)
//			canScore = cButtonEnable(14, "Lg. Straight (+40)");

		//check Yahtzee (5 of a kind) and bonus
		if (inArray(5, faceValues, "exact"))
		{
			//if main yahtzee not scored
			if (scores[12] == -1)
				canScore = cButtonEnable(12, "YAHTZEE! (+50)");
			else if (scores[12] != 0)
			{//no bonus if yahtzee has 0 points
				//if score of the facevalue of yahtzee dice is already set, then can select to score as a filler to any lower section
				if (scores[dice[0].getFaceValue()] != -1)
				{
					for (int i=9; i<12; i++) 
					{
						if (scores[i] == -1)
						{
							canScore = cButtonEnable(i+3, null);
						}
					}
				}
			}
				
		}		
		//check chance
		if (scores[0] == -1)
			canScore = cButtonEnable(13, "Chance (+"+total5+")");
		
		//if you cant score and number of rolls left is 0, enable buttons to choose a score to fill in as 0
		if (!canScore && numRollsLeft == 0)
		{
			for (int i=1; i<13; i++) //skipping 0, because chance would automatically have to be set to be in this situation
			{
				if (scores[i] == -1)
				{
					if (i <=6)//checking upper section
						cButtons[i+1].setEnabled(true);
					else//lower
						cButtons[i+3].setEnabled(true);
				}
			}
		}
		return canScore;
	}//end of checkDice method
	
	//method to update the scores int[] called from score()
	private void updateScore(int[] points, boolean canScore, int bIndex, int scoreIndex, int pointsToScore)
	{
		//upper section scored, multiply score by facevalue of scored section
		if (bIndex == scoreIndex+1 && pointsToScore == -1)
			scores[scoreIndex] = (canScore) ? (faceValues[scoreIndex-1]*scoreIndex) : 0;
		else
			scores[scoreIndex] = (canScore) ? pointsToScore : 0;
		points[0] += scores[scoreIndex];
		
		cLabels[player][bIndex].setText("Score: "+scores[scoreIndex]);
	}
	
	//method that updates everything score related, called upon pressing a scoring button
	public int[] score(Object buttonO, boolean canScore)
	{
		JButton button = (JButton)buttonO;
		int[] points = new int[3]; // int[] that will be returned containing amount of points scored [0]->points [1]->upper bonus [2]->yahtzeebonus
		int bIndex = -1; // index of button clicked, also respective label
		
		//total of all 5 dice
		int total5 = (faceValues[0])+(faceValues[1]*2)+(faceValues[2]*3)+(faceValues[3]*4)+(faceValues[4]*5)+(faceValues[5]*6);

		//check Yahtzee (5 of a kind) and award bonus if 2nd+ yahtzee
		if (inArray(5, faceValues, "exact"))
		{
			if ((!yahtzeeBonus[0] || !yahtzeeBonus[1]) && scores[12] > 0)
			{
				if (!yahtzeeBonus[0])
					yahtzeeBonus[0] = true;
				else
					yahtzeeBonus[1] = true;
				points[2] += 100;
			}
		}		
		
		//check for button/label index
		for (int i=0; i<cButtons.length; i++)
		{
			if (cButtons[i] == button)
			{
				bIndex = i;
				break;
			}
		}
		
		//update scores and labels
		switch(bIndex)
		{
			case 2: //aces		
			case 3: //twos
			case 4: //threes
			case 5: //fours
			case 6: //fives
			case 7: //sixes			
				updateScore(points, canScore, bIndex, bIndex-1, -1);
				break;
					
			case 10: //3 of a kind
			case 11: //4 of a kind
				updateScore(points, canScore, bIndex, bIndex-3, total5);
				break;
				
//			case 12: //full house
//				updateScore(points, canScore, bIndex, bIndex-3, 25);
//				break;
//			case 13: //small straight
//				updateScore(points, canScore, bIndex, bIndex-3, 30);
//				break;
//			case 14: //large straight
//				updateScore(points, canScore, bIndex, bIndex-3, 40);
//				break;
			case 12: //yahtzee
				updateScore(points, canScore, bIndex, bIndex-3, 50);
				break;
			case 13: //chance
				updateScore(points, canScore, bIndex, 0, total5);
				break;
		}
		
		//add up totals
		scores[13] = 0; 
		//scores[1]+scores[2]+scores[3]+scores[4]+scores[5]+scores[6];
		for (int i=1; i<=6; i++)
		{
			if (scores[i] != -1)
				scores[13] += scores[i];
		}
		//check for bonus in upper section
		if (scores[13] >= 63)
		{
			upperBonus = true;
			cLabels[player][8].setText("BONUS: +35");
			scores[13] += 35;
			if (!printedBonus)
			{
				printedBonus = true;
				points[1] += 35;
			}
			
		}
		
		scores[14] = 0;
		if (scores[0] != -1)
			scores[14] += scores[0];
		//scores[7]+scores[8]+scores[9]+scores[10]+scores[11]+scores[12]+scores[0];
		for (int i=7; i<=12; i++)
		{
			if (scores[i] != -1)
				scores[14] += scores[i];
		}
		
		//add in any yahtzee bonuses
		int yahBonus = 0;
		if (yahtzeeBonus[0])
			yahBonus+=100;
		if (yahtzeeBonus[1])
			yahBonus+=100;
		scores[14] += yahBonus;
		scores[15] = scores[13]+scores[14];
		
		//update total labels
		cLabels[player][9].setText("UPPER TOTAL: "+scores[13]);
		cLabels[player][14].setText("Yahtzee BONUS: +"+yahBonus);
		cLabels[player][15].setText("LOWER TOTAL: "+scores[14]);
		cLabels[player][16].setText("GRAND TOTAL: "+scores[15]);
		
		return points;
	}//end of score method
	
	//method to reset all scores and labels back to initial text
	public void reset()
	{
		for (int i=0; i<13; i++)
			scores[i] = -1;
		scores[13] = 0;
		scores[14] = 0;
		scores[15] = 0;
		yahtzeeBonus[0] = false;
		yahtzeeBonus[1] = false;
		upperBonus = false;
		printedBonus = false;
		for (int i=2; i<numGridRows; i++)
		{
			switch(i)
			{
				case 8:
					cLabels[player][i].setText("BONUS: +0");
					break;
				case 9:
					cLabels[player][i].setText("UPPER TOTAL: 0");
					break;
				case 14:
					cLabels[player][i].setText("Yahtzee BONUS: +0");
					break;
				case 15:
					cLabels[player][i].setText("LOWER TOTAL: 0");
					break;
				case 16:
					cLabels[player][i].setText("GRAND TOTAL: 0");
					break;
				case 17:
					cLabels[player][i].setText("");
					break;
				case 18:
					cLabels[player][i].setText("");
					break;
				case 19:
					cLabels[player][i].setText("");
					break;
				case 20: 
					cLabels[player][i].setText("Reset");
					break;
				default:
					cLabels[player][i].setText("Score: ");
					break;
			}
			
		}
	}
	
	//return grand total score
	public int getGrandTotal()
	{
		return scores[15];
	}
}