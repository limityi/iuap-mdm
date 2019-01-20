package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Ticketsales;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface TicketsalesRepository {

    @Select("select count(*) from UAP65.MDM_TICKETSALES where similar='Y'")
    int countAll();

    List<Ticketsales> selectOnlyValidateData();

    List<Ticketsales> selectRequiredData(Map<String, Object> searchParams);

    List<Ticketsales> selectAllData();

    @Update("update UAP65.MDM_TICKETSALES set similar='N' where code=#{code}")
    int removeData(@Param("code") String code);
}
