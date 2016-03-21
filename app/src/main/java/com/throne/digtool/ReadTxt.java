package com.throne.digtool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.text.NumberFormat;

import javax.xml.xpath.XPath;

import android.os.Environment;

public class ReadTxt {
  
   	float[] numbers;
	private boolean[] tf;

	private String filename;
	  public boolean[] getTf() {
	        return tf;
	    }
	    public void setTf(boolean[] tf) {
	        this.tf = tf;
	    }
	   public ReadTxt(String string) {
		// TODO Auto-generated constructor stub
		   filename=string;
	}
	public  float[] getfloatnumbers(String[] s)
	    {
	    	float[] numbers;
	    	   numbers=new float[s.length];
	    	   tf=new boolean[s.length];
	           for(int i=0;i<s.length;i++)
	           {
	        	   if(i%4==0){
	        		   numbers[i]=Float.valueOf(s[i]);
	        	   }
                    if(i%4==1){
                    	numbers[i]=Float.valueOf(s[i]);
	        	   }
                    if(i%4==2){
                    	numbers[i]=(float) (Float.valueOf(s[i])/100);//倾角
                      //  numbers[i]=Float.valueOf(s[i]);
 	        	   }
                    if(i%4==3){
                    	numbers[i]=(float) (Float.valueOf(s[i])/10);//方位
                    //	numbers[i]=Float.valueOf(s[i]);
  	        	   }
	        	 
                    
                    //这里判断是不是越界
	        	   if(true)
	        	   {
	        	       tf[i]=false;
	        	   }
	        	   else
	        	   {
	        	       tf[i]=true;
	        	   }
	        	   
	           }
			return numbers;
	    	
	    }
	   @SuppressWarnings("resource")
	public String[] getStrings()
	   {
		   BufferedReader f;
		try {
			f = new BufferedReader( new InputStreamReader(new FileInputStream(new File(Environment
					.getExternalStorageDirectory()
					.getCanonicalFile() + "/dragtool/"+filename))));
		
		 String lineTxt = null;
		 String num="";
		
		    while((lineTxt = f.readLine()) != null){
		        
		         num=num+lineTxt;
		         
     }
		   String[] s= num.split("#");
		   f.close();
		   return s;
		 } catch (IOException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		     return null;
		 }
			   
	   }
       public float[] getX(float[] r)
       {
    	float[] X=new float[r.length/4];
    	   for(int i=3,j=0;i<r.length;i=i+4,j++)
            {
          	   if(i<4)
          	   {
          		 X[j]= (float)(r[i-2]*Math.sin((double)(r[i-1]*0.01745))*Math.cos((double)(r[i]*0.01745)));
          	   }
          	   else
          	   {
          	     //X[j]=(float)(r[i-2]*Math.sin((double)(r[i-1]*0.01745))*Math.cos((double)(r[i]*0.01745)));
          		  X[j]=(float)((r[i-2]-r[i-6])*Math.sin((double)(r[i-1]*0.01745))*Math.cos((double)(r[i]*0.01745))+X[j-1]);
          	   }
		
    	   
       }
    	   return X;
       }
       public float[] getY(float[] r)
       {
           float[] Y=new float[r.length/4];
           for(int i=3,j=0;i<r.length;i=i+4,j++)
            {
               if(i<4)
               {
                 Y[j]= (float)(r[i-2]*Math.sin((double)(r[i-1]*0.01745))*Math.sin((double)(r[i]*0.01745)));
                 
               }
               else
               {
                   //(A4-A3)*SIN(B4*0.01745)*SIN(C4*0.01745)+E3
               
                  Y[j]=(float)((r[i-2]-r[i-6])*Math.sin((double)(r[i-1]*0.01745))*Math.sin((double)(r[i]*0.01745))+Y[j-1]);
               }
        
           
       }
           return Y;
       }
       public float[] getZ(float[] r){
           float[] Z=new float[r.length/4];
           for(int i=3,j=0;i<r.length;i=i+4,j++)
            {
               if(i<4)
               {
                 //  A3*COS(B3*3.1416/180)
                 Z[j]= (float)(r[i-2]*Math.cos((double)(r[i-1]*(3.1416/180))));
                 
               }
               else
               {
                 
                  // (A4-A3)*COS(B4*3.1416/180)+F3
                  Z[j]=(float)((r[i-2]-r[i-6])*Math.cos((double)(r[i-1]*(3.1416/180)))+Z[j-1]);
               }
        
           
       }
           return Z;
       }
       public float[] getL(float[] r,float[] x,float[] y)
       {
           float[] L=new float[r.length/4];
       for(int i=2,j=0;i<r.length;i=i+4,j++)
       {
            //  SQRT(D4*D4+E4*E4)
            L[j]= (float)(Math.sqrt((double)(x[j]*x[j]+y[j]*y[j])));    
  }
      return L;
       }
       /**
     * @param args
     */
//    public static void main(String[] args) {
//    	ReadTxt r=new ReadTxt();
//    	       XYZVo xyz;
//               r.numbers=r.getfloatnumbers(r.getStrings());
//               xyz=new XYZVo(r.numbers.length/3);
//               xyz.setTxtread(r.numbers);
//               xyz.setX(r.getX(r.numbers));
//               xyz.setY(r.getY(r.numbers));
//               xyz.setZ(r.getZ(r.numbers));
//               xyz.setL(r.getL(r.numbers,xyz.getX(),xyz.getY()));
//               Xlsmake xls=new Xlsmake(xyz);
//               xls.toxls();
//              float[] x= xyz.getX();
//              float[] y=xyz.getY();
//              float[] z=xyz.getZ();
//              float[] l=xyz.getL();
//              float[] txt=xyz.getTxtread();
//               for(int i=0;i<x.length;i++)
//               {
//                   NumberFormat nf = NumberFormat.getNumberInstance();
//                   nf.setMaximumFractionDigits(4);
//                   System.out.println(txt[i*3]+" "+txt[i*3+1]+" "+txt[i*3+2]+" "+nf.format(x[i])+" "+nf.format(y[i])+" "+nf.format(z[i])+" "+nf.format(l[i]));    
//               }
//              for(int i=0;i<r.numbers.length;i++)
//              {
//            	 
//            	     System.out.print(r.numbers[i]+"f,");
//              }
               
//               //获得全部�?
//               for(int i=0;i<s.length;i++)
//               {
//                   if((i-1)%3==0)
//                   {
//                       System.out.print("-"+s[i+1].trim()+"f,");
//                       System.out.print(s[i].trim()+"f,");
//                       i++;
//                   }
//                   else
//                   System.out.print(s[i].trim()+"f,");
//                   if(i!=0&&(i+1)%3==0)
//                   {
//                       System.out.println();
//                   }
//                   
//                   
//               }
//               for(int i=0;i<s.length;i++)
//               {
//                   if(i!=0&&(i+1)%3==0)
//                   {
//                       System.out.println();
//                   }
//                   else
//                   {
//                       System.out.print(s[i].trim()+"f,");
//                   }
//                   
//               }
//               
   

//}
	
 
}
