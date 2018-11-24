package blackjack;

import java.util.ArrayList;

public class Game {

	private Deck deck;	
	private ArrayList<Card> dealerCards;
	private ArrayList<Card> playerCards;
	private ArrayList<Card> playerCardsSplit;
	
	private int wagerAmount;
	private int totalMoney;
	
	private String win;
	private boolean split;

	public Game(int totalMoney) {
		deck = new Deck();
		deck.shuffle();
		
		this.totalMoney = totalMoney;
		
		playerCards = new ArrayList<Card>();
		dealerCards = new ArrayList<Card>();
		playerCardsSplit = new ArrayList<Card>();
		dealerCards.add(deck.dealCard());
		playerCards.add(deck.dealCard());
		dealerCards.add(deck.dealCard());
		playerCards.add(deck.dealCard());
		
		win = "true";
		split = false;
	}

	public boolean hit(ArrayList<Card> pCards) {
		pCards.add(deck.dealCard());
		if (sum(pCards) > 21) {
			win = "false";
			totalMoney -= wagerAmount;
		}
		return Boolean.parseBoolean(win);
	}

	public int sum(ArrayList<Card> cardList) {
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
		
		if (sum(dealerCards) > 21 || sum(dealerCards) < sum(pCards)) {
			win = "true";
			totalMoney += wagerAmount;
		} else if (sum(dealerCards) > sum(pCards)) {
			win = "false";
			totalMoney -= wagerAmount;
		} else if (sum(dealerCards) == sum(pCards)) {
			win = "draw";
		}
	}

	public void split(ArrayList<Card> pCards) {
		playerCardsSplit.add(pCards.get(1));
		playerCardsSplit.add(deck.dealCard());
		pCards.set(1, deck.dealCard());
		wagerAmount = wagerAmount*2;
		
		split = true;
	}

	public String printCards(ArrayList<Card> pCards) {
		String printCard = "";
		for (int i = 0; i < pCards.size(); i++) {
			printCard = printCard + pCards.get(i).toString() + ", ";
		}
		printCard = printCard.substring(0, printCard.length() - 2);
		return printCard;
	}

	public String printUI() {
		String print = "Total money: " + totalMoney + "\n" +
				"Your bet: " + wagerAmount + "\n" +
				"Dealer's cards: " + dealerCards.get(0) + "\n" +
				"Your cards: " + printCards(playerCards) + "\n" +
				"Sum of cards: " + sum(playerCards) + "\n";
		if (split) {
			print = print + "Splitted cards: " + printCards(playerCardsSplit) + "\n" +
					"Sum of splitted cards: " + sum(playerCardsSplit) + "\n";
		}
		
		return print;
	}

	public String printAll() {
		String print = "Total money: " + totalMoney + "\n" +
				"Your bet: " + wagerAmount + "\n" +
				"Dealer's cards: " + printCards(dealerCards) + "\n" +
				"Sum of cards: " + sum(dealerCards) + "\n" +
				"Your cards: " + printCards(playerCards) + "\n" +
				"Sum of cards: " + sum(playerCards) + "\n";
		
		if (split) {
			print = print + "Splitted cards: " + printCards(playerCardsSplit) + "\n" +
					"Sum of splitted cards: " + sum(playerCardsSplit) + "\n";
		}
		return print;
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
	
	public void setWagerAmount(int wagerAmount) {
		this.wagerAmount = wagerAmount;
	}
	
	public boolean isSplit() {
		return split;
	}

	public int getTotalMoney() {
		return totalMoney;
	}
	
	
}
