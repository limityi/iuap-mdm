package com.yonyou.iuap.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;
import com.yonyou.iuap.persistence.bs.jdbc.meta.access.DASFacade;

import com.yonyou.iuap.project.entity.Monitor;
import com.yonyou.iuap.project.entity.MonitorLog;
import com.yonyou.iuap.project.repository.MonitorDao;
import com.yonyou.iuap.project.repository.MonitorLogDao;


@Service
public class MonitorLogService {

    @Autowired
    private MonitorLogDao childDao;

    @Autowired
    private MonitorDao dao;

    
    public Page<MonitorLog> selectAllByPage(Map<String, Object> searchParams, PageRequest pageRequest) {
		Page<MonitorLog> pageResult = childDao.selectAllByPage(pageRequest, searchParams) ;
		return pageResult;
	}

    @Transactional
    public void deleteEntity(MonitorLog entity) {
        childDao.delete(entity);
    }

}
