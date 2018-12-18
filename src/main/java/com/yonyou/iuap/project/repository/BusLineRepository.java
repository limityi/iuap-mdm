package com.yonyou.iuap.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.BusLine;

/**
 * 公交线路 repository类
 * Created by zhugaofeng on 2018/12/14.
 *
 */
@MyBatisRepository
public interface BusLineRepository {
	
	@Select("select count(*) from UAP65.MDM_BUSLINE where dr=0")
    int countAll();

    List<BusLine> selectOnlyValidateData();

    List<BusLine> selectRequiredData(List<String> list);

    List<BusLine> selectAllData(Map<String,Object> searchParams);

}
