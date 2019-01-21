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
import com.yonyou.iuap.project.entity.Stores;

/**
 * 便利店导出处理类
 * Created by zhugaofeng on 2018/12/19.
 *
 */
public class WriteStoresExcel {

	private Gson gson=new Gson();

    /**
     * 创建excel
     * @param storesMap
     * @param path
     * @return
     */
    public String createExcelXlsx(Map<String,List<String>> storesMap, String path){
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
        for(int i = 0; i < 16; i++){
            cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            if(i == 0){
                cell.setCellValue("便利店-相似度比较结果");
            }
        }
        for(int i = 0; i < 16; i++){
            cellOnly = rowOnly.createCell(i);
            cellOnly.setCellStyle(headerStyle);
            if(i == 0){
                cellOnly.setCellValue("便利店-唯一性校验结果");
            }
        }
        for(int i = 0; i < 16; i++){
            cellRequired= rowRequired.createCell(i);
            cellRequired.setCellStyle(headerStyle);
            if(i == 0){
                cellRequired.setCellValue("便利店-完整性校验结果");
            }
        }

        // 创建主表Excel的第二行
        row = sheet.createRow(1);
        rowOnly = sheetOnly.createRow(1);
        rowRequired = sheetRequired.createRow(1);

        for(int i = 0; i < 16; i++){
            cell = row.createCell(i);
            cell.setCellStyle(style);

            cellOnly = rowOnly.createCell(i);
            cellOnly.setCellStyle(style);

            cellRequired = rowRequired.createCell(i);
            cellRequired.setCellStyle(style);

            switch(i){
                case 0 : setRowHeadValue(cell,cellOnly,cellRequired,"编码");break;
                case 1 : setRowHeadValue(cell,"相似度");setRowHeadValue(cellOnly,cellRequired,"名称");break;
                case 2 : setRowHeadValue(cell,"名称");setRowHeadValue(cellOnly,cellRequired,"简称");break;
                case 3 : setRowHeadValue(cell,"简称");setRowHeadValue(cellOnly,cellRequired,"服务区");break;
                case 4 : setRowHeadValue(cell,"服务区");setRowHeadValue(cellOnly,cellRequired,"分区");break;
                case 5 : setRowHeadValue(cell,"分区");setRowHeadValue(cellOnly,cellRequired,"业态");break;
                case 6 : setRowHeadValue(cell,"业态");setRowHeadValue(cellOnly,cellRequired,"所属商户");break;
                case 7 : setRowHeadValue(cell,"所属商户");setRowHeadValue(cellOnly,cellRequired,"类型");break;
                case 8 : setRowHeadValue(cell,"类型");setRowHeadValue(cellOnly,cellRequired,"状态");break;
                case 9 : setRowHeadValue(cell,"状态");setRowHeadValue(cellOnly,cellRequired,"地址");break;
                case 10: setRowHeadValue(cell,"地址");setRowHeadValue(cellOnly,cellRequired,"数据源头系统");break;
                case 11: setRowHeadValue(cell,"数据源头系统");setRowHeadValue(cellOnly,cellRequired,"开业日期");break;
                case 12: setRowHeadValue(cell,"开业日期");setRowHeadValue(cellOnly,cellRequired,"站场(加盟)店标识");break;
                case 13: setRowHeadValue(cell,"站场(加盟)店标识");setRowHeadValue(cellOnly,cellRequired,"数据来源");break;
                case 14: setRowHeadValue(cell,"数据来源");setRowHeadValue(cellOnly,cellRequired,"店铺编号(外部)");break;
                case 15: setRowHeadValue(cell,"店铺编号(外部)");break;
                default :break;
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
        List<String> compareData=storesMap.get("compareData");
        this.setRowContent(sheet, rowIndex,compareData);

        List<String> onlyData=storesMap.get("onlyData");
        this.setOnlyRowContent(sheetOnly, rowOnlyIndex,onlyData);

        List<String> requiredData=storesMap.get("requiredData");
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
                Stores stores=gson.fromJson(data.get(i),Stores.class);

                XSSFRow row = sheet.createRow(rowIndex);

                this.setRowValue(row,0,stores.getCode());
                this.setRowValue(row,1,stores.getSimilarity());
                this.setRowValue(row,2,stores.getName());
                this.setRowValue(row,3,stores.getShortname());
                this.setRowValue(row,4,stores.getCompid());
                this.setRowValue(row,5,stores.getAreaid());
                this.setRowValue(row,6,stores.getModeid());                
                this.setRowValue(row,7,stores.getTenantid());
                this.setRowValue(row,8,stores.getYytype());
                this.setRowValue(row,9,stores.getYystatus());
                this.setRowValue(row,10,stores.getAddr());
                this.setRowValue(row,11,stores.getResourcesys());                
                this.setRowValue(row,12,stores.getStartbusinessdate());
                this.setRowValue(row,13,stores.getYn_zc());
                this.setRowValue(row,14,stores.getDatasoucflag());                
                this.setRowValue(row,15,stores.getOuterpsnid());

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
                Stores stores=gson.fromJson(data.get(i),Stores.class);

                XSSFRow row = sheet.createRow(rowIndex);

                this.setRowValue(row,0,stores.getCode());
                this.setRowValue(row,1,stores.getName());
                this.setRowValue(row,2,stores.getShortname());
                this.setRowValue(row,3,stores.getCompid());
                this.setRowValue(row,4,stores.getAreaid());
                this.setRowValue(row,5,stores.getModeid());                
                this.setRowValue(row,6,stores.getTenantid());
                this.setRowValue(row,7,stores.getYytype());
                this.setRowValue(row,8,stores.getYystatus());
                this.setRowValue(row,9,stores.getAddr());
                this.setRowValue(row,10,stores.getResourcesys());                
                this.setRowValue(row,11,stores.getStartbusinessdate());
                this.setRowValue(row,12,stores.getYn_zc());
                this.setRowValue(row,13,stores.getDatasoucflag());                
                this.setRowValue(row,14,stores.getOuterpsnid());
                rowIndex++;
            }
        }
    }
}
