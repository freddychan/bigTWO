public enum Suit {
	DIAMONDS(1),
	CLUBS(2),
	HEARTS(3),
	SPADES(4);
	
	private int suitRanking;
	
	private Suit(int rank) {
		this.suitRanking = rank;
	}
	
	public int getSuitRanking() {
		return suitRanking;
	}
}
	