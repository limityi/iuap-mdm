package com.yonyou.iuap.project.jdbc;

import java.util.List;
import java.util.Map;

public interface JdbcDao {

    List<Map<String, Object>> queryForListBySql(String sql, Object... args);

    Map<String, Object> queryForMapBySql(String sql, Object... args);

    int updateBySql(String sql, Object... args);

    void execute(String sql);

}
