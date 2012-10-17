package com.example.testapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity implements SensorEventListener{
	MediaPlayer mplayer;
	private SensorManager sensorManager;
	private Sensor sensor;
	private boolean sensorState = false;
	long currTime = System.currentTimeMillis();
	private float prevx;
	private float prevy;
	private float prevz;
	private boolean initialise = false;
	private boolean mplayerinit = false;
	private SQLiteDatabase database ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        
        TextView textview = (TextView) findViewById(R.id.box1);
        String message = "Welcome to the application!";
        textview.setText(message);
        
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        database = this.openOrCreateDatabase("Logs", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS " +
                " SPEED_VIOLATIONS " +
                " (DATE VARCHAR, TIME VARCHAR," +
                " VIOLATION_TYPE VARCHAR );");
       
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.activity_display_message, menu);
    	    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId()){
    	case R.id.ViolationLog:
    		Intent vlogintent = new Intent(DisplayMessageActivity.this, ViewLog.class);
    		startActivity(vlogintent);
    		break;
    		
    	}
    	return true;
    }
    
    private void init(SensorEvent event)
    {
    	long currTime = System.currentTimeMillis();
    	prevx = event.values[0];
    	prevy = event.values[1];
    	prevz = event.values[2];
    }
    public void onSensorChanged(SensorEvent event)
    {
    	long eventTime = System.currentTimeMillis();
    	checkSverve(event, eventTime);
    	if(!initialise)
    	{
    		init(event);
    		initialise=true;
    	}
    }
    public void checkSverve(SensorEvent event,long eventTime)
    {
    	
    	long timediff = eventTime-currTime;
    	if(timediff>100)
    	{
	    	if(sensorState == true)
	    	{
		    	float[] values = event.values;
		    	float x = values[0];
		    	float y = values[1];
		    	float z = values[2];
		    	
		    	TextView disp = (TextView)findViewById(R.id.box1);
		    	
		    	float diffX = x - prevx;
		    	float diffY = y - prevy;
		    	float diffZ = z - prevz;
		    	
		    	String strx = (Float.toString(diffX)).substring(0, 3);
		    	String stry = Float.toString(diffY).substring(0, 3);
		    	String strz = Float.toString(diffZ).substring(0, 3);
		    	
		    	String accValues = "x: "+strx+"\n"+
		    			"y: "+stry+"\n"+
		    			"z: "+strz+"\n";
		    	disp.setText(accValues);
		    	
		    	if(Math.abs(diffX) > 4.0 || Math.abs(diffY) > 4.0 || Math.abs(diffZ) > 4.0)
		    	{
		    		disp.setText("VIOLATION!!!");
		    		disp.setTextColor(Color.rgb(255, 255, 255));
		    		sensorState = false;
		    		
		    		mplayer = MediaPlayer.create(this,R.raw.sound1);
		            mplayer.start();
		            mplayerinit = true;
		            
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				    String date = sdf.format(new Date());
				    sdf = new SimpleDateFormat("HH:mm:ss");
				    String time = sdf.format(new Date());
					database.execSQL("INSERT INTO " +
	                        " SPEED_VIOLATIONS " + " Values(" + "'" + date + "','" + time + "'," +" 'Rash Driving.'" + ");");
		   
		    	}
		    	prevx = x;
		    	prevy = y;
		    	prevz = z;
		    	
		    }
    	currTime=System.currentTimeMillis();
    	return;
    	}
    }

    public void onAccuracyChanged(Sensor sensor, int i)
    {
    	
    }
    public void buttonStart(View startview)
    {
    	TextView mainbox = (TextView) findViewById(R.id.box1);
    	mainbox.setText("Started !");
    	mainbox.setTextColor(Color.rgb(99, 132, 00));
    	sensorState = true;
    	
    	if(mplayerinit==true)
    		mplayer.stop();
    	
    }
    
    public void buttonStop(View startview)
    {
    	TextView mainbox = (TextView) findViewById(R.id.box1);
    	mainbox.setText("Stopped !");
    	
    	if(mplayerinit==true)
    		mplayer.stop();
    	
    	mainbox.setTextColor(Color.rgb(255, 44, 44));
    	sensorState = false;
    	
    }
    
    @Override
    protected void onResume() {
      super.onResume();
      sensorManager.registerListener(this,
          sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
          SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
      super.onPause();
      sensorManager.unregisterListener(this);
    }
    
}