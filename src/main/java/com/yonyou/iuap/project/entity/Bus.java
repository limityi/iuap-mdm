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
@Entity(namespace = "iuap-mdm-yueyun",name = "Bus")
@Table(name="mdm_bus")
public class Bus extends BaseEntity{
	
	  @Id
	  @GeneratedValue(strategy = Stragegy.UUID, moudle = "")
	  @Column(name = "id")
	  private String id;
      
	  @Column(name = "mdm_pk")
	  private String mdm_pk;
	  
	  @Column(name = "mdm_parentcode")
	  private String mdm_parentcode;
	  
	  @Column(name = "mdm_code")
	  private String mdm_code;
	  
	  @Column(name = "mdm_status")
	  private String mdm_status;
	  
	  @Column(name = "mdm_duplicate")
	  private String mdm_duplicate;
	  
	  @Column(name = "mdm_seal")
	  private String mdm_seal;
	  
	  @Column(name = "mdm_createdby_type")
	  private String mdm_createdby_type;
	  
	  @Column(name = "mdm_createdby")
	  private String mdm_createdby;
	  
	  @Column(name = "mdm_createdon")
	  private String mdm_createdon;
	  
	  @Column(name = "mdm_modifiedby_type")
	  private String mdm_modifiedby_type;
	  
	  @Column(name = "mdm_modifiedby")
	  private String mdm_modifiedby;
	  
	  @Column(name = "mdm_modifiedon")
	  private String mdm_modifiedon;
	  
	  @Column(name = "name")
	  private String name;
	  
	  @Column(name = "name1")
	  private String name1;
	  
	  @Column(name = "name2")
	  private String name2;
	  
	  @Column(name = "code")
	  private String code;
	  
	  @Column(name = "description")
	  private String description;
	  
	  @Column(name = "bus_color")
	  private String bus_color;
	  
	  @Column(name = "bus_lisencenum")
	  private String bus_lisencenum;
	  
	  @Column(name = "bus_specialllisence")
	  private String bus_specialllisence;
	  
	  @Column(name = "bus_depart")
	  private String bus_depart;
	  
	  @Column(name = "bus_departid")
	  private String bus_departid;
	  
	  @Column(name = "bus_brand")
	  private String bus_brand;
	  
	  @Column(name = "bus_model")
	  private String bus_model;
	  
	  @Column(name = "bus_add")
	  private String bus_add;
	  
	  @Column(name = "bus_type")
	  private String bus_type;
	  
	  @Column(name = "bus_oprationtype")
	  private String bus_oprationtype;
	  
	  @Column(name = "bus_bodycolor")
	  private String bus_bodycolor;
	  
	  @Column(name = "bus_move")
	  private String bus_move;
	  
	  @Column(name = "bus_swrl")
	  private String bus_swrl;
	  
	  @Column(name = "bus_boxbrand")
	  private String bus_boxbrand;
	  
	  @Column(name = "bus_boxmodel")
	  private String bus_boxmodel;
	  
	  @Column(name = "bus_engin")
	  private String bus_engin;
	  
	  @Column(name = "bus_framid")
	  private String bus_framid;
	  
	  @Column(name = "bus_vin")
	  private String bus_vin;
	  
	  @Column(name = "bus_oiltype")
	  private String bus_oiltype;
	  
	  @Column(name = "bus_lngsystem")
	  private String bus_lngsystem;
	  
	  @Column(name = "bus_batterytype")
	  private String bus_batterytype;
	  
	  @Column(name = "bus_batterybrand")
	  private String bus_batterybrand;
	  
	  @Column(name = "bus_motocbrand")
	  private String bus_motocbrand;
	  
	  @Column(name = "bus_airbrand")
	  private String bus_airbrand;
	  
	  @Column(name = "bus_frontbrand")
	  private String bus_frontbrand;
	  
	  @Column(name = "bus_frontton")
	  private String bus_frontton;
	  
	  @Column(name = "bus_rearbrand")
	  private String bus_rearbrand;
	  
