package com.throne.digtool;

public class GetCirclePoint {
  

    
    private static float[] vertices ;
    public static float[] getpoint(int width){
        vertices = new float[720];
        for (int i = 0; i < 720; i += 2) {  
            // x value 
           
           
            vertices[i]   = (float) (Math.cos(DegToRad(i)) * width);  
            // y value  
            vertices[i+1] = (float) (Math.sin(DegToRad(i)) * width);
            
     
        }  
        return vertices;
        
    }
    public static float DegToRad(float deg)  
    {  
        return (float) (3.14159265358979323846 * deg / 180.0);  
    }  

    
}
