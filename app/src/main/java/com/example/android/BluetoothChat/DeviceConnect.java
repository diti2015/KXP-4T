package com.example.android.BluetoothChat;

import com.liqin.instrument.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.TextView;



public class DeviceConnect extends Activity
{
	public static DeviceConnect DeviceConnectActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		DeviceConnectActivity = this;
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.device_connect);
		TextView devicel = (TextView) findViewById(R.id.deviceConnectLabel);
		devicel.setText(this.getString(R.string.device_connecting));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
		return false;
	}
	
	
}
