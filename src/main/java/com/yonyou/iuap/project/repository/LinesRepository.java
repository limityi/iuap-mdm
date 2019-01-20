package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Lines;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 客运线路 repository类
 * Created by zhugaofeng on 2018/12/5.
 */
@MyBatisRepository
public interface LinesRepository {

    @Select("select count(*) from UAP65.MDM_LINE where dr=0 and similar='Y'")
    int countAll();

    List<Lines> selectOnlyValidateData();

    List<Lines> selectRequiredData(Map<String, Object> searchParams);

    List<Lines> selectAllData();

    @Update("update UAP65.MDM_LINE set similar='N' where code=#{code}")
    int removeData(@Param("code") String code);
}
