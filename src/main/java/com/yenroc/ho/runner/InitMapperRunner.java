package com.yenroc.ho.runner;

import com.yenroc.ho.common.context.SpringContextHolder;
import com.yenroc.ho.utils.compiler.JavaStringCompiler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;

import java.io.FileOutputStream;
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

    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

    @Autowired
    private Environment env;

//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        log.info("开始初始化动态Mapper接口。。");
//        JavaStringCompiler compiler = new JavaStringCompiler();
//        Map<String, byte[]> results = compiler.compile("TestDemoDao.java", JAVA_SOURCE_CODE2);
//        Class<?> clazz = compiler.loadClass("com.yenroc.ho.mapper.dynamic.TestDemoDao", results);
//        FileOutputStream fos = new FileOutputStream("E:\\Git源码\\github\\blog-system\\target\\classes\\com\\yenroc\\ho\\mapper\\dynamic\\TestDemoDao.class");
//        byte[] bytes = results.get("com.yenroc.ho.mapper.dynamic.TestDemoDao");
//        fos.write(bytes,0,bytes.length);
//        fos.close();

//        Resource resource = new FileSystemResourceLoader().getResource("E:\\Git源码\\github\\blog-system\\target\\classes\\com\\yenroc\\ho\\mapper\\TestDemoDao.class");
//        MetadataReader metadataReader =
//                this.getMetadataReaderFactory().getMetadataReader(resource);
//        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
//        sbd.setResource(resource);
//        sbd.setSource(resource);
//        sbd.setScope("singleton");
//        AnnotationConfigUtils.processCommonDefinitionAnnotations(sbd);
//        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(sbd, "testDemoDao");
//
//        GenericBeanDefinition definition = (GenericBeanDefinition)definitionHolder.getBeanDefinition();
//        definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
//        definition.setBeanClass(MapperFactoryBean.class);
//        definition.getPropertyValues().add("addToConfig", true);
//        definition.getPropertyValues().add("sqlSessionFactory", this.sqlSessionFactory);
//
//        MapperScannerRegistrar autoConfiguredMapperScannerRegistrar = new MapperScannerRegistrar();
//        autoConfiguredMapperScannerRegistrar.setEnvironment(env);
//        autoConfiguredMapperScannerRegistrar.setResourceLoader(resourceLoader);
//        autoConfiguredMapperScannerRegistrar.registerBeanDefinitions(new StandardAnnotationMetadata(clazz), defaultListableBeanFactory);
//        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, defaultListableBeanFactory);

//    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始初始化动态Mapper接口。。");
        JavaStringCompiler compiler = new JavaStringCompiler();
        Map<String, byte[]> results = compiler.compile("DynamicTestDao.java", JAVA_SOURCE_CODE2);
        Class<?> clazz = compiler.loadClass("com.yenroc.ho.mapper.dynamic.DynamicTestDao", results);
        FileOutputStream fos = new FileOutputStream("E:\\Git源码\\github\\blog-system\\target\\classes\\com\\yenroc\\ho\\mapper\\dynamic\\DynamicTestDao.class");
        byte[] bytes = results.get("com.yenroc.ho.mapper.dynamic.DynamicTestDao");
        fos.write(bytes,0,bytes.length);
        fos.close();

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);

        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        String beanClassName = definition.getBeanClassName();

        definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
        definition.getPropertyValues().add("sqlSessionFactory", sqlSessionFactory);
        definition.getPropertyValues().add("sqlSessionTemplate", sqlSessionTemplate);

        //将Bean的定义信息修改为  MapperFactoryBean.class
        definition.setBeanClass(MapperFactoryBean.class);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        defaultListableBeanFactory.registerBeanDefinition(clazz.getSimpleName(), definition);
        BaseMapper dynamicTestDao = SpringContextHolder.getBean("DynamicTestDao");
        System.out.println("dynamicTestDao = " + dynamicTestDao);
        System.out.println("result = " + dynamicTestDao.selectAll());

    }

    private static String JAVA_SOURCE_CODE2 = "package com.yenroc.ho.mapper.dynamic;\n" +
            "\n" +
            "import com.yenroc.ho.mapper.dynamic.entity.TestDemo;\n" +
            "import org.apache.ibatis.annotations.Mapper;\n" +
            "import tk.mybatis.mapper.common.BaseMapper;\n" +
            "\n" +
            "/**\n" +
            " * @author： heyanpeng\n" +
            " * @date： 2021/7/20\n" +
            " */\n" +
            "@Mapper\n" +
            "public interface DynamicTestDao extends BaseMapper<TestDemo> {\n" +
            "}\n";


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
