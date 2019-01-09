package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Bus;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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
    
    List<Bus> selectRequiredData(Map<String,Object> searchParams);

    List<Bus> selectAllData(Map<String,Object> searchParams);

}