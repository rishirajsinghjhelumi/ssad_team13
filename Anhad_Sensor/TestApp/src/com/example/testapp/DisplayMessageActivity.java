package com.example.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity implements SensorEventListener{

	private SensorManager sensorManager;
	private Sensor sensor;
	private boolean sensorState = false;
	long currTime = System.currentTimeMillis();
	private float prevx;// = 0; //event.values[0];
	private float prevy;// = 0; //event.values[1];
	private float prevz;// = 0; //event.values[2];
	private boolean initialise = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        TextView textview = (TextView) findViewById(R.id.box1);
        String message = "Welcome to the application!";
        textview.setText(message);
        
        //TextView textview = new TextView(this);
        //textview.setTextSize(40);
        //textview.setText(message);
        //setContentView(textview);
        /* Get Sensors Working */
        Log.i("Creation", "entered created function");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
       
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
    		//Show log here
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
    	//TextView disp = (TextView)findViewById(R.id.box1);
    	//disp.setText("WHAT THE FUCK MAN?!");
    	//Log.w("sensor", "Entered changed sensor loop");
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
    	if(timediff>50)
    	{
    		
	    	
	    	Log.d("previous Values:", Float.toString(prevx)+" "+Float.toString(prevy)+" "+Float.toString(prevz));
	    	
	    	if(sensorState == true)
	    	{
		    	float[] values = event.values;
		    	float x = values[0];
		    	float y = values[1];
		    	float z = values[2];
		    	
		    	TextView disp = (TextView)findViewById(R.id.box1);
		    	
		    	/*String accValues = "x: "+Float.toString(x)+"\n"+
		    			"y: "+Float.toString(y)+"\n"+
		    			"z: "+Float.toString(z)+"\n";
		    	disp.setText(accValues);*/
		    	
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
    	
    }
    
    public void buttonStop(View startview)
    {
    	TextView mainbox = (TextView) findViewById(R.id.box1);
    	mainbox.setText("Stopped !");
    	mainbox.setTextColor(Color.rgb(255, 44, 44));
    	sensorState = false;
    	
    }
    
    @Override
    protected void onResume() {
      super.onResume();
      // register this class as a listener for the orientation and
      // accelerometer sensors
      sensorManager.registerListener(this,
          sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
          SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
      // unregister listener
      super.onPause();
      sensorManager.unregisterListener(this);
    }
    

}
