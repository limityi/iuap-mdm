package com.yonyou.iuap.project.service.impl;

import com.yonyou.iuap.project.dt.DTEnum;
import com.yonyou.iuap.project.jdbc.JdbcDao;
import com.yonyou.iuap.project.service.OverviewService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("overviewService")
public class OverviewServiceImpl implements OverviewService {

    @Autowired
    private JdbcDao jdbcDao;


    @Override
    public List<Map<String, Object>> getMdmSysregister(String code) {
        String sql = "SELECT b.* FROM UAP65.UAPMDM_SYSREGISTER b WHERE b.DR=0";
        List<Object> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(code)) {
            sql += " AND b.CODE=?";
            params.add(code);
        }
        sql += " ORDER BY b.CODE";

        return jdbcDao.queryForListBySql(sql, params.toArray());
    }

    @Override
    public Map<String, Object> getMdmDataCount(String tableName) {
        Map<String, Object> cmap = null;
        if (StringUtils.isNotEmpty(tableName)) {
            String sql = "SELECT COUNT(*) value FROM";
            sql += " UAP65." + tableName;
            List<Map<String, Object>> clist = jdbcDao.queryForListBySql(sql);
            if (clist != null && clist.size() > 0)
                cmap = clist.get(0);
            String value = cmap.get("VALUE").toString();
            cmap.remove("VALUE");
            cmap.put("name", DTEnum.MdmTable.valueOfId(tableName).getDtName());
            cmap.put("value", value);
        }
        return cmap;
    }

    @Override
    public Map<String, Object> getMdmDataRepeatCount(String tableName) {
        Map<String, Object> cmap = null;
        if (StringUtils.isNotEmpty(tableName)) {
            String sql = "SELECT COUNT(*) value FROM";
            sql += " UAP65." + tableName;
            List<Map<String, Object>> clist = jdbcDao.queryForListBySql(sql);
            if (clist != null && clist.size() > 0)
                cmap = clist.get(0);
            String value = cmap.get("VALUE").toString();
            cmap.remove("VALUE");
            cmap.put("name", DTEnum.MdmTable.valueOfId(tableName).getDtName());
            cmap.put("value", value);
        }
        return cmap;
    }

}
