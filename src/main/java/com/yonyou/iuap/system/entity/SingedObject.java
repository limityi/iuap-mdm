package com.yonyou.iuap.system.entity;

/**
 * 合同客商主表实体类
 */
public class SingedObject {

    private String signObjectId;

    private String name;

    private int type;

    private int status;

    private String code;

    public String getSignObjectId() {
        return signObjectId;
    }

    public void setSignObjectId(String signObjectId) {
        this.signObjectId = signObjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
