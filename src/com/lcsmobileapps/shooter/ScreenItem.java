package com.lcsmobileapps.shooter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class ScreenItem {
	protected Bitmap bitmap;
	protected Rect sourceRect;
	protected int x;				// the X coordinate of the object (top left of the image)
	protected int y;				// the Y coordinate of the object (top left of the image)
	protected boolean touched = false;
	protected int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
	protected int spriteHeight;	// the height of the sprite
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public Rect getSourceRect() {
		return sourceRect;
	}
	public void setSourceRect(Rect sourceRect) {
		this.sourceRect = sourceRect;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
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
	
	public int getSpriteWidth() {
		return spriteWidth;
	}
	public void setSpriteWidth(int spriteWidth) {
		this.spriteWidth = spriteWidth;
	}
	public int getSpriteHeight() {
		return spriteHeight;
	}
	public void setSpriteHeight(int spriteHeight) {
		this.spriteHeight = spriteHeight;
	}
	
	public void draw(Canvas canvas) {
		Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
		canvas.drawBitmap(bitmap, sourceRect, destRect, new Paint());
	}
	public void handleActionDown(int eventX, int eventY) {

		int currentX = sourceRect.width();
		int currentY = sourceRect.height();
		
		if (eventX >= (x) && (eventX <= (x + currentX))) {

			if (eventY >= (y) && (eventY <= (y + currentY))) {

				setTouched(true);

			} else {

				setTouched(false);

			}

		} else {

			setTouched(false);

		}
	}
	
}
