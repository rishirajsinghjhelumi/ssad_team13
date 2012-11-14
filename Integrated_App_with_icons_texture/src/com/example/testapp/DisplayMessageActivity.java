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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
	private long ptime;
	private Location plocation = new Location("ploc");
	
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
        
        
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LocationListener locationListener = new LocationListener() {
        	public void onStatusChanged(String provider, int status, Bundle extras) {	
			}
        	public void onProviderEnabled(String provider) {
			}
			public void onProviderDisabled(String provider) {
			}
			public void onLocationChanged(Location location) {
				double longitude = location.getLongitude();
		        double latitude = location.getLatitude();
		        long time = System.currentTimeMillis();
		        long timediff = time - ptime;
		        long one_second_gap = 1000;
		        if(timediff > one_second_gap)
		        {
			        double distance = location.distanceTo(plocation);
			        long time_interval = timediff / 1000;
			        double speed = distance/time_interval;
			        String text = "Speed: " + speed;
			        TextView disp = (TextView)findViewById(R.id.box0);
			        disp.setText(text);
			        double allowed_speed_limit = 65.0;
			        if( speed > allowed_speed_limit )
			        {
			        	TextView tv = (TextView)findViewById(R.id.box1);
			    		tv.setText("VIOLATION!!!");
			    		
			    		mplayer = MediaPlayer.create(DisplayMessageActivity.this,R.raw.sound1);
			            mplayer.start();
			            mplayerinit = true;
			            
			            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					    String date = sdf.format(new Date());
					    sdf = new SimpleDateFormat("HH:mm:ss");
					    String timer = sdf.format(new Date());
						database.execSQL("INSERT INTO " +
		                        " SPEED_VIOLATIONS " + " Values(" + "'" + date + "','" + timer + 
		                        "'," +" 'Over Speeding.'" + ");");
			        }
		        }
		        plocation.setLatitude(latitude);
		        plocation.setLongitude(longitude);
		        ptime = time;
			}
		};
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
		
		
       
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
    	case R.id.Exit:
    		MainActivity.isQuit = true;
    		finish();
    		break;
    	case R.id.Settings:
    		Intent settings_intent = new Intent(DisplayMessageActivity.this, Settings.class);
    		startActivity(settings_intent);
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
		    	
		    	float diffX = Math.abs(x - prevx);
		    	float diffY = Math.abs(y - prevy);
		    	float diffZ = Math.abs(z - prevz);
		    	
		    	String strx = Float.toString(diffX).substring(0, 3);
		    	String stry = Float.toString(diffY).substring(0, 3);
		    	String strz = Float.toString(diffZ).substring(0, 3);
		    	
		    	String accValues = 
		    			"X: "+ strx + "\n"+
		    			"Y: "+ stry + "\n"+
		    			"Z: "+ strz + "\n";
		    	disp.setText(accValues);
		    	
		    	if(diffX > 4.0 || diffY > 4.0 || diffZ > 4.0)
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