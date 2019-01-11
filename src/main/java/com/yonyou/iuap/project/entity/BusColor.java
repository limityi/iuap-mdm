package com.yonyou.iuap.project.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.*;
import com.yonyou.iuap.persistence.vo.BaseEntity;

@Entity(namespace = "iuap-mdm-yueyun", name = "BusColor")
@Table(name = "bus_bodycolor")
public class BusColor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = Stragegy.UUID, moudle = "")
    @Column(name = "id")
    private String id;

    @Column(name = "mdm_pk")
    private String mdm_pk;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

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


    @Override
    public String getMetaDefinedName() {
        return "BusColor";
    }

    @Override
    public String getNamespace() {
        return "iuap-mdm-yueyun";
    }

}
