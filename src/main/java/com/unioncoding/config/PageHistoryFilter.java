package com.unioncoding.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 页面访问历史过滤器
 * Created by 吴晓冬 on 2017/9/9.
 */
public class PageHistoryFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpSession session = request.getSession();

        List<String> pageHistory = (List<String>)session.getAttribute("pageHistory");

        if (null == pageHistory)
        {
            pageHistory = new ArrayList<>();
        }

        String url = request.getServletPath();

        if (!url.contains(".") && "GET".equals(request.getMethod()) && !url.endsWith("xls"))
        {
            pageHistory.add(0, url);
        }

        session.setAttribute("pageHistory", pageHistory);

        try
        {
            chain.doFilter(servletRequest, servletResponse);
        }
        catch (Throwable e)
        {
            pageHistory.remove(0);
        }
    }

    @Override
    public void destroy()
    {

    }
}
