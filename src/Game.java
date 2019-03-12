import java.util.*;
import java.util.Arrays;

public class Game {
	
	private Deck deckOfCards;
	private Player[] players;
	private List<Card> middleStack;
	private int turn;
	private int totalTurns;
	private List<Player> passCount;
	
	public Game() {
		newGame();
	}
	
	/**
	* Starts a new game.
	*
	* Creates a game with 4 players and deals 13 cards to each player.
	*
	*/
	private void newGame() {
		middleStack = new ArrayList<Card>();
		players = new Player[4];
		
		deckOfCards = new Deck();
		deckOfCards.bigTwo();
		deckOfCards.shuffleDeck();
		
		for (int i = 0; i < 4; i++) {
			players[i] = new Player();
			
			for (int c = 0; c < 13; c++) {
				players[i].getHand().addCardToHand(deckOfCards.dealOneCard());
			}
		}
		
		for (int j = 0; j < 4; j++) {
			Player p = players[j];
			//System.out.println("\nPlayer " + (j+1) + "'s Hand:");
			//p.getHand().printHand();
		}
	}
	
	/**
	* Starts the game.
	*
	* Finds player with a THREE of DIAMONDS in their hand to go first. Each player takes turns making
	* a valid play or can choose to pass. If they pass, they must wait until next round starts to play again.
	*
	*/
	public void startGame() {
		passCount = new ArrayList<Player>();
		int first = isGoingFirst(players);
		turn = first;
		totalTurns = 0;
		
		while (true) {
			Player currentPlayer = players[turn];
			
			if (passCount.contains(currentPlayer)) {
				turn++;
				totalTurns++;
				turn = turn % 4;
				continue;
			}
			
			System.out.println ("================== Player " + (turn+1) + "'s Turn ==================");
			do {
				List<Card> cardsPlayed = currentPlayer.getPlay(this);
				
				if (cardsPlayed == null) {
					System.out.println("Passed Turn.");
					passCount.add(currentPlayer);
					break;
				}
				if (isValidPlay(cardsPlayed) == true) {
					middleStack = cardsPlayed;
					System.out.println("Cards Played: ");
					for (Card c : cardsPlayed) {
						System.out.println(c.getNumber() + "," + c.getSuit());
					}
					currentPlayer.getHand().removeCards(cardsPlayed);
					break;
				}
				else {
					System.out.println("Cards in middle are bigger or invalid play.");
				}
			} while (true);
			
			if (currentPlayer.getHand().isEmptyHand() == true) {
				System.out.println("Player " + (turn+1) + " has won!");
				break;
			}
			
			turn++;
			totalTurns++;
			turn = turn % 4;
			
			if (passCount.size() == 3) {
				int newTurn = isPlayerPassed(players);
				turn = newTurn;
				middleStack.clear();
				passCount.clear();
				System.out.println("Next Round.");
			}
		}
	}
	
	public List<Card> getMiddleStack() {
		return middleStack;
	}
	
	/**
	* Finds player with THREE of DIAMONDS in their hand.
	*
	* @param players	List of players in the game.
	*
	* @return int
	*
	* Returns an int for the player that will be going first.
	*
	*/
	private int isGoingFirst(Player[] players) {
		int f = 0;
		for (int i = 0; i < 4; i++) {
			if (players[i].getHand().containsCard(Card.SMALLEST_CARD)) f = i;
		}
		return f;
	}
	
