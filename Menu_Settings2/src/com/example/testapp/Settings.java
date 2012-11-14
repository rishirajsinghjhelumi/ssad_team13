package com.example.testapp;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class Settings extends Activity {
	private Spinner spinner1;
	private SQLiteDatabase database ;
	public int calibrate;
	public int weather;
	public int vehicle;
	public String v0 = new String("BiCycle");
	public String v1 = new String("MotorCycle");
	public String v2 = new String("Scooter");
	public boolean calibrate2;
	public boolean weather2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
		database = this.openOrCreateDatabase("Logs", MODE_PRIVATE, null);
		this.calibrate=0;
		this.weather=0;
		this.vehicle=1;
		database.execSQL("CREATE TABLE IF NOT EXISTS" +
                " SETTINGS " +
                " (ID int AUTO_INCREMENT,VEHICLE_TYPE VARCHAR , CALIBRATE_SCREEN int , WEATHER_FETCH int );");
		Cursor cursor = database.rawQuery("SELECT * FROM SETTINGS;", null);
		cursor.moveToLast();
		int cal = (int)cursor.getInt(cursor.getColumnIndex("CALIBRATE_SCREEN"));
		if (cal==1)calibrate=1;
		int wea = (int)cursor.getInt(cursor.getColumnIndex("WEATHER_FETCH"));
		if (wea==1)weather=1;
		String veh=cursor.getString(cursor.getColumnIndex("VEHICLE_TYPE"));
		if(veh == v0)
			vehicle=0;
		else if(veh == v1)
			vehicle=1;
		else if(veh == v2)
			vehicle=2;
        Button b = (Button) findViewById(R.id.btnSubmit);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setSelection(vehicle);
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);
        if(calibrate==1)
        	calibrate2=true;
        else 
        	calibrate2=false;
        cb.setChecked(calibrate2);
        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				 if (buttonView.isChecked())
			        {
			        	calibrate2=true;
			        	calibrate=1;
			        }
			        else
			        {
			        	calibrate2=false;
			        	calibrate=0;
			        }

			}
		});
        CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2);
        if(weather==1)weather2=true;
        else weather2=false;
        cb2.setChecked(weather2);
        cb2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				 if (buttonView.isChecked())
			        {
			        	weather2=true;
			        	weather=1;
			        }
			        else
			        {
			        	weather2=false;
			        	weather=0;
			        }

			}
		});
    	b.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			String items = " (`" +  "VEHICLE_TYPE" + "`,`" + "CALIBRATE_SCREEN" + "`,`" + "WEATHER_FETCH" + "`)";  
    			database.execSQL("INSERT INTO " +
                        " `SETTINGS` "  + items + "Values(" + "'" + String.valueOf(spinner1.getSelectedItem()) + " ' , ' " + calibrate + " ' , ' "  + weather  + "' );");
    			Intent settings_intent = new Intent(Settings.this , DisplayMessageActivity.class	);
        		startActivity(settings_intent);
    		}
    	});
    }
    @Override
    protected void onRestart() {
       // TODO Auto-generated method stub
       super.onRestart();
      if(MainActivity.isQuit)
          finish();
  }
}
