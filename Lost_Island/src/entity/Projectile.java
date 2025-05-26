package entity;

import main.GamePanel;

public class Projectile extends Entity{
	GamePanel gp;
	Entity user;
	public Projectile(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
	}
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		this.worldX=worldX;
		this.worldY=worldY;
		this.direction=direction;
		this.alive=alive;
		this.user=user;
		this.life=this.maxLife;
	}
	public void update() {
		if(user==gp.player) {
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			if(monsterIndex!=999) {
				gp.player.damageMonster(monsterIndex, attack);
				alive=false;
			}
		}		
		
		switch(direction) {
		case "up": worldY-= speed; break;
		case "down": worldY+= speed; break;
		case "left": worldX-= speed; break;
		case "right": worldX+= speed; break;
		
		case "up-left": worldX-= speed; break;
		case "up-right": worldX+= speed; break;
		case "down-left": worldX-= speed; break;
		case "down-right": worldX+= speed; break;
		}
		
		life--;
		if(life<=0) {
			alive=false;
		}
		
		spriteCouter++;
		if(spriteCouter > 20) {
			if(spriteNum==1) {
				spriteNum=2;
			}
			else if(spriteNum==2) {
				spriteNum=1;
			}
		}
	}
}