	/**
	* Checks if play being made by player is a valid one.
	*
	* @param cards	List of cards being played.
	*
	* @return boolean
	*
	* Returns true if play is valid and false if invalid. For poker hand strengths, it goes
	* in the order of Straight < Flush < Full House < Four of a Kind. If everyone has passed,
	* player who won the round can start with any valid play. First play must have THREE of DIAMONDS.
	*
	*/
	private boolean isValidPlay(List<Card> cards) {
		if (middleStack.size() == 0 && totalTurns > 0) { 
			return true;
		}
		if (middleStack.size() == 0 && totalTurns == 0) {
			if (cards.contains(Card.SMALLEST_CARD)) {
				return true;
			}
			else {
				System.out.println("First turn must play THREE of DIAMONDS.");
				return false;
			}
		}
		else if (middleStack.size() == 1) {
			if (cards.get(0).isHigher(middleStack.get(0)) == true || cards.get(0).equals(middleStack.get(0)) && cards.size() == 1) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (middleStack.size() == 2) {
			if (isSameNumber(cards) == true && cards.size() == 2) {
				if (highestCard(cards).isHigher(highestCard(middleStack)) == true || cards.get(0).equals(middleStack.get(0)) ) {
					return true;
				}
				else 
					return false;
			}
		}
		else if (middleStack.size() == 3) {
			if (isSameNumber(cards) == true && cards.size() == 3) {
				if (cards.get(0).isHigher(middleStack.get(0)) == true || cards.get(0).equals(middleStack.get(0))) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		else if (middleStack.size() == 4) {
			if (isSameNumber(cards) == true && cards.size() == 4) {
				if (cards.get(0).isHigher(middleStack.get(0)) == true || cards.get(0).equals(middleStack.get(0))) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		else if (middleStack.size() == 5) {
			if (isStraight(middleStack)) {
				if (isFlush(cards) == true || isFullHouse(cards) == true || isFourOfAKind(cards) == true) {
					return true;
				}
				else if (isStraight(cards) == true) {
					if (cards.get(4).isHigher(middleStack.get(4)) == true) {
						return true;
					}
				}
				else {
					return false;
				}
			}
			if (isFlush(middleStack)) {
				if (isFullHouse(cards) == true || isFourOfAKind(cards) == true) {
					return true;
				}
				else if (isFlush(cards) == true) {
					if (cards.get(0).suit.getSuitRanking() > middleStack.get(0).suit.getSuitRanking() == true || cards.get(0).equals(middleStack.get(0))) {
						return true;
					}
				}
				else {
					return false;
				}
			}
			if (isFullHouse(middleStack)) {
				if (isFourOfAKind(cards) == true) {
					return true;
				}
				else if (isFullHouse(cards) == true) {
					if (cards.get(0).isHigher(middleStack.get(0)) == true || cards.get(0).equals(middleStack.get(0))) {
						return true;
					}
				} 
				else {
					return false;
				}
			}
			if (isFourOfAKind(middleStack)) {
				if (cards.get(0).isHigher(middleStack.get(0)) == true) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	* Checks if all cards in a given list have the same number.
	*
	* @param cards	List of cards.
	*
	* @return boolean
	*
	* Returns true if list of cards are all the same number and false otherwise.
	*
	*/
	public boolean isSameNumber(List<Card> cards) {
		Number number = cards.get(0).getNumber();
		for (Card c : cards) {
			if (c.getNumber() != number) return false;
		}
		return true;
	}
	
	/**
	* Returns the highest card from a list of cards.
	*
	* @param cards	List of cards.
	*
	* @return Card
	*
	* Checks a list of cards and returns the highest card found.
	*
	*/
	public Card highestCard(List<Card> cards) {
		Card highest = cards.get(0);
		for (Card c : cards) {
			if (c.isHigher(highest)) highest = c;
		}
		return highest;
	}
	
	/**
	* When three players have passed, this returns the int of player who will start next round.
	*
	* @param players	List of players.
	*
	* @return int
	*
	* Returns int of player who will be going first next round.
	*
	*/
	public int isPlayerPassed(Player[] players) {
		int newTurn = 0;
		for (int i = 0; i < players.length; i++) {
			if (passCount.contains(players[i]) == false) {
				newTurn = i;
			}
		}
		return newTurn;
	}
	
	/**
	* Checks if list of cards is a Straight.
	*
	* @param cards	List of cards to check.
	*
	* @return boolean
	*
	* Returns true if list of cards is a Straight and false if not.
	*
	*/
	private boolean isStraight(List<Card> cards) {
		if (cards.size() != 5) return false;
		
		for (int i = 0; i < cards.size()-1; i++) {
			if (cards.get(i).number.getNumberRanking() + 1 != cards.get(i+1).number.getNumberRanking()) 
				return false;
		}
		return true;
	}
	
	/**
	* Checks if list of cards is a Flush.
	*
	* @param cards	List of cards to check.
	*
	* @return boolean
	*
	* Returns true if list of cards is a Flush and false if not.
	*
	*/
	private boolean isFlush(List<Card> cards) {
		if (cards.size() != 5) return false;
		for (int i = 0; i < cards.size()-1; i++) {
			if (cards.get(i).suit.getSuitRanking() != cards.get(i+1).suit.getSuitRanking())
				return false;
		}
		return true;
	}
	
	/**
	* Checks if list of cards is a Full House.
	*
	* @param cards	List of cards to check.
	*
	* @return boolean
	*
	* Returns true if list of cards is a Full House and false if not.
	*
	*/
	private boolean isFullHouse(List<Card> cards) {
		if (cards.size() != 5) return false;
		
		for (int i = 0; i < cards.size()-3; i++) {
			if (cards.get(i).number.getNumberRanking() != cards.get(i+1).number.getNumberRanking()) {
				System.out.println("Full House: 3 cards of same number followed by 2 of matching number.");
				return false;
			}
		}
		if (cards.get(3).number.getNumberRanking() != cards.get(4).number.getNumberRanking()) {
			System.out.println("Full House: 3 cards of same number followed by 2 of matching number.");	
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	* Checks if list of cards is a Four of a Kind.
	*
	* @param cards	List of cards to check.
	*
	* @return boolean
	*
	* Returns true if list of cards is a Four of a Kind and false if not.
	*
	*/
	private boolean isFourOfAKind(List<Card> cards) {
		if (cards.size() != 5) return false;
		
		for (int i = 0; i < cards.size()-2; i++) {
			if (cards.get(i).number.getNumberRanking() != cards.get(i+1).number.getNumberRanking()) {
				System.out.println("Four of a kind: 4 cards of same number followed by any card.");
				return false;
			}
		}
		return true;
	}
	
}