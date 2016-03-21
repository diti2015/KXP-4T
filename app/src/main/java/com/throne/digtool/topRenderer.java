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

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */

public class topRenderer implements Renderer
{

float rotateAngle;  
XYZVo xyz;
int length;
  
//顶点数组,GL ES只能用这个办法画圆吗？  
private float[] xpoint={52,-2,0
                        ,54,-5,0,
                        54,-2,0,
                        52,-5,0};
private float[] ypoint={-3,53,0,
                         -4,54,0,
                         -2,54,0,
                         -4,52,0};
private float[] vertices = new float[720];

private float[] vertices1= new float[720];

private float[] vertices2= new float[720];

private float[] vertices3= new float[720];

private float[] vertices4= new float[720];

private float[] vertices0= new float[720];  
private float[] line;
private float[] x={
-5000f,0f,
5000f,0f,
};
private float[] y={
0f,-5000f,
0f,5000f,
};
private float deep;

public topRenderer(XYZVo xyz) {
	// TODO Auto-generated constructor stub
	this.xyz=xyz;
	
	
	length=xyz.getX().length*2;
	 float[] x= xyz.getX();
     float[] y=xyz.getY();

	this.line=new float[length];
	for(int i=0,j=0;i<length;i=i+2,j++)
	{
		this.line[i]=x[j];
		this.line[i+1]=y[j];
		System.out.println(""+line[i]+" "+line[i+1]);
		
	}
	
}


//度到弧度的转换  
public float DegToRad(float deg)  
{  
    return (float) (3.14159265358979323846 * deg / 180.0);  
}  

  
@Override  
public void onDrawFrame(GL10 gl) {  
    // TODO Auto-generated method stub  
      
    // 进入这个函数第一件要做的事就是清除屏幕和深度缓存  
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);  

    //画圆形  
    drawCircle(gl);  
}  
  
public void drawCircle(GL10 gl)  
{  
    //重置投影矩阵  
    gl.glLoadIdentity();  
    // 移动操作，移入屏幕(Z轴)5个像素, x, y , z  
    gl.glTranslatef(0.0f, 0.0f, -200.0f);  
      
    //旋转, angle, x, y , z  
    gl.glRotatef(rotateAngle, 1.0f, 0.0f, 0.0f);  

    // 设置当前色为红色, R, G, B, Alpha  
  
    gl.glColor4f(0.0f, 0f, 1.1f, 1.0f);  
    //设置圆形顶点数据，这个是在创建时生成  
    FloatBuffer verBuffer0 = FloatBuffer.wrap(vertices0);  
    FloatBuffer verBuffer = FloatBuffer.wrap(vertices);  
    FloatBuffer verBuffer1 = FloatBuffer.wrap(vertices1);  
    FloatBuffer verBuffer2 = FloatBuffer.wrap(vertices2);  
    FloatBuffer verBuffer3 = FloatBuffer.wrap(vertices3); 
    FloatBuffer verBuffer4 = FloatBuffer.wrap(vertices4); 
    FloatBuffer verBufferline = FloatBuffer.wrap(line); 
    FloatBuffer verBufferx = FloatBuffer.wrap(x); 
    FloatBuffer verBuffery = FloatBuffer.wrap(y); 
    //设置顶点类型为浮点坐标(GL_FLOAT)，不设置或者设置错误类型将导致图形不能显示或者显示错误  
 
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferUtil(xpoint));  

    //打开顶点数组  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //向OGL发送实际画图指令  
    gl.glDrawArrays(GL10.GL_LINES, 0, 4);  
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferUtil(ypoint));  
    gl.glDrawArrays(GL10.GL_LINES, 0, 4);  
    
    
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufferUtil(vertices));  

    //打开顶点数组  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //向OGL发送实际画图指令  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 360);  
    
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufferUtil(vertices1));  

    //打开顶点数组  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //向OGL发送实际画图指令  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 360);    
    
    
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufferUtil(vertices2));  

    //打开顶点数组  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //向OGL发送实际画图指令  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 360);  
    
    
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufferUtil(vertices3));  

    //打开顶点数组  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //向OGL发送实际画图指令  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 360);  
    
    
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufferUtil(vertices4));  

    //打开顶点数组  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //向OGL发送实际画图指令  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 360);  
    
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufferUtil(vertices0));  

    //打开顶点数组  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //向OGL发送实际画图指令  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 360);  
    gl.glColor4f(1.0f, 0.1f, 0.1f, 1.0f);
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0,  bufferUtil(line));  

    //打开顶点数组  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //向OGL发送实际画图指令  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, length/2);
   
    gl.glColor4f(0.0f, 0f, 1.1f, 1.0f); 
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0,  bufferUtil(x));  

    //打开顶点数组  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //向OGL发送实际画图指令  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 2);
    
    gl.glColor4f(0.0f, 0f, 1.1f, 1.0f); 
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0,  bufferUtil(y));  

    //打开顶点数组  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //向OGL发送实际画图指令  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 2);
    
    //关闭顶点数组功能  
    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);  

    //画图结束  
    gl.glFinish();  
      
    //更改旋转角度  
    //rotateAngle += 0.5;  
}  
  

@Override  
public void onSurfaceChanged(GL10 gl, int width, int height) {  
    // TODO Auto-generated method stub  
      
    float ratio = (float) width / height;  
    //设置OpenGL场景的大小  
    gl.glViewport(0, 0, width, height);  
    //设置投影矩阵  
    gl.glMatrixMode(GL10.GL_PROJECTION);  
    //重置投影矩阵  
    gl.glLoadIdentity();  
    // 设置视口的大小  
    gl.glFrustumf(-ratio, ratio, -1, 1, 1, 5000);  
    // 选择模型观察矩阵  
    gl.glMatrixMode(GL10.GL_MODELVIEW);      
    // 重置模型观察矩阵  
    gl.glLoadIdentity();      
      
}  

@Override  
public void onSurfaceCreated(GL10 gl, EGLConfig config) {  
    // TODO Auto-generated method stub  
    // 启用阴影平滑  
    gl.glShadeModel(GL10.GL_SMOOTH);  
    // 黑色背景  
    gl.glClearColor(0, 0, 0, 0);  
    // 设置深度缓存  
    gl.glClearDepthf(1.0f);                              
    // 启用深度测试  
    gl.glEnable(GL10.GL_DEPTH_TEST);                          
    // 所作深度测试的类型  
    gl.glDepthFunc(GL10.GL_LEQUAL);                              
      
    // 告诉系统对透视进行修正  
    gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);  
      

    vertices0=GetCirclePoint.getpoint(100);
    vertices=GetCirclePoint.getpoint(80);
    vertices1=GetCirclePoint.getpoint(60);
    vertices2=GetCirclePoint.getpoint(40);
    vertices3=GetCirclePoint.getpoint(20);
    vertices4=GetCirclePoint.getpoint(0);
    
    
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