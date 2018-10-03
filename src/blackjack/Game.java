package blackjack;

import java.util.ArrayList;

public class Game {
	
	private Deck deck;	
	private ArrayList<Card> dealerCards;
	private ArrayList<Card> playerCards;
	
	public Game() {
		deck = new Deck();
		deck.shuffle();
		playerCards = new ArrayList<Card>();
		dealerCards = new ArrayList<Card>();
		dealerCards.add(deck.dealCard());
		playerCards.add(deck.dealCard());
		dealerCards.add(deck.dealCard());
		playerCards.add(deck.dealCard());
	}
	
	public void hit(ArrayList<Card> cardList) {
		cardList.add(deck.dealCard());
	}
	
	private int sum(ArrayList<Card> cardList) {
		for (int i = 0; i < cardList.size(); i++) {
			
		}
	}
	
}
