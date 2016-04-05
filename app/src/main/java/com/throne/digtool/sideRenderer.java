/**
 * 
 */
package com.throne.digtool;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;



public class sideRenderer implements Renderer
{
    
	float[] rectData ;
	private float[] xpoint={54,-11,0
            ,56,-14,0,
            56,-11,0,
            54,-14,0};
private float[] ypoint={-13,28,0,
             -11,28,0,
             -13,26,0,
             -11,26,0};

		
	int[] rectColor = new int[] {
	        0, 0, 65535, 0, // 右下顶点蓝色
		0, 65535, 0, 0, // 右上顶点绿色

	
	};
	
	float [] LineData=new float[]
	{
	    0f,5000f,0f,
	    0f,-5000f,0f,
	};
	private float[] x={
	        -5000f,0f,
	        5000f,0f,
	        };

	FloatBuffer triangleDataBuffer;
	IntBuffer triangleColorBuffer;
	FloatBuffer rectDataBuffer;
	IntBuffer rectColorBuffer;
	FloatBuffer lineDataBuffer;
	FloatBuffer pentacleBuffer;
	// 控制旋转的角度
	private float rotate;
	private XYZVo xyz;

	private int length;
	public sideRenderer()
	{
		// 将顶点位置数据数组包装成FloatBuffer;
		
		rectDataBuffer = FloatBuffer.wrap(rectData);
		lineDataBuffer = FloatBuffer.wrap(LineData);
		// 将顶点颜色数据数组包装成IntBuffer;

		rectColorBuffer = IntBuffer.wrap(rectColor);
	}

	public sideRenderer(XYZVo xyz) {
		// TODO Auto-generated constructor stub
		this.xyz=xyz;
		length=xyz.getX().length*3;
		 float[] x= xyz.getX();
	     float[] y=xyz.getY();
	     float[] z=xyz.getZ();
		this.rectData=new float[length];
		for(int i=0,j=0;i<length;i=i+3,j++)
		{
			this.rectData[i]=x[j];
			this.rectData[i+1]=-z[j];
			this.rectData[i+2]=y[j];
			System.out.println(""+rectData[i]+" "+rectData[i+1]+" "+rectData[i+2]);
		}
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		// 关闭抗抖动
		gl.glDisable(GL10.GL_DITHER);
		// 设置系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glClearColor(0, 0, 0, 0);
		// 设置阴影平滑模式
		gl.glShadeModel(GL10.GL_SMOOTH);
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 设置深度测试的类型
		gl.glDepthFunc(GL10.GL_LEQUAL);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		// 设置3D视窗的大小及位置
		gl.glViewport(0, 0, width, height);
		// 将当前矩阵模式设为投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// 初始化单位矩阵
		gl.glLoadIdentity();
		// 计算透视视窗的宽度、高度比
		float ratio = (float) width / height;
		// 调用此方法设置透视视窗的空间大小。
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 500);
	}

	// 绘制图形的方法
	@Override
	public void onDrawFrame(GL10 gl)
	{
		// 清除屏幕缓存和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 启用顶点座标数据
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// 启用顶点颜色数据
		//gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// 设置当前矩阵堆栈为模型堆栈，
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLineWidth( 1.0f);
		
		// --------------------绘制第1个图形---------------------
		// 重置当前的模型视图矩阵
		gl.glLoadIdentity();
		gl.glTranslatef(0f, 50.0f, -200f);
	//	gl.glRotatef(rotate, 0f, 4.4f, 0f);       	
		// 设置顶点的位置数据
		gl.glColor4f(0.0f, 0f, 1.1f, 1.0f);  
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferUtil(LineData));
		// 设置顶点的颜色数据
		//gl.glColorPointer(4, GL10.GL_FIXED, 0, bufferUtil(rectColor));
		// 根据顶点数据绘制平面图形
		gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 2);
		 gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferUtil(xpoint));  

		    //打开顶点数组  
		    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
		      
		    //向OGL发送实际画图指令  
		    gl.glDrawArrays(GL10.GL_LINES, 0, 4);  
		    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferUtil(ypoint));  
		    gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 4);  

		    gl.glVertexPointer(2, GL10.GL_FLOAT, 0,  bufferUtil(x));  
//		
		      gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 4); 
		      
        gl.glLoadIdentity();
        gl.glTranslatef(0f, 50.0f, -200f);
      //  gl.glRotatef(rotate, 0f, 4.4f, 0f); 
      //  gl.glRotatef(rotate, 0f, 0f, 0f); 
        gl.glLineWidth( 3.0f);
        // 设置顶点的位置数据
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColor4f(1.0f,0.2f,0.2f,0.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferUtil(rectData));
        // 设置顶点的颜色数据
   
        // 根据顶点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, length/3);
        
        //关闭顶点数组功能  
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);  

        //画图结束  
        gl.glFinish();  
	rotate+=0.3;
	}
	public Buffer bufferUtil(int []arr){  
	    IntBuffer mBuffer ;  
	      
	    //先初始化buffer,数组的长度*4,因为一个int占4个字节  
	   ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);  
	   //数组排列用nativeOrder  
	    qbb.order(ByteOrder.nativeOrder());  
	     
	    mBuffer = qbb.asIntBuffer();  
	    mBuffer.put(arr);  
	    mBuffer.position(0);  
	      
	    return mBuffer;  
	} 

	public Buffer bufferUtil(float []arr){  
		FloatBuffer mBuffer ;  
	      
	    //先初始化buffer,数组的长度*4,因为一个int占4个字节  
	   ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 8);  
	   //数组排列用nativeOrder  
	    qbb.order(ByteOrder.nativeOrder());  
	     
	    mBuffer = qbb.asFloatBuffer();  
	    mBuffer.put(arr);  
	    mBuffer.position(0);  
	      
	    return mBuffer;  
	} 
}
