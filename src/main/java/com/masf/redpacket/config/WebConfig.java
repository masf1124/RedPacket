package com.masf.redpacket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: com.masf.redpacket.config
 * @author: mashifei
 * @create: 2019-06-05-16
 */

@Configuration
//定义springMVC 包扫描
@ComponentScan(value = "com.masf.redpacket",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class)})
//启动web MVC
@EnableWebMvc
public class WebConfig {

    /**
     * 通过注解@Bean 初始化视图解析器
     * @return
     */
    @Bean(name = "internalResourceViewResolver")
    public ViewResolver initViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }


    /**
     *
     * @return
     */
    @Bean(name = "requestMappingHandlerAdapter")
    public HandlerAdapter initRequestMappingHandlerAdapter(){
        //创建RequestMappingHandlerAdapter适配器
        RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();

        ///HTTP JSON 转换器
        MappingJackson2HttpMessageConverter jsonConverter =
                new MappingJackson2HttpMessageConverter();

        //MappingJackson2HttpMessageConverter接收JSON类型的消息转换
        MediaType mediaType  = MediaType.APPLICATION_JSON_UTF8;
        List<MediaType> mediaTypeLists = new ArrayList<>();
        mediaTypeLists.add(mediaType);

        //加入转换器支持的类型
        jsonConverter.setSupportedMediaTypes(mediaTypeLists);
        //往适配器中加入json转换器
        handlerAdapter.getMessageConverters().add(jsonConverter);
        return handlerAdapter;
    }
}
