 package com.example.android.BluetoothChat;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.util.Log;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class RotateRPolygon extends Activity
{ /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
          
        Renderer render = new GLRender();  
        GLSurfaceView glView = new GLSurfaceView(this);  
        glView.setRenderer(render);  
        setContentView(glView);  
    }  
}  
class GLRender implements Renderer{  
  
    private String TAG = "GLRender";  
    float roateTri;//用于三角形的角度  
    float roateQuad;//用于四边形的角度  
    int one = 0x10000;  
    /*//三角形三个顶点 
    private IntBuffer triggerBuffer = IntBuffer.wrap(new int[]{ 
        0,one,0, 
        -one,-one,0, 
        one,-one,0, 
    }); 
    //四边形四个顶点 
    private IntBuffer quaterBuffer = IntBuffer.wrap(new int[]{ 
            -one,one,0, 
            one,one,0, 
            one,-one,0, 
            -one,-one,0, 
    });*/  
    int [] colorArray = {  
            one,0,0,one,  
            0,one,0,one,  
            0,0,one,one,  
    };  
    int [] triggerArray ={  
            0,one,0,  
            -one,-one,0,  
            one,-one,0};  
    int []  quaterArray = {  
            one,one,0,  
            -one,one,0,  
            one,-one,0,  
            -one,-one,0  
    };  
      
    @Override  
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {  
        // TODO Auto-generated method stub  
        Log.i(TAG, "onSurfaceCreated");  
        //告诉系统对透视进行修正，会使透视图看起来好看点  
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);  
        //黑色背景  
        gl.glClearColor(0, 0, 0, 0);//红，绿，蓝，apaha  
        //启动阴影平滑   
        gl.glShadeModel(GL10.GL_SMOOTH);  
          
        //设置深度缓存  
        gl.glClearDepthf(1.0f);  
        //启用深度测试  
        gl.glEnable(GL10.GL_DEPTH_TEST);  
        //所做深度测试的类型  
        gl.glDepthFunc(GL10.GL_LEQUAL);  
    }  
  
    @Override  
    public void onSurfaceChanged(GL10 gl, int width, int height) {  
        // TODO Auto-generated method stub  
        Log.i(TAG, "onSurfaceChanged width:"+width+" height:"+height);//1920 944  
          
        float radio = (float)width/height;  
          
        //设置OpenGL场景的大小  
        gl.glViewport(0, 0, width, height);  
        //设置投影矩阵,投影矩阵负责为场景增加透视  
        gl.glMatrixMode(GL10.GL_PROJECTION);  
        //重置投影矩阵  
        gl.glLoadIdentity();  
        //设置视口的大小 前四个参数去顶窗口的大小，分别是左，右，下，上，后两个参数分别是在场景中所能绘制深度的起点和终点  
        gl.glFrustumf(-radio, radio, -1, 1, 1, 10);  
        //指明任何新的变换即那个会影响 模型观察矩阵  
        gl.glMatrixMode(GL10.GL_MODELVIEW);  
        gl.glLoadIdentity();  
          
    }  
  
    @Override  
    public void onDrawFrame(GL10 gl) {  
        // TODO Auto-generated method stub  
          
        Log.i("GLRender", "onDrawFrame");  
          
        roateTri +=0.5f;  
        roateQuad-=0.5f;  
          
        //清除屏幕和深度缓存  
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);  
        // 重置当前的模型观察矩阵  
        gl.glLoadIdentity();  
        //移动当前中心点，左移1.5单位，并移入屏幕6.0，y不变  
        //注意：屏幕内移动的单位数必须小于前面我们通过  
        //glFrustumf方法所设置的最远距离，否则显示不出来。  
        //腰围OpenGL设置一个顶点数组，故需要告诉OpenGL要设置  
        //顶点这个功能。  
        //开启顶点设置功能  
        gl.glTranslatef(-1.5f, 0.0f, -6.0f);  
          
        //设置某无题沿着指定的轴旋转  
        //参数1：旋转的角度  
        //后三个参数共通决定旋转的方向  
        //注意：要在画图前，使用旋转  
        gl.glRotatef(roateTri, 0.0f, -1.0f, 0.0f);  
          
        //开启颜色渲染功能  
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);  
        //设置颜色,平滑着色  
        gl.glColorPointer(4, GL10.GL_FIXED, 0, bufferUtil(colorArray));  
          
        //允许设置顶点  
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
        //设置三角形  
        //参数1：描述顶点的尺寸，本例中使用X,Y,Z坐标系，所以是3  
        //参数2：描述顶点的类型，本例中数据是固定的，所以使用了GL_FIXED表示固定顶点  
        //参数3：描述步长  
        //参数4：顶点缓存，即我们创建的顶点数组  
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, bufferUtil(triggerArray));  
        //绘制三角形  
        //参数1：绘制模式，GL_TRIANGLES：表示绘制三角形  
        //参数2：开始位置  
        //参数3：要绘制的顶点计数  
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);  
          
        //重置当前的模型观察矩阵  
        gl.glLoadIdentity();  
          
          
        //关闭颜色渲染  
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);  
          
        //左移1.5单位，并移入屏幕6.0  
        gl.glTranslatef(1.5f, 0.0f, -6.0f);  
          
        gl.glRotatef(roateQuad, 1.0f, 0.0f, 0.0f);  
          
        //开启颜色渲染功能  
        gl.glEnableClientState(GL10.GL_COLOR_BUFFER_BIT);  
        //设置颜色，单调着色 （r,g,b,a）  
        gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);  
          
        //设置和绘制正方形  
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, bufferUtil(quaterArray));  
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);  
          
        //关闭颜色渲染  
        gl.glDisableClientState(GL10.GL_COLOR_BUFFER_BIT);  
        //取消顶点设置  
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);  
          
    }  
    /* 
     * OpenGL 是一个非常底层的画图接口，它所使用的缓冲区存储结构是和我们的 java 程序中不相同的。 
     * Java 是大端字节序(BigEdian)，而 OpenGL 所需要的数据是小端字节序(LittleEdian)。 
     * 所以，我们在将 Java 的缓冲区转化为 OpenGL 可用的缓冲区时需要作一些工作。建立buff的方法如下 
     * */  
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
}