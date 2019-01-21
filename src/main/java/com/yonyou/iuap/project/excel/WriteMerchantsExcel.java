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
import com.yonyou.iuap.project.entity.Merchants;

/**
 * 客商导出处理类
 * Created by zhugaofeng on 2018/12/20.
 *
 */
public class WriteMerchantsExcel {
	
	private Gson gson=new Gson();

    /**
     * 创建excel
     * @param busMap
     * @param path
     * @return
     */
    public String createExcelXlsx(Map<String,List<String>> merchantsMap, String path){
        // 创建文件输出流
        FileOutputStream fos;

        // 行变量
        XSSFRow row;
        //唯一sheet行
        XSSFRow rowOnly;
        //必填sheet行
        XSSFRow rowRequired;

        // 列变量
        XSSFCell cell;
        XSSFCell cellOnly;
        XSSFCell cellRequired;

        // 创建一个excel文件，XSSF处理07以上版本
        XSSFWorkbook wookbook = new XSSFWorkbook();

        // 创建一个Sheet
        XSSFSheet sheet = wookbook.createSheet("相似度比较结果");
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

        row = sheet.createRow(0);
        //第二个sheet创建首行
        rowOnly =sheetOnly.createRow(0);
        //第三个sheet创建首行
        rowRequired =sheetRequired.createRow(0);
        /**
         * 虽然表头第一行可以只创建一个单元格，然后设置内容。但是合并之后，样式就消失了，因为只给一个单元格设置了样式。
         * 所以要为每一个单元格都设置样式
         */
        for(int i = 0; i < 13; i++){
            cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            if(i == 0){
                cell.setCellValue("客商-相似度比较结果");
            }
        }
        for(int i = 0; i < 13; i++){
            cellOnly = rowOnly.createCell(i);
            cellOnly.setCellStyle(headerStyle);
            if(i == 0){
                cellOnly.setCellValue("客商-唯一性校验结果");
            }
        }
        for(int i = 0; i < 13; i++){
            cellRequired= rowRequired.createCell(i);
            cellRequired.setCellStyle(headerStyle);
            if(i == 0){
                cellRequired.setCellValue("客商-完整性校验结果");
            }
        }

        // 创建主表Excel的第二行
        row = sheet.createRow(1);
        rowOnly = sheetOnly.createRow(1);
        rowRequired = sheetRequired.createRow(1);

        for(int i = 0; i < 13; i++){
            cell = row.createCell(i);
            cell.setCellStyle(style);

            cellOnly = rowOnly.createCell(i);
            cellOnly.setCellStyle(style);

            cellRequired = rowRequired.createCell(i);
            cellRequired.setCellStyle(style);

            switch(i){
                case 0 : setRowHeadValue(cell,cellOnly,cellRequired,"客商编码");break;
                case 1 : setRowHeadValue(cell,"相似度");setRowHeadValue(cellOnly,cellRequired,"客商名称");break;
                case 2 : setRowHeadValue(cell,"客商名称");setRowHeadValue(cellOnly,cellRequired,"客商简称");break;       
                case 3 : setRowHeadValue(cell,"客商简称");setRowHeadValue(cellOnly,cellRequired,"客商基本分类");break;
                case 4 : setRowHeadValue(cell,"客商基本分类");setRowHeadValue(cellOnly,cellRequired,"客商类型");break;
                case 5 : setRowHeadValue(cell,"客商类型");setRowHeadValue(cellOnly,cellRequired,"纳税人登记号");break;
                case 6 : setRowHeadValue(cell,"纳税人登记号");setRowHeadValue(cellOnly,cellRequired,"国家");break;                       
                case 7: setRowHeadValue(cell,"国家");setRowHeadValue(cellOnly,cellRequired,"省份");break;
                case 8: setRowHeadValue(cell,"省份");setRowHeadValue(cellOnly,cellRequired,"城市");break;
                case 9: setRowHeadValue(cell,"城市");setRowHeadValue(cellOnly,cellRequired,"县区");break;
                case 10: setRowHeadValue(cell,"县区");setRowHeadValue(cellOnly,cellRequired,"邮政编码");break;
                case 11: setRowHeadValue(cell,"邮政编码");setRowHeadValue(cellOnly,cellRequired,"详细地址");break;
                case 12: setRowHeadValue(cell,"详细地址");break;
            }
        }

        //合并表头
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        sheetOnly.addMergedRegion(new CellRangeAddress(0,0,0,4));
        sheetRequired.addMergedRegion(new CellRangeAddress(0,0,0,4));

        sheet.getRow(0).setHeight((short) (2*256));
        sheetOnly.getRow(0).setHeight((short) (2*256));
        sheetRequired.getRow(0).setHeight((short) (2*256));

        int rowIndex = 2;
        int rowOnlyIndex = 2;
        int rowRequiredIndex =2;

        //格式化数据并填充
        List<String> compareData=merchantsMap.get("compareData");
        this.setRowContent(sheet, rowIndex,compareData);

        List<String> onlyData=merchantsMap.get("onlyData");
        this.setOnlyRowContent(sheetOnly, rowOnlyIndex,onlyData);

        List<String> requiredData=merchantsMap.get("requiredData");
        this.setOnlyRowContent(sheetRequired, rowRequiredIndex,requiredData);

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
    private void setRowHeadValue(XSSFCell cell,String value){
        cell.setCellValue(value);
    }

    private void setRowHeadValue(XSSFCell cellOnly,XSSFCell cellRequired,String value){
        cellOnly.setCellValue(value);
        cellRequired.setCellValue(value);
    }

    private void setRowHeadValue(XSSFCell cell,XSSFCell cellOnly,XSSFCell cellRequired,String value){
        cell.setCellValue(value);
        cellOnly.setCellValue(value);
        cellRequired.setCellValue(value);
    }

    /**
     * 设置表体内容
     * @param sheet
     * @param rowIndex
     * @param data
     */
    private void setRowContent(XSSFSheet sheet, int rowIndex, List<String> data){
        if(!data.isEmpty()&&data.size()>0){
            for (int i=0;i<data.size();i++){
                Merchants merchants=gson.fromJson(data.get(i),Merchants.class);

                XSSFRow row = sheet.createRow(rowIndex);

                this.setRowValue(row,0,merchants.getCode());
                this.setRowValue(row,1,merchants.getSimilarity());
                this.setRowValue(row,2,merchants.getName());                
                this.setRowValue(row,3,merchants.getCust_supplier_shortname());  
                this.setRowValue(row,4,merchants.getCustsupplier_category());
                this.setRowValue(row,5,merchants.getCustsuptype());
                this.setRowValue(row,6,merchants.getTaxpayerid());
                this.setRowValue(row,7,merchants.getCountry());
                this.setRowValue(row,8,merchants.getProvince());                
                this.setRowValue(row,9,merchants.getCity());
                this.setRowValue(row,10,merchants.getCounty_area());
                this.setRowValue(row,11,merchants.getPostalcode());
                this.setRowValue(row,12,merchants.getAddress());      

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
    private void setOnlyRowContent(XSSFSheet sheet, int rowIndex, List<String> data){
        if(!data.isEmpty()&&data.size()>0){
            for (int i=0;i<data.size();i++){
                Merchants merchants=gson.fromJson(data.get(i),Merchants.class);

                XSSFRow row = sheet.createRow(rowIndex);

                this.setRowValue(row,0,merchants.getCode());
                this.setRowValue(row,1,merchants.getName());               
                this.setRowValue(row,2,merchants.getCust_supplier_shortname());  
                this.setRowValue(row,3,merchants.getCustsupplier_category());
                this.setRowValue(row,4,merchants.getCustsuptype());
                this.setRowValue(row,5,merchants.getTaxpayerid());
                this.setRowValue(row,6,merchants.getCountry());
                this.setRowValue(row,7,merchants.getProvince());                
                this.setRowValue(row,8,merchants.getCity());
                this.setRowValue(row,9,merchants.getCounty_area());
                this.setRowValue(row,10,merchants.getPostalcode());
                this.setRowValue(row,11,merchants.getAddress()); 

                rowIndex++;
            }
        }
    }

}
