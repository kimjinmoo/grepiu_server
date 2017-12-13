package com.iuom.springboot.common.util;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * Collection 유틸리티.
 * v1.0
 */
public class CollectionUtils {

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
