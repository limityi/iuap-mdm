package com.yonyou.iuap.project.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

/**
 * @Description 网上飞网点-站场
 *
 * @author binbin
 * @date 2018/12/18 14:43
 */
@Entity(namespace = "iuap-mdm-yueyun", name = "DotStation")
@Table(name = "mdm_dot")
public class DotStation extends BaseEntity{
	
	@Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "")
    @Column(name = "id")
    private String id;
	
	@Column(name = "code")
    private String code;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "stationname")
    private String stationname;
	
	@Column(name = "stationcode")
    private String stationcode;
	
	@Column(name = "mdm_pk")
    private String mdm_pk;

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

	public String getStationname() {
		return stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}

	public String getStationcode() {
		return stationcode;
	}

	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}

	public String getMdm_pk() {
		return mdm_pk;
	}

	public void setMdm_pk(String mdm_pk) {
		this.mdm_pk = mdm_pk;
	}
	
	@Override
    public String getMetaDefinedName() {
	        return "DotStation";
	    }

	@Override
	public String getNamespace() {
	        return "iuap-mdm-yueyun";
	    }
	

}
