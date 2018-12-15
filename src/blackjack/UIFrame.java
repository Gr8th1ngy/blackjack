package blackjack;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UIFrame extends JFrame {

	private JButton hit;
	private JButton stand;
	private JButton split;
	private JPanel panel;
	private static JTextArea textArea;

	private static int betValue;
	private static int totalMoney;
	private boolean splitTurn;
	private String nonSplitWin;

	final int FRAME_WIDTH = 800;
	final int FRAME_HEIGHT = 500;

	private final int AREA_ROWS = 30;
	private final int AREA_COLUMNS = 50;

	private static Game game;

	public static void main(String[] args) {

		JFrame frame = new UIFrame();

		frame.setTitle("Welcome to Blackjack");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);

		game(1000);
	}

	public static void game(int totalMoney) {
		game = new Game(totalMoney);
		boolean valid = false;
		while(!valid) {
			String input = JOptionPane.showInputDialog("Please input your bet: Max 100"
					+ " Min 1. You currently have: " + game.getTotalMoney());
			if (input == null || input.isEmpty()) {
				System.exit(0);
			} else if (isInteger(input)) {
				betValue = Integer.parseInt(input);
				if (1 > betValue) {
					JOptionPane.showMessageDialog(null, "Please input a wager of at least 1", "OOPS!", JOptionPane.ERROR_MESSAGE);
				} else if (betValue > game.getTotalMoney()) {
					JOptionPane.showMessageDialog(null, "Your wager is above your total money of " +
							game.getTotalMoney(), "OOPS!", JOptionPane.ERROR_MESSAGE);
				} else if (betValue > 100) {
					JOptionPane.showMessageDialog(null, "Your wager has to be below 100 ", "OOPS!", JOptionPane.ERROR_MESSAGE);
				} else {
					valid = true;
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please input a numeric value", "OOPS!", JOptionPane.ERROR_MESSAGE);
			}
		}

		game.setWagerAmount(betValue);

		textArea.setText(game.printUI());
	}

	public UIFrame() {
		createTextArea();
		createHitButton();
		createStandButton();
		createSplitButton();
		createPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}

	private void createTextArea() {
		textArea = new JTextArea(AREA_ROWS, AREA_COLUMNS);
		textArea.setEditable(false);
		textArea.setFont(new Font("Arial Black", Font.PLAIN, 15));
	}

	private void createPanel() {
		JPanel panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(textArea);

		panel.add(hit);
		panel.add(stand);
		panel.add(split);
		panel.add(textArea);
		panel.add(scrollPane);
		add(panel);
	}

	public static boolean isInteger( String input ) {
		try {
			Integer.parseInt( input );
			return true;
		}
		catch( Exception e ) {
			return false;
		}
	}

	private void createSplitButton() {

		split = new JButton("Split");

		ActionListener listener = new SplitListener();
		split.addActionListener(listener);
	}

	class SplitListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			if (game.getPlayerCards().get(0).equals(game.getPlayerCards().get(1))) {
				if (betValue*2 > totalMoney) {
					JOptionPane.showMessageDialog(null, "You don't have enough money for a split", "Can't split", JOptionPane.ERROR_MESSAGE);
				} else {
					game.split(game.getPlayerCards());
					textArea.setText(game.printUI());
				}
			} else {
				JOptionPane.showMessageDialog(null, "The first two cards have to be the same value to split", "Can't split", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void createHitButton() {
		hit = new JButton("Hit");

		ActionListener listener = new HitListener();
		hit.addActionListener(listener);
	}

	class HitListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if (splitTurn) {
				game.hit(game.getPlayerCardsSplit());
			} else {
				game.hit(game.getPlayerCards());
			}
			textArea.setText(game.printUI());

			if (game.getWin().equals("Busted!")) {
				splitTurn = false;
				stand.doClick();
			}
		}
	}

	private void createStandButton() {
		stand = new JButton("Stand");

		ActionListener listener = new StandListener();
		stand.addActionListener(listener);
	}

	class StandListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (game.getWin().equals("Busted!")) {
				textArea.setText(game.printUI());
			} else {
				game.stand(game.getPlayerCards());
				if (game.isSplit() && !splitTurn) {
					nonSplitWin = new String(game.getWin());
					splitTurn = true;
				} else if (game.isSplit() && splitTurn) {
					game.stand(game.getPlayerCardsSplit());
					splitTurn = false;
				}
			}

			if (!splitTurn) {
				textArea.setText(game.printAll());

				String again[] = {"No", "Yes"};
				int play = 0;
				totalMoney = game.getTotalMoney();
				if (totalMoney <= 0) {
					play = JOptionPane.showOptionDialog(null
							,"You got beaten by a bot dealer that knows one move :p "
									+ "Restart the game?"
									, "Game over!"
									, JOptionPane.PLAIN_MESSAGE
									, JOptionPane.DEFAULT_OPTION
									, null
									, again
									, again[1]);
				} else {
					play = JOptionPane.showOptionDialog(null
							,"Next game?"
							, "Continue"
							, JOptionPane.PLAIN_MESSAGE
							, JOptionPane.DEFAULT_OPTION
							, null
							, again
							, again[1]);
				}
				if (play == 0) {
					System.exit(0);
				} else if (totalMoney == 0){
					game(1000);
				} else {
					game(totalMoney);
				}
			}
		}
	}
}



