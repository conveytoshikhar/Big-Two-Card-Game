///*import java.util.ArrayList;
//
///**This class implements the CardGame
// * @author Shikhar
// *
// */
//public class BigTwo implements CardGame{
//	private Deck deck;  // a deck of cards that is used
//	private ArrayList<CardGamePlayer> playerList=new ArrayList<CardGamePlayer>(); // a list of players playing the game
//	private ArrayList<Hand> handsOnTable=new ArrayList<Hand>();;// a list of hands played on the table
//	private int currentIdx; //an integer for storing the index of the current player playing the turn.
//	private BigTwoTable table;    //BigTwoTable object for association with the Table.
//	private boolean handIsValid=true;      // a boolean for checking whether a hand played by the current player is valid or not.
//	private int[] indicesPlayed=new int[5];  //an integer array that stores the indices of the cards played as a hand by the player.
//	private CardList handPlayed=new CardList(); //an ArrayList of type CardList for storing the hand played.
//	private CardGamePlayer lastHandPlayer;
//	private Card threeOfDiamonds=new Card(0,2); // Three of Diamonds created as a Card to check for the validity of the first hand.
//	
//	
//	/**A constructor function for creating a BigTwoCard game. 
//	 * 
//	 */
//	public BigTwo() {
//		CardGamePlayer p1=new CardGamePlayer();
//		CardGamePlayer p2=new CardGamePlayer();
//		CardGamePlayer p3=new CardGamePlayer();
//		CardGamePlayer p4=new CardGamePlayer();
//		
//		//adding players to the playerList
//		this.playerList.add(p1);
//		this.playerList.add(p2);
//		this.playerList.add(p3);
//		this.playerList.add(p4);
//		
//		table=new BigTwoTable(this);
//	}//end of constructor 
//	
//	/**
//	 * Returns the number of players in this card game.
//	 * 
//	 * @return the number of players in this card game
//	 */
//	public int getNumOfPlayers() {
//			return (playerList.size());
//	}
//	/**
//	 * Returns the deck of cards being used in this card game.
//	 * 
//	 * @return the deck of cards being used in this card game
//	 */
//	public Deck getDeck() {
//			return deck;
//	}
//	/**
//	 * Returns the list of players in this card game.
//	 * 
//	 * @return the list of players in this card game
//	 */
//	public ArrayList <CardGamePlayer> getPlayerList(){
//			return playerList;
//	}
//	/**
//	 * Returns the list of hands played on the table.
//	 * 
//	 * @return the list of hands played on the table
//	 */
//	public ArrayList<Hand> getHandsOnTable(){
//			return handsOnTable;
//	}
//	/**
//	 * Returns the index of the current player.
//	 * 
//	 * @return the index of the current player
//	 */
//	public int getCurrentIdx() {
//			return currentIdx;
//	}
//	/**
//	 * Starts the card game.
//	 * 
//	 * @param deck
//	 *            the deck of (shuffled) cards to be used in this game
//	 */
//	public void start(Deck deck) {
//		
//		this.deck=deck;
//		
//		//reseting the game
//		for(int i=0;i<4;i++) {
//				playerList.get(i).removeAllCards();
//		}
//		
//
//		
//		
//		//dividing the shuffles deck between the four players.
//		int increasingFactor=0;   // a integer variable helping in the distribution of cards amongst the four players. 
//		for(int i=0;i<4;i++) {
//			for(int j=0;j<13;j++) {
//				playerList.get(i).addCard(deck.getCard(increasingFactor+j));
//			}
//			increasingFactor=increasingFactor+13;
//		}
//		
//		
//		//sorting the cards in each player's hand.
//		for(int i=0;i<4;i++) {
//			playerList.get(i).sortCardsInHand();
//		}
//		
//		
//		
//		
//		
//		
//			
//			for(int i=0;i<4;i++) {
//				if ( this.playerList.get(i).getCardsInHand().contains(threeOfDiamonds) )
//					currentIdx=i;
//			}
//			
//			table.setActivePlayer(currentIdx);
//			lastHandPlayer=this.playerList.get(currentIdx);
//			table.printMsg("Player "+currentIdx+"'s turn:");
//			
//			
//		
//	}//end of start
//		
//		
//		
//
//
//	
//
//	/**
//	 * Makes a move by the player.
//	 * 
//	 * @param playerID
//	 *            the playerID of the player who makes the move
//	 * @param cardIdx
//	 *            the list of the indices of the cards selected by the player
//	 */
//	public void makeMove(int playerId, int [] cardIdx) {
//		checkMove(playerId,cardIdx);
//		if(! (this.endOfGame()) ) {
//			table.printMsg("Player "+currentIdx+"'s turn:");
//		}
//			
//	}
//	/**
//	 * Checks the move made by the player.
//	 * 
//	 * @param playerID
//	 *            the playerID of the player who makes the move
//	 * @param cardIdx
//	 *            the list of the indices of the cards selected by the player
//	 */
//	public void checkMove(int playerID, int []cardIdx) {
//		
//		
//		
//		//set currentPlayer and repaint
//		if(handIsValid) {
//			table.setActivePlayer(currentIdx);		
//			//table.repaint();
//			
//		}
//		
//		//if last hand player == this player, player cannot pass the turn
//		indicesPlayed=table.getSelected();
//		if(this.playerList.get(currentIdx)==lastHandPlayer) {
//			
//			if(indicesPlayed==null) {//player cannot pass
//				handIsValid=false;
//			}
//			else {//must play a move
//				
//				handPlayed=this.playerList.get(currentIdx).play(indicesPlayed);
//				if(handsOnTable.isEmpty()) {   //checks if this is the first player
//					if(handPlayed.contains(threeOfDiamonds)) {  //if yes, then he must play a hand containing 3 of diamonds   
//						handIsValid=true;
//					}
//					else {   //or else the hand is invalid 
//						handIsValid=false;
//					}
//				}
//				else {    //if not the first player, then it doesn't matter
//					handIsValid=true;
//				}
//			}
//			
//			if(handIsValid) {
//				Hand handType=composeHand(this.playerList.get(currentIdx), handPlayed); //Gets the type of hand played by the player, null if not valid.
//				if(handType!=null) {
//					table.printMsg("{"+handType.getType()+"} ");
//					table.printMsg(handPlayed.toString());
//					table.printMsg(" ");
//					this.handsOnTable.add(handType);
//				
//					this.playerList.get(currentIdx).removeCards(handPlayed);
//						
//					
//					lastHandPlayer=this.playerList.get(currentIdx);
//					currentIdx=(currentIdx+1)%4;
//					handIsValid=true;
//				}
//				else {
//					String toPrint="";
//					if(cardIdx!=null) {
//						for(int i=0;i<cardIdx.length;i++) {
//							toPrint+="["+this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString()+"]";
//						}
//					}
//						table.printMsg(toPrint+"Not a legal move!!!");
//					handIsValid=false;
//				}
//			}
//			else {
//				String toPrint="";
//				if(cardIdx!=null) {
//					for(int i=0;i<cardIdx.length;i++) {
//						toPrint+="["+this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString()+"]";
//					}
//				}
//					table.printMsg(toPrint+"Not a legal move!!!");
//				handIsValid=false;
//			}
//			
//		}
//		//new player, has to play a beating hand, can pass also
//		else {
//			if(indicesPlayed==null) {   //player is passing the turn
//				handIsValid=true;
//				handPlayed=null;
//			}
//			else {
//				handPlayed=this.playerList.get(currentIdx).play(indicesPlayed);
//				handIsValid=true;
//			}
//			
//			
//			if(handIsValid) {
//				if(handPlayed==null) { //it is a pass
//					table.printMsg("{ Pass }");
//					table.printMsg(" ");
//					currentIdx=(currentIdx+1)%4;  //changing to next player but not lastHandPlayer
//					handIsValid=true;
//				}
//				else {
//					Hand handType=composeHand(this.playerList.get(currentIdx), handPlayed); //Gets the type of hand played by the player, null if not valid.
//					if(handType!=null) {
//						if(handType.beats(handsOnTable.get(handsOnTable.size()-1) ) ) {
//							table.printMsg("{"+handType.getType()+"} ");
//							table.printMsg(handPlayed.toString());
//							table.printMsg(" ");
//							this.handsOnTable.add(handType);
//							this.playerList.get(currentIdx).removeCards(handPlayed);
//							lastHandPlayer=this.playerList.get(currentIdx);
//							currentIdx=(currentIdx+1)%4;
//						}
//						else {
//							String toPrint="";
//							if(cardIdx!=null) {
//								for(int i=0;i<cardIdx.length;i++) {
//									toPrint+="["+this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString()+"]";
//								}
//							}
//								table.printMsg(toPrint+"Not a legal move!!!");
//							handIsValid=false;
//						}
//					}
//					else {
//						String toPrint="";
//						if(cardIdx!=null) {
//							for(int i=0;i<cardIdx.length;i++) {
//								toPrint+="["+this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString()+"]";
//							}
//						}
//							table.printMsg(toPrint+"Not a legal move!!!");
//						handIsValid=false;
//					}			
//				}
//			}
//		}
//		
//		if(this.endOfGame()) {
//			
//			table.setActivePlayer(-1);   //to print all the  cards of the losing players with face front. 
//			
//			table.printMsg("");
//			table.printMsg("Game ends");
//			for(int i=0;i<4;i++) {
//				if(this.playerList.get(i).getNumOfCards()!=0) {
//					table.printMsg(this.playerList.get(i).getName()+" has "+this.playerList.get(i).getCardsInHand().size()+" cards in hand.");
//				}
//				else {
//					table.printMsg(this.playerList.get(i).getName()+" wins the game");
//				}
//			}
//		
//			table.disable();
//		}
//			
//		
//		
//		
//		
//		
//			//table.repaint();
//		
//		
//	}//end of function 
//
//
//	/**
//	 * Checks for end of game.
//	 * 
//	 * @return true if the game ends; false otherwise
//	 */	
//	public boolean endOfGame() {
//		for(int i=0;i<4;i++) {
//			if(this.playerList.get(i).getNumOfCards()==0){	
//				return true;
//			}
//		}
//		return false;
//	}
//			
//	/**A method for returning a valid hand played by the player from the specified list of cards, else null. 
//	 * @param player The player ID playing the hand
//	 * @param card the CardList of cards played as a hand
//	 * @return return the type of Hand (i.e object of class Single,Pair, Triple etc.);returns null if invalid. 
//	 */
//	public static Hand composeHand(CardGamePlayer player, CardList card) {
//		if(card==null) {
//			return null;
//		}
//		Single single=new Single(player,card); //creating an object of Single class
//		if(single.isValid())
//			return single;
//		
//		Pair pair=new Pair(player,card); //creating an object of Pair class
//		if(pair.isValid()) {
//			return pair;
//		}
//		
//		Triple triple=new Triple(player,card); //creating an object of Triple class
//		if(triple.isValid())
//			return triple;
//		
//		StraightFlush straightFlush=new StraightFlush(player,card); //creating an object of StraightFlush class
//		if(straightFlush.isValid()) { 
//			return straightFlush;
//		}
//		
//		
//		Quad quad=new Quad(player,card); //creating an object of Quad class
//		if(quad.isValid())
//			return quad;
//		
//
//		FullHouse fullHouse=new FullHouse(player,card); //creating an object of FullHouse class
//		if(fullHouse.isValid()) {
//			return fullHouse;
//		}
//		
//		Flush flush=new Flush(player,card); //creating an object of Flush class
//		if(flush.isValid())
//			return flush;
//		
//		Straight straight=new Straight(player,card); //creating an object of Straight class
//		if(straight.isValid()) {
//			return straight;
//		}
//			
//		return null; 
//			
//	}
//	
//	
//	/**The execution of the program starts here
//	 * @param args Not Required in this program.
//	 */
//	/*public static void main(String []args) {
//			BigTwo obj=new BigTwo();
//			BigTwoDeck deck=new BigTwoDeck();
//			deck.shuffle();
//			obj.start(deck);
//	}*/
//}
//
//
//
//	
//	
//	
//	
//	
//	
//	
//	
//	
//
