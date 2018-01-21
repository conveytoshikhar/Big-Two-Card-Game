
/**This is a subclass of the class Hand. It is used to model hand type of Quad. 
 * @author Shikhar
 *
 */

public class Quad extends Hand{
	private static final long serialVersionUID = 1L;
	/**Constructor method for building a hand with a specified player and list of cards 
	 * @param player The specified player who is playing the hand. 
	 * @param cards A CardList object containing cards to be played as the hand 
	 */
	public Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
		
	}
	/** This method returns the type (String) of hand that is played.
	 * @return The string value of the hand played. 
	 */
	public String getType() {
		return "Quad";
	}
	
	/** This method checks whether the hand played is valid and conforms to the rules of the 'Quad' hand.
	 * @return The boolean value of true if it is valid, else false.
	 */
	public boolean isValid() {
		this.sort();
		if(this.size()!=5)
			return false;
		else {
			boolean counter1=true;
			boolean counter2=true;
			for(int i=0;i<3;i++) {
				if ( this.getCard(i).getRank() != this.getCard(i+1).getRank() )
					counter1=false;		
			}
			
			for(int i=1;i<4;i++) {
				if ( this.getCard(i).getRank() != this.getCard(i+1).getRank() )
					counter2=false;
			}
			
			if(counter1==false && counter2==false)
				return false;
			return true;
				
		}
	}
			
	/** This method is an overridden method of the hand class. It is used to find the top card in the Quad based on rules and return the same. 
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
		
	

	
	

