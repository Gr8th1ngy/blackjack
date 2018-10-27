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
	private JTextArea textArea;
	
	final int FRAME_WIDTH = 800;
	final int FRAME_HEIGHT = 500;
	
	private final int AREA_ROWS = 30;
	private final int AREA_COLUMNS = 50;
	
	private Game game;

	public static void main(String[] args) {
		
		JFrame frame = new UIFrame();
		
		frame.setTitle("Welcome to Blackjack");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);

	}
	
	public UIFrame() {

		game = new Game();
		
		createHitButton();
		createStandButton();
		createTextArea();
		createPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
	
	private void createTextArea() {
		textArea = new JTextArea(AREA_ROWS, AREA_COLUMNS);
		textArea.setEditable(false);
		textArea.setText(game.printUI());
		textArea.setFont(new Font("Arial Black", Font.PLAIN, 15));
	}
	
	private void createPanel() {
		split = new JButton("Split");
		JPanel panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		panel.add(hit);
		panel.add(stand);
		panel.add(split);
		panel.add(textArea);
		panel.add(scrollPane);
		add(panel);
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
				textArea.append("BUSTED!");
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
		
	}
}


