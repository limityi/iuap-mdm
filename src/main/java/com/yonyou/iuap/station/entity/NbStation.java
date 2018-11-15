package com.yonyou.iuap.station.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

/**
 * <b> 站场实体类 </b>
 * <p>
 *   此处添加类的描述信息
 * </p>
 * @author limit
 * @version 
 */
@Entity(namespace = "iuap-mdm-yueyun",name = "NbStation")
@Table(name="mdm_nbstation")
public class NbStation extends BaseEntity {
	  
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
	
	@Column(name = "sjt_code")
	private String sjt_code;
	
	@Column(name = "station_shortname")
	private String station_shortname;
	
	@Column(name = "station_address")
	private String station_address;
	
	@Column(name = "station_gdprovince")
	private String station_gdprovince;
	
	@Column(name = "station_yueyun")
	private String station_yueyun;
	
	@Column(name = "station_companyid")
	private String station_companyid;
	
	@Column(name = "station_companyname")
	private String station_companyname;
	
	@Column(name = "station_companycode")
	private String station_companycode;
	
	@Column(name = "station_level")
	private String station_level;
	
	@Column(name = "station_servicetype")
	private String station_servicetype;
	
	@Column(name = "station_type")
	private String station_type;

	@Column(name = "dr")
    private java.lang.Integer dr = 0 ;
      
    @Column(name = "ts")
    private java.sql.Timestamp ts ;

    //系统标志
    private String tag;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

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
	

	public String getSjt_code() {
		return this.sjt_code;
	}

	public void setSjt_code(String sjt_code) {
		this.sjt_code = sjt_code;
	}
	

	public String getStation_shortname() {
		return this.station_shortname;
	}

	public void setStation_shortname(String station_shortname) {
		this.station_shortname = station_shortname;
	}
	

	public String getStation_address() {
		return this.station_address;
	}

	public void setStation_address(String station_address) {
		this.station_address = station_address;
	}
	

	public String getStation_gdprovince() {
		return this.station_gdprovince;
	}

	public void setStation_gdprovince(String station_gdprovince) {
		this.station_gdprovince = station_gdprovince;
	}
	

	public String getStation_yueyun() {
		return this.station_yueyun;
	}

	public void setStation_yueyun(String station_yueyun) {
		this.station_yueyun = station_yueyun;
	}
	

	public String getStation_companyid() {
		return this.station_companyid;
	}

	public void setStation_companyid(String station_companyid) {
		this.station_companyid = station_companyid;
	}
	

	public String getStation_companyname() {
		return this.station_companyname;
	}

	public void setStation_companyname(String station_companyname) {
		this.station_companyname = station_companyname;
	}
	

	public String getStation_companycode() {
		return this.station_companycode;
	}

	public void setStation_companycode(String station_companycode) {
		this.station_companycode = station_companycode;
	}
	

	public String getStation_level() {
		return this.station_level;
	}

	public void setStation_level(String station_level) {
		this.station_level = station_level;
	}
	

	public String getStation_servicetype() {
		return this.station_servicetype;
	}

	public void setStation_servicetype(String station_servicetype) {
		this.station_servicetype = station_servicetype;
	}
	

	public String getStation_type() {
		return this.station_type;
	}

	public void setStation_type(String station_type) {
		this.station_type = station_type;
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
	        return "NbStation";
	    }

	@Override
	public String getNamespace() {
	        return "iuap-mdm-yueyun";
	    }
}