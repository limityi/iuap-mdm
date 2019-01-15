package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Settlementmethod;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface SettlementmethodRepository {

    @Select("select count(*) from UAP65.MDM_SETTLEMENTMETHOD where dr=0")
    int countAll();

    List<Settlementmethod> selectOnlyValidateData();

    List<Settlementmethod> selectRequiredData(Map<String, Object> searchParams);

    List<Settlementmethod> selectAllData();
}
