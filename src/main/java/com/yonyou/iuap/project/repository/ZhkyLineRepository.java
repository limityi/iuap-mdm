package com.yonyou.iuap.project.repository;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.ZhkyLine;

/**
 * 智慧客运-客运线路对比 repository类
 * Created by zhugaofeng on 2018/12/28.
 *
 */
@MyBatisRepository
public interface ZhkyLineRepository {
	
	@Select("select count(*) from UAP65.ZHKY_LINE where dr=0")
    int countAll();
	
	List<ZhkyLine> selectOnlyValidateData();
	
	List<ZhkyLine> selectIneqNameData();

}
