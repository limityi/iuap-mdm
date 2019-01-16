package com.yonyou.iuap.project.excel;

import com.google.gson.Gson;
import com.yonyou.iuap.project.entity.Lines;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客运线路导出处理类
 * Created by zhugaofeng on 2018/12/7.
 *
 */
public class WriteLinesExcel {

    private Gson gson=new Gson();

    /**
     * 创建excel
     * @param stationMap
     * @param path
     * @return
     */
    public String createExcelXlsx(Map<String,List<String>> linesMap, String path){
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
        for(int i = 0; i < 41; i++){
            cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            if(i == 0){
                cell.setCellValue("客运线路-相似度比较结果");
            }
        }
        for(int i = 0; i < 41; i++){
            cellOnly = rowOnly.createCell(i);
            cellOnly.setCellStyle(headerStyle);
            if(i == 0){
                cellOnly.setCellValue("客运线路-唯一性校验结果");
            }
        }
        for(int i = 0; i < 41; i++){
            cellRequired= rowRequired.createCell(i);
            cellRequired.setCellStyle(headerStyle);
            if(i == 0){
                cellRequired.setCellValue("客运线路-完整性校验结果");
            }
        }

        // 创建主表Excel的第二行
        row = sheet.createRow(1);
        rowOnly = sheetOnly.createRow(1);
        rowRequired = sheetRequired.createRow(1);

        for(int i = 0; i < 41; i++){
            cell = row.createCell(i);
            cell.setCellStyle(style);

            cellOnly = rowOnly.createCell(i);
            cellOnly.setCellStyle(style);

            cellRequired = rowRequired.createCell(i);
            cellRequired.setCellStyle(style);

            switch(i){
                case 0 : setRowHeadValue(cell,cellOnly,cellRequired,"编码");break;
                case 1 : setRowHeadValue(cell,"相似度");setRowHeadValue(cellOnly,cellRequired,"线路名称");break;
                case 2 : setRowHeadValue(cell,"线路名称");setRowHeadValue(cellOnly,cellRequired,"线路简称");break;
                case 3 : setRowHeadValue(cell,"线路简称");setRowHeadValue(cellOnly,cellRequired,"起点地");break;
                case 4 : setRowHeadValue(cell,"起点地");setRowHeadValue(cellOnly,cellRequired,"始发站");break;
                case 5 : setRowHeadValue(cell,"始发站");setRowHeadValue(cellOnly,cellRequired,"终点地");break;
                case 6: setRowHeadValue(cell,"终点地");setRowHeadValue(cellOnly,cellRequired,"终点站");break;
                case 7: setRowHeadValue(cell,"终点站");setRowHeadValue(cellOnly,"途径站点");setRowHeadValue(cellRequired,"经营类型");break;
                case 8 :setRowHeadValue(cell,"途径站点");setRowHeadValue(cellOnly,"途径路线");setRowHeadValue(cellRequired,"经营性质");break;
                case 9 :setRowHeadValue(cell,"途径路线");setRowHeadValue(cellOnly,"起点地配客站");setRowHeadValue(cellRequired,"投入车辆");break;
                case 10 :setRowHeadValue(cell,"起点地配客站");setRowHeadValue(cellOnly,"终点地配客站");setRowHeadValue(cellRequired,"日均班次");break;
                case 11 :setRowHeadValue(cell,"终点地配客站");setRowHeadValue(cellOnly,"中途休息点");setRowHeadValue(cellRequired,"日均营收");break;
                case 12 :setRowHeadValue(cell,"中途休息点");setRowHeadValue(cellOnly,"单程时间");setRowHeadValue(cellRequired,"日均客流量");break;
                case 13 :setRowHeadValue(cell,"单程时间");setRowHeadValue(cellOnly,"单程里程");setRowHeadValue(cellRequired,"早班时间");break;
                case 14 :setRowHeadValue(cell,"单程里程");setRowHeadValue(cellOnly,"高速里程");setRowHeadValue(cellRequired,"晚班时间");break;
                case 15 :setRowHeadValue(cell,"高速里程");setRowHeadValue(cellOnly,"路况等级");break;
                case 16 :setRowHeadValue(cell,"路况等级");setRowHeadValue(cellOnly,"经营类型");break;
                case 17 :setRowHeadValue(cell,"经营类型");setRowHeadValue(cellOnly,"班车类别");break;
                case 18 :setRowHeadValue(cell,"班车类别");setRowHeadValue(cellOnly,"经营性质");break;
                case 19 :setRowHeadValue(cell,"经营性质");setRowHeadValue(cellOnly,"经营范围");break;
                case 20 :setRowHeadValue(cell,"经营范围");setRowHeadValue(cellOnly,"是否粤运所有");break;
                case 21 :setRowHeadValue(cell,"是否粤运所有");setRowHeadValue(cellOnly,"线路所属组织机构");break;
                case 22 :setRowHeadValue(cell,"线路所属组织机构");setRowHeadValue(cellOnly,"投入车辆");break;
                case 23 :setRowHeadValue(cell,"投入车辆");setRowHeadValue(cellOnly,"日发班次");break;
                case 24 :setRowHeadValue(cell,"日发班次");setRowHeadValue(cellOnly,"日均班次");break;
                case 25 :setRowHeadValue(cell,"日均班次");setRowHeadValue(cellOnly,"日均营收");break;               
                case 26 :setRowHeadValue(cell,"日均营收");setRowHeadValue(cellOnly,"日均客流量");break;
                case 27 :setRowHeadValue(cell,"日均客流量");setRowHeadValue(cellOnly,"竞争方式");break;
                case 28 :setRowHeadValue(cell,"竞争方式");setRowHeadValue(cellOnly,"竞争车辆");break;
                case 29 :setRowHeadValue(cell,"竞争车辆");setRowHeadValue(cellOnly,"实载率");break;
                case 30 :setRowHeadValue(cell,"实载率");setRowHeadValue(cellOnly,"早班时间");break;
                case 31 :setRowHeadValue(cell,"早班时间");setRowHeadValue(cellOnly,"晚班时间");break;
                case 32 :setRowHeadValue(cell,"晚班时间");setRowHeadValue(cellOnly,"创建时间");break;
                case 33 :setRowHeadValue(cell,"创建时间");setRowHeadValue(cellOnly,"修改时间");break;
                case 34 :setRowHeadValue(cell,"修改时间");setRowHeadValue(cellOnly,"创建人");break;
                case 35 :setRowHeadValue(cell,"创建人");break;
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
        List<String> compareData=linesMap.get("compareData");
        this.setRowContent(sheet, rowIndex,compareData);

        List<String> onlyData=linesMap.get("onlyData");
        this.setOnlyRowContent(sheetOnly, rowOnlyIndex,onlyData);

        List<String> requiredData=linesMap.get("requiredData");
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
                Lines lines=gson.fromJson(data.get(i),Lines.class);

                XSSFRow row = sheet.createRow(rowIndex);

                this.setRowValue(row,0,lines.getCode());
                this.setRowValue(row,1,lines.getSimilarity());
                this.setRowValue(row,2,lines.getName());
                this.setRowValue(row,3,lines.getLine_shortname());
                this.setRowValue(row,4,lines.getLine_startdistrict());
                this.setRowValue(row,5,lines.getLine_start());
                this.setRowValue(row,6,lines.getLine_endistrict());
                this.setRowValue(row,7,lines.getLine_end());
                this.setRowValue(row,8,lines.getLine_stopby());
                this.setRowValue(row,9,lines.getLine_road());
                this.setRowValue(row,10,lines.getLine_startstop());
                this.setRowValue(row,11,lines.getLine_endstop());
                this.setRowValue(row,12,lines.getLine_midwaystop());
                this.setRowValue(row,13,lines.getLine_lasttime());
                this.setRowValue(row,14,lines.getLine_distance());
                this.setRowValue(row,15,lines.getLine_highwaykm());
                this.setRowValue(row,16,lines.getLine_roadlevel());
                this.setRowValue(row,17,lines.getLine_businesstype());
                this.setRowValue(row,18,lines.getLine_level());
                this.setRowValue(row,19,lines.getLine_businessnature());
                this.setRowValue(row,20,lines.getLine_area());
                this.setRowValue(row,21,lines.getLine_yueyun());
                this.setRowValue(row,22,lines.getName1());
                this.setRowValue(row,23,lines.getLine_amount());
                this.setRowValue(row,24,lines.getLine_daybus());
                this.setRowValue(row,25,lines.getLine_avgday_bus());
                this.setRowValue(row,26,lines.getLine_avgday_income());
                this.setRowValue(row,27,lines.getLine_avgday_cust());
                this.setRowValue(row,28,lines.getLine_competeway());
                this.setRowValue(row,29,lines.getLine_competecar());
                this.setRowValue(row,30,lines.getLine_carryrate());
                this.setRowValue(row,31,lines.getLine_begtime());
                this.setRowValue(row,32,lines.getLine_endtime());
                this.setRowValue(row,33,lines.getMdm_createdon());
                this.setRowValue(row,34,lines.getMdm_modifiedon());
                this.setRowValue(row,35,lines.getMdm_createdby());         
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
                Lines lines=gson.fromJson(data.get(i),Lines.class);

                XSSFRow row = sheet.createRow(rowIndex);

                this.setRowValue(row,0,lines.getCode());
                this.setRowValue(row,1,lines.getName());              
                this.setRowValue(row,4,lines.getLine_shortname());
                this.setRowValue(row,5,lines.getLine_startdistrict());
                this.setRowValue(row,6,lines.getLine_start());
                this.setRowValue(row,7,lines.getLine_endistrict());
                this.setRowValue(row,8,lines.getLine_end());
                this.setRowValue(row,9,lines.getLine_stopby());
                this.setRowValue(row,10,lines.getLine_road());
                this.setRowValue(row,11,lines.getLine_startstop());
                this.setRowValue(row,12,lines.getLine_endstop());
                this.setRowValue(row,13,lines.getLine_midwaystop());
                this.setRowValue(row,14,lines.getLine_lasttime());
                this.setRowValue(row,15,lines.getLine_distance());
                this.setRowValue(row,16,lines.getLine_highwaykm());
                this.setRowValue(row,17,lines.getLine_roadlevel());
                this.setRowValue(row,18,lines.getLine_businesstype());
                this.setRowValue(row,19,lines.getLine_level());
                this.setRowValue(row,20,lines.getLine_businessnature());
                this.setRowValue(row,21,lines.getLine_area());
                this.setRowValue(row,22,lines.getLine_yueyun());
                this.setRowValue(row,23,lines.getLine_institutionname());
                this.setRowValue(row,24,lines.getLine_amount());
                this.setRowValue(row,25,lines.getLine_daybus());
                this.setRowValue(row,26,lines.getLine_avgday_bus());
                this.setRowValue(row,27,lines.getLine_avgday_income());
                this.setRowValue(row,28,lines.getLine_avgday_cust());
                this.setRowValue(row,29,lines.getLine_competeway());
                this.setRowValue(row,30,lines.getLine_competecar());
                this.setRowValue(row,31,lines.getLine_carryrate());
                this.setRowValue(row,32,lines.getLine_begtime());
                this.setRowValue(row,33,lines.getLine_endtime());
                this.setRowValue(row,34,lines.getMdm_createdon());
                this.setRowValue(row,35,lines.getMdm_modifiedon());
                this.setRowValue(row,36,lines.getMdm_createdby());

                rowIndex++;
            }
        }
    }
    
