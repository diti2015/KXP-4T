package com.example.android.BluetoothChat;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.liqin.instrument.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Online extends Activity {
	private static final String TAG = "online";
	
	private CheckBox onlineDipCheckbox;
	private CheckBox onlineOrientationCheckbox;
	private static TextView onlineDip;
	private static TextView onlineOrientation;
	private LinearLayout clearZero;
	private LinearLayout ReFactoryD;
	static Timer TimerOutS = new Timer() ;
	static boolean TimerOutSStatus = false;
	private static Timer clearWriteOsNext =  new Timer();
	private ImageView devicelamp = null;
	 public static PendingIntent Onlinepi = null;
	 public static Online OnlineActivity = null;
	 public static Boolean isCreatOnline = false;
	public static int wicount;
	public static int flagTN = 0;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(commond.isBlockHome)this.getWindow().setFlags(main.FLAG_HOMEKEY_DISPATCHED, main.FLAG_HOMEKEY_DISPATCHED);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.onlinetest);
		
		commond.activeContext = this;
		OnlineActivity = this;
		onlineDipCheckbox = (CheckBox) findViewById(R.id.onlineDipCheckbox);
		onlineOrientationCheckbox = (CheckBox) findViewById(R.id.onlineOrientationCheck);
		onlineDip = (TextView) findViewById(R.id.onlineDip);
		onlineOrientation = (TextView) findViewById(R.id.onlineOrientation);
		clearZero = (LinearLayout) findViewById(R.id.clearZero);
		ReFactoryD = (LinearLayout) findViewById(R.id.ReFactoryD);
		devicelamp = (ImageView)findViewById(R.id.deviceLamp);
		
		commond.devicelamp = devicelamp;
		
//		devicelamp.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(Online.this,logs.class) ;
//				startActivity(intent);
//			}
//
//		});
		onlineDipCheckbox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(onlineOrientationCheckbox.isChecked()){
					onlineOrientationCheckbox.setChecked(false);
				}
			}
		});

		onlineOrientationCheckbox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(onlineDipCheckbox.isChecked()){
					onlineDipCheckbox.setChecked(false);
				}
			}
		});

		clearZero.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				setClearZert();
			}
		});
		
		ReFactoryD.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				setReFactoryD();
			}
		});
		//commond.SelfTest = true;
		//commond.writeOs("$CELIANG,5,5*  ");
		wicount = 0;
		commond.instruction.clear();
		commond.writeOs(commond.OUTHZ);
		commond.writeOsNext();
		commond.setDeviceType(0);
		
		isCreatOnline = true;
		
	}
	
	@Override
    public void onResume() {
        super.onStart();
        if(commond.D) Log.e(TAG, "++ ON onResume ++");
        //commond.SelfTest = true;
 //       if(commond.isConntected)commond.writeOsNext();
        commond.activeContext = this;
    }
	
	@Override
	protected void onDestroy()
	{
		isCreatOnline = false;
		super.onDestroy();
		commond.SelfTest = false;
		commond.instruction.clear();
		flagTN = 0;
		commond.stopKeepService();
		commond.devicelamp = null;
		OnlineActivity = null;
		finish();
	};
	
	@Override
    public synchronized void onPause() {
		if(commond.D)Log.e(TAG, "on onPause");
		commond.SelfTest = false;
		commond.instruction.clear(); 
        super.onPause();
    }
	
	private void setClearZert(){
		if(commond.instruction.size()!=0){commond.instruction.remove(0);}
		if(onlineDipCheckbox.isChecked()){
			flagTN = 1;
			//commond.writeOs(commond.ZLDJ);
		}
		if(onlineOrientationCheckbox.isChecked()){
			flagTN = 2;
			//commond.writeOs(commond.ZLFW);
		}
		commond.writeOsNext();
	}
	
	private void setReFactoryD(){
		if(commond.instruction.size()!=0){commond.instruction.remove(0);}
		if(onlineDipCheckbox.isChecked()){
			flagTN = 3;
			//commond.writeOs(commond.FWDJ);
		}
		if(onlineOrientationCheckbox.isChecked()){
			flagTN = 4;
			//commond.writeOs(commond.FWFW);
		}
		commond.writeOsNext();
	}
