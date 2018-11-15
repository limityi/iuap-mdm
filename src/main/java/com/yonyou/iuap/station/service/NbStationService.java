package com.yonyou.iuap.station.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.station.entity.NbStation;
import com.yonyou.iuap.station.repository.NbStationDao;
import com.yonyou.iuap.mvc.type.SearchParams;

/**
 * <p>Title: CardTableMetaService</p>
 * <p>Description: </p>
 */
@Service
public class NbStationService {
	
    @Autowired
    private NbStationDao dao;
    
    /**
     * Description:通过非主键字段查询
     * List<CardTable>
     * @param code
     */
    public List<NbStation> findByCode(String code) {
        return dao.findByCode(code);
    }
    public List<NbStation> findByName(String code) {
        return dao.findByName(code);
    }

    /**
     * 查询所有数据
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<NbStation> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        Page<NbStation> pageResult = dao.selectAllByPage(pageRequest, searchParams.getSearchMap()) ;
		return pageResult;
    }
    
    public void save(List<NbStation> recordList) {
        List<NbStation> addList = new ArrayList<>(recordList.size());
        List<NbStation> updateList = new ArrayList<>(recordList.size());
        for (NbStation meta : recordList) {
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
    
    public void batchDeleteByPrimaryKey(List<NbStation> list) {
    	dao.batchDelete(list);
    }
    
}
