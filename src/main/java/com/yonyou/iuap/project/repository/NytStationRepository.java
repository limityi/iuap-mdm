package com.yonyou.iuap.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.NytStation;
/**
 * 南粤通站场 - mdm站场对比 repository类
 * Created by zhugaofeng on 2019/12/15.
 *
 */
@MyBatisRepository
public interface NytStationRepository {

	@Select("select count(*) from UAP65.NYT_STATION where dr=0 ")
    int countAll();

    List<NytStation> selectOnlyValidateData();

    List<NytStation> selectIneqNameData();
    
    List<NytStation> selectAllData(Map<String,Object> searchParams);
}
