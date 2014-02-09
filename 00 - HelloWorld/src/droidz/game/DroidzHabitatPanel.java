package droidz.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import droidz.game.data.Droidz;
import droidz.helloworld.R;

public class DroidzHabitatPanel extends SurfaceView implements
	SurfaceHolder.Callback{
	
	private static final String TAG = DroidzHabitatPanel.class.getSimpleName();
	
	private MainDroidzThread thread;
	private Droidz droid;
	
	public DroidzHabitatPanel(Context context) {
		super(context);
		//Intercepts Events
		getHolder().addCallback(this);
		
		droid = new Droidz(BitmapFactory.decodeResource(getResources(), R.drawable.droidz),50,50);
		
		thread = new MainDroidzThread(getHolder(), this);
		
		//Set focusable to be able to intercept events
		setFocusable(true);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw called");
		super.onDraw(canvas);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		super.draw(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while(retry) {
			try {
				thread.join();
				retry = false;
			} catch(InterruptedException e) {
				//Try again, shutting down thread
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			droid.handleActionDown((int)event.getX(), (int)event.getY());
			
			if(event.getY() > getHeight() - 50) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG, "Coods: x= " + event.getX() + ", y= " + event.getY());
			}
		}
		
		if(event.getAction() == MotionEvent.ACTION_MOVE) {
			if(droid.isTouched()) {
				droid.setX((int)event.getX());
				droid.setY((int)event.getY());
			}
		}
		if(event.getAction() == MotionEvent.ACTION_UP) {
			if(droid.isTouched()) {
				droid.setTouched(false);
			}
		}
		
		return true;
	}

}
