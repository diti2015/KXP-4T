package com.example.android.BluetoothChat;


import com.liqin.instrument.R;

import android.app.Service;  
import android.content.Intent;  
import android.os.IBinder;  
import android.util.Log;

public class KeepService extends Service  {
	private static final String TAG = "service";
    
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override  
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(commond.D)Log.e(TAG, "发送广播:"+commond.retryNum);
		
		if(commond.reallyTimer){
			if(commond.isConntected){
				if(commond.instruction.size()>1&&commond.SelfTest){
					if(commond.D)Log.e(TAG, ""+commond.instruction.get(0));
					commond.instruction.remove(0);
				}
				commond.retryNum = commond.retryNum + 1;//设备没有抛出异常，就计算重复次数，超过两次手动断开连接
			}
			else {
				commond.stopKeepService();
			}
			if(commond.retryNum>2){//超过两次改变灯颜色
				commond.connectionLost();
				commond.setDeviceType(3);
				
			}
			else {
				commond.writeOsNext();
			}
		}
        if(!commond.reallyTimer){
        	commond.reallyTimer = true;
        }
        
		return START_NOT_STICKY;
    }  
	
}
