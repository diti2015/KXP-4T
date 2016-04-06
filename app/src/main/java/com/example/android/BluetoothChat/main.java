/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.BluetoothChat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.liqin.instrument.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This Activity appears as a dialog. It lists any paired devices and devices
 * detected in the area after discovery. When a device is chosen by the user,
 * the MAC address of the device is sent back to the parent Activity in the
 * result Intent.
 */
public class main extends Activity
{
	// Debugging
    private static final String TAG = "main";

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout Views
    public static TextView connectedDeviceName;
    private LinearLayout celiang;
    private LinearLayout chayue;
    private LinearLayout setting;
    private Button ChangeDevice;
    private ImageView mainBg;
    public static ProgressBar conntedprogress;
   
    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    //private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    //private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    //public static BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    //public static BluetoothChatService mChatService = null;
    
    public static BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
    public static BluetoothDevice _device = null;     //蓝牙设备
    public static BluetoothSocket _socket = null;      //蓝牙通信socket
    boolean _discoveryFinished = false;    
    static boolean bRun = false;
    public static boolean bThread = false;
    public final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号
    public static InputStream is;    //输入流，用来接收蓝牙数据
    public static String smsg = "";    //显示用数据缓存
    private static String fmsg = "";    //保存用数据缓存
    public static String cmsg = "";    //判断返回值是否重复
    
    public static Intent bluetoothIntent = new Intent();
    
	private static Context context;
	public static Activity mainActivity;
	
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
	
	//public static ImageView devicelamp = null;
	private ImageView logbtn = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		if(commond.isBlockHome)this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		context=this;
		mainActivity = this;
		
		if(commond.D) Log.e(TAG, "+++ ON CREATE +++");
		
		 String path=Environment.getDataDirectory().getPath();
		 //Log.e("nn", path);
		 
		 setContentView(R.layout.main);
		 commond.activeContext = this;
		 
		 //获取AlarmManager对象
		 commond.alarmM = (AlarmManager)getSystemService(ALARM_SERVICE);
		 commond.pi = PendingIntent.getService(this,55, new Intent(this,
					KeepService.class), Intent.FLAG_ACTIVITY_NEW_TASK);
		 commond.alarmM.cancel(commond.pi);//防止在程序意外崩溃时候，没有正常关闭
		 Mark.initStatic();
		 
		 logbtn = (ImageView)findViewById(R.id.logs);
		 //devicelamp = (ImageView)findViewById(R.id.deviceLamp);
		 mainBg = (ImageView)findViewById(R.id.mainBg);
		 connectedDeviceName = (TextView) findViewById(R.id.connectedDeviceName);
		 celiang= (LinearLayout)findViewById(R.id.celiang);
		 chayue = (LinearLayout)findViewById(R.id.chayue);
		    setting = (LinearLayout)findViewById(R.id.setting);
		    ChangeDevice = (Button)findViewById(R.id.changeDevice);
		    conntedprogress =  (ProgressBar)findViewById(R.id.connectingprogress);
		    
		    //commond.devicelamp = devicelamp;
		    logbtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(main.this,logs.class) ;
					startActivity(intent);
				}
				
			});
		    
		    celiang.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(main.this,InitMark.class) ;
					startActivity(intent);
					//finish();
				}
				
			});
		    
		    chayue.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					if(commond.checkFile()){
						Intent intent = new Intent(main.this,com.throne.digtool.MainActivity.class) ;
						startActivity(intent);
					}
				}
				
			});
		    
			 setting.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(main.this,Setting.class) ;
					startActivity(intent);
					//finish();
				}
				
			});
		 
			 ChangeDevice.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						startSearchDevice();
					}
					
				});
			 
			//检查文件夹
	       commond.checkOutDir();
	       
	      //读取配置文件
		  commond.readTXT(commond.configName,getBaseContext(),true);
		  
		  //写入背景
		  Bitmap newBg= commond.readMainBg();
		  if(commond.D)Log.e(TAG, "newBg:"+newBg);
		  
		  if(newBg==null){
			  //mainBg.setImageDrawable(getResources().getDrawable(R.drawable.blue_down));
			  mainBg.setImageResource(R.drawable.main_bg);
		  }
		  else{
			  mainBg.setImageBitmap(newBg);
		  }
		  
		  
		  //commond.setDeviceType(0);
		  
		//如果打开本地蓝牙设备不成功，提示信息，结束程序
	        if (_bluetooth == null){
	        	commond.setToast(this.getString(R.string.logs_nobuletooth));
	        	//Toast.makeText(this, "无法打开手机蓝牙，请确认手机是否有蓝牙功能！", Toast.LENGTH_LONG).show();
	            finish();
	            return;
	        }
	        
	        if(_bluetooth.isEnabled()==false){
        		_bluetooth.enable();
        		Intent serverIntent = new Intent(mainActivity, waitting.class);
        		startActivity(serverIntent);
        		 logs.setLogs(getApplication().getString(R.string.bluetoothsuccess));
    		   }
    		   else {
    			   logs.setLogs(getApplication().getString(R.string.bluetoothisopened));
    			   //commond.closeSocket();
    			   	//自动搜索设备
    				commond.isLastTask = true;
    				Intent serverIntent = new Intent(mainActivity, DeviceListActivityAuto.class);
    				mainActivity.startActivityForResult(serverIntent, 1);
    		   }
	      
