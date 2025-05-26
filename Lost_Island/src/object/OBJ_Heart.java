package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		try {
			image =ImageIO.read(getClass().getResourceAsStream("/object/heart_blank.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/object/heart_full.png"));
			image3 =  ImageIO.read(getClass().getResourceAsStream("/object/heart_half.png"));

		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
