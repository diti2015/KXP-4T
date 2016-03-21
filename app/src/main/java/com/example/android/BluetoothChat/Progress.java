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
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * This Activity appears as a dialog. It lists any paired devices and devices
 * detected in the area after discovery. When a device is chosen by the user,
 * the MAC address of the device is sent back to the parent Activity in the
 * result Intent.
 */
public class Progress extends Activity
{
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
		return false;
	}

	// Debugging
	private final String TAG = "progress";
	public static ProgressBar bar;
	public static boolean isFinished = false;
	public static Progress progressActivity = null;
	public static ImageView devicelamp = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		if(commond.D)Log.e(TAG, "onCreate");
		progressActivity=this;
		//commond.activeContext = this;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress);
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		
		devicelamp = (ImageView)findViewById(R.id.deviceLamp);
		commond.devicelamp = devicelamp;

		commond.setDeviceType(0);
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
		commond.devicelamp = null;
		progressActivity = null;
		commond.instruction.clear();
		finish();
	}
	
	public static void setProgressBar(int status){
		if(status==100){
			isFinished = true;
		}
		bar.setProgress(status);
	}
	
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键 
	    	new AlertDialog.Builder(this)
			.setMessage(getApplication().getString(R.string.dilog_download_giveup))
			.setNegativeButton(getApplication().getString(R.string.dilog_cancel),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
						}
					})

			.setPositiveButton(getApplication().getString(R.string.dilog_ok),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,
								int whichButton) {
							commond.stopKeepService();
							Progress.progressActivity.onDestroy();
    						if(Mark.markActivity!=null)Mark.markActivity.onDestroy();
    						Mark.initStatic();//必须放在这里
						}
					}).show();
	        return true; 
	    }
	    return super.onKeyDown(keyCode, event); 
	}
	
}
