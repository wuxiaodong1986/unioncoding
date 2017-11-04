package com.unioncoding.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * mvc配置
 * Created by 吴晓冬 on 2017/10/16.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter
{
    /**
     * 添加GsonHttpMessageConverter至mvc处理类列表中
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new MyExclusionStrategy())
                .create();

        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(gson);
        converters.add(gsonHttpMessageConverter);
    }

    //设置使用Gson将对象解析成json时跳过的规则
    public class MyExclusionStrategy implements ExclusionStrategy
    {
        /**
         * 跳过的属性
         */
        @Override
        public boolean shouldSkipField(FieldAttributes f)
        {
            //包含JsonIgnore注解的属性跳过
            return f.getAnnotation(JsonIgnore.class) != null;
        }

        /**
         * 跳过的类
         */
        @Override
        public boolean shouldSkipClass(Class<?> clazz)
        {
            return false;
        }
    }
}
