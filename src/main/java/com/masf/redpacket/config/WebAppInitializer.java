package com.masf.redpacket.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.MultipartConfigElement;

/**
 * @program: com.masf.redpacket.config
 * @author: mashifei
 * @create: 2019-06-05-15
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * spring IOC 环境配置
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    /**
     * DispatcherServlet 环境配置
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }


    /**
     * DispatcherServlet 拦截请求配置
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"*.do"};
    }

    @Override
    protected void customizeRegistration(Dynamic dynamic){
        //配置上传文件路径
        String filepath = "e:/mvc/uploads";

        //5MB
        Long singleMax =  (long) (5*Math.pow(2,20));

        //10MB
        Long totalMax = (long)(10*Math.pow(2,20));

        //设置文件上传配置
        dynamic.setMultipartConfig(new MultipartConfigElement(filepath,singleMax,totalMax,0));
    }
}
