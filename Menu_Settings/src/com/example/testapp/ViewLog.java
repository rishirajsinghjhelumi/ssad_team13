package com.example.testapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewLog extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logviewer);
		final SQLiteDatabase database = this.openOrCreateDatabase("Logs", MODE_PRIVATE, null);
		int i = 0;
		Cursor cursor = database.rawQuery("SELECT * FROM SPEED_VIOLATIONS", null);
		int num_rows = cursor.getCount();
		String results[] = new String[num_rows];
		if(cursor !=null){
			if(cursor.moveToFirst()){
				do{
					String date = cursor.getString(cursor.getColumnIndex("DATE"));
					String time = cursor.getString(cursor.getColumnIndex("TIME"));
					String violation_type = cursor.getString(cursor.getColumnIndex("VIOLATION_TYPE"));
					String text = date + " , " + time + " , " + violation_type;
					results[i++] = text;
				}while(cursor.moveToNext());
			}
		}
		ListView listView1 = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, results);
		listView1.setAdapter(adapter);
	}
    @Override
    protected void onRestart() {
       // TODO Auto-generated method stub
       super.onRestart();
      if(MainActivity.isQuit)
          finish();
  }
}