package com.daowen;

import com.daowen.util.BeansUtil;


import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.JspConfigDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.TaglibDescriptorImpl;
import org.jfree.chart.servlet.DisplayChart;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.descriptor.TaglibDescriptor;
import java.text.MessageFormat;
import java.util.Collections;

@SpringBootApplication
@MapperScan(basePackages= {"com.daowen.mapper"})
@Configuration
@ServletComponentScan(basePackages = "com.daowen.configuration")
@Slf4j
public class MainApplication extends SpringBootServletInitializer {


    public static void main(String[] agrs){

        ApplicationContext applicationContext=SpringApplication.run(MainApplication.class,agrs);
        BeansUtil.setApplicationContext(applicationContext);
        log.info(MessageFormat.format("运行网址点击:http://localhost:8080/{0}/index.html","diancan"));

    }






}
