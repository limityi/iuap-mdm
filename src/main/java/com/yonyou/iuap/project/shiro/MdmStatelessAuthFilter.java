package com.yonyou.iuap.project.shiro;


import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by XiongYi on 2018/11/29.
 *
 */
public class MdmStatelessAuthFilter extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request=(HttpServletRequest)servletRequest;
       /* if(isAjax(servletRequest)){
            return true;
        }*/

        HttpServletResponse response=(HttpServletResponse)servletResponse;
        System.out.println(request.getContextPath());
        System.out.println(request.getServletPath());
        if(request.getServletPath().equals("/Station/list")){
            response.sendRedirect("/portal");
            return false;
        }else{
            return true;
        }

    }

    private boolean isAjax(ServletRequest request) {
        boolean isAjax = false;
        if(request instanceof HttpServletRequest) {
            HttpServletRequest rq = (HttpServletRequest)request;
            String requestType = rq.getHeader("X-Requested-With");
            if(requestType != null && "XMLHttpRequest".equals(requestType)) {
                isAjax = true;
            }
        }

        return isAjax;
    }
}