	  @Column(name = "bus_rearton")
	  private String bus_rearton;
	  
	  @Column(name = "bus_jpg")
	  private String bus_jpg;
	  
	  @Column(name = "bus_axlelength")
	  private String bus_axlelength;
	  
	  @Column(name = "bus_axle")
	  private String bus_axle;
	  
	  @Column(name = "bus_length")
	  private String bus_length;
	  
	  @Column(name = "bus_height")
	  private String bus_height;
	  
	  @Column(name = "bus_weight")
	  private String bus_weight;
	  
	  @Column(name = "bus_code")
	  private String bus_code;
	  
	  @Column(name = "bus_transportid")
	  private String bus_transportid;
	  
	  @Column(name = "bus_transportorgan")
	  private String bus_transportorgan;
	  
	  @Column(name = "bus_peroidstart")
	  private String bus_peroidstart;
	  
	  @Column(name = "bus_peroidend")
	  private String bus_peroidend;
	  
	  @Column(name = "bus_range")
	  private String bus_range;
	  
	  @Column(name = "bus_maint")
	  private String bus_maint;
	  
	  @Column(name = "bus_oprationstatus")
	  private String bus_oprationstatus;
	  
	  @Column(name = "bus_techlevel")
	  private String bus_techlevel;
	  
	  @Column(name = "bus_checkstatus")
	  private String bus_checkstatus;
	  
	  @Column(name = "bus_record")
	  private String bus_record;
	  
	  @Column(name = "bus_gps")
	  private String bus_gps;
	  
	  @Column(name = "bus_recordstatus")
	  private String bus_recordstatus;
	  
	  @Column(name = "bus_gpsstatus")
	  private String bus_gpsstatus;
	  
	  @Column(name = "bus_permitid")
	  private String bus_permitid;
	  
	  @Column(name = "bus_level")
	  private String bus_level;
	  
	  @Column(name = "bus_seat")
	  private String bus_seat;
	  
	  @Column(name = "bus_loadcustomer")
	  private String bus_loadcustomer;
	  
	  @Column(name = "bus_loadweight")
	  private String bus_loadweight;
	  
	  @Column(name = "bus_ton")
	  private String bus_ton;
	  
	  @Column(name = "bus_teu")
	  private String bus_teu;
	  
	  @Column(name = "bus_volume")
	  private String bus_volume;
	  
	  @Column(name = "bus_commercialtype")
	  private String bus_commercialtype;
	  
	  @Column(name = "bus_grossprice")
	  private String bus_grossprice;
	  
	  @Column(name = "bus_purchase")
	  private String bus_purchase;
	  
	  @Column(name = "bus_initial")
	  private String bus_initial;
	  
	  @Column(name = "bus_seattype")
	  private String bus_seattype;
	  
	  @Column(name = "bus_registnum")
	  private String bus_registnum;
	  
	  @Column(name = "bus_registdate")
	  private String bus_registdate;
	  
	  @Column(name = "bus_certificationdate")
	  private String bus_certificationdate;
	  
	  @Column(name = "bus_checkperiod")
	  private String bus_checkperiod;
	  
	  @Column(name = "bus_certificationorgan")
	  private String bus_certificationorgan;
	  
	  @Column(name = "bus_purchasedate")
	  private String bus_purchasedate;
	  
	  @Column(name = "bus_purchasid")
	  private String bus_purchasid;
	  
	  @Column(name = "bus_releasedate")
	  private String bus_releasedate;
	  
	  @Column(name = "bus_scrapdate")
	  private String bus_scrapdate;
	  
	  @Column(name = "bus_roughweight")
	  private String bus_roughweight;
	  
	  @Column(name = "bus_grossweight")
	  private String bus_grossweight;
	  
	  @Column(name = "bus_traction")
	  private String bus_traction;
	  
	  @Column(name = "bus_spring")
	  private String bus_spring;
	  
	  @Column(name = "bus_tyrecount")
	  private String bus_tyrecount;
	  
