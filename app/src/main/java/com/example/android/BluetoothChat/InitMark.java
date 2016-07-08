package com.example.android.BluetoothChat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
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
	private EditText initLatitude;
	private Button initFindLatitude;
	private static Context InitMarkContext;
	public static InitMark  InitMarkActivity = null;
	private static AlertDialog alertActive = null;
//	private LocationListener locationListener;
	//public static ImageView devicelamp = null;
//	private ImageButton initIntervalUp;
//	private ImageButton initIntervalDown;
	private ImageView logbtn = null;
	public static Boolean start = false;
	private LocationManager mgr = null;
	private String provider;
	
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
		//initDepth = (EditText) findViewById(R.id.initDepth);
		initInterval = (TextView) findViewById(R.id.initInterval);
		initStart = (LinearLayout) findViewById(R.id.initStart);
		initLatitude = (EditText) findViewById(R.id.editText_Latitude);
//		initFindLatitude = (Button) findViewById(R.id.button_find_latitude);
	//	initIntervalUp = (ImageButton) findViewById(R.id.initIntervalUp);
	//	initIntervalDown = (ImageButton) findViewById(R.id.initIntervalDown);
		//logbtn= (ImageView)findViewById(R.id.logs);
		
		//commond.devicelamp = devicelamp;

	//	openGPSSettings();
//		mgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//		// 查找到服务信息
//		Criteria criteria = new Criteria();
//		criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
//		criteria.setAltitudeRequired(false);
//		criteria.setBearingRequired(false);
//		criteria.setCostAllowed(true);
//		criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
//		String provider = mgr.getBestProvider(criteria, true); // 获取GPS信息
//		Location location = mgr.getLastKnownLocation(provider); // 通过GPS获取位置
//		while(location == null)
//		{
//			mgr.requestLocationUpdates("gps",60000,1,locationListener);
//		//	location = mgr.getLastKnownLocation(provider); //
//		}
//		double showLocation = (int)(location.getLatitude()*100)/100.0;
//		//initLatitude.setText("123"+ showLocation + "");
	//	initLatitude.setText(31.25 + "");
	//	if(mgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	//		getLocation();
	//	}

	//	mLocationListener = new LocationListener();

		initLatitude.setText("");
		mgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		List<String> providerList = mgr.getProviders(true);
		if(providerList.contains(LocationManager.GPS_PROVIDER)){
			provider = LocationManager.GPS_PROVIDER;
		}else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
			provider = LocationManager.NETWORK_PROVIDER;
		}else{
			if(commond.D) Log.e(TAG, "No location provider to use");
			return;
		}

		Location location = mgr.getLastKnownLocation(provider);
		if(location != null){
			double showLocation = (int)(location.getLatitude()*100)/100.0;
			initLatitude.setText(showLocation + "");
		}

		mgr.requestLocationUpdates(provider,5000,1,locationListener);


		if(commond.D) Log.e(TAG, "+ GetSystemService GPS +");
		
		initDeviceName.setText(commond.conntectedName);

//		initFindLatitude.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				getLocation();
//			}
//		});
		
//		initIntervalUp.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				int val = Integer.parseInt(initInterval.getText().toString());
//				val = val +5;
//				if(val>60){
//					val = 60;
//				}
//				initInterval.setText(""+val);
//			}
//
//		});
//
//		initIntervalDown.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				int val = Integer.parseInt(initInterval.getText().toString());
//				val = val -5;
//				if(val<5){
//					val = 5;
//				}
//				initInterval.setText(""+val);
//			}
//
//		});
		
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
				String initIntervalString =initInterval.getText().toString();
				
				if("".equals( initProjectNameString )){
					startSimpleDailog(R.string.dilog_initmark_noProjectName);
					return;
				}
				if("".equals(initDelyTimerString)){
					startSimpleDailog(R.string.dilog_initmark_noDelyTimer);
					return;
				}
				if("".equals(initIntervalString)){
					startSimpleDailog(R.string.dilog_initmark_noDepth);
					return;
				}

				int initDelyTimerInt =  Integer.parseInt(initDelyTimerString);
				int initIntervalInt =  Integer.parseInt(initIntervalString);

				if(initDelyTimerInt < 3){
					startSimpleDailog(R.string.dilog_initmark_DelayTimeInt);
					return;
				}

				if(initIntervalInt < 3){
					startSimpleDailog(R.string.dilog_initmark_IntervalInt);
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
									InitMark.start = true;//开始写数据倒内存
									//写入队列
									//diti
									for(int a=0;a<commond.lastCount;a++){
										commond.writeOs(commond.caxun(a));
									}
									dialog.dismiss();
									commond.writeOsNext();
									if(commond.D)Log.e(TAG, "keep2");
									commond.startKeepService();
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


	private void openGPSSettings() {
		LocationManager alm = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			if(commond.D) Log.e(TAG, "GPS模块正常");
			return;
		}
		if(commond.D) Log.e(TAG, "请开启GPS！");
		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		startActivityForResult(intent,0); //此为设置完成后返回到获取界面

	}

	private void getLocation()
	{
		// 获取位置管理服务
		LocationManager locationManager;
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) this.getSystemService(serviceName);
		// 查找到服务信息
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

		String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
		Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
		double showLocation = (int)(location.getLatitude()*100)/100.0;
		//initLatitude.setText("123"+ showLocation + "");
		initLatitude.setText(showLocation + "");
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
		commond.NPTime = initDelyTimer.getText().toString();
		commond.NPLatitude = initLatitude.getText().toString();
		commond.NPLatitude = String.valueOf(Float.parseFloat(commond.NPLatitude)*100);
		commond.NPLatitude = commond.NPLatitude.substring(0,commond.NPLatitude.length()-2);
		commond.NPInterval = initInterval.getText().toString();
		
		
		alertActive = new AlertDialog.Builder(InitMarkContext)
		.setMessage(getApplication().getString(R.string.dilog_watting))
		.show();
		
		int strlen = commond.NPLatitude.length();
		for(int i = strlen;i<4;i++) commond.NPLatitude = "0" + commond.NPLatitude;

		strlen = commond.NPInterval.length();
		for(int i = strlen;i<3;i++) commond.NPInterval = "0" + commond.NPInterval;

		strlen = commond.NPTime.length();
		for(int i = strlen;i<3;i++) commond.NPTime = "0" + commond.NPTime;

		commond.writeOs(commond.celiang(commond.NPTime, commond.NPInterval,commond.NPLatitude));
		commond.writeOsNext();
	}
	
	@Override
	protected void onDestroy()
	{
		if(commond.D)Log.e(TAG, "onDestroy");
		super.onDestroy();
		if(mgr != null){
			mgr.removeUpdates(locationListener);
		}
		finish();
	};

	LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			double showLocation = (int)(location.getLatitude()*100)/100.0;
			initLatitude.setText(showLocation + "");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}
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
