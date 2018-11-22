package com.yonyou.iuap.station.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import org.apache.ibatis.annotations.Select;

/**
 * 站场 repository类
 * Created by XiongYi on 2018/11/21.
 *
 */
@MyBatisRepository
public interface NbStationRepository {

    @Select("select count(*) from IUAP.MDM_NBSTATION where dr=0")
    int countAll();
}
