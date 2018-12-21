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
@Entity(namespace = "iuap-mdm-yueyun",name = "Fleet")
@Table(name="mdm_fleet")
public class Fleet extends BaseEntity{
	
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
	
	@Column(name = "bus_num")
	private String bus_num;
	
	@Column(name = "business_org")
	private String business_org;
	
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

	public String getBus_num() {
		return bus_num;
	}

	public void setBus_num(String bus_num) {
		this.bus_num = bus_num;
	}

	public String getBusiness_org() {
		return business_org;
	}

	public void setBusiness_org(String business_org) {
		this.business_org = business_org;
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
	        return "Fleet";
	    }

	    @Override
	    public String getNamespace() {
	        return "iuap-mdm-yueyun";
	    }

}
