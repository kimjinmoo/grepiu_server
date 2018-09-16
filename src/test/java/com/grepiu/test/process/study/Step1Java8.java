package com.grepiu.test.process.study;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 간단한 Stream을 통한 연산
 *
 */
public class Step1Java8 {

    public static void main(String...args) {

        //Set 초기
        List<Book> books =  Arrays.asList(
                Book.builder().price(500).name("신기한책").isdn("111-1111").nation("KOR").count(0).build(),
                Book.builder().price(1000).name("좋은책").isdn("111-1112").nation("JAP").count(10).build(),
                Book.builder().price(3000).name("재미있는책").isdn("111-1113").nation("USA").count(1).build(),
                Book.builder().price(5000).name("최고의책").isdn("111-1113").nation("USA").count(4).build()
        );
        // 2000원 이상인거 찾기
        books.stream().filter(v->v.getPrice()>2000).forEach(System.out::println);

        // 총합산
        Long totPrice = books.stream().mapToLong(v->v.getPrice()).peek(System.out::println).sum();
        System.out.println(String.format("TOT : %d", totPrice));

        // 데이터 가공
        books.stream().map(v->{
            if(v.getCount()>1) {
                v.setEtc("매진");
            } else {
                v.setEtc("판매가능");
            }
            return v;
        }).forEach(System.out::println);
    }


}
@Builder
@Data
class Book {
    private String name;
    private String isdn;
    private Integer price;
    private String nation;
    private int count;
    private String etc;
}
