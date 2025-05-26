package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;

public class player extends Entity {
	KeyHandler keyH;
	
	private int projectileCooldown = 0;
	private final int projectileCooldownTime = 30;
	
	public final int screenX;
	public final int screenY;
	public int killCount;
	
	public player(GamePanel gp , KeyHandler keyH) {
		super(gp);
		this.keyH=keyH;
		
		this.screenY = gp.screenHeight/2- (gp.tileSize/2);
		this.screenX = gp.screenWitdth/2-(gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x= 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		solidArea.width = 32;
		solidArea.height = 32;
		
		attackArea.width=52;
		attackArea.height=52;
		
		this.setDefaultValue();
		getPlayerImage();
		getPlayerAttackImage();
	}
	
	public void setDefaultPositions() {
		worldX = gp.tileSize*51;
		worldY = gp.tileSize*51;
		direction = "right";
		life = maxLife;
		invincible=false;
		killCount=0;

	}
	
	public void setDefaultValue() {
		worldX = gp.tileSize*51;
		worldY = gp.tileSize*51;
		speed = 4;
		direction = "right";
		
		maxLife = 10;
		life= maxLife;
		projectile = new OBJ_Fireball(gp);
		killCount=0;
		attack=2;
	}
	
	public void getPlayerImage() {
			// sử dụng lớp BufferedImage để đọc ảnh từ tệp
			try {
				up1= ImageIO.read(getClass().getResourceAsStream("/player/tile030.png"));
				up2= ImageIO.read(getClass().getResourceAsStream("/player/tile031.png"));
				up3= ImageIO.read(getClass().getResourceAsStream("/player/tile032.png"));
				up4= ImageIO.read(getClass().getResourceAsStream("/player/tile033.png"));
				up5= ImageIO.read(getClass().getResourceAsStream("/player/tile034.png"));
				up6= ImageIO.read(getClass().getResourceAsStream("/player/tile035.png"));
				down1= ImageIO.read(getClass().getResourceAsStream("/player/tile018.png"));
				down2= ImageIO.read(getClass().getResourceAsStream("/player/tile019.png"));
				down3= ImageIO.read(getClass().getResourceAsStream("/player/tile020.png"));
				down4= ImageIO.read(getClass().getResourceAsStream("/player/tile021.png"));
				down5=ImageIO.read(getClass().getResourceAsStream("/player/tile022.png"));
				down6= ImageIO.read(getClass().getResourceAsStream("/player/tile023.png"));
				left1= ImageIO.read(getClass().getResourceAsStream("/player/tile024x.png"));
				left2= ImageIO.read(getClass().getResourceAsStream("/player/tile025x.png"));
				left3= ImageIO.read(getClass().getResourceAsStream("/player/tile026x.png"));
				left4= ImageIO.read(getClass().getResourceAsStream("/player/tile027x.png"));
				left5= ImageIO.read(getClass().getResourceAsStream("/player/tile028x.png"));
				left6= ImageIO.read(getClass().getResourceAsStream("/player/tile029x.png"));
				right1= ImageIO.read(getClass().getResourceAsStream("/player/tile024.png"));
				right2= ImageIO.read(getClass().getResourceAsStream("/player/tile025.png"));
				right3= ImageIO.read(getClass().getResourceAsStream("/player/tile026.png"));
				right4= ImageIO.read(getClass().getResourceAsStream("/player/tile027.png"));
				right5= ImageIO.read(getClass().getResourceAsStream("/player/tile028.png"));
				right6= ImageIO.read(getClass().getResourceAsStream("/player/tile029.png"));
				idle1= ImageIO.read(getClass().getResourceAsStream("/player/tile006.png"));
				idle2= ImageIO.read(getClass().getResourceAsStream("/player/tile007.png"));
				idle3= ImageIO.read(getClass().getResourceAsStream("/player/tile008.png"));
				idle4= ImageIO.read(getClass().getResourceAsStream("/player/tile009.png"));
				idle5= ImageIO.read(getClass().getResourceAsStream("/player/tile010.png"));
				idle6= ImageIO.read(getClass().getResourceAsStream("/player/tile011.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	public void getPlayerAttackImage() {
			try {
				attackUp1 = ImageIO.read(getClass().getResourceAsStream("/player/tile048.png"));
				attackUp2 = ImageIO.read(getClass().getResourceAsStream("/player/tile049.png"));
				attackUp3 = ImageIO.read(getClass().getResourceAsStream("/player/tile050.png"));
				attackUp4 = ImageIO.read(getClass().getResourceAsStream("/player/tile051.png"));
	
				attackDown1 = ImageIO.read(getClass().getResourceAsStream("/player/tile036.png"));
				attackDown2 = ImageIO.read(getClass().getResourceAsStream("/player/tile037.png"));
				attackDown3 = ImageIO.read(getClass().getResourceAsStream("/player/tile038.png"));
				attackDown4 = ImageIO.read(getClass().getResourceAsStream("/player/tile039.png"));
	
				attackLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/tile042x.png"));
				attackLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/tile043x.png"));
				attackLeft3 = ImageIO.read(getClass().getResourceAsStream("/player/tile044x.png"));
				attackLeft4 = ImageIO.read(getClass().getResourceAsStream("/player/tile045x.png"));
	
				attackRight1 = ImageIO.read(getClass().getResourceAsStream("/player/tile042.png"));
				attackRight2 = ImageIO.read(getClass().getResourceAsStream("/player/tile043.png"));
				attackRight3 = ImageIO.read(getClass().getResourceAsStream("/player/tile044.png"));
				attackRight4 = ImageIO.read(getClass().getResourceAsStream("/player/tile045.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

	}
	
	public void update() {
		if(attacking==true) {
			attacking();
		}
		else if (keyH.upPress == true || keyH.downPress == true || keyH.leftPress == true || keyH.rightPress == true||keyH.enterPress==true) {
			if (keyH.upPress && keyH.leftPress) {
				direction = "up-left";
			} else if (keyH.upPress && keyH.rightPress) {
				direction = "up-right";
			} else if (keyH.downPress && keyH.leftPress) {
				direction = "down-left";
			} else if (keyH.downPress && keyH.rightPress) {
				direction = "down-right";
			} else if (keyH.upPress) {
				direction = "up";
			} else if (keyH.downPress) {
				direction = "down";
			} else if (keyH.leftPress) {
				direction = "left";
			} else if (keyH.rightPress) {
				direction = "right";
			}

			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//Check Monster collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			if (keyH.enterPress == true) {
			    if (!attacking) {
			        attacking = true;
			        gp.playSE(6);
					keyH.enterPress=false;	 
			    }
			}
			
			if (collisionOn == false&& keyH.enterPress==false) {
				switch (direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				// Di chuyển chéo, giảm tốc độ bằng √2
				
				case "up-left": worldX -= speed / Math.sqrt(2);  worldY -= speed / Math.sqrt(2); break;
				case "up-right": worldX += speed / Math.sqrt(2); worldY -= speed / Math.sqrt(2); break;
				case "down-left": worldX -= speed / Math.sqrt(2)+0.3; worldY += speed / Math.sqrt(2)+0.3; break;
				case "down-right": worldX += speed / Math.sqrt(2)+0.3; worldY += speed / Math.sqrt(2)+0.3; break;
				}
			}
		}	 
		if (!attacking) {
		    spriteCouter++;
		    if (spriteCouter == 10) {
		        spriteCouter = 0;
		        spriteNum++;
		        if(spriteNum == 6) spriteNum = 1;
		    }
		}
		
	    if (projectileCooldown > 0) {
	        projectileCooldown--;
	    }

	    if (keyH.shotKeyPress == true && projectile.alive == false && projectileCooldown == 0) {
	        projectile.set(worldX, worldY, direction, true, this);
	        gp.projectileList.add(projectile);
	        gp.playSE(8);
	        projectileCooldown = projectileCooldownTime;
	    }
		
		if(invincible == true) {
			invincibleCounter++;
			blinkCounter++;
			if(blinkCounter>60) {
				blinkCounter=0;
			}
			if(invincibleCounter>60) {
				invincible=false;
				invincibleCounter = 0;
			}
		}
		
		if(life<= 0) {
			gp.gameState=gp.gameOverState;
			gp.stopMusic();
			gp.playSE(4);
			gp.ui.commandNum=0;
		}
	}
	public void attacking() {
		spriteCouter++;
		
		if(spriteCouter<=2) {
			spriteNum=1;
		}
		if(spriteCouter>2 && spriteCouter<=12) {
			spriteNum=2;
			
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			switch(direction) {
			case"up": worldY -= attackArea.height;break;
			case"down": worldY += attackArea.height;break;
			case"left": worldX -= attackArea.width;break;
			case"down_left": worldX -= attackArea.width;break;
			case"up_left": worldX -= attackArea.width;break;

			case"right": worldX += attackArea.width;break;
			case"down_right": worldX += attackArea.width;break;
			case"up_right": worldX += attackArea.width;break;
			}
			
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, this.attack);
			
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
			
		}
		if(spriteCouter>12&& spriteCouter<=15) {
			spriteNum=3;
		}
		if(spriteCouter>15 && spriteCouter<=20) {
			spriteNum=4;
		}
		if(spriteCouter>20) {
			spriteNum=1;
			spriteCouter=0;
			attacking=false;
		}
	}

	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		if(invincible) {
			if(blinkCounter%12<5) {
				return;
			}
		}
		if(idle) {
			if(spriteNum==1) image = idle1;
			if(spriteNum==2) image = idle2;
			if(spriteNum==3) image = idle3;
			if(spriteNum==4) image = idle4;
			if(spriteNum==5) image = idle5;
			if(spriteNum==6) image = idle6;
		}
		
		if(direction.equals("up")) {
			if(attacking ==false) {
				if(spriteNum==1) image = up1;
				if(spriteNum==2) image = up2;
				if(spriteNum==3) image = up3;
				if(spriteNum==4) image = up4;
				if(spriteNum==5) image = up5;
				if(spriteNum==6) image = up6;
			}
			if(attacking == true) {
				if(spriteNum==1) image = attackUp1;
				if(spriteNum==2) image = attackUp2;
				if(spriteNum==3) image = attackUp3;
				if(spriteNum==4) image = attackUp4;

			}
		}
		if(direction.equals("down")) {
			if(attacking==false) {
				if(spriteNum==1) image = down1;
				if(spriteNum==2) image = down2;
				if(spriteNum==3) image = down3;
				if(spriteNum==4) image = down4;
				if(spriteNum==5) image = down5;
				if(spriteNum==6) image = down6;
			}
			if(attacking == true) {
				if(spriteNum==1) image = attackDown1;
				if(spriteNum==2) image = attackDown2;
				if(spriteNum==3) image = attackDown3;
				if(spriteNum==4) image = attackDown4;
			}
		}
		if(direction.equals("left")||direction.equals("up-left")||direction.equals("down-left")) {
			if(attacking==false) {
				if(spriteNum==1) image = left1;
				if(spriteNum==2) image = left2;
				if(spriteNum==3) image = left3;
				if(spriteNum==4) image = left4;
				if(spriteNum==5) image = left5;
				if(spriteNum==6) image = left6;
			}
			if(attacking == true) {
				if(spriteNum==1) image = attackLeft1;
				if(spriteNum==2) image = attackLeft2;
				if(spriteNum==3) image = attackLeft3;
				if(spriteNum==4) image = attackLeft4;

			}
		}
		if(direction.equals("right")||direction.equals("up-right")||direction.equals("down-right")) {
			if(attacking == false) {
				if(spriteNum==1) image = right1;
				if(spriteNum==2) image = right2;
				if(spriteNum==3) image = right3;
				if(spriteNum==4) image = right4;
				if(spriteNum==5) image = right5;
				if(spriteNum==6) image = right6;
			}
			if(attacking == true) {
				if(spriteNum==1) image = attackRight1;
				if(spriteNum==2) image = attackRight2;
				if(spriteNum==3) image = attackRight3;
				if(spriteNum==4) image = attackRight4;
			}	
		}
		
		
		for (int i = 0; i < gp.monster.length; i++) {
		    if (gp.monster[i] != null && gp.monster[i].showLOS) {
		        int monsterScreenX = gp.monster[i].losStartX - gp.player.worldX + gp.player.screenX;
		        int monsterScreenY = gp.monster[i].losStartY - gp.player.worldY + gp.player.screenY;
		        int playerScreenX = gp.player.screenX + gp.tileSize / 2;
		        int playerScreenY = gp.player.screenY + gp.tileSize / 2;
		        g2.setColor(Color.GREEN);
		        g2.drawLine(monsterScreenX + gp.tileSize / 2, monsterScreenY + gp.tileSize / 2,
		                    playerScreenX, playerScreenY);
		    }
		}
		
		g2.drawImage(image, screenX-12, screenY-4, 70,70, null);
		g2.setColor(Color.red);
		
		g2.drawRect(worldX + solidArea.x - gp.player.worldX + gp.player.screenX,
	            worldY + solidArea.y - gp.player.worldY + gp.player.screenY,
	            solidArea.width, solidArea.height);
	}
	
	public void contactMonster(int i) {
		if( i!=999) {
			if(invincible == false && gp.monster[i].dying == false) {
				gp.playSE(3);
				life-=gp.monster[i].attack;
				invincible=true;
			}
		}
	}
	public void damageMonster(int i,int attack) {
		if (i != 999) {
			if (gp.monster[i].invincible == false) {
				gp.playSE(5);
				gp.monster[i].life -= attack;
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
				if (gp.monster[i].life <= 0) {
					life+=1;
					killCount+=1;
					if(life >maxLife) {
						life=maxLife;
					}
					gp.monster[i].dying = true;
				}
			}
		}
	}
}