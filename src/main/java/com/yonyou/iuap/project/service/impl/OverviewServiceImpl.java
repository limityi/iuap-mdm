package com.yonyou.iuap.project.service.impl;

import com.yonyou.iuap.project.dt.DTEnum;
import com.yonyou.iuap.project.jdbc.JdbcDao;
import com.yonyou.iuap.project.repository.StationDao;
import com.yonyou.iuap.project.service.OverviewService;
import com.yonyou.iuap.project.service.UserAuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("overviewService")
public class OverviewServiceImpl implements OverviewService {

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private StationDao stationDao;

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
    public List<Map<String, Object>> getMdmRootCategory() {
        String sql = "SELECT b.* FROM UAP65.UAPMDM_FUNCTIONCATEGORY b WHERE b.DR='0' AND b.PK_PARENT='~'";
        return jdbcDao.queryForListBySql(sql);
    }

    @Override
    public List<Map<String, Object>> getMdmCategory(String code) {
        String sql = "SELECT b.* FROM uap65.UAPMDM_FUNCTIONCATEGORY a LEFT JOIN UAP65.UAPMDM_GUIDESIGN b ON b.PK_CATEGORY=a.PK_CATEGORY \n" +
                "WHERE a.DR='0' AND a.PK_PARENT='~' AND b.DR='0'";
        List<Object> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(code)) {
            sql += " AND a.CODE=?";
            params.add(code);
        }
        sql += " ORDER BY b.GD_CODE";
        return jdbcDao.queryForListBySql(sql, params.toArray());
    }

    @Override
    public void updateMdmDataStatistics(String sys, String tableId, String tableName, Integer type, Long value) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String ts = formatter.format(now);
        if (StringUtils.isNotEmpty(sys) && StringUtils.isNotEmpty(tableId) && StringUtils.isNotEmpty(tableName) && type != null) {
            String uSql = "UPDATE IUAP.MDM_DATA_STATISTICS SET STAT_VALUE=?,DATA_SYS=? WHERE TABLE_ID=? AND STAT_TYPE=? AND TS='" + ts + "'";
            int i = jdbcDao.updateBySql(uSql, value, sys, tableId, type);
            if (i == 0) {
                String iSql = "INSERT INTO IUAP.MDM_DATA_STATISTICS (TABLE_ID,TABLE_NAME,STAT_TYPE,STAT_VALUE,TS,DATA_SYS) VALUES ('" + tableId + "','" + tableName + "'，" + type + "," + value + ",'" + ts + "','" + sys + "') ";
                jdbcDao.execute(iSql);
            }
        }
    }

    @Override
    public Long getMdmDataCount(String tableName) {
        Long count = null;
        if (StringUtils.isNotEmpty(tableName)) {
            String sql = "SELECT COUNT(*) value FROM";
            sql += " UAP65." + tableName;
            List<Map<String, Object>> clist = jdbcDao.queryForListBySql(sql);
            if (clist != null && clist.size() > 0) {
                count = Long.valueOf(clist.get(0).get("VALUE").toString());
            }
        }
        return count;
    }


    @Override
    public void updateMdmDataCount() {
        Map<String, Object> map = userAuthService.getAuthMenusByCache("jtadmin");

        List<Map<String, Object>> menus = (List<Map<String, Object>>) map.get("menus");
        List<Map<String, Object>> zhkyMenus = (List<Map<String, Object>>) map.get("zhkyMenus");
        List<Map<String, Object>> nytMenus = (List<Map<String, Object>>) map.get("nytMenus");
        List<Map<String, Object>> dotstationMenus = (List<Map<String, Object>>) map.get("dotstationMenus");

        Date now = new Date();


        if (menus != null && menus.size() > 0) {
            String sys = DTEnum.MdmSys.MDM.getId();
            for (Map<String, Object> menu : menus) {
                String tableName = (String) menu.get("RESCODE");
                String name = DTEnum.UserMenus.valueOfId(tableName).getDtName();
                tableName = tableName.split("md_")[1];
                if ("MDM_STRORES".equalsIgnoreCase(tableName)) {
                    tableName = "MDM_STORES";
                }
                tableName = tableName.toUpperCase();
                Long value = this.getMdmDataCount(tableName);
                if (value != null) {
                    this.updateMdmDataStatistics(sys, tableName, name, 0, value);
                }

                Long logCount = this.getMdmLogDataCount(tableName, now);
                if (logCount != null) {
                    this.updateMdmDataStatistics(sys, tableName, name, 4, logCount);
                }
            }
        }

        if (zhkyMenus != null && zhkyMenus.size() > 0) {
            String sys = DTEnum.MdmSys.ZHKY.getId();
            for (Map<String, Object> menu : zhkyMenus) {
                String tableName = (String) menu.get("RESCODE");
                String name = DTEnum.ZhkyMenus.valueOfId(tableName).getDtName();
                tableName = tableName.split("md_")[1];
                if ("MDM_STRORES".equalsIgnoreCase(tableName)) {
                    tableName = "MDM_STORES";
                }
                tableName = tableName.toUpperCase();
                Long value = this.getMdmDataCount(tableName);
                if (value != null) {
                    this.updateMdmDataStatistics(sys, tableName, name, 0, value);
                }

                Long logCount = this.getMdmLogDataCount(tableName, now);
                if (logCount != null) {
                    this.updateMdmDataStatistics(sys, tableName, name, 4, logCount);
                }
            }
        }

        if (nytMenus != null && nytMenus.size() > 0) {
            String sys = DTEnum.MdmSys.NYT.getId();
            for (Map<String, Object> menu : nytMenus) {
                String tableName = (String) menu.get("RESCODE");
                String name = DTEnum.NytMenus.valueOfId(tableName).getDtName();
                tableName = tableName.split("md_")[1];
                if ("MDM_STRORES".equalsIgnoreCase(tableName)) {
                    tableName = "MDM_STORES";
                }
                tableName = tableName.toUpperCase();
                Long value = this.getMdmDataCount(tableName);
                if (value != null) {
                    this.updateMdmDataStatistics(sys, tableName, name, 0, value);
                }

                Long logCount = this.getMdmLogDataCount(tableName, now);
                if (logCount != null) {
                    this.updateMdmDataStatistics(sys, tableName, name, 4, logCount);
                }
            }
        }
    }

    @Override
    public Map<String, Object> getStatisticsCount(String sys, String tableName, Integer statType) {
        Map<String, Object> cmap = null;
        if (StringUtils.isNotEmpty(sys) && StringUtils.isNotEmpty(tableName)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String ts = formatter.format(now);
            String sql = "SELECT * FROM IUAP.MDM_DATA_STATISTICS WHERE DATA_SYS=? AND TABLE_ID=? AND STAT_TYPE=? AND TS='" + ts + "'";
            List<Map<String, Object>> list = jdbcDao.queryForListBySql(sql, sys, tableName, statType);
            if (list != null && list.size() > 0) {
                cmap = new HashMap<>();
                cmap.put("name", list.get(0).get("TABLE_NAME"));
                cmap.put("value", list.get(0).get("STAT_VALUE"));
            }
        }
        return cmap;
    }

    @Override
    public Long getStatisticsTotal(Integer type, Date statDate, String sys) {
        Long count = null;
        if (type != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if (statDate == null)
                statDate = new Date();
            String ts = formatter.format(statDate);

            List<Object> params = new ArrayList<>();
            params.add(type);
            String sql = "SELECT NVL(SUM(STAT_VALUE), 0) VALUE FROM IUAP.MDM_DATA_STATISTICS WHERE STAT_TYPE=? AND TS='" + ts + "'";
            if (StringUtils.isNotEmpty(sys)) {
                sql += " AND DATA_SYS=?";
                params.add(sys);
            }
            List<Map<String, Object>> list = jdbcDao.queryForListBySql(sql, params.toArray());
            if (list != null && list.size() > 0) {
                String value = list.get(0).get("VALUE").toString();
                count = Long.valueOf(value);
            }
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> getDataTotals() {
        List<Map<String, Object>> totals = new ArrayList<>();
        Map<String, Object> one = new HashMap<>();

        Calendar c = Calendar.getInstance();
        Date now = new Date();
        c.setTime(now);
        c.add(Calendar.YEAR, -1);
        Date year = c.getTime();

        one.put("id", 0);
        one.put("name", "接收数据");
        one.put("today", this.getStatisticsTotal(0, now, null));
        one.put("yoyear", this.getStatisticsTotal(0, year, null));
        totals.add(one);

        Map<String, Object> two = new HashMap<>();
        two.put("id", 1);
        two.put("name", "共享数据");
        two.put("today", "0");
        two.put("yoyear", "0");
        totals.add(two);

        Map<String, Object> three = new HashMap<>();
        three.put("id", 2);
        three.put("name", "重复数据");
        three.put("today", this.getStatisticsTotal(1, now, null));
        three.put("yoyear", this.getStatisticsTotal(1, year, null));
        totals.add(three);

        Map<String, Object> four = new HashMap<>();
        four.put("id", 3);
        four.put("name", "相似数据");
        four.put("today", this.getStatisticsTotal(2, now, null));
        four.put("yoyear", this.getStatisticsTotal(2, year, null));
        totals.add(four);
        return totals;
    }

    @Override
    public List<Map<String, Object>> getDataCategories() {
        List<Map<String, Object>> categories = new ArrayList<>();
        Map<String, Object> onec = new HashMap<>();
        onec.put("id", 0);
        onec.put("name", "数据存量分析");
        onec.put("data", this.getCategoryData(0));
        categories.add(onec);

        Map<String, Object> twoc = new HashMap<>();
        twoc.put("id", 1);
        twoc.put("name", "重复数据占比");
        twoc.put("data", this.getCategoryData(1));
        categories.add(twoc);

        Map<String, Object> threec = new HashMap<>();
        threec.put("id", 2);
        threec.put("name", "相似数据占比");
        threec.put("data", this.getCategoryData(2));
        categories.add(threec);

        return categories;
    }


    @Override
    public List<Map<String, Object>> getCategoryData(Integer mode) {
        List<Map<String, Object>> item = new ArrayList<>();
        if (mode != null) {
            Map<String, Object> map = userAuthService.getAuthMenusByCache("jtadmin");
            List<Map<String, Object>> menus = (List<Map<String, Object>>) map.get("menus");
            if (menus != null && menus.size() > 0) {
                for (Map<String, Object> menu : menus) {
                    String tableName = (String) menu.get("RESCODE");
                    tableName = tableName.split("md_")[1];
                    if ("MDM_STRORES".equalsIgnoreCase(tableName)) {
                        tableName = "MDM_STORES";
                    }
                    tableName = tableName.toUpperCase();
                    if (mode == 0) {//数据存量分析
                        item.add(this.getStatisticsCount(DTEnum.MdmSys.MDM.getId(), tableName, 0));
                    }
                    if (mode == 1) {//重复数据占比
                        item.add(this.getStatisticsCount(DTEnum.MdmSys.MDM.getId(), tableName, 1));
                    }
                    if (mode == 2) {//相似数据占比
                        Map<String, Object> m = this.getStatisticsCount(DTEnum.MdmSys.MDM.getId(), tableName, 2);
                        if (m != null)
                            item.add(m);
                    }
                }
            }
        }
        return item;
    }

    @Override
    public List<Map<String, Object>> getSysListMap(Integer mode) {
        List<Map<String, Object>> slist = null;
        if (mode != null) {
            slist = this.getMdmSysregister(null);
            for (Map<String, Object> m : slist) {
                String sysCode = (String) m.get("CODE");
                sysCode = sysCode.toUpperCase();
                Long value = getStatisticsTotal(mode, new Date(), sysCode);
                m.put("value", value);
            }
        }
        return slist;
    }

    @Override
    public List<Map<String, Object>> getSysCategoryData(Integer mode, String systemId) {
        List<Map<String, Object>> item = null;
        if (mode != null && StringUtils.isNotEmpty(systemId)) {
            Map<String, Object> map = userAuthService.getAuthMenusByCache("jtadmin");

            if (systemId.equals(DTEnum.MdmSys.MDM.getId())) {
                List<Map<String, Object>> menus = (List<Map<String, Object>>) map.get("menus");
                if (menus != null && menus.size() > 0) {
                    item = new ArrayList<>();
                    for (Map<String, Object> menu : menus) {
                        String tableName = (String) menu.get("RESCODE");
                        tableName = tableName.split("md_")[1];
                        if ("MDM_STRORES".equalsIgnoreCase(tableName)) {
                            tableName = "MDM_STORES";
                        }
                        tableName = tableName.toUpperCase();
                        if (mode == 0) {//数据存量分析
                            item.add(this.getStatisticsCount(systemId, tableName, 0));
                        }
                        if (mode == 1) {//重复数据占比
                            item.add(this.getStatisticsCount(systemId, tableName, 1));
                        }
                        if (mode == 2) {//相似数据占比
                            Map<String, Object> sm = this.getStatisticsCount(systemId, tableName, 2);
                            if (sm != null)
                                item.add(sm);
                        }
                        if (mode == 4) {//系统接口监控
                            item.add(this.getStatisticsCount(systemId, tableName, 4));
                        }
                    }
                }
            }

            if (systemId.equals(DTEnum.MdmSys.RC.getId())) {

            }

            if (systemId.equals(DTEnum.MdmSys.RC.getId())) {

            }

            if (systemId.equals(DTEnum.MdmSys.NC65.getId())) {

            }

            if (systemId.equals(DTEnum.MdmSys.ZHKY.getId())) {
                List<Map<String, Object>> zhkyMenus = (List<Map<String, Object>>) map.get("zhkyMenus");
                if (zhkyMenus != null && zhkyMenus.size() > 0) {
                    item = new ArrayList<>();
                    for (Map<String, Object> menu : zhkyMenus) {
                        String tableName = (String) menu.get("RESCODE");
                        tableName = tableName.split("md_")[1];
                        tableName = tableName.toUpperCase();
                        if (mode == 0) {//数据存量分析
                            item.add(this.getStatisticsCount(systemId, tableName, 0));
                        }
                        if (mode == 1) {//重复数据占比
                            item.add(this.getStatisticsCount(systemId, tableName, 1));
                        }
                        if (mode == 2) {//相似数据占比
                            Map<String, Object> sm = this.getStatisticsCount(systemId, tableName, 02);
                            if (sm != null)
                                item.add(sm);
                        }
                        if (mode == 4) {//系统接口监控
                            item.add(this.getStatisticsCount(systemId, tableName, 4));
                        }
                    }
                }
            }

            if (systemId.equals(DTEnum.MdmSys.NYT.getId())) {
                List<Map<String, Object>> nytMenus = (List<Map<String, Object>>) map.get("nytMenus");
                if (nytMenus != null && nytMenus.size() > 0) {
                    item = new ArrayList<>();
                    for (Map<String, Object> menu : nytMenus) {
                        String tableName = (String) menu.get("RESCODE");
                        tableName = tableName.split("md_")[1];
                        tableName = tableName.toUpperCase();
                        if (mode == 0) {//数据存量分析
                            item.add(this.getStatisticsCount(systemId, tableName, 0));
                        }
                        if (mode == 1) {//重复数据占比
                            item.add(this.getStatisticsCount(systemId, tableName, 1));
                        }
                        if (mode == 2) {//相似数据占比
                            Map<String, Object> sm = this.getStatisticsCount(systemId, tableName, 2);
                            if (sm != null)
                                item.add(sm);
                        }
                        if (mode == 4) {//系统接口监控
                            item.add(this.getStatisticsCount(systemId, tableName, 4));
                        }
                    }
                }
            }


            if (systemId.equals(DTEnum.MdmSys.XJKY.getId())) {

            }

        }
        return item;
    }

    @Override
    public List<Map<String, Object>> getDataSystems() {

        List<Map<String, Object>> systems = new ArrayList<>();
        Map<String, Object> ones = new HashMap<>();
        ones.put("id", 0);
        ones.put("name", "系统接收情况");
        ones.put("data", this.getSysListMap(0));
        ones.put("selected", this.getSysCategoryData(0, DTEnum.MdmSys.MDM.getId()));
        systems.add(ones);

        Map<String, Object> twos = new HashMap<>();
        twos.put("id", 3);
        twos.put("name", "系统共享情况");
        twos.put("data", this.getSysListMap(3));
        twos.put("selected", this.getSysCategoryData(3, DTEnum.MdmSys.MDM.getId()));
        systems.add(twos);

        Map<String, Object> threes = new HashMap<>();
        threes.put("id", 4);
        threes.put("name", "系统接口监控");
        threes.put("data", this.getSysListMap(4));
        threes.put("selected", this.getSysCategoryData(4, DTEnum.MdmSys.MDM.getId()));
        systems.add(threes);

        return systems;
    }

    @Override
    public Long getMdmLogDataCount(String tableName, Date statDate) {
        Long count = null;
        if (StringUtils.isNotEmpty(tableName)) {
            tableName = tableName.toUpperCase() + "_LOG";

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            if (statDate == null)
                statDate = new Date();

            c.setTime(statDate);
            c.add(Calendar.DAY_OF_MONTH, -10);
            Date startDate = c.getTime();

            String sql = "SELECT COUNT(*) VALUE FROM IUAP." + tableName + " WHERE LOGDATE BETWEEN TO_DATE('" + format.format(startDate) + "', 'yyyy-MM-dd HH24:mi:ss') AND TO_DATE('" + format.format(statDate) + "', 'yyyy-MM-dd HH24:mi:ss')";
            List<Map<String, Object>> list = jdbcDao.queryForListBySql(sql);
            if (list != null && list.size() > 0) {
                String value = list.get(0).get("VALUE").toString();
                count = Long.valueOf(value);
            }
        }
        return count;
    }

}
