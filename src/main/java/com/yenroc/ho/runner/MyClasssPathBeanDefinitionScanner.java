package com.yenroc.ho.runner;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author： heyanpeng
 * @date： 2021/7/20
 */
public class MyClasssPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public MyClasssPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    /**
     * @Author haien
     * @Description 注册条件过滤器，将@Mapper注解加入允许扫描的过滤器中，
     * 如果加入排除扫描的过滤器集合excludeFilter中，则不会扫描
     * @Date 2019/6/11
     * @Param []
     * @return void
     **/
    protected void registerFilters(){
        addIncludeFilter(new AnnotationTypeFilter(Mapper.class));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        return super.doScan(basePackages);
    }
}
