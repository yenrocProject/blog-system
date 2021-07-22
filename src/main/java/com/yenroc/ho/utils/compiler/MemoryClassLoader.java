package com.yenroc.ho.utils.compiler;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class MemoryClassLoader extends URLClassLoader {
    static Map<String, byte[]> classBytes = new HashMap();

    public MemoryClassLoader() {
        super(new URL[0], MemoryClassLoader.class.getClassLoader());
    }

    public MemoryClassLoader(Map<String, byte[]> classBytes) {
        super(new URL[0], MemoryClassLoader.class.getClassLoader());
        this.classBytes.putAll(classBytes);
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("findClass....");
        byte[] buf = (byte[])this.classBytes.get(name);
        if (buf == null) {
            return super.findClass(name);
        } else {
            this.classBytes.remove(name);
            return this.defineClass(name, buf, 0, buf.length);
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> aClass = super.loadClass(name);
        System.out.println("loadClass...");
        return aClass;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> aClass = super.loadClass(name, resolve);
        System.out.println("loadClass......");
        return aClass;
    }
}
