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
	        0, 0, 65535, 0, // ���¶�����ɫ
		0, 65535, 0, 0, // ���϶�����ɫ

	
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
	// ������ת�ĽǶ�
	private float rotate;
	private XYZVo xyz;

	private int length;
	public sideRenderer()
	{
		// ������λ�����������װ��FloatBuffer;
		
		rectDataBuffer = FloatBuffer.wrap(rectData);
		lineDataBuffer = FloatBuffer.wrap(LineData);
		// ��������ɫ���������װ��IntBuffer;

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
		// �رտ�����
		gl.glDisable(GL10.GL_DITHER);
		// ����ϵͳ��͸�ӽ�������
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glClearColor(0, 0, 0, 0);
		// ������Ӱƽ��ģʽ
		gl.glShadeModel(GL10.GL_SMOOTH);
		// ������Ȳ���
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// ������Ȳ��Ե�����
		gl.glDepthFunc(GL10.GL_LEQUAL);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		// ����3D�Ӵ��Ĵ�С��λ��
		gl.glViewport(0, 0, width, height);
		// ����ǰ����ģʽ��ΪͶӰ����
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// ��ʼ����λ����
		gl.glLoadIdentity();
		// ����͸���Ӵ��Ŀ�ȡ��߶ȱ�
		float ratio = (float) width / height;
		// ���ô˷�������͸���Ӵ��Ŀռ��С��
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 500);
	}

	// ����ͼ�εķ���
	@Override
	public void onDrawFrame(GL10 gl)
	{
		// �����Ļ�������Ȼ���
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// ���ö�����������
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// ���ö�����ɫ����
		//gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// ���õ�ǰ�����ջΪģ�Ͷ�ջ��
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLineWidth( 1.0f);
		
		// --------------------���Ƶ�1��ͼ��---------------------
		// ���õ�ǰ��ģ����ͼ����
		gl.glLoadIdentity();
		gl.glTranslatef(0f, 50.0f, -200f);
	//	gl.glRotatef(rotate, 0f, 4.4f, 0f);       	
		// ���ö����λ������
		gl.glColor4f(0.0f, 0f, 1.1f, 1.0f);  
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferUtil(LineData));
		// ���ö������ɫ����
		//gl.glColorPointer(4, GL10.GL_FIXED, 0, bufferUtil(rectColor));
		// ���ݶ������ݻ���ƽ��ͼ��
		gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 2);
		 gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferUtil(xpoint));  

		    //�򿪶�������  
		    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
		      
		    //��OGL����ʵ�ʻ�ͼָ��  
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
        // ���ö����λ������
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColor4f(1.0f,0.2f,0.2f,0.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferUtil(rectData));
        // ���ö������ɫ����
   
        // ���ݶ������ݻ���ƽ��ͼ��
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, length/3);
        
        //�رն������鹦��  
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);  

        //��ͼ����  
        gl.glFinish();  
	rotate+=0.3;
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
