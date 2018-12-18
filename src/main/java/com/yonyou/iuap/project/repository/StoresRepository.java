package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Stores;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface StoresRepository {

    @Select("select count(*) from UAP65.MDM_STORES where dr=0")
    int countAll();

    List<Stores> selectOnlyValidateData();

    List<Stores> selectRequiredData(List<String> list);

    List<Stores> selectAllData(Map<String, Object> searchParams);
}
