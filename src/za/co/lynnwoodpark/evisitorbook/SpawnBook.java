package za.co.lynnwoodpark.evisitorbook;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SpawnBook extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
    	if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
    		context.startService(new Intent(context, MyService.class));
    	}
/*        final Context c = context;
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0;; i += 4) {
                        try {
                                Thread.sleep(9000);
                        }
                        catch (InterruptedException e)
                        {
                        }
                        if (EVisitorBookActivity.startCnt == 0) {
                                //EVisitorBookActivity.running = true;
                                Intent ebook = new Intent(c, EVisitorBookActivity.class);
                                ebook.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                c.startActivity(ebook);
                        }
                }
            }
        }).start();*/
    }

}