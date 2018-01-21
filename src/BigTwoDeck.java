
public class BigTwoDeck extends Deck {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* * This is the overridden method of the Deck Class, used to operate on BigTwoCards rather than normal Cards.
	 */
	public void initialize() {
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);
				addCard(card);
			}
		}
	}
}
