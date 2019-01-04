package com.yonyou.iuap.project.service.impl;

import com.yonyou.iuap.project.dt.DTEnum;
import com.yonyou.iuap.project.jdbc.JdbcDao;
import com.yonyou.iuap.project.service.UserAuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private JdbcDao jdbcDao;

    @Override
    public Map<String, Object> getUser(String userCode) {
        Map<String, Object> user = null;
        if (StringUtils.isNotEmpty(userCode)) {
            String sql = "SELECT b.* FROM UAP65.SM_USER b WHERE b.USER_CODE=?";
            user = jdbcDao.queryForMapBySql(sql, userCode.trim());
        }
        return user;
    }

    @Override
    public List<Map<String, Object>> getUserRoles(String cUserId) {
        List<Map<String, Object>> roles = null;
        if (StringUtils.isNotEmpty(cUserId)) {
            String sql = "SELECT b.* FROM UAP65.SM_USER_ROLE b WHERE b.CUSERID=?";
            roles = jdbcDao.queryForListBySql(sql, cUserId.trim());
        }
        return roles;
    }

    @Override
    public List<Map<String, Object>> getResponsibilitiesByRolePk(String rolePk) {
        List<Map<String, Object>> responsibilities = null;
        if (StringUtils.isNotEmpty(rolePk)) {
            String sql = "SELECT a.PK_ROLE,a.ROLE_CODE,a.ROLE_NAME,a.ROLE_TYPE,c.* FROM UAP65.SM_ROLE a\n" +
                    "LEFT JOIN UAP65.SM_PERM_FUNC b on b.SUBJECTID=a.PK_ROLE\n" +
                    "LEFT JOIN UAP65.SM_RESPONSIBILITY c on c.PK_RESPONSIBILITY=b.RULEID\n" +
                    "WHERE a.PK_ROLE=?";
            responsibilities = jdbcDao.queryForListBySql(sql, rolePk.trim());
        }
        return responsibilities;
    }

    @Override
    public List<Map<String, Object>> getFunctionsByRespPk(String respPk) {
        List<Map<String, Object>> funcs = null;
        if (StringUtils.isNotEmpty(respPk)) {
            String sql = "SELECT c.RESCODE,c.RESNAME FROM UAP65.SM_RESPONSIBILITY a\n" +
                    "LEFT JOIN UAP65.SM_RESP_FUNC b on b.PK_RESPONSIBILITY=a.PK_RESPONSIBILITY\n" +
                    "LEFT JOIN UAP65.CP_RES c on c.SOURCEPK=b.BUSI_PK\n" +
                    "WHERE a.PK_RESPONSIBILITY=?";
            funcs = jdbcDao.queryForListBySql(sql, respPk.trim());
        }
        return funcs;
    }

    @Override
    public Map<String, Object> getAuthMenus(String userCode) {
        Map<String, Object> userMenus = null;
        if (StringUtils.isNotEmpty(userCode)) {
            Map<String, Object> user = this.getUser(userCode);
            if (user != null) {
                Integer userType = Integer.valueOf(user.get("USER_TYPE").toString());
                if (userType == 0) {//管理员查看全部目录
                    userMenus = new HashMap<>();
                    userMenus.put("userType", 0);

                    List<Map<String, Object>> nmenus = new ArrayList<>();
                    for (DTEnum.UserMenus menu : DTEnum.UserMenus.values()) {
                        Map<String, Object> mp = new HashMap<>();
                        mp.put("RESCODE", menu.getId());
                        mp.put("RESNAME", menu.getDtName());
                        nmenus.add(mp);
                    }
                    userMenus.put("menus", nmenus);

                    List<Map<String, Object>> zmenus = new ArrayList<>();
                    for (DTEnum.ZhkyMenus menu : DTEnum.ZhkyMenus.values()) {
                        Map<String, Object> mp = new HashMap<>();
                        mp.put("RESCODE", menu.getId());
                        mp.put("RESNAME", menu.getDtName());
                        zmenus.add(mp);
                    }

                    userMenus.put("zhkyMenus", zmenus);
                }
                if (userType == 1) {//普通用户根据配置获取查看目录
                    userMenus = new HashMap<>();
                    userMenus.put("userType", 1);
                    String cUserId = (String) user.get("CUSERID");
                    List<Map<String, Object>> roles = this.getUserRoles(cUserId);
                    if (roles != null && roles.size() > 0) {
                        for (Map<String, Object> role : roles) {
                            String rolePk = (String) role.get("PK_ROLE");
                            List<Map<String, Object>> resps = this.getResponsibilitiesByRolePk(rolePk);
                            if (resps != null && resps.size() > 0) {
                                List<Map<String, Object>> menus = new ArrayList<>();
                                for (Map<String, Object> resp : resps) {
                                    String respPk = (String) resp.get("PK_RESPONSIBILITY");
                                    List<Map<String, Object>> funcs = this.getFunctionsByRespPk(respPk);
                                    menus.addAll(funcs);
                                }

                                //去重
                                HashSet h = new HashSet(menus);
                                menus.clear();
                                menus.addAll(h);

                                //质量管理
                                List<String> ids = new ArrayList<>();
                                for (DTEnum.UserMenus menu : DTEnum.UserMenus.values()) {
                                    ids.add(menu.getId());
                                }

                                List<Map<String, Object>> nmenus = new ArrayList<>();
                                for (Map<String, Object> menu : menus) {
                                    if (ids.contains(menu.get("RESCODE"))) {
                                        nmenus.add(menu);
                                    }
                                }
                                userMenus.put("menus", nmenus);

                                //智慧客运
                                List<String> zhkyIds = new ArrayList<>();
                                for (DTEnum.ZhkyMenus menu : DTEnum.ZhkyMenus.values()) {
                                    zhkyIds.add(menu.getId());
                                }

                                List<Map<String, Object>> zmenus = new ArrayList<>();
                                for (Map<String, Object> menu : menus) {
                                    if (zhkyIds.contains(menu.get("RESCODE"))) {
                                        zmenus.add(menu);
                                    }
                                }

                                userMenus.put("zhkyMenus", zmenus);
                            }
                        }
                    }
                }
            }
        }
        return userMenus;
    }

}
