package com.vinil.secondapp;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Speed extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        final TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText("vinil");
        Handler handler = new Handler(); 
        handler.postDelayed(new Runnable() { 
             public void run() {
            	 tv.setText("narang");
                    };
             }, 3000);
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_speed, menu);
        return true;
    }
}
