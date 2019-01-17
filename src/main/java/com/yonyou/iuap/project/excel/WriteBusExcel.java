package com.yonyou.iuap.project.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;
import com.yonyou.iuap.project.entity.Bus;
import com.yonyou.iuap.project.entity.BusColor;

/**
 * 车辆导出处理类
 * Created by zhugaofeng on 2018/12/19.
 *
 */
public class WriteBusExcel {
	
	private Gson gson=new Gson();

    /**
     * 创建excel
     * @param busMap
     * @param path
     * @return
     */
    public String createExcelXlsx(Map<String,List<String>> busMap, String path){
        // 创建文件输出流
        FileOutputStream fos;

        // 行变量
        //XSSFRow row;
        //唯一sheet行
        XSSFRow rowOnly;
        //必填sheet行
        XSSFRow rowRequired;

        // 列变量
        //XSSFCell cell;
        XSSFCell cellOnly;
        XSSFCell cellRequired;

        // 创建一个excel文件，XSSF处理07以上版本
        XSSFWorkbook wookbook = new XSSFWorkbook();

        // 创建一个Sheet
        //XSSFSheet sheet = wookbook.createSheet("相似度比较结果");
        // 创建第二个sheet
        XSSFSheet sheetOnly = wookbook.createSheet("唯一性校验结果");
        // 创建第三个sheet
        XSSFSheet sheetRequired = wookbook.createSheet("完整性校验结果");

        // 创建单元格样式
        XSSFCellStyle style = wookbook.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER); 			//水平居中
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);	//垂直居中
        style.setWrapText(true);	//自动换行

        // 四周边框
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        XSSFFont font1 = wookbook.createFont();
        font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font1);

        //创建一个字体对象
        XSSFFont font = wookbook.createFont();
        font.setFontHeightInPoints((short) 12);				//设置字号
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);     //加粗

        //创建一个用于存放字体的样式
        XSSFCellStyle headerStyle = wookbook.createCellStyle();
        headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); 			//水平居中
        headerStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);	//垂直居中
        headerStyle.setWrapText(true);	//自动换行
        headerStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        headerStyle.setFont(font);

        //row = sheet.createRow(0);
        //第二个sheet创建首行
        rowOnly =sheetOnly.createRow(0);
        //第三个sheet创建首行
        rowRequired =sheetRequired.createRow(0);
        /**
         * 虽然表头第一行可以只创建一个单元格，然后设置内容。但是合并之后，样式就消失了，因为只给一个单元格设置了样式。
         * 所以要为每一个单元格都设置样式
         */
        /*for(int i = 0; i < 5; i++){
            cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            if(i == 0){
                cell.setCellValue("车辆-相似度比较结果");
            }
        }*/
        for(int i = 0; i < 3; i++){
            cellOnly = rowOnly.createCell(i);
            cellOnly.setCellStyle(headerStyle);
            if(i == 0){
                cellOnly.setCellValue("车辆-唯一性校验结果");
            }
        }
        for(int i = 0; i < 38; i++){
            cellRequired= rowRequired.createCell(i);
            cellRequired.setCellStyle(headerStyle);
            if(i == 0){
                cellRequired.setCellValue("车辆-完整性校验结果");
            }
        }

        // 创建主表Excel的第二行
        //row = sheet.createRow(1);
        rowOnly = sheetOnly.createRow(1);
        rowRequired = sheetRequired.createRow(1);
        
        for(int i = 0; i < 3; i++){
            // = row.createCell(i);
            //cell.setCellStyle(style);

        	cellOnly = rowOnly.createCell(i);
            cellOnly.setCellStyle(style);

            switch(i){
                case 0 : setRowHeadValue(cellOnly,"车辆编码");break;
                case 1 : setRowHeadValue(cellOnly,"车辆号牌");break;
                case 2 : setRowHeadValue(cellOnly,"车辆名称");break;                      
                default :break;
            }
        }

        for(int i = 0; i < 38; i++){
            // = row.createCell(i);
            //cell.setCellStyle(style);

            //cellOnly = rowOnly.createCell(i);
            //cellOnly.setCellStyle(style);

            cellRequired = rowRequired.createCell(i);
            cellRequired.setCellStyle(style);

            switch(i){
                case 0 : setRowHeadValue(cellRequired,"车辆编码");break;
                case 1 : setRowHeadValue(cellRequired,"车辆颜色");break;
                case 2 : setRowHeadValue(cellRequired,"车辆号牌");break;       
                case 3 : setRowHeadValue(cellRequired,"车辆所属单位");break; 
                case 4 : setRowHeadValue(cellRequired,"车辆使用单位");break; 
                case 5 : setRowHeadValue(cellRequired,"厂牌");break;       
                case 6 : setRowHeadValue(cellRequired,"车辆型号");break; 
                case 7 : setRowHeadValue(cellRequired,"车辆类型");break;
                case 8 : setRowHeadValue(cellRequired,"车辆营运类别");break;       
                case 9 : setRowHeadValue(cellRequired,"变速箱厂牌");break; 
                case 10 : setRowHeadValue(cellRequired,"变速箱型号");break;
                case 11 : setRowHeadValue(cellRequired,"发动机型号");break;       
                case 12 : setRowHeadValue(cellRequired,"VIN(或车架)号");break; 
                case 13 : setRowHeadValue(cellRequired,"燃油类型");break;
                case 14 : setRowHeadValue(cellRequired,"LNG供气系统");break;       
                case 15 : setRowHeadValue(cellRequired,"电池类型");break; 
                case 16 : setRowHeadValue(cellRequired,"电池厂牌");break;
                case 17 : setRowHeadValue(cellRequired,"电机厂牌");break;       
                case 18 : setRowHeadValue(cellRequired,"空调品牌");break; 
                case 19 : setRowHeadValue(cellRequired,"前桥厂牌");break;
                case 20 : setRowHeadValue(cellRequired,"前桥吨位");break;       
                case 21 : setRowHeadValue(cellRequired,"后桥品牌");break; 
                case 22 : setRowHeadValue(cellRequired,"后桥吨位");break;
                case 23 : setRowHeadValue(cellRequired,"车长");break;       
                case 24 : setRowHeadValue(cellRequired,"客车等级");break; 
                case 25 : setRowHeadValue(cellRequired,"司乘座位数");break;
                case 26 : setRowHeadValue(cellRequired,"核定载客量");break;       
                case 27 : setRowHeadValue(cellRequired,"车辆经营类别");break; 
                case 28 : setRowHeadValue(cellRequired,"车辆注册日期");break;
                case 29 : setRowHeadValue(cellRequired,"排放标准");break;       
                case 30 : setRowHeadValue(cellRequired,"缓速器厂牌");break; 
                case 31 : setRowHeadValue(cellRequired,"CAN总线厂牌");break;
                case 32 : setRowHeadValue(cellRequired,"底盘集中润滑");break;       
                case 33 : setRowHeadValue(cellRequired,"发动机厂牌");break; 
                case 34 : setRowHeadValue(cellRequired,"电动空调厂牌");break;
                case 35 : setRowHeadValue(cellRequired,"电动动力转向系统厂牌");break;       
                case 36 : setRowHeadValue(cellRequired,"电动空压机厂牌");break; 
                case 37 : setRowHeadValue(cellRequired,"电控系统厂牌");break;
                default :break;
            }
        }

        //合并表头
        //sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        sheetOnly.addMergedRegion(new CellRangeAddress(0,0,0,4));
        sheetRequired.addMergedRegion(new CellRangeAddress(0,0,0,4));

        //sheet.getRow(0).setHeight((short) (2*256));
        sheetOnly.getRow(0).setHeight((short) (2*256));
        sheetRequired.getRow(0).setHeight((short) (2*256));

        //int rowIndex = 2;
        int rowOnlyIndex = 2;
        int rowRequiredIndex =2;

        //格式化数据并填充
        //List<String> compareData=busMap.get("compareData");
        //this.setRowContent(sheet, rowIndex,compareData);

        List<String> onlyData=busMap.get("onlyData");
        this.setOnlyRowContent(sheetOnly, rowOnlyIndex,onlyData);

        List<String> requiredData=busMap.get("requiredData");
        this.setRequiredRowContent(sheetRequired, rowRequiredIndex,requiredData);

        //自适应宽度
        /*for(int i=0;i<sheet.getRow(0).getPhysicalNumberOfCells();i++){
            ExcelUtil.autoSizeColumn(i,sheet);
        }
        //自适应宽度
        for(int i=0;i<sheet.getRow(0).getPhysicalNumberOfCells();i++){
            ExcelUtil.autoSizeColumn(i,sheetOnly);
        }
        //自适应宽度
        for(int i=0;i<sheet.getRow(0).getPhysicalNumberOfCells();i++){
            ExcelUtil.autoSizeColumn(i,sheetRequired);
        }*/

        try {
            fos = new FileOutputStream(path);
            wookbook.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    /**
     * 设置表体行元素
     * @param row
     * @param num
     * @param obj
     */
    private void setRowValue(XSSFRow row, int num, String obj){
        if(obj == null || "".equals(obj)){
            row.createCell(num).setCellValue("");
        }else{
            row.createCell(num).setCellValue(obj);
        }
    }
    
    /*private void setRowDateValue(XSSFRow row, int num, Date obj){
        if(obj == null || "".equals(obj)){
            row.createCell(num).setCellValue("");
        }else{
            row.createCell(num).setCellValue(obj);
        }
    }*/

    /**
     * 设置三个表头
     * @param cell
     * @param value
     */
    /*private void setRowHeadValue(XSSFCell cell,String value){
        cell.setCellValue(value);
    }*/

    private void setRowHeadValue(XSSFCell cellOnly,XSSFCell cellRequired,String value){
        cellOnly.setCellValue(value);
        cellRequired.setCellValue(value);
    }

    private void setRowHeadValue(XSSFCell cell,XSSFCell cellOnly,XSSFCell cellRequired,String value){
        cell.setCellValue(value);
        cellOnly.setCellValue(value);
        cellRequired.setCellValue(value);
    }
    
    private void setRowHeadValue(XSSFCell cellRequired,String value){
       
        cellRequired.setCellValue(value);
    }

    /**
     * 设置表体内容
     * @param sheet
     * @param rowIndex
     * @param data
     */
    private void setOnlyRowContent(XSSFSheet sheet, int rowIndex, List<String> data){
        if(!data.isEmpty()&&data.size()>0){
            for (int i=0;i<data.size();i++){
            	Bus bus=gson.fromJson(data.get(i),Bus.class);

                XSSFRow row = sheet.createRow(rowIndex);

                this.setRowValue(row,0,bus.getCode());
                this.setRowValue(row,1,bus.getBus_lisencenum());
                this.setRowValue(row,2,bus.getName());
                                              

                rowIndex++;
            }
        }
    }

    /**
     * 设置表体内容
     * @param sheet
     * @param rowIndex
     * @param data
     */
    private void setRequiredRowContent(XSSFSheet sheet, int rowIndex, List<String> data){
        if(!data.isEmpty()&&data.size()>0){
            for (int i=0;i<data.size();i++){
                Bus bus=gson.fromJson(data.get(i),Bus.class);
                
                XSSFRow row = sheet.createRow(rowIndex);

                this.setRowValue(row,0,bus.getCode());
                this.setRowValue(row,1,bus.getColor()); 
                this.setRowValue(row,2,bus.getBus_lisencenum());                              
                this.setRowValue(row,3,bus.getName1());
                this.setRowValue(row,4,bus.getName2());
                this.setRowValue(row,5,bus.getBus_brand()); 
                this.setRowValue(row,6,bus.getBus_model());                              
                this.setRowValue(row,7,bus.getBus_type());
                this.setRowValue(row,8,bus.getBus_oprationtype());
                this.setRowValue(row,9,bus.getBus_boxbrand()); 
                this.setRowValue(row,10,bus.getBus_boxmodel());                              
                this.setRowValue(row,11,bus.getBus_engin());
                this.setRowValue(row,12,bus.getBus_framid());
                this.setRowValue(row,13,bus.getBus_oiltype()); 
                this.setRowValue(row,14,bus.getBus_lngsystem());                              
                this.setRowValue(row,15,bus.getBus_batterytype());
                this.setRowValue(row,16,bus.getBus_batterybrand());
                this.setRowValue(row,17,bus.getBus_motocbrand());
                this.setRowValue(row,18,bus.getBus_airbrand()); 
                this.setRowValue(row,19,bus.getBus_frontbrand());                              
                this.setRowValue(row,20,bus.getBus_frontton());
                this.setRowValue(row,21,bus.getBus_rearbrand());
                this.setRowValue(row,22,bus.getBus_rearton());
                this.setRowValue(row,23,bus.getBus_length()); 
                this.setRowValue(row,24,bus.getBus_level());                              
                this.setRowValue(row,25,bus.getBus_seat());
                this.setRowValue(row,26,bus.getBus_loadcustomer());
                this.setRowValue(row,27,bus.getBus_commercialtype()); 
                this.setRowValue(row,28,bus.getBus_registdate());                              
                this.setRowValue(row,29,bus.getBus_discharge_standard());
                this.setRowValue(row,30,bus.getBus_retarder_brand());
                this.setRowValue(row,31,bus.getBus_can_brand()); 
                this.setRowValue(row,32,bus.getBus_chassis());                              
                this.setRowValue(row,33,bus.getBus_engin_brand());
                this.setRowValue(row,34,bus.getBus_bev_airbrand());
                this.setRowValue(row,35,bus.getBus_bev_steerbrand()); 
                this.setRowValue(row,36,bus.getBus_bev_acrbrand());                              
                this.setRowValue(row,37,bus.getBus_ecu_brand());

                rowIndex++;
            }
        }
    }

}
