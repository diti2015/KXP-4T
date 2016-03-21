package com.throne.digtool;

import android.R.color;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
/** 
 * create the bitmap from a byte array 
 * 
 * @param src the bitmap object you want proecss 
 * @param watermark the water mark above the src 
 * @return return a bitmap object ,if paramter's length is 0,return null 
 */  

public class pic {
    private Canvas mCanvas;
	private Bitmap mBitmap;

	public Bitmap createBitmaptop(XYZVo xyz)  
    {  
    	
        return Draw.todrawTop(xyz);
      	
    }

	public Bitmap createBitmapside(XYZVo xyz) {
		// TODO Auto-generated method stub
		  return Draw.todrawSide(xyz);
	}

    public Bitmap createBitmapmain(XYZVo xyz) {
        // TODO Auto-generated method stub
        return Draw.todrawMain(xyz);
    }

}
