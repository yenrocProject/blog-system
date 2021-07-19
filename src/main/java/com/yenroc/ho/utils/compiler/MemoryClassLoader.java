package com.yenroc.ho.utils.compiler;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

class MemoryClassLoader extends URLClassLoader {
    Map<String, byte[]> classBytes = new HashMap();

    public MemoryClassLoader(Map<String, byte[]> classBytes) {
        super(new URL[0], MemoryClassLoader.class.getClassLoader());
        this.classBytes.putAll(classBytes);
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] buf = (byte[])this.classBytes.get(name);
        if (buf == null) {
            return super.findClass(name);
        } else {
            this.classBytes.remove(name);
            return this.defineClass(name, buf, 0, buf.length);
        }
    }
}
