package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Costitem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface CostitemRepository {

    @Select("select count(*) from UAP65.MDM_COSTITEM where dr=0 and similar='Y'")
    int countAll();

    List<Costitem> selectOnlyValidateData();

    List<Costitem> selectRequiredData(Map<String, Object> searchParams);

    List<Costitem> selectAllData(Map<String, Object> searchParams);

    @Update("update UAP65.MDM_COSTITEM set similar='N' where code=#{code}")
    int removeData(@Param("code") String code);
}
