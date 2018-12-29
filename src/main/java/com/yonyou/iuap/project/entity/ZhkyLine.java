package com.yonyou.iuap.project.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

@Entity(namespace = "iuap-mdm-yueyun",name = "ZhkyLine")
@Table(name="zhky_line")
public class ZhkyLine extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "id")
	private String id;
	
	@Column(name = "mcode")
	private String mcode;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "lcode")
	private String lcode;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "mdm_code")
	private String mdm_code;
	
	@Column(name = "name1")
	private String name1;
	
	@Column(name = "line_level")
	private String line_level;
	
	@Column(name = "llinelevel")
	private String llinelevel;
		
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

	public String getLcode() {
		return lcode;
	}

	public void setLcode(String lcode) {
		this.lcode = lcode;
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

	public String getLine_level() {
		return line_level;
	}

	public void setLine_level(String line_level) {
		this.line_level = line_level;
	}	

	public String getLlinelevel() {
		return llinelevel;
	}

	public void setLlinelevel(String llinelevel) {
		this.llinelevel = llinelevel;
	}

	@Override
    public String getMetaDefinedName() {
	        return "ZhkyLine";
	    }

	    @Override
	    public String getNamespace() {
	        return "iuap-mdm-yueyun";
	    }
	

}
