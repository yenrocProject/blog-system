package com.yenroc.ho.utils.compiler;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

/**
 * 		<dependency>
 * 			<groupId>com.itranswarp</groupId>
 * 			<artifactId>compiler</artifactId>
 * 			<version>1.0</version>
 * 		</dependency>
 */
public class JavaStringCompiler {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager stdManager;

    public JavaStringCompiler() {
        this.stdManager = this.compiler.getStandardFileManager(null, null, null);
    }

    public Map<String, byte[]> compile(String fileName, String source) throws IOException {
        MemoryJavaFileManager manager = new MemoryJavaFileManager(this.stdManager);
        Throwable var4 = null;

        Map var8;
        try {
            JavaFileObject javaFileObject = manager.makeStringSource(fileName, source);
            CompilationTask task = this.compiler.getTask((Writer)null, manager, null, null, null, Arrays.asList(javaFileObject));
            Boolean result = task.call();
            if (result == null || !result) {
                throw new RuntimeException("Compilation failed.");
            }

            var8 = manager.getClassBytes();
        } catch (Throwable var17) {
            var4 = var17;
            throw var17;
        } finally {
            if (manager != null) {
                if (var4 != null) {
                    try {
                        manager.close();
                    } catch (Throwable var16) {
                        var4.addSuppressed(var16);
                    }
                } else {
                    manager.close();
                }
            }

        }

        return var8;
    }

    public Class<?> loadClass(String name, Map<String, byte[]> classBytes) throws ClassNotFoundException, IOException {
        MemoryClassLoader classLoader = new MemoryClassLoader(classBytes);
        Throwable var4 = null;

        Class var5;
        try {
            var5 = classLoader.loadClass(name);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (classLoader != null) {
                if (var4 != null) {
                    try {
                        classLoader.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    classLoader.close();
                }
            }

        }

        return var5;
    }
}
