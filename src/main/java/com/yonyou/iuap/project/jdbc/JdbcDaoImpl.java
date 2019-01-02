package com.yonyou.iuap.project.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Component("jdbcDao")
public class JdbcDaoImpl extends JdbcDaoSupport implements JdbcDao {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        super.setDataSource(dataSource);
    }

    @Override
    public List<Map<String, Object>> queryForListBySql(String sql, Object... args) {
        return getJdbcTemplate().queryForList(sql, args);
    }

    @Override
    public Map<String, Object> queryForMapBySql(String sql, Object... args) {
        return getJdbcTemplate().queryForMap(sql, args);
    }

    @Override
    public int updateBySql(String sql, Object... args) {
        return getJdbcTemplate().update(sql, args);
    }

    @Override
    public void execute(String sql) {
        getJdbcTemplate().execute(sql);
    }

}
