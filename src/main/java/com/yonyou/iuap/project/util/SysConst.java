package com.yonyou.iuap.project.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量接口
 * @author XiongYi
 *
 */
public interface SysConst {
	
	//业务归属常量
	String [] Business={"planoperation","investexpansion","research","design","marketing","construction"};

	/**
	 *主数据类型
	 */
	Map<String,String> dataType=new HashMap<String,String>(){
		private static final long serialVersionUID = 1L;
		{
			put("line", "线路");
			put("station", "站场");
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