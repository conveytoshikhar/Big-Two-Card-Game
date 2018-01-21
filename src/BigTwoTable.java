import java.awt.*;
import java.awt.event.*;


import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**The BigTwoTable class implements the CardGameTable interface. 
 * It basically is used to build the GPU for the Big Two card game and 
 * handle all user actions like playing cards and passing cards etc.
 * @author Shikhar
 *
 */
public class BigTwoTable implements CardGameTable{
	private BigTwoClient game;  //a card game associates with table
	private boolean[] selected=new boolean[13]; //a boolean array indicating whether a card is selected or not.
	private int activePlayer; //stores the integer value of the current player. 
	private JFrame frame=new JFrame("Big Two Game"); // the main window of the application
	private JPanel bigTwoPanel=new BigTwoPanel(); //a panel for showing the cards of each player and the last hand played on the table.
	private JButton playButton=new JButton("Play");// a "Play" button for playing a hand.
	private JButton passButton=new JButton("Pass"); // a "Pass" button for passing a turn.
	private JTextArea msgArea=new JTextArea();//a text area for showing the current game status as well as the end of game messages. 
	private JTextArea chatArea=new JTextArea(); //a text area for chatting amongst players 
	private Image[][] cardImages=new Image[4][13]; // a 2D array for storing the card images
	private Image cardBackImage; // the image of the card back
	private Image[] avatars=new Image[4]; //array storing the images of the players.
	private boolean endOfGame=false;
	private int cardVerticalPoint=40; //top height in pixels till the first card placement.
	private int cardHorizontalPoint=160; //left width in pixels till the first card placement.
	private JTextField chatMessage;
	/**Constructor function for creating the BigTwoTable. 
	 * @param game Is a reference to a card game associated with the table.
	 */
	public BigTwoTable(BigTwoClient game){
		this.game=game; 
		
		//player image storing
		avatars[0]= new ImageIcon("images/1.png").getImage();
		avatars[1]= new ImageIcon("images/2.png").getImage();
		avatars[2]= new ImageIcon("images/3.png").getImage();
		avatars[3]= new ImageIcon("images/4.png").getImage();
		
		//cardBack image storing 
		cardBackImage=new ImageIcon("images/b.gif").getImage();
		
		
		char suits[]= {'d','c', 'h', 's'};
		//making the 2D array get the cardImages. 
		for(int i=0;i<4;i++) {
			for(int j=0;j<13;j++) {
					cardImages[i][j]=new ImageIcon("images/"+(j+1)+suits[i]+".gif").getImage();
			}
		}
		
		
		//End of Game Dialog box 
		
		//frame settings.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		
		
		
		//msgArea settings. 
		msgArea.setEditable(false);//to prevent player from writing 
		msgArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane ( msgArea );
		scrollPane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		msgArea.setBackground(new Color(240,240,230));
		msgArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
		DefaultCaret caret=(DefaultCaret)msgArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//scrollPane.setMinimumSize(new Dimension(800,500));
		
		JPanel bottomRight=new JPanel();
		bottomRight.setLayout(new BorderLayout());
		
		
		//chatArea settings 
		chatArea.setEditable(false);//to prevent player from writing 
		chatArea.setLineWrap(true);
		JScrollPane scrollPane2 = new JScrollPane ( chatArea );
		scrollPane2.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatArea.setBackground(new Color(255,255,255) );
		chatArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
		DefaultCaret caret2=(DefaultCaret)msgArea.getCaret();
		caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    //chatArea.setLayout(new BorderLayout());
	    //scrollPane2.setMinimumSize(new Dimension(800,500));
	    //inside chatArea we create the type in box
	    
	    JPanel messageTypingArea=new JPanel();
	    messageTypingArea.setLayout(new FlowLayout());
	    JLabel heading=new JLabel("Type Chat Message here:");
	    heading.setFont(new Font("SansSerif", Font.PLAIN, 20));
	    chatMessage=new JTextField(30);
	    //chatMessage.setMinimumSize(); 
	    chatMessage.setFont(new Font("SansSerif", Font.PLAIN, 20));
	    messageTypingArea.add(heading);
	    messageTypingArea.add(chatMessage);
	    bottomRight.add(scrollPane2);
	    bottomRight.add(messageTypingArea, BorderLayout.PAGE_END);
	    chatMessage.addKeyListener(new EnterKeyListener());
	   
		//bigTwoPanel settings. 
		bigTwoPanel.setLayout(new BorderLayout());
		bigTwoPanel.setMinimumSize(new Dimension(800,1000));
		bigTwoPanel.setOpaque(true);
		bigTwoPanel.setMaximumSize(new Dimension(1000,1000) );
	
		

	    //button Panel
	    JPanel buttons = new JPanel();
	    buttons.add(playButton);
	    buttons.add(passButton);
	    playButton.setFont(new Font("Arial", Font.PLAIN, 20));
	    playButton.addActionListener(new PlayButtonListener());
	    passButton.setFont(new Font("Arial", Font.PLAIN, 20));
	    passButton.addActionListener(new PassButtonListener());
	    buttons.add(Box.createRigidArea(new Dimension(100,0)));
	    
	    bigTwoPanel.add(buttons, BorderLayout.PAGE_END);
	    
	    
		//using JSplitPane for convenient vertical splitting 
	    
	    JSplitPane split2 = new JSplitPane( JSplitPane.VERTICAL_SPLIT); 
               
	    split2.setDividerLocation(0.5);   
	    split2.setResizeWeight(0.55);
	    split2.setDividerSize(2);
	    split2.setTopComponent(scrollPane);
	    split2.setBottomComponent(bottomRight);
	  
	    split2.setMinimumSize(new Dimension(1000,800));
	    split2.setPreferredSize(new Dimension (1000,800));
	    
		//using JSplitPane for convenient splitting
		
	    JSplitPane split=new JSplitPane();
	    split.setDividerLocation(0.75);   
	    split.setResizeWeight(0.75);
	    split.setDividerSize(2);
	    split.setLeftComponent(bigTwoPanel);
	    split.setRightComponent(split2);
	    split.setMinimumSize(new Dimension(800,1000));
	    frame.add(split);
	    
	    
	    
	    //menu Bar
	    JMenuBar menuBar=new JMenuBar();	
	    
	    //menu 1
	    JMenu menu=	new JMenu("Menu");	
	    JMenuItem connect=	new JMenuItem("Connection");	
	    connect.addActionListener(new ConnectMenuItemListener());
	    JMenuItem quit=new JMenuItem("Quit");
	    quit.addActionListener(new QuitMenuItemListener());
	    menu.add(connect);
	    menu.add(quit);
	    
	    //menu2
	    JMenu menu2=new JMenu("Message");	
	    JMenuItem cmessage=	new JMenuItem("Clear Chat Area");	
	    cmessage.addActionListener(new ChatMenuItemListener());
	    JMenuItem tmessage=new JMenuItem("Clear Table Messages area");
	    tmessage.addActionListener(new TableMenuItemListener());
	    menu2.add(tmessage);
	    menu2.add(cmessage);
	    
	    menuBar.add(menu);
	    menuBar.add(menu2);
	    frame.setJMenuBar(menuBar);
	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setSize(screenSize.width, screenSize.height);
	    frame.setVisible(true);
	    
	  //  this.disable();
	}
	
