package com.example.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final SQLiteDatabase database = this.openOrCreateDatabase("Logs", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS " +
                " SPEED_VIOLATIONS " +
                " (DATE VARCHAR, TIME VARCHAR," +
                " SPEED DOUBLE, MAX_SPEED DOUBLE);");
     /*   Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			    String date = sdf.format(new Date());
			    sdf = new SimpleDateFormat("HH:mm:ss");
			    String time = sdf.format(new Date());
			    Random rv = new Random();
			    double speed = rv.nextDouble();
			    double max_speed = rv.nextDouble();
				database.execSQL("INSERT INTO " +
                        " SPEED_VIOLATIONS " + " Values(" + "'" + date + "','" + time +"'," + speed 
                        +","+ max_speed +");");
			}
		});*/
        int i = 0;
        Cursor cursor = database.rawQuery("SELECT * FROM SPEED_VIOLATIONS", null);
        int num_rows = cursor.getCount();
        String results[] = new String[num_rows];
        if(cursor !=null){
    	   if(cursor.moveToFirst()){
    		   do{
    		   String date = cursor.getString(cursor.getColumnIndex("DATE"));
    		   String time = cursor.getString(cursor.getColumnIndex("TIME"));
    		   int speed = (int) cursor.getDouble(cursor.getColumnIndex("SPEED"));
    		   int max_speed = (int )cursor.getDouble(cursor.getColumnIndex("MAX_SPEED"));
    		   String text = date + "," + time + "," + speed + "," + max_speed;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}