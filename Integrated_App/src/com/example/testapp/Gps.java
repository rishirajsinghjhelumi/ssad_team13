package com.example.testapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Gps extends Activity {
	
	private LocationManager locman;
	private boolean isGPSEnabled;
	private NetworkInfo networkInfo;
	private ConnectivityManager conman;
	final Context context = this;
	@Override
	public void onResume()
    { 
		super.onResume();
		final TextView tv3 = (TextView) findViewById(R.id.textView3);
		locman=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		isGPSEnabled = locman.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if(!isGPSEnabled)
		{	
			tv3.setText("GPS: ");
			Button b = (Button) findViewById(R.id.button1);
			b.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.setClassName("com.android.settings","com.android.settings.SecuritySettings");
					startActivity(intent);
				}
			});
		}
		else
		{
			final Button b = (Button) findViewById(R.id.button1);
			tv3.setText("GPS: ");
			b.setText("Enabled");
			b.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
					alertdialog.setTitle("GPS..");
					alertdialog.setMessage("GPS Already Enabled!!!")
								.setCancelable(false)
								.setNegativeButton("OK", new DialogInterface.OnClickListener() 
								{
									public void onClick(DialogInterface dialog, int which) 
									{
										dialog.cancel();
									}
								}
						);
					AlertDialog alertDialog2 = alertdialog.create();
					alertDialog2.show();
				}
			});
		}
		TextView tv4 = (TextView) findViewById(R.id.textView4);
		ConnectivityManager conman = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfo = conman.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected())
		{
			final Button b2 = (Button) findViewById(R.id.button2);
			tv4.setText("Network: ");
			b2.setText("Enabled");
			b2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
					alertdialog.setTitle("Network..");
					alertdialog.setMessage("Network Already Enabled!!!")
								.setCancelable(false)
								.setNegativeButton("OK", new DialogInterface.OnClickListener() 
								{
									public void onClick(DialogInterface dialog, int which) 
									{
										dialog.cancel();
									}
								}
						);
					AlertDialog alertDialog2 = alertdialog.create();
					alertDialog2.show();
				}
			});
		}
		else
		{
			tv4.setText("Network: ");
			Button b2 = (Button) findViewById(R.id.button2);
			b2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent2 = new Intent(Intent.ACTION_MAIN);
					intent2.setClassName("com.android.phone", "com.android.phone.Settings");
					startActivity(intent2);
				}
			});
		}
		if(isGPSEnabled && networkInfo!=null && networkInfo.isConnected() )
		{
			Intent intentnext = new Intent(Gps.this,AskScreen.class);
			startActivity(intentnext);
		}
     }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		final TextView tv3 = (TextView) findViewById(R.id.textView3);	
		locman=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		isGPSEnabled = locman.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if(!isGPSEnabled)
		{
			tv3.setText("GPS: ");
			Button b = (Button) findViewById(R.id.button1);
			b.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.setClassName("com.android.settings","com.android.settings.SecuritySettings");
					startActivity(intent);
				}
			});
		}
		else
		{
			final Button b = (Button) findViewById(R.id.button1);
			tv3.setText("GPS: ");
			b.setText("Enabled");
			b.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
					alertdialog.setTitle("GPS..");
					alertdialog.setMessage("GPS Already Enabled!!!")
								.setCancelable(false)
								.setNegativeButton("OK", new DialogInterface.OnClickListener() 
								{
									public void onClick(DialogInterface dialog, int which) 
									{
										dialog.cancel();
									}
								}
						);
					AlertDialog alertDialog2 = alertdialog.create();
					alertDialog2.show();
				}
			});
		}
		TextView tv4 = (TextView) findViewById(R.id.textView4);
		ConnectivityManager conman = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfo = conman.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected())
		{
			final Button b2 = (Button) findViewById(R.id.button2);
			tv4.setText("Network: ");
			b2.setText("Enabled");
			b2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
					alertdialog.setTitle("Network..");
					alertdialog.setMessage("Network Already Enabled!!!")
								.setCancelable(false)
								.setNegativeButton("OK", new DialogInterface.OnClickListener() 
								{
									public void onClick(DialogInterface dialog, int which) 
									{
										dialog.cancel();
									}
								}
						);
					AlertDialog alertDialog2 = alertdialog.create();
					alertDialog2.show();
				}
			});
		}
		else
		{
			tv4.setText("Network: ");
			Button b2 = (Button) findViewById(R.id.button2);
			b2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent2 = new Intent(Intent.ACTION_MAIN);
					intent2.setClassName("com.android.phone", "com.android.phone.Settings");
					startActivity(intent2);
				}
			});
		}
		if(isGPSEnabled && networkInfo!=null && networkInfo.isConnected())
		{
			Intent intentnext = new Intent(Gps.this,AskScreen.class);
			startActivity(intentnext);
		}
	}
}