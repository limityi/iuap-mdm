package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Org;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface OrgRepository {

    @Select("select count(*) from UAP65.MDM_ORG where dr=0")
    int countAll();

    Org getOrgByCode(@Param("mdmcode") String mdmcode);

    List<Org> selectOnlyValidateData();

    List<Org> selectRequiredData(List<String> list);

    List<Org> selectAllData(Map<String, Object> searchParams);
}
