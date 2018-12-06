package com.yonyou.iuap.project.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.project.entity.Lines;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;

/**
 * <p>Title: CardTableMetaDao</p>
 * <p>Description: </p>
 */
@Repository
public class LinesDao {
	
	@Qualifier("mdBaseDAO")
	@Autowired
	private MetadataDAO dao;
	
	//根据某一非主键字段查询实体
	public List<Lines> findByCode(String code){
		String sql = "select * from mdm_lines where code=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(code);
        List<Lines> list = dao.queryByClause(Lines.class, sql, sqlparam);
        return list;
	}
	//根据某一非主键字段查询实体
	public List<Lines> findByName(String name){
		String sql = "select * from mdm_lines where name=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(name);
        List<Lines> list = dao.queryByClause(Lines.class, sql, sqlparam);
        return list;
	}
    
    public Page<Lines> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        String sql = " select * from mdm_lines"; 
        SQLParameter sqlparam = new SQLParameter();
        if (!searchParams.isEmpty()) {
            sql = sql + " where ";
            for (String key : searchParams.keySet()) {
                if (key.equalsIgnoreCase("searchParam")) {
                    sql =sql + "(code like ? OR name like ?) AND";
                    for (int i = 0; i < 2; i++) {
                        sqlparam.addParam("%" + searchParams.get(key) + "%");
                    }
                }
            }
            sql = sql.substring(0, sql.length() - 4);
        }
        return dao.queryPage(sql, sqlparam, pageRequest, Lines.class);
    }
    
    
    public void batchInsert(List<Lines> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Lines> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Lines> list) {
        dao.remove(list);
    }
    

}
