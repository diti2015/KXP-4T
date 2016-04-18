package com.example.android.BluetoothChat;

import com.liqin.instrument.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Setting extends Activity {
	private static final String TAG = "setting";
    
	private LinearLayout onlineTest;
	private Switch threshold;
	private LinearLayout setting_layout;
	private EditText mvd;
	private EditText mvv;
	private EditText gvd;
	private EditText gvv;
	private EditText mdd;
	private EditText mdv;
	//public static ImageView devicelamp = null;
	private ImageView logbtn = null;
	public static Setting SettingActivity = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(commond.isBlockHome)this.getWindow().setFlags(main.FLAG_HOMEKEY_DISPATCHED, main.FLAG_HOMEKEY_DISPATCHED);
		
		commond.activeContext = this;
		SettingActivity = this;
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.setting);
		logbtn = (ImageView)findViewById(R.id.logs);

		onlineTest = (LinearLayout)findViewById(R.id.onlineTest);
		threshold = (Switch)findViewById(R.id.threshold);
		setting_layout = (LinearLayout)findViewById(R.id.settingLayout);
		mvd = (EditText)findViewById(R.id.MVD);
		mvv = (EditText)findViewById(R.id.MVV);
		gvd = (EditText)findViewById(R.id.GVD);
		gvv = (EditText)findViewById(R.id.GVV);
		mdd = (EditText)findViewById(R.id.MDD);
		mdv = (EditText)findViewById(R.id.MDV);

		mvd.setText(""+commond.ql.get(0).getMvd());
		mvv.setText(""+commond.ql.get(0).getMvv());
		gvd.setText(""+commond.ql.get(0).getGvd());
		gvv.setText(""+commond.ql.get(0).getGvv());
		mdd.setText(""+commond.ql.get(0).getMdd());
		mdv.setText(""+commond.ql.get(0).getMdv());
		
//		logbtn.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(Setting.this,logs.class) ;
//				startActivity(intent);
//			}
//
//		});
		
		threshold.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
            	if(arg1){
            		setting_layout.setVisibility(0);
            	}
            	else {
            		setting_layout.setVisibility(8);
            	}
            }
        });
		
		if(commond.ql.get(0).getThreshold()==1){
			threshold.setChecked(true);
		}
		else {
			setting_layout.setVisibility(8);
		}
		
		onlineTest.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(main._socket==null||!commond.isConntected){
					commond.setToast(getApplication().getString(R.string.noconnted));
					return;
				}
				InitMark.start = true;//开始写数据倒内存
				Intent intent = new Intent(Setting.this,Online.class) ;
				startActivity(intent);
			}
			
		});

	}
	
	@Override
    public synchronized void onPause() {
		updateConfig();
        super.onPause();
        if(commond.D) Log.e(TAG, "-Setting ON PAUSE -");
    }
	
	@Override
	protected void onDestroy()
	{
		updateConfig();
		SettingActivity = null;
		super.onDestroy();
		finish();
	};
	
	@Override
    public synchronized void onResume() {
        super.onResume();
        commond.activeContext = this;
        if(commond.D) Log.e(TAG, "+ ON RESUME +");
    }
	
	public void updateConfig(){
		String res = "";
		String thresholdValue = "";
		if(threshold.isChecked()){
			thresholdValue = "1";
		}else {
			thresholdValue = "0";
		}
		res += "@"+commond.ConfigMode+"@"+"\n";
		res += "threshold=" + thresholdValue + ";\n";
		res += "mvd=" + mvd.getText()+ ";\n";
		res += "mvv=" + mvv.getText()+ ";\n";
		res += "gvd=" + gvd.getText()+ ";\n";
		res += "gvv=" + gvv.getText()+ ";\n";
		res += "mdd=" + mdd.getText()+ ";\n";
		res += "mdv=" + mdv.getText()+ ";";
		commond.writeFile(commond.configName, res, getBaseContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
