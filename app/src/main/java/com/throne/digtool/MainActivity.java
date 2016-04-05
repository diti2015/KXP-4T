package com.throne.digtool;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.android.BluetoothChat.InitMark;
import com.example.android.BluetoothChat.logs;
import com.example.android.BluetoothChat.main;
import com.example.android.BluetoothChat.commond;
import com.liqin.instrument.R;



import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private LayoutInflater mInflater;
	private ListView lv;
	private LinearLayout xls;
	private LinearLayout td;
	private XYZVo xyz;
	private Intent intent;
	
	private Spinner spinner;
	 private ArrayAdapter<String> adapter;
	private String[] filename;
    public final class ViewHolder {  
        
    	public TextView TT;
        public TextView T1;  
        public TextView T2;  
        public TextView T3;  
        public TextView T0;  
      
  
    } 
	 Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
//			if(msg.what==111)
//			{
//				
//			}
			Bundle b = msg.getData();
           String name = b.getString("name");
           if(name!=null)
	        dolist(name);
			super.handleMessage(msg);
		}};
    private String fname;
	private LinearLayout det;
	//public static ImageView devicelamp = null;
	private  ImageView logbtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 if(commond.isBlockHome)this.getWindow().setFlags(main.FLAG_HOMEKEY_DISPATCHED, main.FLAG_HOMEKEY_DISPATCHED);
		
		setContentView(R.layout.activity_main);
		 mInflater=(LayoutInflater)this 
		         .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		 lv=(ListView)this.findViewById(R.id.ListView);
		    xls=(LinearLayout)this.findViewById(R.id.button1);
		    td=(LinearLayout)this.findViewById(R.id.button2);
		    det=(LinearLayout)this.findViewById(R.id.det);
		    //devicelamp = (ImageView)findViewById(R.id.deviceLamp);
		    //commond.devicelamp = devicelamp;
		    logbtn = (ImageView)findViewById(R.id.logs);
		    
		    logbtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this,logs.class) ;
					startActivity(intent);
				}
				
			});
		    
		    xls.setOnClickListener(new OnClickListener() {
		    	
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
						if(filename==null){
							commond.setToast( getApplication().getString(R.string.dilog_digtool_empty));
						}
					     Xlsmake xls=new Xlsmake(xyz);
				        try {
                            xls.toxls(fname);
                            commond.setToast( getApplication().getString(R.string.dilog_export_success));
                           //Toast.makeText(getApplicationContext(), "导出成功", Toast.LENGTH_SHORT).show();
                        } catch (RowsExceededException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            commond.setToast( getApplication().getString(R.string.dilog_export_fail));
                            //Toast.makeText(getApplicationContext(), "导出失败", Toast.LENGTH_LONG).show();
                            return ;
                        } catch (WriteException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            commond.setToast( getApplication().getString(R.string.dilog_export_fail));
                            //Toast.makeText(getApplicationContext(), "导出失败", Toast.LENGTH_LONG).show();
                            return ;
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            commond.setToast( getApplication().getString(R.string.dilog_export_fail));
                            //Toast.makeText(getApplicationContext(), "导出失败", Toast.LENGTH_LONG).show();
                            return ;
                        }
				}
			});
		    td.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					startActivity(intent);
				}
		    	
		    });
		    
		    det.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog();
					
				}  });
		    
		   File file = new File("/mnt/sdcard/dragtool");
		      if(file.isDirectory()){
		    	  ArrayList<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		    	  
		           File [] fileArray = file.listFiles();
		           if(null != fileArray && 0 != fileArray.length){
		        	   int filesize = 0;
		        	  // Log.e("11111111",""+filename);
		        	  // String[]   filedata=new String[fileArray.length];
		                for(int i = 0; i < fileArray.length; i++){
		                	FileInfo fileInfo=new FileInfo();
		                	fileInfo.name=fileArray[i].getName();
		                	if(fileInfo.name.indexOf(".txt")!=-1){
	//		                	fileInfo.name=fileArray[i].getName().replace(".txt", "");
			                	fileInfo.lastModified=fileArray[i].lastModified();
			                	fileInfoList.add(fileInfo);
			                	filesize++;
		                	}
		                }
		                //判断文件时.txt的个数
		                if(filesize!=0){
		                	filename=new String[filesize];
		                }
		                else {
		                	return;
		                };
		                Collections.sort(fileInfoList, new FileComparator());
		                for(int i = 0; i < fileInfoList.size(); i++){
		                	//filename[i]=fileInfoList.get(i).name.replace(".txt", "");
		                	filename[i]=fileInfoList.get(i).name;
		                }
		                }
		           }

		
		 spinner = (Spinner) findViewById(R.id.Spinner01);
		 if(filename!=null){
		         //将可选内容与ArrayAdapter连接起来		
		         adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,filename);		 		          		 
		         //设置下拉列表的风格		 
		         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		 		          		 
		         //将adapter 添加到spinner中		 
		         spinner.setAdapter(adapter);		 		          
		 		         //添加事件Spinner事件监听 	 
		         spinner.setOnItemSelectedListener(new SpinnerSelectedListener());		          		 
		         //设置默认值		 
		         spinner.setVisibility(View.VISIBLE);	
		        
		         dolist(filename[0]);

		 }
		 else{
			 commond.setToast(  getApplication().getString(R.string.dilog_digtool_empty));
			 //Toast.makeText(this, "digtool中没有文件", Toast.LENGTH_SHORT).show();
		 }
		 
		 //commond.setDeviceType(0);
	}

	
	


