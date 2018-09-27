package blackjack;

public class Main {

	public static void main(String[] args) {
		Deck deck1 = new Deck();
		for (int i = 0; i < 52; i++) {
			System.out.print(deck1.getDeck()[i].getCardSuit() + " ");
			System.out.print(deck1.getDeck()[i].getCardValue() + ", ");
		}
		deck1.shuffle();
		System.out.println();
		for (int i = 0; i < 52; i++) {
			System.out.print(deck1.getDeck()[i].getCardSuit() + " ");
			System.out.print(deck1.getDeck()[i].getCardValue() + ", ");
		}

	}
}