	/**Returns frame of big two table 
	 * @return the frame of big two table 
	 */
	public JFrame getFrame() {
		return this.frame;
	}
	/**
	 * Sets the index of the active player (i.e., the current player).
	 * 
	 * @param activePlayer
	 *            an int value representing the index of the active player
	 */
	public void setActivePlayer(int activePlayer) {
			activePlayer=game.getCurrentIdx();
	}
	
	/**
	 * Returns an array of indices of the cards selected.
	 * 
	 * @return an array of indices of the cards selected
	 */
	public int[] getSelected() {
	
		int counter=0;
		int []selectedCards=null;
		for(int i=0; i<this.selected.length; i++) {
			if (this.selected[i]==true) {
				counter++;
			}
		}
		int index=0;
		if (counter>0) {							
			selectedCards=new int[counter];
			
			for(int i=0; i<this.selected.length;i++) {			//Finding all the True Values and Returning Indices
				if (this.selected[i]==true) {
					selectedCards[index]=i;
					index++;
				}
			}
		}
		return selectedCards;
		
		
	}
	
	
	
	/**
	 * Resets the list of selected cards to an empty list.
	 */
	public void resetSelected() {
		for(int i=0;i<selected.length;i++) {
				selected[i]=false;
		}
	}
	
	/**
	 * Repaints the GUI.
	 */
	public void repaint() {
		frame.repaint();
	}
	
	/**
	 * Prints the specified string to the message area of the card game table.
	 * 
	 * @param msg
	 *            the string to be printed to the message area of the card game
	 *            table
	 */
	public void printMsg(String message) {
			msgArea.append(message+"\n\r");
	}
	
