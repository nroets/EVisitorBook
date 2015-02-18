package za.co.lynnwoodpark.evisitorbook;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		BroadcastReceiver scrOnBcast = new BroadcastReceiver() {
     		@Override
     		public void onReceive(Context context, Intent intent) {
     	        if ( intent.getAction().equals(Intent.ACTION_SCREEN_ON) ) {
     	        	Intent start = new Intent(context, EVisitorBookActivity.class);
     	        	start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK /*| Intent.FLAG_ACTIVITY_CLEAR_TASK*/);
     	        	context.startActivity(start);
     	   		} else if ( intent.getAction().equals(Intent.ACTION_SCREEN_OFF) ) {
//     	                ScreenReceiverService.reenableKeyguard();
     	   		}
     		}
     	};
     	IntentFilter filt = new IntentFilter ();
     	filt.addAction(Intent.ACTION_SCREEN_ON);
     	getApplicationContext().registerReceiver(scrOnBcast, filt);
	}
}
