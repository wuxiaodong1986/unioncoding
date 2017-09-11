package com.unioncoding.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器配置
 * Created by 吴晓冬 on 2017/9/9.
 */
@Configuration
public class UnionFilterConfig
{
    @Bean
    public FilterRegistrationBean corsFilter()
    {
        PageHistoryFilter pageHistoryFilter = new PageHistoryFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("pageHistoryFilter");
        registrationBean.setFilter(pageHistoryFilter);
        registrationBean.setOrder(1);

        List<String> urlList = new ArrayList<>();
        urlList.add("/*");
        registrationBean.setUrlPatterns(urlList);

        return registrationBean;
    }
}
