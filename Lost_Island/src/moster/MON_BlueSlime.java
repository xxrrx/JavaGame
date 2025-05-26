package moster;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class MON_BlueSlime extends Entity {
	public MON_BlueSlime(GamePanel gp) {
		super(gp);
		this.gp=gp;
		name= "Blue Slime";
		speed=1;
		maxLife =6;
		life = maxLife;
		attack=1;
		
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
			up1= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile008.png"));
			up2= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile009.png"));
			up3= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile010.png"));
			up4= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile012.png"));
			up5= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile014.png"));
			up6= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile015.png"));
			down1= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile008.png"));
			down2= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile009.png"));
			down3= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile010.png"));
			down4= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile012.png"));
			down5= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile014.png"));
			down6= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile015.png"));
			left1= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile008.png"));
			left2= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile009.png"));
			left3= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile010.png"));
			left4= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile012.png"));
			left5= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile014.png"));
			left6= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile015.png"));
			right1= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile008.png"));
			right2= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile009.png"));
			right3= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile010.png"));
			right4= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile012.png"));
			right5= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile014.png"));
			right6= ImageIO.read(getClass().getResourceAsStream("/monsterx/tile015.png"));
			die1=ImageIO.read(getClass().getResourceAsStream("/monsterx/tile016.png"));
			die2=ImageIO.read(getClass().getResourceAsStream("/monsterx/tile021.png"));
			die3=ImageIO.read(getClass().getResourceAsStream("/monsterx/tile022.png"));
			die4=ImageIO.read(getClass().getResourceAsStream("/monsterx/tile023.png"));

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setAction() {
		int monsterCenterX = worldX + solidArea.x + solidArea.width / 2;
		int monsterCenterY = worldY + solidArea.y + solidArea.height / 2;
		int playerCenterX = gp.player.worldX + gp.player.solidArea.x + gp.player.solidArea.width / 2;
		int playerCenterY = gp.player.worldY + gp.player.solidArea.y + gp.player.solidArea.height / 2;

		int monsterCol = monsterCenterX / gp.tileSize;
		int monsterRow = monsterCenterY / gp.tileSize;
		int playerCol = playerCenterX / gp.tileSize;
		int playerRow = playerCenterY / gp.tileSize;

		int dx = Math.abs(playerCenterX - monsterCenterX);
		int dy = Math.abs(playerCenterY - monsterCenterY);
		int attackRange = gp.tileSize * 4;

	    if (dx < attackRange && dy < attackRange &&
	        gp.cChecker.hasLineOfSight(monsterCol, monsterRow, playerCol, playerRow,this)) {
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
