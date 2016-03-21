package com.example.android.BluetoothChat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class RWfile {
	
	public static void saveToSDCard(String filename,String content)throws Exception{
		  File file = new File(Environment.getExternalStorageDirectory()+"/dragtool/",filename);
		  FileOutputStream outStream = new FileOutputStream(file);
		  outStream.write(content.getBytes());
		  outStream.close();

    }

	
	public static String readFile(String fileName,Context context) throws IOException{   
	  String res="";   
	  
	  try{   
	         FileInputStream fin = context.openFileInput(fileName);
	         int length = fin.available();   
	         byte [] buffer = new byte[length];   
	         fin.read(buffer);       
	         res = EncodingUtils.getString(buffer, "UTF-8");   
	         fin.close();       
	     }   
	     catch(Exception e){   
	         e.printStackTrace();
	         writeFile(fileName,read(fileName,context),context);
	     }   
	     return res;   
	  
	}
	
	public static void writeFile(String fileName,String writestr,Context context) throws IOException{   
	  try{   
		  	
	       FileOutputStream fout = context.openFileOutput(fileName, Context.MODE_PRIVATE);
		  /*
		  FileOutputStream fout = context.openFileOutput( Environment
					.getExternalStorageDirectory()
					.getCanonicalFile() + "/dragtool/"+fileName, Context.MODE_PRIVATE);   
	  	*/
	        byte [] bytes = writestr.getBytes();   
	  
	        fout.write(bytes);   
	  
	        fout.close();   
	      }   
	  
	        catch(Exception e){   
	        	e.printStackTrace();   
	       }   
	}   
	
	//读assets
	public static String read(String fileName,Context context){
		//String fileName = "config.txt"; //文件名字 
		String res=""; 
		try{ 
		   //得到资源中的asset数据流
		   InputStream in = context.getResources().getAssets().open(fileName); 
		   int length = in.available();         
		   byte [] buffer = new byte[length];        
		   in.read(buffer);            
		   in.close();
		   res = EncodingUtils.getString(buffer, "UTF-8");
		   if(commond.D)Log.e("TAG", res);
		   return res;
		  }catch(Exception e){ 
		      e.printStackTrace();         
		   }
		return res;
	}
	
}