	/**
	 * Prints the specified string to the chat area of the card game table.
	 * 
	 * @param msg
	 *            the string to be printed to the chat area of the card game
	 *            table
	 */
	public void printChat(String message) {
			chatArea.append(message+"\n\r");
	}
	
	
	/**
	 * Clears the message area of the card game table.
	 */
	public void clearMsgArea() {
			msgArea.setText(null);
	}
	
	public void clearChatArea() {
		chatArea.setText(null);
	}
	/**
	 * Resets the GUI.
	 */
	public void reset() {
			resetSelected();
			clearMsgArea();
			enable();
	}
	/**
	 * Enables user interactions.
	 */
	public void enable() {
		
		playButton.setEnabled(true);
		//playButton.addActionListener(new PlayButtonListener());
		passButton.setEnabled(true);
		//passButton.addActionListener(new PassButtonListener());

		
	}
	
	/**
	 * Disables user interactions.
	 */
	public void disable() {
			playButton.setEnabled(false);
			passButton.setEnabled(false);
			//endOfGame=true;
		
	}
	
	
	
	/**An inner class that extends JPanel and implements the MouseListener interface.
	 * Overrides the paintComponent() method.
	 * Implements the MouseClicked() method.
	 * @author Shikhar
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener{
		private static final long serialVersionUID = 1L;
		/**
		 * Constructor function for calling the MouseListener. 
		 */
		BigTwoPanel(){
				this.addMouseListener(this);
		}
		/**To paint the cards etc. on the BigTwoPanel. 
		 * @param g 
		 * 			An Object of the Graphics class. Helps to paint by calling various methods associated with the Graphics class. 
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D line=(Graphics2D) g;
			line.setStroke(new BasicStroke(2));
			line.setColor(Color.BLACK);
			this.setBackground(new Color(67,160,71));
			g.setFont(new Font("SansSerif", Font.PLAIN, 20));
			
			
			
			
			activePlayer=game.getCurrentIdx();
			//System.out.println("Current player ="+activePlayer);
			int width=bigTwoPanel.getWidth();
			int height=bigTwoPanel.getHeight();
			 
			int playerHeight=height/5;
			
			int fromTop=0;
			
				
			for(int i=0;i<4;i++) {
					g.setColor(Color.BLACK);
					g.drawImage(avatars[i], 10, fromTop+30, this);
					g.drawLine(0, fromTop+playerHeight, width, fromTop+playerHeight);
					for(int j=0;j<game.getPlayerList().get(i).getNumOfCards();j++) {
						
						if(i==game.getPlayerID()) {
							if(selected[j]==true) 
								g.drawImage(cardImages[game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()] [game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()], cardHorizontalPoint+(30*j), fromTop+cardVerticalPoint-20, this);
							else 	
								g.drawImage(cardImages[game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()] [game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()], cardHorizontalPoint+(30*j), fromTop+cardVerticalPoint, this);
								g.setColor(Color.BLACK);
								if(i==game.getCurrentIdx())
									g.setColor(Color.RED);
							g.drawString("You",40, fromTop+20);
						}
						else if(i==game.getCurrentIdx()) {
							g.drawImage(cardBackImage,cardHorizontalPoint+(30*j), fromTop+cardVerticalPoint, this);
							g.setColor(Color.RED);
							g.drawString(game.getPlayerList().get(i).getName(),40, fromTop+20);
						}
						else {
							g.drawImage(cardBackImage,cardHorizontalPoint+(30*j), fromTop+cardVerticalPoint, this);
							g.setColor(Color.BLACK);
							g.drawString(game.getPlayerList().get(i).getName(),40, fromTop+20);
						}
						
						
					}
					fromTop=fromTop+playerHeight;
					g.setColor(Color.BLACK);
			}
			
			
			g.drawString("Last Hand Played", 10, fromTop+30);
			if(game.getHandsOnTable().size()>0) {
				String lastPlayer=game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getPlayer().getName();
				g.drawString("By : "+lastPlayer, 10, fromTop+cardVerticalPoint+20);
				Hand handToDisplay=game.getHandsOnTable().get(game.getHandsOnTable().size()-1);
				
				for(int i=0;i<handToDisplay.size();i++) {
					
					g.drawImage(cardImages[handToDisplay.getCard(i).getSuit()] [handToDisplay.getCard(i).getRank()], cardHorizontalPoint+190+(30*i), fromTop+cardVerticalPoint, this);	
				}
			}
			else {
				g.drawString("Table is empty.", 10, fromTop+cardVerticalPoint+20);
			}
			
		}
		
		/** To provide the functioning of mouse clicks on cards so that the cards are selected. 
		 * @param e An object of the MouseEvent class. Used to carry operations on a mouse event. 
		 */
		public void mouseClicked(MouseEvent e) {
				int xCoordinate=e.getX();
				int yCoordinate=e.getY();
				int numberOfCards=game.getPlayerList().get(activePlayer).getNumOfCards();
				boolean timeToSkip=false;
				activePlayer=game.getPlayerID();
				
				int height=bigTwoPanel.getHeight();
				int playerHeight=height/5;
			
				if(yCoordinate>= ( (game.getPlayerID()*playerHeight)+cardVerticalPoint-20 ) && yCoordinate<= ((game.getPlayerID()*playerHeight)+cardVerticalPoint +97-20) ) {
					if(xCoordinate>= cardHorizontalPoint+(30* (numberOfCards-1) ) && xCoordinate<= cardHorizontalPoint+(30*(numberOfCards-1) )+73) {
						if(selected[numberOfCards-1]==true) {
								selected[numberOfCards-1]=false;	
								timeToSkip=true;
						}
						
					}
					else {
						for(int i=0;i<numberOfCards-1;i++) {
								if(xCoordinate>=cardHorizontalPoint+(30*i) && xCoordinate<=cardHorizontalPoint+(30*i)+30) {
										if(selected[i]==true) {
											selected[i]=false;
											timeToSkip=true;
										}
													
										
								}
						}
					}
				}
			
				
				if(timeToSkip==false) {
					if(yCoordinate>= ( (game.getPlayerID()*playerHeight)+cardVerticalPoint ) && yCoordinate<= ((game.getPlayerID()*playerHeight)+cardVerticalPoint +97) ){
						if(xCoordinate>= cardHorizontalPoint+(30* (numberOfCards-1) ) && xCoordinate<= cardHorizontalPoint+(30*(numberOfCards-1) )+73) {
							if(selected[numberOfCards-1]==false)
								selected[numberOfCards-1]=true;	
							
						}
						else {
							for(int i=0;i<numberOfCards-1;i++) {
									if(xCoordinate>=cardHorizontalPoint+(30*i) && xCoordinate<=cardHorizontalPoint+(30*i)+30) {
											if(selected[i]==false)
												selected[i]=true;
											
									}
							}
						}
					}
				}
				if(endOfGame)
					resetSelected();

				frame.repaint();
				
						
				
				
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	
	/**An inner class that implements the ActionListener interface. Implements the action performed() method. Used to make the play button work. 
	 * @author Shikhar
	 *
	 */
	class PlayButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(game.getPlayerID()==game.getCurrentIdx()) {
			
				if(getSelected()!=null)
					game.makeMove(activePlayer, getSelected());
				//System.out.println("play "+passButton.getActionListeners().length );
				resetSelected();
				frame.repaint();
			}
			
		}
	}
	/**An inner class that implements the ActionListener interface. Implements the action performed() method. Used to make the pass button work. 
	 * @author Shikhar
	 *
	 */
	class PassButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(game.getPlayerID()==game.getCurrentIdx()) {
				if(getSelected()==null)
					game.makeMove(game.getCurrentIdx(), getSelected());
				//System.out.println("pass "+passButton.getActionListeners().length );
				resetSelected();
				frame.repaint();
			}
				
		}
	}
	
	/**An inner class that implements the ActionListener interface. Implements the action performed() method. Used to make the restart menu item work. 
	 * @author Shikhar
	 *
	 */
	class ConnectMenuItemListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			/*frame.dispose();
			BigTwoDeck deck=new BigTwoDeck();
			deck.shuffle();
			BigTwo ob=new BigTwo();
			ob.start(deck);
			reset();*/
			game.makeConnection();
		}
	}
	
	/**An inner class that implements the ActionListener interface. Implements the action performed() method. Used to make the quit menu item work. 
	 * @author Shikhar
	 *
	 */
	class QuitMenuItemListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
			
		}
	}
	
	
	class ChatMenuItemListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			clearChatArea();
			
		}
	}
	
	class TableMenuItemListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			clearMsgArea();
			
		}
	}
	
	/**An inner class that implements the ActionListener interface. Implements the action performed() method. Used to make the pass button work. 
	 * @author Shikhar
	 *
	 */
	class EnterKeyListener implements KeyListener
	{

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent argument) {
			if(argument.getKeyCode() == KeyEvent.VK_ENTER)
			{
				String chat=chatMessage.getText();
				CardGameMessage message = new CardGameMessage(7,-1,chat );
				((BigTwoClient)game).sendMessage(message);
				chatMessage.setText("");
				
			}
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
}//end of class	
	
	

