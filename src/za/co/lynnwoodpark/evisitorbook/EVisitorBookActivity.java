package za.co.lynnwoodpark.evisitorbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class EVisitorBookActivity extends Activity {
	//private static boolean running = false;
	public static long nextGetdata = 0, startCnt = 0;
	/*final String valid[] = { "Lauren 354", "Jeremy 305", "Jeremy 309", "Jeremy 313", "Jeremy 317", 
			"Jeremy 321", "Jeremy 325", "Jeremy 329", "Jeremy 333", "Jeremy 337", "Fairy Glen 353", 
			"Sadie 342", "Sadie 338", "Sadie 334", "Sadie 330", "Sadie 326", "Sadie 322", "Sadie 318",
			"Sadie 314", "Sadie 310", "Sadie 306", "Sadie 302", "Lauren 366", "Sadie 305", "Sadie 309",
			"Sadie 313", "Sadie 317", "Sadie 321", "Sadie 321", "Sadie 325", "Sadie 329", "Sadie 333",
			"Sadie 337", "Fairy Glen 365", "Fairy Glen 369", 
			"Danny 342", "Danny 338", "Danny 334", "Danny 330",
			"Danny 326", "Danny 322", "Danny 318", "Danny 314", "Danny 310", "Danny 306", "Danny 302",
			"Lauren 368", "Lauren 378", "Danny 305", "Danny 309", "Danny 313", "Danny 317", "Danny 321",
			"Danny 325", "Danny 329", "Danny 333", "Danny 337", "Fairy Glen 377", "Edna 342",
			"Fairy Glen 379", "Edna 338", "Edna 334", "Edna 330", "Edna 326", "Edna 322", "Edna 318",
			"Edna 314", "Edna 310", "Edna 306", "Edna 302", "Edna 298", "Edna 294", "Lauren 382", 
			"Lauren 394", "Edna 293", "Edna 297", "Edna 301", "Edna 305", "Edna 309",
			"Edna 313", "Edna 317", "Edna 321", "Edna 325", "Edna 329", "Edna 333", "Edna 341", 
			"Lauren 398",};*/
	private String val = "";
	private TextView tw;
    private String firstTry = null;
    @Override
    public void onPause() {
    	super.onPause();
    	firstTry = null;
		if (nextGetdata < (new Date()).getTime()) {
			nextGetdata = (new Date()).getTime() + 5 * 3600000;
	    	new Thread(new Runnable() {
	    		public void run() {
			try {
        		URL url = new URL("https://docs.google.com/spreadsheet/pub?hl=en_US&hl=en_US&key=&single=true&gid=0&range=C4%3AF89&output=csv");
        		RandomAccessFile r = new RandomAccessFile (new File (getFilesDir(), "newdata"), "rw");
        		BufferedReader bf = new BufferedReader (new InputStreamReader (url.openStream()));
        		String l;
        		while ((l=bf.readLine())!= null) {
        			r.writeBytes (l + "\n");
        		}
        		bf.close();
        		r.close();
        		(new File (getFilesDir(), "newdata")).renameTo(new File (getFilesDir(), "data"));
				URL send2 = new URL("");
				BufferedReader bf2 = new BufferedReader (new InputStreamReader (send2.openStream()));
				while (bf2.readLine()!= null) {}
				bf2.close ();
			} catch (IOException e) {
				Log.d("EVisitorBook", "Error: ");
			}
	    		}}).start();
		}

    	/*if (val.equals ("512")) {
    		
    	    Handler handler = new Handler(); 
    	    handler.postDelayed(new Runnable() { 
    	         public void run() { 
    	        	  running = false;
    	        	  finish ();
    	         } 
    	    }, 20000); 
    	}
    	else {
    	  running = false;
    	  finish ();
    	}*/
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED); 
        setContentView(R.layout.main);
        tw = (TextView) findViewById (R.id.text);
        int btn[] = { R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, 
        		R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnC, R.id.btnDel }; 
        for (int b: btn) { 
        	Button selectButton = (Button) findViewById(b);
        	selectButton.setOnClickListener(new OnClickListener() {
        		public void onClick(View v) {
      		  		if (v == findViewById (R.id.btnC)) val = "";
      		  		else if (v == findViewById (R.id.btnDel)) {
      		  			if (val.length() > 0) val = val.substring(0, val.length()-1);
      		  		}
      		  		else {
      		  			val += v.getTag();
      		  			if (val.length() >= 3) showDialog(val.charAt(1) >= '5' && val.charAt(0) > '2' ? 2 : 1);
      		  		}
      		  		tw.setText (val);
        		}
        	});
        }
        if (startCnt++ == 0) {
        }
    }
    private java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("EEE+HH'h'mm");
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 1 || id == 2) {
            final String[] items2 = { "Edna", "Danny", "Sadie", "Jeremy", "Cancel" };
            final String[] items3 = { "Lauren", "Fairy Glen", "Cancel" };
            return new AlertDialog.Builder(EVisitorBookActivity.this)
            //.setTitle("Choose Street")
            .setItems(id == 2 ? items3 : items2, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    /* User clicked so do some stuff */
                	
                    //String[] items = { "Edna", "Danny", "Sadie", "Jeremy", "Lauren", "Fairy Glen" };
           		    Date d = new Date ();
           		    boolean isItems3 =  val.charAt(1) >= '5' && val.charAt(0) > '2';
                    final String x = (isItems3 ? items3 : items2)[which] + " " + val;
                    final String tstamp = (f.format(new java.util.Date (d.getTime())));
                    /*new AlertDialog.Builder(EVisitorBookActivity.this)
                    .setMessage(Arrays.asList(valid).contains(x)
                    		? "Success !" : "Error! " + x + " does not exist")
                    .show();*/

                            /*try {
                            	URL send2 = new URL("http://osmu.org/audit?" + x.charAt(0) + x.substring(x.length()-3,x.length()));
                            	BufferedReader bf2 = new BufferedReader (new InputStreamReader (send2.openStream()));
                            	while (bf2.readLine()!= null) {}
                            	bf2.close ();
                            } catch (IOException e) {
                        		Log.d("EVisitorBook", "Error: ");
                        	}*/
                        	
                	String l = "";
                    try {
                    	RandomAccessFile bf = new RandomAccessFile (new File (getFilesDir(), "data"), "r");
                    	while ((l=bf.readLine())!= null && !x.equals(l.substring(0, x.length()))) {}
                    	bf.close();
                    	
                        tw.setText (which > (isItems3 ? 1 : 3) ? ""	: l == null ? "Error!"
                		: firstTry == null ? "Repeat" : x.equals(firstTry) ? "Success" : "Repeat fail");
                        
                        int i = 0, j = 0, c = 0, m = 0, e = 0;
                        if (l != null) {
                        	for (i = 0; i < l.length() && c < 2 && j < 10; i++) {
                        		if (l.charAt(i) == ',') c++;
                        		j = l.charAt(i) < '0' || l.charAt(i) > '9' ? 0 : j + 1;
                        	}
                        	for (m = i; m < l.length() && (c < 2 || l.charAt(m) != '"'); m++) if (l.charAt(m) == ',') c++;
                        	for (e = m + 1; e < l.length() && l.charAt(e) != '"'; e++) {}
                        }
                        if (firstTry != null && x.equals(firstTry) && l != null
                        		&& j == 10 && c == 2 && l.charAt(m) == '"' && e - m < 149) {
                        	RandomAccessFile r = new RandomAccessFile (new File (getFilesDir(), "smsCount"), "rw");
                        	r.seek(r.length ());
        		            r.writeBytes("a");
        		            final String dst = l.substring(i-10,i), 
        		            		boodskap = tstamp + " " + l.substring (m + 1, e);
        		            final long rLength = r.length();
        		            r.close();
        		            
                        	new Thread(new Runnable() {
                        		public void run() {
                    				SmsManager sms = SmsManager.getDefault();
                			        sms.sendTextMessage(dst, null, boodskap, null, null);
                            		
                			        try {
                			        	URL send = new URL("http://lynnwoodpark.co.za/house2.php?house="
                           				   + dst + x.charAt(0) + x.substring(x.length()-3,x.length())
                 			        	   + "+" + tstamp
                           				   + "+" + rLength);
                			        	BufferedReader bf2 = new BufferedReader (new InputStreamReader (send.openStream()));
                			        	while (bf2.readLine()!= null) {}
                			        	bf2.close ();
                			        } catch (MalformedURLException e) {
                			        } catch (IOException e) {          			    
                			        }
                        		}
                        	}).start();
                        } // If the repeat was identical and the file contains a phone number for the address entered.
                        firstTry = which > (isItems3 ? 1 : 3) || firstTry != null || l == null ? null : x;
                        val = "";
                	} catch (IOException e) {
                		Log.d("EVisitorBook", "Error: " + e);        		
                	}
                } // Handle a click on a street name
            }).create();
        } // Handle a request to create a dialog listing street names
        return null;
    }
}