package com.yonyou.iuap.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.ZhkyBus;

/**
 * 智慧客运-公交对比 repository类
 * Created by zhugaofeng on 2019/1/9.
 *
 */
@MyBatisRepository
public interface ZhkyBusRepository {

	@Select("select count(*) from UAP65.ZHKY_BUS where dr=0")
    int countAll();

    List<ZhkyBus> selectOnlyValidateData();

    List<ZhkyBus> selectIneqNameData();
    
    List<ZhkyBus> selectAllData(Map<String,Object> searchParams);
}
