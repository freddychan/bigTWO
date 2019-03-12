import java.util.*;
import java.util.Scanner;

public class Player {
	Hand hand = new Hand();
	List<Card> cardsToPlay = new ArrayList<Card>();

	public Player() {}
	
	public Hand getHand() {
		return hand;
	}
	
	/**
	* Gets play that player wants to make in the game.
	*
	* @param game	The current state of the game.
	*
	* @return List <Card>
	*
	* Returns a list of cards that player wants to play. A card is entered as two characters,
	* first one is the number of the card and second is the suit (e.g. 2d,10s,jc,qh). Multiple
	* cards are separated by commas. Checks if hand contains all cards to be played and that
	* there are no duplicates or returns getPlay.
	*
	*/
	public List<Card> getPlay(Game game) {
		cardsToPlay.clear();
		List<Card> middleStack = game.getMiddleStack();
		
		System.out.println("Middle Stack:");
		for (Card c : middleStack) {
			System.out.println(c.getNumber() + "," + c.getSuit());
		}
		
		System.out.println("Hand:");
		hand.printHand();
		System.out.println();
		System.out.println("Type exit to end game at any time.");
		System.out.println("Type pass to end your turn without playing a card.");
		System.out.println();
        System.out.println("Type in the cards you wish to play as its number and first letter of suit\nwith commas separating them (2d, 10c, qs, ah):");
		System.out.println("Poker hands are allowed: Straight < Flush < Full House < Four of a Kind.");
		
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		if (input.equalsIgnoreCase("pass")) {
			return null;
		}
		if (input.equalsIgnoreCase("exit")) {
			System.exit(1);
		}
		else {
			String[] inputArray = input.toLowerCase().replace(" ", "").split(",");
			if (inputArray.length >= 6) {
				System.out.println("Invalid play, too many cards.");
				return getPlay(game);
			}
			for (String i : inputArray) {
				if (i.length() < 2) {
					System.out.println("Invalid card input.");
					return getPlay(game);
				}
				char n = i.charAt(0);
				char s = i.charAt(1);
				
				int number = 0;
				switch (n) {
					case 'j':
						number = 11;
						break;
					case 'q':
						number = 12;
						break;
					case 'k':
						number = 13;
						break;
					case 'a':
						number = 14;
						break;
					default:
						if (n == '1' && s == '0') {
							number = 10;
							s = i.charAt(2);
						}
						else {
							number = n - '0';
						}
						break;
				}
				if (number > 14 || number < 2) {
					System.out.println("Invalid number entered for card. Please choose from 2-10,j,q,k,a.");
					return getPlay(game);
				}
				Card c = new Card(s, number);
				cardsToPlay.add(c);
			}
			if (hand.containsAllCards(cardsToPlay) == false || isDuplicateCards() == true) {
					System.out.println("One or more cards are not in your hand or playing duplicates.");
					cardsToPlay.clear();
					return getPlay(game);
			}
		}
		return cardsToPlay;
	}
	
	/**
	* Returns a boolean if cards being played contain duplicates.
	*
	* @return boolean
	*
	* Returns true if no duplicates are found in cards to be played and false if duplicates
	* exist.
	*
	*/
	public boolean isDuplicateCards() {
		Set<Card> set = new HashSet<Card>(cardsToPlay);
		if (set.size() < cardsToPlay.size()) return true;
		else return false;
	}
	
}