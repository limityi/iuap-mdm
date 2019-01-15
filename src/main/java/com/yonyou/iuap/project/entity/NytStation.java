package com.yonyou.iuap.project.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

@Entity(namespace = "iuap-mdm-yueyun",name = "NytStation")
@Table(name="nyt_station")
public class NytStation extends BaseEntity{

	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "id")
	private String id;
	
	@Column(name = "mcode")
	private String mcode;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "station_companyname")
	private String station_companyname;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "mdm_code")
	private String mdm_code;
	
	@Column(name = "mdm_pk")
	private String mdm_pk;
	
	@Column(name = "name1")
	private String name1;

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

	public String getStation_companyname() {
		return station_companyname;
	}

	public void setStation_companyname(String station_companyname) {
		this.station_companyname = station_companyname;
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

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}	
	
	public String getMdm_pk() {
		return mdm_pk;
	}

	public void setMdm_pk(String mdm_pk) {
		this.mdm_pk = mdm_pk;
	}

	@Override
    public String getMetaDefinedName() {
	        return "NytStation";
	    }

	@Override
	public String getNamespace() {
	        return "iuap-mdm-yueyun";
	    }
}
