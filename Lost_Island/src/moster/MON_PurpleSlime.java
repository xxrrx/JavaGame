package moster;	

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class MON_PurpleSlime extends Entity {
		
	public MON_PurpleSlime(GamePanel gp) {
		super(gp);
		this.gp=gp;
		name= "Green Slime";
		speed=2;
		maxLife =4;
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
			up1= ImageIO.read(getClass().getResourceAsStream("/monster/tile006.png"));
			up2= ImageIO.read(getClass().getResourceAsStream("/monster/tile007.png"));
			up3= ImageIO.read(getClass().getResourceAsStream("/monster/tile008.png"));
			up4= ImageIO.read(getClass().getResourceAsStream("/monster/tile009.png"));
			up5= ImageIO.read(getClass().getResourceAsStream("/monster/tile010.png"));
			up6= ImageIO.read(getClass().getResourceAsStream("/monster/tile011.png"));
			down1= ImageIO.read(getClass().getResourceAsStream("/monster/tile006.png"));
			down2= ImageIO.read(getClass().getResourceAsStream("/monster/tile007.png"));
			down3= ImageIO.read(getClass().getResourceAsStream("/monster/tile008.png"));
			down4= ImageIO.read(getClass().getResourceAsStream("/monster/tile009.png"));
			down5= ImageIO.read(getClass().getResourceAsStream("/monster/tile010.png"));
			down6= ImageIO.read(getClass().getResourceAsStream("/monster/tile011.png"));
			left1= ImageIO.read(getClass().getResourceAsStream("/monster/tile006.png"));
			left2= ImageIO.read(getClass().getResourceAsStream("/monster/tile007.png"));
			left3= ImageIO.read(getClass().getResourceAsStream("/monster/tile008.png"));
			left4= ImageIO.read(getClass().getResourceAsStream("/monster/tile009.png"));
			left5= ImageIO.read(getClass().getResourceAsStream("/monster/tile010.png"));
			left6= ImageIO.read(getClass().getResourceAsStream("/monster/tile011.png"));
			right1= ImageIO.read(getClass().getResourceAsStream("/monster/tile006.png"));
			right2= ImageIO.read(getClass().getResourceAsStream("/monster/tile007.png"));
			right3= ImageIO.read(getClass().getResourceAsStream("/monster/tile008.png"));
			right4= ImageIO.read(getClass().getResourceAsStream("/monster/tile009.png"));
			right5= ImageIO.read(getClass().getResourceAsStream("/monster/tile010.png"));
			right6= ImageIO.read(getClass().getResourceAsStream("/monster/tile011.png"));
			idle1= ImageIO.read(getClass().getResourceAsStream("/monster/tile000.png"));
			idle2= ImageIO.read(getClass().getResourceAsStream("/monster/tile001.png"));
			idle3= ImageIO.read(getClass().getResourceAsStream("/monster/tile002.png"));
			idle4= ImageIO.read(getClass().getResourceAsStream("/monster/tile003.png"));
			die1=ImageIO.read(getClass().getResourceAsStream("/monster/tile013.png"));
			die2=ImageIO.read(getClass().getResourceAsStream("/monster/tile014.png"));
			die3=ImageIO.read(getClass().getResourceAsStream("/monster/tile015.png"));
			die4=ImageIO.read(getClass().getResourceAsStream("/monster/tile016.png"));

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
	        if(actionLockCounter==120) {
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
