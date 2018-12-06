package com.yonyou.iuap.project.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 导出EXCEL中文字符自适应宽度
 * @author XiongYi
 *
 */
public class ExcelUtil {
	
	public static void main(String[] args){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		System.out.println(sdf.format(date));

	}
		
	public static void autoSizeColumn(int column,XSSFSheet sheet) {
	        double width = getColumnWidth(sheet, column);

	        if (width != -1) {
	        	if(width>50){
	        		width=50;
	        	}
	            width *= 256;
	            sheet.setColumnWidth(column, (int)(width));	            
	        }
	}
	
	private static double getColumnWidth(Sheet sheet, int column){

        double width = -1;
        int rowNum=sheet.getLastRowNum();
        if(rowNum>35){
        	rowNum=35;
        }
        for (int i=1;i<=rowNum;i++) {
        	Row row=sheet.getRow(i);
            Cell cell = row.getCell(column);
            
            if (cell == null) {
                continue;
            }
            double cellWidth = length(cell.getStringCellValue());
            
            width = Math.max(width, cellWidth);
        }
        return width+0.5;
    }
	
	public static int length(String s) {  
	       if (s == null)  
	           return 0;  
	       char[] c = s.toCharArray();  
	       int len = 0;  
	       for (int i = 0; i < c.length; i++) {  
	           len++;  
	           if (!isLetter(c[i])) {  
	               len++;  
	           }  
	       }  
	       return len;  
	} 
	
	private static boolean isLetter(char c) {
	       int k = 0x80;   
	       return c / k == 0;
	}
	
}
