package net.jdg.disruption.annotation;


import net.jdg.disruption.util.misc.ArrayUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationUtil {


    @SuppressWarnings("rawtypes")
    public static Class<?>[] getAllClassesInPackage(String pkgName) {
        var dummy = new Dummy();
        var list = new ArrayList<Class>();
        var packages = dummy.getSubPackageNames(pkgName);
        for (String str : packages) {
            list.addAll(dummy.getAllClassesInPackage(str));
        }
        return list.toArray(new Class[]{});
    }

    public static MethodHandle[] getMethodsWithAnnotationInPackage(String pkgName,Class<? extends Annotation> annotationClazz, Class<?>... methodParams) {
        var classes = getAllClassesInPackage(pkgName);
        var annotatedMethods = new ArrayList<MethodHandle>();
        for (Class<?> clazz : classes) {
            var methods = clazz.getMethods();

            for (Method method : methods) {

                if (
                        method.accessFlags().contains(AccessFlag.PUBLIC)
                        && method.accessFlags().contains(AccessFlag.STATIC)
                        && ArrayUtil.equals(method.getParameterTypes(), methodParams)
                        && method.isAnnotationPresent(annotationClazz)
                ) {
                    try {
                        annotatedMethods.add(MethodHandles.publicLookup().unreflect(method));
                    } catch (Exception e) {
                        if (!(e instanceof IllegalAccessException)) { // ignored due to if statement checks
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        return annotatedMethods.toArray(new MethodHandle[]{});
    }

    private static class Dummy {

        private ArrayList<String> getSubPackageNames(String packageName) {
            var list = new ArrayList<String>();
            for (Package pkg : Arrays.stream(this.getClass().getClassLoader().getDefinedPackages()).filter(aPackage -> aPackage.getName().startsWith(packageName)).toList()) {
                list.add(pkg.getName());
            }
            return list;
        }

        @SuppressWarnings("rawtypes")
        public Set<Class> getAllClassesInPackage(String packageName) {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
            if (stream == null) return Set.of();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            return reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(line -> getClass(line, packageName))
                    .collect(Collectors.toSet());
        }

        @SuppressWarnings({"rawtypes"})
        private Class getClass(String className, String packageName) {
            try {
                return Class.forName(packageName + "."
                        + className.substring(0, className.lastIndexOf('.')));
            } catch (ClassNotFoundException e) {
                // handle the exception
            }
            return null;
        }
    }
}
