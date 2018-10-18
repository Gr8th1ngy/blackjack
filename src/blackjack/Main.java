package blackjack;

import java.util.Scanner;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		
		Game game = new Game();
		
		JFrame frame = new UIFrame();
		
		frame.setTitle("Welcome to blackjack");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		
		System.out.println("Dealer's cards: " + game.print(game.getDealerCards()));
		System.out.println("Your cards: " + game.print(game.getPlayerCards()));

	}
}


