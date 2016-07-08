package com.example.android.BluetoothChat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.liqin.instrument.R;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

public class Mark extends Activity {
	private static final String TAG = "mark";
	
	private static TextView markCountDown;
	private static TextView markCount;
	private static TextView markStatus;
//	private Switch markdirection;
	private LinearLayout markend;
	//private static EditText intervalDepth;
	//private LinearLayout markretest;
	private static RelativeLayout mark;
	private static ListView marklist=null;
	
	static Handler handler=new Handler();
	private static int nextCount = 0;
	private static int setCount = 1;
	public static int recordCount = 0;
	private static int recordTime = 0;
	private static int showCount;
//	private static int intervalDepthCount;
//	private static boolean isUpDown = true;//true上false下
	private static boolean initTime = true;
	public static String MarkId ="序号#";
	public static String MarkTime ="记录时间#";
	public static String MarkDepth ="深度#";
	public static String MarkOrientation ="方位#";
	public static String MarkDip ="倾角#";
	private static String lastDepthValue = "0";
	private static int MarkLen = 0;
	private static int NPInterval = 0;
	private static int NPTime = 0;//分钟转换秒
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
		
		NPInterval = Integer.parseInt(commond.NPInterval)*60;//间隔时间
		NPTime = Integer.parseInt(commond.NPTime)*60;//初始延迟时间



		markCountDown = (TextView) findViewById(R.id.markCountDown);
		markCount = (TextView) findViewById(R.id.markCount);
		markStatus = (TextView) findViewById(R.id.textViewMakrStatus);
//		markdirection = (Switch) findViewById(R.id.markdirection);
		markend = (LinearLayout) findViewById(R.id.markend);
//		markretest = (LinearLayout) findViewById(R.id.markretest);
		mark = (RelativeLayout) findViewById(R.id.mark);
//		marklist = (ListView) findViewById(R.id.marklist);
//		intervalDepth = (EditText) findViewById(R.id.intervalDepth);
//		intervalDepth.addTextChangedListener(watcher);

		recordCount = 0;
		recordTime = NPTime;
		markCountDown.setText(""+NPTime/60+"分钟" + NPTime%60 + "秒");
		markStatus.setText(R.string.MarkStatusEnd);
		markCount.setText(""+recordCount);

//		nextCount = NPDTime;

//		logss = (ImageView)findViewById(R.id.logs);
//
//		logss.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(Mark.this,logs.class) ;
//				startActivity(intent);
//			}
//
//		});
//		mark.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
////				if(intervalDepth.getText().length()==0){
////					intervalDepth.setText("0");
////				}
////
//				if(initTime){
//					commond.setToast(markActivity.getString(R.string.logs_nodevice));
//					return;
//				}
//
//				if(isThisPortMark){
//					if(isShowToast){
//						commond.setToast(markActivity.getString(R.string.dilog_mark_already));
//						isShowToast = false;
//						new Timer().schedule(new TimerTask() {
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								isShowToast = true;
//							}
//						}, 3000);
//					}
//				}
//				else {
//					isThisPortMark = true;
//				//	defListview(true);
//					Resources resources = getBaseContext().getResources();
//					Drawable btnDrawable = resources.getDrawable(R.drawable.red_mark_button);
//					mark.setBackgroundDrawable(btnDrawable);
//				}
//			}
//		});
		
//		markretest.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				if(!isReset & !isThisPortMark) {
//					setTime(false);
//					isReset = true;
//				//	changeDepth(null);
//				}
//			}
//		});
//
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

									if(recordCount < 1) {
										commond.setToast(getApplication().getString(R.string.dilog_mark_empty));
										onDestroy();
										return;
									}
									else {
										if(commond.D) Log.e(TAG,"查询：00");
										commond.writeOs(commond.caxun(0));
									}
									
									//MarkTime
									String tt = recordCount+"";
									MarkLen = 1;//减第一个标题
									
//									if(MarkLen<1){
//										commond.setToast(getApplication().getString(R.string.dilog_mark_empty));
//										onDestroy();
//										return;
//									}
									
									commond.writeTask();//写入task日志
									
									
									commond.setToast(getApplication().getString(R.string.dilog_mark_not_download));
	//								int c =  Integer.parseInt(tt.toString())/NPInterval;
	//								if(commond.D)Log.e(TAG, "查询："+commond.caxun(c));
	//								commond.writeOs(commond.caxun(c));

	//								commond.writeOsNext();
									commond.startKeepService();
									
									Mark.markActivity.onDestroy();
								}
							}).show();
				}
				else {
					commond.setToast(markActivity.getString(R.string.dilog_markend_not_ok));
				}
				
			}
		});
