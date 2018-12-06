package com.yonyou.iuap.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.*;
import com.yonyou.iuap.persistence.vo.BaseEntity;

import java.util.List;


/**
 * <b> 此处简要描述此类功能 </b>
 * <p>
 *   此处添加类的描述信息
 * </p>
 *  创建日期:2016-11-17
 * @author 
 * @version 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(namespace = "iuap-mdm-yueyun",name = "Monitor")
@Table(name = "mdm_monitor")
public class Monitor extends BaseEntity{
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "id")
	private String id;

	@Column(name = "system_name")
	private String system_name;

	@Column(name = "data_type")
	private String data_type;

	@Column(name = "integration_mode")
	private String integration_mode;

	@Column(name = "integration_type")
	private String integration_type;

	@Column(name = "integration_strategy")
	private String integration_strategy;

	@Column(name = "integration_cycle")
	private String integration_cycle;

	@Column(name = "total_number_day")
	private Integer total_number_day;

	@Column(name = "success_number_day")
	private Integer success_number_day;

	@Column(name = "fail_number_day")
	private Integer fail_number_day;

	@Column(name = "total_number_week")
	private Integer total_number_week;

	@Column(name = "success_number_week")
	private Integer success_number_week;

	@Column(name = "fail_number_week")
	private Integer fail_number_week;

	@Column(name = "total_number_month")
	private Integer total_number_month;

	@Column(name = "success_number_month")
	private Integer success_number_month;

	@Column(name = "fail_number_month")
	private Integer fail_number_month;

	@OneToMany(targetEntity = MonitorLog.class)
	private List<MonitorLog> id_log;

	@Column(name = "dr")
    private java.lang.Integer dr = 0 ;
      
    @Column(name = "ts")
    private java.sql.Timestamp ts ;


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getSystem_name() {
		return this.system_name;
	}

	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}


	public String getData_type() {
		return this.data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}


	public String getIntegration_mode() {
		return this.integration_mode;
	}

	public void setIntegration_mode(String integration_mode) {
		this.integration_mode = integration_mode;
	}


	public String getIntegration_type() {
		return this.integration_type;
	}

	public void setIntegration_type(String integration_type) {
		this.integration_type = integration_type;
	}


	public String getIntegration_strategy() {
		return this.integration_strategy;
	}

	public void setIntegration_strategy(String integration_strategy) {
		this.integration_strategy = integration_strategy;
	}


	public String getIntegration_cycle() {
		return this.integration_cycle;
	}

	public void setIntegration_cycle(String integration_cycle) {
		this.integration_cycle = integration_cycle;
	}


	public Integer getTotal_number_day() {
		return this.total_number_day;
	}

	public void setTotal_number_day(Integer total_number_day) {
		this.total_number_day = total_number_day;
	}


	public Integer getSuccess_number_day() {
		return this.success_number_day;
	}

	public void setSuccess_number_day(Integer success_number_day) {
		this.success_number_day = success_number_day;
	}


	public Integer getFail_number_day() {
		return this.fail_number_day;
	}

	public void setFail_number_day(Integer fail_number_day) {
		this.fail_number_day = fail_number_day;
	}


	public Integer getTotal_number_week() {
		return this.total_number_week;
	}

	public void setTotal_number_week(Integer total_number_week) {
		this.total_number_week = total_number_week;
	}


	public Integer getSuccess_number_week() {
		return this.success_number_week;
	}

	public void setSuccess_number_week(Integer success_number_week) {
		this.success_number_week = success_number_week;
	}


	public Integer getFail_number_week() {
		return this.fail_number_week;
	}

	public void setFail_number_week(Integer fail_number_week) {
		this.fail_number_week = fail_number_week;
	}


	public Integer getTotal_number_month() {
		return this.total_number_month;
	}

	public void setTotal_number_month(Integer total_number_month) {
		this.total_number_month = total_number_month;
	}


	public Integer getSuccess_number_month() {
		return this.success_number_month;
	}

	public void setSuccess_number_month(Integer success_number_month) {
		this.success_number_month = success_number_month;
	}


	public Integer getFail_number_month() {
		return this.fail_number_month;
	}

	public void setFail_number_month(Integer fail_number_month) {
		this.fail_number_month = fail_number_month;
	}


	public List<MonitorLog> getId_log() {
		return this.id_log;
	}

	public void setId_log(List<MonitorLog> id_log) {
		this.id_log = id_log;
	}

	public java.lang.Integer getDr () {
		return dr;
	}
	
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	
	
	public java.sql.Timestamp getTs () {
		return ts;
	}
	
	public void setTs (java.sql.Timestamp newTs ) {
	 	this.ts = newTs;
	} 
	
	@Override
    public String getMetaDefinedName() {
        return "Monitor";
    }

    @Override
    public String getNamespace() {
        return "iuap-mdm-yueyun";
    }
}