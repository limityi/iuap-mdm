package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Org;
import com.yonyou.iuap.project.entity.SjzyOrg;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface OrgRepository {

    @Select("select count(*) from UAP65.MDM_ORG where dr=0")
    int countAll();

    Org getOrgByMdmCode(@Param("mdmcode") String mdmcode);

    SjzyOrg getSjzyOrgByCode(@Param("code") String code);

    List<Org> selectOnlyValidateData();

    List<Org> selectRequiredData(Map<String, Object> searchParams);

    List<Org> selectAllData(Map<String, Object> searchParams);
}
