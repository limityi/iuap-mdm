package com.yonyou.iuap.project.excel;

import com.google.gson.Gson;
import com.yonyou.iuap.project.entity.Station;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 站场导出处理类
 * Created by XiongYi on 2018/11/30.
 *
 */
public class WriteStationExcel {

    private Gson gson=new Gson();

    /**
     * 创建excel
     * @param stationMap
     * @param path
     * @return
     */
    public String createExcelXlsx(Map<String,List<String>> stationMap, String path){
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
        XSSFSheet sheetRequired = wookbook.createSheet("必填项校验结果");

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
        for(int i = 0; i < 32; i++){
            cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            if(i == 0){
                cell.setCellValue("站场-相似度比较结果");
            }
        }
        for(int i = 0; i < 31; i++){
            cellOnly = rowOnly.createCell(i);
            cellOnly.setCellStyle(headerStyle);
            if(i == 0){
                cellOnly.setCellValue("站场-唯一性校验结果");
            }
        }
        for(int i = 0; i < 31; i++){
            cellRequired= rowRequired.createCell(i);
            cellRequired.setCellStyle(headerStyle);
            if(i == 0){
                cellRequired.setCellValue("站场-完整性校验结果");
            }
        }

        // 创建主表Excel的第二行
        row = sheet.createRow(1);
        rowOnly = sheetOnly.createRow(1);
        rowRequired = sheetRequired.createRow(1);

        for(int i = 0; i < 32; i++){
            cell = row.createCell(i);
            cell.setCellStyle(style);

            cellOnly = rowOnly.createCell(i);
            cellOnly.setCellStyle(style);

            cellRequired = rowRequired.createCell(i);
            cellRequired.setCellStyle(style);

            switch(i){
                case 0 : setRowHeadValue(cell,cellOnly,cellRequired,"编码");break;
                case 1 : setRowHeadValue(cell,"相似度");setRowHeadValue(cellOnly,cellRequired,"名称");break;
                case 2 : setRowHeadValue(cell,"名称");setRowHeadValue(cellOnly,cellRequired,"mdm编码");break;
                case 3 : setRowHeadValue(cell,"mdm编码");setRowHeadValue(cellOnly,cellRequired,"省交通厅编码");break;
                case 4 : setRowHeadValue(cell,"省交通厅编码");setRowHeadValue(cellOnly,cellRequired,"站场简称");break;
                case 5 : setRowHeadValue(cell,"站场简称");setRowHeadValue(cellOnly,cellRequired,"站场集团简称");break;
                case 6 : setRowHeadValue(cell,"站场集团简称");setRowHeadValue(cellOnly,cellRequired,"站场地址");break;
                case 7 : setRowHeadValue(cell,"站场地址");setRowHeadValue(cellOnly,cellRequired,"是否本省站");break;
                case 8 : setRowHeadValue(cell,"是否本省站");setRowHeadValue(cellOnly,cellRequired,"是否粤运所有");break;
                case 9 : setRowHeadValue(cell,"是否粤运所有");setRowHeadValue(cellOnly,cellRequired,"站场经营单位编码");break;
                case 10: setRowHeadValue(cell,"站场经营单位编码");setRowHeadValue(cellOnly,cellRequired,"站场经营单位");break;
                case 11: setRowHeadValue(cell,"站场经营单位");setRowHeadValue(cellOnly,cellRequired,"经营总公司");break;
                case 12 :setRowHeadValue(cell,"经营总公司");setRowHeadValue(cellOnly,cellRequired,"所属行政区域编码");break;
                case 13 :setRowHeadValue(cell,"所属行政区域编码");setRowHeadValue(cellOnly,cellRequired,"站场等级");break;
                case 14 :setRowHeadValue(cell,"站场等级");setRowHeadValue(cellOnly,cellRequired,"站场服务方式");break;
                case 15 :setRowHeadValue(cell,"站场服务方式");setRowHeadValue(cellOnly,cellRequired,"站场面积");break;
                case 16 :setRowHeadValue(cell,"站场面积");setRowHeadValue(cellOnly,cellRequired,"停车场面积");break;
                case 17 :setRowHeadValue(cell,"停车场面积");setRowHeadValue(cellOnly,cellRequired,"停车场租赁面积");break;
                case 18 :setRowHeadValue(cell,"停车场租赁面积");setRowHeadValue(cellOnly,cellRequired,"发车卡位数");break;
                case 19 :setRowHeadValue(cell,"发车卡位数");setRowHeadValue(cellOnly,cellRequired,"客运站类别");break;
                case 20 :setRowHeadValue(cell,"客运站类别");setRowHeadValue(cellOnly,cellRequired,"站场运营状态");break;
                case 21 :setRowHeadValue(cell,"站场运营状态");setRowHeadValue(cellOnly,cellRequired,"站场所有");break;
                case 22 :setRowHeadValue(cell,"站场所有");setRowHeadValue(cellOnly,cellRequired,"经营性质");break;
                case 23 :setRowHeadValue(cell,"经营性质");setRowHeadValue(cellOnly,cellRequired,"进站班车");break;
                case 24 :setRowHeadValue(cell,"进站班车");setRowHeadValue(cellOnly,cellRequired,"经度");break;
                case 25 :setRowHeadValue(cell,"经度");setRowHeadValue(cellOnly,cellRequired,"纬度");break;
                case 26 :setRowHeadValue(cell,"纬度");setRowHeadValue(cellOnly,cellRequired,"日均班次");break;
                case 27 :setRowHeadValue(cell,"日均班次");setRowHeadValue(cellOnly,cellRequired,"日均营收");break;
                case 28 :setRowHeadValue(cell,"日均营收");setRowHeadValue(cellOnly,cellRequired,"日均客流量");break;
                case 29 :setRowHeadValue(cell,"日均客流量");setRowHeadValue(cellOnly,cellRequired,"是否已收购");break;
                case 30 :setRowHeadValue(cell,"是否已收购");setRowHeadValue(cellOnly,cellRequired,"描述");break;
                case 31 :setRowHeadValue(cell,"描述");break;
                default :break;
            }
        }

        //合并表头
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 31));
        sheetOnly.addMergedRegion(new CellRangeAddress(0,0,0,30));
        sheetRequired.addMergedRegion(new CellRangeAddress(0,0,0,30));

        sheet.getRow(0).setHeight((short) (2*256));
        sheetOnly.getRow(0).setHeight((short) (2*256));
        sheetRequired.getRow(0).setHeight((short) (2*256));

        int rowIndex = 2;
        int rowOnlyIndex = 2;
        int rowRequiredIndex =2;

        //格式化数据并填充
        List<String> compareData=stationMap.get("compareData");
        this.setRowContent(sheet, rowIndex,compareData);

        List<String> onlyData=stationMap.get("onlyData");
        this.setOnlyRowContent(sheetOnly, rowOnlyIndex,onlyData);

        List<String> requiredData=stationMap.get("requiredData");
        this.setOnlyRowContent(sheetRequired, rowRequiredIndex,requiredData);

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
                Station station=gson.fromJson(data.get(i),Station.class);

                XSSFRow row = sheet.createRow(rowIndex);

                this.setRowValue(row,0,station.getCode());
                this.setRowValue(row,1,station.getSimilarity());
                this.setRowValue(row,2,station.getName());
                this.setRowValue(row,3,station.getMdm_code());
                this.setRowValue(row,4,station.getSjt_code());
                this.setRowValue(row,5,station.getStation_shortname());
                this.setRowValue(row,6,station.getStation_group_shortname());
                this.setRowValue(row,7,station.getStation_address());
                this.setRowValue(row,8,station.getStation_gdprovince());
                this.setRowValue(row,9,station.getStation_yueyun());
                this.setRowValue(row,10,station.getStation_companyid());
                this.setRowValue(row,11,station.getStation_companyname());
                this.setRowValue(row,12,station.getStation_company());
                this.setRowValue(row,13,station.getStation_districtid());
                this.setRowValue(row,14,station.getStation_level());
                this.setRowValue(row,15,station.getStation_servicetype());
                this.setRowValue(row,16,station.getStation_area());
                this.setRowValue(row,17,station.getStation_parea());
                this.setRowValue(row,18,station.getStation_leasearea());
                this.setRowValue(row,19,station.getStation_pnum());
                this.setRowValue(row,20,station.getStation_type());
                this.setRowValue(row,21,station.getStation_businessnature());
                this.setRowValue(row,22,station.getStation_own());
                this.setRowValue(row,23,station.getStation_businessmode());
                this.setRowValue(row,24,station.getStation_busnum());
                this.setRowValue(row,25,station.getStation_longitude());
                this.setRowValue(row,26,station.getStation_latitude());
                this.setRowValue(row,27,station.getStation_avgday_bus());
                this.setRowValue(row,28,station.getStation_avgday_income());
                this.setRowValue(row,29,station.getStation_avgday_cust());
                this.setRowValue(row,30,station.getStation_acquisition());

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
                Station station=gson.fromJson(data.get(i),Station.class);

                XSSFRow row = sheet.createRow(rowIndex);

                this.setRowValue(row,0,station.getCode());
                this.setRowValue(row,1,station.getName());
                this.setRowValue(row,2,station.getMdm_code());
                this.setRowValue(row,3,station.getSjt_code());
                this.setRowValue(row,4,station.getStation_shortname());
                this.setRowValue(row,5,station.getStation_group_shortname());
                this.setRowValue(row,6,station.getStation_address());
                this.setRowValue(row,7,station.getStation_gdprovince());
                this.setRowValue(row,8,station.getStation_yueyun());
                this.setRowValue(row,9,station.getStation_companyid());
                this.setRowValue(row,10,station.getStation_companyname());
                this.setRowValue(row,11,station.getStation_company());
                this.setRowValue(row,12,station.getStation_districtid());
                this.setRowValue(row,13,station.getStation_level());
                this.setRowValue(row,14,station.getStation_servicetype());
                this.setRowValue(row,15,station.getStation_area());
                this.setRowValue(row,16,station.getStation_parea());
                this.setRowValue(row,17,station.getStation_leasearea());
                this.setRowValue(row,18,station.getStation_pnum());
                this.setRowValue(row,19,station.getStation_type());
                this.setRowValue(row,20,station.getStation_businessnature());
                this.setRowValue(row,21,station.getStation_own());
                this.setRowValue(row,22,station.getStation_businessmode());
                this.setRowValue(row,23,station.getStation_busnum());
                this.setRowValue(row,24,station.getStation_longitude());
                this.setRowValue(row,25,station.getStation_latitude());
                this.setRowValue(row,26,station.getStation_avgday_bus());
                this.setRowValue(row,27,station.getStation_avgday_income());
                this.setRowValue(row,28,station.getStation_avgday_cust());
                this.setRowValue(row,29,station.getStation_acquisition());

                rowIndex++;
            }
        }
    }

}
