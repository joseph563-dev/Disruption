package net.jdg.disruption.util.misc;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

public class ArrayUtil {

    @SuppressWarnings("unchecked")
    public static <T> void shiftArray(T[] array, int by) {
        var newArr = (T[]) new Object[array.length];
        for (int i = 0; i < array.length; ++i) {
            var index = i - by;
            if (index < array.length && index >= 0) {
                newArr[i] = array[index];
            } else {
                newArr[i] = null;
            }
        }
        System.arraycopy(newArr, 0, array, 0, array.length);
    }

    public static <T> boolean equals(T[] a, T[] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; ++i) {
            if (!Objects.equals(a[i], b[i])) {
                return false;
            }
        }
        return true;
    }


    @SuppressWarnings("unchecked")
    public static <T> T[] resizeArray(T[] array, int newSize) {
        if (array.length == newSize) return array;
        var arr = (T[]) new Object[newSize];
        var i = 0;
        while (i < newSize) {
            arr[i] = array[i];
            i += 1;
        }
        return arr;
    }

    @SafeVarargs
    public static <T> T[] of(T... objects) {
        return objects;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] from(int size, Function<Integer, T> func) {
        var arr = (T[]) new Object[size];
        for (int i = 0; i < size; ++i) {
            arr[i] = func.apply(i);
        }
        return arr;
    }

    public static <T> T pickRandom(T[] array) {
        return array[(int) Math.floor(array.length * Math.random())];
    }

    public static <T> T getOrDefault(T[] array, int index, T def) {
        return index > 0 && index < array.length ? array[index] : def;
    }

    public static <T> T getLooped(T[] array, int index) {
        return array.length == 0 ? null : index != 0 ? array[index % array.length] : array[0];
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> T[] logicalOp(LogicalOperator type, T[]... arrays) {
        switch (arrays.length) {
            case 0 -> {return null;}
            case 1 -> {return arrays[0];}
            default -> {
                var list = new ArrayList<T>();
                for (int topI = 0; topI < arrays[0].length; ++topI) {
                    var lastCheck = false;
                    var failed = false;
                    for (int i = 0; i < arrays.length - 1; ++i) {
                        var obj1 = arrays[i][topI];
                        var obj2 = arrays[i + 1][topI];
                        lastCheck = i > 0 ? type.check(lastCheck, obj1.equals(obj2)) : obj1.equals(obj2);
                        if (!lastCheck) {
                            failed = true;
                            break;
                        }
                    }
                    if (!failed) list.add(arrays[0][topI]);
                }
                return list.toArray((T[]) new Object[]{});
            }
        }
    }
}
