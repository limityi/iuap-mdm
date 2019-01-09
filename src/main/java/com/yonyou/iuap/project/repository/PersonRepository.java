package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Person;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 人员 repository类
 * Created by zhugaofeng on 2018/12/20.
 *
 */
@MyBatisRepository
public interface PersonRepository {
	
	@Select("select count(*) from UAP65.MDM_PERSON where dr=0")
    int countAll();

    List<Person> selectOnlyValidateData();

    List<Person> selectRequiredData(Map<String,Object> searchParams);

    List<Person> selectAllData(Map<String,Object> searchParams);

}
