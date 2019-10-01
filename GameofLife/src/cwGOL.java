//All these imports are needed for the different parts of the interface
	//Read up in the Javadocs on each of these classes...
	import javax.swing.AbstractButton;
	import javax.swing.JFrame;
	import javax.swing.JButton;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
	import javax.swing.SwingUtilities;
	import javax.swing.Timer;
	import javax.swing.event.ChangeEvent;
	import javax.swing.event.ChangeListener;

	import java.awt.Color;
	import java.awt.Graphics;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.MouseEvent;
	import java.awt.event.MouseListener;

	
	//Our class needs to inherit functionality from 'JPanel', 'JChange Listener', 'JMouseListener' and 'JActionListener'
	public class cwGOL extends JPanel implements ActionListener,ChangeListener, MouseListener
	{
		//Variables are listed below
		//All forms need a unique ID - ignore this!!!
	    private static final long serialVersionUID = 1862962349L;
	    //We are going to have three buttons
	    private JButton startbtn,exitbtn,pausebtn,resetbtn;
		//For this version of the program we are going to have two labels
		private JLabel labelonecaption, label;
		//The timer is for updating the labels at a regular interval 
		private Timer timer;
		//Constructor for our form
		private boolean[][] grid1, grid2;
		private int gridsize = 30;
		// Size of the Grid
		public int iteration = 0;
	    public cwGOL() 
	    {
	    	// Creating an array. This used to represent a grid of variables 
	    	grid1 = new boolean[gridsize][gridsize];
	    	grid2 = new boolean[gridsize][gridsize];
	    	 //Create a timer every second (500 m/s)
	        //The current instance of 'this' class will process the actions for the timer
	        timer = new Timer(125, this);// This is an example of an object being created
	        //An object is a programming construct that usually holds data and represents a key component of a programming problem
	        //Create the event/action 'Timer' every second
	        timer.setActionCommand("Timer");
	        //Start the timer straight away
	        timer.setInitialDelay(0);
	    	
	        startbtn = new JButton("Start");
	    	startbtn.setVerticalTextPosition(AbstractButton.CENTER);
	    	startbtn.setHorizontalTextPosition(AbstractButton.CENTER);
	    	startbtn.setBounds(600, 350, 90, 50);	       
	        //button to start game and its positioning
	    	
	        pausebtn = new JButton("Pause");
	    	pausebtn.setVerticalTextPosition(AbstractButton.CENTER);
	    	pausebtn.setHorizontalTextPosition(AbstractButton.CENTER);	    	
	    	pausebtn.setBounds(700, 450, 90, 50);	        
	        pausebtn.setEnabled(false);
	        //Initially the stop button is disabled
	        //button to pause game and its positioning
	        
	        exitbtn = new JButton("Exit");
	    	exitbtn.setVerticalTextPosition(AbstractButton.CENTER);
	    	exitbtn.setHorizontalTextPosition(AbstractButton.CENTER);	    		    	
	    	exitbtn.setBounds(800, 450, 90, 50);
	       
	        //button to exit game and its positioning.
	        resetbtn = new JButton("Reset");
	    	resetbtn.setVerticalTextPosition(AbstractButton.CENTER);
	    	resetbtn.setHorizontalTextPosition(AbstractButton.CENTER);    	
	    	resetbtn.setBounds(900, 450, 90, 50);
	        //button to restart game.
	        
	    	labelonecaption = new JLabel("The is Conway's Game of Life");      	               
	        label = new JLabel("count:0");
	        //Creating labels 
	        
	        
	      
	        startbtn.setToolTipText(" Start Game of Life");
	        pausebtn.setToolTipText("Pause Game");
	        exitbtn.setToolTipText(" Click this button to close game (This is the only way to close the game permanantely");
	        resetbtn.setToolTipText(" Rest or restart game ");	        
	        //Set tool tip text for Buttons 
	        
	        add(startbtn);
	        add(pausebtn);
	        add(exitbtn);
	        add(resetbtn);
	        add(labelonecaption); 
	        add(label);
	        //Add all buttons and labels 
	       
	        startbtn.setActionCommand("Start");
	        pausebtn.setActionCommand("Pause");
	        exitbtn.setActionCommand("exit");
	    	resetbtn.setActionCommand("Reset");	        
	        // Set button Action commands 
	    	
	    	startbtn.addActionListener(this);
	    	pausebtn.addActionListener(this);
	    	exitbtn.addActionListener(this);
	    	resetbtn.addActionListener(this);
	    	// Set button Action listener 
	        
	        newgrid();
	     // random arrangement of alive and dead cells
	    }
	   
	 public void newgrid() {
	   //The For loop was used to execute a block that needs to be repeated a specific number of times
	    	//make first one
	    	for(int x=0; x<gridsize; x++)
	    	{
	    		for(int y=0; y<gridsize; y++)
	    		{
	    			if (Math.random() < 0.1) //generate dead or alive cells randomly
	    			{
	    			// This is an example of a built - in function within this program,
	    			//a built-in function is a predefined self contained program that performs a set task
	    				grid1[y][x] = true;
	    			} else {
	    				grid1[y][x] = false;
	    			}
	    		}
	    	}
	    	
	    	
	    	
	    	
	    	repaint();
	    }
	    

	 public void iteration() {
	 // Conditional statement were mainly used here, Conditional statements are used to selectively run different sections of program code 
	//depending on some condition

		 //use first and second grid
	 	
	 	for(int x=0; x<gridsize; x++)
	 	{
	 		for(int y=0; y<gridsize; y++)
	 		{
	 			grid2[y][x] = grid1[y][x];
	 		}
	 	}
	 	
	 	
	 	
	 	for(int x=0; x<gridsize; x++)
	 	{
	 		for(int y=0; y<gridsize; y++)
	 		{
	 			
	 			if (grid1[y][x] == true) 
	 			{
	 			//Conditional statement to see whether the cell is true
	 				if ((nearby(y,x) < 2 || nearby(y,x) > 3)) 
	 				{
	 				// Conditional statement to see if there is less than 2 0r more than 3 neighbour cells alive
	 					grid2[y][x] = false;
	 				}// the cell will die 
	 			} else 
	 			{
	 				if ((nearby(y,x) == 3)) 
	 				{
	 				//Conditional statement to check if the dead cell has 3 alive neighbours
	 					grid2[y][x] = true;
	 				}// the cell will be alive 
	 			}
	 		}
	 	}
	 	
	 	for(int x=0; x<gridsize; x++)
	 	{
	 		for(int y=0; y<gridsize; y++)
	 		{
	 			grid1[y][x] = grid2[y][x];
	 		}
	 	}
	 	
	 }    
	    
	 public int nearby(int i, int j) {
	 	//check for nearby alive cells
	 	int nearby = 0;
	 	
	 	for(int b=-1; b<2; b++)
	 	{
	 		for(int a= -1; a<2; a++)
	 		{
	 			if (((a == 0) && (b == 0)) == false) 
	 			//Conditional statement to exclude centre cell
	 			{
	 					
	 				int x = i+a;
	 				int y = j+b;
	 				
	 				if (x < 0) 
	 				{						
	 				//Conditional statement for not going outside the grid	
	 					x = gridsize-1;
	 				} else if (x > gridsize-1) 
	 				{
	 					x = 0;
	 				}
	 				if (y < 0) {
	 					y = gridsize-1;
	 				} else if (y > gridsize-1) 
	 				{
	 					y = 0;
	 				}										
	 				
	 				
		    			if (grid1[x][y] == true) 
		    			{
		    				nearby++;
		    			}
	 			}
	 			
	 		}
	 	}
	 	
	 	return nearby;
	 	
	 }   
	    	    
	    
	    //The method must be implemented as we have inherited from 'JActionListener'
	    // for the method to run  a button is clicked or a timer event occurs
	 	//Methods simply perform a specific task
	    public void actionPerformed(ActionEvent e) 
	    {
	    	if (e.getActionCommand().equals("Start")) 
	        {
	    		
	            timer.start();
	            System.out.println("Game Started");
	            startbtn.setEnabled(false);
	            //disable start button once it is clicked
	            pausebtn.setEnabled(true);
	            // enable pause button once the start button is clicked
	        }
	    	//Test for the 'Start' action
	    	if (e.getActionCommand().equals("Pause"))
	        {
	    		timer.stop();
	    		System.out.println("Game Paused");
	    		startbtn.setEnabled(true);
	    		//Enable start once the pause button is clicked
	    		pausebtn.setEnabled(false);
	    		//disable pause button once it is clicked
	        }
	    	//Test for the 'Pause' action
	    	if (e.getActionCommand().equals("Timer"))
	        {
	    		iteration++;// This an example of a user defined function and method being called.
	    		// A user defined function is a pre defined task created by the user.
	    		// A method is a set of code which is referred to by name and can be called
	    		label.setText("count " + iteration);
	    		iteration();
	        	repaint();
	        }
	    	//Test for the 'Timer' action
	    	
	    	if (e.getActionCommand().equals("Reset"))
	        {
	    		iteration = 0;
	    		label.setText("count " + iteration);
	        	newgrid();
	        	System.out.println("Game Reset");
	        	// Log that something has happened
	        }
	    	
	    	if (e.getActionCommand().equals("exit"))
	        {
	        	System.out.println("Game Exit");
	        	timer.stop();
	        	JFrame parent = (JFrame)SwingUtilities.getWindowAncestor(this);
	        	//Get the parent JFrame of this panel
	        	parent.setVisible(false);
	        	//Hide the window
	        	parent.dispose();
	        	//Get rid of the Window
	        } 
	    	//Test for the 'Exit' action   	
	    }
	    //Override the super class paint method
	    @Override
	    public void paint(Graphics g) 
	    {
	    	// super class paint method is called 
	    	//This is needed to make sure all the components are drawn correctly
	    	super.paint(g);
	    	for (int j = 0; j < gridsize; j++) 
	    	{
	    		for (int i = 0; i < gridsize; i++) 
	    		{
	    			
	    			if (grid1[i][j] == false) 
	    			{
	    			
	    				g.setColor(Color.RED);// Display colour red
	    				g.fillRect(60 + 10*i,60 + 10*j,10,10);
	    			}
	    			else 
	    			{
	    				g.setColor(Color.BLUE);// Display colour blue
	    				g.fillRect(60 + 10*i,60 + 10*j,10,10);
	    			}
	    		}
	    	}
	    
	    }
	    @Override
		public void mouseClicked(MouseEvent arg0) 
	    {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseEntered(MouseEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}
		@Override
		public void stateChanged(ChangeEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}

		
		







	}



