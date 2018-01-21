
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;
public class BigTwoClient implements CardGame, NetworkGame{
	private int numOfPlayers; // specifying the number of players
	private Deck deck; // a deck of cards
	private ArrayList <CardGamePlayer> playerList=new ArrayList<CardGamePlayer>(); //a list of players 
	private ArrayList<Hand> handsOnTable=new ArrayList<Hand>(); //a list of hands played on the table 
	private int playerID; 
	private String playerName;
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;
	private int currentIdx;
	private BigTwoTable table;
	private BigTwoCard threeOfDiamonds=new BigTwoCard(0,2);  // 
	private boolean handIsValid=true;      // a boolean for checking whether a hand played by the current player is valid or not.
	private int[] indicesPlayed=new int[5];  //an integer array that stores the indices of the cards played as a hand by the player.
	private CardList handPlayed=new CardList(); //an ArrayList of type CardList for storing the hand played.
	private CardGamePlayer lastHandPlayer;
	boolean calledOnce=false;
	public BigTwoClient() {
		
		playerList=new ArrayList<CardGamePlayer>();
		CardGamePlayer p1=new CardGamePlayer();
		CardGamePlayer p2=new CardGamePlayer();
		CardGamePlayer p3=new CardGamePlayer();
		CardGamePlayer p4=new CardGamePlayer();
		
		//adding players to the playerList
		this.playerList.add(p1);
		this.playerList.add(p2);
		this.playerList.add(p3);
		this.playerList.add(p4);
		
		table=new BigTwoTable(this);
		
		
		String currentName=(String) JOptionPane.showInputDialog("Enter Name");
		playerName=currentName;
		this.makeConnection();
		
	}
	
	
	/**
	 * For getting the playerID, ie. the index of the local player.
	 */
	public int getPlayerID() {
		return playerID;
	}

	
	/**
	 * A method for setting the playerID.
	 * This message should be called from the parseMessage() method when a message of the type PLAYER_LIST is received from the game server.
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	
	/**
	 * A method for getting the name of the local player.
	 */
	public String getPlayerName() {
		return playerName;
	}

	
	/**
	 * A method for setting the name of the local player.
	 */
	public void setPlayerName(String playerName) {
		playerList.get(playerID).setName(playerName);
	}

	
	/**
	 * A method for getting the IP address of the game server.
	 */
	public String getServerIP() {
		return serverIP;
	}

	
	/**
	 * A method for setting the IP address of the game server.
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	
	/**
	 * A method for getting the TCP port of the game server.
	 */
	public int getServerPort() {
		return serverPort;
	}

	
	/**
	 * A method for setting the TCP port of the game server.
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void makeConnection() {
		this.setServerIP("127.0.0.1");
		this.setServerPort(2396);
		try {
			sock=new Socket(this.getServerIP(),this.getServerPort());
			oos=new ObjectOutputStream(sock.getOutputStream());
			Runnable threadJob=new ServerHandler();
			Thread receiveMessage=new Thread(threadJob);
			receiveMessage.start();
			
			CardGameMessage tempMessage=new CardGameMessage(1,-1,this.getPlayerName() );
			this.sendMessage(tempMessage);
			tempMessage=new CardGameMessage(4,-1,null);
			this.sendMessage(tempMessage);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	class ServerHandler implements Runnable{
		public void run() {
			
			try {
				ObjectInputStream ois=new ObjectInputStream(sock.getInputStream());
			
				while(true) {
					CardGameMessage messageReceived=(CardGameMessage)ois.readObject();
					parseMessage(messageReceived);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			table.repaint();
		}
	}
	/**
	 * Returns the number of players in this card game.
	 * 
	 * @return the number of players in this card game
	 */
	public int getNumOfPlayers(){
		return numOfPlayers;
	}
	
