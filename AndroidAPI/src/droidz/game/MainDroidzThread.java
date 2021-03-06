package droidz.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;


public class MainDroidzThread extends Thread {
	
	private static final String TAG = MainDroidzThread.class.getSimpleName();
	private boolean running;
	private SurfaceHolder surfaceHolder;
	private DroidzHabitatPanel gamePanel;
	
	private long startTime;
	
	public MainDroidzThread(SurfaceHolder surfaceHolder, DroidzHabitatPanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Start GameLoop");

		startTime = System.nanoTime();
		while(running) {			
			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
			startTime = System.nanoTime();
			//Update 
			this.gamePanel.update(deltaTime);
			canvas = null;
			//Try lock canvas
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					//Update state
					this.gamePanel.draw(canvas);
				}
			} finally {
				if(canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
		Log.d(TAG, "GameLoop ended");
	}	

}
