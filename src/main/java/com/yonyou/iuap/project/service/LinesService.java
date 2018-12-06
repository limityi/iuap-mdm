package com.yonyou.iuap.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.project.entity.Lines;
import com.yonyou.iuap.project.repository.LinesDao;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.bs.jdbc.meta.access.DASFacade;

/**
 * <p>Title: CardTableMetaService</p>
 * <p>Description: </p>
 */
@Service
public class LinesService {
	
    @Autowired
    private LinesDao dao;
    
    /**
     * Description:通过非主键字段查询
     * List<CardTable>
     * @param str
     */
    public List<Lines> findByCode(String code) {
        return dao.findByCode(code);
    }
    public List<Lines> findByName(String code) {
        return dao.findByName(code);
    }
    
    
    public Page<Lines> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        Page<Lines> pageResult = dao.selectAllByPage(pageRequest, searchParams.getSearchMap()) ;
		return pageResult;
    }
    
    public void save(List<Lines> recordList) {
        List<Lines> addList = new ArrayList<>(recordList.size());
        List<Lines> updateList = new ArrayList<>(recordList.size());
        for (Lines meta : recordList) {
        	if (meta.getId() == null) {
            	meta.setId(UUID.randomUUID().toString());
            	meta.setDr(0);
                addList.add(meta);
            } else {
                updateList.add(meta);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
        	dao.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
        	dao.batchUpdate(updateList);
        }
    }
    
    public void batchDeleteByPrimaryKey(List<Lines> list) {
    	dao.batchDelete(list);
    }
    
}