	  @Column(name = "bus_firsttime")
	  private String bus_firsttime;
	  
	  @Column(name = "bus_firstdistance")
	  private String bus_firstdistance;
	  
	  @Column(name = "bus_secondtime")
	  private String bus_secondtime;
	  
	  @Column(name = "bus_seconddistance")
	  private String bus_seconddistance;
	  
	  @Column(name = "bus_change")
	  private String bus_change;
	  
	  @Column(name = "bus_discharge_standard")
	  private String bus_discharge_standard;
	  
	  @Column(name = "bus_retarder_brand")
	  private String bus_retarder_brand;
	  
	  @Column(name = "bus_can_brand")
	  private String bus_can_brand;
	  
	  @Column(name = "bus_chassis")
	  private String bus_chassis;
	  
	  @Column(name = "bus_engin_brand")
	  private String bus_engin_brand;
	  
	  @Column(name = "bus_power")
	  private String bus_power;
	  
	  @Column(name = "bus_bev_airbrand")
	  private String bus_bev_airbrand;
	  
	  @Column(name = "bus_bev_steerbrand")
	  private String bus_bev_steerbrand;
	  
	  @Column(name = "bus_bev_acrbrand")
	  private String bus_bev_acrbrand;
	  
	  @Column(name = "bus_ecu_brand")
	  private String bus_ecu_brand;
	  
	  @Column(name = "makeuser")
	  private String makeuser;
	  
	  @Column(name = "frmtitile")
	  private String frmtitile;
	  
	  @Column(name = "makedate")
	  private String makedate;
	  
	  @Column(name = "makedept")
	  private String makedept;
	  
	  @Column(name = "makenum")
	  private String makenum;
	  
	  @Column(name = "audituser")
	  private String audituser;
	  
	  @Column(name = "auditdate")
	  private String auditdate;
	  
	  @Column(name = "frmstate")
	  private String frmstate;
	  
	  @Column(name = "color")
	  private String color;
	  
	  @Column(name = "dr")
		private java.lang.Integer dr = 0;

		@Column(name = "ts")
		private java.sql.Timestamp ts;
		
		// 系统标志
		private String tag;

		// 相似度
		private String similarity;
	  
	  @Column(name = "resourcesys")
	  private String resourcesys;
	  
	  

	  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMdm_pk() {
	    return mdm_pk;
	  }

	  public void setMdm_pk(String mdm_pk) {
	    this.mdm_pk = mdm_pk;
	  }

	  public String getMdm_parentcode() {
	    return mdm_parentcode;
	  }

	  public void setMdm_parentcode(String mdm_parentcode) {
	    this.mdm_parentcode = mdm_parentcode;
	  }

	  public String getMdm_code() {
	    return mdm_code;
	  }

	  public void setMdm_code(String mdm_code) {
	    this.mdm_code = mdm_code;
	  }

	  public String getMdm_status() {
	    return mdm_status;
	  }

	  public void setMdm_status(String mdm_status) {
	    this.mdm_status = mdm_status;
	  }

	  public String getMdm_duplicate() {
	    return mdm_duplicate;
	  }

	  public void setMdm_duplicate(String mdm_duplicate) {
	    this.mdm_duplicate = mdm_duplicate;
	  }

	  public String getMdm_seal() {
	    return mdm_seal;
	  }

	  public void setMdm_seal(String mdm_seal) {
	    this.mdm_seal = mdm_seal;
	  }

	  public String getMdm_createdby_type() {
	    return mdm_createdby_type;
	  }

	  public void setMdm_createdby_type(String mdm_createdby_type) {
	    this.mdm_createdby_type = mdm_createdby_type;
	  }

	  public String getMdm_createdby() {
	    return mdm_createdby;
	  }

	  public void setMdm_createdby(String mdm_createdby) {
	    this.mdm_createdby = mdm_createdby;
	  }

	  public String getMdm_createdon() {
	    return mdm_createdon;
	  }

	  public void setMdm_createdon(String mdm_createdon) {
	    this.mdm_createdon = mdm_createdon;
	  }

