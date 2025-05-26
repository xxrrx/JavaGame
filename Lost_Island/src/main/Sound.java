package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[10];
	
	public Sound(){
		soundURL[0] = getClass().getResource("/sound/background.wav");
		soundURL[1] = getClass().getResource("/sound/titlescreenMusic.wav");
		soundURL[2] = getClass().getResource("/sound/crusor.wav");
		soundURL[3] = getClass().getResource("/sound/getHit.wav");
		soundURL[4] = getClass().getResource("/sound/gameover.wav");
		soundURL[5] = getClass().getResource("/sound/hitmonster.wav");
		soundURL[6] = getClass().getResource("/sound/swingWeapons.wav");
		soundURL[7] = getClass().getResource("/sound/buttonselected.wav");
		soundURL[8] = getClass().getResource("/sound/shooting.wav");
	}
	
	public void setFile(int i) {
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}
