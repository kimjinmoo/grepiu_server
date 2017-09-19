package com.iuom.springboot.common.util;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * String 유틸리티.
 * v1.0
 */
public class StringUtils {

    public static String getHelloWorld() {
        return "Hello World";
    }

    public static <T> Optional<T> isNull(Supplier<T> resolver) {
        try{
            T result = resolver.get();
            return Optional.ofNullable(result);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }
}
