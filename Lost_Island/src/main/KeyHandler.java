package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp= gp;
	}
	
	public boolean upPress;
	public boolean downPress;
	public boolean leftPress;
	public boolean rightPress;
	public boolean enterPress;
	public boolean shotKeyPress;

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(gp.gameState==gp.titleState) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				gp.playSE(2);
				if(gp.ui.commandNum<0) {
					gp.ui.commandNum=1;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				gp.playSE(2);
				if(gp.ui.commandNum>1) {
					gp.ui.commandNum=0;
				}
			}
			
			if(code == KeyEvent.VK_ENTER) {
				gp.playSE(7);
				if(gp.ui.commandNum==0) {
					gp.stopTitleMusic();
					gp.gameState=gp.playState;
					gp.playMusic(0);
				}
				if(gp.ui.commandNum==1) {
					System.exit(0);
				}
			}
		}
		
		else if (gp.gameState == gp.playState) {
			if (code == KeyEvent.VK_W) {
				upPress = true;
			}
			if (code == KeyEvent.VK_S) {
				downPress = true;
			}
			if (code == KeyEvent.VK_A) {
				leftPress = true;
			}
			if (code == KeyEvent.VK_D) {
				rightPress = true;
			}
			if(code == KeyEvent.VK_ENTER) {
				enterPress = true;
			}
			if (code == KeyEvent.VK_SPACE) {
				shotKeyPress = true;
			}
			if (code == KeyEvent.VK_P) {
				if (gp.gameState == gp.playState) {
					gp.gameState = gp.pauseState;
				} else if (gp.gameState == gp.pauseState) {
					gp.gameState = gp.playState;
				} 
			}
		}
		else if (gp.gameState == gp.pauseState) {
			if (code == KeyEvent.VK_P) {
				gp.gameState = gp.playState;
			}
		}
		
		if(gp.gameState==gp.gameOverState) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				gp.playSE(2);
				if(gp.ui.commandNum<0) {
					gp.ui.commandNum=1;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				gp.playSE(2);
				if(gp.ui.commandNum>1) {
					gp.ui.commandNum=0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				gp.playSE(7);
				if(gp.ui.commandNum==0) {
					gp.gameState=gp.playState;
					gp.retry();
				}
				if(gp.ui.commandNum==1) {
					System.exit(0);
				}

			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPress= false;
		}
		if(code == KeyEvent.VK_S) {
			downPress= false;
		}
		if(code == KeyEvent.VK_A) {
			leftPress= false;
		}
		if(code == KeyEvent.VK_D) {
			rightPress= false;
		}
		if(code==KeyEvent.VK_ENTER) {
			enterPress=false;
		}
		if (code == KeyEvent.VK_SPACE) {
			shotKeyPress = false;
		}
	}

}