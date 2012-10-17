package com.example.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {
	private boolean isGPSEnabled;
	private NetworkInfo networkInfo;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        LocationManager locman=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locman.isProviderEnabled(LocationManager.GPS_PROVIDER);
        
        ConnectivityManager conman = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = conman.getActiveNetworkInfo();
        
        final SQLiteDatabase database = this.openOrCreateDatabase("Logs", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS " +
                " SPEED_VIOLATIONS " +
                " (DATE VARCHAR, TIME VARCHAR," +
                " VIOLATION_TYPE VARCHAR );");         
    }
    public void sendMessage(View view)
    {   	       
        if(isGPSEnabled && networkInfo!=null && networkInfo.isConnected())
        {
        	Intent intent = new Intent (MainActivity.this, AskScreen.class);
        	startActivity(intent);
        }
        else
        {
        	//Intent altintent = new Intent(MainActivity.this,Gps.class);
        	Intent altintent = new Intent(MainActivity.this,AskScreen.class);
        	startActivity(altintent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}