
/**This is a subclass of the class Hand. It is used to model hand type of Flush. 
 * @author Shikhar
 *
 */
public class Flush extends Hand{
	private static final long serialVersionUID = 1L;
	/**Constructor method for building a hand with a specified player and list of cards 
	 * @param player The specified player who is playing the hand. 
	 * @param cards A CardList object containing cards to be played as the hand 
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
		
	}
	

	/** This method returns the type (String) of hand that is played.
	 * @return The string value of the hand played. 
	 */
	public String getType() {
		return "Flush";
	}
	
	/** This method checks whether the hand played is valid and conforms to the rules of the 'Flush' hand.
	 * @return The boolean value of true if it is valid, else false.
	 */
	public boolean isValid() {
		if(this.size()!=5)
			return false;
		else
		{
			int []suit =new int[5];
			for(int i=0;i<5;i++) {
				suit[i]=this.getCard(i).getSuit();
			}
			boolean counter=true;
			
			for(int i=0;i<4;i++) {
				if(suit[i]!=suit[i+1])
					counter=false;
			}
			
			return counter;
		}
	}
			
	


	

	
	
}
