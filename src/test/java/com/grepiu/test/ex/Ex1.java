package com.grepiu.test.ex;

import org.springframework.data.mongodb.core.mapreduce.GroupBy;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Ex1 {

    // Complete the isValid function below.
    static String isValid(String s) {

        // 값을 담기 위해 HashMap을 이용
        HashMap<String, Integer> p = new HashMap<>();

        // 색인을 위한 변수
        int index =0;

        // 입력한 값을 read 하여 p map에 담는다.
        while(s.length() > index) {
            String key = s.substring(index, index+1);
            Integer value = 0;
            if(p.containsKey(key)) {
                value = p.get(key)+1;
            } else {
                value = 1;
                p.put(key, 1);
            }
            p.put(key, value);
            index++;
        }

        // 읽은 값들
        System.out.println("read Map: " + p);

        // 각 숫자들의 갯수를 Group By 한다.
        Map<Integer, Long> m = p.values().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // 읽은 문자열을 그룹별로 카운트
        System.out.println("read Map Group By: " + m);

        // 확인 로직1. 모두 같은 카운트면 Yes 리턴
        if(m.size() == 1) {
            return "YES";
        }

        // 확인 로직2. 숫자를 한개 제거 하거하면 같아지는지 확인 후 Yes 리턴
        Long minValue = m.values().stream().mapToLong(f->f).min().orElse(0l);
        Long keyMin = m.keySet().stream().mapToLong(f->f).min().orElse(0l);
        Long keyMax = m.keySet().stream().mapToLong(f->f).max().orElse(0l);

        if(m.size() == 2 && minValue == 1l) {
            // 카운트 그룹by가 2개 중 1개만 지우면 되는 케이스와 둘다 숫자가 많은 경우 1개차이인지 확인 하여 Yes 리턴
            if(m.get(keyMin.intValue()) == 1 || (keyMax-keyMin) == 1 ) {
                return "YES";
            }
        }

        return "NO";
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("d:/test.txt"));

        String s = scanner.nextLine();

        String result = isValid(s);

        System.out.println(result);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
