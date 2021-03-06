package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Station;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 站场 repository类
 * Created by XiongYi on 2018/11/26.
 */
@MyBatisRepository
public interface StationRepository {

    @Select("select count(*) from UAP65.MDM_STATION where dr=0 and similar='Y'")
    int countAll();

    List<Station> selectOnlyValidateData();

    List<Station> selectRequiredData(Map<String, Object> searchParams);

    List<Station> selectAllData();

    @Update("update UAP65.MDM_STATION set similar='N' where code=#{code}")
    int removeData(@Param("code") String code);
}
