package com.yonyou.iuap.system.filter;

import com.yonyou.iuap.context.InvocationInfoProxy;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by zengxs on 2016/12/1.
 */
public class InvocationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        InvocationInfoProxy.setUserid("test");
        InvocationInfoProxy.setSysid("sysid");
        InvocationInfoProxy.setUsername("admin");
        InvocationInfoProxy.setLocale("zh_CN");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
