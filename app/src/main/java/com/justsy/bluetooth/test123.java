package com.justsy.bluetooth;

import java.lang.reflect.Field;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
  
public class test123 {  
  
    public static void main(String[] args) throws Exception {  
        String[] sr = new String[]{"序号#seq,问题#question,答案#ans","1,问题1,abc","2,问题2,def"};  
        buildT(test123.class, sr);
    }  
      
    public static <T> void buildT(Class clazz, String[] sr) throws Exception {
        String[] sa = sr[0].split(";");  
        String s = null;  
        Map<Integer, String> map = new HashMap<Integer, String>();  
        for(int i = 0, len = sa.length; i < len; i++){  
            s = sa[i];  
            String[] _sa = s.split("=");  
            map.put(i, _sa[1]);  
        }  
        for(int i = 1, len = sr.length; i < len; i++){  
            s = sr[i];  
            String[] _sa = s.split(",");  
            T t = (T) clazz.newInstance();
              
            for(int j = 0; j < _sa.length; j++){  
                Field fi = clazz.getDeclaredField(map.get(j));  
                fi.setAccessible(true);  
                fi.set(t, _sa[j]);  
            } 
        }  
    }  
}  