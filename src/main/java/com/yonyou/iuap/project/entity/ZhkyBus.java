package com.yonyou.iuap.project.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

@Entity(namespace = "iuap-mdm-yueyun",name = "ZhkyBus")
@Table(name="zhky_bus")
public class ZhkyBus extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "id")
	private String id;
	
	@Column(name = "mdm_code")
	private String mdm_code;
	
	@Column(name = "mcode")
	private String mcode;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "bus_lisencenum")
	private String bus_lisencenum;
	
	@Column(name = "bus_lisencenum1")
	private String bus_lisencenum1;
	
	@Column(name = "bus_depart")
	private String bus_depart;
	
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

	public String getMcode() {
		return mcode;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBus_lisencenum() {
		return bus_lisencenum;
	}

	public void setBus_lisencenum(String bus_lisencenum) {
		this.bus_lisencenum = bus_lisencenum;
	}

	public String getBus_lisencenum1() {
		return bus_lisencenum1;
	}

	public void setBus_lisencenum1(String bus_lisencenum1) {
		this.bus_lisencenum1 = bus_lisencenum1;
	}

	public String getBus_depart() {
		return bus_depart;
	}

	public void setBus_depart(String bus_depart) {
		this.bus_depart = bus_depart;
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
	
	

	public String getMdm_code() {
		return mdm_code;
	}

	public void setMdm_code(String mdm_code) {
		this.mdm_code = mdm_code;
	}

	@Override
    public String getMetaDefinedName() {
	        return "ZhkyBus";
	    }

	    @Override
	    public String getNamespace() {
	        return "iuap-mdm-yueyun";
	    }

}
