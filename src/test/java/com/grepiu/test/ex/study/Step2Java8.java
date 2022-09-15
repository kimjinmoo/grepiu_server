package com.grepiu.test.ex.study;

/**
 *
 * functional interface
 *
 */
public class Step2Java8 {
    public static void main(String...args) {
        StringToNumber<String, Integer> toString = (String s) -> {
            return Integer.valueOf(s);
        };
        // 출력
        System.out.println("test : " + toString.run("2"));

        // 외부 변수
        int sampleNum = 0;
        Print<Integer> print = (Integer i) -> {
            System.out.println("free variable : "+sampleNum);
            System.out.println(i);
        };

        print.out(1);

        Print<String> carPrint = Car::getCarModel;
        carPrint.out("마티즈");

        Animal dog = new Animal();
        Print<String> animal = dog::bark;
        animal.out("멍멍");
    }
}

@FunctionalInterface
interface StringToNumber<T, R> {
    R run(T t);
}

@FunctionalInterface
interface Print<T> {
    void out(T t);
}

class Car {
    public static void  getCarModel(String model){
        System.out.println(model);
    }
}

class Animal {
    public void bark(String sound) {
        System.out.println(sound);
    }
}