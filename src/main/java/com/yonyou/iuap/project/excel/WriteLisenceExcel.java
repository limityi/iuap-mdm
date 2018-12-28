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
import com.yonyou.iuap.project.entity.Lisence;

/**
 * 线路牌导出处理类
 * Created by zhugaofeng on 2018/12/17.
 *
 */
public class WriteLisenceExcel {
	
	private Gson gson=new Gson();

    /**
     * 创建excel
     * @param lisenceMap
     * @param path
     * @return
     */
    public String createExcelXlsx(Map<String,List<String>> lisenceMap, String path){
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
        for(int i = 0; i < 35; i++){
            cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            if(i == 0){
                cell.setCellValue("线路牌-相似度比较结果");
            }
        }
        for(int i = 0; i < 35; i++){
            cellOnly = rowOnly.createCell(i);
            cellOnly.setCellStyle(headerStyle);
            if(i == 0){
                cellOnly.setCellValue("线路牌-唯一性校验结果");
            }
        }
        for(int i = 0; i < 35; i++){
            cellRequired= rowRequired.createCell(i);
            cellRequired.setCellStyle(headerStyle);
            if(i == 0){
                cellRequired.setCellValue("线路牌-完整性校验结果");
            }
        }

        // 创建主表Excel的第二行
        row = sheet.createRow(1);
        rowOnly = sheetOnly.createRow(1);
        rowRequired = sheetRequired.createRow(1);

        for(int i = 0; i < 35; i++){
            cell = row.createCell(i);
            cell.setCellStyle(style);

            cellOnly = rowOnly.createCell(i);
            cellOnly.setCellStyle(style);

            cellRequired = rowRequired.createCell(i);
            cellRequired.setCellStyle(style);

            switch(i){
            case 0 : setRowHeadValue(cell,cellOnly,cellRequired,"线路牌号");break;
            case 1 : setRowHeadValue(cell,"相似度");setRowHeadValue(cellOnly,cellRequired,"线路牌名称");break;
            case 2 : setRowHeadValue(cell,"线路牌名称");setRowHeadValue(cellOnly,cellRequired,"mdm编码");break;
            case 3 : setRowHeadValue(cell,"mdm编码");setRowHeadValue(cellOnly,cellRequired,"有效起期");break;       
            case 4 : setRowHeadValue(cell,"有效起期");setRowHeadValue(cellOnly,cellRequired,"有效止期");break;
            case 5 : setRowHeadValue(cell,"有效止期");setRowHeadValue(cellOnly,cellRequired,"有效状态");break;
            case 6 : setRowHeadValue(cell,"有效状态");setRowHeadValue(cellOnly,cellRequired,"起点站");break;
            case 7 : setRowHeadValue(cell,"起点站");setRowHeadValue(cellOnly,cellRequired,"终点站");break;
            case 8: setRowHeadValue(cell,"终点站");setRowHeadValue(cellOnly,cellRequired,"途径点");break;               
            case 9 :setRowHeadValue(cell,"途径点");setRowHeadValue(cellOnly,cellRequired,"途径线路");break;
            case 10 :setRowHeadValue(cell,"途径线路");setRowHeadValue(cellOnly,cellRequired,"车辆号牌");break;
            case 11 :setRowHeadValue(cell,"车辆号牌");setRowHeadValue(cellOnly,cellRequired,"线路编号");break;
            case 12 :setRowHeadValue(cell,"线路编号");setRowHeadValue(cellOnly,cellRequired,"线路名称");break;
            case 13 :setRowHeadValue(cell,"线路名称");setRowHeadValue(cellOnly,cellRequired,"单程里程");break;              
            case 14 :setRowHeadValue(cell,"单程里程");setRowHeadValue(cellOnly,cellRequired,"单程时间");break;               
            case 15 :setRowHeadValue(cell,"单程时间");setRowHeadValue(cellOnly,cellRequired,"是否粤运所有");break;
            case 16 :setRowHeadValue(cell,"是否粤运所有");setRowHeadValue(cellOnly,cellRequired,"经营权单位");break;
            case 17 :setRowHeadValue(cell,"经营权单位");setRowHeadValue(cellOnly,cellRequired,"班线类别");break;
            case 18 :setRowHeadValue(cell,"班线类别");setRowHeadValue(cellOnly,cellRequired,"经营区域");break;
            case 19 :setRowHeadValue(cell,"经营区域");setRowHeadValue(cellOnly,cellRequired,"班车类别");break;                
            case 20 :setRowHeadValue(cell,"班车类别");setRowHeadValue(cellOnly,cellRequired,"经营性质");break;
            case 21 :setRowHeadValue(cell,"经营性质");setRowHeadValue(cellOnly,cellRequired,"线路牌性质");break;
            case 22 :setRowHeadValue(cell,"线路牌性质");setRowHeadValue(cellOnly,cellRequired,"使用状态");break;               
            case 23 :setRowHeadValue(cell,"使用状态");setRowHeadValue(cellOnly,cellRequired,"归属");break;
            case 24 :setRowHeadValue(cell,"归属");setRowHeadValue(cellOnly,cellRequired,"合同到期");break;
            case 25 :setRowHeadValue(cell,"合同到期");setRowHeadValue(cellOnly,cellRequired,"使用权单位");break;
            case 26 :setRowHeadValue(cell,"使用权单位");setRowHeadValue(cellOnly,cellRequired,"起点地");break;
            case 27 :setRowHeadValue(cell,"起点地");setRowHeadValue(cellOnly,cellRequired,"终点地");break;
            case 28 :setRowHeadValue(cell,"终点地");setRowHeadValue(cellOnly,cellRequired,"创建时间");break;
            case 29 :setRowHeadValue(cell,"创建时间");setRowHeadValue(cellOnly,cellRequired,"修改时间");break;
            case 30 :setRowHeadValue(cell,"修改时间");setRowHeadValue(cellOnly,cellRequired,"创建人");break;
            case 31 :setRowHeadValue(cell,"创建人");setRowHeadValue(cellOnly,cellRequired,"智慧客运编码");break;                
            case 32 :setRowHeadValue(cell,"智慧客运编码");setRowHeadValue(cellOnly,cellRequired,"南粤通编码");break;
            case 33 :setRowHeadValue(cell,"南粤通编码");setRowHeadValue(cellOnly,cellRequired,"润辰中心平台编码");break;
            case 34 :setRowHeadValue(cell,"润辰中心平台编码");break;
            default :break;
            }
        }

        //合并表头
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 40));
        sheetOnly.addMergedRegion(new CellRangeAddress(0,0,0,40));
        sheetRequired.addMergedRegion(new CellRangeAddress(0,0,0,40));

        sheet.getRow(0).setHeight((short) (2*256));
        sheetOnly.getRow(0).setHeight((short) (2*256));
        sheetRequired.getRow(0).setHeight((short) (2*256));

        int rowIndex = 2;
        int rowOnlyIndex = 2;
        int rowRequiredIndex =2;

        //格式化数据并填充
        List<String> compareData=lisenceMap.get("compareData");
        this.setRowContent(sheet, rowIndex,compareData);

        List<String> onlyData=lisenceMap.get("onlyData");
        this.setOnlyRowContent(sheetOnly, rowOnlyIndex,onlyData);

        List<String> requiredData=lisenceMap.get("requiredData");
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
                Lisence lisence=gson.fromJson(data.get(i),Lisence.class);

                XSSFRow row = sheet.createRow(rowIndex);

                this.setRowValue(row,0,lisence.getCode());
                this.setRowValue(row,1,lisence.getSimilarity());
                this.setRowValue(row,2,lisence.getName());
                this.setRowValue(row,3,lisence.getMdm_code());                
                this.setRowValue(row,4,lisence.getLisence_validstart());
                this.setRowValue(row,5,lisence.getLisence_validend());
                this.setRowValue(row,6,lisence.getLisence_validstatus());
                this.setRowValue(row,7,lisence.getLisence_start());
                this.setRowValue(row,8,lisence.getLisence_end());
                this.setRowValue(row,9,lisence.getLisence_stopby());
                this.setRowValue(row,10,lisence.getLisence_passlines());
                this.setRowValue(row,11,lisence.getLisence_busnum());
                this.setRowValue(row,12,lisence.getLisence_lineid());
                this.setRowValue(row,13,lisence.getLisence_linename());
                this.setRowValue(row,14,lisence.getLisence_waykm());
                this.setRowValue(row,15,lisence.getLisence_waytime());
                this.setRowValue(row,16,lisence.getLisence_managementunit());
                this.setRowValue(row,17,lisence.getLisence_bustype());
                this.setRowValue(row,18,lisence.getLisence_businessarea());
                this.setRowValue(row,19,lisence.getLisence_linetype());
                this.setRowValue(row,20,lisence.getLisence_businessnature());
                this.setRowValue(row,21,lisence.getLisence_nature());
                this.setRowValue(row,22,lisence.getLisence_usestatus());
                this.setRowValue(row,23,lisence.getLisence_vest());            
                this.setRowValue(row,24,lisence.getLisence_contractend());
                this.setRowValue(row,25,lisence.getLisence_belongsid());
                this.setRowValue(row,26,lisence.getLisence_by1());
                this.setRowValue(row,27,lisence.getLisence_by2());
                this.setRowValue(row,28,lisence.getMdm_createdon());
                this.setRowValue(row,29,lisence.getMdm_modifiedon());
                this.setRowValue(row,30,lisence.getMdm_createdby());
                this.setRowValue(row,31,lisence.getZhky_code());
                this.setRowValue(row,32,lisence.getNyt_code());
                this.setRowValue(row,33,lisence.getRc_code());

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
                Lisence lisence=gson.fromJson(data.get(i),Lisence.class);

                XSSFRow row = sheet.createRow(rowIndex);

                this.setRowValue(row,0,lisence.getCode());
                this.setRowValue(row,1,lisence.getName());
                this.setRowValue(row,2,lisence.getMdm_code());                
                this.setRowValue(row,3,lisence.getLisence_validstart());
                this.setRowValue(row,4,lisence.getLisence_validend());
                this.setRowValue(row,5,lisence.getLisence_validstatus());
                this.setRowValue(row,6,lisence.getLisence_start());
                this.setRowValue(row,7,lisence.getLisence_end());
                this.setRowValue(row,8,lisence.getLisence_stopby());
                this.setRowValue(row,9,lisence.getLisence_passlines());
                this.setRowValue(row,10,lisence.getLisence_busnum());
                this.setRowValue(row,11,lisence.getLisence_lineid());
                this.setRowValue(row,12,lisence.getLisence_linename());
                this.setRowValue(row,13,lisence.getLisence_waykm());
                this.setRowValue(row,14,lisence.getLisence_waytime());
                this.setRowValue(row,15,lisence.getLisence_managementunit());
                this.setRowValue(row,16,lisence.getLisence_bustype());
                this.setRowValue(row,17,lisence.getLisence_businessarea());
                this.setRowValue(row,18,lisence.getLisence_linetype());
                this.setRowValue(row,19,lisence.getLisence_businessnature());
                this.setRowValue(row,20,lisence.getLisence_nature());
                this.setRowValue(row,21,lisence.getLisence_usestatus());
                this.setRowValue(row,22,lisence.getLisence_vest());            
                this.setRowValue(row,23,lisence.getLisence_contractend());
                this.setRowValue(row,24,lisence.getLisence_belongsid());
                this.setRowValue(row,25,lisence.getLisence_by1());
                this.setRowValue(row,26,lisence.getLisence_by2());
                this.setRowValue(row,27,lisence.getMdm_createdon());
                this.setRowValue(row,28,lisence.getMdm_modifiedon());
                this.setRowValue(row,29,lisence.getMdm_createdby());
                this.setRowValue(row,30,lisence.getZhky_code());
                this.setRowValue(row,31,lisence.getNyt_code());
                this.setRowValue(row,32,lisence.getRc_code());

                rowIndex++;
            }
        }
    }

}
