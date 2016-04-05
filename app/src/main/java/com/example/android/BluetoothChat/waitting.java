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


import com.liqin.instrument.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This Activity appears as a dialog. It lists any paired devices and devices
 * detected in the area after discovery. When a device is chosen by the user,
 * the MAC address of the device is sent back to the parent Activity in the
 * result Intent.
 */
public class waitting extends Activity
{
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
		return false;
	}

	// Debugging
	private final String TAG = "progress";
	public static waitting  wattingActivity=null;
	public static TextView deviceConnectLabel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		if(commond.D)Log.e(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.waitting);
		wattingActivity = this;
		deviceConnectLabel = (TextView)findViewById(R.id.deviceConnectLabel);
		
		
//		Handler handler=new Handler();
//		Runnable runnable=new Runnable() {
//		    @Override
//		    public void run() {
//		        // TODO Auto-generated method stub
//		    	//自动搜索设备
//		    	wattingActivity.finish();
//				commond.isLastTask = true;
//				main.startDeviceListActivityAuto();
//		    }
//		};
//		handler.postDelayed(runnable, 5000);//每两秒执行一次runnable.
	}
	
	public void setTitle(String title){
		deviceConnectLabel.setText(title);
	}
	
	@Override
    public void onStart() {
        super.onStart();
        
    }
	
	@Override
	protected void onDestroy()
	{
		if(commond.D)Log.e(TAG, "onDestroy");
		super.onDestroy();
		wattingActivity = null;
		finish();
	}
	
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键 
	        return true; 
	    }
	    return super.onKeyDown(keyCode, event); 
	}
	
}
