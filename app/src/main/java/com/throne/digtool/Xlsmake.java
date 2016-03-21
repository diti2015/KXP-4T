package com.throne.digtool;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Xlsmake {
   XYZVo xyz;
  
    public Xlsmake(XYZVo xyz){
       this.xyz=xyz;
    }
    
    public void toxls(String name) throws IOException, RowsExceededException, WriteException{
        Log.i("nanhu", name);
        String [] s=name.split("[.]");
        Log.i("nanhu", String.valueOf(s.length));
        String fname=s[0]+".xls";
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(4);
        File destDir;
   
            destDir = new File(Environment
                    .getExternalStorageDirectory()
                    .getCanonicalFile() + "/dragtoolxls");
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
    
        
        

            
            //  打开文件 
            WritableWorkbook book  =  Workbook.createWorkbook( new  File( Environment
					.getExternalStorageDirectory()
					.getCanonicalFile() + "/dragtoolxls/"+fname));
           
            WritableSheet sheet  =  book.createSheet( " 第一表" ,  0 );
            //  在Label对象的构造子中指名单元格位置是第�?��第一�?0,0)
            //  以及单元格内容为test 倾角(�?  方位(�?   偏移Lx    偏移Ly    深度Z 偏移L
            Label label0  =   new  Label( 0 ,  0 ,  " 时间(m) " );
            Label label1  =   new  Label( 1 ,  0 ,  " 深度(m) " );
            Label label2  =   new  Label( 2 ,  0 ,  " 倾角(°) " );
            Label label3  =   new  Label( 3 ,  0 ,  " 方位(°)" );
            Label label4  =   new  Label( 4 ,  0 ,  " 偏移Lx " );
            Label label5  =   new  Label( 5 ,  0 ,  " 偏移Ly " );
            Label label6  =   new  Label( 6 ,  0 ,  " 深度Z " );
            Label label7  =   new  Label( 7 ,  0 ,  " 偏移L " );
            
            //  将定义好的单元格添加到工作表�?
            sheet.addCell(label0);
            sheet.addCell(label1);
            sheet.addCell(label2);
            sheet.addCell(label3);
            sheet.addCell(label4);
            sheet.addCell(label5);
            sheet.addCell(label6);
            sheet.addCell(label7);
            float[] x= xyz.getX();
            float[] y=xyz.getY();
            float[] z=xyz.getZ();
            float[] l=xyz.getL();
            float[] txt=xyz.getTxtread();
            for(int i=0;i<x.length;i++)
            {
                for(int j=0;j<8;j++){
                    if(j<4){
              //  jxl.write.Number number  = new  jxl.write.Number( j, i+1 , txt[i*4+j]);
                        Label number  = new   Label( j, i+1 , String.valueOf(txt[i*4+j]));
                sheet.addCell(number);
                    }
                    else{
                        nf.setMaximumFractionDigits(3);
                        Label number1  = new  Label( j, i+1 , String.valueOf(nf.format(x[i])));
                        Label number2  = new  Label( j+1, i+1 ,  String.valueOf(nf.format(y[i])));
                        Label number3  = new  Label( j+2, i+1 ,  String.valueOf(nf.format(z[i])));
                        nf.setMaximumFractionDigits(5);
                        Label number4  = new  Label( j+3, i+1 ,  String.valueOf(nf.format(l[i])));
//                        jxl.write.Number number1  = new  jxl.write.Number( j, i+1 , Float.valueOf(nf.format(x[i])));
//                        jxl.write.Number number2  = new  jxl.write.Number( j+1, i+1 ,  Float.valueOf(nf.format(y[i])));
//                        jxl.write.Number number3  = new  jxl.write.Number( j+2, i+1 ,  Float.valueOf(nf.format(z[i])));
//                        jxl.write.Number number4  = new  jxl.write.Number( j+3, i+1 ,  Float.valueOf(nf.format(l[i])));
                        sheet.addCell(number1);  
                        sheet.addCell(number2);
                        sheet.addCell(number3);
                        sheet.addCell(number4);
                        break;
                    }
                
                }
            }
            /* 
            * 生成�?��保存数字的单元格 必须使用Number的完整包路径，否则有语法歧义 单元格位置是第二列，第一行，值为789.123
             */ 
//           jxl.write.Number number  =   new  jxl.write.Number( 1 ,  0 ,  555.12541 );
//           sheet.addCell(number);

            //  写入数据并关闭文�?
            book.write();
           book.close();

    }
}
