import javax.swing.JFrame;  // importing the JFrame from swing class.

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // this is done so that we can close the game
		window.setResizable(false);  // this is done so that game window can not be resizeable
		window.setTitle("Adventure of Blue Man"); // this is done to set the title
		
		
		gamePanel gmePanel = new gamePanel();
		window.add(gmePanel);
		
		/*Causes this window to be sized to fit the prefered size */
	    window.pack();
		window.setLocationRelativeTo(null);   // to set the window at the center
		window.setVisible(true);  // to make window visible to users.
	
	
	    gmePanel.startGameThread();
	}
}
