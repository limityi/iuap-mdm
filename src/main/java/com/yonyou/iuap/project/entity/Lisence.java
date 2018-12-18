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
 *   此处添加类的描述信息
 * </p>
 * @author 
 * @version 
 */
@Entity(namespace = "iuap-mdm-yueyun",name = "Lisence")
@Table(name="mdm_lisence")
public class Lisence extends BaseEntity{
	
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

	@Column(name = "lisence_validstart")
	private String lisence_validstart;
	
	@Column(name = "lisence_validend")
	private String lisence_validend;
	
	@Column(name = "lisence_validstatus")
	private String lisence_validstatus;
	
	@Column(name = "lisence_start")
	private String lisence_start;
	
	@Column(name = "lisence_end")
	private String lisence_end;
	
	@Column(name = "lisence_stopby")
	private String lisence_stopby;
	
	@Column(name = "lisence_passlines")
	private String lisence_passlines;
	
	@Column(name = "lisence_busnum")
	private String lisence_busnum;
	
	@Column(name = "lisence_lineid")
	private String lisence_lineid;
	
	@Column(name = "lisence_linename")
	private String lisence_linename;
	
	@Column(name = "lisence_waykm")
	private String lisence_waykm;
	
	@Column(name = "lisence_waytime")
	private String lisence_waytime;
	
	@Column(name = "line_yueyun")
	private String line_yueyun;
	
	@Column(name = "lisence_managementunit")
	private String lisence_managementunit;
	
	@Column(name = "lisence_bustype")
	private String lisence_bustype;
	
	@Column(name = "lisence_businessarea")
	private String lisence_businessarea;
	
	@Column(name = "lisence_linetype")
	private String lisence_linetype;
	
	@Column(name = "lisence_businessnature")
	private String lisence_businessnature;
	
	@Column(name = "lisence_nature")
	private String lisence_nature;
	
	@Column(name = "lisence_usestatus")
	private String lisence_usestatus;
	
	@Column(name = "lisence_vest")
	private String lisence_vest;
	
	@Column(name = "lisence_contractend")
	private String lisence_contractend;
	
	@Column(name = "lisence_belongsid")
	private String lisence_belongsid;
	
	@Column(name = "lisence_by1")
	private String lisence_by1;
	
	@Column(name = "lisence_by2")
	private String lisence_by2;
	
	@Column(name = "mdm_createdon")
	private String mdm_createdon;
	
	@Column(name = "mdm_modifiedon")
	private String mdm_modifiedon;
	
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
    
    //系统标志
  	private String tag;

  	//相似度
  	private String similarity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMdm_code() {
		return mdm_code;
	}

	public void setMdm_code(String mdm_code) {
		this.mdm_code = mdm_code;
	}

	public String getMdm_pk() {
		return mdm_pk;
	}

	public void setMdm_pk(String mdm_pk) {
		this.mdm_pk = mdm_pk;
	}

	public String getLisence_validstart() {
		return lisence_validstart;
	}

	public void setLisence_validstart(String lisence_validstart) {
		this.lisence_validstart = lisence_validstart;
	}

	public String getLisence_validend() {
		return lisence_validend;
	}

	public void setLisence_validend(String lisence_validend) {
		this.lisence_validend = lisence_validend;
	}

	public String getLisence_validstatus() {
		return lisence_validstatus;
	}

	public void setLisence_validstatus(String lisence_validstatus) {
		this.lisence_validstatus = lisence_validstatus;
	}

	public String getLisence_start() {
		return lisence_start;
	}

	public void setLisence_start(String lisence_start) {
		this.lisence_start = lisence_start;
	}

	public String getLisence_end() {
		return lisence_end;
	}

	public void setLisence_end(String lisence_end) {
		this.lisence_end = lisence_end;
	}

	public String getLisence_stopby() {
		return lisence_stopby;
	}

	public void setLisence_stopby(String lisence_stopby) {
		this.lisence_stopby = lisence_stopby;
	}

	public String getLisence_passlines() {
		return lisence_passlines;
	}

	public void setLisence_passlines(String lisence_passlines) {
		this.lisence_passlines = lisence_passlines;
	}

	public String getLisence_busnum() {
		return lisence_busnum;
	}

	public void setLisence_busnum(String lisence_busnum) {
		this.lisence_busnum = lisence_busnum;
	}

	public String getLisence_lineid() {
		return lisence_lineid;
	}

	public void setLisence_lineid(String lisence_lineid) {
		this.lisence_lineid = lisence_lineid;
	}

