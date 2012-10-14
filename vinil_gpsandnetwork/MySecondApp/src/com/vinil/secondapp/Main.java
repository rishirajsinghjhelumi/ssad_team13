package com.vinil.secondapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText et = (EditText) findViewById(R.id.editText1); 
        Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
				Intent intent = new Intent(Main.this, Userinterface.class);
				intent.putExtra("text", et.getText().toString());
				startActivity(intent);	
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
