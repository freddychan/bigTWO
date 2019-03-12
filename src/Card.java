import java.util.*;

public class Card implements Comparable<Card>{
	public Suit suit;
	public Number number;
	
	public static final Card SMALLEST_CARD = new Card(Suit.DIAMONDS, Number.THREE);
	
	public Card(Suit suit, Number number) {
		this.suit = suit;
		this.number = number;
		
	}
	
	public Card(char suit, int number) {		
        switch (suit) {
            case 'd':
                this.suit = Suit.DIAMONDS;
                break;
            case 'c':
                this.suit = Suit.CLUBS;
                break;
            case 'h':
                this.suit = Suit.HEARTS;
                break;
            case 's':
                this.suit = Suit.SPADES;
                break;
        }
        switch (number) {
			case 2:
				this.number = Number.TWO;
				break;
			case 3:
				this.number = Number.THREE;
				break;
			case 4:
				this.number = Number.FOUR;
				break;
			case 5:
				this.number = Number.FIVE;
				break;
			case 6:
				this.number = Number.SIX;
				break;
			case 7:
				this.number = Number.SEVEN;
				break;
			case 8:
				this.number = Number.EIGHT;
				break;
			case 9:
				this.number = Number.NINE;
				break;
			case 10:
				this.number = Number.TEN;
				break;
			case 11:
				this.number = Number.JACK;
				break;
			case 12:
				this.number = Number.QUEEN;
				break;
			case 13:
				this.number = Number.KING;
				break;
			case 14:
				this.number = Number.ACE;
				break;
		}	
    }
	
	public Suit getSuit() {
		return suit;
	}
	
	public Number getNumber() {
		return number;
	}
	
	/**
	* Compares two cards for the higher card and returns a boolean.
	*
	* @param card	The card to be compared to.
	*
	* @return boolean
	*
	* Should return true if this card is bigger than the card being compared to and
	* false if it is smaller.
	*
	*/
	public boolean isHigher(Card card) {
		if (this.compareTo(card) == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Card))
			return false;
		Card other = (Card) o;
		return other.suit == suit && other.number == number;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(suit, number);
	}
	
	@Override
	public int compareTo(Card c) {
		if (c == null)
			return 1;
		if (this.getNumber().equals(c.getNumber())) {
			if (this.suit.getSuitRanking() > c.suit.getSuitRanking()) return 1;
			else return 0;
		}
		else {
			if (this.number.getNumberRanking() > c.number.getNumberRanking()) return 1;
			else return 0;
		}
	}
	
}