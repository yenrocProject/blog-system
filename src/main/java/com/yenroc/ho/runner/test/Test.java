package com.yenroc.ho.runner.test;


import com.yenroc.ho.utils.compiler.JavaStringCompiler;

import java.io.IOException;
import java.util.Map;

/**
 * @author： heyanpeng
 * @date： 2021/7/19
 */
public class Test {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        JavaStringCompiler compiler = new JavaStringCompiler();
        Map<String, byte[]> results = compiler.compile("Demo.java", JAVA_SOURCE_CODE);
        Class<?> clazz = compiler.loadClass("com.yenroc.ho.runner.Demo", results);
        // try instance:
        Object user =  clazz.newInstance();
        System.out.println(user);
    }

    static final String JAVA_SOURCE_CODE = "package com.yenroc.ho.runner;\n" +
            "\n" +
            "import javax.persistence.Column;\n" +
            "import javax.persistence.Id;\n" +
            "import javax.persistence.Table;\n" +
            "import java.io.Serializable;\n" +
            "import java.math.BigDecimal;\n" +
            "import java.util.Date;\n" +
            "\n" +
            "\n" +
            "@Table(name=\"demo\")\n" +
            "public class Demo extends com.yenroc.ho.mapper.entity.BaseEntity implements Serializable {\n" +
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
            "    /**\n" +
            "     * 姓名\n" +
            "     */\n" +
            "    @Column(name = \"name\")\n" +
            "    private String name;\n" +
            "\n" +
            "    /**\n" +
            "     * 年龄\n" +
            "     */\n" +
            "    @Column(name = \"age\")\n" +
            "    private Integer age;\n" +
            "\n" +
            "    /**\n" +
            "     * 工资\n" +
            "     */\n" +
            "    @Column(name = \"salary\")\n" +
            "    private BigDecimal salary;\n" +
            "\n" +
            "    /**\n" +
            "     * 出生日期\n" +
            "     */\n" +
            "    @Column(name = \"birthDay\")\n" +
            "    private Date birthDay;\n" +
            "\n" +
            "\n" +
            "    public String getId() {\n" +
            "        return id;\n" +
            "    }\n" +
            "\n" +
            "    public void setId(String id) {\n" +
            "        this.id = id;\n" +
            "    }\n" +
            "\n" +
            "    public String getName() {\n" +
            "        return name;\n" +
            "    }\n" +
            "\n" +
            "    public void setName(String name) {\n" +
            "        this.name = name;\n" +
            "    }\n" +
            "\n" +
            "    public Integer getAge() {\n" +
            "        return age;\n" +
            "    }\n" +
            "\n" +
            "    public void setAge(Integer age) {\n" +
            "        this.age = age;\n" +
            "    }\n" +
            "\n" +
            "    public BigDecimal getSalary() {\n" +
            "        return salary;\n" +
            "    }\n" +
            "\n" +
            "    public void setSalary(BigDecimal salary) {\n" +
            "        this.salary = salary;\n" +
            "    }\n" +
            "\n" +
            "    public Date getBirthDay() {\n" +
            "        return birthDay;\n" +
            "    }\n" +
            "\n" +
            "    public void setBirthDay(Date birthDay) {\n" +
            "        this.birthDay = birthDay;\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "}\n";
}
