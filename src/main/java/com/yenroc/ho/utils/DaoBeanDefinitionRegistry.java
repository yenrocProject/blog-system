//package com.yenroc.ho.utils;
//
//import com.yenroc.ho.common.context.SpringContextHolder;
//import org.mybatis.spring.mapper.MapperFactoryBean;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.ResourceLoaderAware;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternUtils;
//import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
//import org.springframework.core.type.classreading.MetadataReader;
//import org.springframework.core.type.classreading.MetadataReaderFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ClassUtils;
//
//import java.io.IOException;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
///**
// * @author： heyanpeng
// * @date： 2021/7/19
// */
//@Component
//public class DaoBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor, ResourceLoaderAware, ApplicationContextAware {
//
//    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
//
//    private ResourcePatternResolver resourcePatternResolver;
//
//    private ApplicationContext applicationContext;
//
//    private MetadataReaderFactory metadataReaderFactory;
//
//    private Environment getEnvironment() {
//        return applicationContext.getEnvironment();
//    }
//
//    /**
//     * 根据包路径获取包及子包下的所有类
//     * @param basePackage basePackage
//     * @return Set<Class<?>> Set<Class<?>>
//     */
//    private Set<Class<?>> scannerPackages(String basePackage) {
//        Set<Class<?>> set = new LinkedHashSet<>();
//        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
//                resolveBasePackage(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;
//        try {
//            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
//            for (Resource resource : resources) {
//                if (resource.isReadable()) {
//                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
//                    String className = metadataReader.getClassMetadata().getClassName();
//                    Class<?> clazz;
//                    try {
//                        clazz = Class.forName(className);
//                        set.add(clazz);
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return set;
//    }
//
//    protected String resolveBasePackage(String basePackage) {
//        return ClassUtils.convertClassNameToResourcePath(this.getEnvironment().resolveRequiredPlaceholders(basePackage));
//    }
//
//    @Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
//        //这里一般我们是通过反射获取需要代理的接口的clazz列表
//        //比如判断包下面的类，或者通过某注解标注的类等等
//        Set<Class<?>> beanClazzs = scannerPackages("com.yenroc.ho.mapper");
//        for (Class beanClazz : beanClazzs) {
//            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
//            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
//
//            //在这里，我们可以给该对象的属性注入对应的实例。
//            //比如mybatis，就在这里注入了dataSource和sqlSessionFactory，
//            // 注意，如果采用definition.getPropertyValues()方式的话，
//            // 类似definition.getPropertyValues().add("interfaceType", beanClazz);
//            // 则要求在FactoryBean（本应用中即ServiceFactory）提供setter方法，否则会注入失败
//            // 如果采用definition.getConstructorArgumentValues()，
//            // 则FactoryBean中需要提供包含该属性的构造方法，否则会注入失败
//            definition.setScope("singleton");
//            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClazz);
//
//            //注意，这里的BeanClass是生成Bean实例的工厂，不是Bean本身。
//            // FactoryBean是一种特殊的Bean，其返回的对象不是指定类的一个实例，
//            // 其返回的是该工厂Bean的getObject方法所返回的对象。
//            definition.setBeanClass(MapperFactoryBean.class);
//
//            //这里采用的是byType方式注入，类似的还有byName等
//            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
//
//            beanDefinitionRegistry.registerBeanDefinition(beanClazz.getSimpleName(), definition);
//        }
//    }
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//
//    @Override
//    public void setResourceLoader(ResourceLoader resourceLoader) {
//        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
//        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
//    }
//}
