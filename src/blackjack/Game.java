package blackjack;

import java.util.ArrayList;

public class Game {
	
	private Deck deck;	
	private ArrayList<Card> dealerCards;
	private ArrayList<Card> playerCards;
	private ArrayList<Card> playerCardsSplit;
	private String win = "true";
	
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
	
	public boolean hit(ArrayList<Card> pCards) {
		pCards.add(deck.dealCard());
		if (sum(pCards) > 21) {
			win = "false";
		}
		return Boolean.parseBoolean(win);
	}
	
	private int sum(ArrayList<Card> cardList) {
		int sum = 0;
		int aceCount = 0;
		for (int i = 0; i < cardList.size(); i++) {
			if (cardList.get(i).getCardValue().equals("A")) {
				sum = sum + 11;
				aceCount++;
			} else if (cardList.get(i).getCardValue().equals("J") 
					|| cardList.get(i).getCardValue().equals("Q") 
					|| cardList.get(i).getCardValue().equals("K")) {
				sum = sum + 10;
			} else {
				sum = sum + Integer.parseInt(cardList.get(i).getCardValue());
			}
		}
		while (aceCount > 0) {
			if (sum > 21) {
				sum = sum - 10 * aceCount;
			}
			aceCount--;
		}
		return sum;
	}
	
	public void stand(ArrayList<Card > pCards) {
		while (sum(dealerCards) < 17){
			dealerCards.add(deck.dealCard());
		}
		if (sum(dealerCards) > sum(pCards)) {
			win = "false";
		} else if (sum(dealerCards) == sum(pCards)) {
			win = "draw";
		}
	}
	
	public void split(ArrayList<Card> pCards) {
		playerCardsSplit.add(pCards.get(0));
		playerCardsSplit.add(deck.dealCard());
		pCards.set(0, deck.dealCard());
	}
	
	public String print(ArrayList<Card> pCards) {
		String printCard = "[";
		for (int i = 0; i < pCards.size(); i++) {
			printCard = printCard + pCards.get(i).getCardValue() 
					+ " of " + pCards.get(i).getCardSuit() + ", ";
		}
		printCard = printCard.substring(0, printCard.length() - 2) + "]";
		return printCard;
	}

	public ArrayList<Card> getDealerCards() {
		return dealerCards;
	}

	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}

	public ArrayList<Card> getPlayerCardsSplit() {
		return playerCardsSplit;
	}

	public String getWin() {
		return win;
	}
}
