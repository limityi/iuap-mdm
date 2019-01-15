package com.yonyou.iuap.project.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量接口
 * @author XiongYi
 *
 */
public interface SysConst {

	/**
	 * 	监控数据类型常量
	 */
	String [] DataType={"mdm_station","mdm_bus","mdm_busline","mdm_line","mdm_lisence","mdm_merchants_lxrxx","mdm_merchants_yhxx","mdm_service_area","mdm_settlementmethod","mdm_nc_org","nyt_agent","nyt_bus","nyt_company","nyt_line","nyt_station","zhky_bus","zhky_line","zhky_station1"};


	/**
	 *主数据来源系统
	 */
	Map<String,String> monitorSystemName=new HashMap<String,String>(){
		private static final long serialVersionUID = 1L;
		{
			put("mdm_station", "数据资源管理平台");
			put("mdm_bus", "数据资源管理平台");
			put("mdm_busline", "数据资源管理平台");
			put("mdm_line", "数据资源管理平台");
			put("mdm_lisence", "数据资源管理平台");
			put("mdm_merchants_lxrxx", "数据资源管理平台");
			put("mdm_merchants_yhxx", "数据资源管理平台");
			put("mdm_nc_org", "NC财务系统");
			put("mdm_service_area", "数据资源管理平台");
			put("mdm_settlementmethod", "数据资源管理平台");
			put("nyt_agent", "南粤通");
			put("nyt_bus", "南粤通");
			put("nyt_company", "南粤通");
			put("nyt_line", "南粤通");
			put("nyt_station", "南粤通");
			put("zhky_bus", "智慧客运");
			put("zhky_line", "智慧客运");
			put("zhky_station1", "智慧客运");
		}
	};

	/**
	 *主数据类型名称
	 */
	Map<String,String> dataTypeName=new HashMap<String,String>(){
		private static final long serialVersionUID = 1L;
		{
			put("mdm_station", "站场");
			put("mdm_bus", "车辆");
			put("mdm_busline", "公交线路");
			put("mdm_line", "客运线路");
			put("mdm_lisence", "线路牌");
			put("mdm_merchants_lxrxx", "客商联系人");
			put("mdm_merchants_yhxx", "客商银行账号");
			put("mdm_nc_org", "组织");
			put("mdm_service_area", "服务区");
			put("mdm_settlementmethod", "结算方式");
			put("nyt_agent", "南粤通代售点");
			put("nyt_bus", "南粤通车辆");
			put("nyt_company", "南粤通结算单位");
			put("nyt_line", "南粤通客运线路");
			put("nyt_station", "南粤通站场");
			put("zhky_bus", "智慧客运车辆");
			put("zhky_line", "智慧客运线路");
			put("zhky_station1", "智慧客运站场");
		}
	};

	/**
	 *集成方式
	 */
	Map<String,String> integrationMode=new HashMap<String,String>(){
		private static final long serialVersionUID = 1L;
		{
			put("0", "ETL");
		}
	};

	/**
	 *集成类型
	 */
	Map<String,String> integrationType=new HashMap<String,String>(){
		private static final long serialVersionUID = 1L;
		{
			put("0", "接收");
		}
	};

	/**
	 *集成策略
	 */
	Map<String,String> integrationStrategy=new HashMap<String,String>(){
		private static final long serialVersionUID = 1L;
		{
			put("0", "增量");
		}
	};

	/**
	 *同步状态
	 */
	Map<String,String> syncStatus=new HashMap<String,String>(){
		private static final long serialVersionUID = 1L;
		{
			put("end", "成功");
			put("stop", "失败");
		}
	};

}
