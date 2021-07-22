package com.yenroc.ho.runner;

import com.yenroc.ho.common.context.SpringContextHolder;
import com.yenroc.ho.mapper.base.BlogCommonMapper;
import com.yenroc.ho.utils.compiler.JavaStringCompiler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.spring.mapper.MapperFactoryBean;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author： heyanpeng
 * @date： 2021/7/19
 * 参考： https://www.cnblogs.com/yanjie-java/p/7940929.html
 *       https://github.com/michaelliao/compiler
 */
@Component
@Order(value=1)
public class InitMapperRunner extends ClassPathScanningCandidateComponentProvider implements ApplicationRunner{

    private static final Logger log = LoggerFactory.getLogger(InitMapperRunner.class);

    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    static JavaStringCompiler compiler = new JavaStringCompiler();

    MapperHelper mapperHelper = new MapperHelper();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始初始化动态Mapper接口。。");
        long start = System.currentTimeMillis();
        String[] classNames = new String[]{"Atest1","Atest2","BTest1","Ctest1","Ctest2","DTest1","Etest1","Etest2","FTest1","Gtest1","Gtest2","HTest1"};
        for (int i = 0; i < classNames.length; i++) {
            Class clazz = createClass(classNames[i]);
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            String beanClassName = definition.getBeanClassName();

            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
            definition.getPropertyValues().add("sqlSessionFactory", sqlSessionFactory);
            definition.getPropertyValues().add("sqlSessionTemplate", sqlSessionTemplate);
            definition.getPropertyValues().add("mapperHelper", mapperHelper);
            //将Bean的定义信息修改为  MapperFactoryBean.class
            definition.setBeanClass(MapperFactoryBean.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            defaultListableBeanFactory.registerBeanDefinition(clazz.getSimpleName(), definition);
            BlogCommonMapper DynamicTestDao = SpringContextHolder.getBean(classNames[i]);
            System.out.println(classNames[i] +"=" + DynamicTestDao);
            System.out.println(classNames[i] + "result=" + DynamicTestDao.selectAll());
        }
        long end = System.currentTimeMillis();
        System.out.println("加载完成耗时="+(end-start));
    }

    private static Class createClass(String className) throws IOException, ClassNotFoundException {
        StopWatch watch = new StopWatch();
        watch.start("getProperty");
        String classDirPath = System.getProperty("user.dir") +  "\\temp\\classes\\"+className+".class";
        watch.stop();
        watch.start("getDaoStr");
        String daoStr = getDaoStr(className);
        watch.stop();
        watch.start("compile");
        Map<String, byte[]> results = compiler.compile(className+".java", daoStr);
        watch.stop();
        watch.start("FileOutputStream");
        FileOutputStream fos = new FileOutputStream(classDirPath);
        watch.stop();
        watch.start("write");
        byte[] bytes = results.get("com.yenroc.ho.mapper.dynamic." + className);
        fos.write(bytes,0,bytes.length);
        fos.close();
        watch.stop();
        System.out.println(watch.prettyPrint());
        return compiler.loadClass("com.yenroc.ho.mapper.dynamic." + className, results);
    }

    private static String getDaoStr(String className) {
        return  "package com.yenroc.ho.mapper.dynamic;\n" +
                "\n" +
                "import com.yenroc.ho.mapper.dynamic.entity.TestDemo;\n" +
                "import com.yenroc.ho.mapper.common.BlogCommonMapper;\n" +
                "import org.apache.ibatis.annotations.Mapper;\n" +
                "\n" +
                "/**\n" +
                " * @author： heyanpeng\n" +
                " * @date： 2021/7/20\n" +
                " */\n" +
                "@Mapper\n" +
                "public interface "+className+" extends BlogCommonMapper<TestDemo> {\n" +
                "}\n";
    }

}