class SpinnerSelectedListener implements OnItemSelectedListener{



     public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    	 Message msg = new Message();
    	              Bundle b = new Bundle();// 存放数据
    	              b.putString("name", filename[arg2]);
    	             msg.setData(b);
    	           handler.sendMessage(msg); // 向Handler发送消息,更新UI
       
     }
     public void onNothingSelected(AdapterView<?> arg0) {

     }

 }


public void dolist(String name){
    fname=name;
	ReadTxt r=new ReadTxt(name);
    r.numbers=r.getfloatnumbers(r.getStrings());
    xyz=new XYZVo(r.numbers.length/4);
//    for(int i=0;i<r.numbers.length;i++)
//    {
//        NumberFormat nf = NumberFormat.getNumberInstance();
//        nf.setMaximumFractionDigits(4);
//        System.out.println(r.numbers[i*3]+" "+r.numbers[i*3+1]+" "+r.numbers[i*3+2]+" "+r.numbers[i*3+3]+" ");    
//    }
    xyz.setTxtread(r.numbers);
    xyz.setTf(r.getTf());   
    xyz.setX(r.getX(r.numbers));
    xyz.setY(r.getY(r.numbers));
    xyz.setZ(r.getZ(r.numbers));
    xyz.setL(r.getL(r.numbers,xyz.getX(),xyz.getY()));
  //  Xlsmake xls=new Xlsmake(xyz);
  //  xls.toxls();
   float[] x= xyz.getX();
   float[] y=xyz.getY();
   float[] z=xyz.getZ();
   float[] l=xyz.getL();
   float[] txt=xyz.getTxtread();
   boolean[] txtf=xyz.getTf();
   Bundle bundle = new Bundle();   
   intent = new Intent(this, Draw3D.class);
   bundle.putSerializable("XYZVo", xyz);
   bundle.putString("projectname", name);
   intent.putExtras(bundle);
    
    for(int i=0;i<x.length;i++)
    {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(4);
        System.out.println(txt[i*4]+" "+txt[i*4+1]+" "+txt[i*4+2]+" "+txt[i*4+3]+" "+nf.format(x[i])+" "+nf.format(y[i])+" "+nf.format(z[i])+" "+nf.format(l[i]));    
    }
   
   
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(3);
    final List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    HashMap<String, Object> m1=new HashMap<String,Object>();
//    深度(米)	倾角(度)	方位(度)	偏移Lx	偏移Ly	深度Z	
    
   
    m1.put("tt", this.getString(R.string.serialno));
    m1.put("t0", this.getString(R.string.time));
	m1.put("t1", this.getString(R.string.depth));
	m1.put("t2",  this.getString(R.string.d_dip));//d_dip
	m1.put("t3", this.getString(R.string.d_orientation));
	m1.put("t4", this.getString(R.string.lx));
	m1.put("t5",this.getString(R.string.ly));
	m1.put("t6", this.getString(R.string.dz));
	m1.put("t7", this.getString(R.string.pl));
	 m1.put("t0f", false);//数据正常是false
     m1.put("t1f", false);
     m1.put("t2f", false);
     m1.put("t3f", false);
	list.add(m1);
    for(int i=0,j=0;i<x.length;i++,j=j+4)
    {
    	HashMap<String, Object> m=new HashMap<String,Object>();
    	m.put("tt", nf.format(i+1));
    	m.put("t0", nf.format(txt[j]));
    	m.put("t1", nf.format(txt[j+1]));
    	m.put("t2", nf.format(txt[j+2]));
    	m.put("t3", nf.format(txt[j+3]));
    	
        m.put("t0f", txtf[j]);
        m.put("t1f", txtf[j+1]);
        m.put("t2f", txtf[j+2]);
        m.put("t3f", txtf[j+3]);
        
    	m.put("t4", nf.format(x[i]));
    	m.put("t5",nf.format( y[i]));
    	m.put("t6", nf.format(z[i]));
    	m.put("t7", nf.format(l[i]));
    	list.add(m);
    }
//    lv.setAdapter(new SimpleAdapter(this, list, R.layout.digital
//    , new String[]{"t0","t1","t2","t3","t4","t5","t6","t7"}
//    , new int[]{R.id.t0,R.id.t1,R.id.t2,R.id.t3,R.id.t4,R.id.t5,R.id.t6,R.id.t7}));
    BaseAdapter simpleAdapter=new BaseAdapter() {
        
        @Override
        public View getView(int position, View convertView, ViewGroup arg2) {
            // TODO Auto-generated method stub  
            ViewHolder holder;  
            if (convertView == null) {  
                convertView = mInflater.inflate(R.layout.digital, null);  
                holder = new ViewHolder();  
                holder.TT = (TextView) convertView.findViewById(R.id.tt);
                //holder.T0 = (TextView) convertView.findViewById(R.id.t0);  
                holder.T1 = (TextView) convertView.findViewById(R.id.t1);  
                holder.T2 = (TextView) convertView.findViewById(R.id.t2);  
                holder.T3 = (TextView) convertView.findViewById(R.id.t3);  
      
                convertView.setTag(holder);  
            } else {  
                holder = (ViewHolder) convertView.getTag();  
            }  
            
            holder.TT.setText((String)list.get(position).get("tt"));
            //holder.T0.setText((String)list.get(position).get("t0")+"(s)");
            //if((Boolean) ((list.get(position).get("t0f")))){
            //holder.T0.setTextColor(Color.RED);
            //}
            holder.T1.setText((String)list.get(position).get("t1")+"(m)");
            if((Boolean) ((list.get(position).get("t1f")))){
                holder.T1.setTextColor(Color.RED);
                }
            holder.T2.setText((String)list.get(position).get("t2")+"\u00b0");
            if((Boolean) ((list.get(position).get("t2f")))){
                holder.T2.setTextColor(Color.RED);
                }
            holder.T3.setText((String)list.get(position).get("t3")+"\u00b0");
            if((Boolean) ((list.get(position).get("t3f")))){
                holder.T3.setTextColor(Color.RED);
                }
//            holder.img.setOnClickListener(new OnClickListener() {
//                
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    Log.i("nanhu", "1111");
//                }
//            });
            return convertView;  
//         LinearLayout line=new LinearLayout(FullscreenActivity.this);
//         line.setOrientation(0);
//         ImageView image=new ImageView(FullscreenActivity.this);
//         TextView T=new TextView(FullscreenActivity.this );
//         image.setImageDrawable((Drawable)list.get(position).get("i"));
//         T.setText((String)list.get(position).get("n"));
//         line.addView(image);
//         line.addView(T);
//        return line;
        }  
        
        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }
        
        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }
        
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }
    };
    lv.setAdapter(simpleAdapter);
}

