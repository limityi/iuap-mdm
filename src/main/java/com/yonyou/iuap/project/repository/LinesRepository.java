package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Lines;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 客运线路 repository类
 * Created by XiongYi on 2018/11/26.
 *
 */
@MyBatisRepository
public interface LinesRepository {

    @Select("select count(*) from IUAP.MDM_LINES where dr=0")
    int countAll();

    List<Lines> selectOnlyValidateData();

    List<Lines> selectRequiredData(List<String> list);

    List<Lines> selectAllData(Map<String,Object> searchParams);
}
