package com.vinil.secondapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Userinterface extends Activity {

	@Override
	public void onResume()
    { 
		// After a pause OR at startup
		super.onResume();
		//Refresh your stuff here
		LocationManager locman=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		TextView tv3 = (TextView) findViewById(R.id.textView3);
		boolean isGPSEnabled = locman.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if(isGPSEnabled)
		{
			tv3.setText("GPS : ON");
			Button b = (Button) findViewById(R.id.button1);
			b.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
				}});
		}
		else
		{
			tv3.setText("GPS : OFF");
			Button b = (Button) findViewById(R.id.button1);
			b.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					/* TODO Auto-generated method stub
					final ComponentName toLaunch = new ComponentName("com.android.settings","com.android.settings.SecuritySettings");
                    final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setComponent(toLaunch);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, 1);*/
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.setClassName("com.android.settings","com.android.settings.SecuritySettings");
					startActivity(intent);
				}
			});
		}
		TextView tv4 = (TextView) findViewById(R.id.textView4);
		ConnectivityManager conman = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conman.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected())
		{
			tv4.setText("Network : ON");
			Button b2 = (Button) findViewById(R.id.button2);
			b2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
				}});
			
		}
		else
		{
			tv4.setText("Network : OFF");
			Button b2 = (Button) findViewById(R.id.button2);
			b2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent2 = new Intent(Intent.ACTION_MAIN);
					intent2.setClassName("com.android.phone", "com.android.phone.Settings");
					startActivity(intent2);

				}
			});
		}
     }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinterface);
		TextView tv2 = (TextView) findViewById(R.id.textView1);
		tv2.setText("HEY");
		TextView tv = (TextView) findViewById(R.id.textView2);
		tv.setText(getIntent().getExtras().getString("text"));
		LocationManager locman=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		TextView tv3 = (TextView) findViewById(R.id.textView3);
		boolean isGPSEnabled = locman.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if(isGPSEnabled)
		{
			tv3.setText("GPS : ON");
			Button b = (Button) findViewById(R.id.button1);
			b.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
				}});
		}
		else
		{
			tv3.setText("GPS : OFF");
			Button b = (Button) findViewById(R.id.button1);
			b.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					/* TODO Auto-generated method stub
					final ComponentName toLaunch = new ComponentName("com.android.settings","com.android.settings.SecuritySettings");
                    final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setComponent(toLaunch);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, 1);*/
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.setClassName("com.android.settings","com.android.settings.SecuritySettings");
					startActivity(intent);
				}
			});
		}
		TextView tv4 = (TextView) findViewById(R.id.textView4);
		ConnectivityManager conman = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conman.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected())
		{
			tv4.setText("Network : ON");
			Button b2 = (Button) findViewById(R.id.button2);
			b2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
				}});
		}
		else
		{
			tv4.setText("Network : OFF");
			Button b2 = (Button) findViewById(R.id.button2);
			b2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent2 = new Intent(Intent.ACTION_MAIN);
					intent2.setClassName("com.android.phone", "com.android.phone.Settings");
					startActivity(intent2);

				}
			});
		}
		
		}
	
	}
