package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Merchants;
import com.yonyou.iuap.project.entity.Station;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface MerchantsRepository {

    @Select("select count(*) from UAP65.MDM_MERCHANTS where dr=0")
    int countAll();

    List<Merchants> selectOnlyValidateData();

    List<Merchants> selectRequiredData(List<String> list);

    List<Merchants> selectAllData(Map<String,Object> searchParams);
}