//	        // 设置设备可以被搜索  
//	       new Thread(){
//	    	   public void run(){
//	    		  
//	    		   if(_bluetooth.isEnabled()==false){
//	        		_bluetooth.enable();
//	        		Intent serverIntent = new Intent(mainActivity, waitting.class);
//	        		startActivity(serverIntent);
//	        		 logs.setLogs(getApplication().getString(R.string.bluetoothsuccess));
//	    		   }
//	    		   else {
//	    			   logs.setLogs(getApplication().getString(R.string.bluetoothisopened));
//	    			   //commond.closeSocket();
//	    			   	//自动搜索设备
//	    				commond.isLastTask = true;
//	    				Intent serverIntent = new Intent(mainActivity, DeviceListActivityAuto.class);
//	    				mainActivity.startActivityForResult(serverIntent, 1);
//	    		   }
//	    	   }   	   
//	       }.start(); 
		  
	       IntentFilter filter = new IntentFilter();
	       filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED); //bluetooth狀態改變事件
	       registerReceiver(mReceiver, filter);
	       
		   /*
	        // Get local Bluetooth adapter
	        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

	        // 蓝牙是否可用
	        if (mBluetoothAdapter == null) {
	            Toast.makeText(this, "蓝牙不可用", Toast.LENGTH_LONG).show();
	            finish();
	            return;
	        }
	        */
			 /*
			 //old
			 mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			 
			boolean isBluetoothAvailable = commond.checkBluetooth();
			if(!isBluetoothAvailable){
				Toast.makeText(this, "蓝牙不可用", Toast.LENGTH_LONG).show();
	            finish();
	            return;
			}
			*/
		  
		  //View lock = View.inflate(this, R.layout.main, null);  
	        //LockLayer lockLayer = new LockLayer(this);  
	        //lockLayer.setLockView(lock);  
	        //lockLayer.lock();  
	}
	
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        final String action = intent.getAction();
	        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED))  //收到bluetooth狀態改變
	        {
	        	//蓝牙状态
//	        	int STATE_OFF = 10;  
//	        	int STATE_TURNING_ON = 11;  
//	        	int STATE_ON = 12;  
//	        	int STATE_TURNING_OFF = 13;  
	        	
	        	int currentState  = _bluetooth.getState();
	        	Log.e("蓝牙状态", ""+currentState);
	        	if(currentState==12){//开
	        		Log.e(TAG, "蓝牙开");
	        		logs.setLogs(getApplication().getString(R.string.bluetoothisopened));
	        		if(waitting.wattingActivity!=null)waitting.wattingActivity.onDestroy();
					commond.isLastTask = true;
    				Intent serverIntent = new Intent(mainActivity, DeviceListActivityAuto.class);
    				mainActivity.startActivityForResult(serverIntent, 1);
	        	}
	        	else if(currentState==10){//关
	        		Log.e(TAG, "蓝牙关");
	        		_bluetooth.enable();
	        	}
	        }
	    } 
	};
	
	
	public static void startSearchDevice(){
		//Intent intentaa = new Intent(commond.activeContext,Progress.class) ;
		//context.startActivity(intentaa);
		commond.stopKeepService();
		
		//关闭io跟数据流
		if(commond.closeSocket()){
			commond.isLastTask = true;
			
		new AlertDialog.Builder(mainActivity)
		.setMessage(context.getString(R.string.dilog_searchtype))
		 
		.setNegativeButton(context.getString(R.string.dilog_searchtype_auto),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						/*
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								Intent serverIntent = new Intent(mainActivity, DeviceListActivityAuto.class);
								//Intent serverIntent = new Intent(main.this, DeviceListActivity.class);
								mainActivity.startActivityForResult(serverIntent, 1);
					        	
							}
						}, 10000);
						*/
						Intent serverIntent = new Intent(mainActivity, DeviceListActivityAuto.class);
						mainActivity.startActivityForResult(serverIntent, 1);
					}
				})
		.setPositiveButton(context.getString(R.string.dilog_searchtype_manual),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog,
							int whichButton) {
						Intent serverIntent = new Intent(mainActivity, DeviceListActivity.class);
						mainActivity.startActivityForResult(serverIntent, 1);
					}
				}).show();
		
		
		}
	}
	
	public static void startDeviceListActivityAuto(){
		Intent serverIntent = new Intent(mainActivity, DeviceListActivityAuto.class);
		mainActivity.startActivityForResult(serverIntent, 1);
	}
	
	public static  void dialog() {
		Mark.initStatic();
		new AlertDialog.Builder(commond.activeContext)
		.setMessage(commond.activeContext.getString(R.string.dilog_isdownload))
		/*
		.setNegativeButton(context.getString(R.string.dilog_nextdownload),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
					}
				})
		*/
		.setCancelable(false)
		.setPositiveButton(commond.activeContext.getString(R.string.dilog_ok),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog,
							int whichButton) {
						InitMark.start = true;//开始写数据倒内存
						//写入队列
	    				for(int a=0;a<commond.taskIIArray.length;a++){
	    					//Log.e(TAG, "f:"+commond.taskIIArray[a]);
	    					String f= commond.taskIIArray[a];
	    					int caxunId = Integer.parseInt(f);
	    					commond.writeOs(commond.caxun(caxunId));
	    				}
	    				dialog.dismiss();
//	    				context.startProgress();
	    				commond.writeOsNext();
	    				if(commond.D)Log.e(TAG, "keep2");
	    				commond.startKeepService();
	    				Intent intentaa = new Intent(commond.activeContext,Progress.class) ;
	    				commond.activeContext.startActivity(intentaa);
					}
				}).show();
	}
	
	@Override
    public void onStart() {
        super.onStart();
        if(commond.D) Log.e(TAG, "++ ON START ++");
        
        
        /*
        //蓝牙是否启用---old
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) {
            	setupChat();
            	win();
            }
        }*/
    }
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		
		if(commond.D) Log.e(TAG, "++ ON Destroy ++");
		_bluetooth.disable();
		commond.stopKeepService();
		
		int nPid = android.os.Process.myPid();
	    android.os.Process.killProcess(nPid);
	    finish();
	};
	
	
	@Override
    public synchronized void onResume() {
        super.onResume();
        commond.activeContext = main.mainActivity;
        if(commond.D) Log.e(TAG, "+ ON RESUME +");
    }
	
	//接收活动结果，响应startActivityForResult()
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch(requestCode){
    	case REQUEST_CONNECT_DEVICE:     //连接结果，由DeviceListActivity设置返回
    		// 响应返回结果
            if (resultCode == Activity.RESULT_OK) {   //连接成功，由DeviceListActivity设置返回
                // MAC地址，由DeviceListActivity设置返回
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // 得到蓝牙设备句柄      
                _device = _bluetooth.getRemoteDevice(address);
                if(commond.D)Log.e(TAG, "接收句柄："+_device);
                
                /*
                // 用服务号得到socket
                try{
                	_socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
                }catch(IOException e){
                	Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                }
                //连接socket
            	//Button btn = (Button) findViewById(R.id.Button03);
                try{
                	_socket.connect();
                	commond.conntectedName = _device.getName();
                	connectedDeviceName.setText(_device.getName());
                	Toast.makeText(this, "连接"+_device.getName()+"成功！", Toast.LENGTH_SHORT).show();
                	//btn.setText("断开");
                }catch(IOException e){
                	try{
                		Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                		_socket.close();
                		_socket = null;
                	}catch(IOException ee){
                		Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                	}
                	
                	return;
                }
                */
              
      		  
              if(commond.startConnectDevice(newReadThread())){
            	 //读取任务文件
          		  if(commond.isLastTask)commond.readTXT(commond.taskName,getBaseContext(),true);
              };
                
                
            }
    		break;
    	default:break;
    	}
    }
	
  //消息处理队列
   public static Handler handler= new Handler(){
    	public void handleMessage(Message msg){
    		//Log.e(TAG, "xxx:"+msg);
    		super.handleMessage(msg);
    		if(smsg.equals(cmsg)){
    			return;
    		}
    		if(cmsg=="")cmsg=smsg;
    		cmsg=smsg;
    		
    		//Toast.makeText(getBaseContext(), "接收数据:"+smsg, Toast.LENGTH_SHORT).show();
    		//String[] smsgArray = smsg.split("#");
			//if(smsgArray[smsgArray.length-1].length()<=1){return;}
			int matchlen = smsg.length();
			if(matchlen<=1)return;
			
			int matchportS = smsg.indexOf("$");//匹配开始位
			if(matchportS<0)return;
			
			int matchportE = smsg.indexOf("*");//匹配结束位
			if(matchportE<0)return;
			
			int matchportEE = smsg.indexOf("#");//匹配结束位
			if(matchportEE<0)return;
			
			if(matchportS>matchportEE){//开始位大于结束位
				if(commond.D)Log.e(TAG, "smsg1:"+smsg);
				smsg = smsg.replace("#", "|");
				if(commond.D)Log.e(TAG, "smsg2:"+smsg);
				matchportE = smsg.indexOf("#");
				
				//if(D)Log.e(TAG, "matchportE:"+matchportE);
				//if(D)Log.e(TAG, "指令不全，废弃：matchportE:"+matchportE);
				if(matchportE<0)return;
			}
			bRun = false;
			//if(D)Log.e(TAG, "matchportE:"+matchportE+"|matchportS:"+matchportS+"|matchlen:"+matchlen);
			if(commond.D)Log.e(TAG, "smsg3:"+smsg);
    		
			String matchstr = smsg.substring(matchportS+1,matchportE);
			//Online.reallyTimer = false;
			if(commond.instruction.size()>0)commond.instruction.remove(0);
			bluetoothIntent.setAction(commond.packageName);
			bluetoothIntent.putExtra("readMessage",matchstr);
			mainActivity.sendBroadcast(bluetoothIntent);
            
            String rubbishstr = smsg.substring(matchportS,matchportEE+1);
            //Log.e(TAG, "rubbishstr:"+rubbishstr);
			smsg=smsg.replace(rubbishstr, "");//丢弃已执行的部分
		    Log.e(TAG, "smsg4:"+smsg);
			
			//数据残留处理
			if(matchstr.equals("CLOK")){
				int matchnext = smsg.indexOf("$");//匹配结束位
				if(matchnext<0){
					smsg="";
				}				
			}
			
			
    	}
    };
    
    private Thread newReadThread(){
    	//接收数据线程
	Thread ReadThread=new Thread(){
	
	  @Override 
    	public void run(){
    		int num = 0;
    		//String a = String.valueOf((int)(Math.random() * 10+1)) ;
    		byte[] buffer = new byte[1024];
    		byte[] buffer_new = new byte[1024];
    		int i = 0;
    		int n = 0;
    		bRun = true;
    		//Log.e(TAG, "1111111111111111111111111111111");
    		//接收线程
    		while(true){
    			//if(true){
    			try{
    				if(!commond.isConntected)return;
    				while(is.available()==0){
    					if(!commond.isConntected)return;
    				}
    				while(true){
    					if(!commond.isConntected)return;
    					try{
    						num = is.read(buffer);         //读入数据
    					}
    					catch(Exception e){
    						break;
    					}
    					n=0;
    					String s0 = "";
    					try{
    					  s0 = new String(buffer,0,num);
    					}
    					catch(Exception e){
    						continue;
    					}
    					fmsg+=s0;    //保存收到数据
    					//Log.e(TAG, "fmsg:"+fmsg);
    					for(i=0;i<num;i++){
    						if((buffer[i] == 0x0d)&&(buffer[i+1]==0x0a)){
    							buffer_new[n] = 0x23;
    							i++;
    						}else{
    							buffer_new[n] = buffer[i];
    						}
    						n++;
    					}
    					String s = new String(buffer_new,0,n);
    					if(InitMark.start){
    						smsg+=s;   //写入接收缓存
    					}

						handler.sendMessage(handler.obtainMessage());

    					if(is.available()==0){
    						break;
    					}
    					//发送显示消息，进行显示刷新
    					//handler.sendMessage(handler.obtainMessage());
    				}
    				    	    		
    	    		}catch(IOException e){
    	    			
    	    			//this.destroy();
    	    			bRun = false;
    	    			if(commond.D)Log.e(TAG, "disconnected", e);
    	    			commond.isConntected = false;
    	    			commond.connectionLost();
    	    		}
    			}
    			//Looper.loop();
    		}

    	//}
    };
    return ReadThread;
    }
    
    
   
	
    @Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键 
	    	
	    	new AlertDialog.Builder(context)
			.setMessage(getApplication().getString(R.string.dilog_exit))
			.setNegativeButton(getApplication().getString(R.string.dilog_no),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
						}
					})

			.setPositiveButton(getApplication().getString(R.string.dilog_yes),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,
								int whichButton) {
							onDestroy();
			        		
						}
					}).show();
	    	return true; 
	    }
	    return super.onKeyDown(keyCode, event); 
	}
    
}
