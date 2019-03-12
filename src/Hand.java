import java.util.*;

public class Hand{
	private List<Card> hand;
	
	public Hand() {
		hand = new ArrayList<Card>();
	}
	
	public void addCardToHand(Card card) {
		hand.add(card);
	}
	
	public void printHand() {
		for (Card card : hand) {
			System.out.println(card.getNumber()+ "," + card.getSuit());
		}
	}
	
	public boolean isEmptyHand() {
		return hand.isEmpty();
	}
	
	public boolean containsCard(Card card) {
		return hand.contains(card);
	}
	
	/**
	* Checks if hand contains all cards in a list of cards.
	*
	* @param cards    A list of cards to be found in hand.
	*
	* @return boolean
	*
	* Returns true if hand contains all cards in given list and false if hand is missing
	* any cards from the given list.
	*
	*/
	public boolean containsAllCards(List<Card> cards) {
		boolean hasAllCards = false;
		for (Card c : cards) {
			if (hand.contains(c) == false) hasAllCards = false;
			else hasAllCards = true;
		}
		return hasAllCards;
	}
			
	public void removeCards(List<Card> cards) {
		for (Card c : cards) {
			hand.remove(c);
		}
	}
	
	/**
	* Sorts hand in ascending order with numbers.
	*
	* Returns a sorted hand.
	*
	*/
	public void sort() {
		Collections.sort(hand, (Card c1, Card c2) -> c1.number.getNumberRanking() - c2.number.getNumberRanking());
	}
	
}