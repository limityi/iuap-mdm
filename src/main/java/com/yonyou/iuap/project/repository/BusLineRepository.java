package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.BusLine;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 公交线路 repository类
 * Created by zhugaofeng on 2018/12/14.
 *
 */
@MyBatisRepository
public interface BusLineRepository {
	
	@Select("select count(*) from UAP65.MDM_BUSLINE where dr=0 and similar='Y'")
    int countAll();

    List<BusLine> selectOnlyValidateData();

    List<BusLine> selectRequiredData(List<String> list);

    List<BusLine> selectAllData(Map<String,Object> searchParams);

    @Update("update UAP65.MDM_BUSLINE set similar='N' where code=#{code}")
    int removeData(@Param("code") String code);

}
