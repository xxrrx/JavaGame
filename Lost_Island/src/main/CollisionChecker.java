package main;

import entity.Entity;

public class CollisionChecker {
	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entityLeftWorldX + entity.solidArea.width;
		int entityUpWorldY = entity.worldY + entity.solidArea.y;
		int entityDownWorldY = entityUpWorldY + entity.solidArea.height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityUpRow = entityUpWorldY / gp.tileSize;
		int entityDownRow = entityDownWorldY / gp.tileSize;

		int tileNum1, tileNum2;

		switch (entity.direction) {
		case "up":
			entityUpRow = (entityUpWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityUpRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityUpRow];

			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
			break;

		case "down":
			entityDownRow = (entityDownWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityDownRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityDownRow];

			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
			break;

		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityUpRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityDownRow];

			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
			break;

		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityUpRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityDownRow];

			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
			break;

		case "up-left":
			// Kiểm tra cả hướng "up" và "left"
			entityUpRow = (entityUpWorldY - entity.speed) / gp.tileSize;
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;

			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityUpRow];

			if (gp.tileM.tile[tileNum1].collision) {
				entity.collisionOn = true;
			}
			break;

		case "up-right":
			// Kiểm tra cả hướng "up" và "right"
			entityUpRow = (entityUpWorldY - entity.speed) / gp.tileSize;
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;

			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityUpRow];

			if (gp.tileM.tile[tileNum1].collision ) {
				entity.collisionOn = true;
			}
			break;

		case "down-left":
			// Kiểm tra cả hướng "down" và "left"
			entityDownRow = (entityDownWorldY + entity.speed) / gp.tileSize;
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;

			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityDownRow];

			if (gp.tileM.tile[tileNum1].collision ) {
				entity.collisionOn = true;
			}
			break;

		case "down-right":
			// Kiểm tra cả hướng "down" và "right"
			entityDownRow = (entityDownWorldY + entity.speed) / gp.tileSize;
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;

			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityDownRow];

			if (gp.tileM.tile[tileNum1].collision ) {
				entity.collisionOn = true;
			}
			break;
		}
	}
	
	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		entity.solidArea.x= entity.worldX+entity.solidArea.x;
		entity.solidArea.y= entity.worldY+entity.solidArea.y;
		
		gp.player.solidArea.x = gp.player.worldX+gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY+gp.player.solidArea.y;
		
		switch(entity.direction) {
		case"up": entity.solidArea.y -= entity.speed; break;
		case"down": entity.solidArea.y += entity.speed; break;
		case"left": entity.solidArea.x -= entity.speed; break;
		case"right": entity.solidArea.x += entity.speed; break;
		}
		if(entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
			contactPlayer = true;
		}
		
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x= gp.player.solidAreaDefaultX;
		gp.player.solidArea.y= gp.player.solidAreaDefaultY;
		
		return contactPlayer;
	}
	
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		
		for(int i=0;i<target.length;i++) {
			
			if(target[i] != null) {
				entity.solidArea.x=entity.worldX+entity.solidArea.x;
				entity.solidArea.y=entity.worldY+entity.solidArea.y;

				target[i].solidArea.x=target[i].worldX+target[i].solidArea.x;
				target[i].solidArea.y=target[i].worldY+target[i].solidArea.y;
				
				switch(entity.direction) {
				case"up": entity.solidArea.y-=entity.speed; break;
				case"down": entity.solidArea.y+=entity.speed; break;
				case"left": entity.solidArea.x-=entity.speed; break;
				case"right": entity.solidArea.x+=entity.speed; break;
				}
				if(entity.solidArea.intersects(target[i].solidArea)) {
					if(target[i] != entity) {
						entity.collisionOn=true;
						index=i;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x=target[i].solidAreaDefaultX;
				target[i].solidArea.y=target[i].solidAreaDefaultY;
			}
		}
		return index;
	}
	
	public boolean hasLineOfSight(int startCol, int startRow, int endCol, int endRow, Entity entity) {
	    entity.losTiles.clear();
	    int x0 = startCol;
	    int y0 = startRow;
	    int x1 = endCol;
	    int y1 = endRow;

	    int dx = x1 - x0;
	    int dy = y1 - y0;

	    // Avoid division by zero
	    if (dx == 0 && dy == 0) {
	        entity.losTiles.add(new int[]{x0, y0});
	        return true;
	    }

	    double k = (dx == 0) ? Double.POSITIVE_INFINITY : (double) dy / dx;

	    // k > 1 or k < -1: y-major
	    if (k > 1 || k < -1) {
	        int sx = dx > 0 ? 1 : -1;
	        int sy = dy > 0 ? 1 : -1;
	        int absDx = Math.abs(dx);
	        int absDy = Math.abs(dy);
	        int err = absDy / 2;
	        int x = x0;
	        for (int y = y0; sy > 0 ? y <= y1 : y >= y1; y += sy) {
	            if (x < 0 || y < 0 || x >= gp.maxWorldCol || y >= gp.maxWorldRow) return false;
	            entity.losTiles.add(new int[]{x, y});
	            int tileNum = gp.tileM.mapTileNum[x][y];
	            if (gp.tileM.tile[tileNum].collision) return false;
	            err -= absDx;
	            if (err < 0) {
	                x += sx;
	                err += absDy;
	            }
	        }
	    }
	    // -1 < k < 1: x-major
	    else {
	        int sx = dx > 0 ? 1 : -1;
	        int sy = dy > 0 ? 1 : -1;
	        int absDx = Math.abs(dx);
	        int absDy = Math.abs(dy);
	        int err = absDx / 2;
	        int y = y0;
	        for (int x = x0; sx > 0 ? x <= x1 : x >= x1; x += sx) {
	            if (x < 0 || y < 0 || x >= gp.maxWorldCol || y >= gp.maxWorldRow) return false;
	            entity.losTiles.add(new int[]{x, y});
	            int tileNum = gp.tileM.mapTileNum[x][y];
	            if (gp.tileM.tile[tileNum].collision) return false;
	            err -= absDy;
	            if (err < 0) {
	                y += sy;
	                err += absDx;
	            }
	        }
	    }
	    return true;
	}


}
