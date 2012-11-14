package com.example.testapp;

import android.R.integer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
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
	public int call;
	public String v0 = "BiCycle";
	public String v1 = "MotorCycle";
	public String v2 = "Scooter";
	public String veh = new String("");
	public boolean calibrate2;
	public boolean weather2;
	public boolean call2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
		database = this.openOrCreateDatabase("Logs", MODE_PRIVATE, null);
		this.calibrate=0;
		this.weather=0;
		this.call=0;
		this.vehicle=1;
		//database.execSQL("DROP TABLE IF EXISTS SETTINGS;");
		database.execSQL("CREATE TABLE IF NOT EXISTS" +
                " SETTINGS " +
                " (ID int AUTO_INCREMENT,VEHICLE_TYPE int , CALIBRATE_SCREEN int , WEATHER_FETCH int , CALL_HANDLE int);");
		Cursor cursor = database.rawQuery("SELECT * FROM SETTINGS;", null);
		int num_rows=cursor.getCount();
		if(num_rows>0)
		{
		cursor.moveToLast();
		int ca = (int)cursor.getInt(cursor.getColumnIndex("CALL_HANDLE"));
		if(ca==1)call=1;
		int cal = (int)cursor.getInt(cursor.getColumnIndex("CALIBRATE_SCREEN"));
		if (cal==1)calibrate=1;
		int wea = (int)cursor.getInt(cursor.getColumnIndex("WEATHER_FETCH"));
		if (wea==1)weather=1;
		vehicle = (int)cursor.getInt(cursor.getColumnIndex("VEHICLE_TYPE"));
		//TextView tv = (TextView) findViewById(R.id.textView1);
		//tv.setText(Integer.toString(vehicle));
		/*
		veh = (String)cursor.getString(cursor.getColumnIndex("VEHICLE_TYPE")).toString();
		TextView tv = (TextView) findViewById(R.id.textView1);
		if(veh == v0)
		{
				this.vehicle=0;
		}
		if(veh == v1)this.vehicle=1;
		if(veh == v2)this.vehicle=2;
		tv.setText(Integer.toString(vehicle));
		*/
		}
        Button b = (Button) findViewById(R.id.btnSubmit);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setSelection(this.vehicle);
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);
        if(calibrate==1)calibrate2=true;
        else calibrate2=false;
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
        CheckBox cb3 = (CheckBox) findViewById(R.id.checkBox3);
        if(call==1)call2=true;
        else call2=false;
        cb3.setChecked(call2);
        cb3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				 if (buttonView.isChecked())
			        {
			        	call2=true;
			        	call=1;
			        }
			        else
			        {
			        	call2=false;
			        	call=0;
			        }

			}
		});
    	b.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			String items = " (`" +  "VEHICLE_TYPE" + "`,`" + "CALIBRATE_SCREEN" + "`,`" + "WEATHER_FETCH" + "`,`" + "CALL_HANDLE" + "`)";  
    			database.execSQL("INSERT INTO " +
                        " `SETTINGS` "  + items + "Values(" + "'" + spinner1.getSelectedItemPosition() + " ' , ' " + calibrate + " ' , ' "  + weather  + " ' , ' " + call + "' );");
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
