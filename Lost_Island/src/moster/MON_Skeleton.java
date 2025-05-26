package moster;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class MON_Skeleton extends Entity {
	public MON_Skeleton(GamePanel gp) {
		super(gp);
		this.gp=gp;
		name= "Blue Slime";
		speed=1;
		maxLife =8;
		life = maxLife;
		attack = 1;
		
		type=2;
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height=30;
		solidAreaDefaultX= solidArea.x;
		solidAreaDefaultY= solidArea.y;
		
		getImage();
	}
	
	public void getImage() {
		try{
			// sử dụng lớp BufferedImage để đọc ảnh từ tệp
			up1= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile030.png"));
			up2= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile031.png"));
			up3= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile032.png"));
			up4= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile033.png"));
			up5= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile034.png"));
			up6= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile035.png"));
			down1= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile018.png"));
			down2= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile019.png"));
			down3= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile020.png"));
			down4= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile021.png"));
			down5= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile022.png"));
			down6= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile023.png"));
			left1= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile024x.png"));
			left2= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile025x.png"));
			left3= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile026x.png"));
			left4= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile027x.png"));
			left5= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile028x.png"));
			left6= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile029x.png"));
			right1= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile024.png"));
			right2= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile025.png"));
			right3= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile026.png"));
			right4= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile027.png"));
			right5= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile028.png"));
			right6= ImageIO.read(getClass().getResourceAsStream("/monsterS/tile029.png"));
			die1=ImageIO.read(getClass().getResourceAsStream("/monsterS/tile036.png"));
			die2=ImageIO.read(getClass().getResourceAsStream("/monsterS/tile037.png"));
			die3=ImageIO.read(getClass().getResourceAsStream("/monsterS/tile038.png"));
			die4=ImageIO.read(getClass().getResourceAsStream("/monsterS/tile039.png"));

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setAction() {
	    int monsterCol = worldX / gp.tileSize;
	    int monsterRow = worldY / gp.tileSize;
	    int playerCol = gp.player.worldX / gp.tileSize;
	    int playerRow = gp.player.worldY / gp.tileSize;

	    int dx = Math.abs(gp.player.worldX - worldX);
	    int dy = Math.abs(gp.player.worldY - worldY);
	    int attackRange = gp.tileSize * 4;

	    if (dx < attackRange && dy < attackRange &&
	        gp.cChecker.hasLineOfSight(monsterCol, monsterRow, playerCol, playerRow)) {
	        // Đổi hướng về phía người chơi
	    	
	    	showLOS = true;
	        losStartX = worldX;
	        losStartY = worldY;
	    	
	        if (Math.abs(dx) > Math.abs(dy)) {
	            direction = gp.player.worldX > worldX ? "right" : "left";
	        } else {
	            direction = gp.player.worldY > worldY ? "down" : "up";
	        }
	        // Có thể thêm logic tấn công ở đây
	    } else {
	        // Hành vi di chuyển ngẫu nhiên như cũ
	        showLOS = false;

	        actionLockCounter++;
	        if(actionLockCounter==70) {
	            Random random = new Random();
	            int i = random.nextInt(100)+1;
	            if(i<=25) direction="up";
	            if(i>25&&i<=50) direction = "down";
	            if(i>50&&i<=75) direction = "left";
	            if(i>75) direction = "right";
	            actionLockCounter=0;
	        }
	    }
	}
}