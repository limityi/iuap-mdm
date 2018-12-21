package com.yonyou.iuap.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Fleet;

/**
 * 车队 repository类
 * Created by zhugaofeng on 2018/12/19.
 *
 */
@MyBatisRepository
public interface FleetRepository {
	
	@Select("select count(*) from UAP65.MDM_FLEET where dr=0")
    int countAll();

    List<Fleet> selectOnlyValidateData();

    List<Fleet> selectRequiredData(List<String> list);

    List<Fleet> selectAllData(Map<String,Object> searchParams);

}
