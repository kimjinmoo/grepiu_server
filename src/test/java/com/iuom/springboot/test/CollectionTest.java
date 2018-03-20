package com.iuom.springboot.test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * 콜렉션 특징/사용방법/Big O 시간복잡도 표현
 * 참고 URL :
 * https://gist.github.com/FedericoPonzi/8d5094dbae33cbb94536a73f62d1c1a0
 * http://bigocheatsheet.com/
 *
 * <pre>
 * 시간복잡도 대표 표현식 위일수록 빠르다.
 *   빠른 순서 ↑
 *   상수 시간    O(1)
 *   로그시간     O(log N)
 *   직선형 시간  O(logN)
 *   2차 시간     O(n^2)
 *   지수 시간    O(C^n)
 *   느린 순서 ↓
 * </pre>
 */


enum days {
  SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@Slf4j
public class CollectionTest {


  /**
   *
   * List 인터페이스
   *
   */
  @Test
  public void collectionsByList() {
    /**
     *
     * 시간복잡도
     * add             : O(1)
     * remove          : O(n)
     * get             : O(1)
     * Contains        : O(n)
     * iterator.remove : O(n)
     * 특징 :
     */
    List<String> arrayList = new ArrayList<>();

    /**
     *
     * 시간복잡도
     * add             : O(1)
     * remove          : O(1)
     * get             : O(n)
     * Contains        : O(n)
     * iterator.remove : O(1)
     *
     */
    List<String> linkedList = new LinkedList<>();

    /**
     *
     * 시간복잡도
     * add             : O(n)
     * remove          : O(n)
     * get             : O(1)
     * Contains        : O(n)
     * iterator.remove : O(n)
     *
     */
    List<String> copyOnWriteArraylist = new CopyOnWriteArrayList<>();
  }

  public void collectionsBySet() {
    /**
     *
     * 시간복잡도
     * add         :   O(1)
     * contains    :   O(1)
     * next        :   o(h/n) h는 테이블 용량
     *
     */
    Set<String> hashSet = new HashSet<>();

    /**
     *
     * 시간복잡도
     * add       : O(1)
     * contains  : O(1)
     * next      : O(1)
     */
    Set<String> linkedHashSet = new LinkedHashSet<>();

    /**
     *
     * 시간복잡도
     * add       : O(n)
     * contains  : O(n)
     * next      : O(1)
     */
    CopyOnWriteArraySet<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

    /**
     *
     * 시간복잡도
     * add       : O(1)
     * contains  : O(1)
     * next      : O(1)
     *
     */
    Set<days> enumSet = EnumSet.of(days.FRIDAY);

    /**
     *
     * 시간복잡도
     * add       : O(log n)
     * contains  : O(log n)
     * next      : O(long n)
     *
     */
    Set<String> treeSet = new TreeSet<>();

    /**
     *
     * 시간복잡도
     * add       : O(log n)
     * contains  : O(log n)
     * next      : O(1)
     */
    ConcurrentSkipListSet<String> concurrentSkipListSet = new ConcurrentSkipListSet<>();
  }

  public void collectionsByMap() {
    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(h/n) h는 테이블 용량
     *
     */
    Map<String, Object> hashMap = new HashMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(1)
     *
     */
    Map<String, Object> linkedHashMap = new LinkedHashMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(h/n) H는 테이블
     *
     */
    Map<String, Object> IdentityHashMap = new IdentityHashMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(1)
     *
     */
    Map<days, Object>  enumMap = new EnumMap(days.class);

    /**
     *
     * 시간복잡도
     * get           : O(log n)
     * containsKey   : O(log n)
     * next          : O(log n)
     *
     */
    Map<String, String> treeMap = new TreeMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(h/n) h는 테이블
     *
     */
    Map<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(log n)
     * containsKey   : O(log n)
     * next          : O(1)
     *
     */
    Map<String, Object> concurrentSkipListMap  = new ConcurrentSkipListMap<>();
  }

  public void collectionsByQueue() {

    /**
     *
     * 시간복잡도
     * offer         : O(log n)
     * peek          : O(1)
     * poll          : O(log n)
     * size          : O(1)
     */
    Queue<String> priorityQueue = new PriorityQueue<>();

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(n)
     *
     */
    Queue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(1)
     *
     */
    Queue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(5);

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(1)
     *
     */
    Queue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();

    /**
     *
     * 시간복잡도
     * offer         : O(log n)
     * peek          : O(1)
     * poll          : O(log n)
     * size          : O(1)
     *
     */
    Queue<String> priorityBlockingQueue = new PriorityBlockingQueue<>();

    /**
     *
     * 시간복잡도
     * offer         : O(log n)
     * peek          : O(1)
     * poll          : O(log n)
     * size          : O(1)
     *
     */
    Queue<String> delayQueue = new DelayQueue();

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(1)
     *
     */
    Queue<String> linkedList = new LinkedList<>();

  /**
   *
   * 시간복잡도
   * offer         : O(1)
   * peek          : O(1)
   * poll          : O(1)
   * size          : O(1)
   *
   */
    Queue<String> arrayList = new ArrayDeque<>();

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(1)
     *
     */
    Queue<String> linkedBlockingDeque = new LinkedBlockingDeque<>();
  }

}
