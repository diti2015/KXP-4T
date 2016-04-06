package com.example.android.BluetoothChat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.liqin.instrument.R;
import com.throne.digtool.FileInfo;
import com.throne.digtool.MainActivity.FileComparator;

import android.R.string;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;

public class commond {
	//是否开启HOME键屏蔽
    public static boolean isBlockHome = false;
    //是否开启日志
    public static final boolean D = true;
    
    
	// Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    
    private static final String TAG = "commond";
   
    
    public static List<Question> ql;
    
    public static String taskName = "task.txt";
    public static String configName = "config.txt";
    public static String packageName = "com.example.android.BluetoothChat";
    
    public static String conntectedName = "";
    public static String ConfigMode = null;
    
    public static Intent blueIntent = new Intent();
    public static Activity activeContext = null;
    
	
    public static ArrayList<String> instruction = new ArrayList<String>(0);
    public static String OUTHZ = "$OUTHZ*  ";
    public static String ZLDJ = "$ZLDJ*  ";
    public static String ZLFW = "$ZLFW*  ";
    public static String FWDJ = "$FWDJ*  ";
    public static String FWFW = "$FWFW*  ";
    public static boolean SelfTest = false;
    
    public static String NPName = "";
    public static String NPDTime = "0";
    public static String NPDepth = "0";
    public static String NPInterval = "0";
    public static boolean isConntected = false;
    
    static Thread CommondReadThread = null;
    static int reConnect = 1;
    public static boolean reallyTimer = false;
   

    public static String[] taskIIArray;//第几次的数组
    public static String[] taskDepthArray;//第几次的数组
    public static boolean isLastTask = false;
    public static int lastCount = 0;//taskIIArray的个数,关键性判断依据
    public static int lastRecCount = 0;//第几次获取数据
    public static String taskCont = "";
    
    public static ImageView devicelamp = null;
    public static int devicelampType = 1;
    public static boolean isStartConnect = false;
	
    public static AlarmManager alarmM;
    public static boolean isAlarMStart = false;
    public static PendingIntent pi = null;
    public static int retryNum = 0;
    private static Boolean hasReboot = false;
    
    public static void startKeepService(){
    	//if(D)Log.e(TAG, "keepservice start");
    	if(!isAlarMStart){
    		isAlarMStart = true;
    		long now = System.currentTimeMillis()+3000;
    		alarmM.setInexactRepeating(AlarmManager.RTC_WAKEUP, now, 3000 , pi);
    	}
    }
    
