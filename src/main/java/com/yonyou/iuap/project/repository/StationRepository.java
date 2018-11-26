package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import org.apache.ibatis.annotations.Select;

/**
 * 站场 repository类
 * Created by XiongYi on 2018/11/26.
 *
 */
@MyBatisRepository
public interface StationRepository {

    @Select("select count(*) from IUAP.MDM_STATION where dr=0")
    int countAll();
}
