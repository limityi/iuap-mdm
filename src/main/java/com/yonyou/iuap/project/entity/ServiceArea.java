package com.yonyou.iuap.project.entity;


import com.yonyou.iuap.persistence.jdbc.framework.annotation.*;
import com.yonyou.iuap.persistence.vo.BaseEntity;

/**
 * @Description 服务区
 *
 * @author binbin
 * @date 2018/12/18 14:41
 */
@Entity(namespace = "iuap-mdm-yueyun", name = "ServiceArea")
@Table(name = "mdm_service_area")
public class ServiceArea extends BaseEntity {

    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "")
    @Column(name = "id")
    private String id;

    private String mdm_pk;
    private String mdm_parentcode;
    private String mdm_code;
    private String mdm_status;
    private String mdm_duplicate;
    private String mdm_seal;
    private String mdm_createdby_type;
    private String mdm_createdby;
    private String mdm_createdon;
    private String mdm_modifiedby_type;
    private String mdm_modifiedby;
    private String mdm_modifiedon;

    private String name;
    private String code;
    private String description;
    private String highspeedway;
    private String sectionid;
    private String administrativeregion;
    private String stakemark;
    private String yytype;
    private String shopmanager;
    private String phone;
    private String authorizedstaffqty;
    private String actualstaffqty;
    private String businesstype;
    private String yyowner;
    private String startbusinessdate;
    private String opendate;
    private String enddate;
    private String completerepairdate;
    private String storeys;
    private String gasflag;
    private String parkflag;
    private String cvsflag;
    private String cateringflag;
    private String repairingflag;
    private Double floorarea;
    private Double structurearea;
    private Double businessarea;
    private Double notbusinessarea;
    private Double shoparea;
    private double leasedshoparea;
    private double unleasedshoparea;
    private double parkarea;
    private double greenarea;
    private double innertunnelflag;
    private String innertunneltype;
    private String innertunnelvehicleflag;
    private String innertunnelexp;
    private String spacetotalinpark;
    private String largespacetotalinpark;
    private String smallspacetotalinpark;
    private String cvpflag;
    private String cvpnumber;
    private String makedept;
    private String makedate;
    private String frmtitile;
    private String makeuser;
    private String audituser;
    private String auditdate;
    private String makenum;
    private String frmstate;
    private String ts;
    private String dr;
    private String resourcesys;
    private String tag;
    private String similarity;

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

    public String getHighspeedway() {
        return highspeedway;
    }

    public void setHighspeedway(String highspeedway) {
        this.highspeedway = highspeedway;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getAdministrativeregion() {
        return administrativeregion;
    }

    public void setAdministrativeregion(String administrativeregion) {
        this.administrativeregion = administrativeregion;
    }

    public String getStakemark() {
        return stakemark;
    }

    public void setStakemark(String stakemark) {
        this.stakemark = stakemark;
    }

    public String getYytype() {
        return yytype;
    }

    public void setYytype(String yytype) {
        this.yytype = yytype;
    }

    public String getShopmanager() {
        return shopmanager;
    }

    public void setShopmanager(String shopmanager) {
        this.shopmanager = shopmanager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthorizedstaffqty() {
        return authorizedstaffqty;
    }

    public void setAuthorizedstaffqty(String authorizedstaffqty) {
        this.authorizedstaffqty = authorizedstaffqty;
    }

    public String getActualstaffqty() {
        return actualstaffqty;
    }

    public void setActualstaffqty(String actualstaffqty) {
        this.actualstaffqty = actualstaffqty;
    }

    public String getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    public String getYyowner() {
        return yyowner;
    }

    public void setYyowner(String yyowner) {
        this.yyowner = yyowner;
    }

    public String getStartbusinessdate() {
        return startbusinessdate;
    }

    public void setStartbusinessdate(String startbusinessdate) {
        this.startbusinessdate = startbusinessdate;
    }

    public String getOpendate() {
        return opendate;
    }

    public void setOpendate(String opendate) {
        this.opendate = opendate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getCompleterepairdate() {
        return completerepairdate;
    }

    public void setCompleterepairdate(String completerepairdate) {
        this.completerepairdate = completerepairdate;
    }

    public String getStoreys() {
        return storeys;
    }

    public void setStoreys(String storeys) {
        this.storeys = storeys;
    }

    public String getGasflag() {
        return gasflag;
    }

    public void setGasflag(String gasflag) {
        this.gasflag = gasflag;
    }

    public String getParkflag() {
        return parkflag;
    }

    public void setParkflag(String parkflag) {
        this.parkflag = parkflag;
    }

    public String getCvsflag() {
        return cvsflag;
    }

    public void setCvsflag(String cvsflag) {
        this.cvsflag = cvsflag;
    }

    public String getCateringflag() {
        return cateringflag;
    }

    public void setCateringflag(String cateringflag) {
        this.cateringflag = cateringflag;
    }

    public String getRepairingflag() {
        return repairingflag;
    }

    public void setRepairingflag(String repairingflag) {
        this.repairingflag = repairingflag;
    }

    public Double getFloorarea() {
        return floorarea;
    }

    public void setFloorarea(Double floorarea) {
        this.floorarea = floorarea;
    }

    public Double getStructurearea() {
        return structurearea;
    }

    public void setStructurearea(Double structurearea) {
        this.structurearea = structurearea;
    }

    public Double getBusinessarea() {
        return businessarea;
    }

    public void setBusinessarea(Double businessarea) {
        this.businessarea = businessarea;
    }

    public Double getNotbusinessarea() {
        return notbusinessarea;
    }

    public void setNotbusinessarea(Double notbusinessarea) {
        this.notbusinessarea = notbusinessarea;
    }

    public Double getShoparea() {
        return shoparea;
    }

    public void setShoparea(Double shoparea) {
        this.shoparea = shoparea;
    }

    public double getLeasedshoparea() {
        return leasedshoparea;
    }

    public void setLeasedshoparea(double leasedshoparea) {
        this.leasedshoparea = leasedshoparea;
    }

    public double getUnleasedshoparea() {
        return unleasedshoparea;
    }

    public void setUnleasedshoparea(double unleasedshoparea) {
        this.unleasedshoparea = unleasedshoparea;
    }

    public double getParkarea() {
        return parkarea;
    }

    public void setParkarea(double parkarea) {
        this.parkarea = parkarea;
    }

    public double getGreenarea() {
        return greenarea;
    }

    public void setGreenarea(double greenarea) {
        this.greenarea = greenarea;
    }

    public double getInnertunnelflag() {
        return innertunnelflag;
    }

    public void setInnertunnelflag(double innertunnelflag) {
        this.innertunnelflag = innertunnelflag;
    }

    public String getInnertunneltype() {
        return innertunneltype;
    }

    public void setInnertunneltype(String innertunneltype) {
        this.innertunneltype = innertunneltype;
    }

    public String getInnertunnelvehicleflag() {
        return innertunnelvehicleflag;
    }

    public void setInnertunnelvehicleflag(String innertunnelvehicleflag) {
        this.innertunnelvehicleflag = innertunnelvehicleflag;
    }

    public String getInnertunnelexp() {
        return innertunnelexp;
    }

    public void setInnertunnelexp(String innertunnelexp) {
        this.innertunnelexp = innertunnelexp;
    }

    public String getSpacetotalinpark() {
        return spacetotalinpark;
    }

    public void setSpacetotalinpark(String spacetotalinpark) {
        this.spacetotalinpark = spacetotalinpark;
    }

    public String getLargespacetotalinpark() {
        return largespacetotalinpark;
    }

    public void setLargespacetotalinpark(String largespacetotalinpark) {
        this.largespacetotalinpark = largespacetotalinpark;
    }

    public String getSmallspacetotalinpark() {
        return smallspacetotalinpark;
    }

    public void setSmallspacetotalinpark(String smallspacetotalinpark) {
        this.smallspacetotalinpark = smallspacetotalinpark;
    }

    public String getCvpflag() {
        return cvpflag;
    }

    public void setCvpflag(String cvpflag) {
        this.cvpflag = cvpflag;
    }

    public String getCvpnumber() {
        return cvpnumber;
    }

    public void setCvpnumber(String cvpnumber) {
        this.cvpnumber = cvpnumber;
    }

    public String getMakedept() {
        return makedept;
    }

    public void setMakedept(String makedept) {
        this.makedept = makedept;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getFrmtitile() {
        return frmtitile;
    }

    public void setFrmtitile(String frmtitile) {
        this.frmtitile = frmtitile;
    }

    public String getMakeuser() {
        return makeuser;
    }

    public void setMakeuser(String makeuser) {
        this.makeuser = makeuser;
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

    public String getMakenum() {
        return makenum;
    }

    public void setMakenum(String makenum) {
        this.makenum = makenum;
    }

    public String getFrmstate() {
        return frmstate;
    }

    public void setFrmstate(String frmstate) {
        this.frmstate = frmstate;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getDr() {
        return dr;
    }

    public void setDr(String dr) {
        this.dr = dr;
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

    @Override
    public String getMetaDefinedName() {
        return "ServiceArea";
    }

    @Override
    public String getNamespace() {
        return "iuap-mdm-yueyun";
    }

}
