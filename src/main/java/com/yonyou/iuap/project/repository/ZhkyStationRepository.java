package com.yonyou.iuap.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.ZhkyStation;

/**
 * 智慧客运-站场对比 repository类
 * Created by zhugaofeng on 2018/12/26.
 *
 */
@MyBatisRepository
public interface ZhkyStationRepository {
	
	@Select("select count(*) from UAP65.ZHKY_STATION1 where dr=0")
    int countAll();

    List<ZhkyStation> selectOnlyValidateData();

    //List<ZhkyStation> selectRequiredData(List<String> list);

    List<ZhkyStation> selectIneqNameData();
    
    List<ZhkyStation> selectAllData(Map<String,Object> searchParams);

}
