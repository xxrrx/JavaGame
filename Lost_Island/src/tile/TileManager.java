package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp=gp;
		
		tile = new Tile[10];
		mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];

		getTileImage();
		loadMap("/maps/finalMap.txt");
	}
	
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col =0;
			int row = 0;

			while(col<gp.maxWorldCol && row<gp.maxWorldRow) {
				String line = br.readLine();
				
				while(col<gp.maxWorldCol) {
					String num [] = line.split(" ");
					
					int number = Integer.parseInt(num[col]);
					
					mapTileNum[col][row]= number;
					col++;
					
				}
				if(col==gp.maxWorldCol) {
						col=0;
						row++;
				}
			}
			br.close();

		}catch(Exception e ){
			e.printStackTrace();
		}
	}
	 
	public void getTileImage() {
		
		try {
			//1 4 5 0 3 2
			tile[1]=new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[4]=new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[4].collision=true;
			
			tile[5]=new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[5].collision=true;
			
			tile[0]=new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[3]=new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[3].collision=true;
			
			tile[2]=new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
				worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
				worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
				worldY < gp.player.worldY + gp.player.screenY + gp.tileSize)  {
				g2.drawImage(tile[mapTileNum[worldCol][worldRow]].image, screenX, screenY, gp.tileSize, gp.tileSize,
						null);

//				g2.setColor(Color.BLUE);
//				g2.drawRect(worldX - gp.player.worldX + gp.player.screenX,
//				            worldY - gp.player.worldY + gp.player.screenY,
//				            gp.tileSize, gp.tileSize);
//				
			}
			worldCol++;
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}
