package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Costitem;
import com.yonyou.iuap.project.entity.Merchants;
import com.yonyou.iuap.project.entity.Station;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface CostitemRepository {

    @Select("select count(*) from UAP65.MDM_COSTITEM where dr=0")
    int countAll();

    List<Costitem> selectOnlyValidateData();

    List<Costitem> selectRequiredData(List<String> list);

    List<Costitem> selectAllData(Map<String, Object> searchParams);
}
