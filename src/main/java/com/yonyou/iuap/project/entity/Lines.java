package com.yonyou.iuap.project.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

import java.util.Date;
	


/**
 * <b> 此处简要描述此类功能 </b>
 * <p>
 *   此处添加类的描述信息
 * </p>
 * @author 
 * @version 
 */
@Entity(namespace = "iuap-mdm-yueyun",name = "Lines")
@Table(name="mdm_lines")
public class Lines extends BaseEntity {
	  
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "id")
	private String id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "mdm_code")
	private String mdm_code;
	
	@Column(name = "mdm_pk")
	private String mdm_pk;
	
	@Column(name = "mdm_duplicate")
	private String mdm_duplicate;
	
	@Column(name = "mdm_seal")
	private String mdm_seal;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "line_shortname")
	private String line_shortname;
	
	@Column(name = "line_startdistrict")
	private String line_startdistrict;
	
	@Column(name = "line_start")
	private String line_start;
	
	@Column(name = "line_endistrict")
	private String line_endistrict;
	
	@Column(name = "line_end")
	private String line_end;
	
	@Column(name = "line_stopby")
	private String line_stopby;
	
	@Column(name = "line_road")
	private String line_road;
	
	@Column(name = "line_startstop")
	private String line_startstop;
	
	@Column(name = "line_endstop")
	private String line_endstop;
	
	@Column(name = "line_midwaystop")
	private String line_midwaystop;
	
	@Column(name = "line_lasttime")
	private String line_lasttime;
	
	@Column(name = "line_distance")
	private String line_distance;
	
	@Column(name = "line_highwaykm")
	private String line_highwaykm;
	
	@Column(name = "line_roadlevel")
	private String line_roadlevel;
	
	@Column(name = "line_businesstype")
	private String line_businesstype;
	
	@Column(name = "line_level")
	private String line_level;
	
	@Column(name = "line_businessnature")
	private String line_businessnature;
	
	@Column(name = "line_area")
	private String line_area;
	
	@Column(name = "line_yueyun")
	private String line_yueyun;
	
	@Column(name = "line_institutionname")
	private String line_institutionname;
	
	@Column(name = "line_amount")
	private String line_amount;
	
	@Column(name = "line_daybus")
	private String line_daybus;
	
	@Column(name = "line_avgday_bus")
	private String line_avgday_bus;
	
	@Column(name = "line_avgday_income")
	private String line_avgday_income;
	
	@Column(name = "line_avgday_cust")
	private String line_avgday_cust;
	
	@Column(name = "line_competeway")
	private String line_competeway;
	
	@Column(name = "line_competecar")
	private String line_competecar;
	
	@Column(name = "line_carryrate")
	private String line_carryrate;
	
	@Column(name = "line_begtime")
	private Date line_begtime;
	
	@Column(name = "line_endtime")
	private Date line_endtime;
	
	@Column(name = "mdm_createdon")
	private Date mdm_createdon;
	
	@Column(name = "mdm_modifiedon")
	private Date mdm_modifiedon;
	
	@Column(name = "mdm_createdby")
	private String mdm_createdby;
	
	@Column(name = "zhky_code")
	private String zhky_code;
	
	@Column(name = "nyt_code")
	private String nyt_code;
	
	@Column(name = "rc_code")
	private String rc_code;
	

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
	

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getMdm_code() {
		return this.mdm_code;
	}

	public void setMdm_code(String mdm_code) {
		this.mdm_code = mdm_code;
	}
	

	public String getMdm_pk() {
		return this.mdm_pk;
	}

	public void setMdm_pk(String mdm_pk) {
		this.mdm_pk = mdm_pk;
	}
	

	public String getMdm_duplicate() {
		return this.mdm_duplicate;
	}

	public void setMdm_duplicate(String mdm_duplicate) {
		this.mdm_duplicate = mdm_duplicate;
	}
	

	public String getMdm_seal() {
		return this.mdm_seal;
	}

	public void setMdm_seal(String mdm_seal) {
		this.mdm_seal = mdm_seal;
	}
	

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public String getLine_shortname() {
		return this.line_shortname;
	}

	public void setLine_shortname(String line_shortname) {
		this.line_shortname = line_shortname;
	}
	

	public String getLine_startdistrict() {
		return this.line_startdistrict;
	}

	public void setLine_startdistrict(String line_startdistrict) {
		this.line_startdistrict = line_startdistrict;
	}
	

	public String getLine_start() {
		return this.line_start;
	}

	public void setLine_start(String line_start) {
		this.line_start = line_start;
	}
	

	public String getLine_endistrict() {
		return this.line_endistrict;
	}

	public void setLine_endistrict(String line_endistrict) {
		this.line_endistrict = line_endistrict;
	}
	

	public String getLine_end() {
		return this.line_end;
	}

	public void setLine_end(String line_end) {
		this.line_end = line_end;
	}
	

	public String getLine_stopby() {
		return this.line_stopby;
	}

	public void setLine_stopby(String line_stopby) {
		this.line_stopby = line_stopby;
	}
	

	public String getLine_road() {
		return this.line_road;
	}

	public void setLine_road(String line_road) {
		this.line_road = line_road;
	}
	

	public String getLine_startstop() {
		return this.line_startstop;
	}

	public void setLine_startstop(String line_startstop) {
		this.line_startstop = line_startstop;
	}
	

	public String getLine_endstop() {
		return this.line_endstop;
	}

	public void setLine_endstop(String line_endstop) {
		this.line_endstop = line_endstop;
	}
	

	public String getLine_midwaystop() {
		return this.line_midwaystop;
	}

	public void setLine_midwaystop(String line_midwaystop) {
		this.line_midwaystop = line_midwaystop;
	}
	

	public String getLine_lasttime() {
		return this.line_lasttime;
	}

	public void setLine_lasttime(String line_lasttime) {
		this.line_lasttime = line_lasttime;
	}
	

	public String getLine_distance() {
		return this.line_distance;
	}

	public void setLine_distance(String line_distance) {
		this.line_distance = line_distance;
	}
	

	public String getLine_highwaykm() {
		return this.line_highwaykm;
	}

	public void setLine_highwaykm(String line_highwaykm) {
		this.line_highwaykm = line_highwaykm;
	}
	

	public String getLine_roadlevel() {
		return this.line_roadlevel;
	}

	public void setLine_roadlevel(String line_roadlevel) {
		this.line_roadlevel = line_roadlevel;
	}
	

	public String getLine_businesstype() {
		return this.line_businesstype;
	}

	public void setLine_businesstype(String line_businesstype) {
		this.line_businesstype = line_businesstype;
	}
	

	public String getLine_level() {
		return this.line_level;
	}

	public void setLine_level(String line_level) {
		this.line_level = line_level;
	}
	

	public String getLine_businessnature() {
		return this.line_businessnature;
	}

	public void setLine_businessnature(String line_businessnature) {
		this.line_businessnature = line_businessnature;
	}
	

	public String getLine_area() {
		return this.line_area;
	}

	public void setLine_area(String line_area) {
		this.line_area = line_area;
	}
	

	public String getLine_yueyun() {
		return this.line_yueyun;
	}

	public void setLine_yueyun(String line_yueyun) {
		this.line_yueyun = line_yueyun;
	}
	

	public String getLine_institutionname() {
		return this.line_institutionname;
	}

	public void setLine_institutionname(String line_institutionname) {
		this.line_institutionname = line_institutionname;
	}
	

	public String getLine_amount() {
		return this.line_amount;
	}

	public void setLine_amount(String line_amount) {
		this.line_amount = line_amount;
	}
	

	public String getLine_daybus() {
		return this.line_daybus;
	}

	public void setLine_daybus(String line_daybus) {
		this.line_daybus = line_daybus;
	}
	

	public String getLine_avgday_bus() {
		return this.line_avgday_bus;
	}

	public void setLine_avgday_bus(String line_avgday_bus) {
		this.line_avgday_bus = line_avgday_bus;
	}
	

	public String getLine_avgday_income() {
		return this.line_avgday_income;
	}

	public void setLine_avgday_income(String line_avgday_income) {
		this.line_avgday_income = line_avgday_income;
	}
	

	public String getLine_avgday_cust() {
		return this.line_avgday_cust;
	}

	public void setLine_avgday_cust(String line_avgday_cust) {
		this.line_avgday_cust = line_avgday_cust;
	}
	

	public String getLine_competeway() {
		return this.line_competeway;
	}

	public void setLine_competeway(String line_competeway) {
		this.line_competeway = line_competeway;
	}
	

	public String getLine_competecar() {
		return this.line_competecar;
	}

	public void setLine_competecar(String line_competecar) {
		this.line_competecar = line_competecar;
	}
	

	public String getLine_carryrate() {
		return this.line_carryrate;
	}

	public void setLine_carryrate(String line_carryrate) {
		this.line_carryrate = line_carryrate;
	}
	

	public Date getLine_begtime() {
		return this.line_begtime;
	}

	public void setLine_begtime(Date line_begtime) {
		this.line_begtime = line_begtime;
	}
	

	public Date getLine_endtime() {
		return this.line_endtime;
	}

	public void setLine_endtime(Date line_endtime) {
		this.line_endtime = line_endtime;
	}
	

	public Date getMdm_createdon() {
		return this.mdm_createdon;
	}

	public void setMdm_createdon(Date mdm_createdon) {
		this.mdm_createdon = mdm_createdon;
	}
	

	public Date getMdm_modifiedon() {
		return this.mdm_modifiedon;
	}

	public void setMdm_modifiedon(Date mdm_modifiedon) {
		this.mdm_modifiedon = mdm_modifiedon;
	}
	

	public String getMdm_createdby() {
		return this.mdm_createdby;
	}

	public void setMdm_createdby(String mdm_createdby) {
		this.mdm_createdby = mdm_createdby;
	}
	

	public String getZhky_code() {
		return this.zhky_code;
	}

	public void setZhky_code(String zhky_code) {
		this.zhky_code = zhky_code;
	}
	

	public String getNyt_code() {
		return this.nyt_code;
	}

	public void setNyt_code(String nyt_code) {
		this.nyt_code = nyt_code;
	}
	

	public String getRc_code() {
		return this.rc_code;
	}

	public void setRc_code(String rc_code) {
		this.rc_code = rc_code;
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
	        return "Lines";
	    }

	    @Override
	    public String getNamespace() {
	        return "iuap-mdm-yueyun";
	    }
}