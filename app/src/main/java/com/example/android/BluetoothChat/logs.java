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


import java.text.SimpleDateFormat;

import com.liqin.instrument.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This Activity appears as a dialog. It lists any paired devices and devices
 * detected in the area after discovery. When a device is chosen by the user,
 * the MAC address of the device is sent back to the parent Activity in the
 * result Intent.
 */
public class logs extends Activity
{
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
		return false;
	}

	// Debugging
	private final static String TAG = "logs";
	private final static boolean D = true;
	public static logs logActivity;
	private static TextView dis;       //接收数据显示句柄
    private static ScrollView sv;      //翻页句柄

    public static String log = "";
    public static boolean isLogActive = false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		if(commond.D)Log.e(TAG, "onCreate");
		//logActivity=this;
		//commond.activeContext = this;
		
		super.onCreate(savedInstanceState);
		
		if(commond.isBlockHome)this.getWindow().setFlags(main.FLAG_HOMEKEY_DISPATCHED, main.FLAG_HOMEKEY_DISPATCHED);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.logs);
		
		sv = (ScrollView)findViewById(R.id.logScrollView);  //得到翻页句柄
        dis = (TextView) findViewById(R.id.login);      //得到数据显示句柄
        
        isLogActive = true;
		setLogs("");
	}
	
	public static void setLogs(String logcache){
		if(!logcache.equals("")){
			SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("hh:mm:ss");     
		    String   date   =   sDateFormat.format(new   java.util.Date());  
		    logcache = date + "　　" + logcache + "\n";
		    //if(D)Log.e(TAG, logcache);
			log += logcache;
		}
		
		if(isLogActive){
			dis.setText(log);   //显示数据 
			sv.scrollTo(0,dis.getMeasuredHeight()); //跳至数据最后一页
		}
		
	}
	
	@Override
    public void onStart() {
        super.onStart();
        
    }
	
	@Override
	protected void onDestroy()
	{
		isLogActive = false;
		if(commond.D)Log.e(TAG, "onDestroy");
		super.onDestroy();
		finish();
	}
	
	/*
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键 
	        return true; 
	    }
	    return super.onKeyDown(keyCode, event); 
	}
	*/
	
}
