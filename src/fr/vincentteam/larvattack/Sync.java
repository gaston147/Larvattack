package fr.vincentteam.larvattack;

public class Sync {
	private long lastTime;
	
	public Sync() {
		lastTime = -1;
	}
	
	public void sync(long fps) {
		try {
			Thread.sleep(Math.max(0, 1000 / fps - (lastTime != -1 ? System.currentTimeMillis() - lastTime : 0)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lastTime = System.currentTimeMillis();
	}
}
