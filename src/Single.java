
/**This is a subclass of the class Hand. It is used to model hand type of Single. 
 * @author Shikhar
 *
 */
public class Single extends Hand {
	private static final long serialVersionUID = 1L;
	
	/**Constructor method for building a hand with a specified player and list of cards 
	 * @param player The specified player who is playing the hand. 
	 * @param cards A CardList object containing cards to be played as the hand 
	 */
	public Single(CardGamePlayer player, CardList cards) {
		super(player, cards);
		
	}
	
	
	/** This method checks whether the hand played is valid and conforms to the rules of the 'Single' hand.
	 * @return The boolean value of true if it is valid, else false.
	 */
	public boolean isValid() {
		if(this.size()==1)
			return true;
		else
			return false;
	}
	
	/**This method returns the type (String) of hand that is played.
	 * @return The string value of the hand played. 
	 */
	public String getType() {
		return "Single";
	}
	

}
	
