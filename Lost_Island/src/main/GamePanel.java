package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.player;
import object.OBJ_Heart;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	//	SCREEN SETTING
	final int originalTileSize = 16; // kich thuoc mac dinh cua nhan vat npc va quai vat
	final int scale = 3; // 16x16 pixel tren man hinh be nen se scale len
	
	public final int tileSize = originalTileSize * scale; // nhan vat co kich thuoc 48x48px
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWitdth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow; // kich thuoc man hinh cua tro choi
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	KeyHandler keyH= new KeyHandler(this);
	public player player = new player(this, keyH);
	OBJ_Heart Obj_heart = new OBJ_Heart(this);
	public ArrayList<Entity> projectileList = new ArrayList<Entity>();
	ArrayList<Entity> entityList = new ArrayList<Entity>();
	public Entity monster[] = new Entity[100];
	
	public final int maxWorldCol =100;
	public final int maxWorldRow = 100;

	//Cài đặt vị trí mặc định của người chơi
	int playerSpeed = 4;
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	Sound music = new Sound();
	Sound se = new Sound();
	Sound titleMusic = new Sound();
	Thread gameThread;
	
	public int loadingProgress = 0;
	public int loadingMax = 100;
	
	public int gameState;
	public final int loadingState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int titleState = 3;
	public final int gameOverState = 4;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWitdth,screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true); // giúp cải thiện hiệu suất khi vẽ (rendering)
		this.addKeyListener(keyH);// thêm keyLisenner
		this.setFocusable(true); // panel này sẽ được "focus" để nhận các key input
	}
	
	public void setupGame() {
		gameState = loadingState;
		aSetter.setMonster();
		playTitleMusic(1);
	}
	
	public void startGameThread() {
		//Dòng này tạo một luồng mới (gameThread) và yêu cầu luồng đó sử dụng GamePanel (chính là this) làm công việc để thực thi.
		//Nghĩa là khi gameThread.start() được gọi, Java sẽ tự động tìm đến phương thức run() trong GamePanel để chạy.
		gameThread = new Thread(this);
		gameThread.start();
	}

	//GameLoop sử dụng delta để cộng dồn thời gian
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0; // biến timer để đếm số giây đã trôi qua
		
		while(gameThread!= null) {
			
			currentTime= System.nanoTime();
			delta += (currentTime - lastTime)/drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta>=1) {
				update();
				repaint();
				delta--;
			}
			if(timer>=1000000000) {
				timer=0;
			}
		}
	}
	
	public void update() {
		if(gameState == playState){
			player.update();
			
			for(int i=0;i<monster.length;i++) {
				if(monster[i] != null) {
					if(monster[i].alive == true&&monster[i].dying==false) {
						monster[i].update();
					}
					if(monster[i].alive==false) {
						monster[i]=null;
					}
				}
			}
			for(int i=0;i<projectileList.size();i++) {
				if(projectileList.get(i) != null) {
					if(projectileList.get(i).alive==true) {
						projectileList.get(i).update();
					}
					if(projectileList.get(i).alive==false) {
						projectileList.remove(i);
					}
				}	
			}
		}
		else if(gameState == pauseState) {
		}
	}
	
	public void retry() {
		player.setDefaultPositions();
		aSetter.setMonster();
		playMusic(0);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		if(gameState == titleState) {
			ui.draw(g2);
		}
		else {
			tileM.draw(g2);
			entityList.add(player);
			entityList.add(Obj_heart);
			
			for(int i=0;i<monster.length;i++) {
				if(monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			for(int i=0;i<projectileList.size();i++) {
				if(projectileList.get(i) != null) {
					entityList.add(projectileList.get(i));
				}	
			}
				
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity o1, Entity o2) {
					int result = Integer.compare(o1.worldY, o2.worldY);
					return result;	
				}
				
			});
			
			for(int i=0;i<entityList.size();i++) {
				entityList.get(i).draw(g2);
			}
			entityList.clear();
			
			ui.draw(g2);
		}
		g2.dispose(); // giai phong tai nguyen cua frame
	}
	
	public void playTitleMusic(int i) {
		titleMusic.setFile(i);
		titleMusic.play();
		titleMusic.loop();
	}
	
	public void stopTitleMusic() {
		titleMusic.stop();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}