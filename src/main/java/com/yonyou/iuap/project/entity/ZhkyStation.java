package com.yonyou.iuap.project.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

@Entity(namespace = "iuap-mdm-yueyun",name = "ZhkyStation")
@Table(name="zhky_station1")
public class ZhkyStation extends BaseEntity{

	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "id")
	private String id;
	
	@Column(name = "mcode")
	private String mcode;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "mdm_code")
	private String mdm_code;
	
	@Column(name = "name1")
	private String name1;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	@Override
    public String getMetaDefinedName() {
	        return "ZhkyStation";
	    }

	    @Override
	    public String getNamespace() {
	        return "iuap-mdm-yueyun";
	    }
}
