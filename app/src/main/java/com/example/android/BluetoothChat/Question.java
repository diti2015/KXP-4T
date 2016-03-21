package com.example.android.BluetoothChat;

import java.io.Serializable;  
  
public class Question implements Serializable {  
  
	private int threshold;  
    private int mvd;  
    private int mvv;
    private int gvd;
    private int gvv;
    private int mdd;
    private int mdv;
    
    
  
    public int getThreshold() {  
        return threshold;
    }  
    public void setThreshold(int threshold) {  
        this.threshold = threshold;  
    }  
    
    public int getMvd() {  
        return mvd;
    }  
    public void setMvd(int mvd) {  
        this.mvd = mvd;
    }
    
    public int getMvv() {  
        return mvv;
    }  
    public void setMvv(int mvv) {  
        this.mvv = mvv;  
    }
    
    public int getGvd() {  
        return gvd;
    }  
    public void setGvd(int gvd) {  
        this.gvd = gvd;
    }
    
    public int getGvv() {  
        return gvv;
    }  
    public void setGvv(int gvv) {  
        this.gvv = gvv;  
    }
    
    public int getMdd() {  
        return mdd;
    }  
    public void setMdd(int mdd) {  
        this.mdd = mdd;  
    }
    
    public int getMdv() {  
        return mdv;
    }  
    public void setMdv(int mdv) {  
        this.mdv = mdv;  
    }
    
    
    private static final long serialVersionUID = 1L;  
    
    private String seq;  
      
    private String question;  
      
    private String ans;  
  
    public String getAns() {  
        return ans;  
    }  
  
    public void setAns(String ans) {  
        this.ans = ans;  
    }  
  
    public String getSeq() {  
        return seq;  
    }  
  
    public void setSeq(String seq) {  
        this.seq = seq;  
    }  
  
    public String getQuestion() {  
        return question;  
    }  
  
    public void setQuestion(String question) {  
        this.question = question;  
    }
      
}
