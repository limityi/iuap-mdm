package com.yonyou.iuap.project.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

/**
 * <b> 此处简要描述此类功能 </b>
 * <p>
 * 此处添加类的描述信息
 * </p>
 * 
 * @author
 * @version
 */
@Entity(namespace = "iuap-mdm-yueyun", name = "BusLine")
@Table(name = "mdm_busline")
public class BusLine extends BaseEntity {

	@Id
	@GeneratedValue(strategy = Stragegy.UUID, moudle = "")
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

	@Column(name = "description")
	private String description;

	@Column(name = "line_shortname")
	private String line_shortname;

	@Column(name = "line_startdistrict")
	private String line_startdistrict;

	@Column(name = "line_endistrict")
	private String line_endistrict;

	@Column(name = "line_stopby")
	private String line_stopby;

	@Column(name = "line_lasttime")
	private String line_lasttime;

	@Column(name = "line_distance")
	private String line_distance;

	@Column(name = "line_businesstype")
	private String line_businesstype;

	@Column(name = "line_level")
	private String line_level;

	@Column(name = "line_businessnature")
	private String line_businessnature;

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
	private String line_begtime;

	@Column(name = "line_endtime")
	private String line_endtime;

	@Column(name = "mdm_createdon")
	private String mdm_createdon;

	@Column(name = "mdm_modifiedon")
	private String mdm_modifiedon;

	@Column(name = "mdm_createdby")
	private String mdm_createdby;
	
	@Column(name = "mdm_parentcode")
	private String mdm_parentcode;
	
	@Column(name = "mdm_status")
	private java.lang.Integer mdm_status;
	
	@Column(name = "mdm_duplicate")
	private java.lang.Integer mdm_duplicate;
	
	@Column(name = "mdm_seal")
	private java.lang.Integer mdm_seal;
	
	@Column(name = "mdm_createdby_type")
	private String mdm_createdby_type;
	
	@Column(name = "mdm_modifiedby_type")
	private String mdm_modifiedby_type;
	
	@Column(name = "makedept")
	private String makedept;
	
	@Column(name = "makedate")
	private String makedate;
	
	@Column(name = "frmtitle")
	private String frmtitle;
	
	@Column(name = "makeuser")
	private String makeuser;
	
	@Column(name = "audituser")
	private String audituser;
	
	@Column(name = "auditdate")
	private String auditdate;
	
	@Column(name = "makenum")
	private String makenum;
	
	@Column(name = "frmstate")
	private String frmstate;
	
	@Column(name = "resourcesys")
	private String resourcesys;
	
	@Column(name = "nc_code")
	private String nc_code;

	@Column(name = "dr")
	private java.lang.Integer dr = 0;

	@Column(name = "ts")
	private java.sql.Timestamp ts;

	// 系统标志
	private String tag;

	// 相似度
	private String similarity;

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

	public String getLine_endistrict() {
		return this.line_endistrict;
	}

	public void setLine_endistrict(String line_endistrict) {
		this.line_endistrict = line_endistrict;
	}

	public String getLine_stopby() {
		return this.line_stopby;
	}

	public void setLine_stopby(String line_stopby) {
		this.line_stopby = line_stopby;
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

	public String getLine_begtime() {
		return this.line_begtime;
	}

	public void setLine_begtime(String line_begtime) {
		this.line_begtime = line_begtime;
	}

	public String getLine_endtime() {
		return this.line_endtime;
	}

	public void setLine_endtime(String line_endtime) {
		this.line_endtime = line_endtime;
	}

	public String getMdm_createdon() {
		return this.mdm_createdon;
	}

	public void setMdm_createdon(String mdm_createdon) {
		this.mdm_createdon = mdm_createdon;
	}

	public String getMdm_modifiedon() {
		return this.mdm_modifiedon;
	}

	public void setMdm_modifiedon(String mdm_modifiedon) {
		this.mdm_modifiedon = mdm_modifiedon;
	}

	public String getMdm_createdby() {
		return this.mdm_createdby;
	}

	public void setMdm_createdby(String mdm_createdby) {
		this.mdm_createdby = mdm_createdby;
	}

	public java.lang.Integer getDr() {
		return dr;
	}

	public void setDr(java.lang.Integer newDr) {
		this.dr = newDr;
	}

	public java.sql.Timestamp getTs() {
		return ts;
	}

	public void setTs(java.sql.Timestamp newTs) {
		this.ts = newTs;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSimilarity() {
		return similarity;
	}

	public void setSimilarity(String similarity) {
		this.similarity = similarity;
	}	

	public String getMdm_parentcode() {
		return mdm_parentcode;
	}

	public void setMdm_parentcode(String mdm_parentcode) {
		this.mdm_parentcode = mdm_parentcode;
	}

	public java.lang.Integer getMdm_status() {
		return mdm_status;
	}

	public void setMdm_status(java.lang.Integer mdm_status) {
		this.mdm_status = mdm_status;
	}

	public java.lang.Integer getMdm_duplicate() {
		return mdm_duplicate;
	}

	public void setMdm_duplicate(java.lang.Integer mdm_duplicate) {
		this.mdm_duplicate = mdm_duplicate;
	}

	public java.lang.Integer getMdm_seal() {
		return mdm_seal;
	}

	public void setMdm_seal(java.lang.Integer mdm_seal) {
		this.mdm_seal = mdm_seal;
	}

	public String getMdm_createdby_type() {
		return mdm_createdby_type;
	}

	public void setMdm_createdby_type(String mdm_createdby_type) {
		this.mdm_createdby_type = mdm_createdby_type;
	}

	public String getMdm_modifiedby_type() {
		return mdm_modifiedby_type;
	}

	public void setMdm_modifiedby_type(String mdm_modifiedby_type) {
		this.mdm_modifiedby_type = mdm_modifiedby_type;
	}

	public String getMakedept() {
		return makedept;
	}

	public void setMakedept(String makedept) {
		this.makedept = makedept;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public String getFrmtitle() {
		return frmtitle;
	}

	public void setFrmtitle(String frmtitle) {
		this.frmtitle = frmtitle;
	}

	public String getMakeuser() {
		return makeuser;
	}

	public void setMakeuser(String makeuser) {
		this.makeuser = makeuser;
	}

	public String getAudituser() {
		return audituser;
	}

	public void setAudituser(String audituser) {
		this.audituser = audituser;
	}

	public String getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(String auditdate) {
		this.auditdate = auditdate;
	}

	public String getMakenum() {
		return makenum;
	}

	public void setMakenum(String makenum) {
		this.makenum = makenum;
	}

	public String getFrmstate() {
		return frmstate;
	}

	public void setFrmstate(String frmstate) {
		this.frmstate = frmstate;
	}

	public String getResourcesys() {
		return resourcesys;
	}

	public void setResourcesys(String resourcesys) {
		this.resourcesys = resourcesys;
	}

	public String getNc_code() {
		return nc_code;
	}

	public void setNc_code(String nc_code) {
		this.nc_code = nc_code;
	}

	@Override
	public String getMetaDefinedName() {
		return "BusLine";
	}

	@Override
	public String getNamespace() {
		return "iuap-mdm-yueyun";
	}
}
