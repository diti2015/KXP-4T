package com.example.android.BluetoothChat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.liqin.instrument.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Mark extends Activity {
	private static final String TAG = "mark";
	
	private static TextView markCountDown;
	private static TextView markdepth;
	private Switch markdirection;
	private LinearLayout markend;
	private static EditText intervalDepth;
	private LinearLayout markretest;
	private static RelativeLayout mark;
	private static ListView marklist=null;
	
	static Handler handler=new Handler();
	private static int nextCount = 0;
	private static int setCount = 1;
	public static int recordCount = 0;
	private static int recordTime = 0;
	private static int showCount;
	private static int intervalDepthCount;
	private static boolean isUpDown = true;//true???false??
	private static boolean initTime = true;
//	public static String MarkId ="???#";
//	public static String MarkTime ="??????#";
//	public static String MarkDepth ="???#";
//	public static String MarkOrientation ="??λ#";
//	public static String MarkDip ="???#";
//	private static String lastDepthValue = "0";
	public static String MarkId ="序号#";
	public static String MarkTime ="记录时间#";
	public static String MarkDepth ="深度#";
	public static String MarkOrientation ="方位#";
	public static String MarkDip ="倾角#";
	private static String lastDepthValue = "0";
	private static int MarkLen = 0;
	private static int NPInterval = 0;
	private int NPDTime = 0;//?????????
	private static boolean  reallyMark = true;
	private static boolean isReset = false;
	//private ScrollView sv;
	private static int testTimer = 0;
	private static int loopCount = 1;
	
	public static Mark markActivity = null;
	//private ImageView devicelamp = null;
	private ImageView logss = null;
	
	private static boolean isThisPortMark = false;
	//private boolean isThisPortMark = false;
	private boolean isShowToast = true;
	private boolean isFinishedMark = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		markActivity = this;
		commond.activeContext = this;
		
		super.onCreate(savedInstanceState);
		
		if(commond.isBlockHome)this.getWindow().setFlags(main.FLAG_HOMEKEY_DISPATCHED, main.FLAG_HOMEKEY_DISPATCHED);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.mark);
		
		NPInterval = Integer.parseInt(commond.NPInterval);
		NPDTime = Integer.parseInt(commond.NPDTime)*60;//?????????
		
		//д??????????
		//commond.writeOs(commond.celiang(commond.NPDTime, commond.NPInterval));
		//commond.writeOsNext();
		
		markCountDown = (TextView) findViewById(R.id.markCountDown);
		markdepth = (TextView) findViewById(R.id.markdepth);
		markdirection = (Switch) findViewById(R.id.markdirection);
		markend = (LinearLayout) findViewById(R.id.markend);
		markretest = (LinearLayout) findViewById(R.id.markretest);
		mark = (RelativeLayout) findViewById(R.id.mark);
		marklist = (ListView) findViewById(R.id.marklist);
		intervalDepth = (EditText) findViewById(R.id.intervalDepth);
		//sv = (ScrollView)findViewById(R.id.ScrollView01);  //?????????
		intervalDepth.addTextChangedListener(watcher);
		
		markCountDown.setText(""+NPDTime);
		//intervalDepth.setText("12");
		markdepth.setText(commond.NPDepth);
		nextCount = NPDTime;
		
		//Log.e(TAG, "@@@"+commond.NPDepth);
		//devicelamp = (ImageView)findViewById(R.id.deviceLamp);
		logss = (ImageView)findViewById(R.id.logs);
		//commond.devicelamp = devicelamp;
		
		logss.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Mark.this,logs.class) ;
				startActivity(intent);
			}
			
		});
		mark.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(intervalDepth.getText().length()==0){
					intervalDepth.setText("0");
				}
				
				if(initTime){
					commond.setToast(markActivity.getString(R.string.logs_nodevice));
					//Toast.makeText(markActivity, "?豸??δ?????", Toast.LENGTH_LONG).show();
					return;
				}
				
				if(isThisPortMark){
					if(isShowToast){
						commond.setToast(markActivity.getString(R.string.dilog_mark_already));
						isShowToast = false;
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								isShowToast = true;
					        	//Log.e(TAG, "````````");
							}
						}, 3000);
					}

					//commond.setToast(markActivity.getString(R.string.dilog_mark_already));
						//Toast.makeText(markActivity, "???????????", Toast.LENGTH_LONG).show();
				}
				else {
					isThisPortMark = true;
					defListview(true);
					Resources resources = getBaseContext().getResources();   
					Drawable btnDrawable = resources.getDrawable(R.drawable.red_mark_button);  
					mark.setBackgroundDrawable(btnDrawable);
				}
				//setTime(true);
			}
		});
		
		markretest.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				/*
				initStatic();
				marklist.removeAllViewsInLayout();
				List<Map<String,Object>> listItems =
						new ArrayList<Map<String, Object>>();
				Map<String,Object> listItem = new HashMap<String,Object>();
				listItem.put("markId","");
				listItem.put("markTime","");
				listItem.put("markDepth","");
				listItems.add(listItem);
				SimpleAdapter adapter1 = new SimpleAdapter(getApplicationContext(), listItems, R.layout.array_item,
						new String[] {"markId","markTime","markDepth"}, 
						new int[]{R.id.markItemId,R.id.markItemTime,R.id.markItemDepth});
				marklist.setAdapter(adapter1);
				*/
				if(isReset){
					//Toast.makeText(getBaseContext(), "??????????????", Toast.LENGTH_LONG).show();
				}
				else {
					if(isThisPortMark){
						//Toast.makeText(getBaseContext(), "??????????????", Toast.LENGTH_LONG).show();
					}
					else {
						setTime(false);
						isReset = true;
						changeDepth(null);
					}
					
				}
			}
		});
		
		markend.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!isThisPortMark){
					new AlertDialog.Builder(markActivity)
					.setMessage(markActivity.getString(R.string.dilog_mark_exit))
					.setNegativeButton(markActivity.getString(R.string.dilog_mark_back),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							})
	
					.setPositiveButton(markActivity.getString(R.string.dilog_mark_end),
							new DialogInterface.OnClickListener() {
	
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Progress.isFinished = false;
									handler.removeCallbacks(runnable);
									
									//MarkTime
									String[] tt = MarkTime.split("#");
									MarkLen = tt.length - 1;//???????????
									
									if(MarkLen<1){
										commond.setToast(getApplication().getString(R.string.dilog_mark_empty));
										//Toast.makeText(getBaseContext(), "?????б????", Toast.LENGTH_LONG).show();
										onDestroy();
										return;
									}
									
									commond.writeTask();//д??task???
									
									
									commond.setToast(getApplication().getString(R.string.dilog_mark_not_download));
									//Toast.makeText(getBaseContext(), "??????????????????豸", Toast.LENGTH_LONG).show();
									for(int a = 1;a<tt.length;a++){
										int c =  Integer.parseInt(tt[a].toString())/NPInterval;
										if(commond.D)Log.e(TAG, "?????"+commond.caxun(c));
										commond.writeOs(commond.caxun(c));
									}
									
									commond.writeOsNext();
									commond.startKeepService();
									
									Mark.markActivity.onDestroy();
									
//									if(commond.isConntected){
//										Intent intent = new Intent(Mark.this,Progress.class) ;
//										startActivity(intent);
//									}
								}
							}).show();
				}
				else {
					commond.setToast(markActivity.getString(R.string.dilog_markend_not_ok));
				}
				
			}
		});
		markdirection.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					isUpDown = true;
				}
				else {
					isUpDown = false;
				}
				changeDepth(null);
			}
		});
		
		
		defListview(false);
		
		if(NPDTime>0){
			Resources resources = markActivity.getResources();   
			Drawable btnDrawable = resources.getDrawable(R.drawable.red_mark_button);  
			mark.setBackgroundDrawable(btnDrawable);
		}
		
		//??????
		handler.post(runnable);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
		return false;
	}
	
	private TextWatcher watcher = new TextWatcher(){  
		  
        @Override  
        public void afterTextChanged(Editable s) {  
            // TODO Auto-generated method stub  
              
        }  
  
        @Override  
        public void beforeTextChanged(CharSequence s, int start, int count,  
                int after) {  
            // TODO Auto-generated method stub  
              
        }  
  
        @Override  
        public void onTextChanged(CharSequence s, int start, int before,  
                int count) {  
        		Log.d("TAG","[TextWatcher][onTextChanged]"+s);
        		Log.d("TAG","[TextWatcher][onTextChanged]"+lastDepthValue);
        		if(s.length()==0){
            		s = "0";
            	}
        		changeDepth(s);
        }  
          
    };
	
    private void defListview(boolean isMark){
    	String defMarkId;
    	String defMarkTime;
    	String defMarkDepth;
    	
    	if(isMark){
	    	 defMarkId = MarkId+"-#";
	    	 defMarkTime = MarkTime+"-#";
	    	 defMarkDepth = MarkDepth+"-#";
    	}
    	else {
    		 defMarkId = MarkId;
	    	 defMarkTime = MarkTime;
	    	 defMarkDepth = MarkDepth;
    	}
    	String[] id = defMarkId.split("#");
		String[] time = defMarkTime.split("#");
		String[] depth = defMarkDepth.split("#");
		
		
		List<Map<String,Object>> listItems =
				new ArrayList<Map<String, Object>>();
		
		int listItemsLen =  id.length;
		marklist.removeAllViewsInLayout();
		
		for(int i=0;i<listItemsLen;i++){
			Map<String,Object> listItem = new HashMap<String,Object>();
			listItem.put("markId",id[i]);
			listItem.put("markTime",time[i]);
			listItem.put("markDepth",depth[i]);
			listItems.add(listItem);
		}
		SimpleAdapter adapter1 = new SimpleAdapter(markActivity, listItems, R.layout.array_item,
				new String[] {"markId","markTime","markDepth"}, 
				new int[]{R.id.markItemId,R.id.markItemTime,R.id.markItemDepth});
		
		marklist.setAdapter(adapter1);
		marklist.setSelection(marklist.getAdapter().getCount()-1); 
    }
    
    private void changeDepth(CharSequence s){
    	if(recordCount<1){
    		return;
    	}
    	if(s==null){
    		s= intervalDepth.getText();
    		if(s.length()==0)s="0";
    	}
    	int newDepth;
		int lastDepth = Integer.parseInt(lastDepthValue);
		int changeDepth = Integer.parseInt(s.toString());
		if(isUpDown){
			newDepth = lastDepth + changeDepth;
        }
        else {
        	newDepth = lastDepth - changeDepth;
        }
        if(newDepth<0)newDepth=0;
        markdepth.setText(""+newDepth);
    }
    
    
	private static void setTime(boolean isAdd){
		//Log.e(TAG,"reallyMark:"+reallyMark);
		//Log.e(TAG,"isAdd:"+isAdd);
		//?ж??????????????
		if(!reallyMark&&isAdd){
			commond.setToast(markActivity.getString(R.string.dilog_mark_already));
			//Toast.makeText(markActivity, "???????????", Toast.LENGTH_LONG).show();
			return;
		}
		if(initTime){
			commond.setToast(markActivity.getString(R.string.logs_nodevice));
			//Toast.makeText(markActivity, "?豸??δ?????", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(isAdd){
			reallyMark = false;
			//int fixCount = (NPInterval - (setCount%NPInterval));
			//int retime = setCount + fixCount;
			int retime = loopCount * NPInterval;
			MarkId += ((recordCount+1)+"#");
			//MarkTime += (retime+"#");
			MarkTime += (retime+"#");
			MarkDepth += (markdepth.getText()+"#");
			lastDepthValue = ""+markdepth.getText();
		}
		else {
			if(commond.D)Log.e(TAG, MarkId);
			if(recordCount>1){
				MarkId = MarkId.substring(0, MarkId.lastIndexOf("#"));
				MarkId = MarkId.substring(0, MarkId.lastIndexOf("#")+1);
				MarkTime = MarkTime.substring(0, MarkTime.lastIndexOf("#"));
				MarkTime = MarkTime.substring(0, MarkTime.lastIndexOf("#")+1);
				MarkDepth = MarkDepth.substring(0, MarkDepth.lastIndexOf("#"));
				MarkDepth = MarkDepth.substring(0, MarkDepth.lastIndexOf("#")+1);
				if(commond.D)Log.e(TAG, MarkId);
			}
			else if(recordCount==1) {
				MarkId ="序号#";
				MarkTime ="记录时间#";
				MarkDepth ="深度#";
			}
			else {
				commond.setToast(markActivity.getString(R.string.dilog_mark_nomark));
				//Toast.makeText(markActivity, "????м????", Toast.LENGTH_LONG).show();
				return;
			}
		}
		
		String[] id = MarkId.split("#");
		String[] time = MarkTime.split("#");
		String[] depth = MarkDepth.split("#");
		
		
		List<Map<String,Object>> listItems =
				new ArrayList<Map<String, Object>>();
		
		int listItemsLen =  id.length;
		if(!isAdd){
			if(listItemsLen==0){
				marklist.removeAllViewsInLayout();
			}
			else if(listItemsLen<0){
				return;
			}
		}
		for(int i=0;i<listItemsLen;i++){
			Map<String,Object> listItem = new HashMap<String,Object>();
			listItem.put("markId",id[i]);
			listItem.put("markTime",time[i]);
			listItem.put("markDepth",depth[i]);
			listItems.add(listItem);
			if(i==listItemsLen-1&&!isAdd){
				lastDepthValue = depth[i];
			}
		}
		SimpleAdapter adapter1 = new SimpleAdapter(markActivity, listItems, R.layout.array_item,
				new String[] {"markId","markTime","markDepth"}, 
				new int[]{R.id.markItemId,R.id.markItemTime,R.id.markItemDepth});
		
		marklist.setAdapter(adapter1);
		marklist.setSelection(marklist.getAdapter().getCount()-1); 
		
		if(isAdd){
			recordCount++;
			//recordTime = retime;
			//reallyMark=false;
			isReset = false;
			markdepth.setText(""+setDepth());
			//marklist.scrollTo(0,marklist.getMeasuredHeight()); //?????????????
		}
		else {
			recordCount--;
			reallyMark=true;
			//markdepth.setText(""+lastDepthValue);
		}
	}
	
	public static void initStatic(){
		commond.instruction.clear();//???????
		markActivity = null;
		nextCount = 0;
		setCount = 0;
		recordCount = 0;
		recordTime = 0;
		showCount = 0;
		intervalDepthCount = 0;
		isUpDown = true;//true???false??
		initTime = true;
		MarkId ="序号#";
		MarkTime ="记录时间#";
		MarkDepth ="深度#";
		MarkOrientation ="方位#";
		MarkDip ="倾角#";
		MarkLen = 0;
		reallyMark = true;
		loopCount = 1;
		lastDepthValue="0";
		commond.lastRecCount = 0;
		isThisPortMark = false;
		if(marklist!=null)marklist.removeAllViewsInLayout();
		isReset = false;
		handler.removeCallbacks(runnable);
	}
	
	@Override
	protected void onDestroy()
	{
		
		Log.e(TAG, "onDestroy");
		initStatic();
		//commond.devicelamp = null;
		super.onDestroy();
		finish();
	};
	
	@Override
    public synchronized void onPause() {
		super.onPause();
		//handler.removeCallbacks(runnable);
    }
	
	@Override
    public synchronized void onResume() {
        super.onResume();
        commond.activeContext = this;
        if(commond.D) Log.e(TAG, "+ ON RESUME +");
    }
	
	private static int setDepth(){
		
		String intervalDepthVal = intervalDepth.getText().toString();
		if(intervalDepthVal.length()==0){
			intervalDepthVal = "0";
		}
		
    	intervalDepthCount = Integer.parseInt(intervalDepthVal);
        int getDepth = Integer.parseInt(markdepth.getText().toString());
        if(isUpDown){
        	getDepth += intervalDepthCount;
        }
        else {
        	getDepth -= intervalDepthCount;
        }
        if(getDepth<0)getDepth=0;
        return getDepth;
	}
	
	static Runnable runnable=new Runnable() {
	    @Override
	    public void run() {
	        // TODO Auto-generated method stub
	        //????????
	        showCount = nextCount-1;
	        if(showCount==0||showCount<0){
	        	if(initTime){
	        		Resources resources = markActivity.getResources();   
					Drawable btnDrawable = resources.getDrawable(R.drawable.blue_mark_button);  
					mark.setBackgroundDrawable(btnDrawable);
	        		initTime=false;
	        	}
	        	else {
	        		
	        		if(isThisPortMark){
	        			setTime(true);
	        			isThisPortMark = false;
	        		}
	        		
		        	//???????
		        	//markdepth.setText(""+setDepth());
	        		loopCount++;
	        	}
	        	testTimer = 0;
	        	showCount = NPInterval;
	        	//?????????mark
	        	reallyMark = true;
	        	
	        	Resources resources = markActivity.getResources();   
				Drawable btnDrawable = resources.getDrawable(R.drawable.blue_mark_button);  
				mark.setBackgroundDrawable(btnDrawable);   
	        	//mark.setBackgroundColor(R.drawable.blue_mark_button);
	        	
	        }
	        if(!initTime)setCount++;
        	nextCount = showCount;
	        markCountDown.setText(""+showCount);
	        //Log.e(TAG, ""+showCount);
	        //Log.e(TAG, ""+setCount);
	        //???????
	        
	        /*//?????
	        if(!initTime){
		        intervalDepthCount = Integer.parseInt(intervalDepth.getText().toString());
		        int getDepth = Integer.parseInt(markdepth.getText().toString());
		        if(isUpDown){
		        	getDepth += intervalDepthCount;
		        }
		        else {
		        	getDepth -= intervalDepthCount;
		        }
		        markdepth.setText(""+getDepth);
	        }
	        */
	        testTimer++;
	        //Log.e(TAG, ""+testTimer+"??????"+showCount);
	        handler.postDelayed(this, 1000);
	    }
	};
	
	public void writeTask(){
		String res ="";
		
	}
	
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //???/????/???η???? 
	    	new AlertDialog.Builder(this)
			.setMessage(getApplication().getString(R.string.dilog_mark_giveup))
			.setNegativeButton(getApplication().getString(R.string.dilog_mark_back),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
						}
					})

			.setPositiveButton(getApplication().getString(R.string.dilog_ok),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,
								int whichButton) {
							markActivity.onDestroy();
						}
					}).show();
	        return true; 
	    }   
	    return super.onKeyDown(keyCode, event); 
	}
	
	public static class Receiver extends BroadcastReceiver {  
        @Override  
        public void onReceive(Context context, Intent intent) {  
        	String content=intent.getStringExtra("readMessage"); 
        	String[] info = commond.returnInfo(content);
        	/*
        	if(info[0].equals("CLOK")){
        		Log.e(TAG, "?????????");
        		handler.post(runnable);
        	}
        	*/
        	if(info[0].equals("HHZ")){
        		commond.retryNum = 0;//???????????
        		commond.stopKeepService();
        		commond.reallyTimer = false;
        		
        		MarkOrientation +=  info[2] +"#";
        		MarkDip += info[1] +"#";
        		
        		if(commond.lastCount!=0){
        			commond.lastRecCount++;
        			MarkTime += (Integer.parseInt(commond.NPInterval)*commond.lastRecCount) + "#";
        			MarkDepth += commond.taskDepthArray[commond.lastRecCount-1] + "#";
        		}
        		
        		int MarkOrientationLen = MarkOrientation.split("#").length;
        		int MarkIdLen = MarkId.split("#").length;
        		
        		if(commond.D)Log.e(TAG, "MarkOrientationLen:"+MarkOrientationLen+"|MarkIdLen:"+MarkIdLen+"|lastCount?????1:"+commond.lastCount);
        		if(MarkOrientationLen==MarkIdLen||MarkOrientationLen==commond.lastCount+1&&commond.lastCount!=0){
        			Progress.setProgressBar(100);
        			String[] MarkTimeArray = MarkTime.split("#");
        			String[] MarkDepthArray = MarkDepth.split("#");
        			String[] MarkOrientationArray = MarkOrientation.split("#");
        			String[] MarkDipArray = MarkDip.split("#");
        			//д?????,д?????,д?????
        			String res ="";
        			//if(D)Log.e(TAG,"lastCount:"+commond.lastCount);
        			if(MarkIdLen==1) MarkIdLen = commond.lastCount+1;
        			//if(D)Log.e(TAG,"MarkIdLen:"+MarkIdLen);
        			
        			for(int a=1;a<MarkIdLen;a++){
        				res += MarkTimeArray[a]+"#";
        				res += MarkDepthArray[a]+"#";
        				res += MarkDipArray[a]+"#";
        				res += MarkOrientationArray[a]+"#";
        			}
        			if(commond.D)Log.e(TAG,"res:"+res);
        			//commond.writeFile(commond.NPName+".txt", res, context);
        			try {
						RWfile.saveToSDCard(commond.NPName+".txt", res);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			
        			//
        			commond.doTask("del", "");
        			commond.lastCount = 0;
        			
        			//???????
        			
        			new Timer().schedule(new TimerTask() {
    					@Override
    					public void run() {
    						// TODO Auto-generated method stub
    						Progress.progressActivity.finish();
    						if(markActivity!=null)Mark.markActivity.onDestroy();
    						//initStatic();
    					}
    				}, 1000);
        			return;
        		}
        		else {
        			
        			if(recordCount==0) recordCount = commond.lastCount;
        			if(commond.D)Log.e(TAG, "0:"+recordCount);
        			//logs.setLogs("recordCount:"+recordCount);
        			double price =  100 / Double.parseDouble(""+recordCount);
        			if(commond.D)Log.e(TAG, "1:"+price);
        			//logs.setLogs("price:"+price);
        			int num = MarkOrientationLen-1;
        			if(commond.D)Log.e(TAG, "2:"+num);
        			//logs.setLogs("num:"+num);
        			double progressStats = price * num;
        			if(commond.D)Log.e(TAG, "3:"+progressStats);
        			//logs.setLogs("progressStats"+progressStats);
        			int progressStatsVal = Integer.valueOf((int) Math.round( progressStats));
        			if(commond.D)Log.e(TAG, "??????:"+progressStatsVal);
        			//logs.setLogs("progressStatsVal"+progressStatsVal);
        			if(progressStatsVal==-1){
        				initStatic();
        				return;
        			};
        			Progress.setProgressBar(progressStatsVal);
        			
        			if(commond.D)Log.e(TAG, "keep3");
        			commond.startKeepService();
        			//commond.alarmM.setInexactRepeating(AlarmManager.RTC_WAKEUP, now, 3000, markpi);
        		}
        		
        		commond.writeOsNext();
        		
        		/*
        		new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
			        	commond.writeOsNext();
			        	
					}
				}, 1000);
				*/
        		
        	}
        	
        }  
	}
	
	
}