//
//	public static class Receiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//        	String content=intent.getStringExtra("readMessage");
//        	/*
//        	if(TimerOutSStatus){
//        		TimerOutSStatus = false;
//        		TimerOutS.cancel();
//        	}
//        	*/
//            //CharSequence string="收到信息:"+content;
//        	String[] info = commond.returnInfo(content);
//        	Log.e(TAG, ""+info);
//			if(info[0].equals("WO")&&isCreatOnline){
//				commond.retryNum = 0;
//				commond.stopKeepService();
//				commond.reallyTimer = false;
//
//				commond.setToast("测量中"+wicount);
//
//				new Timer().schedule(new TimerTask() {
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
////							if (commond.instruction.size() != 0) {
////								commond.instruction.remove(0);
////							}
//							//commond.writeOs(commond.OUTHZ);
//							flagTN = 6;
//							commond.writeOsNext();
//						}
//					}, 4000);//设备在线时候，执行间隔
//
//				commond.startKeepService();
//			}
//			if(info[0].equals("WI")&&isCreatOnline){
//				commond.retryNum = 0;
//				commond.stopKeepService();
//				commond.reallyTimer = false;
//
//				commond.setToast("明天还要继续吗");
//
//	//			flagTN = 6;
//	//			commond.writeOsNext();
//				new Timer().schedule(new TimerTask() {
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//					//	if(commond.instruction.size()!=0){
//					//	commond.instruction.remove(0);}
//						flagTN = 6;
//						//commond.writeOs(commond.OUTHZ);
//						commond.writeOsNext();
//					}
//				}, 4000);//设备在线时候，执行间隔
//
//				commond.startKeepService();
//			}
//        	if(info[0].equals("TN")&&isCreatOnline){
//				//flagTN = 5;
//        		commond.retryNum = 0;//重置重复次数
//        		//Log.e(TAG, "22222222222222222222222222222222222222222222222");
//        		//clearWriteOsNext.cancel();
//        		//clearWriteOsNext = new Timer();
//        		commond.retryNum = 0;
//        		commond.stopKeepService();
//        		commond.reallyTimer = false;
//
//        		//Log.e(TAG, ""+info[0]);
//        		float dipValue = Float.parseFloat(info[1]) / 100;
//        		float OreValue = Float.parseFloat(info[3]) / 10;
//
//	        //	onlineDip.setText(""+dipValue+"°");
//	        //	onlineOrientation.setText(""+OreValue+"°");
//				onlineDip.setText( ""+dipValue+"\u00b0");
//				onlineOrientation.setText(""+OreValue+"\u00b0");
//	        	//handler.removeCallbacks(runnable);
//	        	//handler.postDelayed(runnable, 2000);
//
//	//			if(commond.instruction.size()!=0){
//	//				commond.instruction.remove(0);}
//
////				flagTN = 5;
////				commond.writeOsNext();
////	       	new Timer().schedule(new TimerTask() {
////					@Override
////					public void run() {
////						// TODO Auto-generated method stub
////						flagTN = 5;
////			        	commond.writeOsNext();
////					}
////				}, 4000);//设备在线时候，执行间隔
//
//	        	commond.startKeepService();
//
//
//	        	/*
//	        	clearWriteOsNext.schedule(new TimerTask() {
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						Log.e(TAG,"超时执行！超时执行！");
//			        	commond.writeOsNext();
//
//					}
//				}, 10000);
//	        	*/
//	        	//if(D)Log.e(TAG,info[1]);
//        	}
//        }
//	}


	public static class Receiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			String content=intent.getStringExtra("readMessage");

			String[] info = commond.returnInfo(content);
			Log.e(TAG, ""+info);
			if((info[0].equals("WO") || info[0].equals("WI") || info[0].equals("TN")) && isCreatOnline)
			{
				commond.retryNum = 0;
				commond.stopKeepService();
				commond.reallyTimer = false;

				if(info[0].equals("WO")){
					commond.setToast("测量中");
					flagTN = 6;
				}
				else if(info[0].equals("WI")){
					commond.setToast("测量中");
					flagTN = 6;
				}
				else if(info[0].equals("TN")){

					flagTN = 5;

					float dipValue = Float.parseFloat(info[1]) / 100;
					float OreValue = Float.parseFloat(info[3]) / 10;

					onlineDip.setText( ""+dipValue+"\u00b0");
					onlineOrientation.setText(""+OreValue+"\u00b0");
				}

				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						commond.writeOsNext();
					}
				}, 4000);//设备在线时候，执行间隔

				commond.startKeepService();
			}
		}
	}
	
	static Handler handler=new Handler();
	static Runnable runnable=new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//要做的事情
			if(commond.D)Log.e(TAG,"1234565");
			commond.writeOsNext();
		}
	};
	
}
