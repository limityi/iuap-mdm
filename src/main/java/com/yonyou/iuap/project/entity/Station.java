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
@Entity(namespace = "iuap-mdm-yueyun",name = "Station")
@Table(name="mdm_station")
public class Station extends BaseEntity {
	  
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
	
	@Column(name = "station_group_shortname")
	private String station_group_shortname;
	
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
	
	@Column(name = "station_company")
	private String station_company;
	
	@Column(name = "station_districtid")
	private String station_districtid;
	
	@Column(name = "station_level")
	private String station_level;
	
	@Column(name = "station_servicetype")
	private String station_servicetype;
	
	@Column(name = "station_area")
	private String station_area;
	
	@Column(name = "station_parea")
	private String station_parea;
	
	@Column(name = "station_leasearea")
	private String station_leasearea;
	
	@Column(name = "station_pnum")
	private String station_pnum;
	
	@Column(name = "station_type")
	private String station_type;
	
	@Column(name = "station_businessnature")
	private String station_businessnature;
	
	@Column(name = "station_own")
	private String station_own;
	
	@Column(name = "station_businessmode")
	private String station_businessmode;
	
	@Column(name = "station_busnum")
	private String station_busnum;
	
	@Column(name = "station_longitude")
	private String station_longitude;
	
	@Column(name = "station_latitude")
	private String station_latitude;
	
	@Column(name = "station_avgday_bus")
	private String station_avgday_bus;
	
	@Column(name = "station_avgday_income")
	private String station_avgday_income;
	
	@Column(name = "station_avgday_cust")
	private String station_avgday_cust;
	
	@Column(name = "station_acquisition")
	private String station_acquisition;
	
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

	public String getSimilarity() {
		return similarity;
	}

	public void setSimilarity(String similarity) {
		this.similarity = similarity;
	}

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
	

	public String getStation_group_shortname() {
		return this.station_group_shortname;
	}

	public void setStation_group_shortname(String station_group_shortname) {
		this.station_group_shortname = station_group_shortname;
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
	

	public String getStation_company() {
		return this.station_company;
	}

	public void setStation_company(String station_company) {
		this.station_company = station_company;
	}
	

	public String getStation_districtid() {
		return this.station_districtid;
	}

	public void setStation_districtid(String station_districtid) {
		this.station_districtid = station_districtid;
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
	

	public String getStation_area() {
		return this.station_area;
	}

	public void setStation_area(String station_area) {
		this.station_area = station_area;
	}
	

	public String getStation_parea() {
		return this.station_parea;
	}

	public void setStation_parea(String station_parea) {
		this.station_parea = station_parea;
	}
	

	public String getStation_leasearea() {
		return this.station_leasearea;
	}

	public void setStation_leasearea(String station_leasearea) {
		this.station_leasearea = station_leasearea;
	}
	

	public String getStation_pnum() {
		return this.station_pnum;
	}

	public void setStation_pnum(String station_pnum) {
		this.station_pnum = station_pnum;
	}
	

	public String getStation_type() {
		return this.station_type;
	}

	public void setStation_type(String station_type) {
		this.station_type = station_type;
	}
	

	public String getStation_businessnature() {
		return this.station_businessnature;
	}

	public void setStation_businessnature(String station_businessnature) {
		this.station_businessnature = station_businessnature;
	}
	

	public String getStation_own() {
		return this.station_own;
	}

	public void setStation_own(String station_own) {
		this.station_own = station_own;
	}
	

	public String getStation_businessmode() {
		return this.station_businessmode;
	}

	public void setStation_businessmode(String station_businessmode) {
		this.station_businessmode = station_businessmode;
	}
	

	public String getStation_busnum() {
		return this.station_busnum;
	}

	public void setStation_busnum(String station_busnum) {
		this.station_busnum = station_busnum;
	}
	

	public String getStation_longitude() {
		return this.station_longitude;
	}

	public void setStation_longitude(String station_longitude) {
		this.station_longitude = station_longitude;
	}
	

	public String getStation_latitude() {
		return this.station_latitude;
	}

	public void setStation_latitude(String station_latitude) {
		this.station_latitude = station_latitude;
	}
	

	public String getStation_avgday_bus() {
		return this.station_avgday_bus;
	}

	public void setStation_avgday_bus(String station_avgday_bus) {
		this.station_avgday_bus = station_avgday_bus;
	}
	

	public String getStation_avgday_income() {
		return this.station_avgday_income;
	}

	public void setStation_avgday_income(String station_avgday_income) {
		this.station_avgday_income = station_avgday_income;
	}
	

	public String getStation_avgday_cust() {
		return this.station_avgday_cust;
	}

	public void setStation_avgday_cust(String station_avgday_cust) {
		this.station_avgday_cust = station_avgday_cust;
	}
	

	public String getStation_acquisition() {
		return this.station_acquisition;
	}

	public void setStation_acquisition(String station_acquisition) {
		this.station_acquisition = station_acquisition;
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
	        return "Station";
	    }

	    @Override
	    public String getNamespace() {
	        return "iuap-mdm-yueyun";
	    }
}