//		markdirection.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				// TODO Auto-generated method stub
//				if(isChecked){
//					isUpDown = true;
//				}
//				else {
//					isUpDown = false;
//				}
//				changeDepth(null);
//			}
//		});
		
		
//		defListview(false);
		

			Resources resources = markActivity.getResources();
			if(NPTime > 180) {
				Drawable btnDrawable = resources.getDrawable(R.drawable.blue_mark_button);
				markStatus.setText(R.string.MarkStatusEnd);
				mark.setBackground(btnDrawable);
			}
			else if(NPTime == 180) {
				Drawable btnDrawable = resources.getDrawable(R.drawable.yellow_mark_button);
				markStatus.setText(R.string.MarkStatusReady);
				mark.setBackground(btnDrawable);
			}

		
		//打开计时器
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
        	//	changeDepth(s);
        }  
          
    };

//    private void defListview(boolean isMark){
//    	String defMarkId;
//    	String defMarkTime;
//    	String defMarkDepth;
//
//    	if(isMark){
//	    	 defMarkId = MarkId+"-#";
//	    	 defMarkTime = MarkTime+"-#";
//	    	 defMarkDepth = MarkDepth+"-#";
//    	}
//    	else {
//    		 defMarkId = MarkId;
//	    	 defMarkTime = MarkTime;
//	    	 defMarkDepth = MarkDepth;
//    	}
//    	String[] id = defMarkId.split("#");
//		String[] time = defMarkTime.split("#");
//		String[] depth = defMarkDepth.split("#");
//
//
//		List<Map<String,Object>> listItems =
//				new ArrayList<Map<String, Object>>();
//
//		int listItemsLen =  id.length;
////		marklist.removeAllViewsInLayout();
//
//		for(int i=0;i<listItemsLen;i++){
//			Map<String,Object> listItem = new HashMap<String,Object>();
//			listItem.put("markId",id[i]);
//			listItem.put("markTime",time[i]);
//			listItem.put("markDepth",depth[i]);
//			listItems.add(listItem);
//		}
//		SimpleAdapter adapter1 = new SimpleAdapter(markActivity, listItems, R.layout.array_item,
//				new String[] {"markId","markTime","markDepth"},
//				new int[]{R.id.markItemId,R.id.markItemTime,R.id.markItemDepth});
//
//		marklist.setAdapter(adapter1);
//		marklist.setSelection(marklist.getAdapter().getCount()-1);
//    }

