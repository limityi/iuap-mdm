package com.yonyou.iuap.project.service;

import java.util.List;
import java.util.Map;

/**
 * 用户目录权限控制Service
 */
public interface UserAuthService {

    /**
     * @Description 根据用户编码获取用户信息
     * @author binbin
     * @date 2018/12/28 16:00
     */
    Map<String, Object> getUser(String userCode);


    /**
     * @Description 根据用户主键获取用户角色
     * @author binbin
     * @date 2018/12/28 16:20
     */
    List<Map<String, Object>> getUserRoles(String cUserId);


    /**
     * @Description 根据角色主键获取职责
     * @author binbin
     * @date 2018/12/28 16:57
     */
    List<Map<String, Object>> getResponsibilitiesByRolePk(String rolePk);

    /**
     * @Description 根据职责主键获取功能节点
     * @author binbin
     * @date 2018/12/28 16:57
     */
    List<Map<String, Object>> getFunctionsByRespPk(String respPk);

    /**
     * @Description 根据用户编码获取用户菜单
     * @author binbin
     * @date 2018/12/28 16:01
     */
    Map<String, Object> getAuthMenus(String userCode);

}
