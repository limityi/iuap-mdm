package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.ServiceArea;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface ServiceAreaRepository {

    @Select("select count(*) from UAP65.MDM_SERVICE_AREA where dr=0")
    int countAll();

    List<ServiceArea> selectOnlyValidateData();

    List<ServiceArea> selectRequiredData(List<String> list);

    List<ServiceArea> selectAllData(Map<String, Object> searchParams);
}
