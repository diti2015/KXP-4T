package com.throne.digtool;

import java.text.NumberFormat;

//import com.example.android.BluetoothChat.logs;
import com.example.android.BluetoothChat.main;
import com.example.android.BluetoothChat.commond;
import com.liqin.instrument.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class Draw3D extends Activity{

    private LinearLayout top;
	private LinearLayout side;
	private RelativeLayout Re;
	private GLSurfaceView glView;
	private GLSurfaceView topView;
	private GLSurfaceView sideiew;
	private XYZVo xyz;
    private String projectname;
    private TextView projectnameview;
    private topRenderer top3d;
    private sideRenderer side3d;
    private ImageView logbtn = null;
	
	private MyImageView imageView;
	private Bitmap bitmap;

	private float beforeLenght;
	private float afterLenght;

	private float afterX,afterY;
	private float beforeX,beforeY;
	private MyImageView sideimageview;
	private Bitmap bitmap1;
	private float maxz;
    private Bitmap bitmap2;
    private LinearLayout mainB;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(commond.isBlockHome)this.getWindow().setFlags(main.FLAG_HOMEKEY_DISPATCHED, main.FLAG_HOMEKEY_DISPATCHED);
        
        setContentView(R.layout.activity_3d);
        Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if(bundle != null){
			this.xyz = (XYZVo)bundle.getSerializable("XYZVo");
			this.projectname=(String)bundle.getString("projectname") ;
		}
		float[] x= xyz.getX();
	       float[] y=xyz.getY();
	       float[] z=xyz.getZ();
	       float[] l=xyz.getL();
	       maxz=z[z.length-1];
	       for(int i=0;i<x.length;i++)
	        {
	            NumberFormat nf = NumberFormat.getNumberInstance();
	            nf.setMaximumFractionDigits(4);
	         
	           // System.out.println(nf.format(x[i])+" "+nf.format(y[i])+" "+nf.format(z[i])+" "+nf.format(l[i]));    
	        }
		 Re=(RelativeLayout)this.findViewById(R.id.RelativeLayout);
		 projectnameview=(TextView)this.findViewById(R.id.projectname);
		 top=(LinearLayout)this.findViewById(R.id.button1);
	        side=(LinearLayout)this.findViewById(R.id.button2);
	        mainB=(LinearLayout)this.findViewById(R.id.button3);
		 projectnameview.setText(projectname);
        findView();
        Re.addView(imageView);
        config();
        
        logbtn = (ImageView)findViewById(R.id.logs);
        //devicelamp = (ImageView)findViewById(R.id.deviceLamp);
	    //commond.devicelamp = devicelamp;
	    
//        logbtn.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(Draw3D.this,logs.class) ;
//				startActivity(intent);
//			}
//
//		});
        
        
		side.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub		
				imageView.setImageBitmap(null);
				imageView.setImageBitmap(bitmap1);
			}});
		top.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				imageView.setImageBitmap(null);
				imageView.setImageBitmap(bitmap);
			}});
		mainB.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                imageView.setImageBitmap(null);
                imageView.setImageBitmap(bitmap2);
            }});
	
		//commond.setDeviceType(0);
    }
    
    private void findView() {
		imageView = new MyImageView(this);

		bitmap =new pic().createBitmaptop(xyz);

     	bitmap1 =new pic().createBitmapside(xyz);
     	
     	bitmap2=new pic().createBitmapmain(xyz);

    }
    
    private void config() {
    	imageView.setImageBitmap(bitmap);
    }

	class MyImageView extends ImageView {

		private float scale = 0.02f;
		private int l,r,t,b;
		public MyImageView(Context context) {
			super(context);
		}

		private void setLocation(int x,int y) {
			this.setFrame(this.getLeft()+x, this.getTop()+y, this.getRight()+x, this.getBottom()+y);
		}
		

		private void setScale(float temp,int flag) {
			
			if(flag==0) {
			
				this.setFrame(this.getLeft()-(int)(temp*this.getWidth()), 
							  this.getTop()-(int)(temp*this.getHeight()), 
							  this.getRight()+(int)(temp*this.getWidth()), 
							  this.getBottom()+(int)(temp*this.getHeight()));	
			}else {
			    int a=(this.getRight()-this.getLeft())*(this.getBottom()-this.getTop());
				if(a>800*800){
				this.setFrame(this.getLeft()+(int)(temp*this.getWidth()), 
							  this.getTop()+(int)(temp*this.getHeight()), 
						      this.getRight()-(int)(temp*this.getWidth()), 
						      this.getBottom()-(int)(temp*this.getHeight()));
			}
			}
		}

		 @Override
	      protected void onDraw(Canvas canvas) {
	          super.onDraw(canvas);    
	          Rect rec=canvas.getClipBounds();
	          rec.bottom--;
	          rec.right--;
	          Paint paint=new Paint();
	          paint.setColor(Color.RED);
	          paint.setStyle(Paint.Style.STROKE);
	      }

		public void moveWithFinger(MotionEvent event) {
			
			switch(event.getAction()) {
			
			case MotionEvent.ACTION_DOWN:
				beforeX = event.getX();
				beforeY = event.getY();
				
				break;
			case MotionEvent.ACTION_MOVE:
				afterX = event.getX();
				afterY = event.getY();
				
				this.setLocation((int)(afterX-beforeX),(int)(afterY-beforeY));
				
				beforeX = afterX;
				beforeY = afterY;
				break;
				
			case MotionEvent.ACTION_UP:
				break;
			}
		}

        public void scaleWithFinger(MotionEvent event) {
			float moveX = event.getX(1) - event.getX(0);
			float moveY = event.getY(1) - event.getY(0);
			
			switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				beforeLenght = (float) Math.sqrt( (moveX*moveX) + (moveY*moveY) );
				break;
			case MotionEvent.ACTION_MOVE:
				afterLenght = (float) Math.sqrt( (moveX*moveX) + (moveY*moveY) );
				
				
				float gapLenght = afterLenght - beforeLenght;
				
				if(gapLenght == 0) {
					break;
				}

				if(gapLenght>0) {
					this.setScale(scale,0);
				}else {
					this.setScale(scale,1);
				}
				beforeLenght = afterLenght;
				break;
			}
		}
    }
}
