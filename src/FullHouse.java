
/**This is a subclass of the class Hand. It is used to model hand type of Full House. 
 * @author Shikhar
 *
 */

/**This is a subclass of the class Hand. It is used to model hand type of Full House. 
 * @author Shikhar
 *
 */
public class FullHouse extends Hand{
	private static final long serialVersionUID = 1L;
	/**Constructor method for building a hand with a specified player and list of cards 
	 * @param player The specified player who is playing the hand. 
	 * @param cards A CardList object containing cards to be played as the hand 
	 */
	public FullHouse(CardGamePlayer player, CardList cards) {
		super(player, cards);
		
	}
	
	/** This method returns the type (String) of hand that is played.
	 * @return The string value of the hand played. 
	 */
	public String getType() {
		return "FullHouse";
	}
	
	/** This method checks whether the hand played is valid and conforms to the rules of the 'Full House' hand.
	 * @return The boolean value of true if it is valid, else false.
	 */
	public boolean isValid() {
		this.sort();
		if(this.size()!=5)
			return false;
		else {
			if(   ( this.getCard(0).getRank()==this.getCard(1).getRank() ) &&   ( this.getCard(1).getRank() == this.getCard(2).getRank() )       ) {
				if( this.getCard(3).getRank() == this.getCard(4).getRank() ) {
					return true;
				}
			}
			
			else if( this.getCard(0).getRank() == this.getCard(1).getRank() ) {
				if(   ( this.getCard(2).getRank()==this.getCard(3).getRank() ) &&   ( this.getCard(3).getRank() == this.getCard(4).getRank() )       ) {
					return true;
				}
			}
			
			return false;
				
		}
	}
		
	/** This method is an overridden method of the hand class. It is used to find the top card in the Full House based on rules and return the same. 
	 * @return an object of the class Card, that is the top card of the hand. 
	 */
	public Card getTopCard() {
		int rankOne=this.getCard(0).getRank();
		int rankTwo=this.getCard(4).getRank();
		int countRankOne=0;
		int countRankTwo=0;
		for(int i=0;i<4;i++) {
			if(this.getCard(i).getRank() == rankOne)
				countRankOne++;
			else
				countRankTwo++;
		}
		int rankToCheck=rankOne;
		if(countRankTwo>countRankOne)
			rankToCheck=rankTwo;
		
		int maxSuit=0;
		for(int i=0;i<5;i++) {
			if( this.getCard(i).getRank() == rankToCheck) {
				if(this.getCard(i).getSuit() > maxSuit) {
					maxSuit=this.getCard(i).getSuit();
				}
			}
		}
		int indexToReturn=0;
		for(int i=0;i<5;i++) {
			if( (  this.getCard(i).getRank()==rankToCheck  )   &&  (  this.getCard(i).getSuit()==maxSuit) )
				indexToReturn=i;
		}
		
		return this.getCard(indexToReturn);
	}
	
}
		

	
	