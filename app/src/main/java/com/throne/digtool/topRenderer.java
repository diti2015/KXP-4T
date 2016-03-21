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
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a> 
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
  
//��������,GL ESֻ��������취��Բ��  
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


//�ȵ����ȵ�ת��  
public float DegToRad(float deg)  
{  
    return (float) (3.14159265358979323846 * deg / 180.0);  
}  

  
@Override  
public void onDrawFrame(GL10 gl) {  
    // TODO Auto-generated method stub  
      
    // �������������һ��Ҫ�����¾��������Ļ����Ȼ���  
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);  

    //��Բ��  
    drawCircle(gl);  
}  
  
public void drawCircle(GL10 gl)  
{  
    //����ͶӰ����  
    gl.glLoadIdentity();  
    // �ƶ�������������Ļ(Z��)5������, x, y , z  
    gl.glTranslatef(0.0f, 0.0f, -200.0f);  
      
    //��ת, angle, x, y , z  
    gl.glRotatef(rotateAngle, 1.0f, 0.0f, 0.0f);  

    // ���õ�ǰɫΪ��ɫ, R, G, B, Alpha  
  
    gl.glColor4f(0.0f, 0f, 1.1f, 1.0f);  
    //����Բ�ζ������ݣ�������ڴ���ʱ����  
    FloatBuffer verBuffer0 = FloatBuffer.wrap(vertices0);  
    FloatBuffer verBuffer = FloatBuffer.wrap(vertices);  
    FloatBuffer verBuffer1 = FloatBuffer.wrap(vertices1);  
    FloatBuffer verBuffer2 = FloatBuffer.wrap(vertices2);  
    FloatBuffer verBuffer3 = FloatBuffer.wrap(vertices3); 
    FloatBuffer verBuffer4 = FloatBuffer.wrap(vertices4); 
    FloatBuffer verBufferline = FloatBuffer.wrap(line); 
    FloatBuffer verBufferx = FloatBuffer.wrap(x); 
    FloatBuffer verBuffery = FloatBuffer.wrap(y); 
    //���ö�������Ϊ��������(GL_FLOAT)�������û������ô������ͽ�����ͼ�β�����ʾ������ʾ����  
 
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferUtil(xpoint));  

    //�򿪶�������  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //��OGL����ʵ�ʻ�ͼָ��  
    gl.glDrawArrays(GL10.GL_LINES, 0, 4);  
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferUtil(ypoint));  
    gl.glDrawArrays(GL10.GL_LINES, 0, 4);  
    
    
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufferUtil(vertices));  

    //�򿪶�������  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //��OGL����ʵ�ʻ�ͼָ��  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 360);  
    
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufferUtil(vertices1));  

    //�򿪶�������  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //��OGL����ʵ�ʻ�ͼָ��  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 360);    
    
    
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufferUtil(vertices2));  

    //�򿪶�������  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //��OGL����ʵ�ʻ�ͼָ��  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 360);  
    
    
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufferUtil(vertices3));  

    //�򿪶�������  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //��OGL����ʵ�ʻ�ͼָ��  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 360);  
    
    
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufferUtil(vertices4));  

    //�򿪶�������  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //��OGL����ʵ�ʻ�ͼָ��  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 360);  
    
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufferUtil(vertices0));  

    //�򿪶�������  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //��OGL����ʵ�ʻ�ͼָ��  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 360);  
    gl.glColor4f(1.0f, 0.1f, 0.1f, 1.0f);
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0,  bufferUtil(line));  

    //�򿪶�������  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //��OGL����ʵ�ʻ�ͼָ��  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, length/2);
   
    gl.glColor4f(0.0f, 0f, 1.1f, 1.0f); 
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0,  bufferUtil(x));  

    //�򿪶�������  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //��OGL����ʵ�ʻ�ͼָ��  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 2);
    
    gl.glColor4f(0.0f, 0f, 1.1f, 1.0f); 
    gl.glVertexPointer(2, GL10.GL_FLOAT, 0,  bufferUtil(y));  

    //�򿪶�������  
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
      
    //��OGL����ʵ�ʻ�ͼָ��  
    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 2);
    
    //�رն������鹦��  
    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);  

    //��ͼ����  
    gl.glFinish();  
      
    //������ת�Ƕ�  
    //rotateAngle += 0.5;  
}  
  

@Override  
public void onSurfaceChanged(GL10 gl, int width, int height) {  
    // TODO Auto-generated method stub  
      
    float ratio = (float) width / height;  
    //����OpenGL�����Ĵ�С  
    gl.glViewport(0, 0, width, height);  
    //����ͶӰ����  
    gl.glMatrixMode(GL10.GL_PROJECTION);  
    //����ͶӰ����  
    gl.glLoadIdentity();  
    // �����ӿڵĴ�С  
    gl.glFrustumf(-ratio, ratio, -1, 1, 1, 5000);  
    // ѡ��ģ�͹۲����  
    gl.glMatrixMode(GL10.GL_MODELVIEW);      
    // ����ģ�͹۲����  
    gl.glLoadIdentity();      
      
}  

@Override  
public void onSurfaceCreated(GL10 gl, EGLConfig config) {  
    // TODO Auto-generated method stub  
    // ������Ӱƽ��  
    gl.glShadeModel(GL10.GL_SMOOTH);  
    // ��ɫ����  
    gl.glClearColor(0, 0, 0, 0);  
    // ������Ȼ���  
    gl.glClearDepthf(1.0f);                              
    // ������Ȳ���  
    gl.glEnable(GL10.GL_DEPTH_TEST);                          
    // ������Ȳ��Ե�����  
    gl.glDepthFunc(GL10.GL_LEQUAL);                              
      
    // ����ϵͳ��͸�ӽ�������  
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
      
    //�ȳ�ʼ��buffer,����ĳ���*4,��Ϊһ��intռ4���ֽ�  
   ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);  
   //����������nativeOrder  
    qbb.order(ByteOrder.nativeOrder());  
     
    mBuffer = qbb.asIntBuffer();  
    mBuffer.put(arr);  
    mBuffer.position(0);  
      
    return mBuffer;  
} 

public Buffer bufferUtil(float []arr){  
	FloatBuffer mBuffer ;  
      
    //�ȳ�ʼ��buffer,����ĳ���*4,��Ϊһ��intռ4���ֽ�  
   ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 8);  
   //����������nativeOrder  
    qbb.order(ByteOrder.nativeOrder());  
     
    mBuffer = qbb.asFloatBuffer();  
    mBuffer.put(arr);  
    mBuffer.position(0);  
      
    return mBuffer;  
} 
}