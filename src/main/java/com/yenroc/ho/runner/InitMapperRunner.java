package com.yenroc.ho.runner;

import com.yenroc.ho.common.context.SpringContextHolder;
import com.yenroc.ho.mapper.sys.SysTableConfigDao;
import com.yenroc.ho.utils.compiler.JavaStringCompiler;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author： heyanpeng
 * @date： 2021/7/19
 * 参考： https://www.cnblogs.com/yanjie-java/p/7940929.html
 *       https://github.com/michaelliao/compiler
 */
@Component
@Order(value=1)
public class InitMapperRunner extends ClassPathScanningCandidateComponentProvider implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(InitMapperRunner.class);

    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始初始化动态Mapper接口。。");
        JavaStringCompiler compiler = new JavaStringCompiler();
        Map<String, byte[]> results = compiler.compile("TestDemoDao.java", JAVA_SOURCE_CODE2);
        Class<?> clazz = compiler.loadClass("com.yenroc.ho.mapper.TestDemoDao", results);
        FileOutputStream fos = new FileOutputStream("D:\\iProject\\iGithub\\blog-system\\target\\classes\\com\\yenroc\\ho\\mapper\\TestDemoDao.class");
        byte[] bytes = results.get("com.yenroc.ho.mapper.TestDemoDao");
        fos.write(bytes,0,bytes.length);
        fos.close();

        Resource resource = new FileSystemResourceLoader().getResource("D:\\iProject\\iGithub\\blog-system\\target\\classes\\com\\yenroc\\ho\\mapper\\TestDemoDao.class");
        MetadataReader metadataReader =
                this.getMetadataReaderFactory().getMetadataReader(resource);
        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
        sbd.setResource(resource);
        sbd.setSource(resource);
        sbd.setScope("singleton");
        AnnotationConfigUtils.processCommonDefinitionAnnotations(sbd);
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(sbd, "testDemoDao");

        GenericBeanDefinition definition = (GenericBeanDefinition)definitionHolder.getBeanDefinition();
        definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
        definition.setBeanClass(MapperFactoryBean.class);
        definition.getPropertyValues().add("addToConfig", true);
        definition.getPropertyValues().add("sqlSessionFactory", this.sqlSessionFactory);
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, defaultListableBeanFactory);



    }
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        log.info("开始初始化动态Mapper接口。。");
////        BaseMapper sysTableConfigDao = SpringContextHolder.getBean("sysTableConfigDao");
////        System.out.println("result = " + sysTableConfigDao.selectAll());
//
//
//        JavaStringCompiler compiler = new JavaStringCompiler();
////        Map<String, byte[]> entityResults = compiler.compile("DelDataEntity.java", JAVA_ENTITY_CODE);
////        FileOutputStream fos = new FileOutputStream("E:\\yenroc\\Desktop\\DelDataEntity.class");
////        byte[] bytes = entityResults.get("com.yenroc.ho.mapper.sys.entity.DelDataEntity");
////        fos.write(bytes,0,bytes.length);
////        fos.close();
////        Class<?> entityClazz = compiler.loadClass("com.yenroc.ho.mapper.sys.entity.DelDataEntity", entityResults);
//
//
//        Map<String, byte[]> results = compiler.compile("TestDemoDao.java", JAVA_SOURCE_CODE2);
//        Class<?> clazz = compiler.loadClass("com.yenroc.ho.mapper.TestDemoDao", results);
//
//
//        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
//        builder.addPropertyValue("annotationClass", Mapper.class);
//        builder.addPropertyValue("processPropertyPlaceHolders", true);
//
//        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
//        String beanClassName = definition.getBeanClassName();
//
//        // the mapper interface is the original class of the bean
//        // but, the actual class of the bean is MapperFactoryBean
//        definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
//        //将Bean的定义信息修改为  MapperFactoryBean.class
//        definition.setBeanClass(MapperFactoryBean.class);
//
////        definition.getPropertyValues().add("addToConfig", this.addToConfig);
////
////        boolean explicitFactoryUsed = false;
////        if (StringUtils.hasText(this.sqlSessionFactoryBeanName)) {
////            definition.getPropertyValues().add("sqlSessionFactory",
////                    new RuntimeBeanReference(this.sqlSessionFactoryBeanName));
////            explicitFactoryUsed = true;
////        } else if (this.sqlSessionFactory != null) {
////            definition.getPropertyValues().add("sqlSessionFactory", this.sqlSessionFactory);
////            explicitFactoryUsed = true;
////        }
////
////        if (StringUtils.hasText(this.sqlSessionTemplateBeanName)) {
////            definition.getPropertyValues().add("sqlSessionTemplate",
////                    new RuntimeBeanReference(this.sqlSessionTemplateBeanName));
////            explicitFactoryUsed = true;
////        } else if (this.sqlSessionTemplate != null) {
////            definition.getPropertyValues().add("sqlSessionTemplate", this.sqlSessionTemplate);
////            explicitFactoryUsed = true;
////        }
////
////        if (!explicitFactoryUsed) {
////            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
////        }
////        definition.setLazyInit(lazyInitialization);
//
////        definition.getPropertyValues().add("sqlSessionFactory", SpringContextHolder.getBean("sqlSessionFactory"));
////        defaultListableBeanFactory.registerBeanDefinition("testDemoDao", definition);
//
//        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder( definition,"testDemoDao");
//        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, defaultListableBeanFactory);
//
//        BaseMapper testDemoDao = SpringContextHolder.getBean("testDemoDao");
//        System.out.println("result = " + testDemoDao.selectAll());
//
//    }

    private static String JAVA_SOURCE_CODE2 = "package com.yenroc.ho.mapper;\n" +
            "\n" +
            "import com.yenroc.ho.mapper.entity.TestDemo;\n" +
            "import org.apache.ibatis.annotations.Mapper;\n" +
            "import tk.mybatis.mapper.common.BaseMapper;\n" +
            "\n" +
            "@Mapper\n" +
            "public interface TestDemoDao extends BaseMapper<TestDemo> {\n" +
            "\n" +
            "}";


    private static String JAVA_SOURCE_CODE = "package com.yenroc.ho.mapper;\n" +
            "\n" +
            "import com.yenroc.ho.mapper.sys.entity.DelDataEntity;\n" +
            "import tk.mybatis.mapper.common.BaseMapper;\n" +
            "\n" +
            "//@Mapper\n" +
            "public interface DelDataEntityDao extends BaseMapper<DelDataEntity> {\n" +
            "\n" +
            "}\n";

    private static String JAVA_ENTITY_CODE = "package com.yenroc.ho.mapper.sys.entity;\n" +
            "\n" +
            "import com.yenroc.ho.mapper.entity.BaseEntity;\n" +
            "\n" +
            "import javax.persistence.Column;\n" +
            "import javax.persistence.Id;\n" +
            "import javax.persistence.Table;\n" +
            "import java.io.Serializable;\n" +
            "import java.util.Objects;\n" +
            "\n" +
            "@Table(name = \"del_data\")\n" +
            "public class DelDataEntity extends BaseEntity implements Serializable {\n" +
            "\n" +
            "    private static final long serialVersionUID = -7245386871063697240L;\n" +
            "\n" +
            "    /**\n" +
            "     * 主键id\n" +
            "     */\n" +
            "    @Id\n" +
            "    @Column(name = \"id\")\n" +
            "    private String id;\n" +
            "\n" +
            "    @Column(name = \"table_name\")\n" +
            "    private String tableName;\n" +
            "\n" +
            "    @Column(name = \"data_info\", columnDefinition = \"json\")\n" +
            "    private String dataInfo;\n" +
            "\n" +
            "    public String getId() {\n" +
            "        return id;\n" +
            "    }\n" +
            "\n" +
            "    public void setId(String id) {\n" +
            "        this.id = id;\n" +
            "    }\n" +
            "\n" +
            "    public String getTableName() {\n" +
            "        return tableName;\n" +
            "    }\n" +
            "\n" +
            "    public void setTableName(String tableName) {\n" +
            "        this.tableName = tableName;\n" +
            "    }\n" +
            "\n" +
            "    public String getDataInfo() {\n" +
            "        return dataInfo;\n" +
            "    }\n" +
            "\n" +
            "    public void setDataInfo(String dataInfo) {\n" +
            "        this.dataInfo = dataInfo;\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public boolean equals(Object o) {\n" +
            "        if (this == o) return true;\n" +
            "        if (o == null || getClass() != o.getClass()) return false;\n" +
            "        if (!super.equals(o)) return false;\n" +
            "        DelDataEntity delData = (DelDataEntity) o;\n" +
            "        return Objects.equals(id, delData.id) &&\n" +
            "                Objects.equals(tableName, delData.tableName) &&\n" +
            "                Objects.equals(dataInfo, delData.dataInfo);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public int hashCode() {\n" +
            "        return Objects.hash(super.hashCode(), id, tableName, dataInfo);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public String toString() {\n" +
            "        return \"DelData{\" +\n" +
            "                \"id='\" + id + '\\'' +\n" +
            "                \", tableName='\" + tableName + '\\'' +\n" +
            "                \", dataInfo='\" + dataInfo + '\\'' +\n" +
            "                '}';\n" +
            "    }\n" +
            "}\n";


}
