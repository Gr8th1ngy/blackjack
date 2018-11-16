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
				game.split(game.getPlayerCards());
				textArea.setText(game.printUI());
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

			boolean bust = false;
			bust = game.hit(game.getPlayerCards());

			textArea.setText(game.printUI());

			if (!bust) {
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

			if (!Boolean.parseBoolean(game.getWin())) {
				textArea.append("Busted!");
			} else {
				game.stand(game.getPlayerCards());
				textArea.setText(game.printAll());

				if (game.getWin() == "draw") {
					textArea.append("Push");
				} else if (Boolean.parseBoolean(game.getWin())) {
					textArea.append("You Win!");
				} else {
					textArea.append("You lose!");
				}
			}
			String again[] = {"No", "Yes"};
			int play = JOptionPane.showOptionDialog(null
					,"Next game?"
					, "Continue"
					, JOptionPane.PLAIN_MESSAGE
					, JOptionPane.DEFAULT_OPTION
					, null
					, again
					, again[1]);
			if (play == 0) {
				System.exit(0);
			} else {
				totalMoney = game.getTotalMoney();
				game(totalMoney);
			}
		}

	}
}


