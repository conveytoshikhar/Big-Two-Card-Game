import java.util.Arrays;

/**This is a subclass of the class Hand. It is used to model hand type of Straight. 
 * @author Shikhar
 *
 */
public class Straight extends Hand{
	private static final long serialVersionUID = 1L;
	/**Constructor method for building a hand with a specified player and list of cards 
	 * @param player The specified player who is playing the hand. 
	 * @param cards A CardList object containing cards to be played as the hand 
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
		
	}
	
	/** This method returns the type (String) of hand that is played.
	 * @return The string value of the hand played. 
	 */
	public String getType() {
		return "Straight";
	}
	
	/** This method checks whether the hand played is valid and conforms to the rules of the 'Straight' hand.
	 * @return The boolean value of true if it is valid, else false.
	 */
	public boolean isValid() {
		
		this.sort();
		if(this.size()!=5)
			return false;
		else {
			int []rank =new int[5];
			for(int i=0;i<5;i++) {
				rank[i]=this.getCard(i).getRank();
				if(rank[i]==0) {
					rank[i]=13;
				}
				if(rank[i]==1) {
					rank[i]=14;
				}
			}
			
			Arrays.sort(rank);
			boolean counter=true;
			for(int i=0;i<rank.length-1;i++) {
				if(rank[i]!=rank[i+1]-1)
					counter=false;
			}
			
			return counter;
		}
		
		
		
	}
	
	
}
