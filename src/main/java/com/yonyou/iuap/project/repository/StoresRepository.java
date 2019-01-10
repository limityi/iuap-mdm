package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Stores;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface StoresRepository {

    @Select("select count(*) from UAP65.MDM_STORES where dr=0 and similar='Y'")
    int countAll();

    List<Stores> selectOnlyValidateData();

    List<Stores> selectRequiredData(Map<String, Object> searchParams);

    List<Stores> selectAllData(Map<String, Object> searchParams);
    
    @Update("update UAP65.MDM_STORES set similar='N' where code=#{code}")
    int removeData(@Param("code") String code);
}
