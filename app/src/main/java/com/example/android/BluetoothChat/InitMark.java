package com.example.android.BluetoothChat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import com.liqin.instrument.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InitMark extends Activity {
	private static final String TAG = "initmark";
	
	private TextView initDeviceName;
	private EditText initProjectName;
	private EditText initDelyTimer;
	private EditText initDepth;
	private TextView initInterval;
	private LinearLayout initStart;
	private static Context InitMarkContext;
	public static InitMark  InitMarkActivity = null;
	private static AlertDialog alertActive = null;
	//public static ImageView devicelamp = null;
	private ImageButton initIntervalUp;
	private ImageButton initIntervalDown;
	private ImageView logbtn = null;
	public static Boolean start = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(commond.isBlockHome)this.getWindow().setFlags(main.FLAG_HOMEKEY_DISPATCHED, main.FLAG_HOMEKEY_DISPATCHED);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.initmark);
		InitMarkContext = this;
		InitMarkActivity = this;
		commond.activeContext = this;
		
		//devicelamp = (ImageView)findViewById(R.id.deviceLamp);
		initDeviceName = (TextView) findViewById(R.id.initDeviceName);
		initProjectName = (EditText) findViewById(R.id.initProjectName);
		initDelyTimer = (EditText) findViewById(R.id.initDelyTimer);
		initDepth = (EditText) findViewById(R.id.initDepth);
		initInterval = (TextView) findViewById(R.id.initInterval);
		initStart = (LinearLayout) findViewById(R.id.initStart);
		initIntervalUp = (ImageButton) findViewById(R.id.initIntervalUp);
		initIntervalDown = (ImageButton) findViewById(R.id.initIntervalDown);
		//logbtn= (ImageView)findViewById(R.id.logs);
		
		//commond.devicelamp = devicelamp;
		
		initDeviceName.setText(commond.conntectedName);
		
		initIntervalUp.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				int val = Integer.parseInt(initInterval.getText().toString());
				val = val +5;
				if(val>60){
					val = 60;
				}
				initInterval.setText(""+val);
			}
			
		});
		
		initIntervalDown.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				int val = Integer.parseInt(initInterval.getText().toString());
				val = val -5;
				if(val<5){
					val = 5;
				}
				initInterval.setText(""+val);
			}
			
		});
		
	//	logbtn.setOnClickListener(new OnClickListener(){
	//		@Override
	//		public void onClick(View v) {
				//Intent intent = new Intent(InitMark.this,logs.class) ;
	//			startActivity(intent);
	//		}
	//
	//	});
		
		
		initStart.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String initProjectNameString=initProjectName.getText().toString();
				String initDelyTimerString=initDelyTimer.getText().toString();
				String initDepthString=initDepth.getText().toString();
				
				if("".equals( initProjectNameString )){
					startSimpleDailog(R.string.dilog_initmark_noProjectName);
					return;
				}
				if("".equals(initDelyTimerString)){
					startSimpleDailog(R.string.dilog_initmark_noDelyTimer);
					return;
				}
				if("".equals(initDepthString)){
					startSimpleDailog(R.string.dilog_initmark_noDepth);
					return;
				}
				
				if(commond.D)Log.e(TAG, "_socket:"+main._socket+"|isConntected:"+commond.isConntected);
				if(main._socket==null||!commond.isConntected){
					commond.setToast(getApplication().getString(R.string.noconnted));
					//Toast.makeText(getApplicationContext(), "设备还未连接，请先连接设备！", Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				
				if(commond.readTXT(commond.taskName,getBaseContext(),false)){
					startMarkActivity();
				}
				else {
					new AlertDialog.Builder(InitMarkContext)
					.setMessage(getApplication().getString(R.string.dilog_isdownload))
					/*
					.setNegativeButton(getApplication().getString(R.string.dilog_newmark),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									commond.lastCount = 0;//清除上次记录
									commond.doTask("del", "");
									Mark.initStatic();
									startMarkActivity();
								}
							})
					*/
					.setPositiveButton(getApplication().getString(R.string.dilog_ok),
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int whichButton) {
									//写入队列
									
				    				for(int a=0;a<commond.taskIIArray.length;a++){
				    					if(commond.D)Log.e(TAG, "f:"+commond.taskIIArray[a]);
				    					String f= commond.taskIIArray[a];
				    					int caxunId = Integer.parseInt(f);
				    					commond.writeOs(commond.caxun(caxunId));
				    				}
				    				dialog.dismiss();
				    				commond.writeOsNext();
//				    				context.startProgress();
				    				commond.writeOsNext();
				    				Intent intentaa = new Intent(InitMarkContext,Progress.class) ;
				    				InitMarkContext.startActivity(intentaa);
				    				
								}
							}).show();
				}
			}
		});
		
		SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd hh-mm-ss");     
	    String   date   =   sDateFormat.format(new   java.util.Date());
		initProjectName.setText(date);
		
		//commond.setDeviceType(0);
	}
	
	private void startSimpleDailog(int Message){
		new AlertDialog.Builder(InitMarkContext)
		.setMessage(getApplication().getString(Message))
		.setPositiveButton(getApplication().getString(R.string.dilog_ok),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
					}
				}).show();
	}
	
	private void startMarkActivity(){
		start = true;
		commond.instruction.clear();
		commond.NPName = initProjectName.getText().toString();
		commond.NPDTime = initDelyTimer.getText().toString();
		commond.NPDepth = initDepth.getText().toString();
		commond.NPInterval = initInterval.getText().toString();
		
		
		alertActive = new AlertDialog.Builder(InitMarkContext)
		.setMessage(getApplication().getString(R.string.dilog_watting))
		.show();
		
		
		commond.writeOs(commond.celiang(commond.NPDTime, commond.NPInterval));
		commond.writeOsNext();
	}
	
	@Override
	protected void onDestroy()
	{
		if(commond.D)Log.e(TAG, "onDestroy");
		super.onDestroy();
		finish();
	};
	
	@Override
    public synchronized void onPause() {
		super.onPause();
    }
	
	@Override
    public synchronized void onResume() {
        super.onResume();
        commond.activeContext = this;
        if(commond.D) Log.e(TAG, "+ ON RESUME +");
    }
	
	public static class Receiver extends BroadcastReceiver {  
        @Override  
        public void onReceive(Context context, Intent intent) {  
        	String content=intent.getStringExtra("readMessage"); 
        	String[] info = commond.returnInfo(content);
        	if(info[0].equals("CLOK")){
        		commond.retryNum = 0;//重置重复次数
        		if(commond.D)Log.e(TAG, "计时器开启");
        		if(Mark.markActivity==null){
	        		alertActive.cancel();
	        		Mark.initStatic();
	        		Intent intentcc = new Intent(context,Mark.class) ;
	        		intentcc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        		context.startActivity(intentcc);
	        		InitMarkActivity.finish();
	        		commond.closeSocket();
	        		
        		}
        	}
        }  
	}
}
