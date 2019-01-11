package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.SQLHelper;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;
import com.yonyou.iuap.project.entity.Monitor;
import com.yonyou.iuap.project.entity.MonitorLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Repository
public class MonitorDao {

    @Autowired
    private MetadataDAO dao;

    @Autowired
    private MonitorRepository repository;

	@Transactional
    public Monitor save(Monitor entity) {
    	if(entity.getId() ==null ){
    		 entity.setStatus(VOStatus.NEW);
    		 entity.setId(UUID.randomUUID().toString());
             entity.setDr(0);// 未删除标识         
    	}else{
    		entity.setStatus(VOStatus.UPDATED);
    	}
    	
		if(entity.getId_log()!=null && entity.getId_log().size()>0){
    		for(MonitorLog child : entity.getId_log() ){
    			if(child.getId() == null ){
    				child.setStatus(VOStatus.NEW);
    				child.setDr(entity.getDr());
    			}else{
    				child.setStatus(VOStatus.UPDATED);
    			}
    		}
    		dao.save(entity, entity.getId_log().toArray(new MonitorLog[entity.getId_log().size()]));
    	}else{
    		dao.save(entity);
    	}
    	return entity ;
    }


    public int delete(Monitor entity) throws Exception {

        if (null == entity) {
            return 0;
        }
        dao.remove(entity);
        return 1;
    }

    public void batchDelete(List<Monitor> list) throws DAOException {

        dao.remove(list);
    }

    public void batchDelChild(List<Monitor> list) throws DAOException {
        SQLParameter sqlparam = new SQLParameter();
		String deleteSQL = SQLHelper.createDeleteIn("mdm_monitorlog", "fk_id_log", list.size());
        for (Monitor item : list) {
            sqlparam.addParam(item.getId());
        }
        dao.update(deleteSQL, sqlparam);
    }     

}
