package com.yonyou.iuap.project.entity;


import com.yonyou.iuap.persistence.jdbc.framework.annotation.*;
import com.yonyou.iuap.persistence.vo.BaseEntity;

/**
 * @Description 客商
 *
 * @author binbin
 * @date 2018/12/18 14:43
 */
@Entity(namespace = "iuap-mdm-yueyun", name = "Merchants")
@Table(name = "MDM_MERCHANTS")
public class Merchants extends BaseEntity {

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
    private String cust_supplier_shortname;
    private String custsupplier_category;
    private String company_type;
    private String custsuptype;
    private String taxpayerid;
    private String industry;
    private String legal_person;
    private String currency;
    private String registered;
    private String website;
    private String telephone;
    private String fax;
    private String e_mail;
    private String country;
    private String province;
    private String city;
    private String county_area;
    private String postalcode;
    private String address;
    private String business_scope;
    private String qualification_information;
    private String nc_code;
    private String ht_code;
    private String wxxt_code;
    private String audituser;
    private String frmstate;
    private String frmtitile;
    private String makedept;
    private String makeuser;
    private String makenum;
    private String auditdate;
    private String makedate;
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

    public String getCust_supplier_shortname() {
        return cust_supplier_shortname;
    }

    public void setCust_supplier_shortname(String cust_supplier_shortname) {
        this.cust_supplier_shortname = cust_supplier_shortname;
    }

    public String getCustsupplier_category() {
        return custsupplier_category;
    }

    public void setCustsupplier_category(String custsupplier_category) {
        this.custsupplier_category = custsupplier_category;
    }

    public String getCompany_type() {
        return company_type;
    }

    public void setCompany_type(String company_type) {
        this.company_type = company_type;
    }

    public String getCustsuptype() {
        return custsuptype;
    }

    public void setCustsuptype(String custsuptype) {
        this.custsuptype = custsuptype;
    }

    public String getTaxpayerid() {
        return taxpayerid;
    }

    public void setTaxpayerid(String taxpayerid) {
        this.taxpayerid = taxpayerid;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLegal_person() {
        return legal_person;
    }

    public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty_area() {
        return county_area;
    }

    public void setCounty_area(String county_area) {
        this.county_area = county_area;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiness_scope() {
        return business_scope;
    }

    public void setBusiness_scope(String business_scope) {
        this.business_scope = business_scope;
    }

    public String getQualification_information() {
        return qualification_information;
    }

    public void setQualification_information(String qualification_information) {
        this.qualification_information = qualification_information;
    }

    public String getNc_code() {
        return nc_code;
    }

    public void setNc_code(String nc_code) {
        this.nc_code = nc_code;
    }

    public String getHt_code() {
        return ht_code;
    }

    public void setHt_code(String ht_code) {
        this.ht_code = ht_code;
    }

    public String getWxxt_code() {
        return wxxt_code;
    }

    public void setWxxt_code(String wxxt_code) {
        this.wxxt_code = wxxt_code;
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

    public String getMakedept() {
        return makedept;
    }

    public void setMakedept(String makedept) {
        this.makedept = makedept;
    }

    public String getMakeuser() {
        return makeuser;
    }

    public void setMakeuser(String makeuser) {
        this.makeuser = makeuser;
    }

    public String getMakenum() {
        return makenum;
    }

    public void setMakenum(String makenum) {
        this.makenum = makenum;
    }

    public String getAuditdate() {
        return auditdate;
    }

    public void setAuditdate(String auditdate) {
        this.auditdate = auditdate;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
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
        return "Merchants";
    }

    @Override
    public String getNamespace() {
        return "iuap-mdm-yueyun";
    }
}