	/**
	 * Returns the deck of cards being used in this card game.
	 * 
	 * @return the deck of cards being used in this card game
	 */
	public Deck getDeck() {
			return deck;
	}
	/**
	 * Returns the list of players in this card game.
	 * 
	 * @return the list of players in this card game
	 */
	public ArrayList <CardGamePlayer> getPlayerList(){
			return playerList;
	}
	/**
	 * Returns the list of hands played on the table.
	 * 
	 * @return the list of hands played on the table
	 */
	public ArrayList<Hand> getHandsOnTable(){
			return handsOnTable;
	}
	/**
	 * Returns the index of the current player.
	 * 
	 * @return the index of the current player
	 */
	public int getCurrentIdx() {
			return currentIdx;
	}
	
	
	/**
	 * Starts the card game.
	 * 
	 * @param deck
	 *            the deck of (shuffled) cards to be used in this game
	 */
	public void start(Deck deck) {
		//table.enable();
		this.deck=deck;
		
		//reseting the game
		for(int i=0;i<4;i++) {
				playerList.get(i).removeAllCards();
		}
		

		
		
		//dividing the shuffles deck between the four players.
		int increasingFactor=0;   // a integer variable helping in the distribution of cards amongst the four players. 
		for(int i=0;i<4;i++) {
			for(int j=0;j<13;j++) {
				playerList.get(i).addCard(deck.getCard(increasingFactor+j));
			}
			increasingFactor=increasingFactor+13;
		}
		
		
		//sorting the cards in each player's hand.
		for(int i=0;i<4;i++) {
			playerList.get(i).sortCardsInHand();
		}
		
		
		
		
		
		
			
			for(int i=0;i<4;i++) {
				if ( this.playerList.get(i).getCardsInHand().contains(threeOfDiamonds) )
					currentIdx=i;
			}
			
			//table.setActivePlayer(currentIdx);
			lastHandPlayer=this.playerList.get(currentIdx);
			//System.out.println("Current player: "+currentIdx);
			table.printMsg(this.playerList.get(currentIdx).getName()+"'s turn:");
			
			
		
	}//end of start
	
