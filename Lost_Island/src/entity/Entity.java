package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.GamePanel;

public class Entity {
	protected GamePanel gp;
	
	public boolean showLOS = false;
	public int losStartX, losStartY;
    public List<int[]> losTiles = new ArrayList<>();
	
	public BufferedImage image,image2,image3;
	public BufferedImage up1,up2,up3,up4,up5,up6,
		down1,down2,down3,down4,down5,down6,
		left1,left2,left3,left4,left5,left6,
		right1,right2,right3,right4,right5,right6,
		idle1,idle2,idle3,idle4,idle5,idle6; // sử dụng lớp BufferedImage để đọc ảnh và vẽ ảnh
	public BufferedImage attackUp1,attackUp2,attackUp3,attackUp4;
	public BufferedImage attackDown1,attackDown2,attackDown3,attackDown4;
	public BufferedImage attackLeft1,attackLeft2,attackLeft3,attackLeft4;
	public BufferedImage attackRight1,attackRight2,attackRight3,attackRight4;
	public BufferedImage die1,die2,die3,die4;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	public int solidAreaDefaultX,solidAreaDefaultY;
	public boolean collisionOn = false;
	public boolean collision = false;
	
	//STATE
	public int spriteNum = 1;
	public String direction = "down";
	public int worldX,worldY;
	public boolean invincible = false;
	public boolean idle;
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn=false;

	//Counter
	public int blinkCounter =0;
	public int spriteCouter = 0;
	public int actionLockCounter =0;
	public int invincibleCounter=0;
	int dyingCouter=0;
	int hpBarCounter =0;

	public boolean attacking = false;

	public String name;
	public int type;
	public int speed;
	public int maxLife;
	public int life;
	public int attack;
	public int useCost;
	public Projectile projectile;
	
	// KnockBack
	public boolean knockBack = false;
	public String knockBackDirection = "";
	public int knockBackCounter = 0;
	public int knockBackDistance = 16; 
	
	public Entity(GamePanel gp) {
		this.gp=gp;
	}
	
	public void setAction() {
		
	}
	
	public void damageReaction() {
	    knockBack = true;
	    knockBackDirection =gp.player.direction;
	    knockBackCounter = 0;
	}
	
	public void update() {
		setAction();
		
	    if (knockBack) {
	        int knockSpeed = 4; // speed of knockback
	        switch (knockBackDirection) {
	            case "up": worldY -= knockSpeed; break;
	            case "down": worldY += knockSpeed; break;
	            case "left": worldX -= knockSpeed; break;
	            case "right": worldX += knockSpeed; break;
	        }
	        knockBackCounter += knockSpeed;
	        if (knockBackCounter >= knockBackDistance) {
	            knockBack = false;
	            knockBackCounter = 0;
	        }
	        return;
	    }
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkEntity(this, gp.monster);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
				
		if(this.type==2 && contactPlayer== true) {
			if(gp.player.invincible==false) {
				gp.playSE(3);
				gp.player.life-=1;
				gp.player.invincible=true;
			}
		}
		if (collisionOn == false) {
			switch (direction) {
			case "up":worldY -= speed;break;
			case "down":worldY += speed;break;
			case "left":worldX -= speed;break;
			case "right":worldX += speed;break;
			}
		}
		spriteCouter++;
		if (spriteCouter == 10) {
			spriteCouter = 0;
			spriteNum++;
			if(spriteNum==6) spriteNum=1;
		}
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter>20) {
				invincible=false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize
				&& worldX < gp.player.worldX + gp.player.screenX + gp.tileSize
				&& worldY > gp.player.worldY - gp.player.screenY - gp.tileSize
				&& worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
			if(dying == true) {
				dyingAnimation(g2);
				return;
			}
			
			if(direction.equals("up")) {
				if(spriteNum==1) image = up1;
				if(spriteNum==2) image = up2;
				if(spriteNum==3) image = up3;
				if(spriteNum==4) image = up4;
				if(spriteNum==5) image = up5;
				if(spriteNum==6) image = up6;
			}
			if(direction.equals("down")) {
				if(spriteNum==1) image = down1;
				if(spriteNum==2) image = down2;
				if(spriteNum==3) image = down3;
				if(spriteNum==4) image = down4;
				if(spriteNum==5) image = down5;
				if(spriteNum==6) image = down6;
			}
			if(direction.equals("left")||direction.equals("up-left")||direction.equals("down-left")) {
				if(spriteNum==1) image = left1;
				if(spriteNum==2) image = left2;
				if(spriteNum==3) image = left3;
				if(spriteNum==4) image = left4;
				if(spriteNum==5) image = left5;
				if(spriteNum==6) image = left6;
			}
			if(direction.equals("right")||direction.equals("up-right")||direction.equals("down-right")) {
				if(spriteNum==1) image = right1;
				if(spriteNum==2) image = right2;
				if(spriteNum==3) image = right3;
				if(spriteNum==4) image = right4;
				if(spriteNum==5) image = right5;
				if(spriteNum==6) image = right6;
			}
			if(type ==2&& hpBarOn==true) {
				double oneScale = (double)gp.tileSize/maxLife;
				double hpBarValue = oneScale*life;
				
				g2.setColor(new Color(35,35,35));
				g2.fillRect(screenX-1, screenY-13, gp.tileSize+2, 8);
				g2.setColor(new Color(255,0,30));
				g2.fillRect(screenX, screenY-12, (int)hpBarValue  , 6);
				
				hpBarCounter++;
				if(hpBarCounter>300) {
					hpBarCounter=0;
					hpBarOn=false;
				}
			}
			
			if (invincible == true) {
				hpBarOn = true;
				hpBarCounter=0;
			    blinkCounter++;
			    if (blinkCounter % 10 < 5) { // Nhấp nháy mỗi 10 frame
			        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // Vẽ quái vật bình thường
			    } else {
			        // Không vẽ gì để tạo hiệu ứng "biến mất"
			    }

			    if (blinkCounter > 60) { // Kết thúc nhấp nháy sau 60 frame
			        invincible = false;
			        blinkCounter = 0;
			    }
			} else {
			    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
		}
	}
	public void dyingAnimation(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		BufferedImage image = null;

		dyingCouter++;
		if (dyingCouter < 10) {
			image = die1;
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);	
		}
		if (dyingCouter < 20 && dyingCouter>=10) {
			image = die2;
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);	

		}
		if (dyingCouter < 30 && dyingCouter>=20) {
			image = die3;
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);	

		}
		if (dyingCouter < 40 && dyingCouter>=30) {
			image = die4;
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);	

		}
		if (dyingCouter >= 40) {
			alive=false;
		}
	}
}