//    private void changeDepth(CharSequence s){
//    	if(recordCount<1){
//    		return;
//    	}
//    	if(s==null){
//    		s= intervalDepth.getText();
//    		if(s.length()==0)s="0";
//    	}
//    	int newDepth;
//		int lastDepth = Integer.parseInt(lastDepthValue);
//		int changeDepth = Integer.parseInt(s.toString());
//		if(isUpDown){
//			newDepth = lastDepth + changeDepth;
//        }
//        else {
//        	newDepth = lastDepth - changeDepth;
//        }
//        if(newDepth<0)newDepth=0;
//        markdepth.setText(""+newDepth);
//    }
    
    
//	private static void setTime(boolean isAdd){
//		//判断记录点是否允许记录
//		if(!reallyMark&&isAdd){
//			commond.setToast(markActivity.getString(R.string.dilog_mark_already));
//			return;
//		}
//		if(initTime){
//			commond.setToast(markActivity.getString(R.string.logs_nodevice));
//			return;
//		}
//
//		if(isAdd){
//			reallyMark = false;
//			int retime = loopCount * NPInterval;
////			MarkId += ((recordCount+1)+"#");
////			MarkTime += (retime+"#");
////			MarkDepth += (markdepth.getText()+"#");
//			lastDepthValue = ""+markCount.getText();
//		}
//		else {
////			if(commond.D)Log.e(TAG, MarkId);
////			if(recordCount>1){
////				MarkId = MarkId.substring(0, MarkId.lastIndexOf("#"));
////				MarkId = MarkId.substring(0, MarkId.lastIndexOf("#")+1);
////				MarkTime = MarkTime.substring(0, MarkTime.lastIndexOf("#"));
////				MarkTime = MarkTime.substring(0, MarkTime.lastIndexOf("#")+1);
////				MarkDepth = MarkDepth.substring(0, MarkDepth.lastIndexOf("#"));
////				MarkDepth = MarkDepth.substring(0, MarkDepth.lastIndexOf("#")+1);
////				if(commond.D)Log.e(TAG, MarkId);
////			}
////			else if(recordCount==1) {
////				MarkId ="序号#";
////				MarkTime ="记录时间#";
////				MarkDepth ="深度#";
////			}
////			else {
////				commond.setToast(markActivity.getString(R.string.dilog_mark_nomark));
////				return;
////			}
//		}
//
////		String[] id = MarkId.split("#");
////		String[] time = MarkTime.split("#");
////		String[] depth = MarkDepth.split("#");
//
//
//		List<Map<String,Object>> listItems =
//				new ArrayList<Map<String, Object>>();
//
////		int listItemsLen =  id.length;
////		if(!isAdd){
////			if(listItemsLen==0){
////				marklist.removeAllViewsInLayout();
////			}
////			else if(listItemsLen<0){
////				return;
////			}
////		}
////		for(int i=0;i<listItemsLen;i++){
////			Map<String,Object> listItem = new HashMap<String,Object>();
////			listItem.put("markId",id[i]);
////			listItem.put("markTime",time[i]);
////			listItem.put("markDepth",depth[i]);
////			listItems.add(listItem);
////			if(i==listItemsLen-1&&!isAdd){
////				lastDepthValue = depth[i];
////			}
////		}
//		SimpleAdapter adapter1 = new SimpleAdapter(markActivity, listItems, R.layout.array_item,
//				new String[] {"markId","markTime","markDepth"},
//				new int[]{R.id.markItemId,R.id.markItemTime,R.id.markItemDepth});
//
//		marklist.setAdapter(adapter1);
//		marklist.setSelection(marklist.getAdapter().getCount()-1);
//
//		if(isAdd){
//			recordCount++;
//			isReset = false;
//	//		markdepth.setText(""+setDepth());
//		}
//		else {
//			recordCount--;
//			reallyMark=true;
//		}
//	}
//
	public static void initStatic(){
		commond.instruction.clear();//清除队列
		markActivity = null;
		nextCount = 0;
		setCount = 0;
		recordCount = 0;
		recordTime = 0;
		showCount = 0;
	//	intervalDepthCount = 0;
	//	isUpDown = true;//true上，false下
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
		super.onDestroy();
		finish();
	}
	
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
	
//	private static int setDepth(){
//
//		String intervalDepthVal = intervalDepth.getText().toString();
//		if(intervalDepthVal.length()==0){
//			intervalDepthVal = "0";
//		}
//
//    	intervalDepthCount = Integer.parseInt(intervalDepthVal);
//        int getDepth = Integer.parseInt(markdepth.getText().toString());
//        if(isUpDown){
//        	getDepth += intervalDepthCount;
//        }
//        else {
//        	getDepth -= intervalDepthCount;
//        }
//        if(getDepth<0)getDepth=0;
//        return getDepth;
//	}
	
	static Runnable runnable=new Runnable() {
	    @Override
	    public void run() {
	        // TODO Auto-generated method stub
	        //设置倒计时
			recordTime = recordTime - 1;

			if(recordTime == 180){
				Resources resources = markActivity.getResources();
				Drawable btnDrawable = resources.getDrawable(R.drawable.yellow_mark_button);
				markStatus.setText(R.string.MarkStatusReady);
				mark.setBackground(btnDrawable);
			}

			if(recordTime == 120){
				Resources resources = markActivity.getResources();
				Drawable btnDrawable = resources.getDrawable(R.drawable.red_mark_button);
				markStatus.setText(R.string.MarkStatusIng);
				mark.setBackground(btnDrawable);
			}

			if(recordTime == 0){
				recordCount++;
					recordTime = NPInterval;
				Resources resources = markActivity.getResources();
				if(recordTime > 180) {
					Drawable btnDrawable = resources.getDrawable(R.drawable.blue_mark_button);
					markStatus.setText(R.string.MarkStatusEnd);
					mark.setBackground(btnDrawable);
				}
				else if(recordTime == 180) {
					Drawable btnDrawable = resources.getDrawable(R.drawable.yellow_mark_button);
					markStatus.setText(R.string.MarkStatusReady);
					mark.setBackground(btnDrawable);
				}
			}

		//	markCountDown.setText(""+recordTime);
			markCountDown.setText(""+recordTime/60+"分钟" + recordTime%60 + "秒");
			markCount.setText(""+recordCount);

	        handler.postDelayed(this, 1000);

//	        showCount = nextCount-1;
//	        if(showCount==0||showCount<0){
//	        	if(initTime){
//	        		Resources resources = markActivity.getResources();
//					Drawable btnDrawable = resources.getDrawable(R.drawable.blue_mark_button);
//					mark.setBackground(btnDrawable);
//	        		initTime=false;
//	        	}
//	        	else {
//
//	        		if(isThisPortMark){
//	        			setTime(true);
//	        			isThisPortMark = false;
//	        		}
//
//		        	//设置深度
//		        	//markdepth.setText(""+setDepth());
//	        		loopCount++;
//	        	}
//	        	testTimer = 0;
//	        	showCount = NPInterval;
//	        	//允许下一次mark
//	        	reallyMark = true;
//
//	        	Resources resources = markActivity.getResources();
//				Drawable btnDrawable = resources.getDrawable(R.drawable.blue_mark_button);
//				mark.setBackground(btnDrawable);
//
//	        }
//	        if(!initTime)setCount++;
//        	nextCount = showCount;
//	        markCountDown.setText(""+showCount);
//	        testTimer++;
//	        //Log.e(TAG, ""+testTimer+"显示时间"+showCount);
//	        handler.postDelayed(this, 1000);
	    }
	};
	
