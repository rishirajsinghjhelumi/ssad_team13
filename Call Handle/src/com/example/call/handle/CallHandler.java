package com.example.call.handle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallHandler extends BroadcastReceiver {
	
	
	private SQLiteDatabase database;
	static int prevPhoneState = -1;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		AudioManager audiomanage = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		final int defaultPhoneState = 2;
		
		// TODO Auto-generated method stub
		/*
		 database = context.openOrCreateDatabase("Logs", 0, null);
		
	     database.execSQL("CREATE TABLE IF NOT EXISTS " +
	                " SPEED_VIOLATIONS " +
	                " (DATE VARCHAR, TIME VARCHAR," +
	                " VIOLATION_TYPE VARCHAR );");

		 
		 Cursor cursor = database.rawQuery("SELECT * FROM SETTINGS;", null);
		 cursor.moveToLast();
		 int call = (int)cursor.getInt(cursor.getColumnIndex("CALL_HANDLE"));
		 if(call==1)
		 {
		
			 
			 
			 *
			 */
		/*
		 * Broken on Android 2.2+
		//Log.v(TAG, "Receving....");
		  TelephonyManager telephony = (TelephonyManager) 
		  context.getSystemService(Context.TELEPHONY_SERVICE);  
		  try {
		   Class c = Class.forName(telephony.getClass().getName());
		   Method m = c.getDeclaredMethod("getITelephony");
		   m.setAccessible(true);
		   telephonyService = (ITelephony) m.invoke(telephony);
		   //telephonyService.silenceRinger();
		   telephonyService.endCall();
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  */
		
		Bundle extras = intent.getExtras();
		if (extras!=null)
		{
			String state = extras.getString(TelephonyManager.EXTRA_STATE);
			Log.w("DEBUG", state);
			if(state.equals(TelephonyManager.EXTRA_STATE_RINGING))
			{
				String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
				Log.w("DEBUG",phoneNumber);
				prevPhoneState = audiomanage.getRingerMode();
				audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				SmsManager smsManager = SmsManager.getDefault();
				String smsString = "The person you are calling is currently driving," +
						" and will call you later." +
						" To ensure his Safety please call later.";
				smsManager.sendTextMessage(phoneNumber, null, smsString, null, null);
				
			}
			/*
			if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
			{
				//This is a violation
				//Log the violation
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                 String date = sdf.format(new Date());
                 sdf = new SimpleDateFormat("HH:mm:ss");
                 String timer = sdf.format(new Date());

				database.execSQL("INSERT INTO " +
                        " SPEED_VIOLATIONS " + " Values(" + "'" + date + "','" + timer +
                        "'," +" 'Negligent Driving'" + ");");
				
			}*/
			if(state.equals(TelephonyManager.EXTRA_STATE_IDLE))
			{
				if(prevPhoneState==-1)
				{
					audiomanage.setRingerMode(defaultPhoneState);
				}
				else
				{
					audiomanage.setRingerMode(prevPhoneState);
				}
			}
		}
	}
	//}
}
