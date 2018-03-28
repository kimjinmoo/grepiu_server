package com.iuom.springboot.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 자료 구조
 * Stack
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@Slf4j
public class DataStructure {
    // Innser class 스택 구현
    class Stack {
        private int maxSize;
        private Integer[] array;
        private int top;
        public Stack(Integer maxSize){
            this.maxSize = maxSize;
            this.top = -1;
            this.array = new Integer[maxSize];
        }
        //등록
        public void push(int value){
            if(!isFull())
                array[++top]=value;
        }
        //최상위값 삭제;
        public int pop() {
            return array[top--];
        }
        //최상위값
        public int peek() {
            return array[top];
        }
        public boolean isEmpty(){
            return top==-1?true:false;
        }
        public boolean isFull(){
            return (top == maxSize-1);
        }
        public void showStack(){
            for(Integer v : array) {
                if(v != null) System.out.println("-------------------->>>"+v);
            }
        }
    }

    class Queue {

    }

    @Test
    public void stack() {
        Stack stack = new Stack(6);
        stack.push(10);
        stack.push(11);
        stack.push(12);
        stack.push(13);
        stack.push(14);
        stack.push(15);
        stack.push(16);
        stack.showStack();
    }

    public void queue() {

    }
}