	/**
	 * Makes a move by the player.
	 * 
	 * @param playerID
	 *            the playerID of the player who makes the move
	 * @param cardIdx
	 *            the list of the indices of the cards selected by the player
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public void makeMove(int playerId, int [] cardIdx) {
		// create a CardGameMessage
		CardGameMessage messageToSend = new CardGameMessage(6, playerID, cardIdx);
		// send the message to the server
		sendMessage(messageToSend);
		//table.printChat("Make move called");
		
		
		
	}
	
	
	public  void parseMessage(GameMessage message) {
		int typeOfMessage=message.getType();
		
		switch (typeOfMessage) {
		case 0: //Player_List
			this.setPlayerID(message.getPlayerID());
			String namesOfPlayers[]= (String[])message.getData();
			//System.out.println("working");
			//if(namesOfPlayers!=null) {
			for(int i=0;i<namesOfPlayers.length;i++) {
				//System.out.println(namesOfPlayers[i]+" ");
				this.getPlayerList().get(i).setName(namesOfPlayers[i]);
			}
			//}
			table.repaint();
			break;
			
			
		case 1: //Join
			String name=(String) message.getData();
			this.playerList.get(message.getPlayerID()).setName(name);
			//System.out.println("Joined at"+message.getPlayerID());
			//System.out.println("Player name changed to:"+this.playerList.get(message.getPlayerID()).getName());
			//this.numOfPlayers++;
			break;
		
		case 2: //FUll
			table.printMsg("Server is full, cannot join the game");
			break;
			
		case 3: //Quit
		
			this.playerList.get(message.getPlayerID()).setName("");
			this.numOfPlayers--;
			for(int i=0;i<4;i++) {
				this.playerList.get(i).removeAllCards();
			}
				
			
			//table.printMsg(this.playerList.get(message.getPlayerID()).getName() + " just left the game.");
			table.repaint();
			table.disable();
			CardGameMessage messageToSend=new CardGameMessage(4,-1,null);
			
			this.sendMessage(messageToSend);
			
		
		
			break;
			
			
		case 4: //Ready
			
			handsOnTable = new ArrayList<Hand>();
			table.clearMsgArea();
			for(int i=0;i<4;i++) {
				if(this.playerList.get(i).getName()!=null)
					if(this.playerList.get(i).getName()=="")
						table.printMsg("A Player just left the game. Rest of the players get ready for a new game.");
					else
						table.printMsg("Player "+this.playerList.get(i).getName()+" is ready now!");
			}
				
			table.repaint();
			break;
		case 5: //Start
			handIsValid=true;      // a boolean for checking whether a hand played by the current player is valid or not.
			indicesPlayed=new int[5];  //an integer array that stores the indices of the cards played as a hand by the player.
			handPlayed=new CardList(); //an ArrayList of type CardList for storing the hand played.
			this.table.enable();
			table.printMsg("All players are ready. Game starts now.");
			this.start( (BigTwoDeck)message.getData());
			table.repaint();
			break;
		
		
		case 6: //MOve
			int [] cardIdx=(int[]) message.getData();
			
			//System.out.println("Move prsing");
			this.checkMove(message.getPlayerID(), cardIdx);
			if(! (this.endOfGame()) ) {
				table.printMsg(this.playerList.get(currentIdx).getName()+"'s turn:");
			}
			table.repaint();
			break;
		case 7:  //Message
			String chatMessage= (String) message.getData();
			table.printChat(chatMessage);
			table.repaint();
			break;
			
		default:
			System.out.println("Wrong message");
			break;
			 
			
		}
	}
	
	
	public void sendMessage(GameMessage message) {
		try {
			
			oos.writeObject(message);
			oos.flush();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Checks the move made by the player.
	 * 
	 * @param playerID
	 *            the playerID of the player who makes the move
	 * @param cardIdx
	 *            the list of the indices of the cards selected by the player
	 */
	public void checkMove(int playerID, int []cardIdx) {

		
		//System.out.println();
		if(playerID==currentIdx) {
		//set currentPlayer and repaint
		if(handIsValid) {
			table.setActivePlayer(currentIdx);
			//table.repaint();

		}

		//if last hand player == this player, player cannot pass the turn
		indicesPlayed=cardIdx;
		if(this.playerList.get(currentIdx)==lastHandPlayer) {

			if(indicesPlayed==null) {//player cannot pass
				handIsValid=false;
			}
			else {//must play a move

				handPlayed=this.playerList.get(currentIdx).play(indicesPlayed);
				if(handsOnTable.isEmpty()) {   //checks if this is the first player
					if(handPlayed.contains(threeOfDiamonds)) {  //if yes, then he must play a hand containing 3 of diamonds
						handIsValid=true;
					}
					else {   //or else the hand is invalid
						handIsValid=false;
					}
				}
				else {    //if not the first player, then it doesn't matter
					handIsValid=true;
				}
			}

			if(handIsValid) {
				Hand handType=composeHand(this.playerList.get(currentIdx), handPlayed); //Gets the type of hand played by the player, null if not valid.
				if(handType!=null) {
					table.printMsg("{"+handType.getType()+"} ");
					table.printMsg(handPlayed.toString());
					table.printMsg(" ");
					this.handsOnTable.add(handType);

					this.playerList.get(currentIdx).removeCards(handPlayed);


					lastHandPlayer=this.playerList.get(currentIdx);
					currentIdx=(currentIdx+1)%4;
					handIsValid=true;
				}
				else {
					String toPrint="";
					if(cardIdx!=null) {
						for(int i=0;i<cardIdx.length;i++) {
							toPrint+="["+this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString()+"]";
						}
					}
						table.printMsg(toPrint+"Not a legal move!!!");
					handIsValid=false;
				}
			}
			else {
				String toPrint="";
				if(cardIdx!=null) {
					for(int i=0;i<cardIdx.length;i++) {
						toPrint+="["+this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString()+"]";
					}
				}
					table.printMsg(toPrint+"Not a legal move!!!");
				handIsValid=false;
			}

		}
		//new player, has to play a beating hand, can pass also
		else {
			if(indicesPlayed==null) {   //player is passing the turn
				handIsValid=true;
				handPlayed=null;
			}
			else {
				handPlayed=this.playerList.get(currentIdx).play(indicesPlayed);
				handIsValid=true;
			}


			if(handIsValid) {
				if(handPlayed==null) { //it is a pass
					table.printMsg("{ Pass }");
					table.printMsg(" ");
					currentIdx=(currentIdx+1)%4;  //changing to next player but not lastHandPlayer
					handIsValid=true;
				}
				else {
					Hand handType=composeHand(this.playerList.get(currentIdx), handPlayed); //Gets the type of hand played by the player, null if not valid.
					if(handType!=null) {
						if(handType.beats(handsOnTable.get(handsOnTable.size()-1) ) ) {
							table.printMsg("{"+handType.getType()+"} ");
							table.printMsg(handPlayed.toString());
							table.printMsg(" ");
							this.handsOnTable.add(handType);
							this.playerList.get(currentIdx).removeCards(handPlayed);
							lastHandPlayer=this.playerList.get(currentIdx);
							currentIdx=(currentIdx+1)%4;
						}
						else {
							String toPrint="";
							if(cardIdx!=null) {
								for(int i=0;i<cardIdx.length;i++) {
									toPrint+="["+this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString()+"]";
								}
							}
								table.printMsg(toPrint+"Not a legal move!!!");
							handIsValid=false;
						}
					}
					else {
						String toPrint="";
						if(cardIdx!=null) {
							for(int i=0;i<cardIdx.length;i++) {
								toPrint+="["+this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString()+"]";
							}
						}
							table.printMsg(toPrint+"Not a legal move!!!");
						handIsValid=false;
					}
				}
			}
		}
	
		//
		if(this.endOfGame()) {

			//table.setActivePlayer(-1);   //to print all the  cards of the losing players with face front.
			String temp="";
			temp=temp+"\n";
			
			for(int i=0;i<4;i++) {
				if(this.playerList.get(i).getNumOfCards()!=0) {
					temp=temp+(this.playerList.get(i).getName()+" has "+this.playerList.get(i).getCardsInHand().size()+" cards in hand.\n");
				}
				else {
					temp=temp+(this.playerList.get(i).getName()+" wins the game\n");
				}
			}
			//JOptionPane.showMessageDialog(table.getFrame(),temp);
			int resultOfOkButton = JOptionPane.showOptionDialog(null, temp, "Game Ends", JOptionPane.DEFAULT_OPTION,
			        JOptionPane.INFORMATION_MESSAGE, null, null, null);
			if(resultOfOkButton==JOptionPane.OK_OPTION) {
				GameMessage message=new CardGameMessage(4,-1,null);
				sendMessage(message);
			}
			table.disable();
		}
		
		
	}
	}



