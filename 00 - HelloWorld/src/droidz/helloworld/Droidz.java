package droidz.helloworld;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Droidz {
	private Bitmap bitmap;
	private float x;
	private float y;
	private boolean touched;
	private int speedX = 10;
	
	public Droidz(Bitmap bitmap, int x, int y) {
		this.x = x;
		this.y = y;
		this.bitmap = bitmap;
	}
	
	/**
	 * Draw the Droidz on the canvas
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x- (bitmap.getWidth()/2), y - (bitmap.getHeight()/2), null);
	}
	
	/**
	 * Handle user interaction with Droidz
	 * @param eventX
	 * @param eventY
	 */
	public void handleActionDown(int eventX, int eventY) {
		if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
			if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
				setTouched(true);
			}else {
				setTouched(false);
			}
		} else { setTouched(false);}
	}
	
	public void update(int delta) {
		float increment = speedX * (float)(delta * 0.001f);
		Log.i("Droidz increment", "+" + increment + " Speed added.");
		x += increment;
	}

	/* Getters and Setters */
	
	
	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}
	
	
}