//	public void writeTask(){
//		String res ="";
//
//	}
	
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
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



			if(info[0].equals("HWC")){
				commond.retryNum = 0;//重置重复次数
				commond.stopKeepService();
				commond.reallyTimer = false;

//				MarkOrientation +=  info[2] +"#";
//				MarkDip += info[1] +"#";
				MarkOrientation +=  info[3] +"#";
				MarkDip += info[2] +"#";

				if(commond.lastCount!=0){
					commond.lastRecCount++;
			//		MarkTime += (Integer.parseInt(commond.NPInterval)*commond.lastRecCount) + "#";
			//		MarkDepth += commond.taskDepthArray[commond.lastRecCount-1] + "#";
					MarkTime += "0" + "#";
					MarkDepth += "0" + "#";
				}

				int MarkOrientationLen = MarkOrientation.split("#").length;
				//int MarkIdLen = MarkId.split("#").length;
				int MarkIdLen = Integer.parseInt(info[1]);
		//		if(MarkIdLen == 0) {
		//			MarkOrientation =  info[3] +"#";
		//			MarkDip = info[2] +"#";
		//		}

				if(commond.D)Log.e(TAG, "MarkOrientationLen:"+MarkOrientationLen+"|MarkIdLen:"+MarkIdLen+"|lastCount需要加1:"+commond.lastCount);
				//if(MarkOrientationLen==MarkIdLen||MarkOrientationLen==commond.lastCount+1&&commond.lastCount!=0){
				if(MarkOrientationLen==commond.lastCount+1&&commond.lastCount!=0){
					Progress.setProgressBar(100);
					String[] MarkTimeArray = MarkTime.split("#");
					String[] MarkDepthArray = MarkDepth.split("#");
					String[] MarkOrientationArray = MarkOrientation.split("#");
					String[] MarkDipArray = MarkDip.split("#");
					//写入文件
					String res ="";
					//if(MarkIdLen==1) MarkIdLen = commond.lastCount+1;
					MarkIdLen = commond.lastCount+1;

					for(int a=1;a<MarkIdLen;a++){
						res += MarkTimeArray[a]+"#";
						res += MarkDepthArray[a]+"#";
						res += MarkDipArray[a]+"#";
						res += MarkOrientationArray[a]+"#";
					}
					if(commond.D)Log.e(TAG,"res:"+res);
					try {
						RWfile.saveToSDCard(commond.NPName+".txt", res);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//
					//diti
					commond.doTask("del", "");
					commond.lastCount = 0;

					//下一个界面

					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Progress.progressActivity.finish();
							if(markActivity!=null)Mark.markActivity.onDestroy();
						}
					}, 1000);
					return;
				}
				else {

					if(recordCount==0) recordCount = commond.lastCount;
					if(commond.D)Log.e(TAG, "0:"+recordCount);
					double price =  100 / Double.parseDouble(""+recordCount);
					if(commond.D)Log.e(TAG, "1:"+price);
					int num = MarkOrientationLen-1;
					if(commond.D)Log.e(TAG, "2:"+num);
					double progressStats = price * num;
					if(commond.D)Log.e(TAG, "3:"+progressStats);
					int progressStatsVal = Integer.valueOf((int) Math.round( progressStats));
					if(commond.D)Log.e(TAG, "进度条："+progressStatsVal);
					if(progressStatsVal==-1){
						initStatic();
						return;
					}
					Progress.setProgressBar(progressStatsVal);

					if(commond.D)Log.e(TAG, "keep3");
					commond.startKeepService();
				}

				commond.writeOsNext();
			}

		}
	}
	
	
}
