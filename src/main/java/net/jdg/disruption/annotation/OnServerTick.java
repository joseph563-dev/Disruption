package net.jdg.disruption.annotation;

import net.minecraft.server.MinecraftServer;

import java.lang.annotation.*;
import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface OnServerTick {



    class ServerTickMethods {

        private static final ArrayList<MethodHandle> methods = new ArrayList<>();


        public static ArrayList<MethodHandle> getServerMethods() {
            return new ArrayList<>(methods);
        }
        public static void addMarkedMethods() {
            methods.addAll(Arrays.stream(AnnotationUtil.getMethodsWithAnnotationInPackage("net.jdg.disruption", OnServerTick.class, MinecraftServer.class)).toList());
        }
    }
}