	/**
	 * Checks for end of game.
	 * 
	 * @return true if the game ends; false otherwise
	 */	
	public boolean endOfGame() {
		for(int i=0;i<4;i++) {
			if(this.playerList.get(i).getNumOfCards()==0){	
				return true;
			}
		}
		return false;
	}
		
	/**A method for returning a valid hand played by the player from the specified list of cards, else null. 
	 * @param player The player ID playing the hand
	 * @param card the CardList of cards played as a hand
	 * @return return the type of Hand (i.e object of class Single,Pair, Triple etc.);returns null if invalid. 
	 */
	public static Hand composeHand(CardGamePlayer player, CardList card) {
		if(card==null) {
			return null;
		}
		Single single=new Single(player,card); //creating an object of Single class
		if(single.isValid())
			return single;
		
		Pair pair=new Pair(player,card); //creating an object of Pair class
		if(pair.isValid()) {
			return pair;
		}
		
		Triple triple=new Triple(player,card); //creating an object of Triple class
		if(triple.isValid())
			return triple;
		
		StraightFlush straightFlush=new StraightFlush(player,card); //creating an object of StraightFlush class
		if(straightFlush.isValid()) { 
			return straightFlush;
		}
		
		
		Quad quad=new Quad(player,card); //creating an object of Quad class
		if(quad.isValid())
			return quad;
		

		FullHouse fullHouse=new FullHouse(player,card); //creating an object of FullHouse class
		if(fullHouse.isValid()) {
			return fullHouse;
		}
		
		Flush flush=new Flush(player,card); //creating an object of Flush class
		if(flush.isValid())
			return flush;
		
		Straight straight=new Straight(player,card); //creating an object of Straight class
		if(straight.isValid()) {
			return straight;
		}
			
		return null; 
			
	}
	
	
	public static void main(String[] args) {
		BigTwoClient ob = new BigTwoClient();
	}
	
	
	
	
	
}



	
	
	
	
	
	
	
	
	


	
	
	

