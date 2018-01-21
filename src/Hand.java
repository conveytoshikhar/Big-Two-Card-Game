//import java.util.ArrayList;

/** The Hand Class is a sub-class of the CardList class and is used to model a hand of cards played.
 * @author Shikhar
 *
 */
public abstract class Hand extends CardList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardGamePlayer player; //Stores the player of the current hand. 
	
	/**Constructor method for building a hand with a specified player and list of cards 
	 * @param player The specified player who is playing the hand. 
	 * @param cards A CardList object containing cards to be played as the hand 
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		this.player=player;
		for(int i=0;i<cards.size();i++) {
			this.addCard(cards.getCard(i));
		}
	}
	
	/** A method for retrieving the player of this hand
	 * @return returns the player of this hand
	 */
	public CardGamePlayer getPlayer() {
		return this.player;
	}
	
	/**A method for retrieving the top card of the hand played by the player.
	 * @return Top card of the hand played by the player.
	 */
	public Card getTopCard() {
		Card maxCardOfHand=this.getCard(0);
		
		for(int i=0;i<this.size();i++) {
			if(this.getCard(i).compareTo(maxCardOfHand)==1) {
				maxCardOfHand=this.getCard(i);
			}
		}
		return maxCardOfHand;
		
	
	}

	/**A method for checking if this hand beats the hand passed in arguments. 
	 * @param hand The hand to compare the present hand to
	 * @return The result of comparison between the present hand and the hand passed as an argument.
	 */
	public boolean beats(Hand hand) {
		boolean result=false;
		if(this.size()==hand.size()) {
			//checking for single,pair and triple
			if(this.size()<=3) {
				if(this.getType()==hand.getType() && this.getTopCard().compareTo(hand.getTopCard())==1)
					result=true;
			}
			else {
				//checking for Straight FLush
				if(this instanceof StraightFlush ) {
					if(this.getType()==hand.getType() && this.getTopCard().compareTo(hand.getTopCard())==1)
						result=true;
					if(this.getType()!= hand.getType())
						result=true;
						
				}
				//checking for Quad
				if(this instanceof Quad) {
					if(this.getType()==hand.getType() && this.getTopCard().compareTo(hand.getTopCard())==1)
						result=true;
					if(this.getType()!= hand.getType() && hand.getType()!="Straight Flush")
						result=true;
				}
				
				//checking for FullHouse 
				if(this instanceof FullHouse) {
					if(this.getType()==hand.getType() && this.getTopCard().compareTo(hand.getTopCard())==1)
						result=true;
					if(this.getType()!= hand.getType() && hand.getType()!="Straight Flush" && hand.getType()!="Quad")
						result=true;
				}
				
				//checking for Flush
				if(this instanceof Flush) {
					if(this.getType()==hand.getType() && this.getTopCard().compareTo(hand.getTopCard())==1)
						result=true;
					if(this.getType()!= hand.getType() && hand.getType()!="Straight Flush" && hand.getType()!="Quad" && hand.getType()!="Full House")
						result=true;
				}
				
				//checking for Straight
				if(this instanceof Straight) {
					if(this.getType()==hand.getType() && this.getTopCard().compareTo(hand.getTopCard())==1)
						result=true;
				}
			}
		}
		
		return result;
		
	}
	
	public abstract boolean isValid();
		
	
	public abstract String getType();
		
}