	public String getLisence_linename() {
		return lisence_linename;
	}

	public void setLisence_linename(String lisence_linename) {
		this.lisence_linename = lisence_linename;
	}

	public String getLisence_waykm() {
		return lisence_waykm;
	}

	public void setLisence_waykm(String lisence_waykm) {
		this.lisence_waykm = lisence_waykm;
	}

	public String getLisence_waytime() {
		return lisence_waytime;
	}

	public void setLisence_waytime(String lisence_waytime) {
		this.lisence_waytime = lisence_waytime;
	}

	public String getLine_yueyun() {
		return line_yueyun;
	}

	public void setLine_yueyun(String line_yueyun) {
		this.line_yueyun = line_yueyun;
	}

	public String getLisence_managementunit() {
		return lisence_managementunit;
	}

	public void setLisence_managementunit(String lisence_managementunit) {
		this.lisence_managementunit = lisence_managementunit;
	}

	public String getLisence_bustype() {
		return lisence_bustype;
	}

	public void setLisence_bustype(String lisence_bustype) {
		this.lisence_bustype = lisence_bustype;
	}

	public String getLisence_businessarea() {
		return lisence_businessarea;
	}

	public void setLisence_businessarea(String lisence_businessarea) {
		this.lisence_businessarea = lisence_businessarea;
	}

	public String getLisence_linetype() {
		return lisence_linetype;
	}

	public void setLisence_linetype(String lisence_linetype) {
		this.lisence_linetype = lisence_linetype;
	}

	public String getLisence_businessnature() {
		return lisence_businessnature;
	}

	public void setLisence_businessnature(String lisence_businessnature) {
		this.lisence_businessnature = lisence_businessnature;
	}

	public String getLisence_nature() {
		return lisence_nature;
	}

	public void setLisence_nature(String lisence_nature) {
		this.lisence_nature = lisence_nature;
	}

	public String getLisence_usestatus() {
		return lisence_usestatus;
	}

	public void setLisence_usestatus(String lisence_usestatus) {
		this.lisence_usestatus = lisence_usestatus;
	}

	public String getLisence_vest() {
		return lisence_vest;
	}

	public void setLisence_vest(String lisence_vest) {
		this.lisence_vest = lisence_vest;
	}

	public String getLisence_contractend() {
		return lisence_contractend;
	}

	public void setLisence_contractend(String lisence_contractend) {
		this.lisence_contractend = lisence_contractend;
	}

	public String getLisence_belongsid() {
		return lisence_belongsid;
	}

	public void setLisence_belongsid(String lisence_belongsid) {
		this.lisence_belongsid = lisence_belongsid;
	}

	public String getLisence_by1() {
		return lisence_by1;
	}

	public void setLisence_by1(String lisence_by1) {
		this.lisence_by1 = lisence_by1;
	}

	public String getLisence_by2() {
		return lisence_by2;
	}

	public void setLisence_by2(String lisence_by2) {
		this.lisence_by2 = lisence_by2;
	}

	public String getMdm_createdon() {
		return mdm_createdon;
	}

	public void setMdm_createdon(String mdm_createdon) {
		this.mdm_createdon = mdm_createdon;
	}

	public String getMdm_modifiedon() {
		return mdm_modifiedon;
	}

	public void setMdm_modifiedon(String mdm_modifiedon) {
		this.mdm_modifiedon = mdm_modifiedon;
	}

	public String getMdm_createdby() {
		return mdm_createdby;
	}

	public void setMdm_createdby(String mdm_createdby) {
		this.mdm_createdby = mdm_createdby;
	}

	public String getZhky_code() {
		return zhky_code;
	}

	public void setZhky_code(String zhky_code) {
		this.zhky_code = zhky_code;
	}

	public String getNyt_code() {
		return nyt_code;
	}

	public void setNyt_code(String nyt_code) {
		this.nyt_code = nyt_code;
	}

	public String getRc_code() {
		return rc_code;
	}

	public void setRc_code(String rc_code) {
		this.rc_code = rc_code;
	}

	public java.lang.Integer getDr() {
		return dr;
	}

	public void setDr(java.lang.Integer dr) {
		this.dr = dr;
	}

	public java.sql.Timestamp getTs() {
		return ts;
	}

	public void setTs(java.sql.Timestamp ts) {
		this.ts = ts;
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
  	
	@Override
    public String getMetaDefinedName() {
	        return "Lisence";
	    }

	    @Override
	    public String getNamespace() {
	        return "iuap-mdm-yueyun";
	    }

}
