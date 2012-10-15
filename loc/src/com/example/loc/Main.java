package com.example.loc;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class Main extends Activity {
	private long ptime;
	private Location plocation = new Location("ploc");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LocationListener locationListener = new LocationListener() {
        	@Override
        	public void onStatusChanged(String provider, int status, Bundle extras) {	
			}
			@Override
			public void onProviderEnabled(String provider) {
			}
			@Override
			public void onProviderDisabled(String provider) {
			}
			@Override
			public void onLocationChanged(Location location) {
				double longitude = location.getLongitude();
		        double latitude = location.getLatitude();
		        long time = System.currentTimeMillis();
		        long timediff = time - ptime;
		        long one_second_gap = 1000;
		        if(timediff > one_second_gap)
		        {
			        double distance = location.distanceTo(plocation);
			        String text;
			        text = "" + distance;
			        TextView tv = (TextView) findViewById(R.id.TextView05);
			        tv.setText(text);
			        text = "" + plocation.getLatitude();
			        TextView tv1 = (TextView) findViewById(R.id.TextView03);
			        tv1.setText(text);
			        text = "" + plocation.getLongitude();
			        TextView tv2 = (TextView) findViewById(R.id.textView4);
			        tv2.setText(text);
			        text = "" + location.getLatitude();
			        TextView tv3 = (TextView) findViewById(R.id.textView3);
			        tv3.setText(text);
			        text = "" + location.getLongitude();
			        TextView tv4 = (TextView) findViewById(R.id.TextView04);
			        tv4.setText(text);
		        }
		        plocation.setLatitude(latitude);
		        plocation.setLongitude(longitude);
		        ptime = time;
			}
		};
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
