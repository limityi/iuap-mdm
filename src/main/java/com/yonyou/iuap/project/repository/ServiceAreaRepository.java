package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.ServiceArea;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface ServiceAreaRepository {

    @Select("select count(*) from UAP65.MDM_SERVICE_AREA where dr=0 and similar='Y'")
    int countAll();

    List<ServiceArea> selectOnlyValidateData();

    List<ServiceArea> selectRequiredData(Map<String, Object> searchParams);

    List<ServiceArea> selectAllData();
    
    @Update("update UAP65.MDM_SERVICE_AREA set similar='N' where mdm_code=#{mdm_code}")
    int removeData(@Param("mdm_code") String mdm_code);
}
