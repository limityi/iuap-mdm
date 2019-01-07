package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.Merchants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 客商  repository类
 * Created by zhugaofeng on 2018/12/20.
 *
 */
@MyBatisRepository
public interface MerchantsRepository {

	@Select("select count(*) from UAP65.MDM_MERCHANTS where dr=0 and similar='Y'")
    int countAll();

    List<Merchants> selectOnlyValidateData();
    
    List<Merchants> selectRequiredData(List<String> list);

    List<Merchants> selectAllData(Map<String,Object> searchParams);

    @Update("update UAP65.MDM_MERCHANTS set similar='N' where code=#{code}")
    int removeData(@Param("code") String code);
}
