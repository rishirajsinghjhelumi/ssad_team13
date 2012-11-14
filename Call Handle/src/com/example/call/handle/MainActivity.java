package com.example.call.handle;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    protected void onResume() {
    	CallHandler chandler = new CallHandler();
    	registerReceiver(chandler,new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED));
		super.onResume();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
