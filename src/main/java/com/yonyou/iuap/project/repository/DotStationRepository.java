package com.yonyou.iuap.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Dot;
import com.yonyou.iuap.project.entity.DotStation;

/**
 * 网上飞-站场对比 repository类
 * Created by zhugaofeng on 2019/1/18.
 *
 */
@MyBatisRepository
public interface DotStationRepository {

	@Select("select count(*) from UAP65.MDM_DOT where dr=0 ")
    int countAll();

    List<DotStation> selectOnlyValidateData();

    List<DotStation> selectIneqNameData();
    
    List<DotStation> selectAllData(Map<String,Object> searchParams);
}