	  public String getMdm_modifiedby_type() {
	    return mdm_modifiedby_type;
	  }

	  public void setMdm_modifiedby_type(String mdm_modifiedby_type) {
	    this.mdm_modifiedby_type = mdm_modifiedby_type;
	  }

	  public String getMdm_modifiedby() {
	    return mdm_modifiedby;
	  }

	  public void setMdm_modifiedby(String mdm_modifiedby) {
	    this.mdm_modifiedby = mdm_modifiedby;
	  }

	  public String getMdm_modifiedon() {
	    return mdm_modifiedon;
	  }

	  public void setMdm_modifiedon(String mdm_modifiedon) {
	    this.mdm_modifiedon = mdm_modifiedon;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public String getCode() {
	    return code;
	  }

	  public void setCode(String code) {
	    this.code = code;
	  }

	  public String getDescription() {
	    return description;
	  }

	  public void setDescription(String description) {
	    this.description = description;
	  }

	  public String getBus_color() {
	    return bus_color;
	  }

	  public void setBus_color(String bus_color) {
	    this.bus_color = bus_color;
	  }

	  public String getBus_lisencenum() {
	    return bus_lisencenum;
	  }

	  public void setBus_lisencenum(String bus_lisencenum) {
	    this.bus_lisencenum = bus_lisencenum;
	  }

	  public String getBus_specialllisence() {
	    return bus_specialllisence;
	  }

	  public void setBus_specialllisence(String bus_specialllisence) {
	    this.bus_specialllisence = bus_specialllisence;
	  }

	  public String getBus_depart() {
	    return bus_depart;
	  }

	  public void setBus_depart(String bus_depart) {
	    this.bus_depart = bus_depart;
	  }

	  public String getBus_departid() {
	    return bus_departid;
	  }

	  public void setBus_departid(String bus_departid) {
	    this.bus_departid = bus_departid;
	  }

	  public String getBus_brand() {
	    return bus_brand;
	  }

	  public void setBus_brand(String bus_brand) {
	    this.bus_brand = bus_brand;
	  }

	  public String getBus_model() {
	    return bus_model;
	  }

	  public void setBus_model(String bus_model) {
	    this.bus_model = bus_model;
	  }

	  public String getBus_add() {
	    return bus_add;
	  }

	  public void setBus_add(String bus_add) {
	    this.bus_add = bus_add;
	  }

	  public String getBus_type() {
	    return bus_type;
	  }

	  public void setBus_type(String bus_type) {
	    this.bus_type = bus_type;
	  }

	  public String getBus_oprationtype() {
	    return bus_oprationtype;
	  }

	  public void setBus_oprationtype(String bus_oprationtype) {
	    this.bus_oprationtype = bus_oprationtype;
	  }

	  public String getBus_bodycolor() {
	    return bus_bodycolor;
	  }

	  public void setBus_bodycolor(String bus_bodycolor) {
	    this.bus_bodycolor = bus_bodycolor;
	  }

	  public String getBus_move() {
	    return bus_move;
	  }

	  public void setBus_move(String bus_move) {
	    this.bus_move = bus_move;
	  }

	  public String getBus_swrl() {
	    return bus_swrl;
	  }

	  public void setBus_swrl(String bus_swrl) {
	    this.bus_swrl = bus_swrl;
	  }

	  public String getBus_boxbrand() {
	    return bus_boxbrand;
	  }

	  public void setBus_boxbrand(String bus_boxbrand) {
	    this.bus_boxbrand = bus_boxbrand;
	  }

	  public String getBus_boxmodel() {
	    return bus_boxmodel;
	  }

	  public void setBus_boxmodel(String bus_boxmodel) {
	    this.bus_boxmodel = bus_boxmodel;
	  }

	  public String getBus_engin() {
	    return bus_engin;
	  }

	  public void setBus_engin(String bus_engin) {
	    this.bus_engin = bus_engin;
	  }

	  public String getBus_framid() {
	    return bus_framid;
	  }

	  public void setBus_framid(String bus_framid) {
	    this.bus_framid = bus_framid;
	  }

	  public String getBus_vin() {
	    return bus_vin;
	  }

	  public void setBus_vin(String bus_vin) {
	    this.bus_vin = bus_vin;
	  }

	  public String getBus_oiltype() {
	    return bus_oiltype;
	  }

	  public void setBus_oiltype(String bus_oiltype) {
	    this.bus_oiltype = bus_oiltype;
	  }

	  public String getBus_lngsystem() {
	    return bus_lngsystem;
	  }

	  public void setBus_lngsystem(String bus_lngsystem) {
	    this.bus_lngsystem = bus_lngsystem;
	  }

	  public String getBus_batterytype() {
	    return bus_batterytype;
	  }

	  public void setBus_batterytype(String bus_batterytype) {
	    this.bus_batterytype = bus_batterytype;
	  }

	  public String getBus_batterybrand() {
	    return bus_batterybrand;
	  }

	  public void setBus_batterybrand(String bus_batterybrand) {
	    this.bus_batterybrand = bus_batterybrand;
	  }

	  public String getBus_motocbrand() {
	    return bus_motocbrand;
	  }

	  public void setBus_motocbrand(String bus_motocbrand) {
	    this.bus_motocbrand = bus_motocbrand;
	  }

	  public String getBus_airbrand() {
	    return bus_airbrand;
	  }

	  public void setBus_airbrand(String bus_airbrand) {
	    this.bus_airbrand = bus_airbrand;
	  }

	  public String getBus_frontbrand() {
	    return bus_frontbrand;
	  }

	  public void setBus_frontbrand(String bus_frontbrand) {
	    this.bus_frontbrand = bus_frontbrand;
	  }

	  public String getBus_frontton() {
	    return bus_frontton;
	  }

	  public void setBus_frontton(String bus_frontton) {
	    this.bus_frontton = bus_frontton;
	  }

	  public String getBus_rearbrand() {
	    return bus_rearbrand;
	  }

	  public void setBus_rearbrand(String bus_rearbrand) {
	    this.bus_rearbrand = bus_rearbrand;
	  }

	  public String getBus_rearton() {
	    return bus_rearton;
	  }

	  public void setBus_rearton(String bus_rearton) {
	    this.bus_rearton = bus_rearton;
	  }

	  public String getBus_jpg() {
	    return bus_jpg;
	  }

	  public void setBus_jpg(String bus_jpg) {
	    this.bus_jpg = bus_jpg;
	  }

	  public String getBus_axlelength() {
	    return bus_axlelength;
	  }

	  public void setBus_axlelength(String bus_axlelength) {
	    this.bus_axlelength = bus_axlelength;
	  }

	  public String getBus_axle() {
	    return bus_axle;
	  }

	  public void setBus_axle(String bus_axle) {
	    this.bus_axle = bus_axle;
	  }

	  public String getBus_length() {
	    return bus_length;
	  }

	  public void setBus_length(String bus_length) {
	    this.bus_length = bus_length;
	  }

	  public String getBus_height() {
	    return bus_height;
	  }

	  public void setBus_height(String bus_height) {
	    this.bus_height = bus_height;
	  }

	  public String getBus_weight() {
	    return bus_weight;
	  }

	  public void setBus_weight(String bus_weight) {
	    this.bus_weight = bus_weight;
	  }

	  public String getBus_code() {
	    return bus_code;
	  }

	  public void setBus_code(String bus_code) {
	    this.bus_code = bus_code;
	  }

	  public String getBus_transportid() {
	    return bus_transportid;
	  }

	  public void setBus_transportid(String bus_transportid) {
	    this.bus_transportid = bus_transportid;
	  }

	  public String getBus_transportorgan() {
	    return bus_transportorgan;
	  }

	  public void setBus_transportorgan(String bus_transportorgan) {
	    this.bus_transportorgan = bus_transportorgan;
	  }

	  public String getBus_peroidstart() {
	    return bus_peroidstart;
	  }

	  public void setBus_peroidstart(String bus_peroidstart) {
	    this.bus_peroidstart = bus_peroidstart;
	  }

	  public String getBus_peroidend() {
	    return bus_peroidend;
	  }

	  public void setBus_peroidend(String bus_peroidend) {
	    this.bus_peroidend = bus_peroidend;
	  }

	  public String getBus_range() {
	    return bus_range;
	  }

	  public void setBus_range(String bus_range) {
	    this.bus_range = bus_range;
	  }

	  public String getBus_maint() {
	    return bus_maint;
	  }

	  public void setBus_maint(String bus_maint) {
	    this.bus_maint = bus_maint;
	  }

	  public String getBus_oprationstatus() {
	    return bus_oprationstatus;
	  }

	  public void setBus_oprationstatus(String bus_oprationstatus) {
	    this.bus_oprationstatus = bus_oprationstatus;
	  }

	  public String getBus_techlevel() {
	    return bus_techlevel;
	  }

	  public void setBus_techlevel(String bus_techlevel) {
	    this.bus_techlevel = bus_techlevel;
	  }

	  public String getBus_checkstatus() {
	    return bus_checkstatus;
	  }

	  public void setBus_checkstatus(String bus_checkstatus) {
	    this.bus_checkstatus = bus_checkstatus;
	  }

	  public String getBus_record() {
	    return bus_record;
	  }

	  public void setBus_record(String bus_record) {
	    this.bus_record = bus_record;
	  }

	  public String getBus_gps() {
	    return bus_gps;
	  }

	  public void setBus_gps(String bus_gps) {
	    this.bus_gps = bus_gps;
	  }

	  public String getBus_recordstatus() {
	    return bus_recordstatus;
	  }

	  public void setBus_recordstatus(String bus_recordstatus) {
	    this.bus_recordstatus = bus_recordstatus;
	  }

	  public String getBus_gpsstatus() {
	    return bus_gpsstatus;
	  }

	  public void setBus_gpsstatus(String bus_gpsstatus) {
	    this.bus_gpsstatus = bus_gpsstatus;
	  }

	  public String getBus_permitid() {
	    return bus_permitid;
	  }

	  public void setBus_permitid(String bus_permitid) {
	    this.bus_permitid = bus_permitid;
	  }

	  public String getBus_level() {
	    return bus_level;
	  }

	  public void setBus_level(String bus_level) {
	    this.bus_level = bus_level;
	  }

	  public String getBus_seat() {
	    return bus_seat;
	  }

	  public void setBus_seat(String bus_seat) {
	    this.bus_seat = bus_seat;
	  }

	  public String getBus_loadcustomer() {
	    return bus_loadcustomer;
	  }

	  public void setBus_loadcustomer(String bus_loadcustomer) {
	    this.bus_loadcustomer = bus_loadcustomer;
	  }

	  public String getBus_loadweight() {
	    return bus_loadweight;
	  }

	  public void setBus_loadweight(String bus_loadweight) {
	    this.bus_loadweight = bus_loadweight;
	  }

	  public String getBus_ton() {
	    return bus_ton;
	  }

	  public void setBus_ton(String bus_ton) {
	    this.bus_ton = bus_ton;
	  }

	  public String getBus_teu() {
	    return bus_teu;
	  }

	  public void setBus_teu(String bus_teu) {
	    this.bus_teu = bus_teu;
	  }

	  public String getBus_volume() {
	    return bus_volume;
	  }

	  public void setBus_volume(String bus_volume) {
	    this.bus_volume = bus_volume;
	  }

	  public String getBus_commercialtype() {
	    return bus_commercialtype;
	  }

	  public void setBus_commercialtype(String bus_commercialtype) {
	    this.bus_commercialtype = bus_commercialtype;
	  }

	  public String getBus_grossprice() {
	    return bus_grossprice;
	  }

	  public void setBus_grossprice(String bus_grossprice) {
	    this.bus_grossprice = bus_grossprice;
	  }

	  public String getBus_purchase() {
	    return bus_purchase;
	  }

	  public void setBus_purchase(String bus_purchase) {
	    this.bus_purchase = bus_purchase;
	  }

	  public String getBus_initial() {
	    return bus_initial;
	  }

	  public void setBus_initial(String bus_initial) {
	    this.bus_initial = bus_initial;
	  }

	  public String getBus_seattype() {
	    return bus_seattype;
	  }

	  public void setBus_seattype(String bus_seattype) {
	    this.bus_seattype = bus_seattype;
	  }

	  public String getBus_registnum() {
	    return bus_registnum;
	  }

	  public void setBus_registnum(String bus_registnum) {
	    this.bus_registnum = bus_registnum;
	  }

	  public String getBus_registdate() {
	    return bus_registdate;
	  }

	  public void setBus_registdate(String bus_registdate) {
	    this.bus_registdate = bus_registdate;
	  }

	  public String getBus_certificationdate() {
	    return bus_certificationdate;
	  }

	  public void setBus_certificationdate(String bus_certificationdate) {
	    this.bus_certificationdate = bus_certificationdate;
	  }

	  public String getBus_checkperiod() {
	    return bus_checkperiod;
	  }

	  public void setBus_checkperiod(String bus_checkperiod) {
	    this.bus_checkperiod = bus_checkperiod;
	  }

	  public String getBus_certificationorgan() {
	    return bus_certificationorgan;
	  }

	  public void setBus_certificationorgan(String bus_certificationorgan) {
	    this.bus_certificationorgan = bus_certificationorgan;
	  }

	  public String getBus_purchasedate() {
	    return bus_purchasedate;
	  }

	  public void setBus_purchasedate(String bus_purchasedate) {
	    this.bus_purchasedate = bus_purchasedate;
	  }

	  public String getBus_purchasid() {
	    return bus_purchasid;
	  }

	  public void setBus_purchasid(String bus_purchasid) {
	    this.bus_purchasid = bus_purchasid;
	  }

	  public String getBus_releasedate() {
	    return bus_releasedate;
	  }

	  public void setBus_releasedate(String bus_releasedate) {
	    this.bus_releasedate = bus_releasedate;
	  }

	  public String getBus_scrapdate() {
	    return bus_scrapdate;
	  }

	  public void setBus_scrapdate(String bus_scrapdate) {
	    this.bus_scrapdate = bus_scrapdate;
	  }

	  public String getBus_roughweight() {
	    return bus_roughweight;
	  }

	  public void setBus_roughweight(String bus_roughweight) {
	    this.bus_roughweight = bus_roughweight;
	  }

	  public String getBus_grossweight() {
	    return bus_grossweight;
	  }

	  public void setBus_grossweight(String bus_grossweight) {
	    this.bus_grossweight = bus_grossweight;
	  }

	  public String getBus_traction() {
	    return bus_traction;
	  }

	  public void setBus_traction(String bus_traction) {
	    this.bus_traction = bus_traction;
	  }

	  public String getBus_spring() {
	    return bus_spring;
	  }

	  public void setBus_spring(String bus_spring) {
	    this.bus_spring = bus_spring;
	  }

	  public String getBus_tyrecount() {
	    return bus_tyrecount;
	  }

	  public void setBus_tyrecount(String bus_tyrecount) {
	    this.bus_tyrecount = bus_tyrecount;
	  }

	  public String getBus_firsttime() {
	    return bus_firsttime;
	  }

	  public void setBus_firsttime(String bus_firsttime) {
	    this.bus_firsttime = bus_firsttime;
	  }

	  public String getBus_firstdistance() {
	    return bus_firstdistance;
	  }

	  public void setBus_firstdistance(String bus_firstdistance) {
	    this.bus_firstdistance = bus_firstdistance;
	  }

	  public String getBus_secondtime() {
	    return bus_secondtime;
	  }

	  public void setBus_secondtime(String bus_secondtime) {
	    this.bus_secondtime = bus_secondtime;
	  }

	  public String getBus_seconddistance() {
	    return bus_seconddistance;
	  }

	  public void setBus_seconddistance(String bus_seconddistance) {
	    this.bus_seconddistance = bus_seconddistance;
	  }

	  public String getBus_change() {
	    return bus_change;
	  }

	  public void setBus_change(String bus_change) {
	    this.bus_change = bus_change;
	  }

	  public String getBus_discharge_standard() {
	    return bus_discharge_standard;
	  }

	  public void setBus_discharge_standard(String bus_discharge_standard) {
	    this.bus_discharge_standard = bus_discharge_standard;
	  }

	  public String getBus_retarder_brand() {
	    return bus_retarder_brand;
	  }

	  public void setBus_retarder_brand(String bus_retarder_brand) {
	    this.bus_retarder_brand = bus_retarder_brand;
	  }

	  public String getBus_can_brand() {
	    return bus_can_brand;
	  }

	  public void setBus_can_brand(String bus_can_brand) {
	    this.bus_can_brand = bus_can_brand;
	  }

	  public String getBus_chassis() {
	    return bus_chassis;
	  }

	  public void setBus_chassis(String bus_chassis) {
	    this.bus_chassis = bus_chassis;
	  }

	  public String getBus_engin_brand() {
	    return bus_engin_brand;
	  }

	  public void setBus_engin_brand(String bus_engin_brand) {
	    this.bus_engin_brand = bus_engin_brand;
	  }

	  public String getBus_power() {
	    return bus_power;
	  }

	  public void setBus_power(String bus_power) {
	    this.bus_power = bus_power;
	  }

	  public String getBus_bev_airbrand() {
	    return bus_bev_airbrand;
	  }

	  public void setBus_bev_airbrand(String bus_bev_airbrand) {
	    this.bus_bev_airbrand = bus_bev_airbrand;
	  }

	  public String getBus_bev_steerbrand() {
	    return bus_bev_steerbrand;
	  }

	  public void setBus_bev_steerbrand(String bus_bev_steerbrand) {
	    this.bus_bev_steerbrand = bus_bev_steerbrand;
	  }

	  public String getBus_bev_acrbrand() {
	    return bus_bev_acrbrand;
	  }

	  public void setBus_bev_acrbrand(String bus_bev_acrbrand) {
	    this.bus_bev_acrbrand = bus_bev_acrbrand;
	  }

	  public String getBus_ecu_brand() {
	    return bus_ecu_brand;
	  }

	  public void setBus_ecu_brand(String bus_ecu_brand) {
	    this.bus_ecu_brand = bus_ecu_brand;
	  }

	  public String getMakeuser() {
	    return makeuser;
	  }

	  public void setMakeuser(String makeuser) {
	    this.makeuser = makeuser;
	  }

	  public String getFrmtitile() {
	    return frmtitile;
	  }

	  public void setFrmtitile(String frmtitile) {
	    this.frmtitile = frmtitile;
	  }

	  public String getMakedate() {
	    return makedate;
	  }

	  public void setMakedate(String makedate) {
	    this.makedate = makedate;
	  }

	  public String getMakedept() {
	    return makedept;
	  }

	  public void setMakedept(String makedept) {
	    this.makedept = makedept;
	  }

	  public String getMakenum() {
	    return makenum;
	  }

	  public void setMakenum(String makenum) {
	    this.makenum = makenum;
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

	  public String getFrmstate() {
	    return frmstate;
	  }

	  public void setFrmstate(String frmstate) {
	    this.frmstate = frmstate;
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

	public String getResourcesys() {
	    return resourcesys;
	  }

	  public void setResourcesys(String resourcesys) {
	    this.resourcesys = resourcesys;
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
	
	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}
		

		public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

		@Override
	    public String getMetaDefinedName() {
		        return "Bus";
		    }

		    @Override
		    public String getNamespace() {
		        return "iuap-mdm-yueyun";
		    }

}
