package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Dot;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Description 网上飞网点
 *
 * @author binbin
 * @date 2018/12/18 15:13
 */
@MyBatisRepository
public interface DotRepository {

    @Select("select count(*) from UAP65.MDM_DOT where dr=0")
    int countAll();

    List<Dot> selectOnlyValidateData();

    List<Dot> selectRequiredData(List<String> list);

    List<Dot> selectAllData(Map<String, Object> searchParams);
}
