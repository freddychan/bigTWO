import java.util.*;

public class Deck {
	List<Card> deck;

	
	public Deck() {
		deck = new ArrayList<Card>();
		for (Suit s : Suit.values()) {
			for (Number n : Number.values()) {
				deck.add(new Card(s, n));
			}
		}
		
	}
	
	/**
	* Shuffle the deck.
	*
	* The resulting deck should return a randomly permuted list of cards.
	*/

	public void shuffleDeck() {
		Collections.shuffle(deck);
	}

	public Card dealOneCard() {
		return deck.remove(0);
	}
	
	public void printCards() {
		for (Card card : deck) {
			System.out.println(card.getNumber()+ "," + card.getSuit());
		}
	}
	
	/**
	* Change value of all 2 cards for Big Two.
	*
	* Two should become the biggest card in this game.
	*/
	public void bigTwo() {
		for (Card c : deck) {
			if (c.number.getNumberRanking() == 2) {
				c.number.setNumberRanking(15);
			}
		}
	}
	
}