package com.yenroc.ho.runner;

import com.yenroc.ho.mapper.sys.SysTableConfigDao;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author： heyanpeng
 * @date： 2021/7/20
 */
public class MyMapperAutoConfiguredMyBatisRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanFactoryAware {

    private ResourceLoader resourceLoader;
    private BeanFactory beanFactory;

    private SysTableConfigDao sysTableConfigDao;

    /**
     * @Author haien
     * @Description 注册bean，但我们并不知道需要注册哪些bean，所以需要借助
     * ClassPathBeanDefinitionScanner扫描器，扫描我们需要注册的bean
     * @Date 2019/6/11
     * @Param [annotationMetadata, beanDefinitionRegistry]
     * @return void
     **/
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
                                        BeanDefinitionRegistry registry) {
        System.out.println("registerBeanDefinitions===================================================");

        MyClasssPathBeanDefinitionScanner scanner=
                new MyClasssPathBeanDefinitionScanner(registry);
        scanner.setResourceLoader(resourceLoader);
        scanner.registerFilters();
        scanner.doScan("com.yenroc.ho.mapper.dynamic");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader=resourceLoader;
    }

}
