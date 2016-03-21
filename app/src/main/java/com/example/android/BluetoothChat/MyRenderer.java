/**
 * 
 */
package com.example.android.BluetoothChat;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;


public class MyRenderer implements Renderer
{
    
	float[] rectData = new float[] {

//	        0f, 0f , 0f,
//	        0.05f,-0.12f,0f,
//	        0.08f, -0.25f , 0f, 
//	        0.1f, -0.38f ,0f,    
//		   0.11f, -0.4f , 0f, 
//	        0.12f,-0.52f,0f,
//	        0.16f,-0.66f,0f,
//	        0.2f,-0.7f,0f,
//	        0.21f,-0.81f,0f,
//	        0.25f,-0.9f,0f,
	        0.019f,-1.000f,0.025f,
	        0.016f,-2.499f,0.052f,
	        -0.003f,-3.999f,0.076f,
	        0.013f,-5.499f,0.094f,
	        0.137f,-6.999f,0.105f,
	        0.274f,-8.498f,0.117f,
	        0.488f,-9.996f,0.119f,
	        0.699f,-11.493f,0.111f,
	        0.854f,-12.990f,0.098f,
	        1.159f,-14.485f,0.089f,
	        1.533f,-15.978f,0.086f,
	        1.949f,-17.469f,0.093f,
	        2.405f,-18.957f,0.110f,
	        2.906f,-20.441f,0.133f,
	        3.526f,-21.922f,0.164f,
	        3.976f,-23.400f,0.192f,
	        4.614f,-24.874f,0.222f,
	        5.256f,-26.344f,0.251f,
	        5.893f,-27.810f,0.279f,
	        6.560f,-29.272f,0.309f,
	        7.353f,-30.728f,0.346f,
	        8.079f,-32.181f,0.390f,
	        8.786f,-33.629f,0.439f,
	        9.632f,-35.072f,0.496f,
	        10.569f,-36.508f,0.561f,
	        11.701f,-37.935f,0.630f,
	        12.965f,-39.352f,0.696f,
	        14.150f,-40.761f,0.760f,
	        15.289f,-42.161f,0.826f,
	        16.532f,-43.552f,0.898f,
	        17.932f,-44.932f,0.963f,
		
		
	};
	
//    0.4f, -0.4f, 0.0f, // 2, 右下
//    0.4f, 0.4f, 0.0f, // 3, 右上
//    -0.4f, 0.4f, 0.0f, // 0, 左上
//    -0.4f, -0.4f, 0.0f, // 1, 左下
	int[] rectColor = new int[] {
	        0, 0, 65535, 0, // 右下顶点蓝色
		0, 65535, 0, 0, // 右上顶点绿色

	
	};
	
	float [] LineData=new float[]
	{
	    0f,20.5f,0f,
	    0f,-80.5f,0f,
	};
	

	FloatBuffer triangleDataBuffer;
	IntBuffer triangleColorBuffer;
	FloatBuffer rectDataBuffer;
	IntBuffer rectColorBuffer;
	FloatBuffer lineDataBuffer;
	FloatBuffer pentacleBuffer;
	// 控制旋转的角度
	private float rotate;
	public MyRenderer()
	{
		// 将顶点位置数据数组包装成FloatBuffer;
		
		rectDataBuffer = FloatBuffer.wrap(rectData);
		lineDataBuffer = FloatBuffer.wrap(LineData);
		// 将顶点颜色数据数组包装成IntBuffer;

		rectColorBuffer = IntBuffer.wrap(rectColor);
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
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 100);
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
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// 设置当前矩阵堆栈为模型堆栈，
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLineWidth( 1.0f);

		
		// --------------------绘制第1个图形---------------------
        // 重置当前的模型视图矩阵
        gl.glLoadIdentity();
        gl.glTranslatef(-10f, 31.0f, -40.5f);
    //  gl.glRotatef(rotate, 0f, 4.4f, 0f);         
        // 设置顶点的位置数据
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, lineDataBuffer);
        // 设置顶点的颜色数据
        gl.glColorPointer(4, GL10.GL_FIXED, 0, rectColorBuffer);
        // 根据顶点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 2);
        
        
     
     
		
		
		
		// --------------------绘制第二个图形---------------------
		// 重置当前的模型视图矩阵
		gl.glLoadIdentity();
		gl.glTranslatef(10f, 31.0f, -40.5f);
	//	gl.glRotatef(rotate, 0f, 4.4f, 0f);       	
		// 设置顶点的位置数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, lineDataBuffer);
		// 设置顶点的颜色数据
		gl.glColorPointer(4, GL10.GL_FIXED, 0, rectColorBuffer);
		// 根据顶点数据绘制平面图形
		gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 2);
		
		   gl.glLoadIdentity();
	        gl.glTranslatef(-10f, 31.0f, -40.5f);
	       gl.glRotatef(rotate, 0f, 4.4f, 0f);    
	        gl.glLineWidth( 3.0f);
	        // 设置顶点的位置数据
	        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	        gl.glColor4f(1.0f,0.2f,0.2f,0.0f);
	        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectDataBuffer);
	        // 设置顶点的颜色数据
	   
	        // 根据顶点数据绘制平面图形
	        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 31);
		
	 
        gl.glLoadIdentity();
        gl.glTranslatef(10f, 31.0f, -40.5f);
      //  gl.glRotatef(rotate, 0f, 4.4f, 0f);    
        gl.glLineWidth( 3.0f);
        // 设置顶点的位置数据
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColor4f(1.0f,0.2f,0.2f,0.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectDataBuffer);
        // 设置顶点的颜色数据
   
        // 根据顶点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 31);
		rotate+=0.3;
	}
}
