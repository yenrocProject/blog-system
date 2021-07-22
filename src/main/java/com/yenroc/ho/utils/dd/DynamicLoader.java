package com.yenroc.ho.utils.dd;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author： heyanpeng
 * @date： 2021/7/22
 */
public class DynamicLoader {

    /**
     * 通过类名和其代码（Java代码字符串），编译得到字节码，返回类名及其对应类的字节码，封装于Map中，值得注意的是，
     * 平常类中就编译出来的字节码只有一个类，但是考虑到内部类的情况， 会出现很多个类名及其字节码，所以用Map封装方便。
     *
     * @param javaName 类名
     * @param javaSrc  Java源码
     * @return map
     */
    public static Map<String, byte[]> compile(String javaName, String javaSrc) {
        // 调用java编译器接口
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);

        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {

            @SuppressWarnings("static-access")
            JavaFileObject javaFileObject = manager.makeStringSource(javaName, javaSrc);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager,null, null, null, Arrays.asList(javaFileObject));
            if (task.call()) {
                return manager.getClassBytes();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 先根据类名在内存中查找是否已存在该类，若不存在则调用 URLClassLoader的 defineClass方法加载该类
     * URLClassLoader的具体作用就是将class文件加载到jvm虚拟机中去
     *
     * @author Administrator
     *
     */
    public static class MemoryClassLoader extends URLClassLoader {
        static Map<String, byte[]> classBytes = new HashMap<String, byte[]>();

        public MemoryClassLoader(Map<String, byte[]> classBytes) {
            super(new URL[0], MemoryClassLoader.class.getClassLoader());
            if (classBytes != null) {
                this.classBytes.putAll(classBytes);
            }
        }

        @Override
        protected Class<?> findClass(String name)
                throws ClassNotFoundException {
            System.out.println("findClass..."+name);
            byte[] buf = classBytes.get(name);
            if (buf == null) {
                return super.findClass(name);
            }
//            classBytes.remove(name);
            return defineClass(name, buf, 0, buf.length);
        }

        @Override
        public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            Class<?> aClass = super.loadClass(name, resolve);
            System.out.println("loadClass......"+name);
            return aClass;
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            Class<?> aClass = super.loadClass(name);
            System.out.println("loadClass..."+name);
            return aClass;
        }
    }

}
