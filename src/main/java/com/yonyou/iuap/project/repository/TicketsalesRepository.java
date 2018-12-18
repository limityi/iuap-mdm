package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Ticketsales;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface TicketsalesRepository {

    @Select("select count(*) from UAP65.MDM_TICKETSALES where dr=0")
    int countAll();

    List<Ticketsales> selectOnlyValidateData();

    List<Ticketsales> selectRequiredData(List<String> list);

    List<Ticketsales> selectAllData(Map<String, Object> searchParams);
}
