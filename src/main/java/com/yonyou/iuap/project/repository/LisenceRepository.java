package com.yonyou.iuap.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Lisence;

/**
 * 线路牌 repository类
 * Created by zhugaofeng on 2018/12/17.
 *
 */
@MyBatisRepository
public interface LisenceRepository {
	
	@Select("select count(*) from UAP65.MDM_LISENCE where dr=0")
    int countAll();

    List<Lisence> selectOnlyValidateData();

    List<Lisence> selectRequiredData(List<String> list);

    List<Lisence> selectAllData(Map<String,Object> searchParams);

}
