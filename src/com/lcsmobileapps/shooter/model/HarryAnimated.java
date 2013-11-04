package com.lcsmobileapps.shooter.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.lcsmobileapps.shooter.ScreenItem;
import com.lcsmobileapps.shooter.model.component.Speed;


public class HarryAnimated extends ScreenItem {

	private static final String TAG = HarryAnimated.class.getSimpleName();

	
	private int frameNr;		// number of frames in animation
	private int currentFrame;	// the current frame
	private long frameTicker;	// the time of the last frame update
	private int framePeriod;	// milliseconds between each frame (1000/fps)

	

	
	private Speed speed;
	
	public HarryAnimated(Bitmap bitmap, int x, int y, int width, int height, int fps, int frameCount)
	{
		this.bitmap = bitmap;
	
		currentFrame = 0;
		frameNr = frameCount;
		spriteWidth = bitmap.getWidth()/frameCount;
		spriteHeight = bitmap.getHeight();
		this.x = spriteWidth;
		
		this.y = y - spriteHeight*2;
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		framePeriod = 1000 / fps;
		frameTicker = 0l;
		touched= false;
		speed = new Speed();
		

	}

	
	public int getFrameNr() {
		return frameNr;
	}
	public void setFrameNr(int frameNr) {
		this.frameNr = frameNr;
	}
	public int getCurrentFrame() {
		return currentFrame;
	}
	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	public int getFramePeriod() {
		return framePeriod;
	}
	public void setFramePeriod(int framePeriod) {
		this.framePeriod = framePeriod;
	}
	
	

	public Speed getSpeed()
	{
		return speed;
	}
	public void update(long gameTime) {
		
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;

			if(speed.getxDirection() == Speed.STANDING)
			{
				currentFrame = 0;
				frameNr = 2;
				
			
			}
			else {
				if (speed.getxDirection() == Speed.DIRECTION_RIGHT) {
					// increment the frame
					frameNr = 6;
					currentFrame++;
					if (currentFrame >= frameNr) {
						currentFrame = 0;

					}
				}
				else {
					frameNr = 6;
					currentFrame--;
					if (currentFrame < 0 ) {
						currentFrame = frameNr -1;

					}
				}
			}
		}
		// define the rectangle to cut out sprite
		spriteWidth = bitmap.getWidth() / frameNr;
		this.sourceRect.left = currentFrame * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
		
		
		
		if (!touched) {
			x += (speed.getXv() * speed.getxDirection()); 
//			y += (speed.getYv() * speed.getyDirection());
		}
	}

	// the draw method which draws the corresponding frame
	public void draw(Canvas canvas) {
		// where to draw the sprite
		Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);
		//canvas.drawBitmap(bitmap, 20, 150, null);
//		Paint paint = new Paint();
//		paint.setARGB(50, 0, 255, 0);
//		canvas.drawRect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight,  paint);
	}

	
}
