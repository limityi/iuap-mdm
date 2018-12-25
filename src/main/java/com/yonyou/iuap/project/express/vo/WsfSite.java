package com.yonyou.iuap.project.express.vo;

import com.google.gson.annotations.Expose;

/**
 * 网上飞网点详细信息
 * Created by XiongYi on 2018/12/24.
 *
 */
public class WsfSite {

    private String id;

    private String code;

    private String name;

    private String phone;

    private String charge_person;

    private String province;

    private String city;

    private String county;

    private String address;

    private String sjt_code;

    private String sjt_name;

    @Expose
    private String business_org;

    private String resourcesys = "小件快运系统";

    private String enableLogo;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCharge_person() {
        return charge_person;
    }

    public void setCharge_person(String charge_person) {
        this.charge_person = charge_person;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSjt_code() {
        return sjt_code;
    }

    public void setSjt_code(String sjt_code) {
        this.sjt_code = sjt_code;
    }

    public String getSjt_name() {
        return sjt_name;
    }

    public void setSjt_name(String sjt_name) {
        this.sjt_name = sjt_name;
    }

    public String getBusiness_org() {
        return business_org;
    }

    public void setBusiness_org(String business_org) {
        this.business_org = business_org;
    }

    public String getResourcesys() {
        return resourcesys;
    }

    public void setResourcesys(String resourcesys) {
        this.resourcesys = resourcesys;
    }

    public String getEnableLogo() {
        return enableLogo;
    }

    public void setEnableLogo(String enableLogo) {
        this.enableLogo = enableLogo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
