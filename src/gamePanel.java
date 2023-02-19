import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

// this game panel works as a game screen
public class gamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS WHICH HOW OUR SCREEN WIL LOOK
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* 16 x 16 tiles basically just 
	 * like the matrix of our snake game*/
	final int originalTileSize = 16; 
	
	/* original size of the game tile or cube of the matrix is 16x16
	 * but that is too small to today's computer so we scale the size
	 * that is why this scale varrribale is created . It will help to scale things*/
	final int scale  =3;
	
	/*actual tile size of the game 16x3*/
	public final int tileSize = originalTileSize * scale; 
	
	/*they are going to decide the number of rows and column in the 
	 * window of the game JUST LIKE THE MATRIX OF OUR SNAKE GAME*/
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	
	
	/*this is just simple math to decide the max width and heigh of
	 * the window when the cubes are give just like our snake game.*/
	final int screenWidth = tileSize * maxScreenCol;  //768px
	final int screenHeight = tileSize * maxScreenRow; //576px
	
	
	// FPS setting.
	int FPS = 60;
	/*the logics for the 2D game is that we can all the other character other 
	 * than the main charater keep on moving randomly in the confined space this is done
	 * with help of our THREADS.*/
	Thread gameThread;
	
	
	
	// instantiating our keyhandler class
	
	keyHandlers keyHandle = new keyHandlers();
	
//	instantiating player class
	
	player play = new player(this, keyHandle);
	
	
	// set player's defaul positions
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 3;  // this means 4 pixels at a time.
	
	
	
	
	
	// CONSTRUCTor OF GAME PANEl
	public gamePanel() {
		/*this will be used to set the prefered size of the game panel*/
		 this.setPreferredSize(new Dimension(screenWidth , screenHeight));
		 this.setBackground(Color.black);
		 
		 /*if this is set to true then the drawing from this component will be done in an 
		  * offscreen painting buffer. In short makes rendering easy*/
		 this.setDoubleBuffered(true); 
		 
		 
		 // adding key handler to panel
		 this.addKeyListener(keyHandle);
		 this.setFocusable(true);
	}


	public void startGameThread() {
		
		// here we have instantiated our gameThread with the instance of gamePanel class
		// which is help full for our program to run.
		gameThread = new Thread(this);  // this (this) is used as the object of our gamePanel class
		gameThread.start(); // this will automatically call our run method and in run() method
		// we will write the game loop which will be CORE of our game.
		
	}
	
	
	@Override  // mandatory to define this method because our thread wo'nt run without it.
	public void run() {
		// TODO Auto-generated method stub
		
		//====>>>>>>>>>>>>>>.  NANO SECONDS IS USED AS CALCULATION UNIT IN THIS GAME.
		
		// ====>>>>>>>>>>>>>>>.. varriable for showing the container after a key event
		double drawInterval = 1000000000/FPS;             /// THIS NUMBER MEANS 0.0166 SECOND IN nano seconds.
		
		// supposse if we draw the rectangle right now then the next draw time will be = to current time in nanosec + drawInteval(0.0166)
		double nextDrawTime = System.nanoTime() + drawInterval;		
		
		
       
		
        // this is for getting current time during update and during repaint of the rectangle on the screen
//		long currentTime = System.nanoTime();  // more precise
//		long currentTime2 = System.currentTimeMillis(); // can use this too
		
		// this the game loop and it is the core of our game and it will run everyting assosiated with the game
		while(gameThread != null) {   // it means as long as gameThread exists it will run things which are in the loop

			// this is just the testing
			//System.out.print("Game is running in the loop"); 
			
			/*
			 *here we are going to do two kind of thing
			 *
			 1 UPDATE: update information such as character positions
			    example : suppose initially the charater coordinates in the screen are
			    100 , 100 and player hits any of the arrow key then there must be updates according to the keys
			    we will add pixels according to the movement speed of our player.
			 
			
			 2 DRAW: draw the  screen with the updated information
			    then based on the updated information we will draw the character in the screen.
			    
			    
			 ===>>> BOTH OF THE ABOVE THINGS ARE GOING TO BE REPEATED AGAIN AND AGAIN.
			 
			 ===>>> if the FPS of the game is set to 30 then we will do these update and draw things 30 times per second and lly for 60 FPS
			 */
			
			update();
			
			// to redraw things
			repaint(); // also a built in func
			
			
			// after this (update,repaint) and nextDrawInterval there might be some time left we want to know that time
			
			
				try {
					double remainingTime = nextDrawTime - System.nanoTime();
					remainingTime = remainingTime/1000000;   // here we are converting our remainingtime from nano secs to milli seconds.
					
					if(remainingTime < 0) {
						remainingTime = 0;
					}
					
					Thread.sleep( (long)remainingTime);
					
					// when thread is out of sleep nextDrawTime is changed.
					nextDrawTime += drawInterval; 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
	
	
	
	// this method is going to be used for update and change player positon
	
	
	/* =======>>>>>>>>>>>>>  CATCH WITH UPDATE AND PAINT
	 * when we press any key the cotainer moves and repaint paint it again at the location desired by the user
	 * but this thing is happenning again and again and computer is doing it as fast as possible in a loop 
	 * this will not allow our container to be displayed on the screen it comes -> updated -> repaints and so on
	 * do not stay static on the screen. because simple container drawing is not heavy for GPU TO DRAW 
	 * THEY RECTANGLE MOVES WITH A VERY HIGH SPEED AND GOES OUT OF THE RECTANGLE PANEL WIDTH.
	 * we have to set some restrictions  on the container ===>> SLOW down , update and draw for this time only.
	 *
	 *
	 *to do that hme ye pta krna hoga bhi kya TIME hai and update and repaint me kita time lapse ho gya hai iske
	 *bina hm nahi kr payege ye 
	 * */
	public void update() {
	
		
		// calling update of player class same implementation
		play.update();
		
	}
	
	
	// this paintcomponent is a built in method in JAVA. it is the standard method to draw things
	public void paintComponent(Graphics g) {
		
		// here we are calling  constructor of the parent class
		// here parent class is JPanel becoz is this gamepanel is subclass of JPanel class. Standard set by JAVA.
		super.paintComponent(g);
		
		
		// here consider this graphics g as a paint brush.
		/*
		 * graphics 2D class extends Graphics class to provide more sphisticated control over geometry ,
		 *  coordinate tranformations , color management and text layouts.
		 * */
		Graphics2D g2 = (Graphics2D)g;
		
        play.draw(g2);
		
		g2.dispose();  // good practice to save memories 
		
	}
	
}
