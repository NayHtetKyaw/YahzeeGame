package YahzeeY;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Yahtzee
{

  public static void main(String[] args)
  {
//		Object[] options = {"1","2","3","4","5"};
//		String s = (String)JOptionPane.showInputDialog(
//												null,
//												"How many players will be playing this game?\n",
//												"Customized Dialog",
//												JOptionPane.PLAIN_MESSAGE,
//												null,
//												options,
//												"1");

		//If a string was returned, say so.
		int numPlayers = 1;
//		if (s != null && s.length() == 1) 
//			numPlayers = Integer.parseInt(s);
//		if (numPlayers < 1 || numPlayers > 5)
//			System.exit(0);


		//int numPlayers = Integer.parseInt(JOptionPane.showInputDialog("How many players will be playing this game?"));
		
    JFrame frame = new ExFrame(numPlayers);
    frame.setSize(150+150*numPlayers,700);
    frame.setTitle("YAHTZEE!");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true); 	
  }
}