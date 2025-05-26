package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import object.OBJ_Heart;

public class UI {
	GamePanel gp;
	Font arial_40;
	Font nomal;
	public boolean messageOn=false;
	public String message ="";
	Graphics2D g2;
	BufferedImage image,background,cursor;
	public int commandNum=0;
	BufferedImage heart_full,heart_half,heart_blank;
	BufferedImage killCount;
	
	
	public UI(GamePanel gp) {
		this.gp=gp;
		arial_40=new Font("Super Pixel", Font.PLAIN,40);
		nomal=new Font("Arial", Font.PLAIN,40);

		try {
			killCount = ImageIO.read(getClass().getResourceAsStream("/UI/KillCount.png"));
			background = ImageIO.read(getClass().getResourceAsStream("/background/background.jpg"));
			cursor = ImageIO.read(getClass().getResourceAsStream("/UI/UI.png"));

		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image2;
		heart_half = heart.image3;
		heart_blank = heart.image;
	}
	
	public void draw(Graphics2D g2) {
		this.g2=g2;
		g2.setFont(arial_40);
		
		if(gp.gameState==gp.playState) {
			drawPlayerLife();
		}
		
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		if(gp.gameState==gp.pauseState) {
			drawPauseScreen();
		}
		if(gp.gameState==gp.gameOverState) {
			drawGameOverScreen();
		}
	}
	
	public void drawPlayerLife() {
		
		int x = gp.tileSize/2;
		int y=gp.tileSize/2;
		
		int i=0;
		while(i<gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y,60,60, null);
			i++;
			x+=gp.tileSize*1.5;
		}
		
		 x = gp.tileSize/2;
		 y=gp.tileSize/2;
		 i=0;
		 
		 while(i<gp.player.life) {
			 g2.drawImage(heart_half, x, y,60,60, null);
			 i++;
			 if(i<gp.player.life) {
				 g2.drawImage(heart_full, x, y,60,60, null);
			 }
			 i++;
			 x+=gp.tileSize*1.5;
		 }
		g2.setFont(nomal);
		String kill =""+ gp.player.killCount;
		g2.setColor(Color.white);
		g2.drawString(kill, gp.tileSize*14+16, gp.tileSize*1+12);
		g2.drawImage(killCount, gp.tileSize*13, gp.tileSize-24,48,48, null);

	}
	
	public void drawTitleScreen() {
		g2.drawImage(background, 0, 0,gp.screenWitdth , gp.screenHeight, null);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,84F));
		
		String text= "LOST ISLAND";
		int x=getXForCenteredText(text);
		int y = gp.screenHeight/3;
		g2.setColor(new Color(0,255,255));
		g2.drawString(text, x+3, y);
		g2.setColor(new Color(212,6,224));
		g2.drawString(text, x-3, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		text = "NEW GAME";
		x=getXForCenteredText(text);
		y+=gp.tileSize*3+32;
		g2.setColor(new Color(0,255,255));
		g2.drawString(text, x+1, y);
		g2.setColor(new Color(212,6,224));
		g2.drawString(text, x-1, y);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		if(commandNum==0) {
			g2.drawImage(cursor, x-gp.tileSize, y-32,25 , 42, null);
		}
		
		text = "EXIT";
		x=getXForCenteredText(text);
		y+=gp.tileSize+15;
		g2.setColor(new Color(0,255,255));
		g2.drawString(text, x+1, y);
		g2.setColor(new Color(212,6,224));
		g2.drawString(text, x-1, y);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		if(commandNum==1) {
			g2.drawImage(cursor, x-gp.tileSize, y-32,25 , 42, null);
		}
	}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		
		g2.fillRect(0, 0, gp.screenWitdth, gp.screenHeight);
		int x;
		int y;
		String text;
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,86F));
		
		text = "GAME OVER";
		x=getXForCenteredText(text);
		y=gp.tileSize*5-12;
		g2.setColor(new Color(0,255,255));
		g2.drawString(text, x+1, y);
		g2.setColor(new Color(212,6,224));
		g2.drawString(text, x-1, y);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		text = "RETRY";
		x=getXForCenteredText(text);
		y+=gp.tileSize*3;
		g2.setColor(new Color(0,255,255));
		g2.drawString(text, x+1, y);
		g2.setColor(new Color(212,6,224));
		g2.drawString(text, x-1, y);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		if(commandNum==0) {
			g2.drawImage(cursor, x-gp.tileSize, y-32,25 , 42, null);
		}
		
		text = "QUIT";
		x=getXForCenteredText(text);
		y+=gp.tileSize+15;
		g2.setColor(new Color(0,255,255));
		g2.drawString(text, x+1, y);
		g2.setColor(new Color(212,6,224));
		g2.drawString(text, x-1, y);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		if(commandNum==1) {
			g2.drawImage(cursor, x-gp.tileSize, y-32,25 , 42, null);
		}
	}
	
	public void drawPauseScreen() {
		String text = "GAME PAUSED";
		int	x = getXForCenteredText(text);
		int y= gp.screenHeight/2;
		
		g2.setColor(new Color(0,255,255));
		g2.drawString(text, x+1, y);
		g2.setColor(new Color(212,6,224));
		g2.drawString(text, x-1, y);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		g2.drawString(text, x, y);
		
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn=true;
	}
	
	public int getXForCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWitdth/2 - length/2;
		return x;
	}
}
