
public class BigTwoCard extends Card {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BigTwoCard(int suit, int rank) {
		super(suit,rank);
	}
	
	

	public int compareTo(Card card) {
		int rankOfThisCard=this.getRank();
		int rankOfPassedCard=card.getRank();
		
		//checking for the 2 and 'A'
		if (rankOfThisCard==0)
			rankOfThisCard=13;
		if(rankOfPassedCard==0)
			rankOfPassedCard=13;
		if(rankOfThisCard==1) 
			rankOfThisCard=14;
		if(rankOfPassedCard==1)
			rankOfPassedCard=14;
		
		
		if(rankOfThisCard>rankOfPassedCard) {
			return 1;
		}
		else if (rankOfThisCard< rankOfPassedCard) {
			return -1;
		}
		else if (this.suit > card.suit) {
			return 1;
		} else if (this.suit < card.suit) {
			return -1;
		} else {
			return 0;
		}
	}
}
