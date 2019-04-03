package com.grepiu.test.ex;

import java.util.*;

public class Test {
    public static void main(String[] arsg) {
        char compare = '0';
        char[] arrayStr = Integer.toBinaryString(1041).toCharArray();
        System.out.println(Integer.toBinaryString(1041));
        int search = 0;
        List<Integer> sum = new ArrayList<>();
        for(int i =0 ; i < arrayStr.length; i++) {
            if(arrayStr[i] == compare) {
                search++;
            }
            if(arrayStr.length > i+1) {
                if(arrayStr[i+1] == '1') {
                    sum.add(search);
                    search = 0;
                }
            }
        }
        sum.stream().forEach(System.out::println);
        System.out.println(sum.stream().max(Comparator.naturalOrder()).orElse(0));

    }
}