    private void setRequiredRowContent(XSSFSheet sheet, int rowIndex, List<String> data){
        if(!data.isEmpty()&&data.size()>0){
            for (int i=0;i<data.size();i++){
                Lines lines=gson.fromJson(data.get(i),Lines.class);

                XSSFRow row = sheet.createRow(rowIndex);

                this.setRowValue(row,0,lines.getCode());
                this.setRowValue(row,1,lines.getName());              
                this.setRowValue(row,2,lines.getLine_shortname());
                this.setRowValue(row,3,lines.getLine_startdistrict());
                this.setRowValue(row,4,lines.getLine_start());
                this.setRowValue(row,5,lines.getLine_endistrict());
                this.setRowValue(row,6,lines.getLine_end());        
                this.setRowValue(row,7,lines.getLine_businesstype());
                this.setRowValue(row,8,lines.getLine_businessnature());
                this.setRowValue(row,9,lines.getLine_amount());
                this.setRowValue(row,10,lines.getLine_avgday_bus());
                this.setRowValue(row,11,lines.getLine_avgday_income());
                this.setRowValue(row,12,lines.getLine_avgday_cust());
                this.setRowValue(row,13,lines.getLine_begtime());
                this.setRowValue(row,14,lines.getLine_endtime());

                rowIndex++;
            }
        }
    }

}
