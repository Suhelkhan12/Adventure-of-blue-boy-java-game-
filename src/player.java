import java.awt.Color;
import java.awt.Graphics;

public class player extends entity {
	
	gamePanel gp;
	keyHandlers keyH;
	
	
	public player(gamePanel gp , keyHandlers keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		setDefaultValues();
	}
	
	public void setDefaultValues() {
		
		x = 100;
		y = 100;
		speed = 4;
	
	}
	
	
	// same impletation just like gamepanel class
	public void update() {
		if(keyH.upPressed == true) {
			y -= speed;
		}
		
		else if(keyH.downPressed == true) {
			y += speed;
		}
		
		else if(keyH.leftPressed == true) {
			x -= speed;
		}
		
		else if(keyH.rightPressed == true) {
			x += speed;
		}
	}
	
	
    // same implementation like gameplanel
	public void draw(Graphics g2) {
		g2.setColor(Color.white);
		
		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
	}

}
