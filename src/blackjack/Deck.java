package blackjack;

public class Deck {
	private String suit[] = {"Heart","Spades","Diamonds","Clubs"};
	private String value[] = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	private Card deck[]= new Card[52];
	private Card temp;

	public Deck() {
		for (int i = 0; i < suit.length; i++) {
			int j= 13 * i;
			for (String value : value) {
				deck[j]= new Card();
				deck[j].setCardSuit(suit[i]);
				deck[j].setCardValue(value);
				j++;
			}
		}
	}

	public void shuffle() {
		int a;
		int b;

		for (int j=0 ; j < 50; j++) {
			a = (int) (Math.random()*52);
			b = (int) (Math.random()*52);
			temp = new Card (deck[a]);
			deck[a] = new Card(deck[b]);
			deck[b] = new Card(temp);
		}
	}

	public Card dealCard() {
		temp = new Card(deck[0]);
		for (int i = 0; i < deck.length; i++) {
			if (i == deck.length-1) {
				deck[i] = new Card(temp);
			} else {
				deck[i] = new Card(deck[i+1]);
			}
		}
		return temp;
	}

	public Card[] getDeck() {
		return deck;
	}
}

class Card {

	private String cardSuit;
	private String cardValue;

	public Card() {
	}

	public Card(Card card) {
		this.cardSuit = card.getCardSuit();
		this.cardValue = card.getCardValue();
	}

	public String getCardSuit() {
		return cardSuit;
	}

	public void setCardSuit(String cardSuit) {
		this.cardSuit = cardSuit;
	}

	public String getCardValue() {
		return cardValue;
	}

	public void setCardValue(String cardValue) {
		this.cardValue = cardValue;
	}
}