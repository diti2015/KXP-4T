package com.throne.digtool;

import java.io.Serializable;

public class XYZVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float[] txtread;
	private boolean[] tf;
	private boolean[] t0f;
	private boolean[] t1f;
	private boolean[] t2f;
	private boolean[] t3f;
	
	private float[] X;
	private float[] Y;
	private float[] Z;
	private float[] L;
	public XYZVo(int s)
	
	{
	    setTxtread(new float[s*4]);
		X=new float[s];
		Y=new float[s];
		Z=new float[s];
		L=new float[s];
		setT0f(new boolean[s]);
		setT1f(new boolean[s]);
		setT2f(new boolean[s]);
		setT3f(new boolean[s]);
		
	}
	public float[] getX() {
		return X;
	}
	public void setX(float[] x) {
		X = x;
	}
	public float[] getY() {
		return Y;
	}
	public void setY(float[] y) {
		Y = y;
	}
	public float[] getZ() {
		return Z;
	}
	public void setZ(float[] z) {
		Z = z;
	}
	public float[] getL() {
		return L;
	}
	public void setL(float[] l) {
		L = l;
	}
    public float[] getTxtread() {
        return txtread;
    }
    public void setTxtread(float[] txtread) {
        this.txtread = txtread;
    }
    public boolean[] getT0f() {
        return t0f;
    }
    public void setT0f(boolean[] t0f) {
        this.t0f = t0f;
    }
    public boolean[] getT1f() {
        return t1f;
    }
    public void setT1f(boolean[] t1f) {
        this.t1f = t1f;
    }
    public boolean[] getT2f() {
        return t2f;
    }
    public void setT2f(boolean[] t2f) {
        this.t2f = t2f;
    }
    public boolean[] getT3f() {
        return t3f;
    }
    public void setT3f(boolean[] t3f) {
        this.t3f = t3f;
    }
    public boolean[] getTf() {
        return tf;
    }
    public void setTf(boolean[] tf) {
        this.tf = tf;
    }

	

}
