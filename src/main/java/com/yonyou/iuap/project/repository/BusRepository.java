package com.yonyou.iuap.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Bus;

/**
 * 车辆  repository类
 * Created by zhugaofeng on 2018/12/18.
 *
 */
@MyBatisRepository
public interface BusRepository {
	
	@Select("select count(*) from UAP65.MDM_BUS where dr=0")
    int countAll();

    List<Bus> selectOnlyValidateData();
    
    List<Bus> selectRequiredData(List<String> list);

    List<Bus> selectAllData(Map<String,Object> searchParams);

}