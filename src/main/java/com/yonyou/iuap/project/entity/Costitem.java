package com.yonyou.iuap.project.entity;


import com.yonyou.iuap.persistence.jdbc.framework.annotation.*;
import com.yonyou.iuap.persistence.vo.BaseEntity;

/**
 * @Description 费用项目
 *
 * @author binbin
 * @date 2018/12/18 14:41
 */
@Entity(namespace = "iuap-mdm-yueyun", name = "Costitem")
@Table(name = "mdm_costitem")
public class Costitem extends BaseEntity {

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
    private String dataoriginflag;
    private String pk_group;
    private String pk_inoutbusiclass;
    private String pk_org;
    private String pk_parent;
    private String auditdate;
    private String audituser;
    private String frmstate;
    private String frmtitile;
    private String makedate;
    private String makedept;
    private String makenum;
    private String makeuser;
    private String ts;
    private String dr;
    private String zhky_code;
    private String nyt_code;
    private String rc_code;
    private String wx_code;
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

    public String getDataoriginflag() {
        return dataoriginflag;
    }

    public void setDataoriginflag(String dataoriginflag) {
        this.dataoriginflag = dataoriginflag;
    }

    public String getPk_group() {
        return pk_group;
    }

    public void setPk_group(String pk_group) {
        this.pk_group = pk_group;
    }

    public String getPk_inoutbusiclass() {
        return pk_inoutbusiclass;
    }

    public void setPk_inoutbusiclass(String pk_inoutbusiclass) {
        this.pk_inoutbusiclass = pk_inoutbusiclass;
    }

    public String getPk_org() {
        return pk_org;
    }

    public void setPk_org(String pk_org) {
        this.pk_org = pk_org;
    }

    public String getPk_parent() {
        return pk_parent;
    }

    public void setPk_parent(String pk_parent) {
        this.pk_parent = pk_parent;
    }

    public String getAuditdate() {
        return auditdate;
    }

    public void setAuditdate(String auditdate) {
        this.auditdate = auditdate;
    }

    public String getAudituser() {
        return audituser;
    }

    public void setAudituser(String audituser) {
        this.audituser = audituser;
    }

    public String getFrmstate() {
        return frmstate;
    }

    public void setFrmstate(String frmstate) {
        this.frmstate = frmstate;
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

    public String getMakeuser() {
        return makeuser;
    }

    public void setMakeuser(String makeuser) {
        this.makeuser = makeuser;
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

    public String getZhky_code() {
        return zhky_code;
    }

    public void setZhky_code(String zhky_code) {
        this.zhky_code = zhky_code;
    }

    public String getNyt_code() {
        return nyt_code;
    }

    public void setNyt_code(String nyt_code) {
        this.nyt_code = nyt_code;
    }

    public String getRc_code() {
        return rc_code;
    }

    public void setRc_code(String rc_code) {
        this.rc_code = rc_code;
    }

    public String getWx_code() {
        return wx_code;
    }

    public void setWx_code(String wx_code) {
        this.wx_code = wx_code;
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
        return "Costitem";
    }

    @Override
    public String getNamespace() {
        return "iuap-mdm-yueyun";
    }
}
