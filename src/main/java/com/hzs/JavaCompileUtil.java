package com.hzs;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class JavaCompileUtil {

    /**
     * 编译输出路径 idea 默认输出路径
     */
    public static final String DEST_OUTPUT = System.getProperty("user.dir") + File.separator + "target" + File.separator + "classes";

    public static final String PACKAGE_NAME_PREFIX = "classDemo._";

    public static Pattern pattern = Pattern.compile("^package\\s+\\S+;");

    public static Object execJava(String path, String methodName, Map<String,Object> args) {

        String javaName = FileUtil.mainName(path);
        File file = new File(path);
        String packageName = PACKAGE_NAME_PREFIX + FileUtil.mainName(FileUtil.getParent(path, 1));

        // 标准化包名
        amendPackageName(file, packageName);

        List<String> options = new ArrayList<>();
        options.add("-d");
        options.add(DEST_OUTPUT);

        // 调用jdk tools 编译.class文件
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8);
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(file);
        Boolean call = compiler.getTask(null, fileManager, null, options, null, compilationUnits).call();

        if (call) {
            try {
                // 加载 class 并调用方法
                // 目前仅支持传入map类型参数
                return execMethod(packageName + "." + javaName, methodName, new Object[]{args});
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 修改包名
     *
     * @param file        文件
     * @param packageName 包名
     */
    private static void amendPackageName(File file, String packageName) {
        String code = FileUtil.readString(file, StandardCharsets.UTF_8);
        Matcher matcher = pattern.matcher(code);
        boolean hasPackage = matcher.find();
        if (!hasPackage) {
            code = "package " + packageName + ";" + code;
        } else {
            code = matcher.replaceFirst("package " + packageName + ";");
        }
        FileUtil.writeString(code, file, StandardCharsets.UTF_8);
    }


    /**
     * 执行类方法
     *
     * @param path       路径
     * @param methodName 方法名称
     * @param args       参数
     * @return {@link Object} 方法返回参数
     */
    public static Object execMethod(String path, String methodName, Object[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> aClass = JavaCompileUtil.class.getClassLoader().loadClass(path);
        Method add = aClass.getDeclaredMethod(methodName, new Class[]{Map.class});
        Object obj = aClass.newInstance();
        Object invoke = add.invoke(obj, args);
        System.out.println("compute result = " + invoke);
        return invoke;
    }

}
