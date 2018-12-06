package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.project.entity.MonitorLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MonitorLogDao {

    @Autowired
    private MetadataDAO dao;

    public MonitorLog findById(String id) throws DAOException {

        String sql = "select * from mdm_monitorlog  where dr='0' and id=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(id);
        List<MonitorLog> list = dao.queryByClause(MonitorLog.class, sql, sqlparam);
        return list == null || list.isEmpty() ? null : list.get(0);
    }


    public void delete(MonitorLog entity) {

        if (null != entity) {
            dao.remove(entity);
        }
    }

    public Page<MonitorLog> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams)
            throws DAOException {

        String sql = " select * from mdm_monitorlog";
        SQLParameter sqlparam = new SQLParameter();

        if (!searchParams.isEmpty()) {
            sql = sql + " where ";
            for (String key : searchParams.keySet()) {
                sql = sql + FastBeanHelper.getColumn(MonitorLog.class, key) + " like ? AND ";
                sqlparam.addParam("%" + searchParams.get(key) + "%");
            }
            sql = sql.substring(0, sql.length() - 4);
        }
        return dao.queryPage(sql, sqlparam, pageRequest, MonitorLog.class);
    }

}