    public static void stopKeepService(){
    	//if(D)Log.e(TAG, "keepservice stop");
    	if(pi!=null&&isAlarMStart)alarmM.cancel(pi);
    	isAlarMStart = false;
    }
    
    
    public static void writeFile(String filename,String rws,Context context){
    	try {
			RWfile.writeFile(filename, rws, context);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    public static String readFile(String filename,Context context){
    	String res="";
    	try {
    		res = RWfile.readFile(filename, context);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return res;
    }
    
    
    public static boolean readTXT(String filename,Context context,Boolean isShow){
    	String config = readFile(filename,context);

		if(D) Log.e("TAG", "读到的配置:"+config);
    	if(config.equals("")){
    		//假设读不到则从assets取内容
    		config = RWfile.read(filename,context); 
    	}
    	else {
    		
    	}
    	
    	if(filename.equals(configName)){
	    	config = config.replace("\n","");
	    	//Log.e("TAG", "!!!!!!"+config);
	    	String[] configArray = config.split("@");
	    	ConfigMode = configArray[1];
	    	String[] postArgs = new String[]{configArray[1].replace("@", ""),configArray[2]};
	    	try {
				setConfigValue(postArgs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return true;
    	}
    	else if(filename.equals(taskName)){
    		try{
	    		taskCont = config;
	    		isLastTask = false;
	    		if(config.length()==0)return true;
	    		String[] taskArray = config.split("#");
	    		boolean hasSameDevice = false;
	    		for(int i=0;i<taskArray.length;i++){
	    			//taskII 0:设备名   1:时间间隔   2:文件名
	    			//taskIArray  0:taskII  1:间隔次数     2:间隔深度
	    			
	    			String[] taskIArray = taskArray[i].split("&");
	    			String[] taskII = taskIArray[0].split(",");
	    			
	    			
	    			NPName = taskII[2];
	    			NPInterval = taskII[1];
	    			
	    			if(taskII[0].equals(main._device.getName())){
	    				hasSameDevice = true;
	    				isLastTask = true;
	    				taskIIArray = null;
	    				Log.e(TAG,"1:"+taskIArray[1]);
	    				Log.e(TAG,"2:"+taskIArray[1]);
	    				
	    				taskIIArray = taskIArray[1].split(",");
	    				taskDepthArray = taskIArray[2].split(",");
	    				
	    				lastCount = taskIIArray.length;
	    				if(isShow)main.dialog();
	    				return false;
	    			}
	    			
	    		}
	    		if(!hasSameDevice){
	    			lastCount = 0;
	    		}
    		}
    		catch (Exception e) {
    			if(D)Log.e(TAG, "读取任务文件出错");
    			logs.setLogs("读取任务文件出错,删除任务文件内容");
    			commond.setToast("读取任务文件出错,删除任务文件内容");
    			commond.writeFile(commond.taskName, "", activeContext);
    		}
    	}
		return true;
    }
    
    public static void doTask(String string,String string2){
    	String newTaskCont = "";
    	if(string.equals("del")){
	    	String[] taskArray = taskCont.split("#");
	    	
	    	for(int i=0;i<taskArray.length;i++){
	    		//taskII 0:设备名   1:时间间隔   2:文件名
				//taskIArray  0:taskII  1:间隔次数     2:间隔深度
				String[] taskIArray = taskArray[i].split("&");
				String[] taskII = taskIArray[0].split(",");
				if(taskII[0].equals(main._device.getName())){
					continue;
				}
				else {
					newTaskCont += taskArray[i]+"#";
				}
			}
	    	newTaskCont = "";
    	}
    	if(string.equals("add")){
    		newTaskCont += string2;
    	}
    	commond.writeFile(commond.taskName, newTaskCont, activeContext);
    	//commond.writeFile(commond.taskName, "", activeContext);
    }
    
    public static void writeOs(String instruct){
    	instruction.add(instruct);
    }
    
    public static void writeOsNext(){
    	if(D)Log.e(TAG, "isConntected:"+isConntected);
    	if(!isConntected){
    		
    		return;
    	}
    	
    	if (D)Log.e(TAG, "size:"+instruction.size());
    	if(instruction.size()!=0){
    		/*
    		if(instruction.isEmpty()){
    			if(SelfTest){
    				instruction.add(OUTHZ);
    			}
    			else {
    				return;
    			}
    		}
    		*/
        	String message = instruction.get(0);
	    	byte[] bos = message.getBytes();
	    	byte[] bos_new = new byte[bos.length+2];
	    	int n=0;
	    	for(int i=0;i<bos.length;i++){ //手机中换行为0a,将其改为0d 0a后再发送
	    		bos_new[n]=bos[i];
				n++;
			}
	    	bos_new[n]=0x0d;
			n++;
			bos_new[n]=0x0a;
			
	        //锟斤拷锟酵碉拷main锟斤拷锟竭筹拷
			OutputStream os;
			
			try {
				main.bRun = true;
				if(main._socket==null){
					logs.setLogs(activeContext.getString(R.string.noconnted));
					//Toast.makeText(activeContext, "设备还未连接！！", Toast.LENGTH_SHORT).show();
					return;
				}
				os = main._socket.getOutputStream();
				os.write(bos_new);
				if (D)Log.e(TAG, instruction.get(0)+":发送成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logs.setLogs("写入指令失败，断开连接");
				connectionLost();
				e.printStackTrace();
			}
    	}
    	else {
    		
    		if(SelfTest){
    			writeOs(OUTHZ);
    			writeOsNext();
    		}
    		else {
    			if(D)Log.e(TAG, "队列已执行完");
    		}
    		
    	}
    }
    
    public static void writeTask(){
    	String[] MarkTime = null;
    	String[] MarkDepth = null;
    	MarkTime = Mark.MarkTime.split("#");
    	MarkDepth = Mark.MarkDepth.split("#");
    	
    	String r = "";
		String deviceName = main._device.getName();
		int NPInterval = Integer.parseInt(commond.NPInterval);
		
		String MarkTimeN = "";
		
		for(int i=1;i<MarkTime.length;i++){
			int c =  Integer.parseInt(MarkTime[i].toString())/NPInterval;
			if(i==MarkTime.length-1){
				MarkTimeN += ""+c;
			}
			else {
				MarkTimeN += c +",";
			}
		};
		
		String MarkDepthN = "";
		
		for(int ii=1;ii<MarkDepth.length;ii++){
			if(ii==MarkDepth.length-1){
				MarkDepthN += ""+MarkDepth[ii];
			}
			else {
				MarkDepthN += MarkDepth[ii] +",";
			}
		};
		
		r = deviceName + "," + 
				commond.NPInterval + "," +
				commond.NPName + "&" +
				MarkTimeN +"&"+
				MarkDepthN + "#";
		Log.e(TAG, r);
		
		int checkLen1 = r.split("&").length;
		int checkLen2 = r.split(",").length;
		if(checkLen1==3&&checkLen2>2){
			commond.doTask("add", r);
		}
		else {
			commond.setToast(r);
			//Toast.makeText(activeContext, r , Toast.LENGTH_LONG).show();
		}
    }
    

	public static boolean checkFile(){
		String[] filename = null;
		 File file = new File("/mnt/sdcard/dragtool");
		 if(file.isDirectory()){
	    	  ArrayList<FileInfo> fileInfoList = new ArrayList<FileInfo>();
	    	  
	           File [] fileArray = file.listFiles();
	           Log.e(TAG, "文件目录个数:"+fileArray.length);
	           if(null != fileArray && 0 != fileArray.length){
	        	   int filesize = 0;
	        	   for(int i = 0; i < fileArray.length; i++){
	                	String filenameVal =fileArray[i].getName();
	                	//Log.e(TAG, filenameVal);
	                	//Log.e(TAG, ""+filenameVal.indexOf(".txt"));
	                	if(filenameVal.indexOf(".txt")!=-1){
		                	filesize++;
	                	}
	                }
	                //判断文件时.txt的个数
	                if(filesize!=0){
	                	return true;
	                }
	                else {
	                	commond.setToast(activeContext.getString(R.string.dilog_empty));
	                	return false;
	                }
	           }
	  		   else {
	  			 commond.setToast(activeContext.getString(R.string.dilog_empty));
	  			 return false;
	  		 }
	     }
	     return false;
	}
    
    public static String[] returnInfo(String info){
    	//if(D)Log.e(TAG, "==="+info);
    	/*
    	int pointIndex2 = info.indexOf("$");
    	int pointIndex = info.indexOf("*");
    	if(pointIndex2<0||pointIndex<0){
    		return new String[]{"","","",""};
    	}
    	//if(D)Log.e(TAG, "port:"+pointIndex);
    	info = info.substring(pointIndex2, pointIndex);
    	//if(D)Log.e(TAG, info);
    	//String[] infoArray = info.split("*");
    	//info = infoArray[0];
    	String[] infoArray = info.split(",");
    	*/
    	String[] infoArray = info.split(",");
    	return infoArray;
    }
    
    public static void setConfigValue(String[] args) throws Exception {  
        ql = buildT(Question.class, args);  
        //System.err.println();
        //Log.e("TAG", "!!!!!!"+ql.get(0).getThreshold());
        //THRESHOLD = ql.get(0).getThreshold();
        //MVD  = ql.get(0).getMvd();
        //MVV  = ql.get(0).getMvv();
        //GVD  = ql.get(0).getGvd();
        //GVV  = ql.get(0).getGvv();
        //MDD  = ql.get(0).getMdd();
        //MDV  = ql.get(0).getMdv();
    }  
      
    public static String celiang(String delaytime,String intervaltime){
    	String re = "$CELIANG,"+delaytime+","+intervaltime+"*  ";
    	if(D)Log.e(TAG, re);
		return re;
    }
    
    public static String caxun(int time){
    	String re = "$CAXUN,"+time+"*  ";
    	if(D)Log.e(TAG, re);
		return re;
    }
    
    
	public static boolean closeSocket(){
		Log.e(TAG, "closeSocket1");
		commond.isConntected = false;
		main.connectedDeviceName.setText("");
		
	//	if(hasReboot){
//			//main.onKeyDown(KeyEvent.KEYCODE_POWER,KeyEvent);
//			new AlertDialog.Builder(activeContext)
//			.setMessage("手持设备遇到故障，您可能需要重启设备！")
//			.setNegativeButton(activeContext.getString(R.string.dilog_yes),
//					new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialog,
//								int which) {
//						}
//					})
//			.setPositiveButton(activeContext.getString(R.string.dilog_no),
//				new DialogInterface.OnClickListener() {
//
//					public void onClick(DialogInterface dialog,
//							int whichButton) {
//						hasReboot = false;
//					}
//				}).show();
	//		return false;
	//	}
		
		
		//回收线程
		if(commond.CommondReadThread!=null){
			main.handler.removeCallbacks(commond.CommondReadThread);
			commond.CommondReadThread = null;
			main.bThread=false;
		}
		
		//清理蓝牙内存
		InitMark.start = false;
		main.smsg = "";
		main.cmsg = "";
		
		
		try {
			Log.e(TAG, "closeSocket:"+main._socket);
			if(main._socket!=null){
				Log.e(TAG, "2:"+main.is);
				if(main.is!=null)main.is.close();
//				Log.e(TAG, "isConnected:"+main._socket.isConnected());
				if(main._socket.isConnected()){
	    	    	main._socket.close();
	    	    	main._socket = null;
				}
    	    	main.bRun = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "3:xxxxxxxxxxxxxxx");
			hasReboot = false;//以前是true，用于拦截下次的点击,现在直接重启蓝牙
			logs.setLogs(activeContext.getString(R.string.socket_error));
			Intent serverIntent = new Intent(main.mainActivity, waitting.class);
    		main.mainActivity.startActivity(serverIntent);
			main._bluetooth.disable();
			return false;
		}
		
		return true;
	}
    
    
    public static void checkOutDir(){
    	File destDir;
    	 try {
    		 if(D)Log.e(TAG, "0000000:"+Environment.getExternalStorageDirectory().getCanonicalFile());
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        try {
			destDir = new File(Environment
			        .getExternalStorageDirectory()
			        .getCanonicalFile() + "/dragtool");
			if (!destDir.exists()) {
				logs.setLogs(activeContext.getString(R.string.creatfolder));
				//Toast.makeText(activeContext, "创建文件夹",, Toast.LENGTH_SHORT).show();
	            destDir.mkdirs();
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logs.setLogs(activeContext.getString(R.string.creatfailed));
			//Toast.makeText(activeContext, "创建文件夹失败!!!!", Toast.LENGTH_SHORT).show();
		}
    }
    
    public static void setToast(String ToastString){
    	
    	Toast toast = new Toast(activeContext.getBaseContext());
    	TextView view = new TextView(activeContext.getBaseContext());
    	view.setTextSize(30);
    	view.setText(ToastString);
    	view.setTextColor(Color.parseColor("#ffffff"));
    	view.setBackgroundColor(Color.parseColor("#000000"));
    	view.setPadding(20, 10, 20, 10);
        toast.setView(view);
        toast.show();
        
        
    }
    
    
    
    public static void setDeviceType(int Type){
    	//0不变，1未连接，2在线，3不在线
    	if(Type==0){
    		setDeviceType(devicelampType);
    	}
    	else if(Type==1){
    		devicelampType = 1;
    		if(devicelamp!=null)devicelamp.setImageResource(R.drawable.lamp_red);
    	}
    	else if(Type==2){
    		devicelampType = 2;
    		if(devicelamp!=null)devicelamp.setImageResource(R.drawable.lamp_green);
    	}
    	else if(Type==3){
    		devicelampType = 3;
    		if(devicelamp!=null)devicelamp.setImageResource(R.drawable.lamp_yellow);
    	}
    }
    
    public static Bitmap readMainBg(){
    	//checkOutDir();
    	Bitmap bitmap = null;
    	try {
			bitmap = getLoacalBitmap(Environment
			        .getExternalStorageDirectory()
			        .getCanonicalFile() + "/dragtool/main_bg.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return bitmap;
    }
    
    public static Bitmap getLoacalBitmap(String url) {
        try {
             FileInputStream fis = new FileInputStream(url);
             return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片       

          } catch (FileNotFoundException e) {
             e.printStackTrace();
             return null;
        }
   }
    
    public static <T> List<T> buildT(Class clazz, String[] sr) throws Exception {  
        List<T> list = new ArrayList<T>();  
        String[] sa = sr[0].split(",");  
        String s = null;  
        Map<Integer, String> map = new HashMap<Integer, String>();  
        for(int i = 0, len = sa.length; i < len; i++){  
            s = sa[i];  
            String[] _sa = s.split("#");
            //Log.e("TAG", i+":"+_sa[1]);
            map.put(i, _sa[1]);  
        }  
        for(int i = 1, len = sr.length; i < len; i++){  
            s = sr[i];  
            String[] _sa = s.split(";");  
            T t = (T) clazz.newInstance();  
              
            for(int j = 0; j < _sa.length; j++){
            	//Log.e("TAG", j+"::"+map.get(j));
            	String[] cof = _sa[j].split("=");
            	int cofvalue = Integer.parseInt(cof[1]);
                Field fi = clazz.getDeclaredField(map.get(j));  
                fi.setAccessible(true);
                //Log.e("TAG", j+"::"+cof[1]);
                fi.set(t, cofvalue);
            }  
            list.add(t);  
        }  
        return list;  
    }
    
    public static Boolean checkSetting(String type,int value){
    	int typeValue = 0;
    	JSONObject jsonText = new JSONObject();
    	try {
			jsonText.put("aaa", 1);
			jsonText.put("bbb", 2);
	    	jsonText.put("ccc", 3);
	    	typeValue = (Integer) jsonText.get("aaa");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	switch(typeValue){
    		case 1:
    			;
    		case 2:
    			;
    		case 3:
    			;
    		
    	}
		return true;
    }
    
    public static boolean startConnectDevice(Thread readThread){
    	CommondReadThread = readThread;
    	//用服务号得到socket
        try{
        	logs.setLogs(activeContext.getString(R.string.conntecting)+main._device.getName());
        	if(D)Log.e(TAG, "这里会报错？"+main._device);
        	
        	if(main._device==null){
        		if(D)Log.e(TAG, "device是null，退出");
        		return false;
        	}
        	main._socket = main._device.createRfcommSocketToServiceRecord(UUID.fromString(main.MY_UUID));
        }catch(IOException e){
        	if(devicelampType!=1)setDeviceType(3);
        	logs.setLogs(activeContext.getString(R.string.socketfailed));
        	//Toast.makeText(activeContext, "连接失败1", Toast.LENGTH_SHORT).show();
        	return false;
        }
        //连接socket
    	//Button btn = (Button) findViewById(R.id.Button03);
        try{
        	stopKeepService();
        	main._socket.connect();
        	commond.conntectedName = main._device.getName();
        	//connectedDeviceName.setText(_device.getName());
        	setDeviceType(2);
        	commond.setToast( "连接"+main._device.getName()+"成功！");
        	if(D)Log.e(TAG, "连接"+main._device.getName()+"成功！");
        	//Toast.makeText(activeContext, "连接"+main._device.getName()+"成功！", Toast.LENGTH_SHORT).show();
        	main.conntedprogress.setVisibility(View.GONE);
        	main.connectedDeviceName.setText(main._device.getName());
        	main.bRun = true;
    		commond.isConntected = true;
    		retryNum = 0;
    		logs.setLogs( "连接"+main._device.getName()+"成功！");
        	commond.writeOsNext();
    		
        }catch(IOException e){
        	if(D)Log.e(TAG, "keep1");
        	startKeepService();
        	if(Mark.markActivity!=null){
        		Mark.markActivity.onDestroy();
        		logs.setLogs(activeContext.getString(R.string.dilog_mark_not_download_faild));
        		setToast(activeContext.getString(R.string.dilog_mark_not_download_faild));
        	}
        		//if(devicelampType!=1)setDeviceType(3);
        		//String errors = "系统将会在"+(10*reConnect)+"秒后重连;
        		String errors = activeContext.getString(R.string.systemistrys)
        				+(10*reConnect)
        				+ activeContext.getString(R.string.systemistrye);
        		
        		//logs.setLogs(activeContext.getString(R.string.conntectedfailed));
        		//logs.setLogs(errors);
        		
        		//Toast.makeText(activeContext, "连接失败2！"+errors, Toast.LENGTH_SHORT).show();
        		
        		//logs.setLogs(activeContext.getString(R.string.conntectingfailed));
        		//stopKeepService();
        		//if(D)Log.e(TAG, "socket连接故障，自动重连关闭，请手动尝试连接");
        	
        	return false;
        }
        
      //打开接收线程
        try{
        	main.is = main._socket.getInputStream();   //得到蓝牙数据输入流
		}catch(IOException e){
			logs.setLogs(activeContext.getString(R.string.dataflowfailed));
			//Toast.makeText(activeContext, "接收数据失败！, Toast.LENGTH_SHORT).show();
			return false;
		}
        Log.e("sssssssssssssssss", ""+main.bThread);	
        if(main.bThread==false){
        	if(D)Log.e(TAG, "readThread start");
			readThread.start();
			main.bThread=true;
		}else{
			//bRun = true;
		}
        
        return true;
    }
    
    public static void connectionLost(){
    	main.bRun = false;
		main.bThread=false;
		
		//回收线程
		if(commond.CommondReadThread!=null){
			main.handler.removeCallbacks(commond.CommondReadThread);
			commond.CommondReadThread = null;
		}
		//清理蓝牙内存
		InitMark.start = false;//不再写数据倒内存
		main.smsg = "";
		main.cmsg = "";
		
    	logs.setLogs(activeContext.getString(R.string.conntedclose));
    	if(D)Log.e(TAG, "设备连接已断开");
		//closeSocket();
		//Toast.makeText(activeContext, "设备连接已断开", Toast.LENGTH_SHORT).show();
		//main._socket.close();
		commond.isConntected = false;
		commond.retryNum=0;
		reallyTimer = false;
		main.connectedDeviceName.setText("");
		
		stopKeepService();
		if(Progress.progressActivity!=null)Progress.progressActivity.onDestroy();
		
		//commond.setToast("设备已断开");
		
//		AlertDialog dialog = new AlertDialog.Builder(activeContext)
//		.setMessage(activeContext.getString(R.string.dilog_device_reconect))
//		.setNegativeButton(activeContext.getString(R.string.dilog_ok),
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog,
//							int which) {
//						//销毁各个层
//						if(Mark.markActivity!=null){
//							Mark.markActivity.onDestroy();
//						}
//						if(InitMark.InitMarkActivity!=null){
//							InitMark.InitMarkActivity.onDestroy();
//						}
//						if(Online.OnlineActivity!=null){
//							Setting.SettingActivity.onDestroy();
//							Online.OnlineActivity.onDestroy();
//						}
//						setToast(activeContext.getString(R.string.dilog_mark_not_download));
//					}
//				}).show();
//		 dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失 
//		 dialog.setOnKeyListener(
//				 new DialogInterface.OnKeyListener() {
//					   @Override
//					   public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event){
//					   if (keyCode == KeyEvent.KEYCODE_BACK){
//					     return true;
//					    }
//					return true;
//					   }
//				 });
		 
/*
		.setPositiveButton(activeContext.getString(R.string.dilog_yes),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog,
							int whichButton) {
						//销毁各个层
						if(Mark.markActivity!=null){
							Mark.markActivity.onDestroy();
						}
						if(InitMark.InitMarkActivity!=null){
							InitMark.InitMarkActivity.onDestroy();
						}
						if(Online.OnlineActivity!=null){
							Setting.SettingActivity.onDestroy();
							Online.OnlineActivity.onDestroy();
						}
						setToast(activeContext.getString(R.string.dilog_mark_not_download));
						main.startSearchDevice();
					}
				}).show();*/
    }
    
}