public void deleteFile(File file) {
if (file.exists()) { // 判断文件是否存在

file.delete(); // delete()方法 你应该知道 是删除的意思;


}
else
	commond.setToast(getApplication().getString(R.string.dilog_txt_empty));
	//Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
}

protected void dialog() {
	AlertDialog.Builder builder = new AlertDialog.Builder(this); 
	builder.setMessage(getApplication().getString(R.string.dilog_txt_del)) 
	       .setCancelable(false) 
	       .setPositiveButton(getApplication().getString(R.string.dilog_yes), new DialogInterface.OnClickListener() { 
	           public void onClick(DialogInterface dialog, int id) { 
	        	   deleteFile(new File("/mnt/sdcard/dragtool/"+fname));
	        	   MainActivity.this.finish();
	        	   if(commond.checkFile()){
		        	   Intent Intent2=   new Intent(MainActivity.this,MainActivity.class);
		        	   startActivity(Intent2);
	        	   }
	           } 
	       }) 
	       .setNegativeButton(getApplication().getString(R.string.dilog_no), new DialogInterface.OnClickListener() { 
	           public void onClick(DialogInterface dialog, int id) { 
	                dialog.cancel(); 
	           } 
	       }); 
	AlertDialog alert = builder.create(); 
	alert.show();
	 }

public class FileComparator implements Comparator<FileInfo> {  
    public int compare(FileInfo file1, FileInfo file2) {  
        if(file1.lastModified < file2.lastModified)  
        {  
            return 1;  
        }else  
        {  
            return -1;  
        }  
    }  
}  
}


