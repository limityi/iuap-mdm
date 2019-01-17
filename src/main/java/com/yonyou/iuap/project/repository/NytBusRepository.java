package com.yonyou.iuap.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.NytBus;

/**
 * 南粤通车辆-mdm车辆对比 repository类
 * Created by zhugaofeng on 2019/01/17.
 *
 */
@MyBatisRepository
public interface NytBusRepository {
	
	@Select("select count(*) from UAP65.NYT_BUS where dr=0")
    int countAll();

    List<NytBus> selectOnlyValidateData();

    List<NytBus> selectIneqNameData();
    
    List<NytBus> selectAllData(Map<String,Object> searchParams);

}
