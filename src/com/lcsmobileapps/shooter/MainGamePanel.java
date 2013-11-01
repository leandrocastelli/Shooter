/**
 * 
 */
package com.lcsmobileapps.shooter;





import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lcsmobileapps.shooter.model.HarryAnimated;
import com.lcsmobileapps.shooter.model.component.Speed;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	private HarryAnimated harry;
	private MainThread thread;
	
	// the fps to be displayed
	private String avgFps;
	public void setAvgFps(String avgFps) {
		this.avgFps = avgFps;
	}

	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create Elaine and load bitmap
		
		harry = new HarryAnimated(
				BitmapFactory.decodeResource(getResources(), R.drawable.runnig_right2) 
				, 100, 100	// initial position
				, 60, 90	// width and height of sprite
				, 3, 6);	// FPS and number of frames in the animation 
		
		thread = new MainThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	private HarryAnimated getHarry()
	{
		return harry;
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}

	
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

            // delegating event handling to the droid

            harry.handleActionDown((int)event.getX(), (int)event.getY());

             

            // check if in the lower part of the screen we exit

            if (event.getY() > getHeight() - 50) {

                thread.setRunning(false);

                ((Activity)getContext()).finish();

            } else {

                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());

            }

        } if (event.getAction() == MotionEvent.ACTION_MOVE) {

            // the gestures

            if (harry.isTouched()) {

                // the droid was picked up and is being dragged

            	harry.setX((int)event.getX());

            	harry.setY((int)event.getY());

            }

        } if (event.getAction() == MotionEvent.ACTION_UP) {

            // touch was released

            if (harry.isTouched()) {

            	harry.setTouched(false);

            }

        }

        return true;
	}

	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		//elaine.draw(canvas);
		harry.draw(canvas);
		// display fps
		displayFps(canvas, avgFps);
	}

	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */
	public void update() {
		//elaine.update(System.currentTimeMillis());
		// check collision with right wall if heading right
		if (harry.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
				&& harry.getX() + harry.getBitmap().getWidth() / 2 >= getWidth()) {
			harry.getSpeed().toggleXDirection();
		}
		// check collision with left wall if heading left
		if (harry.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
				&& harry.getX() - harry.getBitmap().getWidth() / 2 <= 0) {
			harry.getSpeed().toggleXDirection();
		}
		// check collision with bottom wall if heading down
		if (harry.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
				&& harry.getY() + harry.getBitmap().getHeight() / 2 >= getHeight()) {
			harry.getSpeed().toggleYDirection();
		//	harry.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.crono_running_up) );
			harry.setCurrentFrame(0);
		}
		// check collision with top wall if heading up
		if (harry.getSpeed().getyDirection() == Speed.DIRECTION_UP
				&& harry.getY() - harry.getBitmap().getHeight() / 2 <= 0) {
			harry.getSpeed().toggleYDirection();
			//harry.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.crono_running_down) );
			harry.setCurrentFrame(0);
		}
		harry.update(System.currentTimeMillis());
	}

	public void gyroUpdate(float[] args)
	{
		float x = args[0];
		float y = args[1];
		if (x> 1)
			harry.getSpeed().setxDirection(Speed.DIRECTION_UP);
		else 
			if (x < -1)
				harry.getSpeed().setxDirection(Speed.DIRECTION_DOWN);
		
		if (y> 1)
			harry.getSpeed().setyDirection(Speed.DIRECTION_RIGHT);
		else 
			if (y < -1)
				harry.getSpeed().setyDirection(Speed.DIRECTION_LEFT);
			
	}
	private void displayFps(Canvas canvas, String fps) {
		if (canvas != null && fps != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			canvas.drawText(fps, this.getWidth() - 50, 20, paint);
		}
	}
	
    


}
