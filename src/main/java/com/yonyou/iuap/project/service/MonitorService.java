package com.yonyou.iuap.project.service;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.project.entity.Monitor;
import com.yonyou.iuap.project.repository.MonitorDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class MonitorService {
    private Logger logger = LoggerFactory.getLogger(MonitorService.class);

    @Autowired
    private MonitorDao dao;

	@Autowired
    private MonitorLogService childService;
    
    @Transactional
    public Monitor save(Monitor entity) {
    	logger.debug("execute  Monitor save .");
        return	dao.save(entity) ;
    }

    /**
     * 批量删除数据
     * 
     * @param list
     */
    @Transactional
    public void batchDeleteEntity(List<Monitor> list) {
        this.batchDelChild(list);
        dao.batchDelete(list);
    }

    /**
     * 删除主表下面的子表数据
     * 
     * @param list
     * @throws DAOException
     */
    private void batchDelChild(List<Monitor> list) throws DAOException {
        dao.batchDelChild(list);
    }

    
    /**
     * 根据传递的参数，进行分页查询
     */
    public Page<Monitor> selectAllByPage(Map<String, Object> searchParams, PageRequest pageRequest) {
      	Page<Monitor> pageResult = dao.selectAllByPage(pageRequest, searchParams) ;
		return pageResult;
    }


                  
 
    
}
