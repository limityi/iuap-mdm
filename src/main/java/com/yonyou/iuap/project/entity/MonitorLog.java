package com.yonyou.iuap.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.FK;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

import java.util.List;
	import java.util.Date;
	


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
@Entity(namespace = "iuap-mdm-yueyun",name = "MonitorLog")
@Table(name = "mdm_monitorlog")
public class MonitorLog extends BaseEntity{
	 
    @FK(name = "fk_id_log", referenceTableName = "mdm_monitor", referencedColumnName = "id") 
    @Column(name = "fk_id_log")
    private java.lang.String fk_id_log;
    
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "id")
	private String id;
	
	@Column(name = "channel_id")
	private String channel_id;
	
	@Column(name = "transname")
	private String transname;
	
	@Column(name = "sync_status")
	private String sync_status;
	
	@Column(name = "lines_read")
	private Integer lines_read;
	
	@Column(name = "lines_written")
	private Integer lines_written;
	
	@Column(name = "lines_updated")
	private Integer lines_updated;
	
	@Column(name = "lines_input")
	private Integer lines_input;
	
	@Column(name = "lines_output")
	private Integer lines_output;
	
	@Column(name = "lines_rejected")
	private Integer lines_rejected;
	
	@Column(name = "errors")
	private Integer errors;
	
	@Column(name = "startdate")
	private Date startdate;
	
	@Column(name = "enddate")
	private Date enddate;
	
	@Column(name = "logdate")
	private Date logdate;
	
	@Column(name = "depdate")
	private Date depdate;
	
	@Column(name = "replaydate")
	private Date replaydate;
	
	@Column(name = "log_field")
	private String log_field;
	
	@Column(name = "dr")
    private java.lang.Integer dr = 0 ;
      
    @Column(name = "ts")
    private java.util.Date ts ;
	

	/**
	 * 属性 fk的Getter方法.属性名：parentPK
	 *  创建日期:2016-11-17
	 * @return java.lang.String
	 */
	public java.lang.String getFk_id_log() {
		return fk_id_log;
	}
	   
	/**
	 * 属性fk的Setter方法.属性名：parentPK
	 * 创建日期:2016-11-17
	 * @param newFk java.lang.String
	 */
	public void setFk_id_log (java.lang.String fk_id_log ) {
	 	this.fk_id_log = fk_id_log;
	} 	 
	

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getChannel_id() {
		return this.channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}


	public String getTransname() {
		return this.transname;
	}

	public void setTransname(String transname) {
		this.transname = transname;
	}


	public String getSync_status() {
		return this.sync_status;
	}

	public void setSync_status(String sync_status) {
		this.sync_status = sync_status;
	}


	public Integer getLines_read() {
		return this.lines_read;
	}

	public void setLines_read(Integer lines_read) {
		this.lines_read = lines_read;
	}


	public Integer getLines_written() {
		return this.lines_written;
	}

	public void setLines_written(Integer lines_written) {
		this.lines_written = lines_written;
	}


	public Integer getLines_updated() {
		return this.lines_updated;
	}

	public void setLines_updated(Integer lines_updated) {
		this.lines_updated = lines_updated;
	}


	public Integer getLines_input() {
		return this.lines_input;
	}

	public void setLines_input(Integer lines_input) {
		this.lines_input = lines_input;
	}


	public Integer getLines_output() {
		return this.lines_output;
	}

	public void setLines_output(Integer lines_output) {
		this.lines_output = lines_output;
	}


	public Integer getLines_rejected() {
		return this.lines_rejected;
	}

	public void setLines_rejected(Integer lines_rejected) {
		this.lines_rejected = lines_rejected;
	}


	public Integer getErrors() {
		return this.errors;
	}

	public void setErrors(Integer errors) {
		this.errors = errors;
	}


	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}


	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}


	public Date getLogdate() {
		return this.logdate;
	}

	public void setLogdate(Date logdate) {
		this.logdate = logdate;
	}


	public Date getDepdate() {
		return this.depdate;
	}

	public void setDepdate(Date depdate) {
		this.depdate = depdate;
	}


	public Date getReplaydate() {
		return this.replaydate;
	}

	public void setReplaydate(Date replaydate) {
		this.replaydate = replaydate;
	}


	public String getLog_field() {
		return this.log_field;
	}

	public void setLog_field(String log_field) {
		this.log_field = log_field;
	}

	
	/**
	 * 属性 dr的Getter方法.属性名：dr
	 *  创建日期:2016-11-17
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr () {
		return dr;
	}
	   
	/**
	 * 属性dr的Setter方法.属性名：dr
	 * 创建日期:2016-11-17
	 * @param newDr java.lang.Integer
	 */
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	 
	
	/**
	 * 属性 ts的Getter方法.属性名：ts
	 *  创建日期:2016-11-17
	 * @return java.util.Date
	 */
	public java.util.Date getTs () {
		return ts;
	}
	   
	/**
	 * 属性ts的Setter方法.属性名：ts
	 * 创建日期:2016-11-17
	 * @param newTs java.util.Date
	 */
	public void setTs (java.util.Date newTs ) {
	 	this.ts = newTs;
	} 	 
	
	@Override
    public String getMetaDefinedName() {
        return "MonitorLog";
    }

    @Override
    public String getNamespace() {
        return "iuap-mdm-yueyun";
    }
}