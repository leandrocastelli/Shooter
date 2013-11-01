package com.lcsmobileapps.shooter;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class StartActivity extends Activity implements SensorEventListener{
	private SensorManager mSensorManager;
    private Sensor gyroscope;
    private  MainGamePanel mgp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // set our MainGamePanel as the View
        mgp = new MainGamePanel(this);
        setContentView(mgp);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        gyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		
	}

public void onAccuracyChanged(Sensor arg0, int arg1) {
		
		
	}

	public void onSensorChanged(SensorEvent arg0) {
		mgp.gyroUpdate(arg0.values);
		
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		mSensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
	}
	@Override
	protected void onPause()
	{
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

}
