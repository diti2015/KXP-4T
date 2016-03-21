package com.throne.digtool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.Log;

public class Draw {

private static Bitmap mBitmap;
private static Canvas mCanvas;
private static float maxz;
private static float pointsize;
;


public static Bitmap todrawTop(XYZVo xyz)
{

   int	length=xyz.getX().length;
	 float[] x= xyz.getX();
    float[] y=xyz.getY();
    maxz=0;
    for(int i=0;i<x.length;i++)
    {
        float maxz1;
        if(Math.abs(x[i])>Math.abs(y[i]))
        {
            maxz1=Math.abs(x[i]);
        }
        else{
            maxz1=Math.abs(y[i]);
        }
        if(maxz<maxz1)
        {
            maxz=maxz1;
        }
        
    }
   
    pointsize=(490/maxz);
    float[]  line=new float[length*4];
    for(int i=0,j=0;i<(length-1)*4;i=i+4,j++)
	{
		line[i]=x[j]*pointsize+500;
		line[i+1]=y[j]*pointsize+500;
		line[i+2]=x[j+1]*pointsize+500;
		line[i+3]=y[j+1]*pointsize+500;
		System.out.println(""+line[i]+" "+line[i+1]+" "+line[i+2]+" "+line[i+3]);
		
	}
	mBitmap=Bitmap.createBitmap(1000,1000,Config.ARGB_8888);
    mCanvas=new Canvas(mBitmap);
    Paint mPaint=new Paint();
 	mPaint.setColor(Color.RED);
 	mPaint.setAntiAlias(true);  
 	mPaint.setStrokeWidth(8);

	  mCanvas.drawColor(Color.BLACK);
	
	    	Log.i("nn1", String.valueOf(length));
	    	mCanvas.drawLines(line, mPaint);
	    	mPaint.setStrokeWidth(4);   	
	  mPaint.setColor(Color.WHITE);
	  for(int i=0;i<x.length;i++)
	  {
	  mCanvas.drawCircle(x[i]*pointsize+500, y[i]*pointsize+500, 3f, mPaint);
	  }
	  mCanvas.drawLine(0,500,1000,500, mPaint);
	  mCanvas.drawLine(500,0,500,1000, mPaint);
	  mPaint.setTextSize(30);

	

	//mCanvas.drawRect(20,20,80,30,mPaint); 
	  
	  Matrix matrix = new Matrix();  
      matrix.reset();  
      matrix.setRotate(270);  
      Bitmap mBitmap2 = Bitmap.createBitmap(mBitmap,0,0, mBitmap.getWidth(), mBitmap.getHeight(),matrix, true);
      mCanvas=new Canvas(mBitmap2);
      mCanvas.drawText("E", 940,460, mPaint);
      mCanvas.drawText("N", 440,40, mPaint);
    return mBitmap2;  
	}

public static Bitmap todrawSide(XYZVo xyz) {
	// TODO Auto-generated method stub
int	length=xyz.getX().length;
	 float[] x= xyz.getX();
   float[] z=xyz.getZ();
   maxz=0;
   for(int i=0;i<x.length;i++)
   {
       float maxz1;
       if(Math.abs(x[i])>Math.abs(z[i]))
       {
           maxz1=Math.abs(x[i]);
       }
       else{
           maxz1=Math.abs(z[i]);
       }
       if(maxz<maxz1)
       {
           maxz=maxz1;
       }
       
   }
   
//   maxz=z[z.length-1];
   pointsize= (490/maxz);
   float[]  line=new float[length*4];
   for(int i=0,j=0;i<(length-1)*4;i=i+4,j++)
	{
		line[i]=x[j]*pointsize+500;
		line[i+1]=z[j]*pointsize+100;
		line[i+2]=x[j+1]*pointsize+500;
		line[i+3]=z[j+1]*pointsize+100;
//		line[i]=x[j];
//		line[i+1]=z[j];
//		line[i+2]=x[j+1];
//		line[i+3]=z[j+1];
	   
		System.out.println(""+line[i]+" "+line[i+1]+" "+line[i+2]+" "+line[i+3]);
		
	}
 		
 	
   
   
	mBitmap=Bitmap.createBitmap(1000,1000,Config.ARGB_8888);
   mCanvas=new Canvas(mBitmap);
   Paint mPaint=new Paint();
	mPaint.setColor(Color.RED);
	mPaint.setAntiAlias(true);  
	mPaint.setStrokeWidth(8);
 
 float [] line1={500,500,510,510,510,510,520,520,520,520,530,530};
	  mCanvas.drawColor(Color.BLACK);
	 
	    	Log.i("nn1", String.valueOf(length));
	    	mCanvas.drawLines(line, mPaint);
	    	mPaint.setStrokeWidth(4);
	    	mPaint.setColor(Color.WHITE);;
	  mCanvas.drawLine(0,100,1000,100, mPaint);
	  mCanvas.drawLine(500,0,500,1000, mPaint);
	  for(int i=0;i<x.length;i++)
      {
      mCanvas.drawCircle(x[i]*pointsize+500, z[i]*pointsize+100, 3f, mPaint);
      }
	  
	  mPaint.setTextSize(30);
	  mCanvas.drawText("N", 940,160, mPaint);
	//mCanvas.drawRect(20,20,80,30,mPaint); 
   return mBitmap;  
}


public static Bitmap todrawMain(XYZVo xyz) {
    // TODO Auto-generated method stub
    int length=xyz.getX().length;
    float[] x= xyz.getY();
  float[] z=xyz.getZ();
  maxz=0;
  for(int i=0;i<x.length;i++)
  {
      float maxz1;
      if(Math.abs(x[i])>Math.abs(z[i]))
      {
          maxz1=Math.abs(x[i]);
      }
      else{
          maxz1=Math.abs(z[i]);
      }
      if(maxz<maxz1)
      {
          maxz=maxz1;
      }
       
  }
//  maxz=z[z.length-1];
  pointsize=(490/maxz);
  float[]  line=new float[length*4];
  for(int i=0,j=0;i<(length-1)*4;i=i+4,j++)
   {
       line[i]=x[j]*pointsize+500;
       line[i+1]=z[j]*pointsize+100;
       line[i+2]=x[j+1]*pointsize+500;
       line[i+3]=z[j+1]*pointsize+100;
//     line[i]=x[j];
//     line[i+1]=z[j];
//     line[i+2]=x[j+1];
//     line[i+3]=z[j+1];
      
       System.out.println(""+line[i]+" "+line[i+1]+" "+line[i+2]+" "+line[i+3]);
       
   }
       
   
  
  
   mBitmap=Bitmap.createBitmap(1000,1000,Config.ARGB_8888);
  mCanvas=new Canvas(mBitmap);
  Paint mPaint=new Paint();
   mPaint.setColor(Color.RED);
   mPaint.setAntiAlias(true);  
   mPaint.setStrokeWidth(8);

float [] line1={500,500,510,510,510,510,520,520,520,520,530,530};
     mCanvas.drawColor(Color.BLACK);
    
           Log.i("nn1", String.valueOf(length));
           mCanvas.drawLines(line, mPaint);
           mPaint.setStrokeWidth(4);
           mPaint.setColor(Color.WHITE);
     mCanvas.drawLine(0,100,1000,100, mPaint);
     mCanvas.drawLine(500,0,500,1000, mPaint);
     for(int i=0;i<x.length;i++)
     {
     mCanvas.drawCircle(x[i]*pointsize+500, z[i]*pointsize+100, 3f, mPaint);
     }
     mPaint.setTextSize(30);
     mCanvas.drawText("E", 940,160, mPaint);

   //mCanvas.drawRect(20,20,80,30,mPaint); 
    return mBitmap;
}

}
