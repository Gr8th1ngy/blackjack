package blackjack;

import javax.swing.*;

public class UIFrame extends JFrame{
	
	private JButton button;
	private JPanel panel;
	final int FRAME_WIDTH = 300;
	final int FRAME_HEIGHT = 400;
	
	public UIFrame() {
		createComponents();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
	
	private void createComponents() {
		button = new JButton("Click me!");
		JPanel panel = new JPanel();
		panel.add(button);
		add(panel);
	}
}
