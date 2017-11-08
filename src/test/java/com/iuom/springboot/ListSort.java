package com.iuom.springboot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 * 리스트 소팅
 *
 */
public class ListSort {

    public static void main(String[] args) {
        // 임시 domain
        class Vo {
            private int age;
            private String name;
            public Vo(int age,String name) {
                this.age = age;
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        List<Vo> list = new ArrayList<>();
        list.add(new Vo(1,"테스트3"));
        list.add(new Vo(2,"테스트2"));
        list.add(new Vo(3,"테스트5"));

        Comparator<Vo> c = (v1,v2)->v1.getName().compareTo(v2.getName());
        List<Vo> sortingList = list.stream().sorted(c).collect(Collectors.toList());
        for(Vo v : sortingList) {
            System.out.println(v.getAge()+"/"+v.getName());
        }
    }
}
