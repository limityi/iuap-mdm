package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Fleet;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 车队 repository类
 * Created by zhugaofeng on 2018/12/19.
 */
@MyBatisRepository
public interface FleetRepository {

    @Select("select count(*) from UAP65.MDM_FLEET where dr=0")
    int countAll();

    List<Fleet> selectOnlyValidateData();

    List<Fleet> selectRequiredData(Map<String, Object> searchParams);

    List<Fleet> selectAllData();

}
