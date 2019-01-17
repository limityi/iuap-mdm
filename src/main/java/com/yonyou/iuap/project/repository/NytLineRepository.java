package com.yonyou.iuap.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.NytLine;

/**
 * 南粤通客运线路 - mdm客运线路对比 repository类
 * Created by zhugaofeng on 2019/1/17.
 *
 */
@MyBatisRepository
public interface NytLineRepository {

	@Select("select count(*) from UAP65.NYT_STATION where dr=0 ")
    int countAll();

    List<NytLine> selectOnlyValidateData();

    List<NytLine> selectIneqNameData();
    
    List<NytLine> selectAllData(Map<String,Object> searchParams);